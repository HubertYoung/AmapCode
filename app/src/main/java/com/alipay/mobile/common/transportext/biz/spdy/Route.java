package com.alipay.mobile.common.transportext.biz.spdy;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class Route {
    final Address address;
    final InetSocketAddress inetSocketAddress;
    final boolean modernTls;
    final Proxy proxy;

    public Route(Address address2, Proxy proxy2, InetSocketAddress inetSocketAddress2, boolean modernTls2) {
        if (address2 == null) {
            throw new NullPointerException("address == null");
        } else if (proxy2 == null) {
            throw new NullPointerException("proxy == null");
        } else if (inetSocketAddress2 == null) {
            throw new NullPointerException("inetSocketAddress == null");
        } else {
            this.address = address2;
            this.proxy = proxy2;
            this.inetSocketAddress = inetSocketAddress2;
            this.modernTls = modernTls2;
        }
    }

    public Address getAddress() {
        return this.address;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public InetSocketAddress getSocketAddress() {
        return this.inetSocketAddress;
    }

    public boolean isModernTls() {
        return this.modernTls;
    }

    /* access modifiers changed from: 0000 */
    public Route flipTlsMode() {
        return new Route(this.address, this.proxy, this.inetSocketAddress, !this.modernTls);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) {
            return false;
        }
        Route other = (Route) obj;
        if (!this.address.equals(other.address) || !this.proxy.equals(other.proxy) || !this.inetSocketAddress.equals(other.inetSocketAddress) || this.modernTls != other.modernTls) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = ((((this.address.hashCode() + 527) * 31) + this.proxy.hashCode()) * 31) + this.inetSocketAddress.hashCode();
        return (this.modernTls ? result * 31 : 0) + result;
    }

    public String toString() {
        return "Route{address=[" + this.address.getUriHost() + ":" + this.address.getUriPort() + "], proxy=" + (this.proxy != null ? this.proxy.toString() : "is null") + ", inetSocketAddress=" + (this.inetSocketAddress != null ? this.inetSocketAddress.toString() : "is null") + ", modernTls=" + this.modernTls + '}';
    }
}
