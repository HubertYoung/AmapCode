package com.autonavi.minimap.ajx3.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import java.io.File;

public class PhotoGraphedUtil {
    private static final String PHOTO_CAPTURE_OUT_DIR = "/autonavi/capture/";
    public static final int REQUEST_RETAKE_PHOTO = 512;
    public static final int REQUEST_TAKE_PHOTO = 256;
    private static final String TAG = "PhotoGraphedController";

    public static class CompressBitmapThread extends Thread {
        private static final int SCALE_HEIGHT = 480;
        private static final int SCALE_WIDTH = 320;
        private String inFilePath = null;
        private OnBitmapCompressedListener listener = null;
        private String outFilePath = null;

        public CompressBitmapThread(String str, String str2) {
            this.inFilePath = str;
            this.outFilePath = str2;
        }

        public void setListener(OnBitmapCompressedListener onBitmapCompressedListener) {
            this.listener = onBitmapCompressedListener;
        }

        /* JADX WARNING: Removed duplicated region for block: B:28:0x005a A[Catch:{ all -> 0x007c }] */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x0061 A[SYNTHETIC, Splitter:B:30:0x0061] */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x0074 A[Catch:{ Exception -> 0x0091 }] */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x007f A[SYNTHETIC, Splitter:B:43:0x007f] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r5 = this;
                java.lang.String r0 = r5.inFilePath     // Catch:{ Exception -> 0x0091 }
                r1 = 320(0x140, float:4.48E-43)
                r2 = 480(0x1e0, float:6.73E-43)
                android.graphics.Bitmap r0 = defpackage.kp.a(r0, r1, r2)     // Catch:{ Exception -> 0x0091 }
                if (r0 != 0) goto L_0x000d
                return
            L_0x000d:
                java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0091 }
                r1.<init>()     // Catch:{ Exception -> 0x0091 }
                android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x0091 }
                r3 = 90
                r0.compress(r2, r3, r1)     // Catch:{ Exception -> 0x0091 }
                byte[] r1 = r1.toByteArray()     // Catch:{ Exception -> 0x0091 }
                java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0091 }
                java.lang.String r3 = r5.outFilePath     // Catch:{ Exception -> 0x0091 }
                r2.<init>(r3)     // Catch:{ Exception -> 0x0091 }
                boolean r3 = r2.exists()     // Catch:{ Exception -> 0x0091 }
                if (r3 != 0) goto L_0x0031
                java.io.File r3 = r2.getParentFile()     // Catch:{ Exception -> 0x0091 }
                r3.mkdirs()     // Catch:{ Exception -> 0x0091 }
            L_0x0031:
                r3 = 0
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
                r4.<init>(r2)     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
                r4.write(r1)     // Catch:{ IOException -> 0x004f }
                r4.flush()     // Catch:{ IOException -> 0x004f }
                r4.close()     // Catch:{ IOException -> 0x0041 }
                goto L_0x004b
            L_0x0041:
                r1 = move-exception
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r2 = r5.listener     // Catch:{ Exception -> 0x0091 }
                if (r2 == 0) goto L_0x004b
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r2 = r5.listener     // Catch:{ Exception -> 0x0091 }
                r2.onException(r1)     // Catch:{ Exception -> 0x0091 }
            L_0x004b:
                r0.recycle()     // Catch:{ Exception -> 0x0091 }
                goto L_0x0070
            L_0x004f:
                r1 = move-exception
                goto L_0x0056
            L_0x0051:
                r1 = move-exception
                r4 = r3
                goto L_0x007d
            L_0x0054:
                r1 = move-exception
                r4 = r3
            L_0x0056:
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r2 = r5.listener     // Catch:{ all -> 0x007c }
                if (r2 == 0) goto L_0x005f
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r2 = r5.listener     // Catch:{ all -> 0x007c }
                r2.onException(r1)     // Catch:{ all -> 0x007c }
            L_0x005f:
                if (r4 == 0) goto L_0x004b
                r4.close()     // Catch:{ IOException -> 0x0065 }
                goto L_0x004b
            L_0x0065:
                r1 = move-exception
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r2 = r5.listener     // Catch:{ Exception -> 0x0091 }
                if (r2 == 0) goto L_0x004b
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r2 = r5.listener     // Catch:{ Exception -> 0x0091 }
                r2.onException(r1)     // Catch:{ Exception -> 0x0091 }
                goto L_0x004b
            L_0x0070:
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r0 = r5.listener     // Catch:{ Exception -> 0x0091 }
                if (r0 == 0) goto L_0x007b
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r0 = r5.listener     // Catch:{ Exception -> 0x0091 }
                java.lang.String r1 = r5.inFilePath     // Catch:{ Exception -> 0x0091 }
                r0.onCompress(r3, r1)     // Catch:{ Exception -> 0x0091 }
            L_0x007b:
                return
            L_0x007c:
                r1 = move-exception
            L_0x007d:
                if (r4 == 0) goto L_0x008d
                r4.close()     // Catch:{ IOException -> 0x0083 }
                goto L_0x008d
            L_0x0083:
                r2 = move-exception
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r3 = r5.listener     // Catch:{ Exception -> 0x0091 }
                if (r3 == 0) goto L_0x008d
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r3 = r5.listener     // Catch:{ Exception -> 0x0091 }
                r3.onException(r2)     // Catch:{ Exception -> 0x0091 }
            L_0x008d:
                r0.recycle()     // Catch:{ Exception -> 0x0091 }
                throw r1     // Catch:{ Exception -> 0x0091 }
            L_0x0091:
                r0 = move-exception
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r1 = r5.listener
                if (r1 == 0) goto L_0x009b
                com.autonavi.minimap.ajx3.util.PhotoGraphedUtil$OnBitmapCompressedListener r1 = r5.listener
                r1.onException(r0)
            L_0x009b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.PhotoGraphedUtil.CompressBitmapThread.run():void");
        }
    }

    public interface IPhotoGraphedListener {
        void onPhotoCaptureResult(String str);
    }

    interface OnBitmapCompressedListener {
        void onCompress(Bitmap bitmap, String str);

        void onException(Exception exc);
    }

    public static void doTakePhoto(final Activity activity) {
        kj.b(activity, new String[]{"android.permission.CAMERA"}, new b() {
            public final void run() {
                try {
                    if (!FileUtil.PHOTO_DIR.exists()) {
                        FileUtil.PHOTO_DIR.mkdirs();
                    }
                    adu.a(FileUtil.PHOTO_DIR.getAbsolutePath(), activity, 256, new Callback<Object>() {
                        public void callback(Object obj) {
                        }

                        public void error(Throwable th, boolean z) {
                        }
                    });
                } catch (ActivityNotFoundException unused) {
                    ToastHelper.showLongToast("您设备上的照相机功能异常，请确认。");
                }
            }
        });
    }

    public static void onCaptureResult(int i, int i2, Intent intent, IPhotoGraphedListener iPhotoGraphedListener) {
        if (i == 256) {
            if (i2 == -1) {
                onCaptureResult(intent, iPhotoGraphedListener);
            } else if (i2 == 0 && iPhotoGraphedListener != null) {
                iPhotoGraphedListener.onPhotoCaptureResult(null);
            }
        } else if (i == 512 && i2 == -1) {
            onCaptureResult(intent, iPhotoGraphedListener);
        }
    }

    private static void onCaptureResult(Intent intent, final IPhotoGraphedListener iPhotoGraphedListener) {
        ku.a().c("NaviMonitor", "onCaptureResult");
        String str = (String) adu.a(intent).get("camera_pic_path");
        File file = new File(str);
        if (file.exists() && file.canRead()) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append(PHOTO_CAPTURE_OUT_DIR);
            File file2 = new File(sb.toString());
            if (file2.exists()) {
                File[] listFiles = file2.listFiles();
                if (listFiles != null) {
                    for (File delete : listFiles) {
                        delete.delete();
                    }
                }
            } else {
                file2.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory());
            sb2.append(PHOTO_CAPTURE_OUT_DIR);
            sb2.append(System.currentTimeMillis());
            sb2.append(".jpg");
            final String sb3 = sb2.toString();
            CompressBitmapThread compressBitmapThread = new CompressBitmapThread(str, sb3);
            compressBitmapThread.setListener(new OnBitmapCompressedListener() {
                public final void onCompress(Bitmap bitmap, String str) {
                    ku a = ku.a();
                    StringBuilder sb = new StringBuilder("onCompress ");
                    sb.append(sb3);
                    a.c("NaviMonitor", sb.toString());
                    if (iPhotoGraphedListener != null) {
                        iPhotoGraphedListener.onPhotoCaptureResult(sb3);
                    }
                }

                public final void onException(Exception exc) {
                    ku a = ku.a();
                    StringBuilder sb = new StringBuilder("compressBitmapThread  onException ");
                    sb.append(sb3);
                    a.c("NaviMonitor", sb.toString());
                }
            });
            compressBitmapThread.start();
        }
    }
}
