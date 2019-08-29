package com.alibaba.appmonitor.event;

import com.alibaba.appmonitor.pool.Reusable;

@Deprecated
public interface IRawEvent extends Reusable {
    UTEvent dumpToUTEvent();
}
