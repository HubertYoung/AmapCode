package com.autonavi.minimap.ajx3.widget.view.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxScrollerDomNode;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.AjxAbsoluteLayout;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.IScrollView;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.list.AjxListData.Section;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;
import java.util.HashMap;
import java.util.List;

public class AjxListAdapter extends BaseListAdapter<JsViewHolder> {
    private boolean firstCellBinded = false;
    public final IAjxContext mAjxContext;
    private AjxListData mDataList;
    private AjxList mList = null;
    private RecyclerView mRecyclerView;
    private HashMap<Section, View> mSectionFooters = new HashMap<>();
    private HashMap<Section, View> mSectionHeaderFooters = new HashMap<>();
    private HashMap<Section, View> mSectionHeaders = new HashMap<>();

    static class JsViewHolder extends ViewHolder {
        JsViewHolder(View view) {
            super(view);
        }
    }

    public AjxListAdapter(@NonNull IAjxContext iAjxContext) {
        this.mAjxContext = iAjxContext;
    }

    public void removeSectionCache() {
        this.mSectionHeaders.clear();
        this.mSectionFooters.clear();
        this.mSectionHeaderFooters.clear();
    }

    public void setListData(AjxListData ajxListData) {
        this.mDataList = ajxListData;
        notifyDataSetChanged();
        this.firstCellBinded = false;
    }

    public AjxListData getData() {
        return this.mDataList;
    }

    public int getCellTotalHeight() {
        return DimensionUtils.standardUnitToPixel(this.mDataList.getCellTotalHeight());
    }

    public int getCellTotalHeight(int i) {
        return DimensionUtils.standardUnitToPixel(this.mDataList.getCellTotalHeight(i));
    }

    public int getScrollToPosition(float f, boolean z) {
        return this.mDataList.getScrollToPosition(f, z);
    }

    public int getTargetScrollOffsetY(long j) {
        return DimensionUtils.standardUnitToPixel(this.mDataList.getTargetScrollOffsetY(j));
    }

    public int getScrollToPosition(long j) {
        return this.mDataList.getScrollToPosition(j);
    }

    public void setList(AjxList ajxList) {
        this.mList = ajxList;
    }

    public JsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View createTemplateView = createTemplateView(this.mDataList.getTemplate(i));
        ((AbsoluteLayout) createTemplateView).setMotionEventSplittingEnabled(false);
        return new JsViewHolder(createTemplateView);
    }

    public void onBindViewHolder(JsViewHolder jsViewHolder, int i) {
        AjxListCell cellData = this.mDataList.getCellData(i);
        if (cellData != null) {
            if (i == 0 && !this.firstCellBinded) {
                Ajx.getInstance().addTimestamp("cellShow");
                LogManager.performaceLog("cellShow");
                this.firstCellBinded = true;
            }
            if (cellData.triggerBindAppearedEvent()) {
                if (Ajx.sStartTime == 0) {
                    this.mAjxContext.invokeJsEvent(new Builder().setEventName("appeared").setNodeId(cellData.getId()).build());
                } else {
                    long currentTimeMillis = System.currentTimeMillis();
                    IAjxContext iAjxContext = this.mAjxContext;
                    Builder nodeId = new Builder().setEventName("appeared").setNodeId(cellData.getId());
                    StringBuilder sb = new StringBuilder();
                    sb.append(currentTimeMillis - Ajx.sStartTime);
                    Builder addAttribute = nodeId.addAttribute("loadtime", sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(currentTimeMillis - Ajx.sStartTime);
                    iAjxContext.invokeJsEvent(addAttribute.addContent("loadtime", sb2.toString()).build());
                }
            }
            if (cellData.mIsHeaderOrFooter) {
                Section sectionPosition = this.mDataList.getSectionPosition(i);
                this.mSectionHeaderFooters.put(sectionPosition, jsViewHolder.itemView);
                if (cellData.mIsHeader) {
                    this.mSectionHeaders.put(sectionPosition, jsViewHolder.itemView);
                } else if (cellData.mIsFooter) {
                    this.mSectionFooters.put(sectionPosition, jsViewHolder.itemView);
                }
            }
        }
        boolean z = cellData != null && cellData.mIsHeaderOrFooter;
        if (cellData != null && this.mList.mIsWaterFall) {
            View view = jsViewHolder.itemView;
            LayoutManager layoutManager = this.mList.getLayoutManager();
            if ((layoutManager instanceof StaggeredGridLayoutManager) && (view instanceof AjxAbsoluteLayout)) {
                LayoutParams layoutParams = view.getLayoutParams();
                if (!(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams)) {
                    layoutParams = layoutManager.generateDefaultLayoutParams();
                }
                if (cellData.mIsHeaderOrFooter || cellData.mIsFullSpan) {
                    ((StaggeredGridLayoutManager.LayoutParams) layoutParams).b = true;
                } else {
                    ((StaggeredGridLayoutManager.LayoutParams) layoutParams).b = false;
                }
                view.setLayoutParams(layoutParams);
            }
        }
        bindItemWithRealData(jsViewHolder.itemView, cellData, z, z);
    }

    public void onViewRecycled(JsViewHolder jsViewHolder) {
        super.onViewRecycled(jsViewHolder);
        if (jsViewHolder.itemView instanceof AjxAbsoluteLayout) {
            ViewExtension viewExtension = (ViewExtension) ((AjxAbsoluteLayout) jsViewHolder.itemView).getRealChildAt(0);
            if (viewExtension != null) {
                finishAnimatorByViewExtension(viewExtension);
            }
        }
    }

    private void finishAnimatorByViewExtension(ViewExtension viewExtension) {
        AjxDomNode node = viewExtension.getProperty().getNode();
        if (node instanceof AjxListCell) {
            ((AjxListCell) node).clearTempView();
        }
        if (viewExtension instanceof ViewGroup) {
            int i = 0;
            while (true) {
                ViewGroup viewGroup = (ViewGroup) viewExtension;
                if (i < viewGroup.getChildCount()) {
                    View childAt = viewGroup.getChildAt(i);
                    if (childAt instanceof ViewExtension) {
                        finishAnimatorByViewExtension((ViewExtension) childAt);
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public int getItemViewType(int i) {
        return this.mDataList.getItemViewType(i);
    }

    public int getItemCount() {
        return this.mDataList.getDataCount();
    }

    public boolean hasSectionHeader() {
        return this.mDataList.isHasSectionHeader();
    }

    public boolean hasSectionFooter() {
        return this.mDataList.isHasSectionFooter();
    }

    public int[] getSectionHeaderAndFooter(int i) {
        return this.mDataList.getSectionHeaderAndFooter(i);
    }

    public View getSectionHeaderView(RecyclerView recyclerView, int i) {
        Section sectionPosition = this.mDataList.getSectionPosition(i);
        View view = this.mSectionHeaders.get(sectionPosition);
        if (view != null) {
            return view;
        }
        View makeSectionView = makeSectionView(recyclerView, i);
        this.mSectionHeaders.put(sectionPosition, makeSectionView);
        return makeSectionView;
    }

    public View getSectionFooterView(RecyclerView recyclerView, int i) {
        Section sectionPosition = this.mDataList.getSectionPosition(i);
        View view = this.mSectionFooters.get(sectionPosition);
        if (view != null) {
            return view;
        }
        View makeSectionView = makeSectionView(recyclerView, i);
        this.mSectionFooters.put(sectionPosition, makeSectionView);
        return makeSectionView;
    }

    private View findSectionInChildren(RecyclerView recyclerView, int i) {
        int childCount = recyclerView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            if (i == recyclerView.getChildAdapterPosition(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    private View updateSectionView(RecyclerView recyclerView, View view, int i) {
        bindItemWithRealData(view, this.mDataList.getCellData(i), true, false);
        measureView(recyclerView, view);
        return view;
    }

    private View makeSectionView(RecyclerView recyclerView, int i) {
        View createTemplateView = createTemplateView(this.mDataList.getTemplate(this.mDataList.getItemViewType(i)));
        bindItemWithRealData(createTemplateView, this.mDataList.getCellData(i), true, false);
        measureView(recyclerView, createTemplateView);
        return createTemplateView;
    }

    private View createTemplateView(AjxDomNode ajxDomNode) {
        AjxAbsoluteLayout ajxAbsoluteLayout = new AjxAbsoluteLayout(this.mAjxContext);
        ajxDomNode.initView(this.mAjxContext);
        if (ajxDomNode.getEnterView().getLayoutParams() == null) {
            ajxDomNode.getEnterView().setLayoutParams(ajxAbsoluteLayout.generateDefaultLayoutParams());
        }
        ((ViewExtension) ajxDomNode.getEnterView()).bind(ajxDomNode);
        ajxAbsoluteLayout.addView(ajxDomNode.getEnterView(), ajxDomNode);
        return ajxAbsoluteLayout;
    }

    private void bindItemWithRealData(View view, @Nullable AjxListCell ajxListCell, boolean z, boolean z2) {
        if ((view instanceof AjxAbsoluteLayout) && ajxListCell != null) {
            AjxAbsoluteLayout ajxAbsoluteLayout = (AjxAbsoluteLayout) view;
            long templateId = ajxListCell.getTemplateId();
            View findViewByNodeId = ajxAbsoluteLayout.findViewByNodeId(templateId);
            if (findViewByNodeId == null) {
                findViewByNodeId = ajxAbsoluteLayout.findViewByNodeId(ajxListCell.getId());
            }
            if (findViewByNodeId == null) {
                StringBuilder sb = new StringBuilder("could not find target View- template id: ");
                sb.append(templateId);
                sb.append(" , ");
                sb.append(ajxListCell.getId());
            }
            ajxListCell.setTempView(findViewByNodeId);
            AjxDomNode findTemplateByNodeId = this.mDataList.findTemplateByNodeId(templateId);
            ajxListCell.mergeTemplate(findTemplateByNodeId);
            if (findViewByNodeId instanceof ViewExtension) {
                if ((findViewByNodeId instanceof AjxViewPager) || (findViewByNodeId instanceof HorizontalScroller) || (findViewByNodeId instanceof Scroller)) {
                    handleScrollerBind(ajxAbsoluteLayout, findViewByNodeId, ajxListCell, findTemplateByNodeId, z, z2);
                } else {
                    ((ViewExtension) findViewByNodeId).bind(ajxListCell);
                }
            }
            List<AjxDomNode> children = ajxListCell.getChildren();
            if (children != null) {
                for (AjxDomNode next : children) {
                    if (next instanceof AjxListCell) {
                        bindItemWithRealData(view, (AjxListCell) next, z, z2);
                    }
                }
            }
        }
    }

    private void handleScrollerBind(AjxAbsoluteLayout ajxAbsoluteLayout, View view, AjxListCell ajxListCell, AjxDomNode ajxDomNode, boolean z, boolean z2) {
        View view2;
        ViewExtension viewExtension = (ViewExtension) view;
        BaseProperty property = viewExtension.getProperty();
        if (view instanceof AjxViewPager) {
            AjxViewPager ajxViewPager = (AjxViewPager) view;
            long cellId = property.getCellId();
            viewExtension.bind(ajxListCell);
            if (!(ajxListCell.getId() == cellId || ajxListCell.isNormalView() || ajxListCell.mScrollerData == null)) {
                ajxViewPager.initPage(ajxListCell.mScrollerData.getChildren());
                ajxAbsoluteLayout.saveScrollerView(ajxListCell.mScrollerData, null);
            }
            return;
        }
        boolean z3 = view instanceof HorizontalScroller;
        if (z3 || (view instanceof Scroller)) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (z3) {
                view2 = ajxListCell.checkHorizontalScroller(this.mAjxContext, (HorizontalScroller) viewGroup, (AjxScrollerDomNode) ajxDomNode);
            } else {
                view2 = ajxListCell.checkScroller(this.mAjxContext, (Scroller) viewGroup, (AjxScrollerDomNode) ajxDomNode);
            }
            if (view2 != null) {
                ajxAbsoluteLayout.saveScrollerView(ajxListCell.mScrollerData, ajxDomNode);
                return;
            }
            if (!ajxListCell.isNormalView() && ajxListCell.mScrollerData != null) {
                initScroller(viewGroup, ajxListCell.mScrollerData, z);
                ajxAbsoluteLayout.saveScrollerView(ajxListCell.mScrollerData, null);
            }
            viewExtension.bind(ajxListCell);
        }
    }

    private void initScroller(ViewGroup viewGroup, AjxDomNode ajxDomNode, boolean z) {
        if (ajxDomNode != null) {
            viewGroup.removeAllViews();
            int standardUnitToPixel = DimensionUtils.standardUnitToPixel(ajxDomNode.getWidth());
            int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(ajxDomNode.getHeight());
            if (viewGroup instanceof IScrollView) {
                IScrollView iScrollView = (IScrollView) viewGroup;
                iScrollView.setContentSize(standardUnitToPixel, standardUnitToPixel2);
                if (z) {
                    iScrollView.setAjxList(this);
                }
            }
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null && children.size() > 0) {
                for (AjxDomNode next : children) {
                    next.initView(this.mAjxContext);
                    next.addToViewTree(viewGroup);
                }
            }
        }
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mRecyclerView = null;
    }

    /* access modifiers changed from: 0000 */
    public int getCellPosition(long j) {
        if (this.mDataList == null) {
            return -1;
        }
        return this.mDataList.getCellPosition(j);
    }
}
