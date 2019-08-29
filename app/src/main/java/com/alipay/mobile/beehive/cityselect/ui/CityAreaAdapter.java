package com.alipay.mobile.beehive.cityselect.ui;

import android.content.Context;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import java.util.List;

public class CityAreaAdapter extends CityListAdapter {
    public CityAreaAdapter(Context context, List<CityVO> cityList, List<String> sections, List<Integer> positions, int offset) {
        super(context, cityList, sections, positions, offset);
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.list_item_area_list;
    }
}
