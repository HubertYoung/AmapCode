package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.basemap.errorback.inter.IBusErrorReportRemind;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager;
import com.autonavi.minimap.basemap.errorback.inter.impl.ReportErrorManagerImpl;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.basemap.errorback.inter.impl.ErrorReportStarterImpl", "com.autonavi.minimap.basemap.errorback.inter.impl.BusErrorReportRemindImpl", "com.autonavi.minimap.basemap.errorback.inter.impl.ReportErrorManagerImpl"}, inters = {"com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter", "com.autonavi.minimap.basemap.errorback.inter.IBusErrorReportRemind", "com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager"}, module = "feedback")
@KeepName
public final class FEEDBACK_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public FEEDBACK_ServiceImpl_DATA() {
        put(IErrorReportStarter.class, cok.class);
        put(IBusErrorReportRemind.class, coj.class);
        put(IReportErrorManager.class, ReportErrorManagerImpl.class);
    }
}
