package com.autonavi.miniapp.plugin.map.property;

import android.content.Context;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.ComponentJsonObj;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.MapJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import java.lang.ref.WeakReference;

public abstract class BasePropertyProcessor {
    protected WeakReference<Context> mContext;
    protected WeakReference<H5Page> mPage;
    protected AdapterTextureMapView mRealView;

    /* access modifiers changed from: protected */
    public abstract void doClear();

    /* access modifiers changed from: protected */
    public abstract void doDestroy();

    /* access modifiers changed from: protected */
    public void doProcess(ComponentJsonObj componentJsonObj) {
    }

    /* access modifiers changed from: protected */
    public abstract void doProcess(MapJsonObj mapJsonObj);

    public BasePropertyProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        this.mContext = weakReference;
        this.mPage = weakReference2;
        this.mRealView = adapterTextureMapView;
    }

    public final void process(MapJsonObj mapJsonObj) {
        if (this.mContext != null && this.mRealView != null && mapJsonObj != null) {
            doProcess(mapJsonObj);
        }
    }

    public final void process(ComponentJsonObj componentJsonObj) {
        if (this.mContext != null && this.mRealView != null && componentJsonObj != null) {
            doProcess(componentJsonObj);
        }
    }

    public final void clear() {
        doClear();
    }

    public final void destroy() {
        doDestroy();
    }

    /* access modifiers changed from: protected */
    public final int convertDp(double d) {
        return agn.a((Context) this.mContext.get(), (float) d);
    }
}
