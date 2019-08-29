package com.alibaba.appmonitor.event;

@Deprecated
public class RawCountEvent extends Event implements IRawEvent {
    public UTEvent dumpToUTEvent() {
        return null;
    }

    public double getValue() {
        return 0.0d;
    }

    public void setValue(double d) {
    }

    public void clean() {
        super.clean();
    }
}
