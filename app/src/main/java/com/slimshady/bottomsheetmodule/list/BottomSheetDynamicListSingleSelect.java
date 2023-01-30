package com.slimshady.bottomsheetmodule.list;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.slimshady.bottomsheetmodule.abstractions.IDynamicList;
import com.slimshady.bottomsheetmodule.abstractions.ISearch;
import com.slimshady.bottomsheetmodule.adapter.AsyncSearchListAdapter;
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemListener;
import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel;
import com.slimshady.bottomsheetmodule.util.AdapterAction;
import com.slimshady.bottomsheetmodule.util.searchWatcher.SearchWatcher;

import java.util.ArrayList;

public class BottomSheetDynamicListSingleSelect<T extends BaseBottomSheetRecyclerModel, V extends ViewBinding> extends BaseBottomSheetRecyclerView<V> implements IDynamicList, ISearch {

    private boolean isSearchEnable;
    private String searchHint;
    private ArrayList<T> items;
    private AdapterItemListener<T> adapterItemListener;

    private AsyncSearchListAdapter<T> asyncSearchListAdapter;

    private ArrayList<T> filteredListBaseSearchDbModel;

    public BottomSheetDynamicListSingleSelect(
            V viewBinding,
            Context context,
            int parentLayoutBottomSheetResId,
            RecyclerView.LayoutManager layoutManager,
            BaseBottomSheetRecyclerViewBuilder<V> bottomSheetRecyclerViewBuilder,
            boolean isSearchEnable,
            String searchHint,
            ArrayList<T> items,
            AdapterItemListener<T> adapterItemListener
    ) {

        super(viewBinding, context, parentLayoutBottomSheetResId, layoutManager, bottomSheetRecyclerViewBuilder);

        this.isSearchEnable = isSearchEnable;
        this.searchHint = searchHint;
        this.items = items;
        this.adapterItemListener = adapterItemListener;

        if (isSearchEnable) {
            searchView.setVisibility(View.VISIBLE);
            initSearchView(context, searchView, searchHint, closeBtn, searchIcon, searchEditText);
            closeButtonSearchListener();

        } else
            searchView.setVisibility(View.GONE);

        btnApply.setVisibility(View.INVISIBLE);

        initListAdapter();

        recyclerView.setPadding(0, 0, 0, (int) (btnApply.getHeight() * 1.5));
        recyclerView.setItemAnimator(null);

    }

    @Override
    public void initListAdapter() {
        asyncSearchListAdapter = new AsyncSearchListAdapter<T>(false, new AdapterItemListener<T>() {
            @Override
            public void onItemSelect(T model, int position, AdapterAction action) {
                adapterItemListener.onItemSelect(model, position, action);
                searchView.setQuery("", false);
                bottomSheetBehavior.setPeekHeight(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

        asyncSearchListAdapter.submitList(items);
        recyclerView.setAdapter(asyncSearchListAdapter);

    }

    @Override
    protected void initSearchView(Context context, SearchWatcher searchView, String searchHint, ImageView closeBtn, ImageView searchIcon, EditText searchEditText) {
        super.initSearchView(context, searchView, searchHint, closeBtn, searchIcon, searchEditText);

        searchView.addTextWatcher(s -> {
            if (s.trim().length() > 0) {
                filter(s, items);
            } else {
                asyncSearchListAdapter.submitList(items);
            }
        }, 400);
    }

    private void filter(String query,
                        ArrayList<T> items) {
        filteredListBaseSearchDbModel = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {

            final String text = items.get(i).getName().toLowerCase();
            if (text.contains(query)) {

                filteredListBaseSearchDbModel.add(items.get(i));
            }
        }

        Log.d("BottomSheetSearch", "filteredList: " + filteredListBaseSearchDbModel.size());

        asyncSearchListAdapter = new AsyncSearchListAdapter<T>(false, new AdapterItemListener<T>() {
            @Override
            public void onItemSelect(T model, int position, AdapterAction action) {
                adapterItemListener.onItemSelect(model, position, action);
                searchView.setQuery("", false);
                bottomSheetBehavior.setPeekHeight(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                filteredListBaseSearchDbModel.clear();

            }
        });

        recyclerView.setAdapter(asyncSearchListAdapter);
        asyncSearchListAdapter.submitList(filteredListBaseSearchDbModel);

    }

    @Override
    public void closeButtonSearchListener() {
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                searchView.setQuery("", false);
                bottomSheetBehavior.setPeekHeight(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
}
