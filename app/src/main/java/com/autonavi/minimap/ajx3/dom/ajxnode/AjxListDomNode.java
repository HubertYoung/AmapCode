package com.autonavi.minimap.ajx3.dom.ajxnode;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomEventListCellData;
import com.autonavi.minimap.ajx3.dom.JsDomEventListDataAttribute;
import com.autonavi.minimap.ajx3.dom.JsDomEventListDataSizeChange;
import com.autonavi.minimap.ajx3.dom.JsDomEventListSection;
import com.autonavi.minimap.ajx3.dom.JsDomEventListTemplateAdd;
import com.autonavi.minimap.ajx3.dom.JsDomEventListTemplateAttribute;
import com.autonavi.minimap.ajx3.dom.JsDomEventListTemplateProperty;
import com.autonavi.minimap.ajx3.dom.JsDomList;
import com.autonavi.minimap.ajx3.dom.JsDomListCellData;
import com.autonavi.minimap.ajx3.dom.JsDomListSection;
import com.autonavi.minimap.ajx3.dom.JsDomNode;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.AjxViewManager;
import com.autonavi.minimap.ajx3.widget.view.list.AjxList;
import com.autonavi.minimap.ajx3.widget.view.list.AjxListAdapter;
import com.autonavi.minimap.ajx3.widget.view.list.AjxListData;
import com.autonavi.minimap.ajx3.widget.view.list.BaseListAdapter;
import com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList;

public class AjxListDomNode extends AjxDomNode {
    private static final String TAG = "AjxListDomNode";
    private AjxListData mAdapterData = null;
    private int mColumnCount = 1;
    public boolean mIsWaterFall = false;

    public AjxListDomNode(@NonNull JsDomNode jsDomNode) {
        super(jsDomNode);
    }

    public AjxListDomNode(@NonNull JsDomNode jsDomNode, boolean z) {
        super(jsDomNode);
        this.mIsWaterFall = z;
    }

    public void createView(IAjxContext iAjxContext) {
        this.mColumnCount = 1;
        if (this.mIsWaterFall) {
            Object attributeValue = getAttributeValue("column-count");
            if (attributeValue instanceof String) {
                this.mColumnCount = StringUtils.parseInt((String) attributeValue);
            }
        }
        if (this.mColumnCount <= 1) {
            this.mColumnCount = 1;
            this.mIsWaterFall = false;
        }
        this.mView = new PullToRefreshList(iAjxContext, this.mColumnCount);
        ((ViewGroup) this.mView).setMotionEventSplittingEnabled(false);
        AjxViewManager.registerInterfaceView(iAjxContext, this.mView);
    }

    public void initEnterView(IAjxContext iAjxContext) {
        if (this.mView == null) {
            this.mAjxContext = iAjxContext;
            createView(iAjxContext);
            if (this.mAdapterData != null) {
                PullToRefreshList pullToRefreshList = (PullToRefreshList) this.mView;
                BaseListAdapter adapter = pullToRefreshList.getAdapter();
                ((AjxList) pullToRefreshList.getRefreshableView()).getRecycledViewPool().a.clear();
                if (adapter instanceof AjxListAdapter) {
                    AjxListAdapter ajxListAdapter = (AjxListAdapter) adapter;
                    ajxListAdapter.setList((AjxList) pullToRefreshList.getRefreshableView());
                    ajxListAdapter.setListData(this.mAdapterData);
                    return;
                }
                AjxListAdapter ajxListAdapter2 = new AjxListAdapter(iAjxContext);
                AjxListAdapter ajxListAdapter3 = ajxListAdapter2;
                ajxListAdapter3.setList((AjxList) pullToRefreshList.getRefreshableView());
                pullToRefreshList.setAdapter(ajxListAdapter2);
                ajxListAdapter3.setListData(this.mAdapterData);
            }
        }
    }

    public float getBeforeExpandCellHeight(long j) {
        if (this.mAdapterData != null) {
            return this.mAdapterData.getBeforeExpandCellHeight(j);
        }
        return -1.0f;
    }

    public AjxDomNode findNodeById(long j) {
        AjxDomNode findNodeByIdInNodeMap = this.mAjxContext != null ? this.mAjxContext.getDomTree().findNodeByIdInNodeMap(j) : null;
        return (findNodeByIdInNodeMap != null || this.mAdapterData == null) ? findNodeByIdInNodeMap : this.mAdapterData.findNodeById(j);
    }

    public void scrollBy(String str, int i, int i2, int i3, long j) {
        ((PullToRefreshList) this.mView).scrollBy(str, i, i2, i3, j);
    }

    public boolean updateData(IAjxContext iAjxContext, JsDomList jsDomList) {
        this.mAjxContext = iAjxContext;
        if (jsDomList == null) {
            return false;
        }
        this.mAdapterData = new AjxListData(jsDomList, this.mColumnCount);
        if (this.mView == null) {
            return false;
        }
        PullToRefreshList pullToRefreshList = (PullToRefreshList) this.mView;
        try {
            ((AjxList) pullToRefreshList.getRefreshableView()).getRecycledViewPool().a.clear();
            AjxListAdapter ajxListAdapter = new AjxListAdapter(this.mAjxContext);
            ajxListAdapter.setList((AjxList) pullToRefreshList.getRefreshableView());
            pullToRefreshList.setAdapter(ajxListAdapter);
            ajxListAdapter.setListData(this.mAdapterData);
        } catch (Exception unused) {
        }
        return true;
    }

    public boolean sectionAddEvent(JsDomEventListSection jsDomEventListSection) {
        if (this.mAdapterData == null || jsDomEventListSection.sectionData == null) {
            return false;
        }
        int i = jsDomEventListSection.sectionIndex;
        JsDomListSection jsDomListSection = jsDomEventListSection.section;
        JsDomListCellData[] cellData = jsDomEventListSection.sectionData.getCellData();
        if (jsDomListSection == null) {
            return false;
        }
        jsDomListSection.setCells(cellData);
        BaseListAdapter adapter = this.mView instanceof PullToRefreshList ? ((PullToRefreshList) this.mView).getAdapter() : null;
        if (!this.mAdapterData.addSection(i, jsDomListSection, adapter) || adapter == null) {
            return false;
        }
        adapter.removeSectionCache();
        ((PullToRefreshList) this.mView).askToUpdate();
        return true;
    }

    public boolean sectionRemoveEvent(JsDomEventListSection jsDomEventListSection) {
        if (this.mAdapterData == null) {
            return false;
        }
        BaseListAdapter adapter = this.mView instanceof PullToRefreshList ? ((PullToRefreshList) this.mView).getAdapter() : null;
        if (!this.mAdapterData.removeSection(jsDomEventListSection.sectionIndex, adapter) || adapter == null) {
            return false;
        }
        adapter.removeSectionCache();
        ((PullToRefreshList) this.mView).askToUpdate();
        return true;
    }

    public boolean sectionReplaceEvent(JsDomEventListSection jsDomEventListSection) {
        if (this.mAdapterData == null || jsDomEventListSection.sectionData == null) {
            return false;
        }
        int i = jsDomEventListSection.sectionIndex;
        JsDomListSection jsDomListSection = jsDomEventListSection.section;
        JsDomListCellData[] cellData = jsDomEventListSection.sectionData.getCellData();
        if (jsDomListSection == null) {
            return false;
        }
        jsDomListSection.setCells(cellData);
        BaseListAdapter adapter = this.mView instanceof PullToRefreshList ? ((PullToRefreshList) this.mView).getAdapter() : null;
        if (!this.mAdapterData.replaceSection(i, jsDomListSection, adapter) || adapter == null) {
            return false;
        }
        adapter.removeSectionCache();
        ((PullToRefreshList) this.mView).askToUpdate();
        return true;
    }

    public boolean dataAddEvent(JsDomEventListCellData jsDomEventListCellData) {
        if (this.mAdapterData == null || jsDomEventListCellData.cellData == null) {
            return false;
        }
        int addCell = this.mAdapterData.addCell(jsDomEventListCellData.cellData);
        if (addCell == AjxListData.EMPTY_ITEM_POSITION) {
            return false;
        }
        if (!(this.mView instanceof PullToRefreshList)) {
            return true;
        }
        BaseListAdapter adapter = ((PullToRefreshList) this.mView).getAdapter();
        if (adapter == null) {
            return true;
        }
        adapter.removeSectionCache();
        if (addCell == 0 || jsDomEventListCellData.cellData.getDataIndex() == 0) {
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyItemInserted(addCell);
        }
        ((PullToRefreshList) this.mView).askToUpdate();
        return true;
    }

    public boolean dataRemoveEvent(JsDomEventListCellData jsDomEventListCellData) {
        if (this.mAdapterData == null) {
            return false;
        }
        int removeCell = this.mAdapterData.removeCell(jsDomEventListCellData.sectionIndex, jsDomEventListCellData.dataIndex);
        if (removeCell == AjxListData.EMPTY_ITEM_POSITION) {
            return false;
        }
        if (!(this.mView instanceof PullToRefreshList)) {
            return true;
        }
        BaseListAdapter adapter = ((PullToRefreshList) this.mView).getAdapter();
        if (adapter == null) {
            return true;
        }
        adapter.removeSectionCache();
        adapter.notifyItemRemoved(removeCell);
        ((PullToRefreshList) this.mView).askToUpdate();
        return true;
    }

    public boolean dataReplaceEvent(JsDomEventListCellData jsDomEventListCellData) {
        if (this.mAdapterData == null || jsDomEventListCellData.cellData == null) {
            return false;
        }
        int replaceCell = this.mAdapterData.replaceCell(jsDomEventListCellData.cellData);
        if (replaceCell == AjxListData.EMPTY_ITEM_POSITION) {
            return false;
        }
        if (!(this.mView instanceof PullToRefreshList)) {
            return true;
        }
        BaseListAdapter adapter = ((PullToRefreshList) this.mView).getAdapter();
        if (adapter == null) {
            return true;
        }
        adapter.removeSectionCache();
        adapter.notifyItemChanged(replaceCell);
        ((PullToRefreshList) this.mView).askToUpdate();
        return true;
    }

    public boolean dataSizeChangeEvent(JsDomEventListDataSizeChange jsDomEventListDataSizeChange) {
        if (this.mAdapterData == null || jsDomEventListDataSizeChange.node == null) {
            return false;
        }
        AjxDomNode createAjxNode = jsDomEventListDataSizeChange.node.createAjxNode();
        AjxDomNode findNodeById = findNodeById(createAjxNode.getId());
        if (findNodeById == null) {
            return false;
        }
        AjxDomNode ajxDomNode = findNodeById;
        ajxDomNode.setSize("left", createAjxNode.getLeft(), true, true, false, false);
        ajxDomNode.setSize("top", createAjxNode.getTop(), true, true, false, false);
        ajxDomNode.setSize("width", createAjxNode.getWidth(), true, true, false, false);
        ajxDomNode.setSize("height", createAjxNode.getHeight(), true, true, false, false);
        findNodeById.setPropertiesToView(createAjxNode.getData(), 0);
        if (!(this.mView instanceof PullToRefreshList)) {
            return true;
        }
        BaseListAdapter adapter = ((PullToRefreshList) this.mView).getAdapter();
        if (adapter != null && createAjxNode.mIsHeaderOrFooter) {
            adapter.removeSectionCache();
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0087 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dataStyleAddEvent(com.autonavi.minimap.ajx3.dom.JsDomEventListDataProperty r13) {
        /*
            r12 = this;
            com.autonavi.minimap.ajx3.widget.view.list.AjxListData r0 = r12.mAdapterData
            r1 = 0
            if (r0 == 0) goto L_0x009e
            com.autonavi.minimap.ajx3.dom.JsDomProperty r0 = r13.prop
            if (r0 != 0) goto L_0x000b
            goto L_0x009e
        L_0x000b:
            long r2 = r13.nodeId
            com.autonavi.minimap.ajx3.dom.AjxDomNode r0 = r12.findNodeById(r2)
            if (r0 == 0) goto L_0x009d
            com.autonavi.minimap.ajx3.dom.JsDomProperty r2 = r13.prop
            int r5 = r2.type
            com.autonavi.minimap.ajx3.dom.JsDomProperty r2 = r13.prop
            int r6 = r2.key
            r2 = 1056964655(0x3f00002f, float:0.5000028)
            r3 = 1
            if (r6 != r2) goto L_0x0075
            com.autonavi.minimap.ajx3.dom.JsDomProperty r4 = r13.prop
            java.lang.Object r4 = r4.value
            if (r4 == 0) goto L_0x0075
            boolean r4 = r0 instanceof com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell
            if (r4 == 0) goto L_0x0075
            r4 = r0
            com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell r4 = (com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell) r4
            boolean r7 = r4.mIsListCell
            if (r7 == 0) goto L_0x0075
            com.autonavi.minimap.ajx3.dom.JsDomProperty r5 = r13.prop
            java.lang.Object r5 = r5.value
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r6 = -1
            int r1 = r0.getStyleIntValue(r2, r6, r1)
            com.autonavi.minimap.ajx3.dom.JsDomProperty r13 = r13.prop
            r0.setStyle(r13, r3)
            com.autonavi.minimap.ajx3.widget.view.list.AjxListData r13 = r12.mAdapterData
            int r13 = r13.displayChanged(r4, r1, r5)
            int r1 = com.autonavi.minimap.ajx3.widget.view.list.AjxListData.EMPTY_ITEM_POSITION
            if (r13 == r1) goto L_0x0081
            android.view.View r1 = r12.mView
            boolean r1 = r1 instanceof com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList
            if (r1 == 0) goto L_0x0081
            android.view.View r1 = r12.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r1 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r1
            com.autonavi.minimap.ajx3.widget.view.list.BaseListAdapter r1 = r1.getAdapter()
            if (r1 != 0) goto L_0x0061
            return r3
        L_0x0061:
            r2 = 1056964728(0x3f000078, float:0.50000715)
            if (r5 != r2) goto L_0x006a
            r1.notifyItemRemoved(r13)
            goto L_0x006d
        L_0x006a:
            r1.notifyItemInserted(r13)
        L_0x006d:
            android.view.View r13 = r12.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r13 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r13
            r13.askToUpdate()
            goto L_0x0081
        L_0x0075:
            com.autonavi.minimap.ajx3.dom.JsDomProperty r13 = r13.prop
            java.lang.Object r7 = r13.value
            r8 = 1
            r9 = 1
            r10 = 0
            r11 = 0
            r4 = r0
            r4.setStyle(r5, r6, r7, r8, r9, r10, r11)
        L_0x0081:
            android.view.View r13 = r12.mView
            boolean r13 = r13 instanceof com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList
            if (r13 != 0) goto L_0x0088
            return r3
        L_0x0088:
            android.view.View r13 = r12.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r13 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r13
            com.autonavi.minimap.ajx3.widget.view.list.BaseListAdapter r13 = r13.getAdapter()
            if (r13 == 0) goto L_0x009c
            boolean r0 = r0.mIsHeaderOrFooter
            if (r0 == 0) goto L_0x009c
            r13.removeSectionCache()
            r13.notifyDataSetChanged()
        L_0x009c:
            return r3
        L_0x009d:
            return r1
        L_0x009e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.dom.ajxnode.AjxListDomNode.dataStyleAddEvent(com.autonavi.minimap.ajx3.dom.JsDomEventListDataProperty):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x008f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0090  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dataStyleRemoveEvent(com.autonavi.minimap.ajx3.dom.JsDomEventListDataProperty r13) {
        /*
            r12 = this;
            com.autonavi.minimap.ajx3.widget.view.list.AjxListData r0 = r12.mAdapterData
            r1 = 0
            if (r0 == 0) goto L_0x00a6
            com.autonavi.minimap.ajx3.dom.JsDomProperty r0 = r13.prop
            if (r0 != 0) goto L_0x000b
            goto L_0x00a6
        L_0x000b:
            long r2 = r13.nodeId
            com.autonavi.minimap.ajx3.dom.AjxDomNode r0 = r12.findNodeById(r2)
            if (r0 == 0) goto L_0x00a5
            com.autonavi.minimap.ajx3.dom.JsDomProperty r2 = r13.prop
            int r2 = r2.type
            com.autonavi.minimap.ajx3.dom.JsDomProperty r3 = r13.prop
            int r3 = r3.key
            r0.removeStyle(r2, r3)
            com.autonavi.minimap.ajx3.dom.JsDomProperty r2 = r13.prop
            int r5 = r2.type
            com.autonavi.minimap.ajx3.dom.JsDomProperty r2 = r13.prop
            int r6 = r2.key
            r2 = 1056964655(0x3f00002f, float:0.5000028)
            r3 = 1
            if (r6 != r2) goto L_0x0080
            com.autonavi.minimap.ajx3.dom.JsDomProperty r4 = r13.prop
            java.lang.Object r4 = r4.value
            if (r4 == 0) goto L_0x0080
            boolean r4 = r0 instanceof com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell
            if (r4 == 0) goto L_0x0080
            r4 = r0
            com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell r4 = (com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell) r4
            boolean r7 = r4.mIsListCell
            if (r7 == 0) goto L_0x0080
            com.autonavi.minimap.ajx3.dom.JsDomProperty r5 = r13.prop
            java.lang.Object r5 = r5.value
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r6 = -1
            int r1 = r0.getStyleIntValue(r2, r6, r1)
            com.autonavi.minimap.ajx3.dom.JsDomProperty r13 = r13.prop
            r0.setStyle(r13, r3)
            com.autonavi.minimap.ajx3.widget.view.list.AjxListData r13 = r12.mAdapterData
            int r13 = r13.displayChanged(r4, r1, r5)
            int r1 = com.autonavi.minimap.ajx3.widget.view.list.AjxListData.EMPTY_ITEM_POSITION
            if (r13 == r1) goto L_0x0089
            android.view.View r1 = r12.mView
            boolean r1 = r1 instanceof com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList
            if (r1 == 0) goto L_0x0089
            android.view.View r1 = r12.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r1 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r1
            com.autonavi.minimap.ajx3.widget.view.list.BaseListAdapter r1 = r1.getAdapter()
            if (r1 != 0) goto L_0x006c
            return r3
        L_0x006c:
            r2 = 1056964728(0x3f000078, float:0.50000715)
            if (r5 != r2) goto L_0x0075
            r1.notifyItemRemoved(r13)
            goto L_0x0078
        L_0x0075:
            r1.notifyItemInserted(r13)
        L_0x0078:
            android.view.View r13 = r12.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r13 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r13
            r13.askToUpdate()
            goto L_0x0089
        L_0x0080:
            r7 = 0
            r8 = 1
            r9 = 1
            r10 = 0
            r11 = 0
            r4 = r0
            r4.setStyle(r5, r6, r7, r8, r9, r10, r11)
        L_0x0089:
            android.view.View r13 = r12.mView
            boolean r13 = r13 instanceof com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList
            if (r13 != 0) goto L_0x0090
            return r3
        L_0x0090:
            android.view.View r13 = r12.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r13 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r13
            com.autonavi.minimap.ajx3.widget.view.list.BaseListAdapter r13 = r13.getAdapter()
            if (r13 == 0) goto L_0x00a4
            boolean r0 = r0.mIsHeaderOrFooter
            if (r0 == 0) goto L_0x00a4
            r13.removeSectionCache()
            r13.notifyDataSetChanged()
        L_0x00a4:
            return r3
        L_0x00a5:
            return r1
        L_0x00a6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.dom.ajxnode.AjxListDomNode.dataStyleRemoveEvent(com.autonavi.minimap.ajx3.dom.JsDomEventListDataProperty):boolean");
    }

    public boolean dataAttributeAddEvent(JsDomEventListDataAttribute jsDomEventListDataAttribute) {
        if (this.mAdapterData == null || jsDomEventListDataAttribute.attr == null) {
            return false;
        }
        AjxDomNode findNodeById = findNodeById(jsDomEventListDataAttribute.nodeId);
        if (findNodeById == null) {
            return false;
        }
        String str = jsDomEventListDataAttribute.attr.key;
        if (str != null) {
            findNodeById.setAttribute(str, jsDomEventListDataAttribute.attr.value, true, true, false, false);
            if (!(this.mView instanceof PullToRefreshList)) {
                return true;
            }
            BaseListAdapter adapter = ((PullToRefreshList) this.mView).getAdapter();
            if (adapter != null && findNodeById.mIsHeaderOrFooter) {
                adapter.removeSectionCache();
                adapter.notifyDataSetChanged();
            }
        }
        return true;
    }

    public boolean dataAttributeRemoveEvent(JsDomEventListDataAttribute jsDomEventListDataAttribute) {
        if (this.mAdapterData == null || jsDomEventListDataAttribute.attr == null) {
            return false;
        }
        AjxDomNode findNodeById = findNodeById(jsDomEventListDataAttribute.nodeId);
        if (findNodeById == null) {
            return false;
        }
        String str = jsDomEventListDataAttribute.attr.key;
        if (str != null) {
            findNodeById.removeAttribute(str);
            findNodeById.setAttribute(str, null, true, true, false, false);
            if (!(this.mView instanceof PullToRefreshList)) {
                return true;
            }
            BaseListAdapter adapter = ((PullToRefreshList) this.mView).getAdapter();
            if (adapter != null && findNodeById.mIsHeaderOrFooter) {
                adapter.removeSectionCache();
                adapter.notifyDataSetChanged();
            }
        }
        return true;
    }

    public boolean templateAddEvent(JsDomEventListTemplateAdd jsDomEventListTemplateAdd) {
        if (this.mAdapterData == null || jsDomEventListTemplateAdd.node == null) {
            return false;
        }
        this.mAdapterData.addTemplate(jsDomEventListTemplateAdd.node.createAjxNode());
        return true;
    }

    public boolean templateStyleChange(JsDomEventListTemplateProperty jsDomEventListTemplateProperty) {
        if (this.mAdapterData == null) {
            return false;
        }
        this.mAdapterData.updateTemplateStyle(jsDomEventListTemplateProperty.templateId, jsDomEventListTemplateProperty.nodeId, jsDomEventListTemplateProperty.prop);
        if (!(this.mView instanceof PullToRefreshList)) {
            return false;
        }
        BaseListAdapter adapter = ((PullToRefreshList) this.mView).getAdapter();
        if (adapter == null) {
            return true;
        }
        adapter.notifyDataSetChanged();
        ((PullToRefreshList) this.mView).askToUpdate();
        return true;
    }

    public boolean templateAttributeChange(JsDomEventListTemplateAttribute jsDomEventListTemplateAttribute) {
        if (this.mAdapterData == null) {
            return false;
        }
        this.mAdapterData.updateTemplateAttribute(jsDomEventListTemplateAttribute.templateId, jsDomEventListTemplateAttribute.nodeId, jsDomEventListTemplateAttribute.attr);
        if (!(this.mView instanceof PullToRefreshList)) {
            return false;
        }
        BaseListAdapter adapter = ((PullToRefreshList) this.mView).getAdapter();
        if (adapter == null) {
            return true;
        }
        adapter.notifyDataSetChanged();
        ((PullToRefreshList) this.mView).askToUpdate();
        return true;
    }

    public AjxListData getListData() {
        return this.mAdapterData;
    }
}
