package defpackage;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

@TargetApi(14)
/* renamed from: cwv reason: default package */
/* compiled from: StatisticAccessibilityDelegate */
public abstract class cwv extends AccessibilityDelegate implements i {
    private AccessibilityDelegate a = null;

    public cwv(AccessibilityDelegate accessibilityDelegate) {
        this.a = accessibilityDelegate;
    }

    public void sendAccessibilityEvent(View view, int i) {
        a(view, i);
        if (this.a != null) {
            this.a.sendAccessibilityEvent(view, i);
        } else {
            super.sendAccessibilityEvent(view, i);
        }
    }

    @TargetApi(16)
    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (this.a != null) {
            return this.a.performAccessibilityAction(view, i, bundle);
        }
        return super.performAccessibilityAction(view, i, bundle);
    }

    public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
        if (this.a != null) {
            this.a.sendAccessibilityEventUnchecked(view, accessibilityEvent);
        } else {
            super.sendAccessibilityEventUnchecked(view, accessibilityEvent);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.a != null) {
            return this.a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
        }
        return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.a != null) {
            this.a.onPopulateAccessibilityEvent(view, accessibilityEvent);
        } else {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
        }
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.a != null) {
            this.a.onInitializeAccessibilityEvent(view, accessibilityEvent);
        } else {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        }
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        if (this.a != null) {
            this.a.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        } else {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        }
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        if (this.a != null) {
            return this.a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
        return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    @TargetApi(16)
    public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
        if (this.a != null) {
            return this.a.getAccessibilityNodeProvider(view);
        }
        return super.getAccessibilityNodeProvider(view);
    }
}
