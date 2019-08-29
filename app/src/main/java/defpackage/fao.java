package defpackage;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: fao reason: default package */
/* compiled from: Device */
public final class fao {
    public static final boolean a = fbd.b((String) "ro.vivo.product.overseas", (String) BQCCameraParam.VALUE_NO).equals("yes");
    public static final String b;
    public static final boolean c = "RU".equals(b);
    public static final boolean d = "IN".equals(b);
    public static final boolean e = b("rom_1.0");
    public static final boolean f = b("rom_2.0");
    public static final boolean g = b("rom_2.5");
    public static final boolean h = b("rom_3.0");
    private static Method i = null;
    private static String j = null;
    private static String k = null;
    private static String l = "";
    private static String m = "";

    public static synchronized String a() {
        synchronized (fao.class) {
            if (j == null && k == null) {
                try {
                    Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class, String.class});
                    i = declaredMethod;
                    declaredMethod.setAccessible(true);
                    j = (String) i.invoke(null, new Object[]{"ro.vivo.rom", "@><@"});
                    k = (String) i.invoke(null, new Object[]{"ro.vivo.rom.version", "@><@"});
                } catch (Exception unused) {
                    fat.b((String) "Device", (String) "getRomCode error");
                }
            }
            StringBuilder sb = new StringBuilder("sRomProperty1 : ");
            sb.append(j);
            sb.append(" ; sRomProperty2 : ");
            sb.append(k);
            fat.d("Device", sb.toString());
            String a2 = a(j);
            if (!TextUtils.isEmpty(a2)) {
                return a2;
            }
            String a3 = a(k);
            if (!TextUtils.isEmpty(a3)) {
                return a3;
            }
            return null;
        }
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Matcher matcher = Pattern.compile("rom_([\\d]*).?([\\d]*)", 2).matcher(str);
        if (!matcher.find()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(matcher.group(1));
        sb.append(TextUtils.isEmpty(matcher.group(2)) ? "0" : matcher.group(2).substring(0, 1));
        return sb.toString();
    }

    private static boolean b(String str) {
        String b2 = fbd.b((String) "ro.vivo.rom", (String) "");
        String b3 = fbd.b((String) "ro.vivo.rom.version", (String) "");
        StringBuilder sb = new StringBuilder("ro.vivo.rom = ");
        sb.append(b2);
        sb.append(" ; ro.vivo.rom.version = ");
        sb.append(b3);
        fat.d("Device", sb.toString());
        return (b2 != null && b2.contains(str)) || (b3 != null && b3.contains(str));
    }

    public static boolean b() {
        if (TextUtils.isEmpty(Build.MANUFACTURER)) {
            fat.d("Device", "Build.MANUFACTURER is null");
            return false;
        }
        StringBuilder sb = new StringBuilder("Build.MANUFACTURER is ");
        sb.append(Build.MANUFACTURER);
        fat.d("Device", sb.toString());
        if (Build.MANUFACTURER.toLowerCase().contains("bbk") || Build.MANUFACTURER.toLowerCase().startsWith(DeviceProperty.ALIAS_VIVO)) {
            return true;
        }
        return false;
    }

    public static boolean c() {
        if (TextUtils.isEmpty(Build.MANUFACTURER)) {
            fat.d("Device", "Build.MANUFACTURER is null");
            return false;
        }
        StringBuilder sb = new StringBuilder("Build.MANUFACTURER is ");
        sb.append(Build.MANUFACTURER);
        fat.d("Device", sb.toString());
        return Build.MANUFACTURER.toLowerCase().contains(DeviceProperty.ALIAS_OPPO);
    }

    static {
        String str;
        if (VERSION.SDK_INT >= 26) {
            str = fbd.b((String) "ro.product.country.region", (String) "N");
        } else {
            str = fbd.b((String) "ro.product.customize.bbk", (String) "N");
        }
        b = str;
    }
}
