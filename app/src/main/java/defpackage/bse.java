package defpackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bse reason: default package */
/* compiled from: MapLayerPresenter */
public final class bse implements OnClickListener, c {
    public f a;
    private long b;
    /* access modifiers changed from: private */
    public cde c;
    private MapManager d;
    private cem e;
    private brt f;
    private boolean g;
    private final defpackage.ccu.a h;

    /* renamed from: bse$a */
    /* compiled from: MapLayerPresenter */
    static class a implements defpackage.ccu.a {
        private cde a;

        public a(cde cde) {
            this.a = cde;
        }

        public final boolean a() {
            if (this.a == null || this.a.b() == null) {
                return true;
            }
            return this.a.b().isViewEnable(256);
        }

        public final boolean b() {
            if (this.a == null || this.a.b() == null) {
                return true;
            }
            return this.a.b().isViewEnable(512);
        }

        public final boolean c() {
            if (this.a == null || this.a.b() == null) {
                return true;
            }
            return this.a.b().isViewEnable(1024);
        }
    }

    public bse(cde cde, MapManager mapManager) {
        this(cde, mapManager, true, new a(cde));
    }

    public bse(cde cde, MapManager mapManager, boolean z) {
        this(cde, mapManager, z, new a(cde));
    }

    public bse(cde cde, MapManager mapManager, boolean z, defpackage.ccu.a aVar) {
        this.b = 0;
        this.g = true;
        this.c = cde;
        this.d = mapManager;
        this.g = z;
        this.h = aVar;
        cdd.n().a((c) this);
    }

    public final void a(cem cem) {
        this.e = cem;
        this.e.setIconImageResource(R.drawable.icon_c2_selector);
        this.e.setIconBackgroundResource(R.drawable.icon_c_bg_single);
        this.e.setIconClickListener(this);
        this.e.setTipsClickListener(this);
    }

    public final void onClick(View view) {
        if (this.e != null && this.c != null) {
            if (view.getId() == R.id.btn_maplayers) {
                a();
                AmapMessage amapMessage = (AmapMessage) this.e.dismissTips();
                if (amapMessage != null) {
                    IMsgboxService iMsgboxService = (IMsgboxService) defpackage.esb.a.a.a(IMsgboxService.class);
                    if (iMsgboxService != null) {
                        iMsgboxService.setRead(amapMessage.id);
                    }
                }
                return;
            }
            if (view.getId() == R.id.layer_tip_tv) {
                AmapMessage amapMessage2 = (AmapMessage) this.e.dismissTips();
                if (amapMessage2 != null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("category", !TextUtils.isEmpty(amapMessage2.id) ? amapMessage2.id : "");
                        if (amapMessage2.location == 4) {
                            jSONObject.put(TrafficUtil.KEYWORD, "4");
                        } else if (amapMessage2.location == 5) {
                            jSONObject.put(TrafficUtil.KEYWORD, "5");
                        } else {
                            jSONObject.put(TrafficUtil.KEYWORD, "");
                        }
                        jSONObject.put("name", !TextUtils.isEmpty(amapMessage2.title) ? amapMessage2.title : "");
                        jSONObject.put("time", lf.b());
                        jSONObject.put("status", 1);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    LogManager.actionLogV2("P00001", "B033", jSONObject);
                }
                if (amapMessage2 != null) {
                    IMsgboxService iMsgboxService2 = (IMsgboxService) defpackage.esb.a.a.a(IMsgboxService.class);
                    if (iMsgboxService2 != null) {
                        iMsgboxService2.setRead(amapMessage2.id);
                        if (!TextUtils.isEmpty(amapMessage2.actionUri)) {
                            iMsgboxService2.executeAction(amapMessage2);
                        }
                    }
                }
            }
        }
    }

    private void d() {
        if (!h()) {
            e();
            g();
        }
    }

    private void e() {
        if (!this.d.checkMutex()) {
            if (this.a == null) {
                this.a = new bsd(this.c, this.d, new com.autonavi.map.core.view.MapLayerDrawerView.a() {
                    public final Context a() {
                        return bse.this.c.a();
                    }

                    public final ViewGroup b() {
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null) {
                            return (ViewGroup) pageContext.getContentView().findViewById(R.id.defaultpage_drawer_content);
                        }
                        return null;
                    }
                });
            }
            this.a.c();
            this.d.doMutex();
        }
    }

    private void f() {
        brt brt;
        int i;
        if (!h()) {
            if (this.g) {
                brt = new brs(this.c, this.d);
            } else {
                brt = new brt(this.c, this.d);
            }
            int i2 = 2;
            int[] iArr = new int[2];
            if (this.e instanceof View) {
                ((View) this.e).getLocationInWindow(iArr);
            }
            Rect rect = new Rect();
            ((Activity) this.c.a()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int i3 = 0;
            brt.a(iArr[0], (iArr[1] - rect.top) + 10);
            if (this.h != null) {
                boolean a2 = this.h.a() | false;
                if (!this.h.b()) {
                    i2 = 0;
                }
                boolean z = i2 | a2;
                if (this.h.c()) {
                    i3 = 4;
                }
                i = z | i3;
            } else {
                i = -1;
            }
            g();
            brt.a(this.c.a(), i, this.c.b().getMapLayerDialogCustomActions());
            this.f = brt;
        }
    }

    private static void g() {
        String str = "";
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (!(mapView == null || mapView.n() == null)) {
            str = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getAdCode());
        }
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, str);
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        LogManager.actionLogV2("P00001", "B008", jSONObject);
    }

    private boolean h() {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - this.b) < 500) {
            this.b = currentTimeMillis;
            return true;
        }
        this.b = currentTimeMillis;
        return false;
    }

    public final void b() {
        if (this.f != null) {
            brt brt = this.f;
            if (brt.a != null) {
                if (brt.c != null) {
                    brt.c.setVisibility(8);
                }
                brt.a.dismiss();
                brt.a = null;
            }
            this.f = null;
        }
        if (this.a != null && this.a.f()) {
            this.a.d();
        }
    }

    public final void c() {
        if (this.a != null && this.a.f()) {
            this.a.d();
        }
    }

    public final void a() {
        apr apr = (apr) defpackage.esb.a.a.a(apr.class);
        if (apr != null ? apr.a(AMapPageUtil.getPageContext()) : false) {
            d();
        } else {
            f();
        }
    }
}
