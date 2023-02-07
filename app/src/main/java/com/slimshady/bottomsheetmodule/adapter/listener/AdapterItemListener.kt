package com.slimshady.bottomsheetmodule.adapter.listener

import com.slimshady.bottomsheetmodule.util.AdapterAction

interface AdapterItemListener<T> {
    fun onItemSelect(model: T, position: Int, Action: AdapterAction?)
}