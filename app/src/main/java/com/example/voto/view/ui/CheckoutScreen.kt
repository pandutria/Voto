package com.example.voto.view.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voto.R
import com.example.voto.databinding.ActivityCheckoutScreenBinding
import com.example.voto.util.helper

class CheckoutScreen : AppCompatActivity() {
    var total = 0

    lateinit var binding: ActivityCheckoutScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
            CartScreen().removeItem()
        }

        total = intent.getIntExtra("total", 0)
        binding.tvSubtotal.text = "Subtotal:          ${helper.formatRupiah(total)}"
        binding.tvFee.text = "Delivery Fee:         ${helper.formatRupiah(helper.fee)}"
        binding.tvTotal.text = "Total: ${helper.formatRupiah(total + helper.fee)}"
    }
}