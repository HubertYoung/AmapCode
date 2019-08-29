package com.alipay.mobile.mascanengine.impl;

import android.graphics.Bitmap;
import android.graphics.YuvImage;
import android.os.Bundle;
import com.alipay.ma.analyze.a.a;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.e;
import com.alipay.ma.decode.DecodeResult;
import com.alipay.ma.decode.MaDecode;
import com.alipay.ma.parser.Ma4GParSer;
import com.alipay.ma.parser.MaARParSer;
import com.alipay.ma.parser.MaBarParSer;
import com.alipay.ma.parser.MaDMParSer;
import com.alipay.ma.parser.MaGen3ParSer;
import com.alipay.ma.parser.MaParSer;
import com.alipay.ma.parser.MaQrParSer;
import com.alipay.ma.parser.MaTBAntiFakeParSer;
import com.alipay.mobile.binarize.BinarizeScanEngineImpl;
import com.alipay.mobile.bqcscanservice.BQCScanEngine;
import com.alipay.mobile.mascanengine.MaScanEngineService;
import com.alipay.mobile.mascanengine.MaScanResult;
import java.util.ArrayList;
import java.util.List;

public class MaScanEngineServiceImpl extends MaScanEngineService {
    private List<MaParSer> a = new ArrayList();

    public Class<? extends BQCScanEngine> getEngineClazz() {
        return BinarizeScanEngineImpl.class;
    }

    public MaScanResult process(String imgFilePath) {
        if (imgFilePath == null) {
            return null;
        }
        c maResult = a.a(imgFilePath);
        if (maResult != null) {
            return MaScanResultUtils.fromMaResult(maResult);
        }
        return null;
    }

    public MaScanResult process(Bitmap img) {
        if (img == null || img.isRecycled()) {
            return null;
        }
        DecodeResult result = MaDecode.codeDecodePictureWithQr(img, 34304);
        if (result != null) {
            return MaScanResultUtils.fromMaResult(new c(e.QR, result.strCode, result.ecLevel, result.bitErrors, result.version, result.strategy));
        }
        return null;
    }

    public MaScanResult processARCode(byte[] yuv, int previewFormat, int imageWidth, int imageHeight) {
        if (yuv == null || imageWidth <= 0 || imageHeight <= 0) {
            return null;
        }
        YuvImage yuvImage = new YuvImage(yuv, previewFormat, imageWidth, imageHeight, null);
        if (a.a()) {
            a.a(this.a);
        }
        c[] maResults = a.a(yuvImage, null, e.ARCODE);
        if (maResults == null || maResults.length <= 0) {
            return null;
        }
        return MaScanResultUtils.fromMaResult(maResults[0]);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.a.add(new MaQrParSer());
        this.a.add(new MaBarParSer());
        this.a.add(new MaGen3ParSer());
        this.a.add(new MaDMParSer());
        this.a.add(new Ma4GParSer());
        this.a.add(new MaTBAntiFakeParSer());
        this.a.add(new MaARParSer());
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
