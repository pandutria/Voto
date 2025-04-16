package com.example.voto.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voto.databinding.ItemHistoryBinding
import com.example.voto.databinding.ItemNestedBinding
import com.example.voto.model.Cart
import com.example.voto.util.helper

class NestedAdapter(private var cartList: List<Cart>): RecyclerView.Adapter<NestedAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemNestedBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = ItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cart = cartList[position]
        var binding = holder.binding
        binding.tvName.text = cart.camera.name
        binding.tvQty.text = "(${cart.qty})"
        binding.tvSubtotal.text = helper.formatRupiah(cart.subtotal!!)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}