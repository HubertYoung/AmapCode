package defpackage;

import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* renamed from: afn reason: default package */
/* compiled from: LogUploadUtil */
public final class afn {
    private static volatile afn b;
    public Context a = null;
    private Thread c;
    private a d;

    /* renamed from: afn$a */
    /* compiled from: LogUploadUtil */
    class a implements Runnable {
        File a;
        int b;

        a() {
        }

        public final void run() {
            afn afn = afn.this;
            File file = this.a;
            int i = this.b;
            if (file != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(file.getParent());
                sb.append("/");
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(UUID.randomUUID().toString());
                sb3.append(FilePathHelper.SUFFIX_DOT_ZIP);
                File file2 = new File(sb3.toString());
                try {
                    afn.a(file2, file, afn.a);
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String a2 = agy.a(file2, null, true);
                if (a2 != null) {
                    a2 = a2.toUpperCase(Locale.getDefault());
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append(Environment.getExternalStorageDirectory().getPath());
                sb4.append("/autonavi/copy.zip");
                afn.a(file2, new File(sb4.toString()));
                new afp(afn.a, file2, a2, i).start();
            }
        }
    }

    public final synchronized void a(File file) {
        this.d = new a();
        a aVar = this.d;
        aVar.a = file;
        aVar.b = 4;
        this.c = new Thread(this.d);
        this.c.start();
    }

    public static synchronized afn a() {
        afn afn;
        synchronized (afn.class) {
            try {
                if (b == null) {
                    b = new afn();
                }
                afn = b;
            }
        }
        return afn;
    }

    private afn() {
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r4v6, types: [java.io.Closeable, java.io.FileInputStream] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v7, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v2
      assigns: []
      uses: []
      mth insns count: 43
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
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 11 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.io.File r4, java.io.File r5) {
        /*
            r3 = this;
            r0 = 0
            android.content.Context r1 = r3.a     // Catch:{ Exception -> 0x0034, all -> 0x0031 }
            java.lang.String r4 = r4.getName()     // Catch:{ Exception -> 0x0034, all -> 0x0031 }
            java.io.FileInputStream r4 = r1.openFileInput(r4)     // Catch:{ Exception -> 0x0034, all -> 0x0031 }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x002d, all -> 0x002b }
            r1.<init>(r5)     // Catch:{ Exception -> 0x002d, all -> 0x002b }
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x0029, all -> 0x0027 }
        L_0x0014:
            int r0 = r4.read(r5)     // Catch:{ Exception -> 0x0029, all -> 0x0027 }
            r2 = -1
            if (r0 == r2) goto L_0x0020
            r2 = 0
            r1.write(r5, r2, r0)     // Catch:{ Exception -> 0x0029, all -> 0x0027 }
            goto L_0x0014
        L_0x0020:
            defpackage.ahe.a(r4)
        L_0x0023:
            defpackage.ahe.a(r1)
            return
        L_0x0027:
            r5 = move-exception
            goto L_0x003f
        L_0x0029:
            r5 = move-exception
            goto L_0x002f
        L_0x002b:
            r5 = move-exception
            goto L_0x0040
        L_0x002d:
            r5 = move-exception
            r1 = r0
        L_0x002f:
            r0 = r4
            goto L_0x0036
        L_0x0031:
            r5 = move-exception
            r4 = r0
            goto L_0x0040
        L_0x0034:
            r5 = move-exception
            r1 = r0
        L_0x0036:
            r5.printStackTrace()     // Catch:{ all -> 0x003d }
            defpackage.ahe.a(r0)
            goto L_0x0023
        L_0x003d:
            r5 = move-exception
            r4 = r0
        L_0x003f:
            r0 = r1
        L_0x0040:
            defpackage.ahe.a(r4)
            defpackage.ahe.a(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.afn.a(java.io.File, java.io.File):void");
    }

    public final File b(File file) {
        StringBuilder sb = new StringBuilder();
        sb.append(file.getParent());
        sb.append("/");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(UUID.randomUUID().toString());
        sb3.append(FilePathHelper.SUFFIX_DOT_ZIP);
        File file2 = new File(sb3.toString());
        try {
            a(file2, file, this.a);
            file.delete();
            return file2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x004d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.io.File r5, int r6, com.autonavi.common.Callback<java.lang.Integer> r7) {
        /*
            r4 = this;
            boolean r0 = r5.exists()
            if (r0 == 0) goto L_0x0083
            long r0 = r5.length()
            r2 = 1
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x0083
            boolean r0 = b()
            if (r0 == 0) goto L_0x0027
            com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger r0 = com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger.getInst()
            boolean r0 = r0.isLocalLogActive()
            if (r0 == 0) goto L_0x0027
            r0 = 4
            if (r6 == r0) goto L_0x0027
            r5.delete()
            return
        L_0x0027:
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x003e }
            r1.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x003e }
            r2 = 50
            byte[] r2 = new byte[r2]     // Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x003e }
            r1.read(r2)     // Catch:{ FileNotFoundException -> 0x003b, IOException -> 0x0038 }
            r1.close()     // Catch:{ FileNotFoundException -> 0x003b, IOException -> 0x0038 }
            goto L_0x0049
        L_0x0038:
            r0 = move-exception
            r1 = r0
            goto L_0x0040
        L_0x003b:
            r0 = move-exception
            r1 = r0
            goto L_0x0046
        L_0x003e:
            r1 = move-exception
            r2 = r0
        L_0x0040:
            r1.printStackTrace()
            goto L_0x0049
        L_0x0044:
            r1 = move-exception
            r2 = r0
        L_0x0046:
            r1.printStackTrace()
        L_0x0049:
            java.lang.String r0 = ""
            if (r2 == 0) goto L_0x0051
            java.lang.String r0 = defpackage.agx.a(r2)
        L_0x0051:
            afo r1 = new afo
            r1.<init>(r6, r0)
            java.lang.String r6 = r1.a()
            com.amap.bundle.aosservice.request.AosFileUploadRequest r0 = new com.amap.bundle.aosservice.request.AosFileUploadRequest
            r0.<init>()
            r0.setUrl(r6)
            r6 = 1
            r0.setWithoutSign(r6)
            r6 = -1
            r0.setCommonParamStrategy(r6)
            r0.a(r5)
            java.lang.String r6 = r5.getName()
            java.lang.String r6 = a(r6)
            r0.a(r6)
            defpackage.yq.a()
            com.amap.bundle.statistics.util.LogUploadUtil$1 r6 = new com.amap.bundle.statistics.util.LogUploadUtil$1
            r6.<init>(r4, r7, r5)
            defpackage.yq.a(r0, r6)
        L_0x0083:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.afn.a(java.io.File, int, com.autonavi.common.Callback):void");
    }

    private static String a(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return FilePart.DEFAULT_CONTENT_TYPE;
        }
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(lastIndexOf + 1));
    }

    private static boolean b() {
        String b2 = lt.a().b((String) "");
        return "1".equals(b2) || "".equals(b2);
    }

    static void a(File file, File file2, Context context) throws Exception {
        if (file2.isFile()) {
            ZipOutputStream zipOutputStream = new ZipOutputStream(context.openFileOutput(file.getName(), 0));
            zipOutputStream.putNextEntry(new ZipEntry(file2.getName()));
            FileInputStream openFileInput = context.openFileInput(file2.getName());
            byte[] bArr = new byte[2048];
            while (true) {
                int read = openFileInput.read(bArr);
                if (read != -1) {
                    zipOutputStream.write(bArr, 0, read);
                } else {
                    openFileInput.close();
                    zipOutputStream.close();
                    return;
                }
            }
        }
    }
}
