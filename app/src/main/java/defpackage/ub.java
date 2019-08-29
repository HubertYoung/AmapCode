package defpackage;

import android.text.TextUtils;
import com.autonavi.common.tool.CrashLogUtil;
import java.io.File;
import java.net.HttpURLConnection;

/* renamed from: ub reason: default package */
/* compiled from: DumpCrashReporter */
public final class ub {

    /* renamed from: ub$a */
    /* compiled from: DumpCrashReporter */
    static class a extends Thread {
        private File a;
        private String b;

        public a(File file, String str) {
            this.a = file;
            this.b = str;
        }

        public final void run() {
            new bmz();
            bmz.b(this.b, this.a, new bmy() {
                public final void a(HttpURLConnection httpURLConnection) throws Throwable {
                    httpURLConnection.getResponseCode();
                }

                public final void a(Throwable th) {
                    th.printStackTrace();
                }
            });
        }
    }

    public static void a(int i, uc ucVar) {
        ul.a = ucVar.d();
        switch (i) {
            case 0:
                StringBuilder sb = new StringBuilder();
                sb.append(ahk.a(ucVar.a().a()).getAbsolutePath());
                sb.append("/");
                sb.append(ucVar.a().b());
                String sb2 = sb.toString();
                CrashLogUtil.initCrashLog(new uf(ucVar));
                CrashLogUtil.setOnInstallErrorListener(new ul());
                CrashLogUtil.setCrashNotifyFilePath(sb2);
                bmp.a((String) "Ver_AJX", ucVar.c().b());
                return;
            case 1:
                StringBuilder sb3 = new StringBuilder();
                sb3.append(ahk.a(ucVar.a().a()).getAbsolutePath());
                sb3.append("/");
                sb3.append(ucVar.a().b());
                String sb4 = sb3.toString();
                CrashLogUtil.initCrashLog(new ud(ucVar));
                CrashLogUtil.setCrashNotifyFilePath(sb4);
                break;
        }
    }

    public static void a(File file) {
        String a2 = ui.a();
        if (!TextUtils.isEmpty(a2)) {
            ahm.a(new a(file, a2));
        }
    }
}
