package defpackage;

import android.os.AsyncTask;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;

/* renamed from: bdc reason: default package */
/* compiled from: SwitchNetworkUtil */
public final class bdc {

    /* renamed from: bdc$a */
    /* compiled from: SwitchNetworkUtil */
    public static class a extends AsyncTask<Integer, Void, Integer> {
        private a() {
        }

        public /* synthetic */ a(byte b) {
            this();
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            int intValue = ((Integer[]) objArr)[0].intValue();
            if (intValue == 1) {
                ToastHelper.showToast("已切换到内网环境,开始清理缓存...!\n重启生效！");
            } else if (intValue == 2) {
                ToastHelper.showToast("已切换到预发环境,开始清理缓存...!\n重启生效！");
            } else {
                ToastHelper.showToast("已切换到公网环境,开始清理缓存...!\n重启生效！");
            }
            a(AMapAppGlobal.getApplication().getFilesDir());
            File cacheDir = AMapAppGlobal.getApplication().getCacheDir();
            a(cacheDir);
            StringBuilder sb = new StringBuilder();
            sb.append(cacheDir.getParent());
            sb.append(File.separator);
            sb.append("databases");
            a(new File(sb.toString()));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(cacheDir.getParent());
            sb2.append(File.separator);
            sb2.append("shared_prefs");
            a(new File(sb2.toString()));
            a(AMapAppGlobal.getApplication().getExternalCacheDir().getParentFile());
            return Integer.valueOf(intValue);
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            switch (((Integer) obj).intValue()) {
                case 1:
                    bqd bqd = defpackage.bqd.a.a;
                    if (bqd.a == null) {
                        bqd.a = AMapAppGlobal.getApplication().getSharedPreferences("network_env_cfg", 0);
                    }
                    bqd.a.edit().putBoolean("isInternal", true).commit();
                    bqd.a.edit().putInt("package_type", 1).commit();
                    break;
                case 2:
                    bqd bqd2 = defpackage.bqd.a.a;
                    if (bqd2.a == null) {
                        bqd2.a = AMapAppGlobal.getApplication().getSharedPreferences("network_env_cfg", 0);
                    }
                    bqd2.a.edit().putBoolean("isInternal", true).commit();
                    bqd2.a.edit().putInt("package_type", 2).commit();
                    break;
                default:
                    bqd bqd3 = defpackage.bqd.a.a;
                    if (bqd3.a == null) {
                        bqd3.a = AMapAppGlobal.getApplication().getSharedPreferences("network_env_cfg", 0);
                    }
                    bqd3.a.edit().putBoolean("isInternal", false).commit();
                    bqd3.a.edit().putInt("package_type", 0).commit();
                    break;
            }
            b.a();
        }

        private long a(File file) {
            long j = 0;
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (int i = 0; i < listFiles.length; i++) {
                        if (listFiles[i].isDirectory()) {
                            j += a(listFiles[i]);
                        } else {
                            j += listFiles[i].length();
                            listFiles[i].delete();
                        }
                    }
                }
                file.delete();
            } else {
                file.delete();
            }
            return j;
        }
    }

    public static void a() {
        new a(0).execute(new Integer[]{Integer.valueOf(1)});
    }

    public static void b() {
        new a(0).execute(new Integer[]{Integer.valueOf(0)});
    }
}
