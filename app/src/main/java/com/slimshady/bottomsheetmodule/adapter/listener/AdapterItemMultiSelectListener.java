package com.slimshady.bottomsheetmodule.adapter.listener;

import com.slimshady.bottomsheetmodule.util.AdapterAction;

import java.util.ArrayList;

public interface AdapterItemMultiSelectListener<T> {
    void onItemMultiSelect(ArrayList<T> model, AdapterAction action);

}
