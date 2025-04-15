package com.example.voto.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.voto.databinding.ItemCameraBinding
import com.example.voto.databinding.ItemCartBinding
import com.example.voto.model.Cart
import com.example.voto.util.cart
import com.example.voto.util.helper
import com.example.voto.view.ui.CartScreen

class CartAdapter(private var activity: CartScreen, private var cartList: List<Cart>): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cartItem = cartList[position]
        var binding = holder.binding

        binding.checkbox.isChecked = cartItem.isChecked
        binding.tvName.text = cartItem.camera.name
        binding.tvPrice.text = helper.formatRupiah(cartItem.camera.price!!)
        binding.tvQty.text = cartItem.qty.toString()
        helper.showImage(binding.imgImage).execute(helper.urlImage + cartItem.camera.photo)
        activity.countTotal()
        
        binding.checkbox.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
//                if (cartItem.isChecked == false) {
//                    cartItem.isChecked = true
//                } else {
//                    cartItem.isChecked = true
//                }
                cartItem.isChecked = p1
                activity.countTotal()
            }
        })

        binding.btnMinus.setOnClickListener {
            if (binding.tvQty.text.toString() == "1") {
                cart.cartList.removeAt(position)
                notifyDataSetChanged()
            }

            cartItem.qty -= 1
            binding.tvQty.text = cartItem.qty.toString()

            var subTotal = cartItem.subtotal!! * cartItem.qty
            binding.tvPrice.text = helper.formatRupiah(subTotal)
            activity.countTotal()
        }

        binding.btnPlus.setOnClickListener {
            cartItem.qty += 1
            binding.tvQty.text = cartItem.qty.toString()

            var subTotal = cartItem.subtotal!! * cartItem.qty
            binding.tvPrice.text = helper.formatRupiah(subTotal)
            activity.countTotal()
        }

    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}