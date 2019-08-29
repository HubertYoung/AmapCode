package com.alibaba.analytics.core.store;

public interface ILogChangeListener {
    void onDelete(long j, long j2);

    void onInsert(long j, long j2);
}
