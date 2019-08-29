package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class DIYMainMapView extends LinearLayout implements brh {

    public enum Position {
        TOP,
        MIDDLE,
        BOTTOM
    }

    public DIYMainMapView(Context context) {
        this(context, null);
    }

    public DIYMainMapView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DIYMainMapView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(1);
    }

    public void addContentView(DIYEntryView dIYEntryView, LayoutParams layoutParams, Position position) {
        if (dIYEntryView != null && position != null) {
            if (Position.TOP == position) {
                dIYEntryView.setBackgroundResource(R.drawable.icon_c_bg_up);
            } else if (Position.BOTTOM == position) {
                dIYEntryView.setBackgroundResource(R.drawable.icon_c_bg_down);
            } else {
                dIYEntryView.setBackgroundResource(R.drawable.icon_c_bg_mid);
            }
            addView(dIYEntryView, layoutParams);
        }
    }

    public List<DIYEntryView> getAllEntryView() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof DIYEntryView) {
                arrayList.add((DIYEntryView) childAt);
            }
        }
        return arrayList;
    }

    public Position getPosition(DIYEntryView dIYEntryView) {
        Position position = Position.MIDDLE;
        if (dIYEntryView == null) {
            return position;
        }
        int childCount = getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = getChildAt(i);
            if (!(childAt instanceof DIYEntryView) || dIYEntryView != childAt) {
                i++;
            } else if (i == 0) {
                return Position.TOP;
            } else {
                return i == childCount + -1 ? Position.BOTTOM : position;
            }
        }
        return position;
    }
}
