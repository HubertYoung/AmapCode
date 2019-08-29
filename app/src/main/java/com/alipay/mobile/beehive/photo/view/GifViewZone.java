package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.gif.GifViewWrapper;
import com.alipay.mobile.beehive.photo.ui.PhotoBrowseView;
import com.alipay.mobile.beehive.photo.util.CommonUtils;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.video.views.IVideoViewControl;

public class GifViewZone extends FrameLayout implements IVideoViewControl {
    private static final String TAG = "GifViewZone";
    private DiskFormatter formatter;
    private GifViewWrapper mGifImageView;
    private CircleProgressBar mProgressBar;
    private TextView mTvSize;

    public GifViewZone(Context context) {
        this(context, null, 0);
    }

    public GifViewZone(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifViewZone(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.formatter = new DiskFormatter();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mGifImageView = (GifViewWrapper) findViewById(R.id.async_gif_view);
        this.mTvSize = (TextView) findViewById(R.id.tv_gif_size);
        this.mTvSize.setVisibility(PhotoBrowseView.selectPhoto ? 0 : 8);
        this.mProgressBar = (CircleProgressBar) findViewById(R.id.gif_download_progress);
        this.mGifImageView.mProgressBar = this.mProgressBar;
    }

    public void onFocus(boolean isAutoPlayOri) {
        this.mGifImageView.isFocusing = true;
        if (this.mGifImageView.currentError <= 0) {
            CommonUtils.GifDebugger("onFocus:call start function.");
            this.mGifImageView.startAnimation();
            return;
        }
        CommonUtils.GifDebugger("onFocus:AsyncGifImageView got a error!");
        PhotoLogger.debug(TAG, "Gif can't play because error:" + this.mGifImageView.currentError);
    }

    public void onLoseFocus() {
        this.mGifImageView.isFocusing = false;
        this.mGifImageView.stopAnimation();
    }

    public void destroy() {
        this.mGifImageView.makeClean();
        this.mProgressBar.setProgress(0);
        this.mProgressBar.setVisibility(8);
    }

    public void loadData(PhotoInfo pi) {
        String sizeStr = this.formatter.format((double) pi.getPhotoSize());
        if (TextUtils.isEmpty(sizeStr)) {
            sizeStr = "0.00KB";
        }
        this.mTvSize.setText(String.format(getContext().getString(R.string.gif_size_prefix), new Object[]{sizeStr}));
        this.mGifImageView.loadGifFile(pi.getPhotoPath(), pi.getThumbPath(), pi.getPhotoSize(), pi.getPhotoHeight(), pi.getPhotoWidth());
    }

    public GifViewWrapper getGifView() {
        return this.mGifImageView;
    }
}
