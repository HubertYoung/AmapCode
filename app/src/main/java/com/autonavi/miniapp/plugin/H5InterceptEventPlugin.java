package com.autonavi.miniapp.plugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.android.nebulaapp.MiniAppAuthHelper;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthCallback;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.accountopenauth.OAuthLoginModel;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickNegativeListener;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.alipay.mobile.tinyappcustom.biz.auth.TinyAppAuthBridge;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.Oauth2AuthCodeFacade;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.WalletAuthCodeCreateReq;
import com.autonavi.minimap.R;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class H5InterceptEventPlugin extends H5SimplePlugin {
    private static final String CONFIG_AMAP_MINI_APP_JSAPI_WHITELIST = "amap_mini_app_jsapi_whitelist";
    private static final String CONFIG_AMAP_MINI_APP_USE_NEW_INTERCEPTOR = "amap_mini_app_use_new_interceptor";
    private static final String TAG = "H5InterceptEventPlugin";
    private JSONArray mJsonArray;

    public void onPrepare(H5EventFilter h5EventFilter) {
        super.onPrepare(h5EventFilter);
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String config = h5ConfigProvider.getConfig(CONFIG_AMAP_MINI_APP_JSAPI_WHITELIST);
            if (!TextUtils.isEmpty(config)) {
                this.mJsonArray = H5Utils.parseArray(config);
            }
        }
        if (this.mJsonArray != null && this.mJsonArray.size() > 0) {
            Iterator<Object> it = this.mJsonArray.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof String) {
                    LoggerFactory.getTraceLogger().debug(TAG, "onPrepare: action ".concat(String.valueOf(next)));
                    h5EventFilter.addAction((String) next);
                }
            }
        }
    }

    public boolean interceptEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (this.mJsonArray == null || this.mJsonArray.size() <= 0 || !this.mJsonArray.contains(h5Event.getAction())) {
            return false;
        }
        return needAuth(h5Event, h5BridgeContext);
    }

    /* access modifiers changed from: private */
    public boolean needAuth(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (useNewInterceptor()) {
            LoggerFactory.getTraceLogger().debug(TAG, "useNewInterceptor");
            return needAuthAutoLogin(h5Event, h5BridgeContext);
        }
        LoggerFactory.getTraceLogger().debug(TAG, "useOldInterceptor");
        return needAuthRpc(h5Event, h5BridgeContext);
    }

    private boolean needAuthAutoLogin(final H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        if (!TextUtils.isEmpty(LoggerFactory.getLogContext().getUserId())) {
            return false;
        }
        LoggerFactory.getTraceLogger().debug(TAG, "userId empty, need auth");
        H5Utils.runNotOnMain(H5ThreadType.URGENT, new Runnable() {
            public void run() {
                new MiniAppAuthHelper().authMiniApp(new IAccountOAuthCallback() {
                    public void onAuthResult(String str, String str2, String str3, Bundle bundle) {
                        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                            StringBuilder sb = new StringBuilder("auth failed, amapUid:");
                            sb.append(str);
                            sb.append(", alipayUid:");
                            sb.append(str2);
                            sb.append(", token:");
                            sb.append(str3);
                            traceLogger.debug(H5InterceptEventPlugin.TAG, sb.toString());
                            H5InterceptEventPlugin.this.showNotAuthedDialogIfNeed(h5Event, h5BridgeContext);
                            return;
                        }
                        H5InterceptEventPlugin.this.autoLogin(str, str2, str3, h5Event, h5BridgeContext);
                    }
                }, null, false, 0);
            }
        });
        return true;
    }

    private boolean needAuthRpc(final H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        if (!TextUtils.isEmpty(LoggerFactory.getLogContext().getUserId())) {
            return false;
        }
        H5Utils.runNotOnMain("RPC", new Runnable() {
            public void run() {
                H5Event build = new Builder(h5Event).build();
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put("OpenAuthLogin", "YES");
                    hashMap.put("needOpenAuth", "NO");
                    hashMap.put("bizSource", "tinyapp");
                    hashMap.put("cAuthUUID", UUID.randomUUID().toString());
                    WalletAuthCodeCreateReq walletAuthCodeCreateReq = new WalletAuthCodeCreateReq();
                    walletAuthCodeCreateReq.authSrcUrl = null;
                    ((Oauth2AuthCodeFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(Oauth2AuthCodeFacade.class, hashMap)).createAuthCodeUrl(walletAuthCodeCreateReq);
                    H5Service h5Service = H5ServiceUtils.getH5Service();
                    if (!TextUtils.isEmpty(LoggerFactory.getLogContext().getUserId())) {
                        h5Service.sendEvent(build, h5BridgeContext);
                    }
                } catch (Exception e) {
                    LoggerFactory.getTraceLogger().error((String) H5InterceptEventPlugin.TAG, (Throwable) e);
                    if (e instanceof RpcException) {
                        H5InterceptEventPlugin.this.showNotAuthedDialogIfNeed(build, h5BridgeContext);
                    }
                }
            }
        });
        return true;
    }

    private boolean useNewInterceptor() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return TextUtils.equals(h5ConfigProvider.getConfig(CONFIG_AMAP_MINI_APP_USE_NEW_INTERCEPTOR), "1");
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void autoLogin(String str, String str2, String str3, H5Event h5Event, H5BridgeContext h5BridgeContext) {
        final String str4 = str3;
        final String str5 = str2;
        final String str6 = str;
        final H5Event h5Event2 = h5Event;
        final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
        AnonymousClass3 r1 = new Runnable() {
            public void run() {
                AccountOAuthServiceManager.getInstance().setActiveOAuthService(TinyAppAuthBridge.get());
                OAuthLoginModel oAuthLoginModel = new OAuthLoginModel();
                oAuthLoginModel.setAccessToken(str4);
                oAuthLoginModel.setAlipayUid(str5);
                oAuthLoginModel.setBizSource("tinyapp");
                oAuthLoginModel.setMcUid(str6);
                oAuthLoginModel.setOpenAuthLogin(true);
                try {
                    OperationResult startAction = InsideOperationService.getInstance().startAction((Context) h5Event2.getActivity(), (BaseModel<T>) oAuthLoginModel);
                    if (startAction != null) {
                        LoggerFactory.getTraceLogger().debug(H5InterceptEventPlugin.TAG, startAction.toJsonString());
                    } else {
                        LoggerFactory.getTraceLogger().debug(H5InterceptEventPlugin.TAG, "auto login result null");
                    }
                    if (!TextUtils.isEmpty(LoggerFactory.getLogContext().getUserId())) {
                        H5ServiceUtils.getH5Service().sendEvent(h5Event2, h5BridgeContext2);
                    }
                } catch (Throwable th) {
                    LoggerFactory.getTraceLogger().error((String) H5InterceptEventPlugin.TAG, th);
                    if (th instanceof RpcException) {
                        H5InterceptEventPlugin.this.showNotAuthedDialogIfNeed(h5Event2, h5BridgeContext2);
                    }
                }
            }
        };
        H5Utils.runNotOnMain(H5ThreadType.URGENT, r1);
    }

    /* access modifiers changed from: private */
    public void showNotAuthedDialogIfNeed(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (TextUtils.isEmpty(LoggerFactory.getLogContext().getUserId())) {
            showNotAuthedDialog(h5Event, h5BridgeContext);
        }
    }

    private void showNotAuthedDialog(final H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                final H5Page access$200 = H5InterceptEventPlugin.this.getH5Page(h5Event);
                if (access$200 != null && !access$200.pageIsClose()) {
                    Context context = access$200.getContext().getContext();
                    if ((context instanceof Activity) && !((Activity) context).isFinishing()) {
                        AUNoticeDialog aUNoticeDialog = new AUNoticeDialog(context, null, context.getResources().getString(R.string.not_authed_message), context.getString(R.string.not_authed_retry), context.getString(R.string.not_authed_exit));
                        aUNoticeDialog.setCancelable(false);
                        aUNoticeDialog.setPositiveListener(new OnClickPositiveListener() {
                            public void onClick() {
                                H5InterceptEventPlugin.this.needAuth(h5Event, h5BridgeContext);
                            }
                        });
                        aUNoticeDialog.setNegativeListener(new OnClickNegativeListener() {
                            public void onClick() {
                                MicroApplication microApplication;
                                String hostAppId = TinyAppParamUtils.getHostAppId(access$200);
                                MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
                                if (TextUtils.isEmpty(hostAppId)) {
                                    microApplication = microApplicationContext.findTopRunningApp();
                                } else {
                                    microApplication = microApplicationContext.findAppById(hostAppId);
                                }
                                if (microApplication != null) {
                                    microApplication.destroy(null);
                                }
                            }
                        });
                        aUNoticeDialog.show();
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public H5Page getH5Page(H5Event h5Event) {
        H5Page h5page = h5Event.getH5page();
        if (h5page != null) {
            return h5page;
        }
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        return h5Service != null ? h5Service.getTopH5PageForTiny() : h5page;
    }
}
