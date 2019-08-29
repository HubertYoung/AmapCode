package com.autonavi.minimap.ajx3.dom.managers;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomGroupNode;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.IJsDomData;
import com.autonavi.minimap.ajx3.dom.JsDomEvent;
import com.autonavi.minimap.ajx3.dom.JsDomEventFull;
import com.autonavi.minimap.ajx3.dom.JsDomEventNodeAdd;
import com.autonavi.minimap.ajx3.dom.JsDomEventNodeInsert;
import com.autonavi.minimap.ajx3.dom.JsDomEventNodeRemove;
import com.autonavi.minimap.ajx3.dom.JsDomEventNodeReplace;
import com.autonavi.minimap.ajx3.dom.JsDomEventNodeSizeChange;
import com.autonavi.minimap.ajx3.dom.JsDomEventNodeSnapshot;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxLabelDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxScrollerDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxTextAreaDomNode;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.platform.impl.TextMeasurement;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.SnapshotUtil;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.view.IScrollView;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxNodeManager extends AjxUiEventManager {
    public void destroy() {
    }

    public AjxNodeManager(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public boolean fullEvent(JsDomEventFull jsDomEventFull) {
        this.mAjxContext.getDomTree().getRootView().removeAllViews();
        this.mAjxContext.getDomTree().setRootNode(null);
        this.mAjxContext.getDomTree().clearNodeMap();
        if (jsDomEventFull.node == null) {
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventFull.node.createAjxNode();
        if (Constants.BODY.equals(createAjxNode.getTagName())) {
            this.mAjxContext.getDomTree().setRootNode(createAjxNode);
            createAjxNode.setRootView(this.mAjxContext.getDomTree().getRootView(), this.mAjxContext);
            System.currentTimeMillis();
            this.mAjxContext.getDomTree().saveNodeToMap(createAjxNode);
        }
        Ajx.getInstance().addTimestamp("fullEvent-end");
        LogManager.performaceLog("fullEvent-end");
        try {
            long currentTimeMillis = System.currentTimeMillis() - this.mAjxContext.getDomTree().getRootView().mStartTime;
            this.mAjxContext.getDomTree().getRootView().mRenderTime = currentTimeMillis;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("livetime", String.format("%.4f", new Object[]{Float.valueOf((float) (((double) currentTimeMillis) / 1000.0d))}));
            Ajx.getInstance().onLineLog(this.mAjxContext.getDomTree().getRootView().getUrl(), "first_render", "B002", "p4", jSONObject.toString());
        } catch (Exception unused) {
        }
        return true;
    }

    public boolean addEvent(JsDomEventNodeAdd jsDomEventNodeAdd) {
        if (jsDomEventNodeAdd.node == null) {
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventNodeAdd.node.createAjxNode();
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventNodeAdd.parentNodeId);
        if (findNodeById instanceof AjxDomGroupNode) {
            ((AjxDomGroupNode) findNodeById).addChild(createAjxNode, -1);
        }
        System.currentTimeMillis();
        this.mAjxContext.getDomTree().saveNodeToMap(createAjxNode);
        return true;
    }

    public boolean insertEvent(JsDomEventNodeInsert jsDomEventNodeInsert) {
        AjxDomNode rootNode = this.mAjxContext.getDomTree().getRootNode();
        if (jsDomEventNodeInsert.node == null || rootNode == null) {
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventNodeInsert.node.createAjxNode();
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventNodeInsert.parentNodeId);
        if (findNodeById instanceof AjxDomGroupNode) {
            AjxDomGroupNode ajxDomGroupNode = (AjxDomGroupNode) findNodeById;
            ajxDomGroupNode.addChild(createAjxNode, ajxDomGroupNode.indexOfChild(this.mAjxContext.getDomTree().findNodeById(jsDomEventNodeInsert.beforeNodeId)));
            this.mAjxContext.getDomTree().saveNodeToMap(createAjxNode);
        }
        return true;
    }

    public boolean replaceEvent(JsDomEventNodeReplace jsDomEventNodeReplace) {
        if (jsDomEventNodeReplace.node == null) {
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventNodeReplace.node.createAjxNode();
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventNodeReplace.parentNodeId);
        if (!(findNodeById instanceof AjxDomGroupNode)) {
            return false;
        }
        AjxDomNode findNodeById2 = this.mAjxContext.getDomTree().findNodeById(jsDomEventNodeReplace.oldNodeId);
        if (findNodeById2 == null) {
            return false;
        }
        ((AjxDomGroupNode) findNodeById).replaceChild(findNodeById2, createAjxNode);
        this.mAjxContext.getDomTree().removeNodeFromMap(findNodeById2);
        this.mAjxContext.getDomTree().saveNodeToMap(createAjxNode);
        return true;
    }

    public boolean removeEvent(JsDomEventNodeRemove jsDomEventNodeRemove) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventNodeRemove.nodeId);
        if (findNodeById != null) {
            AjxDomGroupNode parent = findNodeById.getParent();
            if (parent != null && parent.getId() == jsDomEventNodeRemove.parentNodeId) {
                parent.removeChild(findNodeById);
                this.mAjxContext.getDomTree().removeNodeFromMap(findNodeById);
                return true;
            }
        }
        return false;
    }

    public boolean sizeChangeEvent(JsDomEventNodeSizeChange jsDomEventNodeSizeChange) {
        if (jsDomEventNodeSizeChange.node == null) {
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventNodeSizeChange.node.createAjxNode();
        long id = createAjxNode.getId();
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(id);
        if (findNodeById == null) {
            AjxScrollerDomNode findScrollerVirtualNodeById = this.mAjxContext.getDomTree().findScrollerVirtualNodeById(id);
            if (findScrollerVirtualNodeById == null || !(findScrollerVirtualNodeById.getEnterView() instanceof IScrollView)) {
                return false;
            }
            ((IScrollView) findScrollerVirtualNodeById.getEnterView()).setContentSize(DimensionUtils.standardUnitToPixel(createAjxNode.getWidth()), DimensionUtils.standardUnitToPixel(createAjxNode.getHeight()));
            return true;
        }
        if ((findNodeById instanceof AjxScrollerDomNode) && (findNodeById.getEnterView() instanceof IScrollView)) {
            ((IScrollView) findNodeById.getEnterView()).setContentSize(DimensionUtils.standardUnitToPixel(createAjxNode.getWidth()), DimensionUtils.standardUnitToPixel(createAjxNode.getHeight()));
        }
        findNodeById.setSize("left", createAjxNode.getLeft(), true);
        findNodeById.setSize("top", createAjxNode.getTop(), true);
        findNodeById.setSize("width", createAjxNode.getWidth(), true);
        findNodeById.setSize("height", createAjxNode.getHeight(), true);
        if ((findNodeById instanceof AjxTextAreaDomNode) || (findNodeById instanceof AjxLabelDomNode)) {
            View enterView = findNodeById.getEnterView();
            if (enterView == null) {
                findNodeById.initView(this.mAjxContext);
                enterView = findNodeById.getEnterView();
            }
            IJsDomData data = createAjxNode.getData();
            if (data != null) {
                int[] iArr = new int[4];
                float[] paddings = data.paddings();
                if (paddings != null) {
                    for (int i = 0; i < paddings.length; i++) {
                        iArr[i] = (int) DimensionUtils.standardUnitToPixelPrecise(paddings[i]);
                    }
                    enterView.setPadding(iArr[3], iArr[0], iArr[1], iArr[2]);
                }
            }
        }
        findNodeById.updateDiffProperty();
        return true;
    }

    public boolean nodeSnapshot(JsDomEventNodeSnapshot jsDomEventNodeSnapshot) {
        if (jsDomEventNodeSnapshot.node == null) {
            invokeSnapshotResult(0, null, 0, "node has deleted");
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventNodeSnapshot.node.createAjxNode();
        long id = createAjxNode.getId();
        View findViewByNodeId = this.mAjxContext.getDomTree().findViewByNodeId(id);
        if (findViewByNodeId != null) {
            triggerSnapshot(findViewByNodeId, id, jsDomEventNodeSnapshot.option);
        } else {
            createAjxNode.initView(this.mAjxContext);
            View enterView = createAjxNode.getEnterView();
            if (enterView instanceof ViewExtension) {
                ((ViewExtension) enterView).bind(createAjxNode);
            }
            int standardUnitToPixel = DimensionUtils.standardUnitToPixel(createAjxNode.getWidth());
            int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(createAjxNode.getHeight());
            enterView.measure(standardUnitToPixel, standardUnitToPixel2);
            enterView.layout(0, 0, standardUnitToPixel, standardUnitToPixel2);
            triggerSnapshot(enterView, id, jsDomEventNodeSnapshot.option);
        }
        return true;
    }

    private void triggerSnapshot(@NonNull View view, long j, String str) {
        TextMeasurement.clearCache();
        AjxView rootView = this.mAjxContext.getDomTree().getRootView();
        final String str2 = str;
        final long j2 = j;
        final View view2 = view;
        AnonymousClass1 r1 = new Runnable() {
            public void run() {
                JSONObject jSONObject;
                try {
                    if (TextUtils.isEmpty(str2)) {
                        jSONObject = new JSONObject(bny.c);
                    } else {
                        jSONObject = new JSONObject(str2);
                    }
                    int optInt = jSONObject.optInt("storageType", 0);
                    if (optInt == SnapshotUtil.SAVE_DCIM || optInt == SnapshotUtil.SAVE_SDCARD) {
                        String snapShotByView = SnapshotUtil.snapShotByView(view2, optInt);
                        int i = snapShotByView == null ? 0 : 1;
                        if (optInt == SnapshotUtil.SAVE_DCIM && !TextUtils.isEmpty(snapShotByView)) {
                            snapShotByView = "";
                        }
                        String str = snapShotByView;
                        AjxNodeManager.this.invokeSnapshotResult(j2, str, i, str == null ? "snapshot failed" : "");
                        return;
                    }
                    AjxNodeManager.this.invokeSnapshotResult(j2, null, 0, "option is illegal");
                } catch (JSONException e) {
                    e.printStackTrace();
                    AjxNodeManager.this.invokeSnapshotResult(j2, null, 0, "option is illegal");
                }
            }
        };
        rootView.postDelayed(r1, 50);
    }

    public Bitmap syncNodeSnapshot(long j) {
        JsDomEvent create = JsDomEvent.create(j);
        if (!(create instanceof JsDomEventNodeSnapshot)) {
            return null;
        }
        JsDomEventNodeSnapshot jsDomEventNodeSnapshot = (JsDomEventNodeSnapshot) create;
        if (jsDomEventNodeSnapshot.node == null) {
            return null;
        }
        View findViewByNodeId = this.mAjxContext.getDomTree().findViewByNodeId(jsDomEventNodeSnapshot.nodeId);
        if (findViewByNodeId != null) {
            return SnapshotUtil.getSnapShotByView(findViewByNodeId);
        }
        AjxDomNode createAjxNode = jsDomEventNodeSnapshot.node.createAjxNode();
        createAjxNode.initView(this.mAjxContext);
        View enterView = createAjxNode.getEnterView();
        if (enterView instanceof ViewExtension) {
            ((ViewExtension) enterView).bind(createAjxNode);
        }
        int standardUnitToPixel = DimensionUtils.standardUnitToPixel(createAjxNode.getWidth());
        int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(createAjxNode.getHeight());
        enterView.measure(standardUnitToPixel, standardUnitToPixel2);
        enterView.layout(0, 0, standardUnitToPixel, standardUnitToPixel2);
        return SnapshotUtil.getSnapShotByView(enterView);
    }

    /* access modifiers changed from: private */
    public void invokeSnapshotResult(long j, @Nullable String str, int i, @NonNull String str2) {
        JSONObject jSONObject = new JSONObject();
        if (str == null) {
            str = "";
        }
        try {
            jSONObject.put("url", str);
            jSONObject.put("state", i);
            jSONObject.put("msg", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Parcel parcel = new Parcel();
        parcel.writeInt(2);
        parcel.writeString("result");
        parcel.writeString(jSONObject.toString());
        this.mAjxContext.invokeJsEvent(new Builder().setEventName(Constants.EVENT_SNAP_SHOT).setNodeId(j).addAttribute("result", jSONObject.toString()).build());
    }
}
