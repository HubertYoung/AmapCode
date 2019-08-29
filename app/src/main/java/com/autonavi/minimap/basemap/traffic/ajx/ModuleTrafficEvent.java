package com.autonavi.minimap.basemap.traffic.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("traffic_event")
@KeepPublicClassMembers
@KeepName
public class ModuleTrafficEvent extends AbstractModule {
    public static final String MODULE_NAME = "traffic_event";
    private a mListener;

    public interface a {
        void a(String str);

        void b(String str);
    }

    @AjxMethod("openReportPage")
    @Deprecated
    public void openReportPage(String str) {
    }

    public ModuleTrafficEvent(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setTrafficEventListener(a aVar) {
        this.mListener = aVar;
    }

    @AjxMethod("trafficEventDetail")
    public void trafficEventDetail(String str) {
        if (this.mListener != null) {
            this.mListener.a(str);
        }
    }

    @AjxMethod("changeContainerHeight")
    public void changeContainerHeight(String str) {
        if (this.mListener != null) {
            this.mListener.b(str);
        }
    }
}
