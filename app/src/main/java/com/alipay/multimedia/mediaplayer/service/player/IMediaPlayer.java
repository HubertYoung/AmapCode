package com.alipay.multimedia.mediaplayer.service.player;

import android.os.Bundle;

public interface IMediaPlayer {
    public static final int MEDIA_ERROR_FORBIDDEN = -2046;
    public static final int MEDIA_ERROR_IO = -1004;

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i);
    }

    public interface OnCompletionListener {
        void onCompletion(IMediaPlayer iMediaPlayer);
    }

    public interface OnErrorListener {
        boolean onError(IMediaPlayer iMediaPlayer, int i, int i2);
    }

    public interface OnInfoListener {
        boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2);
    }

    public interface OnPreparedListener {
        void onPrepared(IMediaPlayer iMediaPlayer);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete(IMediaPlayer iMediaPlayer);
    }

    long getCurrentPosition();

    String getDataSource();

    long getDuration();

    Bundle getExtra();

    boolean isPlaying();

    boolean isSeekToTheEnd();

    void pause();

    void prepareAsync();

    void release();

    void reset();

    void seekTo(int i);

    void setDataSource(String str);

    void setExtra(Bundle bundle);

    void setLooping(boolean z);

    void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    void setOnCompletionListener(OnCompletionListener onCompletionListener);

    void setOnErrorListener(OnErrorListener onErrorListener);

    void setOnInfoListener(OnInfoListener onInfoListener);

    void setOnPreparedListener(OnPreparedListener onPreparedListener);

    void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void start();

    void stop();
}
