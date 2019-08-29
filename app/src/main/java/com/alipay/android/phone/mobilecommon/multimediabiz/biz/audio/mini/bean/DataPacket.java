package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk.SilkUtils;

public class DataPacket {
    public byte[] data;
    public int shortSize;
    public short[] shorts;
    public int size;

    public DataPacket(byte[] data2, int size2) {
        this.data = new byte[size2];
        System.arraycopy(data2, 0, this.data, 0, size2);
        this.size = size2;
    }

    public DataPacket(short[] data2, int size2) {
        this.shorts = new short[size2];
        System.arraycopy(data2, 0, this.shorts, 0, size2);
        this.shortSize = size2;
    }

    public short[] getShorts() {
        if (this.shorts == null && this.data != null) {
            this.shorts = SilkUtils.getShortArray(this.data, this.size);
            this.shortSize = this.shorts.length;
        }
        return this.shorts;
    }

    public int getShortSize() {
        return this.shortSize;
    }
}
