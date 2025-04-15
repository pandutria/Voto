package com.example.voto.view.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voto.R
import com.example.voto.databinding.ActivityCartScreenBinding

class CartScreen : AppCompatActivity() {
    lateinit var binding: ActivityCartScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}