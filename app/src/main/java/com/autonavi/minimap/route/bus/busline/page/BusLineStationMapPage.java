package com.autonavi.minimap.route.bus.busline.page;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.RecyclablePagerAdapter;
import com.autonavi.map.widget.RecyclableViewPager;
import com.autonavi.map.widget.RecyclableViewPager.OnPageChangeListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import com.autonavi.minimap.widget.PoiDetailView;
import com.autonavi.minimap.widget.PoiDetailViewFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageAction("amap.extra.route.busline_station_map")
public class BusLineStationMapPage extends AbstractBaseMapPage<duv> implements com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a, launchModeSingleInstance, OnPageChangeListener, defpackage.dul.a {
    public SearchKeywordResultTitleView a;
    public dul b;
    private RecyclableViewPager c;

    class a extends RecyclablePagerAdapter<POI> {
        private List<POI> b;

        public final float getPageWidth(int i) {
            return 1.0f;
        }

        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        /* synthetic */ a(BusLineStationMapPage busLineStationMapPage, List list, byte b2) {
            this(list);
        }

        private a(List<POI> list) {
            super(list);
            this.b = new ArrayList();
            if (list != null) {
                this.b = list;
            }
        }

        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            POI clone = this.b.get(i).clone();
            PoiDetailView createPoiDetailView = PoiDetailViewFactory.createPoiDetailView();
            Collection<? extends POI> collection = null;
            if (createPoiDetailView == null) {
                createPoiDetailView = null;
            } else {
                duv duv = (duv) BusLineStationMapPage.this.mPresenter;
                defpackage.duv.a aVar = new defpackage.duv.a();
                aVar.a = duv.a;
                createPoiDetailView.refreshByScreenState(ags.b(BusLineStationMapPage.this.getActivity()));
                createPoiDetailView.bindEvent(0, aVar);
                createPoiDetailView.bindEvent(3, aVar);
                createPoiDetailView.setPoi(clone);
                StringBuilder sb = new StringBuilder();
                sb.append(i + 1);
                sb.append(".");
                sb.append(clone.getName());
                createPoiDetailView.setMainTitle(sb.toString());
                String str = "";
                if (!clone.getType().equals("") && clone.getType().length() > 5) {
                    str = clone.getType().substring(0, 4);
                }
                if (str.equals("1507")) {
                    if (clone.getPoiExtra().containsKey("ChildStations") && clone.getPoiExtra().get("ChildStations") != null) {
                        ISearchPoiData iSearchPoiData = (ISearchPoiData) clone.as(ISearchPoiData.class);
                        if (iSearchPoiData.getPoiChildrenInfo() != null) {
                            collection = iSearchPoiData.getPoiChildrenInfo().stationList;
                        }
                        if (collection != null) {
                            createPoiDetailView.setViceTitle(((ChildStationPoiData) collection.toArray()[0]).getBuslineKey());
                        }
                    } else if (clone.getPoiExtra().get("station_lines") != null) {
                        createPoiDetailView.setViceTitle(clone.getPoiExtra().get("station_lines").toString());
                    }
                } else if (!str.equals("1505") || clone.getPoiExtra().get("station_lines") == null) {
                    createPoiDetailView.setViceTitle(clone.getAddr());
                } else {
                    createPoiDetailView.setViceTitle(clone.getPoiExtra().get("station_lines").toString());
                }
            }
            viewGroup.addView(createPoiDetailView);
            return createPoiDetailView;
        }

        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    public final void c() {
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public duv createPresenter() {
        return new duv(this);
    }

    public View getMapSuspendView() {
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        ccv ccv = new ccv(AMapPageUtil.getAppContext());
        ccv.addWidget(suspendWidgetHelper.j(), suspendWidgetHelper.k(), 4);
        ccv.addWidget(suspendWidgetHelper.a(false), suspendWidgetHelper.c(), 1);
        ccv.addWidget(suspendWidgetHelper.l(), suspendWidgetHelper.m(), 6);
        ccv.addWidget(suspendWidgetHelper.f(), suspendWidgetHelper.g(), 7);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = agn.a(AMapPageUtil.getAppContext(), 4.0f);
        layoutParams.bottomMargin = agn.a(AMapPageUtil.getAppContext(), 3.0f);
        suspendWidgetHelper.a(suspendWidgetHelper.d());
        suspendWidgetHelper.a(ccv.getSuspendView(), suspendWidgetHelper.d(), layoutParams, 3);
        View suspendView = ccv.getSuspendView();
        if (euk.a()) {
            suspendView.setPadding(suspendView.getPaddingLeft(), suspendView.getPaddingTop() + euk.a(getContext()), suspendView.getPaddingRight(), suspendView.getPaddingBottom());
        }
        return suspendView;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.busline_station_map_fragment);
        View contentView = getContentView();
        this.c = (RecyclableViewPager) contentView.findViewById(R.id.busline_station_bottom_viewpager);
        this.c.setDescendantFocusability(393216);
        this.c.setOnPageChangeListener(this);
        this.c.setUseRecycler(false);
        e();
        this.a = (SearchKeywordResultTitleView) contentView.findViewById(R.id.mapTopInteractiveView);
        this.a.setOnSearchKeywordResultTitleViewListener(this);
        this.a.showListModel(false);
        if (euk.a()) {
            int a2 = euk.a(getContext());
            View findViewById = findViewById(R.id.immersive_status_bar_placeholder);
            ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
            layoutParams.height = a2;
            findViewById.setLayoutParams(layoutParams);
        }
        this.b = new dul(this);
        this.b.b = this;
    }

    public final void e() {
        getBottomTipsContainer().setView(this.c);
    }

    public final void b(int i) {
        if (this.c != null) {
            this.c.setCurrentItem(i);
        }
    }

    public void onPageSelected(int i) {
        if (getSuspendManager() != null) {
            getSuspendManager().d().f();
        }
        ((duv) this.mPresenter).a(i);
        if (getMapView() != null) {
            getMapView().P();
        }
    }

    public final void a() {
        finish();
    }

    public final void b() {
        String keyword = this.a.getKeyword();
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(TrafficUtil.KEYWORD, keyword);
        ((BusLineStationMapPage) ((duv) this.mPresenter).mPage).startPage(BusLineSearchPage.class, pageBundle);
    }

    public final void d() {
        duv duv = (duv) this.mPresenter;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, duv.a);
        ((BusLineStationMapPage) duv.mPage).startPage(BusLineStationListPage.class, pageBundle);
    }

    public final void a(int i) {
        duv duv = (duv) this.mPresenter;
        if (duv.a != null) {
            IBusLineSearchResult iBusLineSearchResult = duv.a;
            if (i >= 10) {
                i = duv.b;
            }
            iBusLineSearchResult.setFocusedPoiIndex(i);
            if (duv.a.getFocusedPoi() != null) {
                BusLineStationMapPage busLineStationMapPage = (BusLineStationMapPage) duv.mPage;
                if (busLineStationMapPage.getSuspendManager() != null) {
                    busLineStationMapPage.getSuspendManager().d().f();
                }
                duv.a(duv.a.getFocusedPoiIndex());
            }
        }
    }

    public final void a(IBusLineSearchResult iBusLineSearchResult) {
        if (iBusLineSearchResult != null) {
            this.c.setAdapter(new a(this, iBusLineSearchResult.getPoiList(1), 0));
        }
    }
}
