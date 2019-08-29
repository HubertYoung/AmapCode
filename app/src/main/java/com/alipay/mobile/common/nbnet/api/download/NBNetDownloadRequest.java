package com.alipay.mobile.common.nbnet.api.download;

import com.alipay.mobile.common.nbnet.api.NBNetFactory;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPCmdType;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPExtraData;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPResType;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPSourceType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public class NBNetDownloadRequest {
    private MMDPSourceType a = MMDPSourceType.FILEID;
    private MMDPResType b = MMDPResType.FILE;
    private String c;
    private String d;
    private Object e;
    private String f;
    private List<MMDPExtraData> g;
    private MMDPCmdType h;
    private byte[] i;
    private String j;
    private int k = 1;
    private int l = -1;
    private boolean m = false;
    private Map<String, String> n;

    public Future<NBNetDownloadResponse> download(NBNetDownloadCallback callback) {
        return NBNetFactory.getDefault().getDownloadClient().requestDownload(this, callback);
    }

    public MMDPResType getResType() {
        return this.b;
    }

    public void setResType(MMDPResType resType) {
        this.b = resType;
    }

    public int getRequestId() {
        int result = 1;
        if (this.c != null) {
            result = this.c.hashCode() + 1;
        }
        if (this.i == null || this.i.length <= 0) {
            return result;
        }
        return result + Arrays.hashCode(this.i);
    }

    public String toString() {
        return "requestId=" + getRequestId() + ", fileId=" + this.c + ", savePath=" + this.d + ", hashcode=" + hashCode();
    }

    public MMDPSourceType getSourceType() {
        return this.a;
    }

    public void setSourceType(MMDPSourceType sourceType) {
        this.a = sourceType;
    }

    public String getFileId() {
        return this.c;
    }

    public void setFileId(String fileId) {
        this.c = fileId;
    }

    public String getSavePath() {
        return this.d;
    }

    public void setSavePath(String savePath) {
        this.d = savePath;
    }

    public Object getTag() {
        return this.e;
    }

    public void setTag(Object tag) {
        this.e = tag;
    }

    public String getBizType() {
        return this.f;
    }

    public void setBizType(String bizType) {
        this.f = bizType;
    }

    public List<MMDPExtraData> getExtList() {
        return this.g;
    }

    public void setExtList(List<MMDPExtraData> extList) {
        this.g = extList;
    }

    public MMDPCmdType getCmdType() {
        return this.h;
    }

    public void setCmdType(MMDPCmdType cmdType) {
        this.h = cmdType;
    }

    public byte[] getBizParams() {
        return this.i;
    }

    public void setBizParams(byte[] bizParams) {
        this.i = bizParams;
    }

    public String getSessionId() {
        return this.j;
    }

    public void setSessionId(String sessionId) {
        this.j = sessionId;
    }

    public String getTraceId() {
        StringBuilder append = new StringBuilder().append(this.j).append("_");
        int i2 = this.k;
        this.k = i2 + 1;
        return append.append(i2).toString();
    }

    public void setExtInfo(String key, String value) {
        if (this.n == null) {
            this.n = new HashMap(1);
        }
        this.n.put(key, value);
    }

    public String getExtInfo(String key) {
        if (this.n == null) {
            return "";
        }
        return this.n.get(key);
    }

    public void setReqTimeOut(int reqTimeOut) {
        if (reqTimeOut > 0) {
            this.l = reqTimeOut;
        }
    }

    public int getReqTimeOut() {
        return this.l;
    }

    public boolean isCancel() {
        return this.m;
    }

    public void setCancel(boolean cancel) {
        this.m = cancel;
    }

    public Map<String, String> getExtMap() {
        return this.n;
    }
}
