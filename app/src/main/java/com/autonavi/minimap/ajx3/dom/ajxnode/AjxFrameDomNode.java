package com.autonavi.minimap.ajx3.dom.ajxnode;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomGroupNode;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomNode;
import com.autonavi.minimap.ajx3.widget.AjxViewManager;
import com.autonavi.minimap.ajx3.widget.view.FrameContainer;

public class AjxFrameDomNode extends AjxDomGroupNode {
    public AjxFrameDomNode(@NonNull JsDomNode jsDomNode) {
        super(jsDomNode);
    }

    public void createView(IAjxContext iAjxContext) {
        this.mView = new FrameContainer(iAjxContext);
        ((ViewGroup) this.mView).setMotionEventSplittingEnabled(false);
        AjxViewManager.registerInterfaceView(iAjxContext, this.mView);
    }

    public void initEnterView(IAjxContext iAjxContext) {
        if (isTemplate() || this.mView == null) {
            this.mAjxContext = iAjxContext;
            createView(iAjxContext);
            if (getChildren() != null) {
                for (AjxDomNode ajxDomNode : this.mChildren) {
                    ajxDomNode.initView(iAjxContext);
                    ajxDomNode.addToViewTree(getContainer());
                }
            }
        }
    }

    public void onChildAdd(AjxDomNode ajxDomNode, int i) {
        if (ajxDomNode != null && this.mAjxContext != null && getContainer() != null) {
            ajxDomNode.initView(this.mAjxContext);
            if (ajxDomNode.getEnterView() != null) {
                ajxDomNode.addToViewTree(getContainer(), getRealChildIndex(i));
                if ((this.mView instanceof ViewGroup) && ((ViewGroup) this.mView).getChildCount() > 1) {
                    ajxDomNode.getEnterView().setVisibility(8);
                }
            }
            this.mAjxContext.getDomTree().saveNodeToMap(ajxDomNode);
        }
    }

    public void onChildReplace(AjxDomNode ajxDomNode, AjxDomNode ajxDomNode2, int i) {
        super.onChildReplace(ajxDomNode, ajxDomNode2, i);
    }

    public void onChildRemove(AjxDomNode ajxDomNode) {
        super.onChildRemove(ajxDomNode);
    }
}
