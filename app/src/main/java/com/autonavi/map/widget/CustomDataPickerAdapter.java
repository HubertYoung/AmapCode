package com.autonavi.map.widget;

import com.autonavi.map.widget.wheel.TimePickerAdapter;

public class CustomDataPickerAdapter implements TimePickerAdapter {
    private int mCurrent;
    private int[] mData;

    public int getMaximumLength() {
        return 0;
    }

    public int getItemsCount() {
        if (this.mData != null) {
            return this.mData.length;
        }
        return 0;
    }

    public String getItem(int i) {
        if (this.mData == null || i >= this.mData.length) {
            return null;
        }
        this.mCurrent = i;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mData[i]);
        return sb.toString();
    }

    public int getCurrentIndex() {
        return this.mCurrent;
    }

    public void setData(int[] iArr) {
        this.mData = iArr;
    }

    public int[] getData() {
        return this.mData;
    }
}
