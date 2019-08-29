package com.alipay.mobile.common.cache.disk;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.task.AsyncTaskExecutor;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DiskCache {
    /* access modifiers changed from: private */
    public AtomicBoolean a = new AtomicBoolean();
    private AtomicBoolean b = new AtomicBoolean();
    /* access modifiers changed from: private */
    public AtomicBoolean c = new AtomicBoolean(false);
    protected String mDirectory;
    protected HashMap<String, Entity> mEntities;
    protected HashMap<String, Set<Entity>> mGroup;
    protected long mMaxsize;
    protected Meta mMeta;
    protected long mSize;

    /* access modifiers changed from: protected */
    public abstract void init();

    protected DiskCache() {
        this.a.set(false);
        this.b.set(false);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void open() {
        if (this.b.get()) {
            LoggerFactory.getTraceLogger().warn((String) "DiskCache", (String) "DiskCache has inited");
            return;
        }
        init();
        this.mMeta = new Meta(this);
        this.mMeta.init();
        this.b.set(true);
    }

    public void close() {
        a();
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048 A[Catch:{ IOException -> 0x004c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void putSerializable(java.lang.String r18, java.lang.String r19, java.lang.String r20, java.io.Serializable r21, long r22, long r24, java.lang.String r26) {
        /*
            r17 = this;
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            r14 = 0
            java.io.ObjectOutputStream r15 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x003b }
            r15.<init>(r2)     // Catch:{ IOException -> 0x003b }
            r0 = r21
            r15.writeObject(r0)     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            byte[] r7 = r2.toByteArray()     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            r3 = r17
            r4 = r18
            r5 = r19
            r6 = r20
            r8 = r22
            r10 = r24
            r12 = r26
            r3.put(r4, r5, r6, r7, r8, r10, r12)     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            r2.close()     // Catch:{ IOException -> 0x002c }
            r15.close()     // Catch:{ IOException -> 0x002c }
        L_0x002b:
            return
        L_0x002c:
            r13 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "DiskCache"
            java.lang.String r5 = java.lang.String.valueOf(r13)
            r3.error(r4, r5)
            goto L_0x002b
        L_0x003b:
            r13 = move-exception
        L_0x003c:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ all -> 0x0042 }
            r3.<init>(r13)     // Catch:{ all -> 0x0042 }
            throw r3     // Catch:{ all -> 0x0042 }
        L_0x0042:
            r3 = move-exception
        L_0x0043:
            r2.close()     // Catch:{ IOException -> 0x004c }
            if (r14 == 0) goto L_0x004b
            r14.close()     // Catch:{ IOException -> 0x004c }
        L_0x004b:
            throw r3
        L_0x004c:
            r13 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "DiskCache"
            java.lang.String r6 = java.lang.String.valueOf(r13)
            r4.error(r5, r6)
            goto L_0x004b
        L_0x005b:
            r3 = move-exception
            r14 = r15
            goto L_0x0043
        L_0x005e:
            r13 = move-exception
            r14 = r15
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.cache.disk.DiskCache.putSerializable(java.lang.String, java.lang.String, java.lang.String, java.io.Serializable, long, long, java.lang.String):void");
    }

    public void put(String owner, String group, String url, byte[] data, long createTime, long period, String contentType) {
        if (!this.b.get()) {
            throw new RuntimeException("DiskCache must call open() before");
        } else if (owner != null && owner.equalsIgnoreCase("-")) {
            throw new RuntimeException("owner can't be -");
        } else if (group == null || !group.equalsIgnoreCase("-")) {
            final String key = obtainKey(url);
            addEntity(new Entity(owner, group, url, 0, (long) data.length, key, createTime, period, contentType));
            final byte[] bArr = data;
            AsyncTaskExecutor.getInstance().executeSerially(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    try {
                        DiskCache.a(DiskCache.this.getDirectory() + File.separatorChar + key, bArr);
                        DiskCache.this.a();
                    } catch (CacheException e) {
                        LoggerFactory.getTraceLogger().error((String) "DiskCache", "fail to put cache:" + e);
                    }
                }
            });
        } else {
            throw new RuntimeException("group can't be -");
        }
    }

    private void a(Entity entity) {
        String group = entity.getGroup();
        if (group != null && !group.equalsIgnoreCase("-")) {
            Set entitys = this.mGroup.get(group);
            if (entitys == null) {
                entitys = new HashSet();
                this.mGroup.put(group, entitys);
            }
            entitys.add(entity);
        }
    }

    public void remove(String url) {
        if (!this.b.get()) {
            throw new RuntimeException("DiskCache must call open() before");
        }
        a(url);
    }

    public void removeByOwner(String owner) {
        if (!this.b.get()) {
            throw new RuntimeException("DiskCache must call open() before");
        } else if (owner != null) {
            if (owner.equalsIgnoreCase("-")) {
                throw new RuntimeException("owner can't be -");
            }
            Set<String> urls = new HashSet<>();
            for (Entry<String, Entity> value : this.mEntities.entrySet()) {
                Entity entity = (Entity) value.getValue();
                if (owner.equals(entity.getOwner())) {
                    urls.add(entity.getUrl());
                }
            }
            for (String url : urls) {
                a(url);
            }
        }
    }

    public void removeByGroup(String group) {
        if (!this.b.get()) {
            throw new RuntimeException("DiskCache must call open() before");
        } else if (group == null) {
        } else {
            if (group.equalsIgnoreCase("-")) {
                throw new RuntimeException("group can't be -");
            }
            Set<Entity> entities = this.mGroup.get(group);
            if (entities != null) {
                Set<String> urls = new HashSet<>();
                for (Entity entity : entities) {
                    urls.add(entity.getUrl());
                }
                for (String url : urls) {
                    a(url);
                }
            }
        }
    }

    private void a(String url) {
        if (url != null) {
            removeEntity(url);
            removeCacheFile(url);
        }
    }

    private void b(Entity entity) {
        String group = entity.getGroup();
        if (group != null && !group.equalsIgnoreCase("-")) {
            Set entitys = this.mGroup.get(group);
            if (entitys != null) {
                entitys.remove(entity);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeCacheFile(String url) {
        final String key = obtainKey(url);
        AsyncTaskExecutor.getInstance().executeSerially(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                File file = new File(DiskCache.this.getDirectory() + File.separatorChar + key);
                if (file.exists()) {
                    if (!file.delete()) {
                        LoggerFactory.getTraceLogger().error((String) "DiskCache", (String) "fail to remove cache file");
                    }
                    DiskCache.this.a();
                }
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0040 A[Catch:{ IOException -> 0x005a }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:13:0x0030=Splitter:B:13:0x0030, B:23:0x0045=Splitter:B:23:0x0045} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.Serializable getSerializable(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            byte[] r2 = r9.get(r10, r11)
            if (r2 != 0) goto L_0x0008
            r5 = 0
        L_0x0007:
            return r5
        L_0x0008:
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r2)
            r3 = 0
            java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch:{ StreamCorruptedException -> 0x002f, IOException -> 0x0044, ClassNotFoundException -> 0x004f }
            r4.<init>(r0)     // Catch:{ StreamCorruptedException -> 0x002f, IOException -> 0x0044, ClassNotFoundException -> 0x004f }
            java.lang.Object r5 = r4.readObject()     // Catch:{ StreamCorruptedException -> 0x0072, IOException -> 0x006f, ClassNotFoundException -> 0x006c, all -> 0x0069 }
            java.io.Serializable r5 = (java.io.Serializable) r5     // Catch:{ StreamCorruptedException -> 0x0072, IOException -> 0x006f, ClassNotFoundException -> 0x006c, all -> 0x0069 }
            r0.close()     // Catch:{ IOException -> 0x0020 }
            r4.close()     // Catch:{ IOException -> 0x0020 }
            goto L_0x0007
        L_0x0020:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "DiskCache"
            java.lang.String r8 = java.lang.String.valueOf(r1)
            r6.error(r7, r8)
            goto L_0x0007
        L_0x002f:
            r1 = move-exception
        L_0x0030:
            com.alipay.mobile.common.cache.disk.CacheException r5 = new com.alipay.mobile.common.cache.disk.CacheException     // Catch:{ all -> 0x003a }
            java.lang.String r6 = r1.getMessage()     // Catch:{ all -> 0x003a }
            r5.<init>(r6)     // Catch:{ all -> 0x003a }
            throw r5     // Catch:{ all -> 0x003a }
        L_0x003a:
            r5 = move-exception
        L_0x003b:
            r0.close()     // Catch:{ IOException -> 0x005a }
            if (r3 == 0) goto L_0x0043
            r3.close()     // Catch:{ IOException -> 0x005a }
        L_0x0043:
            throw r5
        L_0x0044:
            r1 = move-exception
        L_0x0045:
            com.alipay.mobile.common.cache.disk.CacheException r5 = new com.alipay.mobile.common.cache.disk.CacheException     // Catch:{ all -> 0x003a }
            java.lang.String r6 = r1.getMessage()     // Catch:{ all -> 0x003a }
            r5.<init>(r6)     // Catch:{ all -> 0x003a }
            throw r5     // Catch:{ all -> 0x003a }
        L_0x004f:
            r1 = move-exception
        L_0x0050:
            com.alipay.mobile.common.cache.disk.CacheException r5 = new com.alipay.mobile.common.cache.disk.CacheException     // Catch:{ all -> 0x003a }
            java.lang.String r6 = r1.getMessage()     // Catch:{ all -> 0x003a }
            r5.<init>(r6)     // Catch:{ all -> 0x003a }
            throw r5     // Catch:{ all -> 0x003a }
        L_0x005a:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "DiskCache"
            java.lang.String r8 = java.lang.String.valueOf(r1)
            r6.error(r7, r8)
            goto L_0x0043
        L_0x0069:
            r5 = move-exception
            r3 = r4
            goto L_0x003b
        L_0x006c:
            r1 = move-exception
            r3 = r4
            goto L_0x0050
        L_0x006f:
            r1 = move-exception
            r3 = r4
            goto L_0x0045
        L_0x0072:
            r1 = move-exception
            r3 = r4
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.cache.disk.DiskCache.getSerializable(java.lang.String, java.lang.String):java.io.Serializable");
    }

    public byte[] get(String owner, String url) {
        if (!this.b.get()) {
            throw new RuntimeException("DiskCache must call open() before");
        } else if (owner != null && owner.equalsIgnoreCase("-")) {
            throw new RuntimeException("owner can't be -");
        } else if (!containEntity(url)) {
            return null;
        } else {
            Entity entity = getEntity(url);
            if (entity.expire()) {
                remove(url);
                return null;
            } else if (!entity.authenticate(owner)) {
                return null;
            } else {
                entity.use();
                return b(getDirectory() + File.separatorChar + obtainKey(entity.getUrl()));
            }
        }
    }

    public long getMaxsize() {
        return this.mMaxsize;
    }

    public long getSize() {
        long j;
        synchronized (this.mEntities) {
            j = this.mSize;
        }
        return j;
    }

    public int getCacheCount() {
        return this.mEntities.size();
    }

    /* access modifiers changed from: protected */
    public final void setDirectory(String directory) {
        this.mDirectory = directory;
        if (this.mDirectory == null) {
            throw new IllegalArgumentException("Not set valid cache directory.");
        }
        File file = new File(this.mDirectory);
        if (!file.exists() && !file.mkdir()) {
            throw new IllegalArgumentException("An Error occured while  cache directory.");
        } else if (!file.isDirectory()) {
            throw new IllegalArgumentException("Not set valid cache directory.");
        }
    }

    /* access modifiers changed from: protected */
    public final void setMaxsize(long maxsize) {
        this.mMaxsize = maxsize;
        if (this.mMaxsize <= 0) {
            throw new IllegalArgumentException("Not set valid cache size.");
        }
    }

    public String getDirectory() {
        return this.mDirectory;
    }

    public String getFileDirectory(String url) {
        if (!containEntity(url)) {
            return null;
        }
        Entity entity = getEntity(url);
        if (entity.expire()) {
            remove(url);
            return null;
        }
        entity.use();
        return getDirectory() + File.separatorChar + obtainKey(entity.getUrl());
    }

    /* access modifiers changed from: 0000 */
    public void addEntity(Entity entity) {
        synchronized (this.mEntities) {
            this.mEntities.put(entity.getUrl(), entity);
            a(entity);
            this.mSize += entity.getSize();
        }
    }

    /* access modifiers changed from: 0000 */
    public void removeEntity(String url) {
        synchronized (this.mEntities) {
            Entity entity = this.mEntities.get(url);
            if (entity != null) {
                this.mEntities.remove(url);
                b(entity);
                this.mSize -= entity.getSize();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean containEntity(String url) {
        boolean containsKey;
        synchronized (this.mEntities) {
            containsKey = this.mEntities.containsKey(url);
        }
        return containsKey;
    }

    /* access modifiers changed from: 0000 */
    public Entity getEntity(String url) {
        Entity entity;
        synchronized (this.mEntities) {
            entity = this.mEntities.get(url);
        }
        return entity;
    }

    /* access modifiers changed from: 0000 */
    public void clear() {
        AsyncTaskExecutor.getInstance().executeSerially(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                File file = new File(DiskCache.this.getDirectory());
                if (file.exists() && file.isDirectory()) {
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File delete : files) {
                            delete.delete();
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public String obtainKey(String url) {
        return Integer.toHexString(url.hashCode());
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0047 A[SYNTHETIC, Splitter:B:19:0x0047] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] b(java.lang.String r9) {
        /*
            java.io.File r2 = new java.io.File
            r2.<init>(r9)
            r3 = 0
            boolean r5 = r2.exists()
            if (r5 != 0) goto L_0x0016
            com.alipay.mobile.common.cache.disk.CacheException r5 = new com.alipay.mobile.common.cache.disk.CacheException
            com.alipay.mobile.common.cache.disk.CacheException$ErrorCode r6 = com.alipay.mobile.common.cache.disk.CacheException.ErrorCode.READ_IO_ERROR
            java.lang.String r7 = "cache file not found."
            r5.<init>(r6, r7)
            throw r5
        L_0x0016:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0037 }
            r4.<init>(r2)     // Catch:{ IOException -> 0x0037 }
            int r5 = r4.available()     // Catch:{ IOException -> 0x005d, all -> 0x005a }
            byte[] r0 = new byte[r5]     // Catch:{ IOException -> 0x005d, all -> 0x005a }
            r4.read(r0)     // Catch:{ IOException -> 0x005d, all -> 0x005a }
            r4.close()     // Catch:{ IOException -> 0x0028 }
        L_0x0027:
            return r0
        L_0x0028:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r6 = "DiskCache"
            java.lang.String r7 = java.lang.String.valueOf(r1)
            r5.error(r6, r7)
            goto L_0x0027
        L_0x0037:
            r1 = move-exception
        L_0x0038:
            com.alipay.mobile.common.cache.disk.CacheException r5 = new com.alipay.mobile.common.cache.disk.CacheException     // Catch:{ all -> 0x0044 }
            com.alipay.mobile.common.cache.disk.CacheException$ErrorCode r6 = com.alipay.mobile.common.cache.disk.CacheException.ErrorCode.READ_IO_ERROR     // Catch:{ all -> 0x0044 }
            java.lang.String r7 = r1.getMessage()     // Catch:{ all -> 0x0044 }
            r5.<init>(r6, r7)     // Catch:{ all -> 0x0044 }
            throw r5     // Catch:{ all -> 0x0044 }
        L_0x0044:
            r5 = move-exception
        L_0x0045:
            if (r3 == 0) goto L_0x004a
            r3.close()     // Catch:{ IOException -> 0x004b }
        L_0x004a:
            throw r5
        L_0x004b:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "DiskCache"
            java.lang.String r8 = java.lang.String.valueOf(r1)
            r6.error(r7, r8)
            goto L_0x004a
        L_0x005a:
            r5 = move-exception
            r3 = r4
            goto L_0x0045
        L_0x005d:
            r1 = move-exception
            r3 = r4
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.cache.disk.DiskCache.b(java.lang.String):byte[]");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c A[SYNTHETIC, Splitter:B:18:0x003c] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:13:0x002d=Splitter:B:13:0x002d, B:30:0x005f=Splitter:B:30:0x005f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.lang.String r8, byte[] r9) {
        /*
            java.io.File r1 = new java.io.File
            r1.<init>(r8)
            r2 = 0
            boolean r4 = r1.exists()     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            if (r4 != 0) goto L_0x0040
            java.io.File r4 = r1.getParentFile()     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            if (r4 == 0) goto L_0x0040
            java.io.File r4 = r1.getParentFile()     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            boolean r4 = r4.mkdir()     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            if (r4 == 0) goto L_0x0040
            boolean r4 = r1.createNewFile()     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            if (r4 != 0) goto L_0x0040
            com.alipay.mobile.common.cache.disk.CacheException r4 = new com.alipay.mobile.common.cache.disk.CacheException     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            com.alipay.mobile.common.cache.disk.CacheException$ErrorCode r5 = com.alipay.mobile.common.cache.disk.CacheException.ErrorCode.WRITE_IO_ERROR     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            java.lang.String r6 = "cache file create error."
            r4.<init>(r5, r6)     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            throw r4     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
        L_0x002c:
            r0 = move-exception
        L_0x002d:
            com.alipay.mobile.common.cache.disk.CacheException r4 = new com.alipay.mobile.common.cache.disk.CacheException     // Catch:{ all -> 0x0039 }
            com.alipay.mobile.common.cache.disk.CacheException$ErrorCode r5 = com.alipay.mobile.common.cache.disk.CacheException.ErrorCode.WRITE_IO_ERROR     // Catch:{ all -> 0x0039 }
            java.lang.String r6 = r0.getMessage()     // Catch:{ all -> 0x0039 }
            r4.<init>(r5, r6)     // Catch:{ all -> 0x0039 }
            throw r4     // Catch:{ all -> 0x0039 }
        L_0x0039:
            r4 = move-exception
        L_0x003a:
            if (r2 == 0) goto L_0x003f
            r2.close()     // Catch:{ IOException -> 0x006b }
        L_0x003f:
            throw r4
        L_0x0040:
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            r3.<init>(r1)     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x005e }
            r3.write(r9)     // Catch:{ FileNotFoundException -> 0x0080, IOException -> 0x007d, all -> 0x007a }
            r3.flush()     // Catch:{ FileNotFoundException -> 0x0080, IOException -> 0x007d, all -> 0x007a }
            r3.close()     // Catch:{ IOException -> 0x004f }
        L_0x004e:
            return
        L_0x004f:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "DiskCache"
            java.lang.String r6 = java.lang.String.valueOf(r0)
            r4.error(r5, r6)
            goto L_0x004e
        L_0x005e:
            r0 = move-exception
        L_0x005f:
            com.alipay.mobile.common.cache.disk.CacheException r4 = new com.alipay.mobile.common.cache.disk.CacheException     // Catch:{ all -> 0x0039 }
            com.alipay.mobile.common.cache.disk.CacheException$ErrorCode r5 = com.alipay.mobile.common.cache.disk.CacheException.ErrorCode.WRITE_IO_ERROR     // Catch:{ all -> 0x0039 }
            java.lang.String r6 = r0.getMessage()     // Catch:{ all -> 0x0039 }
            r4.<init>(r5, r6)     // Catch:{ all -> 0x0039 }
            throw r4     // Catch:{ all -> 0x0039 }
        L_0x006b:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r6 = "DiskCache"
            java.lang.String r7 = java.lang.String.valueOf(r0)
            r5.error(r6, r7)
            goto L_0x003f
        L_0x007a:
            r4 = move-exception
            r2 = r3
            goto L_0x003a
        L_0x007d:
            r0 = move-exception
            r2 = r3
            goto L_0x005f
        L_0x0080:
            r0 = move-exception
            r2 = r3
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.cache.disk.DiskCache.a(java.lang.String, byte[]):void");
    }

    /* access modifiers changed from: private */
    public void a() {
        this.c.set(true);
        if (!this.a.get()) {
            this.c.set(false);
            this.a.set(true);
            AsyncTaskExecutor.getInstance().executeSerially(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    HashMap copyEntities = null;
                    synchronized (DiskCache.this.mEntities) {
                        try {
                            copyEntities = (HashMap) DiskCache.this.mEntities.clone();
                        } catch (Throwable th) {
                        }
                    }
                    if (copyEntities != null) {
                        DiskCache.this.mMeta.writeMeta(copyEntities);
                    }
                    DiskCache.this.a.set(false);
                    if (DiskCache.this.c.get()) {
                        DiskCache.this.a();
                    }
                }
            }, "scheduleMeta");
        }
    }
}
