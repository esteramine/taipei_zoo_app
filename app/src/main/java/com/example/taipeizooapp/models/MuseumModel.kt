package com.example.taipeizooapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MuseumModel(
    val img: String,
    val title: String,
    val body: String,
    val memo: String,
    val category: String,
    val url: String = ""
): Parcelable