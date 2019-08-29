package com.alipay.mobile.monitor.track;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.monitor.track.interceptor.ClickInterceptorManager;

public class TrackTouchDelegate extends TouchDelegate {
    private final TouchDelegate a;
    private final TrackClickListener b = new TrackClickListener();
    private final View c;
    private final View d;
    /* access modifiers changed from: private */
    public final String e;
    /* access modifiers changed from: private */
    public final String f;
    /* access modifiers changed from: private */
    public final boolean g;
    private OnClickListener h;
    /* access modifiers changed from: private */
    public OnClickListener i;
    private final AdapterView<?> j;
    private OnItemClickListener k;
    /* access modifiers changed from: private */
    public OnItemClickListener l;
    /* access modifiers changed from: private */
    public ClickInterceptorManager m;

    public class TrackClickListener implements OnClickListener, OnItemClickListener {
        public TrackClickListener() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (TrackTouchDelegate.this.g) {
                LoggerFactory.getLogContext().notifyClientEvent(LogContext.ENVENT_SUBAPPRESUME, TrackTouchDelegate.this.f);
                LoggerFactory.getLogContext().notifyClientEvent(LogContext.ENVENT_VIEWSWITCH, TrackTouchDelegate.this.e);
            }
            boolean intercepted = false;
            if (TrackTouchDelegate.this.m != null) {
                intercepted = TrackTouchDelegate.this.m.handleOnItemClick(parent, view, position, id, TrackTouchDelegate.this.e, TrackTouchDelegate.this.f);
            }
            if (TrackTouchDelegate.this.l != null && !intercepted) {
                TrackTouchDelegate.this.l.onItemClick(parent, view, position, id);
                TrackTouchDelegate.this.m.handleOnItemClickAfter(parent, view, position, id);
            }
            TrackTouchDelegate.this.b();
        }

        public void onClick(View view) {
            if (TrackTouchDelegate.this.g) {
                LoggerFactory.getLogContext().notifyClientEvent(LogContext.ENVENT_SUBAPPRESUME, TrackTouchDelegate.this.f);
                LoggerFactory.getLogContext().notifyClientEvent(LogContext.ENVENT_VIEWSWITCH, TrackTouchDelegate.this.e);
            }
            boolean intercepted = false;
            if (TrackTouchDelegate.this.m != null) {
                intercepted = TrackTouchDelegate.this.m.handleOnClick(view, TrackTouchDelegate.this.e, TrackTouchDelegate.this.f);
            }
            if (TrackTouchDelegate.this.i != null && !intercepted) {
                TrackTouchDelegate.this.i.onClick(view);
                TrackTouchDelegate.this.m.handleOnClickAfter(view);
            }
            TrackTouchDelegate.this.b();
        }
    }

    public TrackTouchDelegate(AdapterView<?> adapterView, View view, View rootContentView, ClickInterceptorManager clickInterceptorManager, TouchDelegate touchDelegate, String pageId, String appId, boolean isBindingImmediately) {
        super(new Rect(), view);
        this.j = adapterView;
        this.c = view;
        this.d = rootContentView;
        this.m = clickInterceptorManager;
        this.a = touchDelegate;
        this.e = pageId;
        this.f = appId;
        this.g = isBindingImmediately;
    }

    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (event.getAction() == 0) {
                a();
            } else if (event.getAction() == 3) {
                TrackIntegrator.getInstance().postAddDelegate(this.d, this.e, this.f, true, 1500);
            }
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) "TrackTouchDelegate", e2);
        }
        if (this.a != null) {
            return this.a.onTouchEvent(event);
        }
        return false;
    }

    private void a() {
        if (this.j != null) {
            OnItemClickListener curAdapterViewListener = this.j.getOnItemClickListener();
            if (curAdapterViewListener != null && !(curAdapterViewListener instanceof TrackClickListener)) {
                this.k = curAdapterViewListener;
                this.l = this.k;
                this.j.setOnItemClickListener(this.b);
            }
        }
        if (this.c != null) {
            OnClickListener curonClickListener = TrackReflector.a().a(this.c);
            if (curonClickListener != null && curonClickListener != this.b) {
                this.h = curonClickListener;
                this.i = this.h;
                TrackReflector.a().a(this.c, this.b);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (!(this.j == null || this.k == null)) {
            this.j.setOnItemClickListener(this.k);
        }
        if (this.c != null && this.h != null) {
            TrackReflector.a().a(this.c, this.h);
        }
    }
}
