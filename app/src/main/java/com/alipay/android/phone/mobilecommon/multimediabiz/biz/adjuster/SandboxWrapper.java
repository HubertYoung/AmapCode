package com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import com.alipay.multimedia.adjuster.api.data.APMSaveReq.Builder;
import com.alipay.multimedia.adjuster.api.data.ICache;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SandboxWrapper {
    public static InputStream openContentResolverInputStream(Uri uri) {
        InputStream inputStream = null;
        if (uri == null) {
            return inputStream;
        }
        try {
            return AppUtils.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            Logger.E("SandboxWrapper", "openContentResolverInputStream exp: " + e.toString(), new Object[0]);
            return inputStream;
        }
    }

    public static ParcelFileDescriptor openParcelFileDescriptor(Uri uri) {
        ParcelFileDescriptor pfd = null;
        if (uri == null) {
            return null;
        }
        try {
            pfd = AppUtils.getContentResolver().openFileDescriptor(uri, UploadQueueMgr.MSGTYPE_REALTIME);
            pfd.getFd();
            return pfd;
        } catch (FileNotFoundException e) {
            Logger.E("SandboxWrapper", "openContentResolverInputStream exp: " + e.toString(), new Object[0]);
            return pfd;
        }
    }

    public static FileDescriptor openFileDescriptor(Uri uri) {
        if (uri != null) {
            try {
                return AppUtils.getContentResolver().openFileDescriptor(uri, UploadQueueMgr.MSGTYPE_REALTIME).getFileDescriptor();
            } catch (FileNotFoundException e) {
                Logger.E("SandboxWrapper", "openContentResolverInputStream exp: " + e.toString(), new Object[0]);
            }
        }
        return null;
    }

    public static int getImageHeaderType(Uri uri) {
        InputStream inputStream = openContentResolverInputStream(uri);
        if (inputStream != null) {
            return ImageFileType.detectImageFileType(inputStream);
        }
        return 6;
    }

    public static boolean checkFileExist(String path) {
        return APMSandboxProcessor.checkFileExist(path);
    }

    public static boolean isContentUriPath(String path) {
        return APMSandboxProcessor.isContentUriPath(path);
    }

    public static void registerICache() {
        APMSandboxProcessor.registerICache(new ICache() {
            public final String getCacheRootDir() {
                return CacheContext.get().getRootDir();
            }
        });
    }

    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        if (!isContentUriPath(path)) {
            return new File(path).length();
        }
        ParcelFileDescriptor pfd = null;
        try {
            pfd = openParcelFileDescriptor(Uri.parse(path));
            if (pfd != null) {
                return pfd.getStatSize();
            }
            IOUtils.closeQuietly(pfd);
            return -1;
        } finally {
            IOUtils.closeQuietly(pfd);
        }
    }

    public static String copyUriFile(String uri, String savePath) {
        return APMSandboxProcessor.copyContentUriFile(new Builder(uri, "mm_biz").savePath(savePath).build());
    }
}
