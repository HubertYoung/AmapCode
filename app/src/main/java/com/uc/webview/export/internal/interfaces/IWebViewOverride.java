package com.uc.webview.export.internal.interfaces;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
public interface IWebViewOverride {
    void coreComputeScroll();

    void coreDestroy();

    boolean coreDispatchTouchEvent(MotionEvent motionEvent);

    void coreDraw(Canvas canvas);

    void coreOnConfigurationChanged(Configuration configuration);

    void coreOnInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent);

    void coreOnInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo);

    void coreOnScrollChanged(int i, int i2, int i3, int i4);

    void coreOnVisibilityChanged(View view, int i);

    boolean coreOverScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z);

    boolean corePerformAccessibilityAction(int i, Bundle bundle);

    void coreRequestLayout();

    void coreSetVisibility(int i);
}
