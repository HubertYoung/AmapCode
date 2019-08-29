package com.autonavi.minimap.route.sharebike.page;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.sharebike.ajx.ModuleBicycle;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.LocationMode.LocationIgnore;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.order.param.BikeEndBillingRequest;
import com.autonavi.minimap.route.common.view.RouteViewGroup;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.model.ScanQrcode;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest;
import com.autonavi.minimap.route.sharebike.net.request.EndBillingRequest;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.minimap.route.sharebike.utils.ShareBikeLogin;
import com.autonavi.minimap.route.sharebike.utils.ShareBikeLogin.OpenPageType;
import com.autonavi.minimap.route.sharebike.view.ShareBikeRidingPopView;
import com.autonavi.minimap.route.sharebike.view.ShareBikeRidingPopView.a;
import com.autonavi.minimap.route.sharebike.view.ShareRidingDisplayTipView;
import com.autonavi.minimap.route.sharebike.view.ShareRidingDisplayTipView.ViewType;
import com.autonavi.minimap.route.sharebike.view.ShareRidingUnlockTipView;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@LocationPreference(availableOnBackground = true)
public class ShareRidingMapPage extends AbstractBaseMapPage<ehg> implements LocationIgnore, edd {
    public ehh a;
    public ShareRidingDisplayTipView b;
    public ShareRidingUnlockTipView c;
    public ShareBikeRidingPopView d;
    /* access modifiers changed from: 0000 */
    public View e;
    public RouteViewGroup f;
    public View g;
    /* access modifiers changed from: 0000 */
    public View h;
    public TipStyle i;
    public String j;
    public String k;
    public String l = "";
    private TextView m;
    private View n;
    private View o;
    private LayoutInflater p;
    /* access modifiers changed from: private */
    public int q;
    private float r;
    private int s = -1;
    private AlertView t;
    private CompatDialog u;

    public enum TipStyle {
        UNLOCKING,
        RIDING
    }

    public final void a() {
        if (this.c != null) {
            this.c.setTorch(false);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public ehg createPresenter() {
        return new ehg(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.route_sharebike_riding_page_layout);
        this.p = LayoutInflater.from(context);
        View contentView = getContentView();
        if (euk.a()) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + euk.a(getContext()), contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        this.b = (ShareRidingDisplayTipView) contentView.findViewById(R.id.share_bike_ride_display_tip);
        this.c = (ShareRidingUnlockTipView) contentView.findViewById(R.id.share_bike_ride_unlock_tip);
        this.e = contentView.findViewById(R.id.share_riding_header);
        this.m = (TextView) contentView.findViewById(R.id.share_bike_riding_title_text);
        this.n = contentView.findViewById(R.id.share_riding_back);
        this.o = contentView.findViewById(R.id.share_riding_setting);
        this.f = (RouteViewGroup) contentView.findViewById(R.id.share_riding_viewgroup);
        this.g = contentView.findViewById(R.id.share_bike_riding_bottom);
        this.h = contentView.findViewById(R.id.share_bike_riding_body);
        this.d = (ShareBikeRidingPopView) contentView.findViewById(R.id.share_bike_poping_selector);
        this.n.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ShareRidingMapPage.this.d();
            }
        });
        this.o.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (ShareRidingMapPage.this.i == TipStyle.UNLOCKING) {
                    ShareRidingMapPage.a((String) "B007", (JSONObject) null);
                } else if (ShareRidingMapPage.this.i == TipStyle.RIDING) {
                    ShareRidingMapPage.b("B010", null);
                }
                ShareRidingMapPage.this.d.showSelectView();
            }
        });
        this.d.setListener(new a() {
            public final void a() {
                ShareRidingMapPage.this.d.setVisibility(8);
                new ShareBikeLogin(new Object[0]).a(ShareRidingMapPage.this.getPageContext(), OpenPageType.PAGE_HISTORY, true);
                ((ehg) ShareRidingMapPage.this.mPresenter).c();
            }

            public final void b() {
                ShareRidingMapPage.this.d.setVisibility(8);
                new ShareBikeLogin(new Object[0]).a(ShareRidingMapPage.this.getPageContext(), OpenPageType.PAGE_WALLET_LIST, true);
                ((ehg) ShareRidingMapPage.this.mPresenter).c();
            }

            public final void c() {
                ShareRidingMapPage.this.d.setVisibility(8);
                ehc.a(4, null);
            }
        });
        this.f.setStatusListener(new RouteViewGroup.a() {
            public final void a() {
                if (ShareRidingMapPage.this.isAlive()) {
                    ShareRidingMapPage.this.h.setTranslationY((float) ShareRidingMapPage.this.h.getHeight());
                    ShareRidingMapPage.this.e.setTranslationY((float) (0 - ShareRidingMapPage.this.e.getHeight()));
                    ShareRidingMapPage shareRidingMapPage = ShareRidingMapPage.this;
                    ebv.a(shareRidingMapPage.e, shareRidingMapPage.h, new AnimatorListener() {
                        public final void onAnimationCancel(Animator animator) {
                        }

                        public final void onAnimationRepeat(Animator animator) {
                        }

                        public final void onAnimationStart(Animator animator) {
                            ShareRidingMapPage.this.e.setTranslationY((float) (0 - ShareRidingMapPage.this.e.getHeight()));
                        }

                        public final void onAnimationEnd(Animator animator) {
                            if (ShareRidingMapPage.this.e != null) {
                                ShareRidingMapPage.this.e.setVisibility(0);
                                ShareRidingMapPage.this.e.requestLayout();
                            }
                            if (ShareRidingMapPage.this.h != null) {
                                ShareRidingMapPage.this.h.setVisibility(0);
                                ShareRidingMapPage.this.h.requestLayout();
                            }
                        }
                    });
                }
            }
        });
        this.b.setOnFeeTipClickListener(new ShareRidingDisplayTipView.a() {
            public final void a() {
                if (ShareRidingMapPage.this.isAlive()) {
                    ShareRidingMapPage.a(ShareRidingMapPage.this, ShareRidingMapPage.this.getString(R.string.is_to_finish_share_machincal_riding), ShareRidingMapPage.this.getString(R.string.is_to_finish_share_machincal_confirm), ShareRidingMapPage.this.getString(R.string.share_bike_cancel), ShareRidingMapPage.this.getString(R.string.is_to_finish_share_machincal_riding_subtitle));
                }
            }

            public final void b() {
                ShareRidingMapPage.e();
                if (!TextUtils.equals("ofo", ShareRidingMapPage.this.j) || !ShareRidingMapPage.this.isAlive()) {
                    if (!TextUtils.isEmpty(ShareRidingMapPage.this.l)) {
                        aja aja = new aja(ShareRidingMapPage.this.l);
                        aja.b = new ehe(ShareRidingMapPage.this.getActivity());
                        ((ehg) ShareRidingMapPage.this.mPresenter).c();
                        ((ehg) ShareRidingMapPage.this.mPresenter).a = true;
                        aix aix = (aix) a.a.a(aix.class);
                        if (aix != null) {
                            aix.a((bid) ShareRidingMapPage.this, aja);
                        }
                    }
                    return;
                }
                ShareRidingMapPage.a(ShareRidingMapPage.this, ShareRidingMapPage.this.getString(R.string.is_to_finish_share_riding), ShareRidingMapPage.this.getString(R.string.share_bike_end), ShareRidingMapPage.this.getString(R.string.share_bike_back), "");
            }

            public final void c() {
                ShareRidingMapPage.b("B007", null);
                eao.e(ShareRidingMapPage.class.getName(), " onErrorReportClick  --- click");
                ShareRidingMapPage.n(ShareRidingMapPage.this);
            }
        });
        this.c.setOnUnlockingTipClickListener(new ShareRidingUnlockTipView.a() {
            public final void a(boolean z) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", z ? CameraParams.FLASH_MODE_ON : CameraParams.FLASH_MODE_OFF);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ShareRidingMapPage.a((String) "B003", jSONObject);
            }

            public final void a() {
                ShareRidingMapPage.a((String) "B001", (JSONObject) null);
                ShareRidingMapPage.n(ShareRidingMapPage.this);
            }
        });
        PageBundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey("CpSource")) {
                this.j = arguments.getString("CpSource");
            }
            if (arguments.containsKey("OrderId")) {
                this.k = arguments.getString("OrderId");
            }
        }
        if (TextUtils.isEmpty(this.j)) {
            this.j = ehs.b("share_bike_cp_source");
        }
        if (TextUtils.isEmpty(this.k)) {
            this.k = ehs.b("share_bike_order_id");
        }
    }

    public View getMapSuspendView() {
        this.a = new ehh(this);
        this.a.b.c = new a() {
            public final void a(boolean z) {
                if (ShareRidingMapPage.this.i == TipStyle.RIDING) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("type", z ? "2d" : "3d");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ShareRidingMapPage.b("B011", jSONObject);
                }
                ehg ehg = (ehg) ShareRidingMapPage.this.mPresenter;
                GeoPoint e2 = ehg.e();
                if (e2 != null) {
                    ((ShareRidingMapPage) ehg.mPage).getMapView().a(500, -9999.0f, z ? 0 : ehg.d(), z ? 0 : 39, e2.x, e2.y, false);
                }
            }
        };
        return this.a.a;
    }

    public final void a(int i2) {
        this.q = i2;
        this.r = (((float) this.q) * 189.0f) / 1000.0f;
        g();
    }

    public final void a(RideState rideState) {
        if (rideState == null || rideState.status != 1) {
            this.s = -1;
        } else {
            this.s = rideState.duration;
        }
        g();
    }

    public final void b() {
        if (getMapManager() != null) {
            bqx bqx = (bqx) ank.a(bqx.class);
            if (bqx != null) {
                bqx.a(false, false, false, getMapManager(), getContext());
            }
            bty mapView = getMapManager().getMapView();
            if (mapView != null) {
                if (12 != mapView.o(false)) {
                    ebf.a(mapView, mapView.p(false), mapView.ae(), 12);
                }
                float f2 = 0.0f;
                mapView.e(0.0f);
                if (!edr.a((String) "sharebikenavimodewithangle", true)) {
                    f2 = 39.0f;
                }
                mapView.g(f2);
            }
        }
    }

    public final void c() {
        if (getMapManager() != null) {
            bty mapView = getMapManager().getMapView();
            if (mapView != null) {
                mapView.z();
                mapView.B();
            }
        }
    }

    public final void b(RideState rideState) {
        if (this.b != null) {
            this.b.setData(rideState, true);
        }
    }

    public final void c(RideState rideState) {
        if (!(rideState == null || !rideState.result || this.b == null)) {
            this.b.updateElectronicTipText(rideState.faqDesc);
        }
        if (TextUtils.isEmpty(this.l) && rideState != null) {
            this.l = rideState.faqUrl;
        }
    }

    private void g() {
        if (isAlive() && this.b != null) {
            ebr.a(true).post(new Runnable() {
                public final void run() {
                    ShareRidingMapPage.this.b.updateDistanceView(ShareRidingMapPage.this.q);
                }
            });
        }
    }

    public final void d() {
        if (this.i == TipStyle.RIDING) {
            b("B012", null);
        }
        a();
        ebv.b(this.e, this.h, new AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationEnd(Animator animator) {
                if (((ehg) ShareRidingMapPage.this.mPresenter).e && !((ehg) ShareRidingMapPage.this.mPresenter).d) {
                    ShareRidingMapPage.this.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                } else if (((ehg) ShareRidingMapPage.this.mPresenter).b) {
                    ShareRidingMapPage.this.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                    bax bax = (bax) a.a.a(bax.class);
                    if (bax != null) {
                        bax.a((PageBundle) null);
                    }
                } else {
                    ShareRidingMapPage.this.finish();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void h() {
        if (this.u != null && this.u.isShowing()) {
            this.u.dismiss();
        }
    }

    public static void a(String str, JSONObject jSONObject) {
        if (jSONObject != null) {
            LogManager.actionLogV2("P00310", str, jSONObject);
        } else {
            LogManager.actionLogV2("P00310", str);
        }
    }

    public static void b(String str, JSONObject jSONObject) {
        if (jSONObject != null) {
            LogManager.actionLogV2("P00300", str, jSONObject);
        } else {
            LogManager.actionLogV2("P00300", str);
        }
    }

    public final void a(TipStyle tipStyle) {
        if (tipStyle != null && isAlive()) {
            if (TipStyle.UNLOCKING.equals(this.i) && TipStyle.RIDING.equals(tipStyle)) {
                c(TipStyle.RIDING);
                b(TipStyle.RIDING);
            }
            if (this.i != tipStyle) {
                this.i = tipStyle;
                switch (this.i) {
                    case RIDING:
                        this.b.setVisibility(0);
                        this.c.setVisibility(8);
                        this.c.setTorch(false);
                        this.m.setText("用车中");
                        eht.e();
                        return;
                    case UNLOCKING:
                        this.b.setVisibility(8);
                        this.c.setVisibility(0);
                        this.m.setText("解锁码");
                        if (isAlive()) {
                            String b2 = ehs.b("share_bike_ofo_un_lockpwd_key");
                            if (TextUtils.isEmpty(b2)) {
                                b2 = "----";
                            }
                            this.c.showPWDCode(b2);
                            break;
                        }
                        break;
                }
            }
        }
    }

    public final void b(TipStyle tipStyle) {
        if (!TipStyle.UNLOCKING.equals(tipStyle)) {
            String b2 = ehs.b("share_bike_cp_source");
            boolean z = false;
            if (this.b != null && ViewType.MECHANICAL.equals(this.b.getLockType())) {
                z = true;
            }
            if (z && "ofo".equalsIgnoreCase(b2)) {
                b("B006", null);
            }
        }
    }

    public static void c(TipStyle tipStyle) {
        JSONObject jSONObject = new JSONObject();
        try {
            String b2 = ehs.b("share_bike_cp_source");
            String str = "";
            if ("mobike".equalsIgnoreCase(b2)) {
                str = "mobike";
            } else if ("ofo".equalsIgnoreCase(b2)) {
                str = "ofo";
            }
            jSONObject.put("from", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (TipStyle.UNLOCKING.equals(tipStyle)) {
            a((String) "B004", (JSONObject) null);
        } else {
            b("B005", jSONObject);
        }
    }

    public final synchronized void a(long j2) {
        if (j2 > 0) {
            a(TipStyle.UNLOCKING);
            this.c.updateCountdownTime((int) j2);
            return;
        }
        a(TipStyle.RIDING);
    }

    static /* synthetic */ void a(ShareRidingMapPage shareRidingMapPage, BikeEndBillingRequest bikeEndBillingRequest) {
        shareRidingMapPage.h();
        shareRidingMapPage.u = aav.a(bikeEndBillingRequest, "努力加载中…");
        shareRidingMapPage.u.show();
    }

    static /* synthetic */ void n(ShareRidingMapPage shareRidingMapPage) {
        String str;
        if (shareRidingMapPage.isAlive()) {
            ScanQrcode scanQrcodeObj = ModuleBicycle.getScanQrcodeObj();
            if (!TextUtils.isEmpty(scanQrcodeObj.repairurl)) {
                str = scanQrcodeObj.repairurl;
            } else {
                StringBuilder sb = new StringBuilder("https://ofo-amap.ofo.com/webapp/#/Repair/");
                sb.append(scanQrcodeObj.orderId);
                str = sb.toString();
            }
            aja aja = new aja(str);
            aja.b = new ajf() {
                public final boolean g() {
                    return true;
                }

                public final boolean h() {
                    return true;
                }
            };
            ((ehg) shareRidingMapPage.mPresenter).a = true;
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a((bid) shareRidingMapPage, aja);
            }
        }
    }

    static /* synthetic */ void a(ShareRidingMapPage shareRidingMapPage, String str, String str2, String str3, String str4) {
        SpannableString spannableString = new SpannableString(str2);
        spannableString.setSpan(new ForegroundColorSpan(shareRidingMapPage.getResources().getColor(R.color.f_c_8)), 0, spannableString.length(), 33);
        SpannableString spannableString2 = new SpannableString(str3);
        spannableString2.setSpan(new ForegroundColorSpan(shareRidingMapPage.getResources().getColor(R.color.f_c_2)), 0, spannableString2.length(), 33);
        if (shareRidingMapPage.t == null) {
            AlertView.a aVar = new AlertView.a(AMapAppGlobal.getApplication());
            aVar.a((CharSequence) str).b((CharSequence) spannableString2, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    ShareRidingMapPage.this.dismissViewLayer(alertView);
                }
            }).a((CharSequence) spannableString, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    ShareRidingMapPage.this.dismissViewLayer(alertView);
                    final String b = ehs.b("share_bike_order_id");
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                    double d = 0.0d;
                    double longitude = latestPosition == null ? 0.0d : latestPosition.getLongitude();
                    if (latestPosition != null) {
                        d = latestPosition.getLatitude();
                    }
                    BikeEndBillingRequest bikeEndBillingRequest = new BikeEndBillingRequest();
                    bikeEndBillingRequest.b = ShareRidingMapPage.this.j;
                    bikeEndBillingRequest.c = b;
                    bikeEndBillingRequest.e = String.valueOf(longitude);
                    bikeEndBillingRequest.d = String.valueOf(d);
                    ShareRidingMapPage.a(ShareRidingMapPage.this, bikeEndBillingRequest);
                    a.a((BaseRequest) new EndBillingRequest(bikeEndBillingRequest, new BaseRequest.a() {
                        public final void a(BaseNetResult baseNetResult) {
                            ShareRidingMapPage.this.h();
                            if (ShareRidingMapPage.this.isAlive()) {
                                if (baseNetResult == null || !baseNetResult.result) {
                                    ToastHelper.showToast("网络异常，请稍后重试");
                                    return;
                                }
                                OrderInfo orderInfo = new OrderInfo();
                                orderInfo.hasUnfinishOrder = true;
                                orderInfo.status = 0;
                                orderInfo.dataSource = 4;
                                orderInfo.cpSource = ShareRidingMapPage.this.j;
                                orderInfo.orderId = b;
                                orderInfo.extraData = baseNetResult;
                                egx.a().a(orderInfo);
                                if (ShareRidingMapPage.this.mPresenter != null) {
                                    egj.a().d();
                                    eao.f("tylorvan", "ShareRidingMapPresenter requestSuccess");
                                    ((ehg) ShareRidingMapPage.this.mPresenter).c();
                                    egj.a().a(false);
                                    ((ehg) ShareRidingMapPage.this.mPresenter).a(1);
                                }
                            }
                        }
                    }));
                }
            }).c = new a() {
                public final void onClick(AlertView alertView, int i) {
                    ShareRidingMapPage.this.dismissViewLayer(alertView);
                }
            };
            if (!TextUtils.isEmpty(str4)) {
                aVar.b((CharSequence) str4);
            }
            aVar.a(true);
            shareRidingMapPage.t = aVar.a();
        }
        if (shareRidingMapPage.t != null) {
            shareRidingMapPage.showViewLayer(shareRidingMapPage.t);
        }
    }

    static /* synthetic */ void e() {
        JSONObject jSONObject = new JSONObject();
        try {
            String b2 = ehs.b("share_bike_cp_source");
            String str = "";
            if ("mobike".equalsIgnoreCase(b2)) {
                str = "mobike";
            } else if ("ofo".equalsIgnoreCase(b2)) {
                str = "ofo";
            }
            jSONObject.put("from", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00300", "B002", jSONObject);
    }
}
