package com.alipay.mobile.tinyappcommon.remotedebug;

import com.alipay.mobile.h5container.api.H5Event;

/* compiled from: AbstractDataChannel */
public abstract class a {

    /* renamed from: com.alipay.mobile.tinyappcommon.remotedebug.a$a reason: collision with other inner class name */
    /* compiled from: AbstractDataChannel */
    public interface C0101a {
        void onConnectClosed(String str);

        void onConnectError(String str, int i, String str2);

        void onConnectSuccess(String str);

        void recv(String str);

        void recv(byte[] bArr);
    }

    public void a(C0101a listener) {
    }

    public void a(String url, H5Event h5Event) {
    }

    public boolean a(String message) {
        return false;
    }

    public void b(String reason) {
    }
}
