package com.alipay.mobile.framework.pipeline;

import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class ValveDescription extends MicroDescription<ValveDescription> {
    protected String mPipelineName;
    protected String mThreadName;
    protected int mWeight;

    public ValveDescription() {
        super(1);
    }

    public int getWeight() {
        return this.mWeight;
    }

    public ValveDescription setWeight(int weight) {
        this.mWeight = weight;
        return this;
    }

    public String getThreadName() {
        return this.mThreadName;
    }

    public ValveDescription setThreadName(String threadName) {
        this.mThreadName = threadName;
        return this;
    }

    public String getPipelineName() {
        return this.mPipelineName;
    }

    public ValveDescription setPipelineName(String pipelineName) {
        this.mPipelineName = pipelineName;
        return this;
    }

    public String toString() {
        return "ValveDescription [mPipelineName=" + this.mPipelineName + ", mThreadName=" + this.mThreadName + ", mWeight=" + this.mWeight + ", mName=" + this.mName + ", mClassName=" + this.mClassName + ", mClassLoader=" + this.mClassLoader + "]";
    }

    public String toShortString() {
        return "ValveDescription (mClassName=" + this.mClassName + ", mThreadName=" + this.mThreadName + ", mWeight=" + this.mWeight + ")";
    }

    public ValveDescription serialize(BufferedOutputStream bos) {
        super.serialize(bos);
        ByteOrderDataUtil.writeString(bos, this.mPipelineName);
        ByteOrderDataUtil.writeString(bos, this.mThreadName);
        ByteOrderDataUtil.writeInt(bos, this.mWeight);
        return this;
    }

    public ValveDescription deserialize(BufferedInputStream bis) {
        super.deserialize(bis);
        this.mPipelineName = ByteOrderDataUtil.readString(bis);
        this.mThreadName = ByteOrderDataUtil.readString(bis);
        this.mWeight = ByteOrderDataUtil.readInt(bis);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof ValveDescription)) {
            return false;
        }
        boolean pipelineNameEqual = false;
        boolean threadNameEqual = false;
        boolean weightEqual = false;
        if (this.mPipelineName == null && ((ValveDescription) o).mPipelineName == null) {
            pipelineNameEqual = true;
        } else if (this.mPipelineName != null && this.mPipelineName.equals(((ValveDescription) o).mPipelineName)) {
            pipelineNameEqual = true;
        }
        if (this.mThreadName == null && ((ValveDescription) o).mThreadName == null) {
            threadNameEqual = true;
        } else if (this.mThreadName != null && this.mThreadName.equals(((ValveDescription) o).mThreadName)) {
            threadNameEqual = true;
        }
        if (this.mWeight == ((ValveDescription) o).mWeight) {
            weightEqual = true;
        }
        if (!pipelineNameEqual || !threadNameEqual || !weightEqual) {
            return false;
        }
        return true;
    }
}
