package com.alipay.mobile.beehive.cityselect.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityTabModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;

public class CitySingleItemView extends AULinearLayout {
    private final CityTabModel mCityTabModel;
    /* access modifiers changed from: private */
    public final OnItemClickListener onItemClickListener;

    public CitySingleItemView(Context context, CityTabModel cityTabModel, OnItemClickListener onItemClickListener2) {
        super(context);
        this.mCityTabModel = cityTabModel;
        this.onItemClickListener = onItemClickListener2;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_city_singleitem_tab, this, true);
        setOrientation(1);
        ((AUTextView) findViewById(R.id.city_grid_title)).setText(this.mCityTabModel.name);
        if (this.mCityTabModel.cityVOs != null && !this.mCityTabModel.cityVOs.isEmpty()) {
            CityVO cityVO = this.mCityTabModel.cityVOs.get(0);
            ((AUTextView) findViewById(R.id.city_item)).setText(cityVO.city);
            ((AUTextView) findViewById(R.id.city_item_desc)).setText(cityVO.enName);
            final View itemLayout = findViewById(R.id.ll_item);
            itemLayout.setTag(R.layout.activity_area_select, cityVO);
            itemLayout.setOnClickListener(new OnClickListener() {
                public final void onClick(View v) {
                    if (CitySingleItemView.this.onItemClickListener != null) {
                        CitySingleItemView.this.onItemClickListener.onItemClick(null, itemLayout, 0, 0);
                    }
                }
            });
        }
    }
}
