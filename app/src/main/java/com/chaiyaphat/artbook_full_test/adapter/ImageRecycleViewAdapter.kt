package com.chaiyaphat.artbook_full_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.chaiyaphat.artbook_full_test.R
import javax.inject.Inject

class ImageRecycleViewAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ImageRecycleViewAdapter.ImageViewHolder>() {
    private var onItemClickListener: ((String) -> Unit)? = null

    //DiffUI
    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    private val recycleListDiffer = AsyncListDiffer(this, diffUtil)

    var images: List<String>
        get() = recycleListDiffer.currentList
        set(value) = recycleListDiffer.submitList(value)

    //DiffUI

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.singleArtImageView)
        val url = images[position]
        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnItemClickListener{
                onItemClickListener?.let {
                    it(url)
                }
            }
        }
    }

    override fun getItemCount(): Int = images.size
}