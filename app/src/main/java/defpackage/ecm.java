package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.Random;

/* renamed from: ecm reason: default package */
/* compiled from: FootNaviKcalInfo */
public final class ecm {
    public static String a(int i) {
        if (i == 0) {
            return "";
        }
        int nextInt = new Random().nextInt();
        if (i < 5) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_leve_1_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_1_des_2);
        } else if (i < 10) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_2_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_2_des_2);
        } else if (i < 15) {
            int i2 = nextInt % 3;
            if (i2 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_3_des_1);
            } else if (i2 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_3_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_3_des_3);
            }
        } else if (i < 25) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_4_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_4_des_2);
        } else if (i < 35) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_5_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_5_des_2);
        } else if (i < 45) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_6_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_6_des_2);
        } else if (i < 55) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_7_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_7_des_2);
        } else if (i < 65) {
            int i3 = nextInt % 3;
            if (i3 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_8_des_1);
            } else if (i3 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_8_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_8_des_3);
            }
        } else if (i < 75) {
            int i4 = nextInt % 3;
            if (i4 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_9_des_1);
            } else if (i4 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_9_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_9_des_3);
            }
        } else if (i < 100) {
            int i5 = nextInt % 3;
            if (i5 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_10_des_1);
            } else if (i5 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_10_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_10_des_3);
            }
        } else if (i < 125) {
            int i6 = nextInt % 3;
            if (i6 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_11_des_1);
            } else if (i6 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_11_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_11_des_3);
            }
        } else if (i < 150) {
            int i7 = nextInt % 3;
            if (i7 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_12_des_1);
            } else if (i7 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_12_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_12_des_3);
            }
        } else if (i < 200) {
            int i8 = nextInt % 3;
            if (i8 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_13_des_1);
            } else if (i8 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_13_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_13_des_3);
            }
        } else if (i < 250) {
            int i9 = nextInt % 3;
            if (i9 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_14_des_1);
            } else if (i9 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_14_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_14_des_3);
            }
        } else if (i < 300) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_15_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_15_des_2);
        } else if (i < 350) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_16_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_16_des_2);
        } else if (i < 400) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_17_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_17_des_2);
        } else if (i < 450) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_18_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_18_des_2);
        } else if (i < 500) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_19_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_19_des_2);
        } else if (i < 550) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_20_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_20_des_2);
        } else if (i < 600) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_21_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_21_des_2);
        } else if (i < 680) {
            if ((nextInt & 1) == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_22_des_1);
            }
            return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_22_des_2);
        } else if (i < 1360) {
            int i10 = nextInt % 3;
            if (i10 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_23_des_1);
            } else if (i10 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_23_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_23_des_3);
            }
        } else {
            int i11 = nextInt % 3;
            if (i11 == 0) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_24_des_1);
            } else if (i11 == 1) {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_24_des_2);
            } else {
                return AMapAppGlobal.getApplication().getString(R.string.foot_navi_level_24_des_3);
            }
        }
    }
}
