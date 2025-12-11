package com.example.e_readerbookstore.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_readerbookstore.R
import com.example.e_readerbookstore.model.Book

class CartAdapter(
    private var books: List<Book>,
    private val onRemoveClick: (Book) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBookImage: ImageView = view.findViewById(R.id.ivBookImage)
        val tvBookTitle: TextView = view.findViewById(R.id.tvBookTitle)
        val tvBookAuthor: TextView = view.findViewById(R.id.tvBookAuthor)
        val tvBookPrice: TextView = view.findViewById(R.id.tvBookPrice)
        val btnRemove: ImageButton = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        // Reuse item_book but maybe hide some elements or create a new item_cart.
        // For simplicity, I'll create a simple item_cart.xml or reuse item_book and modify it programmatically?
        // Let's create item_cart.xml for clarity.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val book = books[position]
        holder.tvBookTitle.text = book.title
        holder.tvBookAuthor.text = book.author
        holder.tvBookPrice.text = "$${book.price}"
        holder.ivBookImage.setImageResource(book.imageResId)

        holder.btnRemove.setOnClickListener { onRemoveClick(book) }
    }

    override fun getItemCount() = books.size
    
    fun updateData(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
