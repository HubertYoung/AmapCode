package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.maplayer.OverlayManagerImpl$2;
import com.amap.bundle.maplayer.OverlayManagerImpl$3;
import com.amap.bundle.maplayer.OverlayManagerImpl$5;
import com.amap.bundle.maplayer.OverlayManagerImpl$6;
import com.amap.bundle.maplayer.OverlayManagerImpl$OnSaveTab$1;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiLinkTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.IOverlayManager.SAVED_POINT_TYPE;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.MapPointPOI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController;
import com.autonavi.map.fragmentcontainer.page.IPoiDetailDelegate;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppIFloorManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.MapPointOverlay;
import com.autonavi.minimap.base.overlay.MapPointOverlay.OnClearFocusListener;
import com.autonavi.minimap.base.overlay.MapPointOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.bundle.maphome.api.IMapEventListener;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.map.FavoriteLayer.FavoriteItemClickListener;
import com.autonavi.minimap.map.FavoriteOverlayItem;
import com.autonavi.minimap.map.LocalReportOverlay;
import com.autonavi.minimap.map.TrafficOverlayItem;
import com.autonavi.minimap.map.TrafficPointOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: xn reason: default package */
/* compiled from: OverlayManagerImpl */
public class xn implements IOverlayManager {
    private static volatile int v;
    boolean a = false;
    ccz b;
    private cdx c;
    /* access modifiers changed from: private */
    public MapPointOverlay d;
    /* access modifiers changed from: private */
    public MapPointOverlay e;
    /* access modifiers changed from: private */
    public TrafficPointOverlay f;
    private LocalReportOverlay g;
    /* access modifiers changed from: private */
    public FavoriteLayer h;
    /* access modifiers changed from: private */
    public xr i;
    private brp j;
    private bro k;
    /* access modifiers changed from: private */
    public bty l;
    /* access modifiers changed from: private */
    public IPoiDetailDelegate m;
    /* access modifiers changed from: private */
    public a n = new a(this, 0);
    /* access modifiers changed from: private */
    public List<AosRequest> o = new ArrayList();
    private SparseArray<d> p = new SparseArray<>();
    private List<PointOverlayItem> q = new ArrayList();
    /* access modifiers changed from: private */
    public com.autonavi.map.core.IOverlayManager.b r;
    private btu s;
    private List<bts> t;
    private Context u;
    private List<Long> w = null;

    /* renamed from: xn$a */
    /* compiled from: OverlayManagerImpl */
    public class a {
        public AosRequest a;
        public List<POI> b;
        public Timer c;
        public C0108a d;
        public int e;
        public String f;
        public GeocodePOI g;

        /* renamed from: xn$a$a reason: collision with other inner class name */
        /* compiled from: OverlayManagerImpl */
        public class C0108a extends TimerTask {
            public int a;

            private C0108a() {
                this.a = 0;
            }

            public /* synthetic */ C0108a(a aVar, byte b2) {
                this();
            }

            public final void run() {
                if (xn.this.m == null || !xn.this.m.isTokenAvailable(this.a)) {
                    a.this.a();
                } else {
                    aho.a(new Runnable() {
                        public final void run() {
                            if (a.this.b != null && a.this.b.size() > 0) {
                                a.this.e++;
                                if (a.this.e > a.this.b.size() - 1) {
                                    a.this.e = 0;
                                }
                                a.a(a.this, a.this.b.get(a.this.e).getName(), C0108a.this.a);
                            }
                        }
                    });
                }
            }
        }

        private a() {
            this.b = new ArrayList();
            this.e = 0;
            this.f = null;
            this.g = null;
        }

        /* synthetic */ a(xn xnVar, byte b2) {
            this();
        }

        public final void a() {
            if (this.c != null) {
                this.c.cancel();
                this.c = null;
            }
            if (this.d != null) {
                this.d.cancel();
                this.d = null;
            }
        }

        public static /* synthetic */ void a(a aVar, String str, int i) {
            if (xn.this.m != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("mainTitle", aVar.f);
                String string = AMapAppGlobal.getApplication().getString(R.string.something_nearby, new Object[]{str});
                pageBundle.putString("viceTitle", string);
                aVar.g.setAddr(string);
                pageBundle.putObject("POI", aVar.g);
                if (xn.this.m.isTokenAvailable(i)) {
                    xn.this.m.refreshPoiFooter(pageBundle, i);
                }
            }
        }
    }

    /* renamed from: xn$b */
    /* compiled from: OverlayManagerImpl */
    public class b implements FavoriteItemClickListener {
        private b() {
        }

        /* synthetic */ b(xn xnVar, byte b) {
            this();
        }

        public final void onFavoriteItemClick(FavoriteOverlayItem favoriteOverlayItem) {
            xn.this.clearScenicSelectMapPois();
            if (favoriteOverlayItem != null) {
                xn.this.a((xq) xn.this.h);
                POI poi = favoriteOverlayItem.getPOI();
                if (poi != null) {
                    FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
                    if (favoritePOI != null) {
                        if (favoritePOI.getPoiExtra() != null) {
                            favoritePOI.getPoiExtra().put(IOverlayManager.POI_EXTRA_FROM_FAV_ON_MAP, Boolean.TRUE);
                        }
                        xn.b(favoritePOI, xn.a(favoritePOI));
                        PoiButtonTemplate poiButtonTemplate = new PoiButtonTemplate();
                        poiButtonTemplate.setValue("");
                        poiButtonTemplate.setAction("share");
                        poiButtonTemplate.setId(1012);
                        poiButtonTemplate.setType(PoiLayoutTemplate.BUTTON);
                        ((ISearchPoiData) favoritePOI.as(ISearchPoiData.class)).getTemplateDataMap().put(Integer.valueOf(1012), poiButtonTemplate);
                        ((ISearchPoiData) favoritePOI.as(ISearchPoiData.class)).getTemplateData().add(poiButtonTemplate);
                        if (!TextUtils.isEmpty(poi.getAddr())) {
                            PoiTextTemplate poiTextTemplate = new PoiTextTemplate();
                            poiTextTemplate.setValue(poi.getAddr());
                            poiTextTemplate.setId(1010);
                            poiTextTemplate.setType("text");
                            poiTextTemplate.setName("address");
                            ((ISearchPoiData) favoritePOI.as(ISearchPoiData.class)).getTemplateDataMap().put(Integer.valueOf(1010), poiTextTemplate);
                            ((ISearchPoiData) favoritePOI.as(ISearchPoiData.class)).getTemplateData().add(poiTextTemplate);
                        }
                        if (TextUtils.isEmpty(favoritePOI.getId())) {
                            com com2 = (com) ank.a(com.class);
                            if (com2 != null) {
                                cop b = com2.b(com2.a());
                                if (b != null) {
                                    FavoritePOI d = b.d(poi);
                                    if (d != null && !TextUtils.isEmpty(d.getId())) {
                                        favoritePOI.setId(d.getId());
                                    }
                                }
                            }
                        }
                        HashMap<String, Serializable> poiExtra = favoritePOI != null ? favoritePOI.getPoiExtra() : null;
                        if (poiExtra != null) {
                            int pointType = favoriteOverlayItem.getPointType();
                            poiExtra.put("pointType", Integer.valueOf(pointType));
                            if (pointType != 0) {
                                JSONObject jSONObject = new JSONObject();
                                int w = xn.this.l.w();
                                try {
                                    jSONObject.put("type", String.valueOf(pointType));
                                    jSONObject.put("from", String.valueOf(w));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogManager.actionLogV2("P00001", "B207", jSONObject);
                            }
                        }
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putBoolean(Constant.KEY_IS_FAVORITE, true);
                        xn.this.a((POI) favoritePOI, pageBundle, (Callback<Integer>) new OverlayManagerImpl$OnSaveTab$1<Integer>(this, favoritePOI));
                    }
                }
            }
        }
    }

    /* renamed from: xn$c */
    /* compiled from: OverlayManagerImpl */
    static class c implements OnItemClickListener<PointOverlayItem> {
        private c() {
        }

        /* synthetic */ c(byte b) {
            this();
        }

        public final /* synthetic */ void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.overlay_manager_verifying));
        }
    }

    /* renamed from: xn$d */
    /* compiled from: OverlayManagerImpl */
    static class d {
        List<com.autonavi.map.core.IOverlayManager.c> a;

        private d() {
        }

        /* synthetic */ d(byte b) {
            this();
        }
    }

    public boolean onEngineActionGesture(alg alg) {
        return false;
    }

    public boolean onGpsBtnClick() {
        return false;
    }

    public boolean onHorizontalMove(float f2) {
        return false;
    }

    public boolean onHorizontalMoveEnd() {
        return false;
    }

    public boolean onLineOverlayClick(long j2) {
        return false;
    }

    public void onMapAnimationFinished(int i2) {
    }

    public void onMapAnimationFinished(aln aln) {
    }

    public boolean onMapCompassClick() {
        return false;
    }

    public boolean onMapDoubleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        return false;
    }

    public boolean onMapLevelChange(boolean z) {
        return false;
    }

    public boolean onMapMotionFinish() {
        return false;
    }

    public boolean onMapMotionStop() {
        return false;
    }

    public void onMapRenderCompleted() {
    }

    public boolean onMapShowPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        return false;
    }

    public boolean onMapSingleClick(int i2, MotionEvent motionEvent, GeoPoint geoPoint) {
        return false;
    }

    public boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        return false;
    }

    public boolean onMapSizeChange() {
        return false;
    }

    public void onMapTouch() {
    }

    public boolean onMapTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onNoBlankClick() {
        return false;
    }

    public boolean onPointOverlayClick(long j2, int i2) {
        return false;
    }

    public void init(bro bro, bty bty, Context context, List<bts> list) {
        this.k = bro;
        this.l = bty;
        this.u = context;
        this.t = list;
        this.c = new cdx();
        this.h = new FavoriteLayer();
        this.d = new MapPointOverlay(this.l, R.drawable.b_poi_hl);
        this.f = new TrafficPointOverlay(this.l, R.drawable.tmc_poi_hl);
        this.e = new MapPointOverlay(this.l, R.drawable.b_poi_hl);
        this.g = new LocalReportOverlay(this.l);
        this.g.setAnimatorType(1);
        c();
        resetMapPointAnimatorType();
        this.d.setBubbleAnimator(2);
        this.f.setAnimatorType(2);
        this.f.setBubbleAnimator(2);
        this.g.setMoveToFocus(false);
        this.g.setOverlayOnTop(true);
        this.g.setShowFocusTop(true);
        this.e.setShowFocusTop(true);
        this.d.setShowFocusTop(true);
        this.f.setShowFocusTop(true);
        this.h.setFavoriteItemClickListener(new b(this, 0));
        this.g.setOnItemClickListener(new c(0));
        this.i = new xr(this.l);
        this.j = (brp) ank.a(brp.class);
        if (this.j != null) {
            this.j.a(this.l);
        }
        brn brn = (brn) ank.a(brn.class);
        if (brn != null) {
            brn.a(this.l, this.h);
        }
        this.d.setOnClearFocusListener(new OnClearFocusListener() {
            public final void onClearFocus() {
                xn.this.i.a.a();
            }
        });
        xp xpVar = (xp) defpackage.esb.a.a.a(xp.class);
        if (xpVar != null) {
            xpVar.a(0, this.c);
            xpVar.a(1, this.h);
        }
    }

    public cdx getGpsLayer() {
        return this.c;
    }

    public xq getUniversalOverlay(UvOverlay uvOverlay) {
        if (uvOverlay == UvOverlay.LocalReportOverlay) {
            return this.g;
        }
        if (uvOverlay == UvOverlay.SaveOverlay) {
            return this.h;
        }
        return null;
    }

    public MapPointOverlay getGeoCodeOverlay() {
        return this.e;
    }

    public MapPointOverlay getMapPointOverlay() {
        return this.d;
    }

    public TrafficPointOverlay getTrafficPointOverlay() {
        return this.f;
    }

    public com.autonavi.map.core.IOverlayManager.a getDeepInfoOverlayManager() {
        return this.i;
    }

    private synchronized void a() {
        this.q.clear();
        if (this.g.getSize() > 0) {
            for (int i2 = 0; i2 < this.g.getSize(); i2++) {
                this.q.add(this.g.getItem(i2));
            }
        }
    }

    private synchronized void b() {
        this.g.setAnimatorType(0);
        for (int i2 = 0; i2 < this.q.size(); i2++) {
            TrafficOverlayItem trafficOverlayItem = (TrafficOverlayItem) this.q.get(i2);
            if (trafficOverlayItem != null) {
                this.g.addItem(trafficOverlayItem);
            }
        }
        this.g.setAnimatorType(1);
        this.q.clear();
    }

    public int saveFocus() {
        ArrayList arrayList = new ArrayList();
        if (this.h.getFocusItem() != null) {
            FavoriteOverlayItem focusItem = this.h.getFocusItem();
            com.autonavi.map.core.IOverlayManager.c cVar = new com.autonavi.map.core.IOverlayManager.c();
            cVar.a = focusItem;
            cVar.b = SAVED_POINT_TYPE.save;
            arrayList.add(cVar);
        }
        if (this.e.getSize() > 0) {
            com.autonavi.map.core.IOverlayManager.c cVar2 = new com.autonavi.map.core.IOverlayManager.c();
            cVar2.a = (PointOverlayItem) this.e.getItem(0);
            cVar2.b = SAVED_POINT_TYPE.geo_code;
            arrayList.add(cVar2);
        }
        if (this.d.getSize() > 0) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) this.d.getItem(0);
            if (!(pointOverlayItem instanceof MapPointOverlayItem ? ((MapPointOverlayItem) pointOverlayItem).getExtra().containsKey(IOverlayManager.MAP_ITEM_EXTRA_KEY_IS_SAVE) : false)) {
                com.autonavi.map.core.IOverlayManager.c cVar3 = new com.autonavi.map.core.IOverlayManager.c();
                cVar3.a = pointOverlayItem;
                cVar3.b = SAVED_POINT_TYPE.map_point;
                arrayList.add(cVar3);
            }
        }
        if (this.f.getSize() > 0) {
            com.autonavi.map.core.IOverlayManager.c cVar4 = new com.autonavi.map.core.IOverlayManager.c();
            cVar4.a = (PointOverlayItem) this.f.getItem(0);
            cVar4.b = SAVED_POINT_TYPE.traffic_point;
            arrayList.add(cVar4);
        }
        if (arrayList.size() <= 0) {
            return 0;
        }
        int i2 = v + 1;
        v = i2;
        d dVar = new d(0);
        dVar.a = arrayList;
        this.p.put(i2, dVar);
        xr xrVar = this.i;
        xrVar.c.put(i2, xrVar.b);
        return i2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0028 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.autonavi.map.core.IOverlayManager.c> solveSavedFocusWithKey(int r9, boolean r10) {
        /*
            r8 = this;
            android.util.SparseArray<xn$d> r0 = r8.p
            java.lang.Object r0 = r0.get(r9)
            xn$d r0 = (defpackage.xn.d) r0
            r1 = 0
            if (r0 != 0) goto L_0x000c
            return r1
        L_0x000c:
            java.util.List<com.autonavi.map.core.IOverlayManager$c> r2 = r0.a
            if (r2 == 0) goto L_0x012f
            java.util.List<com.autonavi.map.core.IOverlayManager$c> r2 = r0.a
            int r2 = r2.size()
            if (r2 != 0) goto L_0x001a
            goto L_0x012f
        L_0x001a:
            android.util.SparseArray<xn$d> r2 = r8.p
            r2.remove(r9)
            if (r10 != 0) goto L_0x0022
            return r1
        L_0x0022:
            java.util.List<com.autonavi.map.core.IOverlayManager$c> r10 = r0.a
            java.util.Iterator r0 = r10.iterator()
        L_0x0028:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x012e
            java.lang.Object r2 = r0.next()
            com.autonavi.map.core.IOverlayManager$c r2 = (com.autonavi.map.core.IOverlayManager.c) r2
            if (r2 == 0) goto L_0x0039
            com.autonavi.minimap.base.overlay.PointOverlayItem r3 = r2.a
            goto L_0x003a
        L_0x0039:
            r3 = r1
        L_0x003a:
            if (r3 == 0) goto L_0x0028
            com.autonavi.map.core.IOverlayManager$SAVED_POINT_TYPE r2 = r2.b
            int[] r4 = defpackage.xn.AnonymousClass3.a
            int r2 = r2.ordinal()
            r2 = r4[r2]
            r4 = 1
            r5 = 0
            switch(r2) {
                case 1: goto L_0x0109;
                case 2: goto L_0x00d8;
                case 3: goto L_0x00a8;
                case 4: goto L_0x007b;
                case 5: goto L_0x004c;
                default: goto L_0x004b;
            }
        L_0x004b:
            goto L_0x0028
        L_0x004c:
            java.lang.Class<com.autonavi.minimap.base.overlay.TrafficPointOverlayItem> r2 = com.autonavi.minimap.base.overlay.TrafficPointOverlayItem.class
            boolean r2 = r2.isInstance(r3)
            if (r2 == 0) goto L_0x006e
            com.autonavi.minimap.map.TrafficPointOverlay r2 = r8.f
            com.autonavi.minimap.base.overlay.TrafficPointOverlayItem r3 = (com.autonavi.minimap.base.overlay.TrafficPointOverlayItem) r3
            com.autonavi.common.model.GeoPoint r5 = r3.getGeoPoint()
            int r5 = r5.x
            com.autonavi.common.model.GeoPoint r6 = r3.getGeoPoint()
            int r6 = r6.y
            float r7 = r3.mZ
            java.lang.String r3 = r3.getTrafficEventId()
            r2.setItem(r5, r6, r7, r3)
            goto L_0x0075
        L_0x006e:
            com.autonavi.minimap.map.TrafficPointOverlay r2 = r8.f
            com.autonavi.minimap.base.overlay.MapPointOverlayItem r3 = (com.autonavi.minimap.base.overlay.MapPointOverlayItem) r3
            r2.setItem(r3)
        L_0x0075:
            com.autonavi.minimap.map.TrafficPointOverlay r2 = r8.f
            r2.setOverlayOnTop(r4)
            goto L_0x0028
        L_0x007b:
            com.autonavi.minimap.base.overlay.MapPointOverlayItem r3 = (com.autonavi.minimap.base.overlay.MapPointOverlayItem) r3
            java.util.HashMap r2 = r3.getExtra()
            java.lang.String r4 = "is_save"
            boolean r2 = r2.containsKey(r4)
            com.autonavi.minimap.base.overlay.MapPointOverlay r4 = r8.d
            r6 = r2 ^ 1
            r4.setOverlayOnTop(r6)
            com.autonavi.minimap.base.overlay.MapPointOverlay r4 = r8.d
            r2 = r2 ^ 1
            r4.setShowFocusTop(r2)
            com.autonavi.minimap.base.overlay.MapPointOverlay r2 = r8.d
            r2.setItem(r3)
            com.autonavi.minimap.base.overlay.MapPointOverlay r2 = r8.d
            r2.setFocus(r3, r5)
            xr r2 = r8.i
            r2.b(r9)
            r8.a(r3)
            goto L_0x0028
        L_0x00a8:
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r2 == 0) goto L_0x00c9
            boolean r6 = r2 instanceof com.autonavi.map.fragmentcontainer.page.MapBasePage
            if (r6 == 0) goto L_0x00c9
            com.autonavi.map.fragmentcontainer.page.MapBasePage r2 = (com.autonavi.map.fragmentcontainer.page.MapBasePage) r2
            com.autonavi.map.fragmentcontainer.page.ICQLayerController r2 = r2.getCQLayerController()
            if (r2 == 0) goto L_0x00c9
            com.autonavi.map.fragmentcontainer.page.ICQLayerController$DetailLayerState r2 = r2.getDetailLayerState()
            com.autonavi.map.fragmentcontainer.page.ICQLayerController$DetailLayerState r6 = com.autonavi.map.fragmentcontainer.page.ICQLayerController.DetailLayerState.EXPAND
            if (r2 != r6) goto L_0x00c9
            com.autonavi.minimap.base.overlay.MapPointOverlay r2 = r8.e
            r5 = 2
            r2.setAnimatorType(r5)
            goto L_0x00ca
        L_0x00c9:
            r4 = 0
        L_0x00ca:
            com.autonavi.minimap.base.overlay.MapPointOverlay r2 = r8.e
            com.autonavi.minimap.base.overlay.MapPointOverlayItem r3 = (com.autonavi.minimap.base.overlay.MapPointOverlayItem) r3
            r2.setItem(r3)
            if (r4 == 0) goto L_0x0028
            r8.c()
            goto L_0x0028
        L_0x00d8:
            com.autonavi.minimap.base.overlay.MapPointOverlayItem r2 = new com.autonavi.minimap.base.overlay.MapPointOverlayItem
            com.autonavi.common.model.GeoPoint r3 = r3.getGeoPoint()
            int r4 = com.autonavi.minimap.R.drawable.b_poi_hl
            r2.<init>(r3, r4)
            java.util.HashMap r3 = r2.getExtra()
            java.lang.String r4 = "is_save"
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            r3.put(r4, r6)
            com.autonavi.minimap.base.overlay.MapPointOverlay r3 = r8.d
            r3.setOverlayOnTop(r5)
            com.autonavi.minimap.base.overlay.MapPointOverlay r3 = r8.d
            r3.setShowFocusTop(r5)
            com.autonavi.minimap.base.overlay.MapPointOverlay r3 = r8.d
            r3.setItem(r2)
            com.autonavi.minimap.base.overlay.MapPointOverlay r3 = r8.d
            r3.setFocus(r2, r5)
            xr r2 = r8.i
            r2.b(r9)
            goto L_0x0028
        L_0x0109:
            java.lang.Class<com.autonavi.minimap.map.TrafficOverlayItem> r2 = com.autonavi.minimap.map.TrafficOverlayItem.class
            boolean r2 = r2.isInstance(r3)
            if (r2 == 0) goto L_0x0028
            com.autonavi.minimap.map.TrafficOverlayItem r3 = (com.autonavi.minimap.map.TrafficOverlayItem) r3
            if (r3 == 0) goto L_0x0028
            com.autonavi.minimap.map.ITrafficTopic r2 = r3.getTopic()
            if (r2 == 0) goto L_0x0028
            java.util.ArrayList r2 = r2.getSubinfo()
            if (r2 == 0) goto L_0x0028
            com.autonavi.minimap.map.ITrafficTopic r2 = r3.getTopic()
            java.util.ArrayList r2 = r2.getSubinfo()
            r2.size()
            goto L_0x0028
        L_0x012e:
            return r10
        L_0x012f:
            android.util.SparseArray<xn$d> r10 = r8.p
            r10.remove(r9)
            xr r10 = r8.i
            r10.a(r9)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xn.solveSavedFocusWithKey(int, boolean):java.util.List");
    }

    public void recoverSubwayHighlight() {
        if (this.d != null && this.d.getSize() > 0) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) this.d.getItem(0);
            if (pointOverlayItem instanceof MapPointOverlayItem) {
                a((MapPointOverlayItem) pointOverlayItem);
            }
        }
    }

    private void a(MapPointOverlayItem mapPointOverlayItem) {
        if (mapPointOverlayItem != null) {
            List<Long> subWayActiveIds = mapPointOverlayItem.getSubWayActiveIds();
            if (subWayActiveIds != null && subWayActiveIds.size() > 0) {
                ArrayList arrayList = new ArrayList();
                for (Long l2 : subWayActiveIds) {
                    arrayList.add(l2.toString());
                }
                this.l.a((String[]) arrayList.toArray(new String[arrayList.size()]));
            }
        }
    }

    public void setPoiDetailDelegate(IPoiDetailDelegate iPoiDetailDelegate) {
        this.m = iPoiDetailDelegate;
        if (this.m == null || !this.m.isPoiDetailPageEnabled()) {
            this.h.setBubbleEnable(true);
        } else {
            this.h.setBubbleEnable(false);
        }
    }

    public void setMapCommonOverlayListener(btu btu) {
        this.s = btu;
    }

    public void dimissTips() {
        if (this.m != null) {
            this.m.dimissFooter();
        }
    }

    public void handleTrafficItemClick(PageBundle pageBundle) {
        if (pageBundle != null) {
            if (this.m != null) {
                this.m.dimissFooter();
                if (this.d != null) {
                    this.d.clear();
                }
                if (this.e != null) {
                    this.e.clear();
                }
            }
            IMapEventListener iMapEventListener = (IMapEventListener) ank.a(IMapEventListener.class);
            if (iMapEventListener != null) {
                iMapEventListener.a(pageBundle, this.m);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(xq xqVar) {
        if (xqVar != null) {
            if (xqVar != this.d) {
                this.d.clear();
            }
            if (xqVar != this.f) {
                this.f.clear();
            }
            if (xqVar != this.e) {
                this.e.clear();
            }
            if (!(xqVar == this.h || this.h.getFocusItem() == null)) {
                this.h.clearFocus();
            }
        }
    }

    public boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        String str;
        if (getGeoCodeOverlay().isVisible() && getGeoCodeOverlay().isClickable()) {
            this.h.clearFocus();
            getMapPointOverlay().clear();
            getTrafficPointOverlay().clear();
            clearScenicSelectMapPois();
            a((xq) this.e);
            GeocodePOI geocodePOI = (GeocodePOI) POIFactory.createPOI("", geoPoint).as(GeocodePOI.class);
            int i2 = 1;
            if (this.m == null || !this.m.isPoiDetailPageEnabled()) {
                getGeoCodeOverlay().setItem(new MapPointOverlayItem(geocodePOI.getPoint(), R.drawable.b_poi_hl));
                getGeoCodeOverlay().setOverlayOnTop(true);
            }
            PageBundle pageBundle = new PageBundle();
            String string = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
            String string2 = AMapAppGlobal.getApplication().getString(R.string.getting_address);
            geocodePOI.setName(string);
            OverlayManagerImpl$2 overlayManagerImpl$2 = new OverlayManagerImpl$2(this, geoPoint);
            if (TextUtils.isEmpty(string2)) {
                string2 = geocodePOI.getName();
            }
            pageBundle.putString("mainTitle", string2);
            pageBundle.putString("viceTitle", null);
            pageBundle.putObject("POI", geocodePOI);
            if ((this.s == null || !this.s.onShowGeoPoiDetailView(pageBundle, 0)) && this.m != null) {
                this.m.showPoiFooter(pageBundle, 0, overlayManagerImpl$2);
            }
            if (this.l == null) {
                return false;
            }
            int i3 = this.l.s() ? 1 : 2;
            apr apr = (apr) defpackage.esb.a.a.a(apr.class);
            if (!(apr != null && apr.a(AMapPageUtil.getPageContext()))) {
                i2 = 2;
            }
            if (!TextUtils.isEmpty(geocodePOI.getAdCode())) {
                str = geocodePOI.getAdCode();
            } else {
                str = geocodePOI.getCityCode();
            }
            if (TextUtils.isEmpty(str)) {
                GeoPoint point = geocodePOI.getPoint();
                if (point != null) {
                    str = String.valueOf(point.getAdCode());
                }
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("from", this.l.w());
                jSONObject.put("lat", geoPoint.getLatitude());
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, geoPoint.getLongitude());
                jSONObject.put("status", i3);
                jSONObject.put("itemId", i2);
                jSONObject.put(AutoJsonUtils.JSON_ADCODE, str);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_LONGPRESS, jSONObject);
        }
        return false;
    }

    public boolean onLabelClick(List<als> list) {
        boolean z;
        String str;
        String str2;
        boolean z2;
        int i2;
        brl.b();
        if (list != null && list.size() > 0) {
            als als = list.get(0);
            int i3 = 2;
            if (als != null && (als.i == 2 || als.i == 90000)) {
                return false;
            }
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            boolean d2 = awo != null ? awo.d(als.i) : false;
            if (als != null && (d2 || als.i == 16777216)) {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext instanceof AbstractBasePage) {
                    AbstractBasePage abstractBasePage = (AbstractBasePage) pageContext;
                    f fVar = (abstractBasePage.getPageContainer() == null || abstractBasePage.getPageContainer().getCureentRecordPage() == null || !(abstractBasePage.getPageContainer().getCureentRecordPage() instanceof f)) ? pageContext instanceof f ? (f) pageContext : null : (f) abstractBasePage.getPageContainer().getCureentRecordPage();
                    if (fVar != null) {
                        return false;
                    }
                }
                if (this.f.isVisible() && this.f.isClickable()) {
                    if (als.i != 16777216) {
                        i2 = Integer.parseInt(als.b, 36);
                        z2 = false;
                    } else {
                        i2 = Integer.parseInt(als.b);
                        z2 = true;
                    }
                    showTrafficFooter(i2, als.e, als.f, als.g, z2);
                    return false;
                }
            }
            je jeVar = (je) defpackage.esb.a.a.a(je.class);
            if ((jeVar == null || !jeVar.a(als)) && this.d.isVisible() && this.d.isClickable() && this.d.isVisible() && this.d.isClickable()) {
                als als2 = list.get(0);
                if (als2 != null) {
                    int i4 = als2.h;
                }
                if (als2 == null || TextUtils.isEmpty(als2.a)) {
                    return false;
                }
                if (this.t != null) {
                    for (bts b2 : this.t) {
                        b2.b();
                    }
                }
                this.h.clearFocus();
                getGeoCodeOverlay().clear();
                a((xq) this.d);
                if (als2.h != 1) {
                    clearScenicSelectMapPois();
                    if (this.m == null || !this.m.isPoiDetailPageEnabled()) {
                        this.d.setOverlayOnTop(true);
                        this.d.setShowFocusTop(true);
                        MapPointOverlayItem mapPointOverlayItem = new MapPointOverlayItem(new GeoPoint(als2.e, als2.f), R.drawable.b_poi_hl);
                        mapPointOverlayItem.setSubWayActiveIds(this.w);
                        this.d.setItem(mapPointOverlayItem);
                    }
                    z = false;
                } else {
                    this.a = true;
                    this.d.setOverlayOnTop(false);
                    this.d.clear();
                    z = true;
                }
                if ((this.m == null || !this.m.isPoiDetailPageEnabled()) && this.l != null) {
                    this.l.a(new GLGeoPoint(als2.e, als2.f));
                }
                POI createPOI = POIFactory.createPOI(als2.a, new GeoPoint(als2.e, als2.f));
                if (z && createPOI.getPoiExtra() != null) {
                    createPOI.getPoiExtra().put(IOverlayManager.POI_EXTRA_IS_SCENIC, Boolean.TRUE);
                }
                if (!TextUtils.isEmpty(als2.b) && !"0".equals(als2.b)) {
                    createPOI.setId(als2.b);
                }
                this.i.a.a();
                b(createPOI, als2.a);
                a(createPOI, (PageBundle) null, (Callback<Integer>) new OverlayManagerImpl$3<Integer>(this, createPOI));
                if (als2.h == 2) {
                    String stringValue = new MapSharePreference((String) MiniAppIFloorManager.SP_NAME_indoor_config).getStringValue(MiniAppIFloorManager.SP_KEY_indoor_building_poiid, "");
                    if (!(createPOI == null || TextUtils.isEmpty(stringValue) || this.l == null)) {
                        int i5 = this.l.s() ? 1 : 2;
                        apr apr = (apr) defpackage.esb.a.a.a(apr.class);
                        if (apr != null && apr.a(AMapPageUtil.getPageContext())) {
                            i3 = 1;
                        }
                        if (!TextUtils.isEmpty(createPOI.getAdCode())) {
                            str2 = createPOI.getAdCode();
                        } else {
                            str2 = createPOI.getCityCode();
                        }
                        if (TextUtils.isEmpty(str2)) {
                            GeoPoint point = createPOI.getPoint();
                            if (point != null) {
                                str2 = String.valueOf(point.getAdCode());
                            }
                        }
                        GeoPoint point2 = createPOI.getPoint();
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("poiName", createPOI.getName());
                            jSONObject.put(TrafficUtil.POIID, createPOI.getId());
                            jSONObject.put("parent", stringValue);
                            jSONObject.put("from", this.l.w());
                            jSONObject.put("lat", point2.getLatitude());
                            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, point2.getLongitude());
                            jSONObject.put("status", i5);
                            jSONObject.put("itemId", i3);
                            jSONObject.put(AutoJsonUtils.JSON_ADCODE, str2);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_POITIP, jSONObject);
                    }
                } else if (this.l == null) {
                    return false;
                } else {
                    int i6 = this.l.s() ? 1 : 2;
                    apr apr2 = (apr) defpackage.esb.a.a.a(apr.class);
                    if (apr2 != null && apr2.a(AMapPageUtil.getPageContext())) {
                        i3 = 1;
                    }
                    if (!TextUtils.isEmpty(createPOI.getAdCode())) {
                        str = createPOI.getAdCode();
                    } else {
                        str = createPOI.getCityCode();
                    }
                    if (TextUtils.isEmpty(str)) {
                        GeoPoint point3 = createPOI.getPoint();
                        if (point3 != null) {
                            str = String.valueOf(point3.getAdCode());
                        }
                    }
                    GeoPoint point4 = createPOI.getPoint();
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("from", this.l.w());
                        jSONObject2.put("lat", point4 != null ? Double.valueOf(point4.getLatitude()) : "");
                        jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, point4 != null ? Double.valueOf(point4.getLongitude()) : "");
                        jSONObject2.put("status", i6);
                        jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, als2.b);
                        jSONObject2.put("itemId", i3);
                        jSONObject2.put(AutoJsonUtils.JSON_ADCODE, str);
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                    LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_POITIP, jSONObject2);
                }
            }
        }
        return false;
    }

    public void showTrafficFooter(int i2, int i3, int i4, int i5, boolean z) {
        if (!this.k.checkMutex()) {
            if (!agr.b(this.u)) {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext instanceof MapBasePage) {
                    ICQLayerController cQLayerController = ((MapBasePage) pageContext).getCQLayerController();
                    if (cQLayerController != null && cQLayerController.isShowing() && cQLayerController.isFavWhenShow() && this.h.getFocusItem() == null) {
                        cQLayerController.dismissCQLayer(true);
                    }
                }
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_message), 0);
                return;
            }
            if (this.f.isVisible() && this.f.isClickable()) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt(IOverlayManager.EVENT_ID_KEY, i2);
                pageBundle.putBoolean(IOverlayManager.TRAFFIC_GROUP_FLAG_KEY, z);
                handleTrafficItemClick(pageBundle);
                if (this.t != null) {
                    for (bts c2 : this.t) {
                        c2.c();
                    }
                }
                this.h.clearFocus();
                this.f.setItem(i3, i4, (float) i5, String.valueOf(i2));
                this.f.setOverlayOnTop(true);
                if (z) {
                    LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B004");
                }
            }
            this.k.doMutex();
        }
    }

    public boolean onNonFeatureClick() {
        aho.a(new Runnable() {
            public final void run() {
                xn.this.d.clear();
                xn.this.d.setOverlayOnTop(false);
                xn.this.f.setOverlayOnTop(false);
                xn.this.f.clear();
                xn.this.e.clear();
            }
        });
        return false;
    }

    /* access modifiers changed from: private */
    public static void b(POI poi, String str) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        PoiTextTemplate poiTextTemplate = new PoiTextTemplate();
        poiTextTemplate.setValue(str);
        poiTextTemplate.setId(2001);
        poiTextTemplate.setType("text");
        hashMap.put(Integer.valueOf(poiTextTemplate.getId()), poiTextTemplate);
        arrayList.add(poiTextTemplate);
        PoiTextTemplate poiTextTemplate2 = new PoiTextTemplate();
        if (agr.b(AMapAppGlobal.getApplication())) {
            poiTextTemplate2.setValue("                      ");
        } else {
            poiTextTemplate2.setValue("");
        }
        poiTextTemplate2.setId(1010);
        poiTextTemplate2.setType("text");
        hashMap.put(Integer.valueOf(poiTextTemplate2.getId()), poiTextTemplate2);
        arrayList.add(poiTextTemplate2);
        PoiLinkTemplate poiLinkTemplate = new PoiLinkTemplate();
        poiLinkTemplate.setAction("detail");
        poiLinkTemplate.setId(1002);
        poiLinkTemplate.setType("link");
        hashMap.put(Integer.valueOf(poiLinkTemplate.getId()), poiLinkTemplate);
        arrayList.add(poiLinkTemplate);
        PoiButtonTemplate poiButtonTemplate = new PoiButtonTemplate();
        PoiButtonTemplate poiButtonTemplate2 = poiButtonTemplate;
        poiButtonTemplate2.setValue("");
        poiButtonTemplate2.setAction("sebxy");
        poiButtonTemplate.setId(1003);
        poiButtonTemplate.setType(PoiLayoutTemplate.BUTTON);
        hashMap.put(Integer.valueOf(1003), poiButtonTemplate);
        arrayList.add(poiButtonTemplate);
        PoiButtonTemplate poiButtonTemplate3 = new PoiButtonTemplate();
        PoiButtonTemplate poiButtonTemplate4 = poiButtonTemplate3;
        poiButtonTemplate4.setValue("");
        poiButtonTemplate4.setAction(AutoConstants.AUTO_FILE_ROUTE);
        poiButtonTemplate3.setId(2003);
        poiButtonTemplate3.setType(PoiLayoutTemplate.BUTTON);
        hashMap.put(Integer.valueOf(2003), poiButtonTemplate3);
        arrayList.add(poiButtonTemplate3);
        PoiButtonTemplate poiButtonTemplate5 = new PoiButtonTemplate();
        PoiButtonTemplate poiButtonTemplate6 = poiButtonTemplate5;
        poiButtonTemplate6.setValue("");
        poiButtonTemplate6.setAction("nav");
        poiButtonTemplate5.setId(1005);
        poiButtonTemplate5.setType(PoiLayoutTemplate.BUTTON);
        hashMap.put(Integer.valueOf(1005), poiButtonTemplate5);
        arrayList.add(poiButtonTemplate5);
        ((ISearchPoiData) poi.as(ISearchPoiData.class)).setTemplateDataMap(hashMap);
        ((ISearchPoiData) poi.as(ISearchPoiData.class)).setTemplateData(arrayList);
    }

    public void showGpsFooter() {
        if (this.c.d) {
            a((xq) this.c);
            getGpsLayer();
            cdy b2 = cdx.b();
            GpsPOI gpsPOI = (GpsPOI) b2.g.as(GpsPOI.class);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("POI", gpsPOI);
            String b3 = b2.b();
            String c2 = b2.c();
            if (TextUtils.isEmpty(b3)) {
                b3 = gpsPOI.getName();
            }
            pageBundle.putString("mainTitle", b3);
            pageBundle.putString("viceTitle", c2);
            pageBundle.putObject("POI", gpsPOI);
            if ((this.s == null || !this.s.onShowGpsTipView(0, getGpsLayer())) && this.m != null && !this.m.isGpsTipDisable()) {
                this.m.showPoiFooter(pageBundle, 0, new OverlayManagerImpl$5(this));
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(POI poi, PageBundle pageBundle, Callback<Integer> callback) {
        if (pageBundle == null) {
            pageBundle = new PageBundle();
        }
        pageBundle.putObject("POI", poi.as(MapPointPOI.class));
        if ((this.s == null || !this.s.onShowPoiTipView(pageBundle, 0)) && this.m != null) {
            this.m.showPoiFooter(pageBundle, 0, callback);
        }
    }

    public void clearAllMapPointRequest() {
        AosRequest[] aosRequestArr = new AosRequest[this.o.size()];
        this.o.toArray(aosRequestArr);
        for (AosRequest aosRequest : aosRequestArr) {
            if (aosRequest != null) {
                aosRequest.cancel();
            }
        }
        this.o.clear();
    }

    public void clearScenicSelectMapPois() {
        if (this.l != null) {
            this.l.G();
        }
        if (this.a) {
            this.a = false;
            if (this.m != null) {
                this.m.dimissFooter();
            }
        }
    }

    public boolean isScenicSelected() {
        return this.a;
    }

    public boolean onBlankClick() {
        if (this.l == null) {
            return false;
        }
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(this.l.n());
        if (glGeoPoint2GeoPoint == null) {
            return false;
        }
        int j2 = this.l.j(false);
        int i2 = 2;
        if (j2 == 0) {
            j2 = 1;
        } else if (j2 == 1) {
            j2 = 2;
        } else if (j2 == 2) {
            j2 = 3;
        }
        if (this.l.s()) {
            i2 = 1;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", j2);
            jSONObject.put("from", this.l.w());
            jSONObject.put("lat", glGeoPoint2GeoPoint.getLatitude());
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, glGeoPoint2GeoPoint.getLongitude());
            jSONObject.put("status", i2);
            jSONObject.put("itemId", 1);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_CLICK_BLANK_CONTENT, jSONObject);
        HashMap hashMap = new HashMap();
        hashMap.put("type", String.valueOf(j2));
        StringBuilder sb = new StringBuilder();
        sb.append(this.l.w());
        hashMap.put("from", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(glGeoPoint2GeoPoint.getLatitude());
        hashMap.put("lat", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(glGeoPoint2GeoPoint.getLongitude());
        hashMap.put(LocationParams.PARA_FLP_AUTONAVI_LON, sb3.toString());
        hashMap.put("status", String.valueOf(i2));
        hashMap.put("itemId", "1");
        kd.b("amap.P00001.0.B156", hashMap);
        return false;
    }

    public void onSelectSubWayActive(List<Long> list) {
        this.w = list;
    }

    public void setIMapPointRequestingCallBack(com.autonavi.map.core.IOverlayManager.b bVar) {
        this.r = bVar;
    }

    public void setGPSShowMode(int i2) {
        this.c.a(i2);
    }

    public void setGPSCenterLocked(boolean z) {
        this.c.a(z);
    }

    public boolean isGPSVisible() {
        return this.c.d;
    }

    public void setGPSVisible(boolean z) {
        this.c.setVisible(z);
    }

    private void c() {
        this.e.setAnimatorType(1);
    }

    public void resetMapPointAnimatorType() {
        this.d.setAnimatorType(2);
    }

    public brp getAffectAreaOverlayManager() {
        return this.j;
    }

    public void deleteSaveFocusKey(int i2) {
        this.p.remove(i2);
        this.i.a(i2);
    }

    public void setIRealtimeBusStateListener(ccz ccz) {
        this.b = ccz;
    }

    public void removeWhenMapDestroy() {
        a();
        clearAllFocus();
        this.h.setVisible(false);
        this.c.setVisible(false);
        this.e.clear();
        this.d.setOverlayOnTop(false);
        this.d.clear();
        this.f.setOverlayOnTop(false);
        this.f.clear();
        this.g.clear();
        this.l.F().c(this.e);
        this.l.F().c(this.d);
        this.l.F().c(this.f);
        this.l.F().c(this.g);
        xr xrVar = this.i;
        bty bty = this.l;
        bty.F().c(xrVar.a.a);
        bty.F().c(xrVar.a.b);
        this.j.c(this.l);
        brl.a();
        if (this.b != null) {
            this.b.b();
        }
    }

    public void restoreWhenMapCreate() {
        if (this.l.aa() != null) {
            this.h.setVisible(true);
            this.c.setVisible(true);
            this.l.F().b((BaseMapOverlay) this.g);
            this.l.F().b((BaseMapOverlay) this.e);
            this.l.F().b((BaseMapOverlay) this.d);
            this.l.F().b((BaseMapOverlay) this.f);
            xr xrVar = this.i;
            bty bty = this.l;
            bty.F().b((BaseMapOverlay) xrVar.a.a);
            bty.F().b((BaseMapOverlay) xrVar.a.b);
            this.j.b(this.l);
            if (this.b != null) {
                this.b.a();
            }
            brl.a(this.l);
            this.d.resumeMarker();
            this.f.resumeMarker();
            b();
        }
    }

    public void clearAllFocus() {
        boolean z;
        if (this.d != null && this.d.getSize() > 0) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) this.d.getItem(0);
            if (pointOverlayItem instanceof MapPointOverlayItem) {
                List<Long> subWayActiveIds = ((MapPointOverlayItem) pointOverlayItem).getSubWayActiveIds();
                if (subWayActiveIds != null && subWayActiveIds.size() > 0) {
                    z = true;
                    if (z && this.l != null) {
                        this.l.E();
                    }
                    this.e.clear();
                    this.d.setOverlayOnTop(false);
                    this.d.clear();
                    this.f.setOverlayOnTop(false);
                    this.f.clear();
                    this.h.clearFocus();
                    this.g.clearFocus();
                    this.i.b = null;
                }
            }
        }
        z = false;
        this.l.E();
        this.e.clear();
        this.d.setOverlayOnTop(false);
        this.d.clear();
        this.f.setOverlayOnTop(false);
        this.f.clear();
        this.h.clearFocus();
        this.g.clearFocus();
        this.i.b = null;
    }

    public int getGpsAngle() {
        return (int) LocationInstrument.getInstance().getLatestLocation().getBearing();
    }

    public static /* synthetic */ String a(FavoritePOI favoritePOI) {
        Serializable serializable = favoritePOI.getPoiExtra().get("titleName");
        if (serializable != null) {
            String obj = serializable.toString();
            if (!TextUtils.isEmpty(obj)) {
                return obj;
            }
        }
        if (!TextUtils.isEmpty(favoritePOI.getCommonName())) {
            return favoritePOI.getCommonName();
        }
        String customName = favoritePOI.getCustomName();
        if (!TextUtils.isEmpty(customName)) {
            return customName;
        }
        String name = favoritePOI.getName();
        return TextUtils.isEmpty(name) ? favoritePOI.getAddr() : name;
    }

    public static /* synthetic */ void a(xn xnVar, POI poi, int i2) {
        xnVar.clearAllMapPointRequest();
        AosRequest aosRequest = null;
        xnVar.i.b = null;
        OverlayManagerImpl$6 overlayManagerImpl$6 = new OverlayManagerImpl$6(xnVar, i2, poi instanceof FavoritePOI, poi);
        IMapRequestManager iMapRequestManager = (IMapRequestManager) ank.a(IMapRequestManager.class);
        if (iMapRequestManager != null) {
            if (!TextUtils.isEmpty(poi.getId())) {
                aosRequest = iMapRequestManager.idPoi(poi.getId(), li.a().a(poi.getPoint().x, poi.getPoint().y), 0, overlayManagerImpl$6);
            } else {
                String name = poi.getName();
                if (!TextUtils.isEmpty(name) && !TextUtils.equals(name, AMapAppGlobal.getApplication().getString(R.string.select_point_from_map)) && !TextUtils.equals(name, AMapAppGlobal.getApplication().getString(R.string.my_location)) && !TextUtils.equals(name, AMapAppGlobal.getApplication().getString(R.string.ding_wei_dian))) {
                    aosRequest = iMapRequestManager.xyPoi(name, poi.getPoint(), overlayManagerImpl$6);
                }
            }
            if (aosRequest != null) {
                xnVar.o.add(aosRequest);
            }
        }
    }
}
