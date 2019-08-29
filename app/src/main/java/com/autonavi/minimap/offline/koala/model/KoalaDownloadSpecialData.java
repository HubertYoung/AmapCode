package com.autonavi.minimap.offline.koala.model;

import android.support.annotation.Keep;
import android.text.TextUtils;
import java.io.Serializable;

@Keep
public class KoalaDownloadSpecialData implements Serializable, Cloneable {
    private static final long serialVersionUID = 8621577187597776748L;
    private long mDownloadBytes;
    private KoalaDownloadEntity mDownloadEntity;
    private String mLocalPath;
    private long mTotalBytes = -1;
    private String mUrl;

    public KoalaDownloadSpecialData(KoalaDownloadEntity koalaDownloadEntity) {
        this.mDownloadEntity = koalaDownloadEntity;
    }

    public KoalaDownloadEntity getDownloadEntity() {
        return this.mDownloadEntity;
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

    public void setTotalBytes(long j) {
        this.mTotalBytes = j;
    }

    public void setDownloadBytes(long j) {
        this.mDownloadBytes = j;
    }

    public void setLocalPath(String str) {
        this.mLocalPath = str;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public KoalaDownloadSpecialData clone(KoalaDownloadEntity koalaDownloadEntity) {
        try {
            KoalaDownloadSpecialData koalaDownloadSpecialData = (KoalaDownloadSpecialData) super.clone();
            koalaDownloadSpecialData.mDownloadEntity = koalaDownloadEntity;
            return koalaDownloadSpecialData;
        } catch (CloneNotSupportedException unused) {
            return new KoalaDownloadSpecialData(koalaDownloadEntity);
        }
    }

    public boolean isDownloadComplete() {
        return this.mTotalBytes == this.mDownloadBytes;
    }

    public void checkValid() {
        if (TextUtils.isEmpty(this.mUrl)) {
            throw new IllegalArgumentException("check data: download url is null!");
        } else if (TextUtils.isEmpty(this.mLocalPath)) {
            throw new IllegalArgumentException("check data: local path is null!");
        }
    }

    public void save() {
        if (this.mDownloadEntity != null) {
            this.mDownloadEntity.save();
        }
    }
}
