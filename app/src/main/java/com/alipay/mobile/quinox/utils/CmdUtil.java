package com.alipay.mobile.quinox.utils;

import com.alipay.mobile.quinox.log.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class CmdUtil {
    private static final String TAG = "CmdUtil";

    private CmdUtil() {
    }

    public static void execCommand(String str) {
        execCommand(str, false);
    }

    public static String execCommand(String str, boolean z) {
        Runtime runtime = Runtime.getRuntime();
        if (z) {
            try {
                Process exec = runtime.exec(str);
                if (exec.waitFor() != 0) {
                    StringBuilder sb = new StringBuilder("exit value = ");
                    sb.append(exec.exitValue());
                    Log.i((String) TAG, sb.toString());
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                StringBuilder sb2 = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        return sb2.toString();
                    }
                    sb2.append(readLine);
                    sb2.append("\n");
                }
            } catch (Exception unused) {
            }
        } else {
            runtime.exec(str);
            return null;
        }
    }
}
