package com.example.e_readerbookstore.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.example.e_readerbookstore.R
import com.example.e_readerbookstore.database.BookStoreDatabase
import com.example.e_readerbookstore.model.CartItem

class CartFragment : Fragment() {

    private lateinit var dbHelper: BookStoreDatabase
    private lateinit var adapter: CartAdapter
    private var userId: Int = -1
    private var currentType = CartItem.TYPE_CART
    
    private lateinit var btnShowCart: MaterialButton
    private lateinit var btnShowFavorites: MaterialButton
    private lateinit var tvEmptyState: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = BookStoreDatabase(requireContext())
        
        val sharedPref = activity?.getSharedPreferences("EReaderPrefs", Context.MODE_PRIVATE)
        userId = sharedPref?.getInt("USER_ID", -1) ?: -1

        btnShowCart = view.findViewById(R.id.btnShowCart)
        btnShowFavorites = view.findViewById(R.id.btnShowFavorites)
        tvEmptyState = view.findViewById(R.id.tvEmptyState)
        val rvCartItems = view.findViewById<RecyclerView>(R.id.rvCartItems)
        
        rvCartItems.layoutManager = LinearLayoutManager(context)
        adapter = CartAdapter(emptyList()) { book ->
            dbHelper.removeItem(userId, book.id, currentType)
            loadItems()
        }
        rvCartItems.adapter = adapter

        btnShowCart.setOnClickListener {
            currentType = CartItem.TYPE_CART
            updateTabUI()
            loadItems()
        }

        btnShowFavorites.setOnClickListener {
            currentType = CartItem.TYPE_FAVORITE
            updateTabUI()
            loadItems()
        }
        
        updateTabUI()
        loadItems()
    }

    private fun updateTabUI() {
        val context = requireContext()
        if (currentType == CartItem.TYPE_CART) {
            btnShowCart.backgroundTintList = ContextCompat.getColorStateList(context, R.color.primary_orange)
            btnShowCart.setTextColor(ContextCompat.getColor(context, R.color.white))
            btnShowFavorites.backgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
            btnShowFavorites.setTextColor(ContextCompat.getColor(context, R.color.gray))
            btnShowFavorites.strokeColor = ContextCompat.getColorStateList(context, R.color.gray)
        } else {
            btnShowCart.backgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
            btnShowCart.setTextColor(ContextCompat.getColor(context, R.color.gray))
            btnShowCart.strokeColor = ContextCompat.getColorStateList(context, R.color.gray)
            btnShowFavorites.backgroundTintList = ContextCompat.getColorStateList(context, R.color.primary_orange)
            btnShowFavorites.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    private fun loadItems() {
        if (userId == -1) return
        
        val items = dbHelper.getItems(userId, currentType)
        adapter.updateData(items)
        
        if (items.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            tvEmptyState.text = if (currentType == CartItem.TYPE_CART) getString(R.string.empty_cart) else getString(R.string.empty_favorites)
        } else {
            tvEmptyState.visibility = View.GONE
        }
    }
}
