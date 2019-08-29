package com.autonavi.minimap.basemap.save.page;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.widget.RecyclablePagerAdapter;
import com.autonavi.map.widget.RecyclableViewPager;
import com.autonavi.map.widget.RecyclableViewPager.OnPageChangeListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.basemap.save.widget.SaveSearchTipView;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.BasePoiOverlay;
import com.autonavi.minimap.map.BasePoiOverlayItem;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import defpackage.als;
import defpackage.aud;
import defpackage.bty;
import defpackage.cde;
import defpackage.cqy;
import defpackage.crp;
import defpackage.crr;
import defpackage.crs;
import defpackage.euk;

public class SaveSearchResultMapPage extends MapBasePage< crp > implements launchModeSingleTask {
    int a = -1;
    int b = -1;
    private SuperId c;
    private List<POI> d = new ArrayList();
    private BasePoiOverlay e;
    private aud f = null;
    private cqy g;
    private RecyclablePagerAdapter<POI> h;
    private int i = 1;
    /* access modifiers changed from: private */
    public String j;
    private RecyclableViewPager k;
    private SearchKeywordResultTitleView l = null;
    private crr m;
    private com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a n = new com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a() {
        public final void c() {
        }

        public final void a() {
            SaveSearchResultMapPage.this.setResult(ResultType.CANCEL, (PageBundle) null);
            SaveSearchResultMapPage.this.finish();
        }

        public final void b() {
            SaveSearchResultMapPage.this.setResult(ResultType.CANCEL, new PageBundle());
            SaveSearchResultMapPage.this.finish();
        }

        public final void d() {
            SaveSearchResultMapPage.this.startPage(SaveSearchResultListPage.class, SaveSearchResultMapPage.this.getArguments());
        }
    };

    public class PoiPageAdapter extends RecyclablePagerAdapter<POI> {
        private List<POI> b;

        public float getPageWidth(int i) {
            return 1.0f;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public PoiPageAdapter(List<POI> list) {
            super(list);
            this.b = list;
        }

        public List<POI> getRealItems() {
            return this.b;
        }

        @TargetApi(11)
        public Object instantiateItemFromRecycler(ViewGroup viewGroup, View view, int i) {
            if (this.b == null || this.b.size() == 0) {
                return null;
            }
            int i2 = 0;
            POI poi = this.b.get(0);
            if (poi != null && (!poi.getPoiExtra().containsKey("SrcType") ? TextUtils.isEmpty(poi.getId()) : !("nativepoi".equals((String) poi.getPoiExtra().get("SrcType")) || !TextUtils.isEmpty(poi.getId())))) {
                i2 = 1;
            }
            String str = "";
            if (!(i2 == 1 && i == 0)) {
                StringBuilder sb = new StringBuilder();
                sb.append((i + 1) - i2);
                sb.append(".");
                str = sb.toString();
            }
            if (view == null) {
                view = a(i, this.b);
                if (VERSION.SDK_INT >= 11 && view.isHardwareAccelerated()) {
                    view.setLayerType(2, null);
                }
            }
            SaveSearchTipView saveSearchTipView = (SaveSearchTipView) view;
            if (i >= 0 && i <= this.b.size() - 1) {
                POI poi2 = this.b.get(i);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(poi2.getName());
                saveSearchTipView.setMainTitle(sb2.toString());
                saveSearchTipView.setViceTitle(poi2.getAddr());
                saveSearchTipView.setPoi(poi2);
            }
            if (view != null) {
                try {
                    viewGroup.addView(view);
                } catch (Exception unused) {
                }
            }
            return view;
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View a2 = a(i, this.b);
            try {
                viewGroup.addView(a2);
            } catch (Exception unused) {
            }
            return a2;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        private View a(int i, List<POI> list) {
            if (i < 0 || i > list.size() - 1) {
                return null;
            }
            int i2 = 0;
            if (list.size() > 0) {
                POI poi = list.get(0);
                if (!poi.getPoiExtra().containsKey("SrcType") ? TextUtils.isEmpty(poi.getId()) : !("nativepoi".equals((String) poi.getPoiExtra().get("SrcType")) || !TextUtils.isEmpty(poi.getId()))) {
                    i2 = 1;
                }
            }
            String str = "";
            if (list.get(i) == null) {
                return null;
            }
            if (!(i2 == 1 && i == 0)) {
                StringBuilder sb = new StringBuilder();
                sb.append((i + 1) - i2);
                sb.append(".");
                str = sb.toString();
            }
            return a(i, str);
        }

        private View a(int i, String str) {
            if (i < 0 || i > this.b.size() - 1) {
                return null;
            }
            POI poi = this.b.get(i);
            SaveSearchTipView saveSearchTipView = new SaveSearchTipView(SaveSearchResultMapPage.this);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(poi.getName());
            saveSearchTipView.setMainTitle(sb.toString());
            saveSearchTipView.setViceTitle(poi.getAddr());
            saveSearchTipView.setPoi(poi);
            if (SaveSearchResultMapPage.this.j != null) {
                saveSearchTipView.setFavoriteTyep(SaveSearchResultMapPage.this.j);
            }
            return saveSearchTipView;
        }
    }

    class a implements OnItemClickListener<BasePoiOverlayItem> {
        a() {
        }

        public final /* synthetic */ void onItemClick( bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
            BasePoiOverlayItem basePoiOverlayItem = (BasePoiOverlayItem) obj;
            MapManager mapManager = SaveSearchResultMapPage.this.getMapManager();
            if (mapManager != null) {
                mapManager.getOverlayManager().clearAllFocus();
            }
            SaveSearchResultMapPage.a(SaveSearchResultMapPage.this, basePoiOverlayItem);
        }
    }

    public boolean onPageMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        return true;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.save_search_result_map_fragment);
        View contentView = getContentView();
        a();
        this.m = new crr(this);
        this.l = (SearchKeywordResultTitleView) contentView.findViewById(R.id.mapTopInteractiveView);
        if ( euk.a()) {
            int paddingTop = this.l.getPaddingTop() + euk.a(getContext());
            this.l.setPadding(this.l.getPaddingLeft(), paddingTop, this.l.getPaddingRight(), this.l.getPaddingBottom());
            LayoutParams layoutParams = this.l.getLayoutParams();
            layoutParams.height += euk.a(getContext());
            this.l.setLayoutParams(layoutParams);
        }
        this.l.setOnSearchKeywordResultTitleViewListener(this.n);
        this.k = (RecyclableViewPager) contentView.findViewById(R.id.search_result_map_pager);
        this.k.setUseRecycler(false);
        showViewFooter(this.k);
        this.k.setDescendantFocusability(393216);
        this.k.setOnPageChangeListener(new OnPageChangeListener() {
            private int b = 0;
            private boolean c = false;

            public final void onPageScrollStateChanged(int i) {
            }

            public final void onPageSelected(int i) {
                SaveSearchResultMapPage.this.a(i);
            }

            public final void onPageScrolled(int i, float f, int i2) {
                if (i2 > this.b) {
                    this.c = true;
                } else {
                    this.c = false;
                }
                this.b = i2;
            }
        });
        this.e = new BasePoiOverlay(getMapManager().getMapView());
        addOverlay(this.e);
        BasePoiOverlay basePoiOverlay = this.e;
        a aVar = new a();
        basePoiOverlay.showReversed(true);
        basePoiOverlay.setCheckCover(true);
        basePoiOverlay.setHideIconWhenCovered(false);
        basePoiOverlay.setOnItemClickListener(aVar);
    }

    public void onPageResume() {
        int i2;
        Rect rect;
        float f2;
        int i3;
        int i4;
        super.onPageResume();
        if (this.d != null) {
            this.l.showListModel(false);
            cde suspendManager = getSuspendManager();
            if (suspendManager != null) {
                suspendManager.d().f();
            }
            PageBundle arguments = getArguments();
            String str = "";
            if (arguments.containsKey(TrafficUtil.KEYWORD)) {
                str = arguments.getString(TrafficUtil.KEYWORD);
            }
            this.l.setKeyword(str);
            PageBundle arguments2 = getArguments();
            if (arguments2.get("SELECTED_ITEM_INDEX_DATA_KEY") != null) {
                this.a = arguments2.getInt("SELECTED_ITEM_INDEX_DATA_KEY");
            }
            this.g = new cqy(this.i, this.f, this.a);
            if (this.d.size() == 1) {
                this.l.goneRightButton();
            } else {
                this.l.visiableRightButton();
            }
            if (!(this.a == -1 || getMapManager() == null || getMapManager().getOverlayManager() == null)) {
                getMapManager().getOverlayManager().clearAllFocus();
            }
            cqy cqy = this.g;
            getContext();
            BasePoiOverlay basePoiOverlay = this.e;
            if (cqy.a != null) {
                ArrayList<POI> arrayList = cqy.a.b.d;
                if (!(arrayList == null || arrayList.size() == 0 || basePoiOverlay == null)) {
                    basePoiOverlay.clear();
                    POI poi = arrayList.get(0);
                    int i5 = (!poi.getPoiExtra().containsKey("SrcType") ? !TextUtils.isEmpty(poi.getId()) : "nativepoi".equals((String) poi.getPoiExtra().get("SrcType")) || arrayList.size() <= 0 || !TextUtils.isEmpty(poi.getId())) ? 0 : 1;
                    int size = arrayList.size();
                    int i6 = 0;
                    while (i6 < size) {
                        POI poi2 = arrayList.get(i6);
                        if (poi2 != null) {
                            BasePoiOverlayItem addPoi = basePoiOverlay.addPoi(poi2, i6 > 0 ? i6 - i5 : i6);
                            if (addPoi != null && i6 == cqy.b) {
                                basePoiOverlay.setFocus((PointOverlayItem) addPoi, false);
                            }
                        }
                        i6++;
                    }
                }
            }
            BasePoiOverlayItem basePoiOverlayItem = (BasePoiOverlayItem) this.e.getFocus();
            float f3 = -1.0f;
            if (!(this.f == null || this.g.a == null)) {
                GeoPoint geoPoint = basePoiOverlayItem != null ? basePoiOverlayItem.getGeoPoint() : null;
                MapManager mapManager = getMapManager();
                if (mapManager != null) {
                    bty mapView = mapManager.getMapView();
                    if (mapView != null) {
                        int p = mapView.p();
                        int q = mapView.q();
                        float w = (float) mapView.w();
                        float I = mapView.I();
                        float J = mapView.J();
                        if (geoPoint != null) {
                            p = geoPoint.x;
                            q = geoPoint.y;
                        }
                        if (this.f == null || this.f.b.a == null || this.f.b.a.b == null) {
                            i2 = p;
                            ArrayList<POI> overlayPois = this.e.getOverlayPois();
                            if (overlayPois == null || overlayPois.size() <= 1) {
                                rect = null;
                            } else {
                                int i7 = q;
                                int i8 = i7;
                                int i9 = i2;
                                int i10 = i9;
                                for (POI next : overlayPois) {
                                    i9 = Math.min(i9, next.getPoint().x);
                                    i7 = Math.min(i7, next.getPoint().y);
                                    i10 = Math.max(i10, next.getPoint().x);
                                    i8 = Math.max(i8, next.getPoint().y);
                                }
                                rect = new Rect(i9, i7, i10, i8);
                                f3 = getMapManager().getMapView().a(i9, i7, i10, i8) - 1.0f;
                            }
                            f2 = (overlayPois == null || overlayPois.size() != 1) ? f3 : 17.0f;
                        } else {
                            Double[] dArr = this.f.b.a.b;
                            GeoPoint geoPoint2 = new GeoPoint(dArr[0].doubleValue(), dArr[1].doubleValue());
                            i2 = p;
                            GeoPoint geoPoint3 = new GeoPoint(dArr[2].doubleValue(), dArr[3].doubleValue());
                            f2 = getMapManager().getMapView().a(geoPoint2.x, geoPoint2.y, geoPoint3.x, geoPoint3.y) - 0.5f;
                            rect = new Rect(geoPoint2.x, geoPoint2.y, geoPoint3.x, geoPoint3.y);
                        }
                        float f4 = f2 > 0.0f ? f2 : w;
                        if (rect != null) {
                            i4 = rect.centerX();
                            i3 = rect.centerY();
                        } else {
                            i3 = q;
                            i4 = i2;
                        }
                        mapView.a(i4, i3, f4, I, J);
                    }
                }
            }
            c();
            if (getMapView() != null) {
                getMapView().g(false);
            }
        }
    }

    public void onPagePause() {
        super.onPagePause();
        if (!(getMapManager() == null || getMapManager().getMapView() == null)) {
            getMapManager().getMapView().g(true);
        }
        if (this.e.getFocus() == null) {
            this.a = -1;
        }
    }

    public void onPageDestroy() {
        super.onPageDestroy();
        if (this.l != null) {
            this.l.setOnSearchKeywordResultTitleViewListener(null);
        }
    }

    public final void a() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.f = (aud) arguments.getObject("poi_search_result");
            if (!(this.f == null || this.f.b == null || this.f.b.d == null || this.f.b.d.size() <= 0)) {
                this.a = 0;
            }
            this.d = (List) arguments.getObject("poi_list");
            if (this.d == null && this.f != null) {
                this.d = this.f.b.d;
            }
            if (arguments.containsKey("SUPER_ID")) {
                this.c = (SuperId) arguments.get("SUPER_ID");
            }
            if (arguments.containsKey("PAGE_NUM")) {
                this.i = arguments.getInt("PAGE_NUM");
            }
            if (arguments.containsKey("address")) {
                this.j = arguments.getString("address");
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public crp createPresenter() {
        return new crp(this);
    }

    public View getMapSuspendView() {
        return this.m.a();
    }

    private void c() {
        if (this.d != null && this.d.size() > 0 && this.a != -1) {
            a(this.d);
            if (this.b == -1) {
                this.k.setTag("POI");
                this.h = new PoiPageAdapter(this.d);
                this.k.setAdapter(this.h);
                this.h.notifyDataSetChanged();
                if (this.a != -1) {
                    this.k.setCurrentItem(this.a);
                    if (this.a == 0) {
                        a(this.a);
                    }
                    showViewFooter(this.k);
                }
            }
        }
    }

    private void d() {
        if (this.e != null) {
            int i2 = -1;
            if (this.g.a != null) {
                i2 = this.a;
            }
            this.d.get(i2);
            if (this.e.getLastFocusedIndex() != i2) {
                this.e.setFocus(i2, true);
            }
            MapManager mapManager = getMapManager();
            if (mapManager != null) {
                bty mapView = mapManager.getMapView();
                if (mapView != null) {
                    mapView.P();
                }
            }
        }
    }

    public boolean onPageLabelClick(List< als > list) {
        if (list != null && list.size() > 0) {
            this.a = -1;
            this.e.clearFocus();
        }
        return super.onPageLabelClick(list);
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        if (this.f != null && this.d != null && this.a != -1 && this.a < this.d.size() && this.d.get(this.a) != null && ((ISearchPoiData) this.d.get(this.a).as(ISearchPoiData.class)) != null) {
            this.a = i2;
            d();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("index", String.valueOf(i2));
                if (this.d != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.d.get(i2).getId());
                    jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, sb.toString());
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
    }

    private static void a(List<POI> list) {
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (POI next : list) {
                if (next != null) {
                    arrayList.add(next);
                }
            }
            list.clear();
            list.addAll(arrayList);
        }
    }

    static /* synthetic */ void a(SaveSearchResultMapPage saveSearchResultMapPage, BasePoiOverlayItem basePoiOverlayItem) {
        if (!(saveSearchResultMapPage.g == null || crs.a(saveSearchResultMapPage.i, saveSearchResultMapPage.f) == null || crs.a(saveSearchResultMapPage.i, saveSearchResultMapPage.f).size() <= 0)) {
            if (basePoiOverlayItem.getPageIndex() >= 10) {
                saveSearchResultMapPage.a = 0;
            }
            saveSearchResultMapPage.a = saveSearchResultMapPage.e.getItemIndex(basePoiOverlayItem);
            if (saveSearchResultMapPage.d != null && saveSearchResultMapPage.d.size() > 0) {
                saveSearchResultMapPage.b = -1;
                saveSearchResultMapPage.c();
                if (saveSearchResultMapPage.d != null && saveSearchResultMapPage.d.size() > 0) {
                    int i2 = saveSearchResultMapPage.a;
                    if (saveSearchResultMapPage.k.getTag().toString().equals("POI") && i2 != -1) {
                        saveSearchResultMapPage.showViewFooter(saveSearchResultMapPage.k);
                        saveSearchResultMapPage.k.setCurrentItem(i2, true);
                        if (i2 == 0) {
                            saveSearchResultMapPage.a(i2);
                        }
                    }
                }
            }
        }
    }
}
