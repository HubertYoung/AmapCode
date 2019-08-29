package com.autonavi.map.fragmentcontainer.page;

import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;

public final class TopStackPageRecorder {
    private static String sPageToken = agy.a((String) "Error Page");

    static final void record(AbstractBasePage abstractBasePage) {
        if (!(abstractBasePage instanceof Transparent)) {
            if (abstractBasePage != null) {
                sPageToken = agy.a(abstractBasePage.toString());
            } else {
                sPageToken = agy.a((String) "Error Page");
            }
        }
    }

    public static final String getTopStackPageToken() {
        return sPageToken;
    }
}
