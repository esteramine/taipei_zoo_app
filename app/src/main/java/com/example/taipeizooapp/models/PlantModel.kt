package com.example.taipeizooapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantModel(
        val img: String,
        val chineseName: String,
        val englishName: String,
        val alsoKnown: String,
        val brief: String,
        val features: String,
        val functions: String,
        val latestUpdate: String
): Parcelable