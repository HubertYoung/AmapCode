package com.alibaba.appmonitor.event;

@Deprecated
public class RawAlarmEvent extends Event implements IRawEvent {
    public UTEvent dumpToUTEvent() {
        return null;
    }

    public String getErrorCode() {
        return "";
    }

    public String getErrorMsg() {
        return "";
    }

    public int getFailCount() {
        return 0;
    }

    public int getSuccessCount() {
        return 0;
    }

    public void setFail(String str, String str2) {
    }

    public void setSuccess() {
    }

    public void clean() {
        super.clean();
    }
}
