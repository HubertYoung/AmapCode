package anetwork.network.cache;

import java.io.Serializable;

public class CacheBlockConfig implements Serializable {
    private static final long serialVersionUID = -2567271693706129850L;
    public String blockName;
    public long blockSize;
    public boolean isCompress;
    public boolean isEncrypt;
    public boolean isRemovable;

    public CacheBlockConfig(String str, long j, boolean z, boolean z2, boolean z3) {
        this.blockName = str;
        this.blockSize = j;
        this.isCompress = z;
        this.isEncrypt = z2;
        this.isRemovable = z3;
    }

    public CacheBlockConfig() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("CacheBlockConfig [blockName=");
        sb.append(this.blockName);
        sb.append(", blockSize=");
        sb.append(this.blockSize);
        sb.append(", isCompress=");
        sb.append(this.isCompress);
        sb.append(", isEncrypt=");
        sb.append(this.isEncrypt);
        sb.append(", isRemovable=");
        sb.append(this.isRemovable);
        sb.append("]");
        return sb.toString();
    }
}
