package com.alipay.mobile.beehive.audio.v2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.mobile.beehive.audio.model.AudioDetail;
import com.alipay.mobile.beehive.audio.utils.BundleLogger;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.multimedia.apxmmusic.APMusicPlayerService;

public class GlobalAudioPlayer {
    private static final String ACTION_CHANGE_ACCOUNT = "com.alipay.security.login";
    private static final String ACTION_ONE_POINT_LOGOUT = "com.alipay.android.broadcast.FORCE_LOGOUT_ACTION";
    private static final String ACTION_SAFE_LOGOUT = "com.alipay.security.logout";
    private static GlobalAudioPlayer INSTANCE = null;
    private static final String IS_SWITCH_ACCOUNT = "switchaccount";
    private BroadcastReceiver loginStatusListener = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            GlobalAudioPlayer.this.mLogger.d("loginStatusListener:onReceive:###");
            if (!TextUtils.equals(GlobalAudioPlayer.ACTION_CHANGE_ACCOUNT, intent.getAction()) || intent.getBooleanExtra(GlobalAudioPlayer.IS_SWITCH_ACCOUNT, false)) {
                GlobalAudioPlayer.this.mLogger.d("Perform stop audio.");
                GlobalAudioPlayer.this.stopAudio();
                return;
            }
            GlobalAudioPlayer.this.mLogger.d("Not real change account,do nothing.");
        }
    };
    private AudioDetail mAudioDetail;
    BundleLogger mLogger = BundleLogger.getLogger(GlobalAudioPlayer.class);
    private APMusicPlayerService mMusicService;

    public static synchronized GlobalAudioPlayer getInstance() {
        GlobalAudioPlayer globalAudioPlayer;
        synchronized (GlobalAudioPlayer.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new GlobalAudioPlayer();
                }
                globalAudioPlayer = INSTANCE;
            }
        }
        return globalAudioPlayer;
    }

    private GlobalAudioPlayer() {
        this.mLogger.d("GlobalAudioPlayer:init<>");
        this.mMusicService = (APMusicPlayerService) MicroServiceUtil.getMicroService(APMusicPlayerService.class);
        registerLoginStatueChangeReceiver();
    }

    private void registerLoginStatueChangeReceiver() {
        this.mLogger.d("registerLoginStatueChangeReceiver:###");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CHANGE_ACCOUNT);
        filter.addAction(ACTION_ONE_POINT_LOGOUT);
        filter.addAction(ACTION_SAFE_LOGOUT);
        LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).registerReceiver(this.loginStatusListener, filter);
    }

    public void playAudio(AudioDetail audioDetail) {
        this.mLogger.d("playAudio:###");
        if (audioDetail != null) {
            this.mLogger.d("AudioDetail :" + audioDetail.toString());
        }
        setAudioDetail(audioDetail);
        if (this.mAudioDetail != null && !TextUtils.isEmpty(this.mAudioDetail.url)) {
            if (!TextUtils.equals(this.mAudioDetail.url, this.mMusicService.getDataSource())) {
                this.mMusicService.setDataSource(this.mAudioDetail.url, this.mAudioDetail.extraInfo);
                this.mMusicService.start();
                return;
            }
            this.mLogger.d("Current data src is the same, pending to play.");
            if (!isPlaying()) {
                this.mLogger.d("Not playing,start.");
                this.mMusicService.start();
                return;
            }
            this.mLogger.d("Playing,do nothing.");
        }
    }

    public void playAudio() {
        playAudio(this.mAudioDetail);
    }

    public void setAudioDetail(AudioDetail audioDetail) {
        this.mAudioDetail = audioDetail;
    }

    public void pauseAudio() {
        this.mLogger.d("pauseAudio:###");
        this.mMusicService.pause();
    }

    public void stopAudio() {
        this.mLogger.d("stopAudio:###");
        this.mMusicService.stop();
    }

    public void seekTo(int ms) {
        this.mLogger.d("seekTo:### " + ms);
        this.mMusicService.seekTo(ms);
    }

    public boolean isPlaying() {
        this.mLogger.d("isPlaying:###");
        return this.mMusicService.isPlaying();
    }

    public long getCurrentPosition() {
        this.mLogger.d("getCurrentPosition:###");
        return this.mMusicService.getCurrentPosition();
    }

    public long getDuration() {
        this.mLogger.d("getDuration:###");
        return this.mMusicService.getDuration();
    }

    public void reset() {
        this.mLogger.d("reset:###");
        this.mMusicService.reset();
    }

    public int getMediaPlayerState() {
        this.mLogger.d("getMediaPlayerState:###");
        return this.mMusicService.getMediaPlayerState();
    }

    public int getBufferedPercent() {
        this.mLogger.d("getBufferedPercent:###");
        return this.mMusicService.getBufferedPercent();
    }

    public AudioDetail getLatestSongDetail() {
        return this.mAudioDetail;
    }

    public String getDataSource() {
        this.mLogger.d("getDataSource:###");
        String beehiveUrl = this.mAudioDetail == null ? "" : this.mAudioDetail.url;
        String multimediaUrl = this.mMusicService.getDataSource();
        if (!TextUtils.equals(beehiveUrl, multimediaUrl)) {
            this.mLogger.w("Audio url not equal! beehiveUrl = " + beehiveUrl + ",multimediaUrl = " + multimediaUrl);
        }
        return beehiveUrl;
    }

    public boolean isPaused() {
        return this.mMusicService.getMediaPlayerState() == 6;
    }

    public void notifyUpdate() {
        if (this.mMusicService != null) {
            this.mMusicService.invalidate();
        }
    }

    public void setStartTime(int position) {
        if (this.mMusicService != null) {
            this.mMusicService.setStartPosition(position);
        }
    }

    public int getStartTime() {
        if (this.mMusicService != null) {
            return this.mMusicService.getStartPosition();
        }
        return 0;
    }
}
