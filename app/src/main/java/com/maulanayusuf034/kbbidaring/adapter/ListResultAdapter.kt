package com.maulanayusuf034.kbbidaring.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.maulanayusuf034.kbbidaring.R

class ListResultAdapter(private val listArti: ArrayList<String>) : RecyclerView.Adapter<ListResultAdapter.ListViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    inner class ListViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private val timeLine: TimelineView = itemView.findViewById(R.id.timeline)
        val artiTextView: TextView = itemView.findViewById(R.id.arti_kata)

        init {
            timeLine.initLine(viewType)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_result_mean, parent, false)
        return ListViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val arti = listArti[position]
        holder.artiTextView.text = arti
        if (position == 0) {
            holder.itemView.margin(top = 15F)
        }
    }

    override fun getItemCount(): Int {
        return listArti.size
    }
}