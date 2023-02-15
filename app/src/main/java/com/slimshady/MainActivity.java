package com.slimshady;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.slimshady.bottomsheetmodule.BaseBottomSheet;
import com.slimshady.bottomsheetmodule.R;
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemListener;
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemMultiSelectListener;
import com.slimshady.bottomsheetmodule.databinding.ActivityMainBinding;
import com.slimshady.bottomsheetmodule.databinding.BottomSheetRecyclerSearchBinding;
import com.slimshady.bottomsheetmodule.list.BaseBottomSheetRecyclerView;
import com.slimshady.bottomsheetmodule.list.BottomSheetDynamicListMultiSelect;
import com.slimshady.bottomsheetmodule.list.BottomSheetDynamicListSingleSelect;
import com.slimshady.bottomsheetmodule.list.BottomSheetStaticListMultiSelect;
import com.slimshady.bottomsheetmodule.list.BottomSheetStaticListSingleSelect;
import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel;
import com.slimshady.bottomsheetmodule.util.AdapterAction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterItemListener, AdapterItemMultiSelectListener {

    private ActivityMainBinding viewBinding;

    private BaseBottomSheet baseBottomSheetRecyclerView;
    private BottomSheetStaticListSingleSelect bottomSheetStaticListSingleSelect;
    private BottomSheetDynamicListSingleSelect bottomSheetDynamicListSingleSelect;
    private BottomSheetStaticListMultiSelect bottomSheetStaticListMultiSelect;
    private BottomSheetDynamicListMultiSelect bottomSheetDynamicListMultiSelect;
    private BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<BottomSheetRecyclerSearchBinding> bottomSheetRecyclerViewBuilder;

    private BottomSheetRecyclerSearchBinding bottomSheetRecyclerSearchBinding;

    private DividerItemDecoration divider;

    private ArrayList<BaseBottomSheetRecyclerModel> dummyData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = viewBinding.getRoot();
        bottomSheetRecyclerSearchBinding = BottomSheetRecyclerSearchBinding.bind(view);
        setContentView(view);

        baseBottomSheetRecyclerView = new BaseBottomSheet(bottomSheetRecyclerSearchBinding, this, R.id.cardView_recyclerView_bottomSheet);
        bottomSheetRecyclerViewBuilder = new BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<>();

        divider =
                new DividerItemDecoration(this,
                        DividerItemDecoration.VERTICAL);


        dummyData = new ArrayList<>();

//        val dummyData: ArrayList<BaseBottomSheetRecyclerModel> = ArrayList()
        dummyData.add(new BaseBottomSheetRecyclerModel("Tehran", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Tabriz", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Shiraz", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Mashhad", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Sari", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Rasht", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Sanandaj", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Kermanshah", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Ghom", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Arak", "city", false));
        dummyData.add(new BaseBottomSheetRecyclerModel("Semnan", "city", false));

        viewBinding.edtStaticSingleSelect.setOnClickListener(v -> {
            initStaticSingleSelect(dummyData);

        });
        viewBinding.edtStaticSingleSelect.setOnFocusChangeListener((v, hasFocus) -> {
            initStaticSingleSelect(dummyData);

        });

        viewBinding.edtStaticMultiSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initStaticMultiSelect(dummyData);

            }
        });
        viewBinding.edtStaticMultiSelect.setOnFocusChangeListener((v, hasFocus) -> {
            initStaticMultiSelect(dummyData);

        });

        viewBinding.edtDynamicSingleSelect.setOnClickListener(v -> {
            initDynamicSingleSelect(dummyData);

        });
        viewBinding.edtDynamicSingleSelect.setOnFocusChangeListener((v, hasFocus) -> {
            initDynamicSingleSelect(dummyData);

        });

        viewBinding.edtDynamicMultiSelect.setOnClickListener(v -> {
            initDynamicMultiSelect(dummyData);

        });

        viewBinding.edtDynamicMultiSelect.setOnFocusChangeListener((v, hasFocus) -> {
            initDynamicMultiSelect(dummyData);

        });

    }

    private void initDynamicMultiSelect(ArrayList<BaseBottomSheetRecyclerModel> dummyData) {
        bottomSheetDynamicListMultiSelect = new BottomSheetDynamicListMultiSelect(
                bottomSheetRecyclerSearchBinding,
                this,
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(this),
                bottomSheetRecyclerViewBuilder.setDividerItemDecoration(divider),
                true,
                viewBinding.edtDynamicSingleSelect.getHint().toString(),
                dummyData,
                this);
    }

    private void initDynamicSingleSelect(ArrayList<BaseBottomSheetRecyclerModel> dummyData) {
        bottomSheetDynamicListSingleSelect = new BottomSheetDynamicListSingleSelect(
                bottomSheetRecyclerSearchBinding,
                this,
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(this),
                bottomSheetRecyclerViewBuilder.setDividerItemDecoration(divider),
                true,
                viewBinding.edtDynamicSingleSelect.getHint().toString(),
                dummyData,
                this);
    }

    private void initStaticMultiSelect(ArrayList<BaseBottomSheetRecyclerModel> dummyData) {
        bottomSheetStaticListMultiSelect = new BottomSheetStaticListMultiSelect(
                bottomSheetRecyclerSearchBinding,
                this,
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(this),
                bottomSheetRecyclerViewBuilder
                        .setDividerItemDecoration(divider),
                dummyData,
                this);
    }

    private void initStaticSingleSelect(ArrayList<BaseBottomSheetRecyclerModel> dummyData) {
        bottomSheetStaticListSingleSelect = new BottomSheetStaticListSingleSelect(
                bottomSheetRecyclerSearchBinding,
                this,
                R.id.cardView_recyclerView_bottomSheet,
                new LinearLayoutManager(this),
                bottomSheetRecyclerViewBuilder
                        .setDividerItemDecoration(divider),
                dummyData,
                this);
    }

    @Override
    public void onItemSelect(Object model, int position, AdapterAction Action) {

    }

    @Override
    public void onItemMultiSelect(ArrayList model, AdapterAction action) {

    }
}