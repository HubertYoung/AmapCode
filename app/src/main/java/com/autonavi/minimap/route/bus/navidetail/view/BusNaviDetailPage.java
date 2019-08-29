package com.autonavi.minimap.route.bus.navidetail.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.bundle.busnavi.ajx.ModuleBus;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Callout;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxLifeCircleListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory.OnDialogClickListener;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.util.Iterator;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class BusNaviDetailPage extends Ajx3Page implements bgm, IVoiceCmdResponder, com.autonavi.common.impl.Locator.a, launchModeSingleTask, AjxLifeCircleListener, defpackage.dws.a {
    private int a = 2;
    /* access modifiers changed from: private */
    public dxo b;
    private dxk c;
    private View d;
    /* access modifiers changed from: private */
    public boolean e;
    private c f;
    private float g;
    /* access modifiers changed from: private */
    public boolean h = false;
    /* access modifiers changed from: private */
    public boolean i = false;
    private MapInteractiveRelativeLayout j;
    /* access modifiers changed from: private */
    public ctl k;
    private a l;

    public static class a {
        float a;
        float b;
        float c;
        int d;
        int e;
        GeoPoint f = new GeoPoint();

        protected a() {
        }
    }

    public void finishSelf() {
    }

    public String getAjx3Url() {
        return "path://amap_bundle_busnavi/src/components/detail_page/BusPathDetailPage.page.js";
    }

    public long getScene() {
        return 32768;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 32768;
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

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
    }

    public void onContentViewCreated(View view) {
        this.d = view;
        view.setLayoutParams(new LayoutParams(-1, -1));
    }

    public void onCreate(Context context) {
        dxn dxn;
        eao.d("1.2---BusNaviPage onCreate");
        super.onCreate(context);
        ((b) this.mPresenter).a(getArguments());
        setContentView(R.layout.route_bus_ride_remind_fragment);
        ((ViewGroup) getContentView()).addView(this.d);
        eao.d("1.2.1---BusNaviPage onCreate");
        requestScreenOrientation(1);
        this.j = (MapInteractiveRelativeLayout) getContentView().findViewById(R.id.mapInteractiveRelativeLayout);
        if (((b) this.mPresenter).d()) {
            dxn = new dxl();
        } else {
            dxn = new dxm(((b) this.mPresenter).e());
        }
        this.b = new dxp(getSuspendWidgetHelper(), dxn, getSuspendManager(), getMapManager());
        this.b.a((defpackage.dxo.a) new defpackage.dxo.a() {
            public final void a() {
                BusNaviDetailPage.d(BusNaviDetailPage.this).a(false);
            }
        });
        this.b.a((c) new c() {
            public final void a(boolean z) {
                BusNaviDetailPage.this.e = z;
            }
        });
        this.b.a((b) new b() {
            public final void a() {
                BusNaviDetailPage.d(BusNaviDetailPage.this).a();
            }
        });
        this.c = new dxk();
        if (euk.a()) {
            this.j.setPadding(this.j.getPaddingLeft(), this.j.getPaddingTop() + euk.a(getContext()), this.j.getPaddingRight(), this.j.getPaddingBottom());
        }
        eao.d("1.3.0---BusNaviPage loadAjxView");
        if (this.mAjxView != null) {
            this.mAjxView.onAjxContextCreated(new Callback<AmapAjxView>() {
                public void callback(AmapAjxView amapAjxView) {
                    if (BusNaviDetailPage.this.isAlive()) {
                        if (BusNaviDetailPage.this.mAjxView.getAjxContext() == null) {
                            eao.a((String) "BusRideDetail#", (String) "BusRideRemindPage AJX load fail / AJX context is null");
                        } else {
                            BusNaviDetailPage.d(BusNaviDetailPage.this).a((ModuleBus) BusNaviDetailPage.this.mAjxView.getJsModule(ModuleBus.MODULE_NAME));
                        }
                    }
                }

                public void error(Throwable th, boolean z) {
                    StringBuilder sb = new StringBuilder("BusRideRemindPage AJX load fail / ");
                    sb.append((th == null || th.getLocalizedMessage() == null) ? "null" : th.getLocalizedMessage());
                    eao.a((String) "BusRideDetail#", sb.toString());
                }
            });
            this.mAjxView.setAttributeListener(new AttributeListener() {
                public final boolean handleAttr(String str, Object obj) {
                    if ("FADE_FROM_BOTTOM".equalsIgnoreCase(str)) {
                        BusNaviDetailPage.this.b.a(((Float) obj).floatValue());
                        eao.b("BusNaviDetailPage", "initAjxView value = ".concat(String.valueOf(obj)));
                        return true;
                    } else if (!"DP_BUS_BOTTOM_HEIGHT".equalsIgnoreCase(str)) {
                        return false;
                    } else {
                        BusNaviDetailPage.this.a(((Float) obj).floatValue());
                        return true;
                    }
                }
            });
        }
        if (!((b) this.mPresenter).e()) {
            this.k = (ctl) defpackage.esb.a.a.a(ctl.class);
            if (this.k != null) {
                this.k.a("14", new Callback<ctm>() {
                    public void error(Throwable th, boolean z) {
                    }

                    public void callback(final ctm ctm) {
                        aho.a(new Runnable() {
                            public final void run() {
                                if (ctm != null && BusNaviDetailPage.this.isStarted()) {
                                    BusNaviDetailPage.this.k.a(BusNaviDetailPage.this, "14", ctm.c);
                                }
                            }
                        }, 1000);
                    }
                });
            }
        }
        if (this.f == null) {
            this.f = new c() {
                public final void a() {
                }

                public final void c() {
                }

                public final void b() {
                    if (BusNaviDetailPage.this.h) {
                        BusNaviDetailPage.d(BusNaviDetailPage.this).b();
                        if (!BusNaviDetailPage.this.i) {
                            BusNaviDetailPage.this.i = true;
                            String a2 = lo.a().a((String) "bus_navi_config");
                            if (!TextUtils.isEmpty(a2)) {
                                try {
                                    JSONObject jSONObject = new JSONObject(a2);
                                    if (jSONObject.has("broadcast") && 1 == jSONObject.optInt("broadcast")) {
                                        PlaySoundUtils.getInstance().playSound(BusNaviDetailPage.this.getString(R.string.notification_title_bus_navi));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            };
        }
        drm.a((defpackage.drn.a) this.f);
        this.l = new a();
        bty mapView = getMapManager().getMapView();
        cde suspendManager = getSuspendManager();
        MapManager mapManager = getMapManager();
        if (!(suspendManager == null || mapManager == null || mapManager.getMapView() == null)) {
            this.l.a = mapView.I();
            this.l.b = mapView.v();
            this.l.c = mapView.J();
            this.l.f = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            this.l.d = mapView.h();
            this.l.e = mapView.i();
        }
    }

    public Ajx3PagePresenter createPresenter() {
        return new dxd(this);
    }

    public View getMapSuspendView() {
        View b2 = this.b.b();
        if (((b) this.mPresenter).d()) {
            this.a = 3;
        } else {
            this.a = 2;
            this.b.a(0.0f);
        }
        h();
        a(false);
        return b2;
    }

    public void resume() {
        eao.d("1.4---BusNaviPage onPresenterStart");
        ebo.a(this);
        this.ajxPageStateInvoker.setResumeData(((b) this.mPresenter).c());
        super.resume();
        if (((b) this.mPresenter).d()) {
            bqx bqx = (bqx) ank.a(bqx.class);
            if (bqx != null) {
                bqx.a(false, this.e, false, getMapManager(), getContext());
            }
        } else if (this.b != null) {
            this.b.a(this.e);
        }
        if (getMapManager() != null && getMapManager().getMapView() != null) {
            bty mapView = getMapManager().getMapView();
            ebf.a(mapView, mapView.p(false), mapView.ae(), 2);
            mapView.a(true);
        }
    }

    public void onPageCover() {
        if (this.k != null) {
            this.k.a("14");
        }
    }

    public void destroy() {
        super.destroy();
        if (this.b != null) {
            this.b.a();
        }
        if (this.c != null) {
            Iterator<Entry<String, Dialog>> it = this.c.a.entrySet().iterator();
            while (it.hasNext()) {
                ((Dialog) it.next().getValue()).dismiss();
                it.remove();
            }
        }
        if (!(getMapManager() == null || getMapView() == null)) {
            bty mapView = getMapManager().getMapView();
            if (mapView != null) {
                mapView.ab();
                if (!(this.l.f == null || this.l.f.x == 0 || this.l.f.y == 0)) {
                    mapView.a(this.l.f.x, this.l.f.y);
                }
                mapView.e(this.l.a);
                mapView.d(this.l.b);
                mapView.g(this.l.c);
                mapView.b(this.l.d, this.l.e);
                ebf.a(mapView, mapView.p(false), mapView.ae(), 0);
                bqx bqx = (bqx) ank.a(bqx.class);
                if (bqx != null) {
                    boolean a2 = bqx.a();
                    if (a2 != mapView.s()) {
                        bqx.a(a2, false, getMapManager(), getContext());
                    }
                }
            }
        }
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a(true);
        }
        drm.b((defpackage.drn.a) this.f);
    }

    public boolean backPressed() {
        if (!hasViewLayer()) {
            return super.backPressed();
        }
        dismissAllViewLayers();
        return true;
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equalsIgnoreCase("top")) {
                this.a = 1;
            } else if (str.equalsIgnoreCase(Callout.CALLOUT_TEXT_ALIGN_CENTER)) {
                this.a = 2;
            } else if (str.equalsIgnoreCase("bottom")) {
                this.a = 3;
            }
            h();
        }
    }

    public final boolean d() {
        return isAlive();
    }

    public final Activity e() {
        return getActivity();
    }

    public final void a(boolean z, String str, @Nullable OnDialogClickListener onDialogClickListener) {
        if (this.c != null) {
            if (z) {
                dxk dxk = this.c;
                Activity activity = getActivity();
                Dialog dialog = dxk.a.get(str);
                if (dialog == null || !dialog.isShowing()) {
                    Dialog a2 = BusNaviDetailDialogFactory.a(activity, str, new OnDialogClickListener(str, onDialogClickListener) {
                        final /* synthetic */ String a;
                        final /* synthetic */ OnDialogClickListener b;

                        {
                            this.a = r2;
                            this.b = r3;
                        }

                        public final void a(int i) {
                            dxk.this.a(this.a);
                            if (this.b != null) {
                                this.b.a(i);
                            }
                        }
                    });
                    dxk.a.put(str, a2);
                    if (a2 != null) {
                        a2.show();
                    }
                }
                return;
            }
            this.c.a(str);
        }
    }

    public final View f() {
        return getContentView();
    }

    public final void a(boolean z) {
        if (this.b != null) {
            if (z) {
                this.b.b(7);
            } else {
                this.b.c(7);
            }
        }
    }

    public final void b(boolean z) {
        this.h = z;
    }

    public final void a(Class<? extends bid> cls, PageBundle pageBundle, int i2) {
        startPageForResult(cls, pageBundle, i2);
    }

    public final void b() {
        finish();
    }

    public final void a(float f2) {
        if (this.j != null) {
            if (this.g != f2) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.j.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, DimensionUtils.standardUnitToPixel(getContext(), f2));
                this.j.setLayoutParams(layoutParams);
            }
            this.g = f2;
        }
    }

    public final PageBundle g() {
        return (PageBundle) getResult().second;
    }

    private void h() {
        if (this.b != null) {
            if (this.a == 3) {
                this.b.a(0);
            } else {
                this.b.a(8);
            }
        }
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    public final boolean a() {
        return this.h;
    }

    public final void c() {
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(getClass(), AgroupScenes.BusRideRemind, getArguments(), false);
        }
    }

    public void loadJs() {
        String c2 = ((b) this.mPresenter).c();
        eao.d("1.3.1---BusNaviPage loadAjxView");
        this.mAjxView.loadJsWithFullScreen("path://amap_bundle_busnavi/src/components/detail_page/BusPathDetailPage.page.js", c2, "BusPathDetail", "path://amap_bundle_busnavi/src/components/detail_page/bus_detail_preload.js");
        eao.d("1.3.2---BusNaviPage loadAjxView");
    }

    static /* synthetic */ b d(BusNaviDetailPage busNaviDetailPage) {
        return (b) busNaviDetailPage.mPresenter;
    }
}
