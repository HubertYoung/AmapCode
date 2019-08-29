package com.alipay.mobile.beehive.audio.v2;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.beehive.audio.model.AudioDetail;
import com.alipay.mobile.beehive.audio.utils.BundleLogger;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.multimedia.apxmmusic.APMusicPlayerService;
import com.alipay.multimedia.apxmmusic.PlayError;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnBufferingUpdateListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnCompletionListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnErrorListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnInfoListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPausedListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPlayProgressUpdateListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPreparedListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPreparingListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnSeekCompleteListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnSeekingListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnStartListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnStopListener;
import java.util.HashMap;
import java.util.Map;

public abstract class AudioPlayerStateDetector implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPausedListener, OnPlayProgressUpdateListener, OnPreparedListener, OnPreparingListener, OnSeekCompleteListener, OnSeekingListener, OnStartListener, OnStopListener {
    public static final String CARE_EVERY_SONG = "#CARE_EVERY_SONG#";
    public static final String KEY_AUDIO_EVENT = "audioEvent";
    private static final String ON_CAN_PLAY = "onBackgroundAudioCanPlay";
    private static final String ON_ENDED = "onBackgroundAudioEnded";
    public static final String ON_ERROR = "onBackgroundAudioError";
    private static final String ON_PAUSE = "onBackgroundAudioPause";
    private static final String ON_PLAY = "onBackgroundAudioPlay";
    private static final String ON_SEEKED = "onBackgroundAudioSeeked";
    private static final String ON_SEEKING = "onBackgroundAudioSeeking";
    private static final String ON_STOP = "onBackgroundAudioStop";
    private static final String ON_TIME_UPDATE = "onBackgroundAudioTimeUpdate";
    private static final String ON_WAITING = "onBackgroundAudioWaiting";
    private boolean isActive;
    private String mCareWhichSong;
    private BundleLogger mLogger = BundleLogger.getLogger(AudioPlayerStateDetector.class);
    private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    protected APMusicPlayerService mMusicPlayerService;

    /* access modifiers changed from: protected */
    public abstract void onStateChange(AudioDetail audioDetail, PlayerState playerState, Map<String, Object> map);

    public AudioPlayerStateDetector(String url) {
        this.mLogger.d("AudioPlayerStateDetector:### " + url);
        this.mMusicPlayerService = (APMusicPlayerService) MicroServiceUtil.getMicroService(APMusicPlayerService.class);
        this.mCareWhichSong = url;
    }

    public void active() {
        runOnMainThread(new Runnable() {
            public final void run() {
                AudioPlayerStateDetector.this.queryFirst();
                AudioPlayerStateDetector.this.doActive();
            }
        });
    }

    public String getCareWhichSong() {
        return this.mCareWhichSong;
    }

    /* access modifiers changed from: private */
    public void queryFirst() {
        AudioDetail sd = GlobalAudioPlayer.getInstance().getLatestSongDetail();
        int src = this.mMusicPlayerService.getMediaPlayerState();
        PlayerState state = wrapState(src);
        String currentSong = this.mMusicPlayerService.getDataSource();
        if (sd != null && !TextUtils.equals(sd.url, currentSong)) {
            this.mLogger.w("NOT MATCH! Music player DataSrc = " + currentSong + ",songDetail url = " + sd.url);
        }
        this.mLogger.d("AudioStateLink## middle = " + state + " src = " + src);
        onStateChange(sd, state, buildExtraMap(parseEvent(state)));
    }

    @NonNull
    private String parseEvent(PlayerState state) {
        switch (state) {
            case PREPARING:
                return ON_WAITING;
            case PLAYING:
                return ON_PLAY;
            case PAUSING:
                return ON_PAUSE;
            case STOPPED:
                return ON_STOP;
            case ERROR:
                return ON_ERROR;
            case COMPLETE:
                return ON_ENDED;
            default:
                return null;
        }
    }

    private Map<String, Object> buildExtraMap(String event) {
        Map ret = new HashMap();
        if (!TextUtils.isEmpty(event)) {
            ret.put(KEY_AUDIO_EVENT, event);
        }
        return ret;
    }

    /* access modifiers changed from: private */
    public void doActive() {
        this.mMusicPlayerService.addOnBufferingUpdateListener(this);
        this.mMusicPlayerService.addOnCompletionListener(this);
        this.mMusicPlayerService.addOnErrorListener(this);
        this.mMusicPlayerService.addOnInfoListener(this);
        this.mMusicPlayerService.addOnPlayProgressUpdateListener(this);
        this.mMusicPlayerService.addOnPausedListener(this);
        this.mMusicPlayerService.addOnPreparingListener(this);
        this.mMusicPlayerService.addOnSeekCompleteListener(this);
        this.mMusicPlayerService.addOnPreparedListener(this);
        this.mMusicPlayerService.addOnStopListener(this);
        this.mMusicPlayerService.addOnSeekingListener(this);
        this.mMusicPlayerService.addOnStartListener(this);
        this.isActive = true;
    }

    public void disActive() {
        runOnMainThread(new Runnable() {
            public final void run() {
                AudioPlayerStateDetector.this.doDisActive();
            }
        });
    }

    /* access modifiers changed from: private */
    public void doDisActive() {
        this.mMusicPlayerService.removeOnBufferingUpdateListener(this);
        this.mMusicPlayerService.removeOnCompletionListener(this);
        this.mMusicPlayerService.removeOnErrorListener(this);
        this.mMusicPlayerService.removeOnInfoListener(this);
        this.mMusicPlayerService.removeOnPlayProgressUpdateListener(this);
        this.mMusicPlayerService.removeOnPausedListener(this);
        this.mMusicPlayerService.removeOnPreparingListener(this);
        this.mMusicPlayerService.removeOnSeekCompleteListener(this);
        this.mMusicPlayerService.removeOnPreparedListener(this);
        this.mMusicPlayerService.removeOnStopListener(this);
        this.mMusicPlayerService.removeOnSeekingListener(this);
        this.mMusicPlayerService.removeOnStartListener(this);
        this.isActive = false;
    }

    private void runOnMainThread(Runnable job) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainThreadHandler.post(job);
        } else {
            job.run();
        }
    }

    public void onStart(APMediaPlayerService apMediaPlayerService, String s) {
        onAudioStateUpdate(s, ON_PLAY);
    }

    public void onBufferingUpdate(APMediaPlayerService apMediaPlayerService, String s, int i) {
        onAudioStateUpdate(s, "");
    }

    public void onCompletion(APMediaPlayerService apMediaPlayerService, String s) {
        onAudioStateUpdate(s, ON_ENDED);
    }

    public void onError(APMediaPlayerService apMediaPlayerService, String s, int i, int i1) {
        Map extra = new HashMap();
        PlayError error = this.mMusicPlayerService.getError();
        if (error != null) {
            extra = new HashMap();
            extra.put("errorCode", Integer.valueOf(error.errorCode));
            this.mLogger.d("ErrorCode : " + error.errorCode);
        }
        onAudioStateUpdate(s, ON_ERROR, extra);
    }

    public void onInfo(APMediaPlayerService apMediaPlayerService, String s, int what, int extra) {
        String event = null;
        if (701 == what) {
            event = ON_WAITING;
        }
        onAudioStateUpdate(s, event);
    }

    public void onPaused(APMediaPlayerService apMediaPlayerService, String s) {
        onAudioStateUpdate(s, ON_PAUSE);
    }

    public void onProgressUpdate(APMediaPlayerService apMediaPlayerService, String s, int i, int i1) {
        onAudioStateUpdate(s, ON_TIME_UPDATE);
    }

    public void onPrepared(APMediaPlayerService apMediaPlayerService, String s) {
        onAudioStateUpdate(s, ON_CAN_PLAY);
    }

    public void onPreparing(APMediaPlayerService apMediaPlayerService, String s) {
        onAudioStateUpdate(s, ON_WAITING);
    }

    public void onSeekComplete(APMediaPlayerService apMediaPlayerService, String s) {
        onAudioStateUpdate(s, ON_SEEKED);
    }

    public void onStop(APMediaPlayerService apMediaPlayerService, String s) {
        onAudioStateUpdate(s, ON_STOP);
    }

    public void onSeeking(APMediaPlayerService apMediaPlayerService, String s) {
        onAudioStateUpdate(s, ON_SEEKING);
    }

    private void onAudioStateUpdate(String url, String newEvent) {
        onAudioStateUpdate(url, newEvent, null);
    }

    private void onAudioStateUpdate(String url, String caller, Map<String, Object> extra) {
        if (isCareEverySong(this.mCareWhichSong) || TextUtils.equals(this.mCareWhichSong, url)) {
            int src = this.mMusicPlayerService.getMediaPlayerState();
            PlayerState wrappedState = wrapState(src);
            AudioDetail sd = GlobalAudioPlayer.getInstance().getLatestSongDetail();
            if (sd == null) {
                this.mLogger.d("Player state = " + wrappedState + ",but no audioInfo exist,Have u called playAudio before?");
                return;
            }
            if (sd.duration <= 0) {
                sd.duration = this.mMusicPlayerService.getDuration();
                this.mLogger.d("Update audio duration to " + sd.duration);
            }
            sd.playedTime = this.mMusicPlayerService.getCurrentPosition();
            sd.bufferedPercent = this.mMusicPlayerService.getBufferedPercent();
            this.mLogger.d("AudioStateLink## middle = " + wrappedState + " src = " + src);
            Map e = buildExtraMap(caller);
            if (extra != null && !extra.isEmpty()) {
                e.putAll(extra);
            }
            onStateChange(sd, wrappedState, e);
            return;
        }
        this.mLogger.d("Care " + this.mCareWhichSong + ",ignore " + url + " update.");
    }

    private boolean isCareEverySong(String url) {
        return TextUtils.equals(url, CARE_EVERY_SONG);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public static PlayerState wrapState(int playerState) {
        switch (playerState) {
            case 0:
            case 7:
                return PlayerState.STOPPED;
            case 1:
            case 2:
            case 4:
            case 5:
            case 9:
            case 10:
                return PlayerState.PREPARING;
            case 3:
                return PlayerState.PLAYING;
            case 6:
                return PlayerState.PAUSING;
            case 8:
                return PlayerState.COMPLETE;
            case 11:
                return PlayerState.ERROR;
            default:
                return PlayerState.COMPLETE;
        }
    }
}
