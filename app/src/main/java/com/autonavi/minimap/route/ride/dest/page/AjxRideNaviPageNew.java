package com.autonavi.minimap.route.ride.dest.page;

import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.map.core.LocationMode.LocationGpsOnly;
import com.autonavi.map.core.SensorMode.a;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.ajx.inter.OnAjxRideNaviInterface;
import com.autonavi.minimap.route.ajx.module.ModuleValues;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.dest.presenter.AjxRideNaviPresenter;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.minimap.route.sharebike.order.OrderState;
import com.autonavi.minimap.route.sharebike.view.ShareRidingTipInNaviView;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.HandleInterruptEvent;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@LocationPreference(availableOnBackground = true)
public class AjxRideNaviPageNew extends Ajx3Page implements axj, IVoiceCmdResponder, LocationGpsOnly, a, launchModeSingleTask, OnAjxRideNaviInterface, HandleInterruptEvent, edd, egw {
    public ShareRidingTipInNaviView a;
    public boolean b = true;
    private edv c;

    public long getScene() {
        return IjkMediaMeta.AV_CH_WIDE_LEFT;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public AjxRideNaviPresenter createPresenter() {
        return new AjxRideNaviPresenter(this);
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            arguments.putString("url", ModuleValues.URL_RIDE_NAVI);
        }
        super.onCreate(context);
        ebn.a(getActivity().getWindow().getDecorView());
        requestScreenOrientation(1);
        requestScreenOn(true);
        getActivity().setVolumeControlStream(3);
        axq.a().a(axq.c);
        eac a2 = eac.a();
        ead a3 = ead.a(3);
        a3.c = eay.a(R.string.notification_title_keep_navi);
        a3.d = eay.a(R.string.notification_content_ride_navi);
        a2.a(a3);
        dys.a("P00275", "B005");
        dys.b("P00275", "B014");
        this.a = (ShareRidingTipInNaviView) findViewById(R.id.share_bike_riding_tip);
        d();
        a.a.a().setExitNaviListener(new ekm() {
            public final void a() {
                awy awy = (awy) a.a.a(awy.class);
                if (awy != null) {
                    awy.b().c(AjxRideNaviPageNew.this.mAjxView);
                    eko.a(10000);
                }
            }
        });
        ebm.a(getString(R.string.voice_log_dest_navi));
        eec.a().a = true;
    }

    public View createMapInteractiveLayout(View view) {
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.ajx_ride_navi_layout, null, false);
        RelativeLayout relativeLayout = (RelativeLayout) viewGroup.findViewById(R.id.ajx_view_container);
        relativeLayout.removeAllViews();
        relativeLayout.addView(view, new LayoutParams(-1, -1));
        return viewGroup;
    }

    public void resume() {
        super.resume();
        bty mapView = getMapView();
        if (mapView != null) {
            ebf.a(mapView, mapView.j(false), 0, 12);
            mapView.h(true);
        }
        edo.a("JS:#", "onResume");
        if (euk.a() && this.a.getVisibility() == 0) {
            euk.a(getActivity(), this.a.getDrawingCacheBackgroundColor());
        }
    }

    public void stop() {
        super.stop();
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.h(false);
            ebf.a(mapView, mapView.j(false), mapView.l(false), 11);
        }
    }

    public void destroy() {
        super.destroy();
        awy awy = (awy) a.a.a(awy.class);
        if (awy != null) {
            awy.b().a(this.mAjxView, (axj) null);
        }
        if (this.mAjxView != null) {
            this.mAjxView.destroy();
            this.mAjxView = null;
        }
        axq.a().b(axq.c);
        a.a.a().setExitNaviListener(null);
        eec.a().a = false;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        edo.a("performance-", "showRideNaviPage");
        if (isAlive() && iAjxContext != null) {
            awy awy = (awy) a.a.a(awy.class);
            if (awy != null) {
                awy.b().a(this.mAjxView, (axj) this);
                awy.b().a(this.mAjxView, (OnAjxRideNaviInterface) this);
            }
        }
    }

    public void loadJs() {
        String b2 = ((AjxRideNaviPresenter) this.mPresenter).b();
        edo.a("JS:#", UCCore.OPTION_LOAD_KERNEL_TYPE);
        this.mAjxView.load(this.mUrl, b2, "RIDE_NAVI_PAGE");
    }

    public View getMapSuspendView() {
        this.c = new edv(this);
        cde suspendManager = getSuspendManager();
        if (suspendManager != null) {
            cdz d = suspendManager.d();
            if (d != null) {
                d.f();
            }
        }
        return this.c.a.getSuspendView();
    }

    public final void a(boolean z) {
        if (this.a != null && isAlive()) {
            this.a.setVisibility(z ? 0 : 8);
            if (z && euk.a()) {
                this.a.setPadding(this.a.getPaddingLeft(), this.a.getPaddingTop() + euk.a(getContext()), this.a.getPaddingRight(), this.a.getPaddingBottom());
            }
        }
    }

    public final void a(RideState rideState, boolean z) {
        if (this.a != null && isAlive()) {
            this.a.setRidingDetail(rideState, z);
        }
    }

    public final boolean a(OrderState orderState, OrderInfo orderInfo, int i) {
        if (orderInfo == null) {
            return false;
        }
        if (orderInfo.hasNetFailed) {
            a((RideState) null, a());
            if (i == 60) {
                a((ecy) null, false);
            }
            eko.a((int) SDKFactory.getCoreType);
        } else if (orderInfo.extraData instanceof RideState) {
            final RideState rideState = (RideState) orderInfo.extraData;
            bdf bdf = (bdf) a.a.a(bdf.class);
            if (rideState.status == 0) {
                c();
                a((ecy) new ecy() {
                    public final void a(TraceStatistics traceStatistics, RideTraceHistory rideTraceHistory) {
                        if (AjxRideNaviPageNew.this.isAlive()) {
                            awy awy = (awy) a.a.a(awy.class);
                            if (awy != null) {
                                awy.b().a(AjxRideNaviPageNew.this.mAjxView);
                            }
                        }
                    }
                }, true);
                if (bdf != null) {
                    bdf.a().b(this);
                    bdf.a().a(false);
                }
                eko.a((int) SDKFactory.getCoreType);
            } else if (rideState.status == 3) {
                c();
                a(false);
                if (bdf != null) {
                    bdf.a().b(this);
                    bdf.a().a(false);
                }
                eko.a((int) SDKFactory.getCoreType);
            } else {
                aho.a(new Runnable() {
                    public final void run() {
                        AjxRideNaviPageNew.this.a(rideState, AjxRideNaviPageNew.a());
                    }
                });
            }
        }
        return false;
    }

    public void setMakeReceiveCallEvent(int i) {
        if (i > 0) {
            PlaySoundUtils.getInstance().clear();
        }
    }

    public final void a(String str) {
        if (this.c != null) {
            this.c.a(str.equals("0") ? 58 : 4);
        }
    }

    public void onVoiceStatusChange(boolean z) {
        this.b = z;
    }

    public void onExitPage() {
        ((AjxRideNaviPresenter) this.mPresenter).a();
        ((AjxRideNaviPresenter) this.mPresenter).e();
        boolean z = this.b;
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00275", "B015", jSONObject);
        }
        AjxRideNaviPresenter ajxRideNaviPresenter = (AjxRideNaviPresenter) this.mPresenter;
        String a2 = ajxRideNaviPresenter.a(ajxRideNaviPresenter.c());
        if (!TextUtils.isEmpty(a2)) {
            if (ebm.c()) {
                AjxRideNaviPresenter.a((String) "B007", a2);
            } else {
                AjxRideNaviPresenter.a((String) "B001", a2);
            }
        }
        AjxRideNaviPresenter ajxRideNaviPresenter2 = (AjxRideNaviPresenter) this.mPresenter;
        String a3 = ajxRideNaviPresenter2.a(ajxRideNaviPresenter2.d());
        if (!TextUtils.isEmpty(a3)) {
            if (ebm.c()) {
                AjxRideNaviPresenter.a((String) "B008", a3);
                return;
            }
            AjxRideNaviPresenter.a((String) "B002", a3);
        }
    }

    public void onVoiceToast(boolean z) {
        if (!z) {
            ToastHelper.showToast(getString(R.string.route_voice_close), 2000);
        } else if (!d()) {
            ToastHelper.showToast(getString(R.string.route_voice_open), 2000);
        }
    }

    private boolean d() {
        AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
        if (audioManager == null) {
            return false;
        }
        if (audioManager.getStreamVolume(3) >= audioManager.getStreamMaxVolume(3) / 3) {
            return false;
        }
        ToastHelper.showToast(getString(R.string.foot_navi_adjust_volume_tip));
        return true;
    }

    private static void c() {
        bdf bdf = (bdf) a.a.a(bdf.class);
        if (bdf != null) {
            bdf.a().a();
        }
    }

    public static boolean a() {
        bdf bdf = (bdf) a.a.a(bdf.class);
        return bdf != null && bdf.a().b();
    }

    private static void a(ecy ecy, boolean z) {
        bdf bdf = (bdf) a.a.a(bdf.class);
        if (bdf != null) {
            bdf.a(ecy, z, z);
        }
    }

    public void onNotifyChange(String str, String str2, boolean z) {
        StringBuilder sb = new StringBuilder("title = ");
        sb.append(str);
        sb.append(" text = ");
        sb.append(str2);
        sb.append(" isNeedNotify = true force = ");
        sb.append(z);
        edo.a("showNotify", sb.toString());
        eac a2 = eac.a();
        ead a3 = ead.a(3);
        a3.c = str;
        a3.d = str2;
        a2.a(a3);
    }
}
