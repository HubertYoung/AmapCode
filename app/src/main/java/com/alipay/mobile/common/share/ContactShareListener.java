package com.alipay.mobile.common.share;

public interface ContactShareListener {
    void shareMessage(ShareContent shareContent);

    void shareSMS(ShareContent shareContent);
}
