package com.amap.bundle.drivecommon.tools;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMemberNames
@KeepName
@KeepImplementations
public class TripSpUtil {
    public static boolean getTripBroadCastState(Context context) {
        return !getString(context, "KEY_TRIP_CONFIG_BROADCAST_STATE", "2").equals("0");
    }

    public static void setTripBroadCastState(Context context, boolean z) {
        setString(context, "KEY_TRIP_CONFIG_BROADCAST_STATE", z ? "2" : "0");
    }

    public static boolean getCruiseBroadCastState(Context context) {
        return !getString(context, "KEY_CRUISE_CONFIG_BROADCAST_STATE", "2").equals("0");
    }

    public static boolean getCommuteBroadCastState(Context context) {
        return !getString(context, "KEY_COMMUTE_CONFIG_BROADCAST_STATE", "2").equals("0");
    }

    public static String getString(Context context, String str, String str2) {
        return new MapSharePreference((String) "NAMESPACE_TRIP_BUSINESS").getStringValue(str, str2);
    }

    public static String setString(Context context, String str, String str2) {
        new MapSharePreference((String) "NAMESPACE_TRIP_BUSINESS").edit().putString(str, str2).apply();
        return str2;
    }
}
