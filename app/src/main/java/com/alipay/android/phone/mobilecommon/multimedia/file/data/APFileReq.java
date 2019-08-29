package com.alipay.android.phone.mobilecommon.multimedia.file.data;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.BaseInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;

public class APFileReq extends BaseInfo {
    public static final String FILE_TYPE_COMPRESS_IMAGE = "compress_image";
    public static final String FILE_TYPE_IMAGE = "image";
    public static final String KEY_FILE_KEY = "filekey";
    public static final String KEY_REFID = "refid";
    public static final String KEY_SSID = "ssid";
    String aliasFileName;
    Bundle bundle;
    String cacheId;
    boolean cacheWhileError = false;
    int callGroup = 1000;
    String cloudId;
    boolean encrypt = false;
    boolean isForceUrl = false;
    boolean isNeedCache = true;
    boolean isSync;
    int priority = 5;
    APRequestParam requestParam;
    String savePath;
    Boolean setPublic;
    String type;
    byte[] uploadData;
    private String uploadIdentifier;
    @Deprecated
    String url;

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle2) {
        this.bundle = bundle2;
    }

    public APRequestParam getRequestParam() {
        return this.requestParam;
    }

    public void setRequestParam(APRequestParam requestParam2) {
        this.requestParam = requestParam2;
    }

    public String getCacheId() {
        return this.cacheId;
    }

    public void setCacheId(String cacheId2) {
        this.cacheId = cacheId2;
    }

    public String getCloudId() {
        return this.cloudId;
    }

    public void setCloudId(String cloudId2) {
        this.cloudId = cloudId2;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(String savePath2) {
        this.savePath = savePath2;
    }

    public void setUploadIdentifier(String identifier) {
        this.uploadIdentifier = identifier;
    }

    public String getUploadIdentifier() {
        return this.uploadIdentifier;
    }

    public void setForceUrl(boolean forceUrl) {
        this.isForceUrl = forceUrl;
    }

    public boolean getForceUrl() {
        return this.isForceUrl;
    }

    @Deprecated
    public String getUrl() {
        return this.url;
    }

    @Deprecated
    public void setUrl(String url2) {
        this.url = url2;
        setCloudId(url2);
    }

    public boolean isSync() {
        return this.isSync;
    }

    public void setSync(boolean isSync2) {
        this.isSync = isSync2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public boolean isNeedCache() {
        return this.isNeedCache;
    }

    public void setIsNeedCache(boolean isNeedCache2) {
        this.isNeedCache = isNeedCache2;
    }

    public String getAliasFileName() {
        return this.aliasFileName;
    }

    public void setAliasFileName(String aliasFileName2) {
        this.aliasFileName = aliasFileName2;
    }

    public int getCallGroup() {
        return this.callGroup;
    }

    public void setCallGroup(int callGroup2) {
        this.callGroup = callGroup2;
        if (this.isNeedCache) {
            this.isNeedCache = callGroup2 == 1000;
        }
    }

    public boolean isCacheWhileError() {
        return this.cacheWhileError;
    }

    public void setCacheWhileError(boolean cacheWhileError2) {
        this.cacheWhileError = cacheWhileError2;
    }

    public void setPriority(int priority2) {
        if (priority2 > 0 && priority2 <= 10) {
            this.priority = priority2;
        }
    }

    public int getPriority() {
        return this.priority;
    }

    public Boolean getPublic() {
        return this.setPublic;
    }

    public APFileReq setPublic(Boolean setPublic2) {
        this.setPublic = setPublic2;
        return this;
    }

    public byte[] getUploadData() {
        return this.uploadData;
    }

    public void setUploadData(byte[] uploadData2) {
        this.uploadData = uploadData2;
    }

    public boolean isEncrypt() {
        return this.encrypt;
    }

    public void setEncrypt(boolean encrypt2) {
        this.encrypt = encrypt2;
    }

    public String toString() {
        return "APFileReq{requestParam=" + this.requestParam + ", cacheId='" + this.cacheId + '\'' + ", cloudId='" + this.cloudId + '\'' + ", savePath='" + this.savePath + '\'' + ", url='" + this.url + '\'' + ", isSync=" + this.isSync + ", type='" + this.type + '\'' + ", isNeedCache=" + this.isNeedCache + ", cacheWhileError=" + this.cacheWhileError + ", aliasFileName='" + this.aliasFileName + '\'' + ", callGroup=" + this.callGroup + ", businessId='" + this.businessId + '\'' + ", priority='" + this.priority + '\'' + ", uploadIdentifier='" + this.uploadIdentifier + '\'' + ", setPublic='" + this.setPublic + '\'' + ", uploadData='" + this.uploadData + '\'' + ", encrypt=" + this.encrypt + '\'' + ", super=" + super.toString() + '}';
    }
}
