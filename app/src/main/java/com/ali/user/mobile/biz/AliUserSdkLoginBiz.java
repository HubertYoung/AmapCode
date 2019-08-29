package com.ali.user.mobile.biz;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.account.bean.LoginInfo;
import com.ali.user.mobile.account.domain.MspDeviceInfoBean;
import com.ali.user.mobile.account.model.UserLoginResult;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.accountbiz.extservice.DeviceService;
import com.ali.user.mobile.accountbiz.extservice.RSAService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.common.api.AliUserLogin;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.dataprovider.AppDataProvider;
import com.ali.user.mobile.info.TidInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.AbsNotifyFinishCaller;
import com.ali.user.mobile.login.DefaultLoginCaller;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.OnLoginCaller;
import com.ali.user.mobile.rpc.vo.mobilegw.RSAPKeyResult;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.rsa.RSAHandler;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.utils.ResourceUtil;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.sdk.util.j;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class AliUserSdkLoginBiz {
    private static AliUserSdkLoginBiz a;
    /* access modifiers changed from: private */
    public static Context g;
    private OnLoginCaller b;
    private RSAHandler c;
    private AlipayDataProvider d;
    private AliUserLogin e;
    private Bundle f;

    public static class AlipayDataProvider implements AppDataProvider {
        private DeviceService a = AntExtServiceManager.getDeviceService(AliUserSdkLoginBiz.g);
        private String b = "";

        public final TidInfo a() {
            TidInfo tidInfo = new TidInfo();
            MspDeviceInfoBean queryCertification = this.a.queryCertification();
            if (queryCertification != null) {
                tidInfo.a(queryCertification.getTid());
                tidInfo.b(queryCertification.getMspkey());
                tidInfo.c(queryCertification.getImei());
                tidInfo.d(queryCertification.getImsi());
                tidInfo.e(queryCertification.getVimei());
                tidInfo.f(queryCertification.getVimsi());
            }
            return tidInfo;
        }

        public final String b() {
            return AppInfo.a().f();
        }

        public final String c() {
            return AppInfo.a().e();
        }

        public final String d() {
            return DeviceInfo.a().r();
        }

        public final String e() {
            return ResourceUtil.a(R.string.p);
        }
    }

    class AlipayRSAHandler implements RSAHandler {
        private RSAService b = AntExtServiceManager.getRSAService(AliUserSdkLoginBiz.g);

        public AlipayRSAHandler() {
        }

        public final RSAPKeyResult a() {
            RSAPKeyResult rSAPKeyResult = new RSAPKeyResult();
            rSAPKeyResult.rsaPK = this.b.getRsaKey();
            rSAPKeyResult.rsaTS = this.b.getRsaTS();
            AliUserLog.c("AliUserSdkLoginBiz", String.format("rsaService返回的公钥: %s", new Object[]{rSAPKeyResult.rsaPK}));
            return rSAPKeyResult;
        }
    }

    public static synchronized AliUserSdkLoginBiz a(Context context) {
        AliUserSdkLoginBiz aliUserSdkLoginBiz;
        synchronized (AliUserSdkLoginBiz.class) {
            if (a == null) {
                a = new AliUserSdkLoginBiz(context);
            }
            aliUserSdkLoginBiz = a;
        }
        return aliUserSdkLoginBiz;
    }

    private AliUserSdkLoginBiz(Context context) {
        AliUserLog.c("AliUserSdkLoginBiz", "AliUserSdkLoginBiz constructor");
        g = context;
        Context context2 = g;
        if (this.c == null) {
            this.c = new AlipayRSAHandler();
        }
        AliuserLoginContext.a(this.c);
        if (this.d == null) {
            this.d = new AlipayDataProvider();
        }
        AliUserInit.a((AppDataProvider) this.d);
        AliUserInit.a(context2);
        com.ali.user.mobile.info.AppInfo.getInstance().setChannel(AppInfo.a().h());
        g.getApplicationContext();
        if (this.b == null) {
            this.b = new DefaultLoginCaller() {
                public final void a(UnifyLoginRes unifyLoginRes, AbsNotifyFinishCaller absNotifyFinishCaller) {
                    AliUserLog.a((String) "AliUserSdkLoginBiz", (String) "===== call filterLogin");
                    super.a(unifyLoginRes, absNotifyFinishCaller);
                }

                public final void a(AbsNotifyFinishCaller absNotifyFinishCaller) {
                    AliUserLog.a((String) "AliUserSdkLoginBiz", (String) "===== call cancelLogin");
                    super.a(absNotifyFinishCaller);
                }

                public final void b(UnifyLoginRes unifyLoginRes, AbsNotifyFinishCaller absNotifyFinishCaller) {
                    AliUserLog.a((String) "AliUserSdkLoginBiz", (String) "===== call failLogin");
                    super.b(unifyLoginRes, absNotifyFinishCaller);
                }

                public final void c(final UnifyLoginRes unifyLoginRes, final AbsNotifyFinishCaller absNotifyFinishCaller) {
                    AliUserLog.a((String) "AliUserSdkLoginBiz", (String) "===== call postFinishLogin");
                    SecurityUtil.a((Runnable) new Runnable() {
                        public void run() {
                            try {
                                AliUserSdkLoginBiz.a(AliUserSdkLoginBiz.this, unifyLoginRes, absNotifyFinishCaller);
                            } catch (Throwable th) {
                                AliUserLog.a("AliUserSdkLoginBiz", "处理登录成功异常", th);
                                LogAgent.a(th);
                            }
                        }
                    });
                }
            };
        }
        AliUserLogin.a(this.b);
        c();
        HashMap hashMap = new HashMap();
        hashMap.put("VIEW_CUSTOMISE_ADAPTER", new BaseAdapter() {
            public int getCount() {
                return 1;
            }

            public Object getItem(int i) {
                return null;
            }

            public long getItemId(int i) {
                return 0;
            }

            public View getView(int i, View view, ViewGroup viewGroup) {
                if (i == 1) {
                    return AliUserSdkLoginBiz.a(AliUserSdkLoginBiz.this, i, view, viewGroup);
                }
                return null;
            }
        });
        UIConfigManager.a(hashMap);
    }

    public final void a(Bundle bundle) {
        this.f = bundle;
    }

    private void c() {
        AliUserLog.c("AliUserSdkLoginBiz", "registerLoginFragment");
        try {
            AliuserLoginContext.a(getClass().getClassLoader().loadClass("com.ali.user.mobile.login.ui.AlipayUserLoginActivity"));
        } catch (Exception e2) {
            AliUserLog.a((String) "AliUserSdkLoginBiz", (Throwable) e2);
        }
    }

    public final void a() {
        StringBuilder sb = new StringBuilder("startLoginSdk - param:");
        sb.append(this.f);
        AliUserLog.c("AliUserSdkLoginBiz", sb.toString());
        if (this.f != null) {
            StringBuilder sb2 = new StringBuilder("source_accountSelectAccount = ");
            boolean z = false;
            sb2.append(a((String) "source_accountSelectAccount", false));
            AliUserLog.c("AliUserSdkLoginBiz", sb2.toString());
            LoginParam loginParam = new LoginParam();
            LoginInfo loginInfo = (LoginInfo) this.f.getParcelable("loginInfo");
            boolean z2 = true;
            if (loginInfo != null) {
                StringBuilder sb3 = new StringBuilder("has userinfo, account:");
                sb3.append(loginInfo.getAccount());
                AliUserLog.c("AliUserSdkLoginBiz", sb3.toString());
                loginParam.loginAccount = loginInfo.getAccount();
                z = true;
            }
            if (!TextUtils.isEmpty(this.f.getString("logonId"))) {
                String string = this.f.getString("logonId");
                AliUserLog.c("AliUserSdkLoginBiz", "has logonId:".concat(String.valueOf(string)));
                loginParam.loginAccount = string;
                z = true;
            }
            if (!TextUtils.isEmpty(this.f.getString("loginId"))) {
                String string2 = this.f.getString("loginId");
                AliUserLog.c("AliUserSdkLoginBiz", "has loginId:".concat(String.valueOf(string2)));
                loginParam.loginAccount = string2;
                z = true;
            }
            if (!TextUtils.isEmpty(this.f.getString("token"))) {
                loginParam.token = this.f.getString("token");
                z = true;
            }
            if (!TextUtils.isEmpty(this.f.getString("validateType"))) {
                loginParam.validateTpye = this.f.getString("validateType");
            } else {
                z2 = z;
            }
            if (z2) {
                this.f.putSerializable("login_param", loginParam);
            }
        }
        c();
        d().a(g, this.f);
    }

    private static UserLoginResult a(UnifyLoginRes unifyLoginRes) {
        UserLoginResult userLoginResult = new UserLoginResult();
        try {
            JSONObject jSONObject = new JSONObject(unifyLoginRes.data);
            userLoginResult.bindCard = b(jSONObject, "bindCard");
            userLoginResult.extern_token = a(jSONObject, (String) "extern_token");
            userLoginResult.headImg = unifyLoginRes.headImg;
            userLoginResult.isCertified = a(jSONObject, (String) "isCertified");
            userLoginResult.loginId = unifyLoginRes.alipayLoginId;
            userLoginResult.loginServerTime = a(jSONObject, (String) "loginServerTime");
            userLoginResult.loginToken = a(jSONObject, (String) "loginToken");
            userLoginResult.mobileNo = a(jSONObject, (String) "mobileNo");
            userLoginResult.resultStatus = Integer.valueOf(a(jSONObject, (String) j.a)).intValue();
            userLoginResult.tbCheckCodeId = unifyLoginRes.checkCodeId;
            userLoginResult.tbCheckCodeUrl = unifyLoginRes.checkCodeUrl;
            userLoginResult.userName = a(jSONObject, (String) "userName");
            userLoginResult.userId = unifyLoginRes.userId;
            userLoginResult.sessionId = unifyLoginRes.extMap.get("sessionId");
            userLoginResult.customerType = unifyLoginRes.extMap.get("customerType");
            userLoginResult.extResAttrs = new HashMap();
            userLoginResult.extResAttrs.put("havanaId", String.valueOf(unifyLoginRes.hid));
            JSONObject jSONObject2 = jSONObject.getJSONObject("extResAttrs");
            if (jSONObject2 != null) {
                Iterator<String> keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    userLoginResult.extResAttrs.put(next, jSONObject2.getString(next));
                }
            }
            userLoginResult.memo = a(jSONObject, (String) "memo");
            userLoginResult.currentProductVersion = a(jSONObject, (String) "currentProductVersion");
            userLoginResult.existNewVersion = a(jSONObject, (String) "existNewVersion");
            userLoginResult.downloadURL = a(jSONObject, (String) "downloadURL");
            userLoginResult.taobaoSid = a(jSONObject, (String) "taobaoSid");
            userLoginResult.barcodePayToken = a(jSONObject, (String) "barcodePayToken");
            userLoginResult.iconUrl = a(jSONObject, (String) "iconUrl");
            userLoginResult.loginCheckCodeImg = a(jSONObject, (String) "loginCheckCodeImg");
            userLoginResult.loginCheckCodeUrl = a(jSONObject, (String) "loginCheckCodeUrl");
            userLoginResult.loginContext = a(jSONObject, (String) "loginContext");
            userLoginResult.wirelessUser = b(jSONObject, "wirelessUser");
        } catch (Exception e2) {
            AliUserLog.a((String) "AliUserSdkLoginBiz", (Throwable) e2);
        }
        return userLoginResult;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ab A[Catch:{ Exception -> 0x013e }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b8 A[Catch:{ Exception -> 0x013e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.ali.user.mobile.account.bean.UserLoginResultBiz r7, com.ali.user.mobile.account.bean.UserInfo r8) {
        /*
            r6 = this;
            java.lang.String r0 = "AliUserSdkLoginBiz"
            java.lang.String r1 = "processAlipayLoginResult"
            com.ali.user.mobile.log.AliUserLog.c(r0, r1)
            r0 = 0
            if (r7 == 0) goto L_0x0140
            r1 = 1000(0x3e8, float:1.401E-42)
            int r2 = r7.getResultStatus()     // Catch:{ Exception -> 0x013e }
            if (r1 != r2) goto L_0x014b
            java.lang.String r1 = "AliUserSdkLoginBiz"
            java.lang.String r2 = "processAlipayLoginSuccess"
            com.ali.user.mobile.log.AliUserLog.c(r1, r2)     // Catch:{ Exception -> 0x013e }
            r1 = 1
            if (r8 == 0) goto L_0x002a
            java.lang.String r7 = r7.getUserId()     // Catch:{ Exception -> 0x013e }
            java.lang.String r8 = r8.getUserId()     // Catch:{ Exception -> 0x013e }
            boolean r7 = r7.equals(r8)     // Catch:{ Exception -> 0x013e }
            r7 = r7 ^ r1
            goto L_0x002b
        L_0x002a:
            r7 = 0
        L_0x002b:
            java.lang.String r8 = "AliUserSdkLoginBiz"
            java.lang.String r2 = "switchUser: "
            java.lang.String r3 = java.lang.String.valueOf(r7)     // Catch:{ Exception -> 0x013e }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ Exception -> 0x013e }
            com.ali.user.mobile.log.AliUserLog.c(r8, r2)     // Catch:{ Exception -> 0x013e }
            android.content.Context r8 = g     // Catch:{ Exception -> 0x0045 }
            com.ali.user.mobile.accountbiz.extservice.AuthService r8 = com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager.getAuthService(r8)     // Catch:{ Exception -> 0x0045 }
            r8.notifyUnlockLoginApp(r1, r0)     // Catch:{ Exception -> 0x0045 }
            goto L_0x004b
        L_0x0045:
            r8 = move-exception
            java.lang.String r2 = "AliUserSdkLoginBiz"
            com.ali.user.mobile.log.AliUserLog.a(r2, r8)     // Catch:{ Exception -> 0x013e }
        L_0x004b:
            android.content.Context r8 = g     // Catch:{ Exception -> 0x013e }
            com.alipay.android.phone.inside.common.util.CacheSet r8 = com.alipay.android.phone.inside.common.util.CacheSet.a(r8)     // Catch:{ Exception -> 0x013e }
            java.lang.String r2 = "isValidScheme"
            java.lang.String r8 = r8.a(r2)     // Catch:{ Exception -> 0x013e }
            boolean r2 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x013e }
            r3 = 0
            if (r2 == 0) goto L_0x007d
            android.content.Context r2 = g     // Catch:{ Exception -> 0x0077 }
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch:{ Exception -> 0x0077 }
            android.content.Context r4 = g     // Catch:{ Exception -> 0x0077 }
            java.lang.String r4 = r4.getPackageName()     // Catch:{ Exception -> 0x0077 }
            r5 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r2 = r2.getApplicationInfo(r4, r5)     // Catch:{ Exception -> 0x0077 }
            java.lang.String r2 = r2.sourceDir     // Catch:{ Exception -> 0x0077 }
            java.lang.String r2 = com.ali.user.mobile.util.CommentHelper.a(r2)     // Catch:{ Exception -> 0x0077 }
            goto L_0x007e
        L_0x0077:
            r2 = move-exception
            java.lang.String r4 = "AliUserSdkLoginBiz"
            com.ali.user.mobile.log.AliUserLog.a(r4, r2)     // Catch:{ Exception -> 0x013e }
        L_0x007d:
            r2 = r3
        L_0x007e:
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x013e }
            if (r8 == 0) goto L_0x00a3
            boolean r8 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x013e }
            if (r8 != 0) goto L_0x00a3
            java.lang.String r8 = "alipays://platformapi/startapp?appId=20000009"
            boolean r8 = r2.startsWith(r8)     // Catch:{ Exception -> 0x013e }
            if (r8 != 0) goto L_0x00a3
            java.lang.String r7 = "AliUserSdkLoginBiz"
            java.lang.String r8 = "toSchemeApp > "
            java.lang.String r0 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x013e }
            java.lang.String r8 = r8.concat(r0)     // Catch:{ Exception -> 0x013e }
            com.ali.user.mobile.log.AliUserLog.c(r7, r8)     // Catch:{ Exception -> 0x013e }
            return
        L_0x00a3:
            java.lang.String r8 = "registBindToCard"
            boolean r8 = r6.a(r8, r0)     // Catch:{ Exception -> 0x013e }
            if (r8 == 0) goto L_0x00b8
            java.lang.String r8 = "AliUserSdkLoginBiz"
            java.lang.String r0 = "Constants.REGISTBINDTOCARD, false"
            com.ali.user.mobile.log.AliUserLog.c(r8, r0)     // Catch:{ Exception -> 0x013e }
            java.lang.String r8 = "registBindToCard"
            a(r7, r8)     // Catch:{ Exception -> 0x013e }
            return
        L_0x00b8:
            java.lang.String r8 = "source_accountSelectAccount"
            boolean r8 = r6.a(r8, r0)     // Catch:{ Exception -> 0x013e }
            if (r8 == 0) goto L_0x00d5
            android.os.Bundle r8 = new android.os.Bundle     // Catch:{ Exception -> 0x013e }
            r8.<init>()     // Catch:{ Exception -> 0x013e }
            java.lang.String r0 = "source_login"
            r8.putBoolean(r0, r1)     // Catch:{ Exception -> 0x013e }
            if (r7 == 0) goto L_0x00d4
            java.lang.String r0 = "source_switchUser"
            r8.putBoolean(r0, r7)     // Catch:{ Exception -> 0x013e }
        L_0x00d4:
            return
        L_0x00d5:
            java.lang.String r8 = "source_gesture"
            boolean r8 = r6.a(r8, r0)     // Catch:{ Exception -> 0x013e }
            if (r8 == 0) goto L_0x00e2
            a(r7, r3)     // Catch:{ Exception -> 0x013e }
            return
        L_0x00e2:
            java.lang.String r8 = "isGoMain"
            boolean r8 = r6.a(r8, r0)     // Catch:{ Exception -> 0x013e }
            if (r8 == 0) goto L_0x00f2
            java.lang.String r7 = "AliUserSdkLoginBiz"
            java.lang.String r8 = " goMain()"
            com.ali.user.mobile.log.AliUserLog.c(r7, r8)     // Catch:{ Exception -> 0x013e }
            return
        L_0x00f2:
            java.lang.String r8 = "registbindToFundBao"
            boolean r8 = r6.a(r8, r0)     // Catch:{ Exception -> 0x013e }
            if (r8 == 0) goto L_0x0107
            java.lang.String r8 = "AliUserSdkLoginBiz"
            java.lang.String r0 = "Constants.REGISTBINDTOFUNDBAO, false()"
            com.ali.user.mobile.log.AliUserLog.c(r8, r0)     // Catch:{ Exception -> 0x013e }
            java.lang.String r8 = "registbindToFundBao"
            a(r7, r8)     // Catch:{ Exception -> 0x013e }
            return
        L_0x0107:
            java.lang.String r8 = "loginout"
            boolean r8 = r6.a(r8, r0)     // Catch:{ Exception -> 0x013e }
            if (r8 == 0) goto L_0x0113
            a(r1, r3)     // Catch:{ Exception -> 0x013e }
            return
        L_0x0113:
            java.lang.String r8 = "deviceLock"
            java.lang.String r0 = "LoginSource"
            android.os.Bundle r1 = r6.f     // Catch:{ Exception -> 0x013e }
            if (r1 == 0) goto L_0x0122
            android.os.Bundle r1 = r6.f     // Catch:{ Exception -> 0x013e }
            java.lang.String r0 = r1.getString(r0)     // Catch:{ Exception -> 0x013e }
            goto L_0x0124
        L_0x0122:
            java.lang.String r0 = ""
        L_0x0124:
            boolean r8 = r8.equals(r0)     // Catch:{ Exception -> 0x013e }
            if (r8 == 0) goto L_0x0135
            if (r7 == 0) goto L_0x0135
            java.lang.String r7 = "AliUserSdkLoginBiz"
            java.lang.String r8 = "设备锁被踢后切换账户登录，回首页"
            com.ali.user.mobile.log.AliUserLog.c(r7, r8)     // Catch:{ Exception -> 0x013e }
            return
        L_0x0135:
            java.lang.String r7 = "AliUserSdkLoginBiz"
            java.lang.String r8 = "toDefaultApp()"
            com.ali.user.mobile.log.AliUserLog.c(r7, r8)     // Catch:{ Exception -> 0x013e }
            return
        L_0x013e:
            r7 = move-exception
            goto L_0x014c
        L_0x0140:
            android.content.Context r7 = g     // Catch:{ Exception -> 0x013e }
            int r8 = com.ali.user.mobile.security.ui.R.string.cu     // Catch:{ Exception -> 0x013e }
            android.widget.Toast r7 = com.ali.user.mobile.ui.widget.toast.SimpleToast.a(r7, r8, r0)     // Catch:{ Exception -> 0x013e }
            r7.show()     // Catch:{ Exception -> 0x013e }
        L_0x014b:
            return
        L_0x014c:
            java.lang.String r8 = "AliUserSdkLoginBiz"
            com.ali.user.mobile.log.AliUserLog.a(r8, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.biz.AliUserSdkLoginBiz.a(com.ali.user.mobile.account.bean.UserLoginResultBiz, com.ali.user.mobile.account.bean.UserInfo):void");
    }

    private boolean a(String str, boolean z) {
        if (this.f != null) {
            return this.f.getBoolean(str, z);
        }
        return false;
    }

    private static void a(boolean z, String str) {
        StringBuilder sb = new StringBuilder("gestureAfterLogin > ");
        sb.append(z);
        sb.append(",");
        sb.append(str);
        AliUserLog.c("AliUserSdkLoginBiz", sb.toString());
    }

    private AliUserLogin d() {
        if (this.e == null) {
            this.e = new AliUserLogin(g);
        }
        return this.e;
    }

    private static String a(JSONObject jSONObject, String str) {
        try {
            return jSONObject.getString(str);
        } catch (JSONException unused) {
            AliUserLog.c("AliUserSdkLoginBiz", String.format("can not get: %s", new Object[]{str}));
            return "";
        }
    }

    private static boolean b(JSONObject jSONObject, String str) {
        try {
            return jSONObject.getBoolean(str);
        } catch (JSONException unused) {
            AliUserLog.c("AliUserSdkLoginBiz", String.format("can not get: %s", new Object[]{str}));
            return false;
        }
    }

    static /* synthetic */ View a(AliUserSdkLoginBiz aliUserSdkLoginBiz, int i, View view, ViewGroup viewGroup) {
        StringBuilder sb = new StringBuilder("createLanguageView > ");
        sb.append(i);
        sb.append(",");
        sb.append(view);
        AliUserLog.c("AliUserSdkLoginBiz", sb.toString());
        View inflate = LayoutInflater.from(g.getApplicationContext()).inflate(R.layout.w, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.bN)).setText(ResourceUtil.a(R.string.bw));
        inflate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LogAgent.a((String) "UC-GY-161225-01", (String) "loginlanguage", (String) "", (String) null);
            }
        });
        return inflate;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0111  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.ali.user.mobile.biz.AliUserSdkLoginBiz r12, com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes r13, com.ali.user.mobile.login.AbsNotifyFinishCaller r14) {
        /*
            android.content.Context r0 = g
            com.ali.user.mobile.accountbiz.extservice.LoginService r0 = com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager.getLoginService(r0)
            android.content.Context r1 = g
            com.ali.user.mobile.accountbiz.extservice.AuthService r1 = com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager.getAuthService(r1)
            android.content.Context r2 = g
            com.ali.user.mobile.accountbiz.extservice.AccountService r2 = com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager.getAccountService(r2)
            if (r0 == 0) goto L_0x0118
            if (r1 == 0) goto L_0x0118
            if (r2 == 0) goto L_0x0118
            java.lang.String r3 = "AliUserSdkLoginBiz"
            java.lang.String r4 = "processSdkLoginResult"
            com.ali.user.mobile.log.AliUserLog.c(r3, r4)
            com.ali.user.mobile.account.model.UserLoginResult r3 = a(r13)
            com.ali.user.mobile.account.model.UserLoginReq r4 = new com.ali.user.mobile.account.model.UserLoginReq
            r4.<init>()
            java.lang.String r5 = r13.alipayLoginId
            r4.loginId = r5
            java.util.Map<java.lang.String, java.lang.String> r5 = r13.extMap
            java.lang.String r6 = "lp"
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            r4.loginPassword = r5
            java.util.Map<java.lang.String, java.lang.String> r5 = r13.extMap
            java.lang.String r6 = "loginType"
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            r4.loginType = r5
            java.util.Map<java.lang.String, java.lang.String> r13 = r13.extMap
            java.lang.String r5 = "validateTpye"
            java.lang.Object r13 = r13.get(r5)
            java.lang.String r13 = (java.lang.String) r13
            r4.loginWthPwd = r13
            com.ali.user.mobile.account.bean.UserLoginResultBiz r13 = new com.ali.user.mobile.account.bean.UserLoginResultBiz
            r13.<init>()
            com.ali.user.mobile.account.bean.UserInfo r1 = r1.getLastLoginedUserInfo()
            java.lang.String r2 = r2.getCurrentLoginUserId()
            java.util.Map r5 = r3.getExtResAttrs()
            r6 = 1
            r7 = 0
            if (r5 == 0) goto L_0x00a3
            boolean r8 = r5.isEmpty()
            if (r8 != 0) goto L_0x00a3
            java.lang.String r8 = "noQueryPwdUser"
            java.lang.Object r8 = r5.get(r8)
            java.lang.String r8 = (java.lang.String) r8
            java.lang.String r9 = "needSupplyQueryPwd"
            java.lang.Object r5 = r5.get(r9)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r9 = "AliUserSdkLoginBiz"
            java.lang.String r10 = "是否无密账户:%s，是否需要补全:%s"
            r11 = 2
            java.lang.Object[] r11 = new java.lang.Object[r11]
            r11[r7] = r8
            r11[r6] = r5
            java.lang.String r10 = java.lang.String.format(r10, r11)
            com.ali.user.mobile.log.AliUserLog.c(r9, r10)
            java.lang.String r9 = "true"
            boolean r8 = r9.equalsIgnoreCase(r8)
            if (r8 == 0) goto L_0x00a3
            java.lang.String r8 = "true"
            boolean r5 = r8.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x00a3
            r5 = 1
            goto L_0x00a4
        L_0x00a3:
            r5 = 0
        L_0x00a4:
            r8 = r5 ^ 1
            r0.processLoginResult(r3, r13, r4, r8)
            if (r5 == 0) goto L_0x0111
            r14.a()
            android.content.Context r14 = g
            java.lang.String r3 = r3.getLoginId()
            java.lang.String r5 = "login"
            r12.d()
            boolean r3 = com.ali.user.mobile.common.api.AliUserLogin.a(r14, r3, r5)
            if (r3 == 0) goto L_0x00f0
            java.lang.String r3 = "AliUserSdkLoginBiz"
            java.lang.String r5 = "补密成功，更新用户数据"
            com.ali.user.mobile.log.AliUserLog.c(r3, r5)
            com.ali.user.mobile.login.SupplyQueryPasswordService r3 = com.ali.user.mobile.login.SupplyQueryPasswordService.a()
            r3.b()
            android.content.Context r3 = g
            com.ali.user.mobile.accountbiz.extservice.AuthService r3 = com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager.getAuthService(r3)
            if (r3 == 0) goto L_0x00f0
            com.ali.user.mobile.account.bean.UserInfo r3 = r3.getUserInfo()
            if (r3 == 0) goto L_0x00f0
            java.lang.String r5 = "false"
            r3.setNoQueryPwdUser(r5)
            com.ali.user.mobile.db.UserInfoDaoHelper r14 = com.ali.user.mobile.db.UserInfoDaoHelper.a(r14)
            r14.a(r3)
            java.lang.String r14 = "AliUserSdkLoginBiz"
            java.lang.String r3 = "更新userInfo数据成功"
            com.ali.user.mobile.log.AliUserLog.c(r14, r3)
        L_0x00f0:
            java.lang.String r14 = "AliUserSdkLoginBiz"
            java.lang.String r3 = "补密处理完成，开始发送登录广播"
            com.ali.user.mobile.log.AliUserLog.c(r14, r3)
            java.lang.String r14 = r13.getUserId()
            if (r14 == 0) goto L_0x010a
            java.lang.String r14 = r13.getUserId()
            boolean r14 = r14.equals(r2)
            if (r14 != 0) goto L_0x0109
            goto L_0x010a
        L_0x0109:
            r6 = 0
        L_0x010a:
            r0.sendLoginBroadcast(r6, r4, r13)
            r12.a(r13, r1)
            return
        L_0x0111:
            r12.a(r13, r1)
            r14.a()
            return
        L_0x0118:
            java.lang.String r12 = "AliUserSdkLoginBiz"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r14 = "loginService:"
            r13.<init>(r14)
            r13.append(r0)
            java.lang.String r14 = ",authService:"
            r13.append(r14)
            r13.append(r1)
            java.lang.String r13 = r13.toString()
            com.ali.user.mobile.log.AliUserLog.d(r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.biz.AliUserSdkLoginBiz.a(com.ali.user.mobile.biz.AliUserSdkLoginBiz, com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes, com.ali.user.mobile.login.AbsNotifyFinishCaller):void");
    }
}
