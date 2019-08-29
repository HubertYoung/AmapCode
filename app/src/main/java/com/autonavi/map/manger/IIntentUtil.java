package com.autonavi.map.manger;

public interface IIntentUtil {
    boolean haveSuspendTask();

    boolean processIntent();

    void setMapCallBack(cqd cqd);

    void startSuspendTask();
}
