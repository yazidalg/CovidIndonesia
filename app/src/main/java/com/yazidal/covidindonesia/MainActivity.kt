package com.yazidal.covidindonesia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazidal.covidindonesia.adapter.ProvinsiAdapter
import com.yazidal.covidindonesia.model.DataItem
import com.yazidal.covidindonesia.model.ResponseProvinsi
import com.yazidal.covidindonesia.network.ApiService
import com.yazidal.covidindonesia.network.RetrofitBuilder.retrofit
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var adapterProvinsi: ProvinsiAdapter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterProvinsi.filter.filter(newText)
                return false
            }

        })
                swipe_refresh.setOnRefreshListener {
            getProvinsi()
            swipe_refresh.isRefreshing = false
        }

        getProvinsi()
    }

    private fun getProvinsi() {
        var api = retrofit.create(ApiService::class.java)
        api.getAllProvinsi().enqueue(object : Callback<ResponseProvinsi> {
            override fun onFailure(call: Call<ResponseProvinsi>, t: Throwable) {
                progBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<ResponseProvinsi>,
                response: Response<ResponseProvinsi>
            ) {
                if (response.isSuccessful){
                    val formatter: NumberFormat = DecimalFormat("#,###")
                    rv_CovidIndo.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        progBar.visibility = View.GONE
                        adapterProvinsi = ProvinsiAdapter(
                            response.body()!!.data as ArrayList<DataItem>
                        ){}
                        adapter = adapterProvinsi
                    }
                }
            }

        })
    }

}