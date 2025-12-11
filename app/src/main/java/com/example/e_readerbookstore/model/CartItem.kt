package com.example.e_readerbookstore.model

data class CartItem(
    val id: Int = -1,
    val userId: Int,
    val bookId: Int,
    val type: Int // 0 for Cart, 1 for Favorite
) {
    companion object {
        const val TYPE_CART = 0
        const val TYPE_FAVORITE = 1
    }
}
