package com.amap.bundle.drive.naviend.motor.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drive.ajx.inter.IMotorActivityCallback;
import com.amap.bundle.drive.ajx.inter.INaviEnd;
import com.amap.bundle.drive.ajx.module.ModuleCommonBusiness;
import com.amap.bundle.drive.ajx.module.ModuleDriveEnd;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.usepay.UsePayRequestHolder;
import com.autonavi.minimap.usepay.param.ApplyCheckRequest;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import defpackage.og;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxRouteMotorNaviEndPage<Presenter extends og> extends Ajx3Page {
    public ModuleDriveEnd a;
    protected Handler b = new Handler();
    PayforNaviData c;
    /* access modifiers changed from: 0000 */
    public boolean d = true;
    /* access modifiers changed from: 0000 */
    public IPageStateListener e = null;
    public NavigationDataResult f;
    public String g;
    public IReportErrorManager h;
    INaviEnd i = new INaviEnd() {
        public final int getErrorReportNum() {
            return 0;
        }

        public final void requestDriveEndOperationActivities(String str) {
            boolean z;
            AjxRouteMotorNaviEndPage ajxRouteMotorNaviEndPage = AjxRouteMotorNaviEndPage.this;
            boolean z2 = false;
            try {
                JSONObject jSONObject = new JSONObject(str);
                z = "1".equals(jSONObject.optString("isSafeShareSuccess"));
                try {
                    z2 = "1".equals(jSONObject.optString("isFeedbackSuccess"));
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                z = false;
            }
            if (ajxRouteMotorNaviEndPage.d) {
                String a2 = AjxRouteMotorNaviEndPage.a(z, z2);
                ctl ctl = (ctl) a.a.a(ctl.class);
                if (ctl != null) {
                    ctl.a(a2, new Callback<ctm>(ctl, a2) {
                        final /* synthetic */ ctl a;
                        final /* synthetic */ String b;

                        public void error(Throwable th, boolean z) {
                        }

                        {
                            this.a = r2;
                            this.b = r3;
                        }

                        public void callback(ctm ctm) {
                            if (ctm != null && AjxRouteMotorNaviEndPage.this != null && AjxRouteMotorNaviEndPage.this.isStarted() && AjxRouteMotorNaviEndPage.this.d && ctm.a == 1 && !TextUtils.isEmpty(ctm.c)) {
                                AjxRouteMotorNaviEndPage.this.d = false;
                                this.a.a(AjxRouteMotorNaviEndPage.this, this.b, ctm.c);
                            }
                        }
                    });
                }
                ajxRouteMotorNaviEndPage.e = AMapPageUtil.getPageStateListener(ajxRouteMotorNaviEndPage);
                AMapPageUtil.setPageStateListener(ajxRouteMotorNaviEndPage, new IPageStateListener(ajxRouteMotorNaviEndPage, z, z2) {
                    final /* synthetic */ AbstractBasePage a;
                    final /* synthetic */ boolean b;
                    final /* synthetic */ boolean c;

                    {
                        this.a = r2;
                        this.b = r3;
                        this.c = r4;
                    }

                    public final void onCover() {
                        String a2 = AjxRouteMotorNaviEndPage.a(this.b, this.c);
                        ctl ctl = (ctl) a.a.a(ctl.class);
                        if (ctl != null) {
                            ctl.a(a2);
                        }
                        if (AjxRouteMotorNaviEndPage.this.e != null) {
                            AjxRouteMotorNaviEndPage.this.e.onCover();
                        }
                    }

                    public final void onAppear() {
                        if (AjxRouteMotorNaviEndPage.this.e != null) {
                            AjxRouteMotorNaviEndPage.this.e.onAppear();
                        }
                    }
                });
            }
        }

        public final void reportDriveEndError(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONArray optJSONArray = jSONObject.optJSONArray("coords");
                int length = optJSONArray.length() / 2;
                NavigationDataResult navigationDataResult = AjxRouteMotorNaviEndPage.this.f;
                if (navigationDataResult != null) {
                    for (int i = 0; i < length; i++) {
                        GeoPoint geoPoint = new GeoPoint();
                        int i2 = i * 2;
                        geoPoint.setLonLat(optJSONArray.getDouble(i2), optJSONArray.getDouble(i2 + 1));
                        navigationDataResult.addPassedPoint(geoPoint);
                    }
                    JSONArray optJSONArray2 = jSONObject.optJSONArray("yamPoints");
                    for (int i3 = 0; i3 < optJSONArray2.length(); i3++) {
                        GeoPoint geoPoint2 = new GeoPoint();
                        JSONObject jSONObject2 = optJSONArray2.getJSONObject(i3);
                        geoPoint2.setLonLat(jSONObject2.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON), jSONObject2.optDouble("lat"));
                        navigationDataResult.addDeviationPoint(geoPoint2);
                    }
                    navigationDataResult.getPassedPoints().clear();
                    navigationDataResult.onNewRouteStart();
                }
                ku.a().c("NaviMonitor", "15199300   onReportButtonClick    ");
                AjxRouteMotorNaviEndPage.this.b.post(new Runnable() {
                    public final void run() {
                        AjxRouteMotorNaviEndPage.c(AjxRouteMotorNaviEndPage.this);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public final void reportDestinationError(String str) {
            AjxRouteMotorNaviEndPage.a(AjxRouteMotorNaviEndPage.this, str);
        }
    };
    private ModuleCommonBusiness j;
    private ProgressDlg k;
    /* access modifiers changed from: private */
    public boolean l = true;
    private IMotorActivityCallback m = new IMotorActivityCallback() {
        public final void onActivityCallback() {
            if (AjxRouteMotorNaviEndPage.this.l) {
                final ctl ctl = (ctl) a.a.a(ctl.class);
                if (ctl != null) {
                    ctl.a("26", new Callback<ctm>() {
                        public void error(Throwable th, boolean z) {
                        }

                        public void callback(ctm ctm) {
                            if (ctm != null && AjxRouteMotorNaviEndPage.this != null && AjxRouteMotorNaviEndPage.this.isStarted() && AjxRouteMotorNaviEndPage.this.l && ctm.a == 1 && !TextUtils.isEmpty(ctm.c)) {
                                AjxRouteMotorNaviEndPage.this.l = false;
                                ctl.a(AjxRouteMotorNaviEndPage.this, "26", ctm.c);
                            }
                        }
                    });
                }
            }
        }
    };

    public View getMapSuspendView() {
        return null;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        new Handler().postDelayed(new Runnable() {
            public final void run() {
                MapManager mapManager = AjxRouteMotorNaviEndPage.this.getMapManager();
                if (AjxRouteMotorNaviEndPage.this.isAlive() && mapManager != null) {
                    IOverlayManager overlayManager = mapManager.getOverlayManager();
                    if (overlayManager != null) {
                        overlayManager.setGPSVisible(false);
                    }
                    bty mapView = mapManager.getMapView();
                    if (mapView != null) {
                        NaviManager.a().a(false);
                        mapView.b(false);
                    }
                }
            }
        }, 200);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public og createPresenter() {
        return new og(this);
    }

    public void loadJs() {
        if (getArguments() != null) {
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int i2 = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            this.mAjxView.load("path://amap_bundle_motorbike/src/end_page/MotorBikeEndPage.page.js", getArguments().getString("jsData"), "CAR_NAVI_END", i2, i3);
        }
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        this.a = (ModuleDriveEnd) this.mAjxView.getJsModule(ModuleDriveEnd.MODULE_NAME);
        if (this.a != null) {
            this.a.setDriveEndCallback(this.i);
        }
        this.j = (ModuleCommonBusiness) this.mAjxView.getJsModule(ModuleCommonBusiness.MODULE_NAME);
        if (this.j != null) {
            this.j.setMotorEndActivityCallback(this.m);
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.k != null) {
            this.k.dismiss();
            this.k = null;
        }
    }

    private static AosRequest a(AosResponseCallbackOnUi<AosByteResponse> aosResponseCallbackOnUi, String str) {
        ApplyCheckRequest applyCheckRequest = new ApplyCheckRequest();
        try {
            JSONObject jSONObject = new JSONObject(str);
            applyCheckRequest.c = jSONObject.optString("distance");
            applyCheckRequest.b = jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        } catch (JSONException unused) {
        }
        UsePayRequestHolder.getInstance().sendApplyCheck(applyCheckRequest, aosResponseCallbackOnUi);
        return applyCheckRequest;
    }

    static String a(boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        if (z2) {
            sb.append(",");
            sb.append("2");
        }
        if (z) {
            sb.append(",");
            sb.append("4");
        }
        return sb.toString();
    }

    static /* synthetic */ void c(AjxRouteMotorNaviEndPage ajxRouteMotorNaviEndPage) {
        int i2;
        if (ajxRouteMotorNaviEndPage.h != null) {
            StringBuilder sb = new StringBuilder("getErrorCount---mCurrentNaviId=");
            sb.append(ajxRouteMotorNaviEndPage.f.getNaviId());
            AMapLog.d("sudaxia", sb.toString());
            i2 = ajxRouteMotorNaviEndPage.h.getErrorListCount(ajxRouteMotorNaviEndPage.f.getNaviId());
        } else {
            i2 = 0;
        }
        String str = ajxRouteMotorNaviEndPage.g;
        ku a2 = ku.a();
        StringBuilder sb2 = new StringBuilder("showErrorReportListAlert    errorCount:");
        sb2.append(i2);
        sb2.append("   naviType:");
        sb2.append(str);
        a2.c("NaviMonitor", sb2.toString());
        if (i2 == 0) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("RouteCarResultErrorReportFragment.bundle_key_result", ajxRouteMotorNaviEndPage.f);
            pageBundle.putObject("mapLeft", Integer.valueOf(tt.a(ajxRouteMotorNaviEndPage.getContext()) / 2));
            pageBundle.putObject("mapTop", Integer.valueOf(tt.b(ajxRouteMotorNaviEndPage.getContext()) / 2));
            pageBundle.putObject("navi_type", str);
            pageBundle.putBoolean("isFromEyrie", true);
            ajxRouteMotorNaviEndPage.startPage((String) "amap.basemap.action.navigation_error_report", pageBundle);
            LogManager.actionLogV2("P00441", "B001");
            return;
        }
        if (i2 > 0) {
            AnonymousClass6 r1 = new a() {
                public final void onClick(AlertView alertView, int i) {
                    String str = "";
                    if (i != -2) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setAction("amap.basemap.action.feedback_report_error_list_page");
                                intent.setPackage("com.autonavi.minimap");
                                PageBundle pageBundle = new PageBundle(intent);
                                pageBundle.putString("ReportErrorListFragment.naviId", AjxRouteMotorNaviEndPage.this.f.getNaviId());
                                pageBundle.putString("navi_type", AjxRouteMotorNaviEndPage.this.g);
                                AjxRouteMotorNaviEndPage.this.startPageForResult((String) "amap.basemap.action.feedback_report_error_list_page", pageBundle, 4096);
                                AjxRouteMotorNaviEndPage.this.dismissViewLayer(alertView);
                                str = AMapAppGlobal.getApplication().getString(R.string.complete_report);
                                break;
                            case 1:
                                PageBundle pageBundle2 = new PageBundle();
                                pageBundle2.putObject("RouteCarResultErrorReportFragment.bundle_key_result", AjxRouteMotorNaviEndPage.this.f);
                                pageBundle2.putObject("mapLeft", Integer.valueOf(tt.a(AjxRouteMotorNaviEndPage.this.getContext()) / 2));
                                pageBundle2.putObject("mapTop", Integer.valueOf(tt.b(AjxRouteMotorNaviEndPage.this.getContext()) / 2));
                                pageBundle2.putString("navi_type", AjxRouteMotorNaviEndPage.this.g);
                                pageBundle2.putBoolean("isFromEyrie", true);
                                AjxRouteMotorNaviEndPage.this.startPage((String) "amap.basemap.action.navigation_error_report", pageBundle2);
                                AjxRouteMotorNaviEndPage.this.dismissViewLayer(alertView);
                                str = AMapAppGlobal.getApplication().getString(R.string.navigation_done_report);
                                break;
                            default:
                                AjxRouteMotorNaviEndPage.this.dismissViewLayer(alertView);
                                break;
                        }
                    } else {
                        AjxRouteMotorNaviEndPage.this.dismissViewLayer(alertView);
                        str = AMapAppGlobal.getApplication().getString(R.string.cancel);
                    }
                    AjxRouteMotorNaviEndPage.a(AjxRouteMotorNaviEndPage.this.g, str);
                }
            };
            a aVar = new a(ajxRouteMotorNaviEndPage.getContext());
            aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.navigation_done_error_report_dialog_title)).a(new CharSequence[]{AMapAppGlobal.getApplication().getString(R.string.complete_report), AMapAppGlobal.getApplication().getString(R.string.navigation_done_report)}, (a) r1).b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.cancel), (a) r1);
            ajxRouteMotorNaviEndPage.showViewLayer(aVar.a());
        }
    }

    static /* synthetic */ void a(AjxRouteMotorNaviEndPage ajxRouteMotorNaviEndPage, final String str) {
        final AosRequest a2 = a((AosResponseCallbackOnUi<AosByteResponse>) new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                AjxRouteMotorNaviEndPage.this.b();
                if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
                    return;
                }
                try {
                    String str = new String((byte[]) aosByteResponse.getResult(), "UTF-8");
                    if (!TextUtils.isEmpty(str)) {
                        JSONObject jSONObject = new JSONObject(str);
                        int optInt = jSONObject.optInt("code");
                        if (optInt == 101) {
                            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.activities_cannot_apply_payfor));
                        } else if (optInt != 1) {
                            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
                        } else {
                            double optDouble = jSONObject.optDouble(NetConstant.KEY_MONEY_ACCOUNT);
                            AjxRouteMotorNaviEndPage ajxRouteMotorNaviEndPage = AjxRouteMotorNaviEndPage.this;
                            ajxRouteMotorNaviEndPage.c = new PayforNaviData(str, (String) "");
                            ajxRouteMotorNaviEndPage.c.moneyMaypayed = optDouble;
                            Intent intent = new Intent();
                            intent.setAction("apply_pay_type_choose");
                            intent.setPackage("com.autonavi.minimap");
                            PageBundle pageBundle = new PageBundle(intent);
                            pageBundle.putObject("ApplyPayForTypeChooseFragment.PayforNaviData", ajxRouteMotorNaviEndPage.c);
                            AjxRouteMotorNaviEndPage.this.startPageForResult((String) "apply_pay_type_choose", pageBundle, 10001);
                        }
                    } else {
                        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                AjxRouteMotorNaviEndPage.this.b();
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
            }
        }, str);
        ajxRouteMotorNaviEndPage.b();
        ajxRouteMotorNaviEndPage.k = new ProgressDlg(ajxRouteMotorNaviEndPage.getActivity(), "", "");
        ajxRouteMotorNaviEndPage.k.setCancelable(true);
        ajxRouteMotorNaviEndPage.k.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (a2 != null) {
                    a2.cancel();
                }
            }
        });
        ajxRouteMotorNaviEndPage.k.show();
    }

    static /* synthetic */ void a(String str, String str2) {
        String str3 = "car".equals(str) ? "1" : "2";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(TrafficUtil.KEYWORD, str2);
            jSONObject.put("type", str3);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
