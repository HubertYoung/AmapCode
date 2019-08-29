package com.autonavi.minimap.agroup.helper;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public class AGroupActiviesHelper$3 implements Callback<ctm> {
    final /* synthetic */ cie a;

    public void error(Throwable th, boolean z) {
    }

    public AGroupActiviesHelper$3(cie cie) {
        this.a = cie;
    }

    public void callback(ctm ctm) {
        if (ctm != null && ctm.a == 1 && this.a.b != null) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) this.a.b.get();
            if (abstractBasePage != null && abstractBasePage.isAlive()) {
                String str = ctm.c;
                if (!this.a.c || !abstractBasePage.isResumed()) {
                    this.a.a = str;
                } else {
                    cie.a(this.a, abstractBasePage, str);
                }
            }
        }
    }
}
