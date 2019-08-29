package com.alipay.mobile.security.bio.task;

public class ActionFrame<T> {
    private T a;

    public ActionFrame(T t) {
        this.a = t;
    }

    public T getObject() {
        return this.a;
    }
}
