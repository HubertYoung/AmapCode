package com.alipay.mobile.common.logging.appender;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.encrypt.LogEncryptClient;
import com.alipay.mobile.common.logging.util.FileUtil;
import java.io.File;

public abstract class FileAppender extends Appender {
    protected boolean r;
    protected boolean s;
    protected boolean t;
    protected boolean u;

    /* access modifiers changed from: protected */
    public abstract File c();

    public FileAppender(LogContext logContext, String logCategory) {
        super(logContext, logCategory);
    }

    /* access modifiers changed from: protected */
    public final boolean a(String content, boolean isMdapEncrypt) {
        if (!isMdapEncrypt) {
            return a(content);
        }
        LogEncryptClient client = LoggerFactory.getLogContext().getLogEncryptClient();
        if (client == null) {
            return a(content);
        }
        String[] logs = content.split("\\$\\$");
        int len = logs.length;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            String enItem = client.encrypt(logs[i]);
            if (!TextUtils.isEmpty(enItem)) {
                sb.append("1_").append(enItem).append("$$");
            } else {
                sb.append(logs[i]).append("$$");
            }
        }
        return a(sb.toString());
    }

    private boolean a(String content) {
        try {
            File saveFile = c();
            if (saveFile == null) {
                return true;
            }
            FileUtil.writeFile(saveFile, content, true);
            return true;
        } catch (Throwable e) {
            if (!this.r) {
                this.r = true;
                Log.e("Appender", this.b, e);
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a4 A[Catch:{ all -> 0x00b6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b0 A[SYNTHETIC, Splitter:B:45:0x00b0] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b9 A[SYNTHETIC, Splitter:B:50:0x00b9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a(byte[] r14, int r15) {
        /*
            r13 = this;
            monitor-enter(r13)
            r4 = 0
            r10 = 0
            byte[] r4 = com.alipay.mobile.common.logging.util.LoggingUtil.gzipDataByBytes(r14, r10, r15)     // Catch:{ Throwable -> 0x000c }
        L_0x0007:
            if (r4 != 0) goto L_0x001f
            r10 = 0
        L_0x000a:
            monitor-exit(r13)
            return r10
        L_0x000c:
            r9 = move-exception
            boolean r10 = r13.s     // Catch:{ all -> 0x001c }
            if (r10 != 0) goto L_0x0007
            r10 = 1
            r13.s = r10     // Catch:{ all -> 0x001c }
            java.lang.String r10 = "Appender"
            java.lang.String r11 = r13.b     // Catch:{ all -> 0x001c }
            android.util.Log.e(r10, r11, r9)     // Catch:{ all -> 0x001c }
            goto L_0x0007
        L_0x001c:
            r10 = move-exception
            monitor-exit(r13)
            throw r10
        L_0x001f:
            com.alipay.mobile.common.logging.util.HybridEncryption r10 = com.alipay.mobile.common.logging.util.HybridEncryption.getInstance()     // Catch:{ all -> 0x001c }
            r11 = 0
            int r12 = r4.length     // Catch:{ all -> 0x001c }
            byte[] r2 = r10.encrypt(r4, r11, r12)     // Catch:{ all -> 0x001c }
            com.alipay.mobile.common.logging.util.HybridEncryption r10 = com.alipay.mobile.common.logging.util.HybridEncryption.getInstance()     // Catch:{ all -> 0x001c }
            byte[] r6 = r10.getSecureSeed()     // Catch:{ all -> 0x001c }
            if (r2 == 0) goto L_0x0035
            if (r6 != 0) goto L_0x0045
        L_0x0035:
            boolean r10 = r13.t     // Catch:{ all -> 0x001c }
            if (r10 != 0) goto L_0x0043
            r10 = 1
            r13.t = r10     // Catch:{ all -> 0x001c }
            java.lang.String r10 = "Appender"
            java.lang.String r11 = "HybridEncryption.encrypt occured error"
            android.util.Log.e(r10, r11)     // Catch:{ all -> 0x001c }
        L_0x0043:
            r10 = 0
            goto L_0x000a
        L_0x0045:
            int r10 = r6.length     // Catch:{ all -> 0x001c }
            r11 = 32767(0x7fff, float:4.5916E-41)
            if (r10 <= r11) goto L_0x0061
            java.lang.String r10 = "Appender"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x001c }
            java.lang.String r12 = "the length of secure seed is too long: "
            r11.<init>(r12)     // Catch:{ all -> 0x001c }
            int r12 = r6.length     // Catch:{ all -> 0x001c }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x001c }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x001c }
            android.util.Log.e(r10, r11)     // Catch:{ all -> 0x001c }
            r10 = 0
            goto L_0x000a
        L_0x0061:
            java.io.File r5 = r13.c()     // Catch:{ all -> 0x001c }
            r7 = 0
            java.io.File r10 = r5.getParentFile()     // Catch:{ Throwable -> 0x009f }
            boolean r10 = r10.exists()     // Catch:{ Throwable -> 0x009f }
            if (r10 != 0) goto L_0x0077
            java.io.File r10 = r5.getParentFile()     // Catch:{ Throwable -> 0x009f }
            r10.mkdirs()     // Catch:{ Throwable -> 0x009f }
        L_0x0077:
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x009f }
            r10 = 1
            r3.<init>(r5, r10)     // Catch:{ Throwable -> 0x009f }
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x009f }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x009f }
            java.io.DataOutputStream r8 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x009f }
            r8.<init>(r0)     // Catch:{ Throwable -> 0x009f }
            int r10 = r2.length     // Catch:{ Throwable -> 0x00c6, all -> 0x00c3 }
            r8.writeInt(r10)     // Catch:{ Throwable -> 0x00c6, all -> 0x00c3 }
            int r10 = r6.length     // Catch:{ Throwable -> 0x00c6, all -> 0x00c3 }
            short r10 = (short) r10     // Catch:{ Throwable -> 0x00c6, all -> 0x00c3 }
            r8.writeShort(r10)     // Catch:{ Throwable -> 0x00c6, all -> 0x00c3 }
            r8.write(r6)     // Catch:{ Throwable -> 0x00c6, all -> 0x00c3 }
            r8.write(r2)     // Catch:{ Throwable -> 0x00c6, all -> 0x00c3 }
            r8.flush()     // Catch:{ Throwable -> 0x00c6, all -> 0x00c3 }
            r8.close()     // Catch:{ Throwable -> 0x00bd }
        L_0x009c:
            r10 = 1
            goto L_0x000a
        L_0x009f:
            r1 = move-exception
        L_0x00a0:
            boolean r10 = r13.u     // Catch:{ all -> 0x00b6 }
            if (r10 != 0) goto L_0x00ae
            r10 = 1
            r13.u = r10     // Catch:{ all -> 0x00b6 }
            java.lang.String r10 = "Appender"
            java.lang.String r11 = r13.b     // Catch:{ all -> 0x00b6 }
            android.util.Log.e(r10, r11, r1)     // Catch:{ all -> 0x00b6 }
        L_0x00ae:
            if (r7 == 0) goto L_0x00b3
            r7.close()     // Catch:{ Throwable -> 0x00bf }
        L_0x00b3:
            r10 = 0
            goto L_0x000a
        L_0x00b6:
            r10 = move-exception
        L_0x00b7:
            if (r7 == 0) goto L_0x00bc
            r7.close()     // Catch:{ Throwable -> 0x00c1 }
        L_0x00bc:
            throw r10     // Catch:{ all -> 0x001c }
        L_0x00bd:
            r10 = move-exception
            goto L_0x009c
        L_0x00bf:
            r10 = move-exception
            goto L_0x00b3
        L_0x00c1:
            r11 = move-exception
            goto L_0x00bc
        L_0x00c3:
            r10 = move-exception
            r7 = r8
            goto L_0x00b7
        L_0x00c6:
            r1 = move-exception
            r7 = r8
            goto L_0x00a0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.appender.FileAppender.a(byte[], int):boolean");
    }
}
