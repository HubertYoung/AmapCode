package com.alibaba.appmonitor.pool;

interface IPool {
    <T extends Reusable> void offer(T t);

    <T extends Reusable> T poll(Class<T> cls, Object... objArr);
}
