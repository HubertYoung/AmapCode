package com.autonavi.minimap.offline.koala.model;

import android.support.annotation.Keep;
import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Keep
public class KoalaDownloadProfile {
    private Detail[] mDetails;
    private long mDownloadTime;
    private int mId;
    private KoalaDownloadStatus mStatus;

    @Keep
    public static class Detail {
        private long mDownloadBytes = 0;
        private String mLocalPath;
        private long mTotalBytes = -1;
        private String mUrl;

        Detail(String str, String str2, long j, long j2) {
            this.mUrl = str;
            this.mLocalPath = str2;
            this.mTotalBytes = j;
            this.mDownloadBytes = j2;
        }

        public long getTotalBytes() {
            return this.mTotalBytes;
        }

        public long getDownloadBytes() {
            return this.mDownloadBytes;
        }

        public String getLocalPath() {
            return this.mLocalPath;
        }

        public String getUrl() {
            return this.mUrl;
        }

        public boolean isDownloadComplete() {
            return this.mTotalBytes == this.mDownloadBytes;
        }
    }

    KoalaDownloadProfile(int i, KoalaDownloadStatus koalaDownloadStatus, long j, Detail[] detailArr) {
        this.mId = i;
        this.mStatus = koalaDownloadStatus;
        this.mDownloadTime = j;
        if (detailArr == null) {
            this.mDetails = new Detail[0];
        } else {
            this.mDetails = detailArr;
        }
    }

    public static KoalaDownloadProfile empty() {
        KoalaDownloadProfile koalaDownloadProfile = new KoalaDownloadProfile(0, KoalaDownloadStatus.PENDING, 0, new Detail[0]);
        return koalaDownloadProfile;
    }

    public boolean isEmpty() {
        return this.mId <= 0;
    }

    public int getId() {
        return this.mId;
    }

    public KoalaDownloadStatus getStatus() {
        return this.mStatus;
    }

    public long getDownloadTime() {
        return this.mDownloadTime;
    }

    @SuppressFBWarnings({"EI_EXPOSE_REP"})
    public Detail[] getDetails() {
        return this.mDetails;
    }

    public Detail getDetail(String str) {
        Detail[] detailArr;
        for (Detail detail : this.mDetails) {
            if (detail.getUrl().equals(str)) {
                return detail;
            }
        }
        return null;
    }

    public Detail getDownloadingDetail() {
        Detail[] detailArr;
        for (Detail detail : this.mDetails) {
            if (!detail.isDownloadComplete()) {
                return detail;
            }
        }
        return null;
    }

    public long getTotalBytes() {
        long j = 0;
        for (Detail totalBytes : this.mDetails) {
            j += totalBytes.getTotalBytes();
        }
        return j;
    }

    public long getDownloadBytes() {
        long j = 0;
        for (Detail downloadBytes : this.mDetails) {
            j += downloadBytes.getDownloadBytes();
        }
        return j;
    }

    public boolean isDownloadComplete() {
        for (Detail isDownloadComplete : this.mDetails) {
            if (!isDownloadComplete.isDownloadComplete()) {
                return false;
            }
        }
        return true;
    }
}
