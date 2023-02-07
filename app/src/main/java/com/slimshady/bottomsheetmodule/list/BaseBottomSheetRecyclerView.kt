package com.slimshady.bottomsheetmodule.list

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.viewbinding.ViewBinding
import androidx.recyclerview.widget.RecyclerView
import com.slimshady.bottomsheetmodule.list.BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder
import com.slimshady.bottomsheetmodule.BaseBottomSheet
import com.slimshady.bottomsheetmodule.abstractions.IRecyclerView
import com.slimshady.bottomsheetmodule.util.searchWatcher.SearchWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import com.slimshady.bottomsheetmodule.R

open class BaseBottomSheetRecyclerView<V : ViewBinding>(
    viewBinding: V,
    context: Context,
    parentLayoutBottomSheetResId: Int,
    layoutManager: RecyclerView.LayoutManager?,
    bottomSheetRecyclerViewBuilder: BaseBottomSheetRecyclerViewBuilder<V>?
) : BaseBottomSheet<V>(viewBinding, context, parentLayoutBottomSheetResId), IRecyclerView {
    protected var view: View
    @JvmField
    protected var recyclerView: RecyclerView
    @JvmField
    protected var btnApply: Button
    @JvmField
    protected var searchView: SearchWatcher
    @JvmField
    protected var closeBtn: ImageView
    @JvmField
    protected var searchIcon: ImageView
    @JvmField
    protected var searchEditText: EditText
    private val layoutManager: RecyclerView.LayoutManager?
    private val dividerItemDecoration: DividerItemDecoration?
    override fun initRecyclerView() {
        initBottomSheetView(context)
        recyclerView.layoutManager = layoutManager
        if (dividerItemDecoration != null) recyclerView.addItemDecoration(dividerItemDecoration)
    }

    class BaseBottomSheetRecyclerViewBuilder<V : ViewBinding?> {
        var dividerItemDecoration: DividerItemDecoration? = null
        fun setDividerItemDecoration(dividerItemDecoration: DividerItemDecoration?): BaseBottomSheetRecyclerViewBuilder<V> {
            this.dividerItemDecoration = dividerItemDecoration
            return this
        }
    }

    init {
        view = viewBinding.root
        this.layoutManager = layoutManager
        dividerItemDecoration = bottomSheetRecyclerViewBuilder?.dividerItemDecoration
        recyclerView = view.findViewById(R.id.recyclerView_bottomSheet)
        btnApply = view.findViewById(R.id.button_recyclerView_bottomSheet)
        searchView = view.findViewById(R.id.searchView_recyclerView_bottomSheet)
        closeBtn = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button)
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        initRecyclerView()
    }
}