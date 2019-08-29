package com.alipay.mobile.framework.msg;

import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.quinox.utils.bytedata.ByteOrderDataUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.Arrays;

public class BroadcastReceiverDescription extends MicroDescription<BroadcastReceiverDescription> {
    protected boolean mHasRegisted = false;
    protected String[] mMsgCode;
    protected boolean mSubThread;

    public BroadcastReceiverDescription() {
        super(1);
    }

    public String[] getMsgCode() {
        return this.mMsgCode;
    }

    public BroadcastReceiverDescription setMsgCode(String[] msgCode) {
        this.mMsgCode = msgCode;
        return this;
    }

    public boolean isSubThread() {
        return this.mSubThread;
    }

    public void setSubThread(boolean subThread) {
        this.mSubThread = subThread;
    }

    public boolean hasRegisted() {
        return this.mHasRegisted;
    }

    public void setHasRegisted(boolean mHasRegisted2) {
        this.mHasRegisted = mHasRegisted2;
    }

    public String toString() {
        return "BroadcastReceiverDescription [mMsgCode=" + Arrays.toString(this.mMsgCode) + ", mSubThread=" + this.mSubThread + ", mHasRegisted=" + this.mHasRegisted + ", mName=" + this.mName + ", mClassName=" + this.mClassName + ", mClassLoader=" + this.mClassLoader + "]";
    }

    public BroadcastReceiverDescription serialize(BufferedOutputStream bos) {
        super.serialize(bos);
        ByteOrderDataUtil.writeStringArray(bos, this.mMsgCode);
        return this;
    }

    public BroadcastReceiverDescription deserialize(BufferedInputStream bis) {
        super.deserialize(bis);
        this.mMsgCode = ByteOrderDataUtil.readStringArray(bis);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o) || !(o instanceof BroadcastReceiverDescription)) {
            return false;
        }
        if (this.mMsgCode == null && ((BroadcastReceiverDescription) o).mMsgCode == null) {
            return true;
        }
        if (this.mMsgCode == null || ((BroadcastReceiverDescription) o).mMsgCode == null || !Arrays.equals(this.mMsgCode, ((BroadcastReceiverDescription) o).mMsgCode)) {
            return false;
        }
        return true;
    }
}
