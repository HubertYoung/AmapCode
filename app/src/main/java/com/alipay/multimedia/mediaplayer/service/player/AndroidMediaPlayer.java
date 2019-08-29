package com.alipay.multimedia.mediaplayer.service.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnBufferingUpdateListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnCompletionListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnErrorListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnInfoListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnPreparedListener;
import com.alipay.multimedia.mediaplayer.service.player.IMediaPlayer.OnSeekCompleteListener;

public class AndroidMediaPlayer implements IMediaPlayer {
    private double THRESHOLD = 0.5d;
    private boolean isSeekToTheEnd = false;
    private Bundle mExtra;
    private ListenerAdapter mListenerAdapter;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    /* access modifiers changed from: private */
    public OnBufferingUpdateListener mOnBufferingUpdateListener;
    /* access modifiers changed from: private */
    public OnCompletionListener mOnCompletionListener;
    /* access modifiers changed from: private */
    public OnErrorListener mOnErrorListener;
    /* access modifiers changed from: private */
    public OnInfoListener mOnInfoListener;
    /* access modifiers changed from: private */
    public OnPreparedListener mOnPreparedListener;
    /* access modifiers changed from: private */
    public OnSeekCompleteListener mOnSeekCompleteListener;
    private String mSource;

    private class ListenerAdapter implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {
        private ListenerAdapter() {
        }

        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (AndroidMediaPlayer.this.mOnBufferingUpdateListener != null) {
                AndroidMediaPlayer.this.mOnBufferingUpdateListener.onBufferingUpdate(AndroidMediaPlayer.this, percent);
            }
        }

        public void onCompletion(MediaPlayer mp) {
            if (AndroidMediaPlayer.this.mOnCompletionListener != null) {
                AndroidMediaPlayer.this.mOnCompletionListener.onCompletion(AndroidMediaPlayer.this);
            }
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (AndroidMediaPlayer.this.mOnErrorListener != null) {
                return AndroidMediaPlayer.this.mOnErrorListener.onError(AndroidMediaPlayer.this, what, extra);
            }
            return false;
        }

        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (AndroidMediaPlayer.this.mOnInfoListener != null) {
                return AndroidMediaPlayer.this.mOnInfoListener.onInfo(AndroidMediaPlayer.this, what, extra);
            }
            return false;
        }

        public void onPrepared(MediaPlayer mp) {
            if (AndroidMediaPlayer.this.mOnPreparedListener != null) {
                AndroidMediaPlayer.this.mOnPreparedListener.onPrepared(AndroidMediaPlayer.this);
            }
        }

        public void onSeekComplete(MediaPlayer mp) {
            if (AndroidMediaPlayer.this.mOnSeekCompleteListener != null) {
                AndroidMediaPlayer.this.mOnSeekCompleteListener.onSeekComplete(AndroidMediaPlayer.this);
            }
        }
    }

    public void setDataSource(String source) {
        this.mSource = source;
        this.mMediaPlayer.setDataSource(source);
    }

    public String getDataSource() {
        return this.mSource;
    }

    public void prepareAsync() {
        this.mMediaPlayer.prepareAsync();
    }

    public void start() {
        this.isSeekToTheEnd = false;
        this.mMediaPlayer.start();
    }

    public void stop() {
        this.mMediaPlayer.stop();
    }

    public void pause() {
        this.mMediaPlayer.pause();
    }

    public boolean isPlaying() {
        return this.mMediaPlayer.isPlaying();
    }

    public void seekTo(int msec) {
        this.mMediaPlayer.seekTo(msec);
        if (((double) (getDuration() - ((long) msec))) < this.THRESHOLD) {
            this.isSeekToTheEnd = true;
        } else {
            this.isSeekToTheEnd = false;
        }
    }

    public boolean isSeekToTheEnd() {
        return this.isSeekToTheEnd;
    }

    public long getCurrentPosition() {
        return (long) this.mMediaPlayer.getCurrentPosition();
    }

    public long getDuration() {
        return (long) this.mMediaPlayer.getDuration();
    }

    public void release() {
        this.mMediaPlayer.release();
    }

    public void reset() {
        getExtra().clear();
        this.mMediaPlayer.reset();
    }

    public void setOnPreparedListener(OnPreparedListener listener) {
        this.mOnPreparedListener = listener;
        this.mMediaPlayer.setOnPreparedListener(getListenerAdapter());
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
        this.mMediaPlayer.setOnCompletionListener(getListenerAdapter());
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        this.mOnBufferingUpdateListener = listener;
        this.mMediaPlayer.setOnBufferingUpdateListener(getListenerAdapter());
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
        this.mOnSeekCompleteListener = listener;
        this.mMediaPlayer.setOnSeekCompleteListener(getListenerAdapter());
    }

    public void setOnErrorListener(OnErrorListener listener) {
        this.mOnErrorListener = listener;
        this.mMediaPlayer.setOnErrorListener(getListenerAdapter());
    }

    public void setOnInfoListener(OnInfoListener listener) {
        this.mOnInfoListener = listener;
        this.mMediaPlayer.setOnInfoListener(getListenerAdapter());
    }

    public void setExtra(Bundle extra) {
        this.mExtra = extra;
    }

    public Bundle getExtra() {
        if (this.mExtra == null) {
            this.mExtra = new Bundle();
        }
        return this.mExtra;
    }

    public void setLooping(boolean looping) {
        this.mMediaPlayer.setLooping(looping);
    }

    private ListenerAdapter getListenerAdapter() {
        if (this.mListenerAdapter == null) {
            synchronized (this) {
                if (this.mListenerAdapter == null) {
                    this.mListenerAdapter = new ListenerAdapter();
                }
            }
        }
        return this.mListenerAdapter;
    }
}
