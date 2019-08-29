package defpackage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.alipay.sdk.packet.d;
import com.amap.bundle.blutils.device.DeviceInfo;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ju reason: default package */
/* compiled from: UpgradeHelper */
public final class ju {

    /* renamed from: ju$a */
    /* compiled from: UpgradeHelper */
    public interface a {
        void a(boolean z, String str, File file, String str2);
    }

    public static com.autonavi.widget.ui.AlertView.a a(Context context, ji jiVar, String str) {
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(context);
        if (jiVar != null) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.amap_update, null);
            TextView textView = (TextView) inflate.findViewById(R.id.appdesc);
            CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.update_check);
            ((TextView) inflate.findViewById(R.id.appversion)).setVisibility(8);
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append(jiVar.b);
                sb.append("\n");
                sb.append(str);
                textView.setText(sb.toString());
            } else {
                textView.setText(jiVar.b);
            }
            if (!TextUtils.isEmpty(jiVar.m)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(AMapAppGlobal.getApplication().getString(R.string.app_download_use));
                sb2.append(jiVar.m);
                sb2.append(AMapAppGlobal.getApplication().getString(R.string.app_download_help));
                checkBox.setText(sb2.toString());
                checkBox.setVisibility(0);
            }
            aVar.a(inflate);
        } else {
            aVar.b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.app_download_install_msg));
        }
        return aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0036 A[Catch:{ Throwable -> 0x0040 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r5, java.io.File r6, defpackage.ju.a r7) {
        /*
            java.lang.Throwable r0 = new java.lang.Throwable
            java.lang.String r1 = "just for stack info."
            r0.<init>(r1)
            r0 = 1
            r1 = 0
            r2 = 0
            if (r6 == 0) goto L_0x0049
            boolean r3 = r6.exists()     // Catch:{ Throwable -> 0x0045 }
            if (r3 == 0) goto L_0x0049
            boolean r3 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0045 }
            if (r3 != 0) goto L_0x004a
            java.lang.String r3 = "null"
            boolean r3 = r5.equals(r3)     // Catch:{ Throwable -> 0x0045 }
            if (r3 == 0) goto L_0x0021
            goto L_0x004a
        L_0x0021:
            if (r6 == 0) goto L_0x002f
            boolean r3 = r6.exists()     // Catch:{ Throwable -> 0x0045 }
            if (r3 != 0) goto L_0x002a
            goto L_0x002f
        L_0x002a:
            java.lang.String r0 = defpackage.agy.a(r6, r1, r0)     // Catch:{ Throwable -> 0x0045 }
            goto L_0x0030
        L_0x002f:
            r0 = r1
        L_0x0030:
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0040 }
            if (r1 != 0) goto L_0x003e
            boolean r1 = r5.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x0040 }
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x004a
        L_0x003e:
            r1 = r0
            goto L_0x0049
        L_0x0040:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0046
        L_0x0045:
            r0 = move-exception
        L_0x0046:
            r0.printStackTrace()
        L_0x0049:
            r0 = 0
        L_0x004a:
            if (r7 == 0) goto L_0x004f
            r7.a(r0, r5, r6, r1)
        L_0x004f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ju.a(java.lang.String, java.io.File, ju$a):boolean");
    }

    public static String a() {
        return AMapAppGlobal.getApplication().getSharedPreferences("appupdatemd5info", 0).getString("update_md5_param", "");
    }

    public static Long b() {
        if (AMapAppGlobal.getApplication() == null || AMapAppGlobal.getApplication().getSharedPreferences("appDownloadUrl", 0) == null) {
            return Long.valueOf(0);
        }
        return Long.valueOf(AMapAppGlobal.getApplication().getSharedPreferences("appDownloadUrl", 0).getLong("auto_update_show_date", 0));
    }

    public static void a(long j) {
        AMapAppGlobal.getApplication().getSharedPreferences("appDownloadUrl", 0).edit().putLong("auto_update_show_date", j).apply();
    }

    public static boolean a(String str, String str2, String str3) {
        if (ji.y) {
            return false;
        }
        SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
        String string = sharedPrefs.getString("app_lv", "");
        String string2 = sharedPrefs.getString("app_b", "");
        String string3 = sharedPrefs.getString("app_vc", "");
        int i = sharedPrefs.getInt("app_uct", 0);
        if (TextUtils.isEmpty(string3) || !TextUtils.equals(string3, str3) || !TextUtils.equals(string, str) || !TextUtils.equals(string2, str2) || TextUtils.isEmpty(string)) {
            Editor edit = sharedPrefs.edit();
            if (i != 0) {
                edit.putInt("app_uct", 0).apply();
            }
            edit.putString("app_lv", str);
            edit.putString("app_b", str2);
            edit.putString("app_vc", str3);
            edit.apply();
            return false;
        } else if (i >= 3) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean d() {
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null) {
            return dfm.b();
        }
        return false;
    }

    public static void a(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_APP_INSTALL_DIALOG, "B001", jSONObject);
    }

    public static void b(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_APP_UPDATE_DIALOG, "B001", jSONObject);
    }

    public static String e() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(d.n, Build.MODEL);
            jSONObject.put("os", String.valueOf(VERSION.SDK_INT));
            Application application = AMapAppGlobal.getApplication();
            DeviceInfo instance = DeviceInfo.getInstance(application);
            jSONObject.put("high", ags.a((Context) application).height());
            jSONObject.put("width", ags.a((Context) application).width());
            int i = 0;
            jSONObject.put("memory", String.format("%.2f", new Object[]{Float.valueOf(((float) instance.getRAM()) / 1048576.0f)}));
            long[] storageInfo = instance.getStorageInfo();
            float f = 0.0f;
            if (storageInfo != null && storageInfo.length >= 2) {
                f = ((float) storageInfo[1]) / 1.07374182E9f;
            }
            jSONObject.put("free_storage", String.format("%.2f", new Object[]{Float.valueOf(f)}));
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
            if (iOfflineManager == null || iVoicePackageManager == null) {
                AMapLog.e("UpIdateHelper", "get Offline params, init IOfflineManager failed.");
            } else {
                boolean hasNaviTTS = iVoicePackageManager.hasNaviTTS();
                boolean hasCross = iOfflineManager.hasCross();
                if (iOfflineManager.hasOfflineData() || hasNaviTTS || hasCross) {
                    i = 1;
                }
            }
            jSONObject.put("offline_flag", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jSONObject2 = jSONObject.toString();
        AMapLog.d("UpIdateHelper", "ae8 params: ".concat(String.valueOf(jSONObject2)));
        return jSONObject2;
    }

    public static boolean c() {
        return bin.a.p(UploadConstants.STATUS_PUSH_NOTIFIED) == 1;
    }
}
