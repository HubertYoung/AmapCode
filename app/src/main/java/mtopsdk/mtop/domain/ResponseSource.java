package mtopsdk.mtop.domain;

import android.support.annotation.NonNull;
import anetwork.network.cache.RpcCache;
import java.io.Serializable;

public class ResponseSource implements Serializable {
    private String cacheBlock;
    private String cacheKey;
    public fei cacheManager;
    public MtopResponse cacheResponse;
    public fdf mtopContext;
    public boolean requireConnection = true;
    public RpcCache rpcCache = null;
    public String seqNo;

    public ResponseSource(@NonNull fdf fdf, @NonNull fei fei) {
        this.mtopContext = fdf;
        this.cacheManager = fei;
        this.seqNo = fdf.h;
    }

    public String getCacheKey() {
        if (fdd.a(this.cacheKey)) {
            return this.cacheKey;
        }
        this.cacheKey = this.cacheManager.a(this.mtopContext);
        return this.cacheKey;
    }

    public String getCacheBlock() {
        if (fdd.a(this.cacheBlock)) {
            return this.cacheBlock;
        }
        this.cacheBlock = this.cacheManager.b(this.mtopContext.b.getKey());
        return this.cacheBlock;
    }
}
