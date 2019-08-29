package com.alipay.multimedia.adapter.alipay;

import com.alipay.multimedia.adapter.AdapterFactory;

public class AlipayAdapterFactory extends AdapterFactory {
    /* access modifiers changed from: protected */
    public void initAdapter() {
        setExecutor(new AlipayExecutor());
        setL(new AlipayLog());
    }
}
