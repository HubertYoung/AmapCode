package com.alipay.rdssecuritysdk.v3.impl;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.commonbiz.external.UmidSdkWrapper;
import com.alipay.apmobilesecuritysdk.commonbiz.external.UtdidWrapper;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.mobile.security.senative.APSE;
import com.alipay.rdssecuritysdk.RDSModelServiceV3;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.deviceinfo.AppInfo;
import java.util.Map;
import org.json.JSONObject;

public class RDSModelServiceV3Impl extends RDSModelServiceV3 {
    protected static final String TAG = "RDS";
    private Context context = null;
    private TraceLogger logger = LoggerFactory.f();
    private RDSDataBuilder rdsDataBuilder = null;
    private UserBehaviourBuilder userBehaviourBuilder = null;

    public void reInit(Context context2, Map<String, String> map, boolean z) {
        Context context3 = context2;
        Map<String, String> map2 = map;
        this.userBehaviourBuilder = UserBehaviourBuilder.a();
        this.context = context3;
        String str = "";
        try {
            TokenResult tokenResult = APSecuritySdk.getInstance(context3).getTokenResult();
            if (tokenResult != null) {
                str = tokenResult.apdid;
            }
        } catch (Throwable unused) {
        }
        String str2 = str;
        String str3 = map2.get(DictionaryKeys.V2_UMID);
        if (CommonUtils.isBlank(str3)) {
            str3 = UmidSdkWrapper.getLocalUmidToken(context3);
        }
        String str4 = str3;
        String str5 = map2.get("utdid");
        if (CommonUtils.isBlank(str5)) {
            str5 = UtdidWrapper.getUtdid(context3);
        }
        String str6 = str5;
        String str7 = map2.get("tid");
        String str8 = map2.get("appver");
        if (CommonUtils.isBlank(str8)) {
            str8 = AppInfo.getInstance().getAppVersion(context3);
        }
        String str9 = str8;
        String str10 = map2.get(DictionaryKeys.V2_PACKAGENAME);
        if (CommonUtils.isBlank(str10)) {
            str10 = context3.getPackageName();
        }
        String str11 = str10;
        String str12 = map2.get("appname");
        if (CommonUtils.isBlank(str12)) {
            str12 = "";
        }
        String str13 = str12;
        this.rdsDataBuilder = RDSDataBuilder.a();
        Context context4 = context3;
        this.rdsDataBuilder.a(context4, str2, str4, str6, str7, z).c(context3).b(context3).a(context4, str9, map2.get("user"), str13, map2.get("appkey"), str11).d(context3);
    }

    public void updateUser(String str) {
        if (this.rdsDataBuilder != null) {
            this.rdsDataBuilder.a(str);
        }
    }

    public void onControlClick(String str, String str2) {
        if (this.userBehaviourBuilder != null) {
            this.userBehaviourBuilder.a(str, str2);
        }
    }

    public void onKeyDown(String str, String str2, String str3) {
        if (this.userBehaviourBuilder != null) {
            this.userBehaviourBuilder.a(str, str2, str3);
        }
    }

    public void onFocusChange(String str, String str2, boolean z) {
        if (this.userBehaviourBuilder != null) {
            this.userBehaviourBuilder.a(str, str2, z);
        }
    }

    public void onTouchScreen(String str, String str2, double d, double d2) {
        if (this.userBehaviourBuilder != null) {
            this.userBehaviourBuilder.a(str, str2, d, d2);
        }
    }

    public void onPage(String str, String str2) {
        if (this.userBehaviourBuilder != null) {
            this.userBehaviourBuilder.b(str, str2);
        }
    }

    public void onPageEnd() {
        if (this.userBehaviourBuilder != null && this.rdsDataBuilder != null) {
            this.rdsDataBuilder.a(this.context).a(this.userBehaviourBuilder.b());
            this.userBehaviourBuilder = UserBehaviourBuilder.a();
        }
    }

    public String getRdsRequestMessage(Context context2, APSE apse) {
        if (context2 == null || apse == null) {
            return null;
        }
        try {
            RdsRequestMessage b = this.rdsDataBuilder.b();
            String str = "4";
            byte[] zipEncryptAndSignRdsWithWua = apse.zipEncryptAndSignRdsWithWua(context2, b);
            if (zipEncryptAndSignRdsWithWua == null || zipEncryptAndSignRdsWithWua.length == 0) {
                str = "3";
                zipEncryptAndSignRdsWithWua = apse.encryptAndSignRdsWithWua(context2, b);
            }
            if (zipEncryptAndSignRdsWithWua != null && zipEncryptAndSignRdsWithWua.length > 0) {
                String str2 = new String(zipEncryptAndSignRdsWithWua);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("version", str);
                jSONObject.put("data", str2);
                String jSONObject2 = jSONObject.toString();
                TraceLogger traceLogger = this.logger;
                StringBuilder sb = new StringBuilder("RDSModelServiceV3Impl getRdsRequestMessage, version = ");
                sb.append(str);
                sb.append(" encrypt data length = ");
                sb.append(jSONObject2.length());
                traceLogger.b((String) CONST.LOG_TAG, sb.toString());
                return jSONObject2;
            }
        } catch (Throwable th) {
            TraceLogger traceLogger2 = this.logger;
            StringBuilder sb2 = new StringBuilder("RDSModelServiceV3Impl getRdsRequestMessage unexpected error happened, ");
            sb2.append(CommonUtils.getStackString(th));
            traceLogger2.d(CONST.LOG_TAG, sb2.toString());
        }
        return null;
    }
}
