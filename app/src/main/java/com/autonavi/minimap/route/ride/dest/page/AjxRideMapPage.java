package com.autonavi.minimap.route.ride.dest.page;

import android.content.Context;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.amap.bundle.voiceservice.dispatch.IVoiceRideDispatcher;
import com.autonavi.bundle.rideresult.ajx.ModuleRide;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.model.Coord2D;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.model.RoutePOIInfo;
import com.autonavi.jni.eyrie.amap.tbt.model.RouteWayPoint;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxLifeCircleListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.ajx.inter.OnEndPoiChangeInterface;
import com.autonavi.minimap.route.ajx.inter.OnErrorReportClickInterface;
import com.autonavi.minimap.route.ajx.inter.OnNotifyCalcRouteListener;
import com.autonavi.minimap.route.ajx.inter.RouteResultSuccessInterface;
import com.autonavi.minimap.route.ajx.inter.RouteRideShowEleAnimInterface;
import com.autonavi.minimap.route.ajx.inter.UnLockGpsButtonInterface;
import com.autonavi.minimap.route.ajx.module.ModuleValues;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.uc.webview.export.internal.SDKFactory;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxRideMapPage extends Ajx3Page implements bgm, IVoiceCmdResponder, launchModeSingleTask, AjxLifeCircleListener, OnEndPoiChangeInterface, OnErrorReportClickInterface, OnNotifyCalcRouteListener, RouteResultSuccessInterface, RouteRideShowEleAnimInterface, UnLockGpsButtonInterface, a {
    public ModuleRide a;
    private MapSharePreference b;
    /* access modifiers changed from: private */
    public View c;
    private View d;
    private MvpImageView e;
    private ScaleView f;
    private GPSButton g;
    private LayoutParams h;
    private LayoutParams i;
    private AGroupSuspendView j;
    private int k;
    private boolean l = false;
    private boolean m;
    private boolean n = true;
    /* access modifiers changed from: private */
    public eew o;
    /* access modifiers changed from: private */
    public aia p;
    private View q;
    private aii r = new aii() {
        public final void a(int i) {
            if (AjxRideMapPage.this.p != null) {
                aho.a(new Runnable() {
                    public final void run() {
                        if (AjxRideMapPage.this.o != null && AjxRideMapPage.this.o.isShowing()) {
                            AjxRideMapPage.this.o.dismiss();
                        }
                    }
                });
                if (AjxRideMapPage.this.p.a(AjxRideMapPage.e(AjxRideMapPage.this), (String) "startNavi", i)) {
                    return;
                }
            }
            AjxRideMapPage.a(i);
        }
    };
    private coi s = new coi() {
        public final void doReportError(String str) {
            AjxRideMapPage.this.a(str, true);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("name", AjxRideMapPage.this.getString(R.string.ride_map_page_feedback_log_msg));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2(LogConstant.PAGE_ID_FEEDBACK_ENTRANCE, "B001", jSONObject);
        }
    };

    public void finishSelf() {
    }

    public String getAjx3Url() {
        return ModuleValues.URL_RIDE_ROUTE;
    }

    public long getScene() {
        return IjkMediaMeta.AV_CH_TOP_BACK_CENTER;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return IjkMediaMeta.AV_CH_TOP_BACK_CENTER;
    }

    public boolean handleSetContentView() {
        return true;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public edm createPresenter() {
        return new edm(this);
    }

    public void onContentViewCreated(View view) {
        this.q = view;
        this.q.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.ajx_ride_result_map_fragment);
        ebn.a(getActivity().getWindow().getDecorView());
        requestScreenOrientation(1);
        this.b = new MapSharePreference((String) "SharedPreferences");
        bty mapView = getMapView();
        if (mapView != null) {
            this.l = mapView.ai();
        }
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(getClass(), AgroupScenes.DestMap, getArguments(), false);
        }
        IVoiceRideDispatcher iVoiceRideDispatcher = (IVoiceRideDispatcher) ank.a(IVoiceRideDispatcher.class);
        if (iVoiceRideDispatcher != null) {
            iVoiceRideDispatcher.setRideVoiceListener(this.r);
        }
    }

    public void onAjxViewCreated(AmapAjxView amapAjxView) {
        amapAjxView.setAttributeListener(new AttributeListener() {
            public final boolean handleAttr(String str, Object obj) {
                if ("RIDE_FADE_FROM_BOTTOM".equalsIgnoreCase(str)) {
                    Float f = (Float) obj;
                    AjxRideMapPage.this.c.setAlpha(f.floatValue());
                    if (AjxRideMapPage.b(AjxRideMapPage.this).a() == 102) {
                        AjxRideMapPage.a(AjxRideMapPage.this, f.floatValue());
                    }
                    return true;
                } else if (!"RIDE_HEIGHT_TO_TOP".equalsIgnoreCase(str)) {
                    return false;
                } else {
                    if (AjxRideMapPage.b(AjxRideMapPage.this).a() != 102) {
                        AjxRideMapPage.b(AjxRideMapPage.this, ((Float) obj).floatValue());
                    }
                    return true;
                }
            }
        });
    }

    public void resume() {
        super.resume();
        bty mapView = getMapManager() != null ? getMapManager().getMapView() : null;
        if (mapView != null) {
            if (11 != mapView.o(false)) {
                ebf.a(mapView, mapView.p(false), mapView.ae(), 11);
            }
            mapView.b(false);
        }
        if (!this.m) {
            ((edm) this.mPresenter).a.a(this.q);
            this.m = true;
        }
        this.q.setVisibility(0);
        if (this.n) {
            if (ebm.g()) {
                if (!(this.b != null && this.b.getBooleanValue("is_shown_electric_guide", false)) && ((edm) this.mPresenter).a() != 102) {
                    if (this.o == null) {
                        this.o = new eew(getActivity(), this);
                        this.o.show();
                    }
                    if (this.b != null) {
                        this.b.putBooleanValue("is_shown_electric_guide", true);
                    }
                }
            }
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("IsShowCompassTip", false);
            this.n = false;
        }
    }

    public void pause() {
        super.pause();
        this.q.setVisibility(8);
    }

    public void destroy() {
        super.destroy();
        if (this.j != null) {
            this.j.destroy();
        }
        IVoiceRideDispatcher iVoiceRideDispatcher = (IVoiceRideDispatcher) ank.a(IVoiceRideDispatcher.class);
        if (iVoiceRideDispatcher != null) {
            iVoiceRideDispatcher.setRideVoiceListener(null);
        }
        if (this.m) {
            ((edm) this.mPresenter).a.b(this.q);
            this.m = false;
        }
        if (this.a != null) {
            this.a.setEndPoiChangeListener(null);
            this.a.setEleRemindAnimListener(null);
            this.a.setUnLockGpsBtnListener(null);
            this.a.setOnErrorReportClickListener(null);
            this.a.setOnNotifyCalcRouteListener(null);
            this.a.setOnRouteResultSuccessListener(null);
            this.a = null;
        }
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.q(this.l);
            ebf.a(mapView, mapView.j(false), 0, 0);
        }
    }

    public void onPageCover() {
        super.onPageCover();
        ((edm) this.mPresenter).b();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0156  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x015c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x016f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getMapSuspendView() {
        /*
            r8 = this;
            ccy r0 = r8.getSuspendWidgetHelper()
            ccv r1 = new ccv
            android.content.Context r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            r1.<init>(r2)
            com.autonavi.map.fragmentcontainer.page.IPresenter r2 = r8.mPresenter
            edm r2 = (defpackage.edm) r2
            int r2 = r2.a()
            r3 = 102(0x66, float:1.43E-43)
            if (r2 == r3) goto L_0x003e
            android.view.View r2 = r0.a()
            android.widget.LinearLayout$LayoutParams r4 = r0.c()
            r5 = 1
            r1.addWidget(r2, r4, r5)
            android.widget.LinearLayout$LayoutParams r2 = r0.m()
            android.content.Context r4 = r8.getContext()
            r5 = 1065353216(0x3f800000, float:1.0)
            int r4 = defpackage.agn.a(r4, r5)
            int r4 = -r4
            r2.bottomMargin = r4
            com.autonavi.map.suspend.refactor.zoom.ZoomView r4 = r0.l()
            r5 = 6
            r1.addWidget(r4, r2, r5)
        L_0x003e:
            com.autonavi.map.suspend.refactor.scale.ScaleView r2 = r0.f()
            r8.f = r2
            android.widget.LinearLayout$LayoutParams r2 = r0.g()
            r8.h = r2
            com.autonavi.map.suspend.refactor.scale.ScaleView r2 = r8.f
            android.widget.LinearLayout$LayoutParams r4 = r8.h
            r5 = 7
            r1.addWidget(r2, r4, r5)
            com.autonavi.map.suspend.refactor.gps.GPSButton r2 = r0.d()
            r8.g = r2
            android.widget.LinearLayout$LayoutParams r2 = r8.c()
            r8.i = r2
            android.widget.LinearLayout$LayoutParams r2 = r8.i
            android.content.Context r4 = r8.getContext()
            r5 = 1082130432(0x40800000, float:4.0)
            int r4 = defpackage.agn.a(r4, r5)
            r2.leftMargin = r4
            android.widget.LinearLayout$LayoutParams r2 = r8.i
            android.content.Context r4 = r8.getContext()
            r6 = 1077936128(0x40400000, float:3.0)
            int r4 = defpackage.agn.a(r4, r6)
            r2.bottomMargin = r4
            com.autonavi.map.suspend.refactor.gps.GPSButton r2 = r0.d()
            r0.a(r2)
            android.view.View r2 = r1.getSuspendView()
            com.autonavi.map.suspend.refactor.gps.GPSButton r4 = r8.g
            android.widget.LinearLayout$LayoutParams r6 = r8.i
            r7 = 3
            r0.a(r2, r4, r6, r7)
            com.autonavi.map.core.view.MvpImageView r0 = new com.autonavi.map.core.view.MvpImageView
            android.content.Context r2 = r8.getContext()
            r0.<init>(r2)
            r8.e = r0
            com.autonavi.map.core.view.MvpImageView r0 = r8.e
            android.widget.ImageView$ScaleType r2 = android.widget.ImageView.ScaleType.CENTER_INSIDE
            r0.setScaleType(r2)
            com.autonavi.map.core.view.MvpImageView r0 = r8.e
            int r2 = com.autonavi.minimap.R.drawable.icon_c18_selector
            r0.setImageResource(r2)
            com.autonavi.map.core.view.MvpImageView r0 = r8.e
            int r2 = com.autonavi.minimap.R.drawable.rt_bus_around_refresh_bg_selector
            r0.setBackgroundResource(r2)
            com.autonavi.map.core.view.MvpImageView r0 = r8.e
            java.lang.String r2 = "报错"
            r0.setContentDescription(r2)
            android.widget.LinearLayout$LayoutParams r0 = r8.c()
            android.content.Context r2 = r8.getContext()
            int r2 = defpackage.agn.a(r2, r5)
            r0.rightMargin = r2
            com.autonavi.map.core.view.MvpImageView r2 = r8.e
            r4 = 4
            r1.addWidget(r2, r0, r4)
            com.autonavi.map.core.view.MvpImageView r0 = r8.e
            r2 = 0
            if (r0 == 0) goto L_0x00e5
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r8.mPresenter
            edm r0 = (defpackage.edm) r0
            int r0 = r0.a()
            if (r0 != r3) goto L_0x00e0
            com.autonavi.map.core.view.MvpImageView r0 = r8.e
            r6 = 8
            r0.setVisibility(r6)
            goto L_0x00e5
        L_0x00e0:
            com.autonavi.map.core.view.MvpImageView r0 = r8.e
            r0.setVisibility(r2)
        L_0x00e5:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<cuh> r6 = defpackage.cuh.class
            esc r0 = r0.a(r6)
            cuh r0 = (defpackage.cuh) r0
            if (r0 == 0) goto L_0x00fe
            cug r6 = r0.c()
            if (r6 == 0) goto L_0x00fe
            boolean r6 = r6.c()
            goto L_0x00ff
        L_0x00fe:
            r6 = 0
        L_0x00ff:
            if (r6 == 0) goto L_0x013b
            com.autonavi.map.fragmentcontainer.page.IPresenter r6 = r8.mPresenter
            edm r6 = (defpackage.edm) r6
            int r6 = r6.a()
            if (r6 != r3) goto L_0x010c
            goto L_0x013b
        L_0x010c:
            com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView r6 = new com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView
            android.content.Context r7 = r8.getContext()
            r6.<init>(r7)
            r8.j = r6
            android.widget.LinearLayout$LayoutParams r6 = r8.c()
            android.content.Context r7 = r8.getContext()
            int r5 = defpackage.agn.a(r7, r5)
            r6.rightMargin = r5
            com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView r5 = r8.j
            int r7 = com.autonavi.minimap.R.drawable.icon_c_bg_single
            r5.setBackgroundResource(r7)
            com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView r5 = r8.j
            r1.addWidget(r5, r6, r4)
            com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView r5 = r8.j
            com.autonavi.minimap.route.ride.dest.page.AjxRideMapPage$5 r6 = new com.autonavi.minimap.route.ride.dest.page.AjxRideMapPage$5
            r6.<init>(r0)
            r5.setOnEntryEventListener(r6)
        L_0x013b:
            com.autonavi.map.core.view.MvpImageView r0 = r8.e
            com.autonavi.minimap.route.ride.dest.page.AjxRideMapPage$3 r5 = new com.autonavi.minimap.route.ride.dest.page.AjxRideMapPage$3
            r5.<init>()
            r0.setOnClickListener(r5)
            android.view.View r0 = r1.getSuspendView()
            r8.c = r0
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r8.mPresenter
            edm r0 = (defpackage.edm) r0
            int r0 = r0.a()
            if (r0 != r3) goto L_0x0156
            goto L_0x015a
        L_0x0156:
            boolean r2 = defpackage.ebm.g()
        L_0x015a:
            if (r2 == 0) goto L_0x016f
            android.content.Context r0 = r8.getContext()
            r1 = 1109131264(0x421c0000, float:39.0)
            int r0 = defpackage.agn.a(r0, r1)
            android.widget.LinearLayout$LayoutParams r1 = r8.i
            r1.bottomMargin = r0
            android.widget.LinearLayout$LayoutParams r1 = r8.h
            r1.bottomMargin = r0
            goto L_0x0177
        L_0x016f:
            android.widget.LinearLayout$LayoutParams r0 = r8.i
            r0.bottomMargin = r4
            android.widget.LinearLayout$LayoutParams r0 = r8.h
            r0.bottomMargin = r4
        L_0x0177:
            com.autonavi.map.suspend.refactor.gps.GPSButton r0 = r8.g
            r0.requestLayout()
            com.autonavi.map.suspend.refactor.scale.ScaleView r0 = r8.f
            r0.requestLayout()
            android.view.View r0 = r8.c
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.ride.dest.page.AjxRideMapPage.getMapSuspendView():android.view.View");
    }

    public void unLockGpsButtonState() {
        cde suspendManager = getSuspendManager();
        if (suspendManager != null) {
            cdz d2 = suspendManager.d();
            if (d2 != null) {
                d2.f();
            }
        }
    }

    private LayoutParams c() {
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        return new LayoutParams(dimensionPixelSize, dimensionPixelSize);
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.setWeatherData(str);
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, boolean z) {
        if (this.a != null) {
            PageBundle b2 = ebc.b(getContext(), str, this.a.getErrorReportData());
            if (z) {
                b2.putInt("sourcepage", 30);
            } else {
                b2.putInt("sourcepage", 31);
            }
            b2.putInt("page_id", 24);
            b2.putInt("route_line_type", 1);
            IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
            if (iErrorReportStarter != null) {
                iErrorReportStarter.startFeedback(b2);
            }
        }
    }

    public void onEndPoiChangeListener(POI poi) {
        if (this.mPresenter != null) {
            edm edm = (edm) this.mPresenter;
            if (edm.b != null && edm.b.i() == RouteType.RIDE) {
                edm.b.b(RouteType.RIDE, poi);
                edm.c = poi.clone();
            }
        }
    }

    public final void a() {
        edr.a(1);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "ddc");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00273", "B006", jSONObject);
        aho.a(new Runnable() {
            public final void run() {
                if (AjxRideMapPage.this.isAlive() && AjxRideMapPage.this.a != null) {
                    AjxRideMapPage.this.a.requestRideRoute(AjxRideMapPage.b(AjxRideMapPage.this).a(true));
                }
            }
        }, 300);
    }

    public void showEleRemindAnimation() {
        final eex eex = new eex(this, (ImageView) findViewById(R.id.ele_remind_anim_imgview));
        aho.a(new Runnable() {
            public final void run() {
                if (AjxRideMapPage.this.isAlive()) {
                    eex eex = eex;
                    if (eex.b != null && eex.a != null && !edr.c() && edr.a() != 0) {
                        Context appContext = AMapPageUtil.getAppContext();
                        int i = R.raw.ele_remind_sound;
                        SoundPool soundPool = new SoundPool(5, 3, 5);
                        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener(soundPool.load(appContext, i, 1)) {
                            final /* synthetic */ int a;

                            {
                                this.a = r2;
                            }

                            public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                                soundPool.play(this.a, 1.0f, 1.0f, 1, 0, 1.0f);
                            }
                        });
                        eex.a.setVisibility(0);
                        int width = ags.a(AMapPageUtil.getAppContext()).width();
                        TranslateAnimation translateAnimation = new TranslateAnimation((float) (-width), 0.0f, 0.0f, 0.0f);
                        translateAnimation.setFillAfter(true);
                        translateAnimation.setDuration(500);
                        eex.a.startAnimation(translateAnimation);
                        translateAnimation.setAnimationListener(new AnimationListener(width) {
                            final /* synthetic */ int a;

                            public final void onAnimationRepeat(Animation animation) {
                            }

                            public final void onAnimationStart(Animation animation) {
                            }

                            {
                                this.a = r2;
                            }

                            public final void onAnimationEnd(Animation animation) {
                                aho.a(new Runnable() {
                                    public final void run() {
                                        if (eex.this.b != null && eex.this.b.isAlive() && eex.this.a != null) {
                                            eex eex = eex.this;
                                            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, (float) AnonymousClass1.this.a, 0.0f, 0.0f);
                                            translateAnimation.setFillAfter(true);
                                            translateAnimation.setDuration(500);
                                            eex.a.startAnimation(translateAnimation);
                                            translateAnimation.setAnimationListener(new AnimationListener() {
                                                public final void onAnimationRepeat(Animation animation) {
                                                }

                                                public final void onAnimationStart(Animation animation) {
                                                }

                                                public final void onAnimationEnd(Animation animation) {
                                                    if (eex.this.a != null) {
                                                        eex.this.a.setVisibility(8);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }, 2000);
                            }
                        });
                        edr.b();
                    }
                }
            }
        }, 300);
    }

    public void onRouteResultSuccess(String str) {
        GeoPoint geoPoint;
        unLockGpsButtonState();
        edm edm = (edm) this.mPresenter;
        if (edm.b == null || edm.b.f() == null) {
            geoPoint = LocationInstrument.getInstance().getLatestPosition();
        } else {
            geoPoint = edm.b.f().getPoint();
        }
        eco.a(geoPoint.getLongitude(), geoPoint.getLatitude(), new a() {
            public final void a(String str) {
                edm.d(edm.this).a(str);
            }

            public final void a() {
                edm.d(edm.this).a((String) "");
            }
        });
        a(true);
    }

    public void onRouteResultFail() {
        a(false);
    }

    public void onErrorReportClickBtn(String str) {
        a(str, false);
    }

    public void onCalcRoute() {
        int i2;
        int i3 = edr.a() == 0 ? 1 : 3;
        if (((edm) this.mPresenter).a() == 102) {
            i3 = 1;
        }
        POI d2 = ((edm) this.mPresenter).a.d();
        POI f2 = ((edm) this.mPresenter).a.f();
        if (d2 != null && f2 != null) {
            int i4 = 0;
            try {
                i2 = Integer.parseInt(d2.getIndoorFloorNoName());
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                i2 = 0;
            }
            try {
                i4 = Integer.parseInt(f2.getIndoorFloorNoName());
            } catch (NumberFormatException e3) {
                e3.printStackTrace();
            }
            GeoPoint point = ((edm) this.mPresenter).a.d().getPoint();
            GeoPoint point2 = ((edm) this.mPresenter).a.f().getPoint();
            String name = ((edm) this.mPresenter).a.d().getName();
            String name2 = ((edm) this.mPresenter).a.f().getName();
            RouteWayPoint routeWayPoint = new RouteWayPoint();
            RoutePOIInfo routePOIInfo = new RoutePOIInfo();
            Coord2D coord2D = new Coord2D();
            coord2D.lon = point.getLongitude();
            coord2D.lat = point.getLatitude();
            routePOIInfo.realPos = coord2D;
            routePOIInfo.name = name;
            routePOIInfo.floor = i2;
            routePOIInfo.poiID = d2.getId();
            routePOIInfo.parentID = d2.getPid();
            routePOIInfo.type = ebm.a(d2);
            routeWayPoint.start.add(routePOIInfo);
            RoutePOIInfo routePOIInfo2 = new RoutePOIInfo();
            Coord2D coord2D2 = new Coord2D();
            coord2D2.lon = point2.getLongitude();
            coord2D2.lat = point2.getLatitude();
            routePOIInfo2.realPos = coord2D2;
            routePOIInfo2.name = name2;
            routePOIInfo2.floor = i4;
            routePOIInfo2.poiID = f2.getId();
            routePOIInfo2.parentID = f2.getPid();
            routePOIInfo2.type = ebm.a(f2);
            routeWayPoint.end.add(routePOIInfo2);
            byte[] bArr = null;
            PageBundle arguments = ((Ajx3Page) ((edm) this.mPresenter).mPage).getArguments();
            if (arguments != null) {
                bArr = arguments.getByteArray("original_route");
            }
            NaviManager.calcRouteFromDataNew(i3, routeWayPoint, bArr);
        }
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        if (isAlive() && iAjxContext != null) {
            this.a = (ModuleRide) this.mAjxView.getJsModule(ModuleRide.MODULE_NAME);
            if (this.a != null) {
                this.a.setEndPoiChangeListener(this);
                this.a.setEleRemindAnimListener(this);
                this.a.setUnLockGpsBtnListener(this);
                this.a.setOnErrorReportClickListener(this);
                this.a.setOnNotifyCalcRouteListener(this);
                this.a.setOnRouteResultSuccessListener(this);
                this.p = (aia) a.a.a(aia.class);
            }
        }
    }

    public void onJsBack(Object obj, String str) {
        if (isAlive()) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, obj);
            setResult(ResultType.OK, pageBundle);
            if (((edm) this.mPresenter).a instanceof AbstractBasePresenter) {
                ((AbstractBasePresenter) ((edm) this.mPresenter).a).onBackPressed();
            } else {
                finish();
            }
        }
    }

    private void a(boolean z) {
        PageBundle arguments = getArguments();
        int i2 = -1;
        if (arguments != null) {
            i2 = arguments.getInt("bundle_key_token", -1);
        }
        if (a.a.b) {
            if (z) {
                d.a.a(i2, 10000, (String) null);
            } else {
                d.a.a(i2, (int) SDKFactory.getCoreType, (String) null);
            }
            a.a.b = false;
        }
    }

    public void loadJs() {
        this.mAjxView.load(ModuleValues.URL_RIDE_ROUTE, ((edm) this.mPresenter).a(true), "RIDE_MAP_RESULT");
    }

    public bgo getPresenter() {
        return (edm) this.mPresenter;
    }

    static /* synthetic */ edm b(AjxRideMapPage ajxRideMapPage) {
        return (edm) ajxRideMapPage.mPresenter;
    }

    static /* synthetic */ void a(AjxRideMapPage ajxRideMapPage, float f2) {
        if (ajxRideMapPage.d == null && ((edm) ajxRideMapPage.mPresenter).a != null) {
            ajxRideMapPage.d = ((edm) ajxRideMapPage.mPresenter).a.b();
        }
        if (ajxRideMapPage.d != null) {
            ajxRideMapPage.d.setAlpha(f2);
        }
    }

    static /* synthetic */ void b(AjxRideMapPage ajxRideMapPage, float f2) {
        if (((edm) ajxRideMapPage.mPresenter).a != null) {
            ajxRideMapPage.d = ((edm) ajxRideMapPage.mPresenter).a.c();
        }
        if (ajxRideMapPage.d != null) {
            if (ajxRideMapPage.k == 0) {
                ajxRideMapPage.k = ajxRideMapPage.d.getHeight();
            }
            LayoutParams layoutParams = (LayoutParams) ajxRideMapPage.d.getLayoutParams();
            double d2 = (double) f2;
            layoutParams.topMargin = -((int) ((d2 - 0.5d) * 3.3333332538604736d * ((double) ajxRideMapPage.k)));
            if (d2 < 0.5d) {
                layoutParams.topMargin = 0;
            } else if (d2 > 0.8d) {
                layoutParams.topMargin = -ajxRideMapPage.k;
            }
            layoutParams.bottomMargin = -layoutParams.topMargin;
            ajxRideMapPage.d.requestLayout();
        }
    }

    static /* synthetic */ IAjxContext e(AjxRideMapPage ajxRideMapPage) {
        if (ajxRideMapPage.mAjxView == null) {
            return null;
        }
        return ajxRideMapPage.mAjxView.getAjxContext();
    }

    static /* synthetic */ void a(int i2) {
        if (i2 != -1) {
            aia aia = (aia) a.a.a(aia.class);
            if (aia != null) {
                aia.a(i2, (int) SDKFactory.getCoreType, (Pair<String, Object>) null);
            }
        }
    }

    static /* synthetic */ void f(AjxRideMapPage ajxRideMapPage) {
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            iErrorReportStarter.doReportError(ajxRideMapPage.getMapManager(), ajxRideMapPage.s);
        }
    }
}
