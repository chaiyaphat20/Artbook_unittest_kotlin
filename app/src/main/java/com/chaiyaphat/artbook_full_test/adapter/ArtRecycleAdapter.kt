package com.chaiyaphat.artbook_full_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.chaiyaphat.artbook_full_test.R
import com.chaiyaphat.artbook_full_test.roomdb.Art
import javax.inject.Inject

class ArtRecycleAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ArtRecycleAdapter.ArtViewHolder>() {
    //DiffUI
    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }
    private val recycleListDiffer = AsyncListDiffer(this, diffUtil)

     var arts: List<Art>
        get() = recycleListDiffer.currentList
        set(value) = recycleListDiffer.submitList(value)

    //DiffUI
    class ArtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row, parent, false)
        return ArtViewHolder(view)

    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
        val nameText = holder.itemView.findViewById<TextView>(R.id.artRowArtNameText)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.artRowArtistNameText)
        val yearText = holder.itemView.findViewById<TextView>(R.id.artRowYearText)
        val art = arts[position]
        holder.itemView.apply {
            nameText.text = "Name: ${art.name}"
            artistNameText.text = "Artist Name: ${art.name}"
            yearText.text = "Year: ${art.name}"
            glide.load(art.imageUrl).into(imageView)
        }
    }

    override fun getItemCount(): Int = arts.size
}