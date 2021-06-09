package com.example.mviexampleinit.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.mviexampleinit.R
import com.example.mviexampleinit.model.Blog
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*

class MainRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Blog>() {

        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return RecyclerAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_blog_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecyclerAdapterViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Blog>) {
        differ.submitList(list)
    }

    class RecyclerAdapterViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Blog) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.blog_title.text = item.title
            Glide.with(itemView.context)
                .load(item.image)
                .into(itemView.blog_image)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Blog)
    }
}
