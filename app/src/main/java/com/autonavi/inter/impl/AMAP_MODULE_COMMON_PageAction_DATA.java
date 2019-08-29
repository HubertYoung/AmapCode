package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.page.action.alert_dialog_page"}, module = "amap_module_common", pages = {"com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage"})
@KeepName
public final class AMAP_MODULE_COMMON_PageAction_DATA extends HashMap<String, Class<?>> {
    public AMAP_MODULE_COMMON_PageAction_DATA() {
        put("amap.page.action.alert_dialog_page", NodeAlertDialogPage.class);
    }
}
