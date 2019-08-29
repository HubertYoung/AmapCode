package com.autonavi.indoor.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.autonavi.indoor.entity.LocationResult;
import java.util.ArrayList;

public class MessageHelper {
    public static void setMessageString(Message message, String str) {
        Bundle data = message.getData();
        StringBuilder sb = new StringBuilder("String");
        sb.append(message.what);
        data.putString(sb.toString(), str);
    }

    public static String getMessageString(Message message) {
        Bundle data = message.getData();
        StringBuilder sb = new StringBuilder("String");
        sb.append(message.what);
        return data.getString(sb.toString());
    }

    public static void publishMessage(ArrayList<Handler> arrayList, int i) {
        synchronized (arrayList) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                Handler handler = arrayList.get(i2);
                handler.sendMessage(Message.obtain(handler, i));
            }
        }
    }

    public static void publishMessage(ArrayList<Handler> arrayList, int i, Bundle bundle) {
        synchronized (arrayList) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                Handler handler = arrayList.get(i2);
                Message obtain = Message.obtain(handler, i);
                obtain.setData(bundle);
                handler.sendMessage(obtain);
            }
        }
    }

    public static void publishMessage(ArrayList<Handler> arrayList, int i, Object obj) {
        synchronized (arrayList) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                Handler handler = arrayList.get(i2);
                if (isValidHandler(handler)) {
                    Message obtain = Message.obtain(handler, i);
                    obtain.obj = obj;
                    handler.sendMessage(obtain);
                }
            }
        }
    }

    public static boolean isValidHandler(Handler handler) {
        return (handler == null || handler.getLooper() == null || handler.getLooper().getThread() == null || !handler.getLooper().getThread().isAlive()) ? false : true;
    }

    public static void publishLocationResult(ArrayList<Handler> arrayList, boolean z, LocationResult locationResult, String str) {
        if (locationResult != null) {
            int i = z ? 208 : 210;
            synchronized (arrayList) {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Handler handler = arrayList.get(i2);
                    if (isValidHandler(handler)) {
                        handler.removeMessages(i);
                        Message obtainMessage = handler.obtainMessage(i);
                        obtainMessage.obj = locationResult;
                        if (!TextUtils.isEmpty(str)) {
                            setMessageString(obtainMessage, str);
                        }
                        handler.sendMessage(obtainMessage);
                    }
                }
            }
        }
    }
}
