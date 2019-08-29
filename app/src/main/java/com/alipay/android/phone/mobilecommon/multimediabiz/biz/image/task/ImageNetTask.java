package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.OriginalBitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.exception.CancelException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.progressive.ProgressiveMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.progressive.ProgressiveStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ImageNetTask extends ImageMMTask<Bitmap> {
    private boolean a = false;
    protected boolean bProgressive = false;
    protected final Set<ImageLoadReq> loadReqSet = Collections.synchronizedSet(new HashSet());
    protected ProgressiveStrategy progressiveStrategy = null;
    protected final AtomicBoolean removedFlag = new AtomicBoolean(false);

    /* access modifiers changed from: protected */
    public abstract Bitmap executeTask();

    public ImageNetTask(ImageLoadReq loadReq, ViewWrapper<View> wrapper) {
        super(loadReq, wrapper);
        addImageLoadReq(loadReq);
        enableLIFO();
    }

    /* access modifiers changed from: protected */
    public void removeTask() {
        if (!this.removedFlag.get()) {
            this.removedFlag.set(true);
            removeTask(getTaskId());
        }
    }

    public Bitmap taskRun() {
        if (this.loadReq.netPerf != null) {
            this.loadReq.netPerf.endWait();
        }
        this.a = true;
        long start = System.currentTimeMillis();
        try {
            if (this.loadReqSet.size() != 1 || !checkTask()) {
                return executeTask();
            }
            Logger.P("ImageNetTask", "call ImageNetTask loadReqSet.size() == 1 return !", new Object[0]);
            this.a = false;
            removeTask();
            Logger.TIME("ImageNetTask call " + this.loadReq.path + ", costTime: " + (System.currentTimeMillis() - start) + ", " + this.loadReq.path, System.currentTimeMillis() - start, new Object[0]);
            return null;
        } finally {
            this.a = false;
            if (this.loadReq.netPerf != null) {
                this.loadReq.netPerf.submitRemoteAsync();
            }
            removeTask();
            r4 = "ImageNetTask call ";
            StringBuilder append = new StringBuilder(r4).append(this.loadReq.path);
            r4 = ", costTime: ";
            StringBuilder append2 = append.append(r4).append(System.currentTimeMillis() - start);
            r4 = ", ";
            Logger.TIME(append2.append(r4).append(this.loadReq.path).toString(), System.currentTimeMillis() - start, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void addImDbRecord(ImageLoadReq loadReq, String imagePath) {
        if (!TextUtils.isEmpty(imagePath) && FileUtils.checkFile(imagePath)) {
            ImageDiskCache.get().savePath(new OriginalBitmapCacheKey(loadReq.cacheKey.key, false), imagePath, 128, loadReq.options.getBusinessId(), loadReq.getExpiredTime());
        }
    }

    public String getTaskId() {
        return this.loadReq.taskId;
    }

    public void onMergeTask(MMTask task) {
        if (task != null && (task instanceof ImageNetTask)) {
            addImageLoadReq(((ImageNetTask) task).loadReq);
        }
    }

    public void addImageLoadReq(ImageLoadReq loadReq) {
        synchronized (this.loadReqSet) {
            this.loadReqSet.add(loadReq);
        }
    }

    public void notifyCancel() {
        removeTask();
        Exception e = new CancelException();
        synchronized (this.loadReqSet) {
            Iterator it = this.loadReqSet.iterator();
            while (it.hasNext()) {
                ImageLoadReq req = it.next();
                if (req.downLoadCallback != null) {
                    notifyError(req, RETCODE.CANCEL, "load cancel", e);
                }
                it.remove();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyReuse() {
        removeTask();
        Exception e = new CancelException();
        synchronized (this.loadReqSet) {
            Iterator it = this.loadReqSet.iterator();
            while (it.hasNext()) {
                ImageLoadReq req = it.next();
                if (req.downLoadCallback != null) {
                    notifyError(req, RETCODE.REUSE, "load reuse", e);
                }
                it.remove();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyError(Exception e) {
        removeTask();
        String errMsg = getExceptionInfo(e);
        synchronized (this.loadReqSet) {
            Iterator it = this.loadReqSet.iterator();
            while (it.hasNext()) {
                ImageLoadReq req = it.next();
                if (req.downLoadCallback != null) {
                    notifyError(req, RETCODE.DOWNLOAD_FAILED, errMsg, e);
                }
                it.remove();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyTimeoutError(String msg) {
        removeTask();
        synchronized (this.loadReqSet) {
            Iterator it = this.loadReqSet.iterator();
            while (it.hasNext()) {
                ImageLoadReq req = it.next();
                if (req.downLoadCallback != null) {
                    notifyError(req, RETCODE.TIME_OUT, msg, null);
                }
                it.remove();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyLimitError() {
        removeTask();
        synchronized (this.loadReqSet) {
            Iterator it = this.loadReqSet.iterator();
            while (it.hasNext()) {
                ImageLoadReq req = it.next();
                if (req.downLoadCallback != null) {
                    notifyError(req, RETCODE.CURRENT_LIMIT, ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG, null);
                }
                it.remove();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyProgress(int percent) {
        synchronized (this.loadReqSet) {
            for (ImageLoadReq req : this.loadReqSet) {
                if (req.downLoadCallback != null) {
                    try {
                        req.downLoadCallback.onProcess(req.source, percent);
                    } catch (Exception e) {
                        Logger.W("ImageNetTask", "notifyProgress req: " + req + ", err: " + e, new Object[0]);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String getExceptionInfo(Exception e) {
        return (e != null ? e.getClass().getSimpleName() + ":" + e.getMessage() : "download fail") + ", loadReq: " + this.loadReq;
    }

    public boolean isRunning() {
        return this.a;
    }

    public void cancel() {
        super.cancel();
        cancelTask(getTaskId());
    }

    /* access modifiers changed from: protected */
    public void progressiveDisplay(int progress, long curSize, long totalSize, File file, long startTime) {
        if (!a(file)) {
            if (this.progressiveStrategy.isNeedProgressive(progress, curSize, file, this.loadReq.cacheKey)) {
                Logger.D("ImageNetTask", "progressiveDisplay ok=true ;progress=" + progress + ";curSize=" + curSize + ";key=" + this.loadReq.cacheKey, new Object[0]);
                int readSize = (int) curSize;
                byte[] buffer = new byte[(readSize + 2)];
                FileInputStream fis = null;
                try {
                    FileInputStream fis2 = new FileInputStream(file);
                    try {
                        int read = fis2.read(buffer, 0, readSize);
                        buffer[read] = -1;
                        buffer[read + 1] = -39;
                        a(buffer, progress);
                        if (this.loadReq.netPerf != null && this.loadReq.netPerf.pjpgTime == -1) {
                            this.loadReq.netPerf.pjpgTime = System.currentTimeMillis() - startTime;
                        }
                        IOUtils.closeQuietly((InputStream) fis2);
                    } catch (Exception e) {
                        e = e;
                        fis = fis2;
                        try {
                            Logger.E((String) "ImageNetTask", (Throwable) e, (String) "progressiveDisplay exp", new Object[0]);
                            IOUtils.closeQuietly((InputStream) fis);
                        } catch (Throwable th) {
                            th = th;
                            IOUtils.closeQuietly((InputStream) fis);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fis = fis2;
                        IOUtils.closeQuietly((InputStream) fis);
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    Logger.E((String) "ImageNetTask", (Throwable) e, (String) "progressiveDisplay exp", new Object[0]);
                    IOUtils.closeQuietly((InputStream) fis);
                }
            }
        }
    }

    private boolean a(File file) {
        return !this.bProgressive || !FileUtils.checkFile(file) || this.progressiveStrategy == null;
    }

    private void a(byte[] data, int progress) {
        FalconFacade facade = FalconFacade.get();
        BitmapCacheLoader cacheLoader = getCacheLoader();
        synchronized (this.loadReqSet) {
            for (ImageLoadReq req : this.loadReqSet) {
                DisplayImageOptions options = req.options;
                int[] fitSize = getFitSize(options.getWidth().intValue(), options.getHeight().intValue());
                Logger.D("ImageNetTask", "doJpegProgressiveDisplay fitSize: " + Arrays.toString(fitSize) + ";progressive=" + req.bProgressive + ", cacheKey: " + req.cacheKey, new Object[0]);
                try {
                    Bitmap bitmap = cacheLoader.getMemCache(req.cacheKey, options.getBusinessId());
                    if ((!ImageUtils.checkBitmap(bitmap) || req.bProgressive) && !req.options.isWithImageDataInCallback()) {
                        bitmap = facade.cutImageKeepRatio(data, fitSize[0], fitSize[1]);
                        if (options.getProcessor() != null) {
                            bitmap = options.getProcessor().process(req.taskModel, bitmap);
                        } else if (isNeedZoom(req) && isNeedZoom(bitmap, options.getWidth(), options.getHeight())) {
                            bitmap = ImageUtils.zoomBitmap(bitmap, fitSize[0], fitSize[1]);
                        }
                        if (ImageUtils.checkBitmap(bitmap)) {
                            cacheLoader.putMemCache(req.cacheKey, bitmap, options.getBusinessId());
                            req.bProgressive = true;
                            putProgressiveVal(req.cacheKey, progress);
                            Logger.D("ImageNetTask", "putMemCache key=" + req.cacheKey + ";bid=" + req.options.getBusinessId() + ";progressive=" + req.bProgressive + ";bitmap=" + bitmap, new Object[0]);
                        }
                    }
                    if (bitmap != null && !req.options.isWithImageDataInCallback()) {
                        Logger.D("ImageNetTask", "doJpegProgressiveDisplay bitmap[w: " + bitmap.getWidth() + ", h: " + bitmap.getHeight() + "], key: " + req.cacheKey, new Object[0]);
                        display(bitmap, req, null, false);
                    }
                } catch (Exception e) {
                    Logger.E((String) "ImageNetTask", (Throwable) e, (String) "doJpegProgressiveDisplay exp", new Object[0]);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeProgressiveVal(BitmapCacheKey key) {
        ProgressiveMgr.getInstance().removeProgressiveVal(key.complexCacheKey());
    }

    /* access modifiers changed from: protected */
    public void putProgressiveVal(BitmapCacheKey key, int value) {
        ProgressiveMgr.getInstance().putProgressiveVal(key.complexCacheKey(), value);
    }

    /* access modifiers changed from: protected */
    public void checkAndAddResIndexUniqueKey() {
        if (this.loadReq.options.getAliasPath() != null) {
            String key = ImageDiskCache.get().queryAliasKey(this.loadReq.options.getAliasPath());
            if (key != null) {
                ImageDiskCache.get().update(key, null);
            }
            ImageDiskCache.get().update(this.loadReq.path, this.loadReq.options.getAliasPath());
        }
    }

    /* access modifiers changed from: protected */
    public boolean isOnlyWithData() {
        Iterator it = new CopyOnWriteArraySet(this.loadReqSet).iterator();
        while (it.hasNext()) {
            if (!((ImageLoadReq) it.next()).options.isWithImageDataInCallback()) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isForceSaveJpg(File saveFile) {
        int type = ImageFileType.detectImageFileType(saveFile);
        return type == 0 || 5 == type;
    }

    /* access modifiers changed from: protected */
    public boolean isForceSaveJpg(byte[] fileData) {
        int type = ImageFileType.detectImageDataType(fileData);
        return type == 0 || 5 == type;
    }
}
