package anet.channel.statist;

@Monitor(module = "networkPrefer", monitorPoint = "request_monitor")
public class RequestMonitor extends RequestStatistic {
    public RequestMonitor(RequestStatistic requestStatistic) {
        super(null, null);
        if (requestStatistic != null) {
            this.host = requestStatistic.host;
            this.ip = requestStatistic.ip;
            this.port = requestStatistic.port;
            this.isSSL = requestStatistic.isSSL;
            this.ipRefer = requestStatistic.ipRefer;
            this.ipType = requestStatistic.ipType;
            this.isProxy = requestStatistic.isProxy;
            this.proxyType = requestStatistic.proxyType;
            this.netType = requestStatistic.netType;
            this.bssid = requestStatistic.bssid;
            this.protocolType = requestStatistic.protocolType;
            this.isDNS = requestStatistic.isDNS;
            this.retryTimes = requestStatistic.retryTimes;
            this.bizId = requestStatistic.bizId;
            this.f_refer = requestStatistic.f_refer;
            this.ret = requestStatistic.ret;
            this.statusCode = requestStatistic.statusCode;
            this.msg = requestStatistic.msg;
            this.contentEncoding = requestStatistic.contentEncoding;
            this.contentType = requestStatistic.contentType;
            this.degraded = requestStatistic.degraded;
            this.isBg = requestStatistic.isBg;
            this.errorTrace = requestStatistic.errorTrace;
            this.url = requestStatistic.url;
            this.lng = requestStatistic.lng;
            this.lat = requestStatistic.lat;
            this.accuracy = requestStatistic.accuracy;
            this.roaming = requestStatistic.roaming;
            this.mnc = requestStatistic.mnc;
            this.unit = requestStatistic.unit;
            this.extra = requestStatistic.extra;
            this.reqHeadInflateSize = requestStatistic.reqHeadInflateSize;
            this.reqBodyInflateSize = requestStatistic.reqBodyInflateSize;
            this.reqHeadDeflateSize = requestStatistic.reqHeadDeflateSize;
            this.reqBodyDeflateSize = requestStatistic.reqBodyDeflateSize;
            this.rspHeadInflateSize = requestStatistic.rspHeadInflateSize;
            this.rspBodyInflateSize = requestStatistic.rspBodyInflateSize;
            this.rspHeadDeflateSize = requestStatistic.rspHeadDeflateSize;
            this.rspBodyDeflateSize = requestStatistic.rspBodyDeflateSize;
            this.retryCostTime = requestStatistic.retryCostTime;
            this.connWaitTime = requestStatistic.connWaitTime;
            this.sendBeforeTime = requestStatistic.sendBeforeTime;
            this.processTime = requestStatistic.processTime;
            this.sendDataTime = requestStatistic.sendDataTime;
            this.firstDataTime = requestStatistic.firstDataTime;
            this.recDataTime = requestStatistic.recDataTime;
            this.serverRT = requestStatistic.serverRT;
            this.cacheTime = requestStatistic.cacheTime;
            this.lastProcessTime = requestStatistic.lastProcessTime;
            this.callbackTime = requestStatistic.callbackTime;
            this.oneWayTime = requestStatistic.oneWayTime;
            this.sendDataSize = requestStatistic.sendDataSize;
            this.recDataSize = requestStatistic.recDataSize;
        }
    }
}
