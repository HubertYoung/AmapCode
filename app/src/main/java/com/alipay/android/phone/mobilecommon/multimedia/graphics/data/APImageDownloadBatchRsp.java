package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APImageDownloadBatchRsp {
    private singleImage[] images;
    private APImageRetMsg retmsg;

    class singleImage {
        private String DFilePath;
        private String FileId;

        singleImage() {
        }

        public String getFileId() {
            return this.FileId;
        }

        public void setFileId(String fileId) {
            this.FileId = fileId;
        }

        public String getDFilePath() {
            return this.DFilePath;
        }

        public void setDFilePath(String dFilePath) {
            this.DFilePath = dFilePath;
        }
    }

    public APImageRetMsg getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(APImageRetMsg retmsg2) {
        this.retmsg = retmsg2;
    }

    public singleImage[] getImages() {
        return this.images;
    }

    public void setImages(singleImage[] images2) {
        this.images = images2;
    }
}
