package com.autonavi.minimap.ajx3.dom.managers;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsAttribute;
import com.autonavi.minimap.ajx3.dom.JsDomEventFrameInit;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxFrameDomNode;
import java.util.List;

public class AjxFrameManager extends AjxUiEventManager {
    public void destroy() {
    }

    public AjxFrameManager(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public boolean frameInit(JsDomEventFrameInit jsDomEventFrameInit) {
        int i = 0;
        if (jsDomEventFrameInit.node == null) {
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventFrameInit.node.createAjxNode();
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(createAjxNode.getId());
        View enterView = findNodeById.getEnterView();
        List<AjxDomNode> children = createAjxNode.getChildren();
        if (!(enterView instanceof ViewGroup) || children == null) {
            return false;
        }
        int childShowIndex = getChildShowIndex(createAjxNode);
        if (childShowIndex >= 0) {
            for (AjxDomNode next : children) {
                ((AjxFrameDomNode) findNodeById).addChild(next, i);
                changeFrameShow(childShowIndex, i, next);
                i++;
            }
        }
        return true;
    }

    public int getChildShowIndex(AjxDomNode ajxDomNode) {
        String str = (String) ajxDomNode.getAttributeValue("index");
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    public int getChildShowIndex(JsAttribute jsAttribute) {
        if ("index".equals(jsAttribute.key)) {
            String str = jsAttribute.value;
            if (!TextUtils.isEmpty(str)) {
                try {
                    return Integer.parseInt(str);
                } catch (NumberFormatException unused) {
                }
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public void changeFrameShow(int i, AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null && children.size() != 0 && i >= 0 && i < children.size() && (ajxDomNode.getEnterView() instanceof ViewGroup)) {
                int i2 = 0;
                for (AjxDomNode changeFrameShow : children) {
                    changeFrameShow(i, i2, changeFrameShow);
                    i2++;
                }
            }
        }
    }

    private void changeFrameShow(int i, int i2, AjxDomNode ajxDomNode) {
        View enterView = ajxDomNode.getEnterView();
        if (enterView != null) {
            if (i == i2) {
                enterView.setVisibility(0);
                return;
            }
            enterView.setVisibility(8);
        }
    }
}
