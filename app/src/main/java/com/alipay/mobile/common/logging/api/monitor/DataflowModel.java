package com.alipay.mobile.common.logging.api.monitor;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;

public class DataflowModel implements Cloneable {
    private boolean a;
    public String appId;
    private int b = 2;
    public String bizId;
    public String bundle;
    public String diagnose;
    public Map<String, String> extParams;
    public String fileId;
    @Deprecated
    public String host;
    public boolean isUpload;
    public Map<String, String> params;
    public Header[] reqHeaders;
    public long reqSize;
    public Header[] respHeaders;
    public long respSize;
    public DataflowID type;
    public String url;

    @Deprecated
    public static DataflowModel obtain(String url2, long reqSize2, long respSize2, DataflowID type2, String bundle2, String diagnose2) {
        return obtain(type2, url2, reqSize2, respSize2, diagnose2);
    }

    public static DataflowModel obtain(DataflowID type2, String url2, long reqSize2, long respSize2, String diagnose2) {
        DataflowModel dataflowModel = a();
        dataflowModel.recycle();
        dataflowModel.type = type2;
        dataflowModel.url = url2;
        dataflowModel.reqSize = reqSize2;
        dataflowModel.respSize = respSize2;
        dataflowModel.diagnose = diagnose2;
        dataflowModel.a = true;
        return dataflowModel;
    }

    private static DataflowModel a() {
        return new DataflowModel();
    }

    private DataflowModel() {
    }

    public DataflowModel cloneMirror() {
        DataflowModel mirror = obtain(this.type, this.url, this.reqSize, this.respSize, this.diagnose);
        mirror.bundle = this.bundle;
        mirror.appId = this.appId;
        mirror.isUpload = this.isUpload;
        mirror.fileId = this.fileId;
        mirror.bizId = this.bizId;
        mirror.reqHeaders = this.reqHeaders;
        mirror.respHeaders = this.respHeaders;
        mirror.extParams = this.extParams;
        mirror.host = this.host;
        mirror.a = this.a;
        if (this.params != null) {
            mirror.params = new HashMap();
            for (Entry entry : this.params.entrySet()) {
                mirror.params.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
        return mirror;
    }

    public void recycle() {
        this.a = false;
        this.type = DataflowID.UNKNOWN;
        this.url = null;
        this.reqSize = 0;
        this.respSize = 0;
        this.bundle = null;
        this.appId = null;
        this.diagnose = null;
        this.isUpload = false;
        this.fileId = null;
        this.bizId = null;
        this.reqHeaders = null;
        this.respHeaders = null;
        this.params = null;
        this.extParams = null;
        this.host = null;
    }

    public boolean isInUse() {
        return this.a;
    }

    public void report() {
        LoggerFactory.getMonitorLogger().dataflow(this);
    }

    public String getParam(String key) {
        if (this.params == null) {
            return null;
        }
        return this.params.get(key);
    }

    public DataflowModel putParam(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap();
        }
        this.params.put(key, value);
        return this;
    }

    public DataflowModel removeParam(String key) {
        if (this.params != null) {
            this.params.remove(key);
        }
        return this;
    }

    public int getLoggerLevel() {
        return this.b;
    }

    public void setLoggerLevel(int loggerLevel) {
        this.b = loggerLevel;
    }
}
