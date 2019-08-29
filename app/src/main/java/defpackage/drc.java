package defpackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.life.api.entity.ScenicPlayRouteItemEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayRoutePoiItemEntity;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.scenic.Label3rd;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.LineOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.LineOverlay.LineType;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.LineOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper.Area;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi;
import com.autonavi.minimap.life.sketchscenic.view.ScenicPlayRouteStartView;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: drc reason: default package */
/* compiled from: ScenicPlayLayer */
public final class drc extends BaseLayer {
    public PointOverlay<dre> a;
    public ConcurrentHashMap<String, ArrayList<Label3rd>> b = new ConcurrentHashMap<>();
    public List<ScenicGuidePoi> c = new ArrayList();
    ScenicPlayRouteItemEntity d;
    public ScenicGuidePoi e;
    public ScenicGuidePoi f;
    @NonNull
    private final Context g;
    @NonNull
    private final bck h;
    private LineOverlay i;
    private final String j = "redesign://basemap/ScenicPlayNormal/";
    private final String k = "redesign://basemap/ScenicPlayActive/";
    private final String l = "redesign://basemap/ScenicPlayFocus/";
    private int m;
    private int n;
    private a o = new a(this);
    private Handler p = new Handler(Looper.getMainLooper());
    private boolean q = true;
    private bty r;
    private boolean s = false;
    private Area t = new Area();
    private Area u = new Area();
    private List<Area> v = new ArrayList();
    private String w;
    private int x;

    /* renamed from: drc$a */
    /* compiled from: ScenicPlayLayer */
    static class a implements Runnable {
        WeakReference<drc> a;

        public a(drc drc) {
            this.a = new WeakReference<>(drc);
        }

        public final void run() {
            if (this.a != null && this.a.get() != null) {
                drc drc = (drc) this.a.get();
                if (drc.a != null && drc.e != null) {
                    drc.a();
                    drc.a(drc.e, Double.valueOf(drc.d.g.get(0).c).doubleValue(), Double.valueOf(drc.d.g.get(0).b).doubleValue(), drc.f, false);
                }
            }
        }
    }

    public drc(@NonNull IVPageContext iVPageContext, @NonNull bck bck) {
        super(iVPageContext);
        this.g = iVPageContext.getContext();
        this.h = bck;
        this.a = new PointOverlay<>(this, "GGC");
        this.a.setVisible(true);
        this.i = new LineOverlay(this, "ScenicPlayLine");
        this.i.setVisible(true);
        this.v.add(this.t);
        this.v.add(this.u);
    }

    public final void a() {
        if (AMapPageUtil.getMVPActivityContext() != null && AMapPageUtil.getMVPActivityContext().c() != null && !AMapPageUtil.getMVPActivityContext().c().getName().endsWith(SearchCQDetailPage.a) && AMapPageUtil.isHomePage()) {
            this.f = null;
        }
    }

    public final void clear() {
        super.clear();
        this.s = false;
        this.p.removeCallbacks(this.o);
        b();
        this.e = null;
        this.c.clear();
        if (this.r != null) {
            this.r.b(20190001, true);
        }
    }

    public final void onDestroy() {
        clear();
        super.onDestroy();
    }

    public final void b() {
        if (this.b != null) {
            this.b.clear();
        }
    }

    public final TextureWrapper loadTexture(OverlayTextureParam overlayTextureParam) {
        if (!(overlayTextureParam.data instanceof dre)) {
            return null;
        }
        String str = overlayTextureParam.uri;
        ScenicPlayRouteStartView scenicPlayRouteStartView = new ScenicPlayRouteStartView(this.g);
        if (str.startsWith("redesign://basemap/ScenicPlayActive/")) {
            if (TextUtils.isEmpty(this.d.e)) {
                scenicPlayRouteStartView.initView(2);
            } else {
                scenicPlayRouteStartView.initView(1);
                scenicPlayRouteStartView.setDesc(this.d.e);
            }
            scenicPlayRouteStartView.setTitle(this.d.c);
            this.q = true;
            this.p.postDelayed(this.o, (long) (this.m * 1000));
        } else if (str.startsWith("redesign://basemap/ScenicPlayNormal/")) {
            this.q = false;
            scenicPlayRouteStartView.initView(2);
            scenicPlayRouteStartView.setTitle(this.g.getResources().getString(R.string.scenic_play_start_detail));
            scenicPlayRouteStartView.setStartIconBackground(this.g.getDrawable(R.drawable.play_start));
        } else if (str.startsWith("redesign://basemap/ScenicPlayFocus/")) {
            if (this.q) {
                if (TextUtils.isEmpty(this.d.e)) {
                    scenicPlayRouteStartView.initView(2);
                } else {
                    scenicPlayRouteStartView.initView(1);
                    scenicPlayRouteStartView.setDesc(this.d.e);
                }
                scenicPlayRouteStartView.setTitle(this.d.c);
            } else {
                scenicPlayRouteStartView.initView(2);
                scenicPlayRouteStartView.setTitle(this.g.getResources().getString(R.string.scenic_play_start_detail));
            }
            scenicPlayRouteStartView.setStartIconBackground(this.g.getDrawable(R.drawable.play_scenic_focus));
        }
        scenicPlayRouteStartView.measure(0, 0);
        int measuredWidth = scenicPlayRouteStartView.getMeasuredWidth();
        int measuredHeight = scenicPlayRouteStartView.getMeasuredHeight();
        this.t.areaId = 100;
        this.t.rect.left = 0.0f;
        float f2 = (float) measuredWidth;
        this.t.rect.right = f2;
        this.t.rect.top = 0.0f;
        this.t.rect.bottom = 152.0f;
        this.u.areaId = 101;
        this.u.rect.left = 0.0f;
        this.u.rect.right = f2;
        this.u.rect.top = 152.0f;
        this.u.rect.bottom = (float) measuredHeight;
        return makeTextureWrapper(scenicPlayRouteStartView, this.v);
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, ScenicPlayRouteItemEntity scenicPlayRouteItemEntity, bty bty, int i2, int i3) {
        if (!this.s) {
            a(str, scenicPlayRouteItemEntity, bty, i2, false, i3, 0);
        }
    }

    public final void a(String str, ScenicPlayRouteItemEntity scenicPlayRouteItemEntity, bty bty, int i2, boolean z, int i3, int i4) {
        a();
        clear();
        this.r = bty;
        this.d = scenicPlayRouteItemEntity;
        this.m = i2;
        if (this.d != null && this.d.g != null) {
            a(this.d.f);
            if (z) {
                a(b(this.d.f), bty, i3);
            }
            a(str, (List<ScenicPlayRoutePoiItemEntity>) this.d.g, bty);
            for (int size = this.d.g.size() - 1; size >= 0; size--) {
                ScenicGuidePoi scenicGuidePoi = (ScenicGuidePoi) POIFactory.createPOI(ScenicGuidePoi.class);
                scenicGuidePoi.setName(this.d.g.get(size).d);
                scenicGuidePoi.setPid(this.d.g.get(size).a);
                scenicGuidePoi.setId(this.d.g.get(size).a);
                scenicGuidePoi.setClearFocus(this.d.g.get(size).a);
                double doubleValue = Double.valueOf(this.d.g.get(size).c).doubleValue();
                double doubleValue2 = Double.valueOf(this.d.g.get(size).b).doubleValue();
                scenicGuidePoi.setPoint(new GeoPointHD(doubleValue, doubleValue2));
                if (this.c != null) {
                    this.c.add(scenicGuidePoi);
                }
                if (size == 0) {
                    a(scenicGuidePoi, doubleValue, doubleValue2, this.f, true);
                } else if (size == this.d.g.size() - 1) {
                    b(scenicGuidePoi, doubleValue, doubleValue2, this.f);
                } else {
                    a(scenicGuidePoi, doubleValue, doubleValue2, this.f);
                }
            }
            this.s = true;
            this.w = str;
            this.x = i4;
            a(str, i4);
        }
    }

    private static void a(String str, int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, str);
            jSONObject.put("itemid", i2);
            LogManager.actionLogV2("P00383", "B030", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void a(int i2, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.w);
            jSONObject.put("itemid", this.x);
            jSONObject.put("sudpoiid", str);
            if (i2 == 100) {
                if (this.q) {
                    jSONObject.put("click", "运营入口");
                } else {
                    jSONObject.put("click", "常驻入口");
                }
            }
            LogManager.actionLogV2("P00383", "B033", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            LineOverlayItem lineOverlayItem = new LineOverlayItem(LineType.LineTypeColor);
            lineOverlayItem.points.addAll(b(str));
            lineOverlayItem.lineWidth = 6;
            lineOverlayItem.borderWidth = 10;
            lineOverlayItem.fillColor = -33475;
            lineOverlayItem.borderColor = -1;
            lineOverlayItem.setFillTextureResource(R.drawable.map_lr);
            lineOverlayItem.fillTextureParam.anchorX = 0.5f;
            lineOverlayItem.fillTextureParam.anchorY = 0.5f;
            lineOverlayItem.setBorderTextureResource(R.drawable.map_lr);
            lineOverlayItem.borderTextureParam.anchorX = 0.5f;
            lineOverlayItem.borderTextureParam.anchorY = 0.5f;
            this.i.addItem(lineOverlayItem);
        }
    }

    @Nullable
    private static List<Coord> b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(";");
        ArrayList arrayList = new ArrayList(split.length);
        for (int i2 = 0; i2 < split.length; i2++) {
            Coord coord = new Coord();
            coord.lon = Double.valueOf(split[i2].split(",")[0]).doubleValue();
            coord.lat = Double.valueOf(split[i2].split(",")[1]).doubleValue();
            arrayList.add(coord);
        }
        return arrayList;
    }

    private void a(ScenicGuidePoi scenicGuidePoi, double d2, double d3, ScenicGuidePoi scenicGuidePoi2) {
        scenicGuidePoi.setIconType("scenic_play_line");
        dre dre = new dre(scenicGuidePoi);
        int i2 = R.drawable.play_scenic;
        int i3 = R.drawable.play_scenic_focus;
        dre.defaultTexture = makeTextureParam(i2, 0.5f, 0.87f);
        dre.focusTexture = makeTextureParam(i3, 0.5f, 0.87f);
        dre.coord = new Coord(d2, d3);
        StringBuilder sb = new StringBuilder();
        sb.append(dre.getID());
        scenicGuidePoi.setIdentityId(sb.toString());
        this.a.addItem(dre);
        if (scenicGuidePoi2 != null && TextUtils.equals(scenicGuidePoi.getPid(), scenicGuidePoi2.getPid())) {
            this.a.setFocus(dre.getID());
        }
    }

    public final void a(ScenicGuidePoi scenicGuidePoi, double d2, double d3, ScenicGuidePoi scenicGuidePoi2, boolean z) {
        String str;
        scenicGuidePoi.setIconType("scenic_play_line");
        final dre dre = new dre(scenicGuidePoi);
        if (this.e != null) {
            StringBuilder sb = new StringBuilder("redesign://basemap/ScenicPlayNormal/");
            sb.append(this.e.getId());
            destroyTexture(sb.toString());
            StringBuilder sb2 = new StringBuilder("redesign://basemap/ScenicPlayFocus/");
            sb2.append(this.e.getId());
            destroyTexture(sb2.toString());
            StringBuilder sb3 = new StringBuilder("redesign://basemap/ScenicPlayActive/");
            sb3.append(this.e.getId());
            destroyTexture(sb3.toString());
        } else {
            destroyTexture("redesign://basemap/ScenicPlayNormal/");
            destroyTexture("redesign://basemap/ScenicPlayFocus/");
            destroyTexture("redesign://basemap/ScenicPlayActive/");
        }
        if (this.n != 0) {
            this.a.removeItem(this.n);
        }
        this.e = scenicGuidePoi;
        if (z) {
            str = "redesign://basemap/ScenicPlayActive/";
        } else {
            StringBuilder sb4 = new StringBuilder("redesign://basemap/ScenicPlayNormal/");
            sb4.append(scenicGuidePoi.getId());
            str = sb4.toString();
        }
        dre.defaultTexture = makeCustomTextureParam(str, 0.5f, 0.87f, dre);
        StringBuilder sb5 = new StringBuilder("redesign://basemap/ScenicPlayFocus/");
        sb5.append(scenicGuidePoi.getId());
        dre.focusTexture = makeCustomTextureParam(sb5.toString(), 0.5f, 0.93f, dre);
        dre.coord = new Coord(d2, d3);
        StringBuilder sb6 = new StringBuilder();
        sb6.append(dre.getID());
        scenicGuidePoi.setIdentityId(sb6.toString());
        this.n = dre.getID();
        this.a.addItem(dre);
        if (scenicGuidePoi2 != null && TextUtils.equals(scenicGuidePoi.getPid(), scenicGuidePoi2.getPid())) {
            this.q = z;
            clearFocus();
            this.p.post(new Runnable() {
                public final void run() {
                    drc.this.a.setFocus(dre.getID());
                }
            });
        }
    }

    private void b(ScenicGuidePoi scenicGuidePoi, double d2, double d3, ScenicGuidePoi scenicGuidePoi2) {
        scenicGuidePoi.setIconType("scenic_play_line");
        dre dre = new dre(scenicGuidePoi);
        int i2 = R.drawable.play_end;
        int i3 = R.drawable.play_scenic_focus;
        dre.defaultTexture = makeTextureParam(i2, 0.5f, 0.87f);
        dre.focusTexture = makeTextureParam(i3, 0.5f, 0.87f);
        dre.coord = new Coord(d2, d3);
        StringBuilder sb = new StringBuilder();
        sb.append(dre.getID());
        scenicGuidePoi.setIdentityId(sb.toString());
        this.a.addItem(dre);
        if (scenicGuidePoi2 != null && TextUtils.equals(scenicGuidePoi.getPid(), scenicGuidePoi2.getPid())) {
            this.a.setFocus(dre.getID());
        }
    }

    private void a(List<Coord> list, bty bty, int i2) {
        if (list != null && list.size() != 0) {
            ArrayList arrayList = new ArrayList();
            for (Coord next : list) {
                arrayList.add(new GeoPoint(next.lon, next.lat));
            }
            int height = ags.a(this.g).height();
            bty.b(ags.a(this.g).width() / 2, (height - i2 == 0 ? 300 : i2 + 60) / 2);
            Rect rect = new Rect();
            float a2 = a((List<GeoPoint>) arrayList, DoNotUseTool.getMapManager(), rect, (float) bty.l());
            bty.a(rect.centerX(), rect.centerY());
            bty.d(a2);
        }
    }

    private static float a(List<GeoPoint> list, MapManager mapManager, Rect rect, float f2) {
        if (list.size() == 0) {
            return f2;
        }
        int i2 = list.get(0).x;
        int i3 = list.get(0).y;
        int i4 = i3;
        int i5 = i2;
        for (GeoPoint next : list) {
            i2 = Math.min(i2, next.x);
            i3 = Math.min(i3, next.y);
            i5 = Math.max(i5, next.x);
            i4 = Math.max(i4, next.y);
        }
        rect.left = i2;
        rect.top = i3;
        rect.right = i5;
        rect.bottom = i4;
        return mapManager.getMapView().a(i2, i3, i5, i4) - 0.75f;
    }

    private void a(String str, List<ScenicPlayRoutePoiItemEntity> list, bty bty) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            Label3rd label3rd = new Label3rd();
            label3rd.mLabelName = list.get(i2).d;
            GeoPointHD geoPointHD = new GeoPointHD(Double.valueOf(list.get(i2).c).doubleValue(), Double.valueOf(list.get(i2).b).doubleValue());
            label3rd.mP20X = geoPointHD.x;
            label3rd.mP20Y = geoPointHD.y;
            String str2 = list.get(i2).k;
            if (!TextUtils.isEmpty(str2) && (str2.startsWith("0x") || str2.startsWith("0X"))) {
                label3rd.mAnchor = (int) dri.b(str2.substring(2));
            }
            label3rd.mPoiId = list.get(i2).a;
            label3rd.mRank = (float) dri.a(list.get(i2).i);
            label3rd.mSubkey = dri.a(list.get(i2).h);
            label3rd.mMainkey = dri.a(list.get(i2).g);
            label3rd.mMinzoom = dri.a(list.get(i2).j);
            arrayList.add(label3rd);
        }
        if (arrayList.size() > 0) {
            this.b.put(str, arrayList);
            a(arrayList, bty);
        }
    }

    public static void a(ArrayList<Label3rd> arrayList, bty bty) {
        bty.a(20190001, (Label3rd[]) arrayList.toArray(new Label3rd[arrayList.size()]), true);
    }

    public final boolean b(int i2, String str) {
        a(i2, str);
        switch (i2) {
            case 100:
                bid pageContext = AMapPageUtil.getPageContext();
                String str2 = this.d.b;
                if (pageContext != null && !TextUtils.isEmpty(str2)) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(str2));
                    intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
                    pageContext.startScheme(intent);
                }
                return true;
            case 101:
                if (this.a != null) {
                    destroyTexture("redesign://basemap/ScenicPlayFocus/");
                    this.a.setFocus(this.n);
                }
                return false;
            default:
                return false;
        }
    }
}
