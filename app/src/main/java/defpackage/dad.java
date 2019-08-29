package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.suspend.refactor.floor.FloorWidgetLayoutWithGuildTip;
import com.autonavi.map.suspend.refactor.floor.FloorWidgetLayoutWithLocationTip;
import com.autonavi.map.suspend.refactor.floor.FloorWidgetView;
import com.autonavi.map.suspend.refactor.floor.FloorWidgetView.a;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppIFloorManager;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dad reason: default package */
/* compiled from: FloorManagerImpl */
public class dad implements cdq {
    cdr a;
    boolean b = false;
    WeakReference<ViewGroup> c = null;
    /* access modifiers changed from: private */
    public FloorWidgetView d;
    /* access modifiers changed from: private */
    public FloorWidgetLayoutWithLocationTip e;
    private View f;
    private a g;
    private String h = "";
    private ami i;
    private ami j;
    /* access modifiers changed from: private */
    public ami k;
    private ami l;
    private cdo m = new cdo() {
        public final void a() {
            dad.this.a.j();
        }

        public final void b() {
            dad.a(dad.this);
            dad.b(dad.this);
        }
    };
    private cdm n = new cdm() {
        public final void onFloorChanged(int i, int i2) {
            String str;
            if (dad.this.k != null) {
                dad.a(dad.this, i, i2);
                cds a2 = dad.this.a(i2);
                if (a2 != null) {
                    i2 = a2.a;
                }
                if (a2 != null) {
                    str = a2.b;
                } else {
                    str = "";
                }
                dad.this.a(dad.this.k.e, i2, str);
                dad.this.a.b().Q();
                if (dad.this.e != null) {
                    if (dad.this.d != null) {
                        dad.this.d.requestLayout();
                    }
                    dad.this.e.requestLayout();
                }
            }
        }
    };
    private agl<cdp> o = new agl<>();
    private String p = null;

    public final void a(cdc cdc) {
        this.a = new cdl(cdc, new FloorWidgetLayoutWithGuildTip(cdc.a()));
        cdd.n().a((a) this);
        cdd.n().a((e) this);
        cdd.n().a((b) this);
        this.e = this.a.g();
        this.f = this.a.h();
        this.g = this.a.i();
    }

    public final void a(cdp cdp) {
        this.o.a(cdp);
    }

    public final void b(cdp cdp) {
        this.o.b(cdp);
    }

    public final ami a() {
        return this.k;
    }

    public final ami b() {
        return this.l;
    }

    public final boolean c() {
        return this.k != null;
    }

    public final void a(String str, int i2, String str2) {
        if (this.k != null && !TextUtils.isEmpty(this.k.e) && this.k.e.equals(str)) {
            this.k.d = i2;
            if (TextUtils.isEmpty(str2)) {
                cds a2 = a(i2);
                if (a2 != null) {
                    str2 = a2.b;
                } else {
                    str2 = "";
                }
            }
            this.k.c = str2;
            this.a.b().a(str2, i2, str);
            if (this.l != null && !TextUtils.isEmpty(this.l.e) && this.l.e.equals(str)) {
                this.l.d = i2;
                this.l.c = str2;
            }
        }
    }

    public final void a(String str) {
        if (TextUtils.isEmpty(str) || this.d == null) {
            c((String) "");
            return;
        }
        c(str);
        if (this.a.d()) {
            try {
                b(Integer.parseInt(str));
            } catch (Throwable th) {
                DebugLog.e("FloorManager", "setIndoorCurrentFloor error.", th);
            }
        }
    }

    private void c(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (!a(str, this.h)) {
            this.h = str;
            if (this.d != null) {
                this.d.setCurrentLocationFloor(str);
                if (this.e != null) {
                    this.d.requestLayout();
                    this.e.requestLayout();
                }
            }
        }
    }

    public final cds d() {
        if (this.d != null) {
            return this.d.getCurrentMapIndoorFloor();
        }
        return null;
    }

    public final cds a(int i2) {
        if (this.d != null) {
            return this.d.getMapIndoorFloorByFloorNum(i2);
        }
        return null;
    }

    public final void b(int i2) {
        if (this.d != null) {
            cds currentMapIndoorFloor = this.d.getCurrentMapIndoorFloor();
            if (currentMapIndoorFloor == null || i2 != currentMapIndoorFloor.a) {
                this.d.setCurrentValue(i2, true);
            }
        }
    }

    public final void b(String str) {
        if (this.d != null) {
            this.d.setCurrentValueByFloorName(str);
        }
    }

    public final void a(String str, int i2, boolean z) {
        if (this.l != null && !TextUtils.isEmpty(this.l.e) && (z || this.l.e.equals(str))) {
            ami ami = this.l;
            if (!(ami == null || ami.h == null)) {
                for (int i3 : ami.h) {
                    if (i2 == i3) {
                        ami.d = i2;
                        return;
                    }
                }
            }
        }
    }

    public final void a(String str, String str2, boolean z) {
        if (this.l != null && !TextUtils.isEmpty(this.l.e) && (z || this.l.e.equals(str))) {
            ami ami = this.l;
            if (!(ami == null || ami.i == null || ami.h == null || TextUtils.isEmpty(str2))) {
                int length = ami.h.length;
                int i2 = 0;
                while (i2 < ami.i.length && i2 < length) {
                    if (str2.equals(ami.i[i2])) {
                        ami.d = ami.h[i2];
                        return;
                    }
                    i2++;
                }
            }
        }
    }

    private void a(ami ami) {
        if (this.e != null) {
            String[] strArr = ami.i;
            int[] iArr = ami.h;
            int i2 = ami.d;
            String str = ami.c;
            this.e.setVisibility(4);
            this.e.removeAllViews();
            if (this.d != null) {
                this.d.removeChangingListener(this.n);
                this.d.removeScrollingListener(this.m);
            }
            this.d = new FloorWidgetView(this.a.a());
            this.d.setCurrentLocationFloor(this.h);
            this.d.setBuildingPoiId(ami.e);
            this.d.setBuildingName(ami.a);
            this.d.setBuildingType(ami.f);
            FloorWidgetView floorWidgetView = this.d;
            StringBuilder sb = new StringBuilder();
            sb.append(ami.d);
            floorWidgetView.setBuildingFloor(sb.toString());
            this.e.setFloorView(this.d, this.g);
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < strArr.length; i3++) {
                cds cds = new cds();
                cds.b = strArr[i3];
                cds.a = iArr[i3];
                arrayList.add(cds);
            }
            this.d.setAdapter(new cdt(arrayList));
            this.d.setCyclic(false);
            this.d.setCurrentValue(i2, false);
            this.d.addChangingListener(this.n);
            this.d.addScrollingListener(this.m);
            if (!(this.i == null || a(ami, this.i) || ami.d == this.i.d)) {
                a(ami.e, i2, str);
            }
            if (this.f != null) {
                this.f.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        dad.b(dad.this);
                    }
                });
            }
        }
    }

    public final boolean e() {
        return this.e != null && this.e.getVisibility() == 0;
    }

    private void b(boolean z) {
        boolean e2 = e();
        int i2 = 0;
        boolean z2 = true;
        boolean z3 = (this.a.e() && this.k != null) && !this.a.f() && z;
        if (z3 && this.d == null) {
            a(this.k);
        }
        if (this.e != null) {
            this.e.setVisibility(z3 ? 0 : 8);
        }
        boolean e3 = e();
        if (e3) {
            this.e.isFloorViewAdded();
        }
        final boolean z4 = this.k != null;
        boolean z5 = (this.k != null && this.j == null) || (this.k == null && this.j != null);
        if (e2 != e3 || z5) {
            final ami ami = this.k;
            final int i3 = this.k == null ? 1 : this.k.d;
            a(this.a.c());
            this.o.a((a<T>) new a<cdp>() {
                public final /* synthetic */ void onNotify(Object obj) {
                    ((cdp) obj).onFloorWidgetVisibilityChanged(ami, z4, i3);
                }
            });
        }
        if (this.f != null && !this.b) {
            boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(MiniAppIFloorManager.SP_KEY_show_map_indoor_guide, true);
            if (!booleanValue && this.f.getVisibility() != 0) {
                this.b = true;
            }
            if (!booleanValue || this.k == null || this.d == null || this.d.getVisibleItems() <= 0 || this.e == null || this.e.getVisibility() != 0) {
                z2 = false;
            }
            View view = this.f;
            if (!z2) {
                i2 = 8;
            }
            view.setVisibility(i2);
        }
    }

    private static boolean a(ami ami, ami ami2) {
        String str = null;
        String str2 = ami != null ? ami.e : null;
        if (ami2 != null) {
            str = ami2.e;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        return !str2.equals(str);
    }

    private static boolean a(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public final void a(ViewGroup viewGroup) {
        if (this.a.k() == null) {
            return;
        }
        if (this.c == null || this.c.get() == null || this.c.get() != viewGroup) {
            if (!(this.c == null || this.c.get() == null)) {
                ((ViewGroup) this.c.get()).removeView(this.a.k());
                this.c = null;
            }
            viewGroup.addView(this.a.k());
            this.c = new WeakReference<>(viewGroup);
        }
    }

    public final void a(boolean z) {
        if (this.a.k() != null) {
            this.a.k().setTipInRight(z);
        }
    }

    private void a(MapManager mapManager) {
        boolean z;
        if (!(mapManager == null || mapManager.getOverlayManager() == null || mapManager.getOverlayManager().getGpsLayer() == null)) {
            if (!c()) {
                mapManager.getOverlayManager().getGpsLayer().b(true);
            } else if (!TextUtils.isEmpty(this.h) && !this.h.equals("")) {
                cds d2 = d();
                if (d2 != null) {
                    String str = this.h;
                    StringBuilder sb = new StringBuilder();
                    sb.append(d2.a);
                    z = str.equals(sb.toString());
                } else {
                    z = false;
                }
                mapManager.getOverlayManager().getGpsLayer().b(z);
            }
        }
    }

    public void onResetViewState() {
        if (!cdd.n().l()) {
            b(true);
        }
    }

    public void updateStateWhenCompassPaint() {
        b(true);
    }

    public boolean onIndoorBuildingActive(ami ami) {
        this.j = this.k;
        if (this.k != null) {
            this.i = this.k;
        }
        this.k = ami;
        if (this.k != null && this.l != null && !TextUtils.isEmpty(this.k.e) && !TextUtils.isEmpty(this.l.e) && this.k.e.equals(this.l.e) && this.k.d != this.l.d) {
            a(this.l.e, this.l.d, this.l.c);
            this.a.b().Q();
        }
        MapSharePreference mapSharePreference = new MapSharePreference((String) MiniAppIFloorManager.SP_NAME_indoor_config);
        if (this.k != null) {
            if (this.l == null || a(ami, this.l)) {
                this.l = ami;
            }
            mapSharePreference.putStringValue(MiniAppIFloorManager.SP_KEY_indoor_building_poiid, this.k.e);
            String str = this.k.e;
            if (TextUtils.isEmpty(this.p) || !this.p.equals(str)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(TrafficUtil.POIID, str);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B050", jSONObject);
                this.p = str;
            }
            if (this.a.e()) {
                boolean a2 = a(ami, this.j);
                if (a2) {
                    b(false);
                }
                if (a(ami, this.i)) {
                    if (this.e != null) {
                        this.e.clearLocationType();
                    }
                    c((String) "");
                }
                if (this.d == null || a2) {
                    a(ami);
                }
            }
        } else {
            this.p = null;
            mapSharePreference.putStringValue(MiniAppIFloorManager.SP_KEY_indoor_building_poiid, "");
            if (this.e != null) {
                if (this.d != null) {
                    this.d.removeChangingListener(this.n);
                    this.d.removeScrollingListener(this.m);
                }
                this.e.removeAllViews();
                this.d = null;
            }
        }
        b(true);
        brq.a().a(c());
        return e();
    }

    static /* synthetic */ void a(dad dad) {
        if (dad.k != null) {
            String str = dad.k.e;
            String str2 = dad.k.a;
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(TrafficUtil.POIID, str);
                    jSONObject.put("poiName", str2);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_ON_FLOOR_CHANGED, jSONObject);
            }
        }
    }

    static /* synthetic */ void b(dad dad) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(MiniAppIFloorManager.SP_KEY_show_map_indoor_guide, false);
        if (dad.f != null) {
            dad.f.setVisibility(8);
        }
    }

    static /* synthetic */ void a(dad dad, final int i2, final int i3) {
        dad.a(dad.a.c());
        dad.o.a((a<T>) new a<cdp>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((cdp) obj).onFloorChanged(i2, i3);
            }
        });
    }
}
