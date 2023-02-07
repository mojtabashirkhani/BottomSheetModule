package com.slimshady.bottomsheetmodule

import android.content.Context
import android.view.View
import com.slimshady.bottomsheetmodule.util.ScreenUtils.getWindowHeight
import androidx.viewbinding.ViewBinding
import com.slimshady.bottomsheetmodule.abstractions.AbstractBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.slimshady.bottomsheetmodule.R
import android.view.ViewGroup

open class BaseBottomSheet<V : ViewBinding>(
    protected var viewBinding: V,
    protected var context: Context,
    protected var parentLayoutBottomSheetResId: Int
) : AbstractBottomSheet() {
    protected var viewBottomsheet: View
    @JvmField
    protected var bottomSheetBehavior: BottomSheetBehavior<*>?
    @JvmField
    protected var expandedHeight //Height of bottom sheet in expanded state
            = 0
    @JvmField
    protected var peekHeight = 0
    override fun initBottomSheetView(context: Context) {
        viewBottomsheet.setBackgroundResource(R.drawable.bottom_dialog_shape)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

        //Retrieve bottom sheet parameters
        val bottomSheetLayoutParams = viewBottomsheet.layoutParams
        bottomSheetLayoutParams.height = getWindowHeight(context)
        expandedHeight = bottomSheetLayoutParams.height
        peekHeight =
            (expandedHeight / 2.3).toInt() //Peek height to 70% of expanded height (Change based on your view)

        //Setup bottom sheet
        viewBottomsheet.layoutParams = bottomSheetLayoutParams
        BottomSheetBehavior.from(viewBottomsheet).skipCollapsed = false
        BottomSheetBehavior.from(viewBottomsheet).peekHeight = peekHeight
        BottomSheetBehavior.from(viewBottomsheet).isHideable = true
    }

    override fun closeBottomSheet() {
        if (bottomSheetBehavior != null) {
            if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED ||
                bottomSheetBehavior?.state == BottomSheetBehavior.STATE_COLLAPSED &&
                bottomSheetBehavior?.peekHeight != 0
            ) {
                bottomSheetBehavior?.peekHeight = 0
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    init {
        val view = viewBinding.root
        viewBottomsheet = view.findViewById(parentLayoutBottomSheetResId)
        bottomSheetBehavior = BottomSheetBehavior.from(viewBottomsheet)
    }
}