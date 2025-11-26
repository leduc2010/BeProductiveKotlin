package com.duc.beproductivekotlin.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.adapter.CollectionAdapter.CollectionViewHolder

class CollectionAdapter(
    var context: Context,
    var pokemonList: List<String>,
    var unlockedList: List<Boolean>
) :
    RecyclerView.Adapter<CollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        Log.d("PokemonAdapter", "Position: $position")
        val isUnlocked = unlockedList.getOrNull(position) ?: false

        Log.d("PokemonAdapter", "Loading image for pokemon: $pokemon")
        if (isUnlocked) {
            Glide.with(context)
                .load(Uri.parse("file:///android_asset/theme/pokemon/$pokemon"))
                .into(holder.ivPokemon)
        } else {
            Glide.with(context)
                .load(R.mipmap.ic_lock)
                .into(holder.ivPokemon)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun updatePokemonList(newList: List<String>) {
        this.pokemonList = newList
        notifyDataSetChanged()
    }

    fun updateUnlockedList(newList: List<Boolean>) {
        this.unlockedList = newList
        notifyDataSetChanged()
    }

    class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPokemon: ImageView = itemView.findViewById(R.id.ivPokemon)
    }
}