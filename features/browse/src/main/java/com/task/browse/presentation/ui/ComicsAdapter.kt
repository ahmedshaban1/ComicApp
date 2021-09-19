package com.task.browse.presentation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.browse.databinding.ComicCoverItemBinding
import com.task.common.loadImage
import com.task.model.Comic

class ComicsAdapter(private val dataList: ArrayList<Comic> = arrayListOf(), private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ComicCoverItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.layoutParams = ViewGroup.LayoutParams((parent.width * 0.9).toInt(),ViewGroup.LayoutParams.MATCH_PARENT)
        return ComicsAdapterVH(
            binding,
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicsAdapterVH -> {
                holder.bind(dataList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Comic>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun getLastItemNumber(): Int {
        return if(dataList.isEmpty()) -1
        else dataList.last().num

    }

    fun add(newComic: Comic) {
        dataList.add(newComic)
        notifyItemInserted(dataList.size-1)
    }


    class ComicsAdapterVH
    constructor(
        private val binding: ComicCoverItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comic: Comic) = with(itemView) {
            binding.comicImage.loadImage(comic.img)
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, comic)
            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Comic)
    }

}
