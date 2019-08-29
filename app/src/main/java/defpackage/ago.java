package defpackage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.amap.bundle.utils.device.DisplayType;
import java.lang.reflect.Method;

/* renamed from: ago reason: default package */
/* compiled from: DisplayTypeAPI */
final class ago {

    /* renamed from: ago$a */
    /* compiled from: DisplayTypeAPI */
    interface a {
        DisplayType a(Context context) throws Exception;
    }

    /* renamed from: ago$b */
    /* compiled from: DisplayTypeAPI */
    static class b implements a {
        b() {
        }

        public final DisplayType a(Context context) throws Exception {
            Class<?> loadClass = context.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method method = loadClass.getMethod("hasNotchInScreen", new Class[0]);
            method.setAccessible(true);
            if (((Boolean) method.invoke(loadClass, new Object[0])).booleanValue()) {
                return DisplayType.CUTOUT;
            }
            return DisplayType.NORMAL;
        }
    }

    /* renamed from: ago$c */
    /* compiled from: DisplayTypeAPI */
    static class c implements a {
        c() {
        }

        public final DisplayType a(Context context) throws Exception {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                throw new IllegalStateException("PackageManager is null");
            } else if (packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")) {
                return DisplayType.CUTOUT;
            } else {
                return DisplayType.NORMAL;
            }
        }
    }

    /* renamed from: ago$d */
    /* compiled from: DisplayTypeAPI */
    static class d implements a {
        d() {
        }

        public final DisplayType a(Context context) throws Exception {
            Method declaredMethod = context.getClassLoader().loadClass("android.util.FtFeature").getDeclaredMethod("isFeatureSupport", new Class[]{Integer.TYPE});
            declaredMethod.setAccessible(true);
            if (((Boolean) declaredMethod.invoke(null, new Object[]{Integer.valueOf(32)})).booleanValue()) {
                return DisplayType.CUTOUT;
            }
            return DisplayType.NORMAL;
        }
    }

    /* renamed from: ago$e */
    /* compiled from: DisplayTypeAPI */
    static class e implements a {
        e() {
        }

        public final DisplayType a(Context context) {
            return TextUtils.equals(aht.a().a("ro.miui.notch"), "1") ? DisplayType.CUTOUT : DisplayType.NORMAL;
        }
    }

    static DisplayType a(Context context) throws IllegalStateException {
        a aVar;
        String str = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(str)) {
            String lowerCase = str.toLowerCase();
            char c2 = 65535;
            int hashCode = lowerCase.hashCode();
            if (hashCode != -1206476313) {
                if (hashCode != -759499589) {
                    if (hashCode != 3418016) {
                        if (hashCode == 3620012 && lowerCase.equals(DeviceProperty.ALIAS_VIVO)) {
                            c2 = 2;
                        }
                    } else if (lowerCase.equals(DeviceProperty.ALIAS_OPPO)) {
                        c2 = 1;
                    }
                } else if (lowerCase.equals("xiaomi")) {
                    c2 = 3;
                }
            } else if (lowerCase.equals(DeviceProperty.ALIAS_HUAWEI)) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                    aVar = new b();
                    break;
                case 1:
                    aVar = new c();
                    break;
                case 2:
                    aVar = new d();
                    break;
                case 3:
                    aVar = new e();
                    break;
            }
        }
        aVar = null;
        if (aVar == null) {
            return DisplayType.NORMAL;
        }
        try {
            return aVar.a(context);
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            throw e2;
        } catch (Exception unused) {
            return DisplayType.NORMAL;
        }
    }
}
