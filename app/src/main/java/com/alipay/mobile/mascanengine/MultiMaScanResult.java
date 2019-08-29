package com.alipay.mobile.mascanengine;

import com.alipay.mobile.bqcscanservice.BQCScanResult;

public class MultiMaScanResult extends BQCScanResult {
    public boolean candidate;
    public int classicFrameCount;
    public int frameCount;
    public MaScanResult[] maScanResults;
    public String readerParams;
    public boolean rsBinarized;
    public int rsBinarizedCount;
    public long rsInitTime;
}
