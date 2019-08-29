package com.autonavi.minimap.ajx3.widget.view.list.sticky;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class StickySectionHeaderDecoration extends ItemDecoration {
    private final StickyRecyclerSectionsAdapter mAdapter;
    private int mHorizontalOffset;
    private final OrientationProvider mOrientationProvider;
    private final SectionRenderer mRenderer;
    public final Rect mSectionOffset;
    private int mSectionPosition;
    private int mVerticalOffset;

    public StickySectionHeaderDecoration(StickyRecyclerSectionsAdapter stickyRecyclerSectionsAdapter) {
        this(stickyRecyclerSectionsAdapter, new LinearLayoutOrientationProvider());
    }

    private StickySectionHeaderDecoration(StickyRecyclerSectionsAdapter stickyRecyclerSectionsAdapter, OrientationProvider orientationProvider) {
        this(stickyRecyclerSectionsAdapter, orientationProvider, new SectionRenderer(orientationProvider));
    }

    private StickySectionHeaderDecoration(StickyRecyclerSectionsAdapter stickyRecyclerSectionsAdapter, OrientationProvider orientationProvider, SectionRenderer sectionRenderer) {
        this(stickyRecyclerSectionsAdapter, sectionRenderer, orientationProvider);
    }

    private StickySectionHeaderDecoration(StickyRecyclerSectionsAdapter stickyRecyclerSectionsAdapter, SectionRenderer sectionRenderer, OrientationProvider orientationProvider) {
        this.mAdapter = stickyRecyclerSectionsAdapter;
        this.mOrientationProvider = orientationProvider;
        this.mRenderer = sectionRenderer;
        this.mSectionOffset = new Rect();
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        super.onDrawOver(canvas, recyclerView, state);
        if (this.mAdapter.hasSectionHeader()) {
            int childCount = recyclerView.getChildCount();
            if (childCount <= 0 || this.mAdapter.getItemCount() <= 0) {
                this.mSectionOffset.setEmpty();
                return;
            }
            View view = null;
            int[] iArr = null;
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                View childAt = recyclerView.getChildAt(i);
                int childAdapterPosition = recyclerView.getChildAdapterPosition(childAt);
                if (childAdapterPosition != -1) {
                    int[] sectionHeaderAndFooter = this.mAdapter.getSectionHeaderAndFooter(childAdapterPosition);
                    if (i == 0 && sectionHeaderAndFooter[0] == -1) {
                        this.mSectionOffset.setEmpty();
                        return;
                    } else if (iArr != null || sectionHeaderAndFooter[0] == -1) {
                        if (!(iArr == null || iArr[0] == sectionHeaderAndFooter[0])) {
                            view = childAt;
                            break;
                        }
                    } else {
                        iArr = sectionHeaderAndFooter;
                    }
                }
                i++;
            }
            if (iArr == null || iArr[0] == -1) {
                this.mSectionOffset.setEmpty();
                return;
            }
            View sectionHeaderView = this.mAdapter.getSectionHeaderView(recyclerView, iArr[0]);
            if (sectionHeaderView == null) {
                this.mSectionOffset.setEmpty();
                return;
            }
            int top = sectionHeaderView.getTop();
            int left = sectionHeaderView.getLeft();
            if (recyclerView.getChildAdapterPosition(sectionHeaderView) == -1 || recyclerView.getChildItemId(sectionHeaderView) == -1) {
                top = 0;
                left = 0;
            }
            int orientation = this.mOrientationProvider.getOrientation(recyclerView);
            if (orientation == 1) {
                if (top > 0) {
                    this.mSectionOffset.setEmpty();
                    return;
                }
            } else if (left > 0) {
                this.mSectionOffset.setEmpty();
                return;
            }
            if (left < 0) {
                left = 0;
            }
            if (top < 0) {
                top = 0;
            }
            this.mSectionOffset.set(left, top, sectionHeaderView.getWidth() + left, sectionHeaderView.getHeight() + top);
            if (orientation == 1) {
                if (this.mVerticalOffset > 0) {
                    this.mSectionOffset.top += this.mVerticalOffset;
                    this.mSectionOffset.bottom += this.mVerticalOffset;
                }
            } else if (this.mHorizontalOffset > 0) {
                this.mSectionOffset.left += this.mHorizontalOffset;
                this.mSectionOffset.right += this.mHorizontalOffset;
            }
            if (view != null) {
                if (orientation == 1) {
                    int height = this.mSectionOffset.height() + (iArr[1] == -1 ? 0 : this.mAdapter.getSectionFooterView(recyclerView, iArr[1]).getHeight());
                    int top2 = view.getTop();
                    if (top2 - this.mVerticalOffset <= height) {
                        this.mSectionOffset.top = top2 - height;
                        this.mSectionOffset.bottom = top2;
                    }
                } else {
                    int left2 = view.getLeft();
                    if (left2 - this.mHorizontalOffset < this.mSectionOffset.width()) {
                        Rect rect = this.mSectionOffset;
                        rect.left = left2 - rect.width();
                        this.mSectionOffset.right = left2;
                    }
                }
            }
            this.mSectionPosition = iArr[0];
            this.mRenderer.drawSection(recyclerView, canvas, sectionHeaderView, this.mSectionOffset);
        }
    }

    public int findSectionPositionUnder(int i, int i2) {
        if (this.mSectionOffset.contains(i, i2)) {
            return this.mSectionPosition;
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public Rect getRenderRect() {
        return this.mSectionOffset;
    }

    public void setPullOffset(int i, int i2) {
        this.mVerticalOffset = i;
        this.mHorizontalOffset = i2;
    }
}
