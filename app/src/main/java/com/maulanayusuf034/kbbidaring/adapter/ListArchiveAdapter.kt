package com.maulanayusuf034.kbbidaring.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maulanayusuf034.kbbidaring.R
import com.maulanayusuf034.kbbidaring.room.Kosakata

class ListArchiveAdapter (private val listKk: ArrayList<Kosakata>) : RecyclerView.Adapter<ListArchiveAdapter.ListArchiveViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListArchiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vocab: TextView = itemView.findViewById(R.id.vocabList)
        val lema: TextView = itemView.findViewById(R.id.lemaList)
        val deleteButton: View = itemView.findViewById(R.id.deleteButton)
    }

    override fun onBindViewHolder(holder:  ListArchiveViewHolder, position: Int) {
        val data = listKk[position]
        holder.lema.text = data.lema
        holder.vocab.text = data.vocab
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listKk[holder.adapterPosition])
        }
        holder.deleteButton.setOnClickListener {
            onItemClickCallback.onItemDeleted(listKk[holder.adapterPosition])
        }
        if (position == 0) {
            holder.itemView.margin(left = 10F, right = 10F, bottom = 8F, top = 16F)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListArchiveViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_archive, parent, false)
        return ListArchiveViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listKk.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData (list: List<Kosakata>) {
        listKk.clear()
        listKk.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Kosakata)
        fun onItemDeleted(data: Kosakata)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


}