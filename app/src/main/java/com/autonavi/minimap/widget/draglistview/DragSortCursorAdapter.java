package com.autonavi.minimap.widget.draglistview;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.widget.draglistview.DragSortListView.DragSortListener;
import java.util.ArrayList;

public abstract class DragSortCursorAdapter extends CursorAdapter implements DragSortListener {
    public static final int REMOVED = -1;
    private SparseIntArray mListMapping = new SparseIntArray();
    private ArrayList<Integer> mRemovedCursorPositions = new ArrayList<>();

    public void drag(int i, int i2) {
    }

    public DragSortCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    public DragSortCursorAdapter(Context context, Cursor cursor, boolean z) {
        super(context, cursor, z);
    }

    public DragSortCursorAdapter(Context context, Cursor cursor, int i) {
        super(context, cursor, i);
    }

    public Cursor swapCursor(Cursor cursor) {
        Cursor swapCursor = super.swapCursor(cursor);
        resetMappings();
        return swapCursor;
    }

    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
        resetMappings();
    }

    public void reset() {
        resetMappings();
        notifyDataSetChanged();
    }

    private void resetMappings() {
        this.mListMapping.clear();
        this.mRemovedCursorPositions.clear();
    }

    public Object getItem(int i) {
        return super.getItem(this.mListMapping.get(i, i));
    }

    public long getItemId(int i) {
        return super.getItemId(this.mListMapping.get(i, i));
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return super.getDropDownView(this.mListMapping.get(i, i), view, viewGroup);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return super.getView(this.mListMapping.get(i, i), view, viewGroup);
    }

    public void drop(int i, int i2) {
        if (i != i2) {
            int i3 = this.mListMapping.get(i, i);
            if (i > i2) {
                while (i > i2) {
                    SparseIntArray sparseIntArray = this.mListMapping;
                    int i4 = i - 1;
                    sparseIntArray.put(i, sparseIntArray.get(i4, i4));
                    i--;
                }
            } else {
                while (i < i2) {
                    SparseIntArray sparseIntArray2 = this.mListMapping;
                    int i5 = i + 1;
                    sparseIntArray2.put(i, sparseIntArray2.get(i5, i5));
                    i = i5;
                }
            }
            this.mListMapping.put(i2, i3);
            cleanMapping();
            notifyDataSetChanged();
        }
    }

    public void remove(int i) {
        int i2 = this.mListMapping.get(i, i);
        if (!this.mRemovedCursorPositions.contains(Integer.valueOf(i2))) {
            this.mRemovedCursorPositions.add(Integer.valueOf(i2));
        }
        int count = getCount();
        while (i < count) {
            SparseIntArray sparseIntArray = this.mListMapping;
            int i3 = i + 1;
            sparseIntArray.put(i, sparseIntArray.get(i3, i3));
            i = i3;
        }
        this.mListMapping.delete(count);
        cleanMapping();
        notifyDataSetChanged();
    }

    private void cleanMapping() {
        ArrayList arrayList = new ArrayList();
        int size = this.mListMapping.size();
        for (int i = 0; i < size; i++) {
            if (this.mListMapping.keyAt(i) == this.mListMapping.valueAt(i)) {
                arrayList.add(Integer.valueOf(this.mListMapping.keyAt(i)));
            }
        }
        int size2 = arrayList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mListMapping.delete(((Integer) arrayList.get(i2)).intValue());
        }
    }

    public int getCount() {
        return super.getCount() - this.mRemovedCursorPositions.size();
    }

    public int getCursorPosition(int i) {
        return this.mListMapping.get(i, i);
    }

    public ArrayList<Integer> getCursorPositions() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            arrayList.add(Integer.valueOf(this.mListMapping.get(i, i)));
        }
        return arrayList;
    }

    public int getListPosition(int i) {
        if (this.mRemovedCursorPositions.contains(Integer.valueOf(i))) {
            return -1;
        }
        int indexOfValue = this.mListMapping.indexOfValue(i);
        if (indexOfValue < 0) {
            return i;
        }
        return this.mListMapping.keyAt(indexOfValue);
    }
}
