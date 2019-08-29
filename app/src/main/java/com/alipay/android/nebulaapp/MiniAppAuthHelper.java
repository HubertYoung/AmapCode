package com.alipay.android.nebulaapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthCallback;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.amap.bundle.commonui.loading.LoadingView;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.HistoryLoginType;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MiniAppAuthHelper {
    private static final String ACTION_ID_AUTH_BEGIN = "B002";
    private static final String ACTION_ID_AUTH_SUCCEED = "B003";
    private static final String TYPE_LOGIN_AUTH = "已登录已绑定未授权";
    private static final String TYPE_LOGIN_NO_AUTH = "已登录未绑定";
    private static final String TYPE_NO_LOGIN_ALIPAY_AUTH = "未登录无/支付宝登录历史";
    private static final String TYPE_NO_LOGIN_OTHER_AUTH = "未登录有非支付宝登录历史";
    private String appId;
    private IAccountOAuthCallback authCallback;
    private String authType;
    /* access modifiers changed from: private */
    public Handler mainHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public ProgressDlg progressDlg;
    private String syncKey;

    static class ProgressDlg extends CompatDialog {
        private LoadingView mLoadingView = ((LoadingView) findViewById(R.id.loading_view));

        public ProgressDlg(Activity activity, String str) {
            super(activity, R.style.custom_dlg);
            requestWindowFeature(1);
            setContentView(R.layout.widget_progress_dlg);
            this.mLoadingView.setCloseIconVisibility(0);
            this.mLoadingView.setOnCloseClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ProgressDlg.this.cancel();
                }
            });
            if (!TextUtils.isEmpty(str)) {
                this.mLoadingView.setLoadingText(str);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void authMiniApp(IAccountOAuthCallback iAccountOAuthCallback, Bundle bundle, boolean z) {
        authMiniApp(iAccountOAuthCallback, bundle, z, 2000);
    }

    public void authMiniApp(IAccountOAuthCallback iAccountOAuthCallback, Bundle bundle, boolean z, int i) {
        String str;
        final List list;
        IAccountOAuthCallback iAccountOAuthCallback2 = iAccountOAuthCallback;
        Bundle bundle2 = bundle;
        if (iAccountOAuthCallback2 != null) {
            this.syncKey = bim.aa().K();
            this.authCallback = iAccountOAuthCallback2;
            if (bundle2 != null) {
                str = bundle2.getString("appId");
                list = bundle2.getStringArrayList("scopeNicks");
            } else {
                list = null;
                str = null;
            }
            if (bundle2 == null) {
                bundle2 = new Bundle();
            }
            Bundle bundle3 = bundle2;
            bundle3.putString("origin", "AMAP_TINYAPP");
            StringBuilder sb = new StringBuilder("authMiniApp, appId: ");
            sb.append(str);
            sb.append(", scopes: ");
            sb.append(list);
            AMapLog.debug("infoservice.miniapp", "MiniAppInitHelper", sb.toString());
            LinkedList linkedList = new LinkedList();
            linkedList.add("aplogin");
            final IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService == null) {
                invokeAuthCallback(null, null, null, null);
                return;
            }
            final int i2 = i;
            final AnonymousClass1 r3 = new anq() {
                public void onComplete(boolean z) {
                    if (z) {
                        MiniAppUtil.refreshAlipayLoggingInfo();
                        ant e = iAccountService.e();
                        if (e != null) {
                            final String a = iAccountService.a((String) "aplogin");
                            final String str = e.a;
                            final String str2 = e.u;
                            MiniAppAuthHelper.this.mainHandler.postDelayed(new Runnable() {
                                public void run() {
                                    MiniAppAuthHelper.this.invokeAuthCallback(str, str2, a, null);
                                    MiniAppAuthHelper.this.dismissProgressDialog();
                                }
                            }, 1000);
                            return;
                        }
                        MiniAppAuthHelper.this.invokeCallbackDelay(i2);
                        MiniAppAuthHelper.this.dismissProgressDialog();
                        return;
                    }
                    MiniAppAuthHelper.this.invokeCallbackDelay(i2);
                    MiniAppAuthHelper.this.dismissProgressDialog();
                }

                public void loginOrBindCancel() {
                    MiniAppAuthHelper.this.invokeCallbackDelay(i2);
                    MiniAppAuthHelper.this.dismissProgressDialog();
                }
            };
            ant e = iAccountService.e();
            if (e != null) {
                String a = iAccountService.a((String) "aplogin");
                if (z || TextUtils.isEmpty(a)) {
                    iAccountService.a((Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get(), str, linkedList, list, "AMAP_TINYAPP", bundle3, r3);
                    showProgressDialog();
                    sendAuthBeginActionLog(str, TYPE_LOGIN_NO_AUTH);
                    return;
                }
                String str2 = e.a;
                String str3 = e.u;
                sendAuthBeginActionLog(str, TYPE_LOGIN_AUTH);
                invokeAuthCallback(str2, str3, a, null);
                return;
            }
            HistoryLoginType a2 = iAccountService.a((Context) AMapAppGlobal.getApplication());
            if (a2 == null || a2 == HistoryLoginType.Alipay) {
                String str4 = str;
                iAccountService.a(str4, linkedList, list, "AMAP_TINYAPP", bundle3, r3);
                showProgressDialog();
                sendAuthBeginActionLog(str4, TYPE_NO_LOGIN_ALIPAY_AUTH);
                return;
            }
            final Activity activity = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
            Activity activity2 = DoNotUseTool.getActivity();
            final IAccountService iAccountService2 = iAccountService;
            final String str5 = str;
            Bundle bundle4 = bundle3;
            final LinkedList linkedList2 = linkedList;
            String str6 = str;
            final Bundle bundle5 = bundle4;
            AnonymousClass2 r0 = new anq() {
                public void onComplete(boolean z) {
                    if (!z) {
                        r3.onComplete(false);
                    } else if (!TextUtils.isEmpty(iAccountService2.a((String) "aplogin"))) {
                        r3.onComplete(true);
                    } else if (activity != null) {
                        iAccountService2.a(activity, str5, linkedList2, list, "AMAP_TINYAPP", bundle5, r3);
                    } else {
                        r3.onComplete(false);
                    }
                }

                public void loginOrBindCancel() {
                    r3.loginOrBindCancel();
                }
            };
            iAccountService.a(activity2, (String) "AMAP_TINYAPP", (anq) r0);
            showProgressDialog();
            sendAuthBeginActionLog(str6, TYPE_NO_LOGIN_OTHER_AUTH);
        }
    }

    /* access modifiers changed from: private */
    public void invokeCallbackDelay(int i) {
        this.mainHandler.postDelayed(new Runnable() {
            public void run() {
                MiniAppAuthHelper.this.invokeAuthCallback(null, null, null, null);
            }
        }, (long) i);
    }

    private void showProgressDialog() {
        final Activity activity = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
        this.mainHandler.post(new Runnable() {
            public void run() {
                if (MiniAppAuthHelper.this.progressDlg == null && activity != null) {
                    MiniAppAuthHelper.this.progressDlg = new ProgressDlg(activity, "授权中...");
                    MiniAppAuthHelper.this.progressDlg.setCanceledOnTouchOutside(false);
                    MiniAppAuthHelper.this.progressDlg.setCancelable(true);
                    MiniAppAuthHelper.this.progressDlg.setOnCancelListener(new OnCancelListener() {
                        public void onCancel(DialogInterface dialogInterface) {
                            MiniAppAuthHelper.this.invokeAuthCallback(null, null, null, null);
                            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                            if (iAccountService != null) {
                                iAccountService.f();
                            }
                        }
                    });
                    AMapLog.debug("infoservice.miniapp", "MiniAppInitHelper", "post showProgressDialog");
                    MiniAppAuthHelper.this.progressDlg.show();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void dismissProgressDialog() {
        this.mainHandler.post(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder("post dismissProgressDialog, progressDialog == null? ");
                sb.append(MiniAppAuthHelper.this.progressDlg == null);
                AMapLog.debug("infoservice.miniapp", "MiniAppInitHelper", sb.toString());
                if (MiniAppAuthHelper.this.progressDlg != null) {
                    MiniAppAuthHelper.this.progressDlg.dismiss();
                    MiniAppAuthHelper.this.progressDlg = null;
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void invokeAuthCallback(String str, String str2, String str3, Bundle bundle) {
        if (this.authCallback != null) {
            if (str != null) {
                sendAuthSuccessActionLog();
            }
            this.authCallback.onAuthResult(str, str2, str3, bundle);
            this.authCallback = null;
        }
        if (!bim.aa().n(this.syncKey)) {
            AMapLog.debug("infoservice.miniapp", "MiniAppInitHelper", "sync manager remove silent merge flag fail!");
        }
    }

    private void sendAuthActionLog(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", str2);
            jSONObject.put("type", str3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00400", str, jSONObject);
    }

    private void sendAuthBeginActionLog(String str, String str2) {
        this.authType = str2;
        this.appId = str;
        if (TextUtils.isEmpty(this.appId)) {
            this.appId = "";
        }
        sendAuthActionLog("B002", str, str2);
    }

    private void sendAuthSuccessActionLog() {
        sendAuthActionLog("B003", this.appId, this.authType);
    }
}
