package com.autonavi.miniapp.plugin.map.texture;

import java.util.HashMap;
import java.util.Map;

public class MiniAppTextureCacheManager {
    public static final int CACHE_TYPE_GROUND = 3;
    public static final int CACHE_TYPE_LINE = 2;
    public static final int CACHE_TYPE_MARKER = 1;
    public static final int CACHE_TYPE_RES = 0;
    private Map<bty, TextureCacheInfo> textureCacheMap = new HashMap();

    static class TextureCacheInfo {
        private Map<Object, Object> groundCache;
        private Map<Object, Object> lineCache;
        private Map<Object, Object> markerCache;
        private Map<Object, Object> resCache;

        private TextureCacheInfo() {
            this.resCache = new HashMap();
            this.markerCache = new HashMap();
            this.lineCache = new HashMap();
            this.groundCache = new HashMap();
        }

        /* access modifiers changed from: 0000 */
        public Map<Object, Object> getCache(int i) {
            switch (i) {
                case 0:
                    return this.resCache;
                case 1:
                    return this.markerCache;
                case 2:
                    return this.lineCache;
                case 3:
                    return this.groundCache;
                default:
                    throw new IllegalArgumentException("illegal cache type: ".concat(String.valueOf(i)));
            }
        }
    }

    public void addTextureCache(bty bty, int i, Object obj, Object obj2) {
        TextureCacheInfo textureCacheInfo = this.textureCacheMap.get(bty);
        if (textureCacheInfo == null) {
            textureCacheInfo = new TextureCacheInfo();
            this.textureCacheMap.put(bty, textureCacheInfo);
        }
        textureCacheInfo.getCache(i).put(obj, obj2);
    }

    public void clearTextureCache(bty bty) {
        this.textureCacheMap.remove(bty);
    }

    public void clearTextureCache(bty bty, int i) {
        TextureCacheInfo textureCacheInfo = this.textureCacheMap.get(bty);
        if (textureCacheInfo != null) {
            textureCacheInfo.getCache(i).clear();
        }
    }

    public Object getTextureCache(bty bty, int i, Object obj) {
        TextureCacheInfo textureCacheInfo = this.textureCacheMap.get(bty);
        if (textureCacheInfo == null) {
            return null;
        }
        return textureCacheInfo.getCache(i).get(obj);
    }
}
