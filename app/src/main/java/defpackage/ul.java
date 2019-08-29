package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Looper;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.dumpcrash.installerror.InstallErrorActivity;
import com.autonavi.amap.app.AMapAppGlobal;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"DM_EXIT"})
/* renamed from: ul reason: default package */
/* compiled from: AmapOnInstallErrorListener */
public final class ul implements bmv {
    public static un a;
    private int b = 5;

    /* renamed from: ul$a */
    /* compiled from: AmapOnInstallErrorListener */
    public interface a {
        void a();
    }

    public ul() {
        new um().a(null);
    }

    public final boolean a(Throwable th) {
        while (th != null) {
            boolean z = th instanceof ClassNotFoundException;
            if (z || (th instanceof NoClassDefFoundError) || (th instanceof NotFoundException) || (th instanceof UnsatisfiedLinkError)) {
                int i = 5;
                if (th instanceof NotFoundException) {
                    i = 1;
                } else if (z) {
                    i = 2;
                } else if (th instanceof NoClassDefFoundError) {
                    i = 3;
                } else if (th instanceof UnsatisfiedLinkError) {
                    i = 4;
                }
                this.b = i;
                a("needShowErrorReportDialog", th);
                return true;
            }
            th = th.getCause();
        }
        return false;
    }

    private static void a(String str, Throwable th) {
        String str2;
        StackTraceElement[] stackTrace;
        StringBuilder sb = new StringBuilder(str);
        sb.append("\n");
        if (th == null) {
            str2 = "null exception!";
        } else {
            str2 = th.getMessage();
        }
        sb.append(str2);
        if (!(th == null || th.getStackTrace() == null)) {
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                if (stackTraceElement != null) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
        }
        FileUtil.writeLogToFile(sb.toString(), "installError.txt");
    }

    public final void b(Throwable th) {
        a("showErrorReportDialog", th);
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity != null) {
            Intent intent = new Intent(topActivity, InstallErrorActivity.class);
            intent.putExtra("typevalue", this.b);
            intent.setFlags(268468224);
            try {
                topActivity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                System.exit(0);
                return;
            }
            return;
        }
        throw new RuntimeException("getTopActivity == null");
    }
}
