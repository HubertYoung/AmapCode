package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoItem {
    @JSONField(name = "cloudId")
    public String cloudId;
    @JSONField(name = "md5")
    public String md5;
    @JSONField(name = "models")
    public List<String> models = new ArrayList();
    @JSONField(name = "ref_libs")
    public String refLibs;
    @JSONField(name = "size")
    public long size;
    @JSONField(deserialize = false, serialize = false)
    private Map<String, String> validMap;
    @JSONField(name = "valids")
    public String valids;

    public String[] refLibs() {
        String[] libs = new String[0];
        if (!TextUtils.isEmpty(this.refLibs)) {
            return this.refLibs.split(";");
        }
        return libs;
    }

    public Map<String, String> validMap() {
        if (this.validMap == null && !TextUtils.isEmpty(this.valids)) {
            this.validMap = new HashMap();
            for (String split : this.valids.split(";")) {
                String[] entry = split.split(":");
                this.validMap.put(entry[0], entry[1]);
            }
        }
        return this.validMap;
    }

    public String toString() {
        return "SoItem{size=" + this.size + ", valids='" + this.valids + '\'' + ", cloudId='" + this.cloudId + '\'' + ", md5='" + this.md5 + '\'' + ", refLibs='" + this.refLibs + '\'' + ", models=" + this.models + '}';
    }
}
