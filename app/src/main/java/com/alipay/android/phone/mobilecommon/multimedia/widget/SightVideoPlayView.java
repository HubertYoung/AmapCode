package com.alipay.android.phone.mobilecommon.multimedia.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.BubbleEffectParams;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoPlayParams;
import com.alipay.mobile.framework.LauncherApplicationAgent;

@TargetApi(14)
public class SightVideoPlayView extends FrameLayout {
    private static final String TAG = "SightVideoPlayView";
    private Context mContext;
    private VideoPlayParams mPlayParams;
    private SightPlayView mPlayView = null;
    private MultimediaVideoService mVideoService = null;

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(int i, Bundle bundle);
    }

    public interface OnCompletionListener {
        void onCompletion(Bundle bundle);
    }

    public interface OnInfoListener {
        boolean onInfo(int i, Bundle bundle);
    }

    public interface OnPlayErrorListener {
        void onError(int i, String str);
    }

    public interface OnPreparedListener {
        void onPrepared(Bundle bundle);
    }

    public interface OnProgressUpdateListener {
        void onProgressUpdate(long j);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete(Bundle bundle);
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(int i, int i2, Bundle bundle);
    }

    public SightVideoPlayView(Context context) {
        super(context);
        init(context);
    }

    public SightVideoPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SightVideoPlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public SightVideoPlayView(Context context, VideoPlayParams params) {
        super(context);
        this.mPlayParams = params;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        Log.i(TAG, "init");
        this.mVideoService = (MultimediaVideoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaVideoService.class.getName());
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        Object tag = getTag();
        if (((tag instanceof String) && "url_video".equals((String) tag)) || (this.mPlayParams != null && this.mPlayParams.mType == VideoPlayParams.TYPE_URL)) {
            Log.i(TAG, "TYPE_URL");
            this.mPlayView = this.mVideoService.createUrlPlayView(this.mContext);
        } else if (((tag instanceof String) && "lazy_play".equals((String) tag)) || (this.mPlayParams != null && this.mPlayParams.mType == VideoPlayParams.TYPE_LAZY)) {
            Log.i(TAG, "TYPE_LAZY");
            this.mPlayView = this.mVideoService.createLazyPlayView(this.mContext);
        } else if (this.mPlayParams == null || this.mPlayParams.mType != VideoPlayParams.TYPE_LIVE) {
            this.mPlayView = this.mVideoService.createPlayView(this.mContext);
        } else {
            this.mPlayView = this.mVideoService.createLivePlayView(this.mContext);
        }
        addView(this.mPlayView, layoutParams);
        setWillNotDraw(false);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPlayView != null) {
            this.mPlayView.drawBubblePreload(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
        if (this.mVideoService == null) {
            throw new RuntimeException("video service not set!");
        }
    }

    public boolean isAvailable() {
        if (this.mPlayView != null) {
            return this.mPlayView.isAvailable();
        }
        return false;
    }

    public void start() {
        if (this.mPlayView != null) {
            this.mPlayView.start();
        }
    }

    public void start(int msec) {
        if (this.mPlayView != null) {
            this.mPlayView.start(msec);
        }
    }

    public void start(String id) {
        if (this.mPlayView != null) {
            this.mPlayView.start(id);
        }
    }

    public void start(String id, boolean enableAudio) {
        if (this.mPlayView != null) {
            this.mPlayView.start(id, enableAudio);
        }
    }

    public void start(String path, long startPosition) {
        if (this.mPlayView != null) {
            this.mPlayView.start(path, startPosition);
        }
    }

    public void reset() {
        if (this.mPlayView != null) {
            this.mPlayView.reset();
        }
    }

    public void setLooping(boolean loop) {
        if (this.mPlayView != null) {
            this.mPlayView.setLooping(loop);
        }
    }

    public void setFastPlay(int fastPlay) {
        if (this.mPlayView != null) {
            this.mPlayView.setFastPlay(fastPlay);
        }
    }

    public void setCenterCropped(int videoWidth, int videoHeight, int displayWidth, int displayHeight) {
        if (this.mPlayView != null) {
            this.mPlayView.setCenterCropped(videoWidth, videoHeight, displayWidth, displayHeight);
        }
    }

    public void setCenterCropped() {
        if (this.mPlayView != null) {
            this.mPlayView.setCenterCropped();
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

    public void setVideoParams(VideoPlayParams params) {
        if (this.mPlayView != null) {
            this.mPlayView.setVideoParams(params);
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

    public void setBubbleEffect(BubbleEffectParams params) {
        if (this.mPlayView != null) {
            this.mPlayView.setBubbleEffect(params);
        }
    }

    public void setVideoRotation(int degree) {
        if (this.mPlayView != null) {
            this.mPlayView.setVideoRotation(degree);
        }
    }

    public void updateViewSize(int w, int h) {
        if (this.mPlayView != null) {
            this.mPlayView.updateViewSize(w, h);
        }
    }

    @Deprecated
    public void setIsPlayback(boolean isPlayback) {
        if (this.mPlayView != null) {
            this.mPlayView.setIsPlayback(isPlayback);
        }
    }

    public void resume() {
        if (this.mPlayView != null) {
            this.mPlayView.resume();
        }
    }

    public void pause() {
        if (this.mPlayView != null) {
            this.mPlayView.pause();
        }
    }

    public void seekTo(long msec) {
        if (this.mPlayView != null) {
            this.mPlayView.seekTo(msec);
        }
    }

    public void setMute(boolean mute) {
        if (this.mPlayView != null) {
            this.mPlayView.setMute(mute);
        }
    }

    public Point getTouchPoint(int x, int y) {
        if (this.mPlayView != null) {
            return this.mPlayView.getTouchPoint(x, y);
        }
        return null;
    }

    @Deprecated
    public void setRetryParam(long interval, int count) {
        if (this.mPlayView != null) {
            this.mPlayView.setRetryParam(interval, count);
        }
    }

    public void setPlayParams(VideoPlayParams params) {
        if (this.mPlayView != null) {
            this.mPlayView.setPlayParams(params);
        }
        this.mPlayParams = params;
    }

    public void pauseLivePlay() {
        if (this.mPlayView != null) {
            this.mPlayView.pauseLivePlay();
        }
    }

    public void retryLivePlay() {
        if (this.mPlayView != null) {
            this.mPlayView.retryLivePlay();
        }
    }

    public long getCurrentPosition() {
        if (this.mPlayView != null) {
            return this.mPlayView.getCurrentPosition();
        }
        return -1;
    }

    public long getDuration() {
        if (this.mPlayView != null) {
            return this.mPlayView.getDuration();
        }
        return -1;
    }

    public void setAutoFitCenter(boolean fit) {
        if (this.mPlayView != null) {
            this.mPlayView.setAutoFitCenter(fit);
        }
    }

    public boolean isAutoFitCenter() {
        if (this.mPlayView != null) {
            return this.mPlayView.isAutoFitCenter();
        }
        return false;
    }

    public void setOnErrorListener(OnPlayErrorListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnErrorListener(listener);
        }
    }

    public void setOnPreparedListener(OnPreparedListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnPreparedListener(listener);
        }
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnCompletionListener(listener);
        }
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnBufferingUpdateListener(listener);
        }
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnSeekCompleteListener(listener);
        }
    }

    public void setOnInfoListener(OnInfoListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnInfoListener(listener);
        }
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnVideoSizeChangedListener(listener);
        }
    }

    public void setOnProgressUpateListener(OnProgressUpdateListener listener) {
        if (this.mPlayView != null) {
            this.mPlayView.setOnProgressUpdateListener(listener);
        }
    }
}
