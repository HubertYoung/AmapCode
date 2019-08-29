package com.alipay.multimedia.apxmmusic;

import android.os.Bundle;
import com.alipay.multimedia.mediaplayer.service.APBaseMediaPlayerService;
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
import com.alipay.multimedia.network.LocalNetworkProxy;
import com.googlecode.androidannotations.api.BackgroundExecutor;

public class MusicPlayerServiceImpl extends APMusicPlayerService {
    private APBaseMediaPlayerService mPlayerService;

    public APMediaPlayerService createPlayService(Bundle extra) {
        return new APBaseMediaPlayerService();
    }

    private APBaseMediaPlayerService getPlayerService() {
        if (this.mPlayerService == null) {
            this.mPlayerService = new APBaseMediaPlayerService();
        }
        return this.mPlayerService;
    }

    public void invalidate() {
        getPlayerService().invalidate();
    }

    public void setDataSource(String source) {
        getPlayerService().setDataSource(source);
    }

    public void setDataSource(String source, Bundle extra) {
        getPlayerService().setDataSource(source, extra);
    }

    public void setDataSource(String source, Bundle extra, String jsonExtra) {
        getPlayerService().setDataSource(source, extra, jsonExtra);
    }

    public String getDataSource() {
        return getPlayerService().getDataSource();
    }

    public void start() {
        getPlayerService().start();
    }

    public void stop() {
        getPlayerService().stop();
    }

    public void pause() {
        getPlayerService().pause();
    }

    public boolean isPlaying() {
        return getPlayerService().isPlaying();
    }

    public void seekTo(int msec) {
        getPlayerService().seekTo(msec);
    }

    public long getCurrentPosition() {
        return getPlayerService().getCurrentPosition();
    }

    public long getDuration() {
        return getPlayerService().getDuration();
    }

    public float getVolume() {
        return getPlayerService().getVolume();
    }

    public void reset() {
        getPlayerService().reset();
    }

    public int getMediaPlayerState() {
        return getPlayerService().getMediaPlayerState();
    }

    public int getStartPosition() {
        return getPlayerService().getStartPosition();
    }

    public void setStartPosition(int startPosition) {
        getPlayerService().setStartPosition(startPosition);
    }

    public int getBufferedPercent() {
        return getPlayerService().getBufferedPercent();
    }

    public PlayError getError() {
        return getPlayerService().getError();
    }

    public void setLooping(boolean looping) {
        getPlayerService().setLooping(looping);
    }

    public void setVolume(float volume) {
        getPlayerService().setVolume(volume);
    }

    public void setHls(boolean isHls) {
        getPlayerService().setHls(isHls);
    }

    public boolean isHls() {
        return getPlayerService().isHls();
    }

    public void addOnPreparingListener(OnPreparingListener listener) {
        getPlayerService().addOnPreparingListener(listener);
    }

    public void addOnPreparedListener(OnPreparedListener listener) {
        getPlayerService().addOnPreparedListener(listener);
    }

    public void addOnCompletionListener(OnCompletionListener listener) {
        getPlayerService().addOnCompletionListener(listener);
    }

    public void addOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        getPlayerService().addOnBufferingUpdateListener(listener);
    }

    public void addOnSeekingListener(OnSeekingListener listener) {
        getPlayerService().addOnSeekingListener(listener);
    }

    public void addOnSeekCompleteListener(OnSeekCompleteListener listener) {
        getPlayerService().addOnSeekCompleteListener(listener);
    }

    public void addOnErrorListener(OnErrorListener listener) {
        getPlayerService().addOnErrorListener(listener);
    }

    public void addOnInfoListener(OnInfoListener listener) {
        getPlayerService().addOnInfoListener(listener);
    }

    public void addOnPlayProgressUpdateListener(OnPlayProgressUpdateListener listener) {
        getPlayerService().addOnPlayProgressUpdateListener(listener);
    }

    public void addOnStartListener(OnStartListener listener) {
        getPlayerService().addOnStartListener(listener);
    }

    public void addOnStopListener(OnStopListener listener) {
        getPlayerService().addOnStopListener(listener);
    }

    public void addOnPausedListener(OnPausedListener listener) {
        getPlayerService().addOnPausedListener(listener);
    }

    public void removeOnPreparingListener(OnPreparingListener listener) {
        getPlayerService().removeOnPreparingListener(listener);
    }

    public void removeOnPreparedListener(OnPreparedListener listener) {
        getPlayerService().removeOnPreparedListener(listener);
    }

    public void removeOnCompletionListener(OnCompletionListener listener) {
        getPlayerService().removeOnCompletionListener(listener);
    }

    public void removeOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        getPlayerService().removeOnBufferingUpdateListener(listener);
    }

    public void removeOnSeekingListener(OnSeekingListener listener) {
        getPlayerService().removeOnSeekingListener(listener);
    }

    public void removeOnSeekCompleteListener(OnSeekCompleteListener listener) {
        getPlayerService().removeOnSeekCompleteListener(listener);
    }

    public void removeOnErrorListener(OnErrorListener listener) {
        getPlayerService().removeOnErrorListener(listener);
    }

    public void removeOnInfoListener(OnInfoListener listener) {
        getPlayerService().removeOnInfoListener(listener);
    }

    public void removeOnPlayProgressUpdateListener(OnPlayProgressUpdateListener listener) {
        getPlayerService().removeOnPlayProgressUpdateListener(listener);
    }

    public void removeOnStartListener(OnStartListener listener) {
        getPlayerService().removeOnStartListener(listener);
    }

    public void removeOnStopListener(OnStopListener listener) {
        getPlayerService().removeOnStopListener(listener);
    }

    public void removeOnPausedListener(OnPausedListener listener) {
        getPlayerService().removeOnPausedListener(listener);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public void run() {
                LocalNetworkProxy.getInstance().start();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            public void run() {
                LocalNetworkProxy.getInstance().stop();
            }
        });
    }
}
