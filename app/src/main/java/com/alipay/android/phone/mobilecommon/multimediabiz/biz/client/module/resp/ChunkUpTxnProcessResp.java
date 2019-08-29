package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;

public class ChunkUpTxnProcessResp extends BaseResp {
    private Data data;
    private boolean isRapid;

    public static class Data {
        private String gcid;
        private String md5;

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
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data2) {
        this.data = data2;
    }

    public boolean isRapid() {
        return this.isRapid;
    }

    public void setRapid(boolean isRapid2) {
        this.isRapid = isRapid2;
    }
}
