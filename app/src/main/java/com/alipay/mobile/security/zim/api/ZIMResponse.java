package com.alipay.mobile.security.zim.api;

import com.alipay.mobile.security.bio.utils.StringUtil;
import java.util.HashMap;
import java.util.Map;

public class ZIMResponse {
    public String bizData;
    public int code;
    public Map<String, String> extInfo = new HashMap();
    public String msg;
    public String reason;
    public String subCode;

    public String toString() {
        String collection2String;
        StringBuilder append = new StringBuilder("ZIMResponse{code=").append(this.code).append(", subCode=").append(this.subCode).append(", msg=").append(this.msg).append(", reason='").append(this.reason).append('\'').append(", bizData='").append(this.bizData).append('\'').append(", extInfo.keySet()=");
        if (this.extInfo == null) {
            collection2String = "null";
        } else {
            collection2String = StringUtil.collection2String(this.extInfo.keySet());
        }
        return append.append(collection2String).append('}').toString();
    }
}
