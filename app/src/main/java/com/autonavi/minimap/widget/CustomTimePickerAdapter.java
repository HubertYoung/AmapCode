package com.autonavi.minimap.widget;

import com.autonavi.map.widget.wheel.TimePickerAdapter;

public class CustomTimePickerAdapter implements TimePickerAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;
    private static final int DEFAULT_STEP = 1;
    private int current;
    private String format;
    private int maxValue;
    private int minValue;
    private int step;

    public CustomTimePickerAdapter() {
        this(0, 9, 1);
    }

    public CustomTimePickerAdapter(int i, int i2, int i3) {
        this(i, i2, i3, null);
    }

    public CustomTimePickerAdapter(int i, int i2, int i3, String str) {
        this.minValue = i;
        this.maxValue = i2;
        if (i2 <= i) {
            throw new RuntimeException("maxValue can't be smaller than minValue!");
        }
        this.step = i3 == 0 ? 1 : i3;
        this.format = str;
    }

    public int getItemsCount() {
        return ((this.maxValue - this.minValue) + 1) / this.step;
    }

    public String getItem(int i) {
        if (i < 0 || i >= getItemsCount()) {
            return null;
        }
        int i2 = this.minValue + (i * this.step);
        this.current = i2;
        if (this.format == null) {
            return Integer.toString(i2);
        }
        return String.format(this.format, new Object[]{Integer.valueOf(i2)});
    }

    public int getMaximumLength() {
        int length = Integer.toString(Math.max(Math.abs(this.maxValue), Math.abs(this.minValue))).length();
        return this.minValue < 0 ? length + 1 : length;
    }

    public int getCurrentIndex() {
        return this.current / this.step;
    }

    public void setMaxValue(int i) {
        this.maxValue = i;
    }

    public void setMinValue(int i) {
        this.minValue = i;
    }
}
