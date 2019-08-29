package com.alipay.android.phone.mobilecommon.multimedia.api;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.video.CompressLevel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoEditor;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APAlbumVideoInfo;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoInfo;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoUpReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.VideoPlayView.VideoSurfaceView;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.io.File;
import java.util.ArrayList;

public abstract class MultimediaVideoService extends ExternalService {
    public abstract String buildUrl(String str, Bundle bundle);

    public abstract void burnFile(String str);

    public abstract void cancelDownload(String str);

    public abstract void cancelUpload(String str);

    public abstract int checkVideoTransmissible(String str);

    @Deprecated
    public abstract APAlbumVideoInfo compressVideo(String str, String str2);

    public abstract APAlbumVideoInfo compressVideo(String str, String str2, CompressLevel compressLevel, Bundle bundle);

    public abstract SightCameraView createCameraView(Context context);

    public abstract SightCameraView createCameraView(Context context, CameraParams cameraParams);

    public abstract SightCameraView createCameraView(Object obj, Context context, CameraParams cameraParams);

    public abstract SightPlayView createLazyPlayView(Context context);

    public abstract SightPlayView createLivePlayView(Context context);

    public abstract SightPlayView createPlayView(Context context);

    public abstract SightPlayView createUrlPlayView(Context context);

    public abstract VideoSurfaceView createVideoPlayView(Context context);

    public abstract void deleteShortVideo(String str);

    public abstract void downloadVideo(String str, APVideoDownloadCallback aPVideoDownloadCallback, String str2);

    public abstract ArrayList<APVideoInfo> getRecentVideo(int i);

    public abstract String getThumbPathById(String str);

    public abstract APMultimediaTaskModel getVideoDownloadStatus(String str);

    public abstract APVideoEditor getVideoEditor(String str, String str2);

    public abstract String getVideoPathById(String str);

    public abstract String getVideoThumbnail(String str, String str2);

    public abstract boolean isNeedUpdateSo();

    public abstract boolean isVideoAvailable(String str);

    public abstract void loadAlbumVideo(APVideoReq aPVideoReq, ImageView imageView, String str);

    public abstract void loadAlbumVideo(String str, ImageView imageView, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    public abstract void loadAlbumVideo(String str, ImageView imageView, Integer num, Integer num2, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    public abstract void loadLibrary(String str);

    public abstract void loadNecessaryLibs();

    public abstract APVideoDownloadRsp loadShortVideo(APVideoReq aPVideoReq, SightVideoPlayView sightVideoPlayView, String str);

    public abstract void loadShortVideo(String str, SightVideoPlayView sightVideoPlayView, Drawable drawable, APVideoDownloadCallback aPVideoDownloadCallback, boolean z, String str2);

    public abstract void loadShortVideo(String str, SightVideoPlayView sightVideoPlayView, Integer num, Integer num2, Drawable drawable, APVideoDownloadCallback aPVideoDownloadCallback, boolean z, String str2);

    public abstract APVideoDownloadRsp loadShortVideoSync(String str, APVideoDownloadCallback aPVideoDownloadCallback, String str2);

    public abstract void loadVideoThumb(String str, View view, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    public abstract void loadVideoThumb(String str, View view, Integer num, Integer num2, Drawable drawable, APImageDownLoadCallback aPImageDownLoadCallback, String str2);

    public abstract void optimizeView(AbsListView absListView, OnScrollListener onScrollListener);

    public abstract APVideoInfo parseVideoInfo(String str);

    public abstract int saveVideo(String str, File file);

    public abstract void startPlay(String str, SightVideoPlayView sightVideoPlayView);

    public abstract void updateSo(APFileDownCallback aPFileDownCallback);

    public abstract void uploadAlbumVideo(String str, APVideoUploadCallback aPVideoUploadCallback, String str2);

    public abstract APVideoUploadRsp uploadAlbumVideoSync(String str, APVideoUploadCallback aPVideoUploadCallback, String str2);

    public abstract APVideoUploadRsp uploadShortVideoSync(String str, APVideoUploadCallback aPVideoUploadCallback, String str2);

    public abstract APVideoUploadRsp uploadVideo(APVideoUpReq aPVideoUpReq);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
