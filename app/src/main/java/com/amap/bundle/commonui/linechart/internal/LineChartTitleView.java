package com.amap.bundle.commonui.linechart.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.commonui.linechart.LineChart.c;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class LineChartTitleView extends LinearLayout {
    private int mCircleRadius;
    private List<c> mValueEntities;

    public void setData(List<c> list) {
        this.mValueEntities.clear();
        this.mValueEntities.addAll(list);
        removeAllViews();
        addAllTitle();
    }

    public LineChartTitleView(Context context) {
        this(context, null);
    }

    public LineChartTitleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LineChartTitleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mValueEntities = new ArrayList();
        init(context);
    }

    private void init(Context context) {
        int a = mk.a(context, 16);
        this.mCircleRadius = mk.a(getContext(), 6);
        setPadding(a, 0, a, 0);
        setOrientation(0);
        setDividerPadding(0);
        setDividerDrawable(getResources().getDrawable(R.drawable.grapha_title_divider));
        setShowDividers(2);
    }

    private void addAllTitle() {
        for (int i = 0; i < this.mValueEntities.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(0);
            linearLayout.setDividerPadding(0);
            linearLayout.setDividerDrawable(getResources().getDrawable(R.drawable.grapha_title_text_img_divider));
            linearLayout.setShowDividers(2);
            linearLayout.setGravity(16);
            linearLayout.addView(new CircleImageView(getContext(), this.mValueEntities.get(i).a, (float) this.mCircleRadius));
            TextView textView = new TextView(getContext());
            textView.setTextColor(getResources().getColor(R.color.chart_f_c_3n));
            textView.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.chart_f_s_14));
            textView.setText(this.mValueEntities.get(i).b);
            linearLayout.addView(textView);
            addView(linearLayout, -2, -2);
        }
    }
}
