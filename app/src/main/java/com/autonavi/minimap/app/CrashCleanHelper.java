package com.autonavi.minimap.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.bundle.network.response.AosParserResponse;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.map.activity.SplashActivity;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bl.NetworkInitializer;
import com.autonavi.minimap.bl.NetworkInitializer.b;
import com.autonavi.minimap.bl.net.INetworkProvider;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Iterator;

public final class CrashCleanHelper {
    public boolean a;
    boolean b = false;
    Button c;
    ImageView d;
    TextView e;
    TextView f;
    Activity g;

    static class CrashCallback implements AosResponseCallback<CrashParser> {
        public /* bridge */ /* synthetic */ void onSuccess(AosResponse aosResponse) {
        }

        private CrashCallback() {
        }

        /* synthetic */ CrashCallback(byte b) {
            this();
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            if (aosResponseException != null && aosResponseException.exception != null) {
                aosResponseException.exception.printStackTrace();
            }
        }
    }

    @Path(builder = AosURLBuilder.class, host = "lotuspool_upload_url", sign = {"diu", "div"}, url = "/ud/updata")
    class CrashParams implements ParamEntity {
        public String content;
        public int environment;
        public String md5;
        public String mode;
        public long uploadTime;

        private CrashParams() {
            this.content = "crash_warning";
            this.md5 = emx.a("crash_warning");
            this.mode = "crash_warning";
            this.environment = "wifi".equals(aaw.a(CrashCleanHelper.this.g.getApplicationContext())) ^ true ? 1 : 0;
            this.uploadTime = System.currentTimeMillis();
        }

        /* synthetic */ CrashParams(CrashCleanHelper crashCleanHelper, byte b) {
            this();
        }
    }

    public static class CrashParser extends AosParserResponse {
        public final String b() {
            return null;
        }
    }

    static class a extends AsyncTask<Object, Integer, Boolean> {
        private WeakReference<CrashCleanHelper> a;

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            CrashCleanHelper crashCleanHelper = (CrashCleanHelper) this.a.get();
            if (((Boolean) obj).booleanValue() && crashCleanHelper != null) {
                crashCleanHelper.b = true;
                crashCleanHelper.c.setClickable(true);
                crashCleanHelper.c.setBackgroundResource(R.drawable.crash_repair_button_shape_pressed);
                crashCleanHelper.c.setText("重启客户端");
                crashCleanHelper.c.setTextColor(R.color.black);
                crashCleanHelper.d.setImageResource(R.drawable.crash_warning_b);
                crashCleanHelper.e.setText("完成修复");
                crashCleanHelper.f.setText("已完成修复，请重启高德地图客户端");
            }
        }

        public a(CrashCleanHelper crashCleanHelper) {
            this.a = new WeakReference<>(crashCleanHelper);
        }

        /* access modifiers changed from: protected */
        public final void onPreExecute() {
            CrashCleanHelper crashCleanHelper = (CrashCleanHelper) this.a.get();
            if (crashCleanHelper != null) {
                crashCleanHelper.c.setText("修复中...");
                crashCleanHelper.c.setClickable(false);
            }
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            boolean z;
            CrashCleanHelper crashCleanHelper = (CrashCleanHelper) this.a.get();
            if (crashCleanHelper != null) {
                Activity activity = crashCleanHelper.g;
                String packageName = crashCleanHelper.g.getPackageName();
                int myPid = Process.myPid();
                Iterator<RunningAppProcessInfo> it = ((ActivityManager) activity.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses().iterator();
                while (true) {
                    z = false;
                    if (!it.hasNext()) {
                        break;
                    }
                    RunningAppProcessInfo next = it.next();
                    if (next.processName.equalsIgnoreCase(packageName)) {
                        if (next.pid == myPid) {
                            z = true;
                        }
                    }
                }
                if (z) {
                    crashCleanHelper.a(crashCleanHelper.g.getFilesDir().getParentFile());
                    crashCleanHelper.a(crashCleanHelper.g.getExternalCacheDir().getParentFile());
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
                    sb.append(FilePathHelper.APP_FOLDER);
                    crashCleanHelper.a(new File(sb.toString()));
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Environment.getExternalStorageDirectory().getAbsolutePath());
                    sb2.append("/amap");
                    crashCleanHelper.a(new File(sb2.toString()));
                }
            }
            return Boolean.TRUE;
        }
    }

    public CrashCleanHelper(Activity activity) {
        this.g = activity;
    }

    public final boolean a() {
        SharedPreferences sharedPreferences = this.g.getSharedPreferences("crash_record", 0);
        if (sharedPreferences.getInt("crash_count", 0) < 3) {
            return false;
        }
        this.a = true;
        sharedPreferences.edit().putInt("crash_count", 0).apply();
        sharedPreferences.edit().putLong("launch_time", 0).apply();
        sharedPreferences.edit().putBoolean("crash_on", false).apply();
        this.g.setContentView(R.layout.crash_repair_layout);
        this.c = (Button) this.g.findViewById(16908313);
        this.d = (ImageView) this.g.findViewById(16908294);
        this.e = (TextView) this.g.findViewById(16908308);
        this.f = (TextView) this.g.findViewById(16908309);
        this.c.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!CrashCleanHelper.this.b) {
                    if (!"修复中...".equals(CrashCleanHelper.this.c.getText())) {
                        new a(CrashCleanHelper.this).execute(new Object[0]);
                    }
                } else if ("重启客户端".equals(CrashCleanHelper.this.c.getText())) {
                    ((AlarmManager) CrashCleanHelper.this.g.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(1, System.currentTimeMillis() + 100, PendingIntent.getActivity(CrashCleanHelper.this.g, 123456, new Intent(CrashCleanHelper.this.g, SplashActivity.class), 268435456));
                    System.exit(0);
                }
            }
        });
        b();
        c();
        return true;
    }

    private void a(Application application) {
        NetworkInitializer.init(application, new b().a((iq) new aaa()).a((ctg) new ctg() {
            public final String a() {
                return aat.a();
            }
        }).a((is) new aac()).a((INetworkProvider) new clg()).a());
    }

    private static void b() {
        File file;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/autonavi/uploadcrash");
        File file2 = new File(sb.toString());
        if (!file2.exists() || !file2.isDirectory() || file2.listFiles() == null || file2.listFiles().length <= 0) {
            file = null;
        } else {
            File[] listFiles = file2.listFiles();
            file = listFiles[0];
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].lastModified() > file.lastModified()) {
                    file = listFiles[i];
                }
            }
        }
        if (file != null) {
            ub.a(file);
        }
    }

    /* access modifiers changed from: 0000 */
    public final long a(File file) {
        long j = 0;
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    j += a(listFiles[i]);
                } else {
                    j += listFiles[i].length();
                    if ("girf_sync.db".equals(listFiles[i].getName())) {
                        File file2 = listFiles[i];
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.g.getFilesDir().getParentFile().getAbsolutePath());
                        sb.append("/backup.db");
                        ahd.a(file2, new File(sb.toString()));
                    }
                    listFiles[i].delete();
                }
            }
            file.delete();
        } else {
            file.delete();
        }
        return j;
    }

    private void c() {
        PathManager.a().a((Context) this.g.getApplication());
        yq.b();
        a(this.g.getApplication());
        CrashCallback crashCallback = new CrashCallback(0);
        AosPostRequest b2 = aax.b(new CrashParams(this, 0));
        yq.a();
        yq.a((AosRequest) b2, (AosResponseCallback<T>) crashCallback);
    }
}
