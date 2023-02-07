package com.slimshady.bottomsheetmodule.adapter

import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel
import androidx.recyclerview.widget.AsyncListDiffer
import com.slimshady.bottomsheetmodule.adapter.diffUtil.SearchListAdapterDiffUtil
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemListener
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slimshady.bottomsheetmodule.databinding.ItemBaseSearchBinding
import com.slimshady.bottomsheetmodule.util.AdapterAction
import java.util.ArrayList

class AsyncSearchListAdapter<T : BaseBottomSheetRecyclerModel> :
    ListAdapter<T, AsyncSearchListAdapter<T>.ViewHolder> {
    private var isMultiSelect: Boolean
    private val mDiffer = AsyncListDiffer(this, SearchListAdapterDiffUtil<T>())
    private var adapterItemListener: AdapterItemListener<T>? = null

    constructor(isMultiSelect: Boolean, adapterItemListener: AdapterItemListener<T>?) : super(
        SearchListAdapterDiffUtil<T>()
    ) {
        this.isMultiSelect = isMultiSelect
        this.adapterItemListener = adapterItemListener
    }

    constructor(isMultiSelect: Boolean) : super(SearchListAdapterDiffUtil<T>()) {
        this.isMultiSelect = isMultiSelect
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBaseSearchBinding =
            ItemBaseSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBaseSearchBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(mDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun submitList(list: List<T>?) {
        mDiffer.submitList(list)
    }

    inner class ViewHolder(var binding: ItemBaseSearchBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(baseSearchDbModel: T) {
            binding.txtSearch.text = baseSearchDbModel.name
            if (!isMultiSelect) {
                binding.checkboxSearch.visibility = View.GONE
            }
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
        return mDiffer.currentList[position]?.isSelected ?: false
    }

    fun toggleSelection(baseSearchDbModel: T, binding: ItemBaseSearchBinding) {
        if (baseSearchDbModel.isSelected) {
            baseSearchDbModel.isSelected = false
            binding.checkboxSearch.isChecked = false

        } else {
            baseSearchDbModel.isSelected = true
            binding.checkboxSearch.isChecked = true

        }
    }

    fun selectAll() {
        for (i in mDiffer.currentList.indices) {
            mDiffer.currentList[i]?.isSelected = true
        }
    }

    fun clearSelections() {
        for (i in mDiffer.currentList.indices) {
            mDiffer.currentList[i]?.isSelected = false
        }
    }

    val selectedItemCount: Int
        get() {
            var count = 0
            for (i in mDiffer.currentList.indices) {
                if (mDiffer.currentList[i]?.isSelected == true) count++
            }
            return count
        }
    val selectedItems: ArrayList<T>
        get() {
            val mDifferTemp = ArrayList<T>()
            for (i in mDiffer.currentList.indices) {
                if (mDiffer.currentList[i]?.isSelected == true) mDifferTemp.add(mDiffer.currentList[i])
            }
            return mDifferTemp
        }
}