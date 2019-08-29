package com.taobao.agoo;

public abstract class IRegister extends ICallback {
    public abstract void onFailure(String str, String str2);

    public void onSuccess() {
    }

    public abstract void onSuccess(String str);
}
