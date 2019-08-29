package com.alipay.bis.common.service.facade.gw.zim;

import com.alipay.mobile.security.bio.utils.StringUtil;
import java.util.Map;

public class ZimValidateGwResponse {
    public Map<String, String> extParams;
    public boolean hasNext = false;
    public String nextProtocol;
    public int productRetCode = 0;
    public String retCodeSub;
    public String retMessageSub;
    public int validationRetCode = 0;

    public String toString() {
        String collection2String;
        StringBuilder append = new StringBuilder("ZimValidateGwResponse{validationRetCode=").append(this.validationRetCode).append(", productRetCode=").append(this.productRetCode).append(", hasNext=").append(this.hasNext).append(", nextProtocol='").append(this.nextProtocol).append('\'').append(", extParams=");
        if (this.extParams == null) {
            collection2String = "null";
        } else {
            collection2String = StringUtil.collection2String(this.extParams.keySet());
        }
        return append.append(collection2String).append(", retCodeSub='").append(this.retCodeSub).append('\'').append(", retMessageSub='").append(this.retMessageSub).append('\'').append('}').toString();
    }
}
