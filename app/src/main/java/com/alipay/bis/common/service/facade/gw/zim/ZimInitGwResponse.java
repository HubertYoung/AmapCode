package com.alipay.bis.common.service.facade.gw.zim;

import com.alipay.mobile.security.bio.utils.StringUtil;
import java.util.Map;

public class ZimInitGwResponse {
    public Map<String, String> extParams;
    public String message;
    public String protocol;
    public int retCode = 0;
    public String retCodeSub;
    public String retMessageSub;
    public String zimId;

    public String toString() {
        String collection2String;
        StringBuilder append = new StringBuilder("ZimInitGwResponse{retCode=").append(this.retCode).append(", message='").append(this.message).append('\'').append(", zimId='").append(this.zimId).append('\'').append(", protocol='").append(this.protocol).append('\'').append(", extParams=");
        if (this.extParams == null) {
            collection2String = "null";
        } else {
            collection2String = StringUtil.collection2String(this.extParams.keySet());
        }
        return append.append(collection2String).append(", retCodeSub='").append(this.retCodeSub).append('\'').append(", retMessageSub='").append(this.retMessageSub).append('\'').append('}').toString();
    }
}
