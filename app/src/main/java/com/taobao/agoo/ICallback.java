package com.taobao.agoo;

public abstract class ICallback {
    public String extra;

    public abstract void onFailure(String str, String str2);

    public abstract void onSuccess();
}
