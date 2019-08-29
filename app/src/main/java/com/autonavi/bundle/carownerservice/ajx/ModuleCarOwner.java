package com.autonavi.bundle.carownerservice.ajx;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.CarInfo;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import com.autonavi.minimap.route.common.view.RouteBanner;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@AjxModule("carOwner")
@Keep
public class ModuleCarOwner extends AbstractModule {
    public static final String CAR_OWNER_INFO_OBJ = "carOwnerInfoObj";
    public static final String FROM_CAROWNER_LIST_PAGE = "1";
    public static final String FROM_CAROWNER_OTHER_PAGE = "2";
    public static final String FROM_CAROWNER_TYPE_DRIVE = "110";
    public static final String FROM_CAROWNER_VIOLATION_PAGE = "0";
    public static final String FROM_PAGE_VEHICLE_ADD = "addPage";
    public static final String FROM_PAGE_VEHICLE_EDIT = "editPage";
    public static final String FROM_PAGE_VEHICLE_LIST = "listPage";
    public static final String FROM_SOURCE_CAR_EDIT = "FROM_SOURCE_CAR_EDIT";
    public static final String KEY_AJX_CAROWNER_SOURCE = "from";
    public static final String KEY_FROM_SOURCE = "from_source";
    public static final String KEY_RROM_DRIVE = "drive";
    public static final String KEY_VEHICLE = "vehicle";
    private static final String NULL_STR = "null";
    private static final String TruckStatutePage = "truckStatute";
    private static String axleNum = "axleNum";
    private static String checkReminder = "checkReminder";
    private static String createTime = "createTime";
    private static String engineNum = "engineNum";
    private static String frameNum = "frameNum";
    private static String limitReminder = "limitReminder";
    private static String ocrRequestId = "ocrRequestId";
    private static String oftenUse = "oftenUse";
    private static String plateNum = "plateNum";
    private static String[] sProvinceCode = {"11", "12", "31", "50", "13", "14", "21", "22", "23", "32", RouteBanner.REQUEST_KEY_TRAIN, "34", "35", RouteBanner.REQUEST_KEY_RIDE, RouteBanner.REQUEST_KEY_COACH, SuperId.BIT_2_INDOOR_TAG_HOT, "42", "43", "44", "46", "51", "52", "53", "61", "62", SuperId.BIT_2_REALTIMEBUS_BUSSTATION_AUTO, "45", "15", "54", SuperId.BIT_2_REALTIMEBUS_BUSSTATION, SuperId.BIT_2_MAIN_BUSSTATION};
    private static String[] sProvinceNames = {"京", "津", "沪", "渝", "冀", "晋", "辽", "吉", "黑", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "琼", "川", "贵", "云", "陕", "甘", "青", "桂", "蒙", "藏", "宁", "新"};
    private static String telephone = "telphone";
    private static String truckAvoidWeightLimit = "truckAvoidWeightLimit";
    private static String truckCapacity = "capacity";
    private static String truckHeight = "height";
    private static String truckInfo = "truckInfo";
    private static String truckLength = "length";
    private static String truckType = "truckType";
    private static String truckWeight = "weight";
    private static String truckWidth = "width";
    private static String updateTime = "updateTime";
    private static String validityPeriod = "validityPeriod";
    private static String vehicleCode = "vehicleCode";
    private static String vehicleLogo = "vehicleLogo";
    private static String vehicleMsg = "vehicleMsg";
    private static String vehiclePowerType = "vehiclePowerType";
    private static String vehicleType = "vehicleType";
    private static String violationReminder = "violationReminder";
    private int AJX_CODE_ALREADY_EXIST = 2;
    private int AJX_CODE_FAIL = 0;
    private int AJX_CODE_MAX_EXCEEDED = 3;
    private int AJX_CODE_SUCCESS = 1;
    private String CarLicensePage = "license";
    private String CarTypePage = "brand";
    bid con;
    ctl iOperationsActivitiesService = null;
    a mSyncMergeEndListenerForCarOwnerAccountLogin = new a();

    class a implements bja {
        WeakReference<JsFunctionCallback> a = null;
        public boolean b = false;

        a() {
        }

        public final void onMergeEnd(boolean z) {
            if (this.b) {
                this.b = false;
                if (this.a != null) {
                    JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) this.a.get();
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback("1");
                    }
                }
            }
        }
    }

    public ModuleCarOwner(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("saveCarInfo")
    public void saveCarInfo(String str, JsFunctionCallback jsFunctionCallback) {
        int a2 = bsl.a().a(JsonToCarInfo(str));
        if (a2 == 0) {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_SUCCESS));
        } else if (a2 == 1) {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_ALREADY_EXIST));
        } else if (a2 == 2) {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_MAX_EXCEEDED));
        } else {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_FAIL));
        }
    }

    @AjxMethod("addCarInfoForLoginProcess")
    public void addCarInfoForLoginProcess(String str, JsFunctionCallback jsFunctionCallback) {
        Car JsonToCarInfo = JsonToCarInfo(str);
        if (bsl.a().a(getUid(), JsonToCarInfo) == 0) {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_SUCCESS));
            return;
        }
        jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_FAIL));
    }

    @AjxMethod("updateCarInfo")
    public void updateCarInfo(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (bsl.a().a(JsonToCarInfo(str2), str) == 0) {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_SUCCESS));
            return;
        }
        jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_FAIL));
    }

    @AjxMethod("deleteCarInfo")
    public void deleteCarInfo(String str, int i, JsFunctionCallback jsFunctionCallback) {
        if (bsl.a().a(str, i) == 0) {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_SUCCESS));
            return;
        }
        jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_FAIL));
    }

    @AjxMethod("getCarInfo")
    public void getCarInfo(String str, JsFunctionCallback jsFunctionCallback) {
        String str2;
        JSONObject jSONObject;
        bsl a2 = bsl.a();
        if (a2.a != null && !TextUtils.isEmpty(str)) {
            str2 = bsl.b(bsl.b(a2.a.getCar(agy.a(str))));
        } else {
            str2 = null;
        }
        try {
            jSONObject = new JSONObject(str2);
        } catch (Exception e) {
            e.printStackTrace();
            jSONObject = null;
        }
        Object[] objArr = new Object[1];
        objArr[0] = jSONObject == null ? "" : jSONObject.toString();
        jsFunctionCallback.callback(objArr);
    }

    @AjxMethod("getCarInfoList")
    public void getCarInfoList(String str, JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(bsl.a().c(getCarType(str)));
    }

    @AjxMethod("startCarInfoSync")
    public void startCarInfoSync() {
        bim.aa().z();
    }

    @AjxMethod("setOftenUsedCarPlateNum")
    public void setOftenUsedCarPlateNum(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (bsl.a().a(getCarType(str2), str) == 0) {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_SUCCESS));
            return;
        }
        jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_FAIL));
    }

    @AjxMethod("getOftenUsedCarPlateNum")
    public void getOftenUsedCarPlateNum(String str, JsFunctionCallback jsFunctionCallback) {
        Car b = bsl.a().b(getCarType(str));
        if (b == null || TextUtils.isEmpty(b.plateNum)) {
            jsFunctionCallback.callback("");
            return;
        }
        jsFunctionCallback.callback(b.plateNum);
    }

    @AjxMethod("getOftenUsedCarInfo")
    public void getOftenUsedCarInfo(JsFunctionCallback jsFunctionCallback) {
        Car b = bsl.a().b(1);
        if (b != null) {
            jsFunctionCallback.callback(JSON.toJSONString(b));
            return;
        }
        Car b2 = bsl.a().b(2);
        if (b2 != null) {
            jsFunctionCallback.callback(JSON.toJSONString(b2));
            return;
        }
        List<Car> a2 = bsl.a().a(-1);
        if (a2 == null || a2.size() <= 0) {
            jsFunctionCallback.callback("");
            return;
        }
        jsFunctionCallback.callback(JSON.toJSONString(a2.get(0)));
    }

    @AjxMethod(invokeMode = "sync", value = "getOftenUsedCarInfoSync")
    public String getOftenUsedCarInfoSync() {
        Car b = bsl.a().b(1);
        if (b != null) {
            return JSON.toJSONString(b);
        }
        Car b2 = bsl.a().b(2);
        if (b2 != null) {
            return JSON.toJSONString(b2);
        }
        List<Car> a2 = bsl.a().a(-1);
        return (a2 == null || a2.size() <= 0) ? "" : JSON.toJSONString(a2.get(0));
    }

    @AjxMethod("needShowSynTip")
    public void needShowSynTip(JsFunctionCallback jsFunctionCallback) {
        if (bnm.a()) {
            jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_SUCCESS));
            return;
        }
        jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_FAIL));
    }

    @AjxMethod("setSynTipShown")
    public void setSynTipShown(JsFunctionCallback jsFunctionCallback) {
        bnm.a(false);
        jsFunctionCallback.callback(Integer.valueOf(this.AJX_CODE_SUCCESS));
    }

    @AjxMethod("showOperatingActivity")
    public void showOperatingActivity(String str, JsFunctionCallback jsFunctionCallback) {
        if ("1".equals(str)) {
            requestActivities();
        }
    }

    @AjxMethod("hideOperatingActivity")
    public void hideOperatingActivity() {
        cancelActivities();
    }

    @AjxMethod("setSyncCarAutoMerge")
    public void setSyncCarAutoMerge(String str) {
        if ("1".equals(str)) {
            bnm.b(true);
        } else {
            bnm.b(false);
        }
    }

    @AjxMethod("getCarProvince")
    public void getCarProvince(JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(getProName(getProvinceCode()));
    }

    @AjxMethod("goNativePage")
    @Deprecated
    public void goNativePage(String str, final String str2, JsFunctionCallback jsFunctionCallback) {
        if ("scan".equals(str)) {
            kj.a(AMapAppGlobal.getTopActivity(), new String[]{"android.permission.CAMERA"}, (b) new b() {
                public final void run() {
                    ModuleCarOwner.this.openCarLicenseScan(str2);
                }

                public final void reject() {
                    ModuleCarOwner.this.openCarLicenseScan(str2);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    @Deprecated
    public void openCarLicenseScan(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("carInfo", CarInfo.JsonToCarInfo(str));
            pageContext.startPageForResult(CarLicenseScanPage.class, pageBundle, 1002);
        }
    }

    @AjxMethod("openAmapOnline")
    public void openAmapOnline(String str) {
        if (TruckStatutePage.equals(str)) {
            aja aja = new aja(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.TRUCK_STATUTE_URL));
            aix aix = (aix) defpackage.esb.a.a.a(aix.class);
            if (aix != null) {
                aix.a(AMapPageUtil.getPageContext(), aja);
            }
        }
    }

    @AjxMethod("startCarListPageForDriveAchievement")
    public void startCarListPageForDriveAchievement() {
        bid pageContext = AMapPageUtil.getPageContext();
        PageBundle pageBundle = new PageBundle();
        CarInfo carInfo = new CarInfo();
        carInfo.entryType = "DrivingCheivement";
        pageBundle.putString("jsData", CarInfo.CarInfoToJson(carInfo).toString());
        pageBundle.putString("url", "path://amap_bundle_carowner/src/car_owner/CarListViewController.page.js");
        pageContext.startPage(Ajx3Page.class, pageBundle);
    }

    public String getProName(String str) {
        String str2 = "京";
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        int length = sProvinceCode.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (sProvinceCode[i].equals(str)) {
                str2 = sProvinceNames[i];
                break;
            } else {
                i++;
            }
        }
        return str2;
    }

    private int getCarType(String str) {
        if ("1".equals(str)) {
            return 1;
        }
        return "2".equals(str) ? 2 : -1;
    }

    public void requestActivities() {
        if (this.iOperationsActivitiesService == null) {
            this.con = AMapPageUtil.getPageContext();
            this.iOperationsActivitiesService = (ctl) defpackage.esb.a.a.a(ctl.class);
            if (this.iOperationsActivitiesService != null) {
                this.iOperationsActivitiesService.a("6", new Callback<ctm>() {
                    public void error(Throwable th, boolean z) {
                        ModuleCarOwner.this.iOperationsActivitiesService = null;
                    }

                    public void callback(ctm ctm) {
                        if (ctm != null && ctm.a == 1) {
                            String str = ctm.c;
                            if (!TextUtils.isEmpty(ctm.c) && (ModuleCarOwner.this.con instanceof AbstractBasePage)) {
                                try {
                                    ModuleCarOwner.this.iOperationsActivitiesService.a((AbstractBasePage) ModuleCarOwner.this.con, "6", str);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public void cancelActivities() {
        if (!(this.iOperationsActivitiesService == null || this.con == null)) {
            this.iOperationsActivitiesService.a("6");
        }
        this.iOperationsActivitiesService = null;
        this.con = null;
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        bim.aa().b((bja) this.mSyncMergeEndListenerForCarOwnerAccountLogin);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r0 = new org.json.JSONObject(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
        r0 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x002b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.map.db.model.Car JsonToCarInfo(java.lang.String r5) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0029 }
            r0.<init>(r5)     // Catch:{ JSONException -> 0x0029 }
            java.lang.String r2 = "data"
            org.json.JSONObject r0 = r0.getJSONObject(r2)     // Catch:{ JSONException -> 0x0029 }
            java.lang.String r2 = "sourcePage"
            java.lang.String r2 = r0.optString(r2)     // Catch:{ JSONException -> 0x0029 }
            java.lang.String r3 = "from"
            java.lang.String r3 = r0.optString(r3)     // Catch:{ JSONException -> 0x0027 }
            java.lang.String r4 = "carInfo"
            org.json.JSONObject r0 = r0.optJSONObject(r4)     // Catch:{ JSONException -> 0x002b }
            goto L_0x0032
        L_0x0027:
            r3 = r1
            goto L_0x002b
        L_0x0029:
            r2 = r1
            r3 = r2
        L_0x002b:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0031 }
            r0.<init>(r5)     // Catch:{ JSONException -> 0x0031 }
            goto L_0x0032
        L_0x0031:
            r0 = r1
        L_0x0032:
            if (r0 != 0) goto L_0x0035
            return r1
        L_0x0035:
            com.autonavi.map.db.model.Car r5 = new com.autonavi.map.db.model.Car
            r5.<init>()
            r5.sourcePage = r2
            r5.from = r3
            java.lang.String r1 = plateNum
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.plateNum = r1
            java.lang.String r1 = vehicleCode
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.vehicleCode = r1
            java.lang.String r1 = engineNum
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.engineNum = r1
            java.lang.String r1 = frameNum
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.frameNum = r1
            java.lang.String r1 = telephone
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.telphone = r1
            java.lang.String r1 = validityPeriod
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.validityPeriod = r1
            java.lang.String r1 = ocrRequestId
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.ocrRequestId = r1
            java.lang.String r1 = vehicleMsg
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.vehicleMsg = r1
            java.lang.String r1 = vehicleLogo
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.vehicleLogo = r1
            java.lang.String r1 = vehicleType
            int r1 = r0.optInt(r1)
            r5.vehicleType = r1
            java.lang.String r1 = violationReminder
            int r1 = r0.optInt(r1)
            r5.violationReminder = r1
            java.lang.String r1 = checkReminder
            int r1 = r0.optInt(r1)
            r5.checkReminder = r1
            java.lang.String r1 = limitReminder
            int r1 = r0.optInt(r1)
            r5.limitReminder = r1
            java.lang.String r1 = truckAvoidWeightLimit
            r2 = 1
            int r1 = r0.optInt(r1, r2)
            r5.truckAvoidWeightLimit = r1
            java.lang.String r1 = createTime
            long r1 = r0.optLong(r1)
            r5.createTime = r1
            java.lang.String r1 = updateTime
            long r1 = r0.optLong(r1)
            r5.updateTime = r1
            java.lang.String r1 = vehiclePowerType
            int r1 = r0.optInt(r1)
            r5.vehiclePowerType = r1
            int r1 = r5.vehicleType
            r2 = 2
            if (r1 != r2) goto L_0x010a
            java.lang.String r1 = truckInfo
            org.json.JSONObject r0 = r0.optJSONObject(r1)
            java.lang.String r1 = truckType
            int r1 = r0.optInt(r1)
            r5.truckType = r1
            java.lang.String r1 = truckLength
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.length = r1
            java.lang.String r1 = truckWidth
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.width = r1
            java.lang.String r1 = truckHeight
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.height = r1
            java.lang.String r1 = truckCapacity
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.capacity = r1
            java.lang.String r1 = truckWeight
            java.lang.String r1 = getStringSafe(r0, r1)
            r5.weight = r1
            java.lang.String r1 = axleNum
            java.lang.String r0 = getStringSafe(r0, r1)
            r5.axleNum = r0
        L_0x010a:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.carownerservice.ajx.ModuleCarOwner.JsonToCarInfo(java.lang.String):com.autonavi.map.db.model.Car");
    }

    private static String getStringSafe(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return "";
        }
        String optString = jSONObject.optString(str);
        return optString.equals("null") ? "" : optString;
    }

    private String getUid() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }

    @AjxMethod("firecCarOwnerAccountLogin")
    public void firecCarOwnerAccountLogin(final JsFunctionCallback jsFunctionCallback, final JsFunctionCallback jsFunctionCallback2) {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            bim.aa().a((JsFunctionCallback) new JsFunctionCallback() {
                public final boolean isForMock() {
                    return false;
                }

                public final Object callback(Object... objArr) {
                    jsFunctionCallback.callback("1");
                    bim.aa().a((biy) new biy() {
                        public final void updateSuccess() {
                            if (!bnm.a) {
                                ModuleCarOwner.this.mSyncMergeEndListenerForCarOwnerAccountLogin.b = false;
                                jsFunctionCallback2.callback("1");
                                return;
                            }
                            ModuleCarOwner.this.mSyncMergeEndListenerForCarOwnerAccountLogin.b = true;
                            bnm.a = false;
                        }
                    });
                    bim.aa().a((bix) new bix() {
                        public final void a() {
                            jsFunctionCallback2.callback("0");
                        }
                    });
                    bim.aa().a((bja) ModuleCarOwner.this.mSyncMergeEndListenerForCarOwnerAccountLogin);
                    a aVar = ModuleCarOwner.this.mSyncMergeEndListenerForCarOwnerAccountLogin;
                    JsFunctionCallback jsFunctionCallback = jsFunctionCallback2;
                    if (jsFunctionCallback != null) {
                        aVar.a = new WeakReference<>(jsFunctionCallback);
                    } else {
                        aVar.a = null;
                    }
                    return null;
                }
            });
            iAccountService.a(AMapPageUtil.getPageContext(), (anq) null);
        }
    }

    public String getProvinceCode() {
        String valueOf = String.valueOf((long) LocationInstrument.getInstance().getLatestPosition().getAdCode());
        return (TextUtils.isEmpty(valueOf) || valueOf.length() < 2) ? "" : valueOf.substring(0, 2);
    }
}
