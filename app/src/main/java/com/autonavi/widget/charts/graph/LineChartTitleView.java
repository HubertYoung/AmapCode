package com.autonavi.widget.charts.graph;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.widget.R;
import com.autonavi.widget.charts.graph.LineChart.c;
import java.util.ArrayList;
import java.util.List;

public class LineChartTitleView extends LinearLayout {
    private int circleRadius;
    private List<c> valueEntities;

    public void setData(List<c> list) {
        this.valueEntities.clear();
        this.valueEntities.addAll(list);
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
        this.valueEntities = new ArrayList();
        init(context);
    }

    private void init(Context context) {
        int a = eqp.a(context, 16);
        this.circleRadius = eqp.a(getContext(), 6);
        setPadding(a, 0, a, 0);
        setOrientation(0);
        setDividerPadding(0);
        setDividerDrawable(getResources().getDrawable(R.drawable.grapha_title_divider));
        setShowDividers(2);
    }

    private void addAllTitle() {
        for (int i = 0; i < this.valueEntities.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(0);
            linearLayout.setDividerPadding(0);
            linearLayout.setDividerDrawable(getResources().getDrawable(R.drawable.grapha_title_text_img_divider));
            linearLayout.setShowDividers(2);
            linearLayout.setGravity(16);
            linearLayout.addView(new CircleImageView(getContext(), this.valueEntities.get(i).a, (float) this.circleRadius));
            TextView textView = new TextView(getContext());
            textView.setTextColor(getResources().getColor(R.color.chart_f_c_3n));
            textView.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.chart_f_s_14));
            textView.setText(this.valueEntities.get(i).b);
            linearLayout.addView(textView);
            addView(linearLayout, -2, -2);
        }
    }
}
