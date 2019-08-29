package com.alipay.mobile.antui.picker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUScrollView;
import com.alipay.mobile.antui.model.PickerDataModel;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.util.ArrayList;
import java.util.List;

public class AUWheelView extends AUScrollView {
    private static final int DELAY = 50;
    public static final int LINE_COLOR = -8139290;
    public static final int OFF_SET = 1;
    public static final int TEXT_COLOR_FOCUS = -16611122;
    public static final int TEXT_COLOR_NORMAL = -4473925;
    public static final int TEXT_SIZE = 16;
    private Context context;
    private List<PickerDataModel> curData;
    private int displayItemCount;
    /* access modifiers changed from: private */
    public int initialY;
    private boolean isUserScroll = false;
    /* access modifiers changed from: private */
    public int itemHeight = 0;
    private List<String> items = new ArrayList();
    private int lineColor = -8139290;
    private boolean lineVisible = true;
    public AUWheelView next;
    /* access modifiers changed from: private */
    public int offset = 1;
    private OnWheelViewListener onWheelViewListener;
    /* access modifiers changed from: private */
    public Paint paint;
    private float previousY = 0.0f;
    private Runnable scrollerTask = new t(this, 0);
    private int[] selectedAreaBorder;
    /* access modifiers changed from: private */
    public int selectedIndex = 1;
    private int textColorFocus = -16611122;
    private int textColorNormal = -4473925;
    private int textSize = 16;
    /* access modifiers changed from: private */
    public int viewWidth;
    private LinearLayout views;

    public interface OnWheelViewListener {
        void onSelected(boolean z, int i, String str);
    }

    public AUWheelView(Context context2) {
        super(context2);
        init(context2);
    }

    public AUWheelView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        init(context2);
    }

    public AUWheelView(Context context2, AttributeSet attrs, int defStyle) {
        super(context2, attrs, defStyle);
        init(context2);
    }

    private void init(Context context2) {
        this.context = context2;
        this.textColorNormal = getResources().getColor(R.color.AU_COLOR_SUB_CONTENT);
        this.textColorFocus = getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT);
        this.lineColor = getResources().getColor(R.color.AU_COLOR_DIALOG_DIVIDER_COLOR);
        setFadingEdgeLength(0);
        if (VERSION.SDK_INT >= 9) {
            setOverScrollMode(2);
        }
        setVerticalScrollBarEnabled(false);
        this.views = new LinearLayout(context2);
        this.views.setOrientation(1);
        addView(this.views);
    }

    /* access modifiers changed from: private */
    public void startScrollerTask() {
        this.initialY = getScrollY();
        postDelayed(this.scrollerTask, 50);
    }

    private void initData() {
        this.displayItemCount = (this.offset * 2) + 1;
        this.views.removeAllViews();
        for (String item : this.items) {
            this.views.addView(createView(item));
        }
        refreshItemView(this.itemHeight * (this.selectedIndex - this.offset));
    }

    private TextView createView(String item) {
        TextView tv2 = new TextView(this.context);
        tv2.setLayoutParams(new LayoutParams(-1, -2));
        tv2.setSingleLine(true);
        tv2.setEllipsize(TruncateAt.END);
        tv2.setText(item);
        tv2.setTextSize((float) this.textSize);
        tv2.setGravity(17);
        int padding = dip2px(15.0f);
        tv2.setPadding(padding, padding, padding, padding);
        if (this.itemHeight == 0) {
            this.itemHeight = getViewMeasuredHeight(tv2);
            Log.d("compositeui", "itemHeight: " + this.itemHeight);
            this.views.setLayoutParams(new LayoutParams(-1, this.itemHeight * this.displayItemCount));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
            if (lp == null) {
                lp = new LinearLayout.LayoutParams(-1, -2);
            }
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-2, this.itemHeight * this.displayItemCount);
            layoutParams1.leftMargin = lp.leftMargin;
            setLayoutParams(layoutParams1);
        }
        return tv2;
    }

    private void refreshItemView(int y) {
        int position = (y / this.itemHeight) + this.offset;
        int remainder = y % this.itemHeight;
        int divided = y / this.itemHeight;
        if (remainder == 0) {
            position = divided + this.offset;
        } else if (remainder > this.itemHeight / 2) {
            position = this.offset + divided + 1;
        }
        int childSize = this.views.getChildCount();
        int i = 0;
        while (i < childSize) {
            TextView itemView = (TextView) this.views.getChildAt(i);
            if (itemView != null) {
                if (position == i) {
                    itemView.setTextColor(this.textColorFocus);
                } else {
                    itemView.setTextColor(this.textColorNormal);
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public int[] obtainSelectedAreaBorder() {
        if (this.selectedAreaBorder == null) {
            this.selectedAreaBorder = new int[2];
            this.selectedAreaBorder[0] = this.itemHeight * this.offset;
            this.selectedAreaBorder[1] = this.itemHeight * (this.offset + 1);
        }
        return this.selectedAreaBorder;
    }

    public void setSeledIndex(int index) {
        this.selectedIndex = index;
    }

    /* access modifiers changed from: private */
    public void onSelectedCallBack() {
        if (this.onWheelViewListener != null) {
            this.onWheelViewListener.onSelected(this.isUserScroll, this.selectedIndex - this.offset, this.items.get(this.selectedIndex));
        }
    }

    private int dip2px(float dpValue) {
        return (int) ((dpValue * this.context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private int getViewMeasuredHeight(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
        return view.getMeasuredHeight();
    }

    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    public void setBackgroundDrawable(Drawable background) {
        if (this.viewWidth == 0) {
            this.viewWidth = ((Activity) this.context).getWindowManager().getDefaultDisplay().getWidth();
            Log.d("compositeui", "viewWidth: " + this.viewWidth);
        }
        if (this.lineVisible) {
            if (this.paint == null) {
                this.paint = new Paint();
                this.paint.setColor(this.lineColor);
                this.paint.setStrokeWidth((float) dip2px(1.0f));
            }
            super.setBackgroundDrawable(new r(this));
        }
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(t);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("compositeui", "w: " + w + ", h: " + h + ", oldw: " + oldw + ", oldh: " + oldh);
        this.viewWidth = w;
        setBackgroundDrawable(null);
    }

    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                this.previousY = ev.getY();
                break;
            case 1:
                Log.d("compositeui", String.format("items=%s, offset=%s", new Object[]{Integer.valueOf(this.items.size()), Integer.valueOf(this.offset)}));
                Log.d("compositeui", "selectedIndex=" + this.selectedIndex);
                Log.d("compositeui", "delta=" + (ev.getY() - this.previousY));
                this.isUserScroll = true;
                startScrollerTask();
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setPickerDateModel(List<PickerDataModel> models) {
        if (models != null) {
            this.curData = models;
            List tmpList = new ArrayList();
            for (int i = 0; i < models.size(); i++) {
                PickerDataModel model = models.get(i);
                if (model != null && !TextUtils.isEmpty(model.name)) {
                    tmpList.add(model.name);
                }
            }
            setItems(tmpList);
        }
    }

    public List<PickerDataModel> getCurData() {
        return this.curData;
    }

    private void _setItems(List<String> list) {
        this.items.clear();
        this.items.addAll(list);
        for (int i = 0; i < this.offset; i++) {
            this.items.add(0, "");
            this.items.add("");
        }
        initData();
    }

    public void setItems(List<String> list) {
        _setItems(list);
        setSelectedIndex(0);
    }

    public void setItems(List<String> list, int index) {
        _setItems(list);
        setSelectedIndex(index);
    }

    public void setItems(List<String> list, String item) {
        _setItems(list);
        setSelectedItem(item);
    }

    public int getTextSize() {
        return this.textSize;
    }

    public void setTextSize(int textSize2) {
        this.textSize = textSize2;
    }

    public int getTextColor() {
        return this.textColorFocus;
    }

    public void setTextColor(@ColorInt int textColorNormal2, @ColorInt int textColorFocus2) {
        this.textColorNormal = textColorNormal2;
        this.textColorFocus = textColorFocus2;
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColorFocus = textColor;
    }

    public boolean isLineVisible() {
        return this.lineVisible;
    }

    public void setLineVisible(boolean lineVisible2) {
        this.lineVisible = lineVisible2;
    }

    public int getLineColor() {
        return this.lineColor;
    }

    public void setLineColor(@ColorInt int lineColor2) {
        this.lineColor = lineColor2;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset2) {
        if (offset2 <= 0 || offset2 > 4) {
            throw new IllegalArgumentException("Offset must between 1 and 4");
        }
        this.offset = offset2;
    }

    private void setSelectedIndex(int index) {
        this.isUserScroll = false;
        post(new s(this, index));
    }

    public void setSelectedItem(String item) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).equals(item)) {
                setSelectedIndex(i - this.offset);
                return;
            }
        }
    }

    public void setSelectedModel(int index) {
        try {
            setSelectedIndex(index);
        } catch (Exception e) {
            AuiLogger.error("setSelectedModel", "setSelectedModel:" + e);
        }
    }

    @Deprecated
    public String getSeletedItem() {
        return getSelectedItem();
    }

    public String getSelectedItem() {
        if (this.items.isEmpty()) {
            return null;
        }
        return this.items.get(this.selectedIndex);
    }

    public PickerDataModel getSelectModel() {
        if (this.curData != null && this.selectedIndex < this.curData.size() + this.offset && this.selectedIndex >= 0) {
            return this.curData.get(this.selectedIndex - this.offset);
        }
        return null;
    }

    @Deprecated
    public int getSeletedIndex() {
        return getSelectedIndex();
    }

    public int getSelectedIndex() {
        return this.selectedIndex - this.offset;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener2) {
        this.onWheelViewListener = onWheelViewListener2;
    }

    public void register(AUWheelView wheelView) {
        this.next = wheelView;
    }
}
