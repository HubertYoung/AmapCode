package com.autonavi.minimap.ajx3.dom.managers;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomEventNodeAttribute;
import com.autonavi.minimap.ajx3.dom.JsDomEventNodeProperty;

public class AjxPropertyManager extends AjxUiEventManager {
    public void destroy() {
    }

    public AjxPropertyManager(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public boolean attributeChangeEvent(JsDomEventNodeAttribute jsDomEventNodeAttribute) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventNodeAttribute.nodeId);
        if (findNodeById == null || jsDomEventNodeAttribute.attr == null) {
            return false;
        }
        findNodeById.setAttribute(jsDomEventNodeAttribute.attr, true);
        if (findNodeById.getTag() == 1056964756) {
            AjxFrameManager ajxFrameManager = this.mAjxContext.getDomTree().getAjxFrameManager();
            int childShowIndex = ajxFrameManager.getChildShowIndex(jsDomEventNodeAttribute.attr);
            if (childShowIndex >= 0) {
                ajxFrameManager.changeFrameShow(childShowIndex, findNodeById);
            }
        } else {
            findNodeById.updateDiffProperty();
        }
        return true;
    }

    public boolean styleChangeEvent(JsDomEventNodeProperty jsDomEventNodeProperty) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventNodeProperty.nodeId);
        if (findNodeById == null || jsDomEventNodeProperty.prop == null) {
            return false;
        }
        findNodeById.setStyle(jsDomEventNodeProperty.prop, true);
        findNodeById.updateDiffProperty();
        return true;
    }
}
