package com.autonavi.bundle.uitemplate.mapwidget.widget.zoom;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class ZoomInOutMapWidget extends AbstractMapWidget<ZoomWidgetPresenter> {
    private View zoomInTip;
    private TextView zoomInTipText;
    private View zoomOutTip;
    private TextView zoomOutTipText;

    public ZoomInOutMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, getLayoutId());
        this.zoomInTip = loadLayoutRes.findViewById(R.id.zoomInTip);
        this.zoomInTip.setContentDescription(context.getResources().getString(R.string.zoom_in));
        this.zoomOutTip = loadLayoutRes.findViewById(R.id.zoomOutTip);
        this.zoomOutTip.setContentDescription(context.getResources().getString(R.string.zoom_out));
        this.zoomInTipText = (TextView) loadLayoutRes.findViewById(R.id.tv_zoom_in_tip);
        this.zoomOutTipText = (TextView) loadLayoutRes.findViewById(R.id.tv_zoom_out_tip);
        return loadLayoutRes;
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.map_widget_zoom_layout;
    }

    public ZoomWidgetLayout getZoomInView() {
        if (this.mContentView != null) {
            return (ZoomWidgetLayout) this.mContentView.findViewById(R.id.zoom_in_container);
        }
        return null;
    }

    public ZoomWidgetLayout getZoomOutView() {
        if (this.mContentView != null) {
            return (ZoomWidgetLayout) this.mContentView.findViewById(R.id.zoom_out_container);
        }
        return null;
    }

    public void onInit(Context context) {
        ((ZoomWidgetPresenter) getPresenter()).addListenerType(0);
        if (this.mContentView != null) {
            this.mContentView.setVisibility(8);
        }
    }

    public int getZoomOutTipId() {
        return R.id.zoomOutTip;
    }

    public int getZoomOutViewId() {
        return R.id.zoom_out_container;
    }

    public int getZoomInTipId() {
        return R.id.zoomInTip;
    }

    public int getZoomInViewId() {
        return R.id.zoom_in_container;
    }

    public View getZoomInTip() {
        return this.zoomInTip;
    }

    public View getZoomOutTip() {
        return this.zoomOutTip;
    }

    public TextView getZoomOutTipText() {
        return this.zoomOutTipText;
    }

    public TextView getZoomInTipText() {
        return this.zoomInTipText;
    }
}
