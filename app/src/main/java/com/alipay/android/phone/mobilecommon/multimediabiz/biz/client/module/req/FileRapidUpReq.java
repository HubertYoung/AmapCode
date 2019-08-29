package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.LiteStringUtils;

public class FileRapidUpReq extends AbstractHttpReq {
    private String ext;
    private String gcid;
    private String md5;
    private Boolean setPublic;

    public FileRapidUpReq(String md52, String gcid2) {
        if (!LiteStringUtils.isBlank(md52) || !LiteStringUtils.isBlank(gcid2)) {
            this.md5 = md52;
            this.gcid = gcid2;
            return;
        }
        throw new IllegalArgumentException("Parameter md5 or gcid can not be null !");
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext2) {
        this.ext = ext2;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md52) {
        this.md5 = md52;
    }

    public String getGcid() {
        return this.gcid;
    }

    public void setGcid(String gcid2) {
        this.gcid = gcid2;
    }

    public Boolean getPublic() {
        return this.setPublic;
    }

    public void setPublic(Boolean setPublic2) {
        this.setPublic = setPublic2;
    }
}
