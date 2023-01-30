package com.slimshady.bottomsheetmodule.adapter.diffUtil;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel;

import java.util.Objects;

public class SearchListAdapterDiffUtil<T extends BaseBottomSheetRecyclerModel> extends DiffUtil.ItemCallback<T> {
    @Override
    public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName());
    }
}
