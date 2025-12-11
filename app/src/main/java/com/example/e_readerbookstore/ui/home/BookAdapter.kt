package com.example.e_readerbookstore.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.e_readerbookstore.R
import com.example.e_readerbookstore.model.Book
import com.google.android.material.button.MaterialButton

class BookAdapter(
    private var books: List<Book>,
    private val onCartClick: (Book) -> Unit,
    private val onFavoriteClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBookImage: ImageView = view.findViewById(R.id.ivBookImage)
        val tvBookTitle: TextView = view.findViewById(R.id.tvBookTitle)
        val tvBookAuthor: TextView = view.findViewById(R.id.tvBookAuthor)
        val tvBookPrice: TextView = view.findViewById(R.id.tvBookPrice)
        val tvStockStatus: TextView = view.findViewById(R.id.tvStockStatus)
        val btnFavorite: MaterialButton = view.findViewById(R.id.btnFavorite)
        val btnAddToCart: MaterialButton = view.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.tvBookTitle.text = book.title
        holder.tvBookAuthor.text = book.author
        holder.tvBookPrice.text = "$${book.price}"
        
        // Load Image using Coil
        if (book.imageUrl.isNotEmpty()) {
            holder.ivBookImage.load(book.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.app_logo)
                error(R.drawable.app_logo)
            }
        } else {
            holder.ivBookImage.setImageResource(book.imageResId)
        }

        val context = holder.itemView.context
        if (book.isInStock) {
            holder.tvStockStatus.text = context.getString(R.string.in_stock)
            holder.tvStockStatus.setTextColor(androidx.core.content.ContextCompat.getColor(context, R.color.green))
            holder.btnAddToCart.isEnabled = true
            holder.btnAddToCart.alpha = 1.0f
        } else {
            holder.tvStockStatus.text = context.getString(R.string.out_of_stock)
            holder.tvStockStatus.setTextColor(androidx.core.content.ContextCompat.getColor(context, R.color.red))
            holder.btnAddToCart.isEnabled = false
            holder.btnAddToCart.alpha = 0.5f
        }

        holder.btnAddToCart.setOnClickListener { onCartClick(book) }
        holder.btnFavorite.setOnClickListener { onFavoriteClick(book) }
    }

    override fun getItemCount() = books.size
    
    fun updateData(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
