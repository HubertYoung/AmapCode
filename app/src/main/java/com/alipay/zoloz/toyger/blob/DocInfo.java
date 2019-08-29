package com.alipay.zoloz.toyger.blob;

import android.graphics.Point;
import android.graphics.Rect;
import java.util.List;

public class DocInfo {
    public String docType;
    public Rect faceRect;
    public List<DocFieldInfo> fields;
    public int pageNo;
    public List<Point> region;

    public DocInfo() {
    }

    public DocInfo(String str, int i, List<Point> list, List<DocFieldInfo> list2, Rect rect) {
        this.docType = str;
        this.pageNo = i;
        this.region = list;
        this.fields = list2;
        this.faceRect = rect;
    }
}
