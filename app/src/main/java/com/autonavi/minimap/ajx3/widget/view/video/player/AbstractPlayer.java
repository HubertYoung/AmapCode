package com.autonavi.minimap.ajx3.widget.view.video.player;

import android.graphics.SurfaceTexture;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.autonavi.minimap.ajx3.widget.view.video.player.IPlayer.PlayCallback;

public abstract class AbstractPlayer implements SurfaceTextureListener, IPlayer {
    protected PlayCallback mPlayCallback;
    protected SurfaceTexture mSurfaceTexture;
    protected TextureView mTextureView;

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    /* access modifiers changed from: protected */
    public abstract void prepare();

    public void setPlayCallback(PlayCallback playCallback) {
        this.mPlayCallback = playCallback;
    }

    public void setTextureView(TextureView textureView) {
        if (this.mTextureView != null) {
            this.mTextureView.setSurfaceTextureListener(null);
        }
        this.mSurfaceTexture = null;
        this.mTextureView = textureView;
        if (textureView != null) {
            this.mTextureView.setSurfaceTextureListener(this);
        }
    }

    public boolean isPlaying() {
        return (getState() == 2 || getState() == 3) && getCurrentPosition() < getDuration();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.mSurfaceTexture == null) {
            prepare();
            this.mSurfaceTexture = surfaceTexture;
            return;
        }
        this.mTextureView.setSurfaceTexture(this.mSurfaceTexture);
    }

    public TextureView getTextureView() {
        return this.mTextureView;
    }
}
