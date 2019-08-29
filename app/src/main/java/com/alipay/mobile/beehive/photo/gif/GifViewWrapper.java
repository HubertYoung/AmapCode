package com.alipay.mobile.beehive.photo.gif;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APDisplayer;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.BaseOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APGifController;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APLoadStateListener;
import com.alipay.mobile.antui.basic.AUToast;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.ui.PhotoBrowseView;
import com.alipay.mobile.beehive.photo.util.CommonUtils;
import com.alipay.mobile.beehive.photo.view.CircleProgressBar;
import com.alipay.mobile.beehive.photo.view.PhotoView;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.util.MicroServiceUtil;

public class GifViewWrapper extends PhotoView {
    public static final int ERR_GIF_DECODE_FAILED = 1000;
    public static final int ERR_GIF_FILE_INVALID = 1;
    public static final int ERR_GIF_RESOLUTION_TO_LARGE_TO_DISPLAY = 100;
    public static final int ERR_GIF_SIZE_TO_LARGE_TO_DISPLAY = 10;
    public volatile int currentError;
    /* access modifiers changed from: private */
    public Drawable defaultDrawable;
    public boolean isFocusing;
    /* access modifiers changed from: private */
    public volatile String mFilePath;
    private Handler mHandler;
    private MultimediaImageService mImageService;
    public CircleProgressBar mProgressBar;

    class a extends APGifLoadRequest implements APImageDownLoadCallback, APGifController, APLoadStateListener {
        public a() {
            this.gifController = this;
            this.loadStateListener = this;
            this.callback = this;
        }

        public final void onGifDrawable(final APMGifDrawable apmGifDrawable, Bundle bundle) {
            GifViewWrapper.this.runOnUIThread(new Runnable() {
                public final void run() {
                    if (a.this.a()) {
                        GifViewWrapper.this.setProgressVisible("onGifDrawable", false);
                        GifViewWrapper.this.onGifDrawableGet(apmGifDrawable);
                    }
                }
            });
        }

        public final void onMemLoadState(boolean b, int i, Bundle bundle) {
        }

        public final void onDiskCacheLoadState(boolean b, int i, Bundle bundle) {
        }

        public final void onLocalLoadState(boolean b, int i, Bundle bundle) {
        }

        public final void onNetLoadState(final boolean isStart, int i, Bundle bundle) {
            GifViewWrapper.this.runOnUIThread(new Runnable() {
                public final void run() {
                    if (a.this.a()) {
                        if (isStart) {
                            GifViewWrapper.this.setProgressVisible("OnNetLoadState START", true);
                        } else {
                            GifViewWrapper.this.setProgressVisible("OnNetLoadState END", false);
                        }
                    }
                }
            });
        }

        public final void onSucc(APImageDownloadRsp apImageDownloadRsp) {
            GifViewWrapper.this.runOnUIThread(new Runnable() {
                public final void run() {
                    if (a.this.a()) {
                        GifViewWrapper.this.setProgressVisible("onSucc", false);
                    }
                }
            });
        }

        public final void onProcess(String s, final int i) {
            GifViewWrapper.this.runOnUIThread(new Runnable() {
                public final void run() {
                    if (a.this.a()) {
                        GifViewWrapper.this.setProgress(i);
                    }
                }
            });
        }

        public final void onError(final APImageDownloadRsp apImageDownloadRsp, Exception e) {
            GifViewWrapper.this.runOnUIThread(new Runnable() {
                public final void run() {
                    if (a.this.a()) {
                        GifViewWrapper.this.setProgressVisible("onError", false);
                        GifViewWrapper.this.onLoadGifError(apImageDownloadRsp);
                    }
                }
            });
        }

        /* access modifiers changed from: private */
        public boolean a() {
            return TextUtils.equals(GifViewWrapper.this.mFilePath, this.path);
        }
    }

    class b implements APDisplayer, APImageDownLoadCallback {
        private String b;

        public b(String targetFilePath) {
            this.b = targetFilePath;
        }

        public final void onSucc(APImageDownloadRsp apImageDownloadRsp) {
            CommonUtils.GifDebugger("Thumb load success!");
        }

        public final void onProcess(String s, int i) {
        }

        public final void display(View view, final Drawable drawable, String s) {
            GifViewWrapper.this.runOnUIThread(new Runnable() {
                public final void run() {
                    b.this.a(drawable);
                }
            });
        }

        public final void onError(APImageDownloadRsp apImageDownloadRsp, Exception e) {
            GifViewWrapper.this.runOnUIThread(new Runnable() {
                public final void run() {
                    b.this.a();
                }
            });
        }

        /* access modifiers changed from: private */
        public void a(Drawable drawable) {
            if (drawable != null && drawable == GifViewWrapper.this.defaultDrawable) {
                CommonUtils.GifDebugger("onDisplay : defaultDrawable ,ignore.");
            } else if (TextUtils.equals(GifViewWrapper.this.mFilePath, this.b)) {
                GifViewWrapper gifViewWrapper = GifViewWrapper.this;
                String str = this.b;
                if (drawable == null) {
                    drawable = GifViewWrapper.this.defaultDrawable;
                }
                gifViewWrapper.doLoadGif(str, drawable, "On thumb load success.");
            }
        }

        /* access modifiers changed from: private */
        public void a() {
            if (TextUtils.equals(GifViewWrapper.this.mFilePath, this.b)) {
                GifViewWrapper.this.doLoadGif(this.b, GifViewWrapper.this.defaultDrawable, "On thumb load error.");
            }
        }
    }

    public GifViewWrapper(Context context) {
        this(context, null);
    }

    public GifViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currentError = 0;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.defaultDrawable = getResources().getDrawable(R.drawable.default_photo);
    }

    public void loadGifFile(String filePath, String thumbPath, long size, int h, int w) {
        CommonUtils.GifDebugger("loadGifFile:###");
        String filePath2 = CommonUtils.removeFilePrefix(filePath);
        if (!isGifLoading(filePath2)) {
            this.mFilePath = filePath2;
            if (!isGifFileInvalid(filePath2, size, h, w)) {
                if (this.mImageService == null) {
                    this.mImageService = (MultimediaImageService) MicroServiceUtil.getMicroService(MultimediaImageService.class);
                }
                APMGifDrawable gifDrawable = this.mImageService.queryGifMem(new APGifQuery(filePath2), ImageHelper.getBusinessId());
                if (gifDrawable != null) {
                    CommonUtils.GifDebugger("Gif memory cache find.");
                    onGifDrawableGet(gifDrawable);
                } else if (!TextUtils.isEmpty(thumbPath)) {
                    CommonUtils.GifDebugger("Load GIF thumb.");
                    doLoadThumb(thumbPath, this.mFilePath);
                } else {
                    doLoadGif(filePath2, this.defaultDrawable, "Thumb empty.");
                }
            }
        }
    }

    private void doLoadThumb(String thumbPath, String gifPath) {
        APImageLoadRequest req = new APImageLoadRequest();
        BaseOptions base = new BaseOptions();
        base.ignoreNetTask = true;
        req.baseOptions = base;
        b l = new b(this.mFilePath);
        req.callback = l;
        req.displayer = l;
        req.path = thumbPath;
        if (TextUtils.equals(thumbPath, gifPath)) {
            req.usingSourceType = true;
        }
        req.imageView = this;
        req.defaultDrawable = this.defaultDrawable;
        this.mImageService.loadImage(req, ImageHelper.getBusinessId());
    }

    /* access modifiers changed from: private */
    public void doLoadGif(String filePath, Drawable placeHolder, String caller) {
        boolean z = true;
        CommonUtils.GifDebugger("doLoadGif : ### caller = " + caller);
        APGifLoadRequest req = new a();
        BaseOptions base = new BaseOptions();
        base.ignoreGifAutoStart = true;
        req.baseOptions = base;
        req.path = filePath;
        req.defaultDrawable = placeHolder;
        req.imageView = this;
        if (PhotoBrowseView.hasSameGifInContext) {
            z = false;
        }
        req.shareGifMemCache = z;
        this.mImageService.loadGifImage(req, ImageHelper.getBusinessId());
    }

    /* access modifiers changed from: private */
    public void onGifDrawableGet(APMGifDrawable gifDrawable) {
        setImageDrawable(gifDrawable);
        if (this.isFocusing) {
            startAnimation();
        }
    }

    private boolean isGifLoading(String filePath) {
        if (!TextUtils.equals(this.mFilePath, filePath)) {
            return false;
        }
        CommonUtils.GifDebugger("Is loading, return.");
        return true;
    }

    private boolean isGifFileInvalid(String filePath, long size, int h, int w) {
        if (TextUtils.isEmpty(filePath)) {
            this.currentError = 1;
            CommonUtils.GifDebugger("File invalid, return.");
            return true;
        } else if (size > ((long) PhotoBrowseView.maxGifSizeCanSend)) {
            ImageHelper.load(this, filePath, this.defaultDrawable, 0, 0);
            this.currentError = 10;
            CommonUtils.GifDebugger("File size too large, return.");
            return true;
        } else if (w * h <= PhotoBrowseView.maxGifPixelCanSend) {
            return false;
        } else {
            this.currentError = 100;
            ImageHelper.load(this, filePath, this.defaultDrawable, 0, 0);
            CommonUtils.GifDebugger("File resolution too large, return.");
            return true;
        }
    }

    public void makeClean() {
        CommonUtils.GifDebugger("AsyncGifImageView make clean.");
        stopAnimation();
        this.isFocusing = false;
        this.mFilePath = null;
        this.currentError = 0;
        ImageHelper.safeRemoveDrawable(this);
    }

    public void startAnimation() {
        Drawable dr = getDrawable();
        if (dr instanceof APMGifDrawable) {
            CommonUtils.GifDebugger("Start GIF anim.");
            ((APMGifDrawable) dr).startAnimation();
            return;
        }
        CommonUtils.GifDebugger("NO GIF Drawable. ");
    }

    public void stopAnimation() {
        Drawable dr = getDrawable();
        if (dr instanceof APMGifDrawable) {
            CommonUtils.GifDebugger("Stop GIF anim.");
            ((APMGifDrawable) dr).stopAnimation();
            return;
        }
        CommonUtils.GifDebugger("NO GIF Drawable. ");
    }

    /* access modifiers changed from: private */
    public void setProgressVisible(String caller, boolean visibility) {
        CommonUtils.GifDebugger("Set progress visible = " + visibility + ",caller : " + caller);
        if (this.mProgressBar != null) {
            this.mProgressBar.setVisibility(visibility ? 0 : 8);
        }
    }

    /* access modifiers changed from: private */
    public void onLoadGifError(APImageDownloadRsp apImageDownloadRsp) {
        String toastStr;
        if (apImageDownloadRsp.getRetmsg() == null || apImageDownloadRsp.getRetmsg().getCode() != RETCODE.CURRENT_LIMIT) {
            toastStr = getContext().getString(R.string.str_gif_download_failed);
        } else {
            toastStr = getContext().getString(R.string.current_limit);
        }
        if (this.isFocusing) {
            AUToast.makeToast(getContext(), 0, (CharSequence) toastStr, 0).show();
        }
    }

    /* access modifiers changed from: private */
    public void runOnUIThread(Runnable r) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mHandler.post(r);
        } else {
            r.run();
        }
    }

    /* access modifiers changed from: private */
    public void setProgress(int progress) {
        if (this.mProgressBar != null) {
            if (this.mProgressBar.getVisibility() != 0) {
                setProgressVisible("onProgress", true);
            }
            this.mProgressBar.setProgress(progress);
        }
    }
}
