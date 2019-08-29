package com.autonavi.map.search.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.List;

public class SearchListGasView extends LinearLayout {
    private LinearLayout mLayoutGas1;
    private LinearLayout mLayoutGas2;
    private TextView mNameView1;
    private TextView mNameView2;
    private TextView mPriceView1;
    private TextView mPriceView2;

    public static class a {
        public CharSequence a;
        public CharSequence b;
    }

    public SearchListGasView(Context context) {
        super(context);
        init();
    }

    public SearchListGasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SearchListGasView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setOrientation(0);
        LayoutInflater.from(getContext()).inflate(R.layout.view_poi_gas, this, true);
        this.mLayoutGas1 = (LinearLayout) findViewById(R.id.layout_gas1);
        this.mLayoutGas2 = (LinearLayout) findViewById(R.id.layout_gas2);
        this.mNameView1 = (TextView) findViewById(R.id.txt_gas_name1);
        this.mPriceView1 = (TextView) findViewById(R.id.txt_gas_price1);
        this.mNameView2 = (TextView) findViewById(R.id.txt_gas_name2);
        this.mPriceView2 = (TextView) findViewById(R.id.txt_gas_price2);
    }

    public void setPoiData(List<a> list) {
        List<a> list2 = list;
        this.mLayoutGas1.setVisibility(8);
        this.mLayoutGas2.setVisibility(8);
        if (list2 != null && list.size() != 0) {
            if (list.size() > 0) {
                a aVar = list2.get(0);
                float a2 = (float) agn.a(getContext(), 3.0f);
                agn.a(getContext(), 16.0f);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(0);
                gradientDrawable.setColor(Color.parseColor("#647086"));
                gradientDrawable.setDither(true);
                gradientDrawable.setCornerRadii(new float[]{a2, a2, 0.0f, 0.0f, 0.0f, 0.0f, a2, a2});
                this.mNameView1.setBackgroundDrawable(gradientDrawable);
                this.mNameView1.setText(aVar.a);
                this.mNameView1.setTextColor(Color.parseColor("#ffffff"));
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setShape(0);
                gradientDrawable2.setDither(true);
                gradientDrawable2.setColor(Color.parseColor("#00000000"));
                gradientDrawable2.setCornerRadii(new float[]{0.0f, 0.0f, a2, a2, a2, a2, 0.0f, 0.0f});
                this.mPriceView1.setBackgroundDrawable(gradientDrawable2);
                gradientDrawable2.setStroke(1, Color.parseColor("#647086"));
                this.mPriceView1.setText(aVar.b);
                this.mPriceView1.setTextColor(Color.parseColor("#647086"));
                this.mLayoutGas1.setVisibility(0);
            }
            if (list.size() >= 2) {
                a aVar2 = list2.get(1);
                float a3 = (float) agn.a(getContext(), 3.0f);
                GradientDrawable gradientDrawable3 = new GradientDrawable();
                gradientDrawable3.setShape(0);
                gradientDrawable3.setDither(true);
                gradientDrawable3.setColor(Color.parseColor("#647086"));
                gradientDrawable3.setCornerRadii(new float[]{a3, a3, 0.0f, 0.0f, 0.0f, 0.0f, a3, a3});
                this.mNameView2.setBackgroundDrawable(gradientDrawable3);
                this.mNameView2.setText(aVar2.a);
                this.mNameView2.setTextColor(Color.parseColor("#ffffff"));
                GradientDrawable gradientDrawable4 = new GradientDrawable();
                gradientDrawable4.setShape(0);
                gradientDrawable4.setColor(Color.parseColor("#00000000"));
                gradientDrawable4.setCornerRadii(new float[]{0.0f, 0.0f, a3, a3, a3, a3, 0.0f, 0.0f});
                gradientDrawable4.setDither(true);
                gradientDrawable4.setStroke(1, Color.parseColor("#647086"));
                this.mPriceView2.setBackgroundDrawable(gradientDrawable4);
                this.mPriceView2.setText(aVar2.b);
                this.mPriceView2.setTextColor(Color.parseColor("#647086"));
                this.mLayoutGas2.setVisibility(0);
            }
        }
    }
}
