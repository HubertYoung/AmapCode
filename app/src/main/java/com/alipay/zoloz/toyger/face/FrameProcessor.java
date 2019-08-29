package com.alipay.zoloz.toyger.face;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.alipay.zoloz.toyger.algorithm.TGDepthFrame;
import com.alipay.zoloz.toyger.algorithm.TGFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class FrameProcessor {
    public static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS", Locale.US);
    private TGFrame a;
    private TGDepthFrame b;

    public static String getDetailDateFormat(long j) {
        String format;
        synchronized (DATE_FORMAT2) {
            format = DATE_FORMAT2.format(new Date(j));
        }
        return format;
    }

    /* access modifiers changed from: 0000 */
    public void initFame(TGFrame tGFrame, TGDepthFrame tGDepthFrame) {
        this.a = tGFrame;
        this.b = tGDepthFrame;
    }

    public TGFrame getTgFrame() {
        return this.a;
    }

    public TGDepthFrame getTgDepthFrame() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public void saveBitmap(ToygerFaceAttr toygerFaceAttr, String str) {
        saveBitmap(this.a, this.b, toygerFaceAttr, str);
    }

    static void saveBitmap(TGFrame tGFrame, TGDepthFrame tGDepthFrame, ToygerFaceAttr toygerFaceAttr, String str) {
    }

    public static void saveBestBitmap(Bitmap bitmap) {
    }

    public static void bitmap2File(Bitmap bitmap, File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException | IOException e) {
        }
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0033=Splitter:B:25:0x0033, B:32:0x003f=Splitter:B:32:0x003f} */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean save(java.io.File r5, byte[] r6) {
        /*
            r2 = 0
            java.lang.Class<com.alipay.zoloz.toyger.face.FrameProcessor> r4 = com.alipay.zoloz.toyger.face.FrameProcessor.class
            monitor-enter(r4)
            r0 = 0
            if (r5 == 0) goto L_0x0029
            if (r6 == 0) goto L_0x0029
            boolean r1 = r5.exists()     // Catch:{ all -> 0x003a }
            if (r1 == 0) goto L_0x002b
            r5.delete()     // Catch:{ all -> 0x003a }
        L_0x0012:
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0031, all -> 0x003d }
            r3.<init>(r5)     // Catch:{ IOException -> 0x0031, all -> 0x003d }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x004b, all -> 0x0046 }
            r1.<init>(r3)     // Catch:{ IOException -> 0x004b, all -> 0x0046 }
            r1.write(r6)     // Catch:{ IOException -> 0x004f, all -> 0x0048 }
            r1.flush()     // Catch:{ IOException -> 0x004f, all -> 0x0048 }
            r0 = 1
            close(r1)     // Catch:{ all -> 0x003a }
            close(r3)     // Catch:{ all -> 0x003a }
        L_0x0029:
            monitor-exit(r4)
            return r0
        L_0x002b:
            r5.createNewFile()     // Catch:{ IOException -> 0x002f }
            goto L_0x0012
        L_0x002f:
            r1 = move-exception
            goto L_0x0012
        L_0x0031:
            r1 = move-exception
            r1 = r2
        L_0x0033:
            close(r1)     // Catch:{ all -> 0x003a }
            close(r2)     // Catch:{ all -> 0x003a }
            goto L_0x0029
        L_0x003a:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x003d:
            r0 = move-exception
            r3 = r2
        L_0x003f:
            close(r2)     // Catch:{ all -> 0x003a }
            close(r3)     // Catch:{ all -> 0x003a }
            throw r0     // Catch:{ all -> 0x003a }
        L_0x0046:
            r0 = move-exception
            goto L_0x003f
        L_0x0048:
            r0 = move-exception
            r2 = r1
            goto L_0x003f
        L_0x004b:
            r1 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x0033
        L_0x004f:
            r2 = move-exception
            r2 = r3
            goto L_0x0033
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.zoloz.toyger.face.FrameProcessor.save(java.io.File, byte[]):boolean");
    }

    public static void close(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.toString();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearFrame() {
        this.a = null;
        this.b = null;
    }
}
