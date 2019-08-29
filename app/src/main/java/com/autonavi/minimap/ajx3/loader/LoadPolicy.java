package com.autonavi.minimap.ajx3.loader;

import com.autonavi.minimap.ajx3.loader.picasso.MemoryPolicy;
import com.autonavi.minimap.ajx3.loader.picasso.NetworkPolicy;
import com.autonavi.minimap.ajx3.loader.picasso.RequestCreator;

public class LoadPolicy {
    public static final int LOAD_NO_MERGE = 64;
    public static final int MEMORY_NO_CACHE = 2;
    public static final int MEMORY_NO_STORE = 4;
    public static final int NETWORK_NO_CACHE = 8;
    public static final int NETWORK_NO_STORE = 16;
    public static final int NO_FAST_MODE = 1;
    public static final int SVG = 32;

    public static boolean isSVG(int i) {
        return (i & 32) == 32;
    }

    public static boolean loadNoMerge(int i) {
        return (i & 64) == 64;
    }

    public static boolean memoryNoCache(int i) {
        return (i & 2) == 2;
    }

    public static boolean memoryNoStore(int i) {
        return (i & 4) == 4;
    }

    public static boolean networkNoCache(int i) {
        return (i & 8) == 8;
    }

    public static boolean networkNoStore(int i) {
        return (i & 16) == 16;
    }

    public static boolean noFastMode(int i) {
        return (i & 1) == 1;
    }

    public static void handleMemoryPolicy(RequestCreator requestCreator, int i) {
        boolean memoryNoCache = memoryNoCache(i);
        boolean memoryNoStore = memoryNoStore(i);
        boolean networkNoCache = networkNoCache(i);
        boolean networkNoStore = networkNoStore(i);
        boolean noFastMode = noFastMode(i);
        boolean isSVG = isSVG(i);
        boolean loadNoMerge = loadNoMerge(i);
        if (isSVG) {
            requestCreator.setSVGFlag(true);
        }
        if (noFastMode) {
            requestCreator.fastMode(false);
        }
        if (loadNoMerge) {
            requestCreator.noMerge(true);
        }
        if (memoryNoCache && memoryNoStore) {
            requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
        } else if (memoryNoCache) {
            requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, new MemoryPolicy[0]);
        } else if (memoryNoStore) {
            requestCreator.memoryPolicy(MemoryPolicy.NO_STORE, new MemoryPolicy[0]);
        }
        if (networkNoCache && networkNoStore) {
            requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
        } else if (networkNoCache) {
            requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, new NetworkPolicy[0]);
        } else {
            if (networkNoStore) {
                requestCreator.networkPolicy(NetworkPolicy.NO_STORE, new NetworkPolicy[0]);
            }
        }
    }
}
