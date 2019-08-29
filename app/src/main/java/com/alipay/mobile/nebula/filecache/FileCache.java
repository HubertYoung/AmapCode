package com.alipay.mobile.nebula.filecache;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import java.io.File;
import java.util.Stack;

public class FileCache {
    public static final long EXPIRE_TIME = 604800000;
    public static final String TAG = "FileCache";
    private String subPath;

    public FileCache(Context context) {
        clearExpired(context);
    }

    public FileCache(final Context context, String appId) {
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            this.subPath = appId + "/" + H5SecurityUtil.getMD5(h5LoginProvider.getUserId());
        } else {
            this.subPath = appId;
        }
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                FileCache.this.clearExpired(context);
            }
        });
    }

    public String getCachePath(Context context, String url) {
        String sub;
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String mapTo = H5SecurityUtil.getSHA1(url) + "." + H5FileUtil.getExtension(url);
        if (TextUtils.isEmpty(this.subPath)) {
            sub = "/h5/download/cache/";
        } else {
            sub = "/h5/download/cache/" + this.subPath + "/";
        }
        return DiskUtil.getSubDir(context, sub + mapTo);
    }

    public String getCacheDir(Context context) {
        String sub;
        if (TextUtils.isEmpty(this.subPath)) {
            sub = "/h5/download/cache/";
        } else {
            sub = "/h5/download/cache/" + this.subPath + "/";
        }
        return DiskUtil.getSubDir(context, sub);
    }

    public String getTempPath(Context context, String url) {
        String sub;
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String mapTo = H5SecurityUtil.getSHA1(url) + "." + H5FileUtil.getExtension(url);
        if (TextUtils.isEmpty(this.subPath)) {
            sub = "/h5/download/temp/";
        } else {
            sub = "/h5/download/temp/" + this.subPath + "/";
        }
        String filePath = DiskUtil.getSubDir(context, sub + mapTo);
        if (H5FileUtil.exists(filePath)) {
            return filePath;
        }
        H5FileUtil.create(filePath);
        return filePath;
    }

    public String getTempPath(Context context, String url, String extension) {
        String sub;
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String mapTo = H5SecurityUtil.getSHA1(url) + "." + extension;
        if (TextUtils.isEmpty(this.subPath)) {
            sub = "/h5/download/temp/";
        } else {
            sub = "/h5/download/temp/" + this.subPath + "/";
        }
        String filePath = DiskUtil.getSubDir(context, sub + mapTo);
        if (H5FileUtil.exists(filePath)) {
            return filePath;
        }
        H5FileUtil.create(filePath);
        return filePath;
    }

    public void clearExpired(Context context) {
        String tempDir;
        String cacheDir;
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(this.subPath)) {
                    tempDir = DiskUtil.getSubDir(context, "/h5/download/temp/" + this.subPath);
                } else {
                    tempDir = DiskUtil.getSubDir(context, "/h5/download/temp");
                }
                if (H5FileUtil.exists(tempDir)) {
                    clearPath(tempDir);
                }
                if (!TextUtils.isEmpty(this.subPath)) {
                    cacheDir = DiskUtil.getSubDir(context, "/h5/download/cache/" + this.subPath);
                } else {
                    cacheDir = DiskUtil.getSubDir(context, "/h5/download/cache");
                }
                if (H5FileUtil.exists(cacheDir)) {
                    clearPath(cacheDir);
                }
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
    }

    public void clearTempPath(Context context) {
        String cacheDir;
        if (!TextUtils.isEmpty(this.subPath)) {
            cacheDir = DiskUtil.getSubDir(context, "/h5/download/temp/" + this.subPath);
        } else {
            cacheDir = DiskUtil.getSubDir(context, "/h5/download/temp");
        }
        H5FileUtil.delete(new File(cacheDir));
    }

    private void clearPath(String path) {
        if (H5FileUtil.exists(path)) {
            Stack pathStack = new Stack();
            pathStack.push(path);
            long current = System.currentTimeMillis();
            while (!pathStack.isEmpty()) {
                File curFile = new File((String) pathStack.pop());
                if (current - curFile.lastModified() > EXPIRE_TIME) {
                    H5FileUtil.delete(curFile);
                } else if (curFile.isDirectory()) {
                    File[] children = curFile.listFiles();
                    if (!(children == null || children.length == 0)) {
                        for (File child : children) {
                            pathStack.push(child.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    public String getSubPath() {
        return this.subPath;
    }

    public static boolean containCachePath(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        if (path.contains("/h5/download/temp") || path.contains("/h5/download/cache")) {
            return true;
        }
        return false;
    }
}
