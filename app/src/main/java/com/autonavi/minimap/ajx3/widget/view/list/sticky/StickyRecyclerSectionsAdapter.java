package com.autonavi.minimap.ajx3.widget.view.list.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface StickyRecyclerSectionsAdapter {
    int getItemCount();

    View getSectionFooterView(RecyclerView recyclerView, int i);

    int[] getSectionHeaderAndFooter(int i);

    View getSectionHeaderView(RecyclerView recyclerView, int i);

    boolean hasSectionFooter();

    boolean hasSectionHeader();
}
