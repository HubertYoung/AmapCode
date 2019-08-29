package com.alipay.mobile.common.cleancache.impl;

import android.os.Bundle;
import android.os.Environment;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.common.cleancache.CacheCleanerService;
import com.alipay.mobile.common.cleancache.CacheCleanerService.CleanExecutor;
import com.alipay.mobile.common.cleancache.CacheCleanerService.CleanListener;
import com.alipay.mobile.common.cleancache.CacheCleanerUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.BundleContext;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.autonavi.indoor.constant.MessageCode;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CacheCleanerServiceImpl extends CacheCleanerService {
    private static final String CLEAN_EXCUTOR_FILE = "cacheCleanConfig.config";
    private static final String TAG = "CacheCleanerService";
    private final int BUFFER_SIZE = MessageCode.MSG_ONLINE_BUILDING_CHANGED;
    /* access modifiers changed from: private */
    public BundleContext mBundleContext;

    public void clean(final CleanListener cleanListener) {
        new Thread(new Runnable() {
            public void run() {
                if (CacheCleanerServiceImpl.this.mBundleContext == null) {
                    LoggerFactory.getTraceLogger().debug(CacheCleanerServiceImpl.TAG, "mBundleContext == null");
                    return;
                }
                try {
                    CacheCleanerServiceImpl.this.excuteTask(CacheCleanerServiceImpl.this.getExecutors(CacheCleanerServiceImpl.this.parseCacheCleanConfig()), cleanListener);
                } catch (Exception e) {
                    LoggerFactory.getTraceLogger().debug(CacheCleanerServiceImpl.TAG, "clean Exception : " + e.getMessage());
                    if (cleanListener != null) {
                        cleanListener.onClean(1.0f, 0);
                    }
                }
            }
        }).start();
    }

    public long syncClean(CleanListener cleanListener) {
        long j = 0;
        if (this.mBundleContext == null) {
            LoggerFactory.getTraceLogger().debug(TAG, "mBundleContext == null");
            return j;
        }
        try {
            return excuteTask(getExecutors(parseCacheCleanConfig()), cleanListener);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error((String) TAG, (Throwable) e);
            if (cleanListener == null) {
                return j;
            }
            cleanListener.onClean(1.0f, j);
            return j;
        }
    }

    /* access modifiers changed from: private */
    public List<CleanExecutor> getExecutors(CacheClean cacheClean) {
        List cleanInfos = cacheClean.cleanInfos;
        List cleanExecutors = new ArrayList();
        for (CleanInfo cleanInfo : cleanInfos) {
            try {
                cleanExecutors.add((CleanExecutor) this.mBundleContext.findClassLoaderByBundleName(cleanInfo.bundleName).loadClass(cleanInfo.className).newInstance());
                LoggerFactory.getTraceLogger().debug(TAG, "doClean new class success : [cleanInfo.bundleName = " + cleanInfo.bundleName + ",cleanInfo.className" + cleanInfo.className + "]");
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().error((String) TAG, (Throwable) e);
            }
        }
        return cleanExecutors;
    }

    /* access modifiers changed from: private */
    public long excuteTask(List<CleanExecutor> cleanExecutors, CleanListener cleanListener) {
        if (cleanExecutors == null || cleanExecutors.size() == 0) {
            LoggerFactory.getTraceLogger().debug(TAG, "cleanExecutors==null||cleanExecutors.size()==0");
            return 0;
        }
        long beforCleanSize = getAllMemory();
        int size = cleanExecutors.size();
        CleanExecutor tmpCleanExecutor = null;
        long cleanSize = 0;
        for (int i = 0; i < size; i++) {
            try {
                tmpCleanExecutor = cleanExecutors.get(i);
                long cleanTemp = tmpCleanExecutor.executeClean();
                if (cleanTemp > 0) {
                    cleanSize += cleanTemp;
                }
                LoggerFactory.getTraceLogger().debug(TAG, "cleanExecutor.executeClean  [cleanExecutor = " + tmpCleanExecutor + ",   clean size : " + cleanTemp + ",total : " + cleanSize + "]");
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().debug(TAG, "cleanExecutor.executeClean Exception : " + e.getMessage() + "   -> crash executor is : " + tmpCleanExecutor);
            }
            if (cleanListener != null) {
                try {
                    cleanListener.onClean((((float) (i + 1)) * 1.0f) / ((float) size), cleanSize);
                } catch (Exception e2) {
                    LoggerFactory.getTraceLogger().debug(TAG, "cleanListener.onClean Exception : " + e2.getMessage() + "   -> crash cleanListener is : " + cleanListener);
                }
            }
        }
        long resultCleanSize = correct(beforCleanSize - getAllMemory(), cleanSize);
        if (cleanListener == null) {
            return cleanSize;
        }
        cleanListener.onClean(1.0f, resultCleanSize);
        return cleanSize;
    }

    private long correct(long allDiffCount, long cleanSize) {
        if (allDiffCount <= 0) {
            return cleanSize;
        }
        if (cleanSize <= 0) {
            return 0;
        }
        return allDiffCount;
    }

    private long getAllMemory() {
        return getExternalSize() + getInternalSize();
    }

    private long getInternalSize() {
        return CacheCleanerUtil.sizeOfFile(getMicroApplicationContext().getApplicationContext().getFilesDir().getParentFile());
    }

    private long getExternalSize() {
        return CacheCleanerUtil.sizeOfFile(new File(Environment.getExternalStorageDirectory(), BehavorReporter.PROVIDE_BY_ALIPAY)) + CacheCleanerUtil.sizeOfFile(getMicroApplicationContext().getApplicationContext().getExternalCacheDir().getParentFile());
    }

    /* access modifiers changed from: private */
    public CacheClean parseCacheCleanConfig() {
        return (CacheClean) JSON.parseObject(input2String(LauncherApplicationAgent.getInstance().getBaseContext().getAssets().open(CLEAN_EXCUTOR_FILE), "UTF-8"), CacheClean.class);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.mBundleContext = LauncherApplicationAgent.getInstance().getBundleContext();
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    public String input2String(InputStream in, String encoding) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[MessageCode.MSG_ONLINE_BUILDING_CHANGED];
        while (true) {
            int count = in.read(data, 0, MessageCode.MSG_ONLINE_BUILDING_CHANGED);
            if (count == -1) {
                return new String(outStream.toByteArray(), encoding);
            }
            outStream.write(data, 0, count);
        }
    }
}
