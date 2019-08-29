package com.alipay.mobile.framework.service.common;

import com.alibaba.fastjson.TypeReference;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class SecurityCacheService extends ExternalService {
    public static final String CONTENT_TYPE_TEXT = "text";
    public static final String DEFAULT_CONTENT_TYPE = "txt";
    public static final long DEFAULT_PERIOD = 2592000;

    public class Config {
        public boolean diskCacheEnabled = true;
        public boolean encryptEnabled = true;
        public boolean isDynamicEncrypt = false;
        public boolean memCacheEnabled = true;
        public boolean migrateToInternal = false;
        public boolean useInternalStorage = false;

        public String toString() {
            return String.format("Config{useInternalStorage=%s, memCache=%s,diskCache=%s, encrypt=%s, dynamicEncrypt=%s}", new Object[]{Boolean.valueOf(this.useInternalStorage), Boolean.valueOf(this.memCacheEnabled), Boolean.valueOf(this.diskCacheEnabled), Boolean.valueOf(this.encryptEnabled), Boolean.valueOf(this.isDynamicEncrypt)});
        }

        public Config clone() {
            Config c = new Config();
            c.useInternalStorage = this.useInternalStorage;
            c.migrateToInternal = this.migrateToInternal;
            c.memCacheEnabled = this.memCacheEnabled;
            c.diskCacheEnabled = this.diskCacheEnabled;
            c.isDynamicEncrypt = this.isDynamicEncrypt;
            return this;
        }
    }

    public class GetParams<T> {
        public Class<T> clazz;
        public String key;
        public String owner;
        public TypeReference<T> typeRef;

        public String toString() {
            return String.format("GetParams{owner=%s, key=%s, typeRef=%s, clazz=%s}", new Object[]{this.owner, this.key, this.typeRef, this.clazz});
        }
    }

    public class SetParams {
        public String contentType = SecurityCacheService.DEFAULT_CONTENT_TYPE;
        public String group;
        public String key;
        public String owner;
        public long period = 2592000;
        public Object value;

        public String toString() {
            return String.format("SetParams{owner=%s,key=%s,value=%s, group=%s, period=%s}", new Object[]{this.owner, this.key, this.value == null ? "[null]" : this.value.getClass().toString(), this.group, Long.valueOf(this.period)});
        }
    }

    public abstract <T> T get(GetParams<T> getParams, Config config);

    public abstract <T> T get(String str, String str2, TypeReference<T> typeReference);

    public abstract <T> T get(String str, String str2, Class<T> cls);

    public abstract <T> T get(String str, String str2, Class<T> cls, boolean z);

    public abstract byte[] getBytes(String str, String str2);

    public abstract String getString(String str, String str2);

    public abstract String getString(String str, String str2, Config config);

    public abstract void remove(String str);

    public abstract void remove(String str, Config config);

    public abstract void remove(String str, String str2);

    public abstract void remove(String str, String str2, Config config);

    public abstract void removeByGroup(String str, String str2);

    public abstract void removeByOwner(String str, String str2);

    public abstract void removeByOwner(String str, String str2, Config config);

    public abstract void set(SetParams setParams, Config config);

    public abstract void set(String str, String str2, Object obj);

    public abstract void set(String str, String str2, Object obj, Config config);

    public abstract void set(String str, String str2, String str3, Object obj, long j, long j2, String str4);

    public abstract void set(String str, String str2, String str3, Object obj, long j, long j2, String str4, boolean z);
}
