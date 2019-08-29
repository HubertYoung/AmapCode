package com.alipay.mobile.common.netsdkextdependapi;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstraceExtBeanFactory<T> {
    protected T backupBean = null;
    protected T defaultBean = null;
    protected Map<String, T> mBeanMap = null;

    /* access modifiers changed from: protected */
    public abstract T newBackupBean();

    /* access modifiers changed from: protected */
    public abstract T newDefaultBean();

    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return getBackupBean();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public T getDefaultBean() {
        /*
            r5 = this;
            T r1 = r5.defaultBean
            if (r1 == 0) goto L_0x0007
            T r1 = r5.defaultBean
        L_0x0006:
            return r1
        L_0x0007:
            java.lang.Class<com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory> r2 = com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory.class
            monitor-enter(r2)
            T r1 = r5.defaultBean     // Catch:{ all -> 0x0012 }
            if (r1 == 0) goto L_0x0015
            T r1 = r5.defaultBean     // Catch:{ all -> 0x0012 }
            monitor-exit(r2)     // Catch:{ all -> 0x0012 }
            goto L_0x0006
        L_0x0012:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0012 }
            throw r1
        L_0x0015:
            java.lang.Object r1 = r5.newDefaultBean()     // Catch:{ Throwable -> 0x0023 }
            r5.defaultBean = r1     // Catch:{ Throwable -> 0x0023 }
            T r1 = r5.defaultBean     // Catch:{ Throwable -> 0x0023 }
            if (r1 == 0) goto L_0x0037
            T r1 = r5.defaultBean     // Catch:{ Throwable -> 0x0023 }
            monitor-exit(r2)     // Catch:{ all -> 0x0012 }
            goto L_0x0006
        L_0x0023:
            r0 = move-exception
            java.util.logging.Logger r1 = com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil.logger     // Catch:{ all -> 0x0012 }
            java.util.logging.Level r3 = java.util.logging.Level.FINEST     // Catch:{ all -> 0x0012 }
            boolean r1 = r1.isLoggable(r3)     // Catch:{ all -> 0x0012 }
            if (r1 == 0) goto L_0x0037
            java.util.logging.Logger r1 = com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil.logger     // Catch:{ all -> 0x0012 }
            java.util.logging.Level r3 = java.util.logging.Level.FINEST     // Catch:{ all -> 0x0012 }
            java.lang.String r4 = "[getDefaultBean] Exception"
            r1.log(r3, r4, r0)     // Catch:{ all -> 0x0012 }
        L_0x0037:
            monitor-exit(r2)     // Catch:{ all -> 0x0012 }
            java.lang.Object r1 = r5.getBackupBean()
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory.getDefaultBean():java.lang.Object");
    }

    public void setDefaultBean(T t) {
        this.defaultBean = t;
    }

    /* access modifiers changed from: protected */
    public T getBackupBean() {
        if (this.backupBean != null) {
            return this.backupBean;
        }
        synchronized (this) {
            if (this.backupBean != null) {
                T t = this.backupBean;
                return t;
            }
            this.backupBean = newBackupBean();
            return this.backupBean;
        }
    }

    public T getBean(String serviceName) {
        if (this.mBeanMap == null || this.mBeanMap.isEmpty()) {
            return null;
        }
        return this.mBeanMap.get(serviceName);
    }

    public T addBean(String serviceName, T t) {
        return getBeanMap().put(serviceName, t);
    }

    public T removeBean(String serviceName) {
        if (this.mBeanMap == null || this.mBeanMap.isEmpty()) {
            return null;
        }
        return this.mBeanMap.remove(serviceName);
    }

    /* access modifiers changed from: protected */
    public Map<String, T> getBeanMap() {
        if (this.mBeanMap != null) {
            return this.mBeanMap;
        }
        synchronized (this) {
            if (this.mBeanMap != null) {
                Map<String, T> map = this.mBeanMap;
                return map;
            }
            this.mBeanMap = new HashMap(2);
            return this.mBeanMap;
        }
    }
}
