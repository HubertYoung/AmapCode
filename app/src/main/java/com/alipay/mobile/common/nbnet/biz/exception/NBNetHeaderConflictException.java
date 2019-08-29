package com.alipay.mobile.common.nbnet.biz.exception;

import com.alipay.mobile.common.nbnet.api.NBNetException;

public class NBNetHeaderConflictException extends NBNetException {
    public NBNetHeaderConflictException(String detailMessage) {
        super(detailMessage, -25);
    }
}
