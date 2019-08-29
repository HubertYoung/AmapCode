package com.autonavi.minimap.bundle.maphome;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.alc.model.ALCTriggerType;

public class MapHomeVApp extends esh {
    private cyz a;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleMapHome.class);
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        if (this.a == null) {
            this.a = new cyz(DoNotUseTool.getMapManager());
        }
        cyz cyz = this.a;
        if (cyz.a != null) {
            awo awo = (awo) a.a.a(awo.class);
            if (awo != null) {
                awo.a(awo.c);
                if (awo.d()) {
                    awo.a(awo.d);
                }
                awo.b();
                awo.b(9001);
                if (awo.d()) {
                    awo.b(9003);
                }
            }
        }
        cyz.a();
        awo awo2 = (awo) a.a.a(awo.class);
        if (awo2 != null) {
            if (System.currentTimeMillis() - awo2.h() > 86400000) {
                awo2.g();
            }
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractBaseMapPage) {
            ((AbstractBaseMapPage) pageContext).resetMapSkinState();
        }
        AMapLog.upload(ALCTriggerType.appLaunch);
        AMapLog.playbackAppAction(1);
    }

    public void vAppEnterForeground() {
        super.vAppEnterForeground();
        if (this.a == null) {
            this.a = new cyz(DoNotUseTool.getMapManager());
        }
        cyz cyz = this.a;
        if (!cyz.b) {
            cyz.b = true;
            return;
        }
        if (cyz.a != null) {
            awo awo = (awo) a.a.a(awo.class);
            if (awo != null) {
                awo.g();
            }
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        this.a = null;
    }
}
