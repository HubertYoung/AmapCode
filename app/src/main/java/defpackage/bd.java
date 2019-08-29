package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import java.util.HashMap;
import java.util.Map;

/* renamed from: bd reason: default package */
/* compiled from: SecurityGuardImpl */
final class bd implements ba {
    private static String a = "awcn.DefaultSecurityGuard";
    private static boolean b = false;
    private static Map<String, Integer> c;
    private String d = null;

    public final boolean a() {
        return false;
    }

    static {
        try {
            Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
            b = true;
            HashMap hashMap = new HashMap();
            c = hashMap;
            hashMap.put("HMAC_SHA1", Integer.valueOf(3));
            c.put("ASE128", Integer.valueOf(16));
        } catch (Throwable unused) {
            b = false;
        }
    }

    bd(String str) {
        this.d = str;
    }

    public final String a(Context context, String str, String str2, String str3) {
        if (!b || context == null || TextUtils.isEmpty(str2) || !c.containsKey(str)) {
            return null;
        }
        try {
            ISecureSignatureComponent secureSignatureComp = SecurityGuardManager.getInstance(context).getSecureSignatureComp();
            if (secureSignatureComp != null) {
                SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
                securityGuardParamContext.appKey = str2;
                securityGuardParamContext.paramMap.put("INPUT", str3);
                securityGuardParamContext.requestType = c.get(str).intValue();
                return secureSignatureComp.signRequest(securityGuardParamContext, this.d);
            }
        } catch (Throwable unused) {
            cl.e(a, "Securityguard sign request failed.", null, new Object[0]);
        }
        return null;
    }

    public final byte[] a(Context context, String str, String str2, byte[] bArr) {
        if (!b || context == null || bArr == null || TextUtils.isEmpty(str2) || !c.containsKey(str)) {
            return null;
        }
        Integer num = c.get(str);
        if (num == null) {
            return null;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance != null) {
                IStaticDataEncryptComponent staticDataEncryptComp = instance.getStaticDataEncryptComp();
                if (staticDataEncryptComp != null) {
                    return staticDataEncryptComp.staticBinarySafeDecryptNoB64(num.intValue(), str2, bArr, this.d);
                }
            }
        } catch (Throwable unused) {
            cl.e(a, "staticBinarySafeDecryptNoB64", null, new Object[0]);
        }
        return null;
    }

    public final boolean a(Context context, String str, byte[] bArr) {
        boolean z = false;
        if (context == null || bArr == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance != null) {
                IDynamicDataStoreComponent dynamicDataStoreComp = instance.getDynamicDataStoreComp();
                if (!(dynamicDataStoreComp == null || dynamicDataStoreComp.putByteArray(str, bArr) == 0)) {
                    z = true;
                }
            }
        } catch (Throwable unused) {
            cl.e(a, "saveBytes", null, new Object[0]);
        }
        return z;
    }

    public final byte[] a(Context context, String str) {
        byte[] bArr = null;
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance != null) {
                IDynamicDataStoreComponent dynamicDataStoreComp = instance.getDynamicDataStoreComp();
                if (dynamicDataStoreComp != null) {
                    bArr = dynamicDataStoreComp.getByteArray(str);
                }
            }
        } catch (Throwable unused) {
            cl.e(a, "getBytes", null, new Object[0]);
        }
        return bArr;
    }
}
