package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.route.health.PathLineParser;
import com.autonavi.jni.route.health.PathLineSegment;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam.SendType;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.route.run.beans.RunTraceHistory;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.RunType;
import com.autonavi.minimap.route.run.page.RunFinishMapPage;
import com.autonavi.minimap.route.run.presenter.RunFinishMapPresenter$1;
import com.autonavi.minimap.route.run.view.RouteRunShareView;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.io.File;
import java.util.ArrayList;

/* renamed from: efn reason: default package */
/* compiled from: RunFinishMapPresenter */
public final class efn extends eae<RunFinishMapPage> {
    public efl a;
    public RunTraceHistory b = null;
    public boolean c;
    public boolean d;
    public boolean e;
    public boolean f = true;
    public boolean g = false;
    public String h = null;
    /* access modifiers changed from: private */
    public a i;
    /* access modifiers changed from: private */
    public efj j;
    /* access modifiers changed from: private */
    public boolean k = true;
    private int l = 0;
    /* access modifiers changed from: private */
    public ctl m;
    private boolean n = false;

    /* renamed from: efn$a */
    /* compiled from: RunFinishMapPresenter */
    static class a extends ecs<RunFinishMapPage> {
        a(RunFinishMapPage runFinishMapPage) {
            super(runFinishMapPage);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            RunFinishMapPage runFinishMapPage = (RunFinishMapPage) a();
            if (runFinishMapPage != null && message.what == 0) {
                dct dct = new dct(0);
                dct.f = true;
                dct.d = true;
                dct.e = true;
                dct.l = true;
                dct.h = true;
                dcb dcb = (dcb) defpackage.esb.a.a.a(dcb.class);
                if (dcb != null) {
                    dcb.a(dct, (dcd) new dcd() {
                        public final ShareParam getShareDataByType(int i) {
                            if (i != 11) {
                                switch (i) {
                                    case 3:
                                        e eVar = new e(0);
                                        eVar.g = ((efn) RunFinishMapPage.this.mPresenter).b();
                                        eVar.h = ((efn) RunFinishMapPage.this.mPresenter).c();
                                        eVar.c = false;
                                        eVar.e = 3;
                                        return eVar;
                                    case 4:
                                        e eVar2 = new e(1);
                                        eVar2.g = ((efn) RunFinishMapPage.this.mPresenter).b();
                                        eVar2.h = ((efn) RunFinishMapPage.this.mPresenter).c();
                                        eVar2.c = false;
                                        eVar2.e = 3;
                                        return eVar2;
                                    case 5:
                                        f fVar = new f();
                                        efn efn = (efn) RunFinishMapPage.this.mPresenter;
                                        if ((efn.b != null ? efn.b.j : RunType.RUN_TYPE) == RunType.FOOT_TYPE) {
                                            fVar.a = AMapAppGlobal.getApplication().getString(R.string.foot_navi_share_weibo_body);
                                        } else {
                                            fVar.a = AMapAppGlobal.getApplication().getString(R.string.run_finish_share_weibo_msg);
                                        }
                                        fVar.j = true;
                                        fVar.h = ((efn) RunFinishMapPage.this.mPresenter).c();
                                        fVar.c = false;
                                        return fVar;
                                    default:
                                        return null;
                                }
                            } else {
                                DingDingParam dingDingParam = new DingDingParam();
                                dingDingParam.e = SendType.LocalImage;
                                dingDingParam.c = false;
                                dingDingParam.g = ((efn) RunFinishMapPage.this.mPresenter).b();
                                String c = ((efn) RunFinishMapPage.this.mPresenter).c();
                                if (c != null) {
                                    dingDingParam.i = (c.startsWith("/") ? "file:/" : "file://").concat(String.valueOf(c));
                                }
                                return dingDingParam;
                            }
                        }
                    });
                }
            }
        }
    }

    public efn(RunFinishMapPage runFinishMapPage) {
        super(runFinishMapPage);
    }

    public final void onMapRenderCompleted() {
        super.onMapRenderCompleted();
        eft.a("RunFinishMapPresenter", "onMapRenderCompleted");
        if (this.mPage != null && ((RunFinishMapPage) this.mPage).isAlive()) {
            this.e = true;
            ((RunFinishMapPage) this.mPage).getMapView().f(0);
            if (this.f && this.b != null) {
                StringBuilder sb = new StringBuilder("viewReady ");
                sb.append(this.d);
                sb.append("renderReady");
                sb.append(this.e);
                eft.a("RunFinishMapPresenter", sb.toString());
                if (this.d && this.e) {
                    ((RunFinishMapPage) this.mPage).getMapView().f(0);
                    if (!d()) {
                        if (this.b == null) {
                            this.f = false;
                            return;
                        }
                        ((RunFinishMapPage) this.mPage).getMapView().a(((RunFinishMapPage) this.mPage).getMapView().al(), ((RunFinishMapPage) this.mPage).getMapView().am(), (defpackage.bty.a) new defpackage.bty.a() {
                            public final void onCallBack(Bitmap bitmap) {
                                StringBuilder sb = new StringBuilder("current thead id and name = ");
                                sb.append(Thread.currentThread().getId());
                                sb.append(", ");
                                sb.append(Thread.currentThread().getName());
                                eft.a("RunFinishMapPresenter", sb.toString());
                                if (!efn.this.k && bitmap != null && efn.this.f) {
                                    efw.a(efw.a(bitmap), efn.this.b.h);
                                }
                                efn.this.f = false;
                                ((RunFinishMapPage) efn.this.mPage).getMapView().f(true);
                            }
                        });
                    }
                }
            }
        }
    }

    public final void onPageCreated() {
        super.onPageCreated();
        ((RunFinishMapPage) this.mPage).requestScreenOrientation(1);
        this.i = new a((RunFinishMapPage) this.mPage);
        if (((RunFinishMapPage) this.mPage).getArguments() != null) {
            this.b = (RunTraceHistory) ((RunFinishMapPage) this.mPage).getArguments().getObject("data");
            this.n = ((RunFinishMapPage) this.mPage).getArguments().getBoolean("isfromRunPage");
            this.g = ((RunFinishMapPage) this.mPage).getArguments().getBoolean("isfromRecommend");
        }
        this.a = new efl((AbstractBaseMapPage) this.mPage);
        if (!(this.b == null || this.b.i == null)) {
            ArrayList<com.autonavi.minimap.route.run.beans.RunTraceHistory.a> arrayList = this.b.i.e;
            ArrayList<Double> arrayList2 = this.b.i.f;
            if (!(arrayList == null || arrayList2 == null)) {
                int size = arrayList2.size();
                int[] iArr = new int[size];
                for (int i2 = 0; i2 < size; i2++) {
                    iArr[i2] = arrayList2.get(i2).intValue();
                }
                com.autonavi.minimap.route.run.beans.RunTraceHistory.a[] aVarArr = new com.autonavi.minimap.route.run.beans.RunTraceHistory.a[arrayList.size()];
                for (int i3 = 0; i3 < aVarArr.length; i3++) {
                    aVarArr[i3] = arrayList.get(i3);
                }
                POI poi = this.b.i.a;
                POI poi2 = this.b.i.b;
                POI poi3 = this.b.i.c;
                boolean z = this.b.i.d;
                efl efl = this.a;
                efl.j = aVarArr;
                efl.k = iArr;
                efl.l = poi;
                efl.m = poi2;
                efl.n = poi3;
                efl.o = z;
            }
        }
        this.c = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(true, false, false, ((RunFinishMapPage) this.mPage).getMapManager(), ((RunFinishMapPage) this.mPage).getContext());
        }
        AMapPageUtil.setPageStateListener(((RunFinishMapPage) this.mPage).getPageContext(), new IPageStateListener() {
            public final void onAppear() {
            }

            public final void onCover() {
                PlaySoundUtils.getInstance().release();
                if (efn.this.m != null) {
                    ctl d = efn.this.m;
                    efn.this.mPage;
                    d.a("7");
                }
            }
        });
        if (this.n) {
            this.m = (ctl) defpackage.esb.a.a.a(ctl.class);
            if (this.m != null) {
                this.m.a("10", new RunFinishMapPresenter$1(this));
            }
        }
    }

    public final void onResume() {
        com.autonavi.minimap.route.run.beans.RunTraceHistory.a[] aVarArr;
        super.onResume();
        if (this.k && this.b != null) {
            this.a.a();
            efl efl = this.a;
            RunType runType = this.b.j;
            if (runType == RunType.RUN_TYPE) {
                if (efl.j != null && efl.k != null && efl.j.length > 0 && efl.k.length > 0) {
                    PathLineParser pathLineParser = new PathLineParser(0);
                    for (int i2 = 0; i2 < efl.j.length; i2++) {
                        com.autonavi.minimap.route.run.beans.RunTraceHistory.a aVar = efl.j[i2];
                        pathLineParser.addPoint(aVar.a.getPoint(), aVar.b != 0, efl.k[i2]);
                    }
                    PathLineSegment[] segments = pathLineParser.getSegments();
                    for (PathLineSegment pathLineSegment : segments) {
                        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, pathLineSegment.getGeoPointArray(), agn.a(AMapPageUtil.getAppContext(), 4.0f));
                        lineOverlayItem.setFillLineId(R.drawable.map_frontlr);
                        lineOverlayItem.setIsColorGradient(true);
                        lineOverlayItem.setMatchColors(pathLineSegment.getColorArray());
                        lineOverlayItem.setBorderLineWidth(agn.a(AMapPageUtil.getAppContext(), 5.0f));
                        lineOverlayItem.setBackgrondId(R.drawable.map_lr);
                        lineOverlayItem.setBackgroundColor(-1);
                        efl.a.addItem(lineOverlayItem);
                    }
                    PointOverlayItem pointOverlayItem = new PointOverlayItem(efl.j[0].a.getPoint());
                    PointOverlayItem pointOverlayItem2 = new PointOverlayItem(efl.j[efl.j.length - 1].a.getPoint());
                    pointOverlayItem.mDefaultMarker = efl.b.createMarker(R.drawable.run_point_start, 4);
                    pointOverlayItem2.mDefaultMarker = efl.b.createMarker(R.drawable.run_point_end, 4);
                    efl.b.addItem(pointOverlayItem);
                    efl.b.addItem(pointOverlayItem2);
                }
            } else if (runType == RunType.FOOT_TYPE && efl.j != null && efl.j.length > 0 && efl.k != null && efl.k.length > 0) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (com.autonavi.minimap.route.run.beans.RunTraceHistory.a aVar2 : efl.j) {
                    if (aVar2.b == 0) {
                        arrayList2.add(aVar2.a);
                    } else {
                        arrayList.add((ArrayList) arrayList2.clone());
                        arrayList2.clear();
                    }
                }
                arrayList.add(arrayList2);
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    ArrayList arrayList3 = (ArrayList) arrayList.get(i3);
                    GeoPoint[] geoPointArr = new GeoPoint[arrayList3.size()];
                    for (int i4 = 0; i4 < arrayList3.size(); i4++) {
                        geoPointArr[i4] = ((POI) arrayList3.get(i4)).getPoint();
                    }
                    efl.c.createAndAddBackgroundLineItem(geoPointArr);
                    efl.c.createAndAddArrowLineItem(geoPointArr);
                }
                if (!(efl.l == null || efl.l.getPoint() == null)) {
                    GeoPoint point = efl.l.getPoint();
                    PointOverlayItem pointOverlayItem3 = new PointOverlayItem(point);
                    pointOverlayItem3.mDefaultMarker = efl.b.createMarker(R.drawable.bubble_start, 4);
                    efl.b.addItem(pointOverlayItem3);
                    efl.c.addItem(new LineOverlayItem(1, new GeoPoint[]{point}, 0));
                }
                if (!(efl.m == null || efl.m.getPoint() == null)) {
                    GeoPoint point2 = efl.m.getPoint();
                    PointOverlayItem pointOverlayItem4 = new PointOverlayItem(point2);
                    pointOverlayItem4.mDefaultMarker = efl.b.createMarker(R.drawable.bubble_end, 4);
                    efl.b.addItem(pointOverlayItem4);
                    efl.c.addItem(new LineOverlayItem(1, new GeoPoint[]{point2}, 0));
                }
                if (!(!efl.o || efl.n == null || efl.n.getPoint() == null)) {
                    PointOverlayItem pointOverlayItem5 = new PointOverlayItem(efl.n.getPoint());
                    pointOverlayItem5.mDefaultMarker = efl.b.createMarker(R.drawable.foot_navi_end_icon, 4);
                    efl.b.addItem(pointOverlayItem5);
                }
            }
            eft.a("performance-", "showPathForTrack foot finish");
        }
        bty mapView = ((RunFinishMapPage) this.mPage).getMapView();
        if (mapView != null) {
            this.l = mapView.k(false);
            ebf.a(mapView, mapView.j(false), 0, 6);
            mapView.d(true);
            mapView.m(true);
            mapView.g(587202559);
        }
        this.k = false;
        RunFinishMapPage runFinishMapPage = (RunFinishMapPage) this.mPage;
        runFinishMapPage.a.getViewTreeObserver().addOnPreDrawListener(runFinishMapPage);
        eft.a("performance-", "OldFootEndPage  onResume");
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onPause() {
        super.onPause();
        this.k = true;
    }

    public final ON_BACK_TYPE onBackPressed() {
        RunFinishMapPage runFinishMapPage = (RunFinishMapPage) this.mPage;
        runFinishMapPage.finish();
        runFinishMapPage.b();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
        StringBuilder sb = new StringBuilder("onMapAnimationFinished");
        sb.append(aln == null ? null : Integer.valueOf(aln.a));
        eft.a("RunFinishMapPresenter", sb.toString());
        if (aln != null && aln.a == 201 && this.mPage != null && ((RunFinishMapPage) this.mPage).isAlive()) {
            ((RunFinishMapPage) this.mPage).getMapView().f(1);
            RunFinishMapPage runFinishMapPage = (RunFinishMapPage) this.mPage;
            if (!runFinishMapPage.isAlive() || !((efn) runFinishMapPage.mPresenter).d()) {
                ebr.a(true).postDelayed(new Runnable() {
                    public final void run() {
                        if (RunFinishMapPage.this.isAlive()) {
                            ((efn) RunFinishMapPage.this.mPresenter).f = false;
                            RunFinishMapPage.this.getMapView().f(true);
                        }
                    }
                }, 2500);
            } else {
                runFinishMapPage.getMapView().f(true);
            }
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        PlaySoundUtils.getInstance().clear();
        this.a.a();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(true, this.c, false, ((RunFinishMapPage) this.mPage).getMapManager(), ((RunFinishMapPage) this.mPage).getContext());
        }
        if (this.m != null) {
            this.m.a("10");
        }
        bty mapView = ((RunFinishMapPage) this.mPage).getMapView();
        if (mapView != null) {
            mapView.f(0);
            ebf.a(mapView, mapView.j(false), mapView.l(false), this.l);
            mapView.z();
        }
    }

    private boolean d() {
        if (!new File(efw.a(), this.b.h).exists()) {
            return false;
        }
        ((RunFinishMapPage) this.mPage).getMapView().f(true);
        this.f = false;
        return true;
    }

    public final void a() {
        if (this.b != null) {
            this.a.a(this.b.j);
        }
    }

    public final Bitmap b() {
        if (this.j != null) {
            return BitmapFactory.decodeFile(efj.a("runTraceThumbnail.png"));
        }
        return null;
    }

    public final String c() {
        if (this.j != null) {
            return efj.a("runTrace.png");
        }
        return null;
    }

    static /* synthetic */ void a(efn efn, Bitmap bitmap) {
        if (efn.b == null || bitmap == null) {
            ToastHelper.showToast(((RunFinishMapPage) efn.mPage).getString(R.string.screenshot_fail));
            ((RunFinishMapPage) efn.mPage).a();
            return;
        }
        RouteRunShareView routeRunShareView = new RouteRunShareView(((RunFinishMapPage) efn.mPage).getContext());
        routeRunShareView.setMapBg(bitmap);
        routeRunShareView.bindData(efn.b, efn.h);
        new efj();
        final Bitmap a2 = efj.a(((RunFinishMapPage) efn.mPage).getContext(), bitmap, routeRunShareView);
        if (a2 == null) {
            ToastHelper.showToast(((RunFinishMapPage) efn.mPage).getString(R.string.screenshot_fail));
            ((RunFinishMapPage) efn.mPage).a();
            return;
        }
        efn.j = new efj();
        efn.i = new a((RunFinishMapPage) efn.mPage);
        ahm.a(new Runnable() {
            public final void run() {
                efn.this.j;
                Bitmap a2 = ahc.a(a2, a2.getWidth() >> 3, a2.getHeight() >> 3);
                efn.this.j;
                efj.a(a2, efj.a("runTraceThumbnail.png"));
                efn.this.j;
                Bitmap bitmap = a2;
                efn.this.j;
                efj.a(bitmap, efj.a("runTrace.png"));
                efn.this.i.sendEmptyMessage(0);
                ((RunFinishMapPage) efn.this.mPage).a();
            }
        });
    }
}
