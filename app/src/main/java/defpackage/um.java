package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.dumpcrash.installerror.ErrorSoUploader$2;
import com.amap.bundle.network.util.NetworkReachability;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: um reason: default package */
/* compiled from: ErrorSoUploader */
public final class um {

    /* renamed from: um$a */
    /* compiled from: ErrorSoUploader */
    static class a {
        long a;
        File b;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    /* renamed from: um$b */
    /* compiled from: ErrorSoUploader */
    public static class b {
        String a;
        Map<String, String> b;

        public b(String str, Map<String, String> map) {
            this.a = str;
            this.b = map;
        }
    }

    public final Thread a(final defpackage.ul.a aVar) {
        final File file = new File(FileUtil.getAppSDCardFileDir(), "uploadsoerr");
        Thread thread = new Thread(new Runnable() {
            public final void run() {
                try {
                    File file = null;
                    if (NetworkReachability.a()) {
                        File file2 = new File(file, "errorsoupload.jpg");
                        if (!file2.getParentFile().exists()) {
                            file2.getParentFile().mkdirs();
                        }
                        if (um.this.a(file, file2)) {
                            file = file2;
                        }
                    }
                    if (file == null) {
                        if (aVar != null) {
                            aVar.a();
                        }
                        return;
                    }
                    um.a(um.this, file, file, aVar);
                } catch (Throwable th) {
                    th.printStackTrace();
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            }
        }, "uploadEso");
        thread.start();
        return thread;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:36|(2:42|43)|44|45) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x00b5 */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00b2 A[SYNTHETIC, Splitter:B:42:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00b8 A[SYNTHETIC, Splitter:B:48:0x00b8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(java.io.File r10, java.io.File r11) {
        /*
            r9 = this;
            r0 = 0
            if (r10 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.io.File[] r10 = r10.listFiles()     // Catch:{ Throwable -> 0x00bc }
            java.util.TreeSet r1 = new java.util.TreeSet     // Catch:{ Throwable -> 0x00bc }
            um$2 r2 = new um$2     // Catch:{ Throwable -> 0x00bc }
            r2.<init>()     // Catch:{ Throwable -> 0x00bc }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x00bc }
            int r2 = r10.length     // Catch:{ Throwable -> 0x00bc }
            r3 = 0
        L_0x0014:
            if (r3 >= r2) goto L_0x0043
            r4 = r10[r3]     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r5 = r4.getPath()     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r6 = ".so"
            boolean r5 = r5.endsWith(r6)     // Catch:{ Throwable -> 0x00bc }
            if (r5 != 0) goto L_0x0030
            java.lang.String r5 = r4.getPath()     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r6 = ".txt"
            boolean r5 = r5.endsWith(r6)     // Catch:{ Throwable -> 0x00bc }
            if (r5 == 0) goto L_0x0040
        L_0x0030:
            um$a r5 = new um$a     // Catch:{ Throwable -> 0x00bc }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x00bc }
            r5.b = r4     // Catch:{ Throwable -> 0x00bc }
            long r6 = r4.length()     // Catch:{ Throwable -> 0x00bc }
            r5.a = r6     // Catch:{ Throwable -> 0x00bc }
            r1.add(r5)     // Catch:{ Throwable -> 0x00bc }
        L_0x0040:
            int r3 = r3 + 1
            goto L_0x0014
        L_0x0043:
            int r10 = r1.size()     // Catch:{ Throwable -> 0x00bc }
            if (r10 > 0) goto L_0x004a
            return r0
        L_0x004a:
            r10 = 0
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch:{ Throwable -> 0x00b6, all -> 0x00ad }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x00b6, all -> 0x00ad }
            r3.<init>(r11, r0)     // Catch:{ Throwable -> 0x00b6, all -> 0x00ad }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00b6, all -> 0x00ad }
            r10 = 1024(0x400, float:1.435E-42)
            byte[] r11 = new byte[r10]     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r3 = 0
        L_0x005e:
            boolean r4 = r1.hasNext()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            if (r4 == 0) goto L_0x00a1
            java.lang.Object r4 = r1.next()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            um$a r4 = (defpackage.um.a) r4     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            long r5 = (long) r3     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            long r7 = r4.a     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r3 = 0
            long r5 = r5 + r7
            int r3 = (int) r5     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            long r5 = (long) r3     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r7 = 2097152(0x200000, double:1.0361308E-317)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 > 0) goto L_0x00a1
            java.io.File r4 = r4.b     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r6.<init>(r4)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r5.<init>(r6)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            java.util.zip.ZipEntry r6 = new java.util.zip.ZipEntry     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            java.lang.String r4 = r4.getName()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r6.<init>(r4)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r2.putNextEntry(r6)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
        L_0x0090:
            int r4 = r5.read(r11, r0, r10)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            if (r4 <= 0) goto L_0x009a
            r2.write(r11, r0, r4)     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            goto L_0x0090
        L_0x009a:
            r2.closeEntry()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r5.close()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            goto L_0x005e
        L_0x00a1:
            r2.flush()     // Catch:{ Throwable -> 0x00ab, all -> 0x00a9 }
            r2.close()     // Catch:{ IOException -> 0x00a7 }
        L_0x00a7:
            r10 = 1
            return r10
        L_0x00a9:
            r10 = move-exception
            goto L_0x00b0
        L_0x00ab:
            r10 = r2
            goto L_0x00b6
        L_0x00ad:
            r11 = move-exception
            r2 = r10
            r10 = r11
        L_0x00b0:
            if (r2 == 0) goto L_0x00b5
            r2.close()     // Catch:{ IOException -> 0x00b5 }
        L_0x00b5:
            throw r10     // Catch:{ Throwable -> 0x00bc }
        L_0x00b6:
            if (r10 == 0) goto L_0x00bb
            r10.close()     // Catch:{ IOException -> 0x00bb }
        L_0x00bb:
            return r0
        L_0x00bc:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.um.a(java.io.File, java.io.File):boolean");
    }

    static /* synthetic */ void a(um umVar, File file, File file2, defpackage.ul.a aVar) {
        HashMap hashMap = new HashMap();
        hashMap.put("sourcepage", "13");
        hashMap.put("errortype", "0");
        hashMap.put(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, "代码错误");
        hashMap.put("error_id", "1309");
        b a2 = ul.a.a(hashMap);
        AosMultipartRequest aosMultipartRequest = new AosMultipartRequest();
        aosMultipartRequest.setUrl(a2.a);
        aosMultipartRequest.addReqParams(a2.b);
        aosMultipartRequest.setPriority(150);
        ArrayList arrayList = new ArrayList();
        arrayList.add(file.getPath());
        HashMap hashMap2 = new HashMap();
        if (arrayList.size() > 0) {
            String str = "";
            for (int i = 0; i < arrayList.size(); i++) {
                if (!TextUtils.isEmpty((CharSequence) arrayList.get(i))) {
                    File file3 = new File((String) arrayList.get(i));
                    if (i != 0) {
                        str = String.valueOf(i);
                    }
                    hashMap2.put(SocialConstants.PARAM_AVATAR_URI.concat(String.valueOf(str)), file3);
                }
            }
        }
        if (hashMap2.size() > 0) {
            for (Entry entry : hashMap2.entrySet()) {
                if (entry.getValue() != null && ((File) entry.getValue()).exists()) {
                    aosMultipartRequest.a((String) entry.getKey(), (File) entry.getValue());
                }
            }
        }
        aosMultipartRequest.addSignParam("type");
        aosMultipartRequest.addSignParam("description");
        yq.a();
        yq.a((AosRequest) aosMultipartRequest, (AosResponseCallback<T>) new ErrorSoUploader$2<T>(umVar, file2, aVar));
    }
}
