package com.alipay.mobile.mascanengine;

import android.graphics.Rect;
import com.alipay.mobile.bqcscanservice.BQCScanResult;

public class MaScanResult extends BQCScanResult {
    public int bitErrors;
    public char ecLevel;
    public Rect rect;
    public int strategy;
    public String text;
    public MaScanType type;
    public int version;
}
