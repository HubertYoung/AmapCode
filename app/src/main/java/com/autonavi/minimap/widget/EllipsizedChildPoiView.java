package com.autonavi.minimap.widget;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class EllipsizedChildPoiView extends LinearLayout {
    private TextView mChildLabelTV;
    private TextView mChildNameTV;
    private boolean mIsJudge;

    public EllipsizedChildPoiView(Context context) {
        super(context);
        init();
    }

    public EllipsizedChildPoiView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public EllipsizedChildPoiView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.elipsized_child_poi_layout, this, true);
        this.mChildNameTV = (TextView) findViewById(R.id.child_name);
        this.mChildLabelTV = (TextView) findViewById(R.id.child_label);
    }

    public void setData(String str, String str2) {
        setData(str, str2, true);
    }

    public void setData(String str, String str2, boolean z) {
        this.mIsJudge = z;
        this.mChildNameTV.setText(str);
        if (TextUtils.isEmpty(str2)) {
            this.mChildLabelTV.setVisibility(8);
            return;
        }
        this.mChildLabelTV.setText(Html.fromHtml(str2));
        this.mChildLabelTV.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mChildLabelTV.getVisibility() == 0 || !this.mIsJudge) {
            super.onMeasure(i, i2);
            int measuredWidth = getMeasuredWidth();
            super.onMeasure(0, 0);
            if (getMeasuredWidth() > measuredWidth) {
                this.mChildLabelTV.setVisibility(8);
            } else {
                this.mChildLabelTV.setVisibility(0);
            }
        }
        super.onMeasure(i, i2);
    }
}
