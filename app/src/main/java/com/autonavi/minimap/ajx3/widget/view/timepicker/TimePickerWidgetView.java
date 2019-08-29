package com.autonavi.minimap.ajx3.widget.view.timepicker;

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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.R;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TimePickerWidgetView extends View {
    private static final int DEF_VISIBLE_ITEMS = 7;
    private static final int MIN_DELTA_FOR_SCROLLING = 1;
    private static final int SCROLLING_DURATION = 150;
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
    private int labelOffset;
    private int labelWidth;
    /* access modifiers changed from: private */
    public int lastScrollY;
    private List<String> mDrawTextKeyList;
    private List<String> mDrawTextList;
    private boolean mNeedHandleClick;
    private int mNormalItemHeight;
    private float mNormalTextHeight;
    private TouchListener mTouchListener;
    private float mValueTextHeight;
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

    public interface TouchListener {
        void onTouched();
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
        this.mNeedHandleClick = false;
        this.mDrawTextList = new ArrayList();
        this.mDrawTextKeyList = new ArrayList();
        this.mNormalItemHeight = -1;
        this.mNormalTextHeight = 0.0f;
        this.mValueTextHeight = 0.0f;
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
                TimePickerWidgetView.this.lastScrollY = (TimePickerWidgetView.this.currentItem * TimePickerWidgetView.this.getItemHeight()) + TimePickerWidgetView.this.scrollingOffset;
                int itemsCount = TimePickerWidgetView.this.isCyclic ? Integer.MAX_VALUE : TimePickerWidgetView.this.adapter.getItemsCount() * TimePickerWidgetView.this.getItemHeight();
                int i = TimePickerWidgetView.this.isCyclic ? -itemsCount : 0;
                if (TimePickerWidgetView.this.isCyclic) {
                    TimePickerWidgetView.this.scroller.fling(0, TimePickerWidgetView.this.lastScrollY, 0, (int) (f2 * -1.0f), 0, 0, i, itemsCount);
                } else {
                    TimePickerWidgetView.this.scroller.setFriction(0.09f);
                    TimePickerWidgetView.this.scroller.fling(0, TimePickerWidgetView.this.lastScrollY, 0, (int) (((double) f2) * -0.5d), 0, 0, i, itemsCount);
                }
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
                if (Math.abs(currY - TimePickerWidgetView.this.scroller.getFinalY()) < (message.what == 0 ? 10 : 1)) {
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

    public TimePickerWidgetView(Context context, PickerData pickerData) {
        this(context);
        initDataFromParent(pickerData);
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
        this.mNeedHandleClick = false;
        this.mDrawTextList = new ArrayList();
        this.mDrawTextKeyList = new ArrayList();
        this.mNormalItemHeight = -1;
        this.mNormalTextHeight = 0.0f;
        this.mValueTextHeight = 0.0f;
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
                TimePickerWidgetView.this.lastScrollY = (TimePickerWidgetView.this.currentItem * TimePickerWidgetView.this.getItemHeight()) + TimePickerWidgetView.this.scrollingOffset;
                int itemsCount = TimePickerWidgetView.this.isCyclic ? Integer.MAX_VALUE : TimePickerWidgetView.this.adapter.getItemsCount() * TimePickerWidgetView.this.getItemHeight();
                int i = TimePickerWidgetView.this.isCyclic ? -itemsCount : 0;
                if (TimePickerWidgetView.this.isCyclic) {
                    TimePickerWidgetView.this.scroller.fling(0, TimePickerWidgetView.this.lastScrollY, 0, (int) (f2 * -1.0f), 0, 0, i, itemsCount);
                } else {
                    TimePickerWidgetView.this.scroller.setFriction(0.09f);
                    TimePickerWidgetView.this.scroller.fling(0, TimePickerWidgetView.this.lastScrollY, 0, (int) (((double) f2) * -0.5d), 0, 0, i, itemsCount);
                }
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
                if (Math.abs(currY - TimePickerWidgetView.this.scroller.getFinalY()) < (message.what == 0 ? 10 : 1)) {
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

    private void initDataFromParent(PickerData pickerData) {
        if (pickerData != null) {
            this.valueTextSize = pickerData.mFountSize;
            this.itemTextSize = pickerData.itemTextSize;
            this.valueTextColor = pickerData.valueTextColor;
            this.itemTextColor = pickerData.itemTextColor;
            this.valueBorderColor = pickerData.valueBorderColor;
            this.valueSolidColor = pickerData.valueSolidColor;
            this.isCyclic = pickerData.isCyclic;
        }
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
        setItemHeight(this.valueTextSize);
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
            if (Math.abs(i - this.currentItem) < 2) {
                z = false;
                notifyScrollingListenersAboutEnd();
            }
            if (z) {
                scroll(i - this.currentItem, 150);
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
            Rect rect = new Rect();
            this.itemsPaint.getTextBounds("高德fg09", 0, "高德fg09".length(), rect);
            this.mNormalTextHeight = (float) rect.height();
        }
        if (this.valuePaint == null) {
            this.valuePaint = new TextPaint(5);
            this.valuePaint.setTextSize(this.valueTextSize);
            this.valuePaint.setShadowLayer(0.1f, 0.0f, 0.1f, -4144960);
            Rect rect2 = new Rect();
            this.valuePaint.getTextBounds("高德09", 0, "高德09".length(), rect2);
            this.mValueTextHeight = (float) rect2.height();
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
        this.mDrawTextList.clear();
        this.mDrawTextKeyList.clear();
        for (int i2 = this.currentItem - i; i2 <= this.currentItem + i; i2++) {
            if (z || i2 != this.currentItem) {
                String textItem = getTextItem(i2);
                if (textItem != null) {
                    sb.append(textItem);
                }
            }
            String textItem2 = getTextItem(i2);
            if (TextUtils.isEmpty(textItem2)) {
                this.mDrawTextList.add("\n");
                this.mDrawTextKeyList.add("\n".concat(String.valueOf(i2)));
            } else {
                this.mDrawTextList.add(textItem2);
                List<String> list = this.mDrawTextKeyList;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(textItem2);
                sb2.append(i2);
                list.add(sb2.toString());
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
        if (this.mNormalItemHeight > 0) {
            this.itemHeight = this.mNormalItemHeight;
            return this.itemHeight;
        } else if (this.itemHeight != 0) {
            return this.itemHeight;
        } else {
            if (this.itemsLayout == null || this.itemsLayout.getLineCount() <= 2) {
                return getHeight() / this.visibleItems;
            }
            this.itemHeight = this.itemsLayout.getLineTop(2) - this.itemsLayout.getLineTop(1);
            return this.itemHeight;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0085  */
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
            r0 = 1073741824(0x40000000, float:2.0)
            r2 = 1
            if (r7 != r0) goto L_0x0031
        L_0x002f:
            r0 = r6
            goto L_0x0052
        L_0x0031:
            int r0 = r5.itemsWidth
            int r3 = r5.labelWidth
            int r0 = r0 + r3
            int r3 = r5.horizontalPadding
            int r3 = r3 * 2
            int r0 = r0 + r3
            int r3 = r5.labelWidth
            if (r3 <= 0) goto L_0x0042
            int r3 = r5.labelOffset
            int r0 = r0 + r3
        L_0x0042:
            int r3 = r5.getSuggestedMinimumWidth()
            int r0 = java.lang.Math.max(r0, r3)
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r7 != r3) goto L_0x0051
            if (r6 >= r0) goto L_0x0051
            goto L_0x002f
        L_0x0051:
            r2 = 0
        L_0x0052:
            if (r2 == 0) goto L_0x0081
            int r7 = r5.labelOffset
            int r6 = r6 - r7
            int r7 = r5.horizontalPadding
            int r7 = r7 * 2
            int r6 = r6 - r7
            if (r6 > 0) goto L_0x0062
            r5.labelWidth = r1
            r5.itemsWidth = r1
        L_0x0062:
            int r7 = r5.labelWidth
            if (r7 <= 0) goto L_0x007c
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
            goto L_0x0081
        L_0x007c:
            int r7 = r5.labelOffset
            int r6 = r6 + r7
            r5.itemsWidth = r6
        L_0x0081:
            int r6 = r5.itemsWidth
            if (r6 <= 0) goto L_0x008c
            int r6 = r5.itemsWidth
            int r7 = r5.labelWidth
            r5.createLayouts(r6, r7)
        L_0x008c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerWidgetView.calculateLayoutWidth(int, int):int");
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
            int i = 0;
            if (this.fitLandScape && this.visibleItems <= 1) {
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
        if (this.valueLayout != null && !this.isScrollingPerformed) {
            String item = getAdapter() != null ? getAdapter().getItem(this.currentItem) : null;
            if (!TextUtils.isEmpty(item)) {
                int height = getHeight() / 2;
                float measureText = this.valuePaint.measureText(item);
                if (measureText > ((float) this.itemsWidth)) {
                    Paint paint = new Paint(this.valuePaint);
                    float textSize = this.valuePaint.getTextSize();
                    while (true) {
                        textSize -= 1.0f;
                        if (textSize > 0.0f) {
                            paint.setTextSize(textSize);
                            float measureText2 = paint.measureText(item);
                            if (measureText2 < ((float) this.itemsWidth)) {
                                Rect rect = new Rect();
                                paint.getTextBounds("高德fg09", 0, "高德fg09".length(), rect);
                                canvas.drawText(item, (((float) this.itemsWidth) - measureText2) / 2.0f, ((float) height) + (((float) rect.height()) / 2.0f), paint);
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    canvas.drawText(item, (((float) this.itemsWidth) - measureText) / 2.0f, ((float) height) + (this.mValueTextHeight / 2.0f), this.valuePaint);
                }
            }
        }
    }

    public void setItemHeight(float f) {
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(f);
        Rect rect = new Rect();
        textPaint.getTextBounds("高德fg09", 0, "高德fg09".length(), rect);
        this.mNormalItemHeight = (int) (((double) rect.height()) * 1.5d);
    }

    private void drawItems(Canvas canvas) {
        canvas.save();
        this.itemsPaint.setColor(this.itemTextColor);
        this.itemsPaint.drawableState = getDrawableState();
        float f = (((float) this.mNormalItemHeight) - this.mNormalTextHeight) / 2.0f;
        List<String> list = this.mDrawTextKeyList;
        StringBuilder sb = new StringBuilder();
        sb.append(getTextItem(this.currentItem));
        sb.append(this.currentItem);
        int indexOf = list.indexOf(sb.toString());
        float height = (float) (getHeight() / 2);
        float f2 = ((((float) (this.mNormalItemHeight * indexOf)) + f) - height) - ((float) this.scrollingOffset);
        float f3 = ((((float) (this.mNormalItemHeight * indexOf)) + f) - f2) + (this.mNormalTextHeight / 2.0f);
        if (!this.isCyclic) {
            if (this.currentItem == 0) {
                if (f3 > height) {
                    int i = this.scrollingOffset;
                    f2 = ((((float) (this.mNormalItemHeight * indexOf)) + f) - height) - ((float) ((i * 500) / ((i + 800) + (i * 2))));
                }
            } else if (this.currentItem == this.adapter.getItemsCount() - 1 && f3 < height) {
                int i2 = this.scrollingOffset * -1;
                f2 = ((((float) (this.mNormalItemHeight * indexOf)) + f) - height) - ((float) (((i2 * 500) / ((i2 + 800) + (i2 * 2))) * -1));
            }
        }
        int i3 = 0;
        while (i3 < this.mDrawTextList.size()) {
            String str = (this.isScrollingPerformed || i3 != indexOf) ? this.mDrawTextList.get(i3) : Token.SEPARATOR;
            float measureText = this.itemsPaint.measureText(str) + 2.0f;
            if (measureText > ((float) this.itemsWidth)) {
                Paint paint = new Paint(this.itemsPaint);
                float textSize = this.itemsPaint.getTextSize();
                while (true) {
                    textSize -= 1.0f;
                    if (textSize <= 0.0f) {
                        break;
                    }
                    paint.setTextSize(textSize);
                    float measureText2 = paint.measureText(str) + 1.0f;
                    if (measureText2 < ((float) this.itemsWidth)) {
                        Rect rect = new Rect();
                        paint.getTextBounds("高德fg09", 0, "高德fg09".length(), rect);
                        canvas.drawText(str, (((float) this.itemsWidth) - measureText2) / 2.0f, ((((float) (this.mNormalItemHeight * i3)) + f) - f2) + (((float) rect.height()) / 2.0f), paint);
                        break;
                    }
                }
            } else {
                canvas.drawText(str, (((float) this.itemsWidth) - measureText) / 2.0f, ((((float) (this.mNormalItemHeight * i3)) + f) - f2) + (this.mNormalTextHeight / 2.0f), this.itemsPaint);
            }
            i3++;
        }
        canvas.restore();
    }

    private void drawValueBorder(Canvas canvas) {
        if (this.isBorderVisible) {
            int i = this.mNormalItemHeight / 2;
            int height = getHeight() / 2;
            if (this.fitLandScape && this.visibleItems <= 1) {
                i -= this.itemOffset;
            }
            float f = (float) (height - i);
            canvas.drawLine(0.0f, f, (float) getWidth(), f, this.valueBorderPaint);
            float f2 = (float) (height + i);
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
        if (this.mTouchListener != null) {
            this.mTouchListener.onTouched();
        }
        if (getAdapter() == null) {
            return true;
        }
        if (motionEvent.getAction() == 0) {
            this.mNeedHandleClick = true;
        }
        boolean onTouchEvent = this.gestureDetector.onTouchEvent(motionEvent);
        if (onTouchEvent) {
            this.mNeedHandleClick = false;
        }
        if (!onTouchEvent && motionEvent.getAction() == 1) {
            justify();
        }
        if (this.mNeedHandleClick && motionEvent.getAction() == 1) {
            handleClick(motionEvent.getY());
        }
        return true;
    }

    private void handleClick(float f) {
        if (!this.isScrollingPerformed && this.adapter != null && this.adapter.getItemsCount() > 0) {
            float height = f - ((float) (getHeight() / 2));
            if (Math.abs(height) >= ((float) (this.mNormalItemHeight / 2))) {
                int abs = ((int) ((Math.abs(height) - ((float) (this.mNormalItemHeight / 2))) / ((float) this.mNormalItemHeight))) + 1;
                int i = this.currentItem;
                if (height < 0.0f) {
                    abs *= -1;
                }
                int i2 = i + abs;
                if (this.isCyclic) {
                    int itemsCount = this.adapter.getItemsCount();
                    if (i2 < 0) {
                        while (i2 < 0) {
                            i2 += this.adapter.getItemsCount();
                        }
                    } else if (i2 >= itemsCount) {
                        while (i2 > itemsCount) {
                            i2 -= this.adapter.getItemsCount();
                        }
                    }
                    setCurrentItem(i2, true);
                } else if (i2 >= 0 && i2 < this.adapter.getItemsCount()) {
                    setCurrentItem(i2, true);
                }
            }
        }
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
                this.scroller.startScroll(0, 0, 0, i2, 150);
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

    public boolean isScrollingPerformed() {
        return this.isScrollingPerformed;
    }

    public void setTouchListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
    }
}
