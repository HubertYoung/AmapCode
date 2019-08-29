package com.amap.bundle.commonui.barchart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;
import org.json.JSONArray;
import org.json.JSONObject;

public class BarChartView extends LinearLayout {
    private static final String TAG = "BarChartView";
    private Context mContext;
    private JSONArray mJsonArray;

    public BarChartView(Context context) {
        super(context);
        this.mContext = context;
    }

    public BarChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public void setData(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0) {
            throw new RuntimeException("BarChartView:setData param is invalid");
        }
        this.mJsonArray = jSONArray;
        initView();
    }

    private void initView() {
        LayoutInflater.from(this.mContext).inflate(R.layout.layout_bar_chart_view, this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llayout_chart_view_root);
        linearLayout.removeAllViews();
        int length = this.mJsonArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject optJSONObject = this.mJsonArray.optJSONObject(i);
            if (optJSONObject != null) {
                View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_bar_chart_item, null);
                TextView textView = (TextView) inflate.findViewById(R.id.tv_num);
                textView.setText(optJSONObject.optString("num"));
                try {
                    textView.setTextColor(Color.parseColor(parseColor(optJSONObject.optString("numColor"))));
                } catch (Exception e) {
                    textView.setTextColor(getResources().getColor(R.color.f_c_2));
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(Log.getStackTraceString(e));
                        AMapLog.e(TAG, sb.toString());
                    }
                }
                TextView textView2 = (TextView) inflate.findViewById(R.id.tv_info);
                textView2.setText(optJSONObject.optString("text"));
                try {
                    textView2.setTextColor(Color.parseColor(parseColor(optJSONObject.optString("textColor"))));
                } catch (Exception e2) {
                    textView2.setTextColor(getResources().getColor(R.color.f_c_2));
                    if (bno.a) {
                        AMapLog.e(TAG, Log.getStackTraceString(e2));
                    }
                }
                GradientDrawable gradientDrawable = (GradientDrawable) inflate.findViewById(R.id.view_bar_chart).getBackground();
                try {
                    gradientDrawable.setColor(Color.parseColor(parseColor(optJSONObject.optString("color"))));
                } catch (Exception e3) {
                    gradientDrawable.setColor(getResources().getColor(R.color.c_12));
                    if (bno.a) {
                        AMapLog.e(TAG, Log.getStackTraceString(e3));
                    }
                }
                LayoutParams layoutParams = new LayoutParams(-1, -1);
                layoutParams.weight = 1.0f;
                inflate.setLayoutParams(layoutParams);
                linearLayout.addView(inflate);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateBarChartHeight();
    }

    private void updateBarChartHeight() {
        if (this.mJsonArray != null && this.mJsonArray.length() != 0) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llayout_chart_view_root);
            int childCount = linearLayout.getChildCount();
            if (childCount > 0) {
                View childAt = linearLayout.getChildAt(0);
                TextView textView = (TextView) childAt.findViewById(R.id.tv_num);
                TextView textView2 = (TextView) childAt.findViewById(R.id.tv_info);
                textView.measure(0, 0);
                textView2.measure(0, 0);
                int measuredHeight = (getMeasuredHeight() - textView.getMeasuredHeight()) - (textView2.getMeasuredHeight() + textView2.getPaddingTop());
                for (int i = 0; i < childCount; i++) {
                    View findViewById = linearLayout.getChildAt(i).findViewById(R.id.view_bar_chart);
                    float optDouble = (float) this.mJsonArray.optJSONObject(i).optDouble("height", 0.0d);
                    if (optDouble > 1.0f) {
                        optDouble = 1.0f;
                    }
                    if (optDouble < 0.0f) {
                        optDouble = 0.0f;
                    }
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
                    layoutParams.width = getResources().getDimensionPixelOffset(R.dimen.bar_chart_inner_width);
                    float f = ((float) measuredHeight) * optDouble;
                    int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.bar_chart_top_corner);
                    if (f < ((float) dimensionPixelOffset)) {
                        layoutParams.height = dimensionPixelOffset;
                    } else {
                        layoutParams.height = (int) f;
                    }
                    findViewById.setLayoutParams(layoutParams);
                }
            }
        }
    }

    private String parseColor(String str) {
        if (str.charAt(0) != '#') {
            return "";
        }
        if (str.length() == 7) {
            return str;
        }
        if (str.length() != 9) {
            return "";
        }
        StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
        sb.append(str.substring(7));
        sb.append(str.substring(1, 7));
        return sb.toString();
    }
}
