package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import java.util.HashMap;
import java.util.Map;

public class ImageStType {
    public static final int ST_IM_TYPE_BIG = 1;
    public static final int ST_IM_TYPE_NONE = 2;
    public static final int ST_IM_TYPE_REGION_CENTER = 4;
    public static final int ST_IM_TYPE_REGION_CENTER_BOTTOM = 9;
    public static final int ST_IM_TYPE_REGION_CENTER_TOP = 8;
    public static final int ST_IM_TYPE_REGION_LEFT_BOTTOM = 7;
    public static final int ST_IM_TYPE_REGION_LEFT_CENTER = 6;
    public static final int ST_IM_TYPE_REGION_LEFT_TOP = 5;
    public static final int ST_IM_TYPE_REGION_RIGHT_BOTTOM = 12;
    public static final int ST_IM_TYPE_REGION_RIGHT_CENTER = 11;
    public static final int ST_IM_TYPE_REGION_RIGHT_TOP = 10;
    public static final int ST_IM_TYPE_SCALE = 0;
    public static final int ST_IM_TYPE_SMART_CROP = 13;
    private static final Map<CutScaleType, Integer> a = new HashMap();
    private static final Map<Integer, Integer> b = new HashMap();

    static {
        a.put(CutScaleType.KEEP_RATIO, Integer.valueOf(0));
        a.put(CutScaleType.SCALE_AUTO_LIMIT, Integer.valueOf(0));
        a.put(CutScaleType.NONE, Integer.valueOf(2));
        a.put(CutScaleType.CENTER_CROP, Integer.valueOf(4));
        a.put(CutScaleType.AUTO_CUT_EXACTLY, Integer.valueOf(4));
        a.put(CutScaleType.REGION_CROP_LEFT_TOP, Integer.valueOf(5));
        a.put(CutScaleType.REGION_CROP_CENTER_TOP, Integer.valueOf(8));
        a.put(CutScaleType.REGION_CROP_RIGHT_TOP, Integer.valueOf(10));
        a.put(CutScaleType.REGION_CROP_LEFT_CENTER, Integer.valueOf(6));
        a.put(CutScaleType.REGION_CROP_CENTER_CENTER, Integer.valueOf(4));
        a.put(CutScaleType.REGION_CROP_RIGHT_CENTER, Integer.valueOf(11));
        a.put(CutScaleType.REGION_CROP_LEFT_BOTTOM, Integer.valueOf(7));
        a.put(CutScaleType.REGION_CROP_CENTER_BOTTOM, Integer.valueOf(9));
        a.put(CutScaleType.REGION_CROP_RIGHT_BOTTOM, Integer.valueOf(12));
        a.put(CutScaleType.SMART_CROP, Integer.valueOf(13));
        b.put(Integer.valueOf(0), Integer.valueOf(4));
        b.put(Integer.valueOf(3), Integer.valueOf(6));
        b.put(Integer.valueOf(4), Integer.valueOf(11));
        b.put(Integer.valueOf(1), Integer.valueOf(8));
        b.put(Integer.valueOf(2), Integer.valueOf(9));
    }

    public static int getType(CutScaleType scaleType) {
        Integer integer = a.get(scaleType);
        if (integer == null) {
            return 0;
        }
        return integer.intValue();
    }

    public static int getType(int align) {
        Integer integer = b.get(Integer.valueOf(align));
        if (integer == null) {
            return 4;
        }
        return integer.intValue();
    }
}
