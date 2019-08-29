package com.autonavi.link;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.link.connect.a.b;
import com.autonavi.link.connect.bluetooth.BluetoothSPP;
import com.autonavi.link.connect.wifi.ShareNetManager;
import com.autonavi.link.transmit.proxy.LinkProxy;
import com.autonavi.link.utils.Logger;

public class LinkSDK {
    private static volatile LinkSDK a;
    private Context b = null;
    private boolean c = false;
    private boolean d = false;

    public void setServerPort(int i) {
    }

    private LinkSDK() {
    }

    public static LinkSDK getInstance() {
        if (a == null) {
            synchronized (LinkSDK.class) {
                try {
                    if (a == null) {
                        a = new LinkSDK();
                    }
                }
            }
        }
        return a;
    }

    public synchronized boolean isInit() {
        return this.c;
    }

    public String getSdkVersion() {
        return this.d ? "2.0" : "V2.0.199";
    }

    public void setLinkVersion(boolean z) {
        this.d = z;
    }

    public boolean oldLinkVersion() {
        return this.d;
    }

    public BluetoothSPP getBtInstance() {
        return BluetoothSPP.getInstance(this.b);
    }

    public ShareNetManager getWifiInstance() {
        return ShareNetManager.getInstance();
    }

    public void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Null context is not available..");
        }
        this.c = true;
        this.b = context.getApplicationContext();
        LinkProxy.init();
        Logger.init();
        ShareNetManager.getInstance().init(context);
    }

    public void release() {
        if (this.c) {
            ShareNetManager.getInstance().release();
            LinkProxy.getInstance().release();
            this.b = null;
            this.c = false;
        }
    }

    public String getConnectionId(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.contains("127.0.0.1")) {
            return LinkProxy.getInstance().getConnectId();
        }
        return b.a().a(str);
    }

    public int getProxyPort() {
        return LinkProxy.getInstance().getProxyPort();
    }
}
