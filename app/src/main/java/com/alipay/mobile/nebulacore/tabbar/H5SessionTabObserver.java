package com.alipay.mobile.nebulacore.tabbar;

public class H5SessionTabObserver {
    private volatile H5SessionTabListener a;
    private volatile String b;

    public interface H5SessionTabListener {
        void onDataParsed(String str);
    }

    public synchronized H5SessionTabListener setData(String data) {
        try {
            this.b = data;
        }
        return this.a;
    }

    public synchronized String getData(H5SessionTabListener h5SessionTabListener) {
        try {
            this.a = h5SessionTabListener;
        }
        return this.b;
    }

    public void clear() {
        this.a = null;
        this.b = null;
    }
}
