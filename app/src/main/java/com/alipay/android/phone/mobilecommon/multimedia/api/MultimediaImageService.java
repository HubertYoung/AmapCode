package com.alipay.android.phone.mobilecommon.multimedia.api;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APCacheBitmapReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUpRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CacheImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.mark.AddMarkRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.Map;

public abstract class MultimediaImageService extends ExternalService {
    public abstract AddMarkRsp addMark(String str, DisplayImageOptions displayImageOptions);

    public abstract String buildUrl(String str, DisplayImageOptions displayImageOptions, Bundle bundle);

    public abstract int[] calculateCutImageRect(int i, int i2, int i3, float f, String str);

    public abstract int[] calculateCutImageRect(int i, int i2, int i3, String str);

    public abstract int[] calculateDesWidthHeight(String str);

    public abstract void cancelLoad(String str);

    public abstract void cancelUp(String str);

    @Deprecated
    public abstract boolean checkInNetTask(String str);

    public abstract int deleteCache(String str);

    public abstract Size getDjangoNearestImageSize(Size size);

    public abstract APMultimediaTaskModel getLoadTaskStatus(String str);

    public abstract String getOriginalImagePath(String str);

    public abstract Drawable getResDrawable(String str, DisplayImageOptions displayImageOptions);

    public abstract APMultimediaTaskModel getUpTaskStatus(String str);

    public abstract Bitmap loadCacheBitmap(APCacheBitmapReq aPCacheBitmapReq);

    @Deprecated
    public abstract void loadCustomImage(String str, View view, DisplayImageOptions displayImageOptions, APImageDownLoadCallback aPImageDownLoadCallback);

    public abstract void loadCustomImage(String str, View view, DisplayImageOptions displayImageOptions, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    public abstract void loadCustomImage(Map<String, Integer> map, DisplayImageOptions displayImageOptions, APImageDownLoadCallback aPImageDownLoadCallback, String str);

    public abstract APMultimediaTaskModel loadGifImage(APGifLoadRequest aPGifLoadRequest, String str);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(APImageLoadRequest aPImageLoadRequest);

    public abstract APMultimediaTaskModel loadImage(APImageLoadRequest aPImageLoadRequest, String str);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, int i, int i2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, int i, int i2, int i3);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, int i, int i2, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, int i, int i2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, int i, int i2, ImageWorkerPlugin imageWorkerPlugin);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, int i, int i2, ImageWorkerPlugin imageWorkerPlugin, String str2);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, int i, int i2, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, int i, int i2, ImageWorkerPlugin imageWorkerPlugin);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, int i, int i2, ImageWorkerPlugin imageWorkerPlugin, Size size);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, int i, int i2, ImageWorkerPlugin imageWorkerPlugin, Size size, String str2);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, int i, int i2, ImageWorkerPlugin imageWorkerPlugin, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, ImageWorkerPlugin imageWorkerPlugin);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, ImageWorkerPlugin imageWorkerPlugin, String str2);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, boolean z, int i, int i2);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, Drawable drawable, boolean z, int i, int i2, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, DisplayImageOptions displayImageOptions, APImageDownLoadCallback aPImageDownLoadCallback);

    public abstract APMultimediaTaskModel loadImage(String str, ImageView imageView, DisplayImageOptions displayImageOptions, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, APImageDownLoadCallback aPImageDownLoadCallback);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, APImageDownLoadCallback aPImageDownLoadCallback, int i, int i2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, APImageDownLoadCallback aPImageDownLoadCallback, int i, int i2, ImageWorkerPlugin imageWorkerPlugin);

    public abstract APMultimediaTaskModel loadImage(String str, APImageDownLoadCallback aPImageDownLoadCallback, int i, int i2, ImageWorkerPlugin imageWorkerPlugin, String str2);

    public abstract APMultimediaTaskModel loadImage(String str, APImageDownLoadCallback aPImageDownLoadCallback, int i, int i2, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(String str, APImageDownLoadCallback aPImageDownLoadCallback, ImageWorkerPlugin imageWorkerPlugin);

    public abstract APMultimediaTaskModel loadImage(String str, APImageDownLoadCallback aPImageDownLoadCallback, ImageWorkerPlugin imageWorkerPlugin, String str2);

    public abstract APMultimediaTaskModel loadImage(String str, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadImage(byte[] bArr, ImageView imageView, Drawable drawable, int i, int i2, APImageDownLoadCallback aPImageDownLoadCallback, ImageWorkerPlugin imageWorkerPlugin);

    public abstract APMultimediaTaskModel loadImage(byte[] bArr, ImageView imageView, Drawable drawable, int i, int i2, APImageDownLoadCallback aPImageDownLoadCallback, ImageWorkerPlugin imageWorkerPlugin, String str);

    @Deprecated
    public abstract APMultimediaTaskModel loadImageWithMark(String str, ImageView imageView, DisplayImageOptions displayImageOptions, APImageDownLoadCallback aPImageDownLoadCallback);

    public abstract APMultimediaTaskModel loadImageWithMark(String str, ImageView imageView, DisplayImageOptions displayImageOptions, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadOriginalImage(String str, ImageView imageView, Drawable drawable, int i, int i2, APImageDownLoadCallback aPImageDownLoadCallback);

    public abstract APMultimediaTaskModel loadOriginalImage(String str, ImageView imageView, Drawable drawable, int i, int i2, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel loadOriginalImage(String str, ImageView imageView, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback);

    public abstract APMultimediaTaskModel loadOriginalImage(String str, ImageView imageView, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    public abstract void optimizeView(ViewPager viewPager, boolean z, OnPageChangeListener onPageChangeListener);

    public abstract void optimizeView(RecyclerView recyclerView, boolean z, OnScrollListener onScrollListener);

    public abstract void optimizeView(AbsListView absListView, AbsListView.OnScrollListener onScrollListener);

    public abstract void optimizeView(AbsListView absListView, boolean z, boolean z2, AbsListView.OnScrollListener onScrollListener);

    public abstract APMGifDrawable queryGifMem(APGifQuery aPGifQuery, String str);

    public abstract <T extends APImageQuery> APImageQueryResult<?> queryImageFor(T t);

    public abstract void registerCommonMemBusiness(String str);

    public abstract boolean saveImageCache(Bitmap bitmap, String str, CacheImageOptions cacheImageOptions, String str2);

    public abstract String saveIntoCache(byte[] bArr, String str);

    public abstract void unregisteLoadCallBack(String str, APImageDownLoadCallback aPImageDownLoadCallback);

    @Deprecated
    public abstract APMultimediaTaskModel uploadImage(APImageUpRequest aPImageUpRequest);

    public abstract APMultimediaTaskModel uploadImage(APImageUpRequest aPImageUpRequest, String str);

    @Deprecated
    public abstract APMultimediaTaskModel uploadImage(String str);

    @Deprecated
    public abstract APMultimediaTaskModel uploadImage(String str, APImageUploadCallback aPImageUploadCallback);

    @Deprecated
    public abstract APMultimediaTaskModel uploadImage(String str, APImageUploadCallback aPImageUploadCallback, APImageUploadOption aPImageUploadOption);

    public abstract APMultimediaTaskModel uploadImage(String str, APImageUploadCallback aPImageUploadCallback, APImageUploadOption aPImageUploadOption, String str2);

    public abstract APMultimediaTaskModel uploadImage(String str, APImageUploadCallback aPImageUploadCallback, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel uploadImage(String str, APImageUploadOption aPImageUploadOption);

    public abstract APMultimediaTaskModel uploadImage(String str, APImageUploadOption aPImageUploadOption, String str2);

    public abstract APMultimediaTaskModel uploadImage(String str, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel uploadImage(String str, boolean z);

    public abstract APMultimediaTaskModel uploadImage(String str, boolean z, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel uploadImage(byte[] bArr);

    @Deprecated
    public abstract APMultimediaTaskModel uploadImage(byte[] bArr, APImageUploadCallback aPImageUploadCallback);

    public abstract APMultimediaTaskModel uploadImage(byte[] bArr, APImageUploadCallback aPImageUploadCallback, String str);

    public abstract APMultimediaTaskModel uploadImage(byte[] bArr, String str);

    @Deprecated
    public abstract APMultimediaTaskModel uploadOriginalImage(String str);

    @Deprecated
    public abstract APMultimediaTaskModel uploadOriginalImage(String str, APImageUploadCallback aPImageUploadCallback);

    public abstract APMultimediaTaskModel uploadOriginalImage(String str, APImageUploadCallback aPImageUploadCallback, String str2);

    public abstract APMultimediaTaskModel uploadOriginalImage(String str, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel uploadOriginalImage(String str, boolean z, APImageUploadCallback aPImageUploadCallback);

    public abstract APMultimediaTaskModel uploadOriginalImage(String str, boolean z, APImageUploadCallback aPImageUploadCallback, String str2);

    public abstract Bitmap zoomBitmap(Bitmap bitmap, int i, int i2);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
