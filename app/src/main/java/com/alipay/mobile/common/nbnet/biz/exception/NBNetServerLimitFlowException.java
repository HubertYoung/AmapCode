package com.alipay.mobile.common.nbnet.biz.exception;

public class NBNetServerLimitFlowException extends NBNetServerException {
    private int sleep = 0;

    public NBNetServerLimitFlowException(int sleep2, String detailMessage) {
        super(detailMessage, 429);
        this.sleep = sleep2;
    }

    public NBNetServerLimitFlowException(String detailMessage) {
        super(detailMessage, 429);
    }

    public int getSleep() {
        return this.sleep;
    }
}
