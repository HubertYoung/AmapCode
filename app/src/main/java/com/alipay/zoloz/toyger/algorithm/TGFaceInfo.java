package com.alipay.zoloz.toyger.algorithm;

public class TGFaceInfo {
    public TGFaceAttr attr;
    public TGFrame frame;

    public TGFaceInfo() {
    }

    public TGFaceInfo(TGFrame tGFrame, TGFaceAttr tGFaceAttr) {
        this.frame = tGFrame;
        this.attr = tGFaceAttr;
    }
}
