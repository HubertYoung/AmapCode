package com.autonavi.minimap.widget;

import com.autonavi.map.widget.wheel.TimePickerAdapter;

public class NumericTimePickerAdapter implements TimePickerAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;
    private int current;
    private String format;
    private int maxValue;
    private int minValue;

    public NumericTimePickerAdapter() {
        this(0, 9);
    }

    public NumericTimePickerAdapter(int i, int i2) {
        this(i, i2, null);
    }

    public NumericTimePickerAdapter(int i, int i2, String str) {
        this.minValue = i;
        this.maxValue = i2;
        this.format = str;
    }

    public String getItem(int i) {
        if (i < 0 || i >= getItemsCount()) {
            return null;
        }
        int i2 = this.minValue + i;
        this.current = i2;
        if (this.format == null) {
            return Integer.toString(i2);
        }
        return String.format(this.format, new Object[]{Integer.valueOf(i2)});
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
