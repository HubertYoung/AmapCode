package com.alipay.mobile.common.task.transaction;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Transaction implements Runnable {
    private static final AtomicInteger a = new AtomicInteger(0);
    final String id = ("Transaction_" + a.getAndIncrement());

    public Transaction() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public final String getId() {
        return this.id;
    }
}
