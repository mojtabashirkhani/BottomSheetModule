package com.slimshady.bottomsheetmodule.list

import android.content.Context
import android.util.Log
import android.view.View

import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel
import androidx.viewbinding.ViewBinding
import androidx.recyclerview.widget.RecyclerView
import com.slimshady.bottomsheetmodule.list.BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemMultiSelectListener
import com.slimshady.bottomsheetmodule.list.BaseBottomSheetRecyclerView
import com.slimshady.bottomsheetmodule.abstractions.IDynamicList
import com.slimshady.bottomsheetmodule.abstractions.IApplyButton
import com.slimshady.bottomsheetmodule.abstractions.ISearch
import com.slimshady.bottomsheetmodule.adapter.AsyncSearchListAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.slimshady.bottomsheetmodule.util.searchWatcher.SearchWatcher
import android.widget.EditText
import android.widget.ImageView
import com.slimshady.bottomsheetmodule.util.searchWatcher.Watcher
import com.slimshady.bottomsheetmodule.util.AdapterAction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.slimshady.bottomsheetmodule.MainActivity
import java.util.*

class BottomSheetDynamicListMultiSelect<V : ViewBinding>(
    viewBinding: V,
    context: Context,
    parentLayoutBottomSheetResId: Int,
    layoutManager: RecyclerView.LayoutManager?,
    bottomSheetRecyclerViewBuilder: BaseBottomSheetRecyclerViewBuilder<V>?,
    private val isSearchEnable: Boolean,
    private val searchHint: String,
    private val items: ArrayList<BaseBottomSheetRecyclerModel>,
    private val adapterItemMultiSelectListener: MainActivity
) : BaseBottomSheetRecyclerView<V>(
    viewBinding,
    context,
    parentLayoutBottomSheetResId,
    layoutManager,
    bottomSheetRecyclerViewBuilder
), IDynamicList, IApplyButton, ISearch {
    private var asyncSearchListAdapter: AsyncSearchListAdapter<BaseBottomSheetRecyclerModel>? = null
    private var buttonLayoutParams: ConstraintLayout.LayoutParams? = null
    private var collapsedMargin //Button margin in collapsed state
            = 0
    private var buttonHeight = 0
    private var filteredListBaseSearchDbModel: ArrayList<BaseBottomSheetRecyclerModel>? = null
    override fun initListAdapter() {
        asyncSearchListAdapter = AsyncSearchListAdapter(true)
        asyncSearchListAdapter?.submitList(items)
        recyclerView.adapter = asyncSearchListAdapter
        recyclerView.itemAnimator = null
    }

    override fun initSearchView(
        context: Context,
        searchView: SearchWatcher,
        searchHint: String?,
        closeBtn: ImageView,
        searchIcon: ImageView,
        searchEditText: EditText
    ) {
        super.initSearchView(context, searchView, searchHint, closeBtn, searchIcon, searchEditText)
        searchView.addTextWatcher(object : Watcher {
            override fun onTextChange(s: String?) {
                if ((s?.trim { it <= ' ' }?.length ?: 0) > 0) {
                    if (s != null) {
                        filter(s, items)
                    }
                } else {
                    asyncSearchListAdapter?.submitList(items)
                }
            }
        }, 400)
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
                asyncSearchListAdapter?.selectedItems,
                AdapterAction.SELECT
            )
            bottomSheetBehavior?.peekHeight = 0
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun filter(
        query: String,
        items: ArrayList<BaseBottomSheetRecyclerModel>
    ) {
        filteredListBaseSearchDbModel = ArrayList()
        for (i in items.indices) {
            val text = items[i].name?.lowercase(Locale.getDefault())
            if (text?.contains(query) == true) {
                filteredListBaseSearchDbModel?.add(items[i])
            }
        }
        Log.d("BottomSheetSearch", "filteredList: " + filteredListBaseSearchDbModel?.size)
        asyncSearchListAdapter = AsyncSearchListAdapter(true)
        recyclerView.adapter = asyncSearchListAdapter
        asyncSearchListAdapter?.submitList(filteredListBaseSearchDbModel)
    }

    override fun closeButtonSearchListener() {
        closeBtn.setOnClickListener {
            searchView.clearFocus()
            searchView.setQuery("", false)
            bottomSheetBehavior?.peekHeight = 0
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    init {
        if (isSearchEnable) {
            searchView.visibility = View.VISIBLE
            initSearchView(context, searchView, searchHint, closeBtn, searchIcon, searchEditText)
            closeButtonSearchListener()
        } else searchView.visibility = View.GONE
        btnApply.visibility = View.VISIBLE
        initListAdapter()
        initApplyButtonView()
        onClickApplyButton()
    }
}