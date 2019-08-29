package com.autonavi.minimap.ajx3.analyzer;

import android.view.MotionEvent;
import com.autonavi.minimap.ajx3.context.IAjxContext;

public interface IAjxAnalyzer {
    void bindAjxContext(IAjxContext iAjxContext);

    void dismiss();

    void onReceiveTouchEvent(MotionEvent motionEvent);

    void pause();

    void resume();

    void show();
}
