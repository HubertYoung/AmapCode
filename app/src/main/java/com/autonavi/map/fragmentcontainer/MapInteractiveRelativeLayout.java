package com.autonavi.map.fragmentcontainer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class MapInteractiveRelativeLayout extends RelativeLayout {
    public MapInteractiveRelativeLayout(Context context) {
        super(context);
        setSpecifiedId();
    }

    public MapInteractiveRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setSpecifiedId();
    }

    public MapInteractiveRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setSpecifiedId();
    }

    private void setSpecifiedId() {
        setId(R.id.mapInteractiveRelativeLayout);
    }
}
