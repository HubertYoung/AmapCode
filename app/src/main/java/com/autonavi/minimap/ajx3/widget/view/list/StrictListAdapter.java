package com.autonavi.minimap.ajx3.widget.view.list;

import android.content.Context;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.ListNodeData;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.view.AjxAbsoluteLayout;
import java.util.HashMap;

public class StrictListAdapter extends BaseListAdapter<StrictViewHolder> {
    private IAjxContext ajxContext;
    private ListNodeData listNodeData;
    private int mCurViewTypeCount = 0;
    HashMap<Long, View> mSectionCache = new HashMap<>();
    private LongSparseArray<Integer> mTemplateId2VType = new LongSparseArray<>();
    private LongSparseArray<Long> mVType2TemplateId = new LongSparseArray<>();

    static class StrictViewHolder extends ViewHolder {
        public boolean needMergeData;

        StrictViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: private */
        public View getInnerView() {
            return ((ViewGroup) this.itemView).getChildAt(0);
        }
    }

    public int getScrollToPosition(float f, boolean z) {
        return -1;
    }

    public int getScrollToPosition(long j) {
        return -1;
    }

    public int getTargetScrollOffsetY(long j) {
        return -1;
    }

    public boolean hasSectionFooter() {
        return true;
    }

    public boolean hasSectionHeader() {
        return true;
    }

    public StrictListAdapter(IAjxContext iAjxContext, ListNodeData listNodeData2) {
        this.ajxContext = iAjxContext;
        this.listNodeData = listNodeData2;
    }

    private ViewGroup createItemView(Context context, int i) {
        AjxAbsoluteLayout ajxAbsoluteLayout = new AjxAbsoluteLayout(this.ajxContext);
        ajxAbsoluteLayout.setMotionEventSplittingEnabled(false);
        this.ajxContext.getDomTree().getAjxStricyListManager().createItemStrictly(ajxAbsoluteLayout, this.listNodeData.getTemplateById(((Long) this.mVType2TemplateId.get((long) i)).longValue()), ajxAbsoluteLayout);
        return ajxAbsoluteLayout;
    }

    public StrictViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new StrictViewHolder(createItemView(viewGroup.getContext(), i));
    }

    public void onBindViewHolder(StrictViewHolder strictViewHolder, int i) {
        long cell = this.listNodeData.getCell(i);
        if (strictViewHolder.needMergeData) {
            long mergeData = this.listNodeData.getMergeData(cell, this.listNodeData.getNodeTemplate(cell));
            this.ajxContext.getDomTree().getAjxStricyListManager().bindItemStrictly(strictViewHolder.getInnerView(), mergeData, (AjxAbsoluteLayout) strictViewHolder.itemView);
            ListNodeData.releaseNode(mergeData);
            return;
        }
        this.ajxContext.getDomTree().getAjxStricyListManager().bindItemStrictly(strictViewHolder.getInnerView(), cell, (AjxAbsoluteLayout) strictViewHolder.itemView);
        strictViewHolder.needMergeData = true;
    }

    public int getItemViewType(int i) {
        long templateId = this.listNodeData.getTemplateId(i);
        Integer num = (Integer) this.mTemplateId2VType.get(templateId);
        if (num != null) {
            return num.intValue();
        }
        int i2 = this.mCurViewTypeCount;
        this.mTemplateId2VType.put(templateId, Integer.valueOf(i2));
        this.mVType2TemplateId.put((long) i2, Long.valueOf(templateId));
        this.mCurViewTypeCount++;
        return i2;
    }

    public int getItemCount() {
        return this.listNodeData.getCellCount();
    }

    public int[] getSectionHeaderAndFooter(int i) {
        return new int[]{this.listNodeData.getSectionHeaderIndex(i), this.listNodeData.getSectionFooterIndex(i)};
    }

    public View getSectionHeaderView(RecyclerView recyclerView, int i) {
        long sectionHeaderAtPosition = this.listNodeData.getSectionHeaderAtPosition(i);
        View findViewInChildren = findViewInChildren(recyclerView, i);
        if (findViewInChildren != null) {
            this.mSectionCache.put(Long.valueOf(sectionHeaderAtPosition), findViewInChildren);
            return findViewInChildren;
        }
        View view = this.mSectionCache.get(Long.valueOf(sectionHeaderAtPosition));
        return view == null ? makeSectionView(recyclerView, i, sectionHeaderAtPosition) : view;
    }

    public View getSectionFooterView(RecyclerView recyclerView, int i) {
        long sectionFooterAtPosition = this.listNodeData.getSectionFooterAtPosition(i);
        View findViewInChildren = findViewInChildren(recyclerView, i);
        if (findViewInChildren != null) {
            this.mSectionCache.put(Long.valueOf(sectionFooterAtPosition), findViewInChildren);
            return findViewInChildren;
        }
        View view = this.mSectionCache.get(Long.valueOf(sectionFooterAtPosition));
        return view == null ? makeSectionView(recyclerView, i, sectionFooterAtPosition) : view;
    }

    public void removeViewType() {
        this.mVType2TemplateId.clear();
        this.mTemplateId2VType.clear();
    }

    private ViewGroup makeSectionView(RecyclerView recyclerView, int i, long j) {
        ViewGroup createItemView = createItemView(recyclerView.getContext(), getItemViewType(i));
        this.ajxContext.getDomTree().getAjxStricyListManager().bindItemStrictly(createItemView.getChildAt(0), j, (AjxAbsoluteLayout) createItemView);
        measureView(recyclerView, createItemView);
        this.mSectionCache.put(Long.valueOf(j), createItemView);
        return createItemView;
    }

    private View findViewInChildren(RecyclerView recyclerView, int i) {
        int childCount = recyclerView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            if (i == recyclerView.getChildAdapterPosition(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    public int getCellTotalHeight() {
        return DimensionUtils.standardUnitToPixel(this.listNodeData.getCellTotalHeight(getItemCount()));
    }

    public int getCellTotalHeight(int i) {
        return DimensionUtils.standardUnitToPixel(this.listNodeData.getCellTotalHeight(i));
    }

    public void removeSectionCache() {
        this.mSectionCache.clear();
    }
}
