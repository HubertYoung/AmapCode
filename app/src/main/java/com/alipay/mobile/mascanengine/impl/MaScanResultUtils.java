package com.alipay.mobile.mascanengine.impl;

import android.graphics.Rect;
import com.alipay.ma.common.a.a;
import com.alipay.ma.common.b.b;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.e;
import com.alipay.mobile.mascanengine.MaScanResult;
import com.alipay.mobile.mascanengine.MaScanType;
import com.alipay.mobile.mascanengine.MultiMaScanResult;
import java.util.ArrayList;
import java.util.List;

public class MaScanResultUtils {
    public static final String TAG = "MaScanResultUtils";

    protected static MultiMaScanResult fromMaResults(c[] maResults) {
        if (maResults == null) {
            return null;
        }
        MultiMaScanResult multiMaScanResult = new MultiMaScanResult();
        List results = new ArrayList();
        for (c fromMaResult : maResults) {
            MaScanResult maScanResult = fromMaResult(fromMaResult);
            if (maScanResult.rect != null) {
                results.add(maScanResult);
            }
        }
        if (results.isEmpty()) {
            results.add(fromMaResult(maResults[0]));
        }
        try {
            multiMaScanResult.maScanResults = (MaScanResult[]) results.toArray(new MaScanResult[results.size()]);
            return multiMaScanResult;
        } catch (ClassCastException e) {
            a.c(TAG, e.getMessage());
            return null;
        }
    }

    protected static MaScanResult fromMaResult(c maResult) {
        a.a(TAG, "fromMaResult(" + maResult + ")");
        if (maResult == null) {
            return null;
        }
        MaScanResult result = new MaScanResult();
        result.text = maResult.b();
        result.type = MaScanType.valueOf(maResult.a().toString());
        result.ecLevel = maResult.g;
        result.bitErrors = maResult.h;
        result.version = maResult.i;
        result.strategy = maResult.j;
        if (maResult.a() != e.QR || !(maResult instanceof b)) {
            return result;
        }
        result.rect = new Rect(((b) maResult).c, ((b) maResult).d, ((b) maResult).c + ((b) maResult).e, ((b) maResult).d + ((b) maResult).f);
        return result;
    }
}
