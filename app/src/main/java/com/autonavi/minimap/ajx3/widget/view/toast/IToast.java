package com.autonavi.minimap.ajx3.widget.view.toast;

import android.view.View;

public interface IToast {
    void cancel();

    View getView();

    IToast setAboveKeyboard(boolean z);

    IToast setAnimation(int i);

    IToast setDuration(long j);

    IToast setGravity(int i);

    IToast setGravity(int i, int i2, int i3);

    IToast setPriority(int i);

    IToast setText(String str);

    IToast setView(View view);

    void show();
}
