package com.autonavi.bundle.uitemplate.mapwidget.widget.scale;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;

public class ScaleMapWidget extends AbstractMapWidget<ScaleWidgetPresenter> {
    public ScaleMapWidget(Context context, IWidgetProperty iWidgetProperty) {
        super(context, iWidgetProperty);
    }

    public View createContentView(Context context) {
        ScaleView scaleView = new ScaleView(context);
        LayoutParams layoutParams = new LayoutParams(bet.a(context, 60), bet.a(context, 24));
        layoutParams.gravity = 83;
        scaleView.setLayoutParams(layoutParams);
        if (this.mWidgetProperty != null && !this.mWidgetProperty.isLoadNewWidgetStyle()) {
            scaleView.setPadding(0, 0, 0, bet.a(context, 5));
        }
        scaleView.getScaleLineView().mAlignRight = false;
        return scaleView;
    }

    public void onInit(Context context) {
        setScaleLogo(R.drawable.map_widget_autonavi_logo);
        setScaleTextSize(bet.a(context, 12));
        setScaleLogoSize(bet.a(context, 60), bet.a(context, 20));
    }

    public ScaleView getScaleView() {
        if (this.mContentView != null) {
            return (ScaleView) this.mContentView;
        }
        return null;
    }

    public void setScaleTextSize(int i) {
        ScaleView scaleView = getScaleView();
        if (scaleView != null) {
            scaleView.setTextSize(i);
        }
    }

    public void setScaleLogoSize(int i, int i2) {
        ScaleView scaleView = getScaleView();
        if (scaleView != null) {
            scaleView.setLogoSize(i, i2);
        }
    }

    public void setScaleLogo(int i) {
        ScaleView scaleView = getScaleView();
        if (scaleView != null) {
            scaleView.setLogo(i);
        }
    }
}
