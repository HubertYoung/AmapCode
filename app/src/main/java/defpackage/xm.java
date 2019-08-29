package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.jni.eyrie.amap.tbt.scene.basemap.NaviBasemapMain;

@BundleInterface(xp.class)
/* renamed from: xm reason: default package */
/* compiled from: MapLayerServiceImpl */
public class xm extends esi implements xp {
    private SparseArray<xo> a = new SparseArray<>();
    private NaviBasemapMain b;

    public final void a(int i, xo xoVar) {
        StringBuilder sb = new StringBuilder("registerGlobalLayer type:");
        sb.append(i);
        sb.append(" , layer:");
        sb.append(xoVar);
        AMapLog.debug("basemap.maplayer", "MapLayerServiceImpl", sb.toString());
        this.a.put(i, xoVar);
    }

    @Nullable
    public final xo a() {
        return this.a.get(0);
    }

    public final void b() {
        if (this.b != null) {
            this.b.hide();
        }
    }

    public final void c() {
        if (this.b != null) {
            this.b.show();
        }
    }

    @NonNull
    public final NaviBasemapMain d() {
        StringBuilder sb = new StringBuilder("getNaviBasemapMainScene:");
        sb.append(this.b);
        AMapLog.debug("basemap.maplayer", "MapLayerServiceImpl", sb.toString());
        if (this.b == null) {
            this.b = NaviBasemapMain.create();
        }
        return this.b;
    }

    public final void e() {
        StringBuilder sb = new StringBuilder("release:");
        sb.append(this.b);
        AMapLog.debug("basemap.maplayer", "MapLayerServiceImpl", sb.toString());
        if (this.b != null) {
            this.b.destroy();
            this.b = null;
        }
    }
}
