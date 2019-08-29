package com.autonavi.minimap.offline.koala;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.minimap.offline.koala.impl.KoalaDownloader;
import com.autonavi.minimap.offline.koala.impl.KoalaNotifyPolicyFactory;
import com.autonavi.minimap.offline.koala.impl.KoalaPersistence;
import com.autonavi.minimap.offline.koala.intf.IKoalaCustomAction;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadLocalPathBuilder;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloader;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloaderFactory;
import com.autonavi.minimap.offline.koala.intf.IKoalaNotifyPredicateFactory;
import com.autonavi.minimap.offline.koala.intf.IKoalaPersistence;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class KoalaConfig implements Cloneable {
    /* access modifiers changed from: private */
    public static final int CPU_NUM = Runtime.getRuntime().availableProcessors();
    /* access modifiers changed from: private */
    public boolean mAllowSameUrlOnTask;
    /* access modifiers changed from: private */
    public int mBufferSize;
    /* access modifiers changed from: private */
    public int mConnectTimeoutMillis;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public List<IKoalaCustomAction> mCustomActionList;
    /* access modifiers changed from: private */
    public Executor mDownloadExecutor;
    /* access modifiers changed from: private */
    public String mDownloadLocalRootPath;
    /* access modifiers changed from: private */
    public IKoalaDownloaderFactory mDownloaderFactory;
    /* access modifiers changed from: private */
    public IKoalaDownloadLocalPathBuilder mLocalPathBuilder;
    /* access modifiers changed from: private */
    public int mMaxAutoRetryTimes;
    /* access modifiers changed from: private */
    public long mMinAvailableSpace;
    /* access modifiers changed from: private */
    public IKoalaNotifyPredicateFactory mNotifyPredicateFactory;
    /* access modifiers changed from: private */
    public IKoalaPersistence mPersistence;
    /* access modifiers changed from: private */
    public int mReadTimeoutMillis;

    public static class Builder {
        private KoalaConfig mConfig;

        public Builder(Context context) {
            this.mConfig = new KoalaConfig();
            this.mConfig.mContext = context.getApplicationContext();
        }

        public Builder(KoalaConfig koalaConfig) {
            this.mConfig = koalaConfig.clone();
        }

        public Builder setMaxAutoRetryTimes(int i) {
            if (i < 0 || i > 10) {
                return this;
            }
            this.mConfig.mMaxAutoRetryTimes = i;
            return this;
        }

        public Builder setBufferSize(int i) {
            if (i < 0) {
                return this;
            }
            this.mConfig.mBufferSize = i;
            return this;
        }

        public Builder setConnectTimeoutMillis(int i) {
            if (i < 0) {
                return this;
            }
            this.mConfig.mConnectTimeoutMillis = i;
            return this;
        }

        public Builder setReadTimeoutMillis(int i) {
            if (i < 0) {
                return this;
            }
            this.mConfig.mReadTimeoutMillis = i;
            return this;
        }

        public Builder setMinAvailableSpace(long j) {
            if (j < 0) {
                return this;
            }
            this.mConfig.mMinAvailableSpace = j;
            return this;
        }

        public Builder setDownloadLocalRootPath(String str) {
            this.mConfig.mDownloadLocalRootPath = str;
            return this;
        }

        public Builder setDownloadExecutor(Executor executor) {
            this.mConfig.mDownloadExecutor = executor;
            return this;
        }

        public Builder setPersistence(IKoalaPersistence iKoalaPersistence) {
            this.mConfig.mPersistence = iKoalaPersistence;
            return this;
        }

        public Builder setDownloaderFactory(IKoalaDownloaderFactory iKoalaDownloaderFactory) {
            this.mConfig.mDownloaderFactory = iKoalaDownloaderFactory;
            return this;
        }

        public Builder setLocalPathBuilder(IKoalaDownloadLocalPathBuilder iKoalaDownloadLocalPathBuilder) {
            this.mConfig.mLocalPathBuilder = iKoalaDownloadLocalPathBuilder;
            return this;
        }

        public Builder setNotifyInterval(long j) {
            this.mConfig.mNotifyPredicateFactory = KoalaNotifyPolicyFactory.createIntervalPolicy(j);
            return this;
        }

        public Builder setNotifyPercentDelta(float f) {
            this.mConfig.mNotifyPredicateFactory = KoalaNotifyPolicyFactory.createPercentPolicy(f);
            return this;
        }

        public Builder setNotifyPercentDelta(IKoalaNotifyPredicateFactory iKoalaNotifyPredicateFactory) {
            this.mConfig.mNotifyPredicateFactory = iKoalaNotifyPredicateFactory;
            return this;
        }

        public Builder setCustomActionList(List<IKoalaCustomAction> list) {
            if (list == null) {
                return this;
            }
            this.mConfig.mCustomActionList = new ArrayList(list);
            return this;
        }

        public Builder addCustomAction(IKoalaCustomAction iKoalaCustomAction) {
            if (iKoalaCustomAction == null) {
                return this;
            }
            this.mConfig.mCustomActionList.add(iKoalaCustomAction);
            return this;
        }

        public Builder setAllowSameUrlOnTask(boolean z) {
            this.mConfig.mAllowSameUrlOnTask = z;
            return this;
        }

        private String getDefaultDownloadLocalPath(Context context) {
            String cacheFilePath = KoalaUtils.getCacheFilePath(context);
            StringBuilder sb = new StringBuilder();
            sb.append(cacheFilePath);
            sb.append(File.separator);
            sb.append("Koala");
            sb.append(File.separator);
            sb.append(LogConstant.SPLASH_SCREEN_DOWNLOADED);
            sb.append(File.separator);
            return sb.toString();
        }

        private IKoalaDownloaderFactory getDefaultDownloadFactory() {
            return new IKoalaDownloaderFactory() {
                public final IKoalaDownloader create() {
                    return new KoalaDownloader();
                }
            };
        }

        private IKoalaDownloadLocalPathBuilder getDefaultLocalPathBuilder() {
            return new IKoalaDownloadLocalPathBuilder() {
                public final String build(String str, String str2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(File.separator);
                    sb.append(UUID.randomUUID().toString());
                    sb.append(KoalaUtils.getFileExt(str));
                    return sb.toString();
                }
            };
        }

        public KoalaConfig build() {
            if (TextUtils.isEmpty(this.mConfig.mDownloadLocalRootPath)) {
                this.mConfig.mDownloadLocalRootPath = getDefaultDownloadLocalPath(this.mConfig.mContext);
            }
            if (this.mConfig.mDownloadExecutor == null) {
                this.mConfig.mDownloadExecutor = Executors.newFixedThreadPool(KoalaConfig.CPU_NUM);
            }
            if (this.mConfig.mDownloaderFactory == null) {
                this.mConfig.mDownloaderFactory = getDefaultDownloadFactory();
            }
            if (this.mConfig.mPersistence == null) {
                this.mConfig.mPersistence = new KoalaPersistence(this.mConfig.getContext());
            }
            if (this.mConfig.mLocalPathBuilder == null) {
                this.mConfig.mLocalPathBuilder = getDefaultLocalPathBuilder();
            }
            if (this.mConfig.mNotifyPredicateFactory == null) {
                this.mConfig.mNotifyPredicateFactory = KoalaNotifyPolicyFactory.createPercentPolicy(0.01f);
            }
            return this.mConfig;
        }
    }

    private KoalaConfig() {
        this.mMaxAutoRetryTimes = 3;
        this.mConnectTimeoutMillis = 3000;
        this.mReadTimeoutMillis = 5000;
        this.mBufferSize = 4096;
        this.mMinAvailableSpace = 0;
        this.mAllowSameUrlOnTask = true;
        this.mCustomActionList = new ArrayList();
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getMaxAutoRetryTimes() {
        return this.mMaxAutoRetryTimes;
    }

    public int getBufferSize() {
        return this.mBufferSize;
    }

    public int getConnectTimeoutMillis() {
        return this.mConnectTimeoutMillis;
    }

    public int getReadTimeoutMillis() {
        return this.mReadTimeoutMillis;
    }

    public long getMinAvailableSpace() {
        return this.mMinAvailableSpace;
    }

    public String getDownloadLocalRootPath() {
        return this.mDownloadLocalRootPath;
    }

    public Executor getDownloadExecutor() {
        return this.mDownloadExecutor;
    }

    public IKoalaPersistence getPersistence() {
        return this.mPersistence;
    }

    public IKoalaDownloaderFactory getDownloaderFactory() {
        return this.mDownloaderFactory;
    }

    public IKoalaDownloadLocalPathBuilder getLocalPathBuilder() {
        return this.mLocalPathBuilder;
    }

    public IKoalaNotifyPredicateFactory getNotifyPredicateFactory() {
        return this.mNotifyPredicateFactory;
    }

    public boolean isAllowSameUrlOnTask() {
        return this.mAllowSameUrlOnTask;
    }

    public List<IKoalaCustomAction> getCustomActionList() {
        return this.mCustomActionList;
    }

    public KoalaConfig clone() {
        try {
            return (KoalaConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new KoalaConfig();
        }
    }
}
