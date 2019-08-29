package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.app.NotificationManager;
import android.content.Context;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class LEHomeBadger extends ShortcutBadger {
    private static final String LEPHONE_PRIVATE_METHOD_SET_NOTIFICATION = "setNotificationSubscript";

    public LEHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) {
        executeBadge(i, "com.autonavi.map.activity.SplashActivity");
    }

    public void executeBadge(int i, String str) {
        try {
            setNotificationSubscript((NotificationManager) this.mContext.getSystemService("notification"), Class.forName(str), i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean setNotificationSubscript(NotificationManager notificationManager, Class cls, int i) {
        try {
            Object invoke = notificationManager.getClass().getMethod(LEPHONE_PRIVATE_METHOD_SET_NOTIFICATION, new Class[]{Class.class, Integer.class}).invoke(notificationManager, new Object[]{cls, Integer.valueOf(i)});
            return (invoke == null || !(invoke instanceof Boolean)) ? false : ((Boolean) invoke).booleanValue();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.xxx.xxx"});
    }
}
