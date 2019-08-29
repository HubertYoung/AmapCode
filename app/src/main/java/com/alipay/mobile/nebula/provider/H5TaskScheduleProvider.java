package com.alipay.mobile.nebula.provider;

public interface H5TaskScheduleProvider {
    boolean addIdleTask(Runnable runnable, String str, int i);
}
