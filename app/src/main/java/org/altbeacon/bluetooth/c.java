package org.altbeacon.bluetooth;

import android.annotation.TargetApi;

/* compiled from: Pdu */
public final class c {
    private byte a;
    private int b;
    private int c;
    private int d;
    private byte[] e;

    @TargetApi(9)
    public static c a(byte[] bytes, int startIndex) {
        c pdu = null;
        if (bytes.length - startIndex >= 2) {
            byte length = bytes[startIndex];
            if (length > 0) {
                byte type = bytes[startIndex + 1];
                int firstIndex = startIndex + 2;
                if (firstIndex < bytes.length) {
                    pdu = new c();
                    pdu.d = startIndex + length;
                    if (pdu.d >= bytes.length) {
                        pdu.d = bytes.length - 1;
                    }
                    pdu.a = type;
                    pdu.b = length;
                    pdu.c = firstIndex;
                    pdu.e = bytes;
                }
            }
        }
        return pdu;
    }

    public final byte a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }

    public final int d() {
        return this.d;
    }
}
