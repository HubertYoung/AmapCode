package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DjangoConf {
    @JSONField(name = "checkHost")
    public int checkHosts = 0;
    @JSONField(name = "css")
    public int enableCalcScaleSize = 1;
    @JSONField(name = "inHosts")
    public String errCodeInHosts = "alipay.up.django.t.taobao.com;alipay.dl.django.t.taobao.com;oalipay-dl-django.alicdn.com;up-mayi.django.t.taobao.com";
    @JSONField(name = "icp")
    public int imgCutPreSet = 0;
    @JSONField(name = "localRapidMaxCount")
    public long localRapidMaxCount = 5000;
    @JSONField(name = "localRapidTrimCount")
    public long localRapidTrimCount = 1000;
    @JSONField(name = "mcfs")
    public long maxCopyFileSize = 204800;
    @JSONField(name = "morh")
    public int maxOssRequestHeight = 4096;
    @JSONField(name = "morw")
    public int maxOssRequestWidth = 4096;
    @JSONField(name = "mota")
    public int maxOssTargetArea = 16777216;
    @JSONField(name = "mots")
    public int maxOssTargetSide = 16384;
    @JSONField(name = "errCodes")
    public String refreshTokenErrorCodes = "403;404;";
    @JSONField(name = "tokenARI")
    public long tokenAutoRefreshInterval = 120;
    @JSONField(name = "tokenFRI")
    public long tokenForceRefreshInterval = 5;
    @JSONField(name = "tps")
    public int tokenPoolSize = 5;
    @JSONField(name = "djPool")
    public int useDjangoTokenPool = 1;
    @JSONField(name = "useLocalRapidUpload")
    public int useLocalRapidUpload = 1;

    public static class RefreshTokenErrorCode {
        public String header;
        public String headerValue;
        public int httpCode;

        public String toString() {
            return "RefreshTokenErrorCode{httpCode=" + this.httpCode + ", header='" + this.header + '\'' + ", headerValue='" + this.headerValue + '\'' + '}';
        }
    }

    public boolean isImgCutPreSet() {
        return this.imgCutPreSet == 1;
    }

    public Map<Integer, RefreshTokenErrorCode> refreshTokenErrorCodeMap() {
        Map map = new HashMap();
        if (!TextUtils.isEmpty(this.refreshTokenErrorCodes)) {
            for (String split : this.refreshTokenErrorCodes.split(";")) {
                String[] parts = split.split(":");
                RefreshTokenErrorCode code = new RefreshTokenErrorCode();
                if (parts.length > 0) {
                    code.httpCode = Integer.parseInt(parts[0]);
                }
                if (parts.length >= 2) {
                    code.header = parts[1];
                }
                if (parts.length >= 3) {
                    code.headerValue = parts[2];
                }
                map.put(Integer.valueOf(code.httpCode), code);
            }
        }
        return map;
    }

    public boolean isUseDjangoTokenPool() {
        return this.useDjangoTokenPool == 1;
    }

    public boolean isCheckHosts() {
        return this.checkHosts == 1;
    }

    public List<String> errorInHosts() {
        List hosts = new ArrayList();
        Collections.addAll(hosts, this.errCodeInHosts == null ? new String[0] : this.errCodeInHosts.split(";"));
        return hosts;
    }

    public String toString() {
        return "DjangoConf{useDjangoTokenPool=" + this.useDjangoTokenPool + ", tokenAutoRefreshInterval=" + this.tokenAutoRefreshInterval + ", tokenForceRefreshInterval=" + this.tokenForceRefreshInterval + ", tokenPoolSize=" + this.tokenPoolSize + ", refreshTokenErrorCodes='" + this.refreshTokenErrorCodes + '\'' + ", checkHosts=" + this.checkHosts + ", errCodeInHosts='" + this.errCodeInHosts + '\'' + ", useLocalRapidUpload=" + this.useLocalRapidUpload + ", localRapidMaxCount=" + this.localRapidMaxCount + ", localRapidTrimCount=" + this.localRapidTrimCount + ", imgCutPreSet=" + this.imgCutPreSet + ", maxOssRequestWidth=" + this.maxOssRequestWidth + ", maxOssRequestHeight=" + this.maxOssRequestHeight + ", maxOssTargetSide=" + this.maxOssTargetSide + ", maxOssTargetArea=" + this.maxOssTargetArea + ", enableCalcScaleSize=" + this.enableCalcScaleSize + ", maxCopyFileSize=" + this.maxCopyFileSize + '}';
    }
}
