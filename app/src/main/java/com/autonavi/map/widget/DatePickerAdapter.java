package com.autonavi.map.widget;

import android.content.Context;
import android.content.res.Resources;
import com.autonavi.map.widget.wheel.TimePickerAdapter;
import com.autonavi.minimap.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerAdapter implements TimePickerAdapter {
    public static final int DEFAULT_MAX_VALUE = 14;
    private static final int DEFAULT_MIN_VALUE = 0;
    private int current;
    private Context mContext;
    private boolean mTomorrowAtFirstState;
    private int maxValue;
    private int minValue;

    public DatePickerAdapter(Context context) {
        this(context, 0, 14);
    }

    public DatePickerAdapter(Context context, int i, int i2) {
        this(context, i, i2, false);
    }

    public DatePickerAdapter(Context context, int i, int i2, boolean z) {
        this.minValue = i;
        this.maxValue = i2;
        this.mTomorrowAtFirstState = z;
        this.mContext = context;
    }

    public String getItem(int i) {
        Resources resources = this.mContext.getResources();
        Calendar instance = Calendar.getInstance();
        if (!this.mTomorrowAtFirstState) {
            if (i == 0) {
                return resources.getString(R.string.date_today);
            }
            if (this.maxValue < 5 && i == 1) {
                return resources.getString(R.string.date_today);
            }
            instance.set(6, instance.get(6) + i);
        } else if (this.maxValue < 4 && i == 0) {
            return resources.getString(R.string.date_tomorrow);
        } else {
            instance.set(6, instance.get(6) + i + 1);
        }
        this.current = i;
        return new SimpleDateFormat(this.mContext.getResources().getString(R.string.format_datepickeradapter_date), Locale.CHINA).format(instance.getTime());
    }

    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }

    public int getMaximumLength() {
        int length = Integer.toString(Math.max(Math.abs(this.maxValue), Math.abs(this.minValue))).length();
        return this.minValue < 0 ? length + 1 : length;
    }

    public int getCurrentIndex() {
        return this.current;
    }
}
