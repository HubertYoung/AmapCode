package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.AEUtil;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.commute.CommuteTipOverlay;
import com.autonavi.minimap.commute.CommuteTipsManager$3;
import com.autonavi.minimap.commute.RainbowBarView;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.map.FavoriteOverlayItem;
import com.autonavi.minimap.navigation.NavigationRequestHolder;
import com.autonavi.minimap.navigation.param.HomeCompanyRequest;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoritePoint;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ddu reason: default package */
/* compiled from: CommuteTipsManager */
public class ddu implements OnClickListener, defpackage.ceg.a, OnItemClickListener {
    /* access modifiers changed from: private */
    public static final String b = "ddu";
    int a;
    private cde c;
    private MapManager d;
    /* access modifiers changed from: private */
    public bty e;
    private Context f;
    private Handler g;
    /* access modifiers changed from: private */
    public CommuteTipOverlay h;
    /* access modifiers changed from: private */
    public View i;
    private View j;
    private ImageView k;
    private TextView l;
    private TextView m;
    private RainbowBarView n;
    private b o = new b(this);
    /* access modifiers changed from: private */
    public int p = 0;
    private boolean q = false;
    private GeoPoint r = null;
    private MapViewLayoutParams s;

    /* renamed from: ddu$a */
    /* compiled from: CommuteTipsManager */
    static class a {
        a() {
        }
    }

    /* renamed from: ddu$b */
    /* compiled from: CommuteTipsManager */
    static class b implements Runnable {
        GeoPoint a;
        private WeakReference<ddu> b;

        public b(ddu ddu) {
            this.b = new WeakReference<>(ddu);
        }

        public final void run() {
            ddu ddu = (ddu) this.b.get();
            if (ddu != null) {
                ddu.a(ddu, this.a);
            }
        }
    }

    public ddu(cde cde, MapManager mapManager) {
        this.c = cde;
        this.c.d().a((defpackage.ceg.a) this);
        this.e = mapManager.getMapView();
        this.d = mapManager;
        this.f = this.c.a();
        this.g = new Handler(Looper.getMainLooper());
        this.h = new CommuteTipOverlay(this.e, mapManager.getOverlayManager());
        this.h.setOnItemClickListener(this);
        this.e.F().b((BaseMapOverlay) this.h);
        if (this.h.getGLOverlay() != null) {
            ((GLPointOverlay) this.h.getGLOverlay()).setOverlayPriority(150);
        }
        EventBus.getDefault().register(this);
        this.i = LayoutInflater.from(this.f).inflate(R.layout.commute_tips_layout2, null);
        this.j = this.i.findViewById(R.id.commute_tips_close);
        this.l = (TextView) this.i.findViewById(R.id.commute_tips_spend_time);
        this.m = (TextView) this.i.findViewById(R.id.commute_tips_traffic);
        this.n = (RainbowBarView) this.i.findViewById(R.id.commute_rainbow_bar);
        this.k = (ImageView) this.i.findViewById(R.id.commute_tips_icon);
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
    }

    public final void a(GeoPoint geoPoint) {
        if (AMapPageUtil.isHomePage() && this.a != 11) {
            if (this.r == null || !this.r.equals(geoPoint)) {
                this.r = geoPoint;
                if (this.a != 12) {
                    if (this.a == 13) {
                        c(geoPoint);
                    }
                } else if (!f()) {
                    b(geoPoint);
                } else {
                    this.g.removeCallbacks(this.o);
                    this.o.a = geoPoint;
                    this.g.postDelayed(this.o, 50);
                }
            }
        }
    }

    public final void b() {
        if (this.e != null && AMapPageUtil.isHomePage() && this.a == 12) {
            c(LocationInstrument.getInstance().getLatestPosition());
            a(13);
            a(12, (String) "4");
        }
    }

    private void b(GeoPoint geoPoint) {
        this.g.removeCallbacks(this.o);
        if (AMapPageUtil.isHomePage()) {
            this.e.a(this.i);
            this.e.a(this.i, (LayoutParams) d(geoPoint));
            this.h.removeAll();
            this.a = 12;
        }
    }

    private void c(final GeoPoint geoPoint) {
        this.g.post(new Runnable() {
            public final void run() {
                if (AMapPageUtil.isHomePage()) {
                    ddu.b;
                    ddu.this.e.a(ddu.this.i);
                    ddu.this.h.updateItem(new FavoriteOverlayItem(geoPoint), ddu.this.p == 2);
                    ddu.this.a = 13;
                }
            }
        });
    }

    private void e() {
        this.e.a(this.i);
        this.h.removeAll();
        this.e.F().c(this.h);
        EventBus.getDefault().unregister(this);
        this.c.d().a((defpackage.ceg.a) null);
        this.a = 11;
    }

    public final void a(String str) {
        if (this.e != null && this.a != 11) {
            this.q = true;
            if (!TextUtils.isEmpty(str)) {
                a(this.a, str);
            }
            this.g.removeCallbacks(this.o);
            e();
        }
    }

    public void onClick(View view) {
        if (view.equals(this.i)) {
            a((String) "2");
            PageBundle pageBundle = new PageBundle();
            FavoritePOI favoritePOI = null;
            com com2 = (com) ank.a(com.class);
            if (com2 != null) {
                cop b2 = com2.b(com2.a());
                if (b2 != null) {
                    if (this.p == 2) {
                        favoritePOI = b2.c();
                    } else if (this.p == 1) {
                        favoritePOI = b2.d();
                    }
                    if (favoritePOI != null) {
                        pageBundle.putObject("bundle_key_poi_end", favoritePOI.clone());
                        pageBundle.putObject("bundle_key_route_type", RouteType.CAR);
                        pageBundle.putObject("bundle_key_auto_route", Boolean.TRUE);
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null) {
                            pageContext.startPage((String) "amap.extra.route.route", pageBundle);
                        }
                        if (this.p != 0) {
                            JSONObject jSONObject = new JSONObject();
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append(this.p);
                                jSONObject.put("type", sb.toString());
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                            LogManager.actionLogV2("P00001", "B210", jSONObject);
                        }
                    }
                }
            }
        } else if (view.equals(this.j)) {
            a((String) "1");
        }
    }

    private void a(int i2) {
        if (this.p != 0 && i2 != 11) {
            String str = "";
            if (this.p == 1) {
                if (i2 == 12) {
                    str = "1";
                } else if (i2 == 13) {
                    str = "3";
                }
            } else if (this.p == 2) {
                if (i2 == 12) {
                    str = "2";
                } else if (i2 == 13) {
                    str = "4";
                }
            }
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", str);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B208", jSONObject);
            }
        }
    }

    private void a(int i2, String str) {
        if (this.p != 0 && i2 != 11) {
            String str2 = "";
            if (this.p == 1) {
                if (i2 == 12) {
                    str2 = "1";
                } else if (i2 == 13) {
                    str2 = "3";
                }
            } else if (this.p == 2) {
                if (i2 == 12) {
                    str2 = "2";
                } else if (i2 == 13) {
                    str2 = "4";
                }
            }
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", str2);
                    jSONObject.put("from", str);
                    LogManager.actionLogV2("P00001", "B209", jSONObject);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private MapViewLayoutParams d(GeoPoint geoPoint) {
        this.s = new MapViewLayoutParams(-2, -2, geoPoint, 81);
        this.s.mode = 0;
        StringBuilder sb = new StringBuilder("getTipParams: ");
        sb.append(geoPoint.getLatitude());
        sb.append("==");
        sb.append(geoPoint.getLongitude());
        return this.s;
    }

    public static void c() {
        EventBus.getDefault().post(new a());
    }

    private boolean f() {
        if (this.e == null) {
            return false;
        }
        return this.e.g();
    }

    @NonNull
    public void onEventMainThread(a aVar) {
        b();
    }

    public final void a() {
        if (!this.q) {
            this.q = true;
            ddw ddw = new ddw();
            List<GirfFavoritePoint> N = bin.a.N();
            List<GirfFavoritePoint> O = bin.a.O();
            int i2 = 0;
            if (N != null && !N.isEmpty()) {
                GirfFavoritePoint girfFavoritePoint = N.get(0);
                if (girfFavoritePoint != null) {
                    DPoint a2 = cfg.a(Long.parseLong(girfFavoritePoint.px), Long.parseLong(girfFavoritePoint.py));
                    ddw.c = a2.x;
                    ddw.d = a2.y;
                }
            }
            if (O != null && !O.isEmpty()) {
                GirfFavoritePoint girfFavoritePoint2 = O.get(0);
                if (girfFavoritePoint2 != null) {
                    DPoint a3 = cfg.a(Long.parseLong(girfFavoritePoint2.px), Long.parseLong(girfFavoritePoint2.py));
                    ddw.e = a3.x;
                    ddw.f = a3.y;
                }
            }
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition == null) {
                latestPosition = new GeoPoint();
            }
            ddw.a = latestPosition.getLongitude();
            ddw.b = latestPosition.getLatitude();
            djk djk = (djk) ank.a(djk.class);
            if (djk != null) {
                i2 = djk.m();
            }
            int i3 = i2 | 256;
            String str = "";
            if (djk != null) {
                str = djk.l();
            }
            if (!TextUtils.isEmpty(str)) {
                str.equals("1");
            }
            if (djk != null) {
                djk.g();
            }
            ddw.h = i3;
            ddw.g = str;
            ddw.i = "";
            HomeCompanyRequest homeCompanyRequest = new HomeCompanyRequest();
            homeCompanyRequest.d = String.valueOf(ddw.a);
            homeCompanyRequest.e = String.valueOf(ddw.b);
            homeCompanyRequest.f = String.valueOf(ddw.c);
            homeCompanyRequest.g = String.valueOf(ddw.d);
            homeCompanyRequest.h = String.valueOf(ddw.e);
            homeCompanyRequest.i = String.valueOf(ddw.f);
            homeCompanyRequest.j = ddw.g;
            homeCompanyRequest.k = String.valueOf(ddw.h);
            homeCompanyRequest.l = ddw.i;
            homeCompanyRequest.m = String.valueOf(ddw.j);
            homeCompanyRequest.n = String.valueOf(ddw.k);
            homeCompanyRequest.o = String.valueOf(ddw.l);
            homeCompanyRequest.p = String.valueOf(ddw.m);
            homeCompanyRequest.q = AEUtil.getNaviRouteVersion();
            NavigationRequestHolder.getInstance().sendHomeCompany(homeCompanyRequest, new CommuteTipsManager$3(this));
        }
    }

    public void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
        b(LocationInstrument.getInstance().getLatestPosition());
        a(12);
        a(13, (String) "4");
    }

    static /* synthetic */ void a(ddu ddu, ddx ddx) {
        String str;
        new StringBuilder("updateCommuteTipView: ").append(ddx);
        ddu.p = ddx.a;
        if (ddu.p == 1) {
            ddu.k.setImageResource(R.drawable.commute_tips_work);
            str = "上班";
        } else if (ddu.p == 2) {
            ddu.k.setImageResource(R.drawable.commute_tips_home);
            str = "回家";
        } else {
            return;
        }
        if (!TextUtils.isEmpty(ddx.b) && !TextUtils.isEmpty(ddx.d) && !TextUtils.isEmpty(ddx.c) && ddx.e != null && ddx.e.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(Token.SEPARATOR);
            sb.append(ddx.c);
            String sb2 = sb.toString();
            SpannableString spannableString = new SpannableString(sb2);
            spannableString.setSpan(new ForegroundColorSpan(-12417025), 2, sb2.length(), 18);
            ddu.l.setText(spannableString);
            TextView textView = ddu.m;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(ddx.b);
            sb3.append(Token.SEPARATOR);
            sb3.append(ddx.d);
            textView.setText(sb3.toString());
            ddu.n.setRainbowData(ddx.e);
            if (ddu.e != null && AMapPageUtil.isHomePage()) {
                if (!ddu.f()) {
                    ddu.b(LocationInstrument.getInstance().getLatestPosition());
                } else if (AMapPageUtil.isHomePage()) {
                    ddu.a = 12;
                    ddu.g.removeCallbacks(ddu.o);
                    ddu.o.a = LocationInstrument.getInstance().getLatestPosition();
                    ddu.g.postDelayed(ddu.o, 50);
                }
                ddu.a(12);
            }
        }
    }

    public static /* synthetic */ void b(ddu ddu, final ddx ddx) {
        if (ddx != null) {
            aho.a(new Runnable() {
                public final void run() {
                    ddu.a(ddu.this, ddx);
                }
            });
        }
    }

    static /* synthetic */ void a(ddu ddu, GeoPoint geoPoint) {
        AMapLog.i("CommuteTips", "tryShowCommuteTips-enter");
        if (ddu.a == 12) {
            if (ddu.f()) {
                ddu.o.a = geoPoint;
                ddu.g.postDelayed(ddu.o, 50);
                AMapLog.i("CommuteTips", "tryShowCommuteTips-delay");
                return;
            }
            ddu.b(geoPoint);
            AMapLog.i("CommuteTips", "tryShowCommuteTips-done");
        }
    }
}
