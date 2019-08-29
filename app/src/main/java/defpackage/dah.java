package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.utils.view.OneClickListener;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.utils.Constant.a;
import com.autonavi.map.core.view.MapGuideView;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dah reason: default package */
/* compiled from: GuideManagerImpl */
public class dah implements cek {
    /* access modifiers changed from: private */
    public cdc a;
    /* access modifiers changed from: private */
    public MapGuideView b;
    private boolean c = false;

    public final void a(cdc cdc) {
        this.a = cdc;
        this.b = new MapGuideView(this.a.a());
        if (this.b.getGuideView() != null) {
            this.b.getGuideView().setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (!aaw.c(AMapAppGlobal.getApplication())) {
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.net_error_message));
                    } else {
                        String str = "indoor.html";
                        final boolean z = false;
                        try {
                            ami a2 = dah.this.a.c().a();
                            if (a2 != null) {
                                if (a.b(a2.e)) {
                                    LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_TRAIN_STATION_GUIDE_BTN_CLICK);
                                    JSONObject jSONObject = new JSONObject();
                                    jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, a2.e);
                                    jSONObject.put("poiname", a2.a);
                                    jSONObject.put(AutoJsonUtils.JSON_ADCODE, GeoPoint.glGeoPoint2GeoPoint(dah.this.a.b().getMapView().n()).getAdCode());
                                    new blh();
                                    blh.a((String) "railwayIndoor", jSONObject.toString());
                                    str = "railwayIndoor.html";
                                } else {
                                    LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_GUIDE_BTN_CLICK);
                                    JSONObject jSONObject2 = new JSONObject();
                                    jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, a2.e);
                                    jSONObject2.put("poiname", a2.a);
                                    new blh();
                                    blh.a((String) "indoorGuide", jSONObject2.toString());
                                    z = true;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ConfigerHelper.getInstance().isLoadPoiPageFromInternet()) {
                            str = "http://tpl.testing.amap.com/and/".concat(String.valueOf(str));
                        } else {
                            bgx bgx = (bgx) a.a.a(bgx.class);
                            if (bgx != null) {
                                str = bgx.getUrl(str);
                            }
                        }
                        aja aja = new aja(str);
                        aja.b = new ajf() {
                            public final boolean f() {
                                return true;
                            }

                            public final boolean g() {
                                return z;
                            }
                        };
                        aix aix = (aix) a.a.a(aix.class);
                        if (aix != null) {
                            aix.a(AMapPageUtil.getPageContext(), aja);
                        }
                        a.k();
                    }
                    brj.b();
                }
            });
        }
        if (this.b.getGuideViewBubble() != null) {
            this.b.getGuideViewBubble().setOnClickListener(new OneClickListener() {
                public final void doClick(View view) {
                    dah.this.b.getGuideViewBubble().setVisibility(8);
                    brj.b();
                }
            });
        }
        cdd.n().a((a) this);
        cdd.n().a((e) this);
        cdd.n().a((d) this);
    }

    private void c() {
        boolean z;
        if (this.a.e() != null) {
            TextView guideViewBubble = this.b.getGuideViewBubble();
            boolean z2 = true;
            boolean z3 = this.a.a().getResources().getConfiguration().orientation == 1;
            ami a2 = this.a.c().a();
            if (a2 != null) {
                boolean b2 = a.b(a2.e);
                if (!z3 || !this.a.e().isViewEnable(32768) || !this.a.e().isViewEnable(268435456) || ((!a.a(a2.f) && !b2) || this.a.b().getMapView().J() != 0.0f)) {
                    z2 = false;
                }
                if (z2 && this.b.getGuideView().getVisibility() != 0) {
                    if (b2) {
                        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_TRAIN_STATION_GUIDE_BTN_VISIBLE);
                    } else {
                        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_GUIDE_BTN_VISIBLE);
                    }
                }
                if (z2) {
                    if (b2) {
                        this.b.getGuideView().setImageResource(R.drawable.map_button_icon_guide_train_station);
                    } else {
                        this.b.getGuideView().setImageResource(R.drawable.btn_mapcontainer_guide);
                    }
                }
                if (z2) {
                    this.b.getGuideView().setVisibility(0);
                    brm brm = (brm) ank.a(brm.class);
                    if (brm != null) {
                        z = brm.d();
                    } else {
                        z = false;
                    }
                    if (z || b2) {
                        guideViewBubble.setVisibility(8);
                    } else {
                        guideViewBubble.setVisibility(0);
                    }
                } else {
                    this.b.getGuideView().setVisibility(8);
                    guideViewBubble.setVisibility(8);
                }
            } else {
                this.b.getGuideView().setVisibility(8);
                guideViewBubble.setVisibility(8);
            }
        }
    }

    public final LinearLayout b() {
        return this.b;
    }

    public void updateStateWhenCompassPaint() {
        c();
    }

    public void onResetViewState() {
        c();
    }

    public final void a() {
        c();
    }
}
