package com.alipay.mobile.antui.bar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import java.util.List;

public class AUVerticalTabView extends AULinearLayout {
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public AUVerticalTabView(Context context) {
        super(context);
        init();
    }

    public AUVerticalTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AUVerticalTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(1);
        setBackgroundColor(getResources().getColor(R.color.AU_COLOR_CLIENT_BG1));
        setClickable(true);
    }

    public void setItems(List<String> items) {
        removeAllViews();
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                AUVerticalTabItemView itemView = new AUVerticalTabItemView(getContext());
                itemView.getNameView().setText(items.get(i));
                itemView.setTag(Integer.valueOf(i));
                itemView.setOnClickListener(new a(this));
                addView(itemView);
            }
        }
    }

    public void setSelected(int index) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView instanceof AUVerticalTabItemView) {
                AUVerticalTabItemView verticalTabItemView = (AUVerticalTabItemView) childView;
                if (index == i) {
                    verticalTabItemView.setSelected(true);
                } else {
                    verticalTabItemView.setSelected(false);
                }
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
