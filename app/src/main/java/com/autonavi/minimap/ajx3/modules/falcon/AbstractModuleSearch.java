package com.autonavi.minimap.ajx3.modules.falcon;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;

public abstract class AbstractModuleSearch extends AbstractModule {
    public abstract void getOfflinePoiDetail(String str, JsFunctionCallback jsFunctionCallback);

    public abstract void offlineSugg(String str, JsFunctionCallback jsFunctionCallback);

    public abstract void poiSelectSearch(String str, JsFunctionCallback jsFunctionCallback);

    public abstract void searchOfflineNearestPoi(String str, JsFunctionCallback jsFunctionCallback);

    public abstract void searchPoiByKeyword(String str, JsFunctionCallback jsFunctionCallback);

    public abstract void share(String str, JsFunctionCallback jsFunctionCallback);

    public AbstractModuleSearch(IAjxContext iAjxContext) {
        super(iAjxContext);
    }
}
