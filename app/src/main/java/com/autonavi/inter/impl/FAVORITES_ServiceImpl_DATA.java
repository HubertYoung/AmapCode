package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.basemap.favorites.FavoriteServiceImpl", "com.autonavi.minimap.basemap.favorites.util.SaveUtils", "com.autonavi.minimap.basemap.OperationCommuteControllerImpl", "com.autonavi.minimap.basemap.favorites.newinter.impl.SavePoiJsonUtils", "com.autonavi.minimap.basemap.favorites.newinter.impl.FavoriteFactoryImpl", "com.autonavi.minimap.basemap.favorites.newinter.impl.SaveDataTransfer"}, inters = {"com.autonavi.map.core.IFavoriteService", "com.autonavi.minimap.basemap.favorites.inner.ISaveUtils", "com.autonavi.bundle.favorites.api.IOperationCommuteController", "com.autonavi.minimap.basemap.favorite.ISavePoiJsonUtils", "com.autonavi.minimap.basemap.favorite.IFavoriteFactory", "com.autonavi.minimap.basemap.favorite.ISaveDataTransfer"}, module = "favorites")
@KeepName
public final class FAVORITES_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public FAVORITES_ServiceImpl_DATA() {
        put(brn.class, cov.class);
        put(coy.class, cpm.class);
        put(ava.class, cod.class);
        put(coo.class, cpe.class);
        put(com.class, cpd.class);
        put(con.class, SaveDataTransfer.class);
    }
}
