package com.amap.bundle.voiceservice;

import com.amap.bundle.voiceservice.module.ModuleVoiceCenter;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.wing.VirtualAllLifecycleApplication;

public class VoiceServiceVApp extends VirtualAllLifecycleApplication {
    private lp a = new lp() {
        public final void onConfigResultCallBack(int i, String str) {
            VoiceServiceVApp.a(i);
        }

        public final void onConfigCallBack(int i) {
            VoiceServiceVApp.a(i);
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppEnterBackground() {
    }

    public void vAppEnterForeground() {
    }

    public void vAppCreate() {
        Ajx.getInstance().registerModule(ModuleVoiceCenter.class);
        aik.a().a(ais.a(true));
        aim.a().a = true;
    }

    public void vAppResume() {
        super.vAppResume();
        ail.a().b();
    }

    public void vAppPause() {
        super.vAppPause();
    }

    public void vAppMapLoadCompleted() {
        lo.a().a((String) "voice_sdk", this.a);
    }

    public void vAppDestroy() {
        aik.a().a(ais.a(false));
        aim.a().a = false;
    }

    static /* synthetic */ void a(int i) {
        if (i == 4 || i == 0 || i == 1 || i == 2 || i == 3) {
            aiv.a(lo.a().a((String) "voice_sdk"));
            ail.a().b();
        }
    }
}
