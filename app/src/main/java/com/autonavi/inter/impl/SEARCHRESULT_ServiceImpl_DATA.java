package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController;
import com.autonavi.map.fragmentcontainer.page.IPoiTipViewService;
import com.autonavi.minimap.search.inter.ICQDetailPageController;
import com.autonavi.minimap.search.inter.impl.CQDetailPageControllerImpl;
import com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl;
import com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.map.search.server.serverImpl.PoiTipViewServiceImpl", "com.autonavi.minimap.search.inter.impl.CQDetailPageControllerImpl", "com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl", "com.autonavi.minimap.search.inter.impl.SearchIntentDispatcherImpl"}, inters = {"com.autonavi.map.fragmentcontainer.page.IPoiTipViewService", "com.autonavi.minimap.search.inter.ICQDetailPageController", "com.autonavi.map.fragmentcontainer.page.ICQLayerController", "com.autonavi.minimap.search.inner.ISearchIntentDispatcher"}, module = "searchresult")
@KeepName
public final class SEARCHRESULT_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public SEARCHRESULT_ServiceImpl_DATA() {
        put(IPoiTipViewService.class, cbj.class);
        put(ICQDetailPageController.class, CQDetailPageControllerImpl.class);
        put(ICQLayerController.class, CQLayerControllerImpl.class);
        put(ekx.class, SearchIntentDispatcherImpl.class);
    }
}
