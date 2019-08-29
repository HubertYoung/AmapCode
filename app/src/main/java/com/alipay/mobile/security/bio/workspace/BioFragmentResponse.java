package com.alipay.mobile.security.bio.workspace;

import com.alipay.mobile.security.bio.utils.StringUtil;
import java.util.HashMap;
import java.util.Map;

public class BioFragmentResponse {
    public int errorCode = 500;
    public Map<String, String> ext = new HashMap();
    public boolean isSucess;
    public String resultMessage;
    public String subCode;
    public String subMsg;
    public int suggest;
    public String token;

    public String toString() {
        return "BioFragmentResponse{errorCode=" + this.errorCode + ", subcode=" + this.subCode + ", submsg='" + this.subMsg + '\'' + ", suggest=" + this.suggest + ", isSucess=" + this.isSucess + ", token='" + this.token + '\'' + ", resultMessage='" + this.resultMessage + '\'' + ", ext=" + StringUtil.collection2String(this.ext.keySet()) + '}';
    }
}
