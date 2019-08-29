package com.autonavi.inter.impl;

import com.autonavi.bundle.feedback.FeedbackVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class FEEDBACK_VirtualApp_DATA extends ArrayList<Class<?>> {
    public FEEDBACK_VirtualApp_DATA() {
        add(FeedbackVApp.class);
    }
}
