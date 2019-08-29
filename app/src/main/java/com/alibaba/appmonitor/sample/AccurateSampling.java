package com.alibaba.appmonitor.sample;

import java.util.Map;

@Deprecated
public class AccurateSampling extends AMConifg {
    public AccurateSampling(int i) {
    }

    public Boolean isSampled(int i, Map<String, String> map) {
        return Boolean.FALSE;
    }
}
