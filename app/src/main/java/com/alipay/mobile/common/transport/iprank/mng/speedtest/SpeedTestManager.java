package com.alipay.mobile.common.transport.iprank.mng.speedtest;

import android.content.Context;
import com.alipay.mobile.common.transport.iprank.mng.speedtest.impl.PingTest;
import com.alipay.mobile.common.transport.iprank.mng.speedtest.impl.SocketTest;
import com.alipay.mobile.common.transport.iprank.mng.speedtest.impl.SocketTestImpl;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SpeedTestManager implements ISpeedtest {
    public static final int FAKE_WIFI = -1000;
    public static final int MAX_OVERTIME_RTT = 9999;
    public static final int SPEEDTEST_ERROR = -1;
    public static final int TIMEOUT = 5000;
    private static SpeedTestManager b = null;
    private ArrayList<BaseSpeedTest> a = new ArrayList<>();

    public static SpeedTestManager getInstance(Context context) {
        if (b != null) {
            return b;
        }
        synchronized (SpeedTestManager.class) {
            try {
                if (b == null) {
                    b = new SpeedTestManager();
                }
            }
        }
        return b;
    }

    private SpeedTestManager() {
        this.a.add(new SocketTest());
        this.a.add(new PingTest());
        this.a.add(new SocketTestImpl());
    }

    public int speedTest(String ip, int port) {
        a();
        Iterator<BaseSpeedTest> it = this.a.iterator();
        while (it.hasNext()) {
            BaseSpeedTest speedTest = it.next();
            if (speedTest.isActivate()) {
                int rtt = speedTest.speedTest(ip, port);
                LogCatUtil.debug("IPR_SpeedTestManager", speedTest.getClass().getSimpleName() + " complete,the result: ip: " + ip + ",rtt: " + rtt);
                if (rtt >= 0) {
                    return rtt;
                }
                if (rtt == -1000) {
                    return -1000;
                }
            }
        }
        return -1;
    }

    private void a() {
        Collections.sort(this.a, new Comparator<BaseSpeedTest>() {
            public int compare(BaseSpeedTest lhs, BaseSpeedTest rhs) {
                if (lhs == null || rhs == null) {
                    return 0;
                }
                return rhs.getPriority() - lhs.getPriority();
            }
        });
    }
}
