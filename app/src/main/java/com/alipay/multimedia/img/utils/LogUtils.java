package com.alipay.multimedia.img.utils;

import com.alipay.alipaylogger.Log;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig;

public class LogUtils {
    public static void printCfg(String tag, PictureBaseConfig cfg, String extra) {
        try {
            Log.d(tag, "cfg: " + cfg.getClass().getName() + ", mi: " + cfg.needMirror + ", ash: " + cfg.useAshMem + ", sw: " + cfg.srcWidth + ", sh: " + cfg.srcHeight + ", dw: " + cfg.dstWidth + ", dh: " + cfg.dstHeight + ", cx: " + cfg.cropX + ", cy: " + cfg.cropY + ", cm: " + cfg.cropMode + ", max: " + cfg.maxDimension + ", min: " + cfg.minDimension + ", ro: " + cfg.rotate + ", ext: " + extra);
        } catch (Throwable th) {
        }
    }

    public static void v(String tag, String msg) {
        try {
            Log.v(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void d(String tag, String msg) {
        try {
            Log.d(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void i(String tag, String msg) {
        try {
            Log.i(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void w(String tag, String msg) {
        try {
            Log.w(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void e(String tag, String msg) {
        try {
            Log.e(tag, msg);
        } catch (Throwable th) {
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        try {
            Log.e(tag, msg, tr);
        } catch (Throwable th) {
        }
    }

    public static String getDeviceId() {
        return Log.getDeviceId();
    }
}
