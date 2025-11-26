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
import com.duc.beproductivekotlin.db.entities.Explore

class NewArticleAdapter(
    val listNewArt: List<Explore>,
    val context: Context,
    val clickListener: (Explore) -> Unit
) :
    RecyclerView.Adapter<NewArticleAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivArticle = itemView.findViewById<ImageView>(R.id.ivArticle)
        var tvArticle = itemView.findViewById<TextView>(R.id.tvArticle)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_new_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newArtItem = listNewArt[position]
        val assetPath = "file:///android_asset/explore/${newArtItem.image}.jpg"
        Glide.with(context).load(assetPath).into(holder.ivArticle)
        holder.tvArticle.text = newArtItem.title
        holder.itemView.setOnClickListener {
            clickListener(newArtItem)
        }
    }

    override fun getItemCount(): Int {
        return listNewArt.size
    }
}
