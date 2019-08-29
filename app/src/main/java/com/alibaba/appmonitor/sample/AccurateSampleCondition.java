package com.alibaba.appmonitor.sample;

import java.util.Set;

@Deprecated
public class AccurateSampleCondition {
    public String getName() {
        return "";
    }

    public boolean hitCondition(String str) {
        return false;
    }

    public AccurateSampleCondition(String str, Set<String> set, int i) {
    }
}
