package com.autonavi.bundle.sharebike.ajx;

import android.bluetooth.BluetoothAdapter;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.fastjson.JSON;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.model.BikeTrack;
import com.autonavi.minimap.route.sharebike.model.ScanQrcode;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("shareBike")
@KeepPublicClassMembers
@KeepName
public class ModuleBicycle extends AbstractModule {
    public static final String MODULE_NAME = "shareBike";
    public static final String SP_BIKE_INFO_KEY = "bike_info";
    private JsFunctionCallback mBannerStatuscallback;
    private JsFunctionCallback mBannercallback;
    private JsFunctionCallback mCallback;
    private ehp mOfoCountdownCallback;

    @AjxMethod("scanResultPageShowExecute")
    public void scanResultPageShowExecute() {
    }

    public ModuleBicycle(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("listenBicycleStatus")
    public void listenBicycleStatus(JsFunctionCallback jsFunctionCallback) {
        this.mCallback = jsFunctionCallback;
    }

    public void notifyBicycleStatus(String str) {
        if (this.mCallback != null) {
            this.mCallback.callback(str);
        }
    }

    @AjxMethod("openShareBikeTrack")
    public void openShareBikeTrack(String str) {
        if (str == null) {
            eao.a((String) "Amap#", (String) "openShareBikeTrack# params is null");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            final String optString = jSONObject.optString("orderId");
            final String optString2 = jSONObject.optString("cpSource");
            final String optString3 = jSONObject.optString("startTime");
            final String optString4 = jSONObject.optString("totalFee");
            final String optString5 = jSONObject.optString("ridingTime");
            final String optString6 = jSONObject.optString("distance");
            a a = ebr.a(false);
            AnonymousClass1 r1 = new Runnable() {
                public final void run() {
                    eab b2 = eaa.a().b(optString);
                    final PageBundle pageBundle = new PageBundle();
                    pageBundle.putString("bundle_orderid_key", optString);
                    pageBundle.putString("bundle_cpsource_key", optString2);
                    pageBundle.putString("bundle_key_back_page", "page_go_back_last_page");
                    pageBundle.putSerializable("bundle_biketrack_key", new BikeTrack(optString3, optString4, optString5, optString6));
                    if (b2 != null) {
                        AMapPageUtil.getAppContext();
                        final RideTraceHistory a2 = ees.a(bsp.a().a(b2.c));
                        if (a2 != null) {
                            aho.a(new Runnable() {
                                public final void run() {
                                    pageBundle.putObject("data", a2);
                                    bid pageContext = AMapPageUtil.getPageContext();
                                    if (pageContext == null) {
                                        eao.a((String) "Amap#", (String) "ModuleBicycle# page context is null !!");
                                    } else {
                                        pageContext.startPage(ShareRidingFinishPage.class, pageBundle);
                                    }
                                }
                            });
                        } else {
                            ModuleBicycle.this.showNoReferOrder(pageBundle);
                        }
                    } else {
                        ModuleBicycle.this.showNoReferOrder(pageBundle);
                    }
                }
            };
            a.post(r1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @AjxMethod("goSetting")
    public void goSetting() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", DoNotUseTool.getActivity().getPackageName(), null));
        DoNotUseTool.getActivity().startActivityForResult(intent, 1);
    }

    @AjxMethod("saveUserInfo")
    public void saveUserInfo(String str, String str2, String str3) {
        if (AMapPageUtil.getAppContext() != null) {
            if (!TextUtils.isEmpty(str)) {
                ehs.a((String) "share_bike_user_id", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                ehs.a((String) "share_bike_token_id", str2);
            }
        }
        bky.a(str3, str, str2);
    }

    @AjxMethod("getCpSourceList")
    public void getCpSourceList(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            ehr.a((GeoPoint) null, (b) new b() {
                public final void a(a aVar) {
                    boolean z = aVar != null;
                    try {
                        JSONObject jSONObject = new JSONObject();
                        if (z) {
                            JSONArray jSONArray = aVar.f;
                            if (jSONArray != null) {
                                for (int i = 0; i < jSONArray.length(); i++) {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                    String access$100 = ModuleBicycle.this.getLogoBig(jSONObject2.getString("source"));
                                    if (access$100 != null) {
                                        jSONObject2.put("logo", access$100);
                                    }
                                }
                            }
                            jSONObject.put("cp", jSONArray);
                        }
                        jSONObject.put(UserTrackerConstants.IS_SUCCESS, z ? "true" : "false");
                        jSONObject.put("cpsource", ehs.b("share_bike_cp_source"));
                        jsFunctionCallback.callback(jSONObject.toString());
                    } catch (Exception unused) {
                        jsFunctionCallback.callback(bny.c);
                    }
                }
            });
        }
    }

    @AjxMethod("saveCpSource")
    public void saveCpSource(String str) {
        if (AMapPageUtil.getAppContext() != null && !TextUtils.isEmpty(str)) {
            ehs.a((String) "share_bike_check_cpsource", str);
        }
    }

    @AjxMethod("setShowedBanner")
    public void setShowedBanner(String str) {
        if (AMapPageUtil.getAppContext() != null && !TextUtils.isEmpty(str)) {
            ehs.a((String) "share_bike_cpsource_banner", str);
        }
    }

    /* access modifiers changed from: private */
    public void showNoReferOrder(final PageBundle pageBundle) {
        ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_bike_no_refer_order));
        ebr.a(true).post(new Runnable() {
            public final void run() {
                AMapPageUtil.getPageContext().startPage(ShareRidingFinishPage.class, pageBundle);
            }
        });
    }

    @AjxMethod("saveBikeInfo")
    public void saveBikeInfo(String str, JsFunctionCallback jsFunctionCallback) {
        boolean saveBikeInfo = saveBikeInfo(str);
        if (jsFunctionCallback != null) {
            Object[] objArr = new Object[1];
            objArr[0] = saveBikeInfo ? "success" : UploadDataResult.FAIL_MSG;
            jsFunctionCallback.callback(objArr);
        }
    }

    public static boolean saveBikeInfo(String str) {
        boolean z;
        eao.b("saveBikeInfo", str);
        try {
            ScanQrcode scanQrcode = (ScanQrcode) JSON.parseObject(str, ScanQrcode.class);
            if (scanQrcode == null) {
                scanQrcode = new ScanQrcode();
            }
            if (!TextUtils.isEmpty(scanQrcode.cpSource)) {
                ehs.a((String) "share_bike_cp_source", scanQrcode.cpSource);
                z = "ofo".equalsIgnoreCase(scanQrcode.cpSource);
            } else {
                z = false;
            }
            if (!TextUtils.isEmpty(scanQrcode.bikeId)) {
                ehs.a((String) "share_bike_id", scanQrcode.bikeId);
            }
            if (!TextUtils.isEmpty(scanQrcode.token)) {
                ehs.a((String) "share_bike_token_id", scanQrcode.token);
            }
            if (!TextUtils.isEmpty(scanQrcode.cpUserId)) {
                ehs.a((String) "share_bike_user_id", scanQrcode.cpUserId);
            }
            if (!TextUtils.isEmpty(scanQrcode.unlockpwd)) {
                ehs.a((String) "share_bike_ofo_un_lockpwd_key", scanQrcode.unlockpwd);
            }
            if (!TextUtils.isEmpty(scanQrcode.repairurl)) {
                ehs.a((String) "share_bike_ofo_repairurl_key", scanQrcode.repairurl);
            }
            bky.a(scanQrcode.appkey, scanQrcode.cpUserId, scanQrcode.token);
            if (z) {
                bky.a(scanQrcode.appkey);
            }
            if (!TextUtils.isEmpty(scanQrcode.orderId)) {
                ehs.a(scanQrcode.orderId);
                eht.e();
                if (z) {
                    ehs.a((String) "share_bike_unlocking_status_id", (String) "true");
                    ehs.a(scanQrcode.loadingTime);
                    ehs.a((String) "share_bike_ofo_max_loading_time", String.valueOf(scanQrcode.loadingTime));
                }
            }
            ear.a(SP_BIKE_INFO_KEY, str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @AjxMethod("getBikeInfo")
    public void getBikeInfo(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(getBikeInfo());
        }
    }

    public static String getBikeInfo() {
        String jSONString = JSON.toJSONString(getScanQrcodeObj());
        eao.b("getBikeInfo", jSONString);
        return jSONString;
    }

    @NonNull
    public static ScanQrcode getScanQrcodeObj() {
        ScanQrcode scanQrcode;
        try {
            scanQrcode = (ScanQrcode) JSON.parseObject(ear.b(SP_BIKE_INFO_KEY, ""), ScanQrcode.class);
        } catch (Exception unused) {
            scanQrcode = null;
        }
        if (scanQrcode == null) {
            scanQrcode = new ScanQrcode();
        }
        scanQrcode.cpSource = ehs.b("share_bike_cp_source");
        scanQrcode.orderId = ehs.b("share_bike_order_id");
        scanQrcode.bikeId = ehs.b("share_bike_id");
        scanQrcode.token = ehs.b("share_bike_token_id");
        scanQrcode.cpUserId = ehs.b("share_bike_user_id");
        scanQrcode.unlockpwd = ehs.b("share_bike_ofo_un_lockpwd_key");
        scanQrcode.repairurl = ehs.b("share_bike_ofo_repairurl_key");
        return scanQrcode;
    }

    @AjxMethod("openScanResultPage")
    public void openScanResultPage(final JsFunctionCallback jsFunctionCallback) {
        eao.a((String) "ofoCountDown", (String) "openScanResultPage");
        eht.b(true);
        if (jsFunctionCallback != null) {
            eht.a((long) ehs.a());
            eht.g();
            egj.a().d();
            if (this.mOfoCountdownCallback == null) {
                this.mOfoCountdownCallback = new ehp() {
                    public final void a() {
                    }

                    public final void a(String str) {
                        StringBuilder sb = new StringBuilder("call js timeleft ");
                        sb.append(str);
                        sb.append("秒");
                        eao.a((String) "ofoCountDown", sb.toString());
                        jsFunctionCallback.callback(str);
                    }
                };
            }
            eht.a(this.mOfoCountdownCallback);
        }
    }

    @AjxMethod("openPage")
    public void openPage(String str, final String str2) {
        bid pageContext = AMapPageUtil.getPageContext();
        if ((pageContext instanceof Ajx3DialogPage) && TextUtils.equals(((Ajx3DialogPage) pageContext).getAjx3Url(), "path://amap_bundle_tripgroup/src/share_bike/ShareBikeLoginPage.page.js")) {
            pageContext.finish();
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -1233108539:
                if (str.equals("LOGIN_PAGE")) {
                    c = 4;
                    break;
                }
                break;
            case -677666082:
                if (str.equals("PAGE_QRCODE_SCAN")) {
                    c = 0;
                    break;
                }
                break;
            case -620727612:
                if (str.equals("PAGE_HISTORY")) {
                    c = 1;
                    break;
                }
                break;
            case 1672777127:
                if (str.equals("PAGE_WALLET_DETAIL")) {
                    c = 3;
                    break;
                }
                break;
            case 1767343988:
                if (str.equals("PAGE_WALLET_LIST")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                kj.a(DoNotUseTool.getActivity(), new String[]{"android.permission.CAMERA"}, (b) new b() {
                    /* JADX WARNING: Removed duplicated region for block: B:27:0x007a  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void run() {
                        /*
                            r7 = this;
                            com.autonavi.bundle.sharebike.ajx.ModuleBicycle r0 = com.autonavi.bundle.sharebike.ajx.ModuleBicycle.this
                            boolean r0 = r0.checkSpecialForCameraPermission()
                            if (r0 != 0) goto L_0x0016
                            android.content.Context r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
                            int r1 = com.autonavi.minimap.R.string.sharebike_camera_init_fail
                            java.lang.String r0 = r0.getString(r1)
                            com.amap.bundle.utils.ui.ToastHelper.showToast(r0)
                            return
                        L_0x0016:
                            r0 = 0
                            r1 = 1
                            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0072 }
                            java.lang.String r3 = r7     // Catch:{ JSONException -> 0x0072 }
                            r2.<init>(r3)     // Catch:{ JSONException -> 0x0072 }
                            java.lang.String r3 = "launchBikePage"
                            boolean r3 = r2.optBoolean(r3, r1)     // Catch:{ JSONException -> 0x0072 }
                            java.lang.String r1 = "end_lat"
                            java.lang.String r1 = r2.getString(r1)     // Catch:{ JSONException -> 0x0070 }
                            java.lang.String r4 = "end_lon"
                            java.lang.String r4 = r2.getString(r4)     // Catch:{ JSONException -> 0x0070 }
                            java.lang.String r5 = "end_name"
                            java.lang.String r5 = r2.getString(r5)     // Catch:{ JSONException -> 0x0070 }
                            boolean r6 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0070 }
                            if (r6 != 0) goto L_0x0078
                            boolean r6 = android.text.TextUtils.isEmpty(r4)     // Catch:{ JSONException -> 0x0070 }
                            if (r6 != 0) goto L_0x0078
                            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ JSONException -> 0x0070 }
                            if (r6 != 0) goto L_0x0078
                            com.autonavi.common.PageBundle r6 = new com.autonavi.common.PageBundle     // Catch:{ JSONException -> 0x0070 }
                            r6.<init>()     // Catch:{ JSONException -> 0x0070 }
                            java.lang.String r0 = "end_lat"
                            r6.putString(r0, r1)     // Catch:{ JSONException -> 0x006c }
                            java.lang.String r0 = "end_lon"
                            r6.putString(r0, r4)     // Catch:{ JSONException -> 0x006c }
                            java.lang.String r0 = "end_name"
                            r6.putString(r0, r5)     // Catch:{ JSONException -> 0x006c }
                            java.lang.String r0 = "sharebike_page_from"
                            java.lang.String r1 = "sharebike_page_from"
                            java.lang.String r1 = r2.getString(r1)     // Catch:{ JSONException -> 0x006c }
                            r6.putString(r0, r1)     // Catch:{ JSONException -> 0x006c }
                            r0 = r6
                            goto L_0x0078
                        L_0x006c:
                            r0 = move-exception
                            r1 = r0
                            r0 = r6
                            goto L_0x0075
                        L_0x0070:
                            r1 = move-exception
                            goto L_0x0075
                        L_0x0072:
                            r2 = move-exception
                            r1 = r2
                            r3 = 1
                        L_0x0075:
                            r1.printStackTrace()
                        L_0x0078:
                            if (r3 == 0) goto L_0x0083
                            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
                            java.lang.Class<com.autonavi.minimap.route.sharebike.page.ShareBikePage> r2 = com.autonavi.minimap.route.sharebike.page.ShareBikePage.class
                            r1.startPage(r2, r0)
                        L_0x0083:
                            r0 = 6
                            java.lang.String r1 = r7
                            defpackage.ehc.a(r0, r1)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.sharebike.ajx.ModuleBicycle.AnonymousClass5.run():void");
                    }
                });
                return;
            case 1:
                ehc.a(1, str2);
                return;
            case 2:
                ehc.a(2, str2);
                return;
            case 3:
                ehc.a(3, str2);
                return;
            case 4:
                ehc.a(7, str2);
                break;
        }
    }

    @AjxMethod("closeScanResultPage")
    public void closeScanResultPage(int i) {
        eht.b(false);
        if (this.mOfoCountdownCallback != null) {
            eht.b(this.mOfoCountdownCallback);
        }
    }

    @AjxMethod("stopOfoTimer")
    public void stopOfoTimer(String str) {
        eht.e();
    }

    @AjxMethod("startRideNavigation")
    @Deprecated
    public void startRideNavigation(String str) {
        POI poi;
        eao.a((String) "ModuleBicycle", "startRideNavigation:".concat(String.valueOf(str)));
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                jSONObject.optString("startPoi");
                String optString = jSONObject.optString("endPoi");
                jSONObject.optInt("source");
                poi = bnx.a(optString);
            } else {
                poi = null;
            }
            if (poi == null) {
                poi = ehu.b();
            }
            if (!(AMapPageUtil.getPageContext() == null || poi == null)) {
                new PageBundle().putObject("endPoi", poi);
            }
        } catch (JSONException e) {
            eao.a("ModuleBicycle", "startRideNavigation:".concat(String.valueOf(str)), e);
            POI b = ehu.b();
            if (!(AMapPageUtil.getPageContext() == null || b == null)) {
                new PageBundle().putObject("endPoi", b);
            }
        } catch (Throwable th) {
            POI b2 = ehu.b();
            if (!(AMapPageUtil.getPageContext() == null || b2 == null)) {
                new PageBundle().putObject("endPoi", b2);
            }
            throw th;
        }
    }

    @AjxMethod("backLogicalWillExecute")
    public void backLogicalWillExecute(String str) {
        eao.a((String) "ModuleBicycle", "backLogicalWillExecute: ".concat(String.valueOf(str)));
        if (!TextUtils.isEmpty(str)) {
            if (!str.startsWith("amapuri://ajx?")) {
                if (str.startsWith("amapuri://")) {
                    String[] split = str.split("\\?");
                    if (split.length > 0) {
                        str = split[0];
                    }
                } else {
                    return;
                }
            }
            eao.a((String) "ModuleBicycle", "backLogicalWillExecute after process: ".concat(String.valueOf(str)));
            char c = 65535;
            if (str.hashCode() == 689253707 && str.equals("amapuri://shareBike/shareBikeBikingView")) {
                c = 0;
            }
            if (c != 0) {
                egj.a().d();
                return;
            }
            if (ehu.b() != null) {
                ehu.a();
            }
            egj.a().d();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkSpecialForCameraPermission() {
        /*
            r3 = this;
            r0 = 0
            android.hardware.Camera r1 = android.hardware.Camera.open()     // Catch:{ Exception -> 0x0021, all -> 0x0017 }
            android.hardware.Camera$Parameters r0 = r1.getParameters()     // Catch:{ Exception -> 0x0015, all -> 0x0013 }
            r1.setParameters(r0)     // Catch:{ Exception -> 0x0015, all -> 0x0013 }
            if (r1 == 0) goto L_0x0011
            r1.release()
        L_0x0011:
            r0 = 1
            goto L_0x0028
        L_0x0013:
            r0 = move-exception
            goto L_0x001b
        L_0x0015:
            r0 = r1
            goto L_0x0021
        L_0x0017:
            r1 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x001b:
            if (r1 == 0) goto L_0x0020
            r1.release()
        L_0x0020:
            throw r0
        L_0x0021:
            r1 = 0
            if (r0 == 0) goto L_0x0027
            r0.release()
        L_0x0027:
            r0 = 0
        L_0x0028:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.sharebike.ajx.ModuleBicycle.checkSpecialForCameraPermission():boolean");
    }

    @AjxMethod("listenBannerUserTagData")
    public void listenBannerUserTagData(JsFunctionCallback jsFunctionCallback) {
        this.mBannercallback = jsFunctionCallback;
    }

    public void setBannerUserTagData(String str) {
        if (this.mBannercallback != null) {
            this.mBannercallback.callback(str);
        }
    }

    public void setBannerStatus(String str) {
        if (this.mBannerStatuscallback != null) {
            this.mBannerStatuscallback.callback(str);
        }
    }

    @AjxMethod("pasteText")
    public void pasteText(String str) {
        ((ClipboardManager) AMapPageUtil.getAppContext().getSystemService("clipboard")).setText(str);
    }

    @AjxMethod("loginAuto")
    public void loginAuto(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("auto")) {
                    String optString = jSONObject.optString("auto");
                    if (!TextUtils.isEmpty(optString)) {
                        awt awt = (awt) a.a.a(awt.class);
                        if (awt != null) {
                            awt.b(optString);
                        }
                    }
                    return;
                }
                if (jSONObject.has("amaplink")) {
                    amapBlueToothLink(jSONObject.optString("amaplink"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void amapBlueToothLink(String str) {
        if (!TextUtils.isEmpty(str)) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
            if (iAutoRemoteController != null) {
                if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
                    iAutoRemoteController.promptToEnableBluetoothBeforePairing(str);
                } else if (iAutoRemoteController.isParied(str)) {
                    iAutoRemoteController.doConnectBt(str);
                } else {
                    iAutoRemoteController.pairDevice(str);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public String getLogoBig(String str) {
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("_big");
            String a = eht.a(sb.toString());
            if (a != null && ahd.b(a)) {
                return "file:/".concat(String.valueOf(a));
            }
        }
        return null;
    }

    @AjxMethod("getCpSource")
    public void getCpSource(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(ehs.b("share_bike_check_cpsource"));
        }
    }

    @AjxMethod("hasShowedBanner")
    public void hasShowedBanner(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(ehs.b("share_bike_cpsource_banner"));
        }
    }
}
