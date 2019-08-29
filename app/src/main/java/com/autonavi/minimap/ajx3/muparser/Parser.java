package com.autonavi.minimap.ajx3.muparser;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public final class Parser {
    private static native double nativeEval(String str, Parcel parcel) throws ParserException;

    private static native double nativeEval(String str, String str2, double d) throws ParserException;

    public static double eval(String str, Parcel parcel) throws ParserException {
        if (TextUtils.isEmpty(str)) {
            return 0.0d;
        }
        return nativeEval(str, parcel);
    }

    public static double eval(String str, String str2, double d) throws ParserException {
        if (TextUtils.isEmpty(str)) {
            return 0.0d;
        }
        return nativeEval(str, str2, d);
    }
}
