package com.alipay.diskcache.model;

import android.text.TextUtils;

public class FileCacheModel {
    public static final String F_ALIAS_KEY = "alias_key";
    public static final String F_CACHE_ACCESS_TIME = "access_time";
    public static final String F_CACHE_BUSINESS_ID = "business_id";
    public static final String F_CACHE_EXPIRED_TIME = "expiredTime";
    public static final String F_CACHE_EXTRA = "extra";
    public static final String F_CACHE_FILE_SIZE = "file_size";
    public static final String F_CACHE_KEY = "key";
    public static final String F_CACHE_MODIFY_TIME = "modify_time";
    public static final String F_CACHE_PATH = "path";
    public static final String F_CACHE_TAG = "tag";
    public static final String F_CACHE_TYPE = "type";
    public static final String F_ID = "id";
    public static final String F_MULTI_ALIAS_KEY = "multi_alias_key";
    public static final String TABLE_FILE_CACHE = "tbl_file_cache";
    public static final int TAG_ALBUMVIDEO = 2;
    public static final int TAG_ALBUMVIDEO_THUMB = 8;
    public static final int TAG_AUDIO = 1024;
    public static final int TAG_BIG_PICTURE = 512;
    public static final int TAG_FILE = 2048;
    public static final int TAG_ILLEGAL_RES = 4096;
    public static final int TAG_LOCAL_RECORD_VIDEO = 32;
    public static final int TAG_LOCKED = 16;
    public static final int TAG_ORIGINAL_PICTURE = 128;
    public static final int TAG_SHORTVIDEO = 1;
    public static final int TAG_SHORTVIDEO_THUMB = 4;
    public static final int TAG_THUMB_PICTURE = 256;
    public static final int TAG_VIDEO_DELETEBYUSER = 64;
    public static final int TYPE_AUDIO = 3;
    public static final int TYPE_FILE = 4;
    public static final int TYPE_PICTURE = 1;
    public static final int TYPE_VIDEO = 2;
    public long accessTime;
    public String aliasKey;
    public String businessId;
    public String cacheKey;
    public long expiredTime = Long.MAX_VALUE;
    public String extra;
    public long fileSize;
    public int id = 0;
    public long modifyTime;
    public String multiAliasKeys;
    public String path;
    public int tag;
    public int type;

    public String[] splitMultiAliasKeys() {
        if (TextUtils.isEmpty(this.multiAliasKeys)) {
            return new String[0];
        }
        return this.multiAliasKeys.split(";");
    }

    public String toString() {
        return "FileCacheModel{id='" + this.id + '\'' + "key='" + this.cacheKey + '\'' + "alias_key=" + this.aliasKey + '\'' + ", Path='" + this.path + '\'' + ", FileSize=" + this.fileSize + ", modifyTime=" + this.modifyTime + ", accessTime=" + this.accessTime + ", BusinessId='" + this.businessId + '\'' + ", type='" + this.type + '\'' + ", tag='" + this.tag + '\'' + ", expiredTime='" + this.expiredTime + '\'' + ", Extra='" + this.extra + '\'' + ", multiAliasKeys='" + this.multiAliasKeys + '\'' + '}';
    }
}
