package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import com.alipay.android.phone.falcon.falconimg.layout.CellDetail;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.BaseOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APGifController;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APLoadStateListener;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.utils.FalconImageProxy;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class PhotoMatchLayout extends GridMatchBaseLayout {
    private static final String TAG = PhotoMatchLayout.class.getSimpleName();
    private Drawable gifDrawable = getResources().getDrawable(R.drawable.icon_gif);
    private MultimediaImageService imageService;
    /* access modifiers changed from: private */
    public b mGifInfoWrapper;
    private DisplayImageOptions options;

    class a extends APGifLoadRequest implements APImageDownLoadCallback, APGifController, APLoadStateListener {
        b a;

        public a(b infoWrapper) {
            this.a = infoWrapper;
            this.gifController = this;
            this.loadStateListener = this;
            this.callback = this;
        }

        public final void onGifDrawable(final APMGifDrawable apmGifDrawable, Bundle bundle) {
            PhotoMatchLayout.this.post(new Runnable() {
                public final void run() {
                    a.this.a(apmGifDrawable);
                }
            });
        }

        /* access modifiers changed from: private */
        public void a(APMGifDrawable apmGifDrawable) {
            if (PhotoMatchLayout.this.mGifInfoWrapper != null && PhotoMatchLayout.this.mGifInfoWrapper == this.a && PhotoMatchLayout.this.mGifInfoWrapper.d) {
                PhotoMatchLayout.this.mGifInfoWrapper.a.getImageView().setImageDrawable(apmGifDrawable);
                apmGifDrawable.startAnimation();
                PhotoMatchLayout.this.mGifInfoWrapper.a.setFontDrawable(null);
            }
        }

        public final void onMemLoadState(boolean b2, int i, Bundle bundle) {
        }

        public final void onDiskCacheLoadState(boolean b2, int i, Bundle bundle) {
        }

        public final void onLocalLoadState(boolean b2, int i, Bundle bundle) {
        }

        public final void onNetLoadState(boolean isStart, int i, Bundle bundle) {
        }

        public final void onSucc(APImageDownloadRsp apImageDownloadRsp) {
        }

        public final void onProcess(String s, int i) {
        }

        public final void onError(APImageDownloadRsp apImageDownloadRsp, Exception e) {
        }
    }

    static class b {
        CustomImgTextView a;
        CellDetail b;
        PhotoInfo c;
        boolean d;
        float e;
        Drawable f;
        Size g;
        MultimediaImageService h;

        b() {
        }
    }

    public PhotoMatchLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PhotoMatchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoMatchLayout(Context context) {
        super(context);
    }

    public void setImageThumbnail(PhotoInfo photoInfo, GridCustomView customView, CellDetail cellDetail, boolean isSingle) {
        this.mGifInfoWrapper = null;
        if (!(customView instanceof CustomImgTextView)) {
            PhotoLogger.debug(TAG, "customView is not CustomImgTextView");
        } else if (this.defaultDrawableID == 0 || cellDetail == null || photoInfo == null) {
            PhotoLogger.debug(TAG, "did not set defaultDrawableID or cellDetail is null or photoInfo is null");
        } else {
            if (this.imageService == null) {
                this.imageService = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaImageService.class.getName());
            }
            customView.setCustomTalkback(photoInfo);
            CustomImgTextView imgTextView = (CustomImgTextView) customView;
            int photoWidth = photoInfo.getPhotoWidth();
            int photoHeight = photoInfo.getPhotoHeight();
            float viewScale = cellDetail.width / cellDetail.height;
            imgTextView.setText(cellDetail.addNum);
            Drawable defaultDrawable = getContext().getResources().getDrawable(this.defaultDrawableID);
            imgTextView.getImageView().setImageDrawable(defaultDrawable);
            Size viewSize = PhotoUtil.calcViewSize(Math.min((int) (((double) (cellDetail.width * ((float) this.screenWidth))) + 0.5d), 800), viewScale);
            PhotoLogger.debug(TAG, "photoInfo width = " + photoWidth + " height = " + photoHeight + " viewSize: " + viewSize + ", scale: " + viewScale);
            buildGifInfoWrapper(photoInfo, (CustomImgTextView) customView, cellDetail, isSingle, viewScale, defaultDrawable, viewSize);
            if (this.options != null) {
                PhotoUtil.loadWithOption(photoInfo, this.options, imgTextView.getImageView(), viewScale, defaultDrawable, viewSize, this.imageService, this.mBusinessId, this.mBizType);
            } else if (photoInfo.getMediaType() == 1) {
                imgTextView.setFontDrawable(null);
                PhotoUtil.loadRatioSpecifiedVideo(photoInfo, imgTextView.getImageView(), viewScale, defaultDrawable, viewSize, this.imageService, this.mBusinessId, this.mBizType);
            } else if (photoInfo.isGif()) {
                doLoadGifThumb(photoInfo, cellDetail, imgTextView, viewScale, defaultDrawable, viewSize);
            } else if (!isSingle || isSuperHeightScale(photoWidth, photoHeight, viewSize)) {
                imgTextView.setFontDrawable(null);
                PhotoUtil.loadRatioSpecifiedImage(photoInfo, imgTextView.getImageView(), viewScale, defaultDrawable, viewSize, this.imageService, this.mBusinessId, this.mBizType);
            } else {
                imgTextView.setFontDrawable(null);
                PhotoUtil.loadSingleSpecifiedImage(photoInfo, imgTextView.getImageView(), viewScale, defaultDrawable, viewSize, this.imageService, this.mBusinessId, this.mBizType);
            }
        }
    }

    private void buildGifInfoWrapper(PhotoInfo photoInfo, CustomImgTextView customView, CellDetail cellDetail, boolean isSingle, float viewScale, Drawable defaultDrawable, Size viewSize) {
        if (isSingle && photoInfo.isGif()) {
            this.mGifInfoWrapper = new b();
            this.mGifInfoWrapper.a = customView;
            this.mGifInfoWrapper.c = photoInfo;
            this.mGifInfoWrapper.e = viewScale;
            this.mGifInfoWrapper.f = defaultDrawable;
            this.mGifInfoWrapper.g = viewSize;
            this.mGifInfoWrapper.h = this.imageService;
            this.mGifInfoWrapper.b = cellDetail;
        }
    }

    private void doLoadGifThumb(PhotoInfo photoInfo, CellDetail cellDetail, CustomImgTextView imgTextView, float viewScale, Drawable defaultDrawable, Size viewSize) {
        imgTextView.setFontDrawable(cellDetail.addNum != 0 ? null : this.gifDrawable);
        PhotoUtil.loadGifImage(photoInfo, imgTextView.getImageView(), viewScale, defaultDrawable, viewSize, this.imageService, this.mBusinessId, this.mBizType);
    }

    public void setOptions(DisplayImageOptions options2) {
        this.options = options2;
    }

    public void addGridImageView() {
        addView(new CustomImgTextView(getContext()), generateDefaultLayoutParams());
    }

    private boolean isSuperHeightScale(int photoWidth, int photoHeight, Size viewSize) {
        if (FalconImageProxy.isSuperHeight(photoWidth, photoHeight, viewSize.getWidth(), viewSize.getHeight()) == 1) {
            return true;
        }
        return false;
    }

    public void startDynamicPreview(Bundle param) {
        PhotoLogger.debug(TAG, "startDynamicPreview:### para=" + param);
        if (this.mGifInfoWrapper == null) {
            return;
        }
        if (!this.mGifInfoWrapper.d) {
            PhotoLogger.debug(TAG, "Start dynamic preview.");
            this.mGifInfoWrapper.d = true;
            APGifLoadRequest req = new a(this.mGifInfoWrapper);
            BaseOptions base = new BaseOptions();
            base.ignoreGifAutoStart = true;
            req.baseOptions = base;
            req.path = this.mGifInfoWrapper.c.getPhotoPath();
            req.defaultDrawable = this.mGifInfoWrapper.a.getImageView().getDrawable();
            req.imageView = this.mGifInfoWrapper.a.getImageView();
            req.shareGifMemCache = false;
            this.mGifInfoWrapper.h.loadGifImage(req, this.mBusinessId);
            return;
        }
        PhotoLogger.debug(TAG, "Has start dynamic preview before !");
    }

    public void pauseDynamicPreview() {
        PhotoLogger.debug(TAG, "pauseDynamicPreview:###");
        if (this.mGifInfoWrapper != null) {
            this.mGifInfoWrapper.d = false;
            this.mGifInfoWrapper.a.setFontDrawable(this.gifDrawable);
            Drawable dr = this.mGifInfoWrapper.a.getImageView().getDrawable();
            if (dr instanceof APMGifDrawable) {
                ((APMGifDrawable) dr).pauseAnimation();
            }
        }
    }

    public void resetDynamicPreview(boolean isShowThumb) {
        PhotoLogger.debug(TAG, "resetDynamicPreview:### isShowThumb = " + isShowThumb);
        if (this.mGifInfoWrapper != null) {
            this.mGifInfoWrapper.d = false;
            this.mGifInfoWrapper.a.setFontDrawable(this.gifDrawable);
            doStopGifAnim();
            if (isShowThumb) {
                doLoadGifThumb(this.mGifInfoWrapper.c, this.mGifInfoWrapper.b, this.mGifInfoWrapper.a, this.mGifInfoWrapper.e, this.mGifInfoWrapper.f, this.mGifInfoWrapper.g);
            }
        }
    }

    private void doStopGifAnim() {
        PhotoLogger.debug(TAG, "doStopGifAnim:###");
        Drawable dr = this.mGifInfoWrapper.a.getImageView().getDrawable();
        if (dr instanceof APMGifDrawable) {
            ((APMGifDrawable) dr).stopAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PhotoLogger.debug(TAG, "onDetachedFromWindow:###");
        resetDynamicPreview(false);
    }
}
