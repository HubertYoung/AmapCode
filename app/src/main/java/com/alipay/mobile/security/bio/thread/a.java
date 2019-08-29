package com.alipay.mobile.security.bio.thread;

import com.alipay.mobile.security.bio.service.BioSysBehaviorType;

/* compiled from: WatchLogThread */
final /* synthetic */ class a {
    static final /* synthetic */ int[] a = new int[BioSysBehaviorType.values().length];

    static {
        try {
            a[BioSysBehaviorType.API.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[BioSysBehaviorType.CLICK.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[BioSysBehaviorType.NET.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            a[BioSysBehaviorType.EVENT.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            a[BioSysBehaviorType.METHOD.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
    }
}
