package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;

public class DjangoFileInfoResp {
    private String appKey;
    private int chunkNumber;
    private long createTime;
    private Map<String, Map<String, String>> ext = new HashMap(2);
    private String id;
    private String md5;
    private String name;
    private long size;
    private int status = 20;
    private short type;

    public Map<String, Map<String, String>> getExt() {
        return this.ext;
    }

    public void setExt(Map<String, Map<String, String>> ext2) {
        this.ext = ext2;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md52) {
        this.md5 = md52;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime2) {
        this.createTime = createTime2;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey2) {
        this.appKey = appKey2;
    }

    public short getType() {
        return this.type;
    }

    public void setType(short type2) {
        this.type = type2;
    }

    public int getChunkNumber() {
        return this.chunkNumber;
    }

    public void setChunkNumber(int chunkNumber2) {
        this.chunkNumber = chunkNumber2;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size2) {
        this.size = size2;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }
}
