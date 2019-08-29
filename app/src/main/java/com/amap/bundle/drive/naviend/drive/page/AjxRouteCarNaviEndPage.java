package com.amap.bundle.drive.naviend.drive.page;

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
import com.amap.bundle.drive.ajx.inter.CompleteReportInfoCallBack;
import com.amap.bundle.drive.ajx.inter.INaviEnd;
import com.amap.bundle.drive.ajx.inter.INewYearActivityCallback;
import com.amap.bundle.drive.ajx.module.ModuleDriveCommonBusiness;
import com.amap.bundle.drive.ajx.module.ModuleDriveEnd;
import com.amap.bundle.drive.ajx.module.ModuleDriveNavi;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
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
import defpackage.of;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxRouteCarNaviEndPage<Presenter extends of> extends Ajx3Page implements bgm {
    public ModuleDriveEnd a;
    public ModuleVUI b;
    public ModuleDriveCommonBusiness c;
    protected Handler d = new Handler();
    PayforNaviData e;
    public NavigationDataResult f;
    public String g;
    public IReportErrorManager h;
    /* access modifiers changed from: 0000 */
    public boolean i = true;
    /* access modifiers changed from: 0000 */
    public IPageStateListener j = null;
    /* access modifiers changed from: 0000 */
    public String k;
    INewYearActivityCallback l = new INewYearActivityCallback() {
        public final void onNewYearActivityCallback(int i) {
            AjxRouteCarNaviEndPage.this.a.notifyNewYearAcitivity(String.valueOf(i));
        }
    };
    INaviEnd m = new INaviEnd() {
        public final void requestDriveEndOperationActivities(String str) {
            boolean z;
            AjxRouteCarNaviEndPage ajxRouteCarNaviEndPage = AjxRouteCarNaviEndPage.this;
            INewYearActivityCallback iNewYearActivityCallback = AjxRouteCarNaviEndPage.this.l;
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
            if (ajxRouteCarNaviEndPage.i) {
                String a2 = AjxRouteCarNaviEndPage.a(z, z2);
                ajxRouteCarNaviEndPage.k = a2;
                ctl ctl = (ctl) a.a.a(ctl.class);
                ctl.a(a2, new Callback<ctm>(iNewYearActivityCallback, ctl, a2) {
                    final /* synthetic */ INewYearActivityCallback a;
                    final /* synthetic */ ctl b;
                    final /* synthetic */ String c;

                    {
                        this.a = r2;
                        this.b = r3;
                        this.c = r4;
                    }

                    public void callback(ctm ctm) {
                        if (ctm == null) {
                            this.a.onNewYearActivityCallback(0);
                        } else if (AjxRouteCarNaviEndPage.this == null || !AjxRouteCarNaviEndPage.this.isStarted() || !AjxRouteCarNaviEndPage.this.i) {
                            this.a.onNewYearActivityCallback(0);
                        } else if (ctm.a != 1 || TextUtils.isEmpty(ctm.c)) {
                            this.a.onNewYearActivityCallback(0);
                        } else {
                            AjxRouteCarNaviEndPage.this.i = false;
                            this.a.onNewYearActivityCallback(1);
                            this.b.a(AjxRouteCarNaviEndPage.this, this.c, ctm.c);
                        }
                    }

                    public void error(Throwable th, boolean z) {
                        this.a.onNewYearActivityCallback(0);
                    }
                });
                ajxRouteCarNaviEndPage.j = AMapPageUtil.getPageStateListener(ajxRouteCarNaviEndPage);
                AMapPageUtil.setPageStateListener(ajxRouteCarNaviEndPage, new IPageStateListener(z, z2) {
                    final /* synthetic */ boolean a;
                    final /* synthetic */ boolean b;

                    {
                        this.a = r2;
                        this.b = r3;
                    }

                    public final void onCover() {
                        String a2 = AjxRouteCarNaviEndPage.a(this.a, this.b);
                        ctl ctl = (ctl) a.a.a(ctl.class);
                        if (ctl != null) {
                            ctl.a(a2);
                        }
                        if (AjxRouteCarNaviEndPage.this.j != null) {
                            AjxRouteCarNaviEndPage.this.j.onCover();
                        }
                    }

                    public final void onAppear() {
                        if (AjxRouteCarNaviEndPage.this.j != null) {
                            AjxRouteCarNaviEndPage.this.j.onAppear();
                        }
                    }
                });
            }
        }

        public final void reportDriveEndError(String str) {
            try {
                NavigationDataResult navigationDataResult = AjxRouteCarNaviEndPage.this.f;
                if (navigationDataResult != null) {
                    JSONObject jSONObject = new JSONObject(str);
                    JSONArray optJSONArray = jSONObject.optJSONArray("coords");
                    int length = optJSONArray.length() / 2;
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
                AjxRouteCarNaviEndPage.this.d.post(new Runnable() {
                    public final void run() {
                        AjxRouteCarNaviEndPage.d(AjxRouteCarNaviEndPage.this);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public final void reportDestinationError(String str) {
            AjxRouteCarNaviEndPage.a(AjxRouteCarNaviEndPage.this, str);
        }

        public final int getErrorReportNum() {
            return AjxRouteCarNaviEndPage.this.a();
        }
    };
    private boolean n = true;
    private ProgressDlg o;
    private bfa p = new bfa() {
        public final void a(boolean z) {
            if (z) {
                ctl ctl = (ctl) a.a.a(ctl.class);
                if (ctl != null) {
                    ctl.a(AjxRouteCarNaviEndPage.this.k);
                }
            }
        }
    };
    private CompleteReportInfoCallBack q = new CompleteReportInfoCallBack() {
        public final void completeReportInfo() {
            AjxRouteCarNaviEndPage.this.d.post(new Runnable() {
                public final void run() {
                    Intent intent = new Intent();
                    intent.setAction("amap.basemap.action.feedback_report_error_list_page");
                    intent.setPackage("com.autonavi.minimap");
                    PageBundle pageBundle = new PageBundle(intent);
                    pageBundle.putString("ReportErrorListFragment.naviId", AjxRouteCarNaviEndPage.this.f.getNaviId());
                    pageBundle.putString("navi_type", AjxRouteCarNaviEndPage.this.g);
                    AjxRouteCarNaviEndPage.this.startPageForResult((String) "amap.basemap.action.feedback_report_error_list_page", pageBundle, 4096);
                }
            });
        }
    };

    public void finishSelf() {
    }

    public View getMapSuspendView() {
        return null;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 72057594037927936L;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        new Handler().postDelayed(new Runnable() {
            public final void run() {
                MapManager mapManager = AjxRouteCarNaviEndPage.this.getMapManager();
                if (AjxRouteCarNaviEndPage.this.isAlive() && mapManager != null) {
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

    public Ajx3PagePresenter createPresenter() {
        this.mPresenter = new of(this);
        return (Ajx3PagePresenter) this.mPresenter;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        this.a = (ModuleDriveEnd) this.mAjxView.getJsModule(ModuleDriveEnd.MODULE_NAME);
        this.c = (ModuleDriveCommonBusiness) this.mAjxView.getJsModule(ModuleDriveCommonBusiness.MODULE_NAME);
        if (this.b == null) {
            this.b = (ModuleVUI) this.mAjxView.getJsModule(ModuleVUI.MODULE_NAME);
        }
        if (this.a != null) {
            this.a.setDriveEndCallback(this.m);
        }
        if (this.c != null) {
            this.c.setCompleteReportInfoCallBack(this.q);
        }
        if (this.b != null) {
            this.b.setMitVuiDialogEventListener(this.p);
        }
    }

    public void loadJs() {
        if (getArguments() != null) {
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int i2 = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            this.mAjxView.load(ModuleDriveNavi.CAR_NAVI_END, getArguments().getString("jsData"), "CAR_NAVI_END", i2, i3);
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.o != null) {
            this.o.dismiss();
            this.o = null;
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

    public final int a() {
        if (this.h == null) {
            return 0;
        }
        StringBuilder sb = new StringBuilder("getErrorCount---mCurrentNaviId=");
        sb.append(this.f.getNaviId());
        AMapLog.d("sudaxia", sb.toString());
        return this.h.getErrorListCount(this.f.getNaviId());
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

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
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
        LogManager.actionLogV2("P00028", "B024", jSONObject);
    }

    static /* synthetic */ void d(AjxRouteCarNaviEndPage ajxRouteCarNaviEndPage) {
        int a2 = ajxRouteCarNaviEndPage.a();
        String str = ajxRouteCarNaviEndPage.g;
        ku a3 = ku.a();
        StringBuilder sb = new StringBuilder("showErrorReportListAlert    errorCount:");
        sb.append(a2);
        sb.append("   naviType:");
        sb.append(str);
        a3.c("NaviMonitor", sb.toString());
        if (a2 == 0) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("RouteCarResultErrorReportFragment.bundle_key_result", ajxRouteCarNaviEndPage.f);
            pageBundle.putObject("mapLeft", Integer.valueOf(tt.a(ajxRouteCarNaviEndPage.getContext()) / 2));
            pageBundle.putObject("mapTop", Integer.valueOf(tt.b(ajxRouteCarNaviEndPage.getContext()) / 2));
            pageBundle.putObject("navi_type", str);
            pageBundle.putBoolean("isFromEyrie", true);
            ajxRouteCarNaviEndPage.startPage((String) "amap.basemap.action.navigation_error_report", pageBundle);
            return;
        }
        if (a2 > 0) {
            AnonymousClass8 r1 = new a() {
                public final void onClick(AlertView alertView, int i) {
                    String str = "";
                    if (i != -2) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setAction("amap.basemap.action.feedback_report_error_list_page");
                                intent.setPackage("com.autonavi.minimap");
                                PageBundle pageBundle = new PageBundle(intent);
                                pageBundle.putString("ReportErrorListFragment.naviId", AjxRouteCarNaviEndPage.this.f.getNaviId());
                                pageBundle.putString("navi_type", AjxRouteCarNaviEndPage.this.g);
                                AjxRouteCarNaviEndPage.this.startPageForResult((String) "amap.basemap.action.feedback_report_error_list_page", pageBundle, 4096);
                                AjxRouteCarNaviEndPage.this.dismissViewLayer(alertView);
                                str = AMapAppGlobal.getApplication().getString(R.string.complete_report);
                                break;
                            case 1:
                                PageBundle pageBundle2 = new PageBundle();
                                pageBundle2.putObject("RouteCarResultErrorReportFragment.bundle_key_result", AjxRouteCarNaviEndPage.this.f);
                                pageBundle2.putObject("mapLeft", Integer.valueOf(tt.a(AjxRouteCarNaviEndPage.this.getContext()) / 2));
                                pageBundle2.putObject("mapTop", Integer.valueOf(tt.b(AjxRouteCarNaviEndPage.this.getContext()) / 2));
                                pageBundle2.putString("navi_type", AjxRouteCarNaviEndPage.this.g);
                                pageBundle2.putBoolean("isFromEyrie", true);
                                AjxRouteCarNaviEndPage.this.startPage((String) "amap.basemap.action.navigation_error_report", pageBundle2);
                                AjxRouteCarNaviEndPage.this.dismissViewLayer(alertView);
                                str = AMapAppGlobal.getApplication().getString(R.string.navigation_done_report);
                                break;
                            default:
                                AjxRouteCarNaviEndPage.this.dismissViewLayer(alertView);
                                break;
                        }
                    } else {
                        AjxRouteCarNaviEndPage.this.dismissViewLayer(alertView);
                        str = AMapAppGlobal.getApplication().getString(R.string.cancel);
                    }
                    AjxRouteCarNaviEndPage.a(AjxRouteCarNaviEndPage.this.g, str);
                }
            };
            a aVar = new a(ajxRouteCarNaviEndPage.getContext());
            aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.navigation_done_error_report_dialog_title)).a(new CharSequence[]{AMapAppGlobal.getApplication().getString(R.string.complete_report), AMapAppGlobal.getApplication().getString(R.string.navigation_done_report)}, (a) r1).b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.cancel), (a) r1);
            ajxRouteCarNaviEndPage.showViewLayer(aVar.a());
        }
    }

    static /* synthetic */ void a(AjxRouteCarNaviEndPage ajxRouteCarNaviEndPage, final String str) {
        final AosRequest a2 = a((AosResponseCallbackOnUi<AosByteResponse>) new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                AjxRouteCarNaviEndPage.this.b();
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
                            AjxRouteCarNaviEndPage ajxRouteCarNaviEndPage = AjxRouteCarNaviEndPage.this;
                            ajxRouteCarNaviEndPage.e = new PayforNaviData(str, (String) "");
                            ajxRouteCarNaviEndPage.e.moneyMaypayed = optDouble;
                            Intent intent = new Intent();
                            intent.setAction("apply_pay_type_choose");
                            intent.setPackage("com.autonavi.minimap");
                            PageBundle pageBundle = new PageBundle(intent);
                            pageBundle.putObject("ApplyPayForTypeChooseFragment.PayforNaviData", ajxRouteCarNaviEndPage.e);
                            AjxRouteCarNaviEndPage.this.startPageForResult((String) "apply_pay_type_choose", pageBundle, 10001);
                        }
                    } else {
                        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                AjxRouteCarNaviEndPage.this.b();
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
            }
        }, str);
        ajxRouteCarNaviEndPage.b();
        ajxRouteCarNaviEndPage.o = new ProgressDlg(ajxRouteCarNaviEndPage.getActivity(), "", "");
        ajxRouteCarNaviEndPage.o.setCancelable(true);
        ajxRouteCarNaviEndPage.o.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (a2 != null) {
                    in.a().a(a2);
                }
            }
        });
        ajxRouteCarNaviEndPage.o.show();
    }
}
