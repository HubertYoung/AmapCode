package com.alipay.android.phone.mobilecommon.rpc;

import com.alipay.mobile.common.rpc.Config;
import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.HttpTransportSevice;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.Header;

/* compiled from: AlipayRpcService */
final class a implements Config {
    final /* synthetic */ AlipayRpcService a;

    a(AlipayRpcService alipayRpcService) {
        this.a = alipayRpcService;
    }

    public final String getUrl() {
        BioLog.w((String) "AlipayRpcService", "getUrl() : mRemoteUrl=" + this.a.c);
        return this.a.c;
    }

    public final Transport getTransport() {
        return (Transport) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(HttpTransportSevice.class.getName());
    }

    public final void addExtHeaders(HttpUrlRequest httpUrlRequest) {
        BioLog.w((String) "AlipayRpcService", "before addExtHeaders: " + httpUrlRequest.getHeaders());
        ArrayList<Header> headers = httpUrlRequest.getHeaders();
        Iterator<Header> it = headers.iterator();
        while (it.hasNext()) {
            Header next = it.next();
            if ("WorkspaceId".equalsIgnoreCase(next.getName())) {
                it.remove();
                BioLog.i("AlipayRpcService", "[WorkspaceId]: " + next.getValue() + " DELETED! ");
            }
            if ("AppId".equalsIgnoreCase(next.getName())) {
                it.remove();
                BioLog.i("AlipayRpcService", "[AppId]: " + next.getValue() + " DELETED! ");
            }
        }
        this.a.addAntCloudHeaders(headers);
        BioLog.w((String) "AlipayRpcService", "after addExtHeaders: " + httpUrlRequest.getHeaders());
    }

    public final void giveResponseHeader(String str, HttpUrlHeader httpUrlHeader) {
    }

    public final boolean isCompress() {
        return false;
    }

    public final String getAppKey() {
        return "rpc-sdk-online";
    }
}
