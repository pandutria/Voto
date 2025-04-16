package com.example.voto.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voto.databinding.ItemHistoryBinding
import com.example.voto.model.History
import com.example.voto.util.helper

class HistoryAdapter(private var historyList: List<History>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var history = historyList[position]
        var binding = holder.binding
        binding.tvId.text = history.id.toString()
        binding.tvStatus.text = "Status: ${history.status}"
        binding.tvTotal.text = helper.formatRupiah(history.totalPrice!!)

        binding.rvNested.adapter = NestedAdapter(history.cart)
        binding.rvNested.setHasFixedSize(true)
        binding.rvNested.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}