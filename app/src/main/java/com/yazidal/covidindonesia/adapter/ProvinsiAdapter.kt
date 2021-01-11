package com.yazidal.covidindonesia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yazidal.covidindonesia.R
import com.yazidal.covidindonesia.model.DataItem
import kotlinx.android.synthetic.main.list_provinsi.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ProvinsiAdapter(private val provinsi: ArrayList<DataItem>, private val clickList: (DataItem)-> Unit):
    RecyclerView.Adapter<ProvinsiViewHolder>(), Filterable {

    var covidList = ArrayList<DataItem>()

    init {
        covidList = provinsi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinsiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_provinsi, parent, false)
        return ProvinsiViewHolder(view)
    }

    override fun getItemCount(): Int {
        return covidList.size
    }

    override fun onBindViewHolder(holder: ProvinsiViewHolder, position: Int) {
        holder.bind(covidList[position], clickList)
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                covidList = if (charSearch.isEmpty()){
                    provinsi
                }else{
                    val resultList = ArrayList<DataItem>()
                    for (row in provinsi){
                        val search = row.provinsi?.toLowerCase(Locale.ROOT)?: ""
                        if (search.contains(charSearch.toLowerCase(Locale.ROOT))){
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = covidList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                covidList = results?.values as ArrayList<DataItem>
                notifyDataSetChanged()
            }

        }
    }

}
class ProvinsiViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(provinsi:DataItem, clickList: (DataItem) -> Unit) {
        val provinsi_name : TextView = itemView.tvProvinsi
        val provinsi_positif : TextView = itemView.tvPositif
        val provinsi_meninggal : TextView = itemView.tvMeninggal
        val provinsi_sembuh : TextView = itemView.tvSembuh

        val formatter: NumberFormat = DecimalFormat("#,###")

        provinsi_name.tvProvinsi.text = provinsi.provinsi
        provinsi_positif.tvPositif.text = formatter.format(provinsi.kasusPosi?.toDouble())
        provinsi_sembuh.tvSembuh.text = formatter.format(provinsi.kasusSemb?.toDouble())
        provinsi_meninggal.tvMeninggal.text = formatter.format(provinsi.kasusMeni?.toDouble())

    }
}