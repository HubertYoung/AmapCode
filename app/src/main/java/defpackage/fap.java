package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import java.util.List;

/* renamed from: fap reason: default package */
/* compiled from: ImageDownTask */
public final class fap extends AsyncTask<String, Void, List<Bitmap>> {
    private Context a;
    private ezp b;
    private long c;
    private boolean d;
    private int e = 0;

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        List list = (List) obj;
        super.onPostExecute(list);
        fat.c((String) "ImageDownTask", (String) "onPostExecute");
        if (this.b != null) {
            fba.b().a((String) "com.vivo.push.notify_key", this.c);
            fae.a(this.a, list, this.b, this.c, this.e);
        }
    }

    public fap(Context context, ezp ezp, long j, boolean z) {
        this.a = context;
        this.b = ezp;
        this.c = j;
        this.d = z;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0092, code lost:
        if (r4 != null) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a0, code lost:
        if (r4 != null) goto L_0x0094;
     */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ac A[SYNTHETIC, Splitter:B:43:0x00ac] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<android.graphics.Bitmap> doInBackground(java.lang.String... r10) {
        /*
            r9 = this;
            android.content.Context r0 = r9.a
            ezj r0 = defpackage.ezj.a(r0)
            int r0 = r0.b()
            r9.e = r0
            boolean r0 = r9.d
            r1 = 0
            if (r0 != 0) goto L_0x0019
            java.lang.String r10 = "ImageDownTask"
            java.lang.String r0 = "bitmap is not display by forbid net"
            defpackage.fat.d(r10, r0)
            return r1
        L_0x0019:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2 = 0
            r3 = 0
        L_0x0020:
            r4 = 2
            if (r3 >= r4) goto L_0x00b9
            r4 = r10[r3]
            java.lang.String r5 = "ImageDownTask"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "imgUrl="
            r6.<init>(r7)
            r6.append(r4)
            java.lang.String r7 = " i="
            r6.append(r7)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            defpackage.fat.d(r5, r6)
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x00b0
            java.net.URL r5 = new java.net.URL     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            r5.<init>(r4)     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            java.net.URLConnection r4 = r5.openConnection()     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            r5 = 30000(0x7530, float:4.2039E-41)
            r4.setConnectTimeout(r5)     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            r5 = 1
            r4.setDoInput(r5)     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            r4.setUseCaches(r2)     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            r4.connect()     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            int r5 = r4.getResponseCode()     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            java.lang.String r6 = "ImageDownTask"
            java.lang.String r7 = "code="
            java.lang.String r8 = java.lang.String.valueOf(r5)     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            java.lang.String r7 = r7.concat(r8)     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            defpackage.fat.c(r6, r7)     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            r6 = 200(0xc8, float:2.8E-43)
            if (r5 != r6) goto L_0x0080
            java.io.InputStream r4 = r4.getInputStream()     // Catch:{ MalformedURLException -> 0x0098, IOException -> 0x008a, all -> 0x0088 }
            android.graphics.Bitmap r5 = android.graphics.BitmapFactory.decodeStream(r4)     // Catch:{ MalformedURLException -> 0x0099, IOException -> 0x008b }
            goto L_0x0082
        L_0x0080:
            r4 = r1
            r5 = r4
        L_0x0082:
            if (r4 == 0) goto L_0x00a4
            r4.close()     // Catch:{ Exception -> 0x00a4 }
            goto L_0x00a4
        L_0x0088:
            r10 = move-exception
            goto L_0x00aa
        L_0x008a:
            r4 = r1
        L_0x008b:
            java.lang.String r5 = "ImageDownTask"
            java.lang.String r6 = "IOException"
            defpackage.fat.a(r5, r6)     // Catch:{ all -> 0x00a8 }
            if (r4 == 0) goto L_0x00a3
        L_0x0094:
            r4.close()     // Catch:{ Exception -> 0x00a3 }
            goto L_0x00a3
        L_0x0098:
            r4 = r1
        L_0x0099:
            java.lang.String r5 = "ImageDownTask"
            java.lang.String r6 = "MalformedURLException"
            defpackage.fat.a(r5, r6)     // Catch:{ all -> 0x00a8 }
            if (r4 == 0) goto L_0x00a3
            goto L_0x0094
        L_0x00a3:
            r5 = r1
        L_0x00a4:
            r0.add(r5)
            goto L_0x00b5
        L_0x00a8:
            r10 = move-exception
            r1 = r4
        L_0x00aa:
            if (r1 == 0) goto L_0x00af
            r1.close()     // Catch:{ Exception -> 0x00af }
        L_0x00af:
            throw r10
        L_0x00b0:
            if (r3 != 0) goto L_0x00b5
            r0.add(r1)
        L_0x00b5:
            int r3 = r3 + 1
            goto L_0x0020
        L_0x00b9:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fap.doInBackground(java.lang.String[]):java.util.List");
    }
}
