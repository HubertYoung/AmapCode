package com.autonavi.minimap.offline.storage;

public class StorageInfo {
    public final long availableSize;
    public final int externalCardIndex;
    public final boolean isExternalCard;
    public final String path;
    public final long totalSize;

    public StorageInfo(String str, long j, long j2, boolean z, int i) {
        this.path = str;
        this.availableSize = j;
        this.totalSize = j2;
        this.isExternalCard = z;
        this.externalCardIndex = i;
    }
}
