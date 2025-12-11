package com.example.e_readerbookstore.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_readerbookstore.R
import com.google.android.material.chip.Chip

class CategoryFilterAdapter(
    private val categories: List<String>,
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<CategoryFilterAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chip: Chip = itemView.findViewById(R.id.chipCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_chip, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.chip.text = category
        val isSelected = position == selectedPosition
        holder.chip.isChecked = isSelected
        
        // Update chip appearance based on selection
        val context = holder.itemView.context
        if (isSelected) {
            holder.chip.chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.primary_orange)
            holder.chip.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.chip.chipStrokeWidth = 0f
        } else {
            holder.chip.chipBackgroundColor = ContextCompat.getColorStateList(context, android.R.color.transparent)
            holder.chip.chipStrokeWidth = 2f
            holder.chip.chipStrokeColor = ContextCompat.getColorStateList(context, R.color.primary_orange)
            holder.chip.setTextColor(ContextCompat.getColor(context, R.color.primary_orange))
        }
        
        holder.chip.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            onCategorySelected(category)
        }
    }

    override fun getItemCount() = categories.size
}

