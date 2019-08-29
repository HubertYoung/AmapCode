package com.autonavi.minimap.net;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.autonavi.server.aos.serverkey;
import java.util.List;

public class Sign {
    public static String getSign(String str) {
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(serverkey.getAosChannel());
        sb.append(str);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(serverkey.getAosKey());
        try {
            return serverkey.sign(sb.toString().getBytes());
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getSign(List<String> list) {
        String str = "";
        if (list != null && list.size() > 0) {
            for (String next : list) {
                if (next != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(next);
                    str = sb.toString();
                }
            }
        }
        return getSign(str);
    }
}
