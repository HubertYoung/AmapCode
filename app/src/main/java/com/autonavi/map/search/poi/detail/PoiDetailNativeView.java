package com.autonavi.map.search.poi.detail;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class PoiDetailNativeView extends FrameLayout {
    private View mHeaderView;
    private TextView mPoiInfoTV;

    public PoiDetailNativeView(Context context) {
        this(context, null);
    }

    public PoiDetailNativeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PoiDetailNativeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_poi_detail_native_view, this);
        this.mHeaderView = findViewById(R.id.transparent_rect_view);
        this.mPoiInfoTV = (TextView) findViewById(R.id.blue_poi_info_tv);
        this.mPoiInfoTV.getPaint().setFakeBoldText(true);
    }

    public void setPoiName(String str) {
        TextView textView = this.mPoiInfoTV;
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        textView.setText(str);
    }

    public void setHeaderH(int i) {
        this.mHeaderView.getLayoutParams().height = i;
    }
}
