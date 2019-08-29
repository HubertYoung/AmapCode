package com.ali.auth.third.core.util;

import android.app.Application;
import android.content.pm.PackageInfo;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.InternalSession;
import com.ali.auth.third.core.model.User;
import com.taobao.accs.common.Constants;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.json.JSONException;
import org.json.JSONObject;

public class SystemUtils {
    public static String getApkPublicKeyDigest() {
        try {
            PackageInfo packageInfo = KernelContext.getApplicationContext().getPackageManager().getPackageInfo(KernelContext.getApplicationContext().getPackageName(), 64);
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            instance.update(((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).getPublicKey().toString().getBytes());
            return CommonUtils.getHashString(instance.digest());
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getApkSignNumber() {
        try {
            return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(KernelContext.getApplicationContext().getPackageManager().getPackageInfo(KernelContext.getApplicationContext().getPackageName(), 64).signatures[0].toByteArray()))).getSerialNumber().toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public static Application getSystemApp() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
            Field declaredField = cls.getDeclaredField("mInitialApplication");
            declaredField.setAccessible(true);
            return (Application) declaredField.get(declaredMethod.invoke(null, new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toInternalSessionJSON(InternalSession internalSession) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("loginTime", internalSession.loginTime);
            jSONObject.put("expireIn", internalSession.expireIn);
            jSONObject.put(Constants.KEY_SID, internalSession.sid);
            jSONObject.put("mobile", internalSession.mobile);
            jSONObject.put("loginId", internalSession.loginId);
            jSONObject.put("autoLoginToken", internalSession.autoLoginToken);
            jSONObject.put("topAccessToken", internalSession.topAccessToken);
            jSONObject.put("topAuthCode", internalSession.topAuthCode);
            jSONObject.put("topExpireTime", internalSession.topExpireTime);
            User user = internalSession.user;
            if (user != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("avatarUrl", user.avatarUrl);
                jSONObject2.put("userId", user.userId);
                jSONObject2.put("openId", user.openId);
                jSONObject2.put("openSid", user.openSid);
                jSONObject2.put("nick", user.nick);
                jSONObject2.put("deviceTokenKey", user.deviceTokenKey);
                jSONObject2.put("deviceTokenSalt", user.deviceTokenSalt);
                jSONObject.put("user", jSONObject2);
            }
            if (internalSession.otherInfo != null) {
                jSONObject.put("otherInfo", JSONUtils.toJsonObject(internalSession.otherInfo));
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
