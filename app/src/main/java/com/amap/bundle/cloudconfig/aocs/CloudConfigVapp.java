package com.amap.bundle.cloudconfig.aocs;

import com.amap.bundle.cloudconfig.ajx.ModuleCloudConfig;
import com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a;
import com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.b;
import com.autonavi.minimap.ajx3.Ajx;
import java.util.ArrayList;
import java.util.List;

public class CloudConfigVapp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    static {
        lo.a();
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleCloudConfig.class);
    }

    public void vAppEnterForeground() {
        super.vAppEnterForeground();
        lo a = lo.a();
        if (a.e("requestAllConfig() not init")) {
            ln lnVar = a.a;
            b bVar = new b();
            if (lnVar.c != null) {
                lnVar.c.a();
                lnVar.c = null;
            }
            lnVar.c = new CloudConfigRequest("1", lnVar.b());
            lnVar.c.a((b) new b(lnVar, 0), (a) new a(bVar) {
                final /* synthetic */ com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a a;

                {
                    this.a = r2;
                }

                public final boolean a(ArrayList<lq> arrayList) {
                    ln.this.a(ln.a(ln.this, (ArrayList) arrayList));
                    this.a.a(arrayList);
                    return true;
                }

                public final void a(int i, List<String> list) {
                    this.a.a(i, list);
                }
            });
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
    }
}
