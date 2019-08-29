package com.alipay.zoloz.toyger.blob;

import android.graphics.Point;
import android.graphics.Rect;
import java.util.List;

public class FaceInfo {
    public float confidence;
    public String feaVersion;
    public String feature;
    public List<Point> points;
    public float quality;
    public Rect rect;

    public FaceInfo() {
    }

    public FaceInfo(Rect rect2, List<Point> list, float f, float f2, String str, String str2) {
        this.rect = rect2;
        this.points = list;
        this.confidence = f;
        this.quality = f2;
        this.feature = str;
        this.feaVersion = str2;
    }
}
