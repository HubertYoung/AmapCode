package com.autonavi.minimap.offline.preference;

public class HintPopupWindowPreference extends BaseOfflinePreference {
    public static final String KEY_PREFIX_HINT_POPUP_WINDOW = "HintPopupWindow-";
    private static final String NAME = "HintPopupWindow";

    public static BaseOfflinePreference getInstance() {
        return BaseOfflinePreference.getInstance(NAME);
    }
}
