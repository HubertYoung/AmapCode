package com.alipay.mobile.nebulacore.dev.sampler;

import com.alibaba.fastjson.JSONObject;

public class ThreadSampler extends AbstractSampler {
    private final JSONObject d = new JSONObject();

    public ThreadSampler(long sampleInterval) {
        super(sampleInterval);
    }

    /* access modifiers changed from: 0000 */
    public final JSONObject a() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        Thread[] slackThreads = new Thread[(topGroup.activeCount() * 2)];
        int actualSize = topGroup.enumerate(slackThreads);
        Thread[] actualThreads = new Thread[actualSize];
        System.arraycopy(slackThreads, 0, actualThreads, 0, actualSize);
        this.d.put((String) "size", (Object) Integer.valueOf(actualThreads.length));
        return this.d;
    }
}
