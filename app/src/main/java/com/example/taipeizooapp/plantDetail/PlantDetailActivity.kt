package com.example.taipeizooapp.plantDetail

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.taipeizooapp.models.PlantModel
import com.example.taipeizooapp.R

class PlantDetailActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var img: ImageView? = null
    private var chineseName: TextView? = null
    private var englishName: TextView? = null
    private var alsoKnown: TextView? = null
    private var brief: TextView? = null
    private var features: TextView? = null
    private var functions: TextView? = null
    private var latestUpdate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        initViews()

        val item = intent.getParcelableExtra<PlantModel>("DATA")
        setSupportActionBar(findViewById(R.id.toolbar))
        toolbar?.title = item?.chineseName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar?.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        Glide.with(this).load(item?.img).centerCrop().into(img!!)
        chineseName?.text = item?.chineseName
        englishName?.text = item?.englishName
        alsoKnown?.text = item?.alsoKnown
        brief?.text = item?.brief
        features?.text = item?.features
        functions?.text = item?.functions
        latestUpdate?.append(item?.latestUpdate)
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        img = findViewById(R.id.img)
        chineseName = findViewById(R.id.chineseName)
        englishName = findViewById(R.id.englishName)
        alsoKnown = findViewById(R.id.alsoKnown)
        brief = findViewById(R.id.brief)
        features = findViewById(R.id.features)
        functions = findViewById(R.id.functions)
        latestUpdate = findViewById(R.id.latestUpdate)
    }
}