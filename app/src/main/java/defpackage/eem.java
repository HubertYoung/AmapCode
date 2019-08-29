package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam.SendType;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.ride.page.RideFinishMapPage;
import com.autonavi.minimap.route.ride.presenter.RideFinishMapPresenter$2;
import com.autonavi.minimap.route.ride.view.RouteRideShareView;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: eem reason: default package */
/* compiled from: RideFinishMapPresenter */
public final class eem extends eae<RideFinishMapPage> {
    public eej a;
    public RideTraceHistory b = null;
    public boolean c;
    public boolean d;
    public boolean e = true;
    public boolean f;
    public boolean g;
    public boolean h;
    public boolean i;
    public String j;
    /* access modifiers changed from: private */
    public a k;
    /* access modifiers changed from: private */
    public efj l;
    private boolean m = true;
    private int n = 0;
    /* access modifiers changed from: private */
    public ctl o;
    private boolean p = false;
    /* access modifiers changed from: private */
    public boolean q = false;

    /* renamed from: eem$a */
    /* compiled from: RideFinishMapPresenter */
    static class a extends ecs<RideFinishMapPage> {
        a(RideFinishMapPage rideFinishMapPage) {
            super(rideFinishMapPage);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            RideFinishMapPage rideFinishMapPage = (RideFinishMapPage) a();
            if (rideFinishMapPage != null && message.what == 0) {
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
                                        eVar.g = ((eem) RideFinishMapPage.this.mPresenter).b();
                                        eVar.h = ((eem) RideFinishMapPage.this.mPresenter).c();
                                        eVar.c = false;
                                        eVar.e = 3;
                                        return eVar;
                                    case 4:
                                        e eVar2 = new e(1);
                                        eVar2.g = ((eem) RideFinishMapPage.this.mPresenter).b();
                                        eVar2.h = ((eem) RideFinishMapPage.this.mPresenter).c();
                                        eVar2.c = false;
                                        eVar2.e = 3;
                                        return eVar2;
                                    case 5:
                                        f fVar = new f();
                                        fVar.a = RideFinishMapPage.this.getString(R.string.ride_finish_share_weibo_msg);
                                        fVar.j = true;
                                        fVar.h = ((eem) RideFinishMapPage.this.mPresenter).c();
                                        fVar.c = false;
                                        return fVar;
                                    default:
                                        return null;
                                }
                            } else {
                                DingDingParam dingDingParam = new DingDingParam();
                                dingDingParam.e = SendType.LocalImage;
                                dingDingParam.c = false;
                                dingDingParam.g = ((eem) RideFinishMapPage.this.mPresenter).b();
                                String c = ((eem) RideFinishMapPage.this.mPresenter).c();
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

    public eem(RideFinishMapPage rideFinishMapPage) {
        super(rideFinishMapPage);
    }

    public final void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
        StringBuilder sb = new StringBuilder("onMapAnimationFinished");
        sb.append(aln == null ? null : Integer.valueOf(aln.a));
        eer.a("RideFinishMapPresenter", sb.toString());
        if (aln != null && aln.a == 201 && this.mPage != null && ((RideFinishMapPage) this.mPage).isAlive()) {
            ((RideFinishMapPage) this.mPage).getMapView().f(1);
            RideFinishMapPage rideFinishMapPage = (RideFinishMapPage) this.mPage;
            if (!rideFinishMapPage.isAlive() || !((eem) rideFinishMapPage.mPresenter).e()) {
                ebr.a(true).postDelayed(new Runnable() {
                    public final void run() {
                        if (RideFinishMapPage.this.isAlive()) {
                            eer.a("RideFinishMapPresenter", "initResumeTimer  --------> mPresenter.isNeedSavePic = false ");
                            ((eem) RideFinishMapPage.this.mPresenter).e = false;
                            RideFinishMapPage.this.getMapView().f(true);
                        }
                    }
                }, 2500);
            } else {
                rideFinishMapPage.getMapView().f(true);
            }
        }
    }

    public final void onResume() {
        super.onResume();
        ebo.a((AbstractBaseMapPage) this.mPage);
        if (this.m && this.b != null) {
            this.a.a();
            this.a.a(this.b.k);
        }
        bty mapView = ((RideFinishMapPage) this.mPage).getMapView();
        if (mapView != null) {
            this.n = mapView.k(false);
            ebf.a(mapView, mapView.j(false), 0, 12);
            mapView.d(false);
            mapView.e(0.0f);
            mapView.g(0.0f);
            mapView.m(true);
            mapView.g(587202559);
        }
        a();
        this.m = false;
        RideFinishMapPage rideFinishMapPage = (RideFinishMapPage) this.mPage;
        rideFinishMapPage.a.getViewTreeObserver().addOnPreDrawListener(rideFinishMapPage);
    }

    public final void onPageCreated() {
        String str;
        super.onPageCreated();
        LocationMode.stopLocation();
        ((RideFinishMapPage) this.mPage).requestScreenOrientation(1);
        this.k = new a((RideFinishMapPage) this.mPage);
        if (((RideFinishMapPage) this.mPage).getArguments() != null) {
            PageBundle arguments = ((RideFinishMapPage) this.mPage).getArguments();
            this.b = (RideTraceHistory) arguments.getObject("data");
            if (!Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"))) {
                ahm.a(new Runnable(this.b, arguments.getInt("where") != 0) {
                    final /* synthetic */ RideTraceHistory a;
                    final /* synthetic */ boolean b;

                    {
                        this.a = r1;
                        this.b = r2;
                    }

                    public final void run() {
                        if (this.a != null && this.b) {
                            ees.a(this.a);
                        }
                    }
                });
            }
            this.g = arguments.getBoolean("isfromRideNaviEndPage");
            ((RideFinishMapPage) this.mPage).a(this.g);
            this.p = arguments.getBoolean("isfromRidePage");
            if (arguments.containsKey("bundle_key_page_jump_from")) {
                String string = arguments.getString("bundle_key_page_jump_from");
                if ("PAGE_JUMP_FROM_DEST".equals(string)) {
                    this.h = true;
                } else if ("PAGE_JUMP_FROM_RIDE".equals(string)) {
                    this.i = true;
                }
                if (this.h) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("type", ebm.c() ? "ddc" : "zxc");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    LogManager.actionLogV2("P00271", "B002", jSONObject);
                } else if (this.i) {
                    LogManager.actionLogV2("P00328", "B002");
                }
            }
            if (arguments.containsKey("ride_finish_bundle_key_naviid")) {
                this.j = arguments.getString("ride_finish_bundle_key_naviid", null);
            }
        }
        d();
        this.f = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(true, false, false, ((RideFinishMapPage) this.mPage).getMapManager(), ((RideFinishMapPage) this.mPage).getContext());
        }
        AMapPageUtil.setPageStateListener(((RideFinishMapPage) this.mPage).getPageContext(), new IPageStateListener() {
            public final void onCover() {
                ((RideFinishMapPage) eem.this.mPage).a(false);
                PlaySoundUtils.getInstance().release();
                if (eem.this.o != null) {
                    ctl b = eem.this.o;
                    eem.this.mPage;
                    b.a("7");
                }
            }

            public final void onAppear() {
                ((RideFinishMapPage) eem.this.mPage).a(eem.this.g);
            }
        });
        if (this.p) {
            this.o = (ctl) defpackage.esb.a.a.a(ctl.class);
            if (!(this.o == null || this.b == null)) {
                if (this.b.k == RideType.RIDE_TYPE) {
                    str = "10";
                } else if (this.b.k == RideType.DEST_TYPE) {
                    str = "8";
                } else {
                    return;
                }
                this.o.a(str, new RideFinishMapPresenter$2(this, str));
            }
        }
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onPause() {
        super.onPause();
        this.m = true;
    }

    public final void onDestroy() {
        super.onDestroy();
        PlaySoundUtils.getInstance().clear();
        this.a.a();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(true, this.f, false, ((RideFinishMapPage) this.mPage).getMapManager(), ((RideFinishMapPage) this.mPage).getContext());
        }
        bty mapView = ((RideFinishMapPage) this.mPage).getMapView();
        if (mapView != null) {
            mapView.f(0);
            ebf.a(mapView, mapView.j(false), mapView.l(false), this.n);
            mapView.z();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((RideFinishMapPage) this.mPage).b();
        return super.onBackPressed();
    }

    public final void onMapRenderCompleted() {
        super.onMapRenderCompleted();
        eer.a("RideFinishMapPresenter", "onMapRenderCompleted");
        if (this.mPage != null && ((RideFinishMapPage) this.mPage).isAlive()) {
            this.d = true;
            StringBuilder sb = new StringBuilder("onMapAnimationFinished isNeedSavePic ------> ");
            sb.append(this.e);
            eer.a("RideFinishMapPresenter", sb.toString());
            ((RideFinishMapPage) this.mPage).getMapView().f(0);
            if (this.e && this.b != null && this.b.i != null) {
                StringBuilder sb2 = new StringBuilder("viewReady ");
                sb2.append(this.c);
                sb2.append("renderReady ");
                sb2.append(this.d);
                eer.a("RideFinishMapPresenter", sb2.toString());
                if (this.c && this.d) {
                    ((RideFinishMapPage) this.mPage).getMapView().f(0);
                    if (!e()) {
                        StringBuilder sb3 = new StringBuilder("saveTraceBitmap --------> [isNeedSavePic] ");
                        sb3.append(this.e);
                        eer.a("RideFinishMapPresenter", sb3.toString());
                        if (this.b == null) {
                            eer.a("RideFinishMapPresenter", "mTraceHistory == null --------> isNeedSavePic = false");
                            this.e = false;
                            return;
                        }
                        ((RideFinishMapPage) this.mPage).getMapView().a(((RideFinishMapPage) this.mPage).getMapView().al(), ((RideFinishMapPage) this.mPage).getMapView().am(), (defpackage.bty.a) new defpackage.bty.a() {
                            public final void onCallBack(Bitmap bitmap) {
                                StringBuilder sb = new StringBuilder("createBitmapFromGLSurface  --------> onCallBack [isNeedSavePic]");
                                sb.append(eem.this.e);
                                sb.append("[bmp == null]");
                                sb.append(bitmap == null);
                                eer.a("RideFinishMapPresenter", sb.toString());
                                StringBuilder sb2 = new StringBuilder("current thead id and name = ");
                                sb2.append(Thread.currentThread().getId());
                                sb2.append(", ");
                                sb2.append(Thread.currentThread().getName());
                                eer.a("RideFinishMapPresenter", sb2.toString());
                                if (((RideFinishMapPage) eem.this.mPage).isStarted() && bitmap != null && eem.this.e) {
                                    Bitmap a2 = efw.a(bitmap);
                                    StringBuilder sb3 = new StringBuilder("createBitmapFromGLSurface  --------> onCallBack createScaleBitmap [map == null]");
                                    sb3.append(a2 == null);
                                    eer.a("RideFinishMapPresenter", sb3.toString());
                                    eet.a(a2, eem.this.b.i);
                                }
                                ((RideFinishMapPage) eem.this.mPage).getMapView().f(true);
                                eer.a("RideFinishMapPresenter", "createBitmapFromGLSurface  --------> after onCallBack [isNeedSavePic] = false");
                                eem.this.e = false;
                            }
                        });
                    }
                }
            }
        }
    }

    private void d() {
        this.a = new eej((AbstractBaseMapPage) this.mPage);
        if (this.b != null && this.b.j != null) {
            ArrayList<com.autonavi.minimap.route.ride.beans.RideTraceHistory.a> arrayList = this.b.j.e;
            if (arrayList != null) {
                com.autonavi.minimap.route.ride.beans.RideTraceHistory.a[] aVarArr = new com.autonavi.minimap.route.ride.beans.RideTraceHistory.a[arrayList.size()];
                for (int i2 = 0; i2 < aVarArr.length; i2++) {
                    aVarArr[i2] = arrayList.get(i2);
                }
                this.a.a(aVarArr, this.b.j.a, this.b.j.b, this.b.j.c, this.b.j.d);
            }
        }
    }

    private boolean e() {
        if (!new File(eet.a(), this.b.i).exists()) {
            return false;
        }
        ((RideFinishMapPage) this.mPage).getMapView().f(true);
        eer.a("RideFinishMapPresenter", "checkFileExist  isNeedSavePic = false ");
        this.e = false;
        return true;
    }

    public final void a() {
        if (this.b != null) {
            this.a.b(this.b.k);
        }
    }

    public final Bitmap b() {
        if (this.l != null) {
            return BitmapFactory.decodeFile(efj.a("runTraceThumbnail.png"));
        }
        return null;
    }

    public final String c() {
        if (this.l != null) {
            return efj.a("runTrace.png");
        }
        return null;
    }

    static /* synthetic */ void a(eem eem, Bitmap bitmap) {
        if (eem.b == null || bitmap == null) {
            ToastHelper.showToast(((RideFinishMapPage) eem.mPage).getString(R.string.screenshot_fail));
            ((RideFinishMapPage) eem.mPage).a();
            return;
        }
        RouteRideShareView routeRideShareView = new RouteRideShareView(((RideFinishMapPage) eem.mPage).getContext());
        routeRideShareView.setMapBg(bitmap);
        routeRideShareView.bindData(eem.b);
        new efj();
        final Bitmap a2 = efj.a(((RideFinishMapPage) eem.mPage).getContext(), bitmap, routeRideShareView);
        if (a2 == null) {
            ToastHelper.showToast(((RideFinishMapPage) eem.mPage).getString(R.string.screenshot_fail));
            ((RideFinishMapPage) eem.mPage).a();
            return;
        }
        eem.l = new efj();
        eem.k = new a((RideFinishMapPage) eem.mPage);
        ahm.a(new Runnable() {
            public final void run() {
                eem.this.l;
                Bitmap a2 = ahc.a(a2, a2.getWidth() >> 3, a2.getHeight() >> 3);
                eem.this.l;
                efj.a(a2, efj.a("runTraceThumbnail.png"));
                eem.this.l;
                Bitmap bitmap = a2;
                eem.this.l;
                efj.a(bitmap, efj.a("runTrace.png"));
                eem.this.k.sendEmptyMessage(0);
                ((RideFinishMapPage) eem.this.mPage).a();
            }
        });
    }
}
