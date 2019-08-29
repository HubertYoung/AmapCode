package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.DecryptException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.File;

public class ImageAliasPathLoadTask extends ImageLoadTask {
    private static final Logger a = Logger.getLogger((String) "ImageAliasPathLoadTask");

    public ImageAliasPathLoadTask(ImageLoadReq loadReq) {
        super(loadReq);
    }

    public Object call() {
        if (!this.loadReq.aliasPathUpdate) {
            return super.call();
        }
        String cacheFile = ImageDiskCache.get().getPath(this.loadReq.cacheKey.complexCacheKey());
        if (!FileUtils.checkFile(cacheFile)) {
            ImageTaskEngine.get().submit((ImageLocalTask) new ImageAliasPathLocalTask(this.loadReq, this.viewWrapper));
            return null;
        } else if (this.loadReq.isEncryptRequest()) {
            try {
                Bitmap bitmap = ImageUtils.decodeBitmapByFalcon(AESUtils.decryptFile(this.loadReq.options.fileKey, new File(cacheFile)));
                getCacheLoader().putMemCache(this.loadReq.cacheKey, bitmap, this.loadReq.options.getBusinessId());
                display(bitmap, this.loadReq, this.viewWrapper);
                return null;
            } catch (DecryptException e) {
                notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
                return null;
            }
        } else {
            Bitmap bitmap2 = ImageUtils.decodeBitmapByFalcon(new File(cacheFile));
            getCacheLoader().putMemCache(this.loadReq.cacheKey, bitmap2, this.loadReq.options.getBusinessId());
            display(bitmap2, this.loadReq, this.viewWrapper);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public boolean displayBitmap(long start, Bitmap bitmap) {
        boolean z = false;
        boolean interrupt = super.displayBitmap(start, bitmap);
        a.d("displayBitmap, interrupt: " + interrupt, new Object[0]);
        if (interrupt) {
            String uniKey = this.loadReq.options.getAliasPath();
            String refKey = ImageDiskCache.get().queryAliasKey(uniKey);
            if (refKey == null) {
                ImageDiskCache.get().update(this.loadReq.path, uniKey);
            } else {
                interrupt = this.loadReq.path.equals(refKey);
            }
            ImageLoadReq imageLoadReq = this.loadReq;
            if (!interrupt) {
                z = true;
            }
            imageLoadReq.aliasPathUpdate = z;
        }
        return interrupt;
    }

    /* access modifiers changed from: protected */
    public void displayDefaultDrawable() {
        if (!this.loadReq.aliasPathUpdate) {
            super.displayDefaultDrawable();
        }
    }

    /* access modifiers changed from: protected */
    public void checkAndStartLocalTask(Bitmap bitmap) {
        if (!FileUtils.checkFile(ImageDiskCache.get().getPath(this.loadReq.cacheKey.complexCacheKey()))) {
            startLocalTask();
        }
    }
}
