package com.autonavi.minimap.ajx3.acanvas;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;

@SuppressLint({"ViewConstructor"})
public class AjxCanvasViewProperty extends BaseProperty<AjxCanvasView> {
    private boolean mContextBind = false;

    public AjxCanvasViewProperty(AjxCanvasView ajxCanvasView, IAjxContext iAjxContext) {
        super(ajxCanvasView, iAjxContext);
    }

    public void updateAttribute(String str, Object obj) {
        if (((str.hashCode() == 3355 && str.equals("id")) ? (char) 0 : 65535) != 0) {
            super.updateAttribute(str, obj);
        } else {
            updateId((String) obj);
        }
    }

    private void updateId(String str) {
        if (!TextUtils.isEmpty(str) && !this.mContextBind) {
            this.mContextBind = true;
            AjxCanvasBridgeManager.getSingleton().getBridge(getAjxContext()).bindContext2D(str, ((AjxCanvasView) this.mView).getCanvasView());
        }
    }
}
