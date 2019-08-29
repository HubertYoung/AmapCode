package com.alipay.multimedia.mediaplayer.service;

import android.os.Bundle;
import com.alipay.multimedia.apxmmusic.PlayError;

public interface APMediaPlayerService {
    public static final int STATE_BUFFERED = 5;
    public static final int STATE_BUFFERING = 4;
    public static final int STATE_ERROR = 11;
    public static final int STATE_INIT = 0;
    public static final int STATE_PAUSING = 6;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_PLAY_COMPLETE = 8;
    public static final int STATE_PREPARED = 2;
    public static final int STATE_PREPARING = 1;
    public static final int STATE_SEEKED_COMPLETE = 10;
    public static final int STATE_SEEKING = 9;
    public static final int STATE_STOP = 7;

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(APMediaPlayerService aPMediaPlayerService, String str, int i);
    }

    public interface OnCompletionListener {
        void onCompletion(APMediaPlayerService aPMediaPlayerService, String str);
    }

    public interface OnErrorListener {
        void onError(APMediaPlayerService aPMediaPlayerService, String str, int i, int i2);
    }

    public interface OnInfoListener {
        void onInfo(APMediaPlayerService aPMediaPlayerService, String str, int i, int i2);
    }

    public interface OnPausedListener {
        void onPaused(APMediaPlayerService aPMediaPlayerService, String str);
    }

    public interface OnPlayProgressUpdateListener {
        void onProgressUpdate(APMediaPlayerService aPMediaPlayerService, String str, int i, int i2);
    }

    public interface OnPreparedListener {
        void onPrepared(APMediaPlayerService aPMediaPlayerService, String str);
    }

    public interface OnPreparingListener {
        void onPreparing(APMediaPlayerService aPMediaPlayerService, String str);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete(APMediaPlayerService aPMediaPlayerService, String str);
    }

    public interface OnSeekingListener {
        void onSeeking(APMediaPlayerService aPMediaPlayerService, String str);
    }

    public interface OnStartListener {
        void onStart(APMediaPlayerService aPMediaPlayerService, String str);
    }

    public interface OnStopListener {
        void onStop(APMediaPlayerService aPMediaPlayerService, String str);
    }

    void addOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    void addOnCompletionListener(OnCompletionListener onCompletionListener);

    void addOnErrorListener(OnErrorListener onErrorListener);

    void addOnInfoListener(OnInfoListener onInfoListener);

    void addOnPausedListener(OnPausedListener onPausedListener);

    void addOnPlayProgressUpdateListener(OnPlayProgressUpdateListener onPlayProgressUpdateListener);

    void addOnPreparedListener(OnPreparedListener onPreparedListener);

    void addOnPreparingListener(OnPreparingListener onPreparingListener);

    void addOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void addOnSeekingListener(OnSeekingListener onSeekingListener);

    void addOnStartListener(OnStartListener onStartListener);

    void addOnStopListener(OnStopListener onStopListener);

    int getBufferedPercent();

    long getCurrentPosition();

    String getDataSource();

    long getDuration();

    PlayError getError();

    int getMediaPlayerState();

    int getStartPosition();

    float getVolume();

    boolean isHls();

    boolean isPlaying();

    void pause();

    void removeOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    void removeOnCompletionListener(OnCompletionListener onCompletionListener);

    void removeOnErrorListener(OnErrorListener onErrorListener);

    void removeOnInfoListener(OnInfoListener onInfoListener);

    void removeOnPausedListener(OnPausedListener onPausedListener);

    void removeOnPlayProgressUpdateListener(OnPlayProgressUpdateListener onPlayProgressUpdateListener);

    void removeOnPreparedListener(OnPreparedListener onPreparedListener);

    void removeOnPreparingListener(OnPreparingListener onPreparingListener);

    void removeOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void removeOnSeekingListener(OnSeekingListener onSeekingListener);

    void removeOnStartListener(OnStartListener onStartListener);

    void removeOnStopListener(OnStopListener onStopListener);

    void reset();

    void seekTo(int i);

    void setDataSource(String str);

    void setDataSource(String str, Bundle bundle);

    void setDataSource(String str, Bundle bundle, String str2);

    void setHls(boolean z);

    void setLooping(boolean z);

    void setStartPosition(int i);

    void setVolume(float f);

    void start();

    void stop();
}
