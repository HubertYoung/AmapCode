package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;
import java.util.List;

public class FileChunksInfoResp extends BaseResp {
    @JSONField(name = "data")
    private ChunkInfo[] chunksInfo;

    public static class ChunkInfo {
        private long chunkNo;
        private long chunkSize;
        private String extensionType;
        private String md5;
        private long offset;
        private List<TFS> tfsList;

        public long getChunkNo() {
            return this.chunkNo;
        }

        public void setChunkNo(long chunkNo2) {
            this.chunkNo = chunkNo2;
        }

        public long getOffset() {
            return this.offset;
        }

        public void setOffset(long offset2) {
            this.offset = offset2;
        }

        public long getChunkSize() {
            return this.chunkSize;
        }

        public void setChunkSize(long chunkSize2) {
            this.chunkSize = chunkSize2;
        }

        public String getExtensionType() {
            return this.extensionType;
        }

        public void setExtensionType(String extensionType2) {
            this.extensionType = extensionType2;
        }

        public String getMd5() {
            return this.md5;
        }

        public void setMd5(String md52) {
            this.md5 = md52;
        }

        public List<TFS> getTfsList() {
            return this.tfsList;
        }

        public void setTfsList(List<TFS> tfsList2) {
            this.tfsList = tfsList2;
        }
    }

    public static class TFS {
        private String key;
        private long length;
        private String md5;
        private long offset;

        public long getOffset() {
            return this.offset;
        }

        public void setOffset(long offset2) {
            this.offset = offset2;
        }

        public long getLength() {
            return this.length;
        }

        public void setLength(long length2) {
            this.length = length2;
        }

        public String getMd5() {
            return this.md5;
        }

        public void setMd5(String md52) {
            this.md5 = md52;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key2) {
            this.key = key2;
        }
    }

    public ChunkInfo[] getChunksInfo() {
        return this.chunksInfo;
    }

    public void setChunksInfo(ChunkInfo[] chunksInfo2) {
        this.chunksInfo = chunksInfo2;
    }
}
