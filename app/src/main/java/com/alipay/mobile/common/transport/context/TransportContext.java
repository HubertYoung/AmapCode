package com.alipay.mobile.common.transport.context;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.monitor.DataContainer;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.RPCDataContainer;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import java.util.HashMap;
import java.util.Map;

public class TransportContext {
    public static final byte BIZ_TYPE_DJG = 3;
    public static final byte BIZ_TYPE_H5 = 2;
    public static final byte BIZ_TYPE_LOG = 5;
    public static final byte BIZ_TYPE_NBNET_UP = 6;
    public static final byte BIZ_TYPE_RPC = 1;
    public static final byte BIZ_TYPE_RSRC = 4;
    public static final byte DEFAULT_LINK = 1;
    public static final byte SPDY_LINK = 2;
    private static final RPCDataContainer a = new EmptyDataContainer();
    public String api;
    private Map<String, String> b = new HashMap();
    public byte bizType = 1;
    public int choseExtLinkType = 1;
    public Context context;
    public SingleRPCReqConfig currentReqInfo;
    public Map<String, DataContainer> dcList = new HashMap();
    public DeviceTrafficStateInfo deviceTrafficStateInfo;
    public int logRandom;
    public String loggerLevel = null;
    public boolean mInitd = false;
    public int net0;
    public int net1;
    public String perfLog;
    public boolean printUrlToMonitorLog = true;
    public boolean reqGzip;
    public String routeInfo;
    public String rpcUUID;
    public long startExecutionTime = -1;
    public String targetSpi = null;
    public boolean testUser;
    public String url;

    class EmptyDataContainer extends RPCDataContainer {
        EmptyDataContainer() {
        }

        public void putDataItem(String k, String v) {
        }

        public String getDataItem(String k) {
            return null;
        }

        public void removeDataItem(String k) {
        }

        public void timeItemDot(String k) {
        }

        public void timeItemRelease(String k) {
        }
    }

    public class SingleRPCReqConfig {
        public String callUrl;
        public String protocol;
        public boolean use;
    }

    public String getNetType() {
        return this.net0 + "_" + this.net1;
    }

    public DataContainer getCurrentDataContainer() {
        if (this.currentReqInfo == null || !this.currentReqInfo.use) {
            return a;
        }
        DataContainer dc = this.dcList.get(this.currentReqInfo.protocol);
        if (dc != null) {
            return dc;
        }
        DataContainer dc2 = new RPCDataContainer();
        this.dcList.put(this.currentReqInfo.protocol, dc2);
        return dc2;
    }

    public boolean isInitd() {
        return this.mInitd;
    }

    public void setInitd(boolean initd) {
        this.mInitd = initd;
    }

    public void addExtInfo(String key, String value) {
        this.b.put(key, value);
    }

    public String getExtInfo(String key) {
        return this.b.get(key);
    }

    public boolean isRequestByMRPC() {
        if (this.currentReqInfo == null || !TextUtils.equals(this.currentReqInfo.protocol, ExtTransportStrategy.EXT_PROTO_MRPC)) {
            return false;
        }
        return true;
    }

    public boolean isRpcBizType() {
        return this.bizType == 1;
    }
}
