package com.alipay.mobile.common.rpc.protocol;

public abstract class AbstractSerializer implements Serializer {
    protected String mOperationType;
    protected Object mParams;
    protected String scene;

    public AbstractSerializer(String operationType, Object params) {
        this.mOperationType = operationType;
        this.mParams = params;
    }

    public String getScene() {
        return this.scene;
    }

    public void setScene(String scene2) {
        this.scene = scene2;
    }
}
