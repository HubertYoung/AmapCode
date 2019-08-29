package com.autonavi.bundle.uitemplate.mapwidget.widget.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class MiniSearchFrameWidget extends AbstractMapWidget<MiniSearchFramePresenter> {
    private TextView mHotTextView;

    public MiniSearchFrameWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, R.layout.map_widget_mini_search_frame_layout);
        loadLayoutRes.setContentDescription(context.getResources().getString(R.string.map_widget_search_text_label));
        this.mHotTextView = (TextView) loadLayoutRes.findViewById(R.id.txt_hotword);
        return loadLayoutRes;
    }

    public void hideSearchIconView() {
        if (this.mContentView != null) {
            this.mContentView.findViewById(R.id.search_icon_view).setVisibility(8);
        }
    }

    public void hideBgView() {
        if (this.mContentView != null) {
            this.mContentView.setBackground(null);
        }
    }

    public String getHotText() {
        return this.mHotTextView != null ? this.mHotTextView.getText().toString() : "";
    }

    public TextView getHotTextView() {
        return this.mHotTextView;
    }

    public void setTextAndColor(CharSequence charSequence, int i) {
        if (this.mHotTextView != null) {
            this.mHotTextView.setText(charSequence);
            this.mHotTextView.setTextColor(i);
        }
    }
}
