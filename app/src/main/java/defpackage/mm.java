package defpackage;

import android.app.Activity;
import android.hardware.SensorEvent;
import android.location.Location;
import android.os.SystemClock;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.ajx.inter.INaviTip;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.AEUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.guide.GuideService;
import com.autonavi.jni.ae.guide.model.GuideConfig;
import com.autonavi.jni.ae.pos.LocManager;
import com.autonavi.jni.ae.pos.LocNGMListener;
import com.autonavi.jni.ae.route.RouteService;
import com.autonavi.jni.ae.route.model.RouteConfig;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.iflytek.tts.TtsService.TtsManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* renamed from: mm reason: default package */
/* compiled from: DriveNaviManagerImpl */
public class mm implements dfm {
    public final void a() {
        NaviManager a = NaviManager.a();
        StringBuilder sb = new StringBuilder("initTBT");
        sb.append(a.toString());
        NaviManager.a(sb.toString());
        if (a.b == null) {
            GuideConfig guideConfig = new GuideConfig();
            guideConfig.naviPath = "";
            StringBuilder sb2 = new StringBuilder();
            sb2.append(FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication()));
            sb2.append("/autonavi//data");
            guideConfig.cachePath = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication()));
            sb3.append("/autonavi//res");
            guideConfig.resPath = sb3.toString();
            guideConfig.userCode = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.TBT_ACCOUNT);
            guideConfig.passWord = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.TBT_PASSWORD);
            guideConfig.userBatch = "0";
            guideConfig.UUID = NetworkParam.getDiu();
            a.b = new GuideService(guideConfig, AMapAppGlobal.getApplication());
            a.d.incrementAndGet();
            a.d();
            a.a(44, NetworkParam.getCifa());
            a.a(43, "1");
            a.a(22, String.valueOf(DriveSpUtil.getInt(AMapAppGlobal.getApplication().getApplicationContext(), DriveSpUtil.BROADCAST_MODE, 2)));
            a.b.setSoundPlayObserver(a);
            a.b.setOnVoiceConfigVersionCallback(a.l);
            a.a(52, DriveSpUtil.getBool(AMapAppGlobal.getApplication().getApplicationContext(), DriveSpUtil.DOWNLOAD_INTERSECTION_OF_REAL_MAP, true) ? "1" : "0");
            a.a(53, DriveUtil.getTranslatedNetworkType());
            a.a(60, rq.b() ? "1" : "0");
            a.a(1, "1");
            if (!NaviManager.n && !a.b()) {
                throw new AssertionError();
            }
        }
        if (a.a == null) {
            RouteConfig routeConfig = new RouteConfig();
            routeConfig.deviceID = NetworkParam.getDiu();
            a.a = new RouteService(routeConfig, AMapAppGlobal.getApplication());
            a.c.incrementAndGet();
            a.a.setRouteObserver(a);
            a.a.control(1, "");
            a.a.control(2, "0");
            a.c(12, String.valueOf(DriveSpUtil.getInt(AMapAppGlobal.getApplication().getApplicationContext(), DriveSpUtil.BROADCAST_MODE, 2)));
            a.c(16, rq.b() ? "1" : "0");
        }
        rp.a();
        anf.a((LocNGMListener) a);
        tq.a(AutoConstants.AUTO_FILE_3DCROSS, "init3dSupport", "");
        MapSharePreference mapSharePreference = new MapSharePreference((String) "SharedPreferences");
        if (mapSharePreference.contains("key_navi_3d_support")) {
            boolean booleanValue = mapSharePreference.getBooleanValue("key_navi_3d_support", true);
            tq.b("NaviMonitor", AutoConstants.AUTO_FILE_3DCROSS, "init3dSupport = ".concat(String.valueOf(booleanValue ? 1 : 0)));
            if (a.c()) {
                a.a.control(11, String.valueOf(booleanValue));
            }
            if (a.b()) {
                a.b.control(39, String.valueOf(booleanValue));
            }
        }
        if (rq.a()) {
            LocManager.setOverheadSwitch(true);
        }
        StringBuilder sb4 = new StringBuilder("initTBT#end");
        sb4.append(a.toString());
        NaviManager.a(sb4.toString());
        if (a.c()) {
            a.b();
        }
    }

    public final boolean b() {
        return NaviManager.a().f();
    }

    public final void a(boolean z) {
        NaviManager.a().a(z);
    }

    public final boolean c() {
        return NaviManager.a().b() && NaviManager.a().c();
    }

    public final void d() {
        NaviManager a = NaviManager.a();
        StringBuilder sb = new StringBuilder("release");
        sb.append(a.toString());
        NaviManager.a(sb.toString());
        if (a.c()) {
            a.a.abortRoutePlan();
            a.a.setRouteObserver(null);
            a.a.registerHttpProcesser(null);
            a.c.decrementAndGet();
        }
        if (a.b()) {
            a.b.stopNavi();
            a.b.registerHttpProcesser(null);
            a.b.setSoundPlayObserver(null);
            a.b.setElecEyeObserver(null);
            a.d.decrementAndGet();
        }
        tn.a().c.clear();
        a.h = null;
    }

    public final void e() {
        NaviManager a = NaviManager.a();
        StringBuilder sb = new StringBuilder("destory");
        sb.append(a.toString());
        NaviManager.a(sb.toString());
        if (a.a != null) {
            a.a.destroy();
            a.a = null;
        }
        if (a.b != null) {
            a.b.destroy();
            a.b = null;
        }
    }

    public final void a(CalcRouteScene calcRouteScene) {
        tn.a().a(calcRouteScene);
    }

    public final void a(SensorEvent sensorEvent) {
        if (sensorEvent != null && sensorEvent.sensor != null && sensorEvent.sensor.getType() == 6) {
            LocManager.setPressure((double) (sensorEvent.values[0] * 100.0f), SystemClock.elapsedRealtime());
        }
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            nm.a(AMapAppGlobal.getTopActivity(), str, null);
        } else {
            AMapLog.e("DriveNaviManagerImpl", "跳转导航参数为null");
        }
    }

    public final void b(boolean z) {
        NaviManager a = NaviManager.a();
        tq.b("NaviMonitor", AutoConstants.AUTO_FILE_3DCROSS, "init3dSupport = ".concat(String.valueOf(z ? 1 : 0)));
        if (a.c()) {
            a.a.control(11, String.valueOf(z));
        }
        if (a.b()) {
            a.b.control(39, String.valueOf(z));
        }
    }

    public final void b(String str) {
        NaviManager.a().a(45, str);
        NaviManager.a().c(13, str);
        if (bno.a) {
            AMapLog.i(mm.class.getSimpleName(), "setVoicePackage srcCode:".concat(String.valueOf(str)));
            ku.a().c("PlaySoundUtils", "setVoicePackage srcCode:".concat(String.valueOf(str)));
        }
    }

    public final String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.equals("GuideService")) {
            NaviManager.a();
            return NaviManager.g();
        } else if (str.equals("RouteService")) {
            NaviManager.a();
            return NaviManager.h();
        } else if (!str.equals("RouteProtocol")) {
            return null;
        } else {
            NaviManager.a();
            return NaviManager.i();
        }
    }

    public final void a(POI poi, POI poi2, String str) {
        int d = dhw.d(str);
        int c = dhw.c(str);
        Activity topActivity = AMapAppGlobal.getTopActivity();
        PageBundle pageBundle = new PageBundle();
        nm.a(pageBundle, d, c, poi, null, poi2, false);
        nm.a(topActivity, pageBundle, (List<POI>) null, poi2);
    }

    public final void g() {
        DriveUtil.delNaviHistoryList();
    }

    public final void a(final Activity activity) {
        ahm.a(new Runnable() {
            public final void run() {
                final mx mxVar = new mx();
                if (AEUtil.IS_DEBUG) {
                    ku a2 = ku.a();
                    StringBuilder sb = new StringBuilder("[DriveManagerImpl]    shouldShowNodeNavigationDlg = ");
                    sb.append(mx.a(activity.getApplicationContext()));
                    a2.c("bug_12148196", sb.toString());
                }
                if (mx.a(activity.getApplicationContext())) {
                    aho.a(new Runnable() {
                        public final void run() {
                            mx mxVar = mxVar;
                            Activity activity = activity;
                            TtsManager.getInstance().InitializeTTs();
                            POI destinationAtException = DriveSpUtil.getDestinationAtException(activity.getApplicationContext());
                            if (destinationAtException != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(activity.getString(R.string.autonavi_navigation_helper_destination));
                                sb.append(destinationAtException.getName());
                                String sb2 = sb.toString();
                                a aVar = new a(AMapAppGlobal.getApplication());
                                aVar.a((CharSequence) AMapPageUtil.getAppContext().getString(R.string.continue_navi_msg)).b((CharSequence) sb2).a((CharSequence) AMapPageUtil.getAppContext().getString(R.string.continue_str), (a) new a(destinationAtException) {
                                    final /* synthetic */ POI a;

                                    {
                                        this.a = r2;
                                    }

                                    public final void onClick(AlertView alertView, int i) {
                                        bid pageContext = AMapPageUtil.getPageContext();
                                        if (pageContext != null) {
                                            pageContext.dismissViewLayer(alertView);
                                        }
                                        mx.a(this.a);
                                    }
                                }).b((CharSequence) AMapPageUtil.getAppContext().getString(R.string.cancle), (a) new a() {
                                    public final void onClick(AlertView alertView, int i) {
                                        bid pageContext = AMapPageUtil.getPageContext();
                                        if (pageContext != null) {
                                            pageContext.dismissViewLayer(alertView);
                                        }
                                        mx.a();
                                    }
                                });
                                aVar.a(true);
                                AlertView a2 = aVar.a();
                                bid pageContext = AMapPageUtil.getPageContext();
                                if (pageContext != null) {
                                    pageContext.showViewLayer(a2);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public final dfl[] f() {
        try {
            List<Location> latestGpsLocations = LocationInstrument.getInstance().getLatestGpsLocations();
            if (latestGpsLocations != null && latestGpsLocations.size() > 0) {
                dfl[] dflArr = new dfl[latestGpsLocations.size()];
                for (int i = 0; i < latestGpsLocations.size(); i++) {
                    Location location = latestGpsLocations.get(i);
                    dflArr[i] = new dfl();
                    dflArr[i].b = location.getLatitude();
                    dflArr[i].a = location.getLongitude();
                    dflArr[i].c = (double) location.getSpeed();
                    dflArr[i].d = location.getBearing();
                    Calendar instance = Calendar.getInstance();
                    instance.setTimeInMillis(location.getTime());
                    dflArr[i].e = instance.get(1);
                    dflArr[i].f = instance.get(2) + 1;
                    dflArr[i].g = instance.get(5);
                    dflArr[i].h = instance.get(11);
                    dflArr[i].i = instance.get(12);
                    dflArr[i].j = instance.get(13);
                }
                return dflArr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public final void a(POI poi) {
        POI createPOI = POIFactory.createPOI();
        if (LocationInstrument.getInstance().getLatestPosition(5) == null) {
            createPOI = null;
        } else {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            createPOI.setName("我的位置");
            createPOI.setPoint(latestPosition);
        }
        nm.a(AMapAppGlobal.getTopActivity(), createPOI, (ArrayList<POI>) null, poi, (String) "");
    }

    public final void a(POI poi, String str, boolean z) {
        POI poi2;
        POI createPOI = POIFactory.createPOI();
        if (LocationInstrument.getInstance().getLatestPosition(5) == null) {
            poi2 = null;
        } else {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            createPOI.setName("我的位置");
            createPOI.setPoint(latestPosition);
            poi2 = createPOI;
        }
        Activity topActivity = AMapAppGlobal.getTopActivity();
        PageBundle pageBundle = new PageBundle();
        nm.a(pageBundle, 0, 0, poi2, null, poi, true);
        pageBundle.putString("navi_type", str);
        pageBundle.putBoolean("need_backprev", z);
        nm.a(topActivity, pageBundle, (List<POI>) null, poi);
    }

    public final void a(int i, String str) {
        if (NaviManager.a().f()) {
            AbstractBaseMapPage abstractBaseMapPage = (AbstractBaseMapPage) AMapPageUtil.getPageContext();
            if (abstractBaseMapPage == null || !(abstractBaseMapPage instanceof INaviTip)) {
                if (NaviManager.a().i) {
                    ToastHelper.showToast(str);
                }
                return;
            }
            ((INaviTip) abstractBaseMapPage).showNaviTip(i, str);
        }
    }
}
