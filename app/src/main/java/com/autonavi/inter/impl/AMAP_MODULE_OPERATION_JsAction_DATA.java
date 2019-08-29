package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import com.autonavi.minimap.jsaction.GetHttpStringAction;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"getDeviceParamString", "openLightApp", "openDatePicker", "logUserAction", "openHtmlStringWebView", "callSMS", "openMovieDetail", "setNoPasswordForAlipay", "shortcutNavi", "getCarInfoList", "getHistoryQuery", "showPanellist", "callPhoneNumber", "openMovieShowings", "licenseConfirm", "getJSONString", "startNavi", "getHttpString", "discountSubscribe", "initPayment", "openScheme", "openHotelDetail", "openIndoorMap", "getMapLocation"}, jsActions = {"com.autonavi.minimap.jsaction.GetDeviceParamStringAction", "com.autonavi.minimap.jsaction.OpenLightAppAction", "com.autonavi.minimap.jsaction.OpenDatePickerAction", "com.autonavi.minimap.jsaction.LogUserActionAction", "com.autonavi.minimap.jsaction.OpenHtmlStringWebViewAction", "com.autonavi.minimap.jsaction.CallSMSAction", "com.autonavi.minimap.jsaction.OpenMovieDetailAction", "com.autonavi.minimap.jsaction.SetNoPasswordForAlipay", "com.autonavi.minimap.jsaction.ShortcutNaviAction", "com.autonavi.minimap.jsaction.GetCarInfoListAction", "com.autonavi.minimap.jsaction.GetHistoryQueryAction", "com.autonavi.minimap.jsaction.ShowPanellistAction", "com.autonavi.minimap.jsaction.CallPhoneNumberAction", "com.autonavi.minimap.jsaction.OpenMovieShowingsAction", "com.autonavi.minimap.jsaction.LicenseConfirmAtion", "com.autonavi.minimap.jsaction.GetJSONStringAction", "com.autonavi.minimap.jsaction.StartNaviAction", "com.autonavi.minimap.jsaction.GetHttpStringAction", "com.autonavi.minimap.jsaction.DiscountSubscribeAction", "com.autonavi.minimap.jsaction.InitPaymentAction", "com.autonavi.minimap.jsaction.OpenSchemeAction", "com.autonavi.minimap.jsaction.OpenHotelDetailAction", "com.autonavi.minimap.jsaction.OpenIndoorMapAction", "com.autonavi.minimap.jsaction.GetMapLocationAction"}, module = "amap_module_operation")
@KeepName
public final class AMAP_MODULE_OPERATION_JsAction_DATA extends HashMap<String, Class<?>> {
    public AMAP_MODULE_OPERATION_JsAction_DATA() {
        put("getDeviceParamString", dmp.class);
        put("openLightApp", dnb.class);
        put("openDatePicker", dmx.class);
        put("logUserAction", dmv.class);
        put("openHtmlStringWebView", dmz.class);
        put("callSMS", dmm.class);
        put("openMovieDetail", dnc.class);
        put("setNoPasswordForAlipay", dnf.class);
        put("shortcutNavi", dng.class);
        put("getCarInfoList", dmo.class);
        put("getHistoryQuery", dmq.class);
        put("showPanellist", dnh.class);
        put("callPhoneNumber", dml.class);
        put("openMovieShowings", dnd.class);
        put("licenseConfirm", dmu.class);
        put("getJSONString", dmr.class);
        put("startNavi", dni.class);
        put("getHttpString", GetHttpStringAction.class);
        put("discountSubscribe", dmn.class);
        put("initPayment", dmt.class);
        put("openScheme", dne.class);
        put("openHotelDetail", dmy.class);
        put("openIndoorMap", dna.class);
        put("getMapLocation", dms.class);
    }
}
