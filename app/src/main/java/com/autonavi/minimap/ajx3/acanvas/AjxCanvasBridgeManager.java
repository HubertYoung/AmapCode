package com.autonavi.minimap.ajx3.acanvas;

import android.support.v4.util.LongSparseArray;
import com.autonavi.minimap.acanvas.ACanvasBridge;
import com.autonavi.minimap.ajx3.context.IAjxContext;

public class AjxCanvasBridgeManager {
    private static volatile AjxCanvasBridgeManager singleton;
    private LongSparseArray<ACanvasBridge> mCanvasBridge = new LongSparseArray<>();

    public static AjxCanvasBridgeManager getSingleton() {
        if (singleton == null) {
            synchronized (AjxCanvasBridgeManager.class) {
                try {
                    if (singleton == null) {
                        singleton = new AjxCanvasBridgeManager();
                    }
                }
            }
        }
        return singleton;
    }

    private AjxCanvasBridgeManager() {
    }

    public synchronized ACanvasBridge getBridge(IAjxContext iAjxContext) {
        ACanvasBridge aCanvasBridge;
        try {
            long shadow = iAjxContext.getJsContext().shadow();
            aCanvasBridge = (ACanvasBridge) this.mCanvasBridge.get(shadow);
            if (aCanvasBridge == null) {
                aCanvasBridge = new ACanvasBridge(new AjxCanvasImageLoader(iAjxContext));
                this.mCanvasBridge.put(shadow, aCanvasBridge);
            }
        }
        return aCanvasBridge;
    }

    public synchronized void removeBridge(IAjxContext iAjxContext) {
        this.mCanvasBridge.remove(iAjxContext.getJsContext().shadow());
    }
}
