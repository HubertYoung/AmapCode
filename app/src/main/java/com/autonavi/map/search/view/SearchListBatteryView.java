package com.autonavi.map.search.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.utils.SearchUtils;
import java.util.List;

public class SearchListBatteryView extends LinearLayout {
    private LinearLayout m01Layout;
    private LinearLayout m02Layout;
    private TextView mFastText;
    private TextView mFastValueText;
    private TextView mSlowText;
    private TextView mSlowValueText;

    public SearchListBatteryView(Context context) {
        super(context);
        init();
    }

    public SearchListBatteryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SearchListBatteryView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setOrientation(0);
        LayoutInflater.from(getContext()).inflate(R.layout.view_poi_battery, this, true);
        this.m01Layout = (LinearLayout) findViewById(R.id.layout_01);
        this.m02Layout = (LinearLayout) findViewById(R.id.layout_02);
        this.mFastText = (TextView) findViewById(R.id.txt_name_01);
        this.mFastValueText = (TextView) findViewById(R.id.txt_value_01);
        this.mSlowText = (TextView) findViewById(R.id.txt_name_02);
        this.mSlowValueText = (TextView) findViewById(R.id.txt_value_02);
    }

    public void setPoiCharge(List<auh> list) {
        List<auh> list2 = list;
        this.m01Layout.setVisibility(8);
        this.m02Layout.setVisibility(8);
        if (list2 != null && list.size() != 0) {
            if (list.size() > 0) {
                auh auh = list2.get(0);
                this.mFastText.setText(auh.a);
                float a = (float) agn.a(getContext(), 3.0f);
                int a2 = agn.a(getContext(), 17.0f);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(0);
                gradientDrawable.setColor(SearchUtils.safeParseColor(auh.c, (String) "#fb8d35"));
                gradientDrawable.setCornerRadii(new float[]{a, a, 0.0f, 0.0f, 0.0f, 0.0f, a, a});
                gradientDrawable.setSize(a2, a2);
                this.mFastText.setBackgroundDrawable(gradientDrawable);
                this.mFastValueText.setText(Html.fromHtml(auh.b));
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setShape(0);
                gradientDrawable2.setColor(SearchUtils.safeParseColor((String) "#ffffff", (String) "#ffffff"));
                gradientDrawable2.setCornerRadii(new float[]{0.0f, 0.0f, a, a, a, a, 0.0f, 0.0f});
                gradientDrawable2.setSize(agn.a(getContext(), 36.0f), a2);
                gradientDrawable2.setStroke(1, SearchUtils.safeParseColor(auh.c, (String) "#fb8d35"));
                this.mFastValueText.setBackgroundDrawable(gradientDrawable2);
                this.m01Layout.setVisibility(0);
            }
            if (list.size() >= 2) {
                auh auh2 = list2.get(1);
                this.mSlowText.setText(auh2.a);
                float a3 = (float) agn.a(getContext(), 3.0f);
                int a4 = agn.a(getContext(), 17.0f);
                GradientDrawable gradientDrawable3 = new GradientDrawable();
                gradientDrawable3.setShape(0);
                gradientDrawable3.setColor(SearchUtils.safeParseColor(auh2.c, (String) "#3cb896"));
                gradientDrawable3.setCornerRadii(new float[]{a3, a3, 0.0f, 0.0f, 0.0f, 0.0f, a3, a3});
                gradientDrawable3.setSize(a4, a4);
                this.mSlowText.setBackgroundDrawable(gradientDrawable3);
                this.mSlowValueText.setText(Html.fromHtml(auh2.b));
                GradientDrawable gradientDrawable4 = new GradientDrawable();
                gradientDrawable4.setShape(0);
                gradientDrawable4.setColor(SearchUtils.safeParseColor((String) "#ffffff", (String) "#ffffff"));
                gradientDrawable4.setCornerRadii(new float[]{0.0f, 0.0f, a3, a3, a3, a3, 0.0f, 0.0f});
                gradientDrawable4.setSize(agn.a(getContext(), 36.0f), a4);
                gradientDrawable4.setStroke(1, SearchUtils.safeParseColor(auh2.c, (String) "#3cb896"));
                this.mSlowValueText.setBackgroundDrawable(gradientDrawable4);
                this.m02Layout.setVisibility(0);
            }
            cbq.a((ViewGroup) this);
        }
    }
}
