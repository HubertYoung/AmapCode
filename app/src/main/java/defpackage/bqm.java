package defpackage;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.IdqMaxCMD;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.idqmax.page.SearchIdqMaxPage;
import com.autonavi.idqmax.page.SearchIdqMaxPresenter$1;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.BaseCQLayerOwner;
import com.autonavi.map.fragmentcontainer.page.IPoiTipViewService;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* renamed from: bqm reason: default package */
/* compiled from: SearchIdqMaxPresenter */
public final class bqm extends MapBasePresenter<SearchIdqMaxPage> implements bgo, com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a {
    public bqh a = new bqh((SearchIdqMaxPage) this.mPage, this.b);
    public final bqi b;
    private String c;
    /* access modifiers changed from: private */
    public ctl d;

    /* renamed from: bqm$a */
    /* compiled from: SearchIdqMaxPresenter */
    public class a extends BaseCQLayerOwner {
        final bzj a;

        public final boolean disableCollapseWhenBack() {
            return true;
        }

        public final boolean enableResetBG() {
            return false;
        }

        public final boolean isCancelHandleSuspendSliding() {
            return false;
        }

        public a(MapBasePage mapBasePage) {
            super(mapBasePage);
            this.a = bqm.this.a.g_();
        }

        public final void onShowCQLayer() {
            super.onShowCQLayer();
            bqm.this.a.c(false);
            ((SearchIdqMaxPage) bqm.this.mPage).l().c();
            if (this.a != null) {
                defpackage.bzj.a b2 = this.a.b();
                if (!(b2 == null || b2.a == 3)) {
                    this.a.a(3);
                }
            }
            ((SearchIdqMaxPage) bqm.this.mPage).getCQLayerController().setLayerVisibility(true);
        }

        public final void onDismissCQLayer(int i) {
            super.onDismissCQLayer(i);
            ((SearchIdqMaxPage) bqm.this.mPage).getMapManager().getOverlayManager().clearAllMapPointRequest();
        }

        public final void onAjxBack() {
            ((SearchIdqMaxPage) bqm.this.mPage).finish();
        }
    }

    public final void d() {
    }

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public bqm(SearchIdqMaxPage searchIdqMaxPage) {
        super(searchIdqMaxPage);
        this.b = new bqi(searchIdqMaxPage.getContext());
    }

    public final boolean onLabelClick(List<als> list) {
        als als = list.get(0);
        if (als.i == 2) {
            return false;
        }
        if (als.i == 9000004 || als.i == 9000003) {
            bqh bqh = this.a;
            if (bqh.d.l() != null) {
                bqh.d.l().sendIdqMaxCmd(IdqMaxCMD.CMD_IDQ_MAX_TRAFFIC_CLICKED, null);
            }
            return super.onLabelClick(list);
        }
        this.a.c(false);
        ((SearchIdqMaxPage) this.mPage).l().c();
        bzj g_ = this.a.g_();
        if (g_ != null) {
            defpackage.bzj.a b2 = g_.b();
            if (!(b2 == null || b2.a == 3)) {
                g_.a(3);
            }
        }
        POI createPOI = POIFactory.createPOI(als.a, new GeoPoint(als.e, als.f));
        if (!TextUtils.isEmpty(als.b) && !"0".equals(als.b)) {
            createPOI.setId(als.b);
        }
        String name = createPOI.getName();
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        PoiTextTemplate poiTextTemplate = new PoiTextTemplate();
        poiTextTemplate.setValue(name);
        poiTextTemplate.setId(2001);
        poiTextTemplate.setType("text");
        hashMap.put(Integer.valueOf(poiTextTemplate.getId()), poiTextTemplate);
        arrayList.add(poiTextTemplate);
        ((ISearchPoiData) createPOI.as(ISearchPoiData.class)).setTemplateDataMap(hashMap);
        ((ISearchPoiData) createPOI.as(ISearchPoiData.class)).setTemplateData(arrayList);
        IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
        if (iPoiTipViewService != null) {
            ely createPoiTipView = iPoiTipViewService.createPoiTipView(((SearchIdqMaxPage) this.mPage).getBottomTipsContainer().getContainer(), (bid) this.mPage, createPOI);
            createPoiTipView.setSingle(true);
            createPoiTipView.setFromSource("mainmap");
            createPoiTipView.adjustMargin();
            if (createPoiTipView instanceof ely) {
                createPoiTipView.setTipItemEvent(iPoiTipViewService.createPoiTipEvent(false));
            }
            createPoiTipView.refreshByScreenState(ags.b(((SearchIdqMaxPage) this.mPage).getActivity()));
            createPoiTipView.initData(null, createPOI, 2);
            PageBundle a2 = a(createPOI, createPoiTipView);
            a2.putInt("key_tip_request_type", 1);
            a2.putInt("key_tip_type", 0);
            a2.putString("key_subway_activeid", this.c);
            this.a.a(createPOI, a2);
        }
        return super.onLabelClick(list);
    }

    public final void onSelectSubWayActive(List<Long> list) {
        super.onSelectSubWayActive(list);
        StringBuilder sb = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            for (Long l : list) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(l.toString());
                sb2.append(",");
                sb.append(sb2.toString());
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        this.c = sb.toString();
    }

    public final boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        GeocodePOI geocodePOI = (GeocodePOI) POIFactory.createPOI("", geoPoint).as(GeocodePOI.class);
        this.a.c(false);
        ((SearchIdqMaxPage) this.mPage).l().c();
        bzj g_ = this.a.g_();
        if (g_ != null) {
            defpackage.bzj.a b2 = g_.b();
            if (!(b2 == null || b2.a == 3)) {
                g_.a(3);
            }
        }
        ((SearchIdqMaxPage) this.mPage).getCQLayerController().setLayerVisibility(true);
        PageBundle pageBundle = new PageBundle();
        String string = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
        String string2 = AMapAppGlobal.getApplication().getString(R.string.getting_address);
        geocodePOI.setName(string);
        if (TextUtils.isEmpty(string2)) {
            string2 = geocodePOI.getName();
        }
        pageBundle.putString("mainTitle", string2);
        pageBundle.putString("viceTitle", null);
        pageBundle.putObject("POI", geocodePOI);
        IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
        if (iPoiTipViewService != null) {
            AbstractPoiDetailView createPoiDetailViewForCQ = iPoiTipViewService.createPoiDetailViewForCQ();
            createPoiDetailViewForCQ.adjustMargin();
            createPoiDetailViewForCQ.refreshByScreenState(ags.b(((SearchIdqMaxPage) this.mPage).getActivity()));
            createPoiDetailViewForCQ.refreshByScreenState(ags.b(((SearchIdqMaxPage) this.mPage).getActivity()));
            if (createPoiDetailViewForCQ != null) {
                String string3 = pageBundle.getString("mainTitle");
                String string4 = pageBundle.getString("viceTitle");
                POI poi = (POI) pageBundle.getObject("POI");
                if (poi != null) {
                    if (string3 == null && string4 == null) {
                        if (!((FavoritePOI) poi.as(FavoritePOI.class)).isSaved()) {
                            string3 = poi.getName();
                            string4 = poi.getAddr();
                        } else {
                            string3 = ((FavoritePOI) poi.as(FavoritePOI.class)).getCustomName();
                            if (TextUtils.isEmpty(string3)) {
                                string3 = ((FavoritePOI) poi.as(FavoritePOI.class)).getName();
                            }
                            string4 = ((FavoritePOI) poi.as(FavoritePOI.class)).getAddr();
                        }
                    }
                    boolean isInstance = GpsPOI.class.isInstance(poi);
                    View findViewById = createPoiDetailViewForCQ.findViewById(R.id.child_station_ll);
                    TextView textView = (TextView) createPoiDetailViewForCQ.findViewById(R.id.station_num);
                    if (pageBundle.getBoolean("isChildStation")) {
                        findViewById.setVisibility(0);
                        textView.setText(pageBundle.getString("childAlia"));
                    } else {
                        findViewById.setVisibility(8);
                    }
                    createPoiDetailViewForCQ.setMainTitle(string3);
                    createPoiDetailViewForCQ.setViceTitle(string4);
                    ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                    if (iSearchPoiData.getPoiChildrenInfo() != null) {
                        Collection<? extends POI> collection = iSearchPoiData.getPoiChildrenInfo().stationList;
                        if (collection != null && collection.size() > 0) {
                            createPoiDetailViewForCQ.setPoi(((ChildStationPoiData[]) collection.toArray(new ChildStationPoiData[collection.size()]))[0]);
                        }
                    }
                    if (isInstance && TextUtils.isEmpty(poi.getName())) {
                        poi.setName(AMapAppGlobal.getApplication().getString(R.string.my_location));
                    }
                    createPoiDetailViewForCQ.setPoi(poi);
                }
            }
            PageBundle a2 = a(geocodePOI, createPoiDetailViewForCQ);
            a2.putInt("key_tip_request_type", 2);
            a2.putInt("key_tip_type", 1);
            a2.putString("point_type", "2");
            this.a.a((POI) geocodePOI, a2);
        }
        return super.onMapLongPress(motionEvent, geoPoint);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = ((SearchIdqMaxPage) this.mPage).getArguments();
        if (arguments != null) {
            this.a.d.b = ((SearchIdqMaxPage) this.mPage).l();
            boolean z = false;
            if (arguments.containsKey("key_superid")) {
                SuperId.getInstance().reset();
                String string = arguments.getString("key_superid");
                if (string != null) {
                    String[] split = string.split("_");
                    if (split != null && split.length >= 2) {
                        SuperId.getInstance().setBit1(split[0]);
                        SuperId.getInstance().setBit2(split[1]);
                    }
                }
            }
            bqi bqi = this.b;
            if (arguments != null) {
                bqi.c = (InfoliteResult) arguments.getObject("poi_search_result");
                if (bqi.c != null) {
                    bqi.b.a(bqi.c);
                }
                bqi.d = (POI) arguments.get("center_poi");
                bqi.e = bqi.b.b(0);
                if (bqi.e != null) {
                    List<POI> a2 = bxv.a((SearchPoi) bqi.e.as(SearchPoi.class));
                    if (a2 != null && a2.size() > 0) {
                        bqi.g.clear();
                        bqi.g.addAll(a2);
                        for (POI next : a2) {
                            bqi.f.put(next.getId(), next);
                        }
                    }
                }
            }
            bqh bqh = this.a;
            if (bqh.a != null && bqh.a.isAlive()) {
                bqh.i.get(Integer.valueOf(11)).a(bqh.h.c, bqh.a.getArguments(), bqh.h.e);
            }
            bqh bqh2 = this.a;
            if (bqh2.b != null) {
                z = bqh2.b instanceof bqg;
            }
            if (!z || ((SearchIdqMaxPage) this.mPage).a().getVisibility() == 0) {
                InfoliteParam c2 = this.b.b.c();
                if ((c2 == null || !"RQBXY".equals(c2.query_type)) && aaw.c(((SearchIdqMaxPage) this.mPage).getContext())) {
                    this.d = (ctl) defpackage.esb.a.a.a(ctl.class);
                    if (this.d != null) {
                        this.d.a("17", new SearchIdqMaxPresenter$1(this));
                    }
                }
            }
        }
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
    }

    public final void onStart() {
        super.onStart();
        bqh bqh = this.a;
        if (bqh.b != null) {
            bqh.b.i();
        }
        if (bqh.j == 4) {
            bqh.d.i();
        }
        ((SearchIdqMaxPage) this.mPage).getMapView().r(true);
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            Iterator<LayerItem> it = awo.i().iterator();
            while (it.hasNext()) {
                LayerItem next = it.next();
                if (next.getLayer_id() == 610000) {
                    awo.a(next.getData());
                    awo.b(610000);
                    return;
                }
            }
        }
    }

    public final void onResume() {
        super.onResume();
        ((SearchIdqMaxPage) this.mPage).l().b();
    }

    public final void onPause() {
        super.onPause();
        bqh bqh = this.a;
        if (bqh.b != null) {
            bqh.b.j();
        }
        if (bqh.j == 4) {
            bqh.d.j();
        }
        ((SearchIdqMaxPage) this.mPage).g.b();
        ((SearchIdqMaxPage) this.mPage).getMapView().r(false);
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            awo.c(610000);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        bqh bqh = this.a;
        if (bqh.b != null) {
            bqh.b.k();
        }
        if (this.d != null) {
            this.d.a("17");
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        return this.a.a();
    }

    public final void a() {
        if (ON_BACK_TYPE.TYPE_NORMAL == this.a.a()) {
            ((SearchIdqMaxPage) this.mPage).finish();
        }
    }

    public final void c() {
        bid pageContext = AMapPageUtil.getPageContext();
        apr apr = (apr) defpackage.esb.a.a.a(apr.class);
        if (apr != null) {
            apr.b(pageContext);
        }
    }

    private static PageBundle a(POI poi, Object obj) {
        PageBundle pageBundle;
        if (obj instanceof ely) {
            pageBundle = eks.a(poi, (ely) obj);
        } else if (obj instanceof View) {
            pageBundle = eks.a(poi, (View) obj);
        } else {
            MapBasePage.LogCQ(String.format("CQLayerController showCQLayer. type of poiTipView is wrong", new Object[0]));
            return null;
        }
        return pageBundle;
    }

    public final void b() {
        bcb bcb = (bcb) defpackage.esb.a.a.a(bcb.class);
        if (bcb != null) {
            bcb.a().c(this.b.a()).a(((SearchIdqMaxPage) this.mPage).getPageContext());
        }
    }
}
