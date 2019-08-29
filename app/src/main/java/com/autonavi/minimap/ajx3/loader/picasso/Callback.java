package com.autonavi.minimap.ajx3.loader.picasso;

public interface Callback {

    public static class EmptyCallback implements Callback {
        public void onError() {
        }

        public void onSuccess() {
        }
    }

    void onError();

    void onSuccess();
}
