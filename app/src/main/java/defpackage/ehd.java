package defpackage;

import android.app.Activity;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback.a;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfo;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType;
import com.autonavi.jni.eyrie.amap.tracker.TrackPoster;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd2Param;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd3Request;
import com.autonavi.minimap.bicycle.param.ShareBikeIConconfRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeUserInfoRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeUserTagRequest;
import com.autonavi.minimap.order.param.BikeCheckOrderRequest;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.foot.RouteFootResultData;
import com.autonavi.minimap.route.foot.model.OnFootNaviPath;
import com.autonavi.minimap.route.logs.track.TrackPostUtils;
import com.autonavi.minimap.route.sharebike.model.AbsBicycleItem;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.BicycleBasicItem;
import com.autonavi.minimap.route.sharebike.model.BicycleDetailItem;
import com.autonavi.minimap.route.sharebike.model.BicycleStatus;
import com.autonavi.minimap.route.sharebike.model.CheckOrder;
import com.autonavi.minimap.route.sharebike.model.Foot;
import com.autonavi.minimap.route.sharebike.model.IconConf;
import com.autonavi.minimap.route.sharebike.model.IconConf.a.C0053a;
import com.autonavi.minimap.route.sharebike.model.IconConf.a.b;
import com.autonavi.minimap.route.sharebike.model.IconConf.a.c;
import com.autonavi.minimap.route.sharebike.model.MixBicycle;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.model.UserInfo;
import com.autonavi.minimap.route.sharebike.model.UserTagData;
import com.autonavi.minimap.route.sharebike.model.UserTagDataFees;
import com.autonavi.minimap.route.sharebike.model.UserTagDataInfo;
import com.autonavi.minimap.route.sharebike.model.UserTagInfo;
import com.autonavi.minimap.route.sharebike.net.parser.UserInfoResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest;
import com.autonavi.minimap.route.sharebike.net.request.BicycleDetailRequest;
import com.autonavi.minimap.route.sharebike.net.request.BicycleStatusRequest;
import com.autonavi.minimap.route.sharebike.net.request.IconConfRequest;
import com.autonavi.minimap.route.sharebike.net.request.UserTagRequest;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import com.autonavi.minimap.route.sharebike.presenter.ShareBikePresenter$18;
import com.autonavi.minimap.route.sharebike.presenter.ShareBikePresenter$19;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ehd reason: default package */
/* compiled from: ShareBikePresenter */
public final class ehd extends eae<ShareBikePage> implements a {
    /* access modifiers changed from: private */
    public boolean A;
    /* access modifiers changed from: private */
    public boolean B;
    /* access modifiers changed from: private */
    public boolean C = true;
    /* access modifiers changed from: private */
    public boolean D;
    private boolean E;
    private MapSharePreference F;
    private cei G;
    /* access modifiers changed from: private */
    public ctl H;
    private boolean I = true;
    private float J;
    private float K;
    /* access modifiers changed from: private */
    public Handler L = new Handler() {
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    sendEmptyMessageDelayed(1, StatisticConfig.MIN_UPLOAD_INTERVAL);
                    ehd.at(ehd.this);
                    return;
                case 2:
                    Bundle bundle = (Bundle) message.obj;
                    ehd.this.t = (BaseNetResult) bundle.getSerializable("result");
                    ehd.a(ehd.this, ehd.this.e, bundle.getBoolean("zoom"));
                    break;
            }
        }
    };
    public eha a;
    public List<egr> b = new ArrayList();
    public GeoPoint c;
    public String d;
    public int e = 0;
    public boolean f;
    public String g = "";
    public String h;
    /* access modifiers changed from: private */
    public UserTagDataFees i;
    private AosRequest j;
    private a k;
    private POI l;
    /* access modifiers changed from: private */
    public POI m;
    private GeoPoint n;
    /* access modifiers changed from: private */
    public GeoPoint o;
    /* access modifiers changed from: private */
    public POI p;
    private String q;
    private MixBicycle r;
    /* access modifiers changed from: private */
    public BicycleDetailItem s;
    /* access modifiers changed from: private */
    public BaseNetResult t;
    /* access modifiers changed from: private */
    public RouteFootResultData u;
    /* access modifiers changed from: private */
    public BicycleStatus v;
    /* access modifiers changed from: private */
    public IconConf w;
    /* access modifiers changed from: private */
    public Foot x;
    /* access modifiers changed from: private */
    public String y;
    /* access modifiers changed from: private */
    public String z;

    public ehd(ShareBikePage shareBikePage) {
        super(shareBikePage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.d = e();
        this.F = new MapSharePreference(SharePreferenceName.SharedPreferences);
        this.a = new eha((ShareBikePage) this.mPage);
        this.a.e = this;
        this.c = new GeoPoint();
        this.c = LocationInstrument.getInstance().getLatestPosition();
        PageBundle arguments = ((ShareBikePage) this.mPage).getArguments();
        if (arguments != null) {
            if (arguments.containsKey(LocationParams.PARA_FLP_AUTONAVI_LON) && arguments.containsKey("lat")) {
                String string = arguments.getString(LocationParams.PARA_FLP_AUTONAVI_LON);
                String string2 = arguments.getString("lat");
                if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                    this.c = new GeoPoint(Double.parseDouble(string), Double.parseDouble(string2));
                }
            }
            String string3 = arguments.getString("sharebike_page_from");
            if (arguments.containsKey("sharebike_page_from") && TextUtils.equals(string3, "gjxq")) {
                if (arguments.containsKey("poi_name") && !TextUtils.equals(((ShareBikePage) this.mPage).getString(R.string.route_foot_navi_end_from_my_position), arguments.getString("poi_name"))) {
                    this.h = arguments.getString("poi_name");
                    ((ShareBikePage) this.mPage).a(this.h);
                }
                String string4 = arguments.getString("end_lon");
                String string5 = arguments.getString("end_lat");
                String string6 = arguments.getString("end_name");
                if (TextUtils.isEmpty(string4) || TextUtils.isEmpty(string5) || TextUtils.isEmpty(string6)) {
                    ehu.a(null);
                } else {
                    this.p = POIFactory.createPOI(string6, new GeoPoint(Double.parseDouble(string4), Double.parseDouble(string5)));
                    ehu.a(this.p);
                }
            }
            JSONObject jSONObject = new JSONObject();
            try {
                if (TextUtils.isEmpty(string3)) {
                    string3 = "else";
                }
                jSONObject.put("from", string3);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00298", "B008", jSONObject);
        }
        ((ShareBikePage) this.mPage).a(this.c);
        this.G = new cei();
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onCover() {
                ehd.this.C = true;
                if (ehd.this.H != null) {
                    ctl b = ehd.this.H;
                    ehd.this.mPage;
                    b.a("19");
                }
            }

            public final void onAppear() {
                ehd.this.f = true;
            }
        });
        d();
        j();
        eht.a((a) new a() {
            public final void a() {
                ((ShareBikePage) ehd.this.mPage).f();
            }
        });
    }

    /* access modifiers changed from: private */
    public void d() {
        egu.a(new ShareBikeUserInfoRequest(), (BaseRequest.a) new BaseRequest.a() {
            public final void a(BaseNetResult baseNetResult) {
                if (baseNetResult instanceof UserInfo) {
                    UserInfo userInfo = (UserInfo) baseNetResult;
                    if (userInfo.errorCode == 14) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(UserInfoResponser.class.getName());
                        sb.append("  --- >> ");
                        sb.append(userInfo.errorCode);
                        eao.e("UserInfo", sb.toString());
                        bky.b();
                    } else if (baseNetResult.result && userInfo.errorCode == 1) {
                        List<egt> list = userInfo.items;
                        String b = ehs.b("share_bike_cp_source");
                        for (int i = 0; i < list.size(); i++) {
                            egt egt = list.get(i);
                            if (egt != null) {
                                ehd.a(ehd.this, egt, b);
                                bky.a(egt.d, egt.e);
                            }
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(UserInfoResponser.class.getName());
                        sb2.append("  --- >> ");
                        sb2.append(userInfo.errorCode);
                        eao.e("UserInfo", sb2.toString());
                    }
                }
            }
        });
    }

    private static String e() {
        return new MapSharePreference((String) "SHARE_BIKE_SETTING").getStringValue("cpinfo_source", "all");
    }

    /* access modifiers changed from: private */
    public static void b(egr egr) {
        if (egr != null) {
            MapSharePreference mapSharePreference = new MapSharePreference((String) "SHARE_BIKE_SETTING");
            mapSharePreference.putStringValue("cpinfo_source", egr.a);
            mapSharePreference.putStringValue("cpinfo_name", egr.b);
        }
    }

    public final void onResume() {
        super.onResume();
        if (h()) {
            RideState c2 = egj.a().c();
            if (c2 != null && c2.result && (c2.status == 1 || c2.status == 2)) {
                ehs.a((String) "share_bike_riding_status_id", (String) "true");
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("CpSource", ehs.b("share_bike_cp_source"));
                egj.a().d();
                ((ShareBikePage) this.mPage).startPage(ShareRidingMapPage.class, pageBundle);
                aho.a(new Runnable() {
                    public final void run() {
                        ((ShareBikePage) ehd.this.mPage).finish();
                        if (ehd.this.C) {
                            ehd.this.a(true);
                        }
                    }
                });
            } else if (!this.I) {
                this.I = true;
                if (this.C) {
                    a(true);
                }
            } else {
                egu.a(new BikeCheckOrderRequest(), (BaseRequest.a) new BaseRequest.a() {
                    public final void a(BaseNetResult baseNetResult) {
                        int i;
                        if (ehd.this.mPage != null && ((ShareBikePage) ehd.this.mPage).isStarted() && ((ShareBikePage) ehd.this.mPage).isAlive()) {
                            if (baseNetResult != null && baseNetResult.result) {
                                CheckOrder checkOrder = (CheckOrder) baseNetResult;
                                StringBuilder sb = new StringBuilder("checkOrder:");
                                sb.append(checkOrder.status);
                                sb.append(", ");
                                sb.append(checkOrder.ridingStatus);
                                eao.a((String) "sharebike", sb.toString());
                                if (checkOrder.ridingStatus == 1 && checkOrder.status == 1) {
                                    ehs.a((String) "share_bike_riding_status_id", (String) "true");
                                    ehd.a(checkOrder);
                                    egj.a().d();
                                    eht.e();
                                    PageBundle pageBundle = new PageBundle();
                                    pageBundle.putString("CpSource", checkOrder.cpSource);
                                    ((ShareBikePage) ehd.this.mPage).startPage(ShareRidingMapPage.class, pageBundle);
                                    ((ShareBikePage) ehd.this.mPage).finish();
                                } else if (checkOrder.ridingStatus == 1 && checkOrder.status == 0) {
                                    ehd.a(checkOrder);
                                    try {
                                        i = Integer.parseInt(checkOrder.loadingtime);
                                    } catch (NumberFormatException unused) {
                                        i = -1;
                                    }
                                    egj.a().d();
                                    eht.a((long) i);
                                    if (!eht.h()) {
                                        if (ehd.this.p != null) {
                                            ehu.a(ehd.this.p);
                                        }
                                        ehs.a((String) "share_bike_riding_status_id", (String) "true");
                                        PageBundle pageBundle2 = new PageBundle();
                                        pageBundle2.putString("CpSource", ehs.b("share_bike_cp_source"));
                                        egj.a().d();
                                        ((ShareBikePage) ehd.this.mPage).startPage(ShareRidingMapPage.class, pageBundle2);
                                        ((ShareBikePage) ehd.this.mPage).finish();
                                    }
                                    ((ShareBikePage) ehd.this.mPage).finish();
                                    return;
                                } else if (checkOrder.ridingStatus == 1 && checkOrder.status == 6) {
                                    ehd.a(checkOrder);
                                    StringBuilder sb2 = new StringBuilder("ShareBikePresener upload sharebike ");
                                    sb2.append(checkOrder.cpSource);
                                    sb2.append(", orderid:");
                                    sb2.append(checkOrder.orderId);
                                    TrackPostUtils.a(sb2.toString());
                                    TrackInfo createTrackInfo = TrackInfo.createTrackInfo(TrackType.SHAREBIKE);
                                    if (createTrackInfo != null) {
                                        createTrackInfo.set(TrackInfoKeyType.ShareBikeSource, checkOrder.cpSource);
                                        createTrackInfo.set(TrackInfoKeyType.ShareBikeOrderId, checkOrder.orderId);
                                        TrackPoster.upload(TrackType.SHAREBIKE);
                                    }
                                    return;
                                }
                            }
                            if (ehd.this.C) {
                                ehd.this.a(true);
                            }
                        }
                    }
                });
            }
        }
        if (h()) {
            ShareBikeUserTagRequest shareBikeUserTagRequest = new ShareBikeUserTagRequest();
            shareBikeUserTagRequest.b = String.valueOf(System.currentTimeMillis());
            a.a((BaseRequest) new UserTagRequest(shareBikeUserTagRequest, new BaseRequest.a() {
                public final void a(BaseNetResult baseNetResult) {
                    if (baseNetResult == null || !(baseNetResult instanceof UserTagInfo)) {
                        ehd.a(ehd.this, (String) "");
                        return;
                    }
                    UserTagInfo userTagInfo = (UserTagInfo) baseNetResult;
                    if (userTagInfo.result && userTagInfo.errorCode == 1) {
                        UserTagData data = userTagInfo.getData();
                        ehd.this.i = data == null ? null : data.getFees();
                        ShareBikePage shareBikePage = (ShareBikePage) ehd.this.mPage;
                        UserTagDataInfo taginfo = userTagInfo == null ? null : data.getTaginfo();
                        if (taginfo != null) {
                            UserTagDataInfo.a icon = taginfo.getIcon();
                            if (icon != null) {
                                String str = icon.b;
                                String str2 = icon.a;
                                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                                    ImageLoader.a(shareBikePage.getContext()).a(str).a(shareBikePage.a, (bjl) null);
                                    ImageLoader.a(shareBikePage.getContext()).a(str2).a(shareBikePage.b, (bjl) null);
                                }
                            }
                        }
                    }
                    ehd.a(ehd.this, userTagInfo.getAllInfoJson());
                }
            }));
        }
        ShareBikePage shareBikePage = (ShareBikePage) this.mPage;
        if (!(shareBikePage.getMapManager() == null || shareBikePage.getMapManager().getMapView() == null)) {
            shareBikePage.getMapManager().getMapView().c(30);
        }
        shareBikePage.a();
        shareBikePage.k = false;
        ((ShareBikePage) this.mPage).f();
        a(this.e);
        if (this.e == 1) {
            if (this.f) {
                c();
                this.f = false;
            } else {
                k();
                a(this.v, false, false, false);
            }
        } else if (this.e == 0 && !this.C) {
            a(this.v, true, true, false);
        }
        if (!this.D) {
            j();
        }
    }

    public final void onPause() {
        super.onPause();
        ShareBikePage shareBikePage = (ShareBikePage) this.mPage;
        if (!(shareBikePage.getMapManager() == null || shareBikePage.getMapManager().getMapView() == null)) {
            shareBikePage.getMapManager().getMapView().c(shareBikePage.h);
            bty mapView = shareBikePage.getMapView();
            if (mapView != null) {
                mapView.z();
                mapView.B();
            }
        }
        if (this.L != null) {
            this.L.removeCallbacksAndMessages(null);
        }
        p();
        this.C = true;
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.G != null) {
            this.G = null;
        }
        ShareBikePage shareBikePage = (ShareBikePage) this.mPage;
        bty mapView = shareBikePage.getMapView();
        if (mapView != null) {
            mapView.b(ags.a(AMapPageUtil.getAppContext()).width() / 2, ags.a(AMapPageUtil.getAppContext()).height() / 2);
            ebf.a(mapView, shareBikePage.j.c, mapView.l(false), shareBikePage.j.d);
            mapView.g(shareBikePage.j.b);
            mapView.e(shareBikePage.j.a);
            mapView.a(shareBikePage.j.f.x, shareBikePage.j.f.y);
            if (mapView.w() != ((int) shareBikePage.j.e)) {
                mapView.f(shareBikePage.j.e);
            }
        }
        if (shareBikePage.c != null) {
            shareBikePage.c.destroy();
            shareBikePage.c.onAjxContextCreated(null);
        }
        this.L = null;
        eht.b();
        eht.a((a) null);
    }

    public final boolean onEngineActionGesture(alg alg) {
        this.E = ((ShareBikePage) this.mPage).isStarted();
        return super.onEngineActionGesture(alg);
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        if (TextUtils.isEmpty(this.h) || this.e == 1) {
            return super.onMapTouchEvent(motionEvent);
        }
        if (this.a != null) {
            if (motionEvent.getAction() == 0) {
                this.J = motionEvent.getX();
                this.K = motionEvent.getY();
            } else if (motionEvent.getAction() == 2 && Math.abs(this.J - motionEvent.getX()) > 40.0f && Math.abs(this.K - motionEvent.getY()) > 40.0f) {
                b();
            }
        }
        return super.onMapTouchEvent(motionEvent);
    }

    public final boolean onMapMotionStop() {
        if (!this.E || this.e == 1) {
            return false;
        }
        ebr.a(true).post(new Runnable() {
            public final void run() {
                ehd.d(ehd.this);
            }
        });
        return super.onMapMotionStop();
    }

    public final void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
        if (!TextUtils.isEmpty(this.h) && this.a != null) {
            eha eha = this.a;
            if (eha.g != null) {
                ebr.a(true).post(new Runnable() {
                    public final void run() {
                        Rect rect;
                        eha eha = eha.this;
                        Rect rect2 = null;
                        if (eha.i == null) {
                            if (eha.a != null) {
                                ShareBikePage shareBikePage = eha.a;
                                if (shareBikePage.f != null) {
                                    rect = shareBikePage.f.getCenterPoiRect();
                                    eha.i = rect;
                                }
                            }
                            rect = null;
                            eha.i = rect;
                        }
                        Rect rect3 = eha.i;
                        eha eha2 = eha.this;
                        GeoPoint geoPoint = eha.this.g;
                        if (geoPoint != null) {
                            PointF f = eha2.c.f(geoPoint.x, geoPoint.y);
                            if (f != null) {
                                rect2 = new Rect();
                                int a2 = agn.a(AMapPageUtil.getAppContext(), 41.0f);
                                int a3 = agn.a(AMapPageUtil.getAppContext(), 19.0f);
                                int a4 = agn.a(AMapPageUtil.getAppContext(), 38.0f);
                                rect2.left = ((int) f.x) - a2;
                                rect2.right = ((int) f.x) + a2;
                                rect2.top = (((int) f.y) - a3) - a4;
                                rect2.bottom = ((int) f.y) - a4;
                            }
                        }
                        if (!(rect3 == null || rect2 == null)) {
                            if (Rect.intersects(rect3, rect2)) {
                                eha.this.b(false);
                                return;
                            }
                            eha.this.b(true);
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        if (!NetworkReachability.b()) {
            ((ShareBikePage) this.mPage).e(8);
        }
    }

    public final boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        if (this.e == 1) {
            a(0);
            aho.a(new Runnable() {
                public final void run() {
                    ehd.this.b(true);
                }
            }, 200);
        }
        return super.onMapSingleClick(motionEvent, geoPoint);
    }

    public final void a(GeoPoint geoPoint, BicycleBasicItem bicycleBasicItem, String str) {
        if (bicycleBasicItem != null) {
            String source = bicycleBasicItem.getSource();
            if (!TextUtils.isEmpty(source)) {
                String lowerCase = source.trim().toLowerCase();
                int i2 = lowerCase.startsWith("mobike") ? 1 : lowerCase.startsWith("ofo") ? 2 : 3;
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("from", i2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                a(jSONObject, (String) "B006");
            }
        }
        this.A = true;
        this.B = false;
        this.y = null;
        this.e = 1;
        if (this.c == null) {
            this.c = LocationInstrument.getInstance().getLatestPosition();
        }
        String a2 = eht.a(this.c);
        this.n = geoPoint;
        if (this.L != null) {
            this.L.removeMessages(1);
            this.r = new MixBicycle(bicycleBasicItem, str, a2);
            this.L.sendEmptyMessage(1);
        }
    }

    public final void a() {
        boolean z2;
        if (((ShareBikePage) this.mPage).getContext() != null && ((ShareBikePage) this.mPage).isAlive() && this.B && this.m != null) {
            final Activity activity = ((ShareBikePage) this.mPage).getActivity();
            if (cfe.a(LocationInstrument.getInstance().getLatestPosition(), this.m.getPoint()) < 100000.0f) {
                z2 = true;
            } else {
                aho.a(new Runnable() {
                    public final void run() {
                        if (activity != null && !activity.isFinishing()) {
                            AMapPageUtil.startAlertDialogPage(new Builder(activity).setTitle((CharSequence) activity.getString(R.string.share_bike_route_foot_alert)).setAutoFinished(false).setPositiveButton((CharSequence) activity.getString(R.string.share_bike_route_foot_alert_submit), (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                    nodeAlertDialogPage.finish();
                                }
                            }));
                        }
                    }
                }, 0);
                z2 = false;
            }
            if (z2) {
                if (!edr.a((String) "agree_onfoot_declare", false)) {
                    ShareBikePage shareBikePage = (ShareBikePage) this.mPage;
                    AnonymousClass17 r1 = new egi() {
                        public final void a() {
                            edr.b((String) "agree_onfoot_declare", true);
                            ehd.this.g();
                        }
                    };
                    if (shareBikePage.getActivity() != null && (shareBikePage.g == null || !shareBikePage.g.isShowing())) {
                        shareBikePage.g = new ConfirmDlg(shareBikePage.getActivity(), new OnClickListener(r1) {
                            final /* synthetic */ egi a;

                            {
                                this.a = r2;
                            }

                            public final void onClick(View view) {
                                int id = view.getId();
                                if (id == R.id.cancel) {
                                    ShareBikePage.r(ShareBikePage.this);
                                    return;
                                }
                                if (id == R.id.confirm && this.a != null) {
                                    this.a.a();
                                }
                            }
                        }, R.layout.onfoot_declare);
                        shareBikePage.g.show();
                    }
                    return;
                }
                g();
            }
        }
    }

    public final void b() {
        if (!TextUtils.isEmpty(this.h)) {
            this.h = null;
            ((ShareBikePage) this.mPage).f.dismissPoiNameView();
            if (this.a != null) {
                this.a.b(true);
            }
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        if (this.m != null) {
            a((JSONObject) null, (String) "B003");
            PageBundle pageBundle = new PageBundle();
            avi avi = (avi) a.a.a(avi.class);
            if (avi != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("endPoi", bnx.b(this.m));
                    jSONObject.put("fromWhere", "ldy");
                    jSONObject.put(H5Param.FROM_TYPE, "nfbike");
                    pageBundle.putString("bundle_key_obj_result", jSONObject.toString());
                    ((ShareBikePage) this.mPage).startPageForResult(avi.c().a(1), pageBundle, 0);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private boolean h() {
        if (NetworkReachability.b()) {
            return true;
        }
        if (this.a != null) {
            this.a.a();
        }
        ((ShareBikePage) this.mPage).g(2);
        this.v = null;
        return false;
    }

    private void c(final boolean z2) {
        if (this.c != null) {
            ehr.a(this.c, (b) new b() {
                public final void a(a aVar) {
                    if (ehd.this.C) {
                        ehd.this.C = false;
                    }
                    if (ehd.this.mPage != null && ((ShareBikePage) ehd.this.mPage).isAlive()) {
                        if (aVar == null) {
                            ehd.this.d = "all";
                            ehd.b(ehd.this.i());
                            ((ShareBikePage) ehd.this.mPage).b(0);
                            ehd.this.b(z2);
                            ((ShareBikePage) ehd.this.mPage).e(8);
                            return;
                        }
                        ehd.this.b.clear();
                        if (aVar.a == null) {
                            aVar.a = new ArrayList();
                        }
                        List<egr> list = aVar.a;
                        ehd.a(ehd.this, (List) list);
                        ehd.this.g = aVar.d;
                        ehd.this.t = null;
                        if (list.isEmpty() || list.size() == 1) {
                            if (list.size() == 1) {
                                ehd.this.d = list.get(0).a;
                                ehd.b(list.get(0));
                                ehd.this.b.addAll(list);
                            } else {
                                ehd.this.d = "all";
                                ehd.b(ehd.this.i());
                                egr egr = new egr();
                                egr.b = ((ShareBikePage) ehd.this.mPage).getString(R.string.sharebike_type_total_bikes);
                                egr.a = "all";
                                ehd.this.b.add(egr);
                            }
                            ((ShareBikePage) ehd.this.mPage).b(0);
                            ehd.this.b(z2);
                            ((ShareBikePage) ehd.this.mPage).e(8);
                            return;
                        }
                        egr egr2 = new egr();
                        egr2.b = ((ShareBikePage) ehd.this.mPage).getString(R.string.sharebike_type_total_bikes);
                        egr2.a = "all";
                        ehd.this.b.add(egr2);
                        ehd.this.b.addAll(list);
                        ((ShareBikePage) ehd.this.mPage).a(ehd.this.b);
                        if (((ShareBikePage) ehd.this.mPage).isAlive() && !ehd.D(ehd.this)) {
                            ((ShareBikePage) ehd.this.mPage).e(0);
                        }
                        ehd.F(ehd.this);
                        ehd.this.b(z2);
                    }
                }
            });
        }
    }

    public final void a(boolean z2) {
        ((ShareBikePage) this.mPage).c();
        if (h()) {
            ((ShareBikePage) this.mPage).g(1);
            if (this.e == 0) {
                m();
            }
            if (this.e == 0) {
                ((ShareBikePage) this.mPage).b(true);
            }
            if (this.c == null) {
                this.c = LocationInstrument.getInstance().getLatestPosition();
            }
            c(z2);
        }
    }

    /* access modifiers changed from: private */
    public egr i() {
        egr egr = new egr();
        egr.b = ((ShareBikePage) this.mPage).getString(R.string.sharebike_type_total_bikes);
        egr.a = "all";
        return egr;
    }

    public final void b(final boolean z2) {
        if (!h()) {
            ((ShareBikePage) this.mPage).b(false);
            return;
        }
        if (this.c == null) {
            this.c = LocationInstrument.getInstance().getLatestPosition();
        }
        if (this.d.equalsIgnoreCase("all")) {
            ((ShareBikePage) this.mPage).g(1);
            if (this.e == 0) {
                ((ShareBikePage) this.mPage).b(true);
            }
            final BicycleStatusCmd2Param bicycleStatusCmd2Param = new BicycleStatusCmd2Param();
            bicycleStatusCmd2Param.b = eht.a(this.c);
            bicycleStatusCmd2Param.c = String.valueOf(this.c.getLongitude());
            bicycleStatusCmd2Param.d = String.valueOf(this.c.getLatitude());
            bicycleStatusCmd2Param.e = "all";
            bicycleStatusCmd2Param.k = "amap";
            bicycleStatusCmd2Param.h = "2";
            a.b(new BicycleStatusRequest(bicycleStatusCmd2Param, new BaseRequest.a() {
                public final void a(BaseNetResult baseNetResult) {
                    ((ShareBikePage) ehd.this.mPage).b(false);
                    if (ehd.this.L != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("result", baseNetResult);
                        bundle.putString("source", bicycleStatusCmd2Param.e);
                        bundle.putBoolean("zoom", z2);
                        ehd.this.L.obtainMessage(2, bundle).sendToTarget();
                    }
                }
            }));
            return;
        }
        final BicycleStatusCmd2Param bicycleStatusCmd2Param2 = new BicycleStatusCmd2Param();
        bicycleStatusCmd2Param2.b = eht.a(this.c);
        bicycleStatusCmd2Param2.c = String.valueOf(this.c.getLongitude());
        bicycleStatusCmd2Param2.d = String.valueOf(this.c.getLatitude());
        bicycleStatusCmd2Param2.e = this.d;
        bicycleStatusCmd2Param2.k = "amap";
        bicycleStatusCmd2Param2.h = "2";
        ((ShareBikePage) this.mPage).g(1);
        if (this.e == 0) {
            ((ShareBikePage) this.mPage).b(true);
        }
        a.a((BaseRequest) new BicycleStatusRequest(bicycleStatusCmd2Param2, new BaseRequest.a() {
            public final void a(BaseNetResult baseNetResult) {
                ((ShareBikePage) ehd.this.mPage).b(false);
                if (ehd.this.L != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("result", baseNetResult);
                    bundle.putString("source", bicycleStatusCmd2Param2.e);
                    bundle.putBoolean("zoom", z2);
                    ehd.this.L.obtainMessage(2, bundle).sendToTarget();
                }
            }
        }));
    }

    /* access modifiers changed from: private */
    public void k() {
        if (this.a != null && this.x != null && this.x.points != null && !this.x.points.isEmpty() && this.o != null) {
            if (this.e == 0) {
                this.a.d();
                return;
            }
            this.a.a(this.o);
            this.a.a(new String[]{this.x.distance, this.x.time}, this.n, this.B, false);
            int size = this.x.points.size();
            this.a.a((GeoPoint[]) this.x.points.toArray(new GeoPoint[size]));
            this.a.a(this.o.x, this.o.y, this.x.points.get(0).x, this.x.points.get(0).y);
            if (this.m != null) {
                GeoPoint point = this.m.getPoint();
                if (point != null) {
                    int i2 = size - 1;
                    this.a.a(this.x.points.get(i2).x, this.x.points.get(i2).y, point.x, point.y);
                }
            }
            this.a.f();
        }
    }

    /* access modifiers changed from: private */
    public void a(BicycleStatus bicycleStatus, boolean z2, boolean z3, boolean z4) {
        this.a.a(bicycleStatus, z2, z3);
        if (z4) {
            this.a.a(bicycleStatus, this.c);
        }
    }

    private void l() {
        this.l = null;
        this.m = null;
        this.z = null;
        this.s = null;
        this.y = null;
    }

    private void m() {
        if (this.a != null) {
            this.a.e();
        }
    }

    /* access modifiers changed from: private */
    public POI n() {
        if (this.l == null) {
            MapManager mapManager = ((ShareBikePage) this.mPage).getMapManager();
            if (mapManager == null) {
                return null;
            }
            this.c = mapManager.getMapView().o();
            if (this.c == null) {
                return null;
            }
            this.l = POIFactory.createPOI("", this.c);
        }
        return this.l;
    }

    @Nullable
    private static List<AbsBicycleItem> b(BaseNetResult baseNetResult) {
        if (!(baseNetResult instanceof BicycleStatus)) {
            return null;
        }
        ego bicycle = ((BicycleStatus) baseNetResult).getBicycle();
        if (bicycle == null) {
            return null;
        }
        egp egp = bicycle.a;
        if (egp == null) {
            return null;
        }
        List<AbsBicycleItem> list = egp.c;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00a1 A[Catch:{ JSONException -> 0x00b0 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String o() {
        /*
            r4 = this;
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r1 = "show"
            java.lang.String r2 = "1"
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r1 = "partnerLogo"
            java.lang.String r2 = r4.z     // Catch:{ JSONException -> 0x00b0 }
            if (r2 == 0) goto L_0x0015
            java.lang.String r2 = r4.z     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x0017
        L_0x0015:
            java.lang.String r2 = ""
        L_0x0017:
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r1 = "partnerName"
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r2 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            if (r2 == 0) goto L_0x0025
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r2 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r2 = r2.partnerName     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x0027
        L_0x0025:
            java.lang.String r2 = ""
        L_0x0027:
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r1 = "feesPrice"
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r2 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            if (r2 == 0) goto L_0x0035
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r2 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r2 = r2.fees     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x0037
        L_0x0035:
            java.lang.String r2 = ""
        L_0x0037:
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r1 = "feesInterval"
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r2 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            if (r2 == 0) goto L_0x0045
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r2 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r2 = r2.feesInterval     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x0047
        L_0x0045:
            java.lang.String r2 = ""
        L_0x0047:
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r1 = "favourableDescription"
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r2 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            if (r2 == 0) goto L_0x0055
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r2 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r2 = r2.favourableDesc     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x0057
        L_0x0055:
            java.lang.String r2 = ""
        L_0x0057:
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r1 = "address"
            java.lang.String r2 = r4.y     // Catch:{ JSONException -> 0x00b0 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x00b0 }
            if (r2 == 0) goto L_0x0073
            com.autonavi.map.fragmentcontainer.page.IPage r2 = r4.mPage     // Catch:{ JSONException -> 0x00b0 }
            com.autonavi.minimap.route.sharebike.page.ShareBikePage r2 = (com.autonavi.minimap.route.sharebike.page.ShareBikePage) r2     // Catch:{ JSONException -> 0x00b0 }
            android.content.Context r2 = r2.getContext()     // Catch:{ JSONException -> 0x00b0 }
            int r3 = com.autonavi.minimap.R.string.text_loading_sb_tab     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r2 = r2.getString(r3)     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x0075
        L_0x0073:
            java.lang.String r2 = r4.y     // Catch:{ JSONException -> 0x00b0 }
        L_0x0075:
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x00b0 }
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r1 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            if (r1 == 0) goto L_0x009e
            com.autonavi.minimap.route.sharebike.model.UserTagDataFees r1 = r4.i     // Catch:{ JSONException -> 0x00b0 }
            if (r1 == 0) goto L_0x009e
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r1 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            boolean r1 = r1.isPartnerOfo()     // Catch:{ JSONException -> 0x00b0 }
            if (r1 == 0) goto L_0x008f
            com.autonavi.minimap.route.sharebike.model.UserTagDataFees r1 = r4.i     // Catch:{ JSONException -> 0x00b0 }
            com.autonavi.minimap.route.sharebike.model.UserTagDataFees$a r1 = r1.getOfo()     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x009f
        L_0x008f:
            com.autonavi.minimap.route.sharebike.model.BicycleDetailItem r1 = r4.s     // Catch:{ JSONException -> 0x00b0 }
            boolean r1 = r1.isPartnerMobike()     // Catch:{ JSONException -> 0x00b0 }
            if (r1 == 0) goto L_0x009e
            com.autonavi.minimap.route.sharebike.model.UserTagDataFees r1 = r4.i     // Catch:{ JSONException -> 0x00b0 }
            com.autonavi.minimap.route.sharebike.model.UserTagDataFees$a r1 = r1.getMobike()     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x009f
        L_0x009e:
            r1 = 0
        L_0x009f:
            if (r1 == 0) goto L_0x00b4
            java.lang.String r2 = "feesDesc"
            java.lang.String r3 = r1.b     // Catch:{ JSONException -> 0x00b0 }
            r0.put(r2, r3)     // Catch:{ JSONException -> 0x00b0 }
            java.lang.String r2 = "tagDesc"
            java.lang.String r1 = r1.a     // Catch:{ JSONException -> 0x00b0 }
            r0.put(r2, r1)     // Catch:{ JSONException -> 0x00b0 }
            goto L_0x00b4
        L_0x00b0:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00b4:
            java.lang.String r1 = ">>>>>ShareBike<<<<<"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "CurPageState: "
            r2.<init>(r3)
            int r3 = r4.e
            r2.append(r3)
            java.lang.String r3 = "--------->AJX Json--------->"
            r2.append(r3)
            java.lang.String r3 = r0.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            defpackage.eao.a(r1, r2)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ehd.o():java.lang.String");
    }

    private void p() {
        if (this.a != null) {
            this.a.g();
            this.a.a();
        }
    }

    public final void c() {
        if (this.e == 0) {
            ((ShareBikePage) this.mPage).getMapManager().getMapView().ab();
            this.c = ((ShareBikePage) this.mPage).getMapManager().getMapView().o();
        } else {
            a(0);
        }
        f();
        a(false);
    }

    public final void a(int i2) {
        if (((ShareBikePage) this.mPage).isAlive()) {
            this.e = i2;
            if (this.a != null) {
                this.a.a(this.e == 1);
            }
            switch (i2) {
                case 0:
                    if (this.L != null) {
                        this.L.removeMessages(1);
                    }
                    ((ShareBikePage) this.mPage).e();
                    ((ShareBikePage) this.mPage).f(8);
                    ((ShareBikePage) this.mPage).c(0);
                    ((ShareBikePage) this.mPage).a(this.h);
                    if (!this.C) {
                        ((ShareBikePage) this.mPage).e(0);
                    }
                    ((ShareBikePage) this.mPage).d(8);
                    if (this.a != null) {
                        this.a.d();
                    }
                    m();
                    if (!(((ShareBikePage) this.mPage).getMapManager() == null || ((ShareBikePage) this.mPage).getMapManager().getMapView() == null)) {
                        ((ShareBikePage) this.mPage).getMapManager().getMapView().a((GLGeoPoint) this.c);
                    }
                    l();
                    return;
                case 1:
                    if (this.a != null) {
                        this.a.c();
                    }
                    ((ShareBikePage) this.mPage).f(0);
                    ((ShareBikePage) this.mPage).e(8);
                    ((ShareBikePage) this.mPage).c(8);
                    ((ShareBikePage) this.mPage).f.dismissPoiNameView();
                    ((ShareBikePage) this.mPage).d(0);
                    break;
            }
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (this.e == 1) {
            if (((ShareBikePage) this.mPage).b()) {
                ((ShareBikePage) this.mPage).a(true);
            } else {
                a(0);
                b(true);
            }
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (this.e != 0) {
            return super.onBackPressed();
        } else {
            if (!((ShareBikePage) this.mPage).b()) {
                return ON_BACK_TYPE.TYPE_NORMAL;
            }
            ((ShareBikePage) this.mPage).a(true);
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (i2 == 48 && resultType == ResultType.CANCEL) {
            this.I = false;
        }
    }

    public static void a(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            LogManager.actionLogV2("P00298", str);
        } else {
            LogManager.actionLogV2("P00298", str, jSONObject);
        }
    }

    private void j() {
        this.H = (ctl) a.a.a(ctl.class);
        if (this.H != null) {
            this.H.a("19", new ShareBikePresenter$19(this));
        }
    }

    static /* synthetic */ void a(ehd ehd, egt egt, String str) {
        if (TextUtils.equals(egt.a, "ofo")) {
            ehd.q = egt.c;
            bky.a(egt.d);
        }
        if (!TextUtils.isEmpty(str) && TextUtils.equals(str, egt.a)) {
            ehs.a((String) "share_bike_user_id", egt.b);
            ehs.a((String) "share_bike_token_id", egt.c);
        }
    }

    static /* synthetic */ void d(ehd ehd) {
        bty mapView = ((ShareBikePage) ehd.mPage).getMapManager().getMapView();
        GeoPoint geoPoint = new GeoPoint(mapView.p(), mapView.q());
        int b2 = cfe.b(ehd.c, geoPoint);
        if (b2 > 5) {
            ehd.b();
        }
        if (b2 > 100) {
            ehd.c = geoPoint;
            ehd.f();
            ehd.a(false);
        }
    }

    static /* synthetic */ void a(ehd ehd, String str) {
        if (((ShareBikePage) ehd.mPage).isAlive() && ((ShareBikePage) ehd.mPage).e != null) {
            ((ShareBikePage) ehd.mPage).e.setBannerUserTagData(str);
        }
    }

    static /* synthetic */ void a(CheckOrder checkOrder) {
        if (checkOrder != null) {
            if (!TextUtils.isEmpty(checkOrder.cpSource)) {
                ehs.a((String) "share_bike_cp_source", checkOrder.cpSource);
            }
            if (!TextUtils.isEmpty(checkOrder.orderId)) {
                ehs.a(checkOrder.orderId);
            }
            if (!"null".equalsIgnoreCase(checkOrder.unlockpwd) && !TextUtils.isEmpty(checkOrder.unlockpwd)) {
                ehs.a((String) "share_bike_ofo_un_lockpwd_key", checkOrder.unlockpwd);
            }
            if (!"null".equalsIgnoreCase(checkOrder.repairurl) && !TextUtils.isEmpty(checkOrder.repairurl)) {
                ehs.a((String) "share_bike_ofo_repairurl_key", checkOrder.repairurl);
            }
            if (!"null".equalsIgnoreCase(checkOrder.bikeId) && !TextUtils.isEmpty(checkOrder.bikeId)) {
                ehs.a((String) "share_bike_id", checkOrder.bikeId);
            }
        }
    }

    static /* synthetic */ void a(ehd ehd, List list) {
        if (list == null || list.isEmpty()) {
            ehd.g = "";
            return;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            egr egr = (egr) list.get(i2);
            if (!TextUtils.isEmpty(egr.a) && !TextUtils.isEmpty(egr.b)) {
                ehd.F.putStringValue(egr.a, egr.b);
            }
        }
    }

    static /* synthetic */ boolean D(ehd ehd) {
        return ehd.e == 1;
    }

    static /* synthetic */ boolean F(ehd ehd) {
        boolean z2;
        if (ehd.b == null || ehd.b.size() <= 0) {
            return false;
        }
        String e2 = e();
        int size = ehd.b.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                z2 = false;
                break;
            } else if (e2.equals(ehd.b.get(i2).a)) {
                z2 = true;
                break;
            } else {
                i2++;
            }
        }
        if (z2) {
            return z2;
        }
        ehd.d = "all";
        ((ShareBikePage) ehd.mPage).b(0);
        b(ehd.i());
        return z2;
    }

    static /* synthetic */ void a(IconConf iconConf) {
        if (iconConf != null && iconConf.getData() != null) {
            List<b> list = iconConf.getData().b;
            if (list != null && !list.isEmpty()) {
                for (b next : list) {
                    String str = next.b;
                    String str2 = next.a;
                    String str3 = next.c;
                    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        eht.a(str2, str);
                        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str3)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append("_big");
                            eht.a(str3, sb.toString());
                        }
                    }
                }
            }
            List<C0053a> list2 = iconConf.getData().a;
            if (list2 != null && !list2.isEmpty()) {
                Iterator<C0053a> it = list2.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    C0053a next2 = it.next();
                    if (next2.a != null) {
                        String str4 = next2.b;
                        if (!TextUtils.isEmpty(str4)) {
                            if (next2.a == null) {
                                break;
                            }
                            String str5 = next2.a.b;
                            if (!TextUtils.isEmpty(str5)) {
                                eht.a(str5, str4, 36, 41);
                            }
                            String str6 = next2.a.a;
                            if (!TextUtils.isEmpty(str6)) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(str4);
                                sb2.append("_selecet");
                                eht.a(str6, sb2.toString(), 45, 55);
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            c cVar = iconConf.getData().c;
            if (cVar != null) {
                String str7 = cVar.a;
                String str8 = cVar.b;
                if (!TextUtils.isEmpty(str7)) {
                    eht.a(str7, "scan_btn_select");
                }
                if (!TextUtils.isEmpty(str8)) {
                    eht.a(str8, "scan_btn_normal");
                }
            }
        }
    }

    static /* synthetic */ BicycleDetailItem a(BaseNetResult baseNetResult) {
        List<AbsBicycleItem> b2 = b(baseNetResult);
        if (b2 == null) {
            return null;
        }
        AbsBicycleItem absBicycleItem = b2.get(0);
        if (!(absBicycleItem instanceof BicycleDetailItem)) {
            return null;
        }
        return (BicycleDetailItem) absBicycleItem;
    }

    static /* synthetic */ String b(ehd ehd, BicycleDetailItem bicycleDetailItem) {
        if (bicycleDetailItem == null) {
            return null;
        }
        String a2 = eht.a(bicycleDetailItem.logoCode);
        if (a2 != null && ahd.b(a2)) {
            return "file:/".concat(String.valueOf(a2));
        }
        String str = bicycleDetailItem.iconCode;
        if (!TextUtils.isEmpty(str) && ehd.w != null) {
            IconConf.a data = ehd.w.getData();
            if (data != null) {
                List<b> list = data.b;
                if (list != null) {
                    for (b next : list) {
                        if (!TextUtils.isEmpty(next.b) && str.contains(next.b)) {
                            return next.a;
                        }
                    }
                }
            }
        }
        return str;
    }

    public static /* synthetic */ void U(ehd ehd) {
        if (((ShareBikePage) ehd.mPage).isAlive() && ((ShareBikePage) ehd.mPage).d != null) {
            ((ShareBikePage) ehd.mPage).d.notifyBicycleStatus(ehd.o());
        }
    }

    static /* synthetic */ void V(ehd ehd) {
        a((JSONObject) null, (String) "B002");
        if (ehd.j != null) {
            ehd.j.cancel();
            ehd.j = null;
        }
        if (ehd.n() != null && ehd.m != null) {
            avi avi = (avi) a.a.a(avi.class);
            if (avi != null) {
                ehd.j = avi.a().a(((ShareBikePage) ehd.mPage).getContext(), ehd.n(), ehd.m, new axa() {
                    public final void a(final IRouteResultData iRouteResultData, RouteType routeType) {
                        ahl.a(new a<Foot>() {
                            public final /* synthetic */ void onFinished(Object obj) {
                                Foot foot = (Foot) obj;
                                if (foot == null) {
                                    ehd.af(ehd.this);
                                    return;
                                }
                                ehd.this.B = true;
                                ehd.this.x = foot;
                                ehd.this.o = ehd.this.n().getPoint();
                                ehd.this.k();
                            }

                            @Nullable
                            public final /* synthetic */ Object doBackground() throws Exception {
                                if (((ShareBikePage) ehd.this.mPage).getContext() == null || iRouteResultData == null) {
                                    return null;
                                }
                                ehd.this.u = (RouteFootResultData) iRouteResultData;
                                return ehd.ae(ehd.this);
                            }
                        });
                    }

                    public final void a(RouteType routeType, int i, String str) {
                        ehd.af(ehd.this);
                    }

                    public final void a(Throwable th, boolean z) {
                        ehd.af(ehd.this);
                    }
                });
            }
        }
    }

    static /* synthetic */ void a(ehd ehd, GeoPoint geoPoint) {
        if (ehd.k != null) {
            ehd.k.cancel();
            ehd.k = null;
        }
        ehd.k = ReverseGeocodeManager.getReverseGeocodeResult(geoPoint, new ShareBikePresenter$18(ehd));
    }

    static /* synthetic */ Foot ae(ehd ehd) {
        OnFootNaviPath onFootNaviPath = ehd.u.getOnFootPlanResult().mOnFootNaviPath[0];
        if (onFootNaviPath == null) {
            return null;
        }
        Vector vector = new Vector();
        ArrayList<GeoPoint> arrayList = onFootNaviPath.mAllPoints;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        vector.addAll(arrayList);
        return new Foot(((ShareBikePage) ehd.mPage).getContext().getString(R.string.text_sb_foot_distance, new Object[]{cfe.a(onFootNaviPath.mPathlength)}), ((ShareBikePage) ehd.mPage).getContext().getString(R.string.text_sb_foot_time, new Object[]{axt.a(onFootNaviPath.mPathTime, false)}), vector);
    }

    static /* synthetic */ void af(ehd ehd) {
        ehd.B = false;
        if (ehd.a != null) {
            ehd.a.a(new String[]{((ShareBikePage) ehd.mPage).getContext().getString(R.string.text_loading_sb_route_error)}, ehd.n, false, false);
        }
    }

    static /* synthetic */ int ao(ehd ehd) {
        if (ehd.t == null) {
            return 1;
        }
        List<AbsBicycleItem> b2 = b(ehd.t);
        if (b2 == null || ehd.r == null || ehd.r.item == null) {
            return 1;
        }
        for (AbsBicycleItem next : b2) {
            if (next instanceof BicycleBasicItem) {
                BicycleBasicItem bicycleBasicItem = (BicycleBasicItem) next;
                StringBuilder sb = new StringBuilder("---------> BikeCheck BikeID: ");
                sb.append(ehd.r.item.getId());
                sb.append("_ ItemID: ");
                sb.append(bicycleBasicItem.getId());
                sb.append("_ BikeX: ");
                sb.append(ehd.r.item.getX());
                sb.append("_ ItemX: ");
                sb.append(bicycleBasicItem.getX());
                sb.append("_ BikeY: ");
                sb.append(ehd.r.item.getY());
                sb.append("_ ItemY: ");
                sb.append(bicycleBasicItem.getY());
                eao.a((String) ">>>>>ShareBike<<<<<", sb.toString());
                if (bicycleBasicItem.getId().equals(ehd.r.item.getId()) && Math.abs(bicycleBasicItem.getX() - ehd.r.item.getX()) < 1.0E-8d && Math.abs(bicycleBasicItem.getY() - ehd.r.item.getY()) < 1.0E-8d) {
                    eao.a((String) ">>>>>ShareBike<<<<<", (String) "---------> NearBikes Check Success");
                    return 2;
                }
            }
        }
        eao.a((String) ">>>>>ShareBike<<<<<", (String) "---------> NearBikes Check Fail");
        return 3;
    }

    static /* synthetic */ boolean ar(ehd ehd) {
        if (!(ehd.a == null || ehd.v == null)) {
            ego bicycle = ehd.v.getBicycle();
            if (bicycle != null) {
                egp egp = bicycle.a;
                if (egp != null) {
                    List<AbsBicycleItem> list = egp.c;
                    if (list == null || list.isEmpty()) {
                        return false;
                    }
                    List<BicycleBasicItem> list2 = ehd.a.d;
                    if (list2 == null || list2.isEmpty() || list.size() != list2.size()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static /* synthetic */ void at(ehd ehd) {
        if (ehd.r != null && ehd.r.item != null) {
            if (TextUtils.isEmpty(ehd.q)) {
                BicycleStatusCmd3Request bicycleStatusCmd3Request = new BicycleStatusCmd3Request();
                bicycleStatusCmd3Request.b = ehd.r.citycode;
                bicycleStatusCmd3Request.c = String.valueOf(ehd.r.item.getX());
                bicycleStatusCmd3Request.d = String.valueOf(ehd.r.item.getY());
                bicycleStatusCmd3Request.e = ehd.r.item.getSource();
                bicycleStatusCmd3Request.f = ehd.r.item.getId();
                bicycleStatusCmd3Request.g = ehd.r.scope;
                bicycleStatusCmd3Request.h = "2";
                a.a((BaseRequest) new BicycleDetailRequest(bicycleStatusCmd3Request, new BaseRequest.a() {
                    public final void a(BaseNetResult baseNetResult) {
                        if (ehd.this.mPage != null && ((ShareBikePage) ehd.this.mPage).isAlive() && ehd.this.e != 0) {
                            if (baseNetResult == null) {
                                if (ehd.this.A) {
                                    eao.a((String) ">>>>>ShareBike<<<<<", (String) "reqBicycle response---------> Result is empty");
                                    ToastHelper.showToast(((ShareBikePage) ehd.this.mPage).getContext().getString(R.string.text_toast_sb_error), SecExceptionCode.SEC_ERROR_SIMULATORDETECT);
                                    ehd.this.a(0);
                                    ehd.this.f();
                                    ehd.this.a(true);
                                }
                                return;
                            }
                            ehd.this.s = ehd.a(baseNetResult);
                            if (ehd.this.s == null) {
                                if (ehd.this.A) {
                                    eao.a((String) ">>>>>ShareBike<<<<<", (String) "reqBicycle response---------> BikeDetail is empty");
                                    ToastHelper.showToast(((ShareBikePage) ehd.this.mPage).getContext().getString(R.string.text_toast_sb_error), SecExceptionCode.SEC_ERROR_SIMULATORDETECT);
                                    ehd.this.a(0);
                                    ehd.this.f();
                                    ehd.this.a(true);
                                }
                            } else if ("1".equals(ehd.this.s.status)) {
                                ((ShareBikePage) ehd.this.mPage).d();
                            } else {
                                ehd.this.z = ehd.b(ehd.this, ehd.this.s);
                                ehd.U(ehd.this);
                                ehd.this.a(1);
                                if (ehd.this.A) {
                                    ehd.this.b(false);
                                    GeoPoint geoPoint = new GeoPoint(ehd.this.s.x, ehd.this.s.y);
                                    ehd.this.m = POIFactory.createPOI("", geoPoint);
                                    ehd.V(ehd.this);
                                    ehd.a(ehd.this, geoPoint);
                                    ehd.this.A = false;
                                }
                            }
                        }
                    }
                }));
                return;
            }
            BicycleStatusCmd3Request bicycleStatusCmd3Request2 = new BicycleStatusCmd3Request();
            bicycleStatusCmd3Request2.b = ehd.r.citycode;
            bicycleStatusCmd3Request2.c = String.valueOf(ehd.r.item.getX());
            bicycleStatusCmd3Request2.d = String.valueOf(ehd.r.item.getY());
            bicycleStatusCmd3Request2.e = ehd.r.item.getSource();
            bicycleStatusCmd3Request2.f = ehd.r.item.getId();
            bicycleStatusCmd3Request2.g = ehd.r.scope;
            bicycleStatusCmd3Request2.h = "2";
            bicycleStatusCmd3Request2.j = ehd.q;
            a.a((BaseRequest) new BicycleDetailRequest(bicycleStatusCmd3Request2, new BaseRequest.a() {
                public final void a(BaseNetResult baseNetResult) {
                    if (ehd.this.mPage != null && ((ShareBikePage) ehd.this.mPage).isAlive() && ehd.this.e != 0) {
                        if (baseNetResult == null) {
                            if (ehd.this.A) {
                                eao.a((String) ">>>>>ShareBike<<<<<", (String) "reqBicycle response---------> Result is empty");
                                ToastHelper.showToast(((ShareBikePage) ehd.this.mPage).getContext().getString(R.string.text_toast_sb_error), SecExceptionCode.SEC_ERROR_SIMULATORDETECT);
                                ehd.this.a(0);
                                ehd.this.f();
                                ehd.this.a(true);
                            }
                            return;
                        }
                        ehd.this.s = ehd.a(baseNetResult);
                        if (ehd.this.s == null) {
                            if (ehd.this.A) {
                                eao.a((String) ">>>>>ShareBike<<<<<", (String) "reqBicycle response---------> BikeDetail is empty");
                                ToastHelper.showToast(((ShareBikePage) ehd.this.mPage).getContext().getString(R.string.text_toast_sb_error), SecExceptionCode.SEC_ERROR_SIMULATORDETECT);
                                ehd.this.a(0);
                                ehd.this.f();
                                ehd.this.a(true);
                            }
                        } else if ("1".equals(ehd.this.s.status)) {
                            ((ShareBikePage) ehd.this.mPage).d();
                        } else {
                            ehd.this.z = ehd.b(ehd.this, ehd.this.s);
                            ehd.U(ehd.this);
                            ehd.this.a(1);
                            if (ehd.this.A) {
                                ehd.this.b(false);
                                GeoPoint geoPoint = new GeoPoint(ehd.this.s.x, ehd.this.s.y);
                                ehd.this.m = POIFactory.createPOI("", geoPoint);
                                ehd.V(ehd.this);
                                ehd.a(ehd.this, geoPoint);
                                ehd.this.A = false;
                            }
                            if (ehd.this.s.cpCode != null && ehd.this.s.cpCode.equals("1")) {
                                ehd.this.d();
                            }
                        }
                    }
                }
            }));
        }
    }

    static /* synthetic */ void a(ehd ehd, int i2, boolean z2) {
        switch (i2) {
            case 0:
                if (ehd.t == null) {
                    ehd.p();
                    ((ShareBikePage) ehd.mPage).g(2);
                    return;
                } else if (ehd.t.errorCode != 1) {
                    ehd.p();
                    ((ShareBikePage) ehd.mPage).g(3);
                    return;
                } else if (ehd.t instanceof BicycleStatus) {
                    ehd.v = (BicycleStatus) ehd.t;
                    if (ehd.v != null) {
                        ego bicycle = ehd.v.getBicycle();
                        if (bicycle != null) {
                            egp egp = bicycle.a;
                            if (egp != null) {
                                final String str = egp.a;
                                String b2 = ehs.b("share_bike_icon_version");
                                if (!TextUtils.isEmpty(str)) {
                                    boolean equals = TextUtils.equals(ehs.b("share_bike_icon_down_suc"), "true");
                                    boolean equals2 = TextUtils.equals(a.a().a, ehs.b("share_bike_icon_down_apk_version"));
                                    if (!eht.a() || !equals || !TextUtils.equals(str, b2) || !equals2) {
                                        ShareBikeIConconfRequest shareBikeIConconfRequest = new ShareBikeIConconfRequest();
                                        shareBikeIConconfRequest.b = String.valueOf(System.currentTimeMillis());
                                        a.a((BaseRequest) new IconConfRequest(shareBikeIConconfRequest, new BaseRequest.a() {
                                            public final void a(BaseNetResult baseNetResult) {
                                                IconConf iconConf = (IconConf) baseNetResult;
                                                if (iconConf != null) {
                                                    ehd.this.w = iconConf;
                                                    ehd.this.a;
                                                    ehd.a(ehd.this.w);
                                                    ehs.a((String) "share_bike_icon_version", str);
                                                    ehs.a((String) "share_bike_icon_down_apk_version", a.a().a);
                                                }
                                            }
                                        }));
                                    }
                                }
                            }
                        }
                    }
                    ego bicycle2 = ehd.v.getBicycle();
                    if (bicycle2 == null) {
                        ehd.p();
                        ((ShareBikePage) ehd.mPage).g(2);
                        return;
                    } else if (bicycle2.b != 0) {
                        ehd.p();
                        ((ShareBikePage) ehd.mPage).g(3);
                        return;
                    } else if (bicycle2.a == null || bicycle2.a.c == null || bicycle2.a.c.size() == 0) {
                        ehd.p();
                        ((ShareBikePage) ehd.mPage).g(4);
                        return;
                    } else {
                        ehd.a(ehd.v, true, true, z2);
                        return;
                    }
                }
                break;
            case 1:
                ahl.a(new a<Integer>() {
                    public final /* synthetic */ void onFinished(Object obj) {
                        switch (((Integer) obj).intValue()) {
                            case 1:
                                return;
                            case 2:
                                if (ehd.this.t != null) {
                                    ehd.this.v = (BicycleStatus) ehd.this.t;
                                    if (ehd.ar(ehd.this)) {
                                        ehd.this.a(ehd.this.v, false, false, false);
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                ToastHelper.showToast(((ShareBikePage) ehd.this.mPage).getString(R.string.text_toast_rescan));
                                ehd.this.a(0);
                                ehd.this.a(true);
                                return;
                        }
                    }

                    public final /* synthetic */ Object doBackground() throws Exception {
                        return Integer.valueOf(ehd.ao(ehd.this));
                    }
                });
                break;
        }
    }

    public static /* synthetic */ void au(ehd ehd) {
        ((ShareBikePage) ehd.mPage).c(8);
        ((ShareBikePage) ehd.mPage).e(8);
        ((ShareBikePage) ehd.mPage).i = true;
        ((ShareBikePage) ehd.mPage).f.dismissPoiNameView();
    }
}
