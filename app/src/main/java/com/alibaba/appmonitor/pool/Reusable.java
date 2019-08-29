package com.alibaba.appmonitor.pool;

public interface Reusable {
    void clean();

    void fill(Object... objArr);
}
