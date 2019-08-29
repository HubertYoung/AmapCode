package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.view.OpenLayerItemView;
import com.autonavi.map.core.view.OpenLayerView;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: brs reason: default package */
/* compiled from: MapLayerDialog */
public final class brs extends brt {
    private bsf l;
    private OpenLayerView m;
    /* access modifiers changed from: private */
    public OpenLayerItemView n;
    private OpenLayerItemView o;
    private OpenLayerItemView p;
    private View q;
    private MapSharePreference r = new MapSharePreference((String) "MapLayer");
    /* access modifiers changed from: private */
    public final MapSharePreference s = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private OnClickListener t = new OnClickListener() {
        public final void onClick(View view) {
            LayerItem layerItem = (LayerItem) view.getTag();
            OpenLayerItemView openLayerItemView = (OpenLayerItemView) view;
            if (layerItem != null) {
                if (layerItem.getLayer_id() != 0 && layerItem.getControl_status() == 0) {
                    awo awo = (awo) a.a.a(awo.class);
                    if (awo != null) {
                        awo.a(layerItem.getLayer_id(), false);
                    }
                }
                openLayerItemView.setNew(false);
                switch (layerItem.getLayer_type()) {
                    case 1:
                        boolean isSelected = openLayerItemView.isSelected();
                        brs.this.a(layerItem, !isSelected);
                        openLayerItemView.setSelected(!isSelected);
                        break;
                    case 2:
                    case 3:
                        brs.this.a(layerItem.getAction_url(), layerItem.getLayer_id());
                        return;
                }
            }
        }
    };

    public brs(cde cde, bro bro) {
        super(cde, bro);
    }

    /* access modifiers changed from: protected */
    public final int a() {
        return R.layout.map_maplayer_popup;
    }

    /* access modifiers changed from: protected */
    public final void b(Window window) {
        super.b(window);
        this.q = window.findViewById(R.id.iv_map_setting_red_point);
        if (new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("map_skin_indicator_2", true)) {
            this.q.setVisibility(0);
        } else {
            this.q.setVisibility(8);
        }
    }

    public final void onClick(View view) {
        int i;
        int i2;
        int i3;
        int i4;
        super.onClick(view);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            int id = view.getId();
            if (R.id.rl_maplayer_realscene == id) {
                d();
                pageContext.startPage((String) "amap.life.action.RealSceneMainFragment", new PageBundle());
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("status", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.PAGE_ID_NEW_MAIN_MAP, "B009", jSONObject);
                return;
            }
            FavoritePOI favoritePOI = null;
            boolean z = false;
            if (R.id.rl_maplayer_setting == id) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_SETTING);
                String str = "";
                if (pageContext != null) {
                    bty mapView = DoNotUseTool.getMapManager().getMapView();
                    if (!(mapView == null || mapView.n() == null)) {
                        str = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getAdCode());
                    }
                }
                if (TextUtils.isEmpty(str)) {
                    str = "";
                }
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put(AutoJsonUtils.JSON_ADCODE, str);
                } catch (JSONException e2) {
                    kf.a((Throwable) e2);
                }
                LogManager.actionLogV2("P00001", LogConstant.MAP_SKIN_FEED_ENTRY, jSONObject2);
                this.q.setVisibility(8);
                new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("map_skin_indicator_2", false);
                pageContext.startPage((String) "amap.basemap.action.sys_map_setting_page", (PageBundle) null);
                d();
            } else if (R.id.rl_maplayer_commuting == id) {
                brm brm = (brm) ank.a(brm.class);
                if (brm != null) {
                    brm.c();
                }
                brm brm2 = (brm) ank.a(brm.class);
                if (brm2 != null) {
                    brm2.b();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_COMMUTING);
                d();
            } else if (100001 == id) {
                boolean z2 = !this.n.isSelected();
                this.n.setSelected(z2);
                boolean booleanValue = this.s.getBooleanValue("confirmTrafficReport", false);
                if (!z2 || booleanValue) {
                    a(z2);
                } else {
                    cse.a(this.g.a(), new a() {
                        final /* synthetic */ boolean a = true;

                        public final void a() {
                            brs.a(true);
                            brs.this.s.putBooleanValue("confirmTrafficReport", true);
                        }

                        public final void b() {
                            if (this.a) {
                                brs.this.n.setSelected(false);
                                brs.a(false);
                            }
                        }
                    });
                }
            } else if (100002 == id) {
                bmn.b();
                if (!brj.b(this.h.getMapView()) || !brj.a(this.h.getMapView())) {
                    ToastHelper.showToast(this.g.a().getResources().getString(R.string.tip_realtimebus_unsupport));
                    return;
                }
                bmn.b(true);
                if (!this.p.isSelected()) {
                    this.p.setSelected(true);
                }
                if (cdd.n().b() != null) {
                    cdd.n().b().a(true, true);
                }
                JSONObject jSONObject3 = new JSONObject();
                try {
                    GLGeoPoint n2 = this.h.getMapView().n();
                    jSONObject3.put(AutoJsonUtils.JSON_ADCODE, String.valueOf(li.a().a(n2.x, n2.y)));
                    jSONObject3.put("status", 1);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_REALTIME_BUS, jSONObject3);
            } else {
                if (100003 == id) {
                    boolean z3 = !this.o.isSelected();
                    this.o.setSelected(z3);
                    if (z3) {
                        bin.a.d((String) "104", 1);
                    } else {
                        bin.a.d((String) "104", 0);
                    }
                    brn brn = (brn) ank.a(brn.class);
                    FavoriteLayer g = brn != null ? brn.g() : null;
                    if (g != null) {
                        JSONObject jSONObject4 = new JSONObject();
                        try {
                            jSONObject4.put("type", z3 ? "1" : "0");
                        } catch (JSONException e4) {
                            kf.a((Throwable) e4);
                        }
                        LogManager.actionLogV2("P00001", "B013", jSONObject4);
                        if (z3) {
                            if (bin.a.S()) {
                                z = true;
                            }
                            if (z || brn.h() <= 1) {
                                bin.a.T();
                                brn.d();
                                AMapLog.i("zyl", "newmap—-isChecked---reloadAllFavorites");
                            }
                            g.setVisible(true);
                            g.setClickable(true);
                            g.setMoveToFocus(true);
                            brn.b();
                            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(this.h.getMapView().n());
                            com com2 = (com) ank.a(com.class);
                            if (com2 != null) {
                                cop b = com2.b(com2.a());
                                if (b != null) {
                                    favoritePOI = b.b();
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
                            this.h.getMapView().c(this.h.getMapView().a(i2, i4, i, i3, ags.a(this.g.a()).width(), (int) (((float) ags.a(this.g.a()).height()) - (this.g.a().getResources().getDisplayMetrics().density * 278.0f))));
                            ToastHelper.showToast(resources.getString(R.string.map_layer_saved_layer_is_opened));
                            return;
                        }
                        g.setFavoriteItemVisible(false);
                        if (!z3 && g.getFocusItem() != null) {
                            this.h.getOverlayManager().dimissTips();
                        }
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getResources().getString(R.string.map_layer_saved_layer_is_closed));
                        if (this.h != null) {
                            IOverlayManager overlayManager = this.h.getOverlayManager();
                            if (!(overlayManager == null || overlayManager.getDeepInfoOverlayManager() == null || g.getFocusItem() == null)) {
                                overlayManager.getDeepInfoOverlayManager().a();
                            }
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void d(Window window) {
        ArrayList arrayList = new ArrayList();
        Context context = window.getContext();
        this.n = new OpenLayerItemView(context);
        this.o = new OpenLayerItemView(context);
        this.p = new OpenLayerItemView(context);
        this.n.setId(100001);
        this.p.setId(100002);
        this.o.setId(100003);
        this.n.setImageResource(R.drawable.icon_layer_traffic);
        this.o.setImageResource(R.drawable.icon_layer_save);
        this.p.setImageResource(R.drawable.icon_layer_bus);
        this.n.setDesc(context.getString(R.string.layer_traffic));
        this.o.setDesc(context.getString(R.string.my_favorites));
        this.p.setDesc(context.getString(R.string.layer_bus));
        arrayList.add(this.n);
        arrayList.add(this.o);
        arrayList.add(this.p);
        this.j = window.findViewById(R.id.maplayer_list_wrapper_ll_divider);
        this.m = (OpenLayerView) window.findViewById(R.id.olv_maplayer);
        this.m.setExtraViews(arrayList);
        this.l = new bsf(this.m);
        this.l.a = this.t;
        this.p.setVisibility(brj.a() ? 0 : 8);
        bmn.b();
        this.p.setSelected(false);
        this.o.setSelected(bin.a.o("104"));
        this.n.setSelected(bin.a.o("103"));
        this.n.setOnClickListener(this);
        this.p.setOnClickListener(this);
        this.o.setOnClickListener(this);
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(LayerItem layerItem, boolean z) {
        if (z) {
            if (!(layerItem.getLayer_id() == 0 || layerItem.getLevel() == 0)) {
                this.h.getMapView().e(layerItem.getLevel());
            }
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
                StringBuilder sb = new StringBuilder();
                sb.append(layerItem.getLayer_id());
                jSONObject.put(TrafficUtil.KEYWORD, sb.toString());
                jSONObject.put("type", "0");
                jSONObject.put(AutoJsonUtils.JSON_ADCODE, str);
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
            LogManager.actionLogV2("P00188", "B004", jSONObject);
            awo awo = (awo) a.a.a(awo.class);
            if (layerItem.getControl_status() == 0 && awo != null) {
                awo.a(layerItem.getLayer_id(), true);
            }
            ArrayList arrayList = new ArrayList();
            if (!TextUtils.isEmpty(layerItem.getData())) {
                try {
                    arrayList.add(layerItem.getData().getBytes("UTF-8"));
                    b(arrayList);
                    if (awo != null) {
                        awo.b(layerItem.getLayer_id());
                    }
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
            }
            if (!TextUtils.isEmpty(layerItem.getToast())) {
                ToastHelper.showLongToast(layerItem.getToast());
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(layerItem.getName());
                sb2.append("图层已开启");
                ToastHelper.showToast(sb2.toString());
            }
            return true;
        }
        awo awo2 = (awo) a.a.a(awo.class);
        if (layerItem.getControl_status() == 0 && awo2 != null) {
            awo2.a(layerItem.getLayer_id(), false);
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(Integer.valueOf(layerItem.getItem_id()));
        a(arrayList2);
        if (awo2 != null) {
            awo2.c(layerItem.getItem_id());
        }
        String str2 = "";
        bty mapView2 = DoNotUseTool.getMapManager().getMapView();
        if (!(mapView2 == null || mapView2.n() == null)) {
            str2 = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView2.n()).getAdCode());
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(layerItem.getLayer_id());
            jSONObject2.put(TrafficUtil.KEYWORD, sb3.toString());
            jSONObject2.put("type", "1");
            jSONObject2.put(AutoJsonUtils.JSON_ADCODE, str2);
        } catch (JSONException e3) {
            kf.a((Throwable) e3);
        }
        LogManager.actionLogV2("P00188", "B004", jSONObject2);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(layerItem.getName());
        sb4.append("图层已关闭");
        ToastHelper.showToast(sb4.toString());
        return true;
    }

    public final void a(Window window) {
        a(bin.a.p("101") + 1);
    }

    /* access modifiers changed from: protected */
    public final void c(Window window) {
        apr apr = (apr) a.a.a(apr.class);
        boolean a = apr != null ? apr.a(AMapPageUtil.getPageContext()) : false;
        View findViewById = window.findViewById(R.id.vg_maplayer_realscene);
        if (a) {
            findViewById.setVisibility(0);
            window.findViewById(R.id.rl_maplayer_realscene).setOnClickListener(this);
            window.findViewById(R.id.rl_maplayer_setting).setOnClickListener(this);
            window.findViewById(R.id.rl_maplayer_commuting).setOnClickListener(this);
            return;
        }
        findViewById.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public final void b() {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            ArrayList<LayerItem> i = awo.i();
            ArrayList arrayList = new ArrayList();
            Iterator<LayerItem> it = i.iterator();
            while (it.hasNext()) {
                LayerItem next = it.next();
                if (next.getSwitch_status() != 2) {
                    arrayList.add(next);
                }
            }
            this.i = arrayList;
        }
    }
}
