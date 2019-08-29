package com.autonavi.bundle.routecommute.common.bean;

public final class TipsMatrix {
    private static final int[][][] a = {new int[][]{new int[]{1, 4, 9, 0}, new int[]{2, 3, 9, 0}, new int[]{1, 3, 9, 0}}, new int[][]{new int[]{5, 8, 7, 0}, new int[]{6, 7, 5, 0}, new int[]{5, 7, 0, 0}}, new int[][]{new int[]{1, 8, 7, 0}, new int[]{2, 7, 1, 0}, new int[]{1, 7, 0, 0}}, new int[][]{new int[]{5, 4, 3, 0}, new int[]{6, 3, 5, 0}, new int[]{5, 3, 0, 0}}, new int[][]{new int[]{11, 11, 11, 11}, new int[]{1, 0, 1, 0}, new int[]{0, 0, 0, 0}}, new int[][]{new int[]{0, 3, 3, 0}, new int[]{12, 12, 12, 12}, new int[]{0, 0, 0, 0}}, new int[][]{new int[]{11, 11, 11, 11}, new int[]{12, 12, 12, 12}, new int[]{0, 0, 0, 0}}, new int[][]{new int[]{11, 11, 11, 11}, new int[]{12, 12, 12, 12}, new int[]{0, 0, 0, 0}}, new int[][]{new int[]{11, 11, 11, 11}, new int[]{12, 12, 12, 12}, new int[]{0, 0, 0, 0}}};

    public enum DisType {
        WORK_DIS,
        HOME_DIS,
        CPOINT_DIS,
        INVALID_DIS
    }

    public enum TimeType {
        WORKTO_TIME,
        WORKOFF_TIME,
        INVALID__TIME
    }

    public enum UserType {
        DOUBLE_SET,
        DOUBLE_MINE,
        HOMESET_WORKMINE,
        WORKSET_HOMEMINE,
        HOME_SET,
        WORK_SET,
        HOME_MINE,
        WORK_MINE,
        NOTING_SET
    }

    public static int a(UserType userType, TimeType timeType, DisType disType) {
        int ordinal = userType.ordinal();
        int ordinal2 = timeType.ordinal();
        int ordinal3 = disType.ordinal();
        if (ordinal > 8 || ordinal2 > 2 || ordinal3 > 3) {
            return 0;
        }
        return a[ordinal][ordinal2][ordinal3];
    }
}
