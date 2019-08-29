package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.gif.GifViewWrapper;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.video.views.EnhancedVideoPlayView;
import com.alipay.mobile.beehive.video.views.IVideoViewControl;

public class PhotoPreview extends FrameLayout implements IVideoViewControl {
    public static final String TAG = "PreviewPhoto";
    private ViewStub gifViewStub;
    private GifViewZone gifViewZone;
    private int mCurrentDisplayType = 0;
    private EnhancedVideoPlayView mVideoView;
    private CircleProgressBar pbExactlyProgress;
    private ProgressBar pbLoading;
    private FrameLayout photoZone;
    private PhotoView pvPhoto;

    public boolean isPhotoType() {
        return this.mCurrentDisplayType == 0;
    }

    public PhotoPreview(Context context) {
        super(context);
    }

    public PhotoPreview(Context context, AttributeSet as) {
        super(context, as);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.photoZone = (FrameLayout) findViewById(R.id.photoZone);
        this.pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        this.pbLoading.bringToFront();
        this.pvPhoto = (PhotoView) findViewById(R.id.iv_content);
        this.mVideoView = (EnhancedVideoPlayView) findViewById(R.id.eVideoPlayView);
        this.pbExactlyProgress = (CircleProgressBar) findViewById(R.id.pb_exactly_progress);
        this.gifViewStub = (ViewStub) findViewById(R.id.gif_stub);
    }

    public EnhancedVideoPlayView getVideoPlayView() {
        return this.mVideoView;
    }

    public void changeViewType(int mediaType) {
        this.mCurrentDisplayType = mediaType;
        switch (mediaType) {
            case 0:
                this.mVideoView.setVisibility(8);
                this.photoZone.setVisibility(0);
                changeGifZoneVisibility(false);
                return;
            case 1:
            case 2:
                changeGifZoneVisibility(false);
                this.mVideoView.setVisibility(0);
                this.photoZone.setVisibility(8);
                return;
            case 10:
                this.mVideoView.setVisibility(8);
                this.photoZone.setVisibility(8);
                changeGifZoneVisibility(true);
                return;
            default:
                return;
        }
    }

    private void changeGifZoneVisibility(boolean visible) {
        if (this.gifViewZone != null) {
            this.gifViewZone.setVisibility(visible ? 0 : 8);
        }
    }

    public PhotoView getPhotoView() {
        return this.pvPhoto;
    }

    public ProgressBar getProgressBar() {
        return this.pbLoading;
    }

    public void setProgress(int progress) {
        if (this.pbExactlyProgress.getVisibility() == 0) {
            this.pbExactlyProgress.setProgress(progress);
        }
    }

    public void showProgress(boolean isShowExactlyProgress) {
        if (isShowExactlyProgress) {
            this.pbLoading.setVisibility(8);
            this.pbExactlyProgress.setVisibility(0);
            return;
        }
        this.pbLoading.setVisibility(0);
        this.pbExactlyProgress.setVisibility(8);
    }

    public void dismissProgress() {
        this.pbLoading.setVisibility(8);
        this.pbExactlyProgress.setVisibility(8);
    }

    public GifViewZone getGifViewZone() {
        if (this.gifViewZone == null) {
            this.gifViewZone = (GifViewZone) this.gifViewStub.inflate();
        }
        return this.gifViewZone;
    }

    public void onFocus(boolean isAutoPlayOri) {
        this.mVideoView.onFocus(isAutoPlayOri);
        if (this.gifViewZone != null && this.mCurrentDisplayType == 10) {
            this.gifViewZone.onFocus(isAutoPlayOri);
        }
    }

    public void onLoseFocus() {
        this.mVideoView.onLoseFocus();
        if (this.gifViewZone != null && this.mCurrentDisplayType == 10) {
            this.gifViewZone.onLoseFocus();
        }
    }

    public void destroy() {
        if (this.gifViewZone != null && this.mCurrentDisplayType == 10) {
            this.gifViewZone.destroy();
            this.gifViewZone.setVisibility(8);
        }
        ImageHelper.safeRemoveDrawable(this.pvPhoto);
        this.mVideoView.destroy();
        this.mVideoView.setVisibility(8);
        this.photoZone.setVisibility(8);
    }

    public void setSupportGif(boolean isSupport) {
        if (isSupport && this.gifViewZone == null) {
            PhotoLogger.debug(TAG, "Enable gif browse.");
            this.gifViewZone = (GifViewZone) this.gifViewStub.inflate();
        }
    }

    public GifViewWrapper touchGifView() {
        if (this.gifViewZone != null) {
            return this.gifViewZone.getGifView();
        }
        return null;
    }
}
