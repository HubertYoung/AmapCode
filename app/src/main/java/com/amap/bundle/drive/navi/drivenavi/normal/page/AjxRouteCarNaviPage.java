package com.amap.bundle.drive.navi.drivenavi.normal.page;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.amap.bundle.drive.ajx.inter.CompleteReportInfoCallBack;
import com.amap.bundle.drive.ajx.inter.IFullScreenChangeCallback;
import com.amap.bundle.drive.ajx.inter.INaviTip;
import com.amap.bundle.drive.ajx.inter.INaviUiActionListener;
import com.amap.bundle.drive.ajx.inter.IRealNaviEventCallback;
import com.amap.bundle.drive.ajx.inter.IRealNaviPageEventCallback;
import com.amap.bundle.drive.ajx.inter.IReportEvent;
import com.amap.bundle.drive.ajx.inter.IWritingPermissionStateCallback;
import com.amap.bundle.drive.ajx.inter.ShareStatusListener;
import com.amap.bundle.drive.ajx.module.ModuleDriveNavi;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.common.basepage.AjxRouteCarNaviBasePage;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager.NaviType;
import com.amap.bundle.drive.naviend.drive.page.AjxRouteCarNaviEndPage;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.schoolbus.notifcation.ISchoolbusStatusMangger;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.amap.bundle.voiceservice.dispatch.IVoiceDriveDispatcher;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.ae.pos.LocListener;
import com.autonavi.map.core.LocationMode.LocationNavi;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.ModulePhoto;
import com.autonavi.minimap.ajx3.modules.os.GravitySensor;
import com.autonavi.minimap.ajx3.modules.os.GravitySensor.GravityListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.ChildViewSHowListener;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager;
import com.autonavi.minimap.basemap.errorback.navi.ErrorType;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.vcs.NativeVcsManager;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.uc.webview.export.internal.SDKFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@SuppressFBWarnings({"AMAP_OPT_X", "BIT_SIGNED_CHECK", "DB_DUPLICATE_BRANCHES", "BC_BAD_CAST_TO_CONCRETE_COLLECTION"})
@LocationPreference(availableOnBackground = true)
public class AjxRouteCarNaviPage extends AjxRouteCarNaviBasePage implements INaviTip, IVoiceCmdResponder, LocListener, LocationNavi, launchModeSingleTask, f {
    protected Handler a = new Handler();
    protected String b;
    private IVoicePackageManager c;
    private long d = 0;
    /* access modifiers changed from: private */
    public IReportErrorManager e;
    /* access modifiers changed from: private */
    public oc f;
    private sp g;
    /* access modifiers changed from: private */
    public LinkedHashSet<String> h = new LinkedHashSet<>();
    private IRealNaviEventCallback i;
    /* access modifiers changed from: private */
    public String j;
    private NavigationDataResult k;
    private int l = -1;
    private GravitySensor m;
    private boolean n = false;
    private BroadcastReceiver o = null;
    private boolean p = false;
    private final a q = new a() {
        public final void a() {
            czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
            if (czk != null && AjxRouteCarNaviPage.this.getContext() != null) {
                czk.a(NotificationChannelIds.g, R.drawable.ic_launcher, AjxRouteCarNaviPage.this.getResources().getString(R.string.autonavi_app_name_in_route), AjxRouteCarNaviPage.this.getResources().getString(R.string.autonavi_navi_ing));
            }
        }
    };
    private IReportEvent r = new IReportEvent() {
        public final void onReportEvent(int i) {
            switch (i) {
                case 1001:
                    return;
                case 1002:
                    AjxRouteCarNaviPage.this.a.post(new Runnable() {
                        public final void run() {
                            if (AjxRouteCarNaviPage.this.f == null) {
                                AjxRouteCarNaviPage ajxRouteCarNaviPage = AjxRouteCarNaviPage.this;
                                a aVar = new a();
                                aVar.f = AjxRouteCarNaviPage.this.getActivity().getWindow().findViewById(16908290);
                                aVar.b = AjxRouteCarNaviPage.this.b();
                                aVar.a = AjxRouteCarNaviPage.this.getMapManager();
                                aVar.c = AjxRouteCarNaviPage.this.e;
                                aVar.d = AjxRouteCarNaviPage.this.j;
                                aVar.e = AjxRouteCarNaviPage.this.h;
                                aVar.h = DriveUtil.getLastRoutingChoice();
                                POI originalFromPoi = AjxRouteCarNaviPage.this.c().getOriginalFromPoi();
                                oc ocVar = new oc(aVar.f, aVar.a);
                                List<so> list = aVar.b.h;
                                ArrayList arrayList = new ArrayList();
                                if (list != null) {
                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i) != null) {
                                            arrayList.add(list.get(i).a);
                                        }
                                    }
                                }
                                ocVar.g = arrayList;
                                ocVar.h = originalFromPoi;
                                ocVar.i = aVar.b.i;
                                ocVar.e = aVar.c;
                                ocVar.j = aVar.h;
                                ocVar.f = aVar.d;
                                ocVar.k = aVar.g;
                                ocVar.l = aVar.i;
                                ocVar.m = aVar.e;
                                ajxRouteCarNaviPage.f = ocVar;
                                StringBuilder sb = new StringBuilder("setCurrentNaviId---mCurrentNaviId=");
                                sb.append(AjxRouteCarNaviPage.this.j);
                                AMapLog.d("sudaxia", sb.toString());
                            }
                            AjxRouteCarNaviPage.this.f.l = ErrorType.OTHER;
                            oc a2 = AjxRouteCarNaviPage.this.f;
                            tl.a();
                            a2.n = tl.b();
                            AjxRouteCarNaviPage.this.f.a(tt.a(AjxRouteCarNaviPage.this.getContext()), tt.b(AjxRouteCarNaviPage.this.getContext()));
                        }
                    });
                    Application application = AMapAppGlobal.getApplication();
                    String string = AjxRouteCarNaviPage.this.getString(R.string.navigation_voice_report_toast);
                    if (application.getResources().getConfiguration().orientation == 2) {
                        ToastHelper.showLongToast(string, 81, application.getResources().getDimensionPixelSize(R.dimen.exit_app_toast_offset), application.getResources().getDimensionPixelSize(R.dimen.navi_toast_y_offset), 16);
                    } else {
                        ToastHelper.showLongToast(string, 81, 0, application.getResources().getDimensionPixelSize(R.dimen.navi_toast_y_offset), 16);
                    }
                    if (!PlaySoundUtils.getInstance().isPlaying()) {
                        PlaySoundUtils.getInstance().playSound(AjxRouteCarNaviPage.this.getResources().getString(R.string.navigation_voice_report_content));
                        break;
                    }
                    break;
            }
        }

        public final int getErrorReportNum() {
            return AjxRouteCarNaviPage.this.e != null && AjxRouteCarNaviPage.this.j != null && AjxRouteCarNaviPage.this.j.equals(AjxRouteCarNaviPage.this.e.getNaviErrorReportFlag()) ? 1 : 0;
        }
    };
    private INaviUiActionListener s = new INaviUiActionListener() {
        public final void onFinishNaviPage() {
            AjxRouteCarNaviPage.this.handleExit();
        }

        public final boolean bottomMenuIsShow() {
            return AjxRouteCarNaviPage.a(AjxRouteCarNaviPage.this.getActivity());
        }
    };
    private aig t = new aig() {
        public final void a(int i, String str) {
            if (AjxRouteCarNaviPage.this.mIVoiceService != null) {
                if (AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), AjxRouteCarNaviPage.this.a() ? "setRouteParams" : "setTruckRouteParams", i, new Pair<>("method", rc.b(str)))) {
                    return;
                }
            }
            sa.a(i, (int) SDKFactory.getCoreType);
        }

        public final void a(int i, int i2) {
            if (AjxRouteCarNaviPage.this.mIVoiceService != null) {
                if (AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "searchAlongInNavi", i, new Pair<>("type", Integer.valueOf(i2)))) {
                    return;
                }
            }
            sa.a(i, (int) SDKFactory.getCoreType);
        }

        public final void a(int i) {
            if (AjxRouteCarNaviPage.this.mIVoiceService != null) {
                if (AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "operateMap", i, new Pair<>("type", Integer.valueOf(0)))) {
                    return;
                }
            }
            sa.a(i, (int) SDKFactory.getCoreType);
        }

        public final void b(int i) {
            if (AjxRouteCarNaviPage.this.mIVoiceService != null) {
                if (AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "operateMap", i, new Pair<>("type", Integer.valueOf(1)))) {
                    return;
                }
            }
            sa.a(i, (int) SDKFactory.getCoreType);
        }

        public final void a(int i, boolean z) {
            if (AjxRouteCarNaviPage.this.mIVoiceService != null) {
                if (AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "previewMap", i, new Pair<>("open", Integer.valueOf(z ? 1 : 0)))) {
                    return;
                }
            }
            sa.a(i, (int) SDKFactory.getCoreType);
        }

        public final void b(int i, boolean z) {
            if (AjxRouteCarNaviPage.this.mIVoiceService != null) {
                if (AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "setTraffic", i, new Pair<>("open", Integer.valueOf(z ? 1 : 0)))) {
                    return;
                }
            }
            sa.a(i, (int) SDKFactory.getCoreType);
        }

        public final void c(int i) {
            if (AjxRouteCarNaviPage.this.mIVoiceService == null || !AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "refreshRouteInNavi", i)) {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }

        public final void d(int i) {
            if (AjxRouteCarNaviPage.this.mIVoiceService == null || !AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "requestGuideInfo", i)) {
                sa.a(i, 9004);
            }
        }

        public final void b(int i, int i2) {
            if (AjxRouteCarNaviPage.this.mIVoiceService != null) {
                if (AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "requestNaviInfo", i, new Pair<>("type", Integer.valueOf(i2)))) {
                    return;
                }
            }
            sa.a(i, 9004);
        }

        public final void e(int i) {
            if (AjxRouteCarNaviPage.this.mIVoiceService == null || !AjxRouteCarNaviPage.this.mIVoiceService.a(AjxRouteCarNaviPage.i(AjxRouteCarNaviPage.this), (String) "exitNavi", i)) {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }

        public final void b(int i, String str) {
            sa.a(i, str);
        }
    };
    private IRealNaviPageEventCallback u = new IRealNaviPageEventCallback() {
        public final void startDriveEndPage(String str) {
            ro.b();
            if (AjxRouteCarNaviPage.this.mNeedBackprev) {
                AjxRouteCarNaviPage ajxRouteCarNaviPage = AjxRouteCarNaviPage.this;
                ajxRouteCarNaviPage.handleExit();
                ajxRouteCarNaviPage.finish();
                return;
            }
            AjxRouteCarNaviPage.this.mWillEnterNaviEndPage = true;
            AjxRouteCarNaviPage.a(AjxRouteCarNaviPage.this, AjxRouteCarNaviPage.b(str));
        }

        public final void onOpenVoiceSquare() {
            AjxRouteCarNaviPage.E(AjxRouteCarNaviPage.this);
        }
    };
    private CompleteReportInfoCallBack v = new CompleteReportInfoCallBack() {
        public final void completeReportInfo() {
            AjxRouteCarNaviPage.this.a.post(new Runnable() {
                public final void run() {
                    Intent intent = new Intent();
                    intent.setAction("amap.basemap.action.feedback_report_error_list_page");
                    intent.setPackage("com.autonavi.minimap");
                    PageBundle pageBundle = new PageBundle(intent);
                    pageBundle.putString("ReportErrorListFragment.naviId", AjxRouteCarNaviPage.this.j);
                    pageBundle.putString("navi_type", AjxRouteCarNaviPage.this.b);
                    AjxRouteCarNaviPage.this.handleStartNewPage();
                    AjxRouteCarNaviPage.this.startPageForResult((String) "amap.basemap.action.feedback_report_error_list_page", pageBundle, 4096);
                }
            });
        }
    };
    private ShareStatusListener w = new ShareStatusListener() {
        public final ShareParam getShareDataByType(int i) {
            return null;
        }

        public final void onFinish(int i, final int i2) {
            AjxRouteCarNaviPage.this.a.post(new Runnable() {
                public final void run() {
                    if (i2 != 2) {
                        ToastHelper.cancel();
                    }
                }
            });
        }
    };
    private eqe x = new eqe() {
        public final void a(String str) {
            AMapLog.d("AjxRouteCarNaviPage", "onVUIEventCallback");
            if (AjxRouteCarNaviPage.this.mModuleDriveNavi != null) {
                AjxRouteCarNaviPage.this.mModuleDriveNavi.onVUIEventCallback(str);
            }
        }
    };
    private IWritingPermissionStateCallback y = new IWritingPermissionStateCallback() {
        public final void checkWritingPermission() {
            AjxRouteCarNaviPage.this.adjustLightness();
        }
    };
    private IFullScreenChangeCallback z = new IFullScreenChangeCallback() {
        public final void onChangeFullScreen(boolean z) {
            AjxRouteCarNaviPage.this.mShowCustomStatusBar = z;
        }
    };

    public View getMapSuspendView() {
        return null;
    }

    public void onCreate(Context context) {
        this.p = ro.h();
        b(true);
        d.a.b((String) "enter");
        super.onCreate(context);
        if (ro.g()) {
            this.m = new GravitySensor(getContext());
            this.m.setGravityListener(new GravityListener() {
                public final void onOrientationChanged(String str) {
                    if (str.equals(GravitySensor.PORTRAIT_PRIMARY)) {
                        AjxRouteCarNaviPage.this.requestScreenOrientation(1);
                    } else if (str.equals(GravitySensor.LANDSCAPE_PRIMARY)) {
                        AjxRouteCarNaviPage.this.requestScreenOrientation(8);
                    } else {
                        if (str.equals(GravitySensor.LANDSCAPE_SECONDARY)) {
                            AjxRouteCarNaviPage.this.requestScreenOrientation(0);
                        }
                    }
                }
            });
        }
        if (this.o == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ModulePhoto.ACTION_OPEN_CAMERA);
            this.o = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (intent != null && ModulePhoto.ACTION_OPEN_CAMERA.equals(intent.getAction())) {
                        AjxRouteCarNaviPage.this.handleStartNewPage();
                    }
                }
            };
            AMapAppGlobal.getApplication().registerReceiver(this.o, intentFilter);
        }
    }

    public Ajx3PagePresenter createPresenter() {
        return new nw(this);
    }

    public void pageCreated() {
        super.pageCreated();
        tj.a((String) "DriveGuide-------Native: AjxRouteCarNaviPage: pageCreated");
        NaviManager naviManager = this.mNaviMgr;
        if (naviManager.f == null) {
            naviManager.f = new CopyOnWriteArrayList();
        }
        if (!naviManager.f.contains(this)) {
            naviManager.f.add(this);
        }
        this.c = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        this.e = (IReportErrorManager) ank.a(IReportErrorManager.class);
        if (TextUtils.isEmpty(this.j)) {
            this.j = String.valueOf(System.currentTimeMillis());
        }
        c().setNaviId(this.j);
        c().setRouteNaviIdAllContainer(this.h);
        c().setMethod(DriveUtil.getLastRoutingChoice());
        ku.a().c("NaviMonitor", "[AjxRouteCarNaviPage] initData()");
        cdz d2 = getSuspendManager().d();
        if (d2 != null) {
            d2.b(1);
            d2.a(false);
            d2.f();
        }
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            czk czk = (czk) iMainMapService.a(czk.class);
            if (czk != null) {
                if (czk.b()) {
                    czk.a(NotificationChannelIds.g, R.drawable.ic_launcher, getResources().getString(R.string.autonavi_app_name_in_route), getResources().getString(R.string.autonavi_navi_ing));
                } else {
                    czk.a(this.q);
                }
            }
        }
        IVoiceDriveDispatcher iVoiceDriveDispatcher = (IVoiceDriveDispatcher) ank.a(IVoiceDriveDispatcher.class);
        if (iVoiceDriveDispatcher != null) {
            iVoiceDriveDispatcher.setNaviApiControlListener(this.t);
        }
        NativeVcsManager.getInstance().setVUIEventCallback(this.x);
        ews.a().b();
        LocationInstrument.getInstance().startNavi();
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            IAgroupOverlayService b2 = cuh.b();
            b2.c(false);
            b2.d(false);
            b2.i();
        }
        if (!to.a(this)) {
            to.a(this, true);
        }
    }

    public void resume() {
        super.resume();
        a(ags.b(getActivity()));
        if (this.l == -1) {
            adjustLightness();
        }
        this.mHasLaunchedNewPage = false;
        String str = "";
        afz afz = (afz) a.a.a(afz.class);
        if (afz != null) {
            str = afz.a();
        }
        AMapLog.i("AjxRouteCarNaviPage", "daihq  refreshSelectVoicePackage     voiceName:".concat(String.valueOf(str)));
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.onVoiceSquareBack(str);
        }
        LocationInstrument.getInstance().subscribe(getContext(), LOCATION_SCENE.TYPE_DRIVE_NAVIGATION);
        if (!TextUtils.isEmpty(this.j)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", 1);
                jSONObject.put(TrafficUtil.KEYWORD, this.j);
            } catch (JSONException e2) {
                kf.a((Throwable) e2);
            }
            LogManager.actionLogV2("P00025", "B128", jSONObject);
        }
        if (euk.a()) {
            euk.a(AMapAppGlobal.getTopActivity(), 0);
        }
    }

    public void pause() {
        super.pause();
        recoverOriginalLightness();
        LocationInstrument.getInstance().unsubscribe(getContext());
        if (!TextUtils.isEmpty(this.j)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", 0);
                jSONObject.put(TrafficUtil.KEYWORD, this.j);
            } catch (JSONException e2) {
                kf.a((Throwable) e2);
            }
            LogManager.actionLogV2("P00025", "B128", jSONObject);
        }
    }

    public void stop() {
        super.stop();
        if (!this.mIsExit && !this.mHasLaunchedNewPage && !this.n && !PlaySoundUtils.getInstance().isPhoneCalling() && !PlaySoundUtils.getInstance().isSilent()) {
            String string = AMapAppGlobal.getApplication().getString(R.string.route_navi_continue_navi_text);
            if (!PlaySoundUtils.getInstance().voiceInSoundStrQueue(string)) {
                PlaySoundUtils.getInstance().playSound(string);
                this.n = true;
            }
        }
    }

    public void destroy() {
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.fakeNetworkLocation(0);
        }
        b(false);
        ku.a().c("NaviMonitor", "[AjxRouteCarNaviPage] destroy()");
        super.destroy();
        IVoiceDriveDispatcher iVoiceDriveDispatcher = (IVoiceDriveDispatcher) ank.a(IVoiceDriveDispatcher.class);
        if (iVoiceDriveDispatcher != null) {
            iVoiceDriveDispatcher.setNaviApiControlListener(null);
        }
        if (this.mNaviMgr != null) {
            NaviManager naviManager = this.mNaviMgr;
            if (naviManager.f != null && naviManager.f.contains(this)) {
                naviManager.f.remove(this);
            }
        }
        if (ahp.a(getContext())) {
            ro.b();
        }
        if (this.f != null) {
            oc ocVar = this.f;
            ocVar.d.a.clear();
            ocVar.d.removeMessages(1001);
            this.f = null;
        }
        if (this.e != null) {
            this.e.setNaviErrorReportFlag(null);
        }
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a((cuj) null);
            if (cuh != null) {
                IAgroupOverlayService b2 = cuh.b();
                b2.c(true);
                b2.d(true);
            }
        }
        czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
        if (czk != null) {
            ISchoolbusStatusMangger iSchoolbusStatusMangger = (ISchoolbusStatusMangger) ank.a(ISchoolbusStatusMangger.class);
            if (iSchoolbusStatusMangger == null) {
                czk.a();
            } else if (iSchoolbusStatusMangger.isTravelling()) {
                czk.a(NotificationChannelIds.p, R.drawable.ic_launcher, "高德地图", "安心校车正在使用位置服务");
            } else {
                czk.a();
            }
        }
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(null);
        rw.a(getContext()).a();
        ews.a().c();
        LocationInstrument.getInstance().stopNavi();
        d.a.b((String) "exit");
        if (this.mModuleCommonBusiness != null) {
            this.mModuleCommonBusiness.setINaviUiActionListener(null);
        }
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.setNaviEventListener(null);
            this.mModuleDriveNavi.setNaviPageEventListener(null);
            this.mModuleDriveNavi.setReportEventListener(null);
            this.mModuleDriveNavi.setShareStatusListener(null);
            this.mModuleDriveNavi.setWritingPermissionStateCallback(null);
        }
        if (this.mModuleDriveCommonBusiness != null) {
            this.mModuleDriveCommonBusiness.setCompleteReportInfoCallBack(null);
        }
        if (this.m != null) {
            this.m.destroy();
        }
        if (this.o != null) {
            AMapAppGlobal.getApplication().unregisterReceiver(this.o);
            this.o = null;
        }
    }

    public void loadJs() {
        String str;
        tj.a((String) "DriveGuide-------Native: AjxRouteCarNaviPage: loadJs: start");
        if (getArguments() != null) {
            String string = getArguments().getString("jsData");
            try {
                JSONObject jSONObject = new JSONObject();
                if (!TextUtils.isEmpty(string)) {
                    jSONObject = new JSONObject(string);
                }
                sp a2 = rk.a(getContext(), getArguments());
                if (!jSONObject.has("result")) {
                    jSONObject.put("endPoi", bnx.b(a2.i));
                    if (a2.g != null && !a2.g.isEmpty()) {
                        JSONArray jSONArray = new JSONArray();
                        for (int i2 = 0; i2 < a2.g.size(); i2++) {
                            jSONArray.put(bnx.b(a2.g.get(i2)));
                        }
                        jSONObject.put("midPois", jSONArray);
                    }
                }
                int i3 = getArguments().getInt("voice_tokenid", -1);
                if (this.l >= 0) {
                    jSONObject.put("startNaviMitVoiceToken", sb.a("requestRoute", this.l, null));
                } else if (i3 >= 0) {
                    if (sa.d(sa.a(jSONObject))) {
                        sa.a(i3, 10000);
                    } else {
                        jSONObject.put("startNaviVoiceToken", sb.b("requestRouteStartNavi", i3, null));
                    }
                }
                if (a()) {
                    jSONObject.put("routeType", RouteType.CAR.getValue());
                } else {
                    jSONObject.put("routeType", RouteType.TRUCK.getValue());
                }
                if (!TextUtils.isEmpty(a2.z)) {
                    jSONObject.put(DriveUtil.SOURCE_APPLICATION, a2.z);
                } else if (!TextUtils.isEmpty(re.a((String) "car_navi_sourceapplication", (String) ""))) {
                    jSONObject.put(DriveUtil.SOURCE_APPLICATION, re.a((String) "car_navi_sourceapplication", (String) ""));
                }
                str = jSONObject.toString();
            } catch (Exception e2) {
                e2.printStackTrace();
                str = string;
            }
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int i4 = displayMetrics.widthPixels;
            int i5 = displayMetrics.heightPixels;
            this.mAjxView.setOnChildViewSHowListener(new ChildViewSHowListener() {
                public final void onDrawChildView() {
                    tj.a((String) "car-navipage-loaded_3.2");
                }
            });
            this.mAjxView.load(ModuleRouteDriveResult.CAR_NAVI, str, "CAR_NAVI", getClass().getSimpleName(), i4, i5, ModuleRouteDriveResult.CAR_NAVI_PRE_LOAD);
        }
        tj.a((String) "DriveGuide-------Native: AjxRouteCarNaviPage: loadJs: finish");
    }

    public void loadNodeFragmentBundle(PageBundle pageBundle) {
        super.loadNodeFragmentBundle(pageBundle);
        this.b = pageBundle.getString("navi_type", "car");
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        oe oeVar = new oe(c(), b(), this.mSpeakerPlayManager, getMapManager(), this.h);
        this.i = oeVar;
        if (this.mModuleCommonBusiness != null) {
            this.mModuleCommonBusiness.setINaviUiActionListener(this.s);
        }
        if (this.mModuleDriveCommonBusiness != null) {
            this.mModuleDriveCommonBusiness.setCompleteReportInfoCallBack(this.v);
        }
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.setNaviEventListener(this.i);
            this.mModuleDriveNavi.setNaviPageEventListener(this.u);
            this.mModuleDriveNavi.setReportEventListener(this.r);
            this.mModuleDriveNavi.setShareStatusListener(this.w);
            this.mModuleDriveNavi.setWritingPermissionStateCallback(this.y);
            this.mModuleDriveNavi.setIFullScreenChangeCallback(this.z);
        }
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            IAgroupOverlayService b2 = cuh.b();
            if (b2 != null) {
                if (a()) {
                    b2.a(getClass(), AgroupScenes.Navigation, getArguments(), "0".equals(this.mModuleDriveNavi.getRealDayNightMode()));
                    return;
                }
                b2.a(getClass());
            }
        }
    }

    public boolean getSpeakTTSMode() {
        return DriveSpUtil.getBool(AMapAppGlobal.getApplication(), DriveSpUtil.CALLING_SPEAK_TTS, false);
    }

    public boolean getTtsMixMusicMode() {
        return re.l();
    }

    public boolean isLightnessSwitchOn() {
        return DriveSpUtil.getBool(AMapAppGlobal.getApplication(), DriveSpUtil.LIGHT_INTENSITY, false);
    }

    private void a(boolean z2) {
        try {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mAjxView.getLayoutParams();
            if (!tt.c((Context) getActivity())) {
                if (marginLayoutParams.topMargin != 0) {
                    marginLayoutParams.topMargin = 0;
                    this.mAjxView.setLayoutParams(marginLayoutParams);
                }
                return;
            }
            if (z2) {
                marginLayoutParams.topMargin = 0;
            } else {
                marginLayoutParams.topMargin = ags.d(getActivity());
            }
            this.mAjxView.setLayoutParams(marginLayoutParams);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("initView#isHuaweiHideNotch:--->");
            sb.append(th.getMessage());
            cjy.b(sb.toString());
        }
    }

    public void updateNaviInfo(LocInfo locInfo) {
        if (locInfo != null && locInfo.MatchInfos != null && locInfo.sourType == 0 && locInfo != null) {
            long j2 = locInfo.ticktime;
            if (j2 - this.d > 400 || this.d == 0) {
                this.d = j2;
            }
        }
    }

    /* access modifiers changed from: private */
    public sp b() {
        if (this.g == null) {
            this.g = new sp();
        }
        return this.g;
    }

    /* access modifiers changed from: private */
    public NavigationDataResult c() {
        if (this.k == null) {
            this.k = new NavigationDataResult();
            ArrayList arrayList = (ArrayList) getArguments().getObject("RouteBoardTraceData");
            if (arrayList != null) {
                AMapLog.d("AjxRouteCarNaviPage", "yuanhc resultData routboard data pass to NavigationDataResult");
                this.k.setTraceListReference(arrayList);
            }
        }
        return this.k;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(java.lang.String r4) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 != 0) goto L_0x0026
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0019 }
            r1.<init>(r4)     // Catch:{ JSONException -> 0x0019 }
            java.lang.String r0 = "trackStorageFolder"
            java.lang.String r2 = defpackage.rd.b()     // Catch:{ JSONException -> 0x0017 }
            r1.put(r0, r2)     // Catch:{ JSONException -> 0x0017 }
            goto L_0x0020
        L_0x0017:
            r0 = move-exception
            goto L_0x001d
        L_0x0019:
            r1 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
        L_0x001d:
            r0.printStackTrace()
        L_0x0020:
            if (r1 == 0) goto L_0x0026
            java.lang.String r4 = r1.toString()
        L_0x0026:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.navi.drivenavi.normal.page.AjxRouteCarNaviPage.b(java.lang.String):java.lang.String");
    }

    public long getScene() {
        if (a()) {
            return IjkMediaMeta.AV_CH_STEREO_LEFT;
        }
        return 1073741824;
    }

    public void onJsBack(Object obj, String str) {
        handleExit();
        super.onJsBack(obj, str);
    }

    public NaviType getNaviType() {
        if (a()) {
            return NaviType.CAR_NAVI;
        }
        return NaviType.TRUCK_NAVI;
    }

    public void adjustLightness() {
        od.a().a(getContext(), isLightnessSwitchOn());
    }

    public void recoverOriginalLightness() {
        od.a().a(getContext());
    }

    public void showNaviTip(int i2, String str) {
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.sendGroupClipBoardMsg(i2, str);
        }
    }

    public final boolean a() {
        return !TextUtils.equals(this.b, DriveUtil.NAVI_TYPE_TRUCK);
    }

    private void b(boolean z2) {
        if (this.p) {
            ags.a(getActivity(), z2);
            try {
                if (getMapView() != null) {
                    aky b2 = getMapView().b();
                    if (b2 != null && !z2) {
                        View view = (View) b2;
                        view.setVisibility(4);
                        view.setVisibility(0);
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    public void configurationChanged(Configuration configuration) {
        if (this.mLastOrientation != configuration.orientation) {
            a(configuration.orientation == 2);
        }
        super.configurationChanged(configuration);
    }

    public void initLightness() {
        this.l = getArguments().getInt("mit_voice_tokenid", -1);
        od.a().a(getContext(), isLightnessSwitchOn(), this.l);
    }

    static /* synthetic */ boolean a(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealMetrics(displayMetrics);
        }
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics2);
        return i3 - displayMetrics2.widthPixels > 0 || i2 - displayMetrics2.heightPixels > 0;
    }

    static /* synthetic */ IAjxContext i(AjxRouteCarNaviPage ajxRouteCarNaviPage) {
        if (ajxRouteCarNaviPage.mAjxView == null) {
            return null;
        }
        return ajxRouteCarNaviPage.mAjxView.getAjxContext();
    }

    static /* synthetic */ void a(AjxRouteCarNaviPage ajxRouteCarNaviPage, String str) {
        PageBundle pageBundle = new PageBundle();
        String str2 = "car";
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("jsData", str);
            try {
                if (new JSONObject(str).getInt("routeType") == RouteType.TRUCK.getValue()) {
                    str2 = DriveUtil.NAVI_TYPE_TRUCK;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        pageBundle.putString("url", ModuleDriveNavi.CAR_NAVI_END);
        pageBundle.putObject("key_navigation_data_result", ajxRouteCarNaviPage.c());
        pageBundle.putString("navi_type", str2);
        ajxRouteCarNaviPage.handleStartNewPage();
        ajxRouteCarNaviPage.startPage(AjxRouteCarNaviEndPage.class, pageBundle);
        ajxRouteCarNaviPage.finish();
    }

    static /* synthetic */ void E(AjxRouteCarNaviPage ajxRouteCarNaviPage) {
        Intent intent = new Intent();
        intent.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, 1001);
        intent.putExtra(IVoicePackageManager.SHOW_TTS_ORIENTATION, ajxRouteCarNaviPage.getActivity().getRequestedOrientation());
        intent.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
        bid pageContext = AMapPageUtil.getPageContext();
        ajxRouteCarNaviPage.handleStartNewPage();
        if (pageContext != null && pageContext.isAlive() && ajxRouteCarNaviPage.c != null) {
            ajxRouteCarNaviPage.c.deal(pageContext, intent);
        }
    }
}
