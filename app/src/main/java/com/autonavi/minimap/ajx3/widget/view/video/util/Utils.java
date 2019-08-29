package com.autonavi.minimap.ajx3.widget.view.video.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.System;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.ajx3.util.FileUtil;

public class Utils {
    private static boolean DEBUG = true;
    private static final int MAX_BRIGHTNESS = 255;
    private static int MAX_VOLUME = 0;
    private static final String UNKNOWN_SIZE = "00:00";

    public static int getMaxBrightness() {
        return 255;
    }

    public static void log(String str) {
    }

    public static void logTouch(String str) {
    }

    public static boolean needLandscape(int i, int i2) {
        return false;
    }

    public static void setDebug(boolean z) {
        DEBUG = z;
    }

    public static String formatVideoTimeLength(long j) {
        String str;
        String str2;
        String str3;
        int i = (int) (j / 1000);
        if (i == 0) {
            return "00:00";
        }
        if (i < 60) {
            StringBuilder sb = new StringBuilder("00:");
            sb.append(i < 10 ? "0".concat(String.valueOf(i)) : Integer.valueOf(i));
            return sb.toString();
        } else if (i < 3600) {
            long j2 = (long) (i % 60);
            long j3 = (long) (i / 60);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(j3 < 10 ? "0".concat(String.valueOf(j3)) : String.valueOf(j3));
            sb2.append(":");
            if (j2 < 10) {
                str3 = "0".concat(String.valueOf(j2));
            } else {
                str3 = String.valueOf(j2);
            }
            sb2.append(str3);
            return sb2.toString();
        } else {
            long j4 = (long) (i / 3600);
            int i2 = i % 3600;
            long j5 = (long) (i2 / 60);
            long j6 = (long) (i2 % 60);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(j4 < 10 ? "0".concat(String.valueOf(j4)) : String.valueOf(j4));
            sb3.append(":");
            if (j5 < 10) {
                str = "0".concat(String.valueOf(j5));
            } else {
                str = String.valueOf(j5);
            }
            sb3.append(str);
            sb3.append(":");
            if (j6 < 10) {
                str2 = "0".concat(String.valueOf(j6));
            } else {
                str2 = String.valueOf(j6);
            }
            sb3.append(str2);
            return sb3.toString();
        }
    }

    public static void showViewIfNeed(View view) {
        if (view.getVisibility() == 8 || view.getVisibility() == 4) {
            view.setVisibility(0);
        }
    }

    public static void hideViewIfNeed(View view) {
        if (view.getVisibility() == 0) {
            view.setVisibility(8);
        }
    }

    public static boolean isViewShown(View view) {
        return view.getVisibility() == 0;
    }

    public static boolean isViewHide(View view) {
        return view.getVisibility() == 8 || view.getVisibility() == 4;
    }

    public static Activity getActivity(Context context) {
        while (context != null) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            if (!(context instanceof ContextWrapper)) {
                return null;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static int getWindowWidth(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        if (VERSION.SDK_INT >= 17) {
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            return point.x;
        } else if (VERSION.SDK_INT < 13) {
            return defaultDisplay.getWidth();
        } else {
            Point point2 = new Point();
            defaultDisplay.getSize(point2);
            return point2.x;
        }
    }

    public static int getWindowHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static String getCacheDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/autonavi/tmp/videocache");
        return sb.toString();
    }

    public static boolean isSDCardAvailable() {
        if (FileUtil.checkSDcard()) {
            try {
                return Environment.getExternalStorageDirectory().canWrite();
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static boolean isConnected(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public static void setBrightness(Window window, float f) {
        LayoutParams attributes = window.getAttributes();
        attributes.screenBrightness = f;
        window.setAttributes(attributes);
    }

    public static float changeBrightness(Window window, float f) {
        LayoutParams attributes = window.getAttributes();
        float maxBrightness = ((f * 1.0f) / ((float) getMaxBrightness())) + attributes.screenBrightness;
        if (maxBrightness >= 1.0f) {
            maxBrightness = 1.0f;
        }
        if (maxBrightness <= 0.0f) {
            maxBrightness = 0.0f;
        }
        attributes.screenBrightness = maxBrightness;
        window.setAttributes(attributes);
        return maxBrightness;
    }

    public static int getSystemBrightness(Context context) {
        return System.getInt(context.getContentResolver(), "screen_brightness", 255);
    }

    public static void startAutoBrightness(Context context) {
        System.putInt(context.getContentResolver(), "screen_brightness_mode", 1);
        Uri uriFor = System.getUriFor("screen_brightness");
        if (uriFor != null) {
            context.getContentResolver().notifyChange(uriFor, null);
        }
    }

    public static void stopAutoBrightness(Context context) {
        System.putInt(context.getContentResolver(), "screen_brightness_mode", 0);
        Uri uriFor = System.getUriFor("screen_brightness");
        if (uriFor != null) {
            context.getContentResolver().notifyChange(uriFor, null);
        }
    }

    public static int getMaxVolume(Context context) {
        if (MAX_VOLUME <= 0) {
            MAX_VOLUME = ((AudioManager) context.getSystemService("audio")).getStreamMaxVolume(3);
        }
        if (MAX_VOLUME <= 0) {
            MAX_VOLUME = 15;
        }
        return MAX_VOLUME;
    }

    public static int getCurrentVolume(Context context) {
        return ((AudioManager) context.getSystemService("audio")).getStreamVolume(3);
    }

    public static void setVolume(Context context, int i) {
        ((AudioManager) context.getSystemService("audio")).setStreamVolume(3, i, 0);
    }

    public static int changeVolume(Context context, int i) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        int maxVolume = getMaxVolume(context);
        int streamVolume = i + audioManager.getStreamVolume(3);
        if (streamVolume < maxVolume) {
            maxVolume = streamVolume;
        }
        if (maxVolume <= 0) {
            maxVolume = 0;
        }
        audioManager.setStreamVolume(3, maxVolume, 0);
        return maxVolume;
    }
}
