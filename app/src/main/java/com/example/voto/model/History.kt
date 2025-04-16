package com.example.voto.model

data class History(
    var id: String? = null,
    var status: String? = null,
    var totalPrice: Int? = null,
    var cart: List<Cart>
)
