package com.alipay.multimedia.mediaplayer.service;

import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.multimedia.apxmmusic.PlayError;
import com.alipay.multimedia.common.adapter.AdapterInitial;
import com.alipay.multimedia.common.config.ConfigCenter;
import com.alipay.multimedia.common.config.ConfigConstants;
import com.alipay.multimedia.common.config.item.PlayerConf;
import com.alipay.multimedia.common.logging.MLog;
import com.alipay.multimedia.common.logging.PlayerBehavior;
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
import com.alipay.multimedia.mediaplayer.service.player.AndroidMediaPlayer;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer;
import com.alipay.multimedia.network.LocalNetworkProxy;
import com.alipay.multimedia.utils.AppUtils;
import com.alipay.multimedia.utils.AudioUtils;
import com.alipay.multimedia.utils.MusicUtils;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.io.IOException;

public class APBaseMediaPlayerService implements OnAudioFocusChangeListener, APMediaPlayerService, OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPlayProgressUpdateListener, OnPreparedListener, OnSeekCompleteListener, OnStopListener {
    private static final String LIVE_TYPE_HLS = ".m3u8";
    private static final int MEDIA_INVALIDATE = 11100;
    private static final int MSG_UPDATE_PROGRESS = 0;
    private static final String TAG = "MediaPlayerService";
    private boolean bHls = false;
    private boolean bLoop = false;
    private long lastSeekPos = -1;
    private int mBufferedPercent = 0;
    protected volatile IMediaPlayer mCurrentMediaPlayer;
    private long mDuration;
    private PlayError mError = null;
    private Bundle mExtra;
    protected String mFileId;
    protected Handler mHandler;
    private String mJsonExtra;
    protected MediaPlayerListenerProxy mListenerProxy;
    protected boolean mPaused = false;
    private boolean mPausedByOutside = false;
    protected String mPlayUrl;
    private PlayerBehavior mPlayerBehavior;
    protected PlayerConf mPlayerConf = new PlayerConf();
    protected boolean mPreparingToPause = false;
    protected String mPreparingUrl;
    private String mReportExtra;
    private String mSource;
    protected int mState = 0;
    private int mStateBeforePause = 0;
    private boolean resumePlayAfterSeekCompleted = false;
    private int startPostion;

    private class PlayerHandler extends Handler {
        public PlayerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    handleUpdateProgress();
                    return;
                default:
                    return;
            }
        }

        private void handleUpdateProgress() {
            if (APBaseMediaPlayerService.this.getMediaPlayerState() == 3) {
                APBaseMediaPlayerService.this.mListenerProxy.notifyProgressUpdate(APBaseMediaPlayerService.this.getDataSource(), (int) APBaseMediaPlayerService.this.getDuration(), (int) APBaseMediaPlayerService.this.getCurrentPosition());
            } else {
                APBaseMediaPlayerService.this.mHandler.sendEmptyMessageDelayed(0, (long) APBaseMediaPlayerService.this.mPlayerConf.progressUpdateInterval);
            }
        }
    }

    private class PlayerReleaseTask implements Runnable {
        private IMediaPlayer mPlayer;

        public PlayerReleaseTask(IMediaPlayer player) {
            this.mPlayer = player;
        }

        public void run() {
            if (this.mPlayer != null) {
                try {
                    this.mPlayer.reset();
                    this.mPlayer.release();
                } catch (Throwable e) {
                    MLog.e(APBaseMediaPlayerService.TAG, "player release error.e=" + e);
                }
            }
        }
    }

    public APBaseMediaPlayerService() {
        initNecessary();
        initListenerProxy();
        initListeners();
        this.bLoop = false;
    }

    /* access modifiers changed from: protected */
    public void initNecessary() {
        this.mHandler = new PlayerHandler(Looper.getMainLooper());
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public void run() {
                APBaseMediaPlayerService.this.mPlayerConf = (PlayerConf) ConfigCenter.get().getConfig(ConfigConstants.KEY_PLAYER);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initListenerProxy() {
        this.mListenerProxy = new MediaPlayerListenerProxy(this);
    }

    /* access modifiers changed from: protected */
    public void initListeners() {
        addOnPreparedListener(this);
        addOnPlayProgressUpdateListener(this);
        addOnCompletionListener(this);
        addOnErrorListener(this);
        addOnSeekCompleteListener(this);
        addOnBufferingUpdateListener(this);
        addOnInfoListener(this);
        addOnStopListener(this);
    }

    /* access modifiers changed from: protected */
    public void registerMediaPlayerListeners(IMediaPlayer player) {
        player.setOnBufferingUpdateListener(this.mListenerProxy);
        player.setOnPreparedListener(this.mListenerProxy);
        player.setOnInfoListener(this.mListenerProxy);
        player.setOnCompletionListener(this.mListenerProxy);
        player.setOnErrorListener(this.mListenerProxy);
        player.setOnSeekCompleteListener(this.mListenerProxy);
    }

    public void setDataSource(String source) {
        setDataSource(source, null);
    }

    public void setDataSource(String source, Bundle extra) {
        String jsonExtra = null;
        if (extra != null) {
            JSONObject object = (JSONObject) extra.get("bizExtraParamsJsonObj");
            if (object != null) {
                jsonExtra = object.toJSONString();
            }
        }
        setDataSource(source, extra, jsonExtra);
    }

    public void setDataSource(String source, Bundle extra, String jsonExtra) {
        MLog.d(TAG, "setDataSource: " + source + ", extra: " + extra + ",jsonExtra:" + jsonExtra);
        this.mPlayUrl = source;
        this.mSource = source;
        this.mExtra = extra;
        this.mJsonExtra = jsonExtra;
        String url = null;
        try {
            if (!TextUtils.isEmpty(jsonExtra)) {
                JSONObject object = JSON.parseObject(jsonExtra);
                url = object.getString("url");
                this.mFileId = object.getString("fid");
                this.mReportExtra = object.getString("extraRemoteLog");
            }
            if (!TextUtils.isEmpty(url)) {
                this.mPlayUrl = url;
            }
        } catch (Throwable e) {
            MLog.e(TAG, "parse json error.e=" + e);
        }
    }

    private String getRealDataSource() {
        if (this.mCurrentMediaPlayer == null) {
            return null;
        }
        String source = this.mCurrentMediaPlayer.getDataSource();
        if (!TextUtils.isEmpty(source)) {
            return LocalNetworkProxy.getInstance().getRealUrl(source);
        }
        return null;
    }

    public String getDataSource() {
        return this.mSource;
    }

    public void start() {
        this.lastSeekPos = -1;
        this.mPausedByOutside = false;
        this.mPreparingToPause = false;
        if (TextUtils.isEmpty(this.mPlayUrl) || this.mCurrentMediaPlayer == null || !this.mPlayUrl.equals(getRealDataSource()) || !isPreparing()) {
            MLog.d(TAG, "start paused? " + this.mPaused + ", url: " + this.mPlayUrl + ", player: " + this.mCurrentMediaPlayer + ", src: " + getDataSource() + ", startPos=" + this.startPostion);
            if ((isPaused() || this.resumePlayAfterSeekCompleted) && !TextUtils.isEmpty(this.mPlayUrl) && this.mCurrentMediaPlayer != null && this.mPlayUrl.equals(getRealDataSource())) {
                registerAudioFocusChangeListener();
                this.mListenerProxy.notifyStart(getDataSource());
                this.mCurrentMediaPlayer.start();
                if (this.mStateBeforePause == 4) {
                    this.mState = this.mStateBeforePause;
                    this.mStateBeforePause = 0;
                } else {
                    this.mState = 3;
                }
                this.mHandler.sendEmptyMessage(0);
                if (this.mPlayerBehavior != null) {
                    this.mPlayerBehavior.resume();
                }
            } else {
                if (isNeedStop()) {
                    stop();
                }
                this.mDuration = 0;
                this.mCurrentMediaPlayer = createMediaPlayer();
                this.mState = 1;
                this.mListenerProxy.notifyPreparing(getDataSource());
                this.mPreparingUrl = this.mPlayUrl;
                this.mPlayerBehavior = new PlayerBehavior(this.mPreparingUrl, this.mExtra, this.mReportExtra);
                if (MusicUtils.isEncrptedMusic(this.mFileId)) {
                    this.mPlayerBehavior.encrypted = 1;
                }
                this.mPlayerBehavior.startPlay();
                try {
                    if (this.mPreparingUrl.contains(LIVE_TYPE_HLS) || this.bHls) {
                        this.mCurrentMediaPlayer.setDataSource(this.mPreparingUrl);
                    } else {
                        this.mCurrentMediaPlayer.setDataSource(LocalNetworkProxy.getInstance().genLocalUrl(this.mPreparingUrl, this.mJsonExtra));
                    }
                    this.mPlayerBehavior.startPreparing();
                    this.mCurrentMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    MLog.e(TAG, "start setDataSource error", e);
                    this.mState = 11;
                    this.mListenerProxy.onError(this.mCurrentMediaPlayer, 11, -1000);
                    return;
                }
            }
            this.mPaused = false;
            return;
        }
        MLog.d(TAG, "start at preparing same playUrl: " + this.mPlayUrl + ", ignore~~~");
    }

    public void setStartPosition(int startPosition) {
        this.startPostion = startPosition;
        MLog.d(TAG, "setStartPosition startPostion: " + this.startPostion);
    }

    /* access modifiers changed from: protected */
    public IMediaPlayer getCurrentMediaPlayer() {
        return this.mCurrentMediaPlayer;
    }

    private IMediaPlayer createMediaPlayer() {
        if (this.mCurrentMediaPlayer != null) {
            AdapterInitial.getAdapterFactory().Executor().execute((Runnable) new PlayerReleaseTask(this.mCurrentMediaPlayer));
            this.mCurrentMediaPlayer = null;
        }
        IMediaPlayer player = new AndroidMediaPlayer();
        MLog.d(TAG, "createMediaPlayer mp: " + player + ";bLoop=" + this.bLoop);
        registerMediaPlayerListeners(player);
        player.setLooping(this.bLoop);
        return player;
    }

    /* access modifiers changed from: protected */
    public boolean isNeedStop() {
        return isPlaying() || isPaused() || isPreparing();
    }

    private void registerAudioFocusChangeListener() {
        AudioUtils.registerAudioFocusChangedListener(AppUtils.getApplication(), this);
    }

    private void unregisterAudioFocusChangeListener() {
        AudioUtils.unregisterAudioFocusChangedListener(AppUtils.getApplication(), this);
    }

    public void stop() {
        stop(getDataSource());
    }

    /* access modifiers changed from: protected */
    public void stop(String dataSource) {
        MLog.d(TAG, "stop start, url: " + dataSource + ", state: " + this.mState);
        if (this.mPlayerBehavior != null) {
            this.mPlayerBehavior.submit();
        }
        this.mDuration = 0;
        this.lastSeekPos = -1;
        this.mPausedByOutside = false;
        long start = System.currentTimeMillis();
        this.mPaused = false;
        removeProgressUpdateMsg();
        reset();
        this.mState = 7;
        if (dataSource != null && dataSource.equalsIgnoreCase(getDataSource())) {
            this.mListenerProxy.notifyStop(dataSource);
        }
        LocalNetworkProxy.getInstance().stopMusicFile(false);
        MLog.d(TAG, "stop end..., cost: " + (System.currentTimeMillis() - start));
    }

    public void pause() {
        MLog.d(TAG, "pause, state: " + this.mState + ", player: " + this.mCurrentMediaPlayer + ", url: " + getDataSource());
        this.mPausedByOutside = false;
        this.lastSeekPos = -1;
        if (this.mCurrentMediaPlayer != null) {
            try {
                this.mCurrentMediaPlayer.pause();
            } catch (Throwable e) {
                MLog.e(TAG, "pause exception=" + e.getMessage());
            }
        }
        this.mPaused = true;
        if (this.mState == 1) {
            this.mPreparingToPause = true;
        } else {
            this.mPreparingToPause = false;
        }
        this.mStateBeforePause = this.mState;
        this.mState = 6;
        removeProgressUpdateMsg();
        this.mListenerProxy.notifyPaused(getDataSource());
        if (this.mPlayerBehavior != null) {
            this.mPlayerBehavior.pause();
        }
    }

    public boolean isPlaying() {
        return this.mCurrentMediaPlayer != null && this.mCurrentMediaPlayer.isPlaying();
    }

    /* access modifiers changed from: protected */
    public boolean isPaused() {
        return this.mPaused;
    }

    /* access modifiers changed from: protected */
    public boolean isPreparing() {
        return 1 == this.mState;
    }

    public void seekTo(int msec) {
        MLog.d(TAG, "seekTo: " + msec + ", state: " + this.mState);
        this.lastSeekPos = (long) msec;
        if (!canSeek() || this.mCurrentMediaPlayer == null) {
            this.mListenerProxy.notifyError(getDataSource(), 1, 0);
            return;
        }
        this.mCurrentMediaPlayer.seekTo(msec);
        this.mState = 9;
        this.mListenerProxy.notifySeeking(getDataSource());
    }

    private boolean canSeek() {
        return (this.mState == 1 || this.mState == 11 || this.mState == 7 || this.mState == 0) ? false : true;
    }

    public long getCurrentPosition() {
        long position = 0;
        if (this.mCurrentMediaPlayer != null && (this.mState == 9 || this.mState == 10 || isPlaying() || isPaused())) {
            try {
                position = this.mCurrentMediaPlayer.getCurrentPosition();
            } catch (Throwable throwable) {
                MLog.w(TAG, "getCurrentPosition error, throwable: " + throwable);
                position = 0;
            }
        }
        if (position <= 0 && this.lastSeekPos > 0 && this.mState != 11 && this.mState != 7) {
            position = this.lastSeekPos;
        }
        if (this.mState != 8 || this.mDuration <= 0) {
            return position;
        }
        return this.mDuration;
    }

    public long getDuration() {
        long duration = 0;
        if (this.mCurrentMediaPlayer != null && (3 == getMediaPlayerState() || 6 == getMediaPlayerState() || 2 == getMediaPlayerState())) {
            if (this.mPreparingToPause && 6 == getMediaPlayerState()) {
                return 0;
            }
            duration = this.mCurrentMediaPlayer.getDuration();
        }
        if (duration > 0) {
            this.mDuration = duration;
        } else if (this.mDuration > 0) {
            duration = this.mDuration;
        }
        return duration;
    }

    public float getVolume() {
        return AudioUtils.getMusicVolume(AppUtils.getApplication());
    }

    public void reset() {
        MLog.i(TAG, "reset..");
        this.startPostion = 0;
        this.lastSeekPos = 0;
        this.mPausedByOutside = false;
        this.mDuration = 0;
        unregisterAudioFocusChangeListener();
        this.mState = 0;
        this.mPreparingToPause = false;
        if (this.mCurrentMediaPlayer != null) {
            AdapterInitial.getAdapterFactory().Executor().execute((Runnable) new PlayerReleaseTask(this.mCurrentMediaPlayer));
            this.mCurrentMediaPlayer = null;
        }
        this.mPaused = false;
    }

    public int getMediaPlayerState() {
        return this.mState;
    }

    public int getStartPosition() {
        return this.startPostion;
    }

    public int getBufferedPercent() {
        return this.mBufferedPercent;
    }

    public PlayError getError() {
        if (this.mState == 11) {
            return this.mError;
        }
        return null;
    }

    public void setLooping(boolean looping) {
        this.bLoop = looping;
        MLog.d(TAG, "setLooping looping: " + looping + ";player=" + this.mCurrentMediaPlayer);
        if (this.mCurrentMediaPlayer != null) {
            this.mCurrentMediaPlayer.setLooping(looping);
        }
    }

    public void setVolume(float volume) {
        AudioUtils.setMusicVolume(AppUtils.getApplication(), volume);
    }

    public void setHls(boolean isHls) {
        this.bHls = isHls;
    }

    public boolean isHls() {
        return (!TextUtils.isEmpty(this.mPreparingUrl) && this.mPreparingUrl.contains(LIVE_TYPE_HLS)) || this.bHls;
    }

    public void addOnPreparingListener(OnPreparingListener listener) {
        this.mListenerProxy.addOnPreparingListener(listener);
    }

    public void addOnPreparedListener(OnPreparedListener listener) {
        this.mListenerProxy.addOnPreparedListener(listener);
    }

    public void addOnCompletionListener(OnCompletionListener listener) {
        this.mListenerProxy.addOnCompletionListener(listener);
    }

    public void addOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        this.mListenerProxy.addOnBufferingUpdateListener(listener);
    }

    public void addOnSeekingListener(OnSeekingListener listener) {
        this.mListenerProxy.addOnSeekingListener(listener);
    }

    public void addOnSeekCompleteListener(OnSeekCompleteListener listener) {
        this.mListenerProxy.addOnSeekCompleteListener(listener);
    }

    public void addOnErrorListener(OnErrorListener listener) {
        this.mListenerProxy.addOnErrorListener(listener);
    }

    public void addOnInfoListener(OnInfoListener listener) {
        this.mListenerProxy.addOnInfoListener(listener);
    }

    public void addOnPlayProgressUpdateListener(OnPlayProgressUpdateListener listener) {
        this.mListenerProxy.addOnPlayProgressUpdateListener(listener);
    }

    public void addOnStartListener(OnStartListener listener) {
        this.mListenerProxy.addOnStartListener(listener);
    }

    public void addOnStopListener(OnStopListener listener) {
        this.mListenerProxy.addOnStopListener(listener);
    }

    public void addOnPausedListener(OnPausedListener listener) {
        this.mListenerProxy.addOnPausedListener(listener);
    }

    public void removeOnPreparingListener(OnPreparingListener listener) {
        this.mListenerProxy.removeOnPreparingListener(listener);
    }

    public void removeOnPreparedListener(OnPreparedListener listener) {
        this.mListenerProxy.removeOnPreparedListener(listener);
    }

    public void removeOnCompletionListener(OnCompletionListener listener) {
        this.mListenerProxy.removeOnCompletionListener(listener);
    }

    public void removeOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        this.mListenerProxy.removeOnBufferingUpdateListener(listener);
    }

    public void removeOnSeekingListener(OnSeekingListener listener) {
        this.mListenerProxy.removeOnSeekingListener(listener);
    }

    public void removeOnSeekCompleteListener(OnSeekCompleteListener listener) {
        this.mListenerProxy.removeOnSeekCompleteListener(listener);
    }

    public void removeOnErrorListener(OnErrorListener listener) {
        this.mListenerProxy.removeOnErrorListener(listener);
    }

    public void removeOnInfoListener(OnInfoListener listener) {
        this.mListenerProxy.removeOnInfoListener(listener);
    }

    public void removeOnPlayProgressUpdateListener(OnPlayProgressUpdateListener listener) {
        this.mListenerProxy.removeOnPlayProgressUpdateListener(listener);
    }

    public void removeOnStartListener(OnStartListener listener) {
        this.mListenerProxy.removeOnStartListener(listener);
    }

    public void removeOnStopListener(OnStopListener listener) {
        this.mListenerProxy.removeOnStopListener(listener);
    }

    public void removeOnPausedListener(OnPausedListener listener) {
        this.mListenerProxy.removeOnPausedListener(listener);
    }

    public void onPrepared(APMediaPlayerService service, String source) {
        long pos;
        MLog.d(TAG, "onPrepared.source=" + source);
        this.mPaused = false;
        if (this.mPreparingUrl == null || !this.mPreparingUrl.equals(this.mPlayUrl) || !(this.mState == 1 || this.mState == 3 || this.mState == 5 || this.mState == 4)) {
            stop();
            return;
        }
        int preState = this.mState;
        this.mState = 2;
        this.mPreparingToPause = false;
        registerAudioFocusChangeListener();
        if (this.startPostion > 0) {
            long dur = getDuration();
            if (((long) this.startPostion) > dur) {
                pos = dur;
            } else {
                pos = (long) this.startPostion;
            }
            seekTo((int) pos);
            this.startPostion = 0;
        }
        this.mListenerProxy.notifyStart(getDataSource());
        this.mCurrentMediaPlayer.start();
        this.mHandler.sendEmptyMessageDelayed(0, (long) this.mPlayerConf.progressUpdateInterval);
        this.mState = 3;
        fixPlayState(preState);
        if (this.mPlayerBehavior != null) {
            this.mPlayerBehavior.duration = getDuration();
            this.mPlayerBehavior.preparedFinished();
        }
    }

    private void fixPlayState(int preState) {
        if (preState == 6) {
            pause();
        }
    }

    public void onProgressUpdate(APMediaPlayerService service, String source, int duration, int currentPosition) {
        this.mHandler.removeMessages(0);
        if (isPlaying()) {
            this.mHandler.sendEmptyMessageDelayed(0, (long) this.mPlayerConf.progressUpdateInterval);
        }
    }

    public void onCompletion(APMediaPlayerService service, String source) {
        MLog.i(TAG, "onCompletion.source=" + source);
        if (this.mPlayerBehavior != null) {
            this.mPlayerBehavior.submit();
        }
        this.mPaused = false;
        this.mState = 8;
        this.mPreparingToPause = false;
        removeProgressUpdateMsg();
        LocalNetworkProxy.getInstance().stopMusicFile(false);
    }

    public void onError(APMediaPlayerService service, String source, int what, int extra) {
        if (!this.mPreparingToPause) {
            this.lastSeekPos = 0;
            this.mDuration = 0;
            this.mPaused = false;
            this.mPausedByOutside = false;
            removeProgressUpdateMsg();
            this.mState = 11;
            this.mPreparingToPause = false;
            this.mError = new PlayError(what);
            int err = LocalNetworkProxy.getInstance().getErrorCode();
            if (err != 0) {
                this.mError.errorCode = err;
                MLog.i(TAG, "onError.switch error to:" + err);
            }
            if (what == 1) {
                LocalNetworkProxy.getInstance().stopMusicFile(true);
            } else {
                LocalNetworkProxy.getInstance().stopMusicFile(false);
            }
            if (this.mPlayerBehavior != null) {
                this.mPlayerBehavior.code = what;
                this.mPlayerBehavior.netCode = err;
                this.mPlayerBehavior.submit();
            }
        } else if (this.mCurrentMediaPlayer != null) {
            AdapterInitial.getAdapterFactory().Executor().execute((Runnable) new PlayerReleaseTask(this.mCurrentMediaPlayer));
            this.mCurrentMediaPlayer = null;
        }
    }

    public void onBufferingUpdate(APMediaPlayerService service, String source, int percent) {
        this.mBufferedPercent = percent;
    }

    public void onInfo(APMediaPlayerService service, String source, int what, int extra) {
        switch (what) {
            case 701:
                if (!isPaused()) {
                    this.mState = 4;
                }
                if (this.mPlayerBehavior != null) {
                    this.mPlayerBehavior.startBuffering();
                }
                this.mPreparingToPause = false;
                break;
            case 702:
                if (!isPaused()) {
                    this.mState = 3;
                } else {
                    this.mState = 5;
                }
                if (this.mPlayerBehavior != null) {
                    this.mPlayerBehavior.bufferedFinished();
                }
                this.mPreparingToPause = false;
                break;
        }
        MLog.d(TAG, "onInfo source: " + source + ", what: " + what + ", extra: " + extra + ", state: " + this.mState);
    }

    public void onSeekComplete(APMediaPlayerService service, String source) {
        MLog.d(TAG, "onSeekComplete source: " + source + ", mState: " + this.mState);
        if (this.mState == 9) {
            try {
                if (!isPlaying()) {
                    this.resumePlayAfterSeekCompleted = true;
                    start();
                    this.resumePlayAfterSeekCompleted = false;
                }
                this.mPreparingToPause = false;
                this.mState = 3;
            } catch (Throwable th) {
                this.mState = 11;
            }
        } else if (isPlaying()) {
            this.mState = 3;
        } else if (isPaused()) {
            if (this.mState == 1) {
                this.mPreparingToPause = true;
            } else {
                this.mPreparingToPause = false;
            }
            this.mState = 6;
        } else {
            this.mState = 10;
        }
    }

    private void removeProgressUpdateMsg() {
        this.mHandler.removeMessages(0);
    }

    public void onAudioFocusChange(int focusChange) {
        MLog.d(TAG, "onAudioFocusChange focusChange: " + focusChange);
        switch (focusChange) {
            case -3:
            case -2:
            case -1:
                doAudioFocusLoss();
                return;
            case 1:
            case 2:
            case 3:
            case 4:
                doAudioFocusGain();
                return;
            default:
                return;
        }
    }

    public void onStop(APMediaPlayerService service, String source) {
        this.mState = 7;
        this.mPreparingToPause = false;
        this.mPausedByOutside = false;
    }

    public IMediaPlayer getMediaPlayer() {
        return this.mCurrentMediaPlayer;
    }

    public boolean shouldNotifyBusiness() {
        if (this.mPreparingToPause) {
            MLog.i(TAG, "mPreparingToPause is true, donot notify business.");
            return false;
        }
        MLog.i(TAG, "shouldNotifyBusiness return true.");
        return true;
    }

    public void invalidate() {
        if (this.mListenerProxy != null) {
            this.mListenerProxy.onInfo(this.mCurrentMediaPlayer, 11100, 0);
        }
    }

    private void doAudioFocusGain() {
        if (getMediaPlayerState() == 6 && this.mPausedByOutside) {
            start();
        }
        MLog.i(TAG, "doAudioFocusGain ");
    }

    private void doAudioFocusLoss() {
        if (isPlaying()) {
            pause();
            this.mPausedByOutside = true;
        }
        MLog.i(TAG, "doAudioFocusLoss");
    }
}
