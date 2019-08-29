package anet.channel.strategy;

import java.io.Serializable;

class ConnHistoryItem implements Serializable {
    private static final long serialVersionUID = 5245740801355223771L;
    byte a = 0;
    long b = 0;
    long c = 0;

    ConnHistoryItem() {
    }

    /* access modifiers changed from: 0000 */
    public final int a() {
        int i = 0;
        for (int i2 = this.a & 255; i2 > 0; i2 >>= 1) {
            i += i2 & 1;
        }
        return i;
    }
}
