package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos;

import java.util.ArrayList;

public class ApiInfoUtil {
    public static ArrayList<String> getIpInfosByHost(String hostName) {
        ArrayList ipList = new ArrayList();
        ipList.add(hostName);
        return ipList;
    }
}
