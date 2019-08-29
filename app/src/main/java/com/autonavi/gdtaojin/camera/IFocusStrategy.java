package com.autonavi.gdtaojin.camera;

import android.view.MotionEvent;

public interface IFocusStrategy {
    void cancelFocus();

    void executeFocus(MotionEvent motionEvent);

    long getFocusEndTime();

    void operateFocus();
}
