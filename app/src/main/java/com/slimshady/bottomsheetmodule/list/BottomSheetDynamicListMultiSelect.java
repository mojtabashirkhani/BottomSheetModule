package com.slimshady.bottomsheetmodule.list;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.slimshady.bottomsheetmodule.abstractions.IApplyButton;
import com.slimshady.bottomsheetmodule.abstractions.IDynamicList;
import com.slimshady.bottomsheetmodule.abstractions.ISearch;
import com.slimshady.bottomsheetmodule.adapter.AsyncSearchListAdapter;
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemMultiSelectListener;
import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel;
import com.slimshady.bottomsheetmodule.util.AdapterAction;
import com.slimshady.bottomsheetmodule.util.searchWatcher.SearchWatcher;

import java.util.ArrayList;

public class BottomSheetDynamicListMultiSelect<T extends BaseBottomSheetRecyclerModel, V extends ViewBinding> extends BaseBottomSheetRecyclerView<V> implements IDynamicList, IApplyButton, ISearch {

    private boolean isSearchEnable;
    private String searchHint;
    private ArrayList<T> items;
    private AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener;


    private AsyncSearchListAdapter<T> asyncSearchListAdapter;

    private ConstraintLayout.LayoutParams buttonLayoutParams;
    private int collapsedMargin; //Button margin in collapsed state
    private int buttonHeight;

    private ArrayList<T> filteredListBaseSearchDbModel;

    public BottomSheetDynamicListMultiSelect(
            V viewBinding,
            Context context,
            int parentLayoutBottomSheetResId,
            RecyclerView.LayoutManager layoutManager,
            BaseBottomSheetRecyclerViewBuilder<V> bottomSheetRecyclerViewBuilder,
            boolean isSearchEnable,
            String searchHint,
            ArrayList<T> items,
            AdapterItemMultiSelectListener<T> adapterItemMultiSelectListener
    ) {

        super(viewBinding, context, parentLayoutBottomSheetResId, layoutManager, bottomSheetRecyclerViewBuilder);

        this.isSearchEnable = isSearchEnable;
        this.searchHint = searchHint;
        this.items = items;
        this.adapterItemMultiSelectListener = adapterItemMultiSelectListener;

        if (isSearchEnable) {
            searchView.setVisibility(View.VISIBLE);
            initSearchView(context, searchView, searchHint, closeBtn, searchIcon, searchEditText);
            closeButtonSearchListener();

        } else
            searchView.setVisibility(View.GONE);

        btnApply.setVisibility(View.VISIBLE);

        initListAdapter();
        initApplyButtonView();
        onClickApplyButton();
    }

    @Override
    public void initListAdapter() {
        asyncSearchListAdapter = new AsyncSearchListAdapter<T>(true);

        asyncSearchListAdapter.submitList(items);
        recyclerView.setAdapter(asyncSearchListAdapter);
        recyclerView.setItemAnimator(null);

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

    @Override
    public void initApplyButtonView() {
        buttonLayoutParams = (ConstraintLayout.LayoutParams) btnApply.getLayoutParams();

        //Calculate button margin from top
        buttonHeight = btnApply.getHeight(); //How tall is the button + experimental distance from bottom (Change based on your view)
        collapsedMargin = (int) (peekHeight - buttonHeight * 1.2); //Button margin in bottom sheet collapsed state
        buttonLayoutParams.topMargin = collapsedMargin;
        btnApply.setLayoutParams(buttonLayoutParams);
        recyclerView.setPadding(0,0,0, (int) (buttonHeight *2.2));

        Log.d("BottomSheetDynamic", "collapsedMargin: " + collapsedMargin + " expandedHeight: " + expandedHeight + " buttonHeight: "+ buttonHeight);

        initBottomSheetCallback(bottomSheetBehavior, btnApply, buttonLayoutParams, collapsedMargin, expandedHeight, buttonHeight);
    }

    @Override
    public void onClickApplyButton() {
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItemMultiSelectListener.onItemMultiSelect(asyncSearchListAdapter.getSelectedItems(), AdapterAction.SELECT);
                bottomSheetBehavior.setPeekHeight(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
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

        asyncSearchListAdapter = new AsyncSearchListAdapter<T>(true);

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