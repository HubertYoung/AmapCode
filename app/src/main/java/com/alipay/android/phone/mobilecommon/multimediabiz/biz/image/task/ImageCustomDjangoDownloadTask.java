package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.DrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.CustomLoadHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.DjangoOriginalDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class ImageCustomDjangoDownloadTask extends ImageNetTask implements APFileDownCallback {
    private static final Logger a = Logger.getLogger((String) "ImgCustomDjDownloadTask");
    private DjangoOriginalDownloader b = null;
    private CountDownLatch c;
    private int d = -1;

    public ImageCustomDjangoDownloadTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
    }

    /* access modifiers changed from: protected */
    public Bitmap executeTask() {
        if (TextUtils.isEmpty(FileUtils.getMediaDir("im"))) {
            notifyError(RETCODE.DOWNLOAD_FAILED, "invalid download dir", new IllegalArgumentException("invalid download dir"));
        } else {
            this.c = new CountDownLatch(1);
            this.b = new DjangoOriginalDownloader(this.loadReq, ImageDiskCache.get().genPathByKey(this.loadReq.cacheKey.key), this);
            String downFile = this.b.download(this.loadReq, (Bundle) null);
            this.c.await();
            a.p("download finish~~~", new Object[0]);
            if (FileUtils.checkFile(downFile)) {
                a.p("download finish~~~   handleDownloadedFile", new Object[0]);
                addImDbRecord(this.loadReq, downFile);
                a(new File(downFile));
                QueryCacheManager.getInstance().queryOriginalAndPut(this.loadReq.path);
            }
        }
        return null;
    }

    private void a(File downFile) {
        removeTask();
        if (this.loadReq.taskModel != null) {
            this.loadReq.taskModel.setTotalSize(downFile.length());
        }
        Drawable drawable = CustomLoadHelper.loadDrawable(downFile, this.options.getDrawableDecoder());
        a.p("handleDownloadedFile downloadFile: " + downFile + ", drawable: " + drawable, new Object[0]);
        if (drawable != null) {
            DrawableCache.get().put(this.loadReq.cacheKey.complexCacheKey(), drawable);
            for (ImageLoadReq req : this.loadReqSet) {
                new ImageCustomDisplayTask(drawable, req, null).runTask();
            }
            return;
        }
        a.p("handleDownloadedFile downloadFile: " + downFile + ", fail~~~~", new Object[0]);
        notifyError(RETCODE.DOWNLOAD_FAILED, "decode fail", new IllegalStateException("decode fail"));
    }

    public String getTaskId() {
        return this.loadReq.path;
    }

    public void cancel() {
        if (this.b != null) {
            this.b.cancel();
        }
        super.cancel();
    }

    public void onDownloadStart(APMultimediaTaskModel taskInfo) {
        a.p("onDownloadStart", new Object[0]);
    }

    public void onDownloadProgress(APMultimediaTaskModel taskInfo, int progress, long hasDownSize, long total) {
        for (ImageLoadReq req : this.loadReqSet) {
            if (req.downLoadCallback != null) {
                req.downLoadCallback.onProcess(req.source, progress);
            }
        }
        a.p("onDownloadProgress progress: " + progress + ", hasDownSize: " + hasDownSize + ", total: " + total, new Object[0]);
    }

    public void onDownloadBatchProgress(APMultimediaTaskModel taskInfo, int progress, int curIndex, long hasDownSize, long total) {
        a.p("onDownloadBatchProgress", new Object[0]);
    }

    public void onDownloadFinished(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
        this.d = 0;
        this.c.countDown();
        a.p("onDownloadFinished taskState: " + this.d, new Object[0]);
    }

    public void onDownloadError(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
        removeTask();
        this.c.countDown();
        this.d = rsp.getRetCode();
        Exception e = new Exception("download failed");
        for (ImageLoadReq req : this.loadReqSet) {
            if (req.downLoadCallback != null) {
                notifyError(req, RETCODE.DOWNLOAD_FAILED, "download fail", e);
            }
        }
    }
}
