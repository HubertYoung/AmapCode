package com.amap.bundle.drive.navi.motornavi.page;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.amap.bundle.drive.ajx.inter.INaviTip;
import com.amap.bundle.drive.ajx.inter.INaviUiActionListener;
import com.amap.bundle.drive.ajx.inter.IRealNaviEventCallback;
import com.amap.bundle.drive.ajx.inter.IRealNaviPageEventCallback;
import com.amap.bundle.drive.ajx.module.ModuleDriveNavi;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.common.basepage.AjxRouteNaviBasePage;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager.NaviType;
import com.amap.bundle.drive.naviend.motor.page.AjxRouteMotorNaviEndPage;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.schoolbus.notifcation.ISchoolbusStatusMangger;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView.ChildViewSHowListener;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import org.json.JSONArray;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@SuppressFBWarnings({"AMAP_OPT_X", "BIT_SIGNED_CHECK", "DB_DUPLICATE_BRANCHES", "BC_BAD_CAST_TO_CONCRETE_COLLECTION"})
@LocationPreference(availableOnBackground = true)
public class AjxRouteMotorNaviPage extends AjxRouteNaviBasePage implements INaviTip, IVoiceCmdResponder, launchModeSingleTask {
    boolean a = false;
    IRealNaviPageEventCallback b = new IRealNaviPageEventCallback() {
        public final void startDriveEndPage(String str) {
            ro.b();
            AjxRouteMotorNaviPage.this.d = true;
            String a2 = AjxRouteMotorNaviPage.a(str);
            PageBundle pageBundle = new PageBundle();
            if (!TextUtils.isEmpty(a2)) {
                pageBundle.putString("jsData", a2);
            }
            pageBundle.putString("url", "path://amap_bundle_motorbike/src/end_page/MotorBikeEndPage.page.js");
            pageBundle.putInt("route_car_type_key", 11);
            pageBundle.putObject("key_navigation_data_result", AjxRouteMotorNaviPage.this.a());
            pageBundle.putString("navi_type", DriveUtil.NAVI_TYPE_MOTORBIKE);
            AjxRouteMotorNaviPage.this.a = true;
            AjxRouteMotorNaviPage.this.startPage(AjxRouteMotorNaviEndPage.class, pageBundle);
            AjxRouteMotorNaviPage.this.finish();
        }

        public final void onOpenVoiceSquare() {
            AjxRouteMotorNaviPage.b(AjxRouteMotorNaviPage.this);
        }
    };
    private mv c = null;
    /* access modifiers changed from: private */
    public boolean d = false;
    /* access modifiers changed from: private */
    public boolean e = false;
    private IVoicePackageManager f;
    private IRealNaviEventCallback g;
    private LinkedHashSet<String> h = new LinkedHashSet<>();
    private NavigationDataResult i;
    private sp j;
    private String k;
    private boolean l = false;
    private final a m = new a() {
        public final void a() {
            czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
            if (czk != null && AjxRouteMotorNaviPage.this.getContext() != null) {
                czk.a(NotificationChannelIds.g, R.drawable.ic_launcher, AjxRouteMotorNaviPage.this.getResources().getString(R.string.autonavi_app_name_in_route), AjxRouteMotorNaviPage.this.getResources().getString(R.string.autonavi_navi_ing));
            }
        }
    };
    private INaviUiActionListener n = new INaviUiActionListener() {
        public final boolean bottomMenuIsShow() {
            return false;
        }

        public final void onFinishNaviPage() {
            AjxRouteMotorNaviPage.this.e = true;
        }
    };

    public long getScene() {
        return 4503599627370496L;
    }

    public void loadNodeFragmentBundle(PageBundle pageBundle) {
    }

    public void requestScreenOrientation() {
        requestScreenOrientation(1);
    }

    public NaviType getNaviType() {
        return NaviType.MOTOR;
    }

    public boolean willEnterNaviEndPage() {
        return this.d;
    }

    public void initLightness() {
        od.a().a(getContext(), isLightnessSwitchOn(), -1);
    }

    public void adjustLightness() {
        od.a().a(getContext(), isLightnessSwitchOn());
    }

    public void recoverOriginalLightness() {
        od.a().a(getContext());
    }

    public Ajx3PagePresenter createPresenter() {
        return new ny(this);
    }

    public void pageCreated() {
        super.pageCreated();
        cde suspendManager = getSuspendManager();
        cdz d2 = suspendManager != null ? suspendManager.d() : null;
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
                    czk.a(this.m);
                }
            }
        }
        this.f = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (TextUtils.isEmpty(this.k)) {
            this.k = String.valueOf(System.currentTimeMillis());
        }
        NavigationDataResult a2 = a();
        if (this.j == null) {
            this.j = new sp();
        }
        oe oeVar = new oe(a2, this.j, this.mSpeakerPlayManager, getMapManager(), this.h);
        this.g = oeVar;
        this.mNaviMgr.a(22, String.valueOf(re.b()));
        this.mNaviMgr.c(12, String.valueOf(re.b()));
        bfe bfe = d.a;
        VUIStateManager.f().q = true;
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            IAgroupOverlayService b2 = cuh.b();
            if (b2 != null) {
                b2.a(getClass(), AgroupScenes.Navigation, getArguments(), !this.g.isRealDayNightMode());
            }
        }
        if (!to.a(this)) {
            to.a(this, true);
        }
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.mModuleDriveNavi = (ModuleDriveNavi) this.mAjxView.getJsModule(ModuleDriveNavi.MODULE_NAME);
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.setNaviEventListener(this.g);
            this.mModuleDriveNavi.setNaviPageEventListener(this.b);
        }
        if (this.mModuleCommonBusiness != null) {
            this.mModuleCommonBusiness.setINaviUiActionListener(this.n);
        }
    }

    public void loadJs() {
        String str;
        if (getArguments() != null) {
            String string = getArguments().getString("jsData");
            try {
                JSONObject jSONObject = new JSONObject();
                if (!TextUtils.isEmpty(string)) {
                    jSONObject = new JSONObject(string);
                }
                if (!jSONObject.has("result")) {
                    sp a2 = rk.a(getContext(), getArguments());
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
                if (i3 >= 0) {
                    if (sa.d(sa.a(jSONObject))) {
                        sa.a(i3, 10000);
                    } else {
                        jSONObject.put("startNaviVoiceToken", sb.b("requestRouteStartNavi", i3, null));
                    }
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
            this.mAjxView.load(ModuleRouteDriveResult.MOTOR_NAVI, str, "MOTOR_NAVI", getClass().getSimpleName(), i4, i5, "path://amap_bundle_motorbike/src/result_page/motorBikeNaviPage_preload.js");
        }
    }

    public void resume() {
        super.resume();
        this.a = false;
        String str = "";
        afz afz = (afz) a.a.a(afz.class);
        if (afz != null) {
            str = afz.a();
        }
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.onVoiceSquareBack(str);
        }
        if (ra.a(this)) {
            if (this.c == null) {
                this.c = new mv(this);
            }
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().getDecorView().addOnLayoutChangeListener(this.c);
            }
        }
    }

    public void pause() {
        super.pause();
        b();
    }

    public void stop() {
        super.stop();
        if (!this.e && !this.a && !this.l && !PlaySoundUtils.getInstance().isPhoneCalling() && !PlaySoundUtils.getInstance().isSilent()) {
            String string = AMapAppGlobal.getApplication().getString(R.string.route_navi_continue_navi_text);
            if (!PlaySoundUtils.getInstance().voiceInSoundStrQueue(string)) {
                PlaySoundUtils.getInstance().playSound(string);
                this.l = true;
            }
        }
    }

    public void destroy() {
        super.destroy();
        b();
        if (ahp.a(getContext())) {
            ro.b();
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
        rj.a().b();
        bfe bfe = d.a;
        VUIStateManager.f().q = false;
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a((cuj) null);
        }
        if (this.mModuleCommonBusiness != null) {
            this.mModuleCommonBusiness.setINaviUiActionListener(null);
        }
    }

    public boolean isLightnessSwitchOn() {
        return re.g() == 1;
    }

    public boolean getSpeakTTSMode() {
        return re.m() != 0;
    }

    public boolean getTtsMixMusicMode() {
        return re.k() == 1;
    }

    public void showNaviTip(int i2, String str) {
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.sendGroupClipBoardMsg(i2, str);
        }
    }

    private void b() {
        if (ra.a(this) && this.c != null) {
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().getDecorView().removeOnLayoutChangeListener(this.c);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final NavigationDataResult a() {
        if (this.i == null) {
            this.i = new NavigationDataResult();
            ArrayList arrayList = (ArrayList) getArguments().getObject("RouteBoardTraceData");
            if (arrayList != null) {
                AMapLog.d(Ajx3Page.TAG, "yuanhc resultData routboard data pass to NavigationDataResult");
                this.i.setTraceListReference(arrayList);
            }
            this.i.setNaviId(this.k);
            this.i.setRouteNaviIdAllContainer(this.h);
            this.i.setMethod(DriveUtil.getLastRoutingChoice());
        }
        return this.i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.lang.String a(java.lang.String r4) {
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
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.navi.motornavi.page.AjxRouteMotorNaviPage.a(java.lang.String):java.lang.String");
    }

    public void onJsBack(Object obj, String str) {
        this.e = true;
        super.onJsBack(obj, str);
    }

    static /* synthetic */ void b(AjxRouteMotorNaviPage ajxRouteMotorNaviPage) {
        Intent intent = new Intent();
        intent.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, 1001);
        intent.putExtra(IVoicePackageManager.SHOW_TTS_ORIENTATION, ajxRouteMotorNaviPage.getActivity().getRequestedOrientation());
        intent.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
        bid pageContext = AMapPageUtil.getPageContext();
        ajxRouteMotorNaviPage.a = true;
        if (pageContext != null && pageContext.isAlive() && ajxRouteMotorNaviPage.f != null) {
            ajxRouteMotorNaviPage.f.deal(pageContext, intent);
        }
    }
}
