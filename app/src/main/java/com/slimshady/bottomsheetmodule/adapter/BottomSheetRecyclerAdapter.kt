package com.slimshady.bottomsheetmodule.adapter

import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel
import androidx.recyclerview.widget.RecyclerView
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemListener
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.slimshady.bottomsheetmodule.databinding.ItemBaseSearchBinding
import com.slimshady.bottomsheetmodule.util.AdapterAction
import java.util.ArrayList

class BottomSheetRecyclerAdapter<T : BaseBottomSheetRecyclerModel?> :
    RecyclerView.Adapter<BottomSheetRecyclerAdapter<T>.ViewHolder> {
    private var items: ArrayList<T>
    private var isMultiSelect: Boolean
    private var adapterItemListener: AdapterItemListener<T>? = null

    constructor(
        items: ArrayList<T>,
        isMultiSelect: Boolean,
        adapterItemListener: AdapterItemListener<T>?
    ) {
        this.items = items
        this.isMultiSelect = isMultiSelect
        this.adapterItemListener = adapterItemListener
    }

    constructor(items: ArrayList<T>, isMultiSelect: Boolean) {
        this.items = items
        this.isMultiSelect = isMultiSelect
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBaseSearchBinding =
            ItemBaseSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBaseSearchBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(var binding: ItemBaseSearchBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(baseSearchDbModel: T) {
            binding.txtSearch.text = baseSearchDbModel?.name
            if (isMultiSelect) binding.checkboxSearch.visibility =
                View.VISIBLE else binding.checkboxSearch.visibility = View.GONE
            binding.root.setOnClickListener {
                if (isMultiSelect) toggleSelection(
                    baseSearchDbModel,
                    binding
                ) else adapterItemListener?.onItemSelect(
                    baseSearchDbModel,
                    absoluteAdapterPosition,
                    AdapterAction.SELECT
                )
            }
        }
    }

    fun isSelected(position: Int): Boolean {
        return items[position]?.isSelected ?: false
    }

    fun toggleSelection(baseSearchDbModel: T, binding: ItemBaseSearchBinding) {
        if (baseSearchDbModel?.isSelected == true) {
            baseSearchDbModel.isSelected = false
            binding.checkboxSearch.isChecked = false

        } else {
            baseSearchDbModel?.isSelected = true
            binding.checkboxSearch.isChecked = true

        }
    }

    fun selectAll() {
        for (i in items.indices) {
            items[i]?.isSelected = true
        }
    }

    fun clearSelections() {
        for (i in items.indices) {
            items[i]?.isSelected = false
        }
    }

    val selectedItemCount: Int
        get() {
            var count = 0
            for (i in items.indices) {
                if (items[i]?.isSelected == true) count++
            }
            return count
        }
    val selectedItems: ArrayList<T>
        get() {
            val mDifferTemp = ArrayList<T>()
            for (i in items.indices) {
                if (items[i]?.isSelected == true) mDifferTemp.add(items[i])
            }
            return mDifferTemp
        }
}