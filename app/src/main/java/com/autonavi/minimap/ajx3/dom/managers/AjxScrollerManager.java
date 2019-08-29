package com.autonavi.minimap.ajx3.dom.managers;

import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.context.AjxContextHandlerCallback;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomGroupNode;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomEventScrollerInit;
import com.autonavi.minimap.ajx3.dom.JsDomScrollIntoView;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxScrollerDomNode;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.lang.ref.WeakReference;
import java.util.List;

public class AjxScrollerManager extends AjxUiEventManager implements AjxContextHandlerCallback {
    private static final int BATCH_DELAY = 50;
    private static final int SCROLL_INTO_VIEW_DELAY_NEXT_BATCH = 1;
    private boolean isMessageExist = false;
    private WeakReference<JsDomScrollIntoView> mCurScrollIntoView = null;
    private long mScrollIntoViewNodeId = -1;

    public void destroy() {
    }

    public AjxScrollerManager(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public View createPage(@NonNull ViewGroup viewGroup, AjxDomNode ajxDomNode) {
        if (ajxDomNode == null) {
            return null;
        }
        ajxDomNode.initView(this.mAjxContext);
        ajxDomNode.addToViewTree(viewGroup);
        return ajxDomNode.getEnterView();
    }

    public boolean scrollerInitEvent(JsDomEventScrollerInit jsDomEventScrollerInit) {
        if (jsDomEventScrollerInit.node == null) {
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventScrollerInit.node.createAjxNode();
        long scrollerNodeId = getScrollerNodeId(createAjxNode);
        if (scrollerNodeId == -1) {
            return false;
        }
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(scrollerNodeId);
        if (findNodeById instanceof AjxScrollerDomNode) {
            ((AjxScrollerDomNode) findNodeById).initData((AjxDomGroupNode) createAjxNode);
            return true;
        } else if (!(findNodeById instanceof AjxListCell)) {
            return false;
        } else {
            ((AjxListCell) findNodeById).setScrollerData((AjxDomGroupNode) createAjxNode);
            List<AjxDomNode> children = createAjxNode.getChildren();
            if (children != null && children.size() > 0) {
                for (AjxDomNode saveNodeToMap : children) {
                    this.mAjxContext.getDomTree().saveNodeToMap(saveNodeToMap);
                }
            }
            return true;
        }
    }

    public static long getScrollerNodeId(AjxDomNode ajxDomNode) {
        if (ajxDomNode == null) {
            return -1;
        }
        String obj = ajxDomNode.getAttributeValue("_SCROLLER_ID").toString();
        if (TextUtils.isEmpty(obj)) {
            return -1;
        }
        return Long.parseLong(obj);
    }

    public boolean scrollIntoViewEvent(long j, JsDomScrollIntoView jsDomScrollIntoView) {
        if (jsDomScrollIntoView == null) {
            return false;
        }
        this.mCurScrollIntoView = new WeakReference<>(jsDomScrollIntoView);
        this.mScrollIntoViewNodeId = j;
        if (!this.isMessageExist) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            this.mAjxContext.post(this, obtain, 50);
            this.isMessageExist = true;
        }
        return true;
    }

    private void scrollIntoViewEventInternal() {
        if (this.mAjxContext.getNativeContext() != null && this.mCurScrollIntoView != null) {
            JsDomScrollIntoView jsDomScrollIntoView = (JsDomScrollIntoView) this.mCurScrollIntoView.get();
            if (jsDomScrollIntoView != null) {
                AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomScrollIntoView.getNodeId());
                if (findNodeById != null) {
                    int standardUnitToPixel = DimensionUtils.standardUnitToPixel((float) jsDomScrollIntoView.getTop());
                    int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel((float) jsDomScrollIntoView.getLeft());
                    int standardUnitToPixel3 = DimensionUtils.standardUnitToPixel((float) jsDomScrollIntoView.getHeight());
                    String option = jsDomScrollIntoView.getOption();
                    if (findNodeById instanceof AjxScrollerDomNode) {
                        ((AjxScrollerDomNode) findNodeById).scrollBy(standardUnitToPixel2, standardUnitToPixel);
                        return;
                    }
                    if (findNodeById instanceof AjxListDomNode) {
                        ((AjxListDomNode) findNodeById).scrollBy(option, standardUnitToPixel2, standardUnitToPixel, standardUnitToPixel3, this.mScrollIntoViewNodeId);
                    }
                }
            }
        }
    }

    public void handleCallback(Message message) {
        if (message != null && message.what == 1) {
            this.isMessageExist = false;
            scrollIntoViewEventInternal();
        }
    }
}
