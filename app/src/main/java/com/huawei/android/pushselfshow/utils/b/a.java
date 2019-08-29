package com.huawei.android.pushselfshow.utils.b;

import com.google.zxing.common.StringUtils;
import com.huawei.android.pushagent.a.a.c;
import java.io.File;

public class a {
    private String a;
    private String b;

    public a(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public static File a(String str, String str2) {
        String[] split = str2.split("/");
        File file = new File(str);
        int i = 0;
        while (i < split.length - 1) {
            try {
                i++;
                file = new File(file, new String(split[i].getBytes("8859_1"), StringUtils.GB2312));
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return file;
            }
        }
        c.a("PushSelfShowLog", "file1 = ".concat(String.valueOf(file)));
        if (!file.exists() && !file.mkdirs()) {
            c.a("PushSelfShowLog", "ret.mkdirs faild");
        }
        String str3 = new String(split[split.length - 1].getBytes("8859_1"), StringUtils.GB2312);
        c.a("PushSelfShowLog", "substr = ".concat(String.valueOf(str3)));
        File file2 = new File(file, str3);
        try {
            c.a("PushSelfShowLog", "file2 = ".concat(String.valueOf(file2)));
            return file2;
        } catch (Exception e2) {
            file = file2;
            e = e2;
            e.printStackTrace();
            return file;
        }
    }

    /* JADX WARNING: type inference failed for: r8v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r7v5, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v3, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r7v6 */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r8v4, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r7v7, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v5, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r7v9 */
    /* JADX WARNING: type inference failed for: r8v8 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: type inference failed for: r8v9, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r7v10, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v10, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: type inference failed for: r7v12 */
    /* JADX WARNING: type inference failed for: r8v13 */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r8v14, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r7v13, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v15, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r6v18 */
    /* JADX WARNING: type inference failed for: r7v15 */
    /* JADX WARNING: type inference failed for: r8v18 */
    /* JADX WARNING: type inference failed for: r6v19 */
    /* JADX WARNING: type inference failed for: r8v19 */
    /* JADX WARNING: type inference failed for: r7v16 */
    /* JADX WARNING: type inference failed for: r6v20 */
    /* JADX WARNING: type inference failed for: r6v21 */
    /* JADX WARNING: type inference failed for: r7v17 */
    /* JADX WARNING: type inference failed for: r8v20 */
    /* JADX WARNING: type inference failed for: r6v22 */
    /* JADX WARNING: type inference failed for: r6v24 */
    /* JADX WARNING: type inference failed for: r6v25 */
    /* JADX WARNING: type inference failed for: r6v26 */
    /* JADX WARNING: type inference failed for: r6v27 */
    /* JADX WARNING: type inference failed for: r7v18, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v28 */
    /* JADX WARNING: type inference failed for: r8v21 */
    /* JADX WARNING: type inference failed for: r6v29 */
    /* JADX WARNING: type inference failed for: r8v22 */
    /* JADX WARNING: type inference failed for: r6v30 */
    /* JADX WARNING: type inference failed for: r8v23 */
    /* JADX WARNING: type inference failed for: r6v31 */
    /* JADX WARNING: type inference failed for: r8v24 */
    /* JADX WARNING: type inference failed for: r6v32, types: [java.io.OutputStream, java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r8v25 */
    /* JADX WARNING: type inference failed for: r8v26 */
    /* JADX WARNING: type inference failed for: r8v27 */
    /* JADX WARNING: type inference failed for: r8v28 */
    /* JADX WARNING: type inference failed for: r8v29, types: [java.io.BufferedInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r8v39 */
    /* JADX WARNING: type inference failed for: r7v25 */
    /* JADX WARNING: type inference failed for: r6v42 */
    /* JADX WARNING: type inference failed for: r8v40 */
    /* JADX WARNING: type inference failed for: r8v41 */
    /* JADX WARNING: type inference failed for: r7v26 */
    /* JADX WARNING: type inference failed for: r7v27 */
    /* JADX WARNING: type inference failed for: r6v43 */
    /* JADX WARNING: type inference failed for: r6v44 */
    /* JADX WARNING: type inference failed for: r6v45 */
    /* JADX WARNING: type inference failed for: r7v28 */
    /* JADX WARNING: type inference failed for: r8v42 */
    /* JADX WARNING: type inference failed for: r8v43 */
    /* JADX WARNING: type inference failed for: r7v29 */
    /* JADX WARNING: type inference failed for: r7v30 */
    /* JADX WARNING: type inference failed for: r6v46 */
    /* JADX WARNING: type inference failed for: r6v47 */
    /* JADX WARNING: type inference failed for: r6v48 */
    /* JADX WARNING: type inference failed for: r7v31 */
    /* JADX WARNING: type inference failed for: r8v44 */
    /* JADX WARNING: type inference failed for: r8v45 */
    /* JADX WARNING: type inference failed for: r7v32 */
    /* JADX WARNING: type inference failed for: r7v33 */
    /* JADX WARNING: type inference failed for: r6v49 */
    /* JADX WARNING: type inference failed for: r6v50 */
    /* JADX WARNING: type inference failed for: r6v51 */
    /* JADX WARNING: type inference failed for: r7v34 */
    /* JADX WARNING: type inference failed for: r8v46 */
    /* JADX WARNING: type inference failed for: r7v35 */
    /* JADX WARNING: type inference failed for: r6v52 */
    /* JADX WARNING: type inference failed for: r6v53 */
    /* JADX WARNING: type inference failed for: r7v36 */
    /* JADX WARNING: type inference failed for: r7v37 */
    /* JADX WARNING: type inference failed for: r7v38 */
    /* JADX WARNING: type inference failed for: r7v39 */
    /* JADX WARNING: type inference failed for: r7v40 */
    /* JADX WARNING: type inference failed for: r7v41 */
    /* JADX WARNING: type inference failed for: r7v42 */
    /* JADX WARNING: type inference failed for: r7v43 */
    /* JADX WARNING: type inference failed for: r7v44 */
    /* JADX WARNING: type inference failed for: r7v45 */
    /* JADX WARNING: type inference failed for: r7v46 */
    /* JADX WARNING: type inference failed for: r7v47 */
    /* JADX WARNING: type inference failed for: r6v54 */
    /* JADX WARNING: type inference failed for: r6v55 */
    /* JADX WARNING: type inference failed for: r6v56 */
    /* JADX WARNING: type inference failed for: r6v57 */
    /* JADX WARNING: type inference failed for: r6v58 */
    /* JADX WARNING: type inference failed for: r6v59 */
    /* JADX WARNING: type inference failed for: r6v60 */
    /* JADX WARNING: type inference failed for: r8v47 */
    /* JADX WARNING: type inference failed for: r8v48 */
    /* JADX WARNING: type inference failed for: r8v49 */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0204, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:?, code lost:
        r10 = new java.lang.StringBuilder("zFileIn.close error:");
        r10.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r10.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:?, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0223, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
        r9 = new java.lang.StringBuilder("is.close error:");
        r9.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r9.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0260, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0261, code lost:
        r6 = "PushSelfShowLog";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
        r7 = new java.lang.StringBuilder("tempFOS.close error:");
        r7.append(r5.getMessage());
        r5 = r7.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0299, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:?, code lost:
        r10 = new java.lang.StringBuilder("zFileIn.close error:");
        r10.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r10.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x02b8, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:?, code lost:
        r9 = new java.lang.StringBuilder("is.close error:");
        r9.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r9.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x02d6, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:?, code lost:
        r8 = new java.lang.StringBuilder("os.close error:");
        r8.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r8.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x02f5, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x02f6, code lost:
        r6 = "PushSelfShowLog";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:?, code lost:
        r7 = new java.lang.StringBuilder("tempFOS.close error:");
        r7.append(r5.getMessage());
        r5 = r7.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x032e, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:?, code lost:
        r10 = new java.lang.StringBuilder("zFileIn.close error:");
        r10.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r10.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x034d, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:?, code lost:
        r9 = new java.lang.StringBuilder("is.close error:");
        r9.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r9.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:193:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x036b, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:?, code lost:
        r8 = new java.lang.StringBuilder("os.close error:");
        r8.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r8.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x038a, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x038b, code lost:
        r6 = "PushSelfShowLog";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:?, code lost:
        r7 = new java.lang.StringBuilder("tempFOS.close error:");
        r7.append(r5.getMessage());
        r5 = r7.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x03a8, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:?, code lost:
        r4 = new java.lang.StringBuilder("zFileIn.close error:");
        r4.append(r2.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r4.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x03c7, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:?, code lost:
        r4 = new java.lang.StringBuilder("is.close error:");
        r4.append(r2.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r4.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x03e5, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:?, code lost:
        r4 = new java.lang.StringBuilder("os.close error:");
        r4.append(r2.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r4.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x0403, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:?, code lost:
        r4 = new java.lang.StringBuilder("tempFOS.close error:");
        r4.append(r2.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r4.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x042d, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x042f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x0431, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x0434, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:253:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:254:0x045e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:0x045f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:0x0460, code lost:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder("zfile.close error:");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x046c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:0x048e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:267:0x048f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:268:0x0490, code lost:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder("zfile.close error:");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:0x049c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:289:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:290:0x04ee, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:291:0x04ef, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:292:0x04f0, code lost:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder("zfile.close error:");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:293:0x04fc, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0133, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r10 = new java.lang.StringBuilder("zFileIn.close error:");
        r10.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r10.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0150, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        r9 = new java.lang.StringBuilder("is.close error:");
        r9.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r9.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x016c, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        r8 = new java.lang.StringBuilder("os.close error:");
        r8.append(r5.getMessage());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", r8.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0189, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x018a, code lost:
        r6 = "PushSelfShowLog";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        r7 = new java.lang.StringBuilder("tempFOS.close error:");
        r7.append(r5.getMessage());
        r5 = r7.toString();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r8v3
      assigns: []
      uses: []
      mth insns count: 450
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
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0200 A[SYNTHETIC, Splitter:B:110:0x0200] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x021f A[SYNTHETIC, Splitter:B:117:0x021f] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x023d A[SYNTHETIC, Splitter:B:124:0x023d] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x025b A[SYNTHETIC, Splitter:B:131:0x025b] */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x0295 A[SYNTHETIC, Splitter:B:144:0x0295] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x02b4 A[SYNTHETIC, Splitter:B:151:0x02b4] */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x02d2 A[SYNTHETIC, Splitter:B:158:0x02d2] */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x02f0 A[SYNTHETIC, Splitter:B:165:0x02f0] */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x032a A[SYNTHETIC, Splitter:B:178:0x032a] */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x0349 A[SYNTHETIC, Splitter:B:185:0x0349] */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x0367 A[SYNTHETIC, Splitter:B:192:0x0367] */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0385 A[SYNTHETIC, Splitter:B:199:0x0385] */
    /* JADX WARNING: Removed duplicated region for block: B:207:0x03a4 A[SYNTHETIC, Splitter:B:207:0x03a4] */
    /* JADX WARNING: Removed duplicated region for block: B:214:0x03c3 A[SYNTHETIC, Splitter:B:214:0x03c3] */
    /* JADX WARNING: Removed duplicated region for block: B:221:0x03e1 A[SYNTHETIC, Splitter:B:221:0x03e1] */
    /* JADX WARNING: Removed duplicated region for block: B:228:0x03ff A[SYNTHETIC, Splitter:B:228:0x03ff] */
    /* JADX WARNING: Removed duplicated region for block: B:240:0x042d A[ExcHandler: NoSuchElementException (e java.util.NoSuchElementException), Splitter:B:65:0x0184] */
    /* JADX WARNING: Removed duplicated region for block: B:241:0x042f A[ExcHandler: IllegalStateException (e java.lang.IllegalStateException), Splitter:B:6:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:243:0x0434 A[ExcHandler: ZipException (e java.util.zip.ZipException), Splitter:B:6:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x045b A[SYNTHETIC, Splitter:B:252:0x045b] */
    /* JADX WARNING: Removed duplicated region for block: B:257:0x046c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:264:0x048b A[SYNTHETIC, Splitter:B:264:0x048b] */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x049c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:276:0x04bb A[SYNTHETIC, Splitter:B:276:0x04bb] */
    /* JADX WARNING: Removed duplicated region for block: B:281:0x04cc A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:288:0x04eb A[SYNTHETIC, Splitter:B:288:0x04eb] */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x04fc A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:296:0x0500 A[SYNTHETIC, Splitter:B:296:0x0500] */
    /* JADX WARNING: Removed duplicated region for block: B:306:0x0034 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x0034 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:310:0x0034 A[SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 33 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r14 = this;
            r0 = 0
            java.lang.String r1 = r14.b     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            java.lang.String r2 = "/"
            boolean r1 = r1.endsWith(r2)     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            if (r1 != 0) goto L_0x0020
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            r1.<init>()     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            java.lang.String r2 = r14.b     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            r1.append(r2)     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            java.lang.String r2 = "/"
            r1.append(r2)     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            java.lang.String r1 = r1.toString()     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            r14.b = r1     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
        L_0x0020:
            java.util.zip.ZipFile r1 = new java.util.zip.ZipFile     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            java.io.File r2 = new java.io.File     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            java.lang.String r3 = r14.a     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            r2.<init>(r3)     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            r1.<init>(r2)     // Catch:{ ZipException -> 0x04cd, IOException -> 0x049d, IllegalStateException -> 0x046d, NoSuchElementException -> 0x043d, all -> 0x0437 }
            java.util.Enumeration r2 = r1.entries()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r3]     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x0034:
            boolean r5 = r2.hasMoreElements()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            if (r5 == 0) goto L_0x041c
            java.lang.Object r5 = r2.nextElement()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.util.zip.ZipEntry r5 = (java.util.zip.ZipEntry) r5     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            boolean r6 = r5.isDirectory()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            if (r6 == 0) goto L_0x009a
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = "ze.getName() = "
            r7.<init>(r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = r5.getName()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r7.append(r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r7 = r7.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r6, r7)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r6.<init>()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r7 = r14.b     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r6.append(r7)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r7 = r5.getName()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r6.append(r7)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r6 = r6.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r7 = new java.lang.String     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = "8859_1"
            byte[] r6 = r6.getBytes(r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = "GB2312"
            r7.<init>(r6, r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.String r8 = "str = "
            java.lang.String r9 = java.lang.String.valueOf(r7)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = r8.concat(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r6, r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.io.File r6 = new java.io.File     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r6.<init>(r7)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            boolean r6 = r6.mkdir()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            if (r6 != 0) goto L_0x0034
        L_0x009a:
            java.lang.String r6 = r14.b     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r7 = r5.getName()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.io.File r6 = a(r6, r7)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            boolean r7 = r6.isDirectory()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            if (r7 == 0) goto L_0x00c8
            r1.close()     // Catch:{ IOException -> 0x00ae }
            return
        L_0x00ae:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "zfile.close error:"
            r2.<init>(r3)
        L_0x00b9:
            java.lang.String r0 = r0.getMessage()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            com.huawei.android.pushagent.a.a.c.a(r1, r0)
            return
        L_0x00c8:
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r9 = "ze.getName() = "
            r8.<init>(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r9 = r5.getName()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r8.append(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r9 = ",output file :"
            r8.append(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r9 = r6.getAbsolutePath()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r8.append(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = r8.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r7, r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r7 = r5.getName()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            if (r7 == 0) goto L_0x010e
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r2 = "ze.getName() is empty= "
            com.huawei.android.pushagent.a.a.c.a(r0, r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r1.close()     // Catch:{ IOException -> 0x0102 }
            return
        L_0x0102:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "zfile.close error:"
            r2.<init>(r3)
            goto L_0x00b9
        L_0x010e:
            java.io.InputStream r5 = r1.getInputStream(r5)     // Catch:{ IOException -> 0x030c, IllegalStateException -> 0x0277, IndexOutOfBoundsException -> 0x01e2, all -> 0x01da }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x01d6, IllegalStateException -> 0x01d2, IndexOutOfBoundsException -> 0x01cf, all -> 0x01cc }
            r7.<init>(r6)     // Catch:{ IOException -> 0x01d6, IllegalStateException -> 0x01d2, IndexOutOfBoundsException -> 0x01cf, all -> 0x01cc }
            java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x01c7, IllegalStateException -> 0x01c2, IndexOutOfBoundsException -> 0x01be, all -> 0x01ba }
            r6.<init>(r7)     // Catch:{ IOException -> 0x01c7, IllegalStateException -> 0x01c2, IndexOutOfBoundsException -> 0x01be, all -> 0x01ba }
            java.io.BufferedInputStream r8 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x01b6, IllegalStateException -> 0x01b2, IndexOutOfBoundsException -> 0x01af, all -> 0x01ac }
            r8.<init>(r5)     // Catch:{ IOException -> 0x01b6, IllegalStateException -> 0x01b2, IndexOutOfBoundsException -> 0x01af, all -> 0x01ac }
        L_0x0121:
            r9 = 0
            int r10 = r8.read(r4, r9, r3)     // Catch:{ IOException -> 0x01a9, IllegalStateException -> 0x01a6, IndexOutOfBoundsException -> 0x01a3 }
            r11 = -1
            if (r10 == r11) goto L_0x012d
            r6.write(r4, r9, r10)     // Catch:{ IOException -> 0x01a9, IllegalStateException -> 0x01a6, IndexOutOfBoundsException -> 0x01a3 }
            goto L_0x0121
        L_0x012d:
            if (r5 == 0) goto L_0x014c
            r5.close()     // Catch:{ IOException -> 0x0133, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x014c
        L_0x0133:
            r5 = move-exception
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r11 = "zFileIn.close error:"
            r10.<init>(r11)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r10.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r10.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r9, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x014c:
            r8.close()     // Catch:{ IOException -> 0x0150, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0168
        L_0x0150:
            r5 = move-exception
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r10 = "is.close error:"
            r9.<init>(r10)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r9.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r9.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r8, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x0168:
            r6.close()     // Catch:{ IOException -> 0x016c, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0184
        L_0x016c:
            r5 = move-exception
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r9 = "os.close error:"
            r8.<init>(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r8.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r8.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r6, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x0184:
            r7.close()     // Catch:{ IOException -> 0x0189, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0034
        L_0x0189:
            r5 = move-exception
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = "tempFOS.close error:"
            r7.<init>(r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r7.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r7.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x019e:
            com.huawei.android.pushagent.a.a.c.a(r6, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0034
        L_0x01a3:
            r9 = move-exception
            goto L_0x01e7
        L_0x01a6:
            r9 = move-exception
            goto L_0x027c
        L_0x01a9:
            r9 = move-exception
            goto L_0x0311
        L_0x01ac:
            r2 = move-exception
            r8 = r0
            goto L_0x01df
        L_0x01af:
            r9 = move-exception
            r8 = r0
            goto L_0x01e7
        L_0x01b2:
            r9 = move-exception
            r8 = r0
            goto L_0x027c
        L_0x01b6:
            r9 = move-exception
            r8 = r0
            goto L_0x0311
        L_0x01ba:
            r2 = move-exception
            r6 = r0
            r8 = r6
            goto L_0x01df
        L_0x01be:
            r9 = move-exception
            r6 = r0
            r8 = r6
            goto L_0x01e7
        L_0x01c2:
            r9 = move-exception
            r6 = r0
            r8 = r6
            goto L_0x027c
        L_0x01c7:
            r9 = move-exception
            r6 = r0
            r8 = r6
            goto L_0x0311
        L_0x01cc:
            r2 = move-exception
            r6 = r0
            goto L_0x01dd
        L_0x01cf:
            r9 = move-exception
            r6 = r0
            goto L_0x01e5
        L_0x01d2:
            r9 = move-exception
            r6 = r0
            goto L_0x027a
        L_0x01d6:
            r9 = move-exception
            r6 = r0
            goto L_0x030f
        L_0x01da:
            r2 = move-exception
            r5 = r0
            r6 = r5
        L_0x01dd:
            r7 = r6
            r8 = r7
        L_0x01df:
            r0 = r2
            goto L_0x03a2
        L_0x01e2:
            r9 = move-exception
            r5 = r0
            r6 = r5
        L_0x01e5:
            r7 = r6
            r8 = r7
        L_0x01e7:
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x03a1 }
            java.lang.String r12 = "os.write error:"
            r11.<init>(r12)     // Catch:{ all -> 0x03a1 }
            java.lang.String r9 = r9.getMessage()     // Catch:{ all -> 0x03a1 }
            r11.append(r9)     // Catch:{ all -> 0x03a1 }
            java.lang.String r9 = r11.toString()     // Catch:{ all -> 0x03a1 }
            com.huawei.android.pushagent.a.a.c.a(r10, r9)     // Catch:{ all -> 0x03a1 }
            if (r5 == 0) goto L_0x021d
            r5.close()     // Catch:{ IOException -> 0x0204, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x021d
        L_0x0204:
            r5 = move-exception
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r11 = "zFileIn.close error:"
            r10.<init>(r11)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r10.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r10.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r9, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x021d:
            if (r8 == 0) goto L_0x023b
            r8.close()     // Catch:{ IOException -> 0x0223, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x023b
        L_0x0223:
            r5 = move-exception
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r10 = "is.close error:"
            r9.<init>(r10)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r9.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r9.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r8, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x023b:
            if (r6 == 0) goto L_0x0259
            r6.close()     // Catch:{ IOException -> 0x0241, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0259
        L_0x0241:
            r5 = move-exception
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r9 = "os.close error:"
            r8.<init>(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r8.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r8.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r6, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x0259:
            if (r7 == 0) goto L_0x0034
            r7.close()     // Catch:{ IOException -> 0x0260, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0034
        L_0x0260:
            r5 = move-exception
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = "tempFOS.close error:"
            r7.<init>(r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r7.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r7.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x019e
        L_0x0277:
            r9 = move-exception
            r5 = r0
            r6 = r5
        L_0x027a:
            r7 = r6
            r8 = r7
        L_0x027c:
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x03a1 }
            java.lang.String r12 = "os.write error:"
            r11.<init>(r12)     // Catch:{ all -> 0x03a1 }
            java.lang.String r9 = r9.getMessage()     // Catch:{ all -> 0x03a1 }
            r11.append(r9)     // Catch:{ all -> 0x03a1 }
            java.lang.String r9 = r11.toString()     // Catch:{ all -> 0x03a1 }
            com.huawei.android.pushagent.a.a.c.a(r10, r9)     // Catch:{ all -> 0x03a1 }
            if (r5 == 0) goto L_0x02b2
            r5.close()     // Catch:{ IOException -> 0x0299, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x02b2
        L_0x0299:
            r5 = move-exception
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r11 = "zFileIn.close error:"
            r10.<init>(r11)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r10.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r10.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r9, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x02b2:
            if (r8 == 0) goto L_0x02d0
            r8.close()     // Catch:{ IOException -> 0x02b8, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x02d0
        L_0x02b8:
            r5 = move-exception
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r10 = "is.close error:"
            r9.<init>(r10)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r9.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r9.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r8, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x02d0:
            if (r6 == 0) goto L_0x02ee
            r6.close()     // Catch:{ IOException -> 0x02d6, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x02ee
        L_0x02d6:
            r5 = move-exception
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r9 = "os.close error:"
            r8.<init>(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r8.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r8.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r6, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x02ee:
            if (r7 == 0) goto L_0x0034
            r7.close()     // Catch:{ IOException -> 0x02f5, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0034
        L_0x02f5:
            r5 = move-exception
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = "tempFOS.close error:"
            r7.<init>(r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r7.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r7.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x019e
        L_0x030c:
            r9 = move-exception
            r5 = r0
            r6 = r5
        L_0x030f:
            r7 = r6
            r8 = r7
        L_0x0311:
            java.lang.String r10 = "PushSelfShowLog"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x03a1 }
            java.lang.String r12 = "os.write error:"
            r11.<init>(r12)     // Catch:{ all -> 0x03a1 }
            java.lang.String r9 = r9.getMessage()     // Catch:{ all -> 0x03a1 }
            r11.append(r9)     // Catch:{ all -> 0x03a1 }
            java.lang.String r9 = r11.toString()     // Catch:{ all -> 0x03a1 }
            com.huawei.android.pushagent.a.a.c.a(r10, r9)     // Catch:{ all -> 0x03a1 }
            if (r5 == 0) goto L_0x0347
            r5.close()     // Catch:{ IOException -> 0x032e, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0347
        L_0x032e:
            r5 = move-exception
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r11 = "zFileIn.close error:"
            r10.<init>(r11)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r10.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r10.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r9, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x0347:
            if (r8 == 0) goto L_0x0365
            r8.close()     // Catch:{ IOException -> 0x034d, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0365
        L_0x034d:
            r5 = move-exception
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r10 = "is.close error:"
            r9.<init>(r10)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r9.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r9.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r8, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x0365:
            if (r6 == 0) goto L_0x0383
            r6.close()     // Catch:{ IOException -> 0x036b, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0383
        L_0x036b:
            r5 = move-exception
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r9 = "os.close error:"
            r8.<init>(r9)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r8.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r8.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r6, r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x0383:
            if (r7 == 0) goto L_0x0034
            r7.close()     // Catch:{ IOException -> 0x038a, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x0034
        L_0x038a:
            r5 = move-exception
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r8 = "tempFOS.close error:"
            r7.<init>(r8)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r5.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r7.append(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = r7.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x019e
        L_0x03a1:
            r0 = move-exception
        L_0x03a2:
            if (r5 == 0) goto L_0x03c1
            r5.close()     // Catch:{ IOException -> 0x03a8, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x03c1
        L_0x03a8:
            r2 = move-exception
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = "zFileIn.close error:"
            r4.<init>(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r2 = r2.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r4.append(r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r2 = r4.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r3, r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x03c1:
            if (r8 == 0) goto L_0x03df
            r8.close()     // Catch:{ IOException -> 0x03c7, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x03df
        L_0x03c7:
            r2 = move-exception
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = "is.close error:"
            r4.<init>(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r2 = r2.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r4.append(r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r2 = r4.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r3, r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x03df:
            if (r6 == 0) goto L_0x03fd
            r6.close()     // Catch:{ IOException -> 0x03e5, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x03fd
        L_0x03e5:
            r2 = move-exception
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = "os.close error:"
            r4.<init>(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r2 = r2.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r4.append(r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r2 = r4.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r3, r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x03fd:
            if (r7 == 0) goto L_0x041b
            r7.close()     // Catch:{ IOException -> 0x0403, ZipException -> 0x0434, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            goto L_0x041b
        L_0x0403:
            r2 = move-exception
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r5 = "tempFOS.close error:"
            r4.<init>(r5)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r2 = r2.getMessage()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            r4.append(r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            java.lang.String r2 = r4.toString()     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
            com.huawei.android.pushagent.a.a.c.a(r3, r2)     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x041b:
            throw r0     // Catch:{ ZipException -> 0x0434, IOException -> 0x0431, IllegalStateException -> 0x042f, NoSuchElementException -> 0x042d }
        L_0x041c:
            r1.close()     // Catch:{ IOException -> 0x0420 }
            return
        L_0x0420:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "zfile.close error:"
            r2.<init>(r3)
            goto L_0x00b9
        L_0x042d:
            r0 = move-exception
            goto L_0x0441
        L_0x042f:
            r0 = move-exception
            goto L_0x0471
        L_0x0431:
            r0 = move-exception
            goto L_0x04a1
        L_0x0434:
            r0 = move-exception
            goto L_0x04d1
        L_0x0437:
            r1 = move-exception
            r13 = r1
            r1 = r0
            r0 = r13
            goto L_0x04fe
        L_0x043d:
            r1 = move-exception
            r13 = r1
            r1 = r0
            r0 = r13
        L_0x0441:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x04fd }
            java.lang.String r4 = "upZipFile error:"
            r3.<init>(r4)     // Catch:{ all -> 0x04fd }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x04fd }
            r3.append(r0)     // Catch:{ all -> 0x04fd }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x04fd }
            com.huawei.android.pushagent.a.a.c.a(r2, r0)     // Catch:{ all -> 0x04fd }
            if (r1 == 0) goto L_0x046c
            r1.close()     // Catch:{ IOException -> 0x045f }
            return
        L_0x045f:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "zfile.close error:"
            r2.<init>(r3)
            goto L_0x00b9
        L_0x046c:
            return
        L_0x046d:
            r1 = move-exception
            r13 = r1
            r1 = r0
            r0 = r13
        L_0x0471:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x04fd }
            java.lang.String r4 = "upZipFile error:"
            r3.<init>(r4)     // Catch:{ all -> 0x04fd }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x04fd }
            r3.append(r0)     // Catch:{ all -> 0x04fd }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x04fd }
            com.huawei.android.pushagent.a.a.c.a(r2, r0)     // Catch:{ all -> 0x04fd }
            if (r1 == 0) goto L_0x049c
            r1.close()     // Catch:{ IOException -> 0x048f }
            return
        L_0x048f:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "zfile.close error:"
            r2.<init>(r3)
            goto L_0x00b9
        L_0x049c:
            return
        L_0x049d:
            r1 = move-exception
            r13 = r1
            r1 = r0
            r0 = r13
        L_0x04a1:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x04fd }
            java.lang.String r4 = "upZipFile error:"
            r3.<init>(r4)     // Catch:{ all -> 0x04fd }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x04fd }
            r3.append(r0)     // Catch:{ all -> 0x04fd }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x04fd }
            com.huawei.android.pushagent.a.a.c.a(r2, r0)     // Catch:{ all -> 0x04fd }
            if (r1 == 0) goto L_0x04cc
            r1.close()     // Catch:{ IOException -> 0x04bf }
            return
        L_0x04bf:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "zfile.close error:"
            r2.<init>(r3)
            goto L_0x00b9
        L_0x04cc:
            return
        L_0x04cd:
            r1 = move-exception
            r13 = r1
            r1 = r0
            r0 = r13
        L_0x04d1:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x04fd }
            java.lang.String r4 = "upZipFile error:"
            r3.<init>(r4)     // Catch:{ all -> 0x04fd }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x04fd }
            r3.append(r0)     // Catch:{ all -> 0x04fd }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x04fd }
            com.huawei.android.pushagent.a.a.c.a(r2, r0)     // Catch:{ all -> 0x04fd }
            if (r1 == 0) goto L_0x04fc
            r1.close()     // Catch:{ IOException -> 0x04ef }
            return
        L_0x04ef:
            r0 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "zfile.close error:"
            r2.<init>(r3)
            goto L_0x00b9
        L_0x04fc:
            return
        L_0x04fd:
            r0 = move-exception
        L_0x04fe:
            if (r1 == 0) goto L_0x051d
            r1.close()     // Catch:{ IOException -> 0x0504 }
            goto L_0x051d
        L_0x0504:
            r1 = move-exception
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "zfile.close error:"
            r3.<init>(r4)
            java.lang.String r1 = r1.getMessage()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            com.huawei.android.pushagent.a.a.c.a(r2, r1)
        L_0x051d:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.b.a.a():void");
    }
}
