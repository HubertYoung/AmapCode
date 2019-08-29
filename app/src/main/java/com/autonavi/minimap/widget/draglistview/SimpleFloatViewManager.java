package com.autonavi.minimap.widget.draglistview;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import com.autonavi.minimap.widget.draglistview.DragSortListView.FloatViewManager;

public class SimpleFloatViewManager implements FloatViewManager {
    private int mFloatBGColor = -16777216;
    private Bitmap mFloatBitmap;
    private ImageView mImageView;
    private ListView mListView;

    public int getFloatViewHeightOffset() {
        return 0;
    }

    public void onDragFloatView(View view, Point point, Point point2) {
    }

    public SimpleFloatViewManager(ListView listView) {
        this.mListView = listView;
    }

    public void setBackgroundColor(int i) {
        this.mFloatBGColor = i;
    }

    public View onCreateFloatView(int i) {
        ListView listView = this.mListView;
        View childAt = listView.getChildAt((i + listView.getHeaderViewsCount()) - this.mListView.getFirstVisiblePosition());
        if (childAt == null) {
            return null;
        }
        childAt.setPressed(false);
        childAt.setDrawingCacheEnabled(true);
        this.mFloatBitmap = Bitmap.createBitmap(childAt.getDrawingCache());
        childAt.setDrawingCacheEnabled(false);
        if (this.mImageView == null) {
            this.mImageView = new ImageView(this.mListView.getContext());
        }
        this.mImageView.setBackgroundColor(this.mFloatBGColor);
        this.mImageView.setPadding(0, 0, 0, 0);
        this.mImageView.setImageBitmap(this.mFloatBitmap);
        this.mImageView.setLayoutParams(new LayoutParams(childAt.getWidth(), childAt.getHeight()));
        return this.mImageView;
    }

    public void onDestroyFloatView(View view) {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(null);
        }
        if (this.mFloatBitmap != null) {
            this.mFloatBitmap.recycle();
            this.mFloatBitmap = null;
        }
    }
}
