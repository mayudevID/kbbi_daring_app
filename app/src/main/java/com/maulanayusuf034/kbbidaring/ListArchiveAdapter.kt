package com.maulanayusuf034.kbbidaring

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maulanayusuf034.kbbidaring.room.Kosakata
import java.text.ParsePosition

class ListArchiveAdapter (private val listKk: ArrayList<Kosakata>) : RecyclerView.Adapter<ListArchiveAdapter.ListArchiveViewHolder>() {
    inner class ListArchiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vocab: TextView = itemView.findViewById(R.id.vocabList)
        val lema: TextView = itemView.findViewById(R.id.lemaList)
    }

    override fun onBindViewHolder(holder:  ListArchiveViewHolder, position: Int) {
        val data = listKk[position]
        holder.lema.text = data.lema
        holder.vocab.text = data.vocab
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListArchiveViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_archive, parent, false)
        return ListArchiveViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listKk.size
    }

    fun setData (list: List<Kosakata>) {
        listKk.clear()
        listKk.addAll(list)
        notifyDataSetChanged()
    }
}