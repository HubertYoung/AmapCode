package com.alipay.mobile.security.bio.service;

import com.alipay.bis.common.service.facade.gw.zim.ZimValidateGwResponse;
import com.alipay.mobile.security.bio.utils.StringUtil;
import java.util.Map;

public class BioUploadResult {
    public Map<String, String> extParams;
    public boolean hasNext = false;
    public String nextProtocol;
    public int productRetCode = 3001;
    public String subCode = "";
    public String subMsg = "";
    public int validationRetCode = 1001;

    public BioUploadResult() {
    }

    public BioUploadResult(ZimValidateGwResponse zimValidateGwResponse) {
        this.productRetCode = zimValidateGwResponse.productRetCode;
        this.validationRetCode = zimValidateGwResponse.validationRetCode;
        this.hasNext = zimValidateGwResponse.hasNext;
        this.nextProtocol = zimValidateGwResponse.nextProtocol;
        this.extParams = zimValidateGwResponse.extParams;
        this.subCode = zimValidateGwResponse.retCodeSub;
        this.subMsg = zimValidateGwResponse.retMessageSub;
    }

    public String toString() {
        String collection2String;
        StringBuilder append = new StringBuilder("BioUploadResult{productRetCode=").append(this.productRetCode).append(", validationRetCode=").append(this.validationRetCode).append(", hasNext=").append(this.hasNext).append(", subCode=").append(this.subCode).append(", subMsg=").append(this.subMsg).append(", nextProtocol='").append(this.nextProtocol).append('\'').append(", extParams=");
        if (this.extParams == null) {
            collection2String = "null";
        } else {
            collection2String = StringUtil.collection2String(this.extParams.keySet());
        }
        return append.append(collection2String).append('}').toString();
    }
}
