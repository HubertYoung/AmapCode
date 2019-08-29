package com.autonavi.map.core.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class RouteLineView extends RelativeLayout implements brh {
    private LinearLayout linearPathContainer;

    public RouteLineView(Context context) {
        this(context, null);
    }

    public RouteLineView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteLineView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(LayoutInflater.from(context).inflate(R.layout.route_line_view, this, true));
    }

    private void initView(View view) {
        if (view != null) {
            this.linearPathContainer = (LinearLayout) view.findViewById(R.id.linear_path_container);
        }
    }

    public void setPathClickListener(OnClickListener onClickListener) {
        if (this.linearPathContainer != null) {
            this.linearPathContainer.setOnClickListener(onClickListener);
        }
    }

    public void setOnLongClickListener(@Nullable OnLongClickListener onLongClickListener) {
        if (this.linearPathContainer != null) {
            this.linearPathContainer.setOnLongClickListener(onLongClickListener);
        }
    }
}
