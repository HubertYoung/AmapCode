package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.LruCache;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.video.CompressLevel;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoCutCallback;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoEditor;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoThumbnailListener;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoCutReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoCutRsp;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoInfo;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoThumbnailReq;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoThumbnailRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import com.alipay.streammedia.video.editor.CutParam;
import com.alipay.streammedia.video.editor.CutResult;
import com.alipay.streammedia.video.editor.NativeVideoEditor;
import com.alipay.streammedia.video.editor.PickerParam;
import com.alipay.streammedia.video.editor.VideoGetFrameResult;
import com.alipay.streammedia.video.editor.VideoPicker;
import com.alipay.streammedia.video.editor.VideoSeekResult;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;

@TargetApi(12)
public class VideoEditorImpl extends APVideoEditor {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "VideoEditor");
    /* access modifiers changed from: private */
    public String b;
    private String c;
    private APVideoThumbnailListener d;
    private APVideoInfo e;
    private VideoPicker f;
    /* access modifiers changed from: private */
    public boolean g = false;
    private ParcelFileDescriptor h = null;
    private LruCache<String, Bitmap> i = new LruCache<String, Bitmap>() {
        /* access modifiers changed from: protected */
        public int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }
    };
    private VideoFrame j;
    private Handler k = new Handler(TaskScheduleManager.get().commonLooper()) {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                VideoEditorImpl.this.b((APVideoThumbnailReq) msg.obj);
            }
        }
    };

    public VideoEditorImpl(String path, String business) {
        this.b = PathUtils.extractPath(path);
        this.c = business;
        a.d("create video editor.path=" + this.b + ", business=" + this.c, new Object[0]);
    }

    public APVideoInfo getVideoInfo() {
        if (this.e == null) {
            this.e = VideoUtils.parseVideoInfo(this.b);
        }
        return this.e;
    }

    public void cutVideo(final APVideoCutReq apVideoCutReq, final APVideoCutCallback apVideoCutCallback) {
        TaskScheduleManager.get().commonExecutor().submit(new Runnable() {
            public void run() {
                int code;
                String destPath = null;
                APVideoCutReq adjustRequest = VideoEditorImpl.this.a(apVideoCutReq);
                long st = System.currentTimeMillis();
                String video_id = String.valueOf(System.currentTimeMillis());
                int rotation = 0;
                long start = System.currentTimeMillis();
                final CutParam p = new CutParam();
                ParcelFileDescriptor pfd = null;
                try {
                    NativeVideoEditor.loadLibrariesOnce(new SoLibLoader());
                    p.videoId = System.currentTimeMillis();
                    if (SandboxWrapper.isContentUriPath(VideoEditorImpl.this.b)) {
                        pfd = SandboxWrapper.openParcelFileDescriptor(Uri.parse(VideoEditorImpl.this.b));
                        if (pfd != null) {
                            p.src = PathUtils.genPipePath(pfd.detachFd());
                        } else {
                            p.src = VideoEditorImpl.this.b;
                        }
                    } else {
                        p.src = VideoEditorImpl.this.b;
                    }
                    destPath = VideoFileManager.getInstance().generateVideoPath(video_id);
                    p.dst = destPath + PhotoParam.VIDEO_SUFFIX;
                    p.startPts = adjustRequest.startPositon;
                    p.endPts = adjustRequest.endPosition;
                    VideoEditorImpl.this.a(adjustRequest, p, apVideoCutReq.quality);
                    p.debugLog = AppUtils.isDebug(AppUtils.getApplicationContext()) ? 1 : 0;
                    p.enableMediaCodec = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.isEnableMediaCodec() ? 1 : 0;
                    p.enableAudioCopy = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.enableAudioCopy;
                    VideoEditorImpl.this.g = true;
                    BackgroundExecutor.execute((Runnable) new Runnable() {
                        APVideoCutRsp a = new APVideoCutRsp();

                        public void run() {
                            while (VideoEditorImpl.this.g) {
                                long pts = 0;
                                try {
                                    Thread.currentThread();
                                    Thread.sleep(250);
                                    pts = NativeVideoEditor.getCurCompressPts(p.videoId);
                                } catch (Exception e) {
                                    VideoEditorImpl.a.e(e, "", new Object[0]);
                                }
                                if (pts > 0) {
                                    int progress = (int) (((((float) pts) * 1.0f) / ((float) (p.endPts - p.startPts))) * 100.0f);
                                    if (progress >= 0 && progress <= 100 && apVideoCutCallback != null) {
                                        this.a.progress = progress;
                                        apVideoCutCallback.onProgress(this.a);
                                    }
                                }
                            }
                        }
                    });
                    CutResult res = NativeVideoEditor.cut(p);
                    VideoEditorImpl.this.g = false;
                    if (res != null) {
                        code = res.code;
                        rotation = res.rotation;
                        if (code >= 0) {
                            code = VideoEditorImpl.this.a(video_id, p.dst);
                        }
                    } else {
                        code = -500;
                    }
                    VideoEditorImpl.this.g = false;
                    VideoEditorImpl.this.a(p, code, System.currentTimeMillis() - start, destPath);
                    IOUtils.closeQuietly(pfd);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    VideoEditorImpl.this.g = false;
                    VideoEditorImpl.this.a(p, 0, System.currentTimeMillis() - start, (String) null);
                    IOUtils.closeQuietly(pfd);
                    throw th2;
                }
                VideoEditorImpl.a.d("cut video cost time=" + (System.currentTimeMillis() - st) + ",start=" + adjustRequest.startPositon + ",end=" + adjustRequest.endPosition, new Object[0]);
                if (apVideoCutCallback != null) {
                    APVideoCutRsp rsp = new APVideoCutRsp();
                    rsp.id = video_id;
                    rsp.start = adjustRequest.startPositon;
                    rsp.end = adjustRequest.endPosition;
                    rsp.sourcePath = VideoEditorImpl.this.b;
                    rsp.targetWidht = adjustRequest.targetWidth;
                    rsp.targetHeight = adjustRequest.targetHeight;
                    rsp.rotation = rotation;
                    rsp.errCode = code;
                    rsp.destFilePath = destPath;
                    VideoEditorImpl.a.d("cutVideo rsp: " + rsp + ", req: " + JSON.toJSONString(adjustRequest), new Object[0]);
                    apVideoCutCallback.onVideoCutFinished(rsp);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(APVideoCutReq adjustRequest, CutParam p, CompressLevel quality) {
        int bitrate;
        int[] dest;
        if (quality == null) {
            p.dstWidth = adjustRequest.targetWidth;
            p.dstHeight = adjustRequest.targetHeight;
            return;
        }
        VideoInfo info = VideoUtils.getVideoInfo(this.b);
        int i2 = info.videoBitrate;
        switch (quality) {
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
        int compsWidth = dest[0] - (dest[0] % 2);
        int compsHeight = dest[1] - (dest[1] % 2);
        p.dstWidth = compsWidth;
        p.dstHeight = compsHeight;
        p.bitrate = bitrate;
        a.d("calCutQualitys compsWidth: " + compsWidth + ", compsHeight: " + compsHeight + ", compsBitrate: " + bitrate, new Object[0]);
    }

    /* access modifiers changed from: private */
    public void a(CutParam p, int code, long costTime, String dstPath) {
        if (p != null) {
            a.d("report param: " + JSON.toJSONString(p) + ", code: " + code + ", costTime: " + costTime + ", dstPath: " + dstPath, new Object[0]);
            Map ext4 = new HashMap();
            APVideoInfo info = getVideoInfo();
            ext4.put("path", p.src);
            ext4.put("os", String.valueOf(FileUtils.fileSize(p.src)));
            ext4.put("ow", String.valueOf(info.width));
            ext4.put("oh", String.valueOf(info.height));
            ext4.put("or", String.valueOf(info.rotation));
            ext4.put("sp", String.valueOf(p.startPts));
            ext4.put(H5Param.ENABLE_PROXY, String.valueOf(p.endPts));
            ext4.put(H5Param.CAN_DESTROY, String.valueOf(p.enableMediaCodec));
            UCLogUtil.UC_MM_C53(code, (int) FileUtils.fileSize(dstPath), (int) costTime, ext4);
        }
    }

    /* access modifiers changed from: private */
    public APVideoCutReq a(APVideoCutReq apVideoCutReq) {
        boolean z;
        APVideoCutReq cutReq = apVideoCutReq;
        if (apVideoCutReq.targetWidth <= 0 || apVideoCutReq.targetWidth <= 0) {
            APVideoInfo info = getVideoInfo();
            cutReq = new APVideoCutReq();
            cutReq.startPositon = apVideoCutReq.startPositon;
            cutReq.endPosition = apVideoCutReq.endPosition;
            if (info.width * info.height > 522240) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                cutReq.targetWidth = (int) Math.sqrt((double) ((info.width * 522240) / info.height));
                cutReq.targetHeight = (cutReq.targetWidth * info.height) / info.width;
            } else {
                cutReq.targetWidth = info.width;
                cutReq.targetHeight = info.height;
            }
        }
        cutReq.targetWidth -= cutReq.targetWidth % 2;
        cutReq.targetHeight -= cutReq.targetHeight % 2;
        a.d("adjustRequest src: " + apVideoCutReq + ", target: " + cutReq, new Object[0]);
        return cutReq;
    }

    /* access modifiers changed from: private */
    public int a(String videoId, String destPath) {
        String business = this.c;
        try {
            b(videoId, destPath);
        } catch (Exception e2) {
            a.e(e2, "saveThumb exp:", new Object[0]);
        }
        a.d("saveLocal rename from: " + destPath + "，" + new File(destPath).renameTo(new File(destPath.substring(0, destPath.lastIndexOf(46)))), new Object[0]);
        VideoFileManager.getInstance().insertRecord("", videoId, 2, 18, business);
        return 0;
    }

    private void b(String videoId, String destPath) {
        String thumb_id = videoId + "_thumb";
        String jpath = VideoFileManager.getInstance().generateThumbPath(thumb_id);
        Bitmap bitmap = VideoUtils.getVideoFrame(destPath, 0);
        if (bitmap == null) {
            a.w("System api failed, try getVideoFrame2", new Object[0]);
            bitmap = VideoUtils.getVideoFrame2(destPath, 0);
        }
        if (bitmap == null) {
            throw new RuntimeException("saveThumb error, destPath: " + destPath);
        }
        ImageUtils.compressJpg(bitmap, jpath);
        bitmap.recycle();
        CacheContext.get().getDiskCache().save(thumb_id, jpath, 1, 24, "", this.c, Long.MAX_VALUE);
    }

    public void setVideoThumbnalListener(APVideoThumbnailListener apVideoThumbnailListener) {
        this.d = apVideoThumbnailListener;
    }

    private synchronized int a(int targetWidth, int targetHeight) {
        int result;
        int i2 = 0;
        synchronized (this) {
            result = 0;
            if (this.f == null) {
                try {
                    NativeVideoEditor.loadLibrariesOnce(new SoLibLoader());
                } catch (MMNativeException e2) {
                    a.e(e2, "initVideoPickerOnce exp code=" + e2.getCode(), new Object[0]);
                } catch (Throwable th) {
                    IOUtils.closeQuietly(this.h);
                    throw th;
                }
                this.f = new VideoPicker();
                PickerParam params = new PickerParam();
                if (SandboxWrapper.isContentUriPath(this.b)) {
                    this.h = SandboxWrapper.openParcelFileDescriptor(Uri.parse(this.b));
                    if (this.h != null) {
                        params.src = PathUtils.genPipePath(this.h.detachFd());
                    } else {
                        params.src = this.b;
                    }
                    IOUtils.closeQuietly(this.h);
                } else {
                    params.src = this.b;
                }
                params.dstWidth = targetWidth;
                params.dstHeight = targetHeight;
                if (AppUtils.isDebug(AppUtils.getApplicationContext())) {
                    i2 = 1;
                }
                params.debugLog = i2;
                params.skipFrame = ConfigManager.getInstance().getCommonConfigItem().videoEditorConf.skipFrame;
                try {
                    result = this.f.init(params);
                } catch (MMNativeException e3) {
                    result = e3.getCode();
                }
                if (result != 0) {
                    this.f = null;
                }
            }
        }
        return result;
    }

    private void a(APVideoThumbnailReq req, int result, Bitmap bitmap) {
        if (this.d != null) {
            APVideoThumbnailRsp rsp = new APVideoThumbnailRsp();
            rsp.sourcePath = this.b;
            rsp.position = req.position;
            rsp.targetWidht = req.targetWidth;
            rsp.targetHeight = req.targetHeight;
            rsp.errCode = result;
            rsp.bitmap = bitmap;
            this.d.onGetThumbnail(req, rsp);
        }
    }

    private APVideoThumbnailReq a(APVideoThumbnailReq req) {
        int originalWidth;
        int originalHeight;
        APVideoInfo info = getVideoInfo();
        if (info != null) {
            if (info.rotation == 90 || info.rotation == 270) {
                originalWidth = info.height;
                originalHeight = info.width;
            } else {
                originalWidth = info.width;
                originalHeight = info.height;
            }
            if (!(originalWidth == 0 || originalHeight == 0 || (req.targetHeight <= originalHeight && req.targetWidth <= originalWidth))) {
                double widthRatio = (((double) req.targetWidth) * 1.0d) / ((double) originalWidth);
                double heightRatio = (((double) req.targetHeight) * 1.0d) / ((double) originalHeight);
                if (widthRatio > heightRatio) {
                    req.targetWidth = originalWidth;
                    req.targetHeight = (int) (((double) req.targetHeight) / widthRatio);
                } else {
                    req.targetWidth = (int) (((double) req.targetWidth) / heightRatio);
                    req.targetHeight = originalHeight;
                }
            }
        }
        return req;
    }

    /* access modifiers changed from: private */
    public void b(APVideoThumbnailReq req) {
        int result;
        int result2;
        APVideoThumbnailReq req2 = a(req);
        String key = req2.position + "_" + req2.targetWidth + "_" + req2.targetHeight;
        Bitmap bitmap = this.i.get(key);
        if (bitmap != null) {
            a(req2, 0, bitmap);
            return;
        }
        int result3 = a(req2.targetWidth, req2.targetHeight);
        if (result3 != 0) {
            a.d("handleGetVideoThumbnail initVideoPickerOnce error, result: " + result3, new Object[0]);
            a(req2, result3, bitmap);
            return;
        }
        VideoSeekResult sr = null;
        try {
            sr = this.f.seek(req2.position);
        } catch (MMNativeException e2) {
            a.e(e2, "mVideoPicker.seek exp code=" + e2.getCode(), new Object[0]);
        }
        if (sr == null) {
            result = -500;
        } else {
            result = sr.code;
        }
        if (result != 0) {
            a.d("handleGetVideoThumbnail seek error, result: " + result, new Object[0]);
            a(req2, result, bitmap);
            return;
        }
        Bitmap bitmap2 = Bitmap.createBitmap(req2.targetWidth, req2.targetHeight, Config.ARGB_8888);
        VideoGetFrameResult gr = null;
        try {
            gr = this.f.getFrame(req2.position, bitmap2);
        } catch (MMNativeException e3) {
            a.e(e3, "mVideoPicker.getFrame exp code=" + e3.getCode(), new Object[0]);
        }
        if (gr == null) {
            result2 = -500;
        } else {
            result2 = gr.code;
        }
        if (result2 == 0) {
            this.i.put(key, bitmap2);
            if (this.j == null) {
                this.j = new VideoFrame(req2.position, bitmap2);
            } else if (this.j.position < req2.position) {
                this.j.update(req2.position, bitmap2);
            }
        } else if (result2 != 2 || this.j == null) {
            a.d("handleGetVideoThumbnail getFrame error, result is " + result2, new Object[0]);
        } else {
            a.d("handleGetVideoThumbnail compensation of eof frame", new Object[0]);
            bitmap2 = this.j.bitmap;
        }
        a(req2, result2, bitmap2);
    }

    public void getVideoThumbnail(APVideoThumbnailReq apVideoThumbnailReq) {
        this.k.obtainMessage(1, apVideoThumbnailReq).sendToTarget();
    }

    public void release() {
        try {
            if (this.f != null) {
                this.f.release();
            }
        } catch (MMNativeException e2) {
            a.e(e2, "mVideoPicker release exp code=" + e2.getCode(), new Object[0]);
        } finally {
            IOUtils.closeQuietly(this.h);
        }
        this.i.evictAll();
    }
}
