package com.autonavi.minimap.ajx3.widget.view.video.player;

import android.view.TextureView;

public interface IPlayer {

    public interface PlayCallback {
        void onComplete();

        void onDurationChanged(long j);

        void onError(String str);

        void onInfo(long j, long j2);

        void onPlayStateChanged(int i);

        void onSizeChanged(int i, int i2);
    }

    long getCurrentPosition();

    long getDuration();

    int getState();

    boolean isPlaying();

    void pause();

    void play();

    void release();

    void seekTo(long j);

    void setPlayCallback(PlayCallback playCallback);

    void setState(int i);

    void setTextureView(TextureView textureView);

    void start(String str);

    void stop();
}
