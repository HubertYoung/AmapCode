package com.sina.weibo.sdk.network.target;

public abstract class BaseTarget<E> implements Target<E> {
    public void onError() {
    }

    public void onRequestDone() {
    }

    public void onRequestSuccessBg(E e) {
    }

    public void onStart() {
    }

    public void onStartBg() {
    }
}
