package com.alipay.android.phone.mobilecommon.multimediabiz.biz.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.video.CompressLevel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APDisplayer;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageLoadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoEditor;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APAlbumVideoInfo;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoInfo;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoLoadStatus;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoUpReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightVideoPlayView;
import com.alipay.android.phone.mobilecommon.multimedia.widget.VideoPlayView;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.FRWBroadcastReceiver;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewAssistant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.net.UriFactory.Request;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.NeonSoManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoEditorImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.diskcache.DiskCache;
import com.alipay.diskcache.DiskCache.QueryFilter;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.video.VideoCompressConfig;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;
import tv.danmaku.ijk.media.widget.FreePlayViewImpl;
import tv.danmaku.ijk.media.widget.NoneNeonPlayViewImpl;
import tv.danmaku.ijk.media.widget.SightCameraViewImpl;
import tv.danmaku.ijk.media.widget.SightPlayViewImpl;
import tv.danmaku.ijk.media.widget.UrlPlayViewImpl;
import tv.danmaku.ijk.media.widget.VideoSurfaceViewImpl;

public class MultimediaVideoServiceImpl extends MultimediaVideoService {
    public static final String TAG = "MultimediaVideoServiceImpl";
    private static final int TASK_PRIORITY_DEF = 5;
    /* access modifiers changed from: private */
    public static final Logger mLogger = Logger.getLogger((String) TAG);
    private Context mContext;
    private MultimediaFileService mFileService;
    private MultimediaImageService mImageService;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, String> mLogMap = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public long mSize = 0;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, APVideoLoadStatus> mStatus = new ConcurrentHashMap<>();
    private int videoUpSizeLimit = 20;

    class APVideoDownloadCallbackWrapper implements APVideoDownloadCallback {
        private APVideoDownloadCallback callback;
        private ViewWrapper viewWrapper;

        public APVideoDownloadCallbackWrapper(APVideoDownloadCallback callback2, ViewWrapper viewWrapper2) {
            this.callback = callback2;
            this.viewWrapper = viewWrapper2;
        }

        public void onDownloadStart(APMultimediaTaskModel taskinfo) {
            if (this.callback != null) {
                this.callback.onDownloadStart(taskinfo);
            }
        }

        public void onDownloadFinished(APVideoDownloadRsp rsp) {
            if (this.callback == null) {
                return;
            }
            if (MultimediaVideoServiceImpl.this.checkViewReused(this.viewWrapper)) {
                rsp.setRetCode(8);
                this.callback.onDownloadError(rsp);
                return;
            }
            this.callback.onDownloadFinished(rsp);
        }

        public void onDownloadError(APVideoDownloadRsp rsp) {
            if (this.callback != null) {
                this.callback.onDownloadError(rsp);
            }
        }

        public void onDownloadProgress(APMultimediaTaskModel taskInfo, int progress) {
            if (this.callback != null) {
                this.callback.onDownloadProgress(taskInfo, progress);
            }
        }

        public void onThumbDownloadFinished(APVideoDownloadRsp rsp) {
            if (this.callback != null) {
                this.callback.onThumbDownloadFinished(rsp);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getMicroApplicationContext().getApplicationContext();
        if (this.mContext != null) {
            this.mFileService = (MultimediaFileService) getMicroApplicationContext().findServiceByInterface(MultimediaFileService.class.getName());
            this.mImageService = (MultimediaImageService) getMicroApplicationContext().findServiceByInterface(MultimediaImageService.class.getName());
        }
        FRWBroadcastReceiver.initOnce();
    }

    public SightCameraView createCameraView(Context context, CameraParams params) {
        checkParams(null);
        return new SightCameraViewImpl(context, params);
    }

    public SightCameraView createCameraView(Context context) {
        checkParams(null);
        return new SightCameraViewImpl(context);
    }

    public SightCameraView createCameraView(Object activityOrFragment, Context context, CameraParams params) {
        if (params == null) {
            params = new CameraParams();
        }
        checkParams(activityOrFragment);
        SightCameraViewImpl cameraView = new SightCameraViewImpl(context, params);
        cameraView.setActivityOrFragment(activityOrFragment);
        return cameraView;
    }

    public SightPlayView createPlayView(Context context, CameraParams params) {
        return createPlayView(context);
    }

    public SightPlayView createPlayView(Context context) {
        if (isNeedUpdateSo()) {
            return new NoneNeonPlayViewImpl(context);
        }
        return new SightPlayViewImpl(context);
    }

    @Deprecated
    public SightPlayView createLivePlayView(Context context) {
        if (isNeedUpdateSo()) {
            return new NoneNeonPlayViewImpl(context);
        }
        return FalconFactory.INS.createLivePlay(context);
    }

    public SightPlayView createUrlPlayView(Context context) {
        if (isNeedUpdateSo()) {
            return new NoneNeonPlayViewImpl(context);
        }
        return new UrlPlayViewImpl(context);
    }

    public SightPlayView createLazyPlayView(Context context) {
        if (isNeedUpdateSo()) {
            return new NoneNeonPlayViewImpl(context);
        }
        return new FreePlayViewImpl(context);
    }

    public VideoSurfaceViewImpl createVideoPlayView(Context context, CameraParams params) {
        return new VideoSurfaceViewImpl(context);
    }

    public VideoSurfaceViewImpl createVideoPlayView(Context context) {
        return new VideoSurfaceViewImpl(context);
    }

    public void uploadAlbumVideo(String id, APVideoUploadCallback callback, String business) {
        mLogger.d("uploadAlbumVideo input id:" + id, new Object[0]);
        APVideoUpReq req = new APVideoUpReq(id);
        req.setVideoType(1);
        req.setCallback(callback).setBusinessId(business);
        uploadVideo(req);
    }

    public APVideoUploadRsp uploadAlbumVideoSync(String id, APVideoUploadCallback callback, String business) {
        mLogger.d("uploadAlbumVideoSync input id:" + id, new Object[0]);
        APVideoUpReq req = new APVideoUpReq(id);
        req.setVideoType(1).setBusinessId(business).setCallback(callback).setSync(true);
        return uploadVideo(req);
    }

    public APVideoUploadRsp uploadShortVideoSync(String id, APVideoUploadCallback callback, String business) {
        return uploadVideo(new APVideoUpReq(id).setCallback(callback).setBusinessId(business).setSync(true));
    }

    private boolean isRealProgress() {
        if (CommonUtils.isActiveNetwork(AppUtils.getApplicationContext()) && ConfigManager.getInstance().getCommonConfigItem().progConf.isVideoProgOn()) {
            return false;
        }
        return true;
    }

    private int getRandomProgress(boolean bRealProg, APVideoUploadCallback cb) {
        int randomPrg = 5;
        if (!bRealProg) {
            try {
                randomPrg = CommonUtils.generateRandom(ConfigManager.getInstance().getCommonConfigItem().progConf.vdUpProgMin, ConfigManager.getInstance().getCommonConfigItem().progConf.vdUpProgMax);
                if (cb != null) {
                    cb.onUploadProgress(null, randomPrg);
                }
            } catch (Exception e) {
                return 5;
            }
        }
        Logger.D(TAG, "getRandomProgress randomPrg=" + randomPrg + ";bRealProg=" + bRealProg, new Object[0]);
        return randomPrg;
    }

    private void addAliasFileName(String path, APFileReq req, boolean image) {
        if (!TextUtils.isEmpty(path)) {
            req.setAliasFileName(new File(path).getName() + (image ? ".jpg" : PhotoParam.VIDEO_SUFFIX));
        }
    }

    public boolean isVideoAvailable(String id) {
        String path;
        String path2 = id;
        if (PathUtils.extractFile(id) == null) {
            path = VideoFileManager.getInstance().getVideoPathById(path2);
        } else {
            path = PathUtils.extractFile(path2).getAbsolutePath();
        }
        boolean existed = FileUtils.checkFile(path);
        if (!existed) {
            CacheHitManager.getInstance().videoCacheMissed();
            mLogger.d("isVideoAvailable false, id: " + id + ", path: " + path, new Object[0]);
        } else {
            CacheHitManager.getInstance().videoCacheHit();
        }
        return existed;
    }

    private boolean checkVideoNetCurrentLimit(APVideoReq videoReq, APVideoDownloadCallback callback, long time) {
        boolean ret = ConfigManager.getInstance().getIntValue(ConfigConstants.MULTIMEDIA_CURRENT_LIMIT, 0) >= 2;
        if (ret) {
            String id = videoReq.getPath();
            this.mStatus.remove(id);
            if (callback != null) {
                APVideoDownloadRsp rsp = new APVideoDownloadRsp();
                rsp.setFullVideoId(id);
                rsp.setRetCode(2000);
                rsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
                callback.onDownloadError(rsp);
            }
            if (this.mLogMap.remove(id) != null) {
                UCLogUtil.UC_MM_C14(2000, this.mSize, (int) (System.currentTimeMillis() - time), id, "", 1, ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG, videoReq.getBizType(), false);
            }
        }
        return ret;
    }

    public void downloadVideo(String id, APVideoDownloadCallback callback, String business) {
        APVideoReq req = new APVideoReq();
        req.setPath(id);
        req.setVideoDownloadCallback(callback);
        req.setBusinessId(business);
        req.setPriority(5);
        req.setHttps(false);
        downloadVideo(req);
    }

    /* access modifiers changed from: private */
    public void downloadVideo(APVideoReq videoReq) {
        mLogger.d("downloadVideo input req:" + videoReq, new Object[0]);
        String id = videoReq.getPath();
        APVideoDownloadCallback callback = videoReq.getVideoDownloadCallback();
        this.mLogMap.put(id, "");
        if (!id.contains(MergeUtil.SEPARATOR_KV)) {
            mLogger.e("download video id must be cloudid!", new Object[0]);
            this.mStatus.remove(id);
            if (callback != null) {
                APVideoDownloadRsp rsp = new APVideoDownloadRsp();
                rsp.from(buildFileDownRsp(7, id, null));
                rsp.setFullVideoId(id);
                callback.onDownloadError(rsp);
            }
            if (this.mLogMap.remove(id) != null) {
                UCLogUtil.UC_MM_C14(7, 0, 0, id, "", 1, "download video id must be cloudid!", videoReq.getBizType(), false);
                return;
            }
            return;
        }
        String vid = id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA));
        String vpath = VideoFileManager.getInstance().generateVideoPath(id);
        final long start = System.currentTimeMillis();
        if (FileUtils.checkFile(vpath)) {
            this.mStatus.remove(id);
            if (!isVideoAvailable(id)) {
                VideoFileManager.getInstance().insertRecord(vid, "", 2, 3, videoReq.getBusinessId(), videoReq.getExpiredTime());
            }
            if (callback != null) {
                callback.onDownloadFinished(buildVideoDownRsp(buildFileDownRsp(0, vid, vpath), id));
            }
            if (PathUtils.isPreloadNeedReport(videoReq.getBusinessId(), vid)) {
                UCLogUtil.UC_MM_48("0", vid, LogItem.MM_C18_K4_VD);
            }
            if (!PathUtils.checkIsResourcePreDownload(videoReq.getBusinessId())) {
                CacheHitManager.getInstance().videoCacheHit();
                return;
            }
            return;
        }
        String videoMd5 = videoReq.getVideoMd5();
        final APFileReq req = new APFileReq();
        req.setCloudId(vid);
        req.setSavePath(vpath);
        req.setCallGroup(1003);
        req.businessId = videoReq.getBusinessId();
        req.setCacheWhileError(true);
        req.setPriority(videoReq.getPriority());
        req.setHttps(videoReq.isHttps());
        req.setMd5(videoMd5);
        req.setBizType(videoReq.getBizType());
        req.setExpiredTime(videoReq.getExpiredTime());
        req.setTimeout(videoReq.getTimeout());
        APVideoLoadStatus status = this.mStatus.get(id);
        if (status != null) {
            status.mProgress = 0;
            status.mStatus = 3;
            this.mStatus.put(id, status);
        } else {
            APVideoLoadStatus info = new APVideoLoadStatus();
            info.mProgress = 0;
            info.mStatus = 3;
            this.mStatus.put(id, info);
        }
        this.mSize = 0;
        if (checkVideoNetCurrentLimit(videoReq, callback, start)) {
            mLogger.d("downloadVideo failed by net limit id: " + id, new Object[0]);
            return;
        }
        final boolean hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
        final APVideoDownloadCallback aPVideoDownloadCallback = callback;
        final String str = id;
        final String str2 = vid;
        this.mFileService.downLoad(req, (APFileDownCallback) new APFileDownCallback() {
            public void onDownloadStart(APMultimediaTaskModel arg0) {
                if (aPVideoDownloadCallback != null) {
                    aPVideoDownloadCallback.onDownloadStart(arg0);
                }
            }

            public void onDownloadProgress(APMultimediaTaskModel arg0, int arg1, long arg2, long arg3) {
                APVideoLoadStatus status = (APVideoLoadStatus) MultimediaVideoServiceImpl.this.mStatus.get(str);
                if (status != null) {
                    status.mProgress = arg1;
                    status.mStatus = 3;
                    MultimediaVideoServiceImpl.this.mStatus.put(str, status);
                }
                if (aPVideoDownloadCallback != null) {
                    aPVideoDownloadCallback.onDownloadProgress(arg0, arg1);
                }
                MultimediaVideoServiceImpl.this.mSize = arg3;
            }

            public void onDownloadFinished(APMultimediaTaskModel arg0, APFileDownloadRsp arg1) {
                MultimediaVideoServiceImpl.this.mStatus.remove(str);
                VideoFileManager.getInstance().insertRecord(str2, "", 2, 3, req.getBusinessId(), req.getExpiredTime());
                if (aPVideoDownloadCallback != null) {
                    APVideoDownloadRsp rsp = new APVideoDownloadRsp();
                    rsp.from(arg1);
                    rsp.setFullVideoId(str);
                    aPVideoDownloadCallback.onDownloadFinished(rsp);
                }
                if (MultimediaVideoServiceImpl.this.mLogMap.remove(str) != null) {
                    UCLogUtil.UC_MM_C14(0, MultimediaVideoServiceImpl.this.mSize, (int) (System.currentTimeMillis() - start), str, arg1.getTraceId(), 1, "", req.getBizType(), false);
                }
            }

            public void onDownloadError(APMultimediaTaskModel arg0, APFileDownloadRsp arg1) {
                MultimediaVideoServiceImpl.this.mStatus.remove(str);
                if (aPVideoDownloadCallback != null) {
                    APVideoDownloadRsp rsp = new APVideoDownloadRsp();
                    rsp.from(arg1);
                    rsp.setFullVideoId(str);
                    if (rsp.getFileReq() == null) {
                        rsp.setFileReq(req);
                    }
                    aPVideoDownloadCallback.onDownloadError(rsp);
                }
                if (MultimediaVideoServiceImpl.this.mLogMap.remove(str) != null) {
                    UCLogUtil.UC_MM_C14(arg1.getRetCode(), MultimediaVideoServiceImpl.this.mSize, (int) (System.currentTimeMillis() - start), str, arg1.getTraceId(), 1, arg1.getMsg(), req.getBizType(), !MultimediaVideoServiceImpl.this.isNeedUcLog(hasNetwork, arg1.getRetCode()));
                }
            }

            public void onDownloadBatchProgress(APMultimediaTaskModel arg0, int arg1, int arg2, long arg3, long arg4) {
            }
        }, req.getBusinessId());
    }

    private void downloadVideoSyncInner(APVideoReq videoReq, APVideoDownloadRsp rsp) {
        mLogger.d("downloadVideoSyncInner input req:" + videoReq, new Object[0]);
        String id = videoReq.getPath();
        this.mLogMap.put(id, "");
        if (!id.contains(MergeUtil.SEPARATOR_KV)) {
            mLogger.e("download video id must be cloudid!", new Object[0]);
            if (this.mLogMap.remove(id) != null) {
                UCLogUtil.UC_MM_C14(7, 0, 0, id, "", 1, "download video id must be cloudid!", videoReq.getBizType(), false);
                return;
            }
            return;
        }
        String vid = id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA));
        String vpath = VideoFileManager.getInstance().generateVideoPath(id);
        long start = System.currentTimeMillis();
        APVideoDownloadCallback callback = videoReq.getVideoDownloadCallback();
        if (FileUtils.checkFile(vpath)) {
            if (!isVideoAvailable(id)) {
                VideoFileManager.getInstance().insertRecord(vid, "", 2, 3, videoReq.getBusinessId(), videoReq.getExpiredTime());
            }
            if (callback != null) {
                APFileDownloadRsp fileDownloadRsp = buildFileDownRsp(0, vid, vpath);
                if (rsp != null) {
                    rsp.from(fileDownloadRsp);
                    rsp.setFullVideoId(id);
                    callback.onDownloadFinished(rsp);
                }
            }
            if (PathUtils.isPreloadNeedReport(videoReq.getBusinessId(), vid)) {
                UCLogUtil.UC_MM_48("0", vid, LogItem.MM_C18_K4_VD);
            }
            if (!PathUtils.checkIsResourcePreDownload(videoReq.getBusinessId())) {
                CacheHitManager.getInstance().videoCacheHit();
                return;
            }
            return;
        }
        requestByFileServiceInner(videoReq, vpath, rsp, start);
    }

    private void requestByFileServiceInner(APVideoReq videoReq, String vpath, APVideoDownloadRsp rsp, long start) {
        final APFileReq req = new APFileReq();
        req.setCloudId(videoReq.getVideoId());
        req.setSavePath(vpath);
        req.setCallGroup(1003);
        req.businessId = videoReq.getBusinessId();
        req.setMd5(videoReq.getVideoMd5());
        req.setHttps(videoReq.isHttps());
        req.setPriority(videoReq.getPriority());
        req.setBizType(videoReq.getBizType());
        req.setExpiredTime(videoReq.getExpiredTime());
        req.setTimeout(videoReq.getTimeout());
        this.mSize = 0;
        final String id = videoReq.getPath();
        final APVideoDownloadCallback callback = videoReq.getVideoDownloadCallback();
        if (checkVideoNetCurrentLimit(videoReq, callback, start)) {
            mLogger.d("requestByFileServiceInner failed by net limit req: " + videoReq, new Object[0]);
            return;
        }
        final boolean hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
        final APVideoReq aPVideoReq = videoReq;
        final APVideoDownloadRsp aPVideoDownloadRsp = rsp;
        final long j = start;
        this.mFileService.downLoadSync(req, new APFileDownCallback() {
            public void onDownloadStart(APMultimediaTaskModel arg0) {
                if (callback != null) {
                    callback.onDownloadStart(arg0);
                }
            }

            public void onDownloadProgress(APMultimediaTaskModel arg0, int arg1, long arg2, long arg3) {
                if (callback != null) {
                    callback.onDownloadProgress(arg0, arg1);
                }
                MultimediaVideoServiceImpl.this.mSize = arg3;
            }

            public void onDownloadFinished(APMultimediaTaskModel arg0, APFileDownloadRsp arg1) {
                VideoFileManager.getInstance().insertRecord(id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA)), "", 2, 3, aPVideoReq.getBusinessId(), req.getExpiredTime());
                aPVideoDownloadRsp.from(arg1);
                aPVideoDownloadRsp.setFullVideoId(id);
                if (callback != null) {
                    callback.onDownloadFinished(aPVideoDownloadRsp);
                }
                if (MultimediaVideoServiceImpl.this.mLogMap.remove(id) != null) {
                    UCLogUtil.UC_MM_C14(0, MultimediaVideoServiceImpl.this.mSize, (int) (System.currentTimeMillis() - j), id, arg1.getTraceId(), 1, "", aPVideoReq.getBizType(), false);
                }
            }

            public void onDownloadError(APMultimediaTaskModel arg0, APFileDownloadRsp arg1) {
                aPVideoDownloadRsp.from(arg1);
                aPVideoDownloadRsp.setFullVideoId(id);
                if (callback != null) {
                    if (aPVideoDownloadRsp.getFileReq() == null) {
                        aPVideoDownloadRsp.setFileReq(req);
                    }
                    callback.onDownloadError(aPVideoDownloadRsp);
                }
                if (MultimediaVideoServiceImpl.this.mLogMap.remove(id) != null) {
                    UCLogUtil.UC_MM_C14(arg1.getRetCode(), MultimediaVideoServiceImpl.this.mSize, (int) (System.currentTimeMillis() - j), id, arg1.getTraceId(), 1, arg1.getMsg(), aPVideoReq.getBizType(), !MultimediaVideoServiceImpl.this.isNeedUcLog(hasNetwork, arg1.getRetCode()));
                }
            }

            public void onDownloadBatchProgress(APMultimediaTaskModel arg0, int arg1, int arg2, long arg3, long arg4) {
            }
        }, videoReq.getBusinessId());
    }

    public void startPlay(String id, SightVideoPlayView playView) {
        if (playView != null) {
            if (playView.isPlaying()) {
                playView.stop();
            }
            playView.setVideoId(id);
            playView.start();
        }
    }

    public void loadShortVideo(String id, SightVideoPlayView playView, Drawable defDrawable, APVideoDownloadCallback callback, boolean forceVideo, String business) {
        loadShortVideo(id, playView, null, null, defDrawable, callback, forceVideo, business);
    }

    public void loadShortVideo(String id, SightVideoPlayView playView, Integer width, Integer height, Drawable defDrawable, APVideoDownloadCallback callback, boolean forceVideo, String business) {
        APVideoReq req = new APVideoReq();
        req.setPath(id);
        req.setWidth(width);
        req.setHeight(height);
        req.setDefDrawable(defDrawable);
        req.setVideoDownloadCallback(callback);
        req.setForceVideo(forceVideo);
        req.setPriority(5);
        req.businessId = business;
        loadShortVideoInner(req, playView);
    }

    private void loadShortVideoInner(APVideoReq req, SightVideoPlayView playView) {
        String path;
        mLogger.d("loadShortVideoInner, req: " + req, new Object[0]);
        String id = req.getPath();
        final APVideoDownloadCallback callback = req.getVideoDownloadCallback();
        if (TextUtils.isEmpty(id)) {
            mLogger.e("Input id is empty!", new Object[0]);
            if (callback != null) {
                APVideoDownloadRsp rsp = new APVideoDownloadRsp();
                rsp.from(buildFileDownRsp(7, id, "Input id is empty!"));
                rsp.setFullVideoId(id);
                callback.onDownloadError(rsp);
                return;
            }
            return;
        }
        this.mLogMap.put(id, "");
        ViewAssistant.getInstance().setViewTag(playView, id);
        if (isVideoAvailable(id)) {
            startPlay(id, playView);
            if (playView == null && callback != null) {
                String path2 = id;
                if (PathUtils.extractFile(id) == null) {
                    path = VideoFileManager.getInstance().getVideoPathById(path2);
                } else {
                    path = PathUtils.extractFile(path2).getAbsolutePath();
                }
                callback.onDownloadFinished(buildVideoDownRsp(buildFileDownRsp(0, id, path), id));
            }
            String vid = id;
            if (id.contains(MergeUtil.SEPARATOR_KV)) {
                vid = id.substring(0, id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA));
            }
            if (PathUtils.isPreloadNeedReport(req.getBusinessId(), vid)) {
                UCLogUtil.UC_MM_48("0", vid, LogItem.MM_C18_K4_VD);
            }
            if (!PathUtils.checkIsResourcePreDownload(req.getBusinessId())) {
                CacheHitManager.getInstance().videoCacheHit();
            }
        } else if (!id.contains(MergeUtil.SEPARATOR_KV)) {
            mLogger.e("you have set a localid or path, but file missed!", new Object[0]);
            if (callback != null) {
                APVideoDownloadRsp rsp2 = new APVideoDownloadRsp();
                rsp2.from(buildFileDownRsp(7, id, "you have set a localid or path, but file missed!"));
                rsp2.setFullVideoId(id);
                callback.onDownloadError(rsp2);
            }
            if (this.mLogMap.remove(id) != null) {
                UCLogUtil.UC_MM_C14(7, 0, 0, id, "", 1, "you have set a localid or path, but file missed!", req.getBizType(), false);
            }
        } else {
            String thumbId = id.substring(id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1, id.length());
            final long start = System.currentTimeMillis();
            if (!this.mStatus.containsKey(id)) {
                APVideoLoadStatus info = new APVideoLoadStatus();
                info.mProgress = 0;
                info.mStatus = 1;
                this.mStatus.put(id, info);
                String imageMd5 = req.getImageMd5();
                final boolean hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
                final ViewWrapper viewWrapper = new ViewWrapper(playView, id);
                Integer width = req.getWidth();
                Integer height = req.getHeight();
                int priority = req.getPriority();
                boolean bHttps = req.isHttps();
                APImageLoadRequest request = new APImageLoadRequest();
                request.defaultDrawable = req.getDefDrawable();
                request.path = thumbId;
                request.width = width == null ? 640 : width.intValue();
                request.height = height == null ? 640 : height.intValue();
                request.setPriority(priority);
                request.setMd5(imageMd5);
                request.setHttps(bHttps);
                request.setBizType(req.getBizType());
                request.setExpiredTime(req.getExpiredTime());
                request.setTimeout(req.getTimeout());
                final String str = id;
                final APVideoReq aPVideoReq = req;
                request.callback = new APImageDownLoadCallback() {
                    public void onSucc(final APImageDownloadRsp rsp) {
                        Runnable runnable = new Runnable() {
                            public void run() {
                                if (MultimediaVideoServiceImpl.this.checkViewReused(viewWrapper)) {
                                    if (callback != null) {
                                        APVideoDownloadRsp videoDownloadRsp = new APVideoDownloadRsp();
                                        videoDownloadRsp.from(rsp);
                                        videoDownloadRsp.setFullVideoId(str);
                                        videoDownloadRsp.setRetCode(8);
                                        callback.onDownloadError(videoDownloadRsp);
                                    }
                                    MultimediaVideoServiceImpl.this.mStatus.remove(str);
                                    return;
                                }
                                if (callback != null) {
                                    APVideoDownloadRsp videoDownloadRsp2 = new APVideoDownloadRsp();
                                    videoDownloadRsp2.from(rsp);
                                    videoDownloadRsp2.setFullVideoId(str);
                                    callback.onThumbDownloadFinished(videoDownloadRsp2);
                                }
                                if (aPVideoReq.isForceVideo()) {
                                    APVideoReq videoReq = aPVideoReq.clone();
                                    videoReq.setVideoDownloadCallback(new APVideoDownloadCallbackWrapper(callback, viewWrapper));
                                    MultimediaVideoServiceImpl.this.downloadVideo(videoReq);
                                    return;
                                }
                                MultimediaVideoServiceImpl.this.mStatus.remove(str);
                            }
                        };
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            TaskScheduleManager.get().execute(runnable);
                        } else {
                            runnable.run();
                        }
                    }

                    public void onProcess(String path, int percentage) {
                    }

                    public void onError(APImageDownloadRsp rsp, Exception e) {
                        APVideoDownloadRsp videoDownloadRsp = new APVideoDownloadRsp();
                        videoDownloadRsp.from(rsp);
                        videoDownloadRsp.setFullVideoId(str);
                        MultimediaVideoServiceImpl.this.mStatus.remove(str);
                        if (callback != null) {
                            callback.onDownloadError(videoDownloadRsp);
                        }
                        APImageRetMsg ret = rsp.getRetmsg();
                        if (ret != null && ret.getCode().ordinal() != RETCODE.CANCEL.ordinal() && ret.getCode().ordinal() != RETCODE.REUSE.ordinal() && MultimediaVideoServiceImpl.this.mLogMap.remove(str) != null) {
                            UCLogUtil.UC_MM_C14(rsp.getRetmsg().getCode().ordinal(), MultimediaVideoServiceImpl.this.mSize, (int) (System.currentTimeMillis() - start), str, "", 0, rsp.getRetmsg().getMsg(), aPVideoReq.getBizType(), !MultimediaVideoServiceImpl.this.isNeedUcLog(hasNetwork, rsp.getRetmsg().getCode().ordinal()));
                        }
                    }
                };
                final SightVideoPlayView sightVideoPlayView = playView;
                request.displayer = new APDisplayer() {
                    public void display(View view, Drawable drawable, String sourcePath) {
                        MultimediaVideoServiceImpl.mLogger.p("loadShortVideo display called### path: " + sourcePath + ", drawable: " + drawable + ", view: " + sightVideoPlayView, new Object[0]);
                        if (!MultimediaVideoServiceImpl.this.checkViewReused(viewWrapper) && (drawable instanceof BitmapDrawable)) {
                            MultimediaVideoServiceImpl.mLogger.d("drawBitmap", new Object[0]);
                            if (sightVideoPlayView != null) {
                                sightVideoPlayView.drawBitmap(((BitmapDrawable) drawable).getBitmap());
                            }
                        }
                    }
                };
                this.mImageService.loadImage(request, req.getBusinessId());
            }
        }
    }

    public APVideoDownloadRsp loadShortVideoSync(String id, APVideoDownloadCallback callback, String business) {
        APVideoReq req = new APVideoReq();
        req.setPath(id);
        req.setVideoDownloadCallback(callback);
        req.setBusinessId(business);
        req.setHttps(false);
        req.setPriority(5);
        return loadShortVideoSyncInner(req);
    }

    private APVideoDownloadRsp loadShortVideoSyncInner(APVideoReq videoReq) {
        mLogger.d("loadShortVideoSyncInner, input req:" + videoReq, new Object[0]);
        String id = videoReq.getPath();
        final APVideoDownloadCallback cb = videoReq.getVideoDownloadCallback();
        final APVideoDownloadRsp loadRsp = new APVideoDownloadRsp();
        if (TextUtils.isEmpty(id) || !id.contains(MergeUtil.SEPARATOR_KV)) {
            mLogger.e("Illegal cloudid, do nothing!", new Object[0]);
            loadRsp.from(buildFileDownRsp(7, id, null));
            loadRsp.setFullVideoId(id);
            if (cb != null) {
                cb.onDownloadError(loadRsp);
            }
            UCLogUtil.UC_MM_C14(7, 0, 0, id, "", 0, "Illegal cloudid, do nothing!", videoReq.getBizType(), false);
        } else {
            String jpath = VideoFileManager.getInstance().generateThumbPath(id);
            String imageMd5 = videoReq.getImageMd5();
            final boolean hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
            APFileReq req = new APFileReq();
            req.setCloudId(id.substring(id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1));
            req.setSavePath(jpath);
            req.setCallGroup(1003);
            req.setPriority(videoReq.getPriority());
            req.setHttps(videoReq.isHttps());
            req.setMd5(imageMd5);
            req.setBizType(videoReq.getBizType());
            req.setExpiredTime(videoReq.getExpiredTime());
            req.setTimeout(videoReq.getTimeout());
            if (FileUtils.checkFile(jpath)) {
                try {
                    String imId = id.substring(id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1, id.length());
                    if (PathUtils.isPreloadNeedReport(videoReq.getBusinessId(), imId)) {
                        UCLogUtil.UC_MM_48("0", imId, "im");
                    }
                    if (!PathUtils.checkIsResourcePreDownload(videoReq.getBusinessId())) {
                        CacheHitManager.getInstance().imageCacheHit();
                    }
                } catch (Exception e) {
                    mLogger.d(TAG, "loadShortVideoSyncInner  isPreloadNeedReport is exp=" + e.toString());
                }
                if (cb != null) {
                    cb.onThumbDownloadFinished(loadRsp);
                    downloadVideoSyncInner(videoReq, loadRsp);
                }
            } else {
                final String str = id;
                final APVideoReq aPVideoReq = videoReq;
                APFileDownloadRsp downloadRsp = this.mFileService.downLoadSync(req, new APFileDownCallback() {
                    public void onDownloadStart(APMultimediaTaskModel arg0) {
                    }

                    public void onDownloadProgress(APMultimediaTaskModel arg0, int arg1, long arg2, long arg3) {
                        MultimediaVideoServiceImpl.this.mSize = arg3;
                    }

                    public void onDownloadFinished(APMultimediaTaskModel arg0, APFileDownloadRsp arg1) {
                        loadRsp.from(arg1);
                        loadRsp.setFullVideoId(str);
                        if (cb != null) {
                            cb.onThumbDownloadFinished(loadRsp);
                        }
                    }

                    public void onDownloadError(APMultimediaTaskModel arg0, APFileDownloadRsp arg1) {
                        loadRsp.from(arg1);
                        loadRsp.setFullVideoId(str);
                        if (cb != null) {
                            cb.onDownloadError(loadRsp);
                        }
                        UCLogUtil.UC_MM_C14(arg1.getRetCode(), 0, 0, str, arg1.getTraceId(), 0, arg1.getMsg(), aPVideoReq.getBizType(), MultimediaVideoServiceImpl.this.isNeedUcLog(hasNetwork, arg1.getRetCode()));
                    }

                    public void onDownloadBatchProgress(APMultimediaTaskModel arg0, int arg1, int arg2, long arg3, long arg4) {
                    }
                }, videoReq.getBusinessId());
                if (downloadRsp != null && downloadRsp.getRetCode() == 0) {
                    downloadVideoSyncInner(videoReq, loadRsp);
                }
            }
        }
        return loadRsp;
    }

    /* access modifiers changed from: private */
    public boolean checkViewReused(ViewWrapper viewWrapper) {
        return ViewAssistant.getInstance().checkViewReused(viewWrapper);
    }

    public void loadAlbumVideo(String id, ImageView view, Drawable defDrawable, APImageDownLoadCallback callback, String business) {
        if (view == null) {
            mLogger.e(TAG, "loadAlbumVideo view is null");
            return;
        }
        loadAlbumVideo(id, view, Integer.valueOf(640), Integer.valueOf(640), defDrawable, callback, business);
    }

    public void loadAlbumVideo(String id, ImageView view, Integer width, Integer height, Drawable defDrawable, APImageDownLoadCallback callback, String business) {
        APVideoReq req = new APVideoReq();
        req.setPath(id);
        req.setWidth(width);
        req.setHeight(height);
        req.setDefDrawable(defDrawable);
        req.setImageDownloadCallback(callback);
        req.setHttps(false);
        req.setPriority(5);
        req.setBusinessId(business);
        loadAlbumVideoInner(req, view);
    }

    public void loadAlbumVideoInner(APVideoReq req, ImageView view) {
        int h = 0;
        mLogger.d("loadAlbumVideoInner input req:" + req, new Object[0]);
        String tid = req.getPath();
        if (!PathUtils.isContentFile(tid)) {
            File file = PathUtils.extractFile(tid);
            if (file != null) {
                tid = file.getAbsolutePath();
            } else if (tid.contains(MergeUtil.SEPARATOR_KV)) {
                tid = tid.substring(tid.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1);
                String jpath = VideoFileManager.getInstance().getThumbPathById(tid);
                if (FileUtils.checkFile(jpath)) {
                    tid = jpath;
                }
            } else {
                tid = VideoFileManager.getInstance().getThumbPathById(tid);
            }
        }
        Integer width = req.getWidth();
        Integer height = req.getHeight();
        int w = width == null ? 0 : width.intValue();
        if (height != null) {
            h = height.intValue();
        }
        String imageMd5 = req.getImageMd5();
        APImageLoadRequest request = new APImageLoadRequest();
        request.defaultDrawable = req.getDefDrawable();
        request.path = tid;
        request.imageView = view;
        request.width = w;
        request.height = h;
        request.callback = req.getImageDownloadCallback();
        request.setPriority(req.getPriority());
        request.setMd5(imageMd5);
        request.setHttps(req.isHttps());
        request.setBusinessId(req.getBusinessId());
        request.setBizType(req.getBizType());
        request.setTimeout(req.getTimeout());
        this.mImageService.loadImage(request, req.getBusinessId());
    }

    public APAlbumVideoInfo compressVideo(String path, String business) {
        return compressVideo(path, business, null, null);
    }

    public APAlbumVideoInfo compressVideo(String path, String business, CompressLevel level, Bundle extra) {
        int i;
        mLogger.d("compressVideo input path:" + path + ", business: " + business + ";extra=" + extra, new Object[0]);
        if (LocalIdTool.isLocalIdRes(path)) {
            path = LocalIdTool.get().decodeToPath(path);
        }
        try {
            if (!SandboxWrapper.isContentUriPath(path)) {
                path = PathUtils.extractFile(path).getAbsolutePath();
            }
            VideoInfo info = getVideoInfo(path);
            APAlbumVideoInfo albuminfo = new APAlbumVideoInfo();
            if (info == null) {
                return albuminfo;
            }
            String video_id = String.valueOf(System.currentTimeMillis());
            String destPath = VideoFileManager.getInstance().generateVideoPath(video_id);
            albuminfo.mDuration = info.duration;
            albuminfo.mPath = destPath;
            albuminfo.mId = video_id;
            long ts = System.currentTimeMillis();
            int destWidth = info.width;
            int destHeigh = info.height;
            if (!needCompress(info, level)) {
                copyFile(path, destPath);
                mLogger.d("needCompress false, took " + (System.currentTimeMillis() - ts) + RPCDataParser.TIME_MS, new Object[0]);
            } else if (level != null) {
                int[] dest = doCompressByLevel(info, path, destPath, level);
                destWidth = dest[0];
                destHeigh = dest[1];
            } else {
                String mp4DstPath = destPath + PhotoParam.VIDEO_SUFFIX;
                mLogger.d("videoCompress tmp mp4 path: " + mp4DstPath, new Object[0]);
                if (info.width * info.height > 522240) {
                    destWidth = (int) Math.sqrt((double) ((522240 * info.width) / info.height));
                    destHeigh = (info.height * destWidth) / info.width;
                }
                VideoCompressConfig config = new VideoCompressConfig();
                config.bitrate = 1126400;
                config.height = destHeigh - (destHeigh % 2);
                config.width = destWidth - (destWidth % 2);
                config.inputPath = path;
                config.outputPath = mp4DstPath;
                config.enableMediaCodec = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.isEnableCompressMediaCodec() ? 1 : 0;
                config.useFixTimebase = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.videoCompressStrategy;
                config.enableAudioCopy = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.enableAudioCopy;
                NativeViedoCompress(config);
                mLogger.d("compressVideo config: " + JSON.toJSONString(config) + ", took " + (System.currentTimeMillis() - ts) + "ms, rename: " + new File(mp4DstPath).renameTo(new File(destPath)), new Object[0]);
            }
            albuminfo.mSize = new File(destPath).length();
            albuminfo.mSuccess = true;
            String thumb_id = video_id + "_thumb";
            copyFile(getVideoThumbnail(path, business), VideoFileManager.getInstance().generateThumbPath(thumb_id));
            CacheContext.get().getDiskCache().update(path, destPath, 8);
            VideoFileManager.getInstance().insertRecord("", albuminfo.mId, 2, 18, business);
            VideoFileManager.getInstance().insertRecord("", thumb_id, 1, 24, business);
            if (albuminfo.mSuccess) {
                i = 0;
            } else {
                i = -1;
            }
            UCLogUtil.UC_MM_C15(i, albuminfo.mSize, (int) (System.currentTimeMillis() - ts), 1, albuminfo.mDuration, info.width, info.height, destWidth, destHeigh, albuminfo.mSize, "");
            return albuminfo;
        } catch (Exception e) {
            mLogger.e(e, "compressVideo exp path=" + path, new Object[0]);
            UCLogUtil.UC_MM_C15(-1, 0, 0, 1, 0, 0, 0, 0, 0, 0, "compressVideo exp with path");
            return new APAlbumVideoInfo();
        }
    }

    private int[] doCompressByLevel(VideoInfo info, String path, String destPath, CompressLevel level) {
        int bitrate;
        int[] dest;
        long ts = System.currentTimeMillis();
        String mp4DstPath = destPath + PhotoParam.VIDEO_SUFFIX;
        mLogger.d("doCompressByLevel tmp mp4 path: " + mp4DstPath, new Object[0]);
        VideoCompressConfig config = new VideoCompressConfig();
        int i = info.videoBitrate;
        switch (level) {
            case V320P:
                bitrate = VideoUtils.getTargetBitrate(info.videoBitrate, VideoUtils.BITRATE_320);
                dest = VideoUtils.compareSize(info.width, info.height, 320);
                break;
            case V540P:
                bitrate = VideoUtils.getTargetBitrate(info.videoBitrate, 1126400);
                dest = VideoUtils.compareSize(info.width, info.height, FFmpegSessionConfig.VIDEO_SOFTENCODE_W);
                break;
            case V720P:
                bitrate = VideoUtils.getTargetBitrate(info.videoBitrate, VideoUtils.BITRATE_720);
                dest = VideoUtils.compareSize(info.width, info.height, 720);
                break;
            case V1080P:
                bitrate = VideoUtils.getTargetBitrate(info.videoBitrate, VideoUtils.BITRATE_1080);
                dest = VideoUtils.compareSize(info.width, info.height, 1072);
                break;
            default:
                bitrate = VideoUtils.getTargetBitrate(info.videoBitrate, 1126400);
                dest = VideoUtils.compareSize(info.width, info.height, FFmpegSessionConfig.VIDEO_SOFTENCODE_W);
                break;
        }
        config.bitrate = bitrate;
        config.height = dest[1] - (dest[1] % 2);
        config.width = dest[0] - (dest[0] % 2);
        config.inputPath = path;
        config.outputPath = mp4DstPath;
        config.enableMediaCodec = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.isEnableCompressMediaCodec() ? 1 : 0;
        config.useFixTimebase = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.videoCompressStrategy;
        config.enableAudioCopy = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.enableAudioCopy;
        NativeViedoCompress(config);
        mLogger.d("doCompressByLevel config: " + JSON.toJSONString(config) + ", took " + (System.currentTimeMillis() - ts) + "ms, rename: " + new File(mp4DstPath).renameTo(new File(destPath)), new Object[0]);
        return dest;
    }

    /* JADX INFO: finally extract failed */
    private int NativeViedoCompress(VideoCompressConfig config) {
        VideoUtils.loadLibrariesOnce();
        ParcelFileDescriptor pfd = null;
        try {
            if (SandboxWrapper.isContentUriPath(config.inputPath)) {
                pfd = SandboxWrapper.openParcelFileDescriptor(Uri.parse(config.inputPath));
                if (pfd.getFd() > 0) {
                    config.inputPath = PathUtils.genPipePath(pfd.getFd());
                }
                mLogger.d("NativeViedoCompress path=" + config.inputPath, new Object[0]);
            }
            int videoCompress = MMNativeEngineApi.videoCompress(config);
            IOUtils.closeQuietly(pfd);
            return videoCompress;
        } catch (MMNativeException e) {
            mLogger.e(e, "videoCompress exp code=" + e.getCode(), new Object[0]);
            IOUtils.closeQuietly((ParcelFileDescriptor) null);
            return -1;
        } catch (Throwable th) {
            IOUtils.closeQuietly((ParcelFileDescriptor) null);
            throw th;
        }
    }

    public int checkVideoTransmissible(String path) {
        boolean audioOK;
        boolean videoOK;
        mLogger.d("checkVideoTransmissible: " + path, new Object[0]);
        if (PathUtils.extractFile(path) != null) {
            path = PathUtils.extractFile(path).getAbsolutePath();
        } else if (!SandboxWrapper.isContentUriPath(path)) {
            return -1;
        }
        long size = SandboxWrapper.getFileSize(path);
        VideoInfo info = getVideoInfo(path);
        if (info == null) {
            return -1;
        }
        if (!MMNativeEngineApi.getSupportPixList().contains(Integer.valueOf(info.videoPixFmt))) {
            return 2;
        }
        if (info.audioEncodeId == info.AAC || info.audioEncodeId == 0) {
            audioOK = true;
        } else {
            audioOK = false;
        }
        if (info.videoEncodeId == info.H264) {
            videoOK = true;
        } else {
            videoOK = false;
        }
        int sizeLimit = getUpVideoSizeLimit();
        if (!audioOK || !videoOK) {
            return 2;
        }
        if (((float) size) / (((float) info.videoBitrate) / 1126400.0f) >= ((float) (((long) sizeLimit) * 1048576)) * 1.0f) {
            return 1;
        }
        return 0;
    }

    private VideoInfo getVideoInfo(String path) {
        return VideoUtils.getVideoInfo(path);
    }

    private boolean needCompress(VideoInfo info, CompressLevel level) {
        int bitrate = 1126400;
        if (level != null) {
            switch (level) {
                case V320P:
                    bitrate = VideoUtils.BITRATE_320;
                    break;
                case V540P:
                    bitrate = 1126400;
                    break;
                case V720P:
                    bitrate = VideoUtils.BITRATE_720;
                    break;
                case V1080P:
                    bitrate = VideoUtils.BITRATE_1080;
                    break;
            }
        }
        if (info.videoBitrate <= bitrate || info.videoEncodeId != info.H264) {
            return false;
        }
        return true;
    }

    public void optimizeView(AbsListView listView, OnScrollListener listener) {
    }

    public ArrayList<APVideoInfo> getRecentVideo(int day) {
        ArrayList videoList = new ArrayList();
        List models = VideoFileManager.getInstance().queryRecentVideo(((long) day) * 86400000);
        mLogger.p("getRecentVideo: day " + day + ", count:" + (models == null ? 0 : models.size()) + ", models: " + models, new Object[0]);
        if (models != null && !models.isEmpty()) {
            for (FileCacheModel model : models) {
                if (model.type == 2 && (model.tag & 32) != 0 && (model.tag & 64) == 0) {
                    APVideoInfo info = new APVideoInfo();
                    info.rotation = VideoUtils.getVideoRotation(model.path);
                    info.width = FFmpegSessionConfig.VIDEO_SOFTENCODE_W;
                    info.height = 960;
                    info.id = model.cacheKey;
                    videoList.add(info);
                }
            }
        }
        return videoList;
    }

    public APVideoLoadStatus getVideoLoadStatus(String id) {
        return this.mStatus.get(id);
    }

    public String getVideoThumbnail(String path, String businessId) {
        Bitmap bitmap;
        mLogger.d("getVideoThumbnail, input path:" + path, new Object[0]);
        if (PathUtils.extractFile(path) != null) {
            path = PathUtils.extractFile(path).getAbsolutePath();
        } else if (!SandboxWrapper.isContentUriPath(path)) {
            path = VideoFileManager.getInstance().getVideoPathById(path);
        }
        mLogger.d("getVideoThumbnail, parsed path:" + path, new Object[0]);
        String result = "";
        DiskCache cache = CacheContext.get().getDiskCache();
        FileCacheModel cacheModel = cache.get(path, new QueryFilter() {
            public FileCacheModel parse(List<FileCacheModel> list) {
                if (list == null || list.isEmpty()) {
                    return null;
                }
                return list.get(0);
            }
        });
        if (cacheModel == null || TextUtils.isEmpty(cacheModel.path)) {
            mLogger.d("No cache hit. getVideoThumbnail: get thumb from video file", new Object[0]);
            if (SandboxWrapper.isContentUriPath(path)) {
                bitmap = VideoUtils.getVideoFrameByUri(Uri.parse(path), 0);
            } else {
                bitmap = VideoUtils.getVideoFrame(path, 0);
            }
            if (bitmap != null) {
                try {
                    cache.save(path, bitmap, businessId);
                    cache.save(path, 1, 8, businessId, Long.MAX_VALUE);
                    result = cache.getPath(path);
                } catch (Exception e) {
                    mLogger.e(e, "cache.save error, key: " + path + ";exp msg: " + e.getMessage(), new Object[0]);
                }
            }
        } else {
            result = cacheModel.path;
        }
        mLogger.d("getVideoThumbnail end. path: " + path + ", result: " + result, new Object[0]);
        return result;
    }

    private boolean copyFile(String srcPath, String destPath) {
        OutputStream out;
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(destPath) || srcPath.equals(destPath)) {
            return false;
        }
        if (SandboxWrapper.isContentUriPath(srcPath)) {
            String filePath = SandboxWrapper.copyUriFile(srcPath, destPath);
            if (!TextUtils.isEmpty(filePath)) {
                return new File(filePath).exists();
            }
            return false;
        }
        File src = new File(srcPath);
        File dst = new File(destPath);
        if (!src.exists() || !src.isFile()) {
            return false;
        }
        if (dst.exists() && dst.isFile()) {
            return false;
        }
        InputStream input = null;
        OutputStream out2 = null;
        try {
            dst.getParentFile().mkdirs();
            InputStream input2 = new FileInputStream(src);
            try {
                out = new FileOutputStream(dst);
            } catch (IOException e) {
                e = e;
                input = input2;
                try {
                    mLogger.e(e, "copy file exception: srcPath:" + srcPath + ",destPath:" + destPath + ",exp msg:" + e.getMessage(), new Object[0]);
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(out2);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(out2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                input = input2;
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(out2);
                throw th;
            }
            try {
                IOUtils.copy(input2, out);
                IOUtils.closeQuietly(input2);
                IOUtils.closeQuietly(out);
                return true;
            } catch (IOException e2) {
                e = e2;
                out2 = out;
                input = input2;
                mLogger.e(e, "copy file exception: srcPath:" + srcPath + ",destPath:" + destPath + ",exp msg:" + e.getMessage(), new Object[0]);
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(out2);
                return false;
            } catch (Throwable th3) {
                th = th3;
                out2 = out;
                input = input2;
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(out2);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            mLogger.e(e, "copy file exception: srcPath:" + srcPath + ",destPath:" + destPath + ",exp msg:" + e.getMessage(), new Object[0]);
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(out2);
            return false;
        }
    }

    public void burnFile(String id) {
        mLogger.d("burnFile input id: " + id, new Object[0]);
        FileUtils.delete(VideoFileManager.getInstance().getThumbPathById(id));
        FileUtils.delete(VideoFileManager.getInstance().getVideoPathById(id));
        if (id.contains(MergeUtil.SEPARATOR_KV)) {
            VideoFileManager.getInstance().removeRecordById(id.substring(0, id.lastIndexOf(MergeUtil.SEPARATOR_KV)));
            VideoFileManager.getInstance().removeRecordById(id.substring(id.lastIndexOf(MergeUtil.SEPARATOR_KV) + 1));
            return;
        }
        VideoFileManager.getInstance().removeRecordById(id);
        VideoFileManager.getInstance().removeRecordById(id + "_thumb");
    }

    public void deleteShortVideo(String localid) {
        VideoFileManager.getInstance().deleteByLocalId(localid);
    }

    public String getVideoPathById(String id) {
        if (PathUtils.extractFile(id) != null) {
            return PathUtils.extractFile(id).getAbsolutePath();
        }
        return VideoFileManager.getInstance().getVideoPathById(id);
    }

    public String getThumbPathById(String id) {
        mLogger.d("getThumbPathById input id:" + id, new Object[0]);
        if (PathUtils.extractFile(id) != null) {
            return PathUtils.extractFile(id).getAbsolutePath();
        }
        return VideoFileManager.getInstance().getThumbPathById(id);
    }

    public void loadVideoThumb(String id, View playView, Drawable defDrawable, APImageDownLoadCallback callback, String business) {
        loadVideoThumb(id, playView, null, null, defDrawable, callback, business);
    }

    public void loadVideoThumb(String id, final View playView, Integer width, Integer height, Drawable defDrawable, APImageDownLoadCallback callback, String business) {
        String tid;
        mLogger.d("loadVideoThumb() input id:" + id + ", playView: " + playView, new Object[0]);
        String tid2 = id;
        File file = PathUtils.extractFile(id);
        if (file != null) {
            tid = file.getAbsolutePath();
        } else if (TextUtils.isEmpty(tid2) || !tid2.contains(MergeUtil.SEPARATOR_KV)) {
            tid = VideoFileManager.getInstance().getThumbPathById(tid2);
        } else {
            tid = tid2.substring(tid2.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA) + 1);
            String jpath = VideoFileManager.getInstance().getThumbPathById(tid);
            if (FileUtils.checkFile(jpath)) {
                tid = jpath;
            }
        }
        mLogger.d("loadVideoThumb(), parsed path for imageservice:" + tid + ", w: " + width + ",h: " + height + ", playView: " + playView, new Object[0]);
        String reuseTag = id;
        ViewAssistant.getInstance().setViewTag(playView, reuseTag);
        final ViewWrapper viewWrapper = new ViewWrapper(playView, reuseTag);
        APImageLoadRequest request = new APImageLoadRequest();
        request.callback = callback;
        request.defaultDrawable = defDrawable;
        request.path = tid;
        request.width = width == null ? 640 : width.intValue();
        request.height = height == null ? 640 : height.intValue();
        request.businessId = business;
        request.displayer = new APDisplayer() {
            public void display(View view, Drawable drawable, String path) {
                MultimediaVideoServiceImpl.mLogger.p("loadVideoThumb display called### path: " + path + ", drawable: " + drawable + ", view: " + playView, new Object[0]);
                if (!MultimediaVideoServiceImpl.this.checkViewReused(viewWrapper) && (drawable instanceof BitmapDrawable)) {
                    MultimediaVideoServiceImpl.mLogger.d("drawBitmap", new Object[0]);
                    if (playView instanceof SightVideoPlayView) {
                        ((SightVideoPlayView) playView).drawBitmap(((BitmapDrawable) drawable).getBitmap());
                    } else if (playView instanceof VideoPlayView) {
                        ((VideoPlayView) playView).drawBitmap(((BitmapDrawable) drawable).getBitmap());
                    }
                }
            }
        };
        this.mImageService.loadImage(request, business);
    }

    public void cancelDownload(String id) {
        int index = id.indexOf(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA);
        if (index < 0) {
            mLogger.e("download video id must be cloudid!", new Object[0]);
            return;
        }
        String vid = id.substring(0, index);
        String pid = id.substring(index + 1);
        APMultimediaTaskModel videoModel = this.mFileService.getLoadTaskStatusByCloudId(vid);
        if (videoModel != null) {
            this.mFileService.cancelLoad(videoModel.getTaskId());
        } else {
            mLogger.d("cancelDownload cannot found task for " + vid, new Object[0]);
        }
        APMultimediaTaskModel picModel = this.mFileService.getLoadTaskStatusByCloudId(pid);
        if (picModel != null) {
            this.mFileService.cancelLoad(picModel.getTaskId());
        } else {
            mLogger.d("cancelDownload cannot found task for " + pid, new Object[0]);
        }
    }

    public void cancelUpload(String id) {
        String thumbPath;
        String videoPath = null;
        if (PathUtils.extractFile(id) == null) {
            thumbPath = VideoFileManager.getInstance().getThumbPathById(id);
            videoPath = VideoFileManager.getInstance().getVideoPathById(id);
        } else {
            thumbPath = PathUtils.extractFile(id).getAbsolutePath();
        }
        if (TextUtils.isEmpty(videoPath)) {
            APMultimediaTaskModel model = this.mFileService.getUpTaskStatusByCloudId(thumbPath);
            if (model != null) {
                this.mFileService.cancelUp(model.getTaskId());
            } else {
                mLogger.d("cancelUpload cannot found task for " + thumbPath, new Object[0]);
            }
        }
        if (TextUtils.isEmpty(videoPath)) {
            APMultimediaTaskModel videoModel = this.mFileService.getUpTaskStatusByCloudId(videoPath);
            if (videoModel != null) {
                this.mFileService.cancelUp(videoModel.getTaskId());
            } else {
                mLogger.d("cancelUpload cannot found task for " + thumbPath, new Object[0]);
            }
        }
    }

    public APVideoInfo parseVideoInfo(String path) {
        return VideoUtils.parseVideoInfo(path);
    }

    private APFileDownloadRsp buildFileDownRsp(int code, String source, String savePath) {
        APFileReq req = new APFileReq();
        req.setSavePath(savePath);
        req.setCloudId(source);
        APFileDownloadRsp rsp = new APFileDownloadRsp();
        rsp.setFileReq(req);
        rsp.setRetCode(code);
        return rsp;
    }

    private APVideoDownloadRsp buildVideoDownRsp(APFileDownloadRsp fileRsp, String fullVideoId) {
        APVideoDownloadRsp rsp = new APVideoDownloadRsp();
        rsp.from(fileRsp);
        rsp.setFullVideoId(fullVideoId);
        return rsp;
    }

    private APFileUploadRsp buildFileUploadRsp(int code, String source, String savePath) {
        APFileReq req = new APFileReq();
        req.setSavePath(savePath);
        req.setCloudId(source);
        APFileUploadRsp rsp = new APFileUploadRsp();
        rsp.setFileReq(req);
        rsp.setRetCode(code);
        return rsp;
    }

    private APVideoUploadRsp buildVideoUploadRsp(APFileUploadRsp fileRsp, String fullVideoId) {
        APVideoUploadRsp rsp = new APVideoUploadRsp();
        rsp.setRsp(fileRsp);
        rsp.mId = fullVideoId;
        return rsp;
    }

    public APMultimediaTaskModel getVideoDownloadStatus(String videoId) {
        if (TextUtils.isEmpty(videoId) || !videoId.contains(MergeUtil.SEPARATOR_KV)) {
            return null;
        }
        return this.mFileService.getLoadTaskStatusByCloudId(videoId.split("\\|")[0]);
    }

    public boolean isNeedUpdateSo() {
        return NeonSoManager.get().isNeedUpgradeFFmpegSo();
    }

    public void updateSo(APFileDownCallback cb) {
    }

    public void loadNecessaryLibs() {
        if (!isNeedUpdateSo()) {
            try {
                loadLibrary("ijkffmpeg");
            } catch (Throwable t) {
                mLogger.e(t, "loadNecessaryLibs error", new Object[0]);
            }
        } else {
            mLogger.e("loadNecessaryLibs error", new Object[0]);
        }
    }

    public void loadLibrary(String name) {
        AppUtils.loadLibrary(name);
    }

    public int saveVideo(String localId, File targetFile) {
        return VideoFileManager.getInstance().saveVideo(localId, targetFile);
    }

    private void checkParams(Object activityOrFragment) {
        if (VERSION.SDK_INT >= 23 && activityOrFragment == null) {
            throw new RuntimeException("sdk version over 23 must has activti or fragment param for permission check");
        }
    }

    private int getUpVideoSizeLimit() {
        this.videoUpSizeLimit = this.videoUpSizeLimit > 0 ? this.videoUpSizeLimit : ConfigManager.getInstance().getCommonConfigItem().videoUpSizeLimit;
        return this.videoUpSizeLimit;
    }

    public APVideoDownloadRsp loadShortVideo(APVideoReq req, SightVideoPlayView playView, String business) {
        if (!TextUtils.isEmpty(business)) {
            req.businessId = business;
        }
        if (req.isSync()) {
            return loadShortVideoSyncInner(req);
        }
        loadShortVideoInner(req, playView);
        return null;
    }

    public void loadAlbumVideo(APVideoReq req, ImageView view, String business) {
        if (!TextUtils.isEmpty(business)) {
            req.businessId = business;
        }
        if (req.isForceVideo()) {
            downloadVideo(req);
        } else {
            loadAlbumVideoInner(req, view);
        }
    }

    public APVideoUploadRsp uploadVideo(APVideoUpReq apVideoUpReq) {
        if (LocalIdTool.isLocalIdRes(apVideoUpReq.getLocalId())) {
            apVideoUpReq.setLocalId(LocalIdTool.get().decodeToPath(apVideoUpReq.getLocalId()));
        }
        if (apVideoUpReq.isSync()) {
            return uploadVideoSync(apVideoUpReq);
        }
        uploadVideoAsync(apVideoUpReq);
        return null;
    }

    private void uploadVideoAsync(APVideoUpReq upReq) {
        mLogger.d("uploadVideoAsync input upReq:" + upReq, new Object[0]);
        String path = upReq.getLocalId();
        final String id = upReq.getLocalId();
        final boolean hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
        final APVideoUploadCallback callback = upReq.getCallback();
        if (PathUtils.extractFile(path) == null) {
            String path2 = VideoFileManager.getInstance().getVideoPathById(path);
            mLogger.d("uploadVideoAsync, parsed path:" + path2, new Object[0]);
            final String jpath = getVideoThumbnail(path2, upReq.getBusinessId());
            final String path0 = path2;
            final long start = System.currentTimeMillis();
            this.mLogMap.put(id, "");
            APFileReq req = new APFileReq();
            req.setIsNeedCache(false);
            req.setSavePath(jpath);
            addAliasFileName(jpath, req, true);
            req.setCallGroup(1003);
            req.setBusinessId(upReq.getBusinessId());
            req.setBizType(upReq.getBizType());
            req.setTimeout(upReq.getTimeout());
            final APVideoUpReq aPVideoUpReq = upReq;
            this.mFileService.upLoad(req, (APFileUploadCallback) new APFileUploadCallback() {
                public void onUploadStart(APMultimediaTaskModel arg0) {
                    if (callback != null) {
                        callback.onUploadStart(arg0);
                    }
                }

                public void onUploadProgress(APMultimediaTaskModel arg0, int progress, long arg2, long arg3) {
                    if (callback != null) {
                        callback.onUploadProgress(arg0, progress / 20);
                    }
                }

                public void onUploadFinished(APMultimediaTaskModel arg0, APFileUploadRsp arg1) {
                    APVideoUploadRsp rsp = new APVideoUploadRsp();
                    rsp.mThumbId = arg0.getCloudId();
                    MultimediaVideoServiceImpl.mLogger.p("uploadVideoAsync(), thumb upload done, path: " + jpath + ", cloudId: " + arg0.getCloudId(), new Object[0]);
                    if (1 == aPVideoUpReq.getVideoType()) {
                        CacheContext.get().getDiskCache().update(path0, 8);
                    } else {
                        CacheContext.get().getDiskCache().update(path0, 4);
                    }
                    MultimediaVideoServiceImpl.this.uploadVideoInternal(aPVideoUpReq, rsp, start, 0, true);
                }

                public void onUploadError(APMultimediaTaskModel arg0, APFileUploadRsp arg1) {
                    APVideoUploadRsp rsp = new APVideoUploadRsp();
                    rsp.setRsp(arg1);
                    if (callback != null) {
                        callback.onUploadError(rsp);
                    }
                    long size = new File(jpath).length();
                    if (MultimediaVideoServiceImpl.this.mLogMap.remove(id) != null) {
                        UCLogUtil.UC_MM_C13(arg1.getRetCode(), size, (int) (System.currentTimeMillis() - start), 0, 0, "", "", arg1.getTraceId(), 0, 1, 0, arg1.getMsg(), aPVideoUpReq.getBizType(), !MultimediaVideoServiceImpl.this.isNeedUcLog(hasNetwork, arg1.getRetCode()));
                    }
                }
            }, upReq.getBusinessId());
            return;
        }
        APVideoUploadRsp rsp = buildVideoUploadRsp(buildFileUploadRsp(7, id, null), id);
        if (callback != null) {
            callback.onUploadError(rsp);
        }
    }

    private APVideoUploadRsp uploadVideoSync(APVideoUpReq upReq) {
        mLogger.d("#######uploadVideoSync sync input upReq:" + upReq, new Object[0]);
        final String id = upReq.getLocalId();
        String path = upReq.getLocalId();
        final APVideoUploadCallback callback = upReq.getCallback();
        final boolean bRealProg = isRealProgress();
        final boolean hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
        if (PathUtils.extractFile(path) == null) {
            String path2 = VideoFileManager.getInstance().getThumbPathById(path + "_thumb");
            final APVideoUploadRsp uploadRsp = new APVideoUploadRsp();
            APFileReq req = new APFileReq();
            req.setIsNeedCache(false);
            req.setSavePath(path2);
            addAliasFileName(path2, req, true);
            req.setCallGroup(1003);
            req.setBizType(upReq.getBizType());
            req.setBusinessId(upReq.getBusinessId());
            req.setTimeout(upReq.getTimeout());
            final String jpath = path2;
            final long start = System.currentTimeMillis();
            this.mLogMap.put(id, "");
            int randomPrg = getRandomProgress(bRealProg, callback);
            final APVideoUpReq aPVideoUpReq = upReq;
            APFileUploadRsp imageFileUploadRsp = this.mFileService.upLoadSync(req, new APFileUploadCallback() {
                public void onUploadStart(APMultimediaTaskModel arg0) {
                    if (callback != null) {
                        callback.onUploadStart(arg0);
                    }
                }

                public void onUploadProgress(APMultimediaTaskModel arg0, int arg1, long arg2, long arg3) {
                    if (callback != null && bRealProg) {
                        callback.onUploadProgress(arg0, arg1 / 20);
                    }
                }

                public void onUploadFinished(APMultimediaTaskModel arg0, APFileUploadRsp arg1) {
                    if (TextUtils.isEmpty(uploadRsp.mThumbId)) {
                        uploadRsp.mThumbId = arg0.getCloudId();
                        try {
                            ImageDiskCache.get().update(jpath, arg0.getCloudId());
                        } catch (Exception e) {
                            MultimediaVideoServiceImpl.mLogger.w("update cache relative error, jpath: " + jpath + ", cloudId: " + arg0.getCloudId(), new Object[0]);
                        }
                    }
                }

                public void onUploadError(APMultimediaTaskModel arg0, APFileUploadRsp arg1) {
                    uploadRsp.setRsp(arg1);
                    if (callback != null) {
                        callback.onUploadError(uploadRsp);
                    }
                    long size = new File(jpath).length();
                    if (MultimediaVideoServiceImpl.this.mLogMap.remove(id) != null) {
                        UCLogUtil.UC_MM_C13(arg1.getRetCode(), size, (int) (System.currentTimeMillis() - start), 0, 0, "", "", arg1.getTraceId(), 0, aPVideoUpReq.getVideoType(), 0, arg1.getMsg(), aPVideoUpReq.getBizType(), !MultimediaVideoServiceImpl.this.isNeedUcLog(hasNetwork, arg1.getRetCode()));
                    }
                }
            }, upReq.getBusinessId());
            if (imageFileUploadRsp == null || imageFileUploadRsp.getRetCode() != 0) {
                return uploadRsp;
            }
            uploadVideoInternal(upReq, uploadRsp, start, randomPrg, bRealProg);
            return uploadRsp;
        }
        APVideoUploadRsp rsp = buildVideoUploadRsp(buildFileUploadRsp(7, id, null), id);
        if (upReq.getCallback() != null) {
            upReq.getCallback().onUploadError(rsp);
        }
        return rsp;
    }

    /* access modifiers changed from: private */
    public void uploadVideoInternal(APVideoUpReq upReq, APVideoUploadRsp rsp, long startTime, int randProg, boolean bRealProg) {
        String path;
        final int index;
        String path2 = upReq.getLocalId();
        final String id = upReq.getLocalId();
        final APVideoUploadCallback callback = upReq.getCallback();
        if (PathUtils.extractFile(path2) == null) {
            path = VideoFileManager.getInstance().getVideoPathById(path2);
        } else {
            path = PathUtils.extractFile(path2).getAbsolutePath();
        }
        APFileReq req = new APFileReq();
        req.setBizType(upReq.getBizType());
        req.setIsNeedCache(false);
        req.setSavePath(path);
        addAliasFileName(path, req, false);
        req.setCallGroup(1003);
        final boolean hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
        final long size = new File(path).length();
        VideoInfo info = getVideoInfo(path);
        final int fps = info == null ? 0 : (int) info.fps;
        final int duration = info == null ? 0 : info.duration;
        if (bRealProg) {
            index = 5;
        } else {
            index = randProg;
        }
        final AtomicInteger lastProg = new AtomicInteger(index);
        Logger.D(TAG, "uploadVideoInternal index=" + index + ";bRealProg=" + bRealProg, new Object[0]);
        final APVideoUploadRsp aPVideoUploadRsp = rsp;
        final long j = startTime;
        final APVideoUpReq aPVideoUpReq = upReq;
        APFileUploadCallback upCallback = new APFileUploadCallback() {
            public void onUploadStart(APMultimediaTaskModel arg0) {
            }

            public void onUploadProgress(APMultimediaTaskModel arg0, int arg1, long arg2, long arg3) {
                if (callback != null) {
                    int prog = (((100 - index) * arg1) / 100) + index;
                    if (prog > lastProg.get()) {
                        lastProg.set(prog);
                    }
                    callback.onUploadProgress(arg0, lastProg.get());
                }
            }

            public void onUploadFinished(APMultimediaTaskModel arg0, APFileUploadRsp arg1) {
                aPVideoUploadRsp.setRsp(arg1);
                aPVideoUploadRsp.mVideoId = arg0.getCloudId();
                aPVideoUploadRsp.mId = aPVideoUploadRsp.mVideoId + MergeUtil.SEPARATOR_KV + aPVideoUploadRsp.mThumbId;
                VideoFileManager.getInstance().setCloudIdByLocalId(aPVideoUploadRsp.mVideoId, id);
                VideoFileManager.getInstance().setCloudIdByLocalId(aPVideoUploadRsp.mThumbId, id + "_thumb");
                if (callback != null) {
                    callback.onUploadFinished(aPVideoUploadRsp);
                }
                if (MultimediaVideoServiceImpl.this.mLogMap.remove(id) != null) {
                    UCLogUtil.UC_MM_C13(0, size, (int) (System.currentTimeMillis() - j), fps, duration, arg0.getCloudId(), arg1.getExtra(UCLogUtil.KEY_MD5), arg1.getTraceId(), 0, aPVideoUpReq.getVideoType(), 1, arg1.getMsg(), aPVideoUpReq.getBizType(), false);
                }
            }

            public void onUploadError(APMultimediaTaskModel arg0, APFileUploadRsp arg1) {
                APVideoUploadRsp rsp = new APVideoUploadRsp();
                rsp.setRsp(arg1);
                if (callback != null) {
                    callback.onUploadError(rsp);
                }
                if (MultimediaVideoServiceImpl.this.mLogMap.remove(id) != null) {
                    UCLogUtil.UC_MM_C13(arg1.getRetCode(), size, (int) (System.currentTimeMillis() - j), fps, duration, "", arg1.getExtra(UCLogUtil.KEY_MD5), arg1.getTraceId(), 0, aPVideoUpReq.getVideoType(), 1, arg1.getMsg(), aPVideoUpReq.getBizType(), MultimediaVideoServiceImpl.this.isNeedUcLog(hasNetwork, arg1.getRetCode()));
                }
            }
        };
        if (upReq.isSync()) {
            this.mFileService.upLoadSync(req, upCallback, upReq.getBusinessId());
        } else {
            this.mFileService.upLoad(req, upCallback, upReq.getBusinessId());
        }
    }

    /* access modifiers changed from: private */
    public boolean isNeedUcLog(boolean hasNetwork, int ret) {
        return hasNetwork || ret == 0;
    }

    public APVideoEditor getVideoEditor(String path, String bussiness) {
        return new VideoEditorImpl(path, bussiness);
    }

    public String buildUrl(String id, Bundle extraConfig) {
        return UriFactory.buildGetUrl(id, new Request(0));
    }
}
