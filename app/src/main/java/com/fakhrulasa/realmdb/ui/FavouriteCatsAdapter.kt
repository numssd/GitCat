package com.fakhrulasa.realmdb.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.fakhrulasa.realmdb.R
import com.fakhrulasa.realmdb.ui.models.CatItem

class FavouriteCatsAdapter : RecyclerView.Adapter<FavouriteCatsAdapter.CatsViewHolder>() {

    private var catsList = ArrayList<CatItem>()

    fun setData(cats: List<CatItem>?) {
        catsList.clear()
        if (cats != null) {
            catsList.addAll(cats)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return CatsViewHolder(item)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        val catItem = catsList[position]
        holder.onBind(catItem)
    }

    override fun getItemCount(): Int {
        return catsList.size
    }

    class CatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img = itemView.findViewById<ImageView>(R.id.img_cat)
        fun onBind(cat: CatItem) {
            val uri = Uri.parse(cat.url)
            img.setImageURI(uri)
        }

    }
}