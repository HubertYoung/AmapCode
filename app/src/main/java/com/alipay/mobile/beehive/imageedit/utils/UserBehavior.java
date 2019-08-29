package com.alipay.mobile.beehive.imageedit.utils;

import com.alipay.mobile.beehive.imageedit.constant.Constants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;

public class UserBehavior {
    public static void onDoodleActivityOpened() {
        Behavor behavor = new Behavor();
        behavor.setUserCaseID("UC-TUYA-160817-01");
        behavor.setSeedID("IMAGE_EDITOR_DOODLE");
        behavor.setAppID(Constants.IMAGE_EDIT_APP_ID);
        LoggerFactory.getBehavorLogger().openPage(behavor);
    }
}
