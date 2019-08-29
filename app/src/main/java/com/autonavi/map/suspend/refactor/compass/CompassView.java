package com.autonavi.map.suspend.refactor.compass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class CompassView extends RelativeLayout implements cdi {
    private LinearLayout mCompassLayerTip;
    private UICompassWidget mCompassWidget;

    public RelativeLayout getCompassLayout() {
        return this;
    }

    public View getView() {
        return this;
    }

    public CompassView(Context context) {
        this(context, null);
    }

    public CompassView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CompassView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        View inflate = LayoutInflater.from(context).inflate(R.layout.default_suspend_compass, this, true);
        this.mCompassWidget = (UICompassWidget) inflate.findViewById(R.id.Compass);
        this.mCompassWidget.setCompassRes(R.drawable.suspend_compass);
        this.mCompassLayerTip = (LinearLayout) inflate.findViewById(R.id.compass_layer_tip);
    }

    public UICompassWidget getCompassWidget() {
        return this.mCompassWidget;
    }

    public LinearLayout getCompassLayerTip() {
        return this.mCompassLayerTip;
    }

    public void setCompassLayoutVisibility(int i) {
        setVisibility(i);
    }

    public void setCompassLayerTipVisibility(boolean z) {
        if (z) {
            if (!(this.mCompassLayerTip == null || this.mCompassLayerTip.getVisibility() == 0 || this.mCompassWidget.getVisibility() != 0)) {
                this.mCompassLayerTip.setVisibility(0);
            }
        } else if (!(this.mCompassLayerTip == null || this.mCompassLayerTip.getVisibility() == 8)) {
            this.mCompassLayerTip.setVisibility(8);
        }
    }

    public void setCompassLayoutAlpha(float f) {
        setAlpha(f);
    }

    public void setCompassWidgetIcon(int i) {
        this.mCompassWidget.setCompassRes(i);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mCompassWidget.setOnClickListener(onClickListener);
        this.mCompassLayerTip.setOnClickListener(onClickListener);
    }
}
