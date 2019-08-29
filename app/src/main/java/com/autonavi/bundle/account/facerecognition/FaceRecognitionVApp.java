package com.autonavi.bundle.account.facerecognition;

import com.autonavi.widget.ui.BalloonLayout;

public class FaceRecognitionVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        ahm.a(new Runnable() {
            public final void run() {
                anu.a(FaceRecognitionVApp.this.getApplicationContext());
            }
        }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }
}
