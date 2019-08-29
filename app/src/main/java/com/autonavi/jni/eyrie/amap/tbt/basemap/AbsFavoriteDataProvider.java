package com.autonavi.jni.eyrie.amap.tbt.basemap;

import java.util.List;

public abstract class AbsFavoriteDataProvider {
    private long mHandle = 0;

    private static native long nativeCreateHandle(AbsFavoriteDataProvider absFavoriteDataProvider);

    private static native void nativeDestroyHandle(long j);

    /* access modifiers changed from: protected */
    public abstract List<FavoritePOIInfo> acquireCompanyData();

    /* access modifiers changed from: protected */
    public abstract List<FavoritePOIInfo> acquireFavoriteData();

    /* access modifiers changed from: protected */
    public abstract List<FavoritePOIInfo> acquireHomeData();

    public long createHandle() {
        this.mHandle = nativeCreateHandle(this);
        return this.mHandle;
    }

    public void releaseHandle() {
        nativeDestroyHandle(this.mHandle);
    }
}
