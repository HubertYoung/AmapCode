package com.alipay.multimedia.sound;

import android.content.Context;
import android.media.AudioManager;
import com.alipay.multimedia.common.logging.MLog;

public class SoundUtils {
    public static float getSoundVolValue(Context context, int type) {
        if (context == null) {
            return 1.0f;
        }
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            return ((float) audioManager.getStreamVolume(type)) / ((float) audioManager.getStreamMaxVolume(type));
        } catch (Throwable e) {
            MLog.e("SoundUtils", "get stream volume exp=" + e);
            return 1.0f;
        }
    }
}
