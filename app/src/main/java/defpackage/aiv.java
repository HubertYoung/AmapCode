package defpackage;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Pair;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.jni.bundle.voiceservice.DriveVoiceKeyHelper;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aiv reason: default package */
/* compiled from: VoiceServiceUtil */
public final class aiv {
    private static Set<String> a = new HashSet();
    private static Set<aib> b = new HashSet();

    public static synchronized boolean a() {
        synchronized (aiv.class) {
            String stringValue = new MapSharePreference((String) "SharedPreferences").getStringValue("amap_service_sp_key", "");
            aiq.b("语音获取本地云控".concat(String.valueOf(stringValue)));
            if (TextUtils.isEmpty(stringValue)) {
                return false;
            }
            try {
                if (new JSONObject(stringValue).getInt("voice_sdk") == 1) {
                    return true;
                }
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static synchronized void a(String str) {
        synchronized (aiv.class) {
            aiq.b("语音保存本地云控".concat(String.valueOf(str)));
            new MapSharePreference((String) "SharedPreferences").edit().putString("amap_service_sp_key", str).apply();
        }
    }

    public static Pair<aib, Boolean> a(Context context, int i) {
        String[] packagesForUid = context.getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null) {
            return new Pair<>(new aib("", ""), Boolean.FALSE);
        }
        boolean z = false;
        Pair<aib, Boolean> pair = null;
        if (eqo.a) {
            String a2 = a(packagesForUid);
            boolean z2 = !TextUtils.isEmpty(a2);
            Pair<aib, Boolean> pair2 = new Pair<>(new aib(a2), Boolean.valueOf(z2));
            z = z2;
            pair = pair2;
        }
        if (!z) {
            pair = a(context, packagesForUid);
            z = ((Boolean) pair.second).booleanValue();
        }
        if (z) {
            return pair;
        }
        AMapLog.d("AMapService", "onTransact包名不再白名单");
        aiq.a((String) "logUnSafePackage", (String) "onTransact包名不再白名单");
        return new Pair<>(new aib("Unknown", ""), Boolean.FALSE);
    }

    private static Pair<aib, Boolean> a(Context context, String[] strArr) {
        if (b.isEmpty()) {
            b = b();
        }
        r1 = null;
        boolean z = false;
        for (aib aib : b) {
            try {
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            if (aib != null) {
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        continue;
                        break;
                    }
                    String str = strArr[i];
                    if (aib.c && !TextUtils.isEmpty(aib.a) && !TextUtils.isEmpty(aib.b) && aib.a.equals(str) && aib.b.equals(a(context, str))) {
                        z |= true;
                        continue;
                        break;
                    }
                    i++;
                }
            }
            if (z) {
                break;
            }
        }
        return new Pair<>(aib, Boolean.valueOf(z));
    }

    private static String a(String[] strArr) {
        if (a.isEmpty()) {
            a = c();
        }
        r1 = "";
        boolean z = false;
        for (String str : a) {
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (TextUtils.equals(strArr[i], str)) {
                    z |= true;
                    continue;
                    break;
                } else {
                    i++;
                }
            }
            if (z) {
                break;
            }
        }
        return z ? str : "";
    }

    private static Set<aib> b() {
        HashSet hashSet = new HashSet();
        for (String decryptedString : aic.a) {
            String decryptedString2 = DriveVoiceKeyHelper.getDecryptedString(decryptedString);
            if (!TextUtils.isEmpty(decryptedString2)) {
                try {
                    JSONObject jSONObject = new JSONObject(decryptedString2);
                    String next = jSONObject.keys().next();
                    if (!TextUtils.isEmpty(next)) {
                        JSONArray jSONArray = jSONObject.getJSONArray(next);
                        for (int i = 0; i < jSONArray.length(); i++) {
                            hashSet.add(new aib(next, ((JSONObject) jSONArray.get(i)).optString("sha1")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return hashSet;
    }

    private static Set<String> c() {
        HashSet hashSet = new HashSet();
        String decryptedString = DriveVoiceKeyHelper.getDecryptedString("sh8dqtYryqjkM0JSy911GLNJNKCBK+Fi3PiimQP7plbr0icnGFZ7f+g9UwPHjLQl2NUyAmAm5ooq7PBnMCiA+3ePu2/YwOhWZeIONyCFJsyL9vH8uA0c2NYZKyqOUxwqk3uE7bIvsi6B8RI+X4mWXv9t3zp7Zpng86wGra/AXV0=");
        if (TextUtils.isEmpty(decryptedString)) {
            return hashSet;
        }
        try {
            JSONArray jSONArray = new JSONArray(decryptedString);
            for (int i = 0; i < jSONArray.length(); i++) {
                hashSet.add(jSONArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hashSet;
    }

    private static String a(Context context, String str) {
        PackageInfo packageInfo;
        CertificateFactory certificateFactory;
        X509Certificate x509Certificate = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 64);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        String str2 = "";
        if (packageInfo == null) {
            return str2;
        }
        Signature[] signatureArr = packageInfo.signatures;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((signatureArr.length <= 0 || signatureArr[0] == null) ? null : signatureArr[0].toByteArray());
        try {
            certificateFactory = CertificateFactory.getInstance("X509");
        } catch (Exception e2) {
            e2.printStackTrace();
            certificateFactory = null;
        }
        if (certificateFactory != null) {
            try {
                x509Certificate = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            if (x509Certificate != null) {
                str2 = a(instance.digest(x509Certificate.getEncoded()));
            }
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
        } catch (CertificateEncodingException e5) {
            e5.printStackTrace();
        }
        return str2;
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            String hexString = Integer.toHexString(bArr[i]);
            int length = hexString.length();
            if (length == 1) {
                hexString = "0".concat(String.valueOf(hexString));
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            sb.append(hexString.toUpperCase());
            if (i < bArr.length - 1) {
                sb.append(':');
            }
        }
        return sb.toString();
    }
}
