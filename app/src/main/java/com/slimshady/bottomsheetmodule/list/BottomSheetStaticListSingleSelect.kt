package com.slimshady.bottomsheetmodule.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.slimshady.bottomsheetmodule.abstractions.IStaticList
import com.slimshady.bottomsheetmodule.adapter.BottomSheetRecyclerAdapter
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemListener
import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel
import com.slimshady.bottomsheetmodule.util.AdapterAction

class BottomSheetStaticListSingleSelect<V : ViewBinding>(
    viewBinding: V,
    context: Context,
    parentLayoutBottomSheetResId: Int,
    layoutManager: RecyclerView.LayoutManager?,
    bottomSheetRecyclerViewBuilder: BaseBottomSheetRecyclerViewBuilder<V>?,
    private val items: ArrayList<BaseBottomSheetRecyclerModel>,
    private val adapterItemListener: AdapterItemListener<BaseBottomSheetRecyclerModel>
) : BaseBottomSheetRecyclerView<V>(
    viewBinding,
    context,
    parentLayoutBottomSheetResId,
    layoutManager,
    bottomSheetRecyclerViewBuilder
), IStaticList {
    private var bottomSheetRecyclerAdapter: BottomSheetRecyclerAdapter<BaseBottomSheetRecyclerModel>? = null
    override fun initRecyclerViewAdapter() {
        bottomSheetRecyclerAdapter = BottomSheetRecyclerAdapter(
            items,
            false,
            object : AdapterItemListener<BaseBottomSheetRecyclerModel> {
                override fun onItemSelect(model: BaseBottomSheetRecyclerModel, position: Int, action: AdapterAction?) {
                    adapterItemListener.onItemSelect(model, position, action)
                    bottomSheetBehavior?.peekHeight = 0
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            })
        recyclerView.adapter = bottomSheetRecyclerAdapter
    }

    init {
        searchView.visibility = View.GONE
        btnApply.visibility = View.INVISIBLE
        recyclerView.setPadding(0, 0, 0, (btnApply.height * 1.5).toInt())
        initRecyclerViewAdapter()
    }
}