package com.squareup.leakcanary;

import android.os.Debug;

public final class AndroidDebuggerControl implements DebuggerControl {
    public final boolean isDebuggerAttached() {
        return Debug.isDebuggerConnected();
    }
}
