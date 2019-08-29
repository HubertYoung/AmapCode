package defpackage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.view.Real3DSwitchView;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: brt reason: default package */
/* compiled from: MapLayerManagerDialog */
public class brt implements OnClickListener {
    private RelativeLayout A;
    private FrameLayout B;
    private ImageView C;
    private ImageView D;
    private TextView E;
    private RelativeLayout F;
    private FrameLayout G;
    private ImageView H;
    private ImageView I;
    private TextView J;
    private RelativeLayout K;
    private FrameLayout L;
    private ImageView M;
    private ImageView N;
    private TextView O;
    private RelativeLayout P;
    private FrameLayout Q;
    private ImageView R;
    private ImageView S;
    private TextView T;
    private RelativeLayout U;
    private View V;
    private TextView W;
    private TextView X;
    private TextView Y;
    private ImageView Z;
    public btn a;
    private bsg aa;
    /* access modifiers changed from: private */
    public defpackage.awo.a ab = new defpackage.awo.a() {
        public final void a() {
            brt.this.c();
        }
    };
    View b;
    public View c;
    protected ImageView d;
    protected ImageView e;
    protected ImageView f;
    protected cde g;
    protected bro h;
    protected ArrayList<LayerItem> i = new ArrayList<>();
    protected View j;
    /* access modifiers changed from: 0000 */
    public CheckBox k;
    private int l = -1;
    private int m = -1;
    private View n;
    /* access modifiers changed from: private */
    public int o = -1;
    private RelativeLayout p;
    private RelativeLayout q;
    private ImageView r;
    private ImageView s;
    private ceo t;
    private LinearLayout u;
    private RelativeLayout v;
    private FrameLayout w;
    private ImageView x;
    private ImageView y;
    private TextView z;

    /* renamed from: brt$a */
    /* compiled from: MapLayerManagerDialog */
    static class a extends btn {
        public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            return true;
        }

        protected a(Context context) {
            super(context);
        }
    }

    public brt(cde cde, bro bro) {
        this.g = cde;
        this.h = bro;
    }

    public final void a(int i2, int i3) {
        this.l = i2;
        int d2 = ags.d(this.g.a());
        if ((DoNotUseTool.getActivity().getWindow().getAttributes().flags & 1024) == 1024) {
            this.m = i3 - d2;
        } else {
            this.m = i3;
        }
    }

    public final void c() {
        b();
    }

    /* access modifiers changed from: protected */
    public int a() {
        return R.layout.map_mapmode_popup;
    }

    /* access modifiers changed from: protected */
    public void b(Window window) {
        this.d = (ImageView) window.findViewById(R.id.check_defaultmode);
        this.e = (ImageView) window.findViewById(R.id.check_satellitemode);
        this.f = (ImageView) window.findViewById(R.id.check_busmode);
        window.findViewById(R.id.linearLayoutMapModePoputRoot).setOnClickListener(this);
        this.d.setOnClickListener(this);
        this.e.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.c = window.findViewById(R.id.btnClosePopup);
        this.c.setOnClickListener(this);
        this.n = window.findViewById(R.id.view_divider);
        this.q = (RelativeLayout) window.findViewById(R.id.map_layer_2d_mode_rl);
        this.q.setOnClickListener(this);
        this.r = (ImageView) window.findViewById(R.id.map_layer_2d_mode_iv);
        this.p = (RelativeLayout) window.findViewById(R.id.map_layer_3d_mode_rl);
        this.p.setOnClickListener(this);
        this.s = (ImageView) window.findViewById(R.id.map_layer_3d_mode_iv);
        this.U = (RelativeLayout) window.findViewById(R.id.traffic_fav_rl);
        this.V = window.findViewById(R.id.traffic_fav_view_divider);
        c(window);
        d(window);
    }

    /* access modifiers changed from: protected */
    public void c(Window window) {
        final bid pageContext = AMapPageUtil.getPageContext();
        apr apr = (apr) defpackage.esb.a.a.a(apr.class);
        boolean a2 = apr != null ? apr.a(pageContext) : false;
        View findViewById = window.findViewById(R.id.real_scene);
        View findViewById2 = window.findViewById(R.id.maplayer_list_wrapper_realscene_divider);
        if (a2) {
            findViewById2.setVisibility(0);
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    pageContext.startPage((String) "amap.life.action.RealSceneMainFragment", (PageBundle) null);
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("status", 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogManager.actionLogV2(LogConstant.PAGE_ID_NEW_MAIN_MAP, "B009", jSONObject);
                }
            });
            return;
        }
        findViewById2.setVisibility(8);
        findViewById.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void d(Window window) {
        this.j = window.findViewById(R.id.maplayer_list_wrapper_ll_divider);
        this.u = (LinearLayout) window.findViewById(R.id.maplayer_list_wrapper_ll);
        this.v = (RelativeLayout) window.findViewById(R.id.maplayer_list_item_0_rl);
        this.w = (FrameLayout) window.findViewById(R.id.maplayer_list_item_0_fl);
        this.w.setOnClickListener(this);
        this.x = (ImageView) window.findViewById(R.id.maplayer_list_item_0_iv);
        this.y = (ImageView) window.findViewById(R.id.maplayer_list_item_0_iv_checked);
        this.z = (TextView) window.findViewById(R.id.maplayer_list_item_0_tv);
        this.A = (RelativeLayout) window.findViewById(R.id.maplayer_list_item_1_rl);
        this.B = (FrameLayout) window.findViewById(R.id.maplayer_list_item_1_fl);
        this.B.setOnClickListener(this);
        this.C = (ImageView) window.findViewById(R.id.maplayer_list_item_1_iv);
        this.D = (ImageView) window.findViewById(R.id.maplayer_list_item_1_iv_checked);
        this.E = (TextView) window.findViewById(R.id.maplayer_list_item_1_tv);
        this.F = (RelativeLayout) window.findViewById(R.id.maplayer_list_item_2_rl);
        this.G = (FrameLayout) window.findViewById(R.id.maplayer_list_item_2_fl);
        this.G.setOnClickListener(this);
        this.H = (ImageView) window.findViewById(R.id.maplayer_list_item_2_iv);
        this.I = (ImageView) window.findViewById(R.id.maplayer_list_item_2_iv_checked);
        this.J = (TextView) window.findViewById(R.id.maplayer_list_item_2_tv);
        this.K = (RelativeLayout) window.findViewById(R.id.maplayer_list_item_3_rl);
        this.L = (FrameLayout) window.findViewById(R.id.maplayer_list_item_3_fl);
        this.L.setOnClickListener(this);
        this.M = (ImageView) window.findViewById(R.id.maplayer_list_item_3_iv);
        this.N = (ImageView) window.findViewById(R.id.maplayer_list_item_3_iv_checked);
        this.O = (TextView) window.findViewById(R.id.maplayer_list_item_3_tv);
        this.P = (RelativeLayout) window.findViewById(R.id.maplayer_list_item_4_rl);
        this.Q = (FrameLayout) window.findViewById(R.id.maplayer_list_item_4_fl);
        this.Q.setOnClickListener(this);
        this.R = (ImageView) window.findViewById(R.id.maplayer_list_item_4_iv);
        this.S = (ImageView) window.findViewById(R.id.maplayer_list_item_4_iv_checked);
        this.T = (TextView) window.findViewById(R.id.maplayer_list_item_4_tv);
        this.u.setVisibility(8);
        this.j.setVisibility(8);
    }

    private boolean a(int i2, boolean z2) {
        if (i2 >= 5 || this.i == null || this.i.size() <= 0) {
            return false;
        }
        if (z2) {
            if (!(this.i.get(i2).getLayer_id() == 0 || this.i.get(i2).getLevel() == 0)) {
                this.h.getMapView().e(this.i.get(i2).getLevel());
            }
            String str = "";
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager != null) {
                bty mapView = mapManager.getMapView();
                if (!(mapView == null || mapView.n() == null)) {
                    str = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getAdCode());
                }
            }
            if (TextUtils.isEmpty(str)) {
                str = "";
            }
            JSONObject jSONObject = new JSONObject();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(this.i.get(i2).getLayer_id());
                jSONObject.put(TrafficUtil.KEYWORD, sb.toString());
                jSONObject.put("type", "0");
                jSONObject.put(AutoJsonUtils.JSON_ADCODE, str);
            } catch (JSONException e2) {
                kf.a((Throwable) e2);
            }
            LogManager.actionLogV2("P00188", "B004", jSONObject);
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            if (awo != null) {
                if (this.i.get(i2).getControl_status() == 0) {
                    awo.a(this.i.get(i2).getItem_id(), true);
                }
                ArrayList arrayList = new ArrayList();
                if (!TextUtils.isEmpty(this.i.get(i2).getData())) {
                    try {
                        arrayList.add(this.i.get(i2).getData().getBytes("UTF-8"));
                        b(arrayList);
                        awo.b(this.i.get(i2).getLayer_id());
                    } catch (UnsupportedEncodingException e3) {
                        e3.printStackTrace();
                    }
                }
                if (!TextUtils.isEmpty(this.i.get(i2).getToast())) {
                    ToastHelper.showLongToast(this.i.get(i2).getToast());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.i.get(i2).getName());
                    sb2.append("图层已开启");
                    ToastHelper.showToast(sb2.toString());
                }
            }
            return true;
        }
        awo awo2 = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo2 == null) {
            return false;
        }
        if (awo2.e(this.i.get(i2).getItem_id())) {
            if (this.i.get(i2).getControl_status() == 0) {
                awo2.a(this.i.get(i2).getItem_id(), false);
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(Integer.valueOf(this.i.get(i2).getLayer_id()));
            a(arrayList2);
            awo2.c(this.i.get(i2).getLayer_id());
        }
        String str2 = "";
        MapManager mapManager2 = DoNotUseTool.getMapManager();
        if (mapManager2 != null) {
            bty mapView2 = mapManager2.getMapView();
            if (!(mapView2 == null || mapView2.n() == null)) {
                str2 = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView2.n()).getAdCode());
            }
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.i.get(i2).getLayer_id());
            jSONObject2.put(TrafficUtil.KEYWORD, sb3.toString());
            jSONObject2.put("type", "1");
            jSONObject2.put(AutoJsonUtils.JSON_ADCODE, str2);
        } catch (JSONException e4) {
            kf.a((Throwable) e4);
        }
        LogManager.actionLogV2("P00188", "B004", jSONObject2);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.i.get(i2).getName());
        sb4.append("图层已关闭");
        ToastHelper.showToast(sb4.toString());
        return true;
    }

    public final void a(String str, int i2) {
        if (!TextUtils.isEmpty(str)) {
            this.c.setVisibility(8);
            d();
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.putExtra("owner", "banner");
            String str2 = "";
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager != null) {
                bty mapView = mapManager.getMapView();
                if (!(mapView == null || mapView.n() == null)) {
                    str2 = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getAdCode());
                }
                if (TextUtils.isEmpty(str2)) {
                    str2 = "";
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(TrafficUtil.KEYWORD, String.valueOf(i2));
                    jSONObject.put(AutoJsonUtils.JSON_ADCODE, str2);
                } catch (JSONException e2) {
                    kf.a((Throwable) e2);
                }
                LogManager.actionLogV2("P00188", "B007", jSONObject);
                DoNotUseTool.startScheme(intent);
            }
        }
    }

    public final void d() {
        if (this.a != null) {
            this.a.dismiss();
            this.a = null;
            cdd.n().e();
            bin.a.d(null);
        }
    }

    public void onClick(View view) {
        int i2;
        if (this.g != null) {
            Resources resources = AMapAppGlobal.getApplication().getResources();
            int id = view.getId();
            if (id == R.id.check_defaultmode) {
                this.aa.a();
                bin.a.d((String) "101", 0);
                this.h.getMapView().q(true);
                if (this.t != null) {
                    i2 = this.t.a;
                } else {
                    i2 = 1;
                }
                a(1);
                c(false);
                d(false);
                b(true);
                if (i2 == 4) {
                    if (!(bin.a.p("101") == 0)) {
                        bin.a.d((String) "101", 0);
                    }
                    this.h.getMapView().a(0, 0, 1);
                    ku.a().c("NaviMonitor", "[MapLayerManagerDialog]setCarMode#setMapModeAndStyle");
                }
                if (this.h.getMapView().p(false) == 0) {
                    this.h.getMapView().q(true);
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(resources.getString(R.string.map_layer_mode), resources.getString(R.string.map_layer_standard_map));
                } catch (JSONException e2) {
                    kf.a((Throwable) e2);
                }
                LogManager.actionLogV2("P00001", "B009", jSONObject);
                this.h.notifyMapModeChange(0);
            } else if (id == R.id.check_satellitemode) {
                this.aa.a();
                bin.a.d((String) "101", 1);
                a(2);
                b(false);
                c(true);
                d(false);
                this.h.getMapView().q(false);
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put(resources.getString(R.string.map_layer_mode), resources.getString(R.string.map_layer_satellite_map));
                } catch (JSONException e3) {
                    kf.a((Throwable) e3);
                }
                LogManager.actionLogV2("P00001", "B011", jSONObject2);
                this.h.notifyMapModeChange(1);
            } else if (id == R.id.check_busmode) {
                this.aa.b();
                bin.a.d((String) "101", 2);
                a(3);
                b(false);
                c(false);
                d(true);
                this.h.getMapView().q(false);
                JSONObject jSONObject3 = new JSONObject();
                try {
                    jSONObject3.put(resources.getString(R.string.map_layer_mode), resources.getString(R.string.map_layer_bus_map));
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B023", jSONObject3);
                this.h.notifyMapModeChange(2);
            } else if (id == R.id.map_layer_2d_mode_rl) {
                JSONObject jSONObject4 = new JSONObject();
                try {
                    jSONObject4.put(resources.getString(R.string.map_layer_map_view), resources.getString(R.string.map_layer_2d_view));
                } catch (JSONException e5) {
                    e5.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B009", jSONObject4);
                this.r.setImageResource(R.drawable.readio_btn_on);
                this.s.setImageResource(R.drawable.readio_btn_off);
                if (bin.a.o("201")) {
                    this.h.getMapView().e(true);
                } else {
                    this.h.getMapView().B();
                }
                this.h.getMapView().c((Runnable) new Runnable() {
                    public final void run() {
                        brt.this.h.getMapView().a(500, -9999.0f, -9999, 0, -9999, -9999);
                    }
                });
                e(false);
            } else if (id == R.id.map_layer_3d_mode_rl) {
                JSONObject jSONObject5 = new JSONObject();
                try {
                    jSONObject5.put(resources.getString(R.string.map_layer_map_view), resources.getString(R.string.map_layer_3d_view));
                } catch (JSONException e6) {
                    e6.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B010", jSONObject5);
                this.r.setImageResource(R.drawable.readio_btn_off);
                this.s.setImageResource(R.drawable.readio_btn_on);
                e(true);
                this.h.getMapView().B();
                this.h.getMapView().c((Runnable) new Runnable() {
                    public final void run() {
                        brt.this.h.getMapView().a(500, -9999.0f, -9999, 55, -9999, -9999);
                    }
                });
            } else if (id == R.id.linearLayoutMapModePoputRoot || id == R.id.btnClosePopup) {
                this.c.setVisibility(8);
                d();
            } else {
                if (id == R.id.maplayer_list_item_0_fl) {
                    if (this.i.get(0).getLayer_type() == 2 || this.i.get(0).getLayer_type() == 3) {
                        a(this.i.get(0).getAction_url(), this.i.get(0).getLayer_id());
                    } else if (this.i.get(0).getLayer_type() == 1) {
                        if (this.y.getVisibility() == 8) {
                            if (a(0, true)) {
                                this.z.setTextColor(Color.rgb(51, 167, 255));
                                this.y.setVisibility(0);
                            }
                        } else if (a(0, false)) {
                            this.z.setTextColor(Color.rgb(102, 102, 102));
                            this.y.setVisibility(8);
                        }
                    }
                } else if (id == R.id.maplayer_list_item_1_fl) {
                    if (this.i.get(1).getLayer_type() == 2 || this.i.get(1).getLayer_type() == 3) {
                        a(this.i.get(1).getAction_url(), this.i.get(1).getLayer_id());
                    } else if (this.i.get(1).getLayer_type() == 1) {
                        if (this.D.getVisibility() == 8) {
                            if (a(1, true)) {
                                this.E.setTextColor(Color.rgb(51, 167, 255));
                                this.D.setVisibility(0);
                            }
                        } else if (a(1, false)) {
                            this.E.setTextColor(Color.rgb(102, 102, 102));
                            this.D.setVisibility(8);
                        }
                    }
                } else if (id == R.id.maplayer_list_item_2_fl) {
                    if (this.i.get(2).getLayer_type() == 2 || this.i.get(2).getLayer_type() == 3) {
                        a(this.i.get(2).getAction_url(), this.i.get(2).getLayer_id());
                    } else if (this.i.get(2).getLayer_type() == 1) {
                        if (this.I.getVisibility() == 8) {
                            if (a(2, true)) {
                                this.J.setTextColor(Color.rgb(51, 167, 255));
                                this.I.setVisibility(0);
                            }
                        } else if (a(2, false)) {
                            this.J.setTextColor(Color.rgb(102, 102, 102));
                            this.I.setVisibility(8);
                        }
                    }
                } else if (id == R.id.maplayer_list_item_3_fl) {
                    if (this.i.get(3).getLayer_type() == 2 || this.i.get(3).getLayer_type() == 3) {
                        a(this.i.get(3).getAction_url(), this.i.get(3).getLayer_id());
                    } else if (this.i.get(3).getLayer_type() == 1) {
                        if (this.N.getVisibility() == 8) {
                            if (a(3, true)) {
                                this.O.setTextColor(Color.rgb(51, 167, 255));
                                this.N.setVisibility(0);
                            }
                        } else if (a(3, false)) {
                            this.O.setTextColor(Color.rgb(102, 102, 102));
                            this.N.setVisibility(8);
                        }
                    }
                } else if (id == R.id.maplayer_list_item_4_fl) {
                    if (this.i.get(4).getLayer_type() == 2 || this.i.get(4).getLayer_type() == 3) {
                        a(this.i.get(4).getAction_url(), this.i.get(4).getLayer_id());
                    } else if (this.i.get(4).getLayer_type() == 1) {
                        if (this.S.getVisibility() == 8) {
                            if (a(4, true)) {
                                this.T.setTextColor(Color.rgb(51, 167, 255));
                                this.S.setVisibility(0);
                            }
                        } else if (a(4, false)) {
                            this.T.setTextColor(Color.rgb(102, 102, 102));
                            this.S.setVisibility(8);
                        }
                    }
                }
            }
        }
    }

    public void a(Window window) {
        int i2 = (this.o & 1) > 0 ? 0 : 4;
        int i3 = (this.o & 4) > 0 ? 0 : 4;
        RelativeLayout relativeLayout = (RelativeLayout) window.findViewById(R.id.check_traffic_wrapper);
        relativeLayout.setVisibility(i2);
        RelativeLayout relativeLayout2 = (RelativeLayout) window.findViewById(R.id.check_favorites_wrapper);
        relativeLayout2.setVisibility(i3);
        if (i2 == 4 && i3 == 4) {
            this.n.setVisibility(8);
            this.U.setVisibility(8);
        } else if (i2 == 4 || i3 == 4) {
            this.V.setVisibility(4);
            this.n.setVisibility(0);
            this.U.setVisibility(0);
            if (i2 == 4) {
                LayoutParams layoutParams = (LayoutParams) relativeLayout2.getLayoutParams();
                LayoutParams layoutParams2 = (LayoutParams) relativeLayout.getLayoutParams();
                layoutParams.addRule(1, 0);
                layoutParams2.addRule(0, 0);
                layoutParams.addRule(0, R.id.traffic_fav_view_divider);
                layoutParams2.addRule(1, R.id.traffic_fav_view_divider);
            }
        } else {
            this.V.setVisibility(0);
            this.n.setVisibility(0);
            this.U.setVisibility(0);
        }
        final MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        final CheckBox checkBox = (CheckBox) window.findViewById(R.id.check_favorites);
        if (bin.a.o("104")) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setClickable(false);
        window.findViewById(R.id.check_favorites_ll).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                int i;
                int i2;
                int i3;
                int i4;
                boolean z = !checkBox.isChecked();
                checkBox.setChecked(z);
                boolean z2 = false;
                if (z) {
                    bin.a.d((String) "104", 1);
                } else {
                    bin.a.d((String) "104", 0);
                }
                brn brn = (brn) ank.a(brn.class);
                FavoritePOI favoritePOI = null;
                FavoriteLayer g = brn != null ? brn.g() : null;
                if (g != null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("type", z ? "1" : "0");
                    } catch (JSONException e) {
                        kf.a((Throwable) e);
                    }
                    LogManager.actionLogV2("P00001", "B013", jSONObject);
                    if (z) {
                        if (bin.a.S()) {
                            z2 = true;
                        }
                        if (z2 || brn.h() <= 1) {
                            bin.a.T();
                            brn.d();
                            AMapLog.i("zyl", "isChecked---reloadAllFavorites");
                        }
                        g.setVisible(true);
                        g.setClickable(true);
                        g.setMoveToFocus(true);
                        brn.b();
                        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(brt.this.h.getMapView().n());
                        com com2 = (com) ank.a(com.class);
                        if (com2 != null) {
                            cop b2 = com2.b(com2.a());
                            if (b2 != null) {
                                favoritePOI = b2.b();
                            }
                        }
                        Resources resources = AMapAppGlobal.getApplication().getResources();
                        if (favoritePOI == null || favoritePOI.getPoint() == null || glGeoPoint2GeoPoint == null) {
                            ToastHelper.showToast(resources.getString(R.string.map_layer_no_saved_point));
                            return;
                        }
                        int i5 = favoritePOI.getPoint().x;
                        int i6 = favoritePOI.getPoint().y;
                        int i7 = (glGeoPoint2GeoPoint.x << 1) - i5;
                        int i8 = (glGeoPoint2GeoPoint.y << 1) - i6;
                        if (i7 > i5) {
                            i2 = i5;
                            i = i7;
                        } else {
                            i = i5;
                            i2 = i7;
                        }
                        if (i8 > i6) {
                            i3 = i8;
                            i4 = i6;
                        } else {
                            i4 = i8;
                            i3 = i6;
                        }
                        brt.this.h.getMapView().c(brt.this.h.getMapView().a(i2, i4, i, i3, ags.a(checkBox.getContext()).width(), (int) (((float) ags.a(checkBox.getContext()).height()) - (checkBox.getContext().getResources().getDisplayMetrics().density * 278.0f))));
                        ToastHelper.showToast(resources.getString(R.string.map_layer_saved_layer_is_opened));
                        return;
                    }
                    g.setFavoriteItemVisible(false);
                    if (!z && g.getFocusItem() != null) {
                        brt.this.h.getOverlayManager().dimissTips();
                    }
                    ToastHelper.showToast(AMapAppGlobal.getApplication().getResources().getString(R.string.map_layer_saved_layer_is_closed));
                    if (brt.this.h != null) {
                        IOverlayManager overlayManager = brt.this.h.getOverlayManager();
                        if (!(overlayManager == null || overlayManager.getDeepInfoOverlayManager() == null || g.getFocusItem() == null)) {
                            overlayManager.getDeepInfoOverlayManager().a();
                        }
                    }
                }
            }
        });
        boolean o2 = bin.a.o("103");
        this.k = (CheckBox) window.findViewById(R.id.check_traffic);
        this.k.setChecked(o2);
        this.k.setClickable(false);
        bin.a.d(new biy() {
            public final void updateSuccess() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public final void run() {
                        if ((brt.this.o & 1) > 0) {
                            boolean o = bin.a.o("103");
                            if (o != brt.this.k.isChecked()) {
                                brt.this.k.setChecked(o);
                                brt.this.k.setClickable(false);
                                awo awo = (awo) defpackage.esb.a.a.a(awo.class);
                                if (awo != null) {
                                    awo.b(o);
                                }
                            }
                        }
                    }
                });
            }
        });
        window.findViewById(R.id.check_traffic_ll).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                boolean z = !brt.this.k.isChecked();
                brt.this.k.setChecked(z);
                boolean booleanValue = mapSharePreference.getBooleanValue("confirmTrafficReport", false);
                if (!z || booleanValue) {
                    brt.a(z);
                } else {
                    cse.a(brt.this.g.a(), new defpackage.cse.a() {
                        final /* synthetic */ boolean a = true;

                        public final void a() {
                            brt.a(true);
                            mapSharePreference.putBooleanValue("confirmTrafficReport", true);
                        }

                        public final void b() {
                            if (this.a) {
                                brt.this.k.setChecked(false);
                                brt.a(false);
                            }
                        }
                    });
                }
            }
        });
        a(bin.a.p("101") + 1);
        if (this.h.getMapView().J() != 0.0f) {
            this.r.setImageResource(R.drawable.readio_btn_off);
            this.s.setImageResource(R.drawable.readio_btn_on);
            return;
        }
        this.r.setImageResource(R.drawable.readio_btn_on);
        this.s.setImageResource(R.drawable.readio_btn_off);
    }

    /* access modifiers changed from: protected */
    public final void a(int i2) {
        switch (i2) {
            case 1:
                this.Z.setVisibility(0);
                this.d.setSelected(true);
                this.e.setSelected(false);
                this.f.setSelected(false);
                this.d.setClickable(false);
                this.e.setClickable(true);
                this.f.setClickable(true);
                this.Y.setSelected(true);
                this.W.setSelected(false);
                this.X.setSelected(false);
                return;
            case 2:
                this.Z.setVisibility(8);
                this.d.setSelected(false);
                this.e.setSelected(true);
                this.f.setSelected(false);
                this.d.setClickable(true);
                this.e.setClickable(false);
                this.f.setClickable(true);
                this.Y.setSelected(false);
                this.W.setSelected(false);
                this.X.setSelected(true);
                return;
            case 3:
                this.Z.setVisibility(8);
                this.d.setSelected(false);
                this.e.setSelected(false);
                this.f.setSelected(true);
                this.d.setClickable(true);
                this.e.setClickable(true);
                this.f.setClickable(false);
                this.Y.setSelected(false);
                this.W.setSelected(true);
                this.X.setSelected(false);
                break;
        }
    }

    private void b(boolean z2) {
        new MapSharePreference(SharePreferenceName.SharedPreferences);
        boolean z3 = true;
        if (z2) {
            bin.a.d((String) "101", 0);
        }
        if (z2) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null || !(pageContext instanceof btz)) {
                e();
                return;
            }
            if (!(pageContext instanceof MapBasePage) || !((MapBasePage) pageContext).onMapLayerSetDefaultMode()) {
                z3 = false;
            }
            if (!z3) {
                this.h.getMapView().a(0, 0, 0);
            }
        }
    }

    private void c(boolean z2) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        if (mapSharePreference.getBooleanValue("satellite", false) != z2) {
            mapSharePreference.putBooleanValue("satellite", z2);
        }
        if (z2) {
            bty mapView = this.h.getMapView();
            if (mapView != null) {
                mapView.a(1, 0, 0);
            }
        }
    }

    private static void e(boolean z2) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        if (mapSharePreference.getBooleanValue("is3DMode", false) != z2) {
            mapSharePreference.putBooleanValue("is3DMode", z2);
        }
    }

    private void c(ArrayList<Integer> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                int intValue = arrayList.get(i2).intValue();
                if (this.h != null) {
                    bty mapView = this.h.getMapView();
                    if (mapView != null) {
                        mapView.l(intValue);
                    }
                }
            }
        }
    }

    public final void a(ArrayList<Integer> arrayList) {
        c(arrayList);
    }

    private void d(ArrayList<byte[]> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                byte[] bArr = arrayList.get(i2);
                if (!(bArr == null || bArr.length <= 0 || this.h == null)) {
                    bty mapView = this.h.getMapView();
                    if (mapView != null) {
                        mapView.a(bArr);
                    }
                }
            }
        }
    }

    public final void b(ArrayList<byte[]> arrayList) {
        d(arrayList);
    }

    private void e() {
        bty mapView = this.h.getMapView();
        if (mapView != null) {
            mapView.a(0, 0, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            ArrayList<LayerItem> i2 = awo.i();
            ArrayList<LayerItem> arrayList = new ArrayList<>();
            Iterator<LayerItem> it = i2.iterator();
            while (it.hasNext()) {
                LayerItem next = it.next();
                if (next.getSwitch_status() != 2) {
                    arrayList.add(next);
                }
            }
            this.i = arrayList;
            if (this.i != null && this.i.size() > 0) {
                Collections.sort(this.i, new Comparator<LayerItem>() {
                    public final /* synthetic */ int compare(Object obj, Object obj2) {
                        return a((LayerItem) obj, (LayerItem) obj2);
                    }

                    private static int a(LayerItem layerItem, LayerItem layerItem2) {
                        try {
                            int order = layerItem.getOrder();
                            int order2 = layerItem2.getOrder();
                            if (order < order2) {
                                return -1;
                            }
                            return order > order2 ? 1 : 0;
                        } catch (NumberFormatException unused) {
                            return -1;
                        }
                    }
                });
            }
            if (this.i == null || this.i.size() <= 0) {
                this.u.setVisibility(8);
                this.j.setVisibility(8);
            } else {
                ks ksVar = new ks(22.0f, AMapAppGlobal.getApplication().getResources().getDisplayMetrics());
                int size = this.i.size();
                if (size > 5) {
                    size = 5;
                }
                for (int i3 = 0; i3 < size; i3++) {
                    if (i3 == 0) {
                        this.v.setVisibility(0);
                        ko.a(this.x, this.i.get(i3).getIcon(), ksVar, R.drawable.maplayer_list_def_pic);
                        this.z.setText(this.i.get(i3).getName());
                        if (this.i.get(i3).getLayer_type() == 2 || this.i.get(i3).getLayer_type() == 3) {
                            this.y.setVisibility(8);
                            this.z.setTextColor(Color.rgb(102, 102, 102));
                        } else if (this.i.get(i3).getLayer_type() == 1) {
                            if (awo.e(this.i.get(i3).getItem_id())) {
                                this.z.setTextColor(Color.rgb(51, 167, 255));
                                this.y.setVisibility(0);
                            } else {
                                this.z.setTextColor(Color.rgb(102, 102, 102));
                                this.y.setVisibility(8);
                            }
                        }
                    } else if (i3 == 1) {
                        this.A.setVisibility(0);
                        ko.a(this.C, this.i.get(i3).getIcon(), ksVar, R.drawable.maplayer_list_def_pic);
                        this.E.setText(this.i.get(i3).getName());
                        if (this.i.get(i3).getLayer_type() == 2 || this.i.get(i3).getLayer_type() == 3) {
                            this.E.setTextColor(Color.rgb(102, 102, 102));
                            this.D.setVisibility(8);
                        } else if (this.i.get(i3).getLayer_type() == 1) {
                            if (awo.e(this.i.get(i3).getItem_id())) {
                                this.E.setTextColor(Color.rgb(51, 167, 255));
                                this.D.setVisibility(0);
                            } else {
                                this.E.setTextColor(Color.rgb(102, 102, 102));
                                this.D.setVisibility(8);
                            }
                        }
                    } else if (i3 == 2) {
                        this.F.setVisibility(0);
                        ko.a(this.H, this.i.get(i3).getIcon(), ksVar, R.drawable.maplayer_list_def_pic);
                        this.J.setText(this.i.get(i3).getName());
                        if (this.i.get(i3).getLayer_type() == 2 || this.i.get(i3).getLayer_type() == 3) {
                            this.J.setTextColor(Color.rgb(102, 102, 102));
                            this.I.setVisibility(8);
                        } else if (this.i.get(i3).getLayer_type() == 1) {
                            if (awo.e(this.i.get(i3).getItem_id())) {
                                this.J.setTextColor(Color.rgb(51, 167, 255));
                                this.I.setVisibility(0);
                            } else {
                                this.J.setTextColor(Color.rgb(102, 102, 102));
                                this.I.setVisibility(8);
                            }
                        }
                    } else if (i3 == 3) {
                        this.K.setVisibility(0);
                        ko.a(this.M, this.i.get(i3).getIcon(), ksVar, R.drawable.maplayer_list_def_pic);
                        this.O.setText(this.i.get(i3).getName());
                        if (this.i.get(i3).getLayer_type() == 2 || this.i.get(i3).getLayer_type() == 3) {
                            this.O.setTextColor(Color.rgb(102, 102, 102));
                            this.N.setVisibility(8);
                        } else if (this.i.get(i3).getLayer_type() == 1) {
                            if (awo.e(this.i.get(i3).getItem_id())) {
                                this.O.setTextColor(Color.rgb(51, 167, 255));
                                this.N.setVisibility(0);
                            } else {
                                this.O.setTextColor(Color.rgb(102, 102, 102));
                                this.N.setVisibility(8);
                            }
                        }
                    } else if (i3 == 4) {
                        this.P.setVisibility(0);
                        ko.a(this.R, this.i.get(i3).getIcon(), ksVar, R.drawable.maplayer_list_def_pic);
                        this.T.setText(this.i.get(i3).getName());
                        if (this.i.get(i3).getLayer_type() == 2 || this.i.get(i3).getLayer_type() == 3) {
                            this.T.setTextColor(Color.rgb(102, 102, 102));
                            this.S.setVisibility(8);
                        } else if (this.i.get(i3).getLayer_type() == 1) {
                            if (awo.e(this.i.get(i3).getItem_id())) {
                                this.T.setTextColor(Color.rgb(51, 167, 255));
                                this.S.setVisibility(0);
                            } else {
                                this.T.setTextColor(Color.rgb(102, 102, 102));
                                this.S.setVisibility(8);
                            }
                        }
                    }
                }
                while (size < 5) {
                    if (size == 0) {
                        this.v.setVisibility(4);
                    } else if (size == 1) {
                        this.A.setVisibility(4);
                    } else if (size == 2) {
                        this.F.setVisibility(4);
                    } else if (size == 3) {
                        this.K.setVisibility(4);
                    } else if (size == 4) {
                        this.P.setVisibility(4);
                    }
                    size++;
                }
                this.u.setVisibility(0);
                this.j.setVisibility(0);
            }
        }
    }

    public final void a(Context context, int i2, final ceo ceo) {
        bin.a.R();
        if (this.a == null) {
            this.a = new a(context);
        }
        this.a.setCanceledOnTouchOutside(true);
        this.a.setCancelable(true);
        this.a.setOnShowListener(new OnShowListener() {
            public final void onShow(DialogInterface dialogInterface) {
                if (ceo.a == 5) {
                    brt brt = brt.this;
                    if (brt.b != null) {
                        brt.b.post(new Runnable() {
                            public final void run() {
                                brt.this.b();
                            }
                        });
                    }
                    awo awo = (awo) defpackage.esb.a.a.a(awo.class);
                    if (awo != null) {
                        awo.a(brt.this.ab);
                        awo.g();
                    }
                }
            }
        });
        this.a.show();
        Window window = this.a.getWindow();
        window.setContentView(a());
        this.b = window.findViewById(R.id.mapmode_popup);
        RelativeLayout relativeLayout = (RelativeLayout) window.findViewById(R.id.map_mapmode_popup_content_rl);
        Resources resources = AMapAppGlobal.getApplication().getResources();
        int i3 = this.m;
        int i4 = 20;
        if (resources != null) {
            i4 = resources.getDimensionPixelSize(R.dimen.maplayer_popup_root_bottom_margin);
        }
        ((MarginLayoutParams) relativeLayout.getLayoutParams()).setMargins(0, i3, 0, i4);
        window.setLayout(-1, -1);
        WindowManager.LayoutParams attributes = window.getAttributes();
        int d2 = ags.d(context);
        int height = ags.a(context).height();
        attributes.gravity = 53;
        attributes.height = height - d2;
        this.a.onWindowAttributesChanged(attributes);
        this.o = i2;
        this.t = ceo;
        b(window);
        this.W = (TextView) window.findViewById(R.id.tv_map_mode_bus_desc);
        this.Y = (TextView) window.findViewById(R.id.tv_map_mode_normal_desc);
        this.X = (TextView) window.findViewById(R.id.tv_map_mode_satellite_desc);
        this.Z = (ImageView) window.findViewById(R.id.check_defaultmode_check_iv);
        Real3DSwitchView real3DSwitchView = (Real3DSwitchView) window.findViewById(R.id.viewReal3DSwitch);
        if (real3DSwitchView != null) {
            if (a() == R.layout.map_mapmode_popup) {
                bsg bsg = new bsg(real3DSwitchView, this.g, this.h, this, window.findViewById(R.id.viewTopDividerOnReal3DSwitch), window.findViewById(R.id.view_divider));
                this.aa = bsg;
            } else {
                bsg bsg2 = new bsg(real3DSwitchView, this.g, this.h, this, window.findViewById(R.id.viewTopDividerOnReal3DSwitch), window.findViewById(R.id.maplayer_list_wrapper_ll_divider));
                this.aa = bsg2;
            }
            real3DSwitchView.setVisibility(8);
        }
        a(window);
    }

    private void d(boolean z2) {
        if ((bin.a.p("101") == 2) != z2 && z2) {
            bin.a.d((String) "101", 2);
        }
        bty mapView = this.h.getMapView();
        if (mapView != null) {
            if (z2) {
                mapView.a(2, 0, 0);
            } else {
                mapView.a(false);
            }
        }
    }

    public static void a(boolean z2) {
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null && z2 != awo.d()) {
            awo.b(z2);
            Resources resources = AMapAppGlobal.getApplication().getResources();
            boolean d2 = awo.d();
            if (d2) {
                ToastHelper.showToast(resources.getString(R.string.map_layer_traffic_layer_is_opened));
            } else {
                ToastHelper.showToast(resources.getString(R.string.map_layer_traffic_layer_is_closed));
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", d2 ? "1" : "0");
                jSONObject.put("status", 1);
            } catch (JSONException e2) {
                kf.a((Throwable) e2);
            }
            LogManager.actionLogV2("P00001", "B047", jSONObject);
        }
    }
}
