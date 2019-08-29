package com.autonavi.ae.gmap;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

public class AMapTextureSurface extends TextureView implements aky, SurfaceTextureListener {
    private String TAG = "AMapTextureSurface";
    private ald mSurfaceLogicImpl = null;

    public AMapTextureSurface(Context context) {
        super(context);
        this.mSurfaceLogicImpl = new ald(context);
        setSurfaceTextureListener(this);
        "AMapTextureSurface Context:  this = ".concat(String.valueOf(this));
        ana.a();
    }

    public AMapTextureSurface(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSurfaceLogicImpl = new ald(context);
        setSurfaceTextureListener(this);
        "AMapTextureSurface AttributeSet:  this = ".concat(String.valueOf(this));
        ana.a();
    }

    public void init(akq akq) {
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

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Surface surface = new Surface(surfaceTexture);
        long elapsedRealtime2 = SystemClock.elapsedRealtime();
        this.mSurfaceLogicImpl.a(surface, i, i2);
        StringBuilder sb = new StringBuilder("onSurfaceTextureAvailable time: ");
        sb.append(elapsedRealtime2 - elapsedRealtime);
        sb.append(", ");
        sb.append(SystemClock.elapsedRealtime() - elapsedRealtime2);
        sb.append(", ");
        sb.append(surfaceTexture);
        sb.append(", ");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Surface surface = new Surface(surfaceTexture);
        long elapsedRealtime2 = SystemClock.elapsedRealtime();
        this.mSurfaceLogicImpl.b(surface, i, i2);
        StringBuilder sb = new StringBuilder("onSurfaceTextureSizeChanged time: ");
        sb.append(elapsedRealtime2 - elapsedRealtime);
        sb.append(", ");
        sb.append(SystemClock.elapsedRealtime() - elapsedRealtime2);
        sb.append(", ");
        sb.append(surfaceTexture);
        sb.append(", ");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mSurfaceLogicImpl.a((Surface) null);
        StringBuilder sb = new StringBuilder("onSurfaceTextureDestroyed time: ");
        sb.append(SystemClock.elapsedRealtime() - elapsedRealtime);
        sb.append(" this = ");
        sb.append(this);
        ana.a();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.TAG);
        sb.append("Draw");
        StringBuilder sb2 = new StringBuilder("onSurfaceTextureUpdated: ");
        sb2.append(surfaceTexture);
        sb2.append(" this = ");
        sb2.append(this);
        ana.a();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        uninit();
        super.finalize();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        "onSizeChanged  this = ".concat(String.valueOf(this));
        ana.a();
        super.onSizeChanged(i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        super.onAttachedToWindow();
        long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
        StringBuilder sb = new StringBuilder("onAttachedToWindow time: ");
        sb.append(elapsedRealtime2);
        sb.append(",  this = ");
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
        StringBuilder sb = new StringBuilder("onDetachedFromWindow time: ");
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

    public void setMapSurfaceListener(amm amm) {
        this.mSurfaceLogicImpl.a(amm);
    }

    public void setMapWidgetListener(amn amn) {
        this.mSurfaceLogicImpl.a(amn);
    }

    public void setNaviMode(int i, boolean z) {
        this.mSurfaceLogicImpl.a(i, z);
    }

    public void setMapGestureListener(amj amj) {
        this.mSurfaceLogicImpl.a(amj);
    }

    public boolean isGestureInMain() {
        return this.mSurfaceLogicImpl.v.b();
    }
}
