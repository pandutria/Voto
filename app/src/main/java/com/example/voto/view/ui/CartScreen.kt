package com.example.voto.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voto.R
import com.example.voto.databinding.ActivityCartScreenBinding
import com.example.voto.util.cart
import com.example.voto.util.helper
import com.example.voto.view.adapter.CartAdapter

class CartScreen : AppCompatActivity() {
    lateinit var binding: ActivityCartScreenBinding
    var total_price = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        binding.rv.adapter = CartAdapter(this, cart.cartList)
        binding.rv.layoutManager = LinearLayoutManager(this)

        countTotal()

        binding.btn.setOnClickListener {
            removeItem()
            startActivity(Intent(this, CheckoutScreen::class.java).apply {
                putExtra("total", total_price)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rv.adapter = CartAdapter(this, cart.cartList)
        countTotal()
    }

    fun removeItem() {
        for (i in cart.cartList.toList()) {
            if (!i.isChecked) {
                cart.cartList.remove(i)
            }
        }
    }


    fun countTotal() {
        var total = 0
        for (i in cart.cartList) {
            if (i.subtotal != null && i.isChecked) {
                total += (i.subtotal!! * i.qty)
            }
            total_price = total
            binding.tvTotal.text = helper.formatRupiah(total)
        }
    }
}