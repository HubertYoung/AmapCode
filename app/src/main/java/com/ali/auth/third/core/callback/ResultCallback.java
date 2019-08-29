package com.ali.auth.third.core.callback;

public interface ResultCallback<T> extends FailureCallback {
    void onSuccess(T t);
}
