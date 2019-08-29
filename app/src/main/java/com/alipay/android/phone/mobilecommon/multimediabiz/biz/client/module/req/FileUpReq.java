package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener;
import java.io.File;

public class FileUpReq extends BaseUpReq<File> {
    public FileUpReq(File file) {
        this.inputSource = file;
    }

    public FileUpReq(File file, TransferredListener transferedListener) {
        this.inputSource = file;
        this.transferedListener = transferedListener;
    }

    public FileUpReq(FileUpReq src) {
        this.md5 = src.md5;
        this.gcid = src.gcid;
        this.ext = src.ext;
        this.inputSource = src.inputSource;
        this.startPos = src.startPos;
        this.endPos = src.endPos;
        this.skipRapid = src.skipRapid;
        this.transferedListener = src.transferedListener;
    }

    public File getFile() {
        return (File) getInputSource();
    }

    public long getTotalLength() {
        return ((File) this.inputSource).length();
    }

    public String getFileName() {
        return ((File) this.inputSource).getName();
    }
}
