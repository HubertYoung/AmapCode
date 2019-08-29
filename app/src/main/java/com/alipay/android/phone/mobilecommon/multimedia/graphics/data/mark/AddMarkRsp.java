package com.alipay.android.phone.mobilecommon.multimedia.graphics.data.mark;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg;

public class AddMarkRsp {
    private APImageRetMsg retmsg;

    public APImageRetMsg getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(APImageRetMsg retmsg2) {
        this.retmsg = retmsg2;
    }

    public String toString() {
        return "AddMarkRsp{retmsg=" + this.retmsg + '}';
    }
}
