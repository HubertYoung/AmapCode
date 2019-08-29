package com.alipay.mobile.beehive.cityselect.view;

import android.content.Context;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityTabModel;

public class CityListNameView extends AULinearLayout {
    private final CityTabModel mCityTabModel;

    public CityListNameView(Context context, CityTabModel cityTabModel) {
        super(context);
        this.mCityTabModel = cityTabModel;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_city_static_tab, this, true);
        setOrientation(1);
        setClickable(false);
        ((AUTextView) findViewById(R.id.city_grid_title)).setText(this.mCityTabModel.name);
        ((ExpandableGridView) findViewById(R.id.city_grid)).setVisibility(8);
    }
}
