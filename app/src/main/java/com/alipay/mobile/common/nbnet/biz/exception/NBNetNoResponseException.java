package com.alipay.mobile.common.nbnet.biz.exception;

import com.alipay.mobile.common.nbnet.api.NBNetException;

public class NBNetNoResponseException extends NBNetException {
    public NBNetNoResponseException() {
        super((String) "The target server failed to respond", -17);
    }
}
