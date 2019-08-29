package anetwork.network.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RpcCache implements Serializable {
    private static final long serialVersionUID = 7346667584637656956L;
    public byte[] body;
    public long cacheCreateTime;
    public CacheStatus cacheStatus;
    public String etag;
    public Map<String, List<String>> header;
    public String lastModified;
    public long maxAge;
    public boolean offline;
    public String version;

    public enum CacheStatus {
        FRESH,
        NEED_UPDATE,
        TIMEOUT
    }
}
