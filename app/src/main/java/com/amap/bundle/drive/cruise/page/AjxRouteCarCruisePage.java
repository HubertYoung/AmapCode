package com.amap.bundle.drive.cruise.page;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.Secure;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.amap.bundle.drive.ajx.inter.ICruiseEvent;
import com.amap.bundle.drive.common.basepage.AjxRouteCarNaviBasePage;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager.NaviType;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.TripSpUtil;
import com.amap.bundle.schoolbus.notifcation.ISchoolbusStatusMangger;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.map.core.LocationMode.LocationGpsOnly;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView.ChildViewSHowListener;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.util.ArrayList;
import java.util.Iterator;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@PageAction("amap.drive.action.edog")
@LocationPreference(availableOnBackground = true)
public class AjxRouteCarCruisePage extends AjxRouteCarNaviBasePage implements LocationGpsOnly, f {
    private alt a;
    /* access modifiers changed from: private */
    public nl b = null;
    private Context c;
    private boolean d = false;
    private final a e = new a() {
        public final void a() {
            czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
            if (czk != null && AjxRouteCarCruisePage.this.getContext() != null) {
                czk.a(NotificationChannelIds.h, R.drawable.ic_launcher, AjxRouteCarCruisePage.this.getResources().getString(R.string.autonavi_app_name_in_route), AjxRouteCarCruisePage.this.getResources().getString(R.string.autonavi_edog_ing));
            }
        }
    };
    private ICruiseEvent f = new ICruiseEvent() {
        public final void refreshCameraLayer() {
            AjxRouteCarCruisePage.this.a();
        }
    };

    public void adjustLightness() {
    }

    @Nullable
    public String getAjx3Url() {
        return "path://amap_bundle_drive/src/cruise/CarCruisePage.page.js";
    }

    public void initLightness() {
    }

    public boolean isLightnessSwitchOn() {
        return false;
    }

    public void recoverOriginalLightness() {
    }

    public NaviType getNaviType() {
        return NaviType.CRUISE;
    }

    public void onCreate(Context context) {
        a((String) "onCreate");
        this.c = context;
        this.mShowCustomStatusBar = false;
        super.onCreate(context);
        czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
        if (czk != null) {
            if (czk.b()) {
                czk.a(NotificationChannelIds.h, R.drawable.ic_launcher, getResources().getString(R.string.autonavi_app_name_in_route), getResources().getString(R.string.autonavi_edog_ing));
            } else {
                czk.a(this.e);
            }
        }
        getMapView();
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            ArrayList<LayerItem> i = awo.i();
            if (!i.isEmpty()) {
                Iterator<LayerItem> it = i.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    LayerItem next = it.next();
                    if (next != null && next.getLayer_id() == 600010) {
                        if (awo != null) {
                            a((String) "appendLayerToMap: addLayer: success");
                            awo.a(next.getData());
                        }
                    }
                }
            } else {
                a((String) "appendLayerToMap: addLayer: fail: dataList is Empty");
            }
        }
        a();
        boolean cruiseBroadCastState = TripSpUtil.getCruiseBroadCastState(AMapAppGlobal.getApplication());
        if (!cruiseBroadCastState) {
            tr.a(this, getString(R.string.autonavi_audio_switch_closed), R.drawable.voice_closed);
        }
        if (!(!cruiseBroadCastState)) {
            to.a(this, true);
        }
    }

    public void pageCreated() {
        a((String) "pageCreated");
        super.pageCreated();
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            MapManager mapManager = getMapManager();
            int p = mapView.p(false);
            if (mapManager != null) {
                bty mapView2 = mapManager.getMapView();
                if (mapView2 != null) {
                    mapView2.a(mapView2.e().j(), p, 0, 18, 2);
                }
            }
            mapView.e(getContext().getSharedPreferences("Traffic_Config", 0).getInt("traffic_edog_last_scale_875", 18));
        }
        if (getSuspendManager() != null && getSuspendManager().d() != null) {
            cdz d2 = getSuspendManager().d();
            d2.b(1);
            d2.a(false);
            d2.f();
        }
    }

    public void resume() {
        boolean z;
        a((String) "resume");
        super.resume();
        boolean z2 = false;
        this.mHasLaunchedNewPage = false;
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.b(9001);
        }
        alu alu = new alu();
        alu.a = 9001;
        this.a = getMapView().a(alu);
        if (awo != null && awo.d()) {
            awo.e();
        }
        String string = Secure.getString(AMapAppGlobal.getApplication().getContentResolver(), "location_providers_allowed");
        if (string == null) {
            z = false;
        } else {
            z = string.contains(WidgetType.GPS);
        }
        boolean isProviderEnabled = LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS);
        if (z && isProviderEnabled) {
            z2 = true;
        }
        if (z2) {
            if (this.b != null) {
                this.b.a();
                this.b = null;
            }
        } else if (this.b == null) {
            a aVar = new a();
            aVar.a = AMapAppGlobal.getApplication().getString(R.string.edog_nogps_dialog_title);
            String string2 = AMapAppGlobal.getApplication().getString(R.string.edog_nogps_dialog_negative_string);
            String string3 = AMapAppGlobal.getApplication().getString(R.string.edog_nogps_dialog_positive_string);
            aVar.b = string2;
            aVar.c = null;
            aVar.d = string3;
            aVar.f = new c() {
                public final void a() {
                    AjxRouteCarCruisePage.this.b = null;
                }
            };
            nl nlVar = new nl(this);
            nlVar.a = aVar.a;
            String str = aVar.b;
            String str2 = aVar.d;
            nlVar.b = str;
            nlVar.c = str2;
            nlVar.d = aVar.e;
            nlVar.e = aVar.f;
            this.b = nlVar;
            this.b.d = new b() {
                public final void a() {
                    AjxRouteCarCruisePage.this.b.a();
                    AjxRouteCarCruisePage.this.b = null;
                }

                public final void b() {
                    AjxRouteCarCruisePage ajxRouteCarCruisePage = AjxRouteCarCruisePage.this;
                    if (ajxRouteCarCruisePage != null) {
                        try {
                            if (ajxRouteCarCruisePage.isAlive()) {
                                Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                                intent.setFlags(268435456);
                                ajxRouteCarCruisePage.getActivity().startActivityForResult(intent, 4098);
                            }
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            ToastHelper.showToast(nk.a((AbstractBasePage) ajxRouteCarCruisePage, R.string.autonavi_dlg_open_setting_failed));
                        } catch (SecurityException e2) {
                            e2.printStackTrace();
                            ToastHelper.showToast(nk.a((AbstractBasePage) ajxRouteCarCruisePage, R.string.autonavi_dlg_open_setting_failed));
                        }
                    }
                }
            };
            nl nlVar2 = this.b;
            synchronized (nlVar2.h) {
                a aVar2 = new a(AMapAppGlobal.getApplication());
                if (!TextUtils.isEmpty(nlVar2.a)) {
                    aVar2.a((CharSequence) nlVar2.a);
                }
                if (!TextUtils.isEmpty(nlVar2.b)) {
                    aVar2.b((CharSequence) nlVar2.b, (a) new a() {
                        public final void onClick(AlertView alertView, int i) {
                            if (nl.this.d != null) {
                                nl.this.d.a();
                            }
                        }
                    });
                }
                if (!TextUtils.isEmpty(nlVar2.c)) {
                    aVar2.a((CharSequence) nlVar2.c, (a) new a() {
                        public final void onClick(AlertView alertView, int i) {
                            if (nl.this.d != null) {
                                nl.this.d.b();
                            }
                        }
                    });
                }
                aVar2.c = new a() {
                    public final void onClick(AlertView alertView, int i) {
                        if (nl.this.e != null) {
                            nl.this.e.a();
                        }
                    }
                };
                aVar2.a(true);
                nlVar2.g = aVar2.a();
                nlVar2.f.showViewLayer(nlVar2.g);
            }
        }
    }

    public void pause() {
        a((String) AudioUtils.CMDPAUSE);
        super.pause();
        if (this.a != null) {
            alv alv = new alv();
            alv.a = 9001;
            alv.b = this.a.a;
            alv.c = this.a.b;
            alv.d = 1;
            getMapView().a(alv);
        }
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c(9001);
            awo.c(9003);
        }
    }

    public void stop() {
        a((String) AudioUtils.CMDSTOP);
        super.stop();
        if (!this.mIsExit && !this.mHasLaunchedNewPage && !this.d && !PlaySoundUtils.getInstance().isPhoneCalling() && !PlaySoundUtils.getInstance().isSilent()) {
            String string = AMapAppGlobal.getApplication().getString(R.string.edog_pause_voice);
            if (!PlaySoundUtils.getInstance().voiceInSoundStrQueue(string)) {
                PlaySoundUtils.getInstance().playSound(string);
                this.d = true;
            }
        }
        if (getMapView() != null) {
            Context context = getContext();
            context.getSharedPreferences("Traffic_Config", 0).edit().putInt("traffic_edog_last_scale_875", getMapView().w()).apply();
        }
    }

    public void destroy() {
        a((String) "destroy");
        super.destroy();
        rw.a(getContext()).a();
        czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
        if (czk != null) {
            ISchoolbusStatusMangger iSchoolbusStatusMangger = (ISchoolbusStatusMangger) ank.a(ISchoolbusStatusMangger.class);
            if (iSchoolbusStatusMangger == null || !iSchoolbusStatusMangger.isTravelling()) {
                czk.a();
            } else {
                czk.a(NotificationChannelIds.p, R.drawable.ic_launcher, "高德地图", "安心校车正在使用位置服务");
            }
        }
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c(600010);
            awo.a(600010);
        }
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a(true);
        }
    }

    public Ajx3PagePresenter createPresenter() {
        return new nj(this);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.mModuleDriveCommonBusiness.setCruiseEventListener(this.f);
    }

    public boolean getSpeakTTSMode() {
        return DriveSpUtil.getBool(AMapAppGlobal.getApplication(), DriveSpUtil.CALLING_SPEAK_TTS, false);
    }

    public boolean getTtsMixMusicMode() {
        return re.l();
    }

    public void loadJs() {
        a((String) "loadJs");
        if (getArguments() != null) {
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            this.mAjxView.setOnChildViewSHowListener(new ChildViewSHowListener() {
                public final void onDrawChildView() {
                    tj.a((String) "car-cruise-page-loaded_3.2");
                }
            });
            this.mAjxView.load("path://amap_bundle_drive/src/cruise/CarCruisePage.page.js", null, "CAR_CRUISE", i, i2);
        }
    }

    public void onJsBack(Object obj, String str) {
        super.onJsBack(obj, str);
    }

    private static void a(String str) {
        if (bno.a) {
            ku.a().c("cruise", str);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        int b2 = nk.b(this.c);
        a("updateCameraLayer: showLayer: flag=".concat(String.valueOf(b2)));
        if (b2 == 1) {
            awo awo = (awo) a.a.a(awo.class);
            if (awo != null) {
                a((String) "updateCameraLayer: showLayer: success");
                awo.b(600010);
            }
            return;
        }
        awo awo2 = (awo) a.a.a(awo.class);
        if (awo2 != null) {
            a((String) "updateCameraLayer: showLayer: success");
            awo2.c(600010);
        }
    }
}
