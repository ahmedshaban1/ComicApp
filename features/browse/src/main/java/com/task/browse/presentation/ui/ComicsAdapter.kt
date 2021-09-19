package com.task.browse.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.browse.databinding.ComicCoverItemBinding
import com.task.model.Comic

class ComicsAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comic>() {

        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.num == newItem.num
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.num == newItem.num
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ComicCoverItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ComicsAdapterVH(
            binding,
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicsAdapterVH -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Comic>) {
        differ.submitList(list)
    }

    class ComicsAdapterVH
    constructor(
        private val binding: ComicCoverItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comic: Comic) = with(itemView) {
            binding.comicTitleTv.text = comic.title
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, comic)
            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Comic)
    }

}
