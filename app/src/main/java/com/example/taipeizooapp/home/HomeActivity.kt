package com.example.taipeizooapp.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.taipeizooapp.models.MuseumModel
import com.example.taipeizooapp.R
import com.example.taipeizooapp.museumDetail.MuseumDetailActivity as DetailActivity

class HomeActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://data.taipei/api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire"

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val list = response.getJSONObject("result").getJSONArray("results")
                    val museumList = ArrayList<MuseumModel>()
                    for (i in 0 until list.length()) {
                        val item = list.getJSONObject(i)
                        museumList.add(
                                MuseumModel(
                                        "https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1200,h_630,f_auto/w_80,x_15,y_15,g_south_west,l_klook_water/activities/gthipcp6frsaz9xsdayv/Taipei%20Zoo%20Combo%20Tickets%20.jpg",
                                        //item.getString("E_Pic_URL"),
                                        item.getString("E_Name"),
                                        item.getString("E_Info"),
                                        if (item.getString("E_Memo").isEmpty()) "無休館資訊" else item.getString("E_Memo"),
                                        item.getString("E_Category"),
                                        item.getString("E_URL")
                                )
                        )
                    }

                    recyclerView?.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = HomeActivityAdapter(museumList, context, object: HomeActivityAdapter.onClickMuseumDetailListener {
                            override fun onClickItem(position: Int, item: MuseumModel) {
                                val intent = Intent(context, DetailActivity::class.java).apply {
                                    putExtra("DATA", item)
                                }
                                startActivity(intent)
                            }
                        })
                        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    }

                },
                {
                    textView?.apply {
                        visibility  = View.VISIBLE
                        text = "API Error!"
                    }
                })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun initViews() {
        textView = findViewById(R.id.txtView)
        recyclerView = findViewById(R.id.recyclerview);
    }

}