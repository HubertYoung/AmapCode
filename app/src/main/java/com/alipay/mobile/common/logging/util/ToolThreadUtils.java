package com.alipay.mobile.common.logging.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class ToolThreadUtils {
    public static String ACTION_TOOLS_THREAD_WITH_START = "action.tools.thread.START";
    public static String ACTION_TOOLS_THREAD_WITH_START_END_END = "action.tools.thread.START_END.END";
    public static String ACTION_TOOLS_THREAD_WITH_START_END_START = "action.tools.thread.START_END.START";
    private static ToolThreadUtils a;
    private Context b;
    private LocalBroadcastManager c;

    public static synchronized ToolThreadUtils getInstance(Context context) {
        ToolThreadUtils toolThreadUtils;
        synchronized (ToolThreadUtils.class) {
            try {
                if (a == null) {
                    a = new ToolThreadUtils(context);
                }
                toolThreadUtils = a;
            }
        }
        return toolThreadUtils;
    }

    public ToolThreadUtils(Context context) {
        this.b = context;
        if (this.b != null && this.b.getApplicationContext() != null) {
            this.c = LocalBroadcastManager.getInstance(this.b);
        }
    }

    public void start(boolean needEnd) {
        if (this.b != null && this.c != null) {
            Intent intent = new Intent();
            if (needEnd) {
                intent.setAction(ACTION_TOOLS_THREAD_WITH_START_END_START);
            } else {
                intent.setAction(ACTION_TOOLS_THREAD_WITH_START);
            }
            this.c.sendBroadcast(intent);
        }
    }

    public void end() {
        if (this.b != null && this.c != null) {
            this.c.sendBroadcast(new Intent(ACTION_TOOLS_THREAD_WITH_START_END_END));
        }
    }
}
