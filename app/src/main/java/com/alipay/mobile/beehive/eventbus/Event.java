package com.alipay.mobile.beehive.eventbus;

import android.text.TextUtils;
import com.alipay.mobile.beehive.util.MiscUtil;
import java.util.ArrayList;
import java.util.List;

public class Event {
    public Object data;
    public Object key;
    public String name;
    private List<String> uniqueIds;

    public Event(String eventName, Object eventData) {
        this.name = eventName;
        this.data = eventData;
        this.key = createEventKey(eventName, eventData);
    }

    public String toString() {
        return MiscUtil.safeToString(this, "name=" + MiscUtil.safeToString(this.name) + ", data=" + MiscUtil.safeToString(this.data));
    }

    public static boolean keyEqual(Object first, Object second) {
        if (first == second) {
            return true;
        }
        if (!(first instanceof String) || !(second instanceof String)) {
            return false;
        }
        return TextUtils.equals((String) first, (String) second);
    }

    public static boolean isValid(Event evt) {
        return (evt == null || evt.key == null) ? false : true;
    }

    private static Object createEventKey(String eventName, Object eventData) {
        if (!TextUtils.isEmpty(eventName)) {
            return eventName;
        }
        if (eventData != null) {
            return eventData.getClass();
        }
        return null;
    }

    public void setUniqueIds(List<String> l) {
        this.uniqueIds = l;
    }

    public void addConsumeUid(String uid) {
        if (!TextUtils.isEmpty(uid)) {
            if (this.uniqueIds == null) {
                this.uniqueIds = new ArrayList();
            }
            this.uniqueIds.add(uid);
        }
    }

    public boolean isConsumed(String uid) {
        if (this.uniqueIds == null) {
            return false;
        }
        for (String s : this.uniqueIds) {
            if (TextUtils.equals(uid, s)) {
                return true;
            }
        }
        return false;
    }
}
