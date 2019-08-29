package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.InputProperty;

@SuppressLint({"ViewConstructor"})
public class Input extends TextArea {
    public int getLines() {
        return 1;
    }

    public Input(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext);
        setLines(1);
        setSingleLine(true);
        setGravity(16);
        setClickable(true);
    }

    /* access modifiers changed from: protected */
    public BaseProperty createProperty(@NonNull IAjxContext iAjxContext) {
        return new InputProperty(this, iAjxContext);
    }
}
