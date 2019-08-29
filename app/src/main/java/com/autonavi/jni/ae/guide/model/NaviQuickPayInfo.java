package com.autonavi.jni.ae.guide.model;

public class NaviQuickPayInfo {
    private int payEvent;
    private PayResult payResult;
    private ShowResult showResult;

    public void setShowResult(ShowResult showResult2) {
        if (showResult2 == null) {
            this.payEvent = 0;
            return;
        }
        this.showResult = showResult2;
        this.payEvent = 1;
    }

    public void setPayResult(PayResult payResult2) {
        if (payResult2 == null) {
            this.payEvent = 0;
            return;
        }
        this.payResult = payResult2;
        this.payEvent = 2;
    }
}
