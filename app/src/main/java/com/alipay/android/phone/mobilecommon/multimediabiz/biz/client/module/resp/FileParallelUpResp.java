package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class FileParallelUpResp extends FileUpResp {
    private boolean hasLeftRange = false;
    private List<Range> leftRanges = new ArrayList();

    public static class Range {
        public Integer end;
        public Integer start;

        public String toString() {
            return "Range{start=" + this.start + ", end=" + this.end + '}';
        }
    }

    public boolean isHasLeftRange() {
        return this.hasLeftRange;
    }

    public void setHasLeftRange(boolean hasLeftRange2) {
        this.hasLeftRange = hasLeftRange2;
    }

    public List<Range> getLeftRanges() {
        return this.leftRanges;
    }

    public void parseLeftRanges(String leftRanges2) {
        boolean z = true;
        if (!TextUtils.isEmpty(leftRanges2)) {
            for (String trim : leftRanges2.split(",")) {
                String[] beginEnd = trim.trim().split("-", 2);
                Range r = new Range();
                if (beginEnd.length == 1) {
                    r.start = Integer.valueOf(Integer.parseInt(beginEnd[0]));
                    r.end = null;
                } else if (beginEnd.length == 2) {
                    r.start = Integer.valueOf(Integer.parseInt(beginEnd[0]));
                    r.end = Integer.valueOf(Integer.parseInt(beginEnd[1]));
                }
                this.leftRanges.add(r);
            }
            if (this.leftRanges.isEmpty()) {
                z = false;
            }
            setHasLeftRange(z);
        }
    }

    public String toString() {
        return "FileParallelUpResp{leftRanges=" + this.leftRanges + ", hasLeftRange=" + this.hasLeftRange + ", super=" + super.toString() + '}';
    }
}
