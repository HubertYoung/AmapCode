package com.alibaba.baichuan.android.trade.utils;

import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.concurrent.atomic.AtomicInteger;

public class c {
    private static final AtomicInteger a = new AtomicInteger(59994);

    public static int a(String str) {
        int andIncrement = a.getAndIncrement();
        StringBuilder sb = new StringBuilder();
        sb.append(andIncrement);
        sb.append(" is allocated for onActivityResult request code for ");
        sb.append(str);
        AlibcLogger.i("kernel", sb.toString());
        return andIncrement;
    }
}
