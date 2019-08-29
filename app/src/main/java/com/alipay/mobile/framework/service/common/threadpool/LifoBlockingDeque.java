package com.alipay.mobile.framework.service.common.threadpool;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LifoBlockingDeque<E> extends LinkedBlockingDeque<E> {
    private static final long serialVersionUID = 7262784894221011657L;

    public LifoBlockingDeque() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean offer(E e) {
        return super.offerFirst(e);
    }

    public boolean offer(E e, long timeout, TimeUnit unit) {
        return super.offerFirst(e, timeout, unit);
    }

    public boolean add(E e) {
        return super.offerFirst(e);
    }

    public void put(E e) {
        super.putFirst(e);
    }
}
