package com.autonavi.bundle.routecommute.common.bean;

import com.autonavi.bundle.routecommute.common.bean.TipsMatrix.UserType;

public final class AddressMatrix {
    private static final UserType[][] a = {new UserType[]{UserType.DOUBLE_SET, UserType.WORKSET_HOMEMINE, UserType.WORK_SET}, new UserType[]{UserType.HOMESET_WORKMINE, UserType.DOUBLE_MINE, UserType.WORK_MINE}, new UserType[]{UserType.HOME_SET, UserType.HOME_MINE, UserType.NOTING_SET}};

    public enum CompanyType {
        COMPANY_SET,
        COMPANY_MINE,
        COMPANY_NOTHING
    }

    public enum HomeType {
        HOME_SET,
        HOME_MINE,
        HOME_NOTHING
    }

    public static UserType a(CompanyType companyType, HomeType homeType) {
        int ordinal = companyType.ordinal();
        int ordinal2 = homeType.ordinal();
        if (ordinal > 2 || ordinal2 > 2) {
            return UserType.NOTING_SET;
        }
        return a[ordinal][ordinal2];
    }
}
