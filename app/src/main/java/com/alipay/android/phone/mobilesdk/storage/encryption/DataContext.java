package com.alipay.android.phone.mobilesdk.storage.encryption;

public class DataContext {
    int category;
    byte[] extData;
    int index;
    int type;

    public DataContext() {
        this.index = -1;
        this.extData = null;
        this.category = -1;
        this.type = -1;
    }

    public DataContext(int index2, byte[] extData2) {
        this.index = index2;
        this.extData = extData2;
    }

    public DataContext(int index2, byte[] extData2, int category2, int type2) {
        this.index = index2;
        this.extData = extData2;
        this.category = category2;
        this.type = type2;
    }
}
