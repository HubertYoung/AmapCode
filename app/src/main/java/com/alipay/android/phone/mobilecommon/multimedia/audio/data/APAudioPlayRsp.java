package com.alipay.android.phone.mobilecommon.multimedia.audio.data;

public class APAudioPlayRsp extends APAudioRsp {
    private int extra;
    private int what;

    public int getWhat() {
        return this.what;
    }

    public void setWhat(int what2) {
        this.what = what2;
    }

    public int getExtra() {
        return this.extra;
    }

    public void setExtra(int extra2) {
        this.extra = extra2;
    }

    public String toString() {
        return "APAudioPlayRsp{what=" + this.what + ", extra=" + this.extra + ", super=" + super.toString() + '}';
    }
}
