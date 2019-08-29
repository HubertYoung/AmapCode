package com.autonavi.minimap.ajx3.widget.view.list.sticky;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class StickySectionFooterDecoration extends ItemDecoration {
    private final StickyRecyclerSectionsAdapter mAdapter;
    private int mHorizontalOffset;
    private final OrientationProvider mOrientationProvider;
    private final SectionRenderer mRenderer;
    private final Rect mSectionOffset;
    private int mSectionPosition;
    private int mVerticalOffset;

    public StickySectionFooterDecoration(StickyRecyclerSectionsAdapter stickyRecyclerSectionsAdapter) {
        this(stickyRecyclerSectionsAdapter, new LinearLayoutOrientationProvider());
    }

    private StickySectionFooterDecoration(StickyRecyclerSectionsAdapter stickyRecyclerSectionsAdapter, OrientationProvider orientationProvider) {
        this(stickyRecyclerSectionsAdapter, orientationProvider, new SectionRenderer(orientationProvider));
    }

    private StickySectionFooterDecoration(StickyRecyclerSectionsAdapter stickyRecyclerSectionsAdapter, OrientationProvider orientationProvider, SectionRenderer sectionRenderer) {
        this(stickyRecyclerSectionsAdapter, sectionRenderer, orientationProvider);
    }

    private StickySectionFooterDecoration(StickyRecyclerSectionsAdapter stickyRecyclerSectionsAdapter, SectionRenderer sectionRenderer, OrientationProvider orientationProvider) {
        this.mAdapter = stickyRecyclerSectionsAdapter;
        this.mOrientationProvider = orientationProvider;
        this.mRenderer = sectionRenderer;
        this.mSectionOffset = new Rect();
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        int[] iArr;
        super.onDrawOver(canvas, recyclerView, state);
        if (this.mAdapter.hasSectionFooter()) {
            int height = recyclerView.getHeight();
            int childCount = recyclerView.getChildCount();
            if (childCount <= 0 || this.mAdapter.getItemCount() <= 0) {
                this.mSectionOffset.setEmpty();
                return;
            }
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            int[] iArr2 = null;
            if (layoutManager instanceof LinearLayoutManager) {
                iArr = this.mAdapter.getSectionHeaderAndFooter(((LinearLayoutManager) layoutManager).findLastVisibleItemPosition());
            } else {
                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int[] findLastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
                    if (findLastVisibleItemPositions != null && findLastVisibleItemPositions.length > 0) {
                        iArr = this.mAdapter.getSectionHeaderAndFooter(findLastVisibleItemPositions[findLastVisibleItemPositions.length - 1]);
                    }
                }
                iArr = null;
            }
            if (iArr != null && iArr[1] != -1) {
                int i = childCount - 1;
                int[] iArr3 = null;
                while (true) {
                    if (i < 0) {
                        break;
                    }
                    int childAdapterPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i));
                    if (childAdapterPosition != -1) {
                        int[] sectionHeaderAndFooter = this.mAdapter.getSectionHeaderAndFooter(childAdapterPosition);
                        if (!(sectionHeaderAndFooter == null || sectionHeaderAndFooter[1] == -1)) {
                            if (iArr3 == null) {
                                iArr3 = sectionHeaderAndFooter;
                            } else if (iArr3[1] != sectionHeaderAndFooter[1]) {
                                iArr2 = sectionHeaderAndFooter;
                                break;
                            }
                        }
                    }
                    i--;
                }
                if (iArr3 == null || iArr3[1] == -1) {
                    this.mSectionOffset.setEmpty();
                    return;
                }
                View sectionFooterView = this.mAdapter.getSectionFooterView(recyclerView, iArr3[1]);
                if (sectionFooterView == null) {
                    this.mSectionOffset.setEmpty();
                    return;
                }
                int i2 = 0;
                int height2 = iArr3[0] == -1 ? 0 : this.mAdapter.getSectionHeaderView(recyclerView, iArr3[0]).getHeight();
                int bottom = sectionFooterView.getBottom();
                int left = sectionFooterView.getLeft();
                if (recyclerView.getChildAdapterPosition(sectionFooterView) == -1) {
                    bottom = height;
                    left = 0;
                }
                int orientation = this.mOrientationProvider.getOrientation(recyclerView);
                if (orientation == 1) {
                    if (bottom > height) {
                        bottom = height;
                    }
                } else if (left > 0) {
                    this.mSectionOffset.setEmpty();
                    return;
                }
                if (left >= 0) {
                    i2 = left;
                }
                if (bottom >= height) {
                    height = bottom;
                }
                this.mSectionOffset.set(i2, height - sectionFooterView.getHeight(), sectionFooterView.getWidth() + i2, height);
                if (orientation == 1) {
                    if (this.mVerticalOffset > 0) {
                        this.mSectionOffset.top += this.mVerticalOffset;
                        this.mSectionOffset.bottom += this.mVerticalOffset;
                    }
                } else if (this.mHorizontalOffset > 0) {
                    this.mSectionOffset.left += this.mHorizontalOffset;
                    this.mSectionOffset.right += this.mHorizontalOffset;
                }
                if (!(iArr2 == null || iArr2[1] == -1)) {
                    View sectionFooterView2 = this.mAdapter.getSectionFooterView(recyclerView, iArr2[1]);
                    if (sectionFooterView2 != null) {
                        if (orientation == 1) {
                            int bottom2 = sectionFooterView2.getBottom() + height2;
                            if (bottom2 - this.mVerticalOffset >= this.mSectionOffset.top) {
                                this.mSectionOffset.top = bottom2;
                                this.mSectionOffset.bottom = this.mSectionOffset.height() + bottom2;
                            }
                        } else {
                            int left2 = sectionFooterView2.getLeft();
                            if (left2 - this.mHorizontalOffset < this.mSectionOffset.width()) {
                                Rect rect = this.mSectionOffset;
                                rect.left = left2 - rect.width();
                                this.mSectionOffset.right = left2;
                            }
                        }
                    }
                }
                this.mSectionPosition = iArr3[1];
                this.mRenderer.drawSection(recyclerView, canvas, sectionFooterView, this.mSectionOffset);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int findSectionPositionUnder(int i, int i2) {
        if (this.mSectionOffset.contains(i, i2)) {
            return this.mSectionPosition;
        }
        return -1;
    }

    public void setPullOffset(int i, int i2) {
        this.mVerticalOffset = i;
        this.mHorizontalOffset = i2;
    }
}
