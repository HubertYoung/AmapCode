package com.autonavi.minimap.ajx3.assistant;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class AjxAssistantMenu extends ViewGroup {
    public static final int ANIM_DURATION = 100;
    public static final int COLOR_BLUE = Color.parseColor("#0091ff");
    public static final int COLOR_GRAY = Color.parseColor("#434343");
    public static final int COLOR_PINK = Color.parseColor("#FF4081");
    public static final int DEFAULT_CHILD_SIZE = 42;
    public static final int DEFAULT_COLLAPSED_RADIUS = 24;
    public static final int DEFAULT_EXPAND_RADIUS = 75;
    public static final int FONT_SIZE_DP = 15;
    public static final int STATE_COLLAPSE = 1;
    public static final int STATE_EXPAND = 2;
    private static int defaultX = 0;
    private static int defaultY = 0;
    /* access modifiers changed from: private */
    public static int state = 1;
    private RectF area;
    private List<Item> childItem;
    /* access modifiers changed from: private */
    public int childSize;
    /* access modifiers changed from: private */
    public int collapsedRadius;
    private float[] coords;
    /* access modifiers changed from: private */
    public float downX;
    /* access modifiers changed from: private */
    public float downY;
    private float expandProgress;
    private Paint expandedBgPaint;
    /* access modifiers changed from: private */
    public int expandedRadius;
    /* access modifiers changed from: private */
    public int initWindowX;
    /* access modifiers changed from: private */
    public int initWindowY;
    boolean isMoveEvent;
    private float itemRadius;
    /* access modifiers changed from: private */
    public long lastTouchTime;
    /* access modifiers changed from: private */
    public OnLongClickListener longClickListener;
    /* access modifiers changed from: private */
    public WindowManager mWindowManager;
    private PathMeasure measure;
    private Paint menuBgPaint;
    /* access modifiers changed from: private */
    public Item menuItem;
    /* access modifiers changed from: private */
    public OnMenuChangeListener menuListener;
    /* access modifiers changed from: private */
    public LayoutParams menuParams;
    private String menuText;
    private Paint menuTextPaint;
    /* access modifiers changed from: private */
    public int offsetX;
    /* access modifiers changed from: private */
    public int offsetY;
    private Path path;
    /* access modifiers changed from: private */
    public int screenHeight;
    /* access modifiers changed from: private */
    public int screenWidth;
    private Rect textRect;
    /* access modifiers changed from: private */
    public boolean touching;

    class Item implements OnTouchListener {
        LayoutParams params = new LayoutParams();
        int position;
        View view;
        int x;
        int y;

        Item(int i) {
            this.position = i;
            this.params.type = VERSION.SDK_INT >= 26 ? 2038 : 2002;
            this.params.format = 1;
            this.params.gravity = 8388659;
            this.params.flags = 40;
            this.params.width = AjxAssistantMenu.this.childSize;
            this.params.height = AjxAssistantMenu.this.childSize;
            this.view = new View(AjxAssistantMenu.this.getContext());
            this.view.setOnTouchListener(this);
        }

        /* access modifiers changed from: 0000 */
        public void setClickable(boolean z) {
            if (z) {
                this.params.flags = 40;
            } else {
                this.params.flags = 536;
            }
            AjxAssistantMenu.this.mWindowManager.updateViewLayout(this.view, this.params);
        }

        /* access modifiers changed from: 0000 */
        public void layout(int i, int i2) {
            if (!AjxAssistantMenu.this.touching) {
                this.x = i;
                this.y = i2;
                this.params.x = i + AjxAssistantMenu.this.menuParams.x;
                this.params.y = i2 + AjxAssistantMenu.this.menuParams.y;
                AjxAssistantMenu.this.mWindowManager.updateViewLayout(this.view, this.params);
            }
        }

        public boolean onTouch(View view2, MotionEvent motionEvent) {
            if (AjxAssistantMenu.this.menuItem == null || AjxAssistantMenu.this.menuItem.params == null) {
                return false;
            }
            switch (motionEvent.getActionMasked()) {
                case 0:
                    AjxAssistantMenu.this.touching = true;
                    AjxAssistantMenu.this.isMoveEvent = false;
                    AjxAssistantMenu.this.downX = motionEvent.getRawX();
                    AjxAssistantMenu.this.downY = motionEvent.getRawY();
                    AjxAssistantMenu.this.initWindowX = this.params.x;
                    AjxAssistantMenu.this.initWindowY = this.params.y;
                    AjxAssistantMenu.this.offsetX = AjxAssistantMenu.this.menuItem.params.x - AjxAssistantMenu.this.initWindowX;
                    AjxAssistantMenu.this.offsetY = AjxAssistantMenu.this.menuItem.params.y - AjxAssistantMenu.this.initWindowY;
                    if (this.position >= 0 && this.position < AjxAssistantMenu.this.getChildCount()) {
                        AjxAssistantMenu.this.getChildAt(this.position).setPressed(true);
                    }
                    AjxAssistantMenu.this.lastTouchTime = System.currentTimeMillis();
                    return true;
                case 1:
                    if (this.position >= 0 && this.position < AjxAssistantMenu.this.getChildCount()) {
                        AjxAssistantMenu.this.getChildAt(this.position).setPressed(false);
                    }
                    AjxAssistantMenu.this.touching = false;
                    if (AjxAssistantMenu.this.isMoveEvent) {
                        AjxAssistantMenu.this.requestLayout();
                        break;
                    } else {
                        AjxAssistantMenu.this.playSoundEffect(0);
                        boolean z = AjxAssistantMenu.this.lastTouchTime > 0 && System.currentTimeMillis() - AjxAssistantMenu.this.lastTouchTime > 500;
                        AjxAssistantMenu.this.lastTouchTime = 0;
                        if (z && AjxAssistantMenu.this.longClickListener != null) {
                            AjxAssistantMenu.this.longClickListener.onLongClick(AjxAssistantMenu.this);
                        }
                        if (this.position != -1) {
                            if (AjxAssistantMenu.this.menuListener != null) {
                                AjxAssistantMenu.this.menuListener.onMenuItemClick(AjxAssistantMenu.this, this.position);
                                break;
                            }
                        } else if (AjxAssistantMenu.state != 1) {
                            AjxAssistantMenu.this.collapse();
                            break;
                        } else {
                            AjxAssistantMenu.this.expand();
                            break;
                        }
                    }
                    break;
                case 2:
                    float access$900 = (((float) AjxAssistantMenu.this.initWindowX) + motionEvent.getRawX()) - AjxAssistantMenu.this.downX;
                    float access$1000 = (((float) AjxAssistantMenu.this.initWindowY) + motionEvent.getRawY()) - AjxAssistantMenu.this.downY;
                    if (((float) AjxAssistantMenu.this.offsetX) + access$900 < 0.0f) {
                        access$900 = (float) (-AjxAssistantMenu.this.offsetX);
                    }
                    if (((float) AjxAssistantMenu.this.offsetX) + access$900 > ((float) (AjxAssistantMenu.this.screenWidth - (AjxAssistantMenu.this.collapsedRadius * 2)))) {
                        access$900 = (float) ((AjxAssistantMenu.this.screenWidth - (AjxAssistantMenu.this.collapsedRadius * 2)) - AjxAssistantMenu.this.offsetX);
                    }
                    if (((float) AjxAssistantMenu.this.offsetY) + access$1000 < 0.0f) {
                        access$1000 = (float) (-AjxAssistantMenu.this.offsetY);
                    }
                    if (((float) AjxAssistantMenu.this.offsetY) + access$1000 > ((float) (AjxAssistantMenu.this.screenHeight - (AjxAssistantMenu.this.collapsedRadius * 2)))) {
                        access$1000 = (float) ((AjxAssistantMenu.this.screenHeight - (AjxAssistantMenu.this.collapsedRadius * 2)) - AjxAssistantMenu.this.offsetY);
                    }
                    if (!AjxAssistantMenu.this.isMoveEvent) {
                        if (Math.abs(motionEvent.getRawX() - AjxAssistantMenu.this.downX) > 40.0f || Math.abs(motionEvent.getRawY() - AjxAssistantMenu.this.downY) > 40.0f) {
                            AjxAssistantMenu.this.isMoveEvent = true;
                            break;
                        }
                    } else {
                        this.params.x = (int) access$900;
                        this.params.y = (int) access$1000;
                        AjxAssistantMenu.this.mWindowManager.updateViewLayout(this.view, this.params);
                        AjxAssistantMenu.this.menuParams.x = this.params.x - this.x;
                        AjxAssistantMenu.this.menuParams.y = this.params.y - this.y;
                        AjxAssistantMenu.this.mWindowManager.updateViewLayout(AjxAssistantMenu.this, AjxAssistantMenu.this.menuParams);
                        break;
                    }
            }
            return false;
        }
    }

    public interface OnMenuChangeListener {
        void onMenuCollapse(AjxAssistantMenu ajxAssistantMenu);

        void onMenuExpand(AjxAssistantMenu ajxAssistantMenu);

        void onMenuItemClick(AjxAssistantMenu ajxAssistantMenu, int i);
    }

    @TargetApi(21)
    class OvalOutline extends ViewOutlineProvider {
        private Rect area = new Rect();

        public OvalOutline() {
        }

        public void getOutline(View view, Outline outline) {
            this.area.set((AjxAssistantMenu.this.expandedRadius - AjxAssistantMenu.this.collapsedRadius) - 2, (AjxAssistantMenu.this.expandedRadius - AjxAssistantMenu.this.collapsedRadius) - 2, AjxAssistantMenu.this.expandedRadius + AjxAssistantMenu.this.collapsedRadius + 2, AjxAssistantMenu.this.expandedRadius + AjxAssistantMenu.this.collapsedRadius + 2);
            outline.setRoundRect(this.area, 360.0f);
        }
    }

    public AjxAssistantMenu(Context context) {
        this(context, null);
    }

    public AjxAssistantMenu(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AjxAssistantMenu(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.expandProgress = 0.0f;
        this.isMoveEvent = false;
        this.menuText = "AJX";
        this.coords = new float[2];
        this.area = new RectF();
        this.path = new Path();
        this.measure = new PathMeasure();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.collapsedRadius = (int) TypedValue.applyDimension(1, 24.0f, displayMetrics);
        this.expandedRadius = (int) TypedValue.applyDimension(1, 75.0f, displayMetrics);
        this.itemRadius = (float) ((this.expandedRadius + this.collapsedRadius) / 2);
        this.childSize = (int) TypedValue.applyDimension(1, 42.0f, displayMetrics);
        float applyDimension = TypedValue.applyDimension(1, 15.0f, displayMetrics);
        this.expandedBgPaint = new Paint(1);
        this.expandedBgPaint.setColor(COLOR_GRAY);
        this.expandedBgPaint.setStyle(Style.FILL);
        this.menuBgPaint = new Paint(1);
        this.menuBgPaint.setColor(COLOR_BLUE);
        this.menuBgPaint.setStyle(Style.FILL);
        this.menuTextPaint = new Paint(1);
        this.menuTextPaint.setColor(-1);
        this.menuTextPaint.setStyle(Style.FILL);
        this.menuTextPaint.setTextSize(applyDimension);
        this.textRect = new Rect();
        this.menuTextPaint.getTextBounds(this.menuText, 0, this.menuText.length(), this.textRect);
        if (VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
            setOutlineProvider(new OvalOutline());
        }
        if (state == 2) {
            setExpandProgress(1.0f);
        }
        setWillNotDraw(false);
        setSoundEffectsEnabled(true);
        this.mWindowManager = (WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY);
        updateSize();
        this.menuParams = new LayoutParams();
        this.menuParams.type = VERSION.SDK_INT >= 26 ? 2038 : 2002;
        this.menuParams.format = 1;
        this.menuParams.gravity = 8388659;
        this.menuParams.flags = 536;
        this.menuParams.width = this.expandedRadius * 2;
        this.menuParams.height = this.expandedRadius * 2;
        this.menuParams.x = defaultX;
        this.menuParams.y = defaultY;
        this.mWindowManager.addView(this, this.menuParams);
        this.childItem = new ArrayList();
        this.menuItem = new Item(-1);
        this.menuItem.params.width = this.collapsedRadius * 2;
        this.menuItem.params.height = this.collapsedRadius * 2;
        this.mWindowManager.addView(this.menuItem.view, this.menuItem.params);
    }

    private void updateSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        int identifier = getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
        if (identifier > 0) {
            this.screenHeight -= getResources().getDimensionPixelSize(identifier);
        }
        if (defaultX == 0 && defaultY == 0) {
            defaultX = (this.screenWidth - (this.expandedRadius * 2)) - 50;
            defaultY = (int) (((double) this.screenHeight) * 0.3d);
        }
    }

    public void addItem(String[] strArr) {
        for (String addItem : strArr) {
            addItem(addItem);
        }
    }

    public void addItem(String str) {
        TextView textView = new TextView(getContext());
        textView.setBackgroundResource(R.drawable.ajx_assistant_menu_item);
        textView.setGravity(17);
        textView.setTextColor(-1);
        textView.setTextSize(2, 12.0f);
        boolean z = false;
        textView.setSingleLine(false);
        textView.setText(str);
        if (state == 1) {
            textView.setVisibility(8);
        }
        addView(textView);
        Item item = new Item(this.childItem.size());
        this.mWindowManager.addView(item.view, item.params);
        if (state == 2) {
            z = true;
        }
        item.setClickable(z);
        this.childItem.add(item);
    }

    public void setMenuListener(OnMenuChangeListener onMenuChangeListener) {
        this.menuListener = onMenuChangeListener;
    }

    public void setLongClickListener(OnLongClickListener onLongClickListener) {
        this.longClickListener = onLongClickListener;
    }

    public void setMenuText(String str, int i) {
        this.menuBgPaint.setColor(i);
        this.menuText = str;
        this.menuTextPaint.getTextBounds(this.menuText, 0, this.menuText.length(), this.textRect);
        invalidate();
    }

    public void destroy() {
        defaultX = this.menuParams.x;
        defaultY = this.menuParams.y;
        try {
            if (getParent() != null) {
                this.mWindowManager.removeView(this);
            }
            if (this.menuItem.view.getParent() != null) {
                this.mWindowManager.removeView(this.menuItem.view);
            }
            this.menuItem = null;
            for (Item next : this.childItem) {
                if (next.view.getParent() != null) {
                    this.mWindowManager.removeView(next.view);
                }
            }
            this.childItem.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        removeAllViews();
    }

    public void collapse() {
        if (state != 1) {
            state = 1;
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setVisibility(8);
                this.childItem.get(i).setClickable(false);
            }
            setExpandProgress(0.0f);
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = getChildAt(childCount);
                childAt.setAlpha(0.0f);
                childAt.setScaleX(0.0f);
                childAt.setScaleY(0.0f);
            }
            if (this.menuListener != null) {
                this.menuListener.onMenuCollapse(this);
            }
        }
    }

    public void expand() {
        if (state != 2) {
            state = 2;
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setVisibility(0);
                this.childItem.get(i).setClickable(true);
            }
            setExpandProgress(1.0f);
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                getChildAt(i2).animate().setDuration(100).alphaBy(0.0f).scaleXBy(0.5f).scaleX(1.0f).scaleYBy(0.5f).scaleY(1.0f).alpha(1.0f).start();
            }
            if (this.menuListener != null) {
                this.menuListener.onMenuExpand(this);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setExpandProgress(float f) {
        this.expandProgress = f;
        this.expandedBgPaint.setAlpha(Math.min(128, (int) (f * 128.0f)));
        if (VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateSize();
        this.menuParams.x = defaultX;
        this.menuParams.y = defaultY;
        this.mWindowManager.updateViewLayout(this, this.menuParams);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.LayoutParams(this.childSize, this.childSize);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(this.expandedRadius * 2, this.expandedRadius * 2);
        measureChildren(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        if (childCount > 0) {
            this.area.set(((float) this.expandedRadius) - this.itemRadius, ((float) this.expandedRadius) - this.itemRadius, ((float) this.expandedRadius) + this.itemRadius, ((float) this.expandedRadius) + this.itemRadius);
            this.path.reset();
            this.path.addArc(this.area, 0.0f, 360.0f);
            this.measure.setPath(this.path, false);
            float length = this.measure.getLength() / ((float) childCount);
            for (int i5 = 0; i5 < childCount; i5++) {
                this.measure.getPosTan((((float) i5) * length) + (0.5f * length), this.coords, null);
                int i6 = (((int) this.coords[0]) - (this.childSize / 2)) + i;
                int i7 = (((int) this.coords[1]) - (this.childSize / 2)) + i2;
                getChildAt(i5).layout(i6, i7, this.childSize + i6, this.childSize + i7);
                this.childItem.get(i5).layout(i6, i7);
            }
        }
        if (this.menuItem != null) {
            this.menuItem.layout((this.menuParams.width - this.menuItem.params.width) / 2, (this.menuParams.height - this.menuItem.params.height) / 2);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.expandProgress > 0.0f) {
            canvas.drawCircle((float) this.expandedRadius, (float) this.expandedRadius, ((float) this.collapsedRadius) + (((float) (this.expandedRadius - this.collapsedRadius)) * Math.min(this.expandProgress, 1.0f)), this.expandedBgPaint);
        }
        canvas.drawCircle((float) this.expandedRadius, (float) this.expandedRadius, (float) this.collapsedRadius, this.menuBgPaint);
        canvas.drawText(this.menuText, (float) ((this.expandedRadius - 3) - (this.textRect.width() / 2)), (float) (this.expandedRadius + (this.textRect.height() / 2)), this.menuTextPaint);
    }
}
