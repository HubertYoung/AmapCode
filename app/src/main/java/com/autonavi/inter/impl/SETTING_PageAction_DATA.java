package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.map.setting.page.AddNaviShortcutPage;
import com.autonavi.map.setting.page.InputNaviShortCutNamePage;
import com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage;
import com.autonavi.mine.page.setting.sysabout.page.ConfigPage;
import com.autonavi.mine.page.setting.sysabout.page.SysAboutPage;
import com.autonavi.mine.page.setting.sysmapset.page.SysMapSettingPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.sys_about_page", "amap.basemap.action.sys_map_setting_page", "amap.basemap.action.add_navi_shortcut_page", "amap.basemap.action.config_page", "amap.basemap.action.input_navi_shortcut_name_page", "amap.basemap.action.amap_setting_page"}, module = "setting", pages = {"com.autonavi.mine.page.setting.sysabout.page.SysAboutPage", "com.autonavi.mine.page.setting.sysmapset.page.SysMapSettingPage", "com.autonavi.map.setting.page.AddNaviShortcutPage", "com.autonavi.mine.page.setting.sysabout.page.ConfigPage", "com.autonavi.map.setting.page.InputNaviShortCutNamePage", "com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage"})
@KeepName
public final class SETTING_PageAction_DATA extends HashMap<String, Class<?>> {
    public SETTING_PageAction_DATA() {
        put("amap.basemap.action.sys_about_page", SysAboutPage.class);
        put("amap.basemap.action.sys_map_setting_page", SysMapSettingPage.class);
        put("amap.basemap.action.add_navi_shortcut_page", AddNaviShortcutPage.class);
        put("amap.basemap.action.config_page", ConfigPage.class);
        put("amap.basemap.action.input_navi_shortcut_name_page", InputNaviShortCutNamePage.class);
        put("amap.basemap.action.amap_setting_page", AmapSettingPage.class);
    }
}
