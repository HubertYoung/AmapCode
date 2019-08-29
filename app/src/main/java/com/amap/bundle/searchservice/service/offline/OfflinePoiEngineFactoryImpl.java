package com.amap.bundle.searchservice.service.offline;

import com.amap.bundle.searchservice.api.IOfflinePoiEngineFactory;
import com.autonavi.ae.search.SearchEngine;

public class OfflinePoiEngineFactoryImpl implements IOfflinePoiEngineFactory {
    private static OfflinePoiEngineFactoryImpl c;
    public boolean a = false;
    public SearchEngine b;

    private OfflinePoiEngineFactoryImpl() {
    }

    public static synchronized OfflinePoiEngineFactoryImpl c() {
        OfflinePoiEngineFactoryImpl offlinePoiEngineFactoryImpl;
        synchronized (OfflinePoiEngineFactoryImpl.class) {
            try {
                if (c == null) {
                    c = new OfflinePoiEngineFactoryImpl();
                }
                offlinePoiEngineFactoryImpl = c;
            }
        }
        return offlinePoiEngineFactoryImpl;
    }

    public final boolean a() {
        if (this.b == null) {
            this.b = new SearchEngine();
            if (this.b.init() != 0 || this.b == null) {
                this.a = false;
            } else {
                this.a = true;
            }
        }
        return this.a;
    }

    public final SearchEngine b() {
        if (!this.a) {
            this.a = a();
        }
        if (this.a) {
            return this.b;
        }
        return null;
    }
}
