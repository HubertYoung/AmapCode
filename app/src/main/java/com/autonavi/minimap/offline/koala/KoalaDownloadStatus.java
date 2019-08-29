package com.autonavi.minimap.offline.koala;

public enum KoalaDownloadStatus {
    PENDING(0),
    CONNECTED(1),
    DOWNLOADING(2),
    BLOCK_COMPLETE(3),
    COMPLETE(4),
    PAUSE(5),
    ERROR(6);
    
    private int value;

    private KoalaDownloadStatus(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }

    public static KoalaDownloadStatus valueOf(int i) {
        switch (i) {
            case 0:
                return PENDING;
            case 1:
                return CONNECTED;
            case 2:
                return DOWNLOADING;
            case 3:
                return BLOCK_COMPLETE;
            case 4:
                return COMPLETE;
            case 5:
                return PAUSE;
            case 6:
                return ERROR;
            default:
                throw new IllegalArgumentException("illegal status!");
        }
    }
}
