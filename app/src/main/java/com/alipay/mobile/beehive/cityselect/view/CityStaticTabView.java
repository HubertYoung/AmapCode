package com.alipay.mobile.beehive.cityselect.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityTabModel;
import com.alipay.mobile.beehive.cityselect.ui.CityGridAdapter;

public class CityStaticTabView extends AULinearLayout {
    private final CityTabModel mCityTabModel;
    private final int mGridNum;
    private OnItemClickListener mOnItemClickListener;

    public CityStaticTabView(Context context, CityTabModel cityTabModel, OnItemClickListener onItemClickListener, int gridNum) {
        super(context);
        this.mCityTabModel = cityTabModel;
        this.mOnItemClickListener = onItemClickListener;
        this.mGridNum = gridNum;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_city_static_tab, this, true);
        setOrientation(1);
        ((AUTextView) findViewById(R.id.city_grid_title)).setText(this.mCityTabModel.name);
        ExpandableGridView gridView = (ExpandableGridView) findViewById(R.id.city_grid);
        gridView.setNumColumns(this.mGridNum);
        gridView.setExpanded(true);
        CityGridAdapter cityGridAdapter = new CityGridAdapter(getContext(), this.mCityTabModel.cityVOs);
        cityGridAdapter.setBgResID(com.alipay.mobile.antui.R.drawable.au_button_bg_for_sub);
        gridView.setAdapter(cityGridAdapter);
        gridView.setOnItemClickListener(this.mOnItemClickListener);
    }
}
