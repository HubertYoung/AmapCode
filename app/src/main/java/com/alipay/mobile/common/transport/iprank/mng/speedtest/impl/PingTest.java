package com.alipay.mobile.common.transport.iprank.mng.speedtest.impl;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.iprank.mng.speedtest.BaseSpeedTest;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PingTest implements BaseSpeedTest {

    public class Ping {
        public static int runcmd(String cmd) {
            Runtime runtime = Runtime.getRuntime();
            String command = cmd.trim();
            long startTime = System.currentTimeMillis();
            Process proc = runtime.exec(command);
            proc.waitFor();
            long endTime = System.currentTimeMillis();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuilder resultBuilder = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                resultBuilder.append(line);
            }
            reader.close();
            if (a(resultBuilder.toString().toLowerCase().trim())) {
                return (int) (endTime - startTime);
            }
            return -1;
        }

        private static boolean a(String result) {
            if (TextUtils.isEmpty(result) || result.indexOf("bytes from ") <= 0) {
                return false;
            }
            return true;
        }
    }

    public int speedTest(String ip, int port) {
        try {
            return Ping.runcmd("ping -c1 -s1 -w1 " + ip);
        } catch (Exception e) {
            LogCatUtil.error("IPR_PingTest", "PingTest exception", e);
            return -1;
        }
    }

    public boolean isActivate() {
        return false;
    }

    public int getPriority() {
        return 0;
    }
}
