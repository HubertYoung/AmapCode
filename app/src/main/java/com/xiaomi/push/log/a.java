package com.xiaomi.push.log;

import android.annotation.SuppressLint;
import android.content.Context;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.xiaomi.channel.commonutils.logger.b;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class a {
    private static String b = "/MiPushLog";
    @SuppressLint({"SimpleDateFormat"})
    private final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String c;
    private String d;
    private boolean e;
    private int f;
    private int g = 2097152;
    private ArrayList<File> h = new ArrayList<>();

    a() {
    }

    private void a(BufferedReader bufferedReader, BufferedWriter bufferedWriter, Pattern pattern) {
        char[] cArr = new char[4096];
        int read = bufferedReader.read(cArr);
        boolean z = false;
        while (read != -1 && !z) {
            String str = new String(cArr, 0, read);
            Matcher matcher = pattern.matcher(str);
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i >= read || !matcher.find(i)) {
                    break;
                }
                int start = matcher.start();
                String substring = str.substring(start, this.c.length() + start);
                if (this.e) {
                    if (substring.compareTo(this.d) > 0) {
                        read = start;
                        z = true;
                        break;
                    }
                } else if (substring.compareTo(this.c) >= 0) {
                    this.e = true;
                    i2 = start;
                }
                int indexOf = str.indexOf(10, start);
                if (indexOf == -1) {
                    indexOf = this.c.length();
                }
                i = start + indexOf;
            }
            if (this.e) {
                int i3 = read - i2;
                this.f += i3;
                if (!z) {
                    bufferedWriter.write(cArr, i2, i3);
                    if (this.f > this.g) {
                        break;
                    }
                } else {
                    bufferedWriter.write(cArr, i2, i3);
                    return;
                }
            }
            read = bufferedReader.read(cArr);
        }
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.Writer] */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.Reader] */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r4v2, types: [java.io.Reader] */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.io.Writer] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r4v4, types: [java.io.Reader] */
    /* JADX WARNING: type inference failed for: r1v5, types: [java.io.Writer] */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.io.BufferedWriter, java.io.Writer] */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r1v8, types: [java.io.Reader] */
    /* JADX WARNING: type inference failed for: r4v11, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r4v13 */
    /* JADX WARNING: type inference failed for: r4v14 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r1v16 */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: type inference failed for: r4v16 */
    /* JADX WARNING: type inference failed for: r1v17 */
    /* JADX WARNING: type inference failed for: r1v18 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r4v17 */
    /* JADX WARNING: type inference failed for: r4v18 */
    /* JADX WARNING: type inference failed for: r4v19 */
    /* JADX WARNING: type inference failed for: r4v20 */
    /* JADX WARNING: type inference failed for: r4v21 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v1
      assigns: []
      uses: []
      mth insns count: 102
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:33:0x00cc=Splitter:B:33:0x00cc, B:27:0x00ae=Splitter:B:27:0x00ae} */
    /* JADX WARNING: Unknown variable types count: 18 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.io.File r8) {
        /*
            r7 = this;
            java.lang.String r0 = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)
            r1 = 0
            java.io.BufferedWriter r2 = new java.io.BufferedWriter     // Catch:{ FileNotFoundException -> 0x00ca, IOException -> 0x00ac, all -> 0x00a9 }
            java.io.OutputStreamWriter r3 = new java.io.OutputStreamWriter     // Catch:{ FileNotFoundException -> 0x00ca, IOException -> 0x00ac, all -> 0x00a9 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00ca, IOException -> 0x00ac, all -> 0x00a9 }
            r4.<init>(r8)     // Catch:{ FileNotFoundException -> 0x00ca, IOException -> 0x00ac, all -> 0x00a9 }
            r3.<init>(r4)     // Catch:{ FileNotFoundException -> 0x00ca, IOException -> 0x00ac, all -> 0x00a9 }
            r2.<init>(r3)     // Catch:{ FileNotFoundException -> 0x00ca, IOException -> 0x00ac, all -> 0x00a9 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r8.<init>()     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = "model :"
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = android.os.Build.MODEL     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = "; os :"
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = android.os.Build.VERSION.INCREMENTAL     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = "; uid :"
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = com.xiaomi.push.service.ba.e()     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = "; lng :"
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.util.Locale r3 = java.util.Locale.getDefault()     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = r3.toString()     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = "; sdk :36"
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = "; andver :"
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r3 = "\n"
            r8.append(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.lang.String r8 = r8.toString()     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r2.write(r8)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r8 = 0
            r7.f = r8     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.util.ArrayList<java.io.File> r8 = r7.h     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
        L_0x006f:
            boolean r3 = r8.hasNext()     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            if (r3 == 0) goto L_0x0098
            java.lang.Object r3 = r8.next()     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.io.File r3 = (java.io.File) r3     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r6.<init>(r3)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r5.<init>(r6)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r4.<init>(r5)     // Catch:{ FileNotFoundException -> 0x00a5, IOException -> 0x00a1, all -> 0x009f }
            r7.a(r4, r2, r0)     // Catch:{ FileNotFoundException -> 0x0096, IOException -> 0x0094, all -> 0x0092 }
            r4.close()     // Catch:{ FileNotFoundException -> 0x0096, IOException -> 0x0094, all -> 0x0092 }
            r1 = r4
            goto L_0x006f
        L_0x0092:
            r8 = move-exception
            goto L_0x00ea
        L_0x0094:
            r8 = move-exception
            goto L_0x00a3
        L_0x0096:
            r8 = move-exception
            goto L_0x00a7
        L_0x0098:
            com.xiaomi.channel.commonutils.file.a.a(r2)
            com.xiaomi.channel.commonutils.file.a.a(r1)
            return
        L_0x009f:
            r8 = move-exception
            goto L_0x00eb
        L_0x00a1:
            r8 = move-exception
            r4 = r1
        L_0x00a3:
            r1 = r2
            goto L_0x00ae
        L_0x00a5:
            r8 = move-exception
            r4 = r1
        L_0x00a7:
            r1 = r2
            goto L_0x00cc
        L_0x00a9:
            r8 = move-exception
            r2 = r1
            goto L_0x00eb
        L_0x00ac:
            r8 = move-exception
            r4 = r1
        L_0x00ae:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e8 }
            java.lang.String r2 = "LOG: filter error = "
            r0.<init>(r2)     // Catch:{ all -> 0x00e8 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x00e8 }
            r0.append(r8)     // Catch:{ all -> 0x00e8 }
            java.lang.String r8 = r0.toString()     // Catch:{ all -> 0x00e8 }
            com.xiaomi.channel.commonutils.logger.b.c(r8)     // Catch:{ all -> 0x00e8 }
            com.xiaomi.channel.commonutils.file.a.a(r1)
            com.xiaomi.channel.commonutils.file.a.a(r4)
            return
        L_0x00ca:
            r8 = move-exception
            r4 = r1
        L_0x00cc:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e8 }
            java.lang.String r2 = "LOG: filter error = "
            r0.<init>(r2)     // Catch:{ all -> 0x00e8 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x00e8 }
            r0.append(r8)     // Catch:{ all -> 0x00e8 }
            java.lang.String r8 = r0.toString()     // Catch:{ all -> 0x00e8 }
            com.xiaomi.channel.commonutils.logger.b.c(r8)     // Catch:{ all -> 0x00e8 }
            com.xiaomi.channel.commonutils.file.a.a(r1)
            com.xiaomi.channel.commonutils.file.a.a(r4)
            return
        L_0x00e8:
            r8 = move-exception
            r2 = r1
        L_0x00ea:
            r1 = r4
        L_0x00eb:
            com.xiaomi.channel.commonutils.file.a.a(r2)
            com.xiaomi.channel.commonutils.file.a.a(r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.log.a.b(java.io.File):void");
    }

    /* access modifiers changed from: 0000 */
    public a a(File file) {
        if (file.exists()) {
            this.h.add(file);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public a a(Date date, Date date2) {
        String format;
        if (date.after(date2)) {
            this.c = this.a.format(date2);
            format = this.a.format(date);
        } else {
            this.c = this.a.format(date);
            format = this.a.format(date2);
        }
        this.d = format;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public File a(Context context, Date date, Date date2, File file) {
        File file2;
        if ("com.xiaomi.xmsf".equalsIgnoreCase(context.getPackageName())) {
            file2 = context.getFilesDir();
            a(new File(file2, "xmsf.log.1"));
            a(new File(file2, "xmsf.log"));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getExternalFilesDir(null));
            sb.append(b);
            File file3 = new File(sb.toString());
            a(new File(file3, "log0.txt"));
            a(new File(file3, "log1.txt"));
            file2 = file3;
        }
        if (!file2.isDirectory()) {
            return null;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(date.getTime());
        sb2.append("-");
        sb2.append(date2.getTime());
        sb2.append(FilePathHelper.SUFFIX_DOT_ZIP);
        File file4 = new File(file, sb2.toString());
        if (file4.exists()) {
            return null;
        }
        a(date, date2);
        long currentTimeMillis = System.currentTimeMillis();
        File file5 = new File(file, "log.txt");
        b(file5);
        StringBuilder sb3 = new StringBuilder("LOG: filter cost = ");
        sb3.append(System.currentTimeMillis() - currentTimeMillis);
        b.c(sb3.toString());
        if (file5.exists()) {
            long currentTimeMillis2 = System.currentTimeMillis();
            com.xiaomi.channel.commonutils.file.a.a(file4, file5);
            StringBuilder sb4 = new StringBuilder("LOG: zip cost = ");
            sb4.append(System.currentTimeMillis() - currentTimeMillis2);
            b.c(sb4.toString());
            file5.delete();
            if (file4.exists()) {
                return file4;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        if (i != 0) {
            this.g = i;
        }
    }
}
