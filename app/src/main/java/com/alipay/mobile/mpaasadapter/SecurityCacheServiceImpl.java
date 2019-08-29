package com.alipay.mobile.mpaasadapter;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.StatFs;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import com.alipay.mobile.common.cache.disk.Entity;
import com.alipay.mobile.common.cache.disk.lru.LruDiskCache;
import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.utils.StringUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.common.GenericMemCacheService;
import com.alipay.mobile.framework.service.common.SecurityCacheService;
import com.alipay.mobile.framework.service.common.SecurityCacheService.Config;
import com.alipay.mobile.framework.service.common.SecurityCacheService.GetParams;
import com.alipay.mobile.framework.service.common.SecurityCacheService.SetParams;
import com.autonavi.ae.bl.map.IMapPageConstant;

public class SecurityCacheServiceImpl extends SecurityCacheService {
    private final String a = "SecurityCacheService";
    private MyLruDiskCache b = new PersistDiskCache(this, 0);
    private MyLruDiskCache c = new MyLruDiskCache(this, 0);
    private GenericMemCacheService d = ((GenericMemCacheService) this.e.findServiceByInterface(GenericMemCacheService.class.getName()));
    private MicroApplicationContext e = LauncherApplicationAgent.getInstance().getMicroApplicationContext();

    class MyLruDiskCache extends LruDiskCache {
        private MyLruDiskCache() {
        }

        /* synthetic */ MyLruDiskCache(SecurityCacheServiceImpl x0, byte b) {
            this();
        }

        public Entity getEntity(String url) {
            Entity entity;
            synchronized (this.mEntities) {
                entity = (Entity) this.mEntities.get(url);
            }
            return entity;
        }

        /* access modifiers changed from: protected */
        public void init() {
            super.init();
            a(a());
        }

        /* access modifiers changed from: protected */
        public String a() {
            String path = DeviceInfo.getInstance().getExternalCacheDir();
            if (TextUtils.isEmpty(path)) {
                return AppInfo.getInstance().getCacheDirPath();
            }
            return path;
        }

        private void a(String dir) {
            try {
                b(dir);
            } catch (Throwable e) {
                b(AppInfo.getInstance().getCacheDirPath());
                LogCatUtil.warn((String) "SecurityCacheService", e);
                LogCatUtil.warn((String) "SecurityCacheService", (String) "使用目录失败，回滚使用内部非持久化cache目录");
            }
        }

        private void b(String path) {
            c(path);
            setDirectory(path);
        }

        private void c(String path) {
            StatFs statFs = new StatFs(path);
            long canUseSize = (((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) - IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE;
            if (canUseSize <= 0) {
                canUseSize = 524288;
            }
            setMaxsize(canUseSize);
        }
    }

    class PersistDiskCache extends MyLruDiskCache {
        private PersistDiskCache() {
            super(SecurityCacheServiceImpl.this, 0);
        }

        /* synthetic */ PersistDiskCache(SecurityCacheServiceImpl x0, byte b2) {
            this();
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:7:0x003d  */
        /* JADX WARNING: Removed duplicated region for block: B:9:0x004d  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.String a() {
            /*
                r5 = this;
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                com.alipay.mobile.common.info.AppInfo r4 = com.alipay.mobile.common.info.AppInfo.getInstance()
                java.lang.String r4 = r4.getFilesDirPath()
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r4 = java.io.File.separator
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r4 = "securityCacheServiceStorage"
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r1 = r3.toString()
                r2 = 0
                java.io.File r0 = new java.io.File
                r0.<init>(r1)
                boolean r3 = r0.exists()
                if (r3 != 0) goto L_0x003a
                boolean r3 = r0.mkdirs()
                if (r3 == 0) goto L_0x0045
                java.lang.String r3 = "SecurityCacheService"
                java.lang.String r4 = "创建持久化SecurityCache缓存目录成功"
                com.alipay.mobile.common.transport.utils.LogCatUtil.info(r3, r4)
            L_0x003a:
                r2 = 1
            L_0x003b:
                if (r2 == 0) goto L_0x004d
                java.lang.String r3 = "SecurityCacheService"
                java.lang.String r4 = "使用持久化SecurityCache目录"
                com.alipay.mobile.common.transport.utils.LogCatUtil.info(r3, r4)
            L_0x0044:
                return r1
            L_0x0045:
                java.lang.String r3 = "SecurityCacheService"
                java.lang.String r4 = "创建持久化SecurityCache缓存目录失败"
                com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r3, r4)
                goto L_0x003b
            L_0x004d:
                com.alipay.mobile.common.info.AppInfo r3 = com.alipay.mobile.common.info.AppInfo.getInstance()
                java.lang.String r1 = r3.getCacheDirPath()
                goto L_0x0044
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.mpaasadapter.SecurityCacheServiceImpl.PersistDiskCache.a():java.lang.String");
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle arg0) {
    }

    public void set(String owner, String key, Object value) {
        SetParams params = new SetParams();
        params.owner = owner;
        params.key = key;
        params.value = value;
        params.period = 2592000;
        Config config = new Config();
        config.useInternalStorage = false;
        set(params, config);
    }

    public void set(String owner, String group, String key, Object value, long createTime, long period, String contentType) {
        set(owner, group, key, value, createTime, period, contentType, false);
    }

    public void set(String owner, String group, String key, Object value, long createTime, long period, String contentType, boolean isDynamicEncrypt) {
        SetParams params = new SetParams();
        params.owner = owner;
        params.group = group;
        params.key = key;
        params.value = value;
        params.period = period;
        Config config = new Config();
        config.useInternalStorage = false;
        config.isDynamicEncrypt = isDynamicEncrypt;
        set(params, config);
    }

    public String getString(String owner, String key) {
        return (String) get(owner, key, String.class);
    }

    public byte[] getBytes(String owner, String key) {
        return (byte[]) get(owner, key, byte[].class);
    }

    public <T> T get(String owner, String key, TypeReference<T> typeRef) {
        GetParams params = new GetParams();
        params.owner = owner;
        params.key = key;
        params.typeRef = typeRef;
        Config config = new Config();
        config.useInternalStorage = false;
        return get(params, config);
    }

    public <T> T get(String owner, String key, Class<T> clazz) {
        return get(owner, key, clazz, false);
    }

    public <T> T get(String owner, String key, Class<T> clazz, boolean isDynamicEncrypt) {
        GetParams params = new GetParams();
        params.owner = owner;
        params.key = key;
        params.clazz = clazz;
        Config config = new Config();
        config.useInternalStorage = false;
        config.isDynamicEncrypt = isDynamicEncrypt;
        return get(params, config);
    }

    public void remove(String key) {
        Config config = new Config();
        config.useInternalStorage = false;
        remove(key, config);
    }

    public void remove(String key, String target) {
        Config config = new Config();
        config.useInternalStorage = false;
        remove(key, target, config);
    }

    public void removeByGroup(String group, String target) {
        Config config = new Config();
        config.useInternalStorage = false;
        removeByGroup(group, target, config);
    }

    public void removeByOwner(String owner, String target) {
        Config config = new Config();
        config.useInternalStorage = false;
        removeByOwner(owner, target, config);
    }

    public String getString(String owner, String key, Config config) {
        GetParams params = new GetParams();
        params.owner = owner;
        params.key = key;
        params.clazz = String.class;
        return (String) get(params, config);
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T get(com.alipay.mobile.framework.service.common.SecurityCacheService.GetParams<T> r14, com.alipay.mobile.framework.service.common.SecurityCacheService.Config r15) {
        /*
            r13 = this;
            r12 = 0
            if (r15 != 0) goto L_0x0008
            com.alipay.mobile.framework.service.common.SecurityCacheService$Config r15 = new com.alipay.mobile.framework.service.common.SecurityCacheService$Config
            r15.<init>()
        L_0x0008:
            if (r14 == 0) goto L_0x0016
            java.lang.String r8 = r14.key
            if (r8 == 0) goto L_0x0016
            java.lang.Class<T> r8 = r14.clazz
            if (r8 != 0) goto L_0x001e
            com.alibaba.fastjson.TypeReference<T> r8 = r14.typeRef
            if (r8 != 0) goto L_0x001e
        L_0x0016:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "读缓存时必须传入非空params参数，且key和(class|typeRef)不能为空"
            r8.<init>(r9)
            throw r8
        L_0x001e:
            r5 = 0
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "SecurityCacheService"
            java.lang.String r10 = "get：%s, %s"
            r11 = 2
            java.lang.Object[] r11 = new java.lang.Object[r11]
            r11[r12] = r14
            r12 = 1
            r11[r12] = r15
            java.lang.String r10 = java.lang.String.format(r10, r11)
            r8.info(r9, r10)
            boolean r8 = r15.memCacheEnabled     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x0065
            com.alipay.mobile.framework.service.common.GenericMemCacheService r8 = r13.d     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r9 = r14.owner     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r10 = r14.key     // Catch:{ Exception -> 0x00e0 }
            java.lang.Object r3 = r8.get(r9, r10)     // Catch:{ Exception -> 0x00e0 }
            if (r3 == 0) goto L_0x00d8
            java.lang.String r8 = "SecurityCacheService"
            java.lang.String r9 = "获取到内存数据"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r9)     // Catch:{ Exception -> 0x00e0 }
            com.alibaba.fastjson.TypeReference<T> r8 = r14.typeRef     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x0052
        L_0x0051:
            return r3
        L_0x0052:
            java.lang.Class<T> r8 = r14.clazz     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x005e
            java.lang.Class<T> r8 = r14.clazz     // Catch:{ Exception -> 0x00e0 }
            boolean r8 = r8.isInstance(r3)     // Catch:{ Exception -> 0x00e0 }
            if (r8 != 0) goto L_0x0051
        L_0x005e:
            java.lang.String r8 = "SecurityCacheService"
            java.lang.String r9 = "内存数据类型不匹配"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r8, r9)     // Catch:{ Exception -> 0x00e0 }
        L_0x0065:
            boolean r8 = r15.diskCacheEnabled     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x00cc
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00e0 }
            byte[] r0 = r13.a(r14, r15)     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r8 = "SecurityCacheService"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r10 = "readDisk end, 耗时:"
            r9.<init>(r10)     // Catch:{ Exception -> 0x00e0 }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00e0 }
            long r10 = r10 - r6
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00e0 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r9)     // Catch:{ Exception -> 0x00e0 }
            if (r0 != 0) goto L_0x00ab
            boolean r8 = r15.useInternalStorage     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x00ab
            boolean r8 = r15.migrateToInternal     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x00ab
            com.alipay.mobile.framework.service.common.SecurityCacheService$Config r4 = r15.clone()     // Catch:{ Exception -> 0x00e0 }
            r8 = 0
            r4.useInternalStorage = r8     // Catch:{ Exception -> 0x00e0 }
            byte[] r0 = r13.a(r14, r4)     // Catch:{ Exception -> 0x00e0 }
            if (r0 == 0) goto L_0x00e8
            java.lang.String r8 = "SecurityCacheService"
            java.lang.String r9 = "从非持久存储中获取到数据"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r9)     // Catch:{ Exception -> 0x00e0 }
            r13.a(r0, r14, r15)     // Catch:{ Exception -> 0x00e0 }
        L_0x00ab:
            if (r0 == 0) goto L_0x00cc
            com.alibaba.fastjson.TypeReference<T> r8 = r14.typeRef     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x00f0
            java.lang.String r8 = new java.lang.String     // Catch:{ Exception -> 0x00e0 }
            r8.<init>(r0)     // Catch:{ Exception -> 0x00e0 }
            com.alibaba.fastjson.TypeReference<T> r9 = r14.typeRef     // Catch:{ Exception -> 0x00e0 }
            r10 = 0
            com.alibaba.fastjson.parser.Feature[] r10 = new com.alibaba.fastjson.parser.Feature[r10]     // Catch:{ Exception -> 0x00e0 }
            java.lang.Object r5 = com.alibaba.fastjson.JSON.parseObject(r8, r9, r10)     // Catch:{ Exception -> 0x00e0 }
        L_0x00bf:
            boolean r8 = r15.memCacheEnabled     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x00cc
            java.lang.String r8 = r14.owner     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r9 = ""
            java.lang.String r10 = r14.key     // Catch:{ Exception -> 0x00e0 }
            r13.a(r8, r9, r10, r5)     // Catch:{ Exception -> 0x00e0 }
        L_0x00cc:
            r3 = r5
        L_0x00cd:
            if (r3 != 0) goto L_0x0118
            java.lang.String r8 = "SecurityCacheService"
            java.lang.String r9 = "get:result=null"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r9)
            goto L_0x0051
        L_0x00d8:
            java.lang.String r8 = "SecurityCacheService"
            java.lang.String r9 = "内存无数据"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r9)     // Catch:{ Exception -> 0x00e0 }
            goto L_0x0065
        L_0x00e0:
            r1 = move-exception
            java.lang.String r8 = "SecurityCacheService"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r8, r1)
            r3 = r5
            goto L_0x00cd
        L_0x00e8:
            java.lang.String r8 = "SecurityCacheService"
            java.lang.String r9 = "从非持久存储中未获取到数据"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r9)     // Catch:{ Exception -> 0x00e0 }
            goto L_0x00ab
        L_0x00f0:
            java.lang.Class<T> r8 = r14.clazz     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x00bf
            java.lang.Class<T> r8 = r14.clazz     // Catch:{ Exception -> 0x00e0 }
            java.lang.Class<byte[]> r9 = byte[].class
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x00e0 }
            if (r8 == 0) goto L_0x0100
            r5 = r0
            goto L_0x00bf
        L_0x0100:
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x00e0 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x00e0 }
            java.lang.Class<T> r8 = r14.clazz     // Catch:{ Exception -> 0x00e0 }
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            boolean r8 = r8.equals(r9)     // Catch:{ Exception -> 0x00e0 }
            if (r8 != 0) goto L_0x0116
            java.lang.Class<T> r8 = r14.clazz     // Catch:{ Exception -> 0x00e0 }
            java.lang.Object r5 = com.alibaba.fastjson.JSON.parseObject(r2, r8)     // Catch:{ Exception -> 0x00e0 }
            goto L_0x00bf
        L_0x0116:
            r5 = r2
            goto L_0x00bf
        L_0x0118:
            java.lang.String r8 = "SecurityCacheService"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "get:result="
            r9.<init>(r10)
            java.lang.Class r10 = r3.getClass()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r9)
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.mpaasadapter.SecurityCacheServiceImpl.get(com.alipay.mobile.framework.service.common.SecurityCacheService$GetParams, com.alipay.mobile.framework.service.common.SecurityCacheService$Config):java.lang.Object");
    }

    public void set(String owner, String key, Object value, Config config) {
        SetParams params = new SetParams();
        params.owner = owner;
        params.key = key;
        params.value = value;
        set(params, config);
    }

    public void set(SetParams params, Config config) {
        String jsonStr;
        String key = params.key;
        Object value = params.value;
        String owner = params.owner;
        String group = params.group;
        if (config == null) {
            config = new Config();
        }
        LoggerFactory.getTraceLogger().warn((String) "SecurityCacheService", String.format("set：%s, %s", new Object[]{params, config}));
        if (StringUtils.isEmpty(key) || value == null) {
            LoggerFactory.getTraceLogger().warn((String) "SecurityCacheService", (String) "参数无效：key或value为空");
            return;
        }
        try {
            if (config.memCacheEnabled) {
                a(owner, group, key, value);
            }
            if (!(value instanceof String)) {
                jsonStr = JSON.toJSONString(value);
            } else {
                jsonStr = (String) value;
            }
            if (config.diskCacheEnabled) {
                a(params, jsonStr.getBytes(), config);
            }
        } catch (Exception e2) {
            LoggerFactory.getTraceLogger().warn((String) "SecurityCacheService", (Throwable) e2);
            LoggerFactory.getTraceLogger().warn((String) "SecurityCacheService", (String) "写缓存异常!");
        }
    }

    private byte[] a(GetParams<?> params, Config config) {
        String decrypted;
        String owner = params.owner;
        String key = params.key;
        boolean isDynamicEncrypt = config.isDynamicEncrypt;
        boolean encryptEnabled = config.encryptEnabled;
        byte[] result = null;
        try {
            a(config.useInternalStorage);
            byte[] data = a(owner, key, config.useInternalStorage);
            ContextWrapper context = a();
            if (!(!encryptEnabled || context == null || data == null)) {
                try {
                    String encrypted = new String(data);
                    if (isDynamicEncrypt) {
                        decrypted = TaobaoSecurityEncryptor.dynamicDecrypt(context, encrypted);
                    } else {
                        decrypted = TaobaoSecurityEncryptor.decrypt(context, encrypted);
                    }
                    if (!StringUtils.isEmpty(decrypted)) {
                        data = decrypted.getBytes();
                    } else {
                        data = null;
                        LoggerFactory.getTraceLogger().info("SecurityCacheService", "decrypt fail");
                    }
                } catch (Exception e2) {
                    LogCatUtil.warn((String) "SecurityCacheService", (Throwable) e2);
                }
            }
            if (data != null) {
                LoggerFactory.getTraceLogger().info("SecurityCacheService", "readRealBytesFromDisk:success, data size=" + data.length);
                result = data;
            } else {
                LoggerFactory.getTraceLogger().info("SecurityCacheService", "readRealBytesFromDisk:fail, data=null");
            }
        } catch (Exception e3) {
            LogCatUtil.warn((String) "SecurityCacheService", (Throwable) e3);
        } finally {
            b(config.useInternalStorage);
        }
        return result;
    }

    private byte[] a(String owner, String key, boolean useInternalStorage) {
        return c(useInternalStorage).get(owner, key);
    }

    private void a(SetParams params, byte[] data, Config config) {
        byte[] encrypted;
        boolean isOpened = false;
        try {
            ContextWrapper context = a();
            if (!(!config.encryptEnabled || context == null || data == null)) {
                if (config.isDynamicEncrypt) {
                    encrypted = TaobaoSecurityEncryptor.dynamicEncrypt(context, data);
                } else {
                    encrypted = TaobaoSecurityEncryptor.encrypt(context, data);
                }
                data = encrypted;
                LoggerFactory.getTraceLogger().info("SecurityCacheService", encrypted == null ? "encrypt fail" : "encrypt success");
            }
            if (data != null) {
                a(config.useInternalStorage);
                isOpened = true;
                long ct = System.currentTimeMillis();
                c(config.useInternalStorage).put(params.owner, params.group, params.key, data, ct, params.period, params.contentType);
                LoggerFactory.getTraceLogger().info("SecurityCacheService", String.format("writeToDisk end, 耗时: %d ms", new Object[]{Long.valueOf(System.currentTimeMillis() - ct)}));
            }
            if (isOpened) {
                b(config.useInternalStorage);
            }
        } catch (Exception ex) {
            LogCatUtil.warn((String) "SecurityCacheService", (Throwable) ex);
            if (0 != 0) {
                b(config.useInternalStorage);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                b(config.useInternalStorage);
            }
            throw th;
        }
    }

    public void remove(String key, Config config) {
        remove(key, "ALL", config);
    }

    public void remove(String key, String target, Config config) {
        if ((TextUtils.equals(target, "MEM") || TextUtils.equals(target, "ALL")) && config.memCacheEnabled) {
            this.d.remove(key);
        }
        if ((TextUtils.equals(target, "DISK") || TextUtils.equals(target, "ALL")) && config.diskCacheEnabled) {
            a(config.useInternalStorage, key, (String) "key");
            if (config.useInternalStorage && config.migrateToInternal) {
                a(false, key, (String) "key");
            }
        }
    }

    public void removeByOwner(String owner, String target, Config config) {
        if ((TextUtils.equals(target, "MEM") || TextUtils.equals(target, "ALL")) && config.memCacheEnabled) {
            this.d.removeByOwner(owner);
        }
        if ((TextUtils.equals(target, "DISK") || TextUtils.equals(target, "ALL")) && config.diskCacheEnabled) {
            a(config.useInternalStorage, owner, (String) "owner");
            if (config.useInternalStorage && config.migrateToInternal) {
                a(false, owner, (String) "owner");
            }
        }
    }

    public void removeByGroup(String group, String target, Config config) {
        if ((TextUtils.equals(target, "MEM") || TextUtils.equals(target, "ALL")) && config.memCacheEnabled) {
            this.d.removeByGroup(group);
        }
        if ((TextUtils.equals(target, "DISK") || TextUtils.equals(target, "ALL")) && config.diskCacheEnabled) {
            a(config.useInternalStorage, group, (String) "group");
            if (config.useInternalStorage && config.migrateToInternal) {
                a(false, group, (String) "group");
            }
        }
    }

    private static ContextWrapper a() {
        return LauncherApplicationAgent.getInstance().getApplicationContext();
    }

    private void a(String owner, String group, String key, Object value) {
        this.d.put(owner, group, key, value);
    }

    private void a(boolean isPersist) {
        c(isPersist).open();
    }

    private void b(boolean isPersist) {
        c(isPersist).close();
    }

    private void a(boolean isPersist, String key, String keyType) {
        MyLruDiskCache diskCache = c(isPersist);
        diskCache.open();
        if (TextUtils.equals(keyType, "owner")) {
            diskCache.removeByOwner(key);
        } else if (TextUtils.equals(keyType, "group")) {
            diskCache.removeByGroup(key);
        } else {
            diskCache.remove(key);
        }
    }

    private void a(byte[] data, GetParams<?> params, Config config) {
        Entity entity = c(false).getEntity(params.key);
        if (entity == null) {
            LogCatUtil.info("SecurityCacheService", "migrate fail：entity is empty");
            return;
        }
        SetParams sp = new SetParams();
        sp.owner = params.owner;
        sp.key = params.key;
        sp.group = entity.getGroup();
        long leftPeriod = ((entity.getCreateTime() + (entity.getPeriod() * 1000)) - System.currentTimeMillis()) / 1000;
        if (leftPeriod <= 0) {
            LogCatUtil.info("SecurityCacheService", String.format("migrate fail：data has expired: %s, %s", new Object[]{Long.valueOf(entity.getPeriod()), Long.valueOf(entity.getCreateTime())}));
            return;
        }
        sp.period = leftPeriod;
        sp.contentType = entity.getContentType();
        a(sp, data, config);
        LogCatUtil.info("SecurityCacheService", String.format("migrate success：new period=%s", new Object[]{Long.valueOf(leftPeriod)}));
    }

    private MyLruDiskCache c(boolean isPersist) {
        if (isPersist) {
            return this.b;
        }
        return this.c;
    }
}
