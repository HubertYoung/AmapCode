package com.autonavi.minimap.bundle.share.entity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.alipay.mobile.beehive.imageedit.constant.Constants;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.weibo.SINAWbShareHandler;

public class WeiboShare extends ShareBase {
    private static final int IMAGE_TIME_OUT = 10000;
    /* access modifiers changed from: private */
    public volatile Bitmap mBitmap;
    private a mImageCallback = new a(this, 0);
    /* access modifiers changed from: private */
    public Runnable mImageTimeOutRunnable = new Runnable() {
        public final void run() {
            WeiboShare.this.mIsImageTimeOut = true;
            WeiboShare.this.dismissProgressDialog();
            if (!WeiboShare.this.mCancleTask) {
                WeiboShare.this.startShareInternal();
            }
        }
    };
    /* access modifiers changed from: private */
    public volatile boolean mIsImageTimeOut;
    private k mWeiboParam;

    class a implements bkf {
        public final void onPrepareLoad(Drawable drawable) {
        }

        private a() {
        }

        /* synthetic */ a(WeiboShare weiboShare, byte b) {
            this();
        }

        public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
            aho.b(WeiboShare.this.mImageTimeOutRunnable);
            if (!WeiboShare.this.mIsImageTimeOut) {
                if (WeiboShare.this.mCancleTask) {
                    WeiboShare.this.dismissProgressDialog();
                    ddi.a().b();
                    WeiboShare.this.mCancleTask = false;
                    return;
                }
                if (!WeiboShare.this.checkShortUrl()) {
                    WeiboShare.this.dismissProgressDialog();
                }
                if (bitmap != null && !bitmap.isRecycled()) {
                    WeiboShare.this.mBitmap = bitmap;
                    if (bitmap.getByteCount() > 2097152) {
                        float byteCount = ((float) bitmap.getByteCount()) / 2000000.0f;
                        WeiboShare.this.mBitmap = Bitmap.createScaledBitmap(bitmap, (int) (((float) bitmap.getWidth()) / byteCount), (int) (((float) bitmap.getHeight()) / byteCount), true);
                    }
                }
                WeiboShare.this.startShareInternal();
            }
        }

        public final void onBitmapFailed(Drawable drawable) {
            aho.b(WeiboShare.this.mImageTimeOutRunnable);
            if (!WeiboShare.this.mIsImageTimeOut) {
                if (WeiboShare.this.mCancleTask) {
                    WeiboShare.this.dismissProgressDialog();
                    ddi.a().b();
                    WeiboShare.this.mCancleTask = false;
                    return;
                }
                WeiboShare.this.startShareInternal();
            }
        }
    }

    public int getShareType() {
        return 0;
    }

    public WeiboShare(k kVar) {
        this.mWeiboParam = kVar;
    }

    private void toSina(String str) {
        ddl.a("WeiboShare", " toSina  url:".concat(String.valueOf(str)));
        PageBundle pageBundle = new PageBundle();
        if (this.mWeiboParam.e) {
            pageBundle.putInt("poi_x", this.mWeiboParam.a);
            pageBundle.putInt("poi_y", this.mWeiboParam.b);
        }
        pageBundle.putString("pic_url", this.mWeiboParam.c);
        pageBundle.putString("content", this.mWeiboParam.f);
        pageBundle.putString("title", this.mWeiboParam.d);
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.share_weibo_subfix));
            pageBundle.putString("short_url", sb.toString());
        }
        pageBundle.putBoolean("ISFROMNAVI", this.mWeiboParam.j);
        if (this.mBitmap != null && checkImage()) {
            pageBundle.putObject(Constants.KEY_BITMAP, this.mBitmap);
        }
        SINAWbShareHandler.getInstance().initShareHandler();
        SINAWbShareHandler.getInstance().sendMessage(pageBundle);
    }

    /* access modifiers changed from: private */
    public boolean checkShortUrl() {
        return this.mWeiboParam.h && !TextUtils.isEmpty(this.mWeiboParam.g);
    }

    private boolean checkImage() {
        return !TextUtils.isEmpty(this.mWeiboParam.c) && !this.mWeiboParam.j;
    }

    public void startShare() {
        ddl.a("WeiboShare", " startShare ");
        this.mBitmap = null;
        if (checkImage()) {
            requestNetImage(this.mWeiboParam.c);
        } else {
            startShareInternal();
        }
    }

    /* access modifiers changed from: private */
    public void startShareInternal() {
        StringBuffer stringBuffer = new StringBuffer(" startShareInternal mWeiboParam.url:");
        stringBuffer.append(this.mWeiboParam == null ? null : this.mWeiboParam.g);
        ddl.a("WeiboShare", stringBuffer.toString());
        if (checkShortUrl()) {
            ddl.a("WeiboShare", " startShareInternal requestShortUri");
            requestShortUrl(this.mWeiboParam.g, "");
            return;
        }
        ddl.a("WeiboShare", " startShareInternal toSina");
        toSina(this.mWeiboParam.g);
    }

    private void requestNetImage(String str) {
        this.mIsImageTimeOut = false;
        showProgressDialog(AMapPageUtil.getAppContext().getString(R.string.share_base_request_content));
        aho.a(this.mImageTimeOutRunnable, 10000);
        ko.a(null, str, null, 0, this.mImageCallback);
    }

    public void onFinishResult(String str) {
        ddl.a("WeiboShare", "onFinishResult url:".concat(String.valueOf(str)));
        if (str == null) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.email_share_get_content_failed));
            ddi.a().b();
            return;
        }
        toSina(str);
    }
}
