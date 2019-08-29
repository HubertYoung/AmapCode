package org.altbeacon.beacon.b;

/* compiled from: AbstractAndroidLogger */
abstract class a implements e {
    a() {
    }

    protected static String a(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
    }
}
