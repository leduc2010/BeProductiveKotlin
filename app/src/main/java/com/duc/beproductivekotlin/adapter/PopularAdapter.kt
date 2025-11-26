package com.duc.beproductivekotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.adapter.PopularAdapter.ViewHolder
import com.duc.beproductivekotlin.db.entities.Explore

class PopularAdapter(
    val popularList: List<Explore>,
    val context: Context,
    val clickListener: (Explore) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_popular, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val popularItem = popularList[position]
        val assetPath = "file:///android_asset/explore/${popularItem.image}.jpg"
        Glide.with(context).load(assetPath).into(holder.ivPopular)
        holder.tvPopular.text = popularItem.title
        holder.itemView.setOnClickListener {
            clickListener(popularItem)
        }
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPopular = itemView.findViewById<ImageView>(R.id.ivPopular)
        var tvPopular = itemView.findViewById<TextView>(R.id.tvPopular)
    }
}