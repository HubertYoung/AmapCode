package com.alipay.mobile.common.transportext.biz.spdy;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.net.Proxy;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class Address {
    final OkAuthenticator authenticator;
    final HostnameVerifier hostnameVerifier;
    final Proxy proxy;
    final SSLSocketFactory sslSocketFactory;
    final List<String> transports;
    final String uriHost;
    final int uriPort;

    public Address(String uriHost2, int uriPort2, SSLSocketFactory sslSocketFactory2, HostnameVerifier hostnameVerifier2, OkAuthenticator authenticator2, Proxy proxy2, List<String> transports2) {
        if (uriHost2 == null) {
            throw new NullPointerException("uriHost == null");
        } else if (uriPort2 <= 0) {
            throw new IllegalArgumentException("uriPort <= 0: " + uriPort2);
        } else if (authenticator2 == null) {
            throw new IllegalArgumentException("authenticator == null");
        } else if (transports2 == null) {
            throw new IllegalArgumentException("transports == null");
        } else {
            this.proxy = proxy2;
            this.uriHost = uriHost2;
            this.uriPort = uriPort2;
            this.sslSocketFactory = sslSocketFactory2;
            this.hostnameVerifier = hostnameVerifier2;
            this.authenticator = authenticator2;
            this.transports = Util.immutableList(transports2);
        }
    }

    public final String getUriHost() {
        return this.uriHost;
    }

    public final int getUriPort() {
        return this.uriPort;
    }

    public final SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public final HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public final OkAuthenticator getAuthenticator() {
        return this.authenticator;
    }

    public final List<String> getTransports() {
        return this.transports;
    }

    public final Proxy getProxy() {
        return this.proxy;
    }

    public final boolean equals(Object other) {
        if (other instanceof Address) {
            Address that = (Address) other;
            if (!TextUtils.equals(this.uriHost, that.uriHost)) {
                try {
                    LoggerFactory.getTraceLogger().debug(InnerLogUtil.MWALLET_SPDY_TAG, "uriHost not equal. this.uriHost=[" + this.uriHost + "] that.uriHost=[" + that.uriHost + "]");
                } catch (Exception e) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "TraceLogger->uriHost exception: " + e.toString());
                }
                return false;
            } else if (this.uriPort != that.uriPort) {
                try {
                    LoggerFactory.getTraceLogger().debug(InnerLogUtil.MWALLET_SPDY_TAG, "uriPort not equal. this.uriPort=[" + this.uriPort + "] that.uriPort=[" + that.uriPort + "]");
                } catch (Exception e2) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "TraceLogger->uriPort exception: " + e2.toString());
                }
                return false;
            } else if (!Util.equal(this.sslSocketFactory, that.sslSocketFactory)) {
                try {
                    LoggerFactory.getTraceLogger().debug(InnerLogUtil.MWALLET_SPDY_TAG, "sslSocketFactory not equal. this.sslSocketFactory=[" + (this.sslSocketFactory != null ? this.sslSocketFactory : " is null ") + "] that.sslSocketFactory=[" + (that.sslSocketFactory != null ? that.sslSocketFactory : " is null ") + "]");
                } catch (Exception e3) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "TraceLogger->sslSocketFactory not equal exception: " + e3.toString());
                }
                return false;
            } else if (!Util.equal(this.hostnameVerifier, that.hostnameVerifier)) {
                try {
                    LoggerFactory.getTraceLogger().debug(InnerLogUtil.MWALLET_SPDY_TAG, "hostnameVerifier not equal. this.hostnameVerifier=[" + (this.hostnameVerifier != null ? this.hostnameVerifier : "is null") + "] that.hostnameVerifier=[" + (that.hostnameVerifier != null ? that.hostnameVerifier : " is null ") + "]");
                } catch (Exception e4) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "TraceLogger->hostnameVerifier exception: " + e4.toString());
                }
                return false;
            } else if (!Util.equal(this.authenticator, that.authenticator)) {
                try {
                    LoggerFactory.getTraceLogger().debug(InnerLogUtil.MWALLET_SPDY_TAG, "authenticator not equal. this.authenticator=[" + (this.authenticator != null ? this.authenticator : " is null ") + "] that.authenticator=[" + (that.authenticator != null ? that.authenticator : "is null") + "]");
                } catch (Exception e5) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "TraceLogger->authenticator exception: " + e5.toString());
                }
                return false;
            } else if (Util.equal(this.transports, that.transports)) {
                return true;
            } else {
                try {
                    LoggerFactory.getTraceLogger().debug(InnerLogUtil.MWALLET_SPDY_TAG, "transports not equal. this.transports=[" + (this.transports != null ? this.transports : " is null ") + "] that.transports=[" + (that.transports != null ? that.transports : "is null") + "]");
                } catch (Exception e6) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "TraceLogger->transports exception: " + e6.toString());
                }
                return false;
            }
        } else {
            LoggerFactory.getTraceLogger().debug(InnerLogUtil.MWALLET_SPDY_TAG, "Address object changed.  other instanceof Address is false. other=(" + (other != null ? other.getClass().getName() : "is null.") + ")");
            return false;
        }
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3 = 0;
        int hashCode = (((((this.uriHost.hashCode() + 527) * 31) + this.uriPort) * 31) + (this.sslSocketFactory != null ? this.sslSocketFactory.hashCode() : 0)) * 31;
        if (this.hostnameVerifier != null) {
            i = this.hostnameVerifier.hashCode();
        } else {
            i = 0;
        }
        int i4 = (hashCode + i) * 31;
        if (this.authenticator != null) {
            i2 = this.authenticator.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (i4 + i2) * 31;
        if (this.proxy != null) {
            i3 = this.proxy.hashCode();
        }
        return ((i5 + i3) * 31) + this.transports.hashCode();
    }
}
