package com.ali.user.mobile.log;

import android.content.Context;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.info.NetWorkInfo;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.mobileapp.biz.rpc.mdap.vo.pb.UnifyCustomMdapReqPb;
import java.util.Calendar;
import java.util.Map;

public class RpcLogUtil {
    public static UnifyCustomMdapReqPb a(Behavior behavior, String str) {
        String str2;
        Context b = AliUserInit.b();
        String productVersion = AppInfo.getInstance().getProductVersion();
        UnifyCustomMdapReqPb unifyCustomMdapReqPb = new UnifyCustomMdapReqPb();
        unifyCustomMdapReqPb.alipayproductid = AppInfo.getInstance().getProductId();
        unifyCustomMdapReqPb.alipayproductversion = productVersion;
        unifyCustomMdapReqPb.app_channel = AppInfo.getInstance().getChannel();
        unifyCustomMdapReqPb.behaviortype = str;
        unifyCustomMdapReqPb.device_model = DeviceInfo.a().j();
        unifyCustomMdapReqPb.exinfo1 = behavior.g;
        unifyCustomMdapReqPb.exinfo2 = behavior.h;
        unifyCustomMdapReqPb.exinfo3 = behavior.i;
        Map<String, String> map = behavior.j;
        if (map == null || map.isEmpty()) {
            str2 = "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (String next : map.keySet()) {
                sb.append(next);
                sb.append("=");
                sb.append(map.get(next));
                sb.append("^");
            }
            sb.deleteCharAt(sb.length() - 1);
            str2 = sb.toString();
        }
        unifyCustomMdapReqPb.exinfo4 = str2;
        NetWorkInfo.a(b);
        unifyCustomMdapReqPb.ip = NetWorkInfo.c();
        unifyCustomMdapReqPb.lbslocation = behavior.a;
        unifyCustomMdapReqPb.logtime = a();
        NetWorkInfo.a(b);
        unifyCustomMdapReqPb.network = NetWorkInfo.b(b);
        DeviceInfo.a();
        unifyCustomMdapReqPb.os_version = DeviceInfo.n();
        unifyCustomMdapReqPb.seed = behavior.c;
        UserInfo lastLoginedUserInfo = AntExtServiceManager.getAuthService(b).getLastLoginedUserInfo();
        if (lastLoginedUserInfo != null) {
            unifyCustomMdapReqPb.userid = lastLoginedUserInfo.getUserId();
        }
        com.ali.user.mobile.info.DeviceInfo.b();
        unifyCustomMdapReqPb.utdid = com.ali.user.mobile.info.DeviceInfo.g();
        unifyCustomMdapReqPb.viewid = behavior.e;
        return unifyCustomMdapReqPb;
    }

    private static String a() {
        try {
            Calendar instance = Calendar.getInstance();
            StringBuilder sb = new StringBuilder();
            int i = instance.get(1);
            int i2 = instance.get(2) + 1;
            int i3 = instance.get(5);
            int i4 = instance.get(11);
            int i5 = instance.get(12);
            int i6 = instance.get(13);
            int i7 = instance.get(14);
            sb.append(i);
            sb.append('-');
            if (i2 < 10) {
                sb.append('0');
            }
            sb.append(i2);
            sb.append('-');
            if (i3 < 10) {
                sb.append('0');
            }
            sb.append(i3);
            sb.append(' ');
            if (i4 < 10) {
                sb.append('0');
            }
            sb.append(i4);
            sb.append(':');
            if (i5 < 10) {
                sb.append('0');
            }
            sb.append(i5);
            sb.append(':');
            if (i6 < 10) {
                sb.append('0');
            }
            sb.append(i6);
            sb.append(':');
            if (i7 < 100) {
                sb.append('0');
            }
            if (i7 < 10) {
                sb.append('0');
            }
            sb.append(i7);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }
}
