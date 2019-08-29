package com.autonavi.ae.gmap;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

import defpackage.akq;
import defpackage.aky;
import defpackage.ald;
import defpackage.amj;
import defpackage.amm;
import defpackage.amn;
import defpackage.ana;

public class AMapSurface extends SurfaceView implements aky, Callback {
    private String TAG = "AMapSurface";
    private ald mSurfaceLogicImpl = null;

    public AMapSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.mSurfaceLogicImpl = new ald(context);
        "AMapSurface Context:  this = ".concat(String.valueOf(this));
        ana.a();
    }

    public AMapSurface(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        this.mSurfaceLogicImpl = new ald(context);
        "AMapSurface AttributeSet:  this = ".concat(String.valueOf(this));
        ana.a();
    }

    public void init( akq akq) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mSurfaceLogicImpl.a(akq);
        long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
        StringBuilder sb = new StringBuilder("init time: ");
        sb.append(elapsedRealtime2);
        sb.append(", ");
        sb.append(akq);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    public void uninit() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mSurfaceLogicImpl.b();
        long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
        StringBuilder sb = new StringBuilder("uninit time: ");
        sb.append(elapsedRealtime2);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    public int getDeviceId() {
        return this.mSurfaceLogicImpl.a();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        uninit();
        super.finalize();
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Surface surface = surfaceHolder.getSurface();
        this.mSurfaceLogicImpl.a(surface, getWidth(), getHeight());
        long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
        StringBuilder sb = new StringBuilder("surfaceCreated time: ");
        sb.append(elapsedRealtime2);
        sb.append(", ");
        sb.append(surface);
        sb.append(", ");
        sb.append(getWidth());
        sb.append(", ");
        sb.append(getHeight());
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Surface surface = surfaceHolder.getSurface();
        this.mSurfaceLogicImpl.b(surface, i2, i3);
        StringBuilder sb = new StringBuilder("surfaceChanged time: ");
        sb.append(SystemClock.elapsedRealtime() - elapsedRealtime);
        sb.append(", ");
        sb.append(surface);
        sb.append(", ");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Surface surface = surfaceHolder.getSurface();
        this.mSurfaceLogicImpl.a(surface);
        long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
        StringBuilder sb = new StringBuilder("surfaceDestroyed time: ");
        sb.append(elapsedRealtime2);
        sb.append(", ");
        sb.append(surface);
        sb.append(", ");
        sb.append(getWidth());
        sb.append(", ");
        sb.append(getHeight());
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        "MapSurfaceView onSizeChanged,  this = ".concat(String.valueOf(this));
        ana.a();
        super.onSizeChanged(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        super.onAttachedToWindow();
        long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
        StringBuilder sb = new StringBuilder("MapSurfaceView onAttachedToWindow time: ");
        sb.append(elapsedRealtime2);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mSurfaceLogicImpl.c();
        long elapsedRealtime2 = SystemClock.elapsedRealtime();
        super.onDetachedFromWindow();
        long j = elapsedRealtime2 - elapsedRealtime;
        long elapsedRealtime3 = SystemClock.elapsedRealtime() - elapsedRealtime2;
        StringBuilder sb = new StringBuilder("MapSurfaceView onDetachedFromWindow time: ");
        sb.append(j);
        sb.append(", ");
        sb.append(elapsedRealtime3);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    public void addView(View view, LayoutParams layoutParams) {
        StringBuilder sb = new StringBuilder("addView: , ");
        sb.append(view);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
        ((ViewGroup) getParent()).addView(view, layoutParams);
    }

    public void removeView(View view) {
        StringBuilder sb = new StringBuilder("removeView: , ");
        sb.append(view);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
        ((ViewGroup) getParent()).removeView(view);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mSurfaceLogicImpl.a(motionEvent);
    }

    public void setMapSurfaceListener( amm amm) {
        this.mSurfaceLogicImpl.a(amm);
    }

    public void setMapWidgetListener( amn amn) {
        this.mSurfaceLogicImpl.a(amn);
    }

    public void setNaviMode(int i, boolean z) {
        this.mSurfaceLogicImpl.a(i, z);
    }

    public void setMapGestureListener( amj amj) {
        this.mSurfaceLogicImpl.a(amj);
    }

    public boolean isGestureInMain() {
        return this.mSurfaceLogicImpl.v.b();
    }
}
