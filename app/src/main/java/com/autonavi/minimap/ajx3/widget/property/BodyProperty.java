package com.autonavi.minimap.ajx3.widget.property;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.AjxView.AnyTouchListener;

public class BodyProperty extends BaseProperty<AjxView> {
    private AnyTouchListener mAnyTouchListener;

    public BodyProperty(@NonNull AjxView ajxView, @NonNull IAjxContext iAjxContext) {
        super(ajxView, iAjxContext);
    }

    /* access modifiers changed from: protected */
    public void updateAttribute(String str, Object obj) {
        if ("offsetScrollY".equalsIgnoreCase(str)) {
            float parseFloat = (obj == null || TextUtils.isEmpty(obj.toString())) ? 0.0f : Float.parseFloat(String.valueOf(obj));
            if (((AjxView) this.mView).getHelper() != null) {
                ((AjxView) this.mView).getHelper().setOffsetY(parseFloat);
            }
        } else if ("anytouch".equalsIgnoreCase(str)) {
            if (obj == null) {
                ((AjxView) this.mView).setAnyTouchListener(null);
            } else {
                if (this.mAnyTouchListener == null) {
                    this.mAnyTouchListener = new AnyTouchListener() {
                        public void onTouch() {
                            BodyProperty.this.mAjxContext.invokeJsEvent(new Builder().setEventName("anytouch").setNodeId(BodyProperty.this.getNode().getId()).build());
                        }
                    };
                }
                ((AjxView) this.mView).setAnyTouchListener(this.mAnyTouchListener);
            }
        } else if ("viewCreated".equalsIgnoreCase(str)) {
            this.mAjxContext.invokeJsEvent(new Builder().setEventName("viewCreated").setNodeId(getNode().getId()).build());
        } else if ("statusbarstyle".equalsIgnoreCase(str) && obj != null) {
            try {
                if (euk.a()) {
                    Activity activity = (Activity) ((AjxView) this.mView).getContext();
                    if ("lightContent".equals(obj.toString())) {
                        euk.a(activity, 0);
                    } else {
                        euk.b(activity);
                    }
                }
            } catch (Exception unused) {
            }
        }
        super.updateAttribute(str, obj);
    }

    public Object getAttribute(String str) {
        return super.getAttribute(str);
    }
}
