package com.alipay.mobile.common.transportext.biz.diagnose.network;

import com.alipay.mobile.common.transportext.amnet.Storage;

class StorageManager {
    private Storage storage;

    public StorageManager(Storage storage2) {
        this.storage = storage2;
    }

    /* access modifiers changed from: 0000 */
    public Long getBig(String key, boolean common) {
        byte[] b = common ? this.storage.getCommon(key) : this.storage.getSecure(key);
        if (b == null || b.length != 8) {
            return null;
        }
        return Long.valueOf((((((((((((((((long) b[0]) << 8) | (((long) b[1]) & 255)) << 8) | (((long) b[2]) & 255)) << 8) | (((long) b[3]) & 255)) << 8) | (((long) b[4]) & 255)) << 8) | (((long) b[5]) & 255)) << 8) | (((long) b[6]) & 255)) << 8) | (((long) b[7]) & 255));
    }

    /* access modifiers changed from: 0000 */
    public Integer getInt(String key, boolean common) {
        byte[] b = common ? this.storage.getCommon(key) : this.storage.getSecure(key);
        if (b == null || b.length != 4) {
            return null;
        }
        return Integer.valueOf((((((b[0] << 8) | (b[1] & 255)) << 8) | (b[2] & 255)) << 8) | (b[3] & 255));
    }

    /* access modifiers changed from: 0000 */
    public String getStr(String key, boolean common) {
        byte[] b = common ? this.storage.getCommon(key) : this.storage.getSecure(key);
        if (b == null) {
            return null;
        }
        return NetworkDiagnoseUtil.convert(b);
    }
}
