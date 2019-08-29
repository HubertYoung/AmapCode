package com.autonavi.minimap.basemap.weather.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class WeatherSmogView extends RelativeLayout {
    private TextView mSmogInfo;
    private TextView mSmogNum;

    public WeatherSmogView(Context context) {
        this(context, null);
    }

    public WeatherSmogView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WeatherSmogView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.weather_smog_view, this);
        this.mSmogInfo = (TextView) findViewById(R.id.smog_info);
        this.mSmogNum = (TextView) findViewById(R.id.smog_num);
    }

    public void setAqiValue(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mSmogInfo.setText(R.string.weather_smog_title_no_num);
            this.mSmogNum.setVisibility(8);
            return;
        }
        this.mSmogInfo.setText(R.string.weather_smog_title);
        this.mSmogNum.setText(str);
    }
}
