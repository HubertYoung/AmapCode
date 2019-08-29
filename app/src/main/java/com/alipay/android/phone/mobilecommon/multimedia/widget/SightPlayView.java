package com.alipay.android.phone.mobilecommon.multimedia.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.TextureView;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.BubbleEffectParams;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.VideoPlayParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnBufferingUpdateListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnCompletionListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnInfoListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnPlayErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnPreparedListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnProgressUpdateListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnSeekCompleteListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView.OnVideoSizeChangedListener;

@TargetApi(14)
public class SightPlayView extends TextureView {
    protected SightPlayView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void drawBubblePreload(Canvas canvas) {
    }

    /* access modifiers changed from: protected */
    public void start() {
    }

    /* access modifiers changed from: protected */
    public void start(int msec) {
    }

    /* access modifiers changed from: protected */
    public void start(String id) {
    }

    /* access modifiers changed from: protected */
    public void start(String id, boolean enableAudio) {
    }

    /* access modifiers changed from: protected */
    public void start(String path, long startPosition) {
    }

    /* access modifiers changed from: protected */
    public void reset() {
    }

    /* access modifiers changed from: protected */
    public void setLooping(boolean loop) {
    }

    /* access modifiers changed from: protected */
    public void setCenterCropped(int videoWidth, int videoHeight, int displayWidth, int displayHeight) {
    }

    /* access modifiers changed from: protected */
    public void setCenterCropped() {
    }

    /* access modifiers changed from: protected */
    public void stop() {
    }

    /* access modifiers changed from: protected */
    public void setVideoId(String id) {
    }

    /* access modifiers changed from: protected */
    public void setVideoParams(VideoPlayParams params) {
    }

    /* access modifiers changed from: protected */
    public String getVideoId() {
        return "";
    }

    /* access modifiers changed from: protected */
    public boolean isPlaying() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void drawThumbnail() {
    }

    /* access modifiers changed from: protected */
    public void drawBitmap(Bitmap bitmap) {
    }

    /* access modifiers changed from: protected */
    public void setBubbleEffect(BubbleEffectParams params) {
    }

    /* access modifiers changed from: protected */
    public void setVideoRotation(int degree) {
    }

    /* access modifiers changed from: protected */
    public void updateViewSize(int w, int h) {
    }

    /* access modifiers changed from: protected */
    public void setIsPlayback(boolean isPlayback) {
    }

    /* access modifiers changed from: protected */
    public void resume() {
    }

    /* access modifiers changed from: protected */
    public void pause() {
    }

    /* access modifiers changed from: protected */
    public void seekTo(long msec) {
    }

    /* access modifiers changed from: protected */
    public void setMute(boolean mute) {
    }

    /* access modifiers changed from: protected */
    public Point getTouchPoint(int x, int y) {
        return null;
    }

    /* access modifiers changed from: protected */
    public void setRetryParam(long interval, int count) {
    }

    /* access modifiers changed from: protected */
    public void setPlayParams(VideoPlayParams params) {
    }

    /* access modifiers changed from: protected */
    public void pauseLivePlay() {
        throw new RuntimeException("Illegal function call exception,pls check your code");
    }

    /* access modifiers changed from: protected */
    public void retryLivePlay() {
        throw new RuntimeException("Illegal function call exception,pls check your code");
    }

    /* access modifiers changed from: protected */
    public long getCurrentPosition() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public long getDuration() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void setAutoFitCenter(boolean fit) {
    }

    /* access modifiers changed from: protected */
    public boolean isAutoFitCenter() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void setOnErrorListener(OnPlayErrorListener listener) {
    }

    /* access modifiers changed from: protected */
    public void setOnPreparedListener(OnPreparedListener listener) {
    }

    /* access modifiers changed from: protected */
    public void setOnCompletionListener(OnCompletionListener listener) {
    }

    /* access modifiers changed from: protected */
    public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
    }

    /* access modifiers changed from: protected */
    public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
    }

    /* access modifiers changed from: protected */
    public void setOnInfoListener(OnInfoListener listener) {
    }

    /* access modifiers changed from: protected */
    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener listener) {
    }

    /* access modifiers changed from: protected */
    public void setOnProgressUpdateListener(OnProgressUpdateListener listener) {
    }

    /* access modifiers changed from: protected */
    public void setFastPlay(int fastPlay) {
    }
}
