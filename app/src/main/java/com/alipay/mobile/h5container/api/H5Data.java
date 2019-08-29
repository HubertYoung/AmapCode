package com.alipay.mobile.h5container.api;

public interface H5Data {
    String get(String str);

    boolean has(String str);

    String remove(String str);

    void set(String str, String str2);
}
