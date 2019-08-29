package com.autonavi.minimap.offline.koala.model;

import android.support.annotation.Keep;
import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile.Detail;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.ArrayList;

@Keep
public class KoalaDownloadEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = 8224612022005658877L;
    private String mErrorCause;
    private int mId;
    private KoalaDownloadModel mModel;
    private KoalaDownloadSpecialData[] mSpecialDatas;
    private KoalaDownloadStatus mStatus;
    private long mTime;

    public KoalaDownloadEntity(KoalaDownloadModel koalaDownloadModel) {
        this.mModel = koalaDownloadModel;
    }

    public KoalaDownloadModel getModel() {
        return this.mModel;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public KoalaDownloadStatus getStatus() {
        return this.mStatus;
    }

    public void setStatus(KoalaDownloadStatus koalaDownloadStatus) {
        this.mStatus = koalaDownloadStatus;
    }

    public void setErrorCause(String str) {
        this.mErrorCause = str;
    }

    public String getErrorCause() {
        return this.mErrorCause;
    }

    public long getTime() {
        return this.mTime;
    }

    public void setTime(long j) {
        this.mTime = j;
    }

    @SuppressFBWarnings({"EI_EXPOSE_REP"})
    public KoalaDownloadSpecialData[] getSpecialDatas() {
        return this.mSpecialDatas;
    }

    @SuppressFBWarnings({"EI_EXPOSE_REP2"})
    public void setSpecialDatas(KoalaDownloadSpecialData[] koalaDownloadSpecialDataArr) {
        this.mSpecialDatas = koalaDownloadSpecialDataArr;
    }

    public KoalaDownloadEntity clone(KoalaDownloadModel koalaDownloadModel) {
        checkValid();
        try {
            KoalaDownloadEntity koalaDownloadEntity = (KoalaDownloadEntity) super.clone();
            koalaDownloadEntity.mModel = koalaDownloadModel;
            koalaDownloadEntity.mSpecialDatas = cloneSpecialDatas(koalaDownloadEntity);
            return koalaDownloadEntity;
        } catch (CloneNotSupportedException unused) {
            return new KoalaDownloadEntity(koalaDownloadModel);
        }
    }

    private KoalaDownloadSpecialData[] cloneSpecialDatas(KoalaDownloadEntity koalaDownloadEntity) {
        checkValid();
        KoalaDownloadSpecialData[] koalaDownloadSpecialDataArr = new KoalaDownloadSpecialData[this.mSpecialDatas.length];
        for (int i = 0; i < this.mSpecialDatas.length; i++) {
            koalaDownloadSpecialDataArr[i] = this.mSpecialDatas[i].clone(koalaDownloadEntity);
        }
        return koalaDownloadSpecialDataArr;
    }

    public long getTotalBytes() {
        long j = 0;
        for (KoalaDownloadSpecialData totalBytes : this.mSpecialDatas) {
            j += totalBytes.getTotalBytes();
        }
        return j;
    }

    public long getDownloadBytes() {
        long j = 0;
        for (KoalaDownloadSpecialData downloadBytes : this.mSpecialDatas) {
            j += downloadBytes.getDownloadBytes();
        }
        return j;
    }

    public KoalaDownloadSpecialData findSpecialData(String str) {
        KoalaDownloadSpecialData[] koalaDownloadSpecialDataArr;
        for (KoalaDownloadSpecialData koalaDownloadSpecialData : this.mSpecialDatas) {
            if (koalaDownloadSpecialData.getUrl().equals(str)) {
                return koalaDownloadSpecialData;
            }
        }
        return null;
    }

    public boolean isDownloadComplete() {
        for (KoalaDownloadSpecialData isDownloadComplete : this.mSpecialDatas) {
            if (!isDownloadComplete.isDownloadComplete()) {
                return false;
            }
        }
        return true;
    }

    public KoalaDownloadSpecialData getDownloadingSpecialData() {
        KoalaDownloadSpecialData[] koalaDownloadSpecialDataArr;
        for (KoalaDownloadSpecialData koalaDownloadSpecialData : this.mSpecialDatas) {
            if (!koalaDownloadSpecialData.isDownloadComplete()) {
                return koalaDownloadSpecialData;
            }
        }
        return null;
    }

    public void checkValid() {
        KoalaDownloadSpecialData[] koalaDownloadSpecialDataArr;
        if (this.mId <= 0) {
            throw new IllegalArgumentException("check data: download id is less than 0 or equal 0!");
        } else if (this.mSpecialDatas == null || this.mSpecialDatas.length == 0) {
            throw new IllegalArgumentException("check data: download special data is null!");
        } else {
            ArrayList arrayList = new ArrayList();
            for (KoalaDownloadSpecialData koalaDownloadSpecialData : this.mSpecialDatas) {
                if (arrayList.contains(koalaDownloadSpecialData.getUrl())) {
                    throw new IllegalArgumentException("check data: download url is duplication in a download task!");
                }
                arrayList.add(koalaDownloadSpecialData.getUrl());
                koalaDownloadSpecialData.checkValid();
            }
        }
    }

    public KoalaDownloadProfile snapshot() {
        Detail[] detailArr = new Detail[this.mSpecialDatas.length];
        KoalaDownloadSpecialData[] koalaDownloadSpecialDataArr = this.mSpecialDatas;
        int length = koalaDownloadSpecialDataArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            KoalaDownloadSpecialData koalaDownloadSpecialData = koalaDownloadSpecialDataArr[i];
            Detail detail = new Detail(koalaDownloadSpecialData.getUrl(), koalaDownloadSpecialData.getLocalPath(), koalaDownloadSpecialData.getTotalBytes(), koalaDownloadSpecialData.getDownloadBytes());
            detailArr[i2] = detail;
            i++;
            i2++;
        }
        KoalaDownloadProfile koalaDownloadProfile = new KoalaDownloadProfile(getId(), getStatus(), getTime(), detailArr);
        return koalaDownloadProfile;
    }

    public void save() {
        if (this.mModel != null) {
            this.mModel.save();
        }
    }
}
