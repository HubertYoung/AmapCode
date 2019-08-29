package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.view.Image;

public class ImageProperty extends BaseProperty<Image> {
    public ImageProperty(@NonNull Image image, @NonNull IAjxContext iAjxContext) {
        super(image, iAjxContext);
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        if (i == 1056964680) {
            this.mPictureHelper.updateFileMode(obj);
        } else if (i != 1056964684) {
            super.updateStyle(i, obj, z);
        } else {
            this.mPictureHelper.updateImageStretch(obj);
        }
    }

    /* access modifiers changed from: protected */
    public void updateAttribute(String str, Object obj) {
        if (((str.hashCode() == 114148 && str.equals("src")) ? (char) 0 : 65535) != 0) {
            super.updateAttribute(str, obj);
            return;
        }
        AjxDomNode node = getNode();
        if (node != null && node.containsAttribute("syncload")) {
            this.mPictureHelper.syncLoadFlag(true);
        }
        this.mPictureHelper.updateSrc(obj);
    }
}
