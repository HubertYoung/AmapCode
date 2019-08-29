package com.alipay.bis.common.service.facade.gw.model.common.BisJson;

public class BisClientConfig {
    private String content;
    private String sign = "";

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String str) {
        this.sign = str;
    }
}
