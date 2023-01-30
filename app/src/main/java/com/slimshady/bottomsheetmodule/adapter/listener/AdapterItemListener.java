package com.slimshady.bottomsheetmodule.adapter.listener;

import com.slimshady.bottomsheetmodule.util.AdapterAction;

public interface AdapterItemListener<T> {
    void onItemSelect(T model, int position, AdapterAction Action);
}
