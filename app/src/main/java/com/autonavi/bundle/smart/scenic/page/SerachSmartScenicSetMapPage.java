package com.autonavi.bundle.smart.scenic.page;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.bundle.smart.scenic.ajx.ModuleSmartSearchScenicSet;
import com.autonavi.bundle.smart.scenic.ajx.ModuleSmartSearchScenicSet.a;
import com.autonavi.bundle.smart.scenic.overlay.SmartScenicFocusOverlay;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(clickable = false, moveToFocus = false, overlay = UvOverlay.MapPointOverlay, visible = true)})
@PageAction("search_smart_scenicarea_set_page")
public class SerachSmartScenicSetMapPage extends Ajx3Page implements a {
    public bdi a;
    public je b;
    private ModuleSmartSearchScenicSet c;
    private bdg d;
    private JsFunctionCallback e;
    private SmartScenicFocusOverlay f;
    private GeoPointHD g;
    private float h = 0.0f;
    private boolean i = false;
    private final BackCallback j = new BackCallback() {
        public final void back(Object obj, String str) {
            SerachSmartScenicSetMapPage.this.finish();
        }
    };
    private final a k = new a() {
        public final void a() {
            SerachSmartScenicSetMapPage.this.a(false, null);
            LogUtil.actionLogV2("P00399", "B008", null);
        }
    };

    public Ajx3PagePresenter createPresenter() {
        return new bdh(this);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.a(true);
        }
        b();
        this.c = (ModuleSmartSearchScenicSet) this.mAjxView.getJsModule(ModuleSmartSearchScenicSet.MODULE_NAME);
        if (this.c != null) {
            this.c.setUiListener(this);
        }
        this.mAjxView.setBackCallBack(this.j);
        if (this.d == null) {
            this.d = new bdg(this);
        }
        if (this.f == null) {
            this.f = new SmartScenicFocusOverlay(getMapView());
            addOverlay(this.f);
        }
        bdg bdg = this.d;
        GeoPointHD geoPointHD = this.g;
        float f2 = this.h;
        if (bdg.d != null) {
            bdg.a();
            if (geoPointHD != null) {
                bdg.d.a(500, f2, 0, 0, geoPointHD.x, geoPointHD.y);
            }
            if (bdg.d != null) {
                if (bdg.h == null) {
                    bdg.h = new a(0);
                }
                bdg.h.a = bdg.d.s();
                bdg.d.b(false);
                bdg.h.f = bdg.d.o(false);
                bdg.d.a(bdg.d.j(false), bdg.d.l(false), 10);
            }
            awo awo2 = (awo) a.a.a(awo.class);
            if (awo2 != null) {
                Iterator<LayerItem> it = awo2.i().iterator();
                while (it.hasNext()) {
                    LayerItem next = it.next();
                    if (next.getLayer_id() == 610002) {
                        awo2.b();
                        awo2.a(next.getData());
                        awo2.b(610002);
                        return;
                    }
                }
            }
        }
    }

    private void b() {
        if (this.b == null) {
            this.b = (je) a.a.a(je.class);
        }
        if (this.b != null) {
            this.b.a(true);
        }
    }

    public void resume() {
        b();
        super.resume();
    }

    public View getMapSuspendView() {
        if (this.d == null) {
            this.d = new bdg(this);
        }
        bdg bdg = this.d;
        a aVar = this.k;
        bdg.e = bdg.b.d();
        bdg.b.a(aVar);
        bdg.g = aVar;
        bdg.b.a(bdg.a.getSuspendView(), bdg.e, bdg.b.e(), 3);
        bdg.f = bdg.b.f();
        LayoutParams g2 = bdg.b.g();
        if (g2 != null) {
            g2.bottomMargin = agn.a(bdg.c, 4.0f);
            bdg.a.addWidget(bdg.f, g2, 7);
        }
        return this.d.a.getSuspendView();
    }

    private String c() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.g != null) {
                jSONObject.put(AutoJsonUtils.JSON_ADCODE, this.g.getAdCode());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public void destroy() {
        super.destroy();
        if (this.b != null) {
            this.b.a(false);
        }
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.a(false);
        }
        if (this.d != null) {
            bdg bdg = this.d;
            if (bdg.d != null) {
                awo awo2 = (awo) a.a.a(awo.class);
                if (awo2 != null) {
                    awo2.a(610002);
                }
            }
            if (!(bdg.d == null || bdg.h == null)) {
                bdg.d.b(bdg.h.a);
                bdg.d.a(bdg.d.j(false), bdg.d.l(false), bdg.h.f);
            }
            bdg.a();
        }
        if (this.f != null) {
            this.f.clear();
            removeOverlay(this.f);
        }
    }

    public void onPageCover() {
        if (this.d != null) {
            awo awo = (awo) a.a.a(awo.class);
            if (awo != null) {
                awo.a(false);
            }
            bdg bdg = this.d;
            if (bdg.d != null) {
                if (bdg.i == null) {
                    bdg.i = new a(0);
                }
                bdg.i.c = bdg.d.o();
                bdg.i.b = bdg.d.v();
                bdg.i.d = bdg.d.I();
                bdg.i.e = bdg.d.J();
                bdg.i.a = bdg.d.s();
                if (bdg.h != null) {
                    bdg.d.b(bdg.h.a);
                }
                awo awo2 = (awo) a.a.a(awo.class);
                if (awo2 != null) {
                    awo2.c(610002);
                }
            }
        }
    }

    public void onPageAppear() {
        if (this.d != null) {
            awo awo = (awo) a.a.a(awo.class);
            if (awo != null) {
                awo.a(true);
            }
            bdg bdg = this.d;
            bdg.a();
            if (!(bdg.d == null || bdg.i == null)) {
                if (!(bdg.g == null || bdg.b == null)) {
                    bdg.b.a(bdg.g);
                }
                bdg.d.b(bdg.i.a);
                bdg.d.a(500, bdg.i.b, (int) bdg.i.d, (int) bdg.i.e, bdg.i.c.x, bdg.i.c.y);
                bdg.d.a(bdg.d.j(false), bdg.d.l(false), 10);
                awo awo2 = (awo) a.a.a(awo.class);
                if (awo2 != null) {
                    awo2.b(610002);
                }
            }
            this.f.addFoliter();
        }
    }

    public final void a(String str) {
        if (this.d != null) {
            try {
                this.d.a((int) Float.parseFloat(new JSONObject(str).optString("height")));
            } catch (JSONException unused) {
            } catch (NumberFormatException unused2) {
            }
        }
    }

    public final void a(JsFunctionCallback jsFunctionCallback) {
        this.e = jsFunctionCallback;
    }

    public final void a(boolean z, @Nullable als als) {
        if (this.f == null) {
            this.f = new SmartScenicFocusOverlay(getMapView());
            addOverlay(this.f);
        }
        if (!z || als == null) {
            if (this.i) {
                this.i = false;
                this.f.clear();
                b(z, null);
            }
            return;
        }
        this.i = true;
        GeoPointHD geoPointHD = new GeoPointHD(als.e, als.f);
        this.f.addSmartScenicFoucsPoint(getContext(), geoPointHD, als.b);
        b(z, als);
        int adCode = geoPointHD.getAdCode();
        String str = als.b;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, adCode);
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, str);
            LogUtil.actionLogV2("P00399", "B007", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private void b(boolean z, @Nullable als als) {
        JSONObject jSONObject = new JSONObject();
        if (!z || als == null) {
            jSONObject.put("action_type", 3);
            if (this.d != null) {
                this.d.a(4);
            }
        } else {
            try {
                jSONObject.put("action_type", 2);
                jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, als.b);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (this.e != null) {
            this.e.callback(jSONObject.toString());
        }
    }

    public final void a() {
        if (this.a == null && getMapView() != null) {
            this.a = new bdi(getMapView(), this.e);
        }
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString(LocationParams.PARA_FLP_AUTONAVI_LON);
            String string2 = arguments.getString("lat");
            String string3 = arguments.getString("key_map_level");
            try {
                if (!TextUtils.isEmpty(string3)) {
                    this.h = Float.parseFloat(string3);
                }
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                this.g = new GeoPointHD(bby.b(string), bby.b(string2));
            }
        }
        PageBundle arguments2 = getArguments();
        if (arguments2 == null) {
            arguments2 = new PageBundle();
            setArguments(arguments2);
        }
        arguments2.putString("url", "path://amap_bundle_scenic_area/src/pages/BizScenicUniverseMapPage.page.js");
        arguments2.putString("jsData", c());
        super.onCreate(context);
    }
}
