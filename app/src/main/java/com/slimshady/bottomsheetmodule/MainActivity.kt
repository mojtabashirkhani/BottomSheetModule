package com.slimshady.bottomsheetmodule

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemListener
import com.slimshady.bottomsheetmodule.adapter.listener.AdapterItemMultiSelectListener
import com.slimshady.bottomsheetmodule.databinding.ActivityMainBinding
import com.slimshady.bottomsheetmodule.databinding.BottomSheetRecyclerSearchBinding
import com.slimshady.bottomsheetmodule.list.BaseBottomSheetRecyclerView
import com.slimshady.bottomsheetmodule.list.BottomSheetDynamicListMultiSelect
import com.slimshady.bottomsheetmodule.list.BottomSheetDynamicListSingleSelect
import com.slimshady.bottomsheetmodule.list.BottomSheetStaticListSingleSelect
import com.slimshady.bottomsheetmodule.model.BaseBottomSheetRecyclerModel
import com.slimshady.bottomsheetmodule.util.AdapterAction

class MainActivity : AppCompatActivity(), AdapterItemListener<BaseBottomSheetRecyclerModel>,
    AdapterItemMultiSelectListener<BaseBottomSheetRecyclerModel> {
    private lateinit var viewBinding : ActivityMainBinding
    private lateinit var bottomSheetRecyclerSearchBinding: BottomSheetRecyclerSearchBinding
    private val bottomSheetRecyclerViewBuilder: BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder<BottomSheetRecyclerSearchBinding>? by lazy {
        BaseBottomSheetRecyclerView.BaseBottomSheetRecyclerViewBuilder()
    }

    private val divider: DividerItemDecoration? by lazy {
        DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = viewBinding.root
        bottomSheetRecyclerSearchBinding = BottomSheetRecyclerSearchBinding.bind(view)
        setContentView(view)

        val dummyData: ArrayList<BaseBottomSheetRecyclerModel> = ArrayList()
        dummyData.add(BaseBottomSheetRecyclerModel("Tehran", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Tabriz", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Shiraz", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Mashhad", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Sari", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Rasht", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Sanandaj", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Kermanshah", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Ghom", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Arak", "city", false))
        dummyData.add(BaseBottomSheetRecyclerModel("Semnan", "city", false))

        viewBinding.edtStaticSingleSelect.setOnClickListener { v-> initStaticSingleSelect(dummyData) }
        viewBinding.edtStaticSingleSelect.setOnFocusChangeListener { v, hasFocus -> initStaticSingleSelect(dummyData) }

        viewBinding.edtStaticMultiSelect.setOnClickListener { v-> initStaticMultiSelect(dummyData) }
        viewBinding.edtStaticMultiSelect.setOnFocusChangeListener { v, hasFocus -> initStaticMultiSelect(dummyData) }

        viewBinding.edtDynamicSingleSelect.setOnClickListener { v-> initDynamicSingleSelect(dummyData) }
        viewBinding.edtDynamicSingleSelect.setOnFocusChangeListener { v, hasFocus -> initDynamicSingleSelect(dummyData) }

        viewBinding.edtDynamicMultiSelect.setOnClickListener { v-> initDynamicMultiSelect(dummyData) }
        viewBinding.edtDynamicMultiSelect.setOnFocusChangeListener { v, hasFocus -> initDynamicMultiSelect(dummyData) }


        ContextCompat.getDrawable(
            this,
            R.drawable.layer_line_divider
        )?.let {
            divider?.setDrawable(
                it
            )
        }

    }

    private fun initDynamicSingleSelect(baseBottomSheetRecyclerModels: ArrayList<BaseBottomSheetRecyclerModel>) {



        BottomSheetDynamicListSingleSelect(
        bottomSheetRecyclerSearchBinding,
        this,
        R.id.cardView_recyclerView_bottomSheet,
        LinearLayoutManager(this),
        bottomSheetRecyclerViewBuilder?.setDividerItemDecoration(divider),
        true,
        viewBinding.edtDynamicSingleSelect.hint.toString(),
            baseBottomSheetRecyclerModels,
        this)

    }

    private fun initStaticMultiSelect(baseBottomSheetRecyclerModels: ArrayList<BaseBottomSheetRecyclerModel>) {
        BottomSheetDynamicListMultiSelect(
            bottomSheetRecyclerSearchBinding,
            this,
            R.id.cardView_recyclerView_bottomSheet,
            LinearLayoutManager(this),
            bottomSheetRecyclerViewBuilder?.setDividerItemDecoration(divider),
            true,
            viewBinding.edtDynamicSingleSelect.hint.toString(),
            baseBottomSheetRecyclerModels,
            this)
    }

    private fun initStaticSingleSelect(baseBottomSheetRecyclerModels: ArrayList<BaseBottomSheetRecyclerModel>) {
        BottomSheetStaticListSingleSelect(
            bottomSheetRecyclerSearchBinding,
            this,
            R.id.cardView_recyclerView_bottomSheet,
            LinearLayoutManager(this),
            bottomSheetRecyclerViewBuilder
                ?.setDividerItemDecoration(divider),
            baseBottomSheetRecyclerModels,
            this);
    }

    private fun initDynamicMultiSelect(baseBottomSheetRecyclerModels: ArrayList<BaseBottomSheetRecyclerModel>) {
        BottomSheetDynamicListMultiSelect(
            bottomSheetRecyclerSearchBinding,
            this,
            R.id.cardView_recyclerView_bottomSheet,
            LinearLayoutManager(this),
            bottomSheetRecyclerViewBuilder?.setDividerItemDecoration(divider),
            true,
            viewBinding.edtDynamicSingleSelect.hint.toString(),
            baseBottomSheetRecyclerModels,
            this)
    }

    override fun onItemSelect(
        model: BaseBottomSheetRecyclerModel,
        position: Int,
        Action: AdapterAction?
    ) {
        TODO("Not yet implemented")
    }


    override fun onItemMultiSelect(
        model: java.util.ArrayList<BaseBottomSheetRecyclerModel>?,
        action: AdapterAction?
    ) {
        TODO("Not yet implemented")
    }
}