package com.autonavi.minimap.route.coach.widget;

import android.text.TextUtils;
import java.io.Serializable;

public class FilterDataItem implements Serializable {
    private static final long serialVersionUID = 8370907514829885398L;
    private boolean checked;
    private int endTime;
    private final boolean filterNone;
    private final int key;
    private final String name;
    private int startTime;

    public FilterDataItem(int i, String str) {
        this(i, str, false);
    }

    public FilterDataItem(int i, String str, boolean z) {
        this.key = i;
        this.name = str;
        this.filterNone = z;
    }

    public int getKey() {
        return this.key;
    }

    public boolean isFilterNone() {
        return this.filterNone;
    }

    public void setChecked(boolean z) {
        this.checked = z;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setTime(int i, int i2) {
        this.startTime = i;
        this.endTime = i2;
    }

    public String toString() {
        return this.name;
    }

    public boolean matchPeriod(String str, String str2) {
        if ((TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) || !this.checked) {
            return false;
        }
        if (!TextUtils.isEmpty(this.name) && this.name.contains(str) && !TextUtils.isEmpty(str)) {
            return true;
        }
        if (!TextUtils.isEmpty(this.name) && this.name.contains(str2) && !TextUtils.isEmpty(str2)) {
            return true;
        }
        int time = getTime(str);
        int time2 = getTime(str2);
        if (time == -1 && time2 == -1) {
            return false;
        }
        if (time == -1) {
            time = 0;
        } else if (time2 == -1) {
            time2 = time;
        }
        if (time <= this.startTime && time2 >= this.startTime) {
            return true;
        }
        if (time <= this.startTime || time >= this.endTime) {
            return false;
        }
        return true;
    }

    private int getTime(String str) {
        try {
            String[] split = str.split(":");
            if (split.length == 2) {
                return (Integer.parseInt(split[0]) * 60) + Integer.parseInt(split[1]);
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }
}
