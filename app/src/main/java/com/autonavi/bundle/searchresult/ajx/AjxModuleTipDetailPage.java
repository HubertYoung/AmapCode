package com.autonavi.bundle.searchresult.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleTipDetailPage;
import com.autonavi.minimap.ajx3.util.DimensionUtils;

public class AjxModuleTipDetailPage extends AbstractModuleTipDetailPage {
    private a mListener;

    public interface a {
        void a(int i);

        void a(String str, JsFunctionCallback jsFunctionCallback);
    }

    public AjxModuleTipDetailPage(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void notifyNative(String str, JsFunctionCallback jsFunctionCallback) {
        if (this.mListener != null) {
            this.mListener.a(str, jsFunctionCallback);
        }
    }

    public void topHeightChange(int i) {
        if (this.mListener != null) {
            this.mListener.a(DimensionUtils.standardUnitToPixel(getNativeContext(), (float) i));
        }
    }

    public void setListener(a aVar) {
        this.mListener = aVar;
    }
}
