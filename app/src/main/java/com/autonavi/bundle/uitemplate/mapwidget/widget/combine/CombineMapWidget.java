package com.autonavi.bundle.uitemplate.mapwidget.widget.combine;

import android.animation.LayoutTransition;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class CombineMapWidget extends AbstractMapWidget<CombineWidgetPresenter> {
    private IMapWidget<? extends IMapWidgetPresenter>[] mCombineWidgets;

    public CombineMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(500);
        layoutTransition.setDuration(3, 0);
        linearLayout.setLayoutTransition(layoutTransition);
        return linearLayout;
    }

    public void onInit(Context context) {
        if (getPresenter() != null) {
            ((CombineWidgetPresenter) getPresenter()).addListenerType(-1);
        }
    }

    public void combineWidgets(IMapWidget<? extends IMapWidgetPresenter>... iMapWidgetArr) {
        if (this.mContentView != null && iMapWidgetArr != null) {
            this.mCombineWidgets = iMapWidgetArr;
            if (this.mContentView != null) {
                ((ViewGroup) this.mContentView).removeAllViews();
            }
            if (!(getContext() == null || iMapWidgetArr == null || iMapWidgetArr.length <= 0)) {
                for (IMapWidget<? extends IMapWidgetPresenter> iMapWidget : iMapWidgetArr) {
                    View contentView = iMapWidget.getContentView();
                    if (contentView != null && (this.mContentView instanceof LinearLayout)) {
                        MarginLayoutParams layoutParams = iMapWidget.getWidgetProperty().getLayoutParams();
                        if (layoutParams != null) {
                            contentView.setPadding(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
                        }
                        ((LinearLayout) this.mContentView).addView(contentView);
                    }
                }
            }
        }
    }

    public IMapWidget<? extends IMapWidgetPresenter>[] getCombineWidgets() {
        return this.mCombineWidgets;
    }

    public void release() {
        this.mCombineWidgets = null;
    }
}
