package com.alipay.mobile.tinyappcustom.h5plugin.openauth;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.mobile.antui.dialog.AUImageDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickNegativeListener;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.monitor.track.TrackIntegrator;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5OpenAuthProvider.OnGetAuthListener;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcustom.biz.auth.TinyAppAuthBridge;
import com.alipay.mobile.tinyappcustom.h5plugin.MiniProgramUtils;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.model.AuthAgreementModelPB;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.EntryStringString;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.MapStringString;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.WalletAuthExecuteRequestPB;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.WalletAuthSkipRequestPB;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.result.WalletAuthExecuteResultPB;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.result.WalletAuthSkipResultPB;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5LinkMovementMethod;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5LinkMovementMethod.OnLinkClickListener;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5OpenAuthClickableSpan;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5OpenAuthDialog;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5OpenAuthNoticeDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class H5OpenAuthHelper {
    /* access modifiers changed from: private */
    public Activity a;
    private OnGetAuthListener b;
    private H5BridgeContext c;
    private String d;

    private class H5AuthRunnable implements Runnable {
        /* access modifiers changed from: private */
        public WalletAuthSkipResultPB b;
        /* access modifiers changed from: private */
        public String c;
        /* access modifiers changed from: private */
        public String d;
        /* access modifiers changed from: private */
        public List<String> e;
        /* access modifiers changed from: private */
        public boolean f;
        /* access modifiers changed from: private */
        public String g;
        /* access modifiers changed from: private */
        public MapStringString h;
        /* access modifiers changed from: private */
        public MapStringString i;

        public H5AuthRunnable(WalletAuthSkipResultPB authSkipResultPB, String appId, String url, List<String> scopeNicks, boolean showErrorTip, String isvAppId, MapStringString extInfo, MapStringString appExtInfo) {
            this.b = authSkipResultPB;
            this.c = appId;
            this.d = url;
            this.e = scopeNicks;
            this.f = showErrorTip;
            this.g = isvAppId;
            this.h = extInfo;
            this.i = appExtInfo;
        }

        public void run() {
            List<String> authText = this.b.authContentResult.authText;
            String appName = this.b.authContentResult.appName;
            List<AuthAgreementModelPB> protocol = this.b.authContentResult.agreements;
            if (H5OpenAuthHelper.this.a != null && !H5OpenAuthHelper.this.a.isFinishing()) {
                final H5OpenAuthDialog h5OpenAuthDialog = new H5OpenAuthDialog(H5OpenAuthHelper.this.a);
                AnonymousClass1 r0 = new OnClickListener() {
                    public void onClick(View v) {
                        H5Log.d("H5OpenAuthHelper", "h5OpenAuthDialog click begin invoke auth");
                        TrackIntegrator.getInstance().logPageEndWithSpmId("a143.b5627", h5OpenAuthDialog, "acctInfoAuth", null);
                        H5OpenAuthHelper.b("a143.b5627.c12343.d22545", "clicked", "acctInfoAuth");
                        h5OpenAuthDialog.cancel();
                        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                            public void run() {
                                H5OpenAuthHelper.this.a(H5AuthRunnable.this.c, H5AuthRunnable.this.d, H5AuthRunnable.this.e, H5AuthRunnable.this.f, H5AuthRunnable.this.g, H5AuthRunnable.this.h, H5AuthRunnable.this.i);
                            }
                        });
                    }
                };
                h5OpenAuthDialog.setOnConfirmClickListener(r0);
                AnonymousClass2 r02 = new OnClickListener() {
                    public void onClick(View v) {
                        H5Log.d("H5OpenAuthHelper", "h5OpenAuthDialog click close");
                        TrackIntegrator.getInstance().logPageEndWithSpmId("a143.b5627", h5OpenAuthDialog, "acctInfoAuth", null);
                        H5OpenAuthHelper.b("a143.b5627.c12343.d22546", "clicked", "acctInfoAuth");
                        h5OpenAuthDialog.cancel();
                        H5OpenAuthHelper.this.a(H5AuthRunnable.this.b, H5AuthRunnable.this.c, H5AuthRunnable.this.d, H5AuthRunnable.this.e, H5AuthRunnable.this.f, H5AuthRunnable.this.g, H5AuthRunnable.this.h, H5AuthRunnable.this.i);
                    }
                };
                h5OpenAuthDialog.setOnCloseClickListener(r02);
                h5OpenAuthDialog.getAuthContentTitle().setText(Html.fromHtml("授权 <b>appName</b> 获取以下信息为你服务".replace("appName", appName)));
                LinearLayout authContentContainer = h5OpenAuthDialog.getAuthContentContainer();
                LayoutParams layoutParams = new LayoutParams(-2, -2);
                if (authText != null && !authText.isEmpty()) {
                    for (String item : authText) {
                        LinearLayout linearLayout = new LinearLayout(H5OpenAuthHelper.this.a);
                        linearLayout.setOrientation(0);
                        TextView dot = new TextView(H5OpenAuthHelper.this.a);
                        dot.setText("• ");
                        dot.setTextSize(1, 15.0f);
                        dot.setTextColor(-6710887);
                        dot.setLayoutParams(layoutParams);
                        TextView textView = new TextView(H5OpenAuthHelper.this.a);
                        textView.setText(item.trim());
                        textView.setTextSize(1, 15.0f);
                        textView.setTextColor(-6710887);
                        textView.setPadding(0, 1, 0, 1);
                        textView.setLineSpacing((float) H5DimensionUtil.dip2px(H5OpenAuthHelper.this.a, 7.0f), 1.0f);
                        textView.setLayoutParams(layoutParams);
                        linearLayout.addView(dot);
                        linearLayout.addView(textView);
                        layoutParams.setMargins(0, 10, 0, 0);
                        linearLayout.setLayoutParams(layoutParams);
                        authContentContainer.addView(linearLayout);
                    }
                    H5OpenAuthHelper.b("a143.b5627.c12341", BehavorID.EXPOSURE, "acctInfoAuth");
                }
                if (this.b.authContentResult.isvAgent.booleanValue()) {
                    TextView isvTipView = h5OpenAuthDialog.getAuthContentIsvTip();
                    isvTipView.setText(this.b.authContentResult.isvAgentDesc);
                    isvTipView.setVisibility(0);
                }
                if (protocol != null && !protocol.isEmpty()) {
                    H5LinkMovementMethod h5LinkMovementMethod = new H5LinkMovementMethod();
                    AnonymousClass3 r03 = new OnLinkClickListener() {
                        public void onLinkClick(String url) {
                            H5Log.d("H5OpenAuthHelper", "onLinkClick url is " + url);
                            H5OpenAuthHelper.b("a143.b5627.c12341.d22542", "clicked", "acctInfoAuth");
                            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                            if (h5ConfigProvider == null || BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_use_new_scheme"))) {
                                Bundle startParams = new Bundle();
                                startParams.putString("url", url);
                                startParams.putString("startMultApp", "YES");
                                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, "20000067", startParams);
                                return;
                            }
                            MiniProgramUtils.goToSchemeService(H5UrlHelper.parseUrl("alipays://platformapi/startapp?appId=20000067&startMultApp=YES&url=" + H5UrlHelper.encode(url)));
                        }
                    };
                    h5LinkMovementMethod.setOnLinkClickListener(r03);
                    TextView authProtocol = h5OpenAuthDialog.getAuthContentProtocol();
                    authProtocol.setMovementMethod(h5LinkMovementMethod);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("同意");
                    int size = protocol.size();
                    int[] segmentStart = new int[size];
                    int[] segmentEnd = new int[size];
                    int index = 0;
                    int cursor = 2;
                    for (AuthAgreementModelPB modelPB : protocol) {
                        stringBuilder.append(Token.SEPARATOR);
                        int cursor2 = cursor + 1;
                        segmentStart[index] = cursor2;
                        r0 = "《tagname》";
                        stringBuilder.append("《tagname》".replace("tagname", modelPB.name));
                        cursor = modelPB.name.length() + cursor2 + 1 + 1;
                        segmentEnd[index] = cursor;
                        index++;
                    }
                    SpannableString spannableString = new SpannableString(stringBuilder.toString());
                    int index2 = 0;
                    for (AuthAgreementModelPB modelPB2 : protocol) {
                        spannableString.setSpan(new H5OpenAuthClickableSpan(modelPB2.link), segmentStart[index2], segmentEnd[index2], 34);
                        index2++;
                    }
                    authProtocol.setText(spannableString);
                }
                try {
                    TrackIntegrator.getInstance().logPageStartWithSpmId("a143.b5627", h5OpenAuthDialog);
                    h5OpenAuthDialog.show();
                } catch (Throwable throwable) {
                    H5Log.e((String) "H5OpenAuthHelper", throwable);
                }
            }
        }
    }

    public H5OpenAuthHelper(Activity activity, OnGetAuthListener listener) {
        this.a = activity;
        this.b = listener;
    }

    public H5OpenAuthHelper(Activity activity, H5BridgeContext bridgeContext, String sessionid) {
        this.a = activity;
        this.c = bridgeContext;
        this.d = sessionid;
    }

    public void getAuthContentOrAutoAuth(String appId, String url, List<String> scopeNicks, String isvAppId, MapStringString extInfo, boolean showErrorTip, MapStringString appExtInfo) {
        WalletAuthSkipRequestPB requestPB = new WalletAuthSkipRequestPB();
        requestPB.appId = appId;
        requestPB.currentPageUrl = url;
        requestPB.fromSystem = "mobilegw_android";
        requestPB.scopeNicks = scopeNicks;
        requestPB.state = "QnJpbmcgc21hbGwgYW5kIGJlYXV0aWZ1bCBjaGFuZ2VzIHRvIHRoZSB3b3JsZA==";
        requestPB.isvAppId = isvAppId;
        requestPB.extInfo = extInfo;
        requestPB.appExtInfo = appExtInfo;
        try {
            String uuid = UUID.randomUUID().toString();
            TinyAppAuthBridge.get().executeSkipIdentifyAuth(uuid, a(requestPB));
            final WalletAuthSkipResultPB authSkipResultPB = a(true, uuid).getAuthContentOrAutoAuth(requestPB);
            if (authSkipResultPB == null) {
                return;
            }
            if (authSkipResultPB.isSuccess != null && !authSkipResultPB.isSuccess.booleanValue()) {
                H5Log.d("H5OpenAuthHelper", "getAuthContentOrAutoAuth rpc !isSuccess" + authSkipResultPB.errorCode + Token.SEPARATOR + authSkipResultPB.errorMsg);
                final boolean z = showErrorTip;
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        H5OpenAuthHelper.this.a(authSkipResultPB.errorCode, authSkipResultPB.errorMsg, z);
                    }
                });
            } else if (authSkipResultPB.canSkipAuth == null || !authSkipResultPB.canSkipAuth.booleanValue()) {
                H5Log.d("H5OpenAuthHelper", "getAuthContentOrAutoAuth rpc !canSkipAuth");
                if (authSkipResultPB.authContentResult != null) {
                    H5Log.d("H5OpenAuthHelper", "getAuthContentOrAutoAuth rpc begin present auth dialog");
                    H5Utils.runOnMain(new H5AuthRunnable(authSkipResultPB, appId, url, scopeNicks, showErrorTip, isvAppId, extInfo, appExtInfo));
                }
            } else {
                H5Log.d("H5OpenAuthHelper", "getAuthContentOrAutoAuth rpc canSkipAuth");
                if (authSkipResultPB.authExecuteResult != null) {
                    String authCode = authSkipResultPB.authExecuteResult.authCode;
                    H5Log.d("H5OpenAuthHelper", "getAuthContentOrAutoAuth rpc authCode is " + authCode);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) "authCode", (Object) authCode);
                    jsonObject.put((String) "authcode", (Object) authCode);
                    JSONArray successArray = new JSONArray();
                    if (authSkipResultPB.authExecuteResult.successScopes != null && !authSkipResultPB.authExecuteResult.successScopes.isEmpty()) {
                        for (String item : authSkipResultPB.authExecuteResult.successScopes) {
                            successArray.add(item);
                        }
                    }
                    jsonObject.put((String) "authSuccessScopes", (Object) successArray);
                    JSONObject errorObject = new JSONObject();
                    if (!(authSkipResultPB.authExecuteResult.errorScopes == null || authSkipResultPB.authExecuteResult.errorScopes.entries == null || authSkipResultPB.authExecuteResult.errorScopes.entries.isEmpty())) {
                        for (EntryStringString ess : authSkipResultPB.authExecuteResult.errorScopes.entries) {
                            errorObject.put(ess.key, (Object) ess.value);
                        }
                    }
                    jsonObject.put((String) "authErrorScopes", (Object) errorObject);
                    if (!(this.c == null || scopeNicks == null || !scopeNicks.contains("auth_user"))) {
                        H5Flag.setOpenAuthGrantFlag(this.d, true);
                    }
                    a(jsonObject);
                }
            }
        } catch (Exception e) {
            H5Log.e("H5OpenAuthHelper", "getAuthContentOrAutoAuth rpc exception ", e);
            a(e);
        }
    }

    /* access modifiers changed from: private */
    public void a(JSONObject obj) {
        if (this.b != null) {
            H5Log.d("H5OpenAuthHelper", "result for provider: " + obj.toString());
            this.b.onResult(obj);
        } else if (this.c != null) {
            H5Log.d("H5OpenAuthHelper", "result for jsbridge: " + obj.toString());
            this.c.sendBridgeResult(obj);
        }
    }

    private void a(Exception e) {
        JSONObject jsonObject = new JSONObject();
        if (e instanceof RpcException) {
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            jsonObject.put((String) "errorMessage", (Object) "Network Error");
        } else {
            jsonObject.put((String) "error", (Object) Integer.valueOf(10));
            if (e instanceof com.alipay.inside.android.phone.mrpc.core.RpcException) {
                int errCode = ((com.alipay.inside.android.phone.mrpc.core.RpcException) e).getCode();
                if (errCode == 15 || errCode == 16) {
                    jsonObject.put((String) "errorMessage", (Object) "Network Error");
                } else {
                    jsonObject.put((String) "errorMessage", (Object) e.toString());
                }
            } else {
                jsonObject.put((String) "errorMessage", (Object) e.toString());
            }
        }
        a(jsonObject);
    }

    /* access modifiers changed from: private */
    public void a(WalletAuthSkipResultPB authSkipResultPB, String appId, String url, List<String> scopeNicks, boolean showErrorTip, String isvAppId, MapStringString extInfo, MapStringString appExtInfo) {
        if (this.a != null && !this.a.isFinishing()) {
            final H5OpenAuthNoticeDialog h5OpenAuthNoticeDialog = new H5OpenAuthNoticeDialog(this.a, "提示", "取消授权，可能会使部分服务无法使用，或页面信息不完整", "重新授权", "我知道了", false);
            final H5OpenAuthNoticeDialog h5OpenAuthNoticeDialog2 = h5OpenAuthNoticeDialog;
            final WalletAuthSkipResultPB walletAuthSkipResultPB = authSkipResultPB;
            final String str = appId;
            final String str2 = url;
            final List<String> list = scopeNicks;
            final boolean z = showErrorTip;
            final String str3 = isvAppId;
            final MapStringString mapStringString = extInfo;
            final MapStringString mapStringString2 = appExtInfo;
            h5OpenAuthNoticeDialog.setPositiveListener(new OnClickPositiveListener() {
                public void onClick() {
                    H5Log.d("H5OpenAuthHelper", "h5OpenAuthNoticeDialog click auth again");
                    H5OpenAuthHelper.b("a143.b5627.c12342.d22544", "clicked", "acctInfoAuth");
                    h5OpenAuthNoticeDialog2.cancel();
                    if (walletAuthSkipResultPB.authContentResult != null) {
                        H5Utils.runOnMain(new H5AuthRunnable(walletAuthSkipResultPB, str, str2, list, z, str3, mapStringString, mapStringString2));
                    }
                }
            });
            h5OpenAuthNoticeDialog.setNegativeListener(new OnClickNegativeListener() {
                public void onClick() {
                    H5Log.d("H5OpenAuthHelper", "h5OpenAuthNoticeDialog click exit auth");
                    H5OpenAuthHelper.b("a143.b5627.c12342.d22543", "clicked", "acctInfoAuth");
                    h5OpenAuthNoticeDialog.cancel();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) "error", (Object) Integer.valueOf(11));
                    H5OpenAuthHelper.this.a(jsonObject);
                }
            });
            b("a143.b5627.c12342", BehavorID.EXPOSURE, "acctInfoAuth");
            h5OpenAuthNoticeDialog.show();
        }
    }

    /* access modifiers changed from: private */
    public void a(String appId, String url, List<String> scopeNicks, boolean showErrorTip, String isvAppId, MapStringString extInfo, MapStringString appExtInfo) {
        WalletAuthExecuteRequestPB authExecuteRequestPB = new WalletAuthExecuteRequestPB();
        authExecuteRequestPB.appId = appId;
        authExecuteRequestPB.currentPageUrl = url;
        authExecuteRequestPB.fromSystem = "mobilegw_android";
        authExecuteRequestPB.scopeNicks = scopeNicks;
        authExecuteRequestPB.state = "QnJpbmcgc21hbGwgYW5kIGJlYXV0aWZ1bCBjaGFuZ2VzIHRvIHRoZSB3b3JsZA==";
        authExecuteRequestPB.isvAppId = isvAppId;
        authExecuteRequestPB.extInfo = extInfo;
        authExecuteRequestPB.appExtInfo = appExtInfo;
        try {
            final WalletAuthExecuteResultPB resultPB = a(false, UUID.randomUUID().toString()).executeAuth(authExecuteRequestPB);
            if (resultPB == null) {
                return;
            }
            if (resultPB.isSuccess == null || resultPB.isSuccess.booleanValue()) {
                H5Log.d("H5OpenAuthHelper", "executeAuth rpc isSuccess");
                String authCode = resultPB.authCode;
                H5Log.d("H5OpenAuthHelper", "executeAuth rpc authCode is " + authCode);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "authCode", (Object) authCode);
                jsonObject.put((String) "authcode", (Object) authCode);
                JSONArray successArray = new JSONArray();
                if (resultPB.successScopes != null && !resultPB.successScopes.isEmpty()) {
                    for (String item : resultPB.successScopes) {
                        successArray.add(item);
                    }
                }
                jsonObject.put((String) "authSuccessScopes", (Object) successArray);
                JSONObject errorObject = new JSONObject();
                if (!(resultPB.errorScopes == null || resultPB.errorScopes.entries == null || resultPB.errorScopes.entries.isEmpty())) {
                    for (EntryStringString ess : resultPB.errorScopes.entries) {
                        errorObject.put(ess.key, (Object) ess.value);
                    }
                }
                jsonObject.put((String) "authErrorScopes", (Object) errorObject);
                if (!(this.c == null || scopeNicks == null || !scopeNicks.contains("auth_user"))) {
                    H5Log.d("H5OpenAuthHelper", "executeAuth setOpenAuthGrantFlag " + this.d);
                    H5Flag.setOpenAuthGrantFlag(this.d, true);
                }
                a(jsonObject);
                return;
            }
            H5Log.d("H5OpenAuthHelper", "executeAuth rpc !isSuccess " + resultPB.errorCode + Token.SEPARATOR + resultPB.errorMsg);
            final boolean z = showErrorTip;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5OpenAuthHelper.this.a(resultPB.errorCode, resultPB.errorMsg, z);
                }
            });
        } catch (Exception e) {
            H5Log.e("H5OpenAuthHelper", "executeAuth rpc exception ", e);
            a(e);
        }
    }

    /* access modifiers changed from: private */
    public void a(String errorCode, String errorMsg, boolean showErrorTip) {
        if (this.a != null && !this.a.isFinishing()) {
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) Integer.valueOf(15));
            result.put((String) "errorMessage", (Object) errorCode);
            result.put((String) "errorDesc", (Object) errorMsg);
            a(result);
            H5Log.d("H5OpenAuthHelper", "showBusinessFailedDialog showErrorTip: " + showErrorTip);
            if (showErrorTip) {
                AUImageDialog dialog = AUImageDialog.getInstance(this.a);
                dialog.setCanceledOnTouch(false);
                dialog.setTitle("服务正忙,请稍后再试");
                dialog.setSubTitle("耽误您的时间，我们深表歉意");
                dialog.setConfirmBtnText("确定");
                dialog.showWithoutAnim();
            }
        }
    }

    private static Oauth2AuthCodeFacade a(boolean needAuth, String uuid) {
        H5Log.d("H5OpenAuthHelper", "needAuth is " + needAuth);
        Map extParams = new HashMap();
        extParams.put("OpenAuthLogin", "YES");
        extParams.put("needOpenAuth", needAuth ? "YES" : "NO");
        extParams.put("bizSource", "tinyapp");
        extParams.put("cAuthUUID", uuid);
        return (Oauth2AuthCodeFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(Oauth2AuthCodeFacade.class, extParams);
    }

    public static MapStringString mapToMapStringString(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        MapStringString mapStringString = new MapStringString();
        mapStringString.entries = new ArrayList();
        for (String key : map.keySet()) {
            if (map.get(key) != null) {
                EntryStringString entryStringString = new EntryStringString();
                entryStringString.key = key;
                entryStringString.value = String.valueOf(map.get(key));
                mapStringString.entries.add(entryStringString);
            }
        }
        return mapStringString;
    }

    /* access modifiers changed from: private */
    public static void b(String seedId, String behavorId, String bizType) {
        Behavor behavor = new Behavor();
        behavor.setBehaviourPro(bizType);
        behavor.setSeedID(seedId);
        LoggerFactory.getBehavorLogger().event(behavorId, behavor);
    }

    private static Bundle a(WalletAuthSkipRequestPB requestPB) {
        Bundle requestParams = new Bundle();
        requestParams.putString("appId", requestPB.appId);
        requestParams.putString("currentPageUrl", requestPB.currentPageUrl);
        requestParams.putString("fromSystem", requestPB.fromSystem);
        requestParams.putString("isvAppId", requestPB.isvAppId);
        requestParams.putString("state", requestPB.state);
        if (requestPB.scopeNicks != null) {
            requestParams.putStringArrayList("scopeNicks", new ArrayList(requestPB.scopeNicks));
        }
        requestParams.putBundle("extInfo", a(requestPB.extInfo));
        requestParams.putBundle("appExtInfo", a(requestPB.appExtInfo));
        return requestParams;
    }

    private static Bundle a(MapStringString map) {
        if (map == null || map.entries == null) {
            return new Bundle();
        }
        Bundle bundle = new Bundle();
        for (EntryStringString entry : map.entries) {
            if (entry != null) {
                bundle.putString(entry.key, entry.value);
            }
        }
        return bundle;
    }
}
