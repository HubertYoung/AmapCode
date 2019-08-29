package com.autonavi.bundle.feedback;

import com.autonavi.bundle.feedback.ajx.ModuleFeedBack;
import com.autonavi.minimap.ajx3.Ajx;

public class FeedbackVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleFeedBack.class);
    }
}
