package com.alibaba.baichuan.android.trade.adapter.ut;

import java.util.Map;

public interface IAlibcUserTracker {
    void sendCustomHit(String str, long j, String str2, Map map);

    void sendCustomHit(String str, String str2, Map map);
}
