package com.autonavi.inter.impl;

import com.autonavi.bundle.account.AccountVApp;
import com.autonavi.bundle.account.facerecognition.FaceRecognitionVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class ACCOUNT_VirtualApp_DATA extends ArrayList<Class<?>> {
    public ACCOUNT_VirtualApp_DATA() {
        add(FaceRecognitionVApp.class);
        add(AccountVApp.class);
    }
}
