package com.alipay.mobile.antui.basic;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.widget.ListAdapter;

/* compiled from: AUHorizontalListView */
final class p extends AccessibilityDelegateCompat {
    final /* synthetic */ AUHorizontalListView a;

    private p(AUHorizontalListView aUHorizontalListView) {
        this.a = aUHorizontalListView;
    }

    /* synthetic */ p(AUHorizontalListView x0, byte b) {
        this(x0);
    }

    public final void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        int position = this.a.getPositionForView(host);
        ListAdapter adapter = this.a.getAdapter();
        if (position != -1 && adapter != null && this.a.isEnabled() && adapter.isEnabled(position)) {
            if (position == this.a.getSelectedItemPosition()) {
                info.setSelected(true);
                info.addAction(8);
            } else {
                info.addAction(4);
            }
            if (this.a.isClickable()) {
                info.addAction(16);
                info.setClickable(true);
            }
            if (this.a.isLongClickable()) {
                info.addAction(32);
                info.setLongClickable(true);
            }
        }
    }

    public final boolean performAccessibilityAction(View host, int action, Bundle arguments) {
        if (super.performAccessibilityAction(host, action, arguments)) {
            return true;
        }
        int position = this.a.getPositionForView(host);
        ListAdapter adapter = this.a.getAdapter();
        if (position == -1 || adapter == null) {
            return false;
        }
        if (!this.a.isEnabled() || !adapter.isEnabled(position)) {
            return false;
        }
        long id = this.a.getItemIdAtPosition(position);
        switch (action) {
            case 4:
                if (this.a.getSelectedItemPosition() == position) {
                    return false;
                }
                this.a.setSelection(position);
                return true;
            case 8:
                if (this.a.getSelectedItemPosition() != position) {
                    return false;
                }
                this.a.setSelection(-1);
                return true;
            case 16:
                if (this.a.isClickable()) {
                    return this.a.performItemClick(host, position, id);
                }
                return false;
            case 32:
                if (this.a.isLongClickable()) {
                    return this.a.performLongPress(host, position, id);
                }
                return false;
            default:
                return false;
        }
    }
}
