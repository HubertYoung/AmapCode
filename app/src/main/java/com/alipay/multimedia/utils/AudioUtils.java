package com.alipay.multimedia.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Build.VERSION;
import com.alipay.multimedia.common.logging.MLog;

public class AudioUtils {
    private static final String TAG = "AudioUtils";

    public static void pauseSystemAudio(Context context) {
        muteAudioFocus(context, true);
    }

    public static void resumeSystemAudio(Context context) {
        muteAudioFocus(context, false);
    }

    public static boolean isMusicActive(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        return audioManager != null && audioManager.isMusicActive();
    }

    @TargetApi(8)
    private static boolean muteAudioFocus(Context context, boolean bMute) {
        boolean bool = true;
        if (context == null) {
            MLog.e(TAG, "context is null.");
            return false;
        } else if (VERSION.SDK_INT < 8) {
            MLog.e(TAG, "Android 2.1 and below can not stop music");
            return false;
        } else {
            AudioManager am = (AudioManager) context.getSystemService("audio");
            if (bMute) {
                if (am.requestAudioFocus(null, 3, 1) != 1) {
                    bool = false;
                }
            } else if (am.abandonAudioFocus(null) != 1) {
                bool = false;
            }
            MLog.e(TAG, "pauseMusic bMute=" + bMute + " result=" + bool);
            return bool;
        }
    }

    public static void registerAudioFocusChangedListener(Context context, OnAudioFocusChangeListener l) {
        try {
            ((AudioManager) context.getSystemService("audio")).requestAudioFocus(l, 3, 1);
            MLog.d(TAG, "registerAudioFocusChangedListener listener: " + l);
        } catch (Exception e) {
            MLog.e(TAG, "registerAudioFocusChangedListener context: " + context + ", listener: " + l + ", error", e);
        }
    }

    public static void unregisterAudioFocusChangedListener(Context context, OnAudioFocusChangeListener l) {
        try {
            ((AudioManager) context.getSystemService("audio")).abandonAudioFocus(l);
            MLog.d(TAG, "unregisterAudioFocusChangedListener listener: " + l);
        } catch (Exception e) {
            MLog.e(TAG, "unregisterAudioFocusChangedListener context: " + context + ", listener: " + l + ", error", e);
        }
    }

    public static float getMusicVolume(Context context) {
        float volume = 0.5f;
        try {
            AudioManager am = (AudioManager) context.getSystemService("audio");
            int maxVolume = am.getStreamMaxVolume(3);
            int curVolume = am.getStreamVolume(3);
            volume = ((float) Math.round((((float) curVolume) / ((float) maxVolume)) * 100.0f)) / 100.0f;
            MLog.d(TAG, "getMusicVolume curVolume=" + curVolume + ";maxVolume=" + maxVolume + "；volume=" + volume);
            return volume;
        } catch (Exception e) {
            MLog.e(TAG, "getMusicVolume context: " + context + ", error", e);
            return volume;
        }
    }

    public static void setMusicVolume(Context context, float volume) {
        float destVol = volume;
        try {
            if (Float.compare(volume, 1.0f) == 1) {
                destVol = 1.0f;
            } else if (Float.compare(volume, 0.0f) == -1) {
                destVol = 0.0f;
            }
            AudioManager am = (AudioManager) context.getSystemService("audio");
            int maxVolume = am.getStreamMaxVolume(3);
            int curVolume = am.getStreamVolume(3);
            int desVolume = (int) (((float) maxVolume) * destVol);
            am.setStreamVolume(3, desVolume, 4);
            MLog.d(TAG, "setMusicVolume curVolume=" + curVolume + ";desVolume=" + desVolume + ";volume=" + destVol);
        } catch (Exception e) {
            MLog.e(TAG, "getMusicVolume context: " + context + ", volume=" + volume + ", error", e);
        }
    }
}
