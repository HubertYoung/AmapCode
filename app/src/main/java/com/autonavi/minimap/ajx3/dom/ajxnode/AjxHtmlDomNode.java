package com.autonavi.minimap.ajx3.dom.ajxnode;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomNode;
import com.autonavi.minimap.ajx3.widget.AjxViewManager;
import com.autonavi.minimap.ajx3.widget.view.Html;

public class AjxHtmlDomNode extends AjxDomNode {
    public AjxHtmlDomNode(@NonNull JsDomNode jsDomNode) {
        super(jsDomNode);
    }

    public void createView(IAjxContext iAjxContext) {
        this.mView = new Html(iAjxContext);
        AjxViewManager.registerInterfaceView(iAjxContext, this.mView);
    }
}
