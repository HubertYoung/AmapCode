package defpackage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.alibaba.wireless.security.SecExceptionCode;
import com.amap.bundle.utils.platform.RomTypeUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: kj reason: default package */
/* compiled from: PermissionUtil */
public abstract class kj {
    public static boolean a = false;
    private static final String b = AMapAppGlobal.getApplication().getString(R.string.permission_camera);
    private static final String c = AMapAppGlobal.getApplication().getString(R.string.permission_phone);
    private static final String d = AMapAppGlobal.getApplication().getString(R.string.permission_location);
    private static final String e = AMapAppGlobal.getApplication().getString(R.string.permission_audio);
    private static final String f = AMapAppGlobal.getApplication().getString(R.string.permission_storage);
    private static final String g = AMapAppGlobal.getApplication().getString(R.string.permission_contacts);
    private static final String h = AMapAppGlobal.getApplication().getString(R.string.permission_send_sms);
    private static final String i = AMapAppGlobal.getApplication().getString(R.string.permission_read_sms);
    private static final String[] j;

    /* renamed from: kj$a */
    /* compiled from: PermissionUtil */
    public interface a {
        void a(b bVar);

        void b(b bVar);
    }

    /* renamed from: kj$b */
    /* compiled from: PermissionUtil */
    public static class b {
        /* access modifiers changed from: protected */
        public void reject() {
        }

        /* access modifiers changed from: protected */
        public void run() {
        }

        public void callback() {
            callback(true);
        }

        public void callback(boolean z) {
            if (z) {
                run();
            } else {
                reject();
            }
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
        arrayList.add("android.permission.ACCESS_FINE_LOCATION");
        if (VERSION.SDK_INT >= 16) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        j = (String[]) arrayList.toArray(new String[0]);
    }

    private static String a(String str) {
        if (str.equals("android.permission.CAMERA")) {
            return b;
        }
        if (str.equals("android.permission.READ_PHONE_STATE")) {
            return c;
        }
        if (str.equals("android.permission.ACCESS_COARSE_LOCATION") || str.equals("android.permission.ACCESS_FINE_LOCATION")) {
            return d;
        }
        if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE") || str.equals("android.permission.READ_EXTERNAL_STORAGE")) {
            return f;
        }
        if (str.equals("android.permission.RECORD_AUDIO")) {
            return e;
        }
        if (str.equals("android.permission.READ_CONTACTS")) {
            return g;
        }
        if (str.equals("android.permission.READ_SMS")) {
            return i;
        }
        if (str.equals("android.permission.SEND_SMS")) {
            return h;
        }
        return "";
    }

    public static String a(List<String> list) {
        if (list.size() <= 0) {
            return "";
        }
        String string = AMapAppGlobal.getApplication().getString(R.string.permission_tip_splitter);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (i2 != 0) {
                sb.append(string);
            }
            sb.append(a(list.get(i2)));
        }
        return String.format(AMapAppGlobal.getApplication().getString(R.string.permission_tip), new Object[]{sb.toString()});
    }

    @TargetApi(23)
    public static boolean a(Context context, String str) {
        if (b() && context.checkSelfPermission(str) != 0) {
            return false;
        }
        return true;
    }

    @TargetApi(23)
    public static boolean a(Context context, String[] strArr) {
        if (!b()) {
            return true;
        }
        for (int i2 = 0; i2 <= 0; i2++) {
            if (context.checkSelfPermission(strArr[0]) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(Activity activity, String[] strArr) {
        return c(activity, strArr, null);
    }

    public static boolean a(Activity activity, String[] strArr, b bVar) {
        return c(activity, strArr, bVar);
    }

    @TargetApi(23)
    private static boolean c(Activity activity, String[] strArr, b bVar) {
        boolean z = false;
        if (b()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (String str : strArr) {
                if (!a(activity, (List<String>) arrayList2, str)) {
                    String a2 = a(str);
                    if (TextUtils.isEmpty(a2)) {
                        a2 = "";
                    }
                    arrayList.add(a2);
                }
            }
            if (arrayList2.size() > 0) {
                if (activity instanceof a) {
                    ((a) activity).a(bVar);
                }
                arrayList.size();
                activity.requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 1);
                if (z && bVar != null) {
                    bVar.callback(true);
                }
                return z;
            }
        }
        z = true;
        bVar.callback(true);
        return z;
    }

    @TargetApi(23)
    public static boolean a(Context context) {
        if (!b()) {
            return true;
        }
        return System.canWrite(context);
    }

    @TargetApi(23)
    public static boolean a(Context context, b bVar) {
        boolean z = false;
        try {
            if (!b() || System.canWrite(context)) {
                z = true;
                if (z && bVar != null) {
                    bVar.callback(true);
                }
                return z;
            }
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.permission_tip_write_settings));
            StringBuilder sb = new StringBuilder("package:");
            sb.append(context.getPackageName());
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS", Uri.parse(sb.toString()));
            if (!(context instanceof Activity) || !(context instanceof a) || bVar == null) {
                context.startActivity(intent);
            } else {
                ((a) context).b(bVar);
                ((Activity) context).startActivityForResult(intent, SecExceptionCode.SEC_ERROR_AVMP_SAFETOKEN_INVALID_PARAM);
            }
            bVar.callback(true);
            return z;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean b(Context context) {
        return a(context, (b) null);
    }

    @TargetApi(23)
    public static boolean c(Context context) {
        if (!b()) {
            return true;
        }
        return Settings.canDrawOverlays(context);
    }

    @TargetApi(23)
    public static boolean d(Context context) {
        try {
            if (!b() || Settings.canDrawOverlays(context)) {
                return true;
            }
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.permission_tip_overlay));
            StringBuilder sb = new StringBuilder("package:");
            sb.append(context.getPackageName());
            context.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse(sb.toString())));
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @TargetApi(23)
    private static boolean a(Activity activity, List<String> list, String str) {
        if (!a((Context) activity, str)) {
            list.add(str);
            if (!activity.shouldShowRequestPermissionRationale(str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean b() {
        return VERSION.SDK_INT >= 23;
    }

    public static boolean e(Context context) {
        if (context == null) {
            return false;
        }
        try {
            context.startActivity(RomTypeUtil.a(context.getPackageName()));
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            Intent intent = new Intent("android.settings.SETTINGS");
            intent.setFlags(268435456);
            context.startActivity(intent);
            return false;
        }
    }

    @TargetApi(23)
    public static boolean a() {
        if (b()) {
            return a((Context) AMapAppGlobal.getApplication(), (String) "android.permission.WRITE_EXTERNAL_STORAGE");
        }
        return true;
    }

    @Deprecated
    public static boolean b(Activity activity, String[] strArr, b bVar) {
        return c(activity, strArr, bVar);
    }
}
