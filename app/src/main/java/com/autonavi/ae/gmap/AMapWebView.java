package com.autonavi.ae.gmap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

import defpackage.akq;

class AMapWebView extends SurfaceView {
    private String TAG = "AMapWebView";
    private Context mContext;
    private akq mMapController = null;

    public AMapWebView(Context context) {
        super(context);
        this.mContext = context;
    }

    public AMapWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public void setGLMapView(akq akq) {
        this.mMapController = akq;
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (this.mMapController != null) {
            akq.b("addView: , ".concat(String.valueOf(view)));
        }
        ((ViewGroup) getParent()).addView(view, layoutParams);
    }

    public void removeView(View view) {
        if (this.mMapController != null) {
            akq.b("removeView: , ".concat(String.valueOf(view)));
        }
        ((ViewGroup) getParent()).removeView(view);
    }
}
