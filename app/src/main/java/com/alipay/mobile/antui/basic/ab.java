package com.alipay.mobile.antui.basic;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;

/* compiled from: AUPinnedSectionListView */
final class ab implements OnScrollListener {
    final /* synthetic */ AUPinnedSectionListView a;

    ab(AUPinnedSectionListView this$0) {
        this.a = this$0;
    }

    public final void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.a.mDelegateOnScrollListener != null) {
            this.a.mDelegateOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public final void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.a.mDelegateOnScrollListener != null) {
            this.a.mDelegateOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        ListAdapter adapter = this.a.getAdapter();
        if (adapter != null && visibleItemCount != 0) {
            if (AUPinnedSectionListView.isItemViewTypePinned(adapter, adapter.getItemViewType(firstVisibleItem))) {
                if (this.a.getChildAt(0).getTop() != this.a.getPaddingTop()) {
                    this.a.ensureShadowForPosition(firstVisibleItem, firstVisibleItem, visibleItemCount);
                }
                this.a.destroyPinnedShadow();
            } else {
                int sectionPosition = this.a.findCurrentSectionPosition(firstVisibleItem);
                if (sectionPosition >= 0) {
                    this.a.ensureShadowForPosition(sectionPosition, firstVisibleItem, visibleItemCount);
                }
                this.a.destroyPinnedShadow();
            }
            int lastVisibleItem = firstVisibleItem + visibleItemCount;
            if (!this.a.isLoading && this.a.hasMoreItems && lastVisibleItem == totalItemCount && this.a.onLoadMoreListener != null) {
                this.a.isLoading = true;
                this.a.onLoadMoreListener.onLoadMoreItems();
            }
        }
    }
}
