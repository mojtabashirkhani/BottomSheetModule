package com.slimshady.bottomsheetmodule.list

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.slimshady.bottomsheetmodule.abstractions.IApplyButton
import com.slimshady.bottomsheetmodule.abstractions.IStaticList
import com.slimshady.bottomsheetmodule.adapter.BottomSheetRecyclerAdapter
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemMultiSelectListener
import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel
import com.slimshady.bottomsheetmodule.util.AdapterAction

class BottomSheetStaticListMultiSelect<V : ViewBinding>(
    viewBinding: V,
    context: Context,
    parentLayoutBottomSheetResId: Int,
    layoutManager: RecyclerView.LayoutManager?,
    bottomSheetRecyclerViewBuilder: BaseBottomSheetRecyclerViewBuilder<V>?,
    private val items: ArrayList<BaseBottomSheetRecyclerModel>,
    private val adapterItemMultiSelectListener: AdapterItemMultiSelectListener<BaseBottomSheetRecyclerModel>
) : BaseBottomSheetRecyclerView<V>(
    viewBinding,
    context,
    parentLayoutBottomSheetResId,
    layoutManager,
    bottomSheetRecyclerViewBuilder
), IStaticList, IApplyButton {
    private var bottomSheetRecyclerAdapter: BottomSheetRecyclerAdapter<BaseBottomSheetRecyclerModel>? = null
    private var buttonLayoutParams: ConstraintLayout.LayoutParams? = null
    private var collapsedMargin //Button margin in collapsed state
            = 0
    private var buttonHeight = 0
    override fun initRecyclerViewAdapter() {
        bottomSheetRecyclerAdapter = BottomSheetRecyclerAdapter(items, true)
        recyclerView.adapter = bottomSheetRecyclerAdapter
    }

    override fun initApplyButtonView() {
        buttonLayoutParams = btnApply.layoutParams as ConstraintLayout.LayoutParams

        //Calculate button margin from top
        buttonHeight =
            btnApply.height //How tall is the button + experimental distance from bottom (Change based on your view)
        collapsedMargin =
            (peekHeight - buttonHeight * 1.2).toInt() //Button margin in bottom sheet collapsed state
        buttonLayoutParams?.topMargin = collapsedMargin
        btnApply.layoutParams = buttonLayoutParams
        recyclerView.setPadding(0, 0, 0, (buttonHeight * 2.2).toInt())
        initBottomSheetCallback(
            bottomSheetBehavior,
            btnApply,
            buttonLayoutParams,
            collapsedMargin,
            expandedHeight,
            buttonHeight
        )
    }

    override fun onClickApplyButton() {
        btnApply.setOnClickListener {
            adapterItemMultiSelectListener.onItemMultiSelect(
                bottomSheetRecyclerAdapter?.selectedItems,
                AdapterAction.SELECT
            )
            bottomSheetBehavior?.peekHeight = 0
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    init {
        searchView.visibility = View.GONE
        btnApply.visibility = View.VISIBLE
        initRecyclerViewAdapter()
        initApplyButtonView()
        onClickApplyButton()
    }
}