package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config;

public class PreReleaseServerAddress extends ServerAddress {
    private boolean a = true;

    public PreReleaseServerAddress() {
        if (this.a) {
            this.upServerHost = "up-mayi.django.t.taobao.com";
            this.dlServerHost = "oalipay-dl-django.alicdn.com";
            this.apiServerHost = "api-mayi.django.t.taobao.com";
            return;
        }
        this.upServerHost = "up-prepub.django.t.taobao.com";
        this.dlServerHost = "dl-prepub.django.t.taobao.com";
        this.apiServerHost = "api-prepub.django.t.taobao.com";
    }
}
