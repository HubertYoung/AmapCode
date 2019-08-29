package com.alipay.bis.common.service.facade.gw.zim;

public class ZimValidateJsonGwRequest {
    public String zimData;
    public String zimId;

    public String toString() {
        String str;
        StringBuilder append = new StringBuilder("ZimValidateJsonGwRequest{zimId='").append(this.zimId).append("', data='");
        if (this.zimData == null) {
            str = "null";
        } else {
            str = "[length=" + this.zimData.length() + "]";
        }
        return append.append(str).append("'}").toString();
    }
}
