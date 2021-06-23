package com.example.taipeizooapp.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taipeizooapp.models.MuseumModel
import com.example.taipeizooapp.R

class HomeActivityAdapter(
        private val items: ArrayList<MuseumModel>,
        private val context: Context,
        private val listener: onClickMuseumDetailListener
    ):
    RecyclerView.Adapter<HomeActivityAdapter.HomeActivityViewHolder>() {

    inner class HomeActivityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.img)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val body: TextView = itemView.findViewById(R.id.body)
        private val memo: TextView = itemView.findViewById(R.id.memo)

        fun initViews(item: MuseumModel) {
            Glide.with(context).load(item.img).centerCrop().into(img)
            title.text = item.title
            body.text = item.body
            memo.text = item.memo

            itemView.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClickItem(bindingAdapterPosition, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeActivityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)

        return HomeActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeActivityViewHolder, position: Int) {
        holder.initViews(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface onClickMuseumDetailListener {
        fun onClickItem(position: Int, item: MuseumModel)
    }
}



