package com.autonavi.map.widget.wheel;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.util.LinkedList;
import java.util.List;

public class TimePickerWidgetView extends View {
    private static final int DEF_VISIBLE_ITEMS = 7;
    private static final int MIN_DELTA_FOR_SCROLLING = 1;
    private static final int PADDING_TEXT_BOTTOM = 10;
    private static final int SCROLLING_DURATION = 250;
    private static final int[] SHADOWS_COLORS = {-15658735, 11184810, 11184810};
    private final int DEFAULT_ITEMTEXTCOLOR;
    private final int DEFAULT_VALUEBORDERCOLOR;
    private final int DEFAULT_VALUESOLIDCOLOR;
    private final int DEFAULT_VALUETEXTCOLOR;
    private final int MESSAGE_JUSTIFY;
    private final int MESSAGE_SCROLL;
    /* access modifiers changed from: private */
    public TimePickerAdapter adapter;
    private int additionalItemsHeight;
    private int addtionalItemSpace;
    /* access modifiers changed from: private */
    public Handler animationHandler;
    private GradientDrawable bottomShadow;
    private Drawable centerDrawable;
    private List<TimePickerChangedListener> changingListeners;
    /* access modifiers changed from: private */
    public int currentItem;
    private boolean fitLandScape;
    private GestureDetector gestureDetector;
    private SimpleOnGestureListener gestureListener;
    private Drawable gradientDarkerDrawable;
    private Drawable gradientLighterDrawable;
    private int horizontalPadding;
    private boolean isBorderVisible;
    boolean isCyclic;
    /* access modifiers changed from: private */
    public boolean isScrollingPerformed;
    private int itemHeight;
    private int itemOffset;
    private int itemTextColor;
    private float itemTextSize;
    private StaticLayout itemsLayout;
    private TextPaint itemsPaint;
    private int itemsWidth;
    private String label;
    private StaticLayout labelLayout;
    private int labelOffset;
    private int labelWidth;
    /* access modifiers changed from: private */
    public int lastScrollY;
    /* access modifiers changed from: private */
    public Scroller scroller;
    private List<TimePickerScrollListener> scrollingListeners;
    /* access modifiers changed from: private */
    public int scrollingOffset;
    private GradientDrawable topShadow;
    private int valueBorderColor;
    private Paint valueBorderPaint;
    private StaticLayout valueLayout;
    private TextPaint valuePaint;
    private int valueSolidColor;
    private Paint valueSolidPaint;
    private int valueTextColor;
    private float valueTextSize;
    private int visibleItems;

    public void setLastDay() {
    }

    public TimePickerWidgetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.DEFAULT_VALUETEXTCOLOR = getResources().getColor(R.color.f_c_2);
        this.DEFAULT_ITEMTEXTCOLOR = getResources().getColor(R.color.f_c_2);
        this.DEFAULT_VALUEBORDERCOLOR = getResources().getColor(R.color.default_font_color_cad);
        this.DEFAULT_VALUESOLIDCOLOR = getResources().getColor(R.color.transparent);
        this.adapter = null;
        this.currentItem = 0;
        this.itemsWidth = 0;
        this.labelWidth = 0;
        this.visibleItems = 7;
        this.itemHeight = 0;
        this.isCyclic = false;
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.gestureListener = new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent motionEvent) {
                if (!TimePickerWidgetView.this.isScrollingPerformed) {
                    return false;
                }
                TimePickerWidgetView.this.scroller.forceFinished(true);
                TimePickerWidgetView.this.clearMessages();
                return true;
            }

            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                TimePickerWidgetView.this.startScrolling();
                TimePickerWidgetView.this.doScroll((int) (-f2));
                return true;
            }

            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                int i;
                TimePickerWidgetView.this.lastScrollY = (TimePickerWidgetView.this.currentItem * TimePickerWidgetView.this.getItemHeight()) + TimePickerWidgetView.this.scrollingOffset;
                if (TimePickerWidgetView.this.isCyclic) {
                    i = Integer.MAX_VALUE;
                } else {
                    i = TimePickerWidgetView.this.adapter.getItemsCount() * TimePickerWidgetView.this.getItemHeight();
                }
                TimePickerWidgetView.this.scroller.fling(0, TimePickerWidgetView.this.lastScrollY, 0, ((int) (-f2)) / 4, 0, 0, TimePickerWidgetView.this.isCyclic ? -i : 0, i);
                TimePickerWidgetView.this.setNextMessage(0);
                return true;
            }
        };
        this.MESSAGE_SCROLL = 0;
        this.MESSAGE_JUSTIFY = 1;
        this.animationHandler = new Handler() {
            public void handleMessage(Message message) {
                TimePickerWidgetView.this.scroller.computeScrollOffset();
                int currY = TimePickerWidgetView.this.scroller.getCurrY();
                int access$500 = TimePickerWidgetView.this.lastScrollY - currY;
                TimePickerWidgetView.this.lastScrollY = currY;
                if (access$500 != 0) {
                    TimePickerWidgetView.this.doScroll(access$500);
                }
                if (Math.abs(currY - TimePickerWidgetView.this.scroller.getFinalY()) <= 0) {
                    TimePickerWidgetView.this.scroller.getFinalY();
                    TimePickerWidgetView.this.scroller.forceFinished(true);
                }
                if (!TimePickerWidgetView.this.scroller.isFinished()) {
                    TimePickerWidgetView.this.animationHandler.sendEmptyMessage(message.what);
                } else if (message.what == 0) {
                    TimePickerWidgetView.this.justify();
                } else {
                    TimePickerWidgetView.this.finishScrolling();
                }
            }
        };
        initData(context, attributeSet);
    }

    public TimePickerWidgetView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TimePickerWidgetView(Context context) {
        super(context);
        this.DEFAULT_VALUETEXTCOLOR = getResources().getColor(R.color.f_c_2);
        this.DEFAULT_ITEMTEXTCOLOR = getResources().getColor(R.color.f_c_2);
        this.DEFAULT_VALUEBORDERCOLOR = getResources().getColor(R.color.default_font_color_cad);
        this.DEFAULT_VALUESOLIDCOLOR = getResources().getColor(R.color.transparent);
        this.adapter = null;
        this.currentItem = 0;
        this.itemsWidth = 0;
        this.labelWidth = 0;
        this.visibleItems = 7;
        this.itemHeight = 0;
        this.isCyclic = false;
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.gestureListener = new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent motionEvent) {
                if (!TimePickerWidgetView.this.isScrollingPerformed) {
                    return false;
                }
                TimePickerWidgetView.this.scroller.forceFinished(true);
                TimePickerWidgetView.this.clearMessages();
                return true;
            }

            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                TimePickerWidgetView.this.startScrolling();
                TimePickerWidgetView.this.doScroll((int) (-f2));
                return true;
            }

            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                int i;
                TimePickerWidgetView.this.lastScrollY = (TimePickerWidgetView.this.currentItem * TimePickerWidgetView.this.getItemHeight()) + TimePickerWidgetView.this.scrollingOffset;
                if (TimePickerWidgetView.this.isCyclic) {
                    i = Integer.MAX_VALUE;
                } else {
                    i = TimePickerWidgetView.this.adapter.getItemsCount() * TimePickerWidgetView.this.getItemHeight();
                }
                TimePickerWidgetView.this.scroller.fling(0, TimePickerWidgetView.this.lastScrollY, 0, ((int) (-f2)) / 4, 0, 0, TimePickerWidgetView.this.isCyclic ? -i : 0, i);
                TimePickerWidgetView.this.setNextMessage(0);
                return true;
            }
        };
        this.MESSAGE_SCROLL = 0;
        this.MESSAGE_JUSTIFY = 1;
        this.animationHandler = new Handler() {
            public void handleMessage(Message message) {
                TimePickerWidgetView.this.scroller.computeScrollOffset();
                int currY = TimePickerWidgetView.this.scroller.getCurrY();
                int access$500 = TimePickerWidgetView.this.lastScrollY - currY;
                TimePickerWidgetView.this.lastScrollY = currY;
                if (access$500 != 0) {
                    TimePickerWidgetView.this.doScroll(access$500);
                }
                if (Math.abs(currY - TimePickerWidgetView.this.scroller.getFinalY()) <= 0) {
                    TimePickerWidgetView.this.scroller.getFinalY();
                    TimePickerWidgetView.this.scroller.forceFinished(true);
                }
                if (!TimePickerWidgetView.this.scroller.isFinished()) {
                    TimePickerWidgetView.this.animationHandler.sendEmptyMessage(message.what);
                } else if (message.what == 0) {
                    TimePickerWidgetView.this.justify();
                } else {
                    TimePickerWidgetView.this.finishScrolling();
                }
            }
        };
        initData(context, null);
    }

    private void initData(Context context, AttributeSet attributeSet) {
        this.gestureDetector = new GestureDetector(context, this.gestureListener);
        this.gestureDetector.setIsLongpressEnabled(false);
        this.scroller = new Scroller(context);
        initDefaultResoure(context, attributeSet);
    }

    private void initDefaultResoure(Context context, AttributeSet attributeSet) {
        this.horizontalPadding = getResources().getDimensionPixelSize(R.dimen.timepicker_item_margin);
        this.valueTextSize = getResources().getDimension(R.dimen.timepicker_selected_text_size);
        this.itemTextSize = getResources().getDimension(R.dimen.timepicker_item_text_size);
        if (getResources().getDisplayMetrics().widthPixels <= 480) {
            this.valueTextSize = this.itemTextSize;
        }
        this.itemOffset = (int) (this.itemTextSize / 4.0f);
        this.labelOffset = getResources().getDimensionPixelSize(R.dimen.default_margin_4A);
        this.additionalItemsHeight = (int) this.itemTextSize;
        this.addtionalItemSpace = getResources().getDimensionPixelSize(R.dimen.default_font_size_t30);
        if (attributeSet == null) {
            this.valueTextColor = this.DEFAULT_VALUETEXTCOLOR;
            this.itemTextColor = this.DEFAULT_ITEMTEXTCOLOR;
            this.valueBorderColor = this.DEFAULT_VALUEBORDERCOLOR;
            this.valueSolidColor = this.DEFAULT_VALUESOLIDCOLOR;
            this.isBorderVisible = true;
            return;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TimePicker);
        this.valueTextColor = obtainStyledAttributes.getColor(R.styleable.TimePicker_selectTextColor, this.DEFAULT_VALUETEXTCOLOR);
        this.itemTextColor = obtainStyledAttributes.getColor(R.styleable.TimePicker_itemTextColor, this.DEFAULT_ITEMTEXTCOLOR);
        this.valueBorderColor = obtainStyledAttributes.getColor(R.styleable.TimePicker_borderColor, this.DEFAULT_VALUEBORDERCOLOR);
        this.valueSolidColor = obtainStyledAttributes.getColor(R.styleable.TimePicker_selectAreaBackground, this.DEFAULT_VALUESOLIDCOLOR);
        this.isBorderVisible = obtainStyledAttributes.getBoolean(R.styleable.TimePicker_borderVisibility, true);
        obtainStyledAttributes.recycle();
    }

    public void setFitLandScape(boolean z) {
        this.fitLandScape = z;
    }

    public TimePickerAdapter getAdapter() {
        return this.adapter;
    }

    public void setAdapter(TimePickerAdapter timePickerAdapter) {
        this.adapter = timePickerAdapter;
        invalidateLayouts();
        invalidate();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.scroller.forceFinished(true);
        this.scroller = new Scroller(getContext(), interpolator);
    }

    public int getVisibleItems() {
        return this.visibleItems;
    }

    private void setVisibleItems(int i) {
        this.visibleItems = i;
        invalidate();
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        if (this.label == null || !this.label.equals(str)) {
            this.label = str;
            this.labelLayout = null;
            invalidate();
        }
    }

    public void addChangingListener(TimePickerChangedListener timePickerChangedListener) {
        this.changingListeners.add(timePickerChangedListener);
    }

    public void removeChangingListener(TimePickerChangedListener timePickerChangedListener) {
        this.changingListeners.remove(timePickerChangedListener);
    }

    /* access modifiers changed from: protected */
    public void notifyChangingListeners(int i, int i2) {
        for (TimePickerChangedListener onChanged : this.changingListeners) {
            onChanged.onChanged(this, i, i2);
        }
    }

    public void addScrollingListener(TimePickerScrollListener timePickerScrollListener) {
        this.scrollingListeners.add(timePickerScrollListener);
    }

    public void removeScrollingListener(TimePickerScrollListener timePickerScrollListener) {
        this.scrollingListeners.remove(timePickerScrollListener);
    }

    /* access modifiers changed from: protected */
    public void notifyScrollingListenersAboutStart() {
        for (TimePickerScrollListener onScrollingStarted : this.scrollingListeners) {
            onScrollingStarted.onScrollingStarted(this);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyScrollingListenersAboutEnd() {
        for (TimePickerScrollListener onScrollingFinished : this.scrollingListeners) {
            onScrollingFinished.onScrollingFinished(this);
        }
    }

    public int getCurrentItem() {
        return this.currentItem;
    }

    public void setCurrentItem(int i, boolean z) {
        if (this.adapter != null && this.adapter.getItemsCount() != 0) {
            if (i < 0 || i >= this.adapter.getItemsCount()) {
                if (this.isCyclic) {
                    while (i < 0) {
                        i += this.adapter.getItemsCount();
                    }
                    i %= this.adapter.getItemsCount();
                } else {
                    return;
                }
            }
            if (z) {
                scroll(i - this.currentItem, 250);
                return;
            }
            invalidateLayouts();
            int i2 = this.currentItem;
            this.currentItem = i;
            notifyChangingListeners(i2, this.currentItem);
            invalidate();
        }
    }

    public void setCurrentItem(int i) {
        setCurrentItem(i, false);
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    public void setCyclic(boolean z) {
        this.isCyclic = z;
        invalidate();
        invalidateLayouts();
    }

    private void invalidateLayouts() {
        this.itemsLayout = null;
        this.valueLayout = null;
        this.scrollingOffset = 0;
    }

    private void initResourcesIfNecessary() {
        if (this.itemsPaint == null) {
            this.itemsPaint = new TextPaint(1);
            this.itemsPaint.setTextSize(this.itemTextSize);
        }
        if (this.valuePaint == null) {
            this.valuePaint = new TextPaint(5);
            this.valuePaint.setTextSize(this.valueTextSize);
            this.valuePaint.setShadowLayer(0.1f, 0.0f, 0.1f, -4144960);
        }
        if (this.valueBorderPaint == null) {
            this.valueBorderPaint = new Paint();
            this.valueBorderPaint.setAntiAlias(true);
            this.valueBorderPaint.setStyle(Style.STROKE);
            this.valueBorderPaint.setColor(this.valueBorderColor);
        }
        if (this.valueSolidPaint == null) {
            this.valueSolidPaint = new Paint();
            this.valueSolidPaint.setAntiAlias(true);
            this.valueSolidPaint.setStyle(Style.FILL);
            this.valueSolidPaint.setColor(this.valueSolidColor);
        }
        if (this.centerDrawable == null) {
            this.centerDrawable = getContext().getResources().getDrawable(R.drawable.timepicker_item);
        }
        if (this.gradientDarkerDrawable == null) {
            this.gradientDarkerDrawable = getResources().getDrawable(R.drawable.gradient_coverer_darker);
        }
        if (this.gradientLighterDrawable == null) {
            this.gradientLighterDrawable = getResources().getDrawable(R.drawable.gradient_coverer_lighter);
        }
        if (this.topShadow == null) {
            this.topShadow = new GradientDrawable(Orientation.TOP_BOTTOM, SHADOWS_COLORS);
        }
        if (this.bottomShadow == null) {
            this.bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP, SHADOWS_COLORS);
        }
    }

    private int getDesiredHeight(Layout layout) {
        int i;
        if (layout == null) {
            return 0;
        }
        if (!this.fitLandScape || this.visibleItems > 1) {
            i = ((getItemHeight() * this.visibleItems) - (this.itemOffset * 2)) - this.additionalItemsHeight;
        } else {
            i = getItemHeight() * this.visibleItems;
        }
        return Math.max(i, getSuggestedMinimumHeight());
    }

    public String getTextItem(int i) {
        if (this.adapter == null || this.adapter.getItemsCount() == 0) {
            return null;
        }
        int itemsCount = this.adapter.getItemsCount();
        if ((i < 0 || i >= itemsCount) && !this.isCyclic) {
            return null;
        }
        while (i < 0) {
            i += itemsCount;
        }
        return this.adapter.getItem(i % itemsCount);
    }

    private String buildText(boolean z) {
        StringBuilder sb = new StringBuilder();
        int i = (this.visibleItems / 2) + 1;
        for (int i2 = this.currentItem - i; i2 <= this.currentItem + i; i2++) {
            if (z || i2 != this.currentItem) {
                String textItem = getTextItem(i2);
                if (textItem != null) {
                    sb.append(textItem);
                }
            }
            if (i2 < this.currentItem + i) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private int getMaxTextLength() {
        TimePickerAdapter adapter2 = getAdapter();
        if (adapter2 == null) {
            return 0;
        }
        int maximumLength = adapter2.getMaximumLength();
        if (maximumLength > 0) {
            return maximumLength;
        }
        String str = null;
        for (int max = Math.max(this.currentItem - (this.visibleItems / 2), 0); max < Math.min(this.currentItem + this.visibleItems, adapter2.getItemsCount()); max++) {
            String item = adapter2.getItem(max);
            if (item != null && (str == null || str.length() < item.length())) {
                str = item;
            }
        }
        if (str != null) {
            return str.length();
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public int getItemHeight() {
        if (this.itemHeight != 0) {
            return this.itemHeight;
        }
        if (this.itemsLayout == null || this.itemsLayout.getLineCount() <= 2) {
            return getHeight() / this.visibleItems;
        }
        this.itemHeight = this.itemsLayout.getLineTop(2) - this.itemsLayout.getLineTop(1);
        return this.itemHeight;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00a1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int calculateLayoutWidth(int r6, int r7) {
        /*
            r5 = this;
            r5.initResourcesIfNecessary()
            int r0 = r5.getMaxTextLength()
            r1 = 0
            if (r0 <= 0) goto L_0x001f
            java.lang.String r2 = "00"
            android.text.TextPaint r3 = r5.itemsPaint
            float r2 = android.text.Layout.getDesiredWidth(r2, r3)
            double r2 = (double) r2
            double r2 = java.lang.Math.ceil(r2)
            float r2 = (float) r2
            float r0 = (float) r0
            float r0 = r0 * r2
            int r0 = (int) r0
            r5.itemsWidth = r0
            goto L_0x0021
        L_0x001f:
            r5.itemsWidth = r1
        L_0x0021:
            int r0 = r5.itemsWidth
            int r2 = r5.addtionalItemSpace
            int r0 = r0 + r2
            r5.itemsWidth = r0
            r5.labelWidth = r1
            java.lang.String r0 = r5.label
            if (r0 == 0) goto L_0x0046
            java.lang.String r0 = r5.label
            int r0 = r0.length()
            if (r0 <= 0) goto L_0x0046
            java.lang.String r0 = r5.label
            android.text.TextPaint r2 = r5.valuePaint
            float r0 = android.text.Layout.getDesiredWidth(r0, r2)
            double r2 = (double) r0
            double r2 = java.lang.Math.ceil(r2)
            int r0 = (int) r2
            r5.labelWidth = r0
        L_0x0046:
            r0 = 1073741824(0x40000000, float:2.0)
            r2 = 1
            if (r7 != r0) goto L_0x004d
        L_0x004b:
            r0 = r6
            goto L_0x006e
        L_0x004d:
            int r0 = r5.itemsWidth
            int r3 = r5.labelWidth
            int r0 = r0 + r3
            int r3 = r5.horizontalPadding
            int r3 = r3 * 2
            int r0 = r0 + r3
            int r3 = r5.labelWidth
            if (r3 <= 0) goto L_0x005e
            int r3 = r5.labelOffset
            int r0 = r0 + r3
        L_0x005e:
            int r3 = r5.getSuggestedMinimumWidth()
            int r0 = java.lang.Math.max(r0, r3)
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r7 != r3) goto L_0x006d
            if (r6 >= r0) goto L_0x006d
            goto L_0x004b
        L_0x006d:
            r2 = 0
        L_0x006e:
            if (r2 == 0) goto L_0x009d
            int r7 = r5.labelOffset
            int r6 = r6 - r7
            int r7 = r5.horizontalPadding
            int r7 = r7 * 2
            int r6 = r6 - r7
            if (r6 > 0) goto L_0x007e
            r5.labelWidth = r1
            r5.itemsWidth = r1
        L_0x007e:
            int r7 = r5.labelWidth
            if (r7 <= 0) goto L_0x0098
            int r7 = r5.itemsWidth
            double r1 = (double) r7
            double r3 = (double) r6
            double r1 = r1 * r3
            int r7 = r5.itemsWidth
            int r3 = r5.labelWidth
            int r7 = r7 + r3
            double r3 = (double) r7
            double r1 = r1 / r3
            int r7 = (int) r1
            r5.itemsWidth = r7
            int r7 = r5.itemsWidth
            int r6 = r6 - r7
            r5.labelWidth = r6
            goto L_0x009d
        L_0x0098:
            int r7 = r5.labelOffset
            int r6 = r6 + r7
            r5.itemsWidth = r6
        L_0x009d:
            int r6 = r5.itemsWidth
            if (r6 <= 0) goto L_0x00a8
            int r6 = r5.itemsWidth
            int r7 = r5.labelWidth
            r5.createLayouts(r6, r7)
        L_0x00a8:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.widget.wheel.TimePickerWidgetView.calculateLayoutWidth(int, int):int");
    }

    private void createLayouts(int i, int i2) {
        if (this.itemsLayout == null || this.itemsLayout.getWidth() > i) {
            StaticLayout staticLayout = new StaticLayout(buildText(this.isScrollingPerformed), this.itemsPaint, i, i2 > 0 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_CENTER, 1.0f, (float) this.additionalItemsHeight, false);
            this.itemsLayout = staticLayout;
        } else {
            this.itemsLayout.increaseWidthTo(i);
        }
        String str = null;
        if (!this.isScrollingPerformed && (this.valueLayout == null || this.valueLayout.getWidth() > i)) {
            if (getAdapter() != null) {
                str = getAdapter().getItem(this.currentItem);
            }
            if (str == null) {
                str = "";
            }
            StaticLayout staticLayout2 = new StaticLayout(str, this.valuePaint, i, i2 > 0 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_CENTER, 1.0f, (float) this.additionalItemsHeight, false);
            this.valueLayout = staticLayout2;
        } else if (this.isScrollingPerformed) {
            this.valueLayout = null;
        } else {
            this.valueLayout.increaseWidthTo(i);
        }
        if (i2 > 0) {
            if (this.labelLayout == null || this.labelLayout.getWidth() > i2) {
                StaticLayout staticLayout3 = new StaticLayout(this.label, this.valuePaint, i2, Alignment.ALIGN_NORMAL, 1.0f, (float) this.additionalItemsHeight, false);
                this.labelLayout = staticLayout3;
                return;
            }
            this.labelLayout.increaseWidthTo(i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int calculateLayoutWidth = calculateLayoutWidth(size, mode);
        if (mode2 != 1073741824) {
            int desiredHeight = getDesiredHeight(this.itemsLayout);
            size2 = (mode2 != Integer.MIN_VALUE || this.fitLandScape) ? desiredHeight : Math.min(desiredHeight, size2);
        }
        setMeasuredDimension(calculateLayoutWidth, size2);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.fitLandScape) {
            if (configuration.orientation == 2) {
                this.visibleItems = 1;
                invalidateLayouts();
                invalidate();
                return;
            }
            this.visibleItems = 7;
            invalidateLayouts();
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        if (this.itemsLayout == null) {
            if (this.itemsWidth == 0) {
                calculateLayoutWidth(getWidth(), UCCore.VERIFY_POLICY_QUICK);
            } else {
                createLayouts(this.itemsWidth, this.labelWidth);
            }
        }
        drawValueSolid(canvas);
        if (this.itemsWidth > 0) {
            canvas.save();
            if (!this.fitLandScape || this.visibleItems > 1) {
                i = -this.itemOffset;
            } else {
                i = this.itemOffset * 2;
            }
            canvas.translate((float) this.horizontalPadding, (float) i);
            drawItems(canvas);
            drawValue(canvas);
            canvas.restore();
        }
        drawValueBorder(canvas);
        drawGradientCover(canvas);
    }

    private void drawShadows(Canvas canvas) {
        this.topShadow.setBounds(0, 0, getWidth(), getHeight() / this.visibleItems);
        this.topShadow.draw(canvas);
        this.bottomShadow.setBounds(0, getHeight() - (getHeight() / this.visibleItems), getWidth(), getHeight());
        this.bottomShadow.draw(canvas);
    }

    private void drawValue(Canvas canvas) {
        this.valuePaint.setColor(this.valueTextColor);
        this.valuePaint.drawableState = getDrawableState();
        Rect rect = new Rect();
        this.itemsLayout.getLineBounds(this.visibleItems / 2, rect);
        if (this.labelLayout != null) {
            canvas.save();
            canvas.translate((float) (this.itemsLayout.getWidth() + this.labelOffset), (float) rect.top);
            this.labelLayout.draw(canvas);
            canvas.restore();
        }
        if (this.valueLayout != null) {
            canvas.save();
            canvas.translate(0.0f, (float) (rect.top + this.scrollingOffset));
            this.valueLayout.draw(canvas);
            canvas.restore();
        }
    }

    private void drawItems(Canvas canvas) {
        canvas.save();
        canvas.translate(0.0f, (float) ((-this.itemsLayout.getLineTop(1)) + this.scrollingOffset + 10));
        this.itemsPaint.setColor(this.itemTextColor);
        this.itemsPaint.drawableState = getDrawableState();
        this.itemsLayout.draw(canvas);
        canvas.restore();
    }

    private void drawValueBorder(Canvas canvas) {
        if (this.isBorderVisible) {
            int itemHeight2 = getItemHeight() / 2;
            int height = getHeight() / 2;
            if (this.fitLandScape && this.visibleItems <= 1) {
                itemHeight2 -= this.itemOffset;
            }
            float f = (float) (height - itemHeight2);
            canvas.drawLine(0.0f, f, (float) getWidth(), f, this.valueBorderPaint);
            float f2 = (float) (height + itemHeight2);
            canvas.drawLine(0.0f, f2, (float) getWidth(), f2, this.valueBorderPaint);
        }
    }

    private void drawValueSolid(Canvas canvas) {
        int itemHeight2 = getItemHeight() / 2;
        int height = getHeight() / 2;
        if (this.fitLandScape && this.visibleItems <= 1) {
            itemHeight2 -= this.itemOffset;
        }
        canvas.drawRect(0.0f, (float) (height - itemHeight2), (float) getWidth(), (float) (height + itemHeight2), this.valueSolidPaint);
    }

    private void drawGradientCover(Canvas canvas) {
        int itemHeight2 = getItemHeight() / 2;
        int height = getHeight() / 2;
        this.gradientDarkerDrawable.setBounds(0, 0, getWidth(), height - itemHeight2);
        this.gradientLighterDrawable.setBounds(0, height + itemHeight2, getWidth(), getHeight());
        this.gradientDarkerDrawable.draw(canvas);
        this.gradientLighterDrawable.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (getAdapter() != null && !this.gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1) {
            justify();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void doScroll(int i) {
        this.scrollingOffset += i;
        int itemHeight2 = this.scrollingOffset / getItemHeight();
        int i2 = this.currentItem - itemHeight2;
        if (this.isCyclic && this.adapter.getItemsCount() > 0) {
            while (i2 < 0) {
                i2 += this.adapter.getItemsCount();
            }
            i2 %= this.adapter.getItemsCount();
        } else if (!this.isScrollingPerformed) {
            i2 = Math.min(Math.max(i2, 0), this.adapter.getItemsCount() - 1);
        } else if (i2 < 0) {
            itemHeight2 = this.currentItem;
            i2 = 0;
        } else if (i2 >= this.adapter.getItemsCount()) {
            itemHeight2 = (this.currentItem - this.adapter.getItemsCount()) + 1;
            i2 = this.adapter.getItemsCount() - 1;
        }
        int i3 = this.scrollingOffset;
        if (i2 != this.currentItem) {
            setCurrentItem(i2, false);
        } else {
            invalidate();
        }
        this.scrollingOffset = i3 - (itemHeight2 * getItemHeight());
        if (this.scrollingOffset > getHeight()) {
            this.scrollingOffset = (this.scrollingOffset % getHeight()) + getHeight();
        }
    }

    /* access modifiers changed from: private */
    public void setNextMessage(int i) {
        clearMessages();
        this.animationHandler.sendEmptyMessage(i);
    }

    /* access modifiers changed from: private */
    public void clearMessages() {
        this.animationHandler.removeMessages(0);
        this.animationHandler.removeMessages(1);
    }

    /* access modifiers changed from: private */
    public void justify() {
        if (this.adapter != null) {
            boolean z = false;
            this.lastScrollY = 0;
            int i = this.scrollingOffset;
            int itemHeight2 = getItemHeight();
            if (i <= 0 ? this.currentItem > 0 : this.currentItem < this.adapter.getItemsCount()) {
                z = true;
            }
            if ((this.isCyclic || z) && Math.abs((float) i) > ((float) itemHeight2) / 2.0f) {
                i = i < 0 ? i + itemHeight2 + 1 : i - (itemHeight2 + 1);
            }
            int i2 = i;
            if (Math.abs(i2) > 1) {
                this.scroller.startScroll(0, 0, 0, i2, 250);
                setNextMessage(1);
                return;
            }
            finishScrolling();
        }
    }

    /* access modifiers changed from: private */
    public void startScrolling() {
        if (!this.isScrollingPerformed) {
            this.isScrollingPerformed = true;
            notifyScrollingListenersAboutStart();
        }
    }

    /* access modifiers changed from: 0000 */
    public void finishScrolling() {
        if (this.isScrollingPerformed) {
            notifyScrollingListenersAboutEnd();
            this.isScrollingPerformed = false;
        }
        invalidateLayouts();
        invalidate();
    }

    public void scroll(int i, int i2) {
        this.scroller.forceFinished(true);
        this.lastScrollY = this.scrollingOffset;
        this.scroller.startScroll(0, this.lastScrollY, 0, (i * getItemHeight()) - this.lastScrollY, i2);
        setNextMessage(0);
        startScrolling();
    }
}
