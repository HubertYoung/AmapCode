package com.alipay.mobile.common.logging.impl;

import android.os.Process;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.api.LogEvent.Level;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class TraceLogEvent extends LogEvent {
    private static final long serialVersionUID = 1;
    private static ThreadLocal<StringBuilder> threadBuilder = new ThreadLocal<>();

    public TraceLogEvent(String tag, Level level, String msg, String tr) {
        StringBuilder builder = threadBuilder.get();
        if (builder == null) {
            builder = new StringBuilder();
            threadBuilder.set(builder);
        }
        long timestamp = System.currentTimeMillis();
        int threadId = Process.myTid();
        String threadName = Thread.currentThread().getName();
        builder.append(timestamp).append(' ').append(level);
        builder.append('/').append(tag).append(':');
        builder.append('[').append(threadId).append(':').append(threadName).append("] ");
        builder.append(msg);
        if (tr != null) {
            builder.append(Token.SEPARATOR).append(tr);
        }
        String message = builder.toString();
        builder.setLength(0);
        this.category = "applog";
        this.tag = tag;
        this.level = level;
        this.timeStamp = timestamp;
        this.message = message;
    }
}
