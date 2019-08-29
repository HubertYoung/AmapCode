package com.alipay.mobile.beehive.compositeui.common;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

@TargetApi(14)
public class SecureAccessbilityDelegate extends AccessibilityDelegate {
    public boolean dispatchPopulateAccessibilityEvent(View arg2, AccessibilityEvent arg3) {
        return true;
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider(View arg2) {
        return null;
    }

    public void onInitializeAccessibilityEvent(View arg1, AccessibilityEvent arg2) {
    }

    public void onInitializeAccessibilityNodeInfo(View arg1, AccessibilityNodeInfo arg2) {
    }

    public void onPopulateAccessibilityEvent(View arg1, AccessibilityEvent arg2) {
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup arg2, View arg3, AccessibilityEvent arg4) {
        return false;
    }

    public boolean performAccessibilityAction(View arg2, int arg3, Bundle arg4) {
        return true;
    }

    public void sendAccessibilityEvent(View arg1, int arg2) {
    }

    public void sendAccessibilityEventUnchecked(View arg1, AccessibilityEvent arg2) {
    }
}
