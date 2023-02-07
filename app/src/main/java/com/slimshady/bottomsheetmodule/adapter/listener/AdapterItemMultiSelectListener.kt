package com.slimshady.bottomsheetmodule.adapter.listener

import com.slimshady.bottomsheetmodule.util.AdapterAction
import java.util.ArrayList

interface AdapterItemMultiSelectListener<T> {
    fun onItemMultiSelect(model: ArrayList<T>?, action: AdapterAction?)
}