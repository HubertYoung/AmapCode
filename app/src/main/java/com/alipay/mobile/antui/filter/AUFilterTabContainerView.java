package com.alipay.mobile.antui.filter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.model.FilterItemData;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

public class AUFilterTabContainerView extends LinearLayout {
    private Context context;
    private int initWidth = -1;
    private List<FilterItemData> list;
    private OnClickListener listener;
    private int mMargin = 8;
    private int maxLine = 0;
    private int paddingLeft;
    private int paddingRight;
    private Resources res;
    private int row = 3;
    int tagWidth;
    private Drawable tvBgDrawable;
    private int width;

    public void setMaxLine(int maxLine2) {
        this.maxLine = maxLine2;
    }

    public void setmMargin(int mMargin2) {
        this.mMargin = mMargin2;
    }

    public void setRow(int row2) {
        this.row = row2;
    }

    public AUFilterTabContainerView(Context context2) {
        super(context2);
        init(context2);
    }

    public AUFilterTabContainerView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        init(context2);
        this.res = context2.getResources();
    }

    public AUFilterTabContainerView(Context context2, AttributeSet attrs, int defStyleAttr) {
        super(context2, attrs, defStyleAttr);
        init(context2);
        this.res = context2.getResources();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void init(Context context2) {
        this.context = context2;
        this.list = new ArrayList();
        setOrientation(1);
    }

    public void setInitWidth(int width2) {
        this.initWidth = width2;
    }

    public void setDatas(List<FilterItemData> datas) {
        if (datas != null) {
            this.list.clear();
            this.list.addAll(datas);
            this.paddingLeft = getPaddingLeft();
            this.paddingRight = getPaddingRight();
            this.tagWidth = getTabWidth(this.row, this.mMargin);
            initTabs();
        }
    }

    public void setTextBgDrawable(Drawable bgDrawable) {
        this.tvBgDrawable = bgDrawable;
    }

    private void initTabs() {
        removeAllViews();
        int contentLength = 0;
        int margin = this.mMargin;
        int margin_v = 0;
        LinearLayout lineContainer = null;
        int size = this.list.size();
        if (this.maxLine > 0) {
            if (this.list.size() > this.maxLine * this.row) {
                size = this.maxLine * this.row;
            } else {
                size = this.list.size();
            }
        }
        for (int i = 0; i < size; i++) {
            View itemView = LayoutInflater.from(this.context).inflate(this.res.getLayout(R.layout.filter_tab_view), this, false);
            itemView.setOnClickListener(this.listener);
            itemView.setTag(this.list.get(i));
            TextView textView = (TextView) itemView.findViewById(R.id.tv_menu_name);
            Drawable bgDrawable = this.res.getDrawable(R.drawable.search_tag_text_bound);
            try {
                textView.setTextColor(ColorStateList.createFromXml(this.res, this.res.getXml(R.xml.search_menu_filter_text)));
            } catch (XmlPullParserException e) {
                AuiLogger.error("FilterTabContainerView", "FilterTabContainerView: e" + e);
            } catch (IOException e2) {
                AuiLogger.error("FilterTabContainerView", "FilterTabContainerView: e" + e2);
            }
            if (this.tvBgDrawable != null) {
                textView.setBackgroundDrawable(this.tvBgDrawable);
            } else {
                textView.setBackgroundDrawable(bgDrawable);
            }
            String itemName = this.list.get(i).name;
            textView.setText(itemName);
            itemView.setContentDescription(itemName);
            textView.setWidth(this.tagWidth);
            textView.setSelected(this.list.get(i).isSelect);
            itemView.setSelected(this.list.get(i).isSelect);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            if (lineContainer == null || this.tagWidth + contentLength > this.width) {
                lineContainer = new LinearLayout(this.context);
                LayoutParams layoutParams2 = new LayoutParams(-1, -2);
                layoutParams2.setMargins(0, margin_v, 0, 0);
                addView(lineContainer, layoutParams2);
                contentLength = 0;
            } else {
                layoutParams.setMargins(margin, 0, 0, 0);
            }
            margin_v = this.mMargin;
            lineContainer.addView(itemView, layoutParams);
            contentLength += this.tagWidth + margin;
        }
    }

    private int getTabWidth(int row2, int margin) {
        if (this.width == 0) {
            int sWidth = this.res.getDisplayMetrics().widthPixels;
            if (this.initWidth != -1) {
                sWidth = this.initWidth;
            }
            this.width = (sWidth - this.paddingLeft) - this.paddingRight;
        }
        return (this.width - ((row2 - 1) * margin)) / row2;
    }

    public void setOnTagClickListener(OnClickListener listener2) {
        this.listener = listener2;
    }

    public void clearSelect() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            updatelayout(getChildAt(i));
            getChildAt(i).setSelected(false);
        }
    }

    private void updatelayout(View view) {
        if (view instanceof LinearLayout) {
            LinearLayout child = (LinearLayout) view;
            int countSub = child.getChildCount();
            for (int i = 0; i < countSub; i++) {
                child.getChildAt(i).setSelected(false);
                updatelayout(child.getChildAt(i));
            }
            return;
        }
        view.setSelected(false);
    }

    public void updateSelectDatas() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            checkSelect(getChildAt(i));
        }
    }

    private void checkSelect(View view) {
        LinearLayout child = (LinearLayout) view;
        int lineItemCount = child.getChildCount();
        for (int i = 0; i < lineItemCount; i++) {
            View childView = child.getChildAt(i);
            if ((childView instanceof FilterMenuItemLayout) && childView.getTag() != null) {
                ((FilterItemData) childView.getTag()).isSelect = childView.isSelected();
            }
        }
    }
}
