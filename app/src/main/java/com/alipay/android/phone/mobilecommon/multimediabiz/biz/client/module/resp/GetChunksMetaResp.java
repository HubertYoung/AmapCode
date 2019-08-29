package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;

public class GetChunksMetaResp extends BaseResp {
    @JSONField(name = "data")
    private ChunkMeta[] chunksMeta;

    public static class ChunkMeta {
        private String createTime;
        private String id;
        private String md5;
        private String sequence;
        private String size;

        public String getId() {
            return this.id;
        }

        public void setId(String id2) {
            this.id = id2;
        }

        public String getSequence() {
            return this.sequence;
        }

        public void setSequence(String sequence2) {
            this.sequence = sequence2;
        }

        public String getSize() {
            return this.size;
        }

        public void setSize(String size2) {
            this.size = size2;
        }

        public String getMd5() {
            return this.md5;
        }

        public void setMd5(String md52) {
            this.md5 = md52;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String createTime2) {
            this.createTime = createTime2;
        }
    }

    public ChunkMeta[] getChunksMeta() {
        return this.chunksMeta;
    }

    public void setChunksMeta(ChunkMeta[] chunksMeta2) {
        this.chunksMeta = chunksMeta2;
    }
}
