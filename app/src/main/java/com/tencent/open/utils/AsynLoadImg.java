package com.tencent.open.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import com.tencent.open.a.f;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: ProGuard */
public class AsynLoadImg {
    /* access modifiers changed from: private */
    public static String c;
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public AsynLoadImgBack b;
    /* access modifiers changed from: private */
    public long d;
    /* access modifiers changed from: private */
    public Handler e;
    private Runnable f = new Runnable() {
        public void run() {
            boolean z;
            f.a("AsynLoadImg", "saveFileRunnable:");
            String encrypt = Util.encrypt(AsynLoadImg.this.a);
            StringBuilder sb = new StringBuilder("share_qq_");
            sb.append(encrypt);
            sb.append(".jpg");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(AsynLoadImg.c);
            sb3.append(sb2);
            String sb4 = sb3.toString();
            File file = new File(sb4);
            Message obtainMessage = AsynLoadImg.this.e.obtainMessage();
            if (file.exists()) {
                obtainMessage.arg1 = 0;
                obtainMessage.obj = sb4;
                StringBuilder sb5 = new StringBuilder("file exists: time:");
                sb5.append(System.currentTimeMillis() - AsynLoadImg.this.d);
                f.a("AsynLoadImg", sb5.toString());
            } else {
                Bitmap bitmap = AsynLoadImg.getbitmap(AsynLoadImg.this.a);
                if (bitmap != null) {
                    z = AsynLoadImg.this.saveFile(bitmap, sb2);
                } else {
                    f.a("AsynLoadImg", "saveFileRunnable:get bmp fail---");
                    z = false;
                }
                if (z) {
                    obtainMessage.arg1 = 0;
                    obtainMessage.obj = sb4;
                } else {
                    obtainMessage.arg1 = 1;
                }
                StringBuilder sb6 = new StringBuilder("file not exists: download time:");
                sb6.append(System.currentTimeMillis() - AsynLoadImg.this.d);
                f.a("AsynLoadImg", sb6.toString());
            }
            AsynLoadImg.this.e.sendMessage(obtainMessage);
        }
    };

    public AsynLoadImg(Activity activity) {
        this.e = new Handler(activity.getMainLooper()) {
            public void handleMessage(Message message) {
                StringBuilder sb = new StringBuilder("handleMessage:");
                sb.append(message.arg1);
                f.a("AsynLoadImg", sb.toString());
                if (message.arg1 == 0) {
                    AsynLoadImg.this.b.saved(message.arg1, (String) message.obj);
                } else {
                    AsynLoadImg.this.b.saved(message.arg1, null);
                }
            }
        };
    }

    public void save(String str, AsynLoadImgBack asynLoadImgBack) {
        f.a("AsynLoadImg", "--save---");
        if (str == null || str.equals("")) {
            asynLoadImgBack.saved(1, null);
        } else if (!Util.hasSDCard()) {
            asynLoadImgBack.saved(2, null);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append(APMSandboxProcessor.TEMP_DIR);
            c = sb.toString();
            this.d = System.currentTimeMillis();
            this.a = str;
            this.b = asynLoadImgBack;
            new Thread(this.f).start();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0067 A[SYNTHETIC, Splitter:B:23:0x0067] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0073 A[SYNTHETIC, Splitter:B:30:0x0073] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean saveFile(android.graphics.Bitmap r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = c
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x005a }
            r2.<init>(r0)     // Catch:{ IOException -> 0x005a }
            boolean r3 = r2.exists()     // Catch:{ IOException -> 0x005a }
            if (r3 != 0) goto L_0x0011
            r2.mkdir()     // Catch:{ IOException -> 0x005a }
        L_0x0011:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x005a }
            r2.<init>()     // Catch:{ IOException -> 0x005a }
            r2.append(r0)     // Catch:{ IOException -> 0x005a }
            r2.append(r6)     // Catch:{ IOException -> 0x005a }
            java.lang.String r0 = r2.toString()     // Catch:{ IOException -> 0x005a }
            java.lang.String r2 = "AsynLoadImg"
            java.lang.String r3 = "saveFile:"
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ IOException -> 0x005a }
            java.lang.String r6 = r3.concat(r6)     // Catch:{ IOException -> 0x005a }
            com.tencent.open.a.f.a(r2, r6)     // Catch:{ IOException -> 0x005a }
            java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x005a }
            r6.<init>(r0)     // Catch:{ IOException -> 0x005a }
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x005a }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x005a }
            r2.<init>(r6)     // Catch:{ IOException -> 0x005a }
            r0.<init>(r2)     // Catch:{ IOException -> 0x005a }
            android.graphics.Bitmap$CompressFormat r6 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ IOException -> 0x0055, all -> 0x0052 }
            r1 = 80
            r5.compress(r6, r1, r0)     // Catch:{ IOException -> 0x0055, all -> 0x0052 }
            r0.flush()     // Catch:{ IOException -> 0x0055, all -> 0x0052 }
            r0.close()     // Catch:{ IOException -> 0x004c }
            goto L_0x0050
        L_0x004c:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0050:
            r5 = 1
            return r5
        L_0x0052:
            r5 = move-exception
            r1 = r0
            goto L_0x0071
        L_0x0055:
            r5 = move-exception
            r1 = r0
            goto L_0x005b
        L_0x0058:
            r5 = move-exception
            goto L_0x0071
        L_0x005a:
            r5 = move-exception
        L_0x005b:
            r5.printStackTrace()     // Catch:{ all -> 0x0058 }
            java.lang.String r6 = "AsynLoadImg"
            java.lang.String r0 = "saveFile bmp fail---"
            com.tencent.open.a.f.b(r6, r0, r5)     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x006f
            r1.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x006f
        L_0x006b:
            r5 = move-exception
            r5.printStackTrace()
        L_0x006f:
            r5 = 0
            return r5
        L_0x0071:
            if (r1 == 0) goto L_0x007b
            r1.close()     // Catch:{ IOException -> 0x0077 }
            goto L_0x007b
        L_0x0077:
            r6 = move-exception
            r6.printStackTrace()
        L_0x007b:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.AsynLoadImg.saveFile(android.graphics.Bitmap, java.lang.String):boolean");
    }

    public static Bitmap getbitmap(String str) {
        f.a("AsynLoadImg", "getbitmap:".concat(String.valueOf(str)));
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            f.a("AsynLoadImg", "image download finished.".concat(String.valueOf(str)));
            return decodeStream;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            f.a("AsynLoadImg", "getbitmap bmp fail---");
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            f.a("AsynLoadImg", "getbitmap bmp fail---");
            return null;
        }
    }
}
