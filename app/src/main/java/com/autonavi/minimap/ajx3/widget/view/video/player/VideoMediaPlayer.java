package com.autonavi.minimap.ajx3.widget.view.video.player;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.view.Surface;
import android.view.TextureView;
import com.autonavi.minimap.ajx3.widget.view.video.player.IPlayer.PlayCallback;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;

public class VideoMediaPlayer extends AbstractHandlerPlayer implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener {
    protected MediaPlayer mMediaPlayer;
    protected int mState = 0;
    private int mStateBeforeBurrering = -1;
    private String mUrl;

    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }

    public void start(String str) {
        this.mUrl = str;
    }

    public void play() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.start();
        }
    }

    public void pause() {
        if ((getState() == 2 || getState() == 3 || getState() == 4 || getState() == 8) && this.mMediaPlayer != null) {
            this.mMediaPlayer.pause();
        }
    }

    public void setState(int i) {
        if (this.mState == 7) {
            if (1 != i) {
                i = this.mState;
            }
            this.mState = i;
        } else if (i == 4 && 5 == this.mState) {
            this.mStateBeforeBurrering = -1;
        } else if (i == 4 && this.mStateBeforeBurrering != -1) {
            this.mState = this.mStateBeforeBurrering;
            this.mStateBeforeBurrering = -1;
        } else if (8 == i) {
            this.mState = 2;
        } else {
            this.mState = i;
        }
    }

    public int getState() {
        return this.mState;
    }

    public long getCurrentPosition() {
        if (this.mMediaPlayer != null) {
            return (long) this.mMediaPlayer.getCurrentPosition();
        }
        return -1;
    }

    public long getDuration() {
        if (this.mMediaPlayer != null) {
            return (long) this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    public void seekTo(long j) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.seekTo((int) j);
        }
    }

    public void setTextureView(TextureView textureView) {
        if (textureView == null && this.mSurfaceTexture != null) {
            this.mSurfaceTexture.release();
        }
        super.setTextureView(textureView);
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        if (this.mPlayCallback != null) {
            this.mPlayCallback.onSizeChanged(i, i2);
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        if (this.mState != 7 && this.mPlayCallback != null) {
            this.mPlayCallback.onComplete();
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        if (this.mPlayCallback != null) {
            PlayCallback playCallback = this.mPlayCallback;
            StringBuilder sb = new StringBuilder("Play error, what=");
            sb.append(i);
            sb.append(", extra=");
            sb.append(i2);
            playCallback.onError(sb.toString());
        }
        return false;
    }

    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        StringBuilder sb = new StringBuilder(" -- MediaPlayer onInfo what = ");
        sb.append(i);
        sb.append(" extra = ");
        sb.append(i2);
        Utils.log(sb.toString());
        if (i == 701) {
            this.mStateBeforeBurrering = this.mState;
            this.mPlayCallback.onPlayStateChanged(3);
        } else if (i == 702) {
            this.mPlayCallback.onPlayStateChanged(4);
        } else if (i == 3) {
            this.mPlayCallback.onPlayStateChanged(8);
        }
        if (this.mPlayCallback != null) {
            this.mPlayCallback.onInfo((long) i, (long) i2);
        }
        return false;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        if (this.mPlayCallback != null) {
            this.mPlayCallback.onDurationChanged((long) mediaPlayer.getDuration());
        }
        play();
    }

    public void onSeekComplete(MediaPlayer mediaPlayer) {
        if (getState() == 1) {
            this.mPlayCallback.onPlayStateChanged(2);
        }
    }

    /* access modifiers changed from: protected */
    public void onHandlePrepare() {
        try {
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer.reset();
                this.mMediaPlayer.release();
            }
            this.mMediaPlayer = new MediaPlayer();
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setOnPreparedListener(this);
            this.mMediaPlayer.setOnCompletionListener(this);
            this.mMediaPlayer.setOnBufferingUpdateListener(this);
            this.mMediaPlayer.setOnSeekCompleteListener(this);
            this.mMediaPlayer.setOnVideoSizeChangedListener(this);
            this.mMediaPlayer.setOnErrorListener(this);
            this.mMediaPlayer.setOnInfoListener(this);
            this.mMediaPlayer.setDataSource(this.mUrl);
            this.mMediaPlayer.prepareAsync();
            this.mMediaPlayer.setSurface(new Surface(this.mSurfaceTexture));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onHandleStop() {
        onHandleRelease();
    }

    /* access modifiers changed from: protected */
    public void onHandleRelease() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
    }
}
