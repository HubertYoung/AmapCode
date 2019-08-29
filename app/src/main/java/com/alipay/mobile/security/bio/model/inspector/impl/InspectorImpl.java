package com.alipay.mobile.security.bio.model.inspector.impl;

import android.content.Context;
import com.alipay.mobile.security.bio.exception.BioObjectNotInitialException;
import com.alipay.mobile.security.bio.model.inspector.Inspector;

public class InspectorImpl implements Inspector {
    Context a;

    public InspectorImpl(Context context) {
        if (context == null) {
            this.a = context;
            return;
        }
        throw new BioObjectNotInitialException("Context");
    }

    public int checkEnvironment() {
        return 0;
    }
}
