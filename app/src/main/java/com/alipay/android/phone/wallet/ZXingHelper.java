package com.alipay.android.phone.wallet;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.alipay.android.phone.wallet.minizxing.BarcodeFormat;
import com.alipay.android.phone.wallet.minizxing.BitMatrix;
import com.alipay.android.phone.wallet.minizxing.EncodeHintType;
import com.alipay.android.phone.wallet.minizxing.ErrorCorrectionLevel;
import com.alipay.android.phone.wallet.minizxing.MultiFormatWriter;
import com.alipay.android.phone.wallet.minizxing.WriterException;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.Hashtable;

public class ZXingHelper {
    static final ErrorCorrectionLevel a = ErrorCorrectionLevel.H;
    private static String b = "ZXingHelper";
    private static Boolean c;

    private static Bitmap a(String contents, BarcodeFormat format, int backcolor, int img_width, int img_height, ErrorCorrectionLevel el, Bitmap avatar, int cornerColor, String barcodeDisplayText, boolean softResample) {
        int width;
        int height;
        Bitmap bitmap;
        int i;
        if (contents == null || img_width > 10000 || img_height > 10000) {
            Log.d(b, "out encodeAsBitmapDecimal too big");
            return null;
        } else if (img_height <= 0 || img_width <= 0) {
            Log.d(b, "too small image!");
            return null;
        } else {
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.ERROR_CORRECTION, el);
            hints.put(EncodeHintType.MARGIN, Integer.valueOf(0));
            hints.put(EncodeHintType.MIXED_ENCODING, Boolean.valueOf(false));
            try {
                BitMatrix result = new MultiFormatWriter().encode(contents, format, 1, 1, hints);
                Log.d(b, String.format("id=%s,img_width=%d,img_height=%d,result(%d,%d),type=%s", new Object[]{contents, Integer.valueOf(img_width), Integer.valueOf(img_height), Integer.valueOf(result.getWidth()), Integer.valueOf(result.getHeight()), format}));
                int width2 = result.getWidth();
                int height2 = result.getHeight();
                int[] pixels = new int[(width2 * height2)];
                for (int y = 0; y < height2; y++) {
                    int offset = y * width2;
                    for (int x = 0; x < width2; x++) {
                        int i2 = offset + x;
                        if (result.get(x, y)) {
                            i = -16777216;
                        } else {
                            i = backcolor;
                        }
                        pixels[i2] = i;
                    }
                }
                if (format == BarcodeFormat.QR_CODE) {
                    a(pixels, width2, cornerColor);
                }
                Bitmap bitmap2 = Bitmap.createBitmap(width2, height2, Config.ARGB_8888);
                bitmap2.setPixels(pixels, 0, width2, 0, 0, width2, height2);
                if (format == BarcodeFormat.QR_CODE) {
                    width = Math.min((img_height / height2) * height2, (img_width / width2) * width2);
                    height = width;
                } else {
                    width = img_width;
                    height = height2 * (img_height / height2);
                }
                if (softResample || isMeizuPro6Plus()) {
                    bitmap = a(bitmap2, backcolor, width, height);
                } else {
                    Bitmap tmp_bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                    Paint p = new Paint();
                    p.setAntiAlias(false);
                    p.setDither(false);
                    new Canvas(tmp_bitmap).drawBitmap(bitmap2, null, new Rect(0, 0, tmp_bitmap.getWidth(), tmp_bitmap.getHeight()), p);
                    bitmap2.recycle();
                    bitmap = tmp_bitmap;
                }
                if (format == BarcodeFormat.QR_CODE && avatar != null) {
                    bitmap = a(bitmap, avatar);
                }
                String displayedCode = a(contents);
                if (format != BarcodeFormat.CODE_128) {
                    return bitmap;
                }
                Canvas canvas = new Canvas(bitmap);
                Paint p2 = new Paint();
                Log.d("===", "contents = " + displayedCode);
                p2.setColor(backcolor);
                p2.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
                canvas.drawRect(0.0f, (float) ((height - (width / 16)) - 4), (float) width, (float) height, p2);
                p2.setXfermode(new PorterDuffXfermode(Mode.SRC));
                p2.setColor(-16777216);
                p2.setAntiAlias(true);
                if (TextUtils.equals(barcodeDisplayText, "DONT_DRAW_TEXT")) {
                    return bitmap;
                }
                if (!TextUtils.isEmpty(barcodeDisplayText)) {
                    String displayText = barcodeDisplayText;
                    p2.setTextSize((float) (width / 20));
                    p2.setColor(-10981992);
                    Canvas canvas2 = canvas;
                    String str = displayText;
                    canvas2.drawText(str, (((float) width) - p2.measureText(displayText)) / 2.0f, (float) (height - 10), p2);
                    return bitmap;
                }
                String displayText2 = displayedCode;
                p2.setTextSize((float) (width / 16));
                int textLength = displayText2.length();
                float padding = (float) (width / 6);
                float space = (((float) width) - (2.0f * padding)) / ((float) textLength);
                for (int i3 = 0; i3 < textLength; i3++) {
                    String s = displayText2.substring(i3, i3 + 1);
                    canvas.drawText(s, (((float) i3) * space) + padding, (float) (height - 4), p2);
                }
                return bitmap;
            } catch (IllegalArgumentException iae) {
                Log.e(b, "out 2 encodeAsBitmapDecimal", iae);
                return null;
            }
        }
    }

    private static Bitmap a(Bitmap bitmap, int backColor, int width, int height) {
        int wSrc = bitmap.getWidth();
        int hSrc = bitmap.getHeight();
        double wTimes = (((double) width) * 1.0d) / ((double) bitmap.getWidth());
        double hTimes = (((double) height) * 1.0d) / ((double) bitmap.getHeight());
        int[] pixels = new int[(bitmap.getWidth() * bitmap.getHeight())];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        Bitmap tmp_bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(tmp_bitmap);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        canvas.drawColor(backColor);
        for (int i = 0; i < wSrc; i++) {
            int left = (int) Math.floor(((double) i) * wTimes);
            int right = (int) Math.floor(((double) (i + 1)) * wTimes);
            for (int j = 0; j < hSrc; j++) {
                if (pixels[(j * wSrc) + i] != backColor) {
                    canvas.drawRect((float) left, (float) ((int) Math.floor(((double) j) * hTimes)), (float) right, (float) ((int) Math.floor(((double) (j + 1)) * hTimes)), paint);
                }
            }
        }
        bitmap.recycle();
        return tmp_bitmap;
    }

    private static void a(int[] pixels, int size, int cornerColor) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pixels[((i + 2) * size) + j + 2] = cornerColor;
                pixels[(((size - 3) - i) * size) + j + 2] = cornerColor;
                pixels[((i + 2) * size) + ((size - 3) - j)] = cornerColor;
            }
        }
    }

    private static Bitmap a(Bitmap bitmap, Bitmap avatar) {
        Canvas c2 = new Canvas(bitmap);
        int width = c2.getWidth();
        int height = c2.getHeight();
        float ss = ((float) Math.min(width, height)) * 0.17391305f;
        Rect src = new Rect(0, 0, avatar.getWidth(), avatar.getHeight());
        RectF dst = new RectF((((float) width) - ss) / 2.0f, (((float) height) - ss) / 2.0f, (((float) width) + ss) / 2.0f, (((float) height) + ss) / 2.0f);
        Paint p = new Paint(1);
        p.setFilterBitmap(true);
        p.setDither(true);
        c2.drawBitmap(avatar, src, dst, p);
        return bitmap;
    }

    private static String a(String code) {
        if (TextUtils.isEmpty(code)) {
            return "";
        }
        if (code.length() >= 20 || code.length() <= 12) {
            return code;
        }
        StringBuilder stringBuffer = new StringBuilder(code);
        stringBuffer.insert(12, Token.SEPARATOR);
        stringBuffer.insert(8, Token.SEPARATOR);
        stringBuffer.insert(4, Token.SEPARATOR);
        String ret = stringBuffer.toString();
        Log.d("facepayment", " format code " + code + " to " + ret);
        return ret;
    }

    public static Bitmap genCodeToImageView(String contents, BarcodeFormat format, int backColor, ImageView iv, boolean rotate, int width, int height, ErrorCorrectionLevel errorCorrectionLevel, Bitmap avatar, int cornerColor, String barcodeDisplayText) {
        return genCodeToImageView(contents, format, backColor, iv, rotate, width, height, errorCorrectionLevel, avatar, cornerColor, barcodeDisplayText, false);
    }

    public static Bitmap genCodeToImageView(String contents, BarcodeFormat format, int backColor, ImageView iv, boolean rotate, int width, int height, ErrorCorrectionLevel errorCorrectionLevel, Bitmap avatar, int cornerColor, String barcodeDisplayText, boolean softResample) {
        Bitmap bitmap;
        if (iv.getVisibility() != 0 || (iv.getWidth() <= 0 && width <= 0)) {
            Log.d(b, "out for iv.getWidth() = " + iv.getWidth());
            return null;
        } else if (rotate) {
            try {
                Bitmap bitmap2 = a(contents, format, backColor, height, width, errorCorrectionLevel, avatar, cornerColor, barcodeDisplayText, softResample);
                if (bitmap2 == null) {
                    return null;
                }
                try {
                    Matrix m = new Matrix();
                    m.postRotate(90.0f);
                    Bitmap tmp_bitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), m, true);
                    bitmap2.recycle();
                    bitmap = tmp_bitmap;
                    iv.setImageMatrix(m);
                    iv.setImageBitmap(bitmap);
                    return bitmap;
                } catch (Exception e) {
                    e = e;
                }
            } catch (Exception e2) {
                e = e2;
                bitmap = null;
            }
        } else {
            if (BarcodeFormat.QR_CODE == format && width > height) {
                width = height;
            }
            Bitmap bitmap3 = a(contents, format, backColor, width, height, errorCorrectionLevel, avatar, cornerColor, barcodeDisplayText, softResample);
            if (bitmap3 == null) {
                return null;
            }
            iv.setImageMatrix(null);
            iv.setImageBitmap(bitmap3);
            return bitmap3;
        }
        Log.w(b, e);
        return bitmap;
    }

    public static Bitmap genCodeToImageView(String contents, BarcodeFormat format, int backColor, ImageView iv, boolean rotate, int width, int height, ErrorCorrectionLevel errorCorrectionLevel, Bitmap avatar, int cornerColor) {
        return genCodeToImageView(contents, format, backColor, iv, rotate, width, height, a, avatar, cornerColor, null);
    }

    public static Bitmap genCodeToImageView(String contents, BarcodeFormat format, int backColor, ImageView iv, boolean rotate, Bitmap avatar, int cornerColor) {
        return genCodeToImageView(contents, format, backColor, iv, rotate, (iv.getWidth() - iv.getPaddingLeft()) - iv.getPaddingRight(), (iv.getHeight() - iv.getPaddingTop()) - iv.getPaddingBottom(), a, avatar, cornerColor);
    }

    public static Bitmap genCodeToImageView(String contents, BarcodeFormat format, int backColor, ImageView iv, boolean rotate, Bitmap avatar, int cornerColor, String barcodeDisplayText) {
        return genCodeToImageView(contents, format, backColor, iv, rotate, avatar, cornerColor, barcodeDisplayText, false);
    }

    public static Bitmap genCodeToImageView(String contents, BarcodeFormat format, int backColor, ImageView iv, boolean rotate, Bitmap avatar, int cornerColor, String barcodeDisplayText, boolean softResample) {
        return genCodeToImageView(contents, format, backColor, iv, rotate, (iv.getWidth() - iv.getPaddingLeft()) - iv.getPaddingRight(), (iv.getHeight() - iv.getPaddingTop()) - iv.getPaddingBottom(), a, avatar, cornerColor, barcodeDisplayText, softResample);
    }

    public static Bitmap createCodeBitmap(String contents, BarcodeFormat format, int backColor, int img_width, int img_height, ErrorCorrectionLevel el, Bitmap avatar, int cornerColor) {
        return createCodeBitmap(contents, format, backColor, img_width, img_height, el, avatar, cornerColor, null);
    }

    public static Bitmap createCodeBitmap(String contents, BarcodeFormat format, int backColor, int img_width, int img_height, ErrorCorrectionLevel el, Bitmap avatar, int cornerColor, String barcodeDisplayText) {
        return createCodeBitmap(contents, format, backColor, img_width, img_height, el, avatar, cornerColor, barcodeDisplayText, false);
    }

    public static Bitmap createCodeBitmap(String contents, BarcodeFormat format, int backColor, int img_width, int img_height, ErrorCorrectionLevel el, Bitmap avatar, int cornerColor, String barcodeDisplayText, boolean softResample) {
        try {
            return a(contents, format, backColor, img_width, img_height, el, avatar, cornerColor, barcodeDisplayText, softResample);
        } catch (WriterException e) {
            Log.e(b, "", e);
            return null;
        }
    }

    public static boolean isMeizuPro6Plus() {
        if (c == null) {
            boolean ret = (Build.MANUFACTURER + Build.MODEL).equals("MeizuPRO 6 Plus");
            c = new Boolean(ret);
            Log.d(b, "init is MeizuPro6P,isMeizuPro6Plus: " + ret);
        }
        Log.d(b, "isMeizuPro6Plus: " + c);
        return c.booleanValue();
    }
}
