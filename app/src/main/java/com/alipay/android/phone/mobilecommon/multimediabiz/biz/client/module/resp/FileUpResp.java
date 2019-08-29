package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

public class FileUpResp extends DjangoFileInfoRespWrapper {
    private boolean isRapid;
    private int range;

    public boolean isRapid() {
        return this.isRapid;
    }

    public void setRapid(boolean isRapid2) {
        this.isRapid = isRapid2;
    }

    public int getRange() {
        return this.range;
    }

    public void setRange(int range2) {
        this.range = range2;
    }

    public String toString() {
        return "FileUpResp{isRapid=" + this.isRapid + ", isSuccess=" + isSuccess() + "}\n" + super.toString();
    }
}
