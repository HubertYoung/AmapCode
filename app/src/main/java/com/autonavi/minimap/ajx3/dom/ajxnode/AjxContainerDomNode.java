package com.autonavi.minimap.ajx3.dom.ajxnode;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomGroupNode;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomNode;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.AjxViewManager;
import com.autonavi.minimap.ajx3.widget.view.Container;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;

public class AjxContainerDomNode extends AjxDomGroupNode {
    private static final String TAG = "ContainerNode";

    public AjxContainerDomNode(@NonNull JsDomNode jsDomNode) {
        super(jsDomNode);
    }

    public void createView(IAjxContext iAjxContext) {
        this.mView = new Container(iAjxContext);
        ((ViewGroup) this.mView).setMotionEventSplittingEnabled(false);
        AjxViewManager.registerInterfaceView(iAjxContext, this.mView);
    }

    public void setRootView(AjxView ajxView, IAjxContext iAjxContext) {
        super.setRootView(ajxView, iAjxContext);
        this.mAjxContext = iAjxContext;
        if (this.mView instanceof ViewExtension) {
            ((ViewExtension) this.mView).bind(this);
        }
        Container container = new Container(iAjxContext, true);
        ((ViewGroup) this.mView).addView(container);
        LayoutParams layoutParams = container.getLayoutParams();
        if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
            AbsoluteLayout.LayoutParams layoutParams2 = (AbsoluteLayout.LayoutParams) layoutParams;
            layoutParams2.x = DimensionUtils.standardUnitToPixel(getSize("left"));
            layoutParams2.y = DimensionUtils.standardUnitToPixel(getSize("top"));
        }
        layoutParams.width = DimensionUtils.standardUnitToPixel(getSize("width"));
        layoutParams.height = DimensionUtils.standardUnitToPixel(getSize("height"));
        initChildrenView(iAjxContext);
    }

    public void initEnterView(IAjxContext iAjxContext) {
        if (!isRoot() && (isTemplate() || this.mView == null)) {
            this.mAjxContext = iAjxContext;
            createView(iAjxContext);
        }
    }

    public void onChildAdd(AjxDomNode ajxDomNode, int i) {
        if (ajxDomNode != null && this.mAjxContext != null && getContainer() != null) {
            ajxDomNode.initView(this.mAjxContext);
            ajxDomNode.addToViewTree(getContainer(), getRealChildIndex(i));
            this.mAjxContext.getDomTree().saveNodeToMap(ajxDomNode);
        }
    }

    public void onChildRemove(AjxDomNode ajxDomNode) {
        super.onChildRemove(ajxDomNode);
        if (ajxDomNode != null && this.mAjxContext != null) {
            if (!(getContainer() == null || ajxDomNode.getEnterView() == null)) {
                getContainer().removeView(ajxDomNode.getEnterView());
            }
            this.mAjxContext.getDomTree().removeNodeFromMap(ajxDomNode);
        }
    }

    public void onChildReplace(AjxDomNode ajxDomNode, AjxDomNode ajxDomNode2, int i) {
        if (ajxDomNode != null && this.mAjxContext != null && getContainer() != null && ajxDomNode.getEnterView() != null) {
            int indexOfChild = getContainer().indexOfChild(ajxDomNode.getEnterView());
            if (indexOfChild >= 0) {
                getContainer().removeViewAt(indexOfChild);
                if (ajxDomNode2 != null) {
                    ajxDomNode2.initView(this.mAjxContext);
                    ajxDomNode2.addToViewTree(getContainer(), getRealChildIndex(i));
                }
                this.mAjxContext.getDomTree().removeNodeFromMap(ajxDomNode);
                this.mAjxContext.getDomTree().saveNodeToMap(ajxDomNode2);
            }
        }
    }
}
