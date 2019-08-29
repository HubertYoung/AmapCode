package com.autonavi.bundle.routecommute.bundle;

public class CommuteVapp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        aho.a(new Runnable() {
            public final void run() {
                axv axv = (axv) a.a.a(axv.class);
                if (axv != null) {
                    axv.i();
                }
            }
        }, 150);
    }
}
