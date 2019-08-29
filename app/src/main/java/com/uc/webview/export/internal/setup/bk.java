package com.uc.webview.export.internal.setup;

import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.cyclone.UCCyclone.DecFileOrign;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.g;
import com.uc.webview.export.internal.utility.j;
import com.uc.webview.export.utility.SetupTask;
import java.io.File;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class bk extends UCSubSetupTask<bk, bk> {
    /* access modifiers changed from: private */
    public static final String a = "bk";
    private static Object b = new Object();

    /* compiled from: ProGuard */
    static class a {
        private static long A = 0;
        private static long B = 0;
        private static long C = 0;
        private static long D = 0;
        private static long E = 0;
        private static long F = 0;
        private static long G = 0;
        private static long H = 0;
        private static long I = 0;
        private static long J = 0;
        private static long a = 1;
        private static long b = 2;
        private static long c = 4;
        private static long d = 8;
        private static long e = 16;
        private static long f = 32;
        private static long g = 64;
        private static long h = 128;
        private static long i = 256;
        private static long j = 512;
        private static long k = 1024;
        private static long l = 2048;
        private static long m = 4096;
        private static long n = 8192;
        private static long o = 16384;
        private static long p = 32768;
        private static long q;
        private static long r;
        private static long s;
        private static long t;
        private static long u;
        private static long v;
        private static long w;
        private static long x;
        private static long y;
        private static long z;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:149:?, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_TIMECOST, java.lang.Long.toString(r9));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:150:0x0637, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_PROCESS, java.lang.Long.toString(r7));
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_EXCEPIION, java.lang.Long.toString(r3));
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_AUTHORITY, java.lang.Long.toString(r1));
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_DELETE, java.lang.Long.toString(r5));
            r10 = r21;
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_PRECONDITION, java.lang.Long.toString(r10));
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_COPY, java.lang.Long.toString(r14));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:151:0x0673, code lost:
            if (r14 != B) goto L_0x067b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:152:0x0675, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_SUCCESS_PV);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:154:0x067f, code lost:
            if (r14 != G) goto L_0x0687;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:155:0x0681, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_HAS_EXISTS_PV);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:156:0x0687, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_OTHER_PV);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x068c, code lost:
            com.uc.webview.export.internal.utility.Log.d(com.uc.webview.export.internal.setup.bk.a(), ".shareCoreDecFile fProcessStat: ".concat(java.lang.String.valueOf(r7)));
            com.uc.webview.export.internal.utility.Log.d(com.uc.webview.export.internal.setup.bk.a(), ".shareCoreDecFile fProcessStatExp: ".concat(java.lang.String.valueOf(r3)));
            com.uc.webview.export.internal.utility.Log.d(com.uc.webview.export.internal.setup.bk.a(), ".shareCoreDecFile fSdcardAuthoryStat: ".concat(java.lang.String.valueOf(r1)));
            com.uc.webview.export.internal.utility.Log.d(com.uc.webview.export.internal.setup.bk.a(), ".shareCoreDecFile fDeleteStat: ".concat(java.lang.String.valueOf(r5)));
            com.uc.webview.export.internal.utility.Log.d(com.uc.webview.export.internal.setup.bk.a(), ".shareCoreDecFile fPreconditionStat: ".concat(java.lang.String.valueOf(r10)));
            r1 = com.uc.webview.export.internal.setup.bk.a();
            r2 = ".shareCoreDecFile fCopyStat: ";
            r3 = java.lang.String.valueOf(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:160:0x06f3, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:0x06f4, code lost:
            r27 = r1;
            r1 = r0;
            r11 = r14;
            r9 = r5;
            r5 = r7;
            r14 = r21;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:162:0x0702, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:163:0x0703, code lost:
            r25 = r3;
            r27 = r1;
            r1 = r0;
            r11 = r14;
            r9 = r5;
            r13 = r7;
            r7 = r21;
            r5 = r27;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:211:0x0849, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:231:0x091c, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:232:0x091d, code lost:
            r25 = r3;
            r13 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:257:0x0986, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_SUCCESS_PV);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:259:0x0990, code lost:
            if (r11 == G) goto L_0x0992;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:260:0x0992, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_HAS_EXISTS_PV);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:261:0x0998, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_OTHER_PV);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:271:0x0a48, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_SUCCESS_PV);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:273:0x0a52, code lost:
            if (r11 == G) goto L_0x0a54;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:274:0x0a54, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_HAS_EXISTS_PV);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:275:0x0a5a, code lost:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat((java.lang.String) com.uc.webview.export.internal.interfaces.IWaStat.SHARE_CORE_COPY_OTHER_PV);
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:211:0x0849 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:34:0x01c4] */
        /* JADX WARNING: Removed duplicated region for block: B:257:0x0986  */
        /* JADX WARNING: Removed duplicated region for block: B:258:0x098c  */
        /* JADX WARNING: Removed duplicated region for block: B:271:0x0a48  */
        /* JADX WARNING: Removed duplicated region for block: B:272:0x0a4e  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void a(java.lang.String r31, int r32) {
            /*
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile "
                java.lang.String r3 = java.lang.String.valueOf(r31)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                long r1 = a
                long r3 = a
                long r5 = a
                long r7 = a
                long r9 = a
                long r11 = a
                java.lang.String r13 = "csc_ctcp"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13)     // Catch:{ Throwable -> 0x093a, all -> 0x0933 }
                java.lang.String r13 = "0"
                java.lang.String r14 = "process_private_data_dir_suffix"
                java.lang.Object r14 = com.uc.webview.export.extension.UCCore.getGlobalOption(r14)     // Catch:{ Throwable -> 0x093a, all -> 0x0933 }
                java.lang.String r14 = (java.lang.String) r14     // Catch:{ Throwable -> 0x093a, all -> 0x0933 }
                boolean r13 = r13.equals(r14)     // Catch:{ Throwable -> 0x093a, all -> 0x0933 }
                if (r13 != 0) goto L_0x00ee
                long r13 = e     // Catch:{ Throwable -> 0x093a, all -> 0x0933 }
                java.lang.String r1 = "csc_cdrp"
                java.lang.String r2 = java.lang.Long.toString(r13)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                java.lang.String r1 = "csc_cdre"
                java.lang.String r2 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                java.lang.String r1 = "csc_cdra"
                java.lang.String r2 = java.lang.Long.toString(r5)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                java.lang.String r1 = "csc_cdrd"
                java.lang.String r2 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                java.lang.String r1 = "csc_cdri"
                java.lang.String r2 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                java.lang.String r1 = "csc_cdrc"
                java.lang.String r2 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                long r1 = B
                int r1 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
                if (r1 != 0) goto L_0x0076
                java.lang.String r1 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)
                goto L_0x0087
            L_0x0076:
                long r1 = G
                int r1 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
                if (r1 != 0) goto L_0x0082
                java.lang.String r1 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)
                goto L_0x0087
            L_0x0082:
                java.lang.String r1 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)
            L_0x0087:
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r13 = java.lang.String.valueOf(r13)
                java.lang.String r2 = r2.concat(r13)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r3 = java.lang.String.valueOf(r5)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r9)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r7)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x00ee:
                java.lang.String r13 = "csc_cmcp"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13)     // Catch:{ Throwable -> 0x093a, all -> 0x0933 }
                long r13 = g     // Catch:{ Throwable -> 0x093a, all -> 0x0933 }
                long r1 = com.uc.webview.export.internal.utility.g.a()     // Catch:{ Throwable -> 0x092e, all -> 0x092a }
                long r5 = com.uc.webview.export.internal.utility.g.a     // Catch:{ Throwable -> 0x0927, all -> 0x0922 }
                int r5 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
                if (r5 == 0) goto L_0x01c2
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x0927, all -> 0x0922 }
                java.lang.String r6 = ".run Sdcard权限检查失败"
                com.uc.webview.export.internal.utility.Log.d(r5, r6)     // Catch:{ Throwable -> 0x0927, all -> 0x0922 }
                java.lang.String r5 = "csc_cdrp"
                java.lang.String r6 = java.lang.Long.toString(r13)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdre"
                java.lang.String r6 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdra"
                java.lang.String r6 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdrd"
                java.lang.String r6 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdri"
                java.lang.String r6 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdrc"
                java.lang.String r6 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                long r5 = B
                int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x014a
                java.lang.String r5 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
                goto L_0x015b
            L_0x014a:
                long r5 = G
                int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x0156
                java.lang.String r5 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
                goto L_0x015b
            L_0x0156:
                java.lang.String r5 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
            L_0x015b:
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r13 = java.lang.String.valueOf(r13)
                java.lang.String r6 = r6.concat(r13)
                com.uc.webview.export.internal.utility.Log.d(r5, r6)
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r6.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r5, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r9)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r7)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x01c2:
                long r5 = h     // Catch:{ Throwable -> 0x0927, all -> 0x0922 }
                java.io.File r13 = new java.io.File     // Catch:{ Throwable -> 0x084c, all -> 0x0849 }
                java.lang.String r14 = com.uc.webview.export.internal.utility.g.c()     // Catch:{ Throwable -> 0x084c, all -> 0x0849 }
                r13.<init>(r14)     // Catch:{ Throwable -> 0x084c, all -> 0x0849 }
                android.content.Context r14 = com.uc.webview.export.internal.SDKFactory.e     // Catch:{ Throwable -> 0x084c, all -> 0x0849 }
                java.lang.String r14 = r14.getPackageName()     // Catch:{ Throwable -> 0x084c, all -> 0x0849 }
                java.io.File r13 = com.uc.webview.export.internal.utility.g.a(r13, r14)     // Catch:{ Throwable -> 0x084c, all -> 0x0849 }
                boolean r14 = r13.exists()     // Catch:{ Throwable -> 0x084c, all -> 0x0849 }
                if (r14 != 0) goto L_0x0299
                long r13 = d     // Catch:{ Throwable -> 0x084c, all -> 0x0849 }
                java.lang.String r5 = "csc_cdrp"
                java.lang.String r6 = java.lang.Long.toString(r13)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdre"
                java.lang.String r6 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdra"
                java.lang.String r6 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdrd"
                java.lang.String r6 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdri"
                java.lang.String r6 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdrc"
                java.lang.String r6 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                long r5 = B
                int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x0221
                java.lang.String r5 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
                goto L_0x0232
            L_0x0221:
                long r5 = G
                int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x022d
                java.lang.String r5 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
                goto L_0x0232
            L_0x022d:
                java.lang.String r5 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
            L_0x0232:
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r13 = java.lang.String.valueOf(r13)
                java.lang.String r6 = r6.concat(r13)
                com.uc.webview.export.internal.utility.Log.d(r5, r6)
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r6.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r5, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r9)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r7)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x0299:
                long r14 = i     // Catch:{ Throwable -> 0x091c, all -> 0x0849 }
                java.lang.String r5 = "sc_cpy"
                java.lang.String r5 = com.uc.webview.export.extension.UCCore.getParam(r5)     // Catch:{ Throwable -> 0x083f, all -> 0x0837 }
                boolean r5 = java.lang.Boolean.parseBoolean(r5)     // Catch:{ Throwable -> 0x083f, all -> 0x0837 }
                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ Throwable -> 0x083f, all -> 0x0837 }
                if (r5 != 0) goto L_0x02ad
                r5 = 0
                goto L_0x02b1
            L_0x02ad:
                boolean r5 = r5.booleanValue()     // Catch:{ Throwable -> 0x083f, all -> 0x0837 }
            L_0x02b1:
                java.lang.String r6 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x083f, all -> 0x0837 }
                r17 = r14
                java.lang.String r14 = "配置允许内核共享:"
                java.lang.String r15 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x0835, all -> 0x0833 }
                java.lang.String r14 = r14.concat(r15)     // Catch:{ Throwable -> 0x0835, all -> 0x0833 }
                com.uc.webview.export.internal.utility.Log.d(r6, r14)     // Catch:{ Throwable -> 0x0835, all -> 0x0833 }
                if (r5 != 0) goto L_0x0388
                long r5 = c     // Catch:{ Throwable -> 0x0835, all -> 0x0833 }
                r14 = 0
                r15 = 0
                com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r13, r15, r14)     // Catch:{ Throwable -> 0x091c, all -> 0x0849 }
                java.lang.String r13 = "csc_cdrp"
                java.lang.String r14 = java.lang.Long.toString(r5)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13, r14)
                java.lang.String r13 = "csc_cdre"
                java.lang.String r14 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13, r14)
                java.lang.String r13 = "csc_cdra"
                java.lang.String r14 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13, r14)
                java.lang.String r13 = "csc_cdrd"
                java.lang.String r14 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13, r14)
                java.lang.String r13 = "csc_cdri"
                java.lang.String r14 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13, r14)
                java.lang.String r13 = "csc_cdrc"
                java.lang.String r14 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13, r14)
                long r13 = B
                int r13 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
                if (r13 != 0) goto L_0x0310
                java.lang.String r13 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13)
                goto L_0x0321
            L_0x0310:
                long r13 = G
                int r13 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
                if (r13 != 0) goto L_0x031c
                java.lang.String r13 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13)
                goto L_0x0321
            L_0x031c:
                java.lang.String r13 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r13)
            L_0x0321:
                java.lang.String r13 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r14 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r5 = java.lang.String.valueOf(r5)
                java.lang.String r5 = r14.concat(r5)
                com.uc.webview.export.internal.utility.Log.d(r13, r5)
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r6.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r5, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r9)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r7)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x0388:
                long r5 = o     // Catch:{ Throwable -> 0x0835, all -> 0x0833 }
                java.lang.String r14 = "sc_hucmv"
                java.lang.String r14 = com.uc.webview.export.extension.UCCore.getParam(r14)     // Catch:{ Throwable -> 0x091c, all -> 0x0849 }
                boolean r14 = com.uc.webview.export.internal.utility.j.a(r14)     // Catch:{ Throwable -> 0x091c, all -> 0x0849 }
                if (r14 == 0) goto L_0x0452
                long r13 = p     // Catch:{ Throwable -> 0x091c, all -> 0x0849 }
                java.lang.String r5 = "csc_cdrp"
                java.lang.String r6 = java.lang.Long.toString(r13)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdre"
                java.lang.String r6 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdra"
                java.lang.String r6 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdrd"
                java.lang.String r6 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdri"
                java.lang.String r6 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdrc"
                java.lang.String r6 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                long r5 = B
                int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x03da
                java.lang.String r5 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
                goto L_0x03eb
            L_0x03da:
                long r5 = G
                int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x03e6
                java.lang.String r5 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
                goto L_0x03eb
            L_0x03e6:
                java.lang.String r5 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
            L_0x03eb:
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r13 = java.lang.String.valueOf(r13)
                java.lang.String r6 = r6.concat(r13)
                com.uc.webview.export.internal.utility.Log.d(r5, r6)
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r6.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r5, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r9)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r7)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x0452:
                long r14 = j     // Catch:{ Throwable -> 0x091c, all -> 0x0849 }
                long r5 = a(r13)     // Catch:{ Throwable -> 0x0916, all -> 0x0913 }
                long r9 = k     // Catch:{ Throwable -> 0x082c, all -> 0x0828 }
                long r14 = a(r32)     // Catch:{ Throwable -> 0x0821, all -> 0x081c }
                long r7 = q     // Catch:{ Throwable -> 0x0812, all -> 0x0802 }
                int r7 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
                if (r7 == 0) goto L_0x0536
                java.lang.String r7 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x052f, all -> 0x0527 }
                java.lang.String r8 = ".run 先决条件判断失败！"
                com.uc.webview.export.internal.utility.Log.d(r7, r8)     // Catch:{ Throwable -> 0x052f, all -> 0x0527 }
                java.lang.String r7 = "csc_cdrp"
                java.lang.String r8 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdre"
                java.lang.String r8 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdra"
                java.lang.String r8 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdrd"
                java.lang.String r8 = java.lang.Long.toString(r5)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdri"
                java.lang.String r8 = java.lang.Long.toString(r14)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdrc"
                java.lang.String r8 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                long r7 = B
                int r7 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
                if (r7 != 0) goto L_0x04af
                java.lang.String r7 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7)
                goto L_0x04c0
            L_0x04af:
                long r7 = G
                int r7 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
                if (r7 != 0) goto L_0x04bb
                java.lang.String r7 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7)
                goto L_0x04c0
            L_0x04bb:
                java.lang.String r7 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7)
            L_0x04c0:
                java.lang.String r7 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r8 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r9 = java.lang.String.valueOf(r9)
                java.lang.String r8 = r8.concat(r9)
                com.uc.webview.export.internal.utility.Log.d(r7, r8)
                java.lang.String r7 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r8 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r8.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r7, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r5)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r14)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x0527:
                r0 = move-exception
                r7 = r1
                r1 = r0
                r27 = r5
                r5 = r9
                goto L_0x080e
            L_0x052f:
                r0 = move-exception
                r25 = r3
                r7 = r14
                r13 = r9
                goto L_0x0825
            L_0x0536:
                long r7 = l     // Catch:{ Throwable -> 0x0812, all -> 0x0802 }
                boolean r9 = com.uc.webview.export.internal.utility.j.a(r31)     // Catch:{ Throwable -> 0x07fc, all -> 0x07f6 }
                if (r9 == 0) goto L_0x060a
                long r9 = n     // Catch:{ Throwable -> 0x05fd, all -> 0x05fa }
                java.lang.String r7 = "csc_cdrp"
                java.lang.String r8 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdre"
                java.lang.String r8 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdra"
                java.lang.String r8 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdrd"
                java.lang.String r8 = java.lang.Long.toString(r5)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdri"
                java.lang.String r8 = java.lang.Long.toString(r14)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                java.lang.String r7 = "csc_cdrc"
                java.lang.String r8 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7, r8)
                long r7 = B
                int r7 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
                if (r7 != 0) goto L_0x0582
                java.lang.String r7 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7)
                goto L_0x0593
            L_0x0582:
                long r7 = G
                int r7 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
                if (r7 != 0) goto L_0x058e
                java.lang.String r7 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7)
                goto L_0x0593
            L_0x058e:
                java.lang.String r7 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r7)
            L_0x0593:
                java.lang.String r7 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r8 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r9 = java.lang.String.valueOf(r9)
                java.lang.String r8 = r8.concat(r9)
                com.uc.webview.export.internal.utility.Log.d(r7, r8)
                java.lang.String r7 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r8 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r8.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r7, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r5)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r14)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x05fa:
                r0 = move-exception
                goto L_0x07f8
            L_0x05fd:
                r0 = move-exception
                r25 = r3
                r9 = r5
                r5 = r1
                r1 = r0
                r27 = r7
                r7 = r14
                r13 = r27
                goto L_0x093f
            L_0x060a:
                r9 = 0
            L_0x060c:
                r16 = 1
                long r9 = r9 + r16
                r19 = r11
                r21 = r14
                r11 = r31
                long r14 = a(r13, r11)     // Catch:{ Throwable -> 0x07ec, all -> 0x07e5 }
                long r16 = B     // Catch:{ Throwable -> 0x07de, all -> 0x07d8 }
                int r12 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r12 == 0) goto L_0x071c
                long r16 = G     // Catch:{ Throwable -> 0x07de, all -> 0x07d8 }
                int r12 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r12 != 0) goto L_0x0628
                goto L_0x071c
            L_0x0628:
                r16 = 3
                int r12 = (r9 > r16 ? 1 : (r9 == r16 ? 0 : -1))
                if (r12 <= 0) goto L_0x0715
                java.lang.String r11 = "csc_cdrt"
                java.lang.String r9 = java.lang.Long.toString(r9)     // Catch:{ Throwable -> 0x0702, all -> 0x06f3 }
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11, r9)     // Catch:{ Throwable -> 0x0702, all -> 0x06f3 }
                java.lang.String r9 = "csc_cdrp"
                java.lang.String r10 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9, r10)
                java.lang.String r9 = "csc_cdre"
                java.lang.String r10 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9, r10)
                java.lang.String r9 = "csc_cdra"
                java.lang.String r10 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9, r10)
                java.lang.String r9 = "csc_cdrd"
                java.lang.String r10 = java.lang.Long.toString(r5)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9, r10)
                java.lang.String r9 = "csc_cdri"
                r10 = r21
                java.lang.String r12 = java.lang.Long.toString(r10)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9, r12)
                java.lang.String r9 = "csc_cdrc"
                java.lang.String r12 = java.lang.Long.toString(r14)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9, r12)
                long r12 = B
                int r9 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
                if (r9 != 0) goto L_0x067b
                java.lang.String r9 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9)
                goto L_0x068c
            L_0x067b:
                long r12 = G
                int r9 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
                if (r9 != 0) goto L_0x0687
                java.lang.String r9 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9)
                goto L_0x068c
            L_0x0687:
                java.lang.String r9 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9)
            L_0x068c:
                java.lang.String r9 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r12 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r7 = java.lang.String.valueOf(r7)
                java.lang.String r7 = r12.concat(r7)
                com.uc.webview.export.internal.utility.Log.d(r9, r7)
                java.lang.String r7 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r8 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r8.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r7, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r5)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r10)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r14)
            L_0x06eb:
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x06f3:
                r0 = move-exception
                r10 = r21
                r27 = r1
                r1 = r0
                r29 = r10
                r11 = r14
                r9 = r5
                r5 = r7
                r14 = r29
                goto L_0x0a0a
            L_0x0702:
                r0 = move-exception
                r10 = r21
                r25 = r3
                r27 = r1
                r1 = r0
                r29 = r10
                r11 = r14
                r9 = r5
                r13 = r7
                r7 = r29
                r5 = r27
                goto L_0x093f
            L_0x0715:
                r23 = r9
                r11 = r14
                r14 = r21
                goto L_0x060c
            L_0x071c:
                r9 = r21
                java.lang.String r11 = "csc_cdrp"
                java.lang.String r12 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11, r12)
                java.lang.String r11 = "csc_cdre"
                java.lang.String r12 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11, r12)
                java.lang.String r11 = "csc_cdra"
                java.lang.String r12 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11, r12)
                java.lang.String r11 = "csc_cdrd"
                java.lang.String r12 = java.lang.Long.toString(r5)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11, r12)
                java.lang.String r11 = "csc_cdri"
                java.lang.String r12 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11, r12)
                java.lang.String r11 = "csc_cdrc"
                java.lang.String r12 = java.lang.Long.toString(r14)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11, r12)
                long r11 = B
                int r11 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
                if (r11 != 0) goto L_0x0760
                java.lang.String r11 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11)
                goto L_0x0771
            L_0x0760:
                long r11 = G
                int r11 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
                if (r11 != 0) goto L_0x076c
                java.lang.String r11 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11)
                goto L_0x0771
            L_0x076c:
                java.lang.String r11 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11)
            L_0x0771:
                java.lang.String r11 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r12 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r7 = java.lang.String.valueOf(r7)
                java.lang.String r7 = r12.concat(r7)
                com.uc.webview.export.internal.utility.Log.d(r11, r7)
                java.lang.String r7 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r8 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r8.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r7, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r5)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r9)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r14)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x07d8:
                r0 = move-exception
                r9 = r21
                r11 = r14
                r14 = r9
                goto L_0x07f8
            L_0x07de:
                r0 = move-exception
                r9 = r21
                r25 = r3
                r11 = r14
                goto L_0x0800
            L_0x07e5:
                r0 = move-exception
                r9 = r21
                r14 = r9
                r11 = r19
                goto L_0x07f8
            L_0x07ec:
                r0 = move-exception
                r9 = r21
                r25 = r3
                r13 = r7
                r7 = r9
                r11 = r19
                goto L_0x0825
            L_0x07f6:
                r0 = move-exception
                r9 = r14
            L_0x07f8:
                r9 = r5
                r5 = r7
                goto L_0x0925
            L_0x07fc:
                r0 = move-exception
                r9 = r14
                r25 = r3
            L_0x0800:
                r13 = r7
                goto L_0x081a
            L_0x0802:
                r0 = move-exception
                r27 = r9
                r9 = r14
                r13 = r27
                r7 = r1
                r1 = r0
                r27 = r5
                r5 = r13
                r14 = r9
            L_0x080e:
                r9 = r27
                goto L_0x0a0c
            L_0x0812:
                r0 = move-exception
                r27 = r9
                r9 = r14
                r13 = r27
                r25 = r3
            L_0x081a:
                r7 = r9
                goto L_0x0825
            L_0x081c:
                r0 = move-exception
                r13 = r9
                r9 = r5
                goto L_0x0923
            L_0x0821:
                r0 = move-exception
                r13 = r9
                r25 = r3
            L_0x0825:
                r9 = r5
                goto L_0x0920
            L_0x0828:
                r0 = move-exception
                r9 = r5
                goto L_0x0914
            L_0x082c:
                r0 = move-exception
                r25 = r3
                r9 = r5
                r13 = r14
                goto L_0x0920
            L_0x0833:
                r0 = move-exception
                goto L_0x083a
            L_0x0835:
                r0 = move-exception
                goto L_0x0842
            L_0x0837:
                r0 = move-exception
                r17 = r14
            L_0x083a:
                r14 = r7
                r5 = r17
                goto L_0x0925
            L_0x083f:
                r0 = move-exception
                r17 = r14
            L_0x0842:
                r5 = r1
                r25 = r3
                r13 = r17
                goto L_0x093e
            L_0x0849:
                r0 = move-exception
                goto L_0x0924
            L_0x084c:
                r0 = move-exception
                r13 = r0
                long r14 = m     // Catch:{ Throwable -> 0x091c, all -> 0x0849 }
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x0916, all -> 0x0913 }
                java.lang.String r6 = ".shareCoreDecFile createAppSubFolder"
                com.uc.webview.export.internal.utility.Log.d(r5, r6, r13)     // Catch:{ Throwable -> 0x0916, all -> 0x0913 }
                java.lang.String r5 = "csc_cdrp"
                java.lang.String r6 = java.lang.Long.toString(r14)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdre"
                java.lang.String r6 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdra"
                java.lang.String r6 = java.lang.Long.toString(r1)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdrd"
                java.lang.String r6 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdri"
                java.lang.String r6 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                java.lang.String r5 = "csc_cdrc"
                java.lang.String r6 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r6)
                long r5 = B
                int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x089b
                java.lang.String r5 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
                goto L_0x08ac
            L_0x089b:
                long r5 = G
                int r5 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r5 != 0) goto L_0x08a7
                java.lang.String r5 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
                goto L_0x08ac
            L_0x08a7:
                java.lang.String r5 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5)
            L_0x08ac:
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r13 = java.lang.String.valueOf(r14)
                java.lang.String r6 = r6.concat(r13)
                com.uc.webview.export.internal.utility.Log.d(r5, r6)
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r6 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r6.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r5, r3)
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r1 = r4.concat(r1)
                com.uc.webview.export.internal.utility.Log.d(r3, r1)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r9)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r7)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                return
            L_0x0913:
                r0 = move-exception
            L_0x0914:
                r5 = r14
                goto L_0x0924
            L_0x0916:
                r0 = move-exception
                r5 = r1
                r25 = r3
                r13 = r14
                goto L_0x093e
            L_0x091c:
                r0 = move-exception
                r25 = r3
                r13 = r5
            L_0x0920:
                r5 = r1
                goto L_0x093e
            L_0x0922:
                r0 = move-exception
            L_0x0923:
                r5 = r13
            L_0x0924:
                r14 = r7
            L_0x0925:
                r7 = r1
                goto L_0x0937
            L_0x0927:
                r0 = move-exception
                r5 = r1
                goto L_0x093c
            L_0x092a:
                r0 = move-exception
                r1 = r0
                goto L_0x0a06
            L_0x092e:
                r0 = move-exception
                r1 = r0
                r25 = r3
                goto L_0x093f
            L_0x0933:
                r0 = move-exception
                r14 = r7
                r7 = r5
                r5 = r1
            L_0x0937:
                r1 = r0
                goto L_0x0a0c
            L_0x093a:
                r0 = move-exception
                r13 = r1
            L_0x093c:
                r25 = r3
            L_0x093e:
                r1 = r0
            L_0x093f:
                long r2 = f     // Catch:{ all -> 0x0a02 }
                java.lang.String r4 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ all -> 0x09fe }
                java.lang.String r15 = ".shareCoreDecFile"
                com.uc.webview.export.internal.utility.Log.d(r4, r15, r1)     // Catch:{ all -> 0x09fe }
                java.lang.String r1 = "csc_cdrp"
                java.lang.String r4 = java.lang.Long.toString(r13)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r4)
                java.lang.String r1 = "csc_cdre"
                java.lang.String r4 = java.lang.Long.toString(r2)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r4)
                java.lang.String r1 = "csc_cdra"
                java.lang.String r4 = java.lang.Long.toString(r5)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r4)
                java.lang.String r1 = "csc_cdrd"
                java.lang.String r4 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r4)
                java.lang.String r1 = "csc_cdri"
                java.lang.String r4 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r4)
                java.lang.String r1 = "csc_cdrc"
                java.lang.String r4 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r4)
                long r15 = B
                int r1 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
                if (r1 != 0) goto L_0x098c
                java.lang.String r1 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)
                goto L_0x099d
            L_0x098c:
                long r15 = G
                int r1 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
                if (r1 != 0) goto L_0x0998
                java.lang.String r1 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)
                goto L_0x099d
            L_0x0998:
                java.lang.String r1 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)
            L_0x099d:
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r13 = java.lang.String.valueOf(r13)
                java.lang.String r4 = r4.concat(r13)
                com.uc.webview.export.internal.utility.Log.d(r1, r4)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r4 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r2 = java.lang.String.valueOf(r2)
                java.lang.String r2 = r4.concat(r2)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r3 = java.lang.String.valueOf(r5)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r3 = java.lang.String.valueOf(r9)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r3 = java.lang.String.valueOf(r7)
                java.lang.String r2 = r2.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r1, r2)
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r2 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r3 = java.lang.String.valueOf(r11)
                goto L_0x06eb
            L_0x09fe:
                r0 = move-exception
                r1 = r0
                r3 = r2
                goto L_0x0a06
            L_0x0a02:
                r0 = move-exception
                r1 = r0
                r3 = r25
            L_0x0a06:
                r27 = r5
                r5 = r13
                r14 = r7
            L_0x0a0a:
                r7 = r27
            L_0x0a0c:
                java.lang.String r2 = "csc_cdrp"
                java.lang.String r13 = java.lang.Long.toString(r5)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2, r13)
                java.lang.String r2 = "csc_cdre"
                java.lang.String r13 = java.lang.Long.toString(r3)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2, r13)
                java.lang.String r2 = "csc_cdra"
                java.lang.String r13 = java.lang.Long.toString(r7)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2, r13)
                java.lang.String r2 = "csc_cdrd"
                java.lang.String r13 = java.lang.Long.toString(r9)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2, r13)
                java.lang.String r2 = "csc_cdri"
                java.lang.String r13 = java.lang.Long.toString(r14)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2, r13)
                java.lang.String r2 = "csc_cdrc"
                java.lang.String r13 = java.lang.Long.toString(r11)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2, r13)
                long r16 = B
                int r2 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
                if (r2 != 0) goto L_0x0a4e
                java.lang.String r2 = "csc_cspv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)
                goto L_0x0a5f
            L_0x0a4e:
                long r16 = G
                int r2 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
                if (r2 != 0) goto L_0x0a5a
                java.lang.String r2 = "csc_chev"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)
                goto L_0x0a5f
            L_0x0a5a:
                java.lang.String r2 = "csc_corv"
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)
            L_0x0a5f:
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r13 = ".shareCoreDecFile fProcessStat: "
                java.lang.String r5 = java.lang.String.valueOf(r5)
                java.lang.String r5 = r13.concat(r5)
                com.uc.webview.export.internal.utility.Log.d(r2, r5)
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r5 = ".shareCoreDecFile fProcessStatExp: "
                java.lang.String r3 = java.lang.String.valueOf(r3)
                java.lang.String r3 = r5.concat(r3)
                com.uc.webview.export.internal.utility.Log.d(r2, r3)
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r3 = ".shareCoreDecFile fSdcardAuthoryStat: "
                java.lang.String r4 = java.lang.String.valueOf(r7)
                java.lang.String r3 = r3.concat(r4)
                com.uc.webview.export.internal.utility.Log.d(r2, r3)
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r3 = ".shareCoreDecFile fDeleteStat: "
                java.lang.String r4 = java.lang.String.valueOf(r9)
                java.lang.String r3 = r3.concat(r4)
                com.uc.webview.export.internal.utility.Log.d(r2, r3)
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r3 = ".shareCoreDecFile fPreconditionStat: "
                java.lang.String r4 = java.lang.String.valueOf(r14)
                java.lang.String r3 = r3.concat(r4)
                com.uc.webview.export.internal.utility.Log.d(r2, r3)
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a
                java.lang.String r3 = ".shareCoreDecFile fCopyStat: "
                java.lang.String r4 = java.lang.String.valueOf(r11)
                java.lang.String r3 = r3.concat(r4)
                com.uc.webview.export.internal.utility.Log.d(r2, r3)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.bk.a.a(java.lang.String, int):void");
        }

        static {
            long j2 = a << 1;
            q = j2;
            long j3 = j2 << 1;
            r = j3;
            long j4 = j3 << 1;
            s = j4;
            long j5 = j4 << 1;
            t = j5;
            u = j5 << 1;
            long j6 = a << 1;
            v = j6;
            long j7 = j6 << 1;
            w = j7;
            long j8 = j7 << 1;
            x = j8;
            long j9 = j8 << 1;
            y = j9;
            z = j9 << 1;
            long j10 = a << 1;
            A = j10;
            long j11 = j10 << 1;
            B = j11;
            long j12 = j11 << 1;
            C = j12;
            long j13 = j12 << 1;
            D = j13;
            long j14 = j13 << 1;
            E = j14;
            long j15 = j14 << 1;
            F = j15;
            long j16 = j15 << 1;
            G = j16;
            long j17 = j16 << 1;
            H = j17;
            long j18 = j17 << 1;
            I = j18;
            J = j18 << 1;
        }

        private static long a(int i2) {
            long j2;
            long j3 = q;
            try {
                if (DecFileOrign.Update == i2) {
                    return j3;
                }
                if (((Boolean) UCMPackageInfo.invoke(10011, new Object[0])).booleanValue()) {
                    j2 = r;
                } else {
                    UCMRunningInfo totalLoadedUCM = SetupTask.getTotalLoadedUCM();
                    if (totalLoadedUCM == null) {
                        j2 = s;
                    } else if (!totalLoadedUCM.isShareCore) {
                        return j3;
                    } else {
                        j2 = u;
                    }
                }
                return j2;
            } catch (Throwable th) {
                Log.d(bk.a, ".checkPrecondition", th);
                return j3;
            }
        }

        private static boolean a(File file, com.uc.webview.export.internal.utility.g.a aVar) {
            if (aVar != null) {
                WaStat.stat(IWaStat.SHARE_CORE_SDK_VERSION_CONFIG, UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_HOST_PUSH_UCM_VERSIONS));
            }
            return !j.a(g.a(file, UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_HOST_PUSH_UCM_VERSIONS), aVar));
        }

        /* JADX WARNING: Unknown top exception splitter block from list: {B:31:0x0091=Splitter:B:31:0x0091, B:18:0x0062=Splitter:B:18:0x0062} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static long a(java.io.File r8) {
            /*
                java.lang.String r0 = com.uc.webview.export.internal.setup.bk.a
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = ".deleteHistoryCoreFiles hostSubFolder:"
                r1.<init>(r2)
                java.lang.String r2 = r8.getAbsolutePath()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                com.uc.webview.export.internal.utility.Log.d(r0, r1)
                long r0 = v
                java.io.File[] r8 = r8.listFiles()     // Catch:{ Throwable -> 0x0094 }
                if (r8 == 0) goto L_0x0091
                int r2 = r8.length     // Catch:{ Throwable -> 0x0094 }
                if (r2 != 0) goto L_0x0026
                goto L_0x0091
            L_0x0026:
                int r2 = r8.length     // Catch:{ Throwable -> 0x0094 }
                r3 = 0
            L_0x0028:
                if (r3 >= r2) goto L_0x0090
                r4 = r8[r3]     // Catch:{ Throwable -> 0x0094 }
                java.lang.String r5 = r4.getName()     // Catch:{ Throwable -> 0x0094 }
                boolean r5 = com.uc.webview.export.cyclone.UCCyclone.detect7zFromFileName(r5)     // Catch:{ Throwable -> 0x0094 }
                r6 = 0
                if (r5 != 0) goto L_0x0062
                boolean r5 = com.uc.webview.export.internal.utility.g.a(r4)     // Catch:{ Throwable -> 0x0094 }
                if (r5 != 0) goto L_0x0062
                boolean r5 = com.uc.webview.export.internal.utility.g.a(r4, r6)     // Catch:{ Throwable -> 0x0094 }
                if (r5 != 0) goto L_0x0062
                r4.delete()     // Catch:{ Throwable -> 0x0094 }
                long r5 = x     // Catch:{ Throwable -> 0x0094 }
                java.lang.String r0 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                java.lang.String r7 = ".deleteHistoryCoreFiles verifySignature failure! file: "
                r1.<init>(r7)     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                r1.append(r4)     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                com.uc.webview.export.internal.utility.Log.d(r0, r1)     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                goto L_0x0086
            L_0x0062:
                boolean r5 = a(r4, r6)     // Catch:{ Throwable -> 0x0094 }
                if (r5 != 0) goto L_0x008d
                r4.delete()     // Catch:{ Throwable -> 0x0094 }
                long r5 = y     // Catch:{ Throwable -> 0x0094 }
                java.lang.String r0 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                java.lang.String r7 = ".deleteHistoryCoreFiles verifyCoreCompressFileVersion failure! file: "
                r1.<init>(r7)     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                r1.append(r4)     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
                com.uc.webview.export.internal.utility.Log.d(r0, r1)     // Catch:{ Throwable -> 0x008a, all -> 0x0088 }
            L_0x0086:
                r0 = r5
                goto L_0x008d
            L_0x0088:
                r0 = r5
                goto L_0x00a2
            L_0x008a:
                r8 = move-exception
                r0 = r5
                goto L_0x0095
            L_0x008d:
                int r3 = r3 + 1
                goto L_0x0028
            L_0x0090:
                return r0
            L_0x0091:
                long r2 = z     // Catch:{ Throwable -> 0x0094 }
                return r2
            L_0x0094:
                r8 = move-exception
            L_0x0095:
                long r2 = w     // Catch:{ all -> 0x00a2 }
                java.lang.String r0 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ all -> 0x00a1 }
                java.lang.String r1 = ".deleteHistoryCoreFiles"
                com.uc.webview.export.internal.utility.Log.d(r0, r1, r8)     // Catch:{ all -> 0x00a1 }
                return r2
            L_0x00a1:
                r0 = r2
            L_0x00a2:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.bk.a.a(java.io.File):long");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:130:0x02a9, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:133:?, code lost:
            com.uc.webview.export.internal.utility.Log.d(com.uc.webview.export.internal.setup.bk.a(), ".copyCoreFileToSdcard", r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:0x02b5, code lost:
            r2 = r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x02b7, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:138:0x02b8, code lost:
            r1 = r0;
            r2 = r10;
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [B:107:0x0272, B:126:0x029f] */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:110:0x0278 A[Catch:{ Throwable -> 0x027c, all -> 0x02b5 }] */
        /* JADX WARNING: Removed duplicated region for block: B:119:0x028e A[Catch:{ Throwable -> 0x0256, Throwable -> 0x02c9 }] */
        /* JADX WARNING: Removed duplicated region for block: B:129:0x02a5 A[Catch:{ Throwable -> 0x02a9, all -> 0x02b5 }] */
        /* JADX WARNING: Removed duplicated region for block: B:136:? A[ExcHandler: all (unused java.lang.Throwable), PHI: r10 
          PHI: (r10v5 long) = (r10v6 long), (r10v6 long), (r10v6 long), (r10v8 long), (r10v8 long) binds: [B:107:0x0272, B:113:0x027e, B:114:?, B:126:0x029f, B:132:0x02ab] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC, Splitter:B:107:0x0272] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:57:0x0130=Splitter:B:57:0x0130, B:24:0x00a5=Splitter:B:24:0x00a5, B:116:0x0288=Splitter:B:116:0x0288, B:139:0x02bb=Splitter:B:139:0x02bb, B:75:0x018e=Splitter:B:75:0x018e} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static long a(java.io.File r17, java.lang.String r18) {
            /*
                r1 = r18
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r4 = ".copyCoreFileToSdcard("
                r3.<init>(r4)
                java.lang.String r4 = r17.getAbsolutePath()
                r3.append(r4)
                java.lang.String r4 = ", "
                r3.append(r4)
                r3.append(r1)
                java.lang.String r4 = ")"
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                com.uc.webview.export.internal.utility.Log.d(r2, r3)
                long r2 = A
                com.uc.webview.export.internal.utility.g$a r4 = new com.uc.webview.export.internal.utility.g$a
                r4.<init>()
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r6 = ".copyCoreFileToSdcard copy file path : "
                java.lang.String r7 = java.lang.String.valueOf(r18)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r6 = r6.concat(r7)     // Catch:{ Throwable -> 0x02c9 }
                com.uc.webview.export.internal.utility.Log.d(r5, r6)     // Catch:{ Throwable -> 0x02c9 }
                boolean r5 = com.uc.webview.export.internal.utility.j.a(r18)     // Catch:{ Throwable -> 0x02c9 }
                if (r5 == 0) goto L_0x0054
                long r5 = D     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r1 = "csc_vvfsv"
                long r2 = r4.s
                java.lang.String r2 = java.lang.Long.toString(r2)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                return r5
            L_0x0054:
                java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x02c9 }
                r5.<init>(r1)     // Catch:{ Throwable -> 0x02c9 }
                boolean r1 = r5.exists()     // Catch:{ Throwable -> 0x02c9 }
                if (r1 == 0) goto L_0x02bb
                boolean r1 = r5.isFile()     // Catch:{ Throwable -> 0x02c9 }
                if (r1 != 0) goto L_0x0067
                goto L_0x02bb
            L_0x0067:
                java.lang.String r1 = r5.getName()     // Catch:{ Throwable -> 0x02c9 }
                boolean r1 = com.uc.webview.export.cyclone.UCCyclone.detect7zFromFileName(r1)     // Catch:{ Throwable -> 0x02c9 }
                r6 = 0
                if (r1 != 0) goto L_0x00a5
                boolean r1 = com.uc.webview.export.internal.utility.g.d()     // Catch:{ Throwable -> 0x02c9 }
                if (r1 != 0) goto L_0x00a5
                boolean r1 = com.uc.webview.export.internal.utility.g.a(r5, r6)     // Catch:{ Throwable -> 0x02c9 }
                if (r1 != 0) goto L_0x00a5
                long r6 = I     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r3 = ".copyCoreFileToSdcard verifySignature failure! file: "
                r2.<init>(r3)     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r3 = r5.getAbsolutePath()     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                r2.append(r3)     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r1 = "csc_vvfsv"
                long r2 = r4.s
                java.lang.String r2 = java.lang.Long.toString(r2)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                return r6
            L_0x00a5:
                boolean r1 = a(r5, r4)     // Catch:{ Throwable -> 0x02c9 }
                if (r1 != 0) goto L_0x00df
                long r6 = J     // Catch:{ Throwable -> 0x02c9 }
                long r1 = com.uc.webview.export.internal.utility.g.a.n     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                r4.a(r1)     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r3 = ".copyCoreFileToSdcard verifyCoreCompressFileVersion failure! file: "
                r2.<init>(r3)     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r3 = r5.getAbsolutePath()     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                r2.append(r3)     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Throwable -> 0x00da, all -> 0x00d7 }
                java.lang.String r1 = "csc_vvfsv"
                long r2 = r4.s
                java.lang.String r2 = java.lang.Long.toString(r2)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                return r6
            L_0x00d7:
                r2 = r6
                goto L_0x0291
            L_0x00da:
                r0 = move-exception
                r1 = r0
                r2 = r6
                goto L_0x02cb
            L_0x00df:
                java.io.File[] r1 = r17.listFiles()     // Catch:{ Throwable -> 0x02c9 }
                r7 = 1
                r8 = 0
                if (r1 == 0) goto L_0x018e
                int r9 = r1.length     // Catch:{ Throwable -> 0x02c9 }
                if (r9 <= 0) goto L_0x018e
                long r9 = r5.length()     // Catch:{ Throwable -> 0x02c9 }
                int r11 = r1.length     // Catch:{ Throwable -> 0x02c9 }
                r12 = r6
                r6 = 0
            L_0x00f1:
                if (r6 >= r11) goto L_0x018e
                r13 = r1[r6]     // Catch:{ Throwable -> 0x02c9 }
                long r14 = r13.length()     // Catch:{ Throwable -> 0x02c9 }
                int r14 = (r9 > r14 ? 1 : (r9 == r14 ? 0 : -1))
                if (r14 != 0) goto L_0x018a
                boolean r14 = com.uc.webview.export.internal.utility.j.a(r12)     // Catch:{ Throwable -> 0x02c9 }
                r15 = 1013(0x3f5, float:1.42E-42)
                if (r14 == 0) goto L_0x0130
                com.uc.webview.export.cyclone.UCCyclone$MessageDigestType r12 = com.uc.webview.export.cyclone.UCCyclone.MessageDigestType.SHA1     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r12 = com.uc.webview.export.cyclone.UCCyclone.hashFileContents(r5, r12)     // Catch:{ Throwable -> 0x02c9 }
                boolean r14 = com.uc.webview.export.internal.utility.j.a(r12)     // Catch:{ Throwable -> 0x02c9 }
                if (r14 == 0) goto L_0x0130
                long r9 = E     // Catch:{ Throwable -> 0x02c9 }
                long r1 = com.uc.webview.export.internal.utility.g.a.o     // Catch:{ Throwable -> 0x012b, all -> 0x0128 }
                r4.a(r1)     // Catch:{ Throwable -> 0x012b, all -> 0x0128 }
                com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ Throwable -> 0x012b, all -> 0x0128 }
                java.lang.String r2 = "SHA1 [%s] failed."
                java.lang.Object[] r3 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x012b, all -> 0x0128 }
                r3[r8] = r5     // Catch:{ Throwable -> 0x012b, all -> 0x0128 }
                java.lang.String r2 = java.lang.String.format(r2, r3)     // Catch:{ Throwable -> 0x012b, all -> 0x0128 }
                r1.<init>(r15, r2)     // Catch:{ Throwable -> 0x012b, all -> 0x0128 }
                throw r1     // Catch:{ Throwable -> 0x012b, all -> 0x0128 }
            L_0x0128:
                r2 = r9
                goto L_0x0291
            L_0x012b:
                r0 = move-exception
                r1 = r0
                r2 = r9
                goto L_0x02cb
            L_0x0130:
                com.uc.webview.export.cyclone.UCCyclone$MessageDigestType r14 = com.uc.webview.export.cyclone.UCCyclone.MessageDigestType.SHA1     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r14 = com.uc.webview.export.cyclone.UCCyclone.hashFileContents(r13, r14)     // Catch:{ Throwable -> 0x02c9 }
                boolean r16 = com.uc.webview.export.internal.utility.j.a(r14)     // Catch:{ Throwable -> 0x02c9 }
                if (r16 == 0) goto L_0x0158
                long r5 = E     // Catch:{ Throwable -> 0x02c9 }
                long r1 = com.uc.webview.export.internal.utility.g.a.o     // Catch:{ Throwable -> 0x0153 }
                r4.a(r1)     // Catch:{ Throwable -> 0x0153 }
                com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ Throwable -> 0x0153 }
                java.lang.String r2 = "SHA1 [%s] failed."
                java.lang.Object[] r3 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x0153 }
                r3[r8] = r13     // Catch:{ Throwable -> 0x0153 }
                java.lang.String r2 = java.lang.String.format(r2, r3)     // Catch:{ Throwable -> 0x0153 }
                r1.<init>(r15, r2)     // Catch:{ Throwable -> 0x0153 }
                throw r1     // Catch:{ Throwable -> 0x0153 }
            L_0x0153:
                r0 = move-exception
                r1 = r0
                r2 = r5
                goto L_0x02cb
            L_0x0158:
                boolean r14 = r14.equals(r12)     // Catch:{ Throwable -> 0x02c9 }
                if (r14 == 0) goto L_0x018a
                long r5 = G     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r1 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x0153 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0153 }
                java.lang.String r3 = ".copyCoreFileToSdcard "
                r2.<init>(r3)     // Catch:{ Throwable -> 0x0153 }
                java.lang.String r3 = r13.getAbsolutePath()     // Catch:{ Throwable -> 0x0153 }
                r2.append(r3)     // Catch:{ Throwable -> 0x0153 }
                java.lang.String r3 = " had exists."
                r2.append(r3)     // Catch:{ Throwable -> 0x0153 }
                java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0153 }
                com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Throwable -> 0x0153 }
                java.lang.String r1 = "csc_vvfsv"
                long r2 = r4.s
                java.lang.String r2 = java.lang.Long.toString(r2)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                return r5
            L_0x018a:
                int r6 = r6 + 1
                goto L_0x00f1
            L_0x018e:
                java.lang.String r1 = r5.getName()     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r6 = "."
                int r6 = r1.lastIndexOf(r6)     // Catch:{ Throwable -> 0x02c9 }
                r9 = 2
                if (r6 <= 0) goto L_0x01cc
                java.lang.String r6 = "."
                int r6 = r1.lastIndexOf(r6)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r6 = r1.substring(r8, r6)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r10 = "."
                int r10 = r1.lastIndexOf(r10)     // Catch:{ Throwable -> 0x02c9 }
                int r10 = r10 + r7
                int r11 = r1.length()     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r1 = r1.substring(r10, r11)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r10 = "%s_%s.%s"
                r11 = 3
                java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x02c9 }
                r11[r8] = r6     // Catch:{ Throwable -> 0x02c9 }
                long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r6 = java.lang.String.valueOf(r12)     // Catch:{ Throwable -> 0x02c9 }
                r11[r7] = r6     // Catch:{ Throwable -> 0x02c9 }
                r11[r9] = r1     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r1 = java.lang.String.format(r10, r11)     // Catch:{ Throwable -> 0x02c9 }
                goto L_0x01e0
            L_0x01cc:
                java.lang.String r6 = "%s_%s"
                java.lang.Object[] r10 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x02c9 }
                r10[r8] = r1     // Catch:{ Throwable -> 0x02c9 }
                long r11 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r1 = java.lang.String.valueOf(r11)     // Catch:{ Throwable -> 0x02c9 }
                r10[r7] = r1     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r1 = java.lang.String.format(r6, r10)     // Catch:{ Throwable -> 0x02c9 }
            L_0x01e0:
                java.io.File r6 = new java.io.File     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r10 = r17.getAbsolutePath()     // Catch:{ Throwable -> 0x02c9 }
                r6.<init>(r10, r1)     // Catch:{ Throwable -> 0x02c9 }
                java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x02c9 }
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02c9 }
                r10.<init>()     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r11 = r6.getAbsolutePath()     // Catch:{ Throwable -> 0x02c9 }
                r10.append(r11)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r11 = ".tmp"
                r10.append(r11)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x02c9 }
                r1.<init>(r10)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r10 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x02c9 }
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r12 = ".copyCoreFileToSdcard targetFile: "
                r11.<init>(r12)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r12 = r6.getAbsolutePath()     // Catch:{ Throwable -> 0x02c9 }
                r11.append(r12)     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x02c9 }
                com.uc.webview.export.internal.utility.Log.d(r10, r11)     // Catch:{ Throwable -> 0x02c9 }
                r1.createNewFile()     // Catch:{ Exception -> 0x0266, all -> 0x0262 }
                com.uc.webview.export.internal.utility.j.a(r5, r1)     // Catch:{ Exception -> 0x0266, all -> 0x0262 }
                boolean r10 = r1.renameTo(r6)     // Catch:{ Exception -> 0x0266, all -> 0x0262 }
                if (r10 != 0) goto L_0x0245
                long r10 = F     // Catch:{ Exception -> 0x0266, all -> 0x0262 }
                long r2 = com.uc.webview.export.internal.utility.g.a.p     // Catch:{ Exception -> 0x0243 }
                r4.a(r2)     // Catch:{ Exception -> 0x0243 }
                com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ Exception -> 0x0243 }
                r3 = 1005(0x3ed, float:1.408E-42)
                java.lang.String r5 = "Rename [%s] to [%s] failed."
                java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x0243 }
                r9[r8] = r1     // Catch:{ Exception -> 0x0243 }
                r9[r7] = r6     // Catch:{ Exception -> 0x0243 }
                java.lang.String r5 = java.lang.String.format(r5, r9)     // Catch:{ Exception -> 0x0243 }
                r2.<init>(r3, r5)     // Catch:{ Exception -> 0x0243 }
                throw r2     // Catch:{ Exception -> 0x0243 }
            L_0x0243:
                r0 = move-exception
                goto L_0x0268
            L_0x0245:
                long r7 = r5.lastModified()     // Catch:{ Exception -> 0x0266, all -> 0x0262 }
                r6.setLastModified(r7)     // Catch:{ Exception -> 0x0266, all -> 0x0262 }
                boolean r5 = r1.exists()     // Catch:{ Throwable -> 0x0256 }
                if (r5 == 0) goto L_0x0288
                r1.delete()     // Catch:{ Throwable -> 0x0256 }
                goto L_0x0288
            L_0x0256:
                r0 = move-exception
                r1 = r0
                java.lang.String r5 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r6 = ".copyCoreFileToSdcard"
                com.uc.webview.export.internal.utility.Log.d(r5, r6, r1)     // Catch:{ Throwable -> 0x02c9 }
                goto L_0x0288
            L_0x0262:
                r0 = move-exception
                r10 = r2
            L_0x0264:
                r2 = r0
                goto L_0x029f
            L_0x0266:
                r0 = move-exception
                r10 = r2
            L_0x0268:
                r2 = r0
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ all -> 0x029d }
                java.lang.String r5 = ".copyCoreFileToSdcard"
                com.uc.webview.export.internal.utility.Log.d(r3, r5, r2)     // Catch:{ all -> 0x029d }
                boolean r2 = r1.exists()     // Catch:{ Throwable -> 0x027c, all -> 0x02b5 }
                if (r2 == 0) goto L_0x0287
                r1.delete()     // Catch:{ Throwable -> 0x027c, all -> 0x02b5 }
                goto L_0x0287
            L_0x027c:
                r0 = move-exception
                r1 = r0
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x02b7, all -> 0x02b5 }
                java.lang.String r3 = ".copyCoreFileToSdcard"
                com.uc.webview.export.internal.utility.Log.d(r2, r3, r1)     // Catch:{ Throwable -> 0x02b7, all -> 0x02b5 }
            L_0x0287:
                r2 = r10
            L_0x0288:
                long r5 = F     // Catch:{ Throwable -> 0x02c9 }
                int r1 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                if (r1 == 0) goto L_0x0291
                long r5 = B     // Catch:{ Throwable -> 0x02c9 }
            L_0x0290:
                r2 = r5
            L_0x0291:
                java.lang.String r1 = "csc_vvfsv"
                long r4 = r4.s
                java.lang.String r4 = java.lang.Long.toString(r4)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r4)
                return r2
            L_0x029d:
                r0 = move-exception
                goto L_0x0264
            L_0x029f:
                boolean r3 = r1.exists()     // Catch:{ Throwable -> 0x02a9, all -> 0x02b5 }
                if (r3 == 0) goto L_0x02b4
                r1.delete()     // Catch:{ Throwable -> 0x02a9, all -> 0x02b5 }
                goto L_0x02b4
            L_0x02a9:
                r0 = move-exception
                r1 = r0
                java.lang.String r3 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ Throwable -> 0x02b7, all -> 0x02b5 }
                java.lang.String r5 = ".copyCoreFileToSdcard"
                com.uc.webview.export.internal.utility.Log.d(r3, r5, r1)     // Catch:{ Throwable -> 0x02b7, all -> 0x02b5 }
            L_0x02b4:
                throw r2     // Catch:{ Throwable -> 0x02b7, all -> 0x02b5 }
            L_0x02b5:
                r2 = r10
                goto L_0x0291
            L_0x02b7:
                r0 = move-exception
                r1 = r0
                r2 = r10
                goto L_0x02cb
            L_0x02bb:
                long r5 = H     // Catch:{ Throwable -> 0x02c9 }
                java.lang.String r1 = "csc_vvfsv"
                long r2 = r4.s
                java.lang.String r2 = java.lang.Long.toString(r2)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                return r5
            L_0x02c9:
                r0 = move-exception
                r1 = r0
            L_0x02cb:
                long r5 = C     // Catch:{ all -> 0x0291 }
                java.lang.String r2 = com.uc.webview.export.internal.setup.bk.a     // Catch:{ all -> 0x0290 }
                java.lang.String r3 = ".copyCoreFileToSdcard"
                com.uc.webview.export.internal.utility.Log.d(r2, r3, r1)     // Catch:{ all -> 0x0290 }
                java.lang.String r1 = "csc_vvfsv"
                long r2 = r4.s
                java.lang.String r2 = java.lang.Long.toString(r2)
                com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.bk.a.a(java.io.File, java.lang.String):long");
        }

        public static void a() {
            String[] split;
            try {
                if (!g.b()) {
                    Log.d(bk.a, ".statDevicesHasShareCore Sdcard配置及权限校验失败");
                    return;
                }
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(SDKFactory.e.getPackageName());
                String param = UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_SPECIAL_HOST_PKG_NAME_LIST);
                if (!j.a(param)) {
                    for (String str : param.split(CDParamKeys.CD_VALUE_STRING_SPLITER)) {
                        if (!j.a(str) && !str.equals(SDKFactory.e.getPackageName())) {
                            arrayList.add(str);
                        }
                    }
                }
                for (String str2 : arrayList) {
                    File c2 = g.c(str2);
                    if (!c2.exists()) {
                        String a2 = bk.a;
                        StringBuilder sb = new StringBuilder(".statDevicesHasShareCore ");
                        sb.append(c2.getAbsolutePath());
                        sb.append(" not exists.");
                        Log.d(a2, sb.toString());
                    } else {
                        File[] listFiles = c2.listFiles();
                        if (listFiles != null) {
                            if (listFiles.length != 0) {
                                String a3 = bk.a;
                                StringBuilder sb2 = new StringBuilder(".statDevicesHasShareCore ");
                                sb2.append(str2);
                                sb2.append(",");
                                sb2.append(str2.hashCode());
                                sb2.append(", ");
                                sb2.append(listFiles.length);
                                Log.d(a3, sb2.toString());
                                StringBuilder sb3 = new StringBuilder("csc_dhsc_");
                                sb3.append(str2.hashCode());
                                WaStat.stat(sb3.toString(), Integer.toString(listFiles.length));
                            }
                        }
                        String a4 = bk.a;
                        StringBuilder sb4 = new StringBuilder(".statDevicesHasShareCore ");
                        sb4.append(c2.getAbsolutePath());
                        sb4.append(" empty.");
                        Log.d(a4, sb4.toString());
                    }
                }
            } catch (Throwable th) {
                Log.d(bk.a, ".statDevicesHasShareCore", th);
            }
        }
    }

    public void run() {
        Log.d(a, ".run");
        if (j.a((Boolean) getOption("o_st_dhcs"))) {
            synchronized (b) {
                new a(0);
                a.a();
            }
            return;
        }
        String param = UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_HOST_COMPRESSED_CORE_FILE_PATH);
        WaStat.stat((String) IWaStat.SHARE_CORE_COPY_TASK_RUN_PV);
        synchronized (b) {
            if (!j.a(param)) {
                WaStat.stat((String) IWaStat.SHARE_CORE_COPY_TASK_RUN_CALL_PV);
                new a(0);
                a.a(param, DecFileOrign.Other);
            }
            new a(0);
            a.a();
        }
    }

    public static void a(String str, String str2) {
        String str3 = a;
        StringBuilder sb = new StringBuilder(".shareDownloadFile(");
        sb.append(str);
        sb.append(", ");
        sb.append(str2);
        sb.append(")");
        Log.d(str3, sb.toString());
        WaStat.stat((String) IWaStat.SHARE_CORE_COPY_TASK_UPD_PV);
        if (!j.a(str2)) {
            synchronized (b) {
                WaStat.stat((String) IWaStat.SHARE_CORE_COPY_TASK_UPD_CALL_PV);
                new a(0);
                a.a(str2, DecFileOrign.Update);
            }
        }
    }
}
