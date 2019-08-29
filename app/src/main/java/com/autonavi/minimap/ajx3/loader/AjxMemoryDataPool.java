package com.autonavi.minimap.ajx3.loader;

public class AjxMemoryDataPool {
    private static AjxMemoryDataPool instance;
    private IDataPoolDelegate mDelegate = null;

    public interface IDataPoolDelegate {
        byte[] getDataBytes(long j);
    }

    public static AjxMemoryDataPool getInstance() {
        synchronized (AjxMemoryDataPool.class) {
            try {
                if (instance == null) {
                    instance = new AjxMemoryDataPool();
                }
            }
        }
        return instance;
    }

    public void setDataDelegate(IDataPoolDelegate iDataPoolDelegate) {
        this.mDelegate = iDataPoolDelegate;
    }

    public byte[] getDataBytes(long j) {
        if (this.mDelegate == null) {
            return new byte[0];
        }
        return this.mDelegate.getDataBytes(j);
    }
}
