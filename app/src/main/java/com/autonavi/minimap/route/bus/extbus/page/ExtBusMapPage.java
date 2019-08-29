package com.autonavi.minimap.route.bus.extbus.page;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.bundle.routecommon.entity.ExtBusPath;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.extbus.adapter.ExtBusDetailAdapter;
import com.autonavi.minimap.route.bus.extbus.overlay.ExtBusLineOverlay;
import com.autonavi.minimap.route.bus.model.ExTaxiPath;
import com.autonavi.minimap.route.bus.widget.RouteBusAlertListDialog;
import com.autonavi.minimap.route.bus.widget.RouteBusResultDetailView;
import com.autonavi.minimap.route.bus.widget.RouteBusResultDetailView.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExtBusMapPage extends AbstractBaseMapPage<dvd> implements OnClickListener, btz, a, b, a {
    public RouteBusResultDetailView a;
    public ExtBusDetailAdapter b;
    public dvb c;
    public ean d;
    private float e;

    /* access modifiers changed from: private */
    /* renamed from: f */
    public dvd createPresenter() {
        return new dvd(this);
    }

    public View getMapSuspendView() {
        Context context = getContext();
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        ccv ccv = new ccv(context);
        ccv.addWidget(suspendWidgetHelper.a(false), suspendWidgetHelper.c(), 1);
        ccv.addWidget(suspendWidgetHelper.l(), suspendWidgetHelper.m(), 6);
        ccv.addWidget(suspendWidgetHelper.f(), suspendWidgetHelper.g(), 7);
        suspendWidgetHelper.a(suspendWidgetHelper.n());
        ccv.addWidget(suspendWidgetHelper.n(), suspendWidgetHelper.q(), 2);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LayoutParams layoutParams = new LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.leftMargin = agn.a(getContext(), 4.0f);
        layoutParams.bottomMargin = agn.a(getContext(), 3.0f);
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
        setContentView(R.layout.ext_bus_result_map_fragment);
        if (!(getSuspendManager() == null || getSuspendManager().b() == null)) {
            getSuspendManager().b().disableView(2240);
        }
        if (getMapView() != null) {
            this.e = getMapView().v();
        }
        this.a = (RouteBusResultDetailView) getContentView().findViewById(R.id.bus_result_detail_view);
        this.a.setBusResultDetailListener(this);
        this.a.setListViewHeader(agn.a(getContext(), 10.0f));
        this.a.setListViewFooter(agn.a(getContext(), 10.0f));
        this.b = new ExtBusDetailAdapter(getContext());
        this.a.setListViewAdapter(this.b);
        this.a.startInAnimation(null);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.a.setActionLogStartTime(arguments.getLong("bundle_key_start_time", 0), false);
        }
        LogManager.actionLogV2("P00262", "B001");
        a();
    }

    public final void a() {
        cdz d2 = getSuspendManager().d();
        d2.a(false);
        d2.f();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.view_pager_item) {
            this.a.toggleSlidingPanel();
        }
    }

    public final void b() {
        dvb dvb = this.c;
        if (dvb.a != null && dvb.a.hasData() && dvb.a.isExtBusResult() && dvb.c != null && dvb.d != null && dvb.b != null) {
            if (dvb.e != null) {
                dvb.e.clear();
                dvb.e.removeAll();
                dvb.e.clearFocus();
            }
            dvb.d.clear();
            dvb.c.clear();
            dvb.b.clear();
            dvb.c.clearFocus();
            dvb.c.setClickable(false);
            ExtBusPath focusExtBusPath = dvb.a.getFocusExtBusPath();
            if (focusExtBusPath != null) {
                dvw dvw = new dvw();
                GeoPoint point = dvb.a.getFromPOI().getPoint();
                GeoPoint point2 = dvb.a.getToPOI().getPoint();
                dvb.c.addStation(point, -999, -100, 5);
                dvb.b.addStation(point, R.drawable.bubble_start, -100, 5);
                int[] iArr = {-1, -1};
                Iterator<axb> it = focusExtBusPath.getBusPathList().iterator();
                while (it.hasNext()) {
                    axb next = it.next();
                    if (next instanceof BusPath) {
                        iArr = dvb.a((BusPath) next, dvw, dvb.d, iArr);
                    } else if (next instanceof ExTrainPath) {
                        ExTrainPath exTrainPath = (ExTrainPath) next;
                        ExtBusLineOverlay extBusLineOverlay = dvb.d;
                        int[] iArr2 = exTrainPath.mXs;
                        int[] iArr3 = exTrainPath.mYs;
                        dvb.a(iArr2[0], iArr3[0], iArr, extBusLineOverlay);
                        dvw.b = extBusLineOverlay.createLineItem(iArr2, iArr3);
                        dvb.a(new GeoPoint(exTrainPath.startX, exTrainPath.startY), R.drawable.train_turnpoint, dvw.b);
                        dvb.a(new GeoPoint(iArr2[iArr2.length / 2], iArr3[iArr3.length / 2]), -999, dvw.b);
                        dvb.a(new GeoPoint(exTrainPath.endX, exTrainPath.endY), -999, dvw.b);
                        iArr = new int[]{iArr2[iArr2.length - 1], iArr3[iArr3.length - 1]};
                    } else if (next instanceof ExTaxiPath) {
                        ExTaxiPath exTaxiPath = (ExTaxiPath) next;
                        ExtBusLineOverlay extBusLineOverlay2 = dvb.d;
                        int[] iArr4 = {exTaxiPath.startX, exTaxiPath.endX};
                        int[] iArr5 = {exTaxiPath.startY, exTaxiPath.endY};
                        dvb.a(exTaxiPath.startX, exTaxiPath.startY, iArr, extBusLineOverlay2);
                        dvw.b = extBusLineOverlay2.createLineItem(iArr4, iArr5);
                        dvb.a(new GeoPoint(exTaxiPath.startX, exTaxiPath.startY), R.drawable.taxi_turnpoint, dvw.b);
                        iArr = new int[]{iArr4[1], iArr5[1]};
                    }
                }
                dvb.c.addStation(point2, -999, dvw.b, 5);
                dvb.b.addStation(point2, R.drawable.bubble_end, dvw.b, 5);
                int focusStationIndex = dvb.a.getFocusStationIndex();
                if (focusStationIndex != -1) {
                    dvb.c.setFocus(focusStationIndex, true);
                }
            }
        }
    }

    public final void c() {
        int a2 = agn.a(getContext(), 125.0f);
        ean ean = this.d;
        if (ean.b != null) {
            Rect bound = ean.b.getBound();
            if (bound != null) {
                DisplayMetrics displayMetrics = ean.d.getResources().getDisplayMetrics();
                int i = displayMetrics.widthPixels + 0;
                int i2 = displayMetrics.heightPixels - a2;
                a a3 = new a().a(bound, agn.a(ean.d, (float) ean.f), agn.a(ean.d, (float) ean.g), agn.a(ean.d, (float) ean.h), agn.a(ean.d, (float) ean.i));
                a3.j = 0;
                a3.k = 0;
                a3.a(ean.a, i, i2, i / 2, i2 / 2);
                a3.l = 0.0f;
                eak.a(a3.a().a);
            }
        }
        d();
    }

    public final void d() {
        if (getMapView() != null) {
            this.c.a(getMapView().w());
        }
    }

    public final void a(List<dvs> list) {
        if (list != null) {
            this.b.setExtBusDetailClickListener((ExtBusDetailAdapter.a) this.mPresenter);
            this.b.setListData(list);
        }
    }

    public final Dialog a(BusPathSection busPathSection) {
        return new dza(getActivity(), busPathSection);
    }

    public final Dialog a(BusPath busPath, GeoPoint geoPoint, ArrayList<BusPathSection> arrayList, RouteBusAlertListDialog.a aVar) {
        RouteBusAlertListDialog routeBusAlertListDialog = new RouteBusAlertListDialog(getActivity(), busPath, geoPoint, arrayList, aVar);
        return routeBusAlertListDialog;
    }

    public final void e() {
        if (this.mPresenter != null && isAlive()) {
            ((ExtBusMapPage) ((dvd) this.mPresenter).mPage).finish();
        }
    }

    public final void a(int i) {
        if (this.mPresenter != null && isAlive()) {
            dvd dvd = (dvd) this.mPresenter;
            if (i >= 0 && i < dvd.b.size()) {
                dvd.a.setFocusBusPathIndex(i);
                dvd.a.setFocusStationIndex(-1);
                dvd.a.setFocusExtBusPath(i);
                ((ExtBusMapPage) dvd.mPage).a((List<dvs>) dvt.a(((ExtBusMapPage) dvd.mPage).getContext(), dvd.a, dvd.a.getFocusExtBusPath()));
                ExtBusMapPage extBusMapPage = (ExtBusMapPage) dvd.mPage;
                extBusMapPage.a.clearListViewStatus(extBusMapPage.b);
                if (!(((ExtBusMapPage) dvd.mPage).getSuspendManager() == null || ((ExtBusMapPage) dvd.mPage).getSuspendManager().d() == null)) {
                    ((ExtBusMapPage) dvd.mPage).getSuspendManager().d().f();
                }
                dvd.a();
            }
        }
    }

    public a getGpsLayerTextureProvider() {
        return ebd.i();
    }
}
