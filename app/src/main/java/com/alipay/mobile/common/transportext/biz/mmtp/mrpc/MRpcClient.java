package com.alipay.mobile.common.transportext.biz.mmtp.mrpc;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.ExtTransportClient;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.ABTestHelper;
import com.alipay.mobile.common.transport.utils.DataItemsUtil;
import com.alipay.mobile.common.transport.utils.HeaderConstant;
import com.alipay.mobile.common.transport.utils.HttpUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.RetryService;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.cookie.MRFC2109DomainHandler;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcRequest;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import com.sina.weibo.sdk.utils.WbAuthConstants;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.cookie.BestMatchSpec;
import org.apache.http.impl.cookie.RFC2109Spec;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HttpContext;

public class MRpcClient implements ExtTransportClient {
    private static MRpcClient MRPC_CLIENT = null;
    private static final String TAG = "MRpcClient";
    private String SERVER_TIME_HEADER = "mmtp-ext-utc";
    private Context mContext;

    public static final MRpcClient getInstance(Context context) {
        if (MRPC_CLIENT != null) {
            return MRPC_CLIENT;
        }
        MRpcClient mRpcClient = new MRpcClient(context);
        MRPC_CLIENT = mRpcClient;
        return mRpcClient;
    }

    private MRpcClient(Context context) {
        this.mContext = context;
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest request, HttpContext context) {
        LogCatUtil.info(TAG, "MRPCClient execute.");
        TransportContext netContext = (TransportContext) context.getAttribute(TransportConstants.KEY_NET_CONTEXT);
        MRpcRequest mRpcRequest = new MRpcRequest(HttpUtils.getRequestURI(request).toString());
        constructRequest(request, (HttpUrlRequest) context.getAttribute(TransportConstants.KEY_ORIGIN_REQUEST), mRpcRequest, context);
        MRpcTransport transport = new MRpcTransport();
        try {
            long startTime = System.currentTimeMillis();
            MRpcResponse mprcResponse = execute(mRpcRequest, transport);
            int allTime = (int) (System.currentTimeMillis() - startTime);
            if (MiscUtils.isDebugger(ExtTransportEnv.getAppContext())) {
                LogCatUtil.printInfo(TAG, "AMTP Transport RESPONSE_notimeout.request=" + mRpcRequest + ".response=" + mprcResponse);
            }
            fillLogDataItem(netContext, mprcResponse, allTime);
            HttpResponse resultResponse = handleResponse(mprcResponse);
            extractCookiesFromResponse(httpHost, request, resultResponse, context);
            return resultResponse;
        } catch (Exception e) {
            String targetHost = transport.getTargetHost();
            if (!TextUtils.isEmpty(targetHost)) {
                netContext.getCurrentDataContainer().putDataItem("TARGET_HOST", targetHost);
            }
            String libversion = transport.getMRpcConneciton().getMrpcConnContext().get(MonitorLoggerUtils.LIB_VERSION);
            if (!TextUtils.isEmpty(libversion)) {
                if (TextUtils.equals(MonitorLoggerUtils.LIB_VERSION_BIFROST_HTTP2, libversion)) {
                    netContext.getCurrentDataContainer().putDataItem("NETTUNNEL", MonitorLoggerUtils.NETTUNNEL_ULib_h2);
                }
                netContext.getCurrentDataContainer().putDataItem(MonitorLoggerUtils.LIB_VERSION, libversion);
            }
            netContext.getCurrentDataContainer().putDataItem("ERROR", e.getMessage());
            processMtag(netContext, "");
            throw e;
        }
    }

    private MRpcResponse execute(MRpcRequest mRpcRequest, MRpcTransport transport) {
        return transport.execute(mRpcRequest);
    }

    private void fillLogDataItem(TransportContext netContext, MRpcResponse mprcResponse, int allTime) {
        LogCatUtil.printInfo(TAG, "fillLogDataItem start.");
        try {
            if (!TextUtils.isEmpty(mprcResponse.targetHost)) {
                netContext.getCurrentDataContainer().putDataItem("TARGET_HOST", mprcResponse.targetHost);
            }
            int sentTime = 0;
            if (mprcResponse.dnsTiming > 0) {
                sentTime = mprcResponse.dnsTiming + 0;
                netContext.getCurrentDataContainer().putDataItem("DNS_TIME", Integer.toString(mprcResponse.dnsTiming));
            }
            if (mprcResponse.tcpTiming > 0) {
                sentTime += mprcResponse.tcpTiming;
                netContext.getCurrentDataContainer().putDataItem("TCP_TIME", Integer.toString(mprcResponse.tcpTiming));
            }
            if (mprcResponse.sslTiming > 0) {
                sentTime += mprcResponse.sslTiming;
                netContext.getCurrentDataContainer().putDataItem("SSL_TIME", Integer.toString(mprcResponse.sslTiming));
            }
            if (mprcResponse.tcpNtv > 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.NTCP_TIME, Integer.toString(mprcResponse.tcpNtv));
            }
            if (mprcResponse.sslNtv > 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.NSSL_TIME, Integer.toString(mprcResponse.sslNtv));
            }
            if (mprcResponse.readTiming >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.READ_TIME, Integer.toString(mprcResponse.readTiming));
            }
            if (mprcResponse.jtcTiming >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.JTC_TIME, Integer.toString(mprcResponse.jtcTiming));
            }
            if (mprcResponse.amnetWaitTiming >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.AW_TIME, Integer.toString(mprcResponse.amnetWaitTiming));
            }
            if (mprcResponse.ipcP2m > 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.IPC_TIME2, Integer.toString(mprcResponse.ipcP2m));
            }
            if (mprcResponse.amnetStalledTime >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.AMNET_STALLED_TIME, Integer.toString(mprcResponse.amnetStalledTime));
            }
            if (mprcResponse.airTime > 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.AIR_TIME, Integer.toString(mprcResponse.airTime));
            }
            LogCatUtil.printInfo(TAG, "sentTime:" + sentTime);
            int dataTime = allTime;
            if (allTime > sentTime) {
                dataTime = allTime - sentTime;
            }
            if (dataTime > mprcResponse.readTiming) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.WAIT_TIME, Integer.toString(dataTime - mprcResponse.readTiming));
            }
            netContext.getCurrentDataContainer().putDataItem("REQ_SIZE", Integer.toString(mprcResponse.reqSize));
            netContext.getCurrentDataContainer().putDataItem("RES_SIZE", Integer.toString(mprcResponse.respSize));
            netContext.getCurrentDataContainer().timeItemRelease("ALL_TIME");
            if (mprcResponse.retried) {
                netContext.getCurrentDataContainer().putDataItem("RETRY", "T");
            }
            netContext.getCurrentDataContainer().putDataItem(RPCDataItems.RPCID, String.valueOf(mprcResponse.streamId));
            if (mprcResponse.saTime > 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.SA_TIME, Integer.toString(mprcResponse.saTime));
                AlipayQosService.getInstance().estimate((double) mprcResponse.saTime, 1);
            }
            if (mprcResponse.isOnShort) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.ONSHORT, "T");
            } else if (mprcResponse.useShort) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.ONSHORT, "F");
            }
            if (!TextUtils.isEmpty(mprcResponse.targetHostShort)) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.TARGET_HOST_SHORT, mprcResponse.targetHostShort);
            }
            if (mprcResponse.qoeCur >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.QOE_CUR, String.valueOf(mprcResponse.qoeCur));
            }
            if (!TextUtils.isEmpty(mprcResponse.queneStorage)) {
                addQueneStorageTime(netContext, mprcResponse);
            }
            if (mprcResponse.isFlexible) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.FLEXIBLE, "T");
            }
            if (mprcResponse.headers != null) {
                String val = mprcResponse.headers.get("cps");
                if (!TextUtils.isEmpty(val)) {
                    LogCatUtil.printInfo(TAG, "cps:" + val);
                    netContext.getCurrentDataContainer().putDataItem(RPCDataItems.CPS, val);
                }
            }
            if (mprcResponse.ctjOutTime >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.CTJ_OUT_TIME, Integer.toString(mprcResponse.ctjOutTime));
            }
            if (mprcResponse.ntIOTime >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.NT_IO_TIME, Integer.toString(mprcResponse.ntIOTime));
            }
            if (mprcResponse.queueOutTime >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.QUEUE_OUT_TIME, Integer.toString(mprcResponse.queueOutTime));
            }
            if (mprcResponse.amnetHungTime >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.AMNET_HUNG_TIME, Integer.toString(mprcResponse.amnetHungTime));
            }
            if (mprcResponse.amnetEncodeTime >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.AMNET_ENCODE_TIME, Integer.toString(mprcResponse.amnetEncodeTime));
            }
            if (mprcResponse.amnetAllTime >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.AMNET_ALL_TIME, Integer.toString(mprcResponse.amnetAllTime));
            }
            processMtag(netContext, mprcResponse.mtag);
            netContext.getCurrentDataContainer().putDataItem(RPCDataItems.CID, Long.toString(mprcResponse.cid));
            if (!TextUtils.isEmpty(mprcResponse.clientIp)) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.CIP, mprcResponse.clientIp);
            }
            if (mprcResponse.reqZipType >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.UP_ZIP_TYPE, String.valueOf(mprcResponse.reqZipType));
            }
            if (mprcResponse.rspZipType >= 0) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.DOWN_ZIP_TYPE, String.valueOf(mprcResponse.rspZipType));
            }
            netContext.getCurrentDataContainer().removeDataItem(MonitorLoggerUtils.LIB_VERSION);
            if (!mprcResponse.isUseBifrost) {
                netContext.getCurrentDataContainer().putDataItem(MonitorLoggerUtils.LIB_VERSION, MonitorLoggerUtils.LIB_VERSION_OLD);
            } else if (mprcResponse.isUseHttp2) {
                netContext.getCurrentDataContainer().putDataItem(MonitorLoggerUtils.LIB_VERSION, MonitorLoggerUtils.LIB_VERSION_BIFROST_HTTP2);
                netContext.getCurrentDataContainer().putDataItem("NETTUNNEL", MonitorLoggerUtils.NETTUNNEL_ULib_h2);
            } else {
                netContext.getCurrentDataContainer().putDataItem(MonitorLoggerUtils.LIB_VERSION, MonitorLoggerUtils.LIB_VERSION_BIFROST);
            }
            processAirTime(netContext, mprcResponse);
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
        }
    }

    private void processAirTime(TransportContext netContext, MRpcResponse mprcResponse) {
        try {
            Map headers = mprcResponse.getHeaders();
            if (headers != null && headers.size() != 0) {
                String servertime = headers.get(this.SERVER_TIME_HEADER);
                if (!TextUtils.isEmpty(servertime)) {
                    headers.remove(this.SERVER_TIME_HEADER);
                    long serverNeedTime = Long.parseLong(servertime);
                    String oriAirTime = netContext.getCurrentDataContainer().getDataItem(RPCDataItems.AIR_TIME);
                    long airTime = Long.parseLong(oriAirTime) - serverNeedTime;
                    LogCatUtil.debug(TAG, "oriAirTime:" + oriAirTime + ",serverTime:" + servertime + ",airTime:" + airTime);
                    DataItemsUtil.putDataItem2ContainerAnyway(netContext.getCurrentDataContainer(), RPCDataItems.AIR_TIME, String.valueOf(airTime));
                    DataItemsUtil.putDataItem2ContainerAnyway(netContext.getCurrentDataContainer(), RPCDataItems.UTC_TIME, servertime);
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "processAirTime ex:" + ex.toString());
        }
    }

    private void processMtag(TransportContext netContext, String mtag) {
        try {
            String mtagFinal = ABTestHelper.calculateABTagValues(mtag);
            if (!TextUtils.isEmpty(mtagFinal)) {
                netContext.getCurrentDataContainer().putDataItem(RPCDataItems.MTAG, mtagFinal);
                LogCatUtil.debug(TAG, "MTAG=[" + mtagFinal + "]");
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "processMtag ex:" + ex.toString());
        }
    }

    private void addQueneStorageTime(TransportContext netContext, MRpcResponse mprcResponse) {
        try {
            String[] values = mprcResponse.queneStorage.split("-");
            netContext.getCurrentDataContainer().putDataItem(RPCDataItems.AMNET_QUENE, String.valueOf(values[0]));
            netContext.getCurrentDataContainer().putDataItem(RPCDataItems.AMNET_ST, String.valueOf(values[1]));
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    private HttpResponse handleResponse(MRpcResponse response) {
        BasicHttpResponse httpResponse = createBasicHttpResponse(response);
        Map headers = response.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (Entry headerEntity : headers.entrySet()) {
                if (!TextUtils.isEmpty((CharSequence) headerEntity.getKey())) {
                    httpResponse.addHeader((String) headerEntity.getKey(), (String) headerEntity.getValue());
                }
            }
        }
        if (httpResponse.getStatusLine().getStatusCode() != 304 || response.body.length > 0) {
            httpResponse.setEntity(new InputStreamEntity(new ByteArrayInputStream(response.body), (long) response.body.length));
        }
        return httpResponse;
    }

    private BasicHttpResponse createBasicHttpResponse(MRpcResponse response) {
        String httpResultMsg = "OK";
        int httpResultCode = 200;
        Map respHeaders = response.getHeaders();
        if (respHeaders != null && TextUtils.equals(respHeaders.get("Result-Status"), WbAuthConstants.AUTH_FAILED_INSTALL_APP_COUNTERFEIT_CODE)) {
            httpResultCode = 304;
            httpResultMsg = "Not Modified";
        }
        return new BasicHttpResponse(HttpVersion.HTTP_1_1, httpResultCode, httpResultMsg);
    }

    private void constructRequest(HttpRequest request, HttpUrlRequest urlRequest, MRpcRequest rpcRequest, HttpContext context) {
        Header[] headers = request.getAllHeaders();
        for (Header header : headers) {
            String headerValue = header.getValue();
            if (!TextUtils.isEmpty(headerValue)) {
                rpcRequest.addHeader(header.getName(), headerValue);
            }
        }
        TransportContext netContext = (TransportContext) context.getAttribute(TransportConstants.KEY_NET_CONTEXT);
        if (!TextUtils.isEmpty(netContext.rpcUUID)) {
            rpcRequest.addHeader(HeaderConstant.HEADER_KEY_PARAM_TRACEID, netContext.rpcUUID);
        }
        rpcRequest.setDatas(urlRequest.getReqData());
        int soTimeout = HttpConnectionParams.getSoTimeout(request.getParams());
        if (soTimeout > 0) {
            rpcRequest.readTimeout = soTimeout;
        } else {
            rpcRequest.readTimeout = TransportStrategy.getReadTimeout(ExtTransportEnv.getAppContext());
        }
        rpcRequest.connTimeout = TransportStrategy.getConnTimeout(ExtTransportEnv.getAppContext());
        rpcRequest.sslTimeout = TransportStrategy.getHandshakTimeout();
        String api = rpcRequest.getHeaders().get("Operation-Type");
        if (RetryService.getInstance().isSupportResend(api, urlRequest.allowRetry)) {
            rpcRequest.important = true;
        }
        if (TransportStrategy.isSupportShortLink(api)) {
            rpcRequest.addHeader(HeaderConstant.HEADER_KEY_PARAM_SUPPORTSHORTLINK, "1");
        }
        if (MiscUtils.isDebugger(this.mContext)) {
            LogCatUtil.printInfo(TAG, "AMTP Transport REQUEST START! operationType=" + urlRequest.getTag(TransportConstants.KEY_OPERATION_TYPE) + ",requestline=" + request.getRequestLine() + ",request=" + rpcRequest);
        }
    }

    public int getModuleCategory() {
        return 1;
    }

    public void extractCookiesFromResponse(HttpHost httpHost, HttpRequest request, HttpResponse response, HttpContext context) {
        CookieStore cookieStore = (CookieStore) context.getAttribute("http.cookie-store");
        if (cookieStore != null) {
            CookieOrigin cookieOrigin = new CookieOrigin(httpHost.getHostName(), MiscUtils.getEffectivePort(httpHost.getSchemeName(), httpHost.getPort()), HttpUtils.getRequestURI(request).getPath(), true);
            LogCatUtil.printInfo(TAG, " set Cookie. host=" + cookieOrigin.getHost() + ",port=" + cookieOrigin.getPort() + ",path=" + cookieOrigin.getPath());
            add2CookieStore(response.headerIterator("Set-Cookie"), getCookieSpec(response), cookieOrigin, cookieStore);
        }
    }

    private CookieSpec getCookieSpec(HttpResponse response) {
        Header cookieHeader = response.getFirstHeader("Set-Cookie");
        if (cookieHeader == null || TextUtils.isEmpty(cookieHeader.getValue()) || (!cookieHeader.getValue().contains("Version=1") && !cookieHeader.getValue().contains("version=1"))) {
            return new BestMatchSpec();
        }
        RFC2109Spec tmpCookieSpec = new RFC2109Spec();
        tmpCookieSpec.registerAttribHandler("domain", new MRFC2109DomainHandler());
        return tmpCookieSpec;
    }

    private void add2CookieStore(HeaderIterator iterator, CookieSpec cookieSpec, CookieOrigin cookieOrigin, CookieStore cookieStore) {
        while (iterator.hasNext()) {
            try {
                for (Cookie cookie : cookieSpec.parse(iterator.nextHeader(), cookieOrigin)) {
                    try {
                        cookieSpec.validate(cookie, cookieOrigin);
                        cookieStore.addCookie(cookie);
                    } catch (Exception ex) {
                        LogCatUtil.error((String) TAG, (Throwable) ex);
                    }
                }
            } catch (Exception ex2) {
                LogCatUtil.error((String) TAG, (Throwable) ex2);
            }
        }
    }
}
