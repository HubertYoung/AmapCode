package com.alipay.mobile.nebulacore.web;

import android.text.TextUtils;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5FallbackStreamProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class H5InputStream extends InputStream {
    public static final String TAG = "H5InputStream";
    private String a;
    private HttpURLConnection b;
    private H5InputListener c;
    private boolean d = true;
    public InputStream realStream;
    public int statusCode;

    public interface H5InputListener {
        void onInputClose(H5InputStream h5InputStream);

        void onInputException();

        void onInputOpen(H5InputStream h5InputStream);
    }

    public H5InputStream(String url, H5InputListener listener) {
        this.a = url;
        this.c = listener;
        String useNetsdkConfig = H5Environment.getConfigWithProcessCache("androidFallbackNetwork");
        if (!TextUtils.isEmpty(useNetsdkConfig)) {
            if ("YES".equals(useNetsdkConfig)) {
                this.d = true;
            } else if ("NO".equalsIgnoreCase(useNetsdkConfig)) {
                this.d = false;
            }
        }
        this.realStream = a();
    }

    public int read() {
        if (this.realStream != null) {
            return this.realStream.read();
        }
        return -1;
    }

    public int available() {
        if (this.realStream != null) {
            return this.realStream.available();
        }
        return super.available();
    }

    public void close() {
        H5Log.d(TAG, "close " + this);
        if (this.realStream != null) {
            this.realStream.close();
        } else {
            super.close();
        }
        if (this.b != null) {
            this.b.disconnect();
        }
        this.b = null;
        if (this.c != null) {
            this.c.onInputClose(this);
        }
    }

    public void mark(int readlimit) {
        if (this.realStream != null) {
            this.realStream.mark(readlimit);
        } else {
            super.mark(readlimit);
        }
    }

    public boolean markSupported() {
        if (this.realStream != null) {
            return this.realStream.markSupported();
        }
        return super.markSupported();
    }

    public synchronized void reset() {
        if (this.realStream != null) {
            this.realStream.reset();
        } else {
            super.reset();
        }
    }

    public long skip(long byteCount) {
        if (this.realStream != null) {
            return this.realStream.skip(byteCount);
        }
        return super.skip(byteCount);
    }

    public int read(byte[] buffer) {
        return read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) {
        if (this.realStream != null) {
            return this.realStream.read(buffer, byteOffset, byteCount);
        }
        return super.read(buffer, byteOffset, byteCount);
    }

    private InputStream a() {
        InputStream inputStream = null;
        try {
            H5FallbackStreamProvider fallbackStreamProvider = (H5FallbackStreamProvider) H5Utils.getProvider(H5FallbackStreamProvider.class.getName());
            if (!this.d || fallbackStreamProvider == null) {
                H5Log.d(TAG, "useUrlConnection get fallback content");
                this.b = (HttpURLConnection) new URL(this.a).openConnection();
                this.statusCode = this.b.getResponseCode();
                H5Log.d(TAG, "statusCode " + this.statusCode);
                inputStream = new BufferedInputStream(this.b.getInputStream());
            } else {
                H5Log.d(TAG, "useNetsdk get fallback content");
                inputStream = new BufferedInputStream(fallbackStreamProvider.getFallbackInputStream(this.a));
            }
            if (this.c != null) {
                this.c.onInputOpen(this);
            }
        } catch (Exception e) {
            H5Log.e(TAG, this.a + " failed to init stream ", e);
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_FallbackException").param4().add("走fallback请求失败," + this.a + " failed to init stream " + e, null));
            if (this.c != null) {
                this.c.onInputException();
            }
        }
        return inputStream;
    }
}
