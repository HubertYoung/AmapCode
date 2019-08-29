package com.alipay.zoloz.toyger.blob;

import java.util.List;

public class BlobElem {
    public byte[] content;
    public DocInfo docInfo;
    public List<FaceInfo> faceInfos;
    public int idx;
    public String subType;
    public String type;
    public String version;

    public BlobElem() {
    }

    public BlobElem(String str, String str2, int i, String str3, byte[] bArr, List<FaceInfo> list, DocInfo docInfo2) {
        this.type = str;
        this.subType = str2;
        this.idx = i;
        this.version = str3;
        this.content = bArr;
        this.faceInfos = list;
        this.docInfo = docInfo2;
    }
}
