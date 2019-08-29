package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.bundle.routecommon.entity.ExtBusPath;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.route.bus.extbus.overlay.ExtBusLineOverlay;
import com.autonavi.minimap.route.bus.extbus.overlay.ExtBusPointOverlay;
import com.autonavi.minimap.route.bus.extbus.page.ExtBusMapPage;
import com.autonavi.minimap.route.bus.extbus.presenter.ExtBusMapPresenter$1;
import com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter;
import com.autonavi.minimap.route.bus.widget.RouteBusResultDetailView;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dvd reason: default package */
/* compiled from: ExtBusMapPresenter */
public final class dvd extends eae<ExtBusMapPage> implements com.autonavi.minimap.route.bus.extbus.adapter.ExtBusDetailAdapter.a, defpackage.dwq.a {
    public IBusRouteResult a;
    public List<ExtBusPath> b;
    private Handler c;
    private float d = 0.0f;
    private boolean e = false;
    private dwr f;
    private dwq g;
    /* access modifiers changed from: private */
    public ctl h;

    /* renamed from: dvd$a */
    /* compiled from: ExtBusMapPresenter */
    static class a extends Handler {
        private WeakReference<ExtBusMapPage> a;

        public a(ExtBusMapPage extBusMapPage) {
            this.a = new WeakReference<>(extBusMapPage);
        }

        public final void handleMessage(Message message) {
            ExtBusMapPage extBusMapPage = (ExtBusMapPage) this.a.get();
            if (extBusMapPage != null) {
                super.handleMessage(message);
                switch (message.what) {
                    case 0:
                        extBusMapPage.c();
                        return;
                    case 1:
                        extBusMapPage.d();
                        break;
                }
            }
        }
    }

    public dvd(ExtBusMapPage extBusMapPage) {
        super(extBusMapPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.c = new a((ExtBusMapPage) this.mPage);
        PageBundle arguments = ((ExtBusMapPage) this.mPage).getArguments();
        if (arguments != null) {
            this.a = (IBusRouteResult) arguments.get(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
            if (this.a.isExtBusResult()) {
                this.b = this.a.getExtBusPathList();
            }
        }
        ExtBusMapPage extBusMapPage = (ExtBusMapPage) this.mPage;
        IBusRouteResult iBusRouteResult = this.a;
        if (extBusMapPage.getMapView() != null) {
            ExtBusLineOverlay extBusLineOverlay = new ExtBusLineOverlay(extBusMapPage.getMapView());
            extBusMapPage.addOverlay(extBusLineOverlay);
            ExtBusPointOverlay extBusPointOverlay = new ExtBusPointOverlay(extBusMapPage.getContext(), extBusMapPage.getMapView());
            extBusMapPage.addOverlay(extBusPointOverlay);
            ExtBusPointOverlay extBusPointOverlay2 = new ExtBusPointOverlay(extBusMapPage.getContext(), extBusMapPage.getMapView());
            extBusMapPage.addOverlay(extBusPointOverlay2);
            ExtBusPointOverlay extBusPointOverlay3 = new ExtBusPointOverlay(extBusMapPage.getContext(), extBusMapPage.getMapView());
            extBusMapPage.addOverlay(extBusPointOverlay3);
            dvb dvb = new dvb(extBusMapPage.getContext(), iBusRouteResult, extBusPointOverlay, extBusLineOverlay, extBusPointOverlay2, extBusPointOverlay3);
            extBusMapPage.c = dvb;
            if (extBusMapPage.d == null) {
                extBusMapPage.d = new ean(extBusMapPage.getMapView(), extBusLineOverlay, extBusMapPage.getSuspendManager().d());
                int a2 = euk.a() ? euk.a(extBusMapPage.getContext()) : 0;
                if (1 == extBusMapPage.getResources().getConfiguration().orientation) {
                    extBusMapPage.d.a(100, a2 + 110, 100, 125);
                } else {
                    extBusMapPage.d.a(100, a2 + 110, 100, 130);
                }
            }
        }
        ExtBusMapPage extBusMapPage2 = (ExtBusMapPage) this.mPage;
        IBusRouteResult iBusRouteResult2 = this.a;
        List<ExtBusPath> list = this.b;
        if (!(iBusRouteResult2 == null || list == null)) {
            extBusMapPage2.c.a = iBusRouteResult2;
            RouteBusResultDetailView routeBusResultDetailView = extBusMapPage2.a;
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                ExtBusPath extBusPath = list.get(i);
                arrayList.add(extBusMapPage2.a.createPagerItemView(extBusPath.getTitleDes(), extBusPath.getSubTitleDes()));
            }
            routeBusResultDetailView.setPagerViews(arrayList);
            extBusMapPage2.a.setCurrentPagerItem(Math.max(0, iBusRouteResult2.getFocusExBusPathIndex()));
        }
        ((ExtBusMapPage) this.mPage).a((List<dvs>) dvt.a(((ExtBusMapPage) this.mPage).getContext(), this.a, this.a.getFocusExtBusPath()));
        this.f = new dwr();
        this.f.b = (defpackage.dwr.a) this.mPage;
        this.g = new dwq();
        this.g.b = (b) this.mPage;
        this.g.c = this;
        this.h = (ctl) defpackage.esb.a.a.a(ctl.class);
        if (this.h != null) {
            this.h.a("14", new ExtBusMapPresenter$1(this));
        }
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onAppear() {
            }

            public final void onCover() {
                if (dvd.this.h != null) {
                    ctl d = dvd.this.h;
                    dvd.this.mPage;
                    d.a("14");
                }
            }
        });
    }

    public final void onPause() {
        super.onPause();
        ((ExtBusMapPage) this.mPage).a();
    }

    public final void onStart() {
        super.onStart();
        ebo.a((AbstractBaseMapPage) this.mPage);
        this.a.setFocusStationIndex(-1);
        ExtBusMapPage extBusMapPage = (ExtBusMapPage) this.mPage;
        if (extBusMapPage.getMapView() != null) {
            ebf.a(extBusMapPage.getMapView(), extBusMapPage.getMapView().p(false), extBusMapPage.getMapView().ae(), 2);
            bqx bqx = (bqx) ank.a(bqx.class);
            if (bqx != null) {
                bqx.a(bqx.a(), false, extBusMapPage.getMapManager(), extBusMapPage.getContext());
            }
            extBusMapPage.getMapView().a(true);
        }
        a();
    }

    public final void onStop() {
        super.onStop();
        ExtBusMapPage extBusMapPage = (ExtBusMapPage) this.mPage;
        if (extBusMapPage.c != null) {
            dvb dvb = extBusMapPage.c;
            if (dvb.d != null) {
                dvb.d.clear();
            }
            if (dvb.c != null) {
                dvb.c.clear();
            }
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.c != null) {
            this.c.removeMessages(0);
            this.c.removeMessages(1);
        }
        dwr dwr = this.f;
        dwr.a();
        dwr.b = null;
        dwq dwq = this.g;
        dwq.a();
        dwq.b = null;
        dwq.c = null;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("bundle_key_index", this.a.getFocusBusPathIndex());
        ((ExtBusMapPage) this.mPage).setResult(ResultType.OK, pageBundle);
    }

    public final void a(View view) {
        if (view != null) {
            dvs dvs = (dvs) view.getTag();
            if (dvs != null) {
                ExTrainPath exTrainPath = dvs.B;
                POI shareFromPOI = exTrainPath.getShareFromPOI();
                POI shareToPOI = exTrainPath.getShareToPOI();
                String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() + 86400000));
                bdo bdo = (bdo) defpackage.esb.a.a.a(bdo.class);
                if (bdo != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("bundle_start_city_key", shareFromPOI);
                    pageBundle.putObject("bundle_end_city_key", shareToPOI);
                    pageBundle.putObject("bundle_selected_date_key", format);
                    pageBundle.putObject("bundle_is_from_which_page", Integer.valueOf(3));
                    ((ExtBusMapPage) this.mPage).startPageForResult(bdo.b(), pageBundle, 1);
                }
            }
        }
    }

    public final void b(View view) {
        BusPath busPath;
        BusPathSection busPathSection;
        if (this.a != null && this.a.getFocusExtBusPath() != null && this.a.getFromPOI() != null && this.a.getFromPOI().getPoint() != null) {
            int intValue = ((Integer) view.getTag(RouteBusDetailAdapter.KEY_ALTER_LIST_INDEX)).intValue();
            ArrayList<axb> busPathList = this.a.getFocusExtBusPath().getBusPathList();
            if (busPathList != null && intValue < busPathList.size()) {
                axb axb = busPathList.get(intValue);
                if (axb instanceof BusPath) {
                    busPath = (BusPath) axb;
                    GeoPoint point = this.a.getFromPOI().getPoint();
                    busPathSection = (BusPathSection) view.getTag();
                    if (busPathSection == null && busPathSection.alter_list != null && busPathSection.alter_list.length != 0) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(busPathSection);
                        for (BusPathSection add : busPathSection.alter_list) {
                            arrayList.add(add);
                        }
                        dwq dwq = this.g;
                        IBusRouteResult iBusRouteResult = this.a;
                        if (!(iBusRouteResult == null || busPath == null || point == null || ((dwq.a != null && dwq.a.isShowing()) || dwq.b == null))) {
                            dwq.d = 0;
                            dwq.a = dwq.b.a(busPath, point, arrayList, new com.autonavi.minimap.route.bus.widget.RouteBusAlertListDialog.a(iBusRouteResult) {
                                final /* synthetic */ IBusRouteResult a;

                                {
                                    this.a = r2;
                                }

                                public final void a(boolean z, boolean z2, String str) {
                                    dwq.this.a();
                                    JSONObject jSONObject = new JSONObject();
                                    try {
                                        jSONObject.put("time", ahw.a(new Date(), (String) "yyyy-MM-dd HH:mm:ss"));
                                        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                                        if (latestPosition != null) {
                                            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, latestPosition.getLongitude());
                                            jSONObject.put("lat", latestPosition.getLatitude());
                                        }
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(this.a.getBsid());
                                        sb.append("_");
                                        sb.append(this.a.getFocusBusPathIndex());
                                        jSONObject.put("itemId", sb.toString());
                                        jSONObject.put("text", str);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    LogManager.actionLogV2("P00019", LogConstant.MORE_MINE_COMMENT, jSONObject);
                                    if (dwq.this.c != null) {
                                        dwq.this.c.a(z, z2);
                                    }
                                }
                            });
                            dwq.a.setCanceledOnTouchOutside(true);
                            dwq.a.setCancelable(true);
                            dwq.a.setOnCancelListener(new OnCancelListener() {
                                public final void onCancel(DialogInterface dialogInterface) {
                                    dwq.this.a();
                                }
                            });
                            dwq.a.show();
                        }
                        dwj.a("B014", null);
                        return;
                    }
                }
            }
            busPath = null;
            GeoPoint point2 = this.a.getFromPOI().getPoint();
            busPathSection = (BusPathSection) view.getTag();
            if (busPathSection == null) {
            }
        }
    }

    public final void c(View view) {
        dwr dwr = this.f;
        BusPathSection busPathSection = (BusPathSection) view.getTag();
        if (busPathSection != null && busPathSection.irregulartime != null) {
            if ((dwr.a == null || !dwr.a.isShowing()) && dwr.b != null) {
                dwr.a = dwr.b.a(busPathSection);
                dwr.a.setCanceledOnTouchOutside(true);
                dwr.a.setCancelable(true);
                dwr.a.setOnCancelListener(new OnCancelListener() {
                    public final void onCancel(DialogInterface dialogInterface) {
                        dwr.this.a();
                    }
                });
                dwr.a.show();
            }
        }
    }

    public final void a(boolean z, boolean z2) {
        if (!z && z2) {
            ((ExtBusMapPage) this.mPage).a((List<dvs>) dvt.a(((ExtBusMapPage) this.mPage).getContext(), this.a, this.a.getFocusExtBusPath()));
        }
        a();
    }

    public final void onMapSurfaceChanged(int i, int i2) {
        super.onMapSurfaceChanged(i, i2);
        if (this.mPage != null && ((ExtBusMapPage) this.mPage).isAlive()) {
            ((ExtBusMapPage) this.mPage).c();
        }
    }

    public final void a() {
        ((ExtBusMapPage) this.mPage).b();
        if (this.c != null) {
            this.c.sendEmptyMessageDelayed(0, 300);
        }
    }
}
