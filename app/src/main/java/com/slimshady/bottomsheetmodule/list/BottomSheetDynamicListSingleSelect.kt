package com.slimshady.bottomsheetmodule.list

import android.content.Context
import android.util.Log
import android.view.View

import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel
import androidx.viewbinding.ViewBinding
import androidx.recyclerview.widget.RecyclerView
import com.slimshady.bottomsheetmodule.list.BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemListener
import com.slimshady.bottomsheetmodule.list.BaseBottomSheetRecyclerView
import com.slimshady.bottomsheetmodule.abstractions.IDynamicList
import com.slimshady.bottomsheetmodule.abstractions.ISearch
import com.slimshady.bottomsheetmodule.adapter.AsyncSearchListAdapter
import com.slimshady.bottomsheetmodule.util.AdapterAction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.slimshady.bottomsheetmodule.util.searchWatcher.SearchWatcher
import android.widget.EditText
import android.widget.ImageView
import com.slimshady.bottomsheetmodule.util.searchWatcher.Watcher
import java.util.*

class BottomSheetDynamicListSingleSelect<V : ViewBinding>(
    viewBinding: V,
    context: Context,
    parentLayoutBottomSheetResId: Int,
    layoutManager: RecyclerView.LayoutManager?,
    bottomSheetRecyclerViewBuilder: BaseBottomSheetRecyclerViewBuilder<V>?,
    private val isSearchEnable: Boolean,
    private val searchHint: String,
    private val items: ArrayList<BaseBottomSheetRecyclerModel>,
    private val adapterItemListener: AdapterItemListener<BaseBottomSheetRecyclerModel>
) : BaseBottomSheetRecyclerView<V>(
    viewBinding,
    context,
    parentLayoutBottomSheetResId,
    layoutManager,
    bottomSheetRecyclerViewBuilder
), IDynamicList, ISearch {
    private var asyncSearchListAdapter: AsyncSearchListAdapter<BaseBottomSheetRecyclerModel>? = null
    private var filteredListBaseSearchDbModel: ArrayList<BaseBottomSheetRecyclerModel>? = null
    override fun initListAdapter() {
        asyncSearchListAdapter = AsyncSearchListAdapter(false, object : AdapterItemListener<BaseBottomSheetRecyclerModel> {
            override fun onItemSelect(model: BaseBottomSheetRecyclerModel, position: Int, action: AdapterAction?) {
                adapterItemListener.onItemSelect(model, position, action)
                searchView.setQuery("", false)
                bottomSheetBehavior?.peekHeight = 0
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })
        asyncSearchListAdapter?.submitList(items)
        recyclerView.adapter = asyncSearchListAdapter
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
        asyncSearchListAdapter = AsyncSearchListAdapter(false, object : AdapterItemListener<BaseBottomSheetRecyclerModel> {
            override fun onItemSelect(model: BaseBottomSheetRecyclerModel, position: Int, action: AdapterAction?) {
                adapterItemListener.onItemSelect(model, position, action)
                searchView.setQuery("", false)
                bottomSheetBehavior?.peekHeight = 0
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                filteredListBaseSearchDbModel?.clear()
            }
        })
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
        btnApply.visibility = View.INVISIBLE
        initListAdapter()
        recyclerView.setPadding(0, 0, 0, (btnApply.height * 1.5).toInt())
        recyclerView.itemAnimator = null
    }
}