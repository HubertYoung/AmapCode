package org.altbeacon.bluetooth;

import java.util.ArrayList;
import java.util.List;

/* compiled from: BleAdvertisement */
public final class a {
    private List<c> a = b();
    private byte[] b;

    public a(byte[] bytes) {
        this.b = bytes;
    }

    private List<c> b() {
        ArrayList pdus = new ArrayList();
        int index = 0;
        do {
            c pdu = c.a(this.b, index);
            if (pdu != null) {
                index = pdu.b() + index + 1;
                pdus.add(pdu);
            }
            if (pdu == null) {
                break;
            }
        } while (index < this.b.length);
        return pdus;
    }

    public final List<c> a() {
        return this.a;
    }
}
