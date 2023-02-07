package com.slimshady.bottomsheetmodule.adapter.diffUtil

import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel
import androidx.recyclerview.widget.DiffUtil

class SearchListAdapterDiffUtil<T : BaseBottomSheetRecyclerModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.name == newItem.name
    }
}