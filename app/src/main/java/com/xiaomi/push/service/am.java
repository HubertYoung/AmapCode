package com.xiaomi.push.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class am {

    public static class a {
        byte[] a;
        int b;

        public a(byte[] bArr, int i) {
            this.a = bArr;
            this.b = i;
        }
    }

    public static class b {
        public Bitmap a;
        public long b;

        public b(Bitmap bitmap, long j) {
            this.a = bitmap;
            this.b = j;
        }
    }

    private static int a(Context context, InputStream inputStream) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            com.xiaomi.channel.commonutils.logger.b.a((String) "decode dimension failed for bitmap.");
            return 1;
        }
        int round = Math.round((((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * 48.0f);
        if (options.outWidth <= round || options.outHeight <= round) {
            return 1;
        }
        return Math.min(options.outWidth / round, options.outHeight / round);
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00dd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.xiaomi.push.service.am.a a(java.lang.String r11) {
        /*
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ IOException -> 0x00c6, all -> 0x00c3 }
            r1.<init>(r11)     // Catch:{ IOException -> 0x00c6, all -> 0x00c3 }
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ IOException -> 0x00c6, all -> 0x00c3 }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ IOException -> 0x00c6, all -> 0x00c3 }
            r2 = 8000(0x1f40, float:1.121E-41)
            r1.setConnectTimeout(r2)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            r2 = 20000(0x4e20, float:2.8026E-41)
            r1.setReadTimeout(r2)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            r1.connect()     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            int r2 = r1.getContentLength()     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            r3 = 102400(0x19000, float:1.43493E-40)
            if (r2 <= r3) goto L_0x0044
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            java.lang.String r4 = "Bitmap size is too big, max size is 102400  contentLen size is "
            r3.<init>(r4)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            r3.append(r2)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            java.lang.String r2 = " from url "
            r3.append(r2)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            r3.append(r11)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            java.lang.String r11 = r3.toString()     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            com.xiaomi.channel.commonutils.logger.b.a(r11)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            com.xiaomi.channel.commonutils.file.a.a(r0)
            if (r1 == 0) goto L_0x0043
            r1.disconnect()
        L_0x0043:
            return r0
        L_0x0044:
            int r11 = r1.getResponseCode()     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            r2 = 200(0xc8, float:2.8E-43)
            if (r11 == r2) goto L_0x006b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            java.lang.String r3 = "Invalid Http Response Code "
            r2.<init>(r3)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            r2.append(r11)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            java.lang.String r11 = " received"
            r2.append(r11)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            java.lang.String r11 = r2.toString()     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            com.xiaomi.channel.commonutils.logger.b.a(r11)     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            com.xiaomi.channel.commonutils.file.a.a(r0)
            if (r1 == 0) goto L_0x006a
            r1.disconnect()
        L_0x006a:
            return r0
        L_0x006b:
            java.io.InputStream r11 = r1.getInputStream()     // Catch:{ IOException -> 0x00bf, all -> 0x00bd }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            r2.<init>()     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r4]     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            r6 = 102400(0x19000, float:1.43493E-40)
        L_0x007b:
            if (r6 <= 0) goto L_0x008a
            r7 = 0
            int r8 = r11.read(r5, r7, r4)     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            r9 = -1
            if (r8 == r9) goto L_0x008a
            int r6 = r6 - r8
            r2.write(r5, r7, r8)     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            goto L_0x007b
        L_0x008a:
            if (r6 > 0) goto L_0x009f
            java.lang.String r2 = "length 102400 exhausted."
            com.xiaomi.channel.commonutils.logger.b.a(r2)     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            com.xiaomi.push.service.am$a r2 = new com.xiaomi.push.service.am$a     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            r2.<init>(r0, r3)     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            com.xiaomi.channel.commonutils.file.a.a(r11)
            if (r1 == 0) goto L_0x009e
            r1.disconnect()
        L_0x009e:
            return r2
        L_0x009f:
            byte[] r2 = r2.toByteArray()     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            com.xiaomi.push.service.am$a r3 = new com.xiaomi.push.service.am$a     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            int r4 = r2.length     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            r3.<init>(r2, r4)     // Catch:{ IOException -> 0x00b7, all -> 0x00b2 }
            com.xiaomi.channel.commonutils.file.a.a(r11)
            if (r1 == 0) goto L_0x00b1
            r1.disconnect()
        L_0x00b1:
            return r3
        L_0x00b2:
            r0 = move-exception
            r10 = r0
            r0 = r11
            r11 = r10
            goto L_0x00d8
        L_0x00b7:
            r2 = move-exception
            r10 = r1
            r1 = r11
            r11 = r2
            r2 = r10
            goto L_0x00c9
        L_0x00bd:
            r11 = move-exception
            goto L_0x00d8
        L_0x00bf:
            r11 = move-exception
            r2 = r1
            r1 = r0
            goto L_0x00c9
        L_0x00c3:
            r11 = move-exception
            r1 = r0
            goto L_0x00d8
        L_0x00c6:
            r11 = move-exception
            r1 = r0
            r2 = r1
        L_0x00c9:
            com.xiaomi.channel.commonutils.logger.b.a(r11)     // Catch:{ all -> 0x00d5 }
            com.xiaomi.channel.commonutils.file.a.a(r1)
            if (r2 == 0) goto L_0x00d4
            r2.disconnect()
        L_0x00d4:
            return r0
        L_0x00d5:
            r11 = move-exception
            r0 = r1
            r1 = r2
        L_0x00d8:
            com.xiaomi.channel.commonutils.file.a.a(r0)
            if (r1 == 0) goto L_0x00e0
            r1.disconnect()
        L_0x00e0:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.am.a(java.lang.String):com.xiaomi.push.service.am$a");
    }

    public static b a(Context context, String str) {
        InputStream inputStream = null;
        b bVar = new b(null, 0);
        try {
            a a2 = a(str);
            if (a2 == null) {
                com.xiaomi.channel.commonutils.file.a.a((InputStream) null);
                return bVar;
            }
            bVar.b = (long) a2.b;
            byte[] bArr = a2.a;
            if (bArr != null) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    int a3 = a(context, (InputStream) byteArrayInputStream);
                    Options options = new Options();
                    options.inSampleSize = a3;
                    bVar.a = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                    inputStream = byteArrayInputStream;
                } catch (Exception e) {
                    e = e;
                    inputStream = byteArrayInputStream;
                    try {
                        com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                        com.xiaomi.channel.commonutils.file.a.a(inputStream);
                        return bVar;
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = byteArrayInputStream;
                    com.xiaomi.channel.commonutils.file.a.a(inputStream);
                    throw th;
                }
            }
            com.xiaomi.channel.commonutils.file.a.a(inputStream);
            return bVar;
        } catch (Exception e2) {
            e = e2;
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
            com.xiaomi.channel.commonutils.file.a.a(inputStream);
            return bVar;
        }
    }

    public static Bitmap b(Context context, String str) {
        InputStream inputStream;
        InputStream inputStream2;
        int a2;
        Uri parse = Uri.parse(str);
        try {
            inputStream2 = context.getContentResolver().openInputStream(parse);
            try {
                a2 = a(context, inputStream2);
                inputStream = context.getContentResolver().openInputStream(parse);
            } catch (IOException e) {
                e = e;
                inputStream = null;
                try {
                    com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                    com.xiaomi.channel.commonutils.file.a.a(inputStream);
                    com.xiaomi.channel.commonutils.file.a.a(inputStream2);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    com.xiaomi.channel.commonutils.file.a.a(inputStream);
                    com.xiaomi.channel.commonutils.file.a.a(inputStream2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                inputStream = null;
                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                com.xiaomi.channel.commonutils.file.a.a(inputStream2);
                throw th;
            }
            try {
                Options options = new Options();
                options.inSampleSize = a2;
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                com.xiaomi.channel.commonutils.file.a.a(inputStream2);
                return decodeStream;
            } catch (IOException e2) {
                e = e2;
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                com.xiaomi.channel.commonutils.file.a.a(inputStream2);
                return null;
            }
        } catch (IOException e3) {
            e = e3;
            inputStream = null;
            inputStream2 = null;
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
            com.xiaomi.channel.commonutils.file.a.a(inputStream);
            com.xiaomi.channel.commonutils.file.a.a(inputStream2);
            return null;
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            inputStream2 = null;
            com.xiaomi.channel.commonutils.file.a.a(inputStream);
            com.xiaomi.channel.commonutils.file.a.a(inputStream2);
            throw th;
        }
    }
}
