package com.autonavi.minimap.ajx3.widget.view.list.sticky;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SectionRenderer {
    private final DimensionCalculator mDimensionCalculator;
    private final OrientationProvider mOrientationProvider;
    private final Rect mTempRect;

    public SectionRenderer(OrientationProvider orientationProvider) {
        this(orientationProvider, new DimensionCalculator());
    }

    private SectionRenderer(OrientationProvider orientationProvider, DimensionCalculator dimensionCalculator) {
        this.mTempRect = new Rect();
        this.mOrientationProvider = orientationProvider;
        this.mDimensionCalculator = dimensionCalculator;
    }

    public void drawSection(RecyclerView recyclerView, Canvas canvas, View view, Rect rect) {
        canvas.save();
        if (recyclerView.getLayoutManager().getClipToPadding()) {
            initClipRectForHeader(this.mTempRect, recyclerView, view);
            canvas.clipRect(this.mTempRect);
        }
        canvas.translate((float) rect.left, (float) rect.top);
        view.draw(canvas);
        canvas.restore();
    }

    private void initClipRectForHeader(Rect rect, RecyclerView recyclerView, View view) {
        this.mDimensionCalculator.initMargins(rect, view);
        if (this.mOrientationProvider.getOrientation(recyclerView) == 1) {
            rect.set(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop(), (recyclerView.getWidth() - recyclerView.getPaddingRight()) - rect.right, recyclerView.getHeight() - recyclerView.getPaddingBottom());
        } else {
            rect.set(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop(), recyclerView.getWidth() - recyclerView.getPaddingRight(), (recyclerView.getHeight() - recyclerView.getPaddingBottom()) - rect.bottom);
        }
    }
}
