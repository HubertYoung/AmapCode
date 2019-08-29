package com.alipay.zoloz.toyger.interfaces;

import android.view.KeyEvent;

public interface ToygerEvent {
    boolean onWindowFocusChanged(boolean z);

    boolean ontActivityEvent(int i, KeyEvent keyEvent);
}
