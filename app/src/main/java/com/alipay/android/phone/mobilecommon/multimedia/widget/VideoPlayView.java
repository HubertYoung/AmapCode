package com.alipay.android.phone.mobilecommon.multimedia.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class VideoPlayView extends FrameLayout {
    private static final String TAG = "VideoPlayView";
    private Context mContext;
    private VideoSurfaceView mPlayView = null;
    private MultimediaVideoService mVideoService = null;

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(int i);
    }

    public interface OnPlayCompletionListener {
        void onCompletion();
    }

    public interface OnPlayErrorListener {
        void onError(int i, int i2);
    }

    public static class VideoSurfaceView extends SurfaceView {
        public VideoSurfaceView(Context context) {
            super(context);
        }

        public void start(String id) {
        }

        public void start(String id, int msec) {
        }

        public void pause() {
        }

        public void resume() {
        }

        public void seekTo(int msec) {
        }

        public int getCurrentPosition() {
            return -1;
        }

        public int getDuration() {
            return -1;
        }

        public void setLooping(boolean loop) {
        }

        public void setOnCompletionListener(OnPlayCompletionListener listener) {
        }

        public void setOnErrorListener(OnPlayErrorListener listener) {
        }

        public void start() {
        }

        public void stop() {
        }

        public void setVideoId(String id) {
        }

        public String getVideoId() {
            return "";
        }

        public boolean isPlaying() {
            return false;
        }

        public void drawThumbnail() {
        }

        public void drawBitmap(Bitmap bitmap) {
        }

        public void setAutoFitCenter(boolean force) {
        }

        public boolean isAutoFitCenter() {
            return false;
        }

        public void setScreenOnWhilePlaying(boolean screenOn) {
        }
    }

    public VideoPlayView(Context context) {
        super(context);
        init(context);
    }

    public VideoPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoPlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mVideoService = (MultimediaVideoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaVideoService.class.getName());
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.mPlayView = this.mVideoService.createVideoPlayView(this.mContext);
        addView(this.mPlayView, layoutParams);
        ImageView img = new ImageView(this.mContext);
        img.setTag("thumbnail");
        img.setVisibility(4);
        addView(img, layoutParams);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
        if (this.mVideoService == null) {
            throw new RuntimeException("video service not set!");
        }
    }

    public void start(String id) {
        if (this.mPlayView != null) {
            this.mPlayView.start(id);
        }
    }

    public void start(String id, int msec) {
        if (this.mPlayView != null) {
            this.mPlayView.start(id, msec);
        }
    }

    public int getCurrentPosition() {
        if (this.mPlayView != null) {
            return this.mPlayView.getCurrentPosition();
        }
        return -1;
    }

    public int getDuration() {
        if (this.mPlayView != null) {
            return this.mPlayView.getDuration();
        }
        return -1;
    }

    public void setLooping(boolean loop) {
        if (this.mPlayView != null) {
            this.mPlayView.setLooping(loop);
        }
    }

    public void setOnCompletionListener(OnPlayCompletionListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnCompletionListener(listener);
        }
    }

    public void setOnErorrListener(OnPlayErrorListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnErrorListener(listener);
        }
    }

    public void start() {
        if (this.mPlayView != null) {
            this.mPlayView.start();
        }
    }

    public void pause() {
        if (this.mPlayView != null) {
            this.mPlayView.pause();
        }
    }

    public void resume() {
        if (this.mPlayView != null) {
            this.mPlayView.resume();
        }
    }

    public void seekTo(int msec) {
        if (this.mPlayView != null) {
            this.mPlayView.seekTo(msec);
        }
    }

    public void stop() {
        if (this.mPlayView != null) {
            this.mPlayView.stop();
        }
    }

    public void setVideoId(String id) {
        if (this.mPlayView != null) {
            this.mPlayView.setVideoId(id);
        }
    }

    public String getVideoId() {
        if (this.mPlayView != null) {
            return this.mPlayView.getVideoId();
        }
        return "";
    }

    public boolean isPlaying() {
        if (this.mPlayView != null) {
            return this.mPlayView.isPlaying();
        }
        return false;
    }

    public void drawThumbnail() {
        if (this.mPlayView != null) {
            this.mPlayView.drawThumbnail();
        }
    }

    public void drawBitmap(Bitmap bitmap) {
        if (this.mPlayView != null) {
            this.mPlayView.drawBitmap(bitmap);
        }
    }

    public void setScreenOnWhilePlaying(boolean screenOn) {
        if (this.mPlayView != null) {
            this.mPlayView.setScreenOnWhilePlaying(screenOn);
        }
    }

    public void setAutoFitCenter(boolean fit) {
        if (this.mPlayView != null) {
            this.mPlayView.setAutoFitCenter(fit);
        }
    }

    public boolean isAutoFitCenter() {
        return this.mPlayView != null && this.mPlayView.isAutoFitCenter();
    }
}
