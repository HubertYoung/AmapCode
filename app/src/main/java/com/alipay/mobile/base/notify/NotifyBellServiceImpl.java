package com.alipay.mobile.base.notify;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.notify.NotifyBellService;

public class NotifyBellServiceImpl extends NotifyBellService {
    private static final String BUSINESS_NOTIFY = "BusinessNotify";
    private static final long DEFAULT_VIBRATOR_TIME = 700;
    private static final String HOME_TIMELINE_NOTIFY = "HomeTimelineNotify";
    private static final String SOCIAL_NOTIFY = "SocailNotify";
    private static final String SOUND_OPEN = "SoundOpen";
    private static final String SP_NAME = "notifybell";
    private static final String TAG = "NotifyBellServiceImpl";
    private static final String VIBRATION_OPEN = "VibrationOpen";
    public static final String VOICE_PLAY_NOTIFY = "VoicePlayNotify";
    private Editor editor;
    private SharedPreferences mSharedPreferences;
    private Vibrator vibrator;

    public void setSoundOpen(boolean isOpen) {
        if (this.editor != null) {
            this.editor.putBoolean(SOUND_OPEN, isOpen);
            this.editor.commit();
        }
    }

    public boolean isOpenSound() {
        return this.mSharedPreferences.getBoolean(SOUND_OPEN, true);
    }

    public void setVibrationOpen(boolean isOpen) {
        if (this.editor != null) {
            this.editor.putBoolean(VIBRATION_OPEN, isOpen);
            this.editor.commit();
        }
    }

    public boolean isOpenVibration() {
        return this.mSharedPreferences.getBoolean(VIBRATION_OPEN, true);
    }

    public void playSystemAlert() {
        playSound();
    }

    public void playSystemVibrate() {
        playVibration(DEFAULT_VIBRATOR_TIME);
    }

    public void playSystemVibrate(long time) {
        playVibration(time);
    }

    public void playSoundFile(String path) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
        this.mSharedPreferences = getMicroApplicationContext().getApplicationContext().getSharedPreferences(SP_NAME, 0);
        this.editor = this.mSharedPreferences.edit();
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle arg0) {
    }

    public void playVibration(long time) {
        if (this.mSharedPreferences.getBoolean(VIBRATION_OPEN, true)) {
            Context context = getMicroApplicationContext().getApplicationContext().getApplicationContext();
            try {
                if (this.vibrator == null) {
                    this.vibrator = (Vibrator) context.getSystemService("vibrator");
                }
                this.vibrator.vibrate(time);
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().error((String) TAG, (Throwable) e);
            }
        }
    }

    public void playSound() {
        if (this.mSharedPreferences.getBoolean(SOUND_OPEN, true)) {
            Uri alert = RingtoneManager.getDefaultUri(2);
            MediaPlayer player = new MediaPlayer();
            try {
                Context context = getMicroApplicationContext().getApplicationContext().getApplicationContext();
                player.setDataSource(context, alert);
                if (((AudioManager) context.getSystemService("audio")).getStreamVolume(5) != 0) {
                    player.setAudioStreamType(5);
                    player.prepare();
                    player.start();
                    player.setOnCompletionListener(new OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            LoggerFactory.getTraceLogger().info(NotifyBellServiceImpl.TAG, "onCompletion");
                            mp.release();
                        }
                    });
                    player.setOnErrorListener(new OnErrorListener() {
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            LoggerFactory.getTraceLogger().info(NotifyBellServiceImpl.TAG, "onError");
                            mp.release();
                            return false;
                        }
                    });
                }
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().error((String) TAG, (Throwable) e);
            }
        }
    }

    public void setBusinessNotifyOpen(boolean isOpen) {
        if (this.editor != null) {
            this.editor.putBoolean(BUSINESS_NOTIFY, isOpen);
            this.editor.commit();
        }
    }

    public boolean isBusinessNotifyOpen() {
        return this.mSharedPreferences.getBoolean(BUSINESS_NOTIFY, true);
    }

    public void setSocialNotifyOpen(boolean isOpen) {
        if (this.editor != null) {
            this.editor.putBoolean(SOCIAL_NOTIFY, isOpen);
            this.editor.commit();
        }
    }

    public boolean isSocialNotifyOpen() {
        return this.mSharedPreferences.getBoolean(SOCIAL_NOTIFY, true);
    }

    public void setHomeTimelineNotifyOpen(boolean isOpen) {
        if (this.editor != null) {
            this.editor.putBoolean(HOME_TIMELINE_NOTIFY, isOpen);
            this.editor.commit();
        }
    }

    public boolean isHomeTimelineNotifyOpen() {
        return this.mSharedPreferences.getBoolean(HOME_TIMELINE_NOTIFY, true);
    }

    public void setVoicePlayNotifyOpen(boolean isOpen) {
        if (this.editor != null) {
            this.editor.putBoolean(VOICE_PLAY_NOTIFY, isOpen);
            this.editor.commit();
        }
    }

    public boolean isVoicePlayNotifyOpen() {
        return this.mSharedPreferences.getBoolean(VOICE_PLAY_NOTIFY, true);
    }
}
