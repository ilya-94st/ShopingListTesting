package com.example.shopinglisttesting.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shopinglisttesting.data.local.ShoppingItems
import com.example.shopinglisttesting.databinding.ItemsShoppingBinding
import javax.inject.Inject

class ShoppingAdapter @Inject constructor(private val glide: RequestManager): RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    inner class ShoppingViewHolder(var binding: ItemsShoppingBinding) : RecyclerView.ViewHolder(binding.root)



    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItems>() {
        override fun areItemsTheSame(oldItem: ShoppingItems, newItem: ShoppingItems): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItems, newItem: ShoppingItems): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItems: List<ShoppingItems>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val binding = ItemsShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val url = shoppingItems[position]
        holder.itemView.apply {
            glide.load(url).into(holder.binding.ivShoppingImage)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(url)
            }
        }
    }

    private var onItemClickListener: ((ShoppingItems)->Unit)? = null

    fun setOnItemClickListener(listener: (ShoppingItems) ->Unit) {
        onItemClickListener = listener
    }
}
