package com.alipay.android.phone.inside.main.action;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.permission.IPermissionCallback;
import com.alipay.android.phone.inside.api.permission.IPermissionManager;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.ScanCodeV2;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.offlinecode.rpc.ScardCenterRpcProvider;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScanActionV2 implements SdkAction {
    private boolean a = false;

    public enum ScanStatus {
        RISK("RISK"),
        NOT_SUPPORT("NOT_SUPPORT"),
        UNAUTHORIZED(ScardCenterRpcProvider.RES_VALUE_UNAUTH),
        ALIPAY_NOT_INSTALL(GenBusCodeService.CODE_ALIPAY_NOT_INSTALL),
        ALIPAY_SIGN_ERROR(GenBusCodeService.CODE_ALIPAY_SIGN_ERROR),
        ALIPAY_VERSION_UNMATCH("ALIPAY_VERSION_UNMATCH"),
        ALIPAY_DEAL("ALIPAY_DEAL"),
        RECOMMEND("RECOMMEND");
        
        private String mValue;

        private ScanStatus(String str) {
            this.mValue = str;
        }

        public final String getValue() {
            return this.mValue;
        }
    }

    public final String a() {
        return ActionEnum.SCAN_CODE_V2.getActionName();
    }

    public final OperationResult<ScanCodeV2> a(JSONObject jSONObject) {
        final OperationResult<ScanCodeV2> operationResult = new OperationResult<>(ScanCodeV2.SUCCESS, ActionEnum.SCAN_CODE_V2.getActionName());
        Bundle bundle = new Bundle();
        String optString = jSONObject.optString(GlobalConstants.CODE_TYPE, "");
        bundle.putString(GlobalConstants.CODE_TYPE, optString);
        bundle.putString("code", jSONObject.optString("code", ""));
        LoggerFactory.d().a("scan", BehaviorType.EVENT, "ScanCodeV2CodeTypeDist").g = optString;
        this.a = jSONObject.optBoolean("routeDirectly", false);
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = 110;
        }
        Application a2 = LauncherApplication.a();
        final Bundle bundle2 = new Bundle();
        ServiceExecutor.a("SCAN_CODE_SERVICE_V2", bundle, new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                Bundle bundle = (Bundle) obj;
                if (bundle != null) {
                    bundle2.putAll(bundle);
                }
                synchronized (bundle2) {
                    bundle2.notifyAll();
                }
            }

            public void onException(Throwable th) {
                String message = th.getMessage();
                if (TextUtils.isEmpty(message) || !message.contains("[7003]")) {
                    operationResult.setCode(ScanCodeV2.INNER_EX);
                } else {
                    operationResult.setCode(ScanCodeV2.TIME_EX);
                }
                synchronized (bundle2) {
                    bundle2.notifyAll();
                }
            }
        });
        long b = b(jSONObject);
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (bundle2) {
            try {
                bundle2.wait(b);
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
        }
        if (System.currentTimeMillis() - currentTimeMillis >= b - 100) {
            operationResult.setCode(ScanCodeV2.TIMEOUT_EX);
        }
        operationResult.setResult(a(a2, parseInt, bundle2));
        return operationResult;
    }

    private static long b(JSONObject jSONObject) {
        int i = 10;
        try {
            int parseInt = Integer.parseInt(jSONObject.optString("timeout", "9"));
            if (parseInt > 0) {
                i = parseInt;
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return (long) (i * 1000);
    }

    private String a(Context context, int i, Bundle bundle) {
        ScanStatus scanStatus;
        JSONObject a2 = a(bundle);
        boolean z = false;
        a2.optBoolean("success", false);
        boolean optBoolean = a2.optBoolean("routeHasRisk", false);
        a2.optBoolean("routeSupport", false);
        JSONObject optJSONObject = a2.optJSONObject("supportParams");
        JSONArray optJSONArray = a2.optJSONArray("recommendChannels");
        if (optBoolean) {
            scanStatus = ScanStatus.RISK;
        } else {
            String str = "";
            if (optJSONObject != null) {
                str = optJSONObject.optString("alipayRouteScheme", "");
            }
            if (!TextUtils.isEmpty(str)) {
                WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(context, i);
                if (checkAlipayStatus == WalletStatusEnum.SUCCESS) {
                    if (optJSONObject != null) {
                        optJSONObject.optString("alipayProductVersion", "");
                    }
                    if (b()) {
                        ScanStatus scanStatus2 = ScanStatus.ALIPAY_DEAL;
                        String str2 = "";
                        if (optJSONObject != null) {
                            str2 = optJSONObject.optString("alipayRouteScheme", "");
                        }
                        try {
                            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str2));
                            intent.setPackage("com.eg.android.AlipayGphone");
                            intent.setFlags(268435456);
                            if (this.a) {
                                intent.putExtra("directly", true);
                            }
                            context.startActivity(intent);
                        } catch (Throwable th) {
                            LoggerFactory.e().a((String) "scan", (String) "AlipayRouteSchemeEx", th);
                        }
                        scanStatus = scanStatus2;
                    } else {
                        scanStatus = ScanStatus.UNAUTHORIZED;
                    }
                } else {
                    scanStatus = checkAlipayStatus == WalletStatusEnum.NOT_INSTALL ? ScanStatus.ALIPAY_NOT_INSTALL : checkAlipayStatus == WalletStatusEnum.SIGN_ERROR ? ScanStatus.ALIPAY_SIGN_ERROR : checkAlipayStatus == WalletStatusEnum.VERSION_UNMATCH ? ScanStatus.ALIPAY_VERSION_UNMATCH : null;
                }
            } else {
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    z = true;
                }
                scanStatus = z ? ScanStatus.RECOMMEND : ScanStatus.NOT_SUPPORT;
            }
        }
        try {
            a2.put("scanStatus", scanStatus.getValue());
            LoggerFactory.d().a("scan", BehaviorType.EVENT, "ScanCodeV2ReqResult").g = scanStatus.getValue();
        } catch (Throwable th2) {
            LoggerFactory.f().c((String) "inside", th2);
        }
        return a2.toString();
    }

    private boolean b() {
        IPermissionManager iPermissionManager;
        Application a2 = LauncherApplication.a();
        try {
            iPermissionManager = (IPermissionManager) Class.forName("com.alipay.android.phone.inside.PermissionManagerImpl").newInstance();
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            iPermissionManager = null;
        }
        if (iPermissionManager == null) {
            LoggerFactory.f().e("ScanActionV2::checkAuthority", "permissionManager == null");
            return true;
        }
        final Object obj = new Object();
        final Bundle bundle = new Bundle();
        LoggerFactory.d().a("scan", BehaviorType.EVENT, "ScanCodeV2PermissionCheck");
        iPermissionManager.precheck(a2, new IPermissionCallback() {
            public boolean onStartActivity(Bundle bundle) {
                return false;
            }

            public void onEnd(boolean z) {
                LoggerFactory.d().a("scan", BehaviorType.EVENT, "ScanCodeV2PermissionCheckResult").g = String.valueOf(z);
                bundle.putBoolean("grant", z);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        });
        synchronized (obj) {
            try {
                obj.wait();
            } catch (Throwable th2) {
                LoggerFactory.f().c((String) "inside", th2);
            }
        }
        return bundle.getBoolean("grant", true);
    }

    private static JSONObject a(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        String string = bundle.getString("result", null);
        if (TextUtils.isEmpty(string)) {
            return jSONObject;
        }
        try {
            return new JSONObject(string);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return jSONObject;
        }
    }
}
