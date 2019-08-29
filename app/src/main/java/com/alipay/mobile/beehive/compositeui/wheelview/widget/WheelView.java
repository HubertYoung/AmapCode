package com.alipay.mobile.beehive.compositeui.wheelview.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class WheelView extends ScrollView {
    private static final int DELAY = 50;
    public static final int LINE_COLOR = -8139290;
    public static final int OFF_SET = 1;
    public static final int TEXT_COLOR_FOCUS = -16611122;
    public static final int TEXT_COLOR_NORMAL = -4473925;
    public static final int TEXT_SIZE = 16;
    private Context context;
    private int displayItemCount;
    /* access modifiers changed from: private */
    public int initialY;
    private boolean isUserScroll = false;
    /* access modifiers changed from: private */
    public int itemHeight = 0;
    private List<String> items = new ArrayList();
    private int lineColor = -8139290;
    private boolean lineVisible = true;
    /* access modifiers changed from: private */
    public int offset = 1;
    private OnWheelViewListener onWheelViewListener;
    /* access modifiers changed from: private */
    public Paint paint;
    private float previousY = 0.0f;
    private Runnable scrollerTask = new a(this, 0);
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

    private class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(WheelView x0, byte b) {
            this();
        }

        public final void run() {
            if (WheelView.this.itemHeight == 0) {
                LoggerFactory.getTraceLogger().debug("compositeui", "itemHeight is zero");
                return;
            }
            if (WheelView.this.initialY - WheelView.this.getScrollY() == 0) {
                final int remainder = WheelView.this.initialY % WheelView.this.itemHeight;
                final int divided = WheelView.this.initialY / WheelView.this.itemHeight;
                LoggerFactory.getTraceLogger().debug("compositeui", "initialY: " + WheelView.this.initialY + ", remainder: " + remainder + ", divided: " + divided);
                if (remainder == 0) {
                    WheelView.this.selectedIndex = WheelView.this.offset + divided;
                    WheelView.this.onSelectedCallBack();
                } else if (remainder > WheelView.this.itemHeight / 2) {
                    WheelView.this.post(new Runnable() {
                        public final void run() {
                            WheelView.this.smoothScrollTo(0, (WheelView.this.initialY - remainder) + WheelView.this.itemHeight);
                            WheelView.this.selectedIndex = divided + WheelView.this.offset + 1;
                            WheelView.this.onSelectedCallBack();
                        }
                    });
                } else {
                    WheelView.this.post(new Runnable() {
                        public final void run() {
                            WheelView.this.smoothScrollTo(0, WheelView.this.initialY - remainder);
                            WheelView.this.selectedIndex = divided + WheelView.this.offset;
                            WheelView.this.onSelectedCallBack();
                        }
                    });
                }
            } else {
                WheelView.this.startScrollerTask();
            }
        }
    }

    public WheelView(Context context2) {
        super(context2);
        init(context2);
    }

    public WheelView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        init(context2);
    }

    public WheelView(Context context2, AttributeSet attrs, int defStyle) {
        super(context2, attrs, defStyle);
        init(context2);
    }

    private void init(Context context2) {
        this.context = context2;
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
            LoggerFactory.getTraceLogger().debug("compositeui", "itemHeight: " + this.itemHeight);
            this.views.setLayoutParams(new LayoutParams(-1, this.itemHeight * this.displayItemCount));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
            if (lp == null) {
                lp = new LinearLayout.LayoutParams(-1, -2);
            }
            setLayoutParams(new LinearLayout.LayoutParams(lp.width, this.itemHeight * this.displayItemCount));
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
            LoggerFactory.getTraceLogger().debug("compositeui", "viewWidth: " + this.viewWidth);
        }
        if (this.lineVisible) {
            if (this.paint == null) {
                this.paint = new Paint();
                this.paint.setColor(this.lineColor);
                this.paint.setStrokeWidth((float) dip2px(1.0f));
            }
            super.setBackgroundDrawable(new Drawable() {
                public final void draw(Canvas canvas) {
                    int[] areaBorder = WheelView.this.obtainSelectedAreaBorder();
                    canvas.drawLine((float) (WheelView.this.viewWidth / 6), (float) areaBorder[0], (float) ((WheelView.this.viewWidth * 5) / 6), (float) areaBorder[0], WheelView.this.paint);
                    canvas.drawLine((float) (WheelView.this.viewWidth / 6), (float) areaBorder[1], (float) ((WheelView.this.viewWidth * 5) / 6), (float) areaBorder[1], WheelView.this.paint);
                }

                public final void setAlpha(int alpha) {
                }

                public final void setColorFilter(ColorFilter cf) {
                }

                public final int getOpacity() {
                    return 0;
                }
            });
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
        LoggerFactory.getTraceLogger().debug("compositeui", "w: " + w + ", h: " + h + ", oldw: " + oldw + ", oldh: " + oldh);
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
                LoggerFactory.getTraceLogger().debug("compositeui", String.format("items=%s, offset=%s", new Object[]{Integer.valueOf(this.items.size()), Integer.valueOf(this.offset)}));
                LoggerFactory.getTraceLogger().debug("compositeui", "selectedIndex=" + this.selectedIndex);
                LoggerFactory.getTraceLogger().debug("compositeui", "delta=" + (ev.getY() - this.previousY));
                this.isUserScroll = true;
                startScrollerTask();
                break;
        }
        return super.onTouchEvent(ev);
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

    private void setSelectedIndex(final int index) {
        this.isUserScroll = false;
        post(new Runnable() {
            public final void run() {
                WheelView.this.scrollTo(0, index * WheelView.this.itemHeight);
                WheelView.this.selectedIndex = index + WheelView.this.offset;
                WheelView.this.onSelectedCallBack();
            }
        });
    }

    public void setSelectedItem(String item) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).equals(item)) {
                setSelectedIndex(i - this.offset);
                return;
            }
        }
    }

    @Deprecated
    public String getSeletedItem() {
        return getSelectedItem();
    }

    public String getSelectedItem() {
        return this.items.get(this.selectedIndex);
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

    public CharSequence getContentDescription() {
        if (this.items == null || this.selectedIndex < 0 || this.selectedIndex >= this.items.size()) {
            return "";
        }
        return this.items.get(this.selectedIndex);
    }
}
