package defpackage;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.page.SearchErrorIndoorPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bwx reason: default package */
/* compiled from: PoiSearcher */
public final class bwx implements bvr {
    public static final SearchFor a = SearchFor.DEFAULT;
    private String A;
    bvp b;
    public bws c;
    boolean d;
    public String e;
    ArrayList<POI> f;
    ArrayList<POI> g;
    public ArrayList<String> h;
    public ArrayList<String> i;
    InfoliteResult j;
    int k;
    public boolean l;
    boolean m;
    a n;
    public Rect o;
    public int p;
    String q;
    public POI r;
    public boolean s;
    /* access modifiers changed from: private */
    public POI t;
    /* access modifiers changed from: private */
    public POI u;
    private String v;
    private bwr w;
    private boolean x;
    private boolean y;
    private boolean z;

    /* renamed from: bwx$a */
    /* compiled from: PoiSearcher */
    class a extends Handler {
        a() {
        }

        public final void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 1008:
                        bwx.this.t = (POI) message.obj;
                        bwx.this.c.b();
                        return;
                    case 1009:
                        bwx.this.c.c();
                        break;
                    case 1010:
                        bwx.this.u = (POI) message.obj;
                        bwx.this.c.a(bwx.this.t, bwx.this.u);
                        return;
                }
            } catch (Exception unused) {
            }
        }
    }

    public bwx(String str, int i2, boolean z2) {
        this(str, i2, z2, false, false, false, "");
    }

    public bwx(String str, int i2, boolean z2, boolean z3, boolean z4, boolean z5, String str2) {
        this.d = false;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = -1;
        this.l = false;
        this.m = false;
        this.n = new a();
        this.p = -1;
        this.q = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.s = false;
        this.x = false;
        this.y = false;
        this.z = false;
        this.A = "";
        this.e = str;
        this.k = i2;
        this.d = z2;
        adz adz = (adz) defpackage.esb.a.a.a(adz.class);
        if (adz != null) {
            adz.a();
        }
        this.b = new bvs();
        this.c = new bwz(this);
        this.w = new bww(this);
        this.x = z3;
        this.y = z4;
        this.z = z5;
        this.A = str2;
    }

    public final bbq a(InfoliteParam infoliteParam, Rect rect, boolean z2) {
        return this.c.a(infoliteParam, rect, z2);
    }

    private static TipItem a(TipItem tipItem, String str) {
        if (tipItem == null) {
            tipItem = new TipItem();
            tipItem.name = str;
        }
        tipItem.time = new Date();
        return tipItem;
    }

    public final void a(String str) {
        this.w.a(str);
    }

    public final void a(InfoliteResult infoliteResult) {
        this.w.a(infoliteResult);
    }

    public final void a(InfoliteResult infoliteResult, TipItem tipItem, boolean z2) {
        if (infoliteResult != null) {
            this.c.a(infoliteResult, a(tipItem, infoliteResult.mKeyword), 1, z2);
        }
    }

    public final void b(InfoliteResult infoliteResult, TipItem tipItem, boolean z2) {
        if (infoliteResult != null) {
            this.c.a(infoliteResult, a(tipItem, infoliteResult.mKeyword), 0, z2);
        }
    }

    public final void c(InfoliteResult infoliteResult, TipItem tipItem, boolean z2) {
        if (infoliteResult != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(TrafficUtil.KEYWORD, infoliteResult.mKeyword);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00004", "B045", jSONObject);
            this.c.a(infoliteResult, a(tipItem, infoliteResult.mKeyword), 2, z2);
        }
    }

    public final void b(InfoliteResult infoliteResult) {
        if (infoliteResult.responseHeader.c == -1) {
            ToastHelper.showLongToast(AbstractAOSParser.ERROR_NETWORK);
        } else if (!this.c.a(infoliteResult)) {
            String str = "";
            if (infoliteResult.searchInfo.a.I != 0 || infoliteResult.searchInfo.a.J == null || !infoliteResult.searchInfo.a.J.equals("interior")) {
                str = AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.ic_net_error_noresult);
            } else {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(TrafficUtil.KEYWORD, infoliteResult.mWrapper.keywords);
                    LogManager.actionLogV2("P00008", "B008", jSONObject);
                } catch (Exception unused) {
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString(TrafficUtil.KEYWORD, infoliteResult.mWrapper.keywords);
                this.b.a(SearchErrorIndoorPage.class, pageBundle);
            }
            if (!TextUtils.isEmpty(str)) {
                this.b.b(str);
            }
        }
    }

    public final void a(InfoliteResult infoliteResult, boolean z2) {
        ArrayList<POI> arrayList = null;
        if (infoliteResult.locationInfo == null || infoliteResult.locationInfo.a != 1) {
            boolean z3 = false;
            if (infoliteResult.locationInfo != null) {
                arrayList = infoliteResult.locationInfo.c;
            } else if (infoliteResult.searchInfo != null) {
                arrayList = infoliteResult.searchInfo.l;
                z3 = true;
            }
            if (arrayList != null) {
                if (!z3) {
                    if (arrayList.isEmpty()) {
                        this.c.a(infoliteResult, z2, this.p);
                        return;
                    }
                } else if (arrayList.size() == 1) {
                    this.c.a(infoliteResult, z2, this.p);
                    return;
                }
                this.w.a(infoliteResult, arrayList);
            } else if (!this.c.a(infoliteResult)) {
                this.b.b(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.ic_net_error_noresult));
                return;
            }
            return;
        }
        if (a != SearchFor.QUICK_NAVI) {
            this.w.a(infoliteResult, null);
        } else if (!this.c.a(infoliteResult)) {
            this.b.b(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.ic_net_error_noresult));
        }
    }

    public final void a(InfoliteResult infoliteResult, boolean z2, TipItem tipItem) {
        ArrayList<CitySuggestion> arrayList = infoliteResult.searchInfo.g;
        if (arrayList == null || arrayList.isEmpty()) {
            this.c.a(infoliteResult, z2, this.p, this.x, this.y, this.z, this.A);
            return;
        }
        this.c.a(infoliteResult, tipItem);
    }

    public final void b(InfoliteResult infoliteResult, boolean z2, TipItem tipItem) {
        ArrayList<CitySuggestion> arrayList = infoliteResult.searchInfo.g;
        if (arrayList == null || arrayList.isEmpty()) {
            if (!z2 && !this.c.a(infoliteResult)) {
                if (infoliteResult.searchInfo.a.I != 0 || infoliteResult.searchInfo.a.J == null || !infoliteResult.searchInfo.a.J.equals("interior")) {
                    this.b.b(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.ic_net_error_noresult));
                } else {
                    this.b.b(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.ic_net_error_indoor_noresult));
                }
            }
            this.c.a(infoliteResult, z2, this.p);
            return;
        }
        this.c.a(infoliteResult, tipItem);
    }

    public final void a() {
        boolean z2 = false;
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getApplicationContext().getSharedPreferences("OfflineDlgSp", 0);
        if (sharedPreferences != null) {
            z2 = sharedPreferences.getBoolean("showed", false);
            if (!z2) {
                sharedPreferences.edit().putBoolean("showed", true).apply();
            }
        }
        if (!z2) {
            this.b.a();
        }
    }

    public final void c(InfoliteResult infoliteResult) {
        if (infoliteResult == null || infoliteResult.routingInfo == null || ((infoliteResult.routingInfo.h == null || infoliteResult.routingInfo.h.size() == 0) && (infoliteResult.routingInfo.c == null || infoliteResult.routingInfo.c.size() == 0))) {
            this.f = null;
            this.g = null;
            this.t = null;
            this.u = null;
            Message message = new Message();
            message.what = 1010;
            this.n.sendMessage(message);
            return;
        }
        this.j = infoliteResult;
        this.h = infoliteResult.routingInfo.d;
        this.i = infoliteResult.routingInfo.i;
        this.v = infoliteResult.routingInfo.a;
        this.q = infoliteResult.routingInfo.g;
        if (this.h == null && this.i != null && this.i.size() > 0) {
            this.c.c();
        } else if (this.h == null || this.h.size() <= 0) {
            this.f = infoliteResult.routingInfo.c;
            this.g = infoliteResult.routingInfo.h;
            this.t = null;
            this.u = null;
            this.c.a();
        } else {
            this.c.a(this.h, this.v, AMapAppGlobal.getApplication().getApplicationContext().getString(R.string.Title_SuggestFromto_Start), (Handler) this.n);
        }
    }
}
