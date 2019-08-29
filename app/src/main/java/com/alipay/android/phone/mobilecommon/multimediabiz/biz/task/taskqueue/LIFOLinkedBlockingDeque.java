package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.taskqueue;

import android.annotation.TargetApi;
import java.util.concurrent.LinkedBlockingDeque;

@TargetApi(9)
public class LIFOLinkedBlockingDeque<T> extends LinkedBlockingDeque<T> {
    private static final long serialVersionUID = -4114786347960826192L;

    public boolean offer(T e) {
        return super.offerFirst(e);
    }

    public T remove() {
        return super.removeFirst();
    }

    public T poll() {
        return super.pollFirst();
    }
}
