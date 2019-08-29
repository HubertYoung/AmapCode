package com.alipay.mobile.beehive.cityselect.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityTabModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.ui.LinkGridAdapter;
import com.alipay.mobile.beehive.util.JumpUtil;
import java.util.ArrayList;
import java.util.List;

public class CityLinkTabView extends AULinearLayout {
    /* access modifiers changed from: private */
    public final CityTabModel mCityTabModel;
    private final int mGridNum;

    public CityLinkTabView(Context context, CityTabModel cityTabModel, int gridNum) {
        super(context);
        this.mCityTabModel = cityTabModel;
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
        List names = new ArrayList();
        for (CityVO cityVO : this.mCityTabModel.cityVOs) {
            names.add(cityVO.city);
        }
        LinkGridAdapter cityGridAdapter = new LinkGridAdapter(getContext(), names);
        cityGridAdapter.setBgResID(com.alipay.mobile.antui.R.drawable.au_button_bg_for_sub);
        gridView.setAdapter(cityGridAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JumpUtil.processSchema(CityLinkTabView.this.mCityTabModel.cityVOs.get(position).url);
            }
        });
    }
}
