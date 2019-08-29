package com.autonavi.bundle.vui;

import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.bundle.vui.common.emojiview.ajx.Ajx3VUIEmojiView;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.vcs.NativeVcsManager;
import com.autonavi.wing.VirtualAllLifecycleApplication;

public class VUIVApp extends VirtualAllLifecycleApplication {
    private bgp a;
    private bgs b;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleVUI.class);
        Ajx.getInstance().registerView("voiceface", Ajx3VUIEmojiView.class);
        this.a = new bgp();
        this.b = new bgs();
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        bgl.a((bgd) new bgi());
        bgl.a((bgd) new bge());
        bgl.a((bgd) new bgh());
        bgl.a((bgd) new bgf());
        bgl.a((bgd) new bgg());
        bgl.a((bgd) new bgk());
        bgl.a((bgd) new bgj());
        VUIStateManager f = VUIStateManager.f();
        bfg.a((bfc) f);
        f.h = false;
        f.n = false;
        f.g = false;
        f.o = false;
        f.i = -1;
        f.j = 0;
        f.k = 5000;
        f.l = 5000;
        bga a2 = bga.a();
        if (VUIStateManager.f().m()) {
            bfg.a((bfc) a2);
            VUIStateManager f2 = VUIStateManager.f();
            if (f2.e != null) {
                f2.e.add(a2);
            }
        }
        bfg.a(1);
        bfg.a(3);
        bfg.a(2);
        bfe bfe = d.a;
    }

    public void vAppResume() {
        super.vAppResume();
        bfg.a(3);
    }

    public void vAppPause() {
        super.vAppPause();
        bfg.a(4);
        NativeVcsManager.getInstance().onActivityPause();
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        bfg.a(5);
        bfe bfe = d.a;
        lo.a().b("vui_navi", this.a.c);
    }
}
