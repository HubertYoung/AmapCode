package com.alipay.android.phone.mobilecommon.multimedia.video.data;

import android.graphics.Bitmap;

public class APTakePicRsp {
    public Bitmap bitmap;
    public int cameraFacing = -1;
    public byte[] data;
    public int dataType = 0;
    public int height;
    public int orientation;
    public String savePath;
    public int width;
}
