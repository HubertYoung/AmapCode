package com.alipay.multimedia.mediaplayer.service;

import com.alipay.multimedia.common.logging.MLog;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPausedListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPlayProgressUpdateListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnPreparingListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnSeekingListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnStartListener;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService.OnStopListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnBufferingUpdateListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnCompletionListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnErrorListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnInfoListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnPreparedListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnSeekCompleteListener;
import com.alipay.multimedia.mediaplayer.service.utils.WeakArrayList;

public class MediaPlayerListenerProxy implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener {
    private static final String TAG = "MediaPlayerListenerProxy";
    private int mBufPercent = -1;
    protected WeakArrayList<APMediaPlayerService.OnBufferingUpdateListener> mOnBufferingUpdateListeners;
    protected WeakArrayList<APMediaPlayerService.OnCompletionListener> mOnCompletionListeners;
    protected WeakArrayList<APMediaPlayerService.OnErrorListener> mOnErrorListeners;
    protected WeakArrayList<APMediaPlayerService.OnInfoListener> mOnInfoListeners;
    protected WeakArrayList<OnPausedListener> mOnPausedListeners;
    protected WeakArrayList<OnPlayProgressUpdateListener> mOnPlayProgressUpdateListeners;
    protected WeakArrayList<APMediaPlayerService.OnPreparedListener> mOnPreparedListeners;
    protected WeakArrayList<OnPreparingListener> mOnPreparingListeners;
    protected WeakArrayList<APMediaPlayerService.OnSeekCompleteListener> mOnSeekCompleteListeners;
    protected WeakArrayList<OnSeekingListener> mOnSeekingListeners;
    protected WeakArrayList<OnStartListener> mOnStartListeners;
    protected WeakArrayList<OnStopListener> mOnStopListeners;
    protected APMediaPlayerService mPlayerService;

    public MediaPlayerListenerProxy(APMediaPlayerService playerService) {
        this.mPlayerService = playerService;
        this.mOnPreparingListeners = new WeakArrayList<>();
        this.mOnPreparedListeners = new WeakArrayList<>();
        this.mOnBufferingUpdateListeners = new WeakArrayList<>();
        this.mOnCompletionListeners = new WeakArrayList<>();
        this.mOnErrorListeners = new WeakArrayList<>();
        this.mOnInfoListeners = new WeakArrayList<>();
        this.mOnSeekingListeners = new WeakArrayList<>();
        this.mOnSeekCompleteListeners = new WeakArrayList<>();
        this.mOnPlayProgressUpdateListeners = new WeakArrayList<>();
        this.mOnStartListeners = new WeakArrayList<>();
        this.mOnStopListeners = new WeakArrayList<>();
        this.mOnPausedListeners = new WeakArrayList<>();
    }

    public void addOnPreparingListener(OnPreparingListener listener) {
        if (listener != null && !this.mOnPreparingListeners.contains(listener)) {
            this.mOnPreparingListeners.add(listener);
        }
    }

    public void addOnPreparedListener(APMediaPlayerService.OnPreparedListener listener) {
        if (listener != null && !this.mOnPreparedListeners.contains(listener)) {
            this.mOnPreparedListeners.add(listener);
        }
    }

    public void addOnCompletionListener(APMediaPlayerService.OnCompletionListener listener) {
        if (listener != null && !this.mOnCompletionListeners.contains(listener)) {
            this.mOnCompletionListeners.add(listener);
        }
    }

    public void addOnBufferingUpdateListener(APMediaPlayerService.OnBufferingUpdateListener listener) {
        if (listener != null && !this.mOnBufferingUpdateListeners.contains(listener)) {
            this.mOnBufferingUpdateListeners.add(listener);
        }
    }

    public void addOnSeekingListener(OnSeekingListener listener) {
        if (listener != null && !this.mOnSeekingListeners.contains(listener)) {
            this.mOnSeekingListeners.add(listener);
        }
    }

    public void addOnSeekCompleteListener(APMediaPlayerService.OnSeekCompleteListener listener) {
        if (listener != null && !this.mOnSeekCompleteListeners.contains(listener)) {
            this.mOnSeekCompleteListeners.add(listener);
        }
    }

    public void addOnErrorListener(APMediaPlayerService.OnErrorListener listener) {
        if (listener != null && !this.mOnErrorListeners.contains(listener)) {
            this.mOnErrorListeners.add(listener);
        }
    }

    public void addOnInfoListener(APMediaPlayerService.OnInfoListener listener) {
        if (listener != null && !this.mOnInfoListeners.contains(listener)) {
            this.mOnInfoListeners.add(listener);
        }
    }

    public void addOnPlayProgressUpdateListener(OnPlayProgressUpdateListener listener) {
        if (listener != null && !this.mOnPlayProgressUpdateListeners.contains(listener)) {
            this.mOnPlayProgressUpdateListeners.add(listener);
        }
    }

    public void addOnStartListener(OnStartListener listener) {
        if (listener != null && !this.mOnStartListeners.contains(listener)) {
            this.mOnStartListeners.add(listener);
        }
    }

    public void addOnStopListener(OnStopListener listener) {
        if (listener != null && !this.mOnStopListeners.contains(listener)) {
            this.mOnStopListeners.add(listener);
        }
    }

    public void addOnPausedListener(OnPausedListener listener) {
        if (listener != null && !this.mOnPausedListeners.contains(listener)) {
            this.mOnPausedListeners.add(listener);
        }
    }

    public void removeOnPreparingListener(OnPreparingListener listener) {
        if (listener != null) {
            this.mOnPreparingListeners.remove(listener);
        }
    }

    public void removeOnPreparedListener(APMediaPlayerService.OnPreparedListener listener) {
        if (listener != null) {
            this.mOnPreparedListeners.remove(listener);
        }
    }

    public void removeOnCompletionListener(APMediaPlayerService.OnCompletionListener listener) {
        if (listener != null) {
            this.mOnCompletionListeners.remove(listener);
        }
    }

    public void removeOnBufferingUpdateListener(APMediaPlayerService.OnBufferingUpdateListener listener) {
        if (listener != null) {
            this.mOnBufferingUpdateListeners.remove(listener);
        }
    }

    public void removeOnSeekingListener(OnSeekingListener listener) {
        if (listener != null) {
            this.mOnSeekingListeners.remove(listener);
        }
    }

    public void removeOnSeekCompleteListener(APMediaPlayerService.OnSeekCompleteListener listener) {
        if (listener != null) {
            this.mOnSeekCompleteListeners.remove(listener);
        }
    }

    public void removeOnErrorListener(APMediaPlayerService.OnErrorListener listener) {
        if (listener != null) {
            this.mOnErrorListeners.remove(listener);
        }
    }

    public void removeOnInfoListener(APMediaPlayerService.OnInfoListener listener) {
        if (listener != null) {
            this.mOnInfoListeners.remove(listener);
        }
    }

    public void removeOnPlayProgressUpdateListener(OnPlayProgressUpdateListener listener) {
        if (listener != null) {
            this.mOnPlayProgressUpdateListeners.remove(listener);
        }
    }

    public void removeOnStartListener(OnStartListener listener) {
        if (listener != null) {
            this.mOnStartListeners.remove(listener);
        }
    }

    public void removeOnStopListener(OnStopListener listener) {
        if (listener != null) {
            this.mOnStopListeners.remove(listener);
        }
    }

    public void removeOnPausedListener(OnPausedListener listener) {
        if (listener != null) {
            this.mOnPausedListeners.remove(listener);
        }
    }

    private boolean checkMediaPlayer(IMediaPlayer player) {
        if (this.mPlayerService != null && (this.mPlayerService instanceof APBaseMediaPlayerService)) {
            IMediaPlayer currentPlayer = ((APBaseMediaPlayerService) this.mPlayerService).getCurrentMediaPlayer();
            if (!(currentPlayer == null || currentPlayer == player)) {
                MLog.e(TAG, "checkMediaPlayer return false.");
                return false;
            }
        }
        return true;
    }

    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        if (this.mBufPercent != percent) {
            MLog.i(TAG, "onBufferingUpdate percent: " + percent);
            this.mBufPercent = percent;
        }
        if (checkMediaPlayer(mp)) {
            for (APMediaPlayerService.OnBufferingUpdateListener onBufferingUpdate : this.mOnBufferingUpdateListeners.values()) {
                onBufferingUpdate.onBufferingUpdate(this.mPlayerService, this.mPlayerService.getDataSource(), percent);
            }
        }
    }

    public void onCompletion(IMediaPlayer mp) {
        MLog.i(TAG, "onCompletion mp: " + mp);
        if (checkMediaPlayer(mp)) {
            if (!checkAgain(mp)) {
                onError(mp, -1004, -1);
                MLog.e(TAG, "checkAgain fail, notify error, return!");
                return;
            }
            for (APMediaPlayerService.OnCompletionListener onCompletion : this.mOnCompletionListeners.values()) {
                onCompletion.onCompletion(this.mPlayerService, this.mPlayerService.getDataSource());
            }
        }
    }

    private boolean checkAgain(IMediaPlayer mp) {
        MLog.e("TestForPlayPercent", "percent: " + this.mPlayerService.getBufferedPercent());
        if (mp == null || (!mp.isSeekToTheEnd() && this.mPlayerService.getBufferedPercent() != 100 && this.mPlayerService.getBufferedPercent() != 0)) {
            return false;
        }
        return true;
    }

    public boolean onError(IMediaPlayer mp, int what, int extra) {
        MLog.e(TAG, "onError mp: " + mp + ", what: " + what + ", extra: " + extra);
        if (checkMediaPlayer(mp)) {
            if (1 != what || !(this.mPlayerService instanceof APBaseMediaPlayerService) || ((APBaseMediaPlayerService) this.mPlayerService).getCurrentMediaPlayer() == mp) {
                for (APMediaPlayerService.OnErrorListener listener : this.mOnErrorListeners.values()) {
                    if (!(listener instanceof APBaseMediaPlayerService)) {
                        APBaseMediaPlayerService service = null;
                        if (this.mPlayerService != null && (this.mPlayerService instanceof APBaseMediaPlayerService)) {
                            service = (APBaseMediaPlayerService) this.mPlayerService;
                        }
                        if (service != null && !service.shouldNotifyBusiness()) {
                        }
                    }
                    listener.onError(this.mPlayerService, this.mPlayerService.getDataSource(), what, extra);
                }
            } else {
                MLog.e(TAG, "onError ignore previous player error!! mp: " + mp);
            }
        }
        return true;
    }

    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        MLog.i(TAG, "onInfo mp: " + mp + ", what: " + what + ", extra: " + extra);
        if (checkMediaPlayer(mp)) {
            for (APMediaPlayerService.OnInfoListener onInfo : this.mOnInfoListeners.values()) {
                onInfo.onInfo(this.mPlayerService, this.mPlayerService.getDataSource(), what, extra);
            }
        }
        return true;
    }

    public void onPrepared(IMediaPlayer mp) {
        MLog.i(TAG, "onPrepared mp: " + mp);
        if (checkMediaPlayer(mp)) {
            for (APMediaPlayerService.OnPreparedListener onPrepared : this.mOnPreparedListeners.values()) {
                onPrepared.onPrepared(this.mPlayerService, this.mPlayerService.getDataSource());
            }
        }
    }

    public void onSeekComplete(IMediaPlayer mp) {
        MLog.i(TAG, "onSeekComplete mp: " + mp);
        if (checkMediaPlayer(mp)) {
            for (APMediaPlayerService.OnSeekCompleteListener onSeekComplete : this.mOnSeekCompleteListeners.values()) {
                onSeekComplete.onSeekComplete(this.mPlayerService, this.mPlayerService.getDataSource());
            }
        }
    }

    public void notifySeeking(String source) {
        MLog.i(TAG, "notifySeeking source: " + source);
        for (OnSeekingListener onSeeking : this.mOnSeekingListeners.values()) {
            onSeeking.onSeeking(this.mPlayerService, source);
        }
    }

    public void notifyPreparing(String source) {
        MLog.i(TAG, "notifyPreparing source: " + source);
        for (OnPreparingListener onPreparing : this.mOnPreparingListeners.values()) {
            onPreparing.onPreparing(this.mPlayerService, source);
        }
    }

    public void notifyProgressUpdate(String source, int duration, int currentPosition) {
        MLog.i(TAG, "notifyProgressUpdate source: " + source + ", duration: " + duration + ", currentPosition: " + currentPosition);
        for (OnPlayProgressUpdateListener onProgressUpdate : this.mOnPlayProgressUpdateListeners.values()) {
            onProgressUpdate.onProgressUpdate(this.mPlayerService, source, duration, currentPosition);
        }
    }

    public void notifyStart(String source) {
        MLog.i(TAG, "notifyStart source: " + source);
        for (OnStartListener onStart : this.mOnStartListeners.values()) {
            onStart.onStart(this.mPlayerService, source);
        }
    }

    public void notifyStop(String source) {
        MLog.i(TAG, "notifyStop source: " + source);
        for (OnStopListener onStop : this.mOnStopListeners.values()) {
            onStop.onStop(this.mPlayerService, source);
        }
    }

    public void notifyPaused(String source) {
        MLog.i(TAG, "notifyPaused source: " + source);
        for (OnPausedListener onPaused : this.mOnPausedListeners.values()) {
            onPaused.onPaused(this.mPlayerService, source);
        }
    }

    public void notifyError(String source, int what, int extra) {
        MLog.i(TAG, "notifyError source: " + source);
        for (APMediaPlayerService.OnErrorListener listener : this.mOnErrorListeners.values()) {
            if (!(listener instanceof APBaseMediaPlayerService)) {
                APBaseMediaPlayerService service = null;
                if (this.mPlayerService != null && (this.mPlayerService instanceof APBaseMediaPlayerService)) {
                    service = (APBaseMediaPlayerService) this.mPlayerService;
                }
                if (service != null && !service.shouldNotifyBusiness()) {
                }
            }
            listener.onError(this.mPlayerService, source, what, extra);
        }
    }
}
