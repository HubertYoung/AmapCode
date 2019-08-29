package com.alipay.mobile.common.transportext.biz.spdy.longlink;

import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LongLinkTransportManager implements ISpyLongLink {
    public static final String TAG = "LL_TRANSPORT";
    private ISpdyCallBack callBack;
    private Future<?> mPingFuture;
    private Object mPingLock;
    private boolean mPingSuc;

    class SingletonHolder {
        /* access modifiers changed from: private */
        public static LongLinkTransportManager instance = new LongLinkTransportManager();

        private SingletonHolder() {
        }
    }

    private LongLinkTransportManager() {
        this.mPingSuc = false;
        this.mPingLock = new Object();
    }

    public static LongLinkTransportManager getInstance() {
        return SingletonHolder.instance;
    }

    public boolean isConnected() {
        Connection spdycon = getConnection();
        if (spdycon == null) {
            LogCatUtil.debug(TAG, "isConnected == false hasRegister=" + hasRegister());
            return false;
        } else if (!spdycon.isAlive() || !spdycon.isConnected()) {
            LogCatUtil.debug(TAG, "isConnected == false  hasRegister=" + hasRegister());
            Util.closeQuietly((Closeable) spdycon);
            return false;
        } else {
            SpdyConnection spdyConnection = spdycon.getSpdyConnection();
            if (spdyConnection == null || spdyConnection.isShutdown()) {
                LogCatUtil.debug(TAG, "isConnected shutdown hasRegister=" + hasRegister());
                return false;
            }
            LogCatUtil.debug(TAG, "isConnected == true hasRegister=" + hasRegister());
            return true;
        }
    }

    private boolean ping() {
        this.mPingFuture = SpdyLongLinkConnManagerImpl.getInstance().justPing();
        try {
            this.mPingSuc = false;
            this.mPingFuture.get(ExtTransportStrategy.getPingTimeOut(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            if (!this.mPingSuc) {
                return false;
            }
        }
        if (!this.mPingSuc) {
            synchronized (this.mPingLock) {
                try {
                    this.mPingLock.wait(ExtTransportStrategy.getPingTimeOut());
                } catch (Exception e2) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "mPingLock.wait exception : " + e2.toString());
                }
            }
        }
        if (!this.mPingSuc) {
            return false;
        }
        return true;
    }

    public boolean register(ISpdyCallBack callback) {
        LogCatUtil.debug(TAG, "注册：LongLink callback register,current status:" + (this.callBack == null));
        this.callBack = callback;
        return true;
    }

    public boolean unregister() {
        LogCatUtil.debug(TAG, "注销：LongLink callback unregister,current status:" + (this.callBack == null));
        this.callBack = null;
        return true;
    }

    public boolean connect() {
        if (isConnected()) {
            return true;
        }
        LogCatUtil.debug(TAG, "connect hasRegister=" + hasRegister());
        return SpdyLongLinkConnManagerImpl.getInstance().connect();
    }

    public void asynConnect() {
        SpdyLongLinkConnManagerImpl.getInstance().asynConnect();
        LogCatUtil.debug(TAG, "asynConnect hasRegister=" + hasRegister());
    }

    public void disconnect() {
    }

    public int writeData(byte[] data) {
        Connection spdycon = getConnection();
        if (spdycon == null) {
            throw new IOException("Connection is close");
        }
        spdycon.getSpdyConnection().writeTcpData(data);
        LogCatUtil.debug(TAG, "writeData hasRegister=" + hasRegister());
        return data.length;
    }

    public void notifyPingResponse() {
        if (!this.mPingSuc) {
            this.mPingSuc = true;
        }
        if (this.mPingFuture != null && !this.mPingFuture.isDone() && !this.mPingFuture.isCancelled()) {
            try {
                this.mPingFuture.cancel(true);
            } catch (Exception e) {
            }
        }
        synchronized (this.mPingLock) {
            try {
                this.mPingLock.notifyAll();
            } catch (Exception e2) {
                LogCatUtil.warn((String) TAG, (Throwable) e2);
            }
        }
    }

    public void notifyNetworkConnectivity(Context context, Intent intent) {
        try {
            SpdyLongLinkConnManagerImpl.getInstance().getNetworkConnectivityReceiver().onReceive(context, intent);
            LogCatUtil.info(TAG, "notifyNetworkConnectivity finish!");
        } catch (Exception e) {
            LogCatUtil.error((String) TAG, (Throwable) e);
        }
    }

    private Connection getConnection() {
        return SpdyLongLinkConnManagerImpl.getInstance().getConnection();
    }

    public void onRecvData(byte[] data) {
        if (this.callBack != null) {
            this.callBack.onRecvData(data);
        }
        LogCatUtil.debug(TAG, "onRecvData hasRegister=" + hasRegister());
    }

    public void onConnected() {
        if (this.callBack != null) {
            this.callBack.onConnected();
        }
        LogCatUtil.debug(TAG, "onConnected, hasRegister=" + hasRegister());
    }

    public void onDisconnected() {
        if (this.callBack != null) {
            this.callBack.onDisconnected();
        }
        LogCatUtil.debug(TAG, "onDisconnected, hasRegister=" + hasRegister());
    }

    public void onConnecting() {
        if (this.callBack != null) {
            this.callBack.onConnecting();
        }
        LogCatUtil.debug(TAG, "onConnecting, hasRegister=" + hasRegister());
    }

    public boolean spdyConnection() {
        return true;
    }

    public void setCallBack(ISpdyCallBack callBack2) {
        this.callBack = callBack2;
    }

    public ISpdyCallBack getCallBack() {
        return this.callBack;
    }

    public boolean hasRegister() {
        return this.callBack != null;
    }

    private boolean isNetworkActive() {
        if (SpdyLongLinkConnManagerImpl.getInstance().isNetworkActive()) {
            return true;
        }
        LogCatUtil.debug(TAG, "isConnected == false, network not active   hasRegister=" + hasRegister());
        return false;
    }
}
