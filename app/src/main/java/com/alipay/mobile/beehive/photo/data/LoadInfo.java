package com.alipay.mobile.beehive.photo.data;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.view.PhotoPreview;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper.LoadListener;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.ref.WeakReference;

public class LoadInfo implements APImageDownLoadCallback, LoadListener {
    private static final String TAG = "LoadInfo";
    public boolean isNeedFullScreen;
    public boolean isShowingPie;
    public boolean loading;
    public int loadingHeight;
    public boolean loadingOrigin;
    public int loadingWidth;
    public PhotoItem photoItem;
    private WeakReference<PhotoPreview> photoPreviewRef;
    public int progress;
    private WeakReference<LoadListener> proxyRef;
    public String taskId;
    public int thumbHeight;
    public int thumbWidth;

    public String toString() {
        return "path =" + (this.photoItem == null ? "null" : this.photoItem.getPhotoPath()) + ",isLoadingOriginal = " + this.loadingOrigin + ",progress=" + this.progress;
    }

    public void onLoadProgress(LoadInfo info, int current, int total) {
        PhotoLogger.debug(TAG, "onLoadProgress " + info.toString() + Token.SEPARATOR + current + "/" + total);
        LoadListener proxy = getProxy();
        if (proxy != null) {
            proxy.onLoadProgress(info, current, total);
        }
    }

    public void onLoadComplete(LoadInfo info) {
        PhotoLogger.debug(TAG, "onLoadComplete " + info.toString());
        LoadListener proxy = getProxy();
        if (proxy != null) {
            proxy.onLoadComplete(info);
        }
    }

    public void onLoadCanceled(LoadInfo info) {
        PhotoLogger.debug(TAG, "onLoadCanceled " + info.toString());
        LoadListener proxy = getProxy();
        if (proxy != null) {
            proxy.onLoadCanceled(info);
        }
    }

    public void onLoadFailed(LoadInfo info, APImageDownloadRsp rsp) {
        PhotoLogger.debug(TAG, "onLoadFailed " + info.toString());
        LoadListener proxy = getProxy();
        if (proxy != null) {
            proxy.onLoadFailed(info, rsp);
        }
    }

    public void onSucc(APImageDownloadRsp apImageDownloadRsp) {
        PhotoUtil.runOnMain(new Runnable() {
            public final void run() {
                LoadInfo.this.onLoadComplete(LoadInfo.this);
            }
        });
    }

    public void onProcess(String s, final int i) {
        PhotoUtil.runOnMain(new Runnable() {
            public final void run() {
                LoadInfo.this.onLoadProgress(LoadInfo.this, i, 100);
            }
        });
    }

    public void onError(final APImageDownloadRsp rsp, Exception e) {
        PhotoUtil.runOnMain(new Runnable() {
            public final void run() {
                LoadInfo.this.onLoadFailed(LoadInfo.this, rsp);
            }
        });
    }

    public void setPhotoPreview(PhotoPreview view) {
        if (view == null) {
            this.photoPreviewRef = null;
        }
        this.photoPreviewRef = new WeakReference<>(view);
    }

    public PhotoPreview getPhotoPreview() {
        if (this.photoPreviewRef == null) {
            return null;
        }
        return (PhotoPreview) this.photoPreviewRef.get();
    }

    public void setProxy(LoadListener proxy) {
        if (proxy == null) {
            this.proxyRef = null;
        } else {
            this.proxyRef = new WeakReference<>(proxy);
        }
    }

    public LoadListener getProxy() {
        if (this.proxyRef == null) {
            return null;
        }
        return (LoadListener) this.proxyRef.get();
    }
}
