package com.autonavi.bundle.trafficevent;

import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.List;

public class TrafficEventVApp extends esh {
    public static final String a = "TrafficEventVApp";
    awb b = ((awb) a.a.a(awb.class));
    b c = new a() {
        public final bty a() {
            return DoNotUseTool.getMapView();
        }

        public final boolean a(List<als> list) {
            boolean z = false;
            if (list != null && list.size() > 0) {
                als als = list.get(0);
                if (als != null && (TrafficEventVApp.a(als.i) || als.i == 16777216)) {
                    String str = TrafficEventVApp.a;
                    bdl bdl = (bdl) a.a.a(bdl.class);
                    if (bdl != null) {
                        bid pageContext = AMapPageUtil.getPageContext();
                        a aVar = new a();
                        aVar.a = als;
                        bdl.a(pageContext, aVar);
                        bci bci = (bci) a.a.a(bci.class);
                        if (bci != null && bci.a(pageContext)) {
                            z = true;
                        }
                        if (!z) {
                            return true;
                        }
                        pageContext.finish();
                        return true;
                    }
                }
            }
            return false;
        }

        public final boolean b() {
            bid pageContext = AMapPageUtil.getPageContext();
            apr apr = (apr) a.a.a(apr.class);
            bci bci = (bci) a.a.a(bci.class);
            return (apr != null && apr.a(pageContext)) || (bci != null && bci.a(pageContext)) || ((bdl) a.a.a(bdl.class)).a(pageContext);
        }
    };
    bdw d = new bdw(this.c);

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        if (this.b != null) {
            this.b.a((MainMapEventListener) this.d);
        }
    }

    static /* synthetic */ boolean a(int i) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            return awo.d(i);
        }
        return false;
    }
}
