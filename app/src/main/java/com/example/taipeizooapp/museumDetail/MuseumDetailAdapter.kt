package com.example.taipeizooapp.museumDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taipeizooapp.models.PlantModel
import com.example.taipeizooapp.R

class MuseumDetailAdapter(private val items: ArrayList<PlantModel>,
                          private val context: Context,
                          private val listener: onClickPlantDetailListener):
        RecyclerView.Adapter<MuseumDetailAdapter.MuseumDetailViewHolder>() {

    inner class MuseumDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.img)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val body: TextView = itemView.findViewById(R.id.body)
        private val memo: TextView = itemView.findViewById(R.id.memo)

        fun initViews(item: PlantModel) {
            Glide.with(context).load(item.img).centerCrop().into(img)
            title.text = item.chineseName
            body.text = item.alsoKnown
            memo.visibility = View.GONE
            itemView.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClickItem(bindingAdapterPosition, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuseumDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)

        return MuseumDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: MuseumDetailViewHolder, position: Int) {
        holder.initViews(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface onClickPlantDetailListener {
        fun onClickItem(position: Int, item: PlantModel)
    }
}