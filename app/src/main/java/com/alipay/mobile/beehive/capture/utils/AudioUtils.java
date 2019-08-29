package com.alipay.mobile.beehive.capture.utils;

import android.annotation.TargetApi;
import android.media.AudioManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class AudioUtils {
    public static final String CMDNAME = "command";
    public static final String CMDNEXT = "next";
    public static final String CMDPAUSE = "pause";
    public static final String CMDPLAY = "play";
    public static final String CMDPREVIOUS = "previous";
    public static final String CMDSTOP = "stop";
    public static final String CMDTOGGLEPAUSE = "togglepause";
    public static final String SERVICECMD = "com.android.music.musicservicecommand";

    public static void pauseSystemAudio() {
        muteAudioFocus(true);
    }

    public static void resumeSystemAudio() {
        muteAudioFocus(false);
    }

    @TargetApi(8)
    public static boolean muteAudioFocus(boolean bMute) {
        AudioManager am = (AudioManager) LauncherApplicationAgent.getInstance().getApplicationContext().getApplicationContext().getSystemService("audio");
        if (bMute) {
            if (am.requestAudioFocus(null, 3, 2) == 1) {
                return true;
            }
            return false;
        } else if (am.abandonAudioFocus(null) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
