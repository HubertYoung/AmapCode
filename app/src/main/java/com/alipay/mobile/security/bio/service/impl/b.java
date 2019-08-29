package com.alipay.mobile.security.bio.service.impl;

import com.alipay.mobile.security.bio.task.ActionType;

/* compiled from: BioTaskServiceImpl */
final /* synthetic */ class b {
    static final /* synthetic */ int[] a = new int[ActionType.values().length];

    static {
        try {
            a[ActionType.INIT.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[ActionType.RUN.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[ActionType.DONE.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
    }
}
