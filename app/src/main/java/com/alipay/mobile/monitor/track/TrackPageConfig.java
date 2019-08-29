package com.alipay.mobile.monitor.track;

import java.util.Map;

public interface TrackPageConfig {
    public static final String KEY_ENTITY_CONTENT_TAGID = "entityContentTagId";

    Map<String, String> getExtParam();

    String getPageSpmId();

    boolean isTrackPage();
}
