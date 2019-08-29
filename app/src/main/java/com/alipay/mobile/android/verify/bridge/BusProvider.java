package com.alipay.mobile.android.verify.bridge;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public final class BusProvider {
    private static final Bus a = new Bus(ThreadEnforcer.MAIN);

    private BusProvider() {
    }

    public static Bus getInstance() {
        return a;
    }
}
