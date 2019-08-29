package com.autonavi.minimap.route.foot.page;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.footresult.ajx.ModuleFoot;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.jni.ae.pos.LocManager;
import com.autonavi.map.core.LocationMode.LocationGpsOnly;
import com.autonavi.map.core.SensorMode.a;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.foot.presenter.AjxFootNaviPresenter;
import com.autonavi.minimap.route.foot.view.Compass;
import com.autonavi.minimap.route.foot.view.CompassView;
import com.autonavi.minimap.route.foot.view.CompassView.c;
import com.autonavi.sdk.log.util.LogConstant;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.HandleInterruptEvent;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@LocationPreference(availableOnBackground = true)
public class AjxFootNaviPage extends Ajx3Page implements axj, axk, IVoiceCmdResponder, LocationGpsOnly, a, launchModeSingleTask, HandleInterruptEvent, edd {
    public boolean a = true;
    private ecw b;
    private FrameLayout c;
    private CompassView d;
    /* access modifiers changed from: private */
    public AjxFootNaviPresenter e;
    private boolean f;
    private Compass.a g = new Compass.a() {
        public final void onFindRightDirection() {
            AjxFootNaviPage.a(AjxFootNaviPage.this.getContext());
        }
    };
    private c h = new c() {
        public final void a() {
            AjxFootNaviPage.this.e.b = true;
            AjxFootNaviPage.this.c(false);
        }
    };

    @Nullable
    public String getAjx3Url() {
        return ModuleFoot.URL_FOOT_NAVI;
    }

    public long getScene() {
        return IjkMediaMeta.AV_CH_WIDE_RIGHT;
    }

    public Ajx3PagePresenter createPresenter() {
        this.mPresenter = new AjxFootNaviPresenter(this);
        this.e = (AjxFootNaviPresenter) this.mPresenter;
        return (Ajx3PagePresenter) this.mPresenter;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        eau.a("performance-", "AjxFootNaviPage  onCreate");
        ebn.a(getActivity().getWindow().getDecorView());
        requestScreenOrientation(1);
        requestScreenOn(true);
        this.c = (FrameLayout) findViewById(R.id.compass_container);
        getActivity().setVolumeControlStream(3);
        axq.a().a(axq.b);
        eac a2 = eac.a();
        ead a3 = ead.a(2);
        a3.c = eay.a(R.string.notification_title_keep_navi);
        a3.d = eay.a(R.string.notification_content_foot_navi);
        a2.a(a3);
        dys.a("P00031", "B039");
        dys.b("P00031", LogConstant.MAIN_MIUI_TIPS_TIP_DIALOG);
        a.a.a().setExitNaviListener(new ekm() {
            public final void a() {
                avl avl = (avl) a.a.a(avl.class);
                if (avl != null) {
                    avl.b().b(AjxFootNaviPage.this.mAjxView);
                    eko.a(10000);
                }
            }
        });
        eko.a(10000);
        LocManager.setAMapStatu(0);
        ebm.a(getString(R.string.voice_log_foot_navi));
        bty mapView = getMapView();
        if (mapView != null) {
            this.f = mapView.s();
            mapView.b(false);
        }
        ecd.a().a = true;
    }

    public View createMapInteractiveLayout(View view) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.ajx_foot_navi_layout, null, false);
        RelativeLayout relativeLayout = (RelativeLayout) viewGroup.findViewById(R.id.ajx_view_container);
        relativeLayout.removeAllViews();
        relativeLayout.addView(view, new LayoutParams(-1, -1));
        return viewGroup;
    }

    public static void c() {
        eca.a().b = null;
    }

    /* access modifiers changed from: private */
    public void a(final int i) {
        final ScaleView f2 = getSuspendWidgetHelper().f();
        if (f2 != null) {
            f2.post(new Runnable() {
                public final void run() {
                    if (i == 2 || i == 3) {
                        f2.setVisibility(8);
                    } else {
                        f2.setVisibility(0);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void b(int i) {
        bty mapView = getMapView();
        if (mapView != null) {
            if (1 == i) {
                mapView.f(true);
                return;
            }
            mapView.f(false);
        }
    }

    public void resume() {
        super.resume();
        a(eca.a().a);
        b(eca.a().a);
        bty mapView = getMapView();
        if (mapView != null) {
            ebf.a(mapView, mapView.j(false), 0, 6);
            mapView.h(true);
        }
    }

    public void stop() {
        super.stop();
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.h(false);
            ebf.a(mapView, mapView.j(false), mapView.l(false), 3);
        }
    }

    public void destroy() {
        axq.a().b(axq.b);
        avl avl = (avl) a.a.a(avl.class);
        if (avl != null) {
            avl.b().a((AmapAjxViewInterface) this.mAjxView, (axj) null);
            avl.b().a((AmapAjxViewInterface) this.mAjxView, (axk) null);
            avl.b().a((AmapAjxViewInterface) this.mAjxView, (axn) null);
            avl.b().a((AmapAjxViewInterface) this.mAjxView, (axo) null);
        }
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.b(this.f);
        }
        super.destroy();
        ecd.a().a = false;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        eau.a("performance-", "showFootNaviPage");
        if (isAlive() && iAjxContext != null) {
            avl avl = (avl) a.a.a(avl.class);
            if (avl != null) {
                avl.b().a((AmapAjxViewInterface) this.mAjxView, (axj) this);
                avl.b().a((AmapAjxViewInterface) this.mAjxView, (axk) this);
                avl.b().a((AmapAjxViewInterface) this.mAjxView, (axn) this.e);
                avl.b().a((AmapAjxViewInterface) this.mAjxView, (axo) this.e);
            }
        }
    }

    public void loadJs() {
        this.mAjxView.load(ModuleFoot.URL_FOOT_NAVI, this.e.e(), "FOOT_NAVI_PAGE");
    }

    public View getMapSuspendView() {
        this.b = new ecw(this);
        cde suspendManager = getSuspendManager();
        if (suspendManager != null) {
            cdz d2 = suspendManager.d();
            if (d2 != null) {
                d2.f();
            }
        }
        return this.b.a.getSuspendView();
    }

    public void setMakeReceiveCallEvent(int i) {
        if (i > 0) {
            PlaySoundUtils.getInstance().clear();
        }
    }

    public final void a(String str) {
        if (this.b != null) {
            this.b.a(str.equals("0") ? 62 : 4);
        }
    }

    public final void a(boolean z) {
        this.a = z;
    }

    public final void a() {
        this.e.d();
        this.e.h();
        boolean z = this.a;
        AudioManager audioManager = (AudioManager) AMapAppGlobal.getApplication().getSystemService("audio");
        if (audioManager != null) {
            String str = "";
            float streamVolume = (float) audioManager.getStreamVolume(3);
            float streamMaxVolume = streamVolume / ((float) audioManager.getStreamMaxVolume(3));
            if (!z) {
                str = streamMaxVolume == 0.0f ? "off/0" : "off/not0";
            } else if (streamVolume == 0.0f) {
                str = "on/0";
            } else if (0.0f < streamMaxVolume && ((double) streamMaxVolume) <= 0.5d) {
                str = "on/(0,50%]";
            } else if (0.5d < ((double) streamMaxVolume) && streamMaxVolume < 1.0f) {
                str = "on/(50%,100%)";
            } else if (streamMaxVolume >= 1.0f) {
                str = "on/[100%,∞)";
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("text", str);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00031", "B011", jSONObject);
        }
        AjxFootNaviPresenter ajxFootNaviPresenter = this.e;
        String a2 = ajxFootNaviPresenter.a(ajxFootNaviPresenter.f());
        if (!TextUtils.isEmpty(a2)) {
            if (ebm.c()) {
                AjxFootNaviPresenter.a((String) "B007", a2);
            } else {
                AjxFootNaviPresenter.a((String) "B001", a2);
            }
        }
        AjxFootNaviPresenter ajxFootNaviPresenter2 = this.e;
        String a3 = ajxFootNaviPresenter2.a(ajxFootNaviPresenter2.g());
        if (!TextUtils.isEmpty(a3)) {
            if (ebm.c()) {
                AjxFootNaviPresenter.a((String) "B008", a3);
            } else {
                AjxFootNaviPresenter.a((String) "B002", a3);
            }
        }
        if (this.d != null) {
            this.d.clearCache();
        }
    }

    public final void b() {
        this.e.i();
    }

    public final void a(float f2, float f3) {
        this.e.b = false;
        if (this.d == null) {
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            this.d = new CompassView(getContext());
            this.d.setLayoutParams(layoutParams);
            this.d.setTargetDirection(f2, f3);
            this.c.addView(this.d);
            this.d.showAnim(edr.a((String) "footnavimodewithangle", false));
            this.d.setOnFindRightDirectionListener(this.g);
            this.d.setOnHidedListener(this.h);
        } else if (this.d.getVisibility() != 0) {
            this.d.setTargetDirection(f2, f3);
            this.d.setVisibility(0);
            this.d.showAnim(edr.a((String) "footnavimodewithangle", false));
        }
    }

    public final void a(String str, String str2, boolean z) {
        StringBuilder sb = new StringBuilder("title = ");
        sb.append(str);
        sb.append("text = ");
        sb.append(str2);
        sb.append("isNeedNotify = trueforce = ");
        sb.append(z);
        eau.a("showNotify", sb.toString());
        eac a2 = eac.a();
        ead a3 = ead.a(2);
        a3.c = str;
        a3.d = str2;
        a2.a(a3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(boolean r4) {
        /*
            r3 = this;
            r0 = 2000(0x7d0, float:2.803E-42)
            if (r4 == 0) goto L_0x0036
            android.content.Context r4 = r3.getContext()
            java.lang.String r1 = "audio"
            java.lang.Object r4 = r4.getSystemService(r1)
            android.media.AudioManager r4 = (android.media.AudioManager) r4
            if (r4 == 0) goto L_0x0029
            r1 = 3
            int r2 = r4.getStreamMaxVolume(r1)
            int r4 = r4.getStreamVolume(r1)
            int r2 = r2 / r1
            if (r4 >= r2) goto L_0x0029
            int r4 = com.autonavi.minimap.R.string.foot_navi_adjust_volume_tip
            java.lang.String r4 = r3.getString(r4)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r4)
            r4 = 1
            goto L_0x002a
        L_0x0029:
            r4 = 0
        L_0x002a:
            if (r4 != 0) goto L_0x003f
            int r4 = com.autonavi.minimap.R.string.route_voice_open
            java.lang.String r4 = r3.getString(r4)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r4, r0)
            return
        L_0x0036:
            int r4 = com.autonavi.minimap.R.string.route_voice_close
            java.lang.String r4 = r3.getString(r4)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r4, r0)
        L_0x003f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.foot.page.AjxFootNaviPage.b(boolean):void");
    }

    public final void b(String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_locationselect/src/Homepage.page.js");
        pageBundle.putString("jsData", this.e.a(str));
        startPageForResult((String) "amap.basemap.action.base_select_fix_poi_from_map_ajx_page", pageBundle, 1);
    }

    public final void c(boolean z) {
        avl avl = (avl) a.a.a(avl.class);
        if (avl != null) {
            avl.b().b((AmapAjxViewInterface) this.mAjxView, z);
        }
    }

    static /* synthetic */ void a(Context context) {
        PlaySoundUtils.getInstance().playNaviWarningSound(context, R.raw.find_direction_sound);
        ebs a2 = ebs.a(context);
        if (a2.a != null) {
            try {
                if (a2.a.hasVibrator()) {
                    a2.a.vibrate(100);
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
    }
}
