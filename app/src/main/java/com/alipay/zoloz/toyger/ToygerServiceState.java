package com.alipay.zoloz.toyger;

public class ToygerServiceState {
    public static final int FINISH = 5;
    public static final int INIT = 2;
    public static final int LOADED = 1;
    public static final int READY = 3;
    public static final int RUNNING = 4;
    private static final String TAG = "TOYGER";
    public static final int UNLOAD = 0;
    private int mToygerServiceState = 0;

    public void set(int i) {
        this.mToygerServiceState = i;
        new StringBuilder("ToygerServiceState.set() : newState=").append(this.mToygerServiceState);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean update(int r5) {
        /*
            r4 = this;
            r0 = 1
            int r2 = r4.mToygerServiceState
            r1 = 0
            switch(r2) {
                case 0: goto L_0x002d;
                case 1: goto L_0x0032;
                case 2: goto L_0x0037;
                case 3: goto L_0x003c;
                case 4: goto L_0x0041;
                case 5: goto L_0x0046;
                default: goto L_0x0007;
            }
        L_0x0007:
            r0 = r1
        L_0x0008:
            if (r0 == 0) goto L_0x004a
            r4.mToygerServiceState = r5
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "ToygerServiceState.update() : oldState="
            r1.<init>(r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ", newState="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r2 = ", mToygerServiceState="
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r4.mToygerServiceState
            r1.append(r2)
        L_0x002c:
            return r0
        L_0x002d:
            switch(r5) {
                case 1: goto L_0x0008;
                default: goto L_0x0030;
            }
        L_0x0030:
            r0 = r1
            goto L_0x0008
        L_0x0032:
            switch(r5) {
                case 0: goto L_0x0008;
                case 1: goto L_0x0035;
                case 2: goto L_0x0008;
                default: goto L_0x0035;
            }
        L_0x0035:
            r0 = r1
            goto L_0x0008
        L_0x0037:
            switch(r5) {
                case 0: goto L_0x0008;
                case 1: goto L_0x003a;
                case 2: goto L_0x003a;
                case 3: goto L_0x0008;
                default: goto L_0x003a;
            }
        L_0x003a:
            r0 = r1
            goto L_0x0008
        L_0x003c:
            switch(r5) {
                case 0: goto L_0x0008;
                case 4: goto L_0x0008;
                default: goto L_0x003f;
            }
        L_0x003f:
            r0 = r1
            goto L_0x0008
        L_0x0041:
            switch(r5) {
                case 0: goto L_0x0008;
                case 1: goto L_0x0044;
                case 2: goto L_0x0044;
                case 3: goto L_0x0008;
                case 4: goto L_0x0044;
                case 5: goto L_0x0008;
                default: goto L_0x0044;
            }
        L_0x0044:
            r0 = r1
            goto L_0x0008
        L_0x0046:
            switch(r5) {
                case 0: goto L_0x0008;
                case 1: goto L_0x0049;
                case 2: goto L_0x0049;
                case 3: goto L_0x0008;
                default: goto L_0x0049;
            }
        L_0x0049:
            goto L_0x0007
        L_0x004a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "ToygerServiceState.update() : oldState="
            r1.<init>(r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ", newState="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r2 = ", mToygerServiceState="
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r4.mToygerServiceState
            r1.append(r2)
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.zoloz.toyger.ToygerServiceState.update(int):boolean");
    }

    public boolean isReady() {
        return this.mToygerServiceState == 3;
    }

    public boolean isRunning() {
        return this.mToygerServiceState == 4;
    }

    public boolean isLoaded() {
        return this.mToygerServiceState == 1;
    }

    public boolean isUnLoad() {
        return this.mToygerServiceState == 0;
    }

    public boolean isFinish() {
        return this.mToygerServiceState == 5;
    }
}
