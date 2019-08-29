package defpackage;

import java.io.InputStream;

/* renamed from: bor reason: default package */
/* compiled from: InputStreamEntity */
public final class bor implements boo {
    private InputStream a;
    private String b;

    public bor(InputStream inputStream, String str) {
        this.a = inputStream;
        this.b = str;
    }

    public final String b() {
        return this.b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0043, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0060, code lost:
        throw r6;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005f A[Catch:{ IOException -> 0x005f, Throwable -> 0x003f, all -> 0x0041, Throwable -> 0x0066 }, ExcHandler: IOException (r6v1 'e' java.io.IOException A[CUSTOM_DECLARE, Catch:{  }]), Splitter:B:6:0x0023] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long a(java.io.OutputStream r6) throws java.io.IOException {
        /*
            r5 = this;
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            java.io.InputStream r1 = r5.a     // Catch:{ Throwable -> 0x000a }
            r1.reset()     // Catch:{ Throwable -> 0x000a }
            goto L_0x0022
        L_0x000a:
            r1 = move-exception
            java.lang.String r2 = "ANet-InputStreamEntity"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "inputStream reset error: "
            r3.<init>(r4)
            java.lang.String r1 = r1.getLocalizedMessage()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            defpackage.bpv.d(r2, r1)
        L_0x0022:
            r1 = 0
            java.io.InputStream r2 = r5.a     // Catch:{ IOException -> 0x005f, Throwable -> 0x0043 }
            int r2 = r2.available()     // Catch:{ IOException -> 0x005f, Throwable -> 0x0043 }
        L_0x0029:
            java.io.InputStream r3 = r5.a     // Catch:{ IOException -> 0x005f, Throwable -> 0x003f }
            int r3 = r3.read(r0)     // Catch:{ IOException -> 0x005f, Throwable -> 0x003f }
            r4 = -1
            if (r3 == r4) goto L_0x0036
            r6.write(r0, r1, r3)     // Catch:{ IOException -> 0x005f, Throwable -> 0x003f }
            goto L_0x0029
        L_0x0036:
            r6.flush()     // Catch:{ IOException -> 0x005f, Throwable -> 0x003f }
        L_0x0039:
            java.io.InputStream r6 = r5.a     // Catch:{ Throwable -> 0x005d }
            r6.close()     // Catch:{ Throwable -> 0x005d }
            goto L_0x005d
        L_0x003f:
            r6 = move-exception
            goto L_0x0045
        L_0x0041:
            r6 = move-exception
            goto L_0x0061
        L_0x0043:
            r6 = move-exception
            r2 = 0
        L_0x0045:
            java.lang.String r0 = "ANet-InputStreamEntity"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0041 }
            java.lang.String r3 = "inputStream write error: "
            r1.<init>(r3)     // Catch:{ all -> 0x0041 }
            java.lang.String r6 = r6.getLocalizedMessage()     // Catch:{ all -> 0x0041 }
            r1.append(r6)     // Catch:{ all -> 0x0041 }
            java.lang.String r6 = r1.toString()     // Catch:{ all -> 0x0041 }
            defpackage.bpv.d(r0, r6)     // Catch:{ all -> 0x0041 }
            goto L_0x0039
        L_0x005d:
            long r0 = (long) r2     // Catch:{ all -> 0x0041 }
            return r0
        L_0x005f:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0041 }
        L_0x0061:
            java.io.InputStream r0 = r5.a     // Catch:{ Throwable -> 0x0066 }
            r0.close()     // Catch:{ Throwable -> 0x0066 }
        L_0x0066:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bor.a(java.io.OutputStream):long");
    }
}
