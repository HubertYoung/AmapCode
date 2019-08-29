package com.autonavi.map.search.fragment;

import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import org.json.JSONObject;

@PageAction("amap.search.action.resultmap")
public class SearchResultMapPage extends SearchResultBasePage implements launchModeSingleInstance {
    public void finishSelf() {
    }

    public JSONObject getScenesData() {
        return null;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final bxk a(SearchResultBasePage searchResultBasePage) {
        return new bxp(searchResultBasePage);
    }

    public long getScenesID() {
        if (!(this.a instanceof bxp)) {
            return 100663296;
        }
        bxp bxp = (bxp) this.a;
        if (bxp.a != null) {
            return bxp.a.c();
        }
        return 100663296;
    }
}
