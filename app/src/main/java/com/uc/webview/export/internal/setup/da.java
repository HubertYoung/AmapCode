package com.uc.webview.export.internal.setup;

import android.content.Context;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCCyclone.MessageDigestType;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.i;
import com.uc.webview.export.internal.utility.i.b;
import com.uc.webview.export.internal.utility.j;
import com.uc.webview.export.internal.utility.m;
import java.io.File;
import java.util.Locale;

/* compiled from: ProGuard */
public final class da extends UCSubSetupTask<da, da> {
    private static final int d = -1;
    String[][] a;
    bx b;
    Integer c;

    private static String a(int i) {
        switch (i) {
            case 2:
                return "MD5";
            case 3:
                return "SHA1";
            case 4:
                return "SHA256";
            default:
                return "SHA1(default)";
        }
    }

    private static void a(File file, String str, int i, boolean z, Context context) {
        String str2;
        if (!z || !m.a(file.getAbsolutePath(), context)) {
            long currentTimeMillis = System.currentTimeMillis();
            if (i == 2) {
                str2 = UCCyclone.hashFileContents(file, MessageDigestType.MD5);
            } else if (i == 4) {
                str2 = UCCyclone.hashFileContents(file, MessageDigestType.SHA256);
            } else {
                str2 = UCCyclone.hashFileContents(file, MessageDigestType.SHA1);
            }
            try {
                if (j.a(str) || str.equals(str2)) {
                    if (z) {
                        m.a(file.getAbsolutePath(), context, true);
                    }
                    long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                    StringBuilder sb = new StringBuilder("组件校验(");
                    sb.append(a(i));
                    sb.append(") Pass:true [");
                    sb.append(file.getAbsolutePath());
                    sb.append("] time[");
                    sb.append(currentTimeMillis2);
                    sb.append("ms]");
                    Log.d("VerifyTask", sb.toString());
                    return;
                }
                Object[] objArr = new Object[4];
                objArr[0] = file;
                objArr[1] = i == 2 ? "md5" : "sha";
                objArr[2] = str2;
                objArr[3] = str;
                throw new UCSetupException(1011, String.format("So file [%s] with [%s] [%s] mismatch to predefined [%s].", objArr));
            } catch (Throwable th) {
                if (z) {
                    m.a(file.getAbsolutePath(), context, false);
                }
                StringBuilder sb2 = new StringBuilder("组件校验(");
                sb2.append(a(i));
                sb2.append(") Pass:false [");
                sb2.append(file.getAbsolutePath());
                sb2.append("] time[");
                sb2.append(System.currentTimeMillis() - currentTimeMillis);
                sb2.append("ms]");
                Log.d("VerifyTask", sb2.toString());
                throw th;
            }
        }
    }

    public static UCElapseTime a(Context context, Integer num, String str) {
        UCElapseTime uCElapseTime = new UCElapseTime();
        if ((num.intValue() & UCCore.VERIFY_POLICY_QUICK) == 0 || !m.a(str, context)) {
            if (!i.a(str, context, context, "com.UCMobile", new b("cd_cvsv"), null)) {
                StringBuilder sb = new StringBuilder("组件校验 Dex Failed [");
                sb.append(str);
                sb.append("]");
                Log.d("VerifyTask", sb.toString());
                m.a(str, context, false);
                throw new UCSetupException(3005, String.format("[%s] verify failed", new Object[]{str}));
            }
            StringBuilder sb2 = new StringBuilder("组件校验 Dex Success [");
            sb2.append(str);
            sb2.append("]");
            Log.d("VerifyTask", sb2.toString());
            m.a(str, context, true);
        }
        return uCElapseTime;
    }

    public final void a(Integer num) {
        if (this.a != null && this.a.length > 0 && num != null) {
            Context context = (Context) this.mOptions.get(UCCore.OPTION_CONTEXT);
            String str = this.mUCM.soDirPath;
            if (str == null) {
                str = context.getApplicationInfo().nativeLibraryDir;
            }
            boolean z = (num.intValue() & UCCore.VERIFY_POLICY_QUICK) != 0;
            boolean z2 = (num.intValue() & 268435456) != 0;
            int i = this.a[0].length > 3 ? 3 : 2;
            int i2 = 4;
            if ((num.intValue() & 1048576) != 0) {
                i2 = 2;
            } else if ((num.intValue() & UCCore.VERIFY_POLICY_WITH_SHA256) == 0 || this.a[0].length <= 4) {
                i2 = i;
            }
            String[][] strArr = this.a;
            int length = strArr.length;
            int i3 = 0;
            while (i3 < length) {
                String[] strArr2 = strArr[i3];
                String str2 = strArr2[0];
                String str3 = strArr2[i2];
                File file = new File(str, str2);
                try {
                    a(file, str3, i2, z || z2, context);
                    UCLogger.print(d, String.format(Locale.CHINA, "Check file hash ok [%s].", new Object[]{file}), new Throwable[0]);
                    i3++;
                    strArr = strArr;
                } catch (UCSetupException e) {
                    this.b.a(3, e);
                    return;
                }
            }
            this.b.a(0, null);
        }
    }

    public static void a(Context context, String str, String[][] strArr, Integer num) throws UCSetupException {
        if (strArr != null && strArr.length > 0 && num != null) {
            boolean z = (num.intValue() & UCCore.VERIFY_POLICY_QUICK) != 0;
            boolean z2 = (num.intValue() & 536870912) != 0;
            int i = 3;
            if (strArr[0].length <= 3) {
                i = 2;
            }
            if ((num.intValue() & 1048576) != 0) {
                i = 2;
            } else if ((num.intValue() & UCCore.VERIFY_POLICY_WITH_SHA256) != 0 && strArr[0].length > 4) {
                i = 4;
            }
            for (String[] strArr2 : strArr) {
                String str2 = strArr2[0];
                String str3 = strArr2[i];
                File file = new File(str, str2);
                a(file, str3, i, z || z2, context);
                UCLogger.print(d, String.format(Locale.CHINA, "Check file hash ok [%s].", new Object[]{file}), new Throwable[0]);
            }
        }
    }

    public final void run() {
        a(this.c);
    }
}
