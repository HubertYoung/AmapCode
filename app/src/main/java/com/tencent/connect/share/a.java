package com.tencent.connect.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import com.tencent.open.a.f;
import com.tencent.open.utils.AsynLoadImgBack;
import com.tencent.open.utils.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class a {
    public static final void a(Context context, final String str, final AsynLoadImgBack asynLoadImgBack) {
        f.b("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage");
        if (TextUtils.isEmpty(str)) {
            asynLoadImgBack.saved(1, null);
        } else if (!Util.hasSDCard()) {
            asynLoadImgBack.saved(2, null);
        } else {
            final AnonymousClass1 r0 = new Handler(context.getMainLooper()) {
                public final void handleMessage(Message message) {
                    switch (message.what) {
                        case 101:
                            asynLoadImgBack.saved(0, (String) message.obj);
                            return;
                        case 102:
                            asynLoadImgBack.saved(message.arg1, null);
                            return;
                        default:
                            super.handleMessage(message);
                            return;
                    }
                }
            };
            new Thread(new Runnable() {
                public final void run() {
                    String str;
                    Bitmap a2 = a.a(str, 140);
                    if (a2 != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(Environment.getExternalStorageDirectory());
                        sb.append(APMSandboxProcessor.TEMP_DIR);
                        String sb2 = sb.toString();
                        String encrypt = Util.encrypt(str);
                        StringBuilder sb3 = new StringBuilder("share2qq_temp");
                        sb3.append(encrypt);
                        sb3.append(".jpg");
                        String sb4 = sb3.toString();
                        if (!a.b(str, 140, 140)) {
                            f.b("openSDK_LOG.AsynScaleCompressImage", "not out of bound,not compress!");
                            str = str;
                        } else {
                            f.b("openSDK_LOG.AsynScaleCompressImage", "out of bound,compress!");
                            str = a.a(a2, sb2, sb4);
                        }
                        f.b("openSDK_LOG.AsynScaleCompressImage", "-->destFilePath: ".concat(String.valueOf(str)));
                        if (str != null) {
                            Message obtainMessage = r0.obtainMessage(101);
                            obtainMessage.obj = str;
                            r0.sendMessage(obtainMessage);
                            return;
                        }
                    }
                    Message obtainMessage2 = r0.obtainMessage(102);
                    obtainMessage2.arg1 = 3;
                    r0.sendMessage(obtainMessage2);
                }
            }).start();
        }
    }

    public static final void a(Context context, final ArrayList<String> arrayList, final AsynLoadImgBack asynLoadImgBack) {
        f.b("openSDK_LOG.AsynScaleCompressImage", "batchScaleCompressImage");
        if (arrayList == null) {
            asynLoadImgBack.saved(1, null);
            return;
        }
        final AnonymousClass3 r0 = new Handler(context.getMainLooper()) {
            public final void handleMessage(Message message) {
                if (message.what != 101) {
                    super.handleMessage(message);
                    return;
                }
                asynLoadImgBack.batchSaved(0, message.getData().getStringArrayList("images"));
            }
        };
        new Thread(new Runnable() {
            public final void run() {
                for (int i = 0; i < arrayList.size(); i++) {
                    String str = (String) arrayList.get(i);
                    if (!Util.isValidUrl(str) && Util.fileExists(str)) {
                        Bitmap a2 = a.a(str, 10000);
                        if (a2 != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(Environment.getExternalStorageDirectory());
                            sb.append(APMSandboxProcessor.TEMP_DIR);
                            String sb2 = sb.toString();
                            String encrypt = Util.encrypt(str);
                            StringBuilder sb3 = new StringBuilder("share2qzone_temp");
                            sb3.append(encrypt);
                            sb3.append(".jpg");
                            String sb4 = sb3.toString();
                            if (!a.b(str, 640, 10000)) {
                                f.b("openSDK_LOG.AsynScaleCompressImage", "not out of bound,not compress!");
                            } else {
                                f.b("openSDK_LOG.AsynScaleCompressImage", "out of bound, compress!");
                                str = a.a(a2, sb2, sb4);
                            }
                            if (str != null) {
                                arrayList.set(i, str);
                            }
                        }
                    }
                }
                Message obtainMessage = r0.obtainMessage(101);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("images", arrayList);
                obtainMessage.setData(bundle);
                r0.sendMessage(obtainMessage);
            }
        }).start();
    }

    private static Bitmap a(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= height) {
            width = height;
        }
        float f = ((float) i) / ((float) width);
        matrix.postScale(f, f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    protected static final String a(Bitmap bitmap, String str, String str2) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.append(str2);
        String stringBuffer2 = stringBuffer.toString();
        File file2 = new File(stringBuffer2);
        if (file2.exists()) {
            file2.delete();
        }
        if (bitmap != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                bitmap.compress(CompressFormat.JPEG, 80, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                bitmap.recycle();
                return stringBuffer2;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static final boolean b(String str, int i, int i2) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        int i3 = options.outWidth;
        int i4 = options.outHeight;
        if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
            return false;
        }
        int i5 = i3 > i4 ? i3 : i4;
        if (i3 >= i4) {
            i3 = i4;
        }
        StringBuilder sb = new StringBuilder("longSide=");
        sb.append(i5);
        sb.append("shortSide=");
        sb.append(i3);
        f.b("openSDK_LOG.AsynScaleCompressImage", sb.toString());
        options.inPreferredConfig = Config.RGB_565;
        if (i5 > i2 || i3 > i) {
            return true;
        }
        return false;
    }

    public static final Bitmap a(String str, int i) {
        Bitmap bitmap;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
            return null;
        }
        if (i2 <= i3) {
            i2 = i3;
        }
        options.inPreferredConfig = Config.RGB_565;
        if (i2 > i) {
            options.inSampleSize = a(options, -1, i * i);
        }
        options.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            bitmap = null;
        }
        if (bitmap == null) {
            return null;
        }
        int i4 = options.outWidth;
        int i5 = options.outHeight;
        if (i4 > i5) {
            i5 = i4;
        }
        return i5 > i ? a(bitmap, i) : bitmap;
    }

    public static final int a(Options options, int i, int i2) {
        int b = b(options, i, i2);
        if (b > 8) {
            return ((b + 7) / 8) * 8;
        }
        int i3 = 1;
        while (i3 < b) {
            i3 <<= 1;
        }
        return i3;
    }

    private static int b(Options options, int i, int i2) {
        int i3;
        int i4;
        double d = (double) options.outWidth;
        double d2 = (double) options.outHeight;
        if (i2 == -1) {
            i3 = 1;
        } else {
            i3 = (int) Math.ceil(Math.sqrt((d * d2) / ((double) i2)));
        }
        if (i == -1) {
            i4 = 128;
        } else {
            double d3 = (double) i;
            i4 = (int) Math.min(Math.floor(d / d3), Math.floor(d2 / d3));
        }
        if (i4 < i3) {
            return i3;
        }
        if (i2 == -1 && i == -1) {
            return 1;
        }
        return i == -1 ? i3 : i4;
    }
}
