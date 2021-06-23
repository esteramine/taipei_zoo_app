package com.example.taipeizooapp.museumDetail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.taipeizooapp.models.MuseumModel
import com.example.taipeizooapp.models.PlantModel
import com.example.taipeizooapp.R
import com.example.taipeizooapp.plantDetail.PlantDetailActivity

class MuseumDetailActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var img: ImageView? = null
    private var body: TextView? = null
    private var memo: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var errorMsg: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum_detail)

        initViews()

        //TODO: null safety && add 無休館資訊 string source && concatenate text in setText -> string resource
        val item = intent.getParcelableExtra<MuseumModel>("DATA")
        setSupportActionBar(findViewById(R.id.toolbar))
        toolbar?.title = item?.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar?.setNavigationOnClickListener {
                onBackPressed()
            }
        }

        img?.apply {
            Glide.with(context).load(item?.img).into(this)
        }
        body?.text = item?.body

        memo?.text = item?.memo + "\n" + item?.category

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "\thttps://data.taipei/api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire&&q=" + item!!.title

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val list = response.getJSONObject("result").getJSONArray("results")
                    val plantList = ArrayList<PlantModel>()
                    for (i in 0 until list.length()) {
                        val item = list.getJSONObject(i)
                        plantList.add(
                                PlantModel(
                                        item.getString("F_Pic01_URL"),
                                        "植物名", //item.getString("﻿F_Name_Ch"),
                                        item.getString("F_Name_En"),
                                        item.getString("F_AlsoKnown"),
                                        item.getString("F_Brief"),
                                        item.getString("F_Feature"),
                                        item.getString("F_Function＆Application"),
                                        item.getString("F_Update")
                                )
                        )
                    }

                    recyclerView?.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = MuseumDetailAdapter(plantList, context, object: MuseumDetailAdapter.onClickPlantDetailListener{
                            override fun onClickItem(position: Int, item: PlantModel) {
                                val intent = Intent(context, PlantDetailActivity::class.java).apply {
                                    putExtra("DATA", item)
                                }
                                startActivity(intent)
                            }
                        })
                        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    }

                },
                {
                    errorMsg?.visibility = View.VISIBLE
                })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)

    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        img = findViewById(R.id.img)
        body = findViewById(R.id.body)
        memo = findViewById(R.id.memo)
        recyclerView = findViewById(R.id.recyclerView)
        errorMsg = findViewById(R.id.errorMsg)
    }
}