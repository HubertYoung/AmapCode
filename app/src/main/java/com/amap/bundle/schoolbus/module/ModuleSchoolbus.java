package com.amap.bundle.schoolbus.module;

import android.text.TextUtils;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;

@AjxModule("route_schoolbus")
public final class ModuleSchoolbus extends AbstractModule {
    public static final String MODULE_NAME = "route_schoolbus";
    private JsFunctionCallback mSchoolbusStatusCallback;

    public ModuleSchoolbus(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "registerPollingTravelCallback")
    public final void registerPollingTravelCallback(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null && !TextUtils.isEmpty(str)) {
            this.mSchoolbusStatusCallback = jsFunctionCallback;
            adl a = adl.a();
            adl.a((String) "school_manager", "registerSchoolbusStatus :".concat(String.valueOf(str)));
            a.c = jsFunctionCallback;
            a.a(str);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "unregisterPollingTravel")
    public final void unregisterPollingTravel(String str) {
        if (!TextUtils.isEmpty(str)) {
            adl a = adl.a();
            adl.a((String) "school_manager", "unregisterSchoolbusStatus :".concat(String.valueOf(str)));
            if (a.b == 1 || a.b == 3) {
                a.e.put(str, Boolean.FALSE);
            }
            if (a.c != null) {
                a.c = null;
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "startSchoolTravel")
    public final void startSchoolTravel(String str) {
        if (!TextUtils.isEmpty(str)) {
            adl a = adl.a();
            adl.a((String) "school_manager", "startTravel:".concat(String.valueOf(str)));
            a.f = true;
            a.a(str);
            czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
            czk.a(new a(czk) {
                final /* synthetic */ czk a;

                {
                    this.a = r2;
                }

                public final void a() {
                    this.a.a(NotificationChannelIds.p, R.drawable.ic_launcher, "高德地图", "安心校车正在使用位置服务");
                }
            });
        }
    }

    @AjxMethod(invokeMode = "sync", value = "finishSchoolTravel")
    public final void finishSchoolTravel(String str) {
        if (!TextUtils.isEmpty(str)) {
            adl a = adl.a();
            a.e.put(str, Boolean.FALSE);
            a.f = false;
            adl.a((String) "school_manager", "finishTravel:".concat(String.valueOf(str)));
            a.d.sendMessage(a.a(1011, str, a.b));
            ((czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class)).a();
        }
    }
}
