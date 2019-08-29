package com.autonavi.map.search.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class SearchListETAView extends FrameLayout {
    private TextView mBusTimeView;
    private TextView mDriveTimeView;
    private boolean mUpdateETA;
    private float offset;

    public SearchListETAView(Context context) {
        super(context);
        init();
    }

    public SearchListETAView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SearchListETAView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_poi_eta, this, true);
        this.mDriveTimeView = (TextView) findViewById(R.id.txt_drive_time);
        this.mBusTimeView = (TextView) findViewById(R.id.txt_bus_time);
        this.offset = (float) getContext().getResources().getDimensionPixelSize(R.dimen.default_margin_1A);
    }

    public void setETAData(CharSequence charSequence, CharSequence charSequence2) {
        this.mDriveTimeView.setText(charSequence);
        this.mBusTimeView.setText(charSequence2);
        int i = 8;
        boolean z = false;
        this.mDriveTimeView.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        TextView textView = this.mBusTimeView;
        if (!TextUtils.isEmpty(charSequence2)) {
            i = 0;
        }
        textView.setVisibility(i);
        if (!TextUtils.isEmpty(charSequence) || !TextUtils.isEmpty(charSequence2)) {
            z = true;
        }
        this.mUpdateETA = z;
    }

    public boolean isUpdateETA() {
        return this.mUpdateETA;
    }

    public float getMeasureTotalWidth() {
        if (!isUpdateETA()) {
            return 0.0f;
        }
        int i = 0;
        float applyDimension = TypedValue.applyDimension(1, 16.0f, getResources().getDisplayMetrics());
        if (!TextUtils.isEmpty(this.mDriveTimeView.getText())) {
            i = (int) (this.mDriveTimeView.getPaint().measureText(this.mDriveTimeView.getText().toString()) + ((float) getResources().getDimensionPixelSize(R.dimen.default_margin_2A)) + applyDimension + 0.0f);
        }
        if (!TextUtils.isEmpty(this.mBusTimeView.getText())) {
            i = (int) (((float) i) + this.mBusTimeView.getPaint().measureText(this.mBusTimeView.getText().toString()) + ((float) getResources().getDimensionPixelSize(R.dimen.default_margin_2A)) + ((float) getResources().getDimensionPixelSize(R.dimen.default_margin_3A)) + applyDimension);
        }
        return ((float) i) + this.offset;
    }
}
