package com.alipay.mobile.tinyappcommon.tinypermission;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.nebulaapp.Config;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickNegativeListener;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5PermissionCallBack;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.H5LocationPlugin;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.nebula.provider.H5TinyAppRemoteLogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.plugin.H5LongClickPlugin;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.alipay.mobile.tinyappcommon.a.c;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl;
import com.autonavi.bundle.carownerservice.ajx.ModuleCarOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: H5ApiBizPermissionManager */
public final class a {
    private static final ConcurrentHashMap<String, String> b = new ConcurrentHashMap<>();
    private static final List<String> c = new ArrayList();
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, List<C0103a>> a = new ConcurrentHashMap<>();

    /* renamed from: com.alipay.mobile.tinyappcommon.tinypermission.a$a reason: collision with other inner class name */
    /* compiled from: H5ApiBizPermissionManager */
    private class C0103a {
        H5Event a;
        H5BridgeContext b;

        public C0103a(H5Event h5Event, H5BridgeContext h5BridgeContext) {
            this.a = h5Event;
            this.b = h5BridgeContext;
        }
    }

    static {
        b.put(H5LocationPlugin.GET_LOCATION, "\"%s\" 想使用您的当前位置");
        b.put(H5LocationPlugin.GET_CURRENT_LOCATION, "\"%s\" 想使用您的当前位置");
        b.put(H5LocationPlugin.START_CONTINUOUS_LOCATION, "\"%s\" 想使用您的当前位置");
        b.put("scan", "\"%s\" 想使用您的相机");
        b.put("chooseImage", "\"%s\" 想使用您的%s");
        b.put("chooseVideo", "\"%s\" 想使用您的%s");
        b.put(H5LongClickPlugin.SAVE_IMAGE, "\"%s\" 想使用您的相册");
        b.put("startAudioRecord", "\"%s\" 想使用您的麦克风");
        b.put("stopAudioRecord", "\"%s\" 想使用您的麦克风");
        b.put("cancelAudioRecord", "\"%s\" 想使用您的麦克风");
        b.put("saveVideoToPhotosAlbum", "\"%s\" 想使用您的相册");
        b.put("getVehicleModelInfos", "\"%s\" 想获取你的车辆信息（车型等）");
        c.add(Config.ABOUT_APP_ID);
        c.add(Config.FEEDBACK_APP_ID);
    }

    public static boolean a(String action, String appId, JSONObject params) {
        try {
            if (H5EventHandlerServiceImpl.tradePay.equals(action)) {
                return b(action, appId, params);
            }
        } catch (Throwable e) {
            H5Log.e((String) "H5ApiBizPermissionManager", "checkBizPermission...e=" + e);
        }
        return true;
    }

    private static boolean b(String action, String appId, JSONObject params) {
        String orderInfo = H5Utils.getString(params, (String) "orderStr");
        if (TextUtils.isEmpty(orderInfo) || !orderInfo.contains("app_id=")) {
            return true;
        }
        String orderInfo2 = orderInfo.substring(orderInfo.indexOf("app_id="));
        String bizAppId = orderInfo2.substring(7, orderInfo2.indexOf("&"));
        H5Log.d("H5ApiBizPermissionManager", "checkBizPermission...bizAppId=" + bizAppId);
        if (TextUtils.equals(appId, bizAppId)) {
            return true;
        }
        Performance performance = new Performance();
        performance.setSubType("TINY_APP");
        performance.setParam1(action);
        performance.setParam2(appId);
        performance.addExtParam("bizAppId", bizAppId);
        LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_WEBAPP, performance);
        if (TinyAppConfig.getInstance().tradePayCheck()) {
            return false;
        }
        return true;
    }

    public final boolean a(Context context, String appId, H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (TextUtils.isEmpty(h5Event.getAction())) {
            return false;
        }
        return b(context, appId, h5Event, h5BridgeContext);
    }

    private boolean b(Context context, String appId, H5Event h5Event, H5BridgeContext h5BridgeContext) {
        String action = h5Event.getAction();
        if (TextUtils.isEmpty(b.get(action))) {
            return false;
        }
        if (!TinyAppConfig.getInstance().getPermissionDialogSwitch()) {
            H5Log.d("H5ApiBizPermissionManager", "checkShowPermissionDialog...dialogSwitch closed");
            return false;
        }
        List<String> whiteList = TinyAppConfig.getInstance().getPermissionDialogWhitelist();
        if (whiteList != null) {
            for (String whiteAppId : whiteList) {
                if (appId.equals(whiteAppId)) {
                    H5Log.d("H5ApiBizPermissionManager", "checkShowPermissionDialog...in white list");
                    return false;
                }
            }
        }
        if (c.contains(appId)) {
            H5Log.d("H5ApiBizPermissionManager", "checkShowPermissionDialog...in preset white list");
            return false;
        }
        List objectList = this.a.get(e(action));
        if (objectList != null) {
            H5Log.d("H5ApiBizPermissionManager", "checkShowPermissionDialog...already has dialog..." + action);
            objectList.add(new C0103a(h5Event, h5BridgeContext));
            return true;
        }
        String alreadyAuthed = null;
        HashMap authMap = null;
        JSONObject params = h5Event.getParam();
        HashMap paramMap = new HashMap();
        if (!"chooseImage".equals(action) && !"chooseVideo".equals(action)) {
            alreadyAuthed = H5SharedPreferenceStorage.getInstance().getString(c(appId, action));
        } else if (params != null) {
            JSONArray paramArray = params.getJSONArray(Key.SOURCE_TYPE);
            if (paramArray == null || paramArray.isEmpty()) {
                paramMap.put("album", "相册");
                paramMap.put(WalletTinyappUtils.CONST_SCOPE_CAMERA, "相机");
            } else {
                if (paramArray.contains("album")) {
                    paramMap.put("album", "相册");
                }
                if (paramArray.contains(WalletTinyappUtils.CONST_SCOPE_CAMERA)) {
                    paramMap.put(WalletTinyappUtils.CONST_SCOPE_CAMERA, "相机");
                }
            }
            boolean hasAuthed = true;
            authMap = new HashMap();
            for (Entry entry : paramMap.entrySet()) {
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                String authed = H5SharedPreferenceStorage.getInstance().getString(c(appId, key));
                if (!"1".equals(authed)) {
                    authMap.put(key, val);
                    hasAuthed = false;
                    if (alreadyAuthed == null) {
                        alreadyAuthed = authed;
                    } else if (!"0".equals(authed)) {
                        alreadyAuthed = authed;
                    }
                }
            }
            if (hasAuthed) {
                alreadyAuthed = "1";
            }
        }
        return a(context, appId, h5Event, h5BridgeContext, authMap, alreadyAuthed);
    }

    private boolean a(Context context, String appId, H5Event h5Event, H5BridgeContext h5BridgeContext, Map<String, String> authMap, String authed) {
        if (!"1".equals(authed)) {
            return a(context, appId, h5Event, h5BridgeContext, authMap);
        }
        a(h5Event);
        return false;
    }

    private void a(final H5Event event) {
        H5TinyAppRemoteLogProvider h5TinyAppLogProvider = (H5TinyAppRemoteLogProvider) H5Utils.getProvider(H5TinyAppRemoteLogProvider.class.getName());
        if (h5TinyAppLogProvider != null && h5TinyAppLogProvider.isRemoteOutputConnected(event)) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public final void run() {
                    H5TinyAppRemoteLogProvider h5TinyAppLogProvider = (H5TinyAppRemoteLogProvider) H5Utils.getProvider(H5TinyAppRemoteLogProvider.class.getName());
                    if (h5TinyAppLogProvider != null) {
                        JSONObject remoteMsg = new JSONObject();
                        remoteMsg.put((String) "category", (Object) ActionConstant.EXCEPTION_VIEW_TYPE_WARN);
                        remoteMsg.put((String) "description", (Object) "用户已经授权，不会再弹窗");
                        remoteMsg.put((String) H5TinyAppLogUtil.TINY_APP_STANDARD_LOGID, (Object) "JSAPI_authorization_9998");
                        remoteMsg.put((String) H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, (Object) "ide");
                        h5TinyAppLogProvider.sendStandardLogToRemoteOutput(event, remoteMsg);
                    }
                }
            });
        }
    }

    private boolean a(Context context, final String appId, H5Event h5Event, H5BridgeContext h5BridgeContext, final Map<String, String> authMap) {
        final String action = h5Event.getAction();
        String dialogContent = a(h5Event, action, appId, authMap);
        if (TextUtils.isEmpty(dialogContent)) {
            return false;
        }
        H5Log.d("H5ApiBizPermissionManager", "doShowPermissionDialog...action:" + action + ",appId:" + appId);
        AUNoticeDialog dialog = new AUNoticeDialog(context, null, dialogContent, "允许", "不允许");
        dialog.setPositiveListener(new OnClickPositiveListener() {
            public final void onClick() {
                a.this.d(action);
                if (authMap == null || authMap.isEmpty()) {
                    a.this.a(appId, action, true);
                    H5Log.d("H5ApiBizPermissionManager", "putString,key: " + a.c(appId, action));
                } else {
                    for (Entry entry : authMap.entrySet()) {
                        a.this.a(appId, (String) entry.getKey(), true);
                        H5Log.d("H5ApiBizPermissionManager", "putString,key: " + a.c(appId, (String) entry.getKey()));
                    }
                }
                a.this.a.remove(a.e(action));
            }
        });
        dialog.setNegativeListener(new OnClickNegativeListener() {
            public final void onClick() {
                a.this.c(action);
                H5Log.d("H5ApiBizPermissionManager", "checkShowPermissionDialog...cancel");
                if (authMap == null || authMap.isEmpty()) {
                    a.this.a(appId, action, false);
                } else {
                    for (Entry entry : authMap.entrySet()) {
                        a.this.a(appId, (String) entry.getKey(), false);
                        H5Log.d("H5ApiBizPermissionManager", "putString,key: " + a.c(appId, (String) entry.getKey()));
                    }
                }
                a.this.a.remove(a.e(action));
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        List resultObjects = new ArrayList();
        resultObjects.add(new C0103a(h5Event, h5BridgeContext));
        this.a.put(e(action), resultObjects);
        return true;
    }

    /* access modifiers changed from: private */
    public void c(String action) {
        List<C0103a> resultList = this.a.get(e(action));
        if (resultList != null) {
            for (C0103a resultObject : resultList) {
                if (resultObject.b != null) {
                    H5Log.d("H5ApiBizPermissionManager", "cancelAuth...action=" + action);
                    resultObject.b.sendError(2001, (String) "用户不允许授权");
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void d(String action) {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            List<C0103a> resultList = this.a.get(e(action));
            if (resultList != null) {
                for (C0103a resultObject : resultList) {
                    H5Log.d("H5ApiBizPermissionManager", "sendResult...action=" + action);
                    h5Service.sendEvent(resultObject.a, resultObject.b);
                }
            }
        }
    }

    private String a(H5Event event, String action, String appId, Map<String, String> param) {
        Object sb;
        String dialogContent = b.get(action);
        if (TextUtils.isEmpty(dialogContent)) {
            return null;
        }
        StringBuilder paramBuilder = null;
        if (param != null && !param.isEmpty()) {
            paramBuilder = new StringBuilder();
            for (Entry<String, String> entry : param.entrySet()) {
                paramBuilder.append((String) entry.getValue());
                paramBuilder.append(",");
            }
            paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        }
        if (TextUtils.isEmpty(f(appId))) {
            b(event);
        }
        Object[] objArr = new Object[2];
        objArr[0] = f(appId);
        if (paramBuilder == null) {
            sb = null;
        } else {
            sb = paramBuilder.toString();
        }
        objArr[1] = sb;
        return String.format(dialogContent, objArr);
    }

    /* access modifiers changed from: private */
    public static String c(String appId, String action) {
        String userId = TinyAppService.get().getUserId();
        if (TextUtils.isEmpty(action)) {
            return null;
        }
        return a(userId, appId, e(action));
    }

    /* access modifiers changed from: private */
    public static String e(String action) {
        if ("scan".equals(action)) {
            return WalletTinyappUtils.CONST_SCOPE_CAMERA;
        }
        if (H5LongClickPlugin.SAVE_IMAGE.equals(action) || "saveVideoToPhotosAlbum".equals(action)) {
            return "album";
        }
        if (action.contains("Location")) {
            return "location";
        }
        if (action.contains("AudioRecord")) {
            return WalletTinyappUtils.CONST_SCOPE_RECORD;
        }
        if ("getVehicleModelInfos".equals(action)) {
            return ModuleCarOwner.KEY_VEHICLE;
        }
        return action;
    }

    private static String a(String userId, String appId, String key) {
        return userId + "_" + appId + "_" + key;
    }

    private static String f(String appId) {
        return WalletTinyappUtils.getAppNameByAppId(appId);
    }

    private void b(final H5Event event) {
        H5TinyAppRemoteLogProvider h5TinyAppLogProvider = (H5TinyAppRemoteLogProvider) H5Utils.getProvider(H5TinyAppRemoteLogProvider.class.getName());
        if (h5TinyAppLogProvider != null && h5TinyAppLogProvider.isRemoteOutputConnected(event)) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public final void run() {
                    H5TinyAppRemoteLogProvider h5TinyAppLogProvider = (H5TinyAppRemoteLogProvider) H5Utils.getProvider(H5TinyAppRemoteLogProvider.class.getName());
                    if (h5TinyAppLogProvider != null) {
                        JSONObject remoteMsg = new JSONObject();
                        remoteMsg.put((String) "category", (Object) ActionConstant.EXCEPTION_VIEW_TYPE_WARN);
                        remoteMsg.put((String) "description", (Object) "授权弹框未获取到app名称，请检查是否已经上架");
                        remoteMsg.put((String) H5TinyAppLogUtil.TINY_APP_STANDARD_LOGID, (Object) "JSAPI_authorization_9999");
                        remoteMsg.put((String) H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, (Object) "ide");
                        h5TinyAppLogProvider.sendStandardLogToRemoteOutput(event, remoteMsg);
                    }
                }
            });
        }
    }

    public static Map<String, Boolean> a(String uid, String appid) {
        Map result = new HashMap();
        if (c(uid, appid, "location")) {
            result.put("location", Boolean.valueOf(b(uid, appid, (String) "location")));
        }
        if (c(uid, appid, WalletTinyappUtils.CONST_SCOPE_RECORD)) {
            result.put(WalletTinyappUtils.CONST_SCOPE_RECORD, Boolean.valueOf(b(uid, appid, (String) WalletTinyappUtils.CONST_SCOPE_RECORD)));
        }
        if (c(uid, appid, "album")) {
            result.put("album", Boolean.valueOf(b(uid, appid, (String) "album")));
        }
        if (c(uid, appid, WalletTinyappUtils.CONST_SCOPE_CAMERA)) {
            result.put(WalletTinyappUtils.CONST_SCOPE_CAMERA, Boolean.valueOf(b(uid, appid, (String) WalletTinyappUtils.CONST_SCOPE_CAMERA)));
        }
        return result;
    }

    private static boolean b(String uid, String appid, String key) {
        String innerKey = uid + "_" + appid + "_" + key;
        String value = H5SharedPreferenceStorage.getInstance().getString(innerKey);
        H5Log.d("H5ApiBizPermissionManager", "getPermissionByKey,key: " + innerKey + ",value: " + value);
        if (TextUtils.equals(value, "1")) {
            return true;
        }
        return false;
    }

    private static boolean c(String uid, String appid, String key) {
        String innerKey = uid + "_" + appid + "_" + key;
        String value = H5SharedPreferenceStorage.getInstance().getString(innerKey);
        H5Log.d("H5ApiBizPermissionManager", "isThePermissionApplied,key: " + innerKey + ",value: " + value);
        return !TextUtils.isEmpty(value);
    }

    public final void a(String uid, String appid, String key, boolean opened) {
        if (!c(uid, appid, key)) {
            H5Log.d("H5ApiBizPermissionManager", "the permission has never been applied, so you can't change it");
        } else {
            a(appid, key, opened);
        }
    }

    /* access modifiers changed from: private */
    public void a(final String appId, final String action, final boolean opened) {
        WalletTinyappUtils.submitTask(new Runnable() {
            public final void run() {
                String key = a.c(appId, action);
                String value = opened ? "1" : "0";
                H5SharedPreferenceStorage.getInstance().putString(appId, key, value);
                H5Log.d("H5ApiBizPermissionManager", "changePermissionState,key: " + key + ",opened: " + opened);
                try {
                    if (LiteProcessApi.isMainProcess()) {
                        c.a(appId, key, value);
                    }
                } catch (Throwable e) {
                    H5Log.e((String) "H5ApiBizPermissionManager", "changePermissionState...e=" + e);
                }
            }
        });
    }

    public static void a(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            String addressKey = c(appId, "location");
            H5SharedPreferenceStorage.getInstance().remove(addressKey);
            String cameraKey = c(appId, WalletTinyappUtils.CONST_SCOPE_CAMERA);
            H5SharedPreferenceStorage.getInstance().remove(cameraKey);
            String albumKey = c(appId, "album");
            H5SharedPreferenceStorage.getInstance().remove(albumKey);
            String recordKey = c(appId, WalletTinyappUtils.CONST_SCOPE_RECORD);
            H5SharedPreferenceStorage.getInstance().remove(recordKey);
            H5SharedPreferenceStorage.getInstance().remove(c(appId, ModuleCarOwner.KEY_VEHICLE));
            if (LiteProcessApi.isMainProcess()) {
                Bundle bundle = new Bundle();
                bundle.putString(addressKey, addressKey);
                bundle.putString(cameraKey, cameraKey);
                bundle.putString(albumKey, albumKey);
                bundle.putString(recordKey, recordKey);
                c.a(2, appId, bundle);
            }
        }
    }

    public final void a(final String appId, Context context, final H5PermissionCallBack h5PermissionCallBack) {
        if (h5PermissionCallBack != null) {
            H5Log.d("H5ApiBizPermissionManager", "checkWebARCameraPermission...appId=" + appId);
            String dialogContent = String.format(b.get("scan"), new Object[]{f(appId)});
            if ("1".equals(H5SharedPreferenceStorage.getInstance().getString(c(appId, "scan")))) {
                h5PermissionCallBack.allow();
                return;
            }
            AUNoticeDialog dialog = new AUNoticeDialog(context, null, dialogContent, "允许", "不允许");
            dialog.setPositiveListener(new OnClickPositiveListener() {
                public final void onClick() {
                    h5PermissionCallBack.allow();
                    H5Log.d("H5ApiBizPermissionManager", "checkWebARCameraPermission...allow");
                    a.this.a(appId, (String) "scan", true);
                }
            });
            dialog.setNegativeListener(new OnClickNegativeListener() {
                public final void onClick() {
                    h5PermissionCallBack.deny();
                    H5Log.d("H5ApiBizPermissionManager", "checkWebARCameraPermission...cancel");
                    a.this.a(appId, (String) "scan", false);
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }
}
