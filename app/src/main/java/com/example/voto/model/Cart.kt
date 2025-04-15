package com.example.voto.model

data class Cart (
    var camera: Camera,
    var qty: Int,
    var subtotal: Int? = null,
    var isChecked: Boolean = false
)