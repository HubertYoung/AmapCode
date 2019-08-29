package com.ali.user.mobile.common.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.login.OnLoginCaller;
import com.ali.user.mobile.login.SupplyQueryPasswordService;

public class AliUserLogin {
    private Context a;

    public AliUserLogin(Context context) {
        this.a = context.getApplicationContext();
    }

    public static void a(OnLoginCaller onLoginCaller) {
        AliuserLoginContext.a(onLoginCaller);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.content.Context r5, android.os.Bundle r6) {
        /*
            r4 = this;
            java.lang.String r0 = "AliUserLogin"
            java.lang.String r1 = "setupNormalLogin - param:"
            java.lang.String r2 = java.lang.String.valueOf(r6)
            java.lang.String r1 = r1.concat(r2)
            com.ali.user.mobile.log.AliUserLog.c(r0, r1)
            com.ali.user.mobile.login.SupplyQueryPasswordService r0 = com.ali.user.mobile.login.SupplyQueryPasswordService.a()
            r0.b()
            if (r6 == 0) goto L_0x0029
            java.lang.String r0 = "sms"
            java.lang.String r1 = "style"
            java.lang.String r1 = r6.getString(r1)
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0080
        L_0x0029:
            android.content.Context r0 = r5.getApplicationContext()
            com.ali.user.mobile.db.UserInfoDaoHelper r0 = com.ali.user.mobile.db.UserInfoDaoHelper.a(r0)
            java.util.List r0 = r0.a()
            java.lang.String r1 = "AliUserLogin"
            if (r0 != 0) goto L_0x003d
            java.lang.String r2 = "不存在历史账户"
            goto L_0x0053
        L_0x003d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            int r3 = r0.size()
            r2.append(r3)
            java.lang.String r3 = "个历史账户"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
        L_0x0053:
            com.ali.user.mobile.log.AliUserLog.c(r1, r2)
            java.lang.String r1 = "login_param"
            java.lang.Object r1 = r6.get(r1)
            com.ali.user.mobile.login.LoginParam r1 = (com.ali.user.mobile.login.LoginParam) r1
            if (r1 != 0) goto L_0x0063
            java.lang.String r1 = ""
            goto L_0x0065
        L_0x0063:
            java.lang.String r1 = r1.loginAccount
        L_0x0065:
            if (r0 == 0) goto L_0x006d
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0080
        L_0x006d:
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 == 0) goto L_0x0080
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<com.ali.user.mobile.login.ui.AliuserGuideActivity> r1 = com.ali.user.mobile.login.ui.AliuserGuideActivity.class
            r0.<init>(r5, r1)
            if (r6 == 0) goto L_0x0084
            r0.putExtras(r6)
            goto L_0x0084
        L_0x0080:
            android.content.Intent r0 = r4.b(r5, r6)
        L_0x0084:
            boolean r6 = r5 instanceof android.app.Activity
            if (r6 != 0) goto L_0x008d
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            r0.addFlags(r6)
        L_0x008d:
            r5.startActivity(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.common.api.AliUserLogin.a(android.content.Context, android.os.Bundle):void");
    }

    private Intent b(Context context, Bundle bundle) {
        if (context == null) {
            context = this.a;
        }
        Intent a2 = AliuserLoginContext.a(context);
        if (bundle != null) {
            a2.putExtras(bundle);
            int[] intArray = bundle.getIntArray("intentFlags");
            if (intArray != null && intArray.length > 0) {
                AliUserLog.c("AliUserLogin", "there is external intent flags, add to login intent");
                for (int addFlags : intArray) {
                    a2.addFlags(addFlags);
                }
            }
        }
        return a2;
    }

    public static boolean a(Context context, String str, String str2) {
        return SupplyQueryPasswordService.a().a(context, str, str2);
    }
}
