package com.alipay.mobile.beehive.api;

import java.util.List;
import java.util.Map;

public interface PoiExtExecutor {
    void addShareUser(String str);

    void collectLocation(List<Map<String, String>> list);

    void sendToFriend(String str, String str2, String str3);
}
