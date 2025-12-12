package com.example.e_readerbookstore.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_readerbookstore.R
import com.example.e_readerbookstore.database.BookStoreDatabase
import com.example.e_readerbookstore.model.CartItem

class HomeFragment : Fragment() {

    private lateinit var dbHelper: BookStoreDatabase
    private lateinit var adapter: BookAdapter
    private lateinit var categoryAdapter: CategoryFilterAdapter
    private var userId: Int = -1
    private var currentCategory: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = BookStoreDatabase(requireContext())
        
        val sharedPref = activity?.getSharedPreferences("EReaderPrefs", Context.MODE_PRIVATE)
        userId = sharedPref?.getInt("USER_ID", -1) ?: -1

        // Build categories dynamically from DB so filter labels always match stored values
        val categoriesFromDb = dbHelper.getCategories()
        val categories = listOf(getString(R.string.filter_all)) + categoriesFromDb
        currentCategory = categories.firstOrNull() ?: getString(R.string.filter_all)
        val rvCategoryFilters = view.findViewById<RecyclerView>(R.id.rvCategoryFilters)
        rvCategoryFilters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryFilterAdapter(categories) { category ->
            currentCategory = category
            loadBooks(category)
        }
        rvCategoryFilters.adapter = categoryAdapter

        // Setup books RecyclerView
        val rvBooks = view.findViewById<RecyclerView>(R.id.rvBooks)
        rvBooks.layoutManager = GridLayoutManager(context, 2)

        adapter = BookAdapter(emptyList(), 
            onCartClick = { book ->
                if (userId != -1) {
                    val result = dbHelper.addToCartOrFavorite(userId, book.id, CartItem.TYPE_CART)
                    if (result > -1) {
                        Toast.makeText(context, getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Already in Cart", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show()
                }
            },
            onFavoriteClick = { book ->
                if (userId != -1) {
                    val result = dbHelper.addToCartOrFavorite(userId, book.id, CartItem.TYPE_FAVORITE)
                    if (result > -1) {
                        Toast.makeText(context, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Already in Favorites", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show()
                }
            }
        )
        rvBooks.adapter = adapter
        
        loadBooks(currentCategory)
    }

    private fun loadBooks(category: String = currentCategory) {
        val books = if (category.equals(getString(R.string.filter_all), ignoreCase = true)) {
            dbHelper.getAllBooks()
        } else {
            dbHelper.getBooksByCategory(category)
        }
        adapter.updateData(books)
    }
}
