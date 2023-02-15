package com.slimshady.bottomsheetmodule.abstractions

import android.content.Context
import android.view.View
import android.widget.Button
import com.slimshady.bottomsheetmodule.util.searchWatcher.SearchWatcher
import android.widget.EditText
import android.widget.ImageView
import com.slimshady.bottomsheetmodule.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback

abstract class AbstractBottomSheet {
    protected abstract fun initBottomSheetView(context: Context)
    abstract fun closeBottomSheet()
    protected open fun initSearchView(
        context: Context,
        searchView: SearchWatcher,
        searchHint: String?,
        closeBtn: ImageView,
        searchIcon: ImageView,
        searchEditText: EditText
    ) {
        searchEditText.setHintTextColor(context.resources.getColor(R.color.teal_200))
        searchEditText.setTextColor(context.resources.getColor(R.color.teal_200))
        closeBtn.setColorFilter(context.resources.getColor(R.color.teal_200))
        searchIcon.setColorFilter(context.resources.getColor(R.color.teal_200))

        // open search view by default
        searchView.setIconifiedByDefault(true)
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.clearFocus()
        searchView.queryHint = searchHint
    }

    protected fun initBottomSheetCallback(
        bottomSheetBehavior: BottomSheetBehavior<*>?,
        btnApply: Button,
        buttonLayoutParams: ConstraintLayout.LayoutParams?,
        collapsedMargin: Int,
        expandedHeight: Int,
        buttonHeight: Int
    ) {
        bottomSheetBehavior?.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset > 0) //Sliding happens from 0 (Collapsed) to 1 (Expanded) - if so, calculate margins
                    buttonLayoutParams?.topMargin =
                        ((expandedHeight - buttonHeight * 2 - collapsedMargin - buttonHeight ) * slideOffset + collapsedMargin).toInt()
                else  //If not sliding above expanded, set initial margin
                    buttonLayoutParams?.topMargin = collapsedMargin
                btnApply.layoutParams =
                    buttonLayoutParams //Set layout params to button (margin from top)
            }
        })
    }
}