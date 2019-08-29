package com.alipay.zoloz.toyger.bean;

import android.graphics.Bitmap;
import com.alipay.zoloz.toyger.algorithm.TGFaceAttr;
import com.alipay.zoloz.toyger.algorithm.TGFaceState;
import com.alipay.zoloz.toyger.algorithm.TGFrame;
import com.alipay.zoloz.toyger.upload.UploadContent;

public class ToygerFrame {
    public Bitmap bestBitmap;
    public ToygerError error = ToygerError.NORMAL;
    public FrameType frameType = FrameType.INIT;
    public TGFaceAttr tgFaceAttr = new TGFaceAttr();
    public TGFaceState tgFaceState = new TGFaceState();
    public TGFrame tgFrame = new TGFrame();
    public UploadContent uploadContent;

    public String toString() {
        return "ToygerFrame{frameType=" + this.frameType + ", error=" + this.error + ", tgFrame=" + (this.tgFrame != null ? "***" : "null") + ", tgFaceState=" + (this.tgFaceState != null ? "***" : "null") + ", tgFaceAttr=" + (this.tgFaceAttr != null ? "***" : "null") + ", uploadContent=" + this.uploadContent + ", bestBitmap=" + (this.bestBitmap != null ? "***" : "null") + '}';
    }
}
