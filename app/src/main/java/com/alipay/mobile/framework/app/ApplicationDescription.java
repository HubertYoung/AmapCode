package com.alipay.mobile.framework.app;

import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class ApplicationDescription extends MicroDescription<ApplicationDescription> {
    protected String mAppId;
    protected String mEngineType;

    public ApplicationDescription() {
        super(1);
    }

    public String getAppId() {
        return this.mAppId;
    }

    public ApplicationDescription setAppId(String appId) {
        this.mAppId = appId;
        return this;
    }

    public String getEngineType() {
        return this.mEngineType;
    }

    public ApplicationDescription setEngineType(String engineType) {
        this.mEngineType = engineType;
        return this;
    }

    public String toString() {
        return "ApplicationDescription [mAppId=" + this.mAppId + ", mEngineType=" + this.mEngineType + ", mName=" + this.mName + ", mClassName=" + this.mClassName + ", mClassLoader=" + this.mClassLoader + "]";
    }

    public ApplicationDescription serialize(BufferedOutputStream bos) {
        super.serialize(bos);
        ByteOrderDataUtil.writeString(bos, this.mAppId);
        ByteOrderDataUtil.writeString(bos, this.mEngineType);
        return this;
    }

    public ApplicationDescription deserialize(BufferedInputStream bis) {
        super.deserialize(bis);
        this.mAppId = ByteOrderDataUtil.readString(bis);
        this.mEngineType = ByteOrderDataUtil.readString(bis);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof ApplicationDescription)) {
            return false;
        }
        boolean appIdEqual = false;
        boolean engineTypeEqual = false;
        if (this.mAppId == null && ((ApplicationDescription) o).mAppId == null) {
            appIdEqual = true;
        } else if (this.mAppId != null && this.mAppId.equals(((ApplicationDescription) o).mAppId)) {
            appIdEqual = true;
        }
        if (this.mEngineType == null && ((ApplicationDescription) o).mEngineType == null) {
            engineTypeEqual = true;
        } else if (this.mEngineType != null && this.mEngineType.equals(((ApplicationDescription) o).mEngineType)) {
            engineTypeEqual = true;
        }
        if (!appIdEqual || !engineTypeEqual) {
            return false;
        }
        return true;
    }
}
