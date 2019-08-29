package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.R;

/* renamed from: dle reason: default package */
/* compiled from: CommonMainMapView */
public abstract class dle {
    protected final bid a;
    protected final Context b;
    protected final ViewGroup c;
    protected final ViewGroup d;
    protected cde e;
    protected MapManager f;
    protected dlc g;
    protected MapSharePreference h;
    protected boolean i;
    private a j = new a() {
        public final void a(boolean z) {
            dlc dlc = dle.this.g;
            boolean z2 = false;
            int i = z ? 8 : 0;
            dam dam = dlc.f;
            if (i == 8) {
                z2 = true;
            }
            dam.b = z2;
            if (dlc.i != null && dlc.f.a()) {
                dlc.f.a();
                dlc.i.setVisibility(i);
            }
        }
    };

    public abstract void a(@Nullable Context context);

    public abstract void d();

    public abstract View e();

    public abstract View f();

    public abstract void g();

    public dle(@NonNull bid bid, @NonNull View view) {
        this.a = bid;
        this.b = bid.getContext();
        this.c = (ViewGroup) view.findViewById(R.id.mapTopInteractiveView);
        this.d = (ViewGroup) view.findViewById(R.id.mapBottomInteractiveView);
        this.h = new MapSharePreference(SharePreferenceName.SharedPreferences);
    }

    public void a() {
        c();
        d();
    }

    public void b() {
        if (this.e != null) {
            brq.a().b(this.j);
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        cdd.n().d();
    }

    public final void a(@NonNull dlc dlc, cde cde, MapManager mapManager) {
        this.g = dlc;
        this.e = cde;
        this.f = mapManager;
        if (this.e != null) {
            brq.a().a(this.j);
        }
    }

    public void a(boolean z) {
        this.i = z;
        cdd.n().d(z);
    }
}
