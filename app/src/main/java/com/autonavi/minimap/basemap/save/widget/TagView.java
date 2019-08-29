package com.autonavi.minimap.basemap.save.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TagView extends TextView {
    public boolean mIsChecked;
    public String mOriginTagStr;
    public String mShowTagStr;

    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TagView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
