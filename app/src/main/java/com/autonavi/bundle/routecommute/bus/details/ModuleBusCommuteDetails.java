package com.autonavi.bundle.routecommute.bus.details;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.eyrie.amap.tbt.bus.BusRequestType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("bus_commute_details")
@KeepPublicClassMembers
@KeepName
public class ModuleBusCommuteDetails extends AbstractModule {
    private static final String COMMUTE_MAP_SWITCH = "COMMUTE_MAP_SWITCH";
    private static final String COMMUTE_MAP_TRIP_MODEL = "COMMUTE_MAP_TRIP_MODEL";
    public static final String MODULE_NAME = "bus_commute_details";
    private static final String TAG = "COMMUTE_MAP";
    private a mUiListener;

    public interface a {
        void a();

        void a(int i, int i2);
    }

    public ModuleBusCommuteDetails(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setUiListener(a aVar) {
        this.mUiListener = aVar;
    }

    @AjxMethod("locateMe")
    public void locateMe() {
        if (this.mUiListener != null) {
            this.mUiListener.a();
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getCommuteMapSwitch")
    public String getCommuteMapSwitch() {
        String str = bic.a((String) TAG).get(COMMUTE_MAP_SWITCH);
        return TextUtils.isEmpty(str) ? "true" : str;
    }

    @AjxMethod("setCommuteMapSwitch")
    public void setCommuteMapSwitch(String str) {
        bic.a((String) TAG).set(COMMUTE_MAP_SWITCH, String.valueOf(str));
    }

    @AjxMethod("updateLogoPosition")
    public void updateLogoPosition(String str) {
        azb.a(null, "deng---updateLogoBottomOffset:".concat(String.valueOf(str)));
        if (!TextUtils.isEmpty(str) && this.mUiListener != null) {
            JSONObject parseObject = JSONObject.parseObject(str);
            if (parseObject != null && parseObject.containsKey("leftOffset") && parseObject.containsKey("bottomOffset")) {
                int intValue = parseObject.getInteger("leftOffset").intValue();
                int intValue2 = parseObject.getInteger("bottomOffset").intValue();
                if (intValue >= 0 && intValue2 >= 0) {
                    this.mUiListener.a(intValue, intValue2);
                }
            }
        }
    }

    @AjxMethod("jumpDetailPage")
    public void jumpDetailPage(String str) {
        if (!TextUtils.isEmpty(str)) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString(ays.a, str);
            AMapPageUtil.getPageContext().startPage(BusCommuteDetailsPage.class, pageBundle);
        }
    }

    @AjxMethod("changeSelectIndex")
    public void changeSelectIndex(String str) {
        azb.a("song---", "changeSelectIndex param = ".concat(String.valueOf(str)));
        JSONObject parseObject = JSONObject.parseObject(str);
        int intValue = parseObject.getInteger("busListIndex").intValue();
        int intValue2 = parseObject.getInteger("alterIndex").intValue();
        ayi a2 = ayi.a();
        a2.b = intValue2;
        a2.a = intValue;
    }

    @AjxMethod("updateBusPathData")
    public void updateBusPathData(String str) {
        if (!azi.p() && !TextUtils.isEmpty(str)) {
            ayi.a().a(BusRequestType.BusRequestTypeRoute.ordinal(), str);
        }
    }

    @AjxMethod("ajxlog")
    public void ajxlog(String str) {
        if (!TextUtils.isEmpty(str)) {
            azb.a("AJX-", str);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "isCpoint")
    public boolean isCpoint() {
        return ayt.a();
    }
}
