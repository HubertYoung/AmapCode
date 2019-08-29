package com.uc.crashsdk;

import com.uc.crashsdk.b.a;
import java.io.File;

/* compiled from: ProGuard */
final class o extends a {
    o(String str) {
        super(str);
    }

    public final boolean a() {
        File file = new File(this.a);
        if (!file.exists()) {
            return false;
        }
        file.delete();
        return true;
    }
}
