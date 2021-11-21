package com.example.shopinglisttesting.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shopinglisttesting.databinding.ItemImageBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor(private val glide: RequestManager): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(var binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)



    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var images: List<String>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]
        holder.itemView.apply {
         glide.load(url).into(holder.binding.ivShoppingImage)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(url)
            }
        }
    }

    private var onItemClickListener: ((String)->Unit)? = null

    fun setOnItemClickListener(listener: (String) ->Unit) {
        onItemClickListener = listener
    }
}
