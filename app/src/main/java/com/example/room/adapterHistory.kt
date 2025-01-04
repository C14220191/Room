package com.example.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.room.database.historyBarang

class adapterHistory (private val historyBarang: MutableList <historyBarang>):
        RecyclerView.Adapter<adapterHistory.ListViewHolder>() {
    class ListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvHTanggal)
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.tvHItemBarang)
        var _tvJumlah = itemView.findViewById<TextView>(R.id.tvHJumlah)
    }

    fun isiData(history: List<historyBarang>){
        historyBarang.clear()
        historyBarang.addAll(history)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterHistory.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.historylayout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterHistory.ListViewHolder, position: Int) {
        var history = historyBarang[position]

        holder._tvTanggal.setText(history.tanggal)
        holder._tvItemBarang.setText(history.item)
        holder._tvJumlah.setText(history.jumlah)
    }

    override fun getItemCount(): Int {
        return historyBarang.size
    }
}