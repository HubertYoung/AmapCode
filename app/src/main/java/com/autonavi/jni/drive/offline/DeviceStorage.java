package com.autonavi.jni.drive.offline;

import android.os.StatFs;
import android.text.TextUtils;

public class DeviceStorage {
    private long mPtr;

    public long getAvailableSpace(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                StatFs statFs = new StatFs(str);
                long blockSize = ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
                if (blockSize >= 0) {
                    return blockSize;
                }
            } catch (Exception unused) {
                return -1;
            }
        }
        return -1;
    }
}
