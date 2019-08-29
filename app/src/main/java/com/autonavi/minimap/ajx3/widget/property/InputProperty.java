package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import android.text.method.PasswordTransformationMethod;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.view.Input;
import com.autonavi.minimap.ajx3.widget.view.TextArea;

public class InputProperty extends TextAreaProperty {
    public InputProperty(@NonNull Input input, @NonNull IAjxContext iAjxContext) {
        super(input, iAjxContext);
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        if (i != 1056964672) {
            super.updateStyle(i, obj, z);
        } else {
            updateType(obj);
        }
    }

    private void updateType(Object obj) {
        if ("password".equalsIgnoreCase((String) obj)) {
            ((TextArea) this.mView).setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            ((TextArea) this.mView).setTransformationMethod(null);
        }
    }

    /* access modifiers changed from: protected */
    public void updateText() {
        updateText(1);
    }
}
