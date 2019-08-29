package com.alipay.mobile.common.nbnet.biz.exception;

import com.alipay.mobile.common.nbnet.api.NBNetException;

public class NBNetCancelException extends NBNetException {
    public NBNetCancelException(String detailMessage) {
        super(detailMessage, -8);
    }
}
