package com.example.e_readerbookstore.model

data class Book(
    val id: Int = -1,
    val title: String,
    val author: String,
    val imageResId: Int = 0, // Keep for backward compatibility or placeholder
    val imageUrl: String = "",
    val isInStock: Boolean,
    val price: Double,
    val category: String
)
