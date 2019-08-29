package com.xiaomi.push.service;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.xiaomi.channel.commonutils.string.d;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class aj {
    private static Object a = new Object();
    private static Map<String, Queue<String>> b = new HashMap();

    public static boolean a(XMPushService xMPushService, String str, String str2) {
        synchronized (a) {
            try {
                SharedPreferences sharedPreferences = xMPushService.getSharedPreferences("push_message_ids", 0);
                Queue queue = b.get(str);
                if (queue == null) {
                    String[] split = sharedPreferences.getString(str, "").split(",");
                    Queue linkedList = new LinkedList();
                    for (String add : split) {
                        linkedList.add(add);
                    }
                    b.put(str, linkedList);
                    queue = linkedList;
                }
                if (queue.contains(str2)) {
                    return true;
                }
                queue.add(str2);
                if (queue.size() > 25) {
                    queue.poll();
                }
                String a2 = d.a((Collection<?>) queue, (String) ",");
                Editor edit = sharedPreferences.edit();
                edit.putString(str, a2);
                edit.commit();
                return false;
            }
        }
    }
}
