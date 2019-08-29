package com.alipay.android.phone.androidannotations.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {
    private PermissionUtils() {
    }

    public static boolean verifyPermissions(int... grantResults) {
        int[] arr$ = grantResults;
        int len$ = grantResults.length;
        for (int i$ = 0; i$ < len$; i$++) {
            if (arr$[i$] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasSelfPermissions(Context context, String... permissions) {
        String[] arr$ = permissions;
        try {
            int len$ = permissions.length;
            for (int i$ = 0; i$ < len$; i$++) {
                if (ContextCompat.checkSelfPermission(context, arr$[i$]) != 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            if (VERSION.SDK_INT >= 23) {
                return false;
            }
        }
        return true;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        String[] arr$ = permissions;
        try {
            int len$ = permissions.length;
            for (int i$ = 0; i$ < len$; i$++) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, arr$[i$])) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String... permissions) {
        String[] arr$ = permissions;
        try {
            int len$ = permissions.length;
            for (int i$ = 0; i$ < len$; i$++) {
                if (fragment.shouldShowRequestPermissionRationale(arr$[i$])) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static void requestPermissions(Activity activity, String[] reqPermissionsPermissions, int requestCode) {
        String[] noPermissions = getNeedCheckPermissions(activity, reqPermissionsPermissions);
        if (noPermissions.length > 0) {
            try {
                ActivityCompat.requestPermissions(activity, noPermissions, requestCode);
            } catch (Exception e) {
            }
        }
    }

    public static void requestPermissions(Fragment fragment, String[] reqPermissionsPermissions, int requestCode) {
        String[] noPermissions = getNeedCheckPermissions(fragment.getActivity(), reqPermissionsPermissions);
        if (noPermissions.length > 0) {
            try {
                fragment.requestPermissions(noPermissions, requestCode);
            } catch (Exception e) {
            }
        }
    }

    public static String[] getNeedCheckPermissions(Context context, String... reqPermissionsPermissions) {
        List noPermissionsList = new ArrayList();
        String[] arr$ = reqPermissionsPermissions;
        int len$ = reqPermissionsPermissions.length;
        for (int i$ = 0; i$ < len$; i$++) {
            String permission = arr$[i$];
            if (!hasSelfPermissions(context, permission)) {
                noPermissionsList.add(permission);
            }
        }
        return (String[]) noPermissionsList.toArray(new String[0]);
    }
}
