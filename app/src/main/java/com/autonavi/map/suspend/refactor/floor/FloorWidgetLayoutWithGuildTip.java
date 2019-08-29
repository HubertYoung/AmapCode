package com.autonavi.map.suspend.refactor.floor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.map.suspend.refactor.floor.FloorWidgetView.a;
import com.autonavi.minimap.R;

public class FloorWidgetLayoutWithGuildTip extends LinearLayout implements a {
    private FloorWidgetLayoutWithLocationTip floorWidgetViewLayout;
    private boolean mTipInRight = false;
    private View mapIndoorGuideTip;

    public boolean invisibleWhenSmallerThanDesiredHeight() {
        return true;
    }

    public void setTipInRight(boolean z) {
        if (this.mTipInRight != z) {
            this.mTipInRight = z;
            onTipInRightChanged();
        }
    }

    private void onTipInRightChanged() {
        LayoutParams layoutParams = (LayoutParams) this.mapIndoorGuideTip.getLayoutParams();
        layoutParams.addRule(1, 0);
        layoutParams.addRule(0, 0);
        LayoutParams layoutParams2 = (LayoutParams) this.floorWidgetViewLayout.getLayoutParams();
        layoutParams2.addRule(9, 0);
        layoutParams2.addRule(11, 0);
        if (this.mTipInRight) {
            layoutParams.addRule(1, R.id.floor_widget_view_layout);
            this.mapIndoorGuideTip.setBackgroundResource(R.drawable.layer_tip_kuang_r);
            layoutParams2.addRule(9);
        } else {
            layoutParams.addRule(0, R.id.floor_widget_view_layout);
            this.mapIndoorGuideTip.setBackgroundResource(R.drawable.layer_tip_kuang);
            layoutParams2.addRule(11);
        }
        this.mapIndoorGuideTip.setLayoutParams(layoutParams);
        this.floorWidgetViewLayout.setLayoutParams(layoutParams2);
        this.mapIndoorGuideTip.setPadding((int) getResources().getDimension(R.dimen.floor_widget_tip_padding_left), 0, (int) getResources().getDimension(R.dimen.floor_widget_tip_padding_right), 0);
        this.floorWidgetViewLayout.setTipInRight(this.mTipInRight);
    }

    public FloorWidgetLayoutWithGuildTip(Context context) {
        super(context);
        init(context);
    }

    public FloorWidgetLayoutWithGuildTip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.floor_widget_with_guild_tip, this);
        initView();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.floorWidgetViewLayout = (FloorWidgetLayoutWithLocationTip) findViewById(R.id.floor_widget_view_layout);
        this.mapIndoorGuideTip = findViewById(R.id.map_indoor_guide);
    }

    public FloorWidgetLayoutWithLocationTip getFloorWidgetViewLayout() {
        return this.floorWidgetViewLayout;
    }

    public View getGuideTipView() {
        return this.mapIndoorGuideTip;
    }

    public void setDesireVisibilty(int i) {
        setVisibility(i);
    }
}
