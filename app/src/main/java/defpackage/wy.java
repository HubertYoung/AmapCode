package defpackage;

import com.amap.bundle.lotuspool.internal.model.bean.Command;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings({"NN_NAKED_NOTIFY", "UW_UNCOND_WAIT", "WA_NOT_IN_LOOP"})
/* renamed from: wy reason: default package */
/* compiled from: ReplaceFileExecutor */
public final class wy implements bjf, wv {
    private int a;
    private String b;
    private long c;
    private StringBuilder d = new StringBuilder();

    public final boolean a(Command command) {
        return command != null;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:82|83|84|85) */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x016f, code lost:
        if (r1.c != 0) goto L_0x0195;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0171, code lost:
        r1.d.append(" file length_0;");
        r11 = r2.b;
        r13 = r2.d;
        r5 = r2.e;
        r9 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult(r4, r11, r13, r5, r2.i, r23, 1201, r1.d.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0194, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0199, code lost:
        if (r9.exists() != false) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x019b, code lost:
        r1.d.append(" file not exist;");
        r10 = r4;
        r9 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult(r10, r2.b, r2.d, r2.e, r2.i, r23, 1201, r1.d.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x01bd, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01c6, code lost:
        if (r9.length() == r1.c) goto L_0x0201;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01c8, code lost:
        r3 = r1.d;
        r3.append(" file err_");
        r3.append(r1.c);
        r3.append("_");
        r3.append(r9.length());
        r3.append(";");
        r10 = r4;
        r9 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult(r10, r2.b, r2.d, r2.e, r2.i, r23, 1201, r1.d.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0200, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0201, code lost:
        r3 = defpackage.xi.a(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0209, code lost:
        if (android.text.TextUtils.equals(r3, r5) != false) goto L_0x024b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x020b, code lost:
        r6 = r1.d;
        r6.append(" md5 err_");
        r6.append(r5);
        r6.append("->");
        r6.append(r3);
        r6.append(";");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0226, code lost:
        if (r9.delete() == false) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0228, code lost:
        r1.d.append(" del newfile;");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x022f, code lost:
        r10 = r4;
        r9 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult(r10, r2.b, r2.d, r2.e, r2.i, r23, 1201, r1.d.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x024a, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x024b, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(r6.getAbsolutePath());
        r5.append(".bak");
        r3 = new java.io.File(r5.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0269, code lost:
        if (r6.exists() == false) goto L_0x02ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x026f, code lost:
        if (r6.renameTo(r3) != false) goto L_0x02ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0271, code lost:
        r1.d.append(" rename oldfile err;");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x027c, code lost:
        if (r6.delete() != false) goto L_0x02ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x027e, code lost:
        r1.d.append(" delete oldfile err;");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0289, code lost:
        if (r9.delete() == false) goto L_0x0292;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x028b, code lost:
        r1.d.append(" del newfile;");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0292, code lost:
        r10 = r4;
        r9 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult(r10, r2.b, r2.d, r2.e, r2.i, r23, 1204, r1.d.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x02ad, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x02b2, code lost:
        if (r9.renameTo(r6) != false) goto L_0x02d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x02b4, code lost:
        r1.d.append(" new file reNameTo old file err;");
        r10 = r4;
        r9 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult(r10, r2.b, r2.d, r2.e, r2.i, r23, 1204, r1.d.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x02d6, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x02db, code lost:
        if (r3.delete() != false) goto L_0x02e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x02dd, code lost:
        r1.d.append(" del bak file err;");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x02e4, code lost:
        r10 = r4;
        r9 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult(r10, r2.b, r2.d, r2.e, r2.i, r23, 1, r1.d.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x02ff, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        r10 = r4;
        r9 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult(r10, r2.b, r2.d, r2.e, r2.i, r23, 1201, r1.d.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x031f, code lost:
        return r9;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:82:0x0303 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.amap.bundle.lotuspool.internal.model.bean.CommandResult a(java.lang.String r22, int r23, com.amap.bundle.lotuspool.internal.model.bean.Command r24) {
        /*
            r21 = this;
            r1 = r21
            r2 = r24
            java.lang.String r3 = "file_url"
            java.lang.String r3 = r2.e(r3)     // Catch:{ Exception -> 0x036c }
            java.lang.String r4 = "path"
            java.lang.String r4 = r2.e(r4)     // Catch:{ Exception -> 0x036c }
            java.lang.String r5 = "network"
            int r5 = r2.c(r5)     // Catch:{ Exception -> 0x036c }
            r1.a = r5     // Catch:{ Exception -> 0x036c }
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 != 0) goto L_0x0347
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 == 0) goto L_0x0026
            goto L_0x0347
        L_0x0026:
            java.lang.String r5 = "md5"
            java.lang.String r5 = r2.e(r5)     // Catch:{ Exception -> 0x0322 }
            java.lang.String r4 = defpackage.xi.a(r4)
            boolean r6 = android.text.TextUtils.isEmpty(r4)
            if (r6 == 0) goto L_0x0058
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r4 = " path is null;"
            r3.append(r4)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r7 = r2.b
            long r9 = r2.d
            int r11 = r2.e
            long r12 = r2.i
            r15 = 1203(0x4b3, float:1.686E-42)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r16 = r2.toString()
            r5 = r3
            r6 = r22
            r14 = r23
            r5.<init>(r6, r7, r9, r11, r12, r14, r15, r16)
            return r3
        L_0x0058:
            java.io.File r6 = new java.io.File
            r6.<init>(r4)
            boolean r7 = r6.exists()
            if (r7 != 0) goto L_0x00a6
            java.lang.StringBuilder r7 = r1.d
            java.lang.String r8 = " old file not exist="
            r7.append(r8)
            r7.append(r4)
            java.lang.String r4 = ";"
            r7.append(r4)
            java.io.File r4 = r6.getParentFile()
            if (r4 == 0) goto L_0x0101
            boolean r7 = r4.exists()
            if (r7 != 0) goto L_0x0101
            boolean r4 = r4.mkdirs()
            if (r4 != 0) goto L_0x0101
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r4 = " mkdirs err;"
            r3.append(r4)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r7 = r2.b
            long r9 = r2.d
            int r11 = r2.e
            long r12 = r2.i
            r15 = 1203(0x4b3, float:1.686E-42)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r16 = r2.toString()
            r5 = r3
            r6 = r22
            r14 = r23
            r5.<init>(r6, r7, r9, r11, r12, r14, r15, r16)
            return r3
        L_0x00a6:
            boolean r7 = r6.isDirectory()
            if (r7 == 0) goto L_0x00d6
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " old file is dir="
            r3.append(r5)
            r3.append(r4)
            java.lang.String r4 = ";"
            r3.append(r4)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r7 = r2.b
            long r9 = r2.d
            int r11 = r2.e
            long r12 = r2.i
            r15 = 101(0x65, float:1.42E-43)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r16 = r2.toString()
            r5 = r3
            r6 = r22
            r14 = r23
            r5.<init>(r6, r7, r9, r11, r12, r14, r15, r16)
            return r3
        L_0x00d6:
            java.lang.String r4 = defpackage.xi.a(r6)
            boolean r4 = android.text.TextUtils.equals(r4, r5)
            if (r4 == 0) goto L_0x0101
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r4 = "same file;"
            r3.append(r4)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r7 = r2.b
            long r9 = r2.d
            int r11 = r2.e
            long r12 = r2.i
            r15 = 1
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r16 = r2.toString()
            r5 = r3
            r6 = r22
            r14 = r23
            r5.<init>(r6, r7, r9, r11, r12, r14, r15, r16)
            return r3
        L_0x0101:
            java.lang.String r4 = r6.getName()
            java.lang.String r7 = "."
            int r7 = r4.lastIndexOf(r7)
            r8 = 0
            if (r7 <= 0) goto L_0x0118
            java.lang.String r7 = "."
            int r7 = r4.lastIndexOf(r7)
            java.lang.String r4 = r4.substring(r8, r7)
        L_0x0118:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r4)
            java.lang.String r4 = "_"
            r7.append(r4)
            r4 = r22
            r7.append(r4)
            java.lang.String r9 = "_"
            r7.append(r9)
            r15 = r23
            r7.append(r15)
            java.lang.String r9 = ".rct"
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            java.io.File r9 = new java.io.File
            java.lang.String r10 = r6.getParent()
            r9.<init>(r10, r7)
            bjg r7 = new bjg
            java.lang.String r10 = r9.getAbsolutePath()
            r7.<init>(r10)
            r7.setUrl(r3)
            r3 = 1
            r7.b = r3
            r7.a = r8
            java.lang.String r3 = r9.getAbsolutePath()
            r1.b = r3
            bjh r3 = defpackage.bjh.a()
            r3.a(r7, r1)
            monitor-enter(r21)
            r21.wait()     // Catch:{ InterruptedException -> 0x0303 }
            monitor-exit(r21)     // Catch:{ all -> 0x0300 }
            long r7 = r1.c
            r10 = 0
            int r3 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r3 != 0) goto L_0x0195
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " file length_0;"
            r3.append(r5)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r5 = r2.e
            long r6 = r2.i
            r19 = 1201(0x4b1, float:1.683E-42)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r15 = r5
            r16 = r6
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x0195:
            boolean r3 = r9.exists()
            if (r3 != 0) goto L_0x01be
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " file not exist;"
            r3.append(r5)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 1201(0x4b1, float:1.683E-42)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x01be:
            long r7 = r9.length()
            long r10 = r1.c
            int r3 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r3 == 0) goto L_0x0201
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " file err_"
            r3.append(r5)
            long r5 = r1.c
            r3.append(r5)
            java.lang.String r5 = "_"
            r3.append(r5)
            long r5 = r9.length()
            r3.append(r5)
            java.lang.String r5 = ";"
            r3.append(r5)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 1201(0x4b1, float:1.683E-42)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x0201:
            java.lang.String r3 = defpackage.xi.a(r9)
            boolean r7 = android.text.TextUtils.equals(r3, r5)
            if (r7 != 0) goto L_0x024b
            java.lang.StringBuilder r6 = r1.d
            java.lang.String r7 = " md5 err_"
            r6.append(r7)
            r6.append(r5)
            java.lang.String r5 = "->"
            r6.append(r5)
            r6.append(r3)
            java.lang.String r3 = ";"
            r6.append(r3)
            boolean r3 = r9.delete()
            if (r3 == 0) goto L_0x022f
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " del newfile;"
            r3.append(r5)
        L_0x022f:
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 1201(0x4b1, float:1.683E-42)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x024b:
            java.io.File r3 = new java.io.File
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = r6.getAbsolutePath()
            r5.append(r7)
            java.lang.String r7 = ".bak"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            r3.<init>(r5)
            boolean r5 = r6.exists()
            if (r5 == 0) goto L_0x02ae
            boolean r5 = r6.renameTo(r3)
            if (r5 != 0) goto L_0x02ae
            java.lang.StringBuilder r5 = r1.d
            java.lang.String r7 = " rename oldfile err;"
            r5.append(r7)
            boolean r5 = r6.delete()
            if (r5 != 0) goto L_0x02ae
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " delete oldfile err;"
            r3.append(r5)
            boolean r3 = r9.delete()
            if (r3 == 0) goto L_0x0292
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " del newfile;"
            r3.append(r5)
        L_0x0292:
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 1204(0x4b4, float:1.687E-42)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x02ae:
            boolean r5 = r9.renameTo(r6)
            if (r5 != 0) goto L_0x02d7
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " new file reNameTo old file err;"
            r3.append(r5)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 1204(0x4b4, float:1.687E-42)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x02d7:
            boolean r3 = r3.delete()
            if (r3 != 0) goto L_0x02e4
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = " del bak file err;"
            r3.append(r5)
        L_0x02e4:
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 1
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x0300:
            r0 = move-exception
            r2 = r0
            goto L_0x0320
        L_0x0303:
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult     // Catch:{ all -> 0x0300 }
            long r11 = r2.b     // Catch:{ all -> 0x0300 }
            long r13 = r2.d     // Catch:{ all -> 0x0300 }
            int r15 = r2.e     // Catch:{ all -> 0x0300 }
            long r5 = r2.i     // Catch:{ all -> 0x0300 }
            r19 = 1201(0x4b1, float:1.683E-42)
            java.lang.StringBuilder r2 = r1.d     // Catch:{ all -> 0x0300 }
            java.lang.String r20 = r2.toString()     // Catch:{ all -> 0x0300 }
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)     // Catch:{ all -> 0x0300 }
            monitor-exit(r21)     // Catch:{ all -> 0x0300 }
            return r3
        L_0x0320:
            monitor-exit(r21)     // Catch:{ all -> 0x0300 }
            throw r2
        L_0x0322:
            r4 = r22
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = "---md5 not exist;"
            r3.append(r5)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 101(0x65, float:1.42E-43)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x0347:
            r4 = r22
            java.lang.StringBuilder r3 = r1.d
            java.lang.String r5 = "---url or path empty;"
            r3.append(r5)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 101(0x65, float:1.42E-43)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        L_0x036c:
            r0 = move-exception
            r4 = r22
            r3 = r0
            java.lang.StringBuilder r5 = r1.d
            java.lang.String r6 = "---"
            r5.append(r6)
            java.lang.String r3 = r3.toString()
            r5.append(r3)
            java.lang.String r3 = ";"
            r5.append(r3)
            com.amap.bundle.lotuspool.internal.model.bean.CommandResult r3 = new com.amap.bundle.lotuspool.internal.model.bean.CommandResult
            long r11 = r2.b
            long r13 = r2.d
            int r15 = r2.e
            long r5 = r2.i
            r19 = 101(0x65, float:1.42E-43)
            java.lang.StringBuilder r2 = r1.d
            java.lang.String r20 = r2.toString()
            r9 = r3
            r10 = r4
            r16 = r5
            r18 = r23
            r9.<init>(r10, r11, r13, r15, r16, r18, r19, r20)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wy.a(java.lang.String, int, com.amap.bundle.lotuspool.internal.model.bean.Command):com.amap.bundle.lotuspool.internal.model.bean.CommandResult");
    }

    public final void onStart(long j, Map<String, List<String>> map, int i) {
        StringBuilder sb = this.d;
        sb.append(" start_");
        sb.append(j);
        sb.append(";");
    }

    public final void onProgressUpdate(long j, long j2) {
        if (!xi.b(this.a)) {
            this.d.append(" cancel down;");
            bjh.a().a(this.b);
        }
    }

    public final void onFinish(bpk bpk) {
        StringBuilder sb = this.d;
        sb.append(" finish_");
        sb.append(bpk.getContentLength());
        sb.append(";");
        this.c = bpk.getContentLength();
        synchronized (this) {
            notifyAll();
        }
    }

    public final void onError(int i, int i2) {
        StringBuilder sb = this.d;
        sb.append(" err_");
        sb.append(i);
        sb.append("_");
        sb.append(i2);
        sb.append(";");
        synchronized (this) {
            notifyAll();
        }
    }
}
