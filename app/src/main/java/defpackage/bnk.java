package defpackage;

import android.text.TextUtils;
import com.autonavi.common.tool.CrashLogUtil;
import java.io.File;

/* renamed from: bnk reason: default package */
/* compiled from: OpenUploadFile */
public final class bnk extends bnj {
    protected bnk(File file, File[] fileArr, bmt bmt) {
        super(file, fileArr, bmt);
    }

    public final void a() {
        if (b()) {
            int I = this.c.I();
            String J = this.c.J();
            String K = this.c.K();
            if (!TextUtils.isEmpty(J) && !TextUtils.isEmpty(K)) {
                bnf.a(I, J, K, "1", "1", this.a, new bmy() {
                    /* JADX WARNING: Removed duplicated region for block: B:34:0x0063 A[SYNTHETIC, Splitter:B:34:0x0063] */
                    /* JADX WARNING: Removed duplicated region for block: B:40:0x006a A[SYNTHETIC, Splitter:B:40:0x006a] */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void a(java.net.HttpURLConnection r7) throws java.lang.Throwable {
                        /*
                            r6 = this;
                            int r0 = r7.getResponseCode()
                            r1 = 200(0xc8, float:2.8E-43)
                            if (r0 != r1) goto L_0x006e
                            r0 = 500(0x1f4, float:7.0E-43)
                            byte[] r0 = new byte[r0]
                            r1 = 0
                            java.io.InputStream r7 = r7.getInputStream()     // Catch:{ Exception -> 0x0054 }
                            int r2 = r7.read(r0)     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            if (r2 > 0) goto L_0x0021
                            r6.a(r1)     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            if (r7 == 0) goto L_0x0020
                            r7.close()     // Catch:{ Exception -> 0x0020 }
                            return
                        L_0x0020:
                            return
                        L_0x0021:
                            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            r4 = 0
                            java.lang.String r5 = "utf-8"
                            r3.<init>(r0, r4, r2, r5)     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            r0.<init>(r3)     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            java.lang.String r2 = "code"
                            int r0 = r0.optInt(r2)     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            r2 = 1
                            if (r0 != r2) goto L_0x0043
                            bnk r0 = defpackage.bnk.this     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            java.io.File[] r0 = r0.b     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            defpackage.bnk.a(r0)     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            bnk r0 = defpackage.bnk.this     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            r0.d = r2     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                            goto L_0x0046
                        L_0x0043:
                            r6.a(r1)     // Catch:{ Exception -> 0x004e, all -> 0x004c }
                        L_0x0046:
                            if (r7 == 0) goto L_0x0067
                            r7.close()     // Catch:{ Exception -> 0x004b }
                        L_0x004b:
                            return
                        L_0x004c:
                            r0 = move-exception
                            goto L_0x0068
                        L_0x004e:
                            r0 = move-exception
                            r1 = r7
                            goto L_0x0055
                        L_0x0051:
                            r0 = move-exception
                            r7 = r1
                            goto L_0x0068
                        L_0x0054:
                            r0 = move-exception
                        L_0x0055:
                            r0.printStackTrace()     // Catch:{ all -> 0x0051 }
                            bnk r7 = defpackage.bnk.this     // Catch:{ all -> 0x0051 }
                            java.io.File[] r7 = r7.b     // Catch:{ all -> 0x0051 }
                            java.lang.String r0 = " uploadFailed."
                            com.autonavi.common.tool.CrashLogUtil.appendUploadFlag(r7, r0)     // Catch:{ all -> 0x0051 }
                            if (r1 == 0) goto L_0x0067
                            r1.close()     // Catch:{ Exception -> 0x0066 }
                        L_0x0066:
                            return
                        L_0x0067:
                            return
                        L_0x0068:
                            if (r7 == 0) goto L_0x006d
                            r7.close()     // Catch:{ Exception -> 0x006d }
                        L_0x006d:
                            throw r0
                        L_0x006e:
                            bnk r7 = defpackage.bnk.this
                            java.io.File[] r7 = r7.b
                            java.lang.String r1 = " uploadFailed. ResCode: "
                            java.lang.String r0 = java.lang.String.valueOf(r0)
                            java.lang.String r0 = r1.concat(r0)
                            com.autonavi.common.tool.CrashLogUtil.appendUploadFlag(r7, r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: defpackage.bnk.AnonymousClass1.a(java.net.HttpURLConnection):void");
                    }

                    public final void a(Throwable th) {
                        if (th == null) {
                            CrashLogUtil.appendUploadFlag(bnk.this.b, "uploadFailed");
                            return;
                        }
                        File[] fileArr = bnk.this.b;
                        StringBuilder sb = new StringBuilder(" uploadFailed. exception:");
                        sb.append(th.toString());
                        CrashLogUtil.appendUploadFlag(fileArr, sb.toString());
                    }
                });
                if (this.a != null) {
                    try {
                        this.a.delete();
                    } catch (Throwable unused) {
                    }
                }
            }
        }
    }
}
