package com.autonavi.minimap.route.bus.busline.page;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.busline.overlay.BusLinePointOverlay;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline;
import com.autonavi.minimap.widget.AmapTextView;
import com.autonavi.widget.ui.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class BusLineToMapPage extends AbstractBaseMapPage<duw> implements OnClickListener, btz, IVoiceCmdResponder {
    public MyViewPageAdapter a;
    public dum b;
    public int c = 2;
    public int d = -1;
    private TitleBar e;
    private ViewPager f;
    private MvpImageView g;

    public class MyViewPageAdapter extends PagerAdapter {
        public int a;
        private List<View> c = new ArrayList();
        private Bus d = null;

        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        MyViewPageAdapter(Bus bus) {
            this.d = bus;
            a();
        }

        public final int getCount() {
            return this.c.size();
        }

        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            View view = this.c.get(i);
            viewGroup.addView(view);
            return view;
        }

        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            if (i < this.c.size()) {
                viewGroup.removeView(this.c.get(i));
            }
        }

        public final void notifyDataSetChanged() {
            int count = getCount();
            for (int i = 1; i < count; i++) {
                TextView textView = (TextView) this.c.get(i).findViewById(R.id.busline_station_realtime_textview);
                String a2 = a(i);
                if (TextUtils.isEmpty(a2)) {
                    ebj.a(textView, (String) null, (String) null);
                    textView.setVisibility(8);
                } else {
                    int i2 = i - 1;
                    if (i2 >= this.d.stations.length) {
                        ebj.a(textView, (String) null, (String) null);
                        textView.setVisibility(8);
                    } else {
                        ebj.a(textView, a2, this.d.stations[i2]);
                        textView.setVisibility(0);
                    }
                }
            }
            super.notifyDataSetChanged();
        }

        private String a(int i) {
            return ebj.a(BusLineToMapPage.this.getContext(), this.d.key_name, ((duw) BusLineToMapPage.this.mPresenter).d(i), this.a);
        }

        private void a() {
            View view;
            View view2;
            this.c.clear();
            if (this.d.stations != null) {
                List<View> list = this.c;
                Bus bus = this.d;
                if (bus == null) {
                    view = null;
                } else {
                    view = LayoutInflater.from(BusLineToMapPage.this.getContext()).inflate(R.layout.v4_busline_map_line_info, null);
                    StringBuilder sb = new StringBuilder();
                    sb.append(bus.startName);
                    sb.append(" - ");
                    sb.append(bus.endName);
                    ((AmapTextView) view.findViewById(R.id.busline_staticon_info_textview)).setText(axs.a(sb.toString()));
                    AmapTextView amapTextView = (AmapTextView) view.findViewById(R.id.busline_station_timestart_textview);
                    AmapTextView amapTextView2 = (AmapTextView) view.findViewById(R.id.busline_station_timeend_textview);
                    int i = bus.startTime;
                    int i2 = bus.endTime;
                    if (i < 0) {
                        amapTextView.setVisibility(8);
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(i / 100);
                        sb2.append(":");
                        sb2.append(axt.a(i % 100));
                        amapTextView.setText(sb2.toString());
                        amapTextView.setVisibility(0);
                    }
                    if (i2 < 0) {
                        amapTextView2.setVisibility(8);
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(i2 / 100);
                        sb3.append(":");
                        sb3.append(axt.a(i2 % 100));
                        amapTextView2.setText(sb3.toString());
                        amapTextView2.setVisibility(0);
                    }
                    AmapTextView amapTextView3 = (AmapTextView) view.findViewById(R.id.busline_station_price_textview);
                    String ticketDesc = bus.getTicketDesc();
                    if (!TextUtils.isEmpty(ticketDesc)) {
                        amapTextView3.setText(ticketDesc);
                        amapTextView3.setVisibility(0);
                    } else {
                        amapTextView3.setVisibility(8);
                    }
                    view.findViewById(R.id.btn_bus_map_return);
                }
                list.add(view);
                int i3 = 0;
                while (i3 < this.d.stations.length) {
                    List<View> list2 = this.c;
                    Bus bus2 = this.d;
                    i3++;
                    if (bus2 == null) {
                        view2 = null;
                    } else {
                        view2 = LayoutInflater.from(BusLineToMapPage.this.getContext()).inflate(R.layout.v4_busline_flipper, null);
                        String str = bus2.stations[i3 - 1];
                        ((TextView) view2.findViewById(R.id.busline_staticon_info_textview)).setText(str);
                        TextView textView = (TextView) view2.findViewById(R.id.busline_station_realtime_textview);
                        String a2 = a(i3);
                        if (TextUtils.isEmpty(a2)) {
                            ebj.a(textView, (String) null, (String) null);
                            textView.setVisibility(8);
                        } else {
                            ebj.a(textView, a2, str);
                            textView.setVisibility(0);
                        }
                        ((LinearLayout) view2.findViewById(R.id.busline_station_search_layout)).setVisibility(0);
                        view2.findViewById(R.id.busline_search_around).setOnClickListener(BusLineToMapPage.this);
                        view2.findViewById(R.id.busline_search_route).setOnClickListener(BusLineToMapPage.this);
                        view2.findViewById(R.id.busline_staticon_info_layout).setOnClickListener(BusLineToMapPage.this);
                    }
                    list2.add(view2);
                }
            }
        }
    }

    public long getScene() {
        return 4194304;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public duw createPresenter() {
        return new duw(this);
    }

    public View getMapSuspendView() {
        Context context = getContext() != null ? getContext() : AMapPageUtil.getAppContext();
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        ccv ccv = new ccv(context);
        ccv.addWidget(suspendWidgetHelper.a(false), suspendWidgetHelper.c(), 1);
        LayoutParams d2 = d();
        d2.leftMargin = agn.a(context, 4.0f);
        d2.bottomMargin = agn.a(context, 3.0f);
        suspendWidgetHelper.a(suspendWidgetHelper.d());
        suspendWidgetHelper.a(ccv.getSuspendView(), suspendWidgetHelper.d(), d2, 3);
        suspendWidgetHelper.a(suspendWidgetHelper.e(true));
        ccv.addWidget(suspendWidgetHelper.e(true), suspendWidgetHelper.q(), 2);
        ccv.addWidget(suspendWidgetHelper.f(), suspendWidgetHelper.g(), 7);
        ccv.addWidget(suspendWidgetHelper.c(false), suspendWidgetHelper.k(), 4);
        this.g = new MvpImageView(context);
        this.g.setImageResource(R.drawable.icon_c14_selector);
        this.g.setBackgroundResource(R.drawable.rt_bus_around_refresh_bg_selector);
        this.g.setContentDescription("实时公交");
        LayoutParams d3 = d();
        d3.rightMargin = agn.a(context, 4.0f);
        ccv.addWidget(this.g, d3, 4);
        NoDBClickUtil.a((View) this.g, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ((duw) BusLineToMapPage.this.mPresenter).a(200, true);
            }
        });
        LayoutParams m = suspendWidgetHelper.m();
        m.bottomMargin = agn.a(context, 3.0f);
        ccv.addWidget(suspendWidgetHelper.l(), m, 6);
        return ccv.getSuspendView();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.busline_searchtomap_fragment);
        requestScreenOrientation(1);
        dys.a((String) "P00265", (String) "B001", 0);
        View contentView = getContentView();
        this.e = (TitleBar) contentView.findViewById(R.id.mapTopInteractiveView);
        this.e.setBackImgContentDescription(getString(R.string.autonavi_back));
        this.e.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ((BusLineToMapPage) ((duw) BusLineToMapPage.this.mPresenter).mPage).finish();
            }
        });
        this.f = (ViewPager) contentView.findViewById(R.id.horizontal_pager);
        this.f.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            boolean a = false;
            int b = 0;

            public final void onPageSelected(int i) {
                this.b = i;
                if (!this.a) {
                    ((duw) BusLineToMapPage.this.mPresenter).c(i);
                }
            }

            public final void onPageScrolled(int i, float f, int i2) {
                super.onPageScrolled(i, f, i2);
                this.a = true;
            }

            public final void onPageScrollStateChanged(int i) {
                super.onPageScrollStateChanged(i);
                this.a = true;
                if (i == 0) {
                    this.a = false;
                    ((duw) BusLineToMapPage.this.mPresenter).c(this.b);
                }
            }
        });
        this.b = new dum(this);
        this.b.h = (a) this.mPresenter;
        if (getMapView() != null) {
            this.d = getMapView().w();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_bus_map_return) {
            ((duw) this.mPresenter).b.b();
        } else if (id == R.id.busline_search_around) {
            duw duw = (duw) this.mPresenter;
            if (duw.c != null && duw.c.stationX != null && duw.c.stationY != null && duw.c.stations != null) {
                PageBundle pageBundle = new PageBundle();
                POI createPOI = POIFactory.createPOI("", new GeoPoint());
                createPOI.setName(duw.c.stations[duw.d]);
                createPOI.setPoint(new GeoPoint(duw.c.stationX[duw.d], duw.c.stationY[duw.d]));
                if (duw.c.stationpoiid2 != null && duw.c.stationpoiid2.length > duw.d) {
                    createPOI.setId(duw.c.stationpoiid2[duw.d]);
                }
                createPOI.setAdCode(duw.c.areacode);
                pageBundle.putObject("POI", createPOI);
                ((BusLineToMapPage) duw.mPage).startPage((String) "amap.search.action.category", pageBundle);
            }
        } else if (id == R.id.busline_search_route) {
            duw duw2 = (duw) this.mPresenter;
            duq duq = duw2.a;
            RouteType routeType = RouteType.BUS;
            Bus bus = duw2.c;
            int i = duw2.d;
            duq.b = bus;
            duq.c = i;
            duq.a(routeType);
        } else {
            if (id == R.id.busline_staticon_info_layout) {
                duw duw3 = (duw) this.mPresenter;
                if (((BusLineToMapPage) duw3.mPage).isAlive()) {
                    if (duw3.c == null || duw3.c.stationpoiid2 == null || duw3.c.stationpoiid2.length <= duw3.d || TextUtils.isEmpty(duw3.c.stationpoiid2[duw3.d]) || duw3.c.stationX == null || duw3.c.stationY == null || duw3.c.stations == null) {
                        ToastHelper.showToast(((BusLineToMapPage) duw3.mPage).getString(R.string.route_no_detail_information));
                        return;
                    }
                    PageBundle pageBundle2 = new PageBundle();
                    POI createPOI2 = POIFactory.createPOI();
                    if (duw3.c.type == 1) {
                        createPOI2.setType("150700");
                    } else if (duw3.c.type == 2) {
                        createPOI2.setType("150500");
                    } else if (duw3.c.type == 3) {
                        createPOI2.setType("150600");
                    }
                    createPOI2.setName(duw3.c.stations[duw3.d]);
                    createPOI2.setPoint(new GeoPoint(duw3.c.stationX[duw3.d], duw3.c.stationY[duw3.d]));
                    createPOI2.setId(duw3.c.stationpoiid2[duw3.d]);
                    createPOI2.setCityCode(duw3.c.areacode);
                    pageBundle2.putObject("POI", createPOI2);
                    pageBundle2.putInt("poi_detail_page_type", 5);
                    ((BusLineToMapPage) duw3.mPage).startPage((String) "amap.search.action.poidetail", pageBundle2);
                }
            }
        }
    }

    public final void a() {
        if (getMapView() != null) {
            getMapView().a(15.0f, 500);
        }
    }

    public final void b() {
        if (getSuspendManager() != null) {
            getSuspendManager().d().f();
        }
    }

    public final void a(int i) {
        if (i >= 0 && i < this.a.getCount() - 1) {
            this.f.setCurrentItem(i + 1, true);
        }
    }

    public final void a(Bus bus, int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(bus.key_name);
        sb.append(getString(R.string.route_detail));
        this.e.setTitle(sb.toString());
        this.a = new MyViewPageAdapter(bus);
        this.f.setAdapter(this.a);
        a(i);
        this.g.setVisibility(z ? 0 : 8);
    }

    public final void a(Bus bus, int i, boolean z, RealTimeBusline realTimeBusline) {
        Rect rect;
        Rect rect2;
        Bus bus2 = bus;
        int i2 = i;
        b();
        if (i2 >= 0) {
            a();
        }
        dum dum = this.b;
        Context context = getContext();
        dum.a();
        int i3 = 0;
        if (!(context == null || bus2 == null)) {
            int[] iArr = bus2.coordX;
            int[] iArr2 = bus2.coordY;
            if (!(iArr == null || iArr.length == 0)) {
                GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
                for (int i4 = 0; i4 < iArr.length; i4++) {
                    geoPointArr[i4] = new GeoPoint(iArr[i4], iArr2[i4]);
                }
                LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(context, 2.5f));
                lineOverlayItem.setFillLineColor(dum.a(bus2.color));
                lineOverlayItem.setFillLineId(R.drawable.map_frontlr);
                lineOverlayItem.setBackgroundColor(-1);
                lineOverlayItem.setBackgrondId(R.drawable.map_lr);
                if (context.getResources().getDisplayMetrics().densityDpi <= 240) {
                    lineOverlayItem.mLineProperty.q = 32.0f;
                }
                dum.c.addItem(lineOverlayItem);
            }
        }
        ViewGroup viewGroup = null;
        if (!(context == null || bus2 == null || bus2.stations == null)) {
            int length = bus2.stations.length;
            while (i3 < length) {
                GeoPoint geoPoint = new GeoPoint(bus2.stationX[i3], bus2.stationY[i3]);
                if (i3 == 0) {
                    PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
                    pointOverlayItem.mDefaultMarker = dum.a.createMarker(-999, 5);
                    dum.a.addItem(pointOverlayItem);
                    PointOverlayItem pointOverlayItem2 = new PointOverlayItem(geoPoint);
                    pointOverlayItem2.mDefaultMarker = dum.b.createMarker(R.drawable.bubble_start, 5);
                    dum.b.addItem(pointOverlayItem2);
                    dum.a(context, i3, geoPoint, bus2.stations[i3]);
                } else if (i3 == length - 1) {
                    PointOverlayItem pointOverlayItem3 = new PointOverlayItem(geoPoint);
                    pointOverlayItem3.mDefaultMarker = dum.a.createMarker(-999, 5);
                    dum.a.addItem(pointOverlayItem3);
                    PointOverlayItem pointOverlayItem4 = new PointOverlayItem(geoPoint);
                    pointOverlayItem4.mDefaultMarker = dum.b.createMarker(R.drawable.bubble_end, 5);
                    dum.b.addItem(pointOverlayItem4);
                    dum.a(context, i3, geoPoint, bus2.stations[i3]);
                } else {
                    PointOverlayItem pointOverlayItem5 = new PointOverlayItem(geoPoint);
                    BusLinePointOverlay busLinePointOverlay = dum.a;
                    String str = bus2.color;
                    View inflate = LayoutInflater.from(context).inflate(R.layout.busline_station_icon, viewGroup);
                    ImageView imageView = (ImageView) inflate.findViewById(R.id.station_icon);
                    int a2 = dum.a(str);
                    if (VERSION.SDK_INT < 15) {
                        imageView.setBackgroundColor(a2);
                    } else {
                        GradientDrawable gradientDrawable = (GradientDrawable) imageView.getBackground();
                        gradientDrawable.setCornerRadius((float) agn.a(context, 7.0f));
                        gradientDrawable.setStroke(agn.a(context, 1.5f), a2);
                    }
                    PointOverlayItem pointOverlayItem6 = pointOverlayItem5;
                    pointOverlayItem6.mDefaultMarker = busLinePointOverlay.createMarker(i3, inflate, 4, 0.0f, 0.0f, false);
                    pointOverlayItem6.mBubbleMarker = dum.a.createMarker(R.drawable.b_poi, 5);
                    dum.a.setBubbleAnimator(2);
                    dum.a.addItem(pointOverlayItem6);
                    dum.g.addBusStationName(geoPoint, bus2.stations[i3], bus2.stationIds[i3], 3);
                }
                i3++;
                viewGroup = null;
            }
            dum.b.setDataInit(true);
        }
        dum.a(bus2, i2);
        if (z) {
            dum.f.b();
        } else if (i2 >= 0) {
            ean ean = dum.f;
            if (ean.c != null) {
                ean.e = null;
                ean.a();
                ean.a(i2);
                PointOverlayItem pointOverlayItem7 = (PointOverlayItem) ean.c.getFocus();
                if (pointOverlayItem7 != null) {
                    ean.a.a(pointOverlayItem7.getGeoPoint().x, pointOverlayItem7.getGeoPoint().y);
                }
            }
        } else {
            ean ean2 = dum.f;
            if (ean2.c != null) {
                ean2.e = null;
                ean2.a();
                dvc dvc = (dvc) ean2.c.getFocus();
                if (dvc == null) {
                    ean2.a((GeoPoint) null);
                    rect = ean2.b.getBound();
                } else {
                    ean2.a(dvc.getGeoPoint());
                    int i5 = dvc.a;
                    LineOverlayItem lineItem = ean2.b.getLineItem(i5);
                    if (lineItem != null) {
                        rect = lineItem.getBound();
                    } else if (i5 != -100) {
                        rect = ean2.b.getBound();
                    } else {
                        if (ean2.a != null) {
                            ean2.a.e(0.0f);
                            ean2.a.e(17);
                        }
                        if (ean2.c == null || dvc == null) {
                            rect2 = null;
                        } else {
                            rect2 = null;
                            ean2.e = null;
                            ean2.a();
                            ean2.a(ean2.c.getItemIndex(dvc));
                            if (dvc.getGeoPoint() != null) {
                                ean2.a.a(dvc.getGeoPoint().x, dvc.getGeoPoint().y);
                            }
                        }
                        rect = rect2;
                    }
                }
                if (rect != null) {
                    ean2.a();
                    int w = ean2.a.w();
                    float a3 = ean2.a(rect);
                    GeoPoint a4 = ean2.a(rect, a3);
                    int compare = Float.compare((float) w, a3);
                    if (compare > 0) {
                        a aVar = new a();
                        ean2.e = aVar;
                        aVar.a = a3;
                        aVar.b = a4.clone();
                    } else if (compare == 0) {
                        a aVar2 = new a();
                        ean2.e = aVar2;
                        aVar2.b = a4.clone();
                    } else if (compare < 0) {
                        a aVar3 = new a();
                        ean2.e = aVar3;
                        aVar3.b = a4.clone();
                        aVar3.a = a3;
                    }
                    try {
                        a aVar4 = ean2.e;
                        if (aVar4 != null) {
                            ean2.a.b(ean2.a.al() / 2, ean2.a.am() / 2);
                            if (aVar4.b != null) {
                                ean2.a.a(aVar4.b.x, aVar4.b.y);
                            }
                            if (aVar4.a != -1.0f) {
                                ean2.a.d(aVar4.a);
                            }
                            ean.this.a.b(500, aVar4.a == -1.0f ? -9999.0f : aVar4.a, aVar4.c, aVar4.d, aVar4.b == null ? -9999 : aVar4.b.x, aVar4.b == null ? -9999 : aVar4.b.y);
                        }
                    } catch (Exception e2) {
                        kf.a((Throwable) e2);
                    }
                }
            }
        }
        this.b.a(bus2, realTimeBusline);
    }

    private LayoutParams d() {
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        return new LayoutParams(dimensionPixelSize, dimensionPixelSize);
    }
}
