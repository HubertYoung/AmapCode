package com.alipay.mobile.beehive.util;

import android.content.ContextWrapper;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.common.DiskCacheService;
import com.alipay.mobile.framework.service.common.GenericMemCacheService;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigManager;

public class SecurityCacheManager {
    private static final String CONTENT_TYPE_TXT = "txt";
    private static SecurityCacheManager instance;
    private final String TAG = "SecurityCacheManager";
    private boolean diskCacheEnabled = true;
    private boolean encryptEnabled = true;
    private DiskCacheService mDiskCacheService;
    private GenericMemCacheService mMemCacheService;
    private boolean memCacheEnabled = true;
    private final int validTime = H5NebulaAppConfigManager.DEFAULT_OUT_DATE_SECOND;

    public static synchronized SecurityCacheManager getInstance() {
        SecurityCacheManager securityCacheManager;
        synchronized (SecurityCacheManager.class) {
            try {
                if (instance == null) {
                    instance = new SecurityCacheManager();
                }
                securityCacheManager = instance;
            }
        }
        return securityCacheManager;
    }

    private SecurityCacheManager() {
        MicroApplicationContext microAppContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        this.mDiskCacheService = (DiskCacheService) microAppContext.findServiceByInterface(DiskCacheService.class.getName());
        this.mMemCacheService = (GenericMemCacheService) microAppContext.findServiceByInterface(GenericMemCacheService.class.getName());
    }

    public void set(String owner, String key, Object value) {
        set(owner, null, key, value, System.currentTimeMillis(), 2592000, "txt");
    }

    public void set(String owner, String group, String key, Object value, long createTime, long period, String contentType) {
        set(owner, group, key, value, createTime, period, contentType, false);
    }

    public void set(String owner, String group, String key, Object value, long createTime, long period, String contentType, boolean isDynamicEncrypt) {
        String jsonStr;
        if (!TextUtils.isEmpty(key) && value != null) {
            boolean diskOpened = false;
            try {
                if (this.memCacheEnabled) {
                    setMemCache(owner, group, key, value);
                }
                if (!(value instanceof String)) {
                    jsonStr = JSON.toJSONString(value);
                } else {
                    jsonStr = (String) value;
                }
                if (this.diskCacheEnabled) {
                    byte[] data = jsonStr.getBytes();
                    open();
                    diskOpened = true;
                    setDiskCache(owner, group, key, data, createTime, period, contentType, isDynamicEncrypt);
                }
                if (diskOpened) {
                    close();
                }
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().warn((String) "SecurityCacheManager", (Throwable) e);
                if (0 != 0) {
                    close();
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    close();
                }
                throw th;
            }
        }
    }

    public String getString(String owner, String key) {
        return (String) get(owner, key, String.class);
    }

    public byte[] getBytes(String owner, String key) {
        return (byte[]) get(owner, key, byte[].class);
    }

    public <T> T get(String owner, String key, TypeReference<T> typeRef) {
        Object result = null;
        try {
            if (this.memCacheEnabled) {
                Object memValue = this.mMemCacheService.get(owner, key);
                if (memValue != null) {
                    return memValue;
                }
            }
            if (this.diskCacheEnabled) {
                byte[] data = getDiskBytes(owner, key, false);
                if (data != null) {
                    result = JSON.parseObject(new String(data), typeRef, new Feature[0]);
                }
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) "SecurityCacheManager", (Throwable) e);
        }
        return result;
    }

    public <T> T get(String owner, String key, Class<T> clazz) {
        return get(owner, key, clazz, false);
    }

    public <T> T get(String owner, String key, Class<T> clazz, boolean isDynamicEncrypt) {
        try {
            if (this.memCacheEnabled) {
                Object memValue = this.mMemCacheService.get(owner, key);
                if (memValue != null && clazz.isInstance(memValue)) {
                    return memValue;
                }
            }
            if (this.diskCacheEnabled) {
                byte[] data = getDiskBytes(owner, key, isDynamicEncrypt);
                if (data != 0) {
                    if (clazz.equals(byte[].class)) {
                        return data;
                    }
                    String jsonStr = new String(data);
                    return !clazz.equals(String.class) ? JSON.parseObject(jsonStr, clazz) : jsonStr;
                }
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) "SecurityCacheManager", (Throwable) e);
        }
        return null;
    }

    private byte[] getDiskBytes(String owner, String key, boolean isDynamicEncrypt) {
        String decrypted;
        byte[] result = null;
        try {
            open();
            byte[] data = this.mDiskCacheService.get(owner, key);
            ContextWrapper context = getEncryptContext();
            if (!(!this.encryptEnabled || context == null || data == null)) {
                try {
                    String encrypted = new String(data);
                    LoggerFactory.getTraceLogger().info("SecurityCacheManager", "isDynamicEncrypt " + isDynamicEncrypt);
                    if (isDynamicEncrypt) {
                        decrypted = TaobaoSecurityEncryptor.dynamicDecrypt(context, encrypted);
                    } else {
                        decrypted = TaobaoSecurityEncryptor.decrypt(context, encrypted);
                    }
                    if (!TextUtils.isEmpty(decrypted)) {
                        data = decrypted.getBytes();
                    } else {
                        data = null;
                        LoggerFactory.getTraceLogger().info("SecurityCacheManager", "decrypt fail");
                    }
                } catch (Exception e) {
                    LoggerFactory.getTraceLogger().warn((String) "SecurityCacheManager", (Throwable) e);
                }
            }
            if (data != null) {
                result = data;
            }
        } catch (Exception e2) {
            LoggerFactory.getTraceLogger().warn((String) "SecurityCacheManager", (Throwable) e2);
        } finally {
            close();
        }
        return result;
    }

    public void remove(String key) {
        remove(key, "ALL");
    }

    public void remove(String key, String target) {
        if ((TextUtils.equals(target, "MEM") || TextUtils.equals(target, "ALL")) && this.memCacheEnabled) {
            this.mMemCacheService.remove(key);
        }
        if ((TextUtils.equals(target, "DISK") || TextUtils.equals(target, "ALL")) && this.diskCacheEnabled) {
            this.mDiskCacheService.remove(key);
        }
    }

    public void removeByGroup(String group, String target) {
        if ((TextUtils.equals(target, "MEM") || TextUtils.equals(target, "ALL")) && this.memCacheEnabled) {
            this.mMemCacheService.removeByGroup(group);
        }
        if ((TextUtils.equals(target, "DISK") || TextUtils.equals(target, "ALL")) && this.diskCacheEnabled) {
            this.mDiskCacheService.removeByGroup(group);
        }
    }

    private void setDiskCache(String owner, String group, String key, byte[] data, long createTime, long period, String contentType, boolean isDynamicEncrypt) {
        byte[] encryped;
        try {
            ContextWrapper context = getEncryptContext();
            if (!(!this.encryptEnabled || context == null || data == null)) {
                LoggerFactory.getTraceLogger().info("SecurityCacheManager", "isDynamicEncrypt " + isDynamicEncrypt);
                if (isDynamicEncrypt) {
                    encryped = TaobaoSecurityEncryptor.dynamicEncrypt(context, data);
                } else {
                    encryped = TaobaoSecurityEncryptor.encrypt(context, data);
                }
                data = encryped;
                if (encryped == null) {
                    LoggerFactory.getTraceLogger().info("SecurityCacheManager", "encrypt fail");
                }
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) "SecurityCacheManager", (Throwable) e);
        }
        if (data != null) {
            this.mDiskCacheService.put(owner, group, key, data, createTime, period, contentType);
        }
    }

    private ContextWrapper getEncryptContext() {
        return LauncherApplicationAgent.getInstance().getApplicationContext();
    }

    private void setMemCache(String owner, String group, String key, Object value) {
        this.mMemCacheService.put(owner, group, key, value);
    }

    private void open() {
        this.mDiskCacheService.open();
    }

    private void close() {
        this.mDiskCacheService.close();
    }
}
