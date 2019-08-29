package defpackage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;
import java.util.List;

/* renamed from: jw reason: default package */
/* compiled from: UpdateHintConfigDownloaderHelper */
public final class jw {
    public static String a(String str) {
        String str2 = "";
        String mapBaseStorage = FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication());
        if (!TextUtils.isEmpty(mapBaseStorage)) {
            StringBuilder sb = new StringBuilder();
            sb.append(mapBaseStorage);
            sb.append("/autonavi/updateConfig/");
            sb.append(str);
            sb.append(File.separator);
            str2 = sb.toString();
            File file = new File(str2);
            if (!file.exists()) {
                try {
                    file.mkdirs();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
        return str2;
    }

    public static void a() {
        ahn.b().execute(new Runnable() {
            public final void run() {
                String mapBaseStorage = FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication());
                if (!TextUtils.isEmpty(mapBaseStorage)) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append(mapBaseStorage);
                        sb.append("/autonavi/updateConfig/");
                        ahd.c(new File(sb.toString()));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(mapBaseStorage);
                        sb2.append("/autonavi/updateConfig/");
                        File file = new File(sb2.toString());
                        if (file.isDirectory()) {
                            File[] listFiles = file.listFiles();
                            if (listFiles != null) {
                                if (listFiles.length != 0) {
                                    for (File file2 : listFiles) {
                                        if (file2.isDirectory() && file2.exists()) {
                                            file2.delete();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
            edit.putString("update_hint_config_download_complete", str.toUpperCase());
            edit.putString("update_hint_config_file_download_complete_version", str2);
            edit.commit();
        }
    }

    public static void b(String str) {
        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
        edit.remove("update_hint_config_download_complete");
        edit.remove("update_hint_config_file_download_complete_version");
        StringBuilder sb = new StringBuilder("new_update_hint_config_file_download_apk_is_wifi");
        sb.append(str.toUpperCase());
        sb.append(0);
        edit.remove(sb.toString());
        StringBuilder sb2 = new StringBuilder("new_update_hint_config_file_download_apk_is_wifi");
        sb2.append(str.toUpperCase());
        sb2.append(1);
        edit.remove(sb2.toString());
        edit.apply();
    }

    protected static boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.toUpperCase().equals(new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString("update_hint_config_download_complete", ""));
    }

    public static void a(String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            boolean z = true;
            if (i == 0 || i == 1) {
                if (1 != aaw.d(AMapAppGlobal.getApplication())) {
                    z = false;
                }
                Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
                StringBuilder sb = new StringBuilder("new_update_hint_config_file_download_apk_is_wifi");
                sb.append(str.toUpperCase());
                sb.append(i);
                edit.putBoolean(sb.toString(), z);
            }
        }
    }

    public static boolean b(String str, int i) {
        if (TextUtils.isEmpty(str) || (i != 0 && i != 1)) {
            return false;
        }
        SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
        StringBuilder sb = new StringBuilder("new_update_hint_config_file_download_apk_is_wifi");
        sb.append(str.toUpperCase());
        sb.append(i);
        return sharedPrefs.getBoolean(sb.toString(), false);
    }

    public static boolean b(String str, String str2) {
        try {
            ahf.a(str, str2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean a(String str, List<b> list) {
        if (TextUtils.isEmpty(str) || list == null || list.size() == 0) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || file.isFile()) {
            return false;
        }
        boolean z = true;
        for (b next : list) {
            if (!TextUtils.isEmpty(next.c) && !TextUtils.isEmpty(next.d)) {
                z = b(str, next);
                if (!z) {
                    break;
                }
            }
        }
        return z;
    }

    public static String a(String str, b bVar) {
        String str2 = "";
        if (TextUtils.isEmpty(str) || bVar == null) {
            return str2;
        }
        if (b(str, bVar)) {
            String str3 = bVar.f;
            char c = 65535;
            int hashCode = str3.hashCode();
            if (hashCode != -2043608161) {
                if (hashCode != -1839152530) {
                    if (hashCode != 70564) {
                        if (hashCode == 81665115 && str3.equals("VIDEO")) {
                            c = 2;
                        }
                    } else if (str3.equals("GIF")) {
                        c = 1;
                    }
                } else if (str3.equals("STATIC")) {
                    c = 0;
                }
            } else if (str3.equals("LOTTIE")) {
                c = 3;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(d(bVar.c));
                    str2 = sb.toString();
                    break;
                case 3:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(bVar.e);
                    sb2.append(".json");
                    str2 = sb2.toString();
                    if (!new File(str2).exists()) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append(d(bVar.c));
                        b(sb3.toString(), str);
                        break;
                    }
                    break;
            }
        }
        return str2;
    }

    protected static boolean b(String str, b bVar) {
        if (bVar == null || TextUtils.isEmpty(bVar.c)) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(d(bVar.c));
        File file = new File(sb.toString());
        if (file.exists() && a(file, bVar.d)) {
            return true;
        }
        return false;
    }

    public static String d(String str) {
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        int lastIndexOf = str.lastIndexOf(File.separator);
        if (lastIndexOf >= 0) {
            try {
                str2 = str.substring(lastIndexOf + 1);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return str2;
    }

    public static boolean a(File file, String str) {
        if (file == null || !file.exists() || TextUtils.isEmpty(str) || !str.equalsIgnoreCase(agy.a(file, null, true))) {
            return false;
        }
        return true;
    }
}
