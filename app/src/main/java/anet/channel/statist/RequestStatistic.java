package anet.channel.statist;

import anet.channel.entity.ConnType;
import anet.channel.status.NetworkStatusHelper;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

@Monitor(module = "networkPrefer", monitorPoint = "network")
public class RequestStatistic extends StatObject {
    @Dimension
    public float accuracy = -1.0f;
    @Dimension
    public volatile String bizId;
    @Dimension
    public volatile String bssid = null;
    @Measure
    public volatile long cacheTime = 0;
    @Measure
    public volatile long callbackTime = 0;
    @Measure
    public volatile long connWaitTime = 0;
    @Dimension
    public volatile String contentEncoding = null;
    public volatile long contentLength = 0;
    @Dimension
    public volatile String contentType = null;
    @Dimension
    public volatile int degraded = 0;
    @Dimension
    public volatile StringBuilder errorTrace = null;
    @Dimension
    public JSONObject extra = null;
    @Dimension
    public volatile String f_refer;
    @Measure
    public volatile long firstDataTime = 0;
    @Dimension
    public volatile String host;
    @Dimension
    public volatile String ip;
    @Dimension
    public volatile int ipRefer = 0;
    @Dimension
    public volatile int ipType = 1;
    @Dimension
    public volatile String isBg = "";
    @Dimension
    public volatile boolean isDNS = false;
    public final AtomicBoolean isDone = new AtomicBoolean(false);
    @Dimension
    public volatile boolean isProxy;
    @Dimension
    public volatile boolean isSSL;
    @Measure
    public volatile long lastProcessTime = 0;
    @Dimension
    public double lat = 90000.0d;
    @Dimension
    public double lng = 90000.0d;
    @Dimension
    public String mnc = "0";
    @Dimension(name = "errorMsg")
    public volatile String msg = "";
    @Dimension
    public volatile String netType = "";
    @Measure(max = 60000.0d)
    public volatile long oneWayTime = 0;
    @Dimension
    public volatile int port;
    @Measure
    public volatile long processTime = 0;
    @Dimension
    public volatile String protocolType;
    @Dimension
    public volatile String proxyType = "";
    @Measure
    public volatile long recDataSize = 0;
    @Measure
    public volatile long recDataTime = 0;
    @Measure
    public volatile long reqBodyDeflateSize = 0;
    @Measure
    public volatile long reqBodyInflateSize = 0;
    @Measure
    public volatile long reqHeadDeflateSize = 0;
    @Measure
    public volatile long reqHeadInflateSize = 0;
    public volatile long reqStart = 0;
    @Dimension
    public volatile int ret;
    @Measure
    public volatile long retryCostTime = 0;
    @Dimension
    public volatile int retryTimes;
    @Dimension
    public int roaming = 0;
    @Measure
    public volatile long rspBodyDeflateSize = 0;
    @Measure
    public volatile long rspBodyInflateSize = 0;
    public volatile long rspEnd = 0;
    @Measure
    public volatile long rspHeadDeflateSize = 0;
    @Measure
    public volatile long rspHeadInflateSize = 0;
    public volatile long rspStart = 0;
    @Measure
    public volatile long sendBeforeTime = 0;
    @Measure
    public volatile long sendDataSize = 0;
    @Measure
    public volatile long sendDataTime = 0;
    public volatile long sendEnd = 0;
    public volatile long sendStart = 0;
    @Measure
    public volatile long serverRT = 0;
    public volatile boolean spdyRequestSend = false;
    public volatile long start = 0;
    @Dimension(name = "errorCode")
    public volatile int statusCode = 0;
    public volatile int tnetErrorCode = 0;
    @Dimension
    public String unit;
    @Dimension(name = "URL")
    public volatile String url;
    public String userInfo;
    @Deprecated
    public volatile long waitingTime = 0;

    public RequestStatistic(String str, String str2) {
        this.host = str;
        this.proxyType = NetworkStatusHelper.j();
        this.isProxy = !this.proxyType.isEmpty();
        this.netType = NetworkStatusHelper.b();
        this.bssid = NetworkStatusHelper.g();
        this.isBg = m.h() ? "bg" : "fg";
        this.roaming = NetworkStatusHelper.f() ? 1 : 0;
        this.mnc = NetworkStatusHelper.e();
        this.bizId = str2;
    }

    public void setConnType(ConnType connType) {
        this.isSSL = connType.c();
        this.protocolType = connType.toString();
    }

    public void setIPAndPort(String str, int i) {
        this.ip = str;
        this.port = i;
        if (str != null) {
            this.isDNS = true;
        }
        if (this.retryTimes == 0 && str != null) {
            putExtra("firstIp", str);
        }
    }

    public void setIpInfo(int i, int i2) {
        this.ipRefer = i;
        this.ipType = i2;
    }

    public void appendErrorTrace(String str) {
        if (this.errorTrace == null) {
            this.errorTrace = new StringBuilder();
        }
        if (this.errorTrace.length() != 0) {
            this.errorTrace.append(",");
        }
        StringBuilder sb = this.errorTrace;
        sb.append(str);
        sb.append("=");
        sb.append(System.currentTimeMillis() - this.reqStart);
    }

    public void recordRedirect(int i, String str) {
        this.url = str;
        appendErrorTrace(String.valueOf(i));
        long currentTimeMillis = System.currentTimeMillis();
        this.retryCostTime += currentTimeMillis - this.start;
        this.start = currentTimeMillis;
    }

    public void putExtra(String str, Object obj) {
        try {
            if (this.extra == null) {
                this.extra = new JSONObject();
            }
            this.extra.put(str, obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("[RequestStatistic]ret=");
        sb.append(this.ret);
        sb.append(",statusCode=");
        sb.append(this.statusCode);
        sb.append(",msg=");
        sb.append(this.msg);
        sb.append(",host=");
        sb.append(this.host);
        sb.append(",ip=");
        sb.append(this.ip);
        sb.append(",port=");
        sb.append(this.port);
        sb.append(",protocolType=");
        sb.append(this.protocolType);
        sb.append(",retryTime=");
        sb.append(this.retryTimes);
        sb.append(",retryCostTime=");
        sb.append(this.retryCostTime);
        sb.append(",processTime=");
        sb.append(this.processTime);
        sb.append(",connWaitTime=");
        sb.append(this.connWaitTime);
        sb.append(",cacheTime=");
        sb.append(this.cacheTime);
        sb.append(",sendDataTime=");
        sb.append(this.sendDataTime);
        sb.append(",firstDataTime=");
        sb.append(this.firstDataTime);
        sb.append(",recDataTime=");
        sb.append(this.recDataTime);
        sb.append(",lastProcessTime=");
        sb.append(this.lastProcessTime);
        sb.append(",oneWayTime=");
        sb.append(this.oneWayTime);
        sb.append(",callbackTime=");
        sb.append(this.callbackTime);
        sb.append(",serverRT=");
        sb.append(this.serverRT);
        sb.append(",sendSize=");
        sb.append(this.sendDataSize);
        sb.append(",recDataSize=");
        sb.append(this.recDataSize);
        sb.append(",originalDataSize=");
        sb.append(this.rspBodyDeflateSize);
        if (this.extra != null) {
            sb.append(",extra=");
            sb.append(this.extra.toString());
        }
        sb.append(",url=");
        sb.append(this.url);
        return sb.toString();
    }

    public boolean beforeCommit() {
        return this.statusCode != -200;
    }
}
