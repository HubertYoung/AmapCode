package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxScrollerDomNode;
import com.autonavi.minimap.ajx3.dom.managers.AjxFrameManager;
import com.autonavi.minimap.ajx3.dom.managers.AjxListManager;
import com.autonavi.minimap.ajx3.dom.managers.AjxNodeManager;
import com.autonavi.minimap.ajx3.dom.managers.AjxPropertyManager;
import com.autonavi.minimap.ajx3.dom.managers.AjxScrollerManager;
import com.autonavi.minimap.ajx3.dom.managers.AjxStrictListManager;
import com.autonavi.minimap.ajx3.util.AjxALCLog;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.PerformanceUtil;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.animator.AjxAnimatorManager;
import com.autonavi.minimap.ajx3.widget.animator.linkage.LinkageAnimatorManager;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList;
import java.util.HashMap;
import java.util.List;

public class AjxDomTree {
    private AjxFrameManager ajxFrameManager;
    private AjxListManager ajxListManager;
    private AjxNodeManager ajxNodeManager;
    private AjxPropertyManager ajxPropertyManager;
    private AjxScrollerManager ajxScrollerManager;
    private AjxStrictListManager ajxStricyListManager;
    private AjxAnimatorManager mAjxAnimatorManager;
    private final IAjxContext mAjxContext;
    private boolean mDestroy = false;
    private HashMap<String, AjxDomNode> mHasFocusIndexNodeMap = new HashMap<>();
    private long mHighLightNodeId = -1;
    private LinkageAnimatorManager mLinkageAnimatorManager;
    private LongSparseArray<AjxListDomNode> mListCache = new LongSparseArray<>();
    private LongSparseArray<AjxDomNode> mNodeMap = new LongSparseArray<>();
    private AjxDomNode mRootNode;
    private final AjxView mRootView;
    private final int[] mRootViewLocation = new int[2];
    private LongSparseArray<AjxScrollerDomNode> mScrollerVirtualNode = new LongSparseArray<>();

    private void transferUiEvent(JsDomEvent jsDomEvent, boolean z) {
    }

    public AjxDomTree(@NonNull IAjxContext iAjxContext, @NonNull AjxView ajxView) {
        this.mAjxContext = iAjxContext;
        this.mRootView = ajxView;
        createManagers();
    }

    private void createManagers() {
        this.mAjxAnimatorManager = new AjxAnimatorManager(this.mAjxContext);
        this.mLinkageAnimatorManager = new LinkageAnimatorManager(this.mAjxContext);
        this.ajxFrameManager = new AjxFrameManager(this.mAjxContext);
        this.ajxListManager = new AjxListManager(this.mAjxContext);
        this.ajxNodeManager = new AjxNodeManager(this.mAjxContext);
        this.ajxPropertyManager = new AjxPropertyManager(this.mAjxContext);
        this.ajxScrollerManager = new AjxScrollerManager(this.mAjxContext);
        this.ajxStricyListManager = new AjxStrictListManager(this.mAjxContext);
    }

    public void destroy() {
        this.mAjxAnimatorManager.destroy();
        this.mLinkageAnimatorManager.destroy();
        this.ajxFrameManager.destroy();
        this.ajxListManager.destroy();
        this.ajxNodeManager.destroy();
        this.ajxPropertyManager.destroy();
        this.ajxScrollerManager.destroy();
        this.ajxFrameManager.destroy();
        this.mRootNode = null;
        this.mDestroy = true;
    }

    public boolean isDestroy() {
        return this.mDestroy;
    }

    public void bind(@NonNull JsDomEvent jsDomEvent) {
        if (jsDomEvent.type == 42) {
            transferUiEvent(jsDomEvent, true);
            for (JsDomEventListCellGroup jsDomEventListCellGroup = ((JsDomEventListCellGroup) jsDomEvent).group; jsDomEventListCellGroup != null; jsDomEventListCellGroup = jsDomEventListCellGroup.group) {
                transferUiEvent(jsDomEventListCellGroup, dispatchEvent(jsDomEventListCellGroup));
            }
            return;
        }
        transferUiEvent(jsDomEvent, dispatchEvent(jsDomEvent));
    }

    public PullToRefreshList findListByCell(AjxListCell ajxListCell) {
        if (ajxListCell == null) {
            return null;
        }
        long id = ajxListCell.getId();
        int size = this.mListCache.size();
        for (int i = 0; i < size; i++) {
            AjxDomNode ajxDomNode = (AjxDomNode) this.mListCache.valueAt(i);
            if ((ajxDomNode instanceof AjxListDomNode) && ((AjxListDomNode) ajxDomNode).findNodeById(id) != null) {
                return (PullToRefreshList) ajxDomNode.getEnterView();
            }
        }
        return null;
    }

    public float getBeforeExpandCellHeight(long j) {
        float f = -1.0f;
        for (int i = 0; i < this.mListCache.size(); i++) {
            AjxDomNode ajxDomNode = (AjxDomNode) this.mListCache.get(this.mListCache.keyAt(i));
            if (ajxDomNode instanceof AjxListDomNode) {
                f = ((AjxListDomNode) ajxDomNode).getBeforeExpandCellHeight(j);
            }
            if (f != -1.0f) {
                return f;
            }
        }
        return f;
    }

    private void saveNodeToCache(AjxDomNode ajxDomNode) {
        if (ajxDomNode instanceof AjxListDomNode) {
            this.mListCache.put(ajxDomNode.getId(), (AjxListDomNode) ajxDomNode);
        }
    }

    private void removeNodeFromCache(AjxDomNode ajxDomNode) {
        if (ajxDomNode instanceof AjxListDomNode) {
            this.mListCache.remove(ajxDomNode.getId());
        }
    }

    public void clearNodeMap() {
        this.mNodeMap.clear();
        this.mListCache.clear();
    }

    public void saveScrollerVirtualNode(long j, AjxScrollerDomNode ajxScrollerDomNode) {
        this.mScrollerVirtualNode.put(j, ajxScrollerDomNode);
    }

    public void saveNodeToMap(AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            if (ajxDomNode instanceof AjxListDomNode) {
                saveNodeToCache(ajxDomNode);
            }
            this.mNodeMap.put(ajxDomNode.getId(), ajxDomNode);
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null && children.size() > 0) {
                for (AjxDomNode saveNodeToMap : children) {
                    saveNodeToMap(saveNodeToMap);
                }
            }
        }
    }

    public void removeNodeFromMap(AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            removeNodeFromCache(ajxDomNode);
            this.mNodeMap.remove(ajxDomNode.getId());
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null && children.size() > 0) {
                for (AjxDomNode removeNodeFromMap : children) {
                    removeNodeFromMap(removeNodeFromMap);
                }
            }
        }
    }

    @Nullable
    public View findViewByNodeId(long j) {
        AjxDomNode findNodeById = findNodeById(j);
        if (findNodeById == null) {
            return null;
        }
        if (findNodeById instanceof AjxListCell) {
            return ((AjxListCell) findNodeById).getTempView();
        }
        return findNodeById.getEnterView();
    }

    @Nullable
    public AjxScrollerDomNode findScrollerVirtualNodeById(long j) {
        return (AjxScrollerDomNode) this.mScrollerVirtualNode.get(j, null);
    }

    @Nullable
    public AjxDomNode findNodeById(long j) {
        AjxDomNode ajxDomNode = (AjxDomNode) this.mNodeMap.get(j, null);
        if (ajxDomNode != null) {
            return ajxDomNode;
        }
        int size = this.mListCache.size();
        for (int i = 0; i < size; i++) {
            AjxListDomNode ajxListDomNode = (AjxListDomNode) this.mListCache.valueAt(i);
            if (ajxListDomNode != null) {
                AjxDomNode findNodeById = ajxListDomNode.findNodeById(j);
                if (findNodeById != null) {
                    return findNodeById;
                }
            }
        }
        return null;
    }

    @Nullable
    public AjxDomNode findNodeByIdInNodeMap(long j) {
        return (AjxDomNode) this.mNodeMap.get(j, null);
    }

    private boolean dispatchEvent(JsDomEvent jsDomEvent) {
        boolean z;
        long currentTimeMillis = System.currentTimeMillis();
        Ajx instance = Ajx.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(jsDomEvent.type);
        sb.append("_");
        sb.append(currentTimeMillis);
        sb.append(PerformanceUtil.START_AFTER_FIX);
        instance.addTimestamp(sb.toString());
        switch (jsDomEvent.type) {
            case 1:
                StringBuilder sb2 = new StringBuilder("native full event ");
                sb2.append(this.mAjxContext.getJsPath());
                sb2.append(".");
                AjxALCLog.info(AjxALCLog.TAG_PERFORMANCE, sb2.toString());
                z = this.ajxNodeManager.fullEvent((JsDomEventFull) jsDomEvent);
                break;
            case 2:
                z = this.ajxNodeManager.addEvent((JsDomEventNodeAdd) jsDomEvent);
                break;
            case 3:
                z = this.ajxNodeManager.insertEvent((JsDomEventNodeInsert) jsDomEvent);
                break;
            case 4:
                z = this.ajxNodeManager.replaceEvent((JsDomEventNodeReplace) jsDomEvent);
                break;
            case 5:
                z = this.ajxNodeManager.removeEvent((JsDomEventNodeRemove) jsDomEvent);
                break;
            case 6:
                z = this.ajxNodeManager.sizeChangeEvent((JsDomEventNodeSizeChange) jsDomEvent);
                break;
            case 7:
                z = this.ajxNodeManager.nodeSnapshot((JsDomEventNodeSnapshot) jsDomEvent);
                break;
            case 8:
            case 9:
                z = this.ajxPropertyManager.styleChangeEvent((JsDomEventNodeProperty) jsDomEvent);
                break;
            case 10:
            case 11:
                z = this.ajxPropertyManager.attributeChangeEvent((JsDomEventNodeAttribute) jsDomEvent);
                break;
            case 12:
                Ajx.getInstance().addTimestamp(PerformanceUtil.LIST_INIT_TIME);
                z = this.ajxListManager.listInitEvent((JsDomEventListInit) jsDomEvent);
                break;
            case 13:
                z = this.ajxListManager.listSectionAddEvent((JsDomEventListSection) jsDomEvent);
                break;
            case 14:
                z = this.ajxListManager.listSectionRemoveEvent((JsDomEventListSection) jsDomEvent);
                break;
            case 15:
                z = this.ajxListManager.listSectionReplaceEvent((JsDomEventListSection) jsDomEvent);
                break;
            case 16:
                z = this.ajxListManager.listDataAddEvent((JsDomEventListCellData) jsDomEvent);
                break;
            case 17:
                z = this.ajxListManager.listDataRemoveEvent((JsDomEventListCellData) jsDomEvent);
                break;
            case 18:
                z = this.ajxListManager.listDataReplaceEvent((JsDomEventListCellData) jsDomEvent);
                break;
            case 19:
                z = this.ajxListManager.listDataSizeChangeEvent((JsDomEventListDataSizeChange) jsDomEvent);
                break;
            case 20:
                z = this.ajxListManager.listDataStyleAddEvent((JsDomEventListDataProperty) jsDomEvent);
                break;
            case 21:
                z = this.ajxListManager.listDataStyleRemoveEvent((JsDomEventListDataProperty) jsDomEvent);
                break;
            case 22:
                z = this.ajxListManager.listDataAttributeAddEvent((JsDomEventListDataAttribute) jsDomEvent);
                break;
            case 23:
                z = this.ajxListManager.listDataAttributeRemoveEvent((JsDomEventListDataAttribute) jsDomEvent);
                break;
            case 24:
                z = this.ajxListManager.listTemplateAddEvent((JsDomEventListTemplateAdd) jsDomEvent);
                break;
            case 25:
                z = this.ajxScrollerManager.scrollerInitEvent((JsDomEventScrollerInit) jsDomEvent);
                break;
            case 26:
                z = this.ajxFrameManager.frameInit((JsDomEventFrameInit) jsDomEvent);
                break;
            case 27:
                z = animationNew((JsDomEventAnimation) jsDomEvent);
                break;
            case 28:
                z = animationPlay((JsDomEventAnimation) jsDomEvent);
                break;
            case 29:
                z = animationPause((JsDomEventAnimation) jsDomEvent);
                break;
            case 30:
                z = animationFinish((JsDomEventAnimation) jsDomEvent);
                break;
            case 31:
                z = animationReverse((JsDomEventAnimation) jsDomEvent);
                break;
            case 32:
                z = animationCancel((JsDomEventAnimation) jsDomEvent);
                break;
            case 33:
                z = animationClear((JsDomEventAnimation) jsDomEvent);
                break;
            case 34:
                z = animateUpdateForbidFlag((JsDomEventAnimation) jsDomEvent);
                break;
            case 35:
                z = animationSerial((JsDomEventAnimationGroup) jsDomEvent);
                break;
            case 36:
                z = animationParallel((JsDomEventAnimationGroup) jsDomEvent);
                break;
            case 37:
                z = relativeAnimationBindTarget((JsDomEventRelativeAnimation) jsDomEvent);
                break;
            case 38:
                z = relativeAnimationAddObserver((JsDomEventRelativeAnimation) jsDomEvent);
                break;
            case 39:
                z = relativeAnimationRemoveByNode((JsDomEventRelativeAnimation) jsDomEvent);
                break;
            case 40:
                z = relativeAnimationClear((JsDomEventRelativeAnimation) jsDomEvent);
                break;
            case 41:
                JsDomEventScrollIntoView jsDomEventScrollIntoView = (JsDomEventScrollIntoView) jsDomEvent;
                z = this.ajxScrollerManager.scrollIntoViewEvent(jsDomEventScrollIntoView.nodeId, jsDomEventScrollIntoView.scrollIntoView);
                break;
            case 43:
            case 44:
                z = this.ajxListManager.listTemplateStyleChange((JsDomEventListTemplateProperty) jsDomEvent);
                break;
            case 45:
            case 46:
                z = this.ajxListManager.listTemplateAttributeChange((JsDomEventListTemplateAttribute) jsDomEvent);
                break;
            default:
                z = false;
                break;
        }
        Ajx instance2 = Ajx.getInstance();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(jsDomEvent.type);
        sb3.append("_");
        sb3.append(currentTimeMillis);
        sb3.append(PerformanceUtil.END_AFTER_FIX);
        instance2.addTimestamp(sb3.toString());
        return z;
    }

    private boolean animateUpdateForbidFlag(JsDomEventAnimation jsDomEventAnimation) {
        return this.mAjxAnimatorManager.animateUpdateForbidFlag(jsDomEventAnimation);
    }

    private boolean animationNew(JsDomEventAnimation jsDomEventAnimation) {
        return this.mAjxAnimatorManager.newAnimation(jsDomEventAnimation, this.mAjxContext);
    }

    private boolean animationPause(JsDomEventAnimation jsDomEventAnimation) {
        return this.mAjxAnimatorManager.pauseAnimation(jsDomEventAnimation.animationId, this.mAjxContext);
    }

    private boolean animationPlay(JsDomEventAnimation jsDomEventAnimation) {
        return this.mAjxAnimatorManager.playAnimation(jsDomEventAnimation.animationId, this.mAjxContext);
    }

    private boolean animationReverse(JsDomEventAnimation jsDomEventAnimation) {
        return this.mAjxAnimatorManager.reverseAnimation(jsDomEventAnimation.animationId, this.mAjxContext);
    }

    private boolean animationFinish(JsDomEventAnimation jsDomEventAnimation) {
        return this.mAjxAnimatorManager.finishAnimation(jsDomEventAnimation.animationId, this.mAjxContext);
    }

    private boolean animationCancel(JsDomEventAnimation jsDomEventAnimation) {
        return this.mAjxAnimatorManager.cancelAnimation(jsDomEventAnimation.animationId, this.mAjxContext);
    }

    private boolean animationClear(JsDomEventAnimation jsDomEventAnimation) {
        return this.mAjxAnimatorManager.clearAnimation(jsDomEventAnimation.animationId, this.mAjxContext);
    }

    private boolean animationSerial(JsDomEventAnimationGroup jsDomEventAnimationGroup) {
        return this.mAjxAnimatorManager.serialAnimation(jsDomEventAnimationGroup, this.mAjxContext);
    }

    private boolean animationParallel(JsDomEventAnimationGroup jsDomEventAnimationGroup) {
        return this.mAjxAnimatorManager.parallelAnimation(jsDomEventAnimationGroup, this.mAjxContext);
    }

    private boolean relativeAnimationBindTarget(JsDomEventRelativeAnimation jsDomEventRelativeAnimation) {
        return this.mLinkageAnimatorManager.bindAnimationTarget(this, jsDomEventRelativeAnimation);
    }

    private boolean relativeAnimationAddObserver(JsDomEventRelativeAnimation jsDomEventRelativeAnimation) {
        return this.mLinkageAnimatorManager.addAnimationObserver(this, jsDomEventRelativeAnimation);
    }

    private boolean relativeAnimationRemoveByNode(JsDomEventRelativeAnimation jsDomEventRelativeAnimation) {
        return this.mLinkageAnimatorManager.removeRelativeAnimationByNode(this, jsDomEventRelativeAnimation);
    }

    private boolean relativeAnimationClear(JsDomEventRelativeAnimation jsDomEventRelativeAnimation) {
        return this.mLinkageAnimatorManager.removeRelativeAnimations(jsDomEventRelativeAnimation.animationId);
    }

    public AjxView getRootView() {
        if (this.mDestroy) {
            LogHelper.throwRunTimeException("AjxDomTree is destroy!");
        }
        return this.mRootView;
    }

    public AjxAnimatorManager getAjxAnimatorManager() {
        return this.mAjxAnimatorManager;
    }

    public AjxFrameManager getAjxFrameManager() {
        return this.ajxFrameManager;
    }

    public AjxListManager getAjxListManager() {
        return this.ajxListManager;
    }

    public AjxNodeManager getAjxNodeManager() {
        return this.ajxNodeManager;
    }

    public AjxScrollerManager getAjxScrollerManager() {
        return this.ajxScrollerManager;
    }

    public AjxStrictListManager getAjxStricyListManager() {
        return this.ajxStricyListManager;
    }

    public AjxDomNode getRootNode() {
        return this.mRootNode;
    }

    public void setRootNode(AjxDomNode ajxDomNode) {
        this.mRootNode = ajxDomNode;
    }

    public long getNodeId(@NonNull View view) {
        if (view instanceof ViewExtension) {
            ViewExtension viewExtension = (ViewExtension) view;
            if (viewExtension.getProperty() != null) {
                return viewExtension.getProperty().getNodeId();
            }
        }
        return -1;
    }

    public LinkageAnimatorManager getLinkageAnimatorManager() {
        return this.mLinkageAnimatorManager;
    }

    public float getRootViewScreenX() {
        getRootView().getLocationOnScreen(this.mRootViewLocation);
        return (float) this.mRootViewLocation[0];
    }

    public float getRootViewScreenY() {
        getRootView().getLocationOnScreen(this.mRootViewLocation);
        return (float) this.mRootViewLocation[1];
    }

    public boolean isHighLightNode(long j) {
        if (this.mHighLightNodeId != -1 && this.mHighLightNodeId == j) {
            return true;
        }
        return false;
    }

    public void highLightNode(long j) {
        View findViewByNodeId = findViewByNodeId(j);
        if (findViewByNodeId != null) {
            this.mHighLightNodeId = j;
            this.mRootView.addCoverView(findViewByNodeId);
        }
    }

    public void hideHighLight() {
        if (this.mHighLightNodeId != -1) {
            this.mRootView.removeCover();
        }
    }

    public void removeHighLight() {
        if (this.mHighLightNodeId != -1) {
            this.mHighLightNodeId = -1;
            this.mRootView.removeCover();
        }
    }

    public void beginForbidEvents(long j) {
        if (this.mRootView != null) {
            this.mRootView.beginForbidEvents(j);
        }
    }

    public void stopForbidEvents(long j) {
        if (this.mRootView != null) {
            this.mRootView.stopForbidEvents(j);
        }
    }

    public void putFocusNode(String str, AjxDomNode ajxDomNode) {
        if (!TextUtils.isEmpty(str) && ajxDomNode != null) {
            this.mHasFocusIndexNodeMap.containsKey(str);
            this.mHasFocusIndexNodeMap.put(str, ajxDomNode);
        }
    }

    public AjxDomNode getNextFocusNode(String str) {
        if (this.mHasFocusIndexNodeMap.containsKey(str)) {
            return this.mHasFocusIndexNodeMap.get(str);
        }
        return null;
    }
}
