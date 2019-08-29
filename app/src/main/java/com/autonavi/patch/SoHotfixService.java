package com.autonavi.patch;

import android.app.IntentService;

public abstract class SoHotfixService extends IntentService {
    private static final String a = "SoHotfixService";

    public SoHotfixService() {
        super(a);
    }
}
