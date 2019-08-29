package test.tinyapp.alipay.com.testlibrary.plugin;

import android.os.Bundle;
import android.util.Log;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import test.tinyapp.alipay.com.testlibrary.service.a.a;

public class TestRouter extends H5SimplePlugin {
    private static final String LOG_TAG = "TestRouter";
    private Set<String> mActionsSet = new HashSet();
    private a pageSwitchNotifyTestService = new a();
    private test.tinyapp.alipay.com.testlibrary.service.performancepanel.a performancePanelTestService = new test.tinyapp.alipay.com.testlibrary.service.performancepanel.a();

    private static class ServiceHelper {
        private static List<test.tinyapp.alipay.com.testlibrary.core.a> sServiceList = new ArrayList();

        private ServiceHelper() {
        }

        static void registerService(test.tinyapp.alipay.com.testlibrary.core.a baseService) {
            if (sServiceList == null) {
                sServiceList = new ArrayList();
            }
            sServiceList.add(baseService);
            baseService.a();
        }

        static void release() {
            if (!test.tinyapp.alipay.com.testlibrary.a.a.a(sServiceList)) {
                for (test.tinyapp.alipay.com.testlibrary.core.a baseService : sServiceList) {
                    if (baseService != null) {
                        baseService.b();
                    }
                }
                sServiceList.clear();
            }
        }

        static boolean handleAction(H5Event event, H5BridgeContext context, Bundle extraParams) {
            if (!test.tinyapp.alipay.com.testlibrary.a.a.a(sServiceList)) {
                synchronized (sServiceList) {
                    for (test.tinyapp.alipay.com.testlibrary.core.a baseService : sServiceList) {
                        if (baseService != null && baseService.a(event)) {
                            baseService.a(event, context);
                        }
                    }
                }
            }
            return false;
        }
    }

    public void onInitialize(H5CoreNode coreNode) {
        Log.i(LOG_TAG, "onInitialize");
        ServiceHelper.registerService(this.performancePanelTestService);
        ServiceHelper.registerService(this.pageSwitchNotifyTestService);
        this.mActionsSet.addAll(this.performancePanelTestService.d());
        this.mActionsSet.addAll(this.pageSwitchNotifyTestService.c());
    }

    public void onPrepare(H5EventFilter filter) {
        Log.i(LOG_TAG, "onPrepare");
        filter.setEventsList(this.performancePanelTestService.d());
        filter.setEventsList(this.pageSwitchNotifyTestService.c());
    }

    public void onRelease() {
        ServiceHelper.release();
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        Log.i(LOG_TAG, "receive action: " + event.getAction());
        if (!this.mActionsSet.contains(event.getAction())) {
            Log.i(LOG_TAG, "can not handle action: " + event.getAction());
        } else {
            Log.i(LOG_TAG, "can handle action: " + event.getAction());
            try {
                ServiceHelper.handleAction(event, context, Bundle.EMPTY);
            } catch (Throwable th) {
                context.sendError(event, Error.NONE);
            }
        }
        return false;
    }
}
