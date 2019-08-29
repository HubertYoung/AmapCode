package com.alibaba.appmonitor.event;

import com.alibaba.appmonitor.pool.Reusable;
import java.util.HashMap;
import java.util.Map;

public class UTEvent implements Reusable {
    public String arg1;
    public String arg2;
    public String arg3;
    public Map<String, String> args;
    public int eventId;
    public String page;

    public void clean() {
        this.page = null;
        this.eventId = 0;
        this.arg1 = null;
        this.arg2 = null;
        this.arg3 = null;
        if (this.args != null) {
            this.args.clear();
        }
    }

    public void fill(Object... objArr) {
        if (this.args == null) {
            this.args = new HashMap();
        }
    }
}
