package com.alipay.mobile.antui.segement;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.model.ItemCategory;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AUSegment extends AURelativeLayout implements AntUI {
    public static final String TAG = "AUSegment";
    private AUIconView addIcon;
    private boolean allowDuplicateClick;
    private View bottomLine;
    private int buttomLineColor;
    /* access modifiers changed from: private */
    public String currentCategoryTag;
    /* access modifiers changed from: private */
    public MenuItemLayout currentClickedOnMenu;
    private int currentIndex = 0;
    private int currentPos = 0;
    private float currentScroolBarLeft;
    /* access modifiers changed from: private */
    public HorizontalScrollView horizontalScrollView;
    private boolean isAdd;
    private boolean isScroll;
    private GradientDrawable lineDrawable;
    private List<ItemCategory> mData = new ArrayList();
    private int mSpaced;
    private final Matrix matrix = new Matrix();
    private ImageView menuBar;
    private int[] paddingDis = new int[4];
    private int paddingDisScroll;
    private int paddingDisUniformly;
    /* access modifiers changed from: private */
    public boolean repeatClick;
    private Map<Integer, View> rightViewMap = new HashMap();
    /* access modifiers changed from: private */
    public int screenWidth;
    private Scroller scroller;
    public ImageView tabButtomLine;
    private int tabContainerWidth;
    protected int tabCount = 3;
    private String[] tabNameArray = new String[4];
    protected RelativeLayout[] tabRl = new RelativeLayout[4];
    /* access modifiers changed from: private */
    public TabSwitchListener tabSwitchListener;
    private String tabTextArrayStr;
    private float tabTextSize;
    private int[] tabTextWidth = new int[4];
    private TextView[] tabViews = new TextView[4];
    private int[] tabWidth = new int[4];
    private int tabWidthEqualDivScreen = 100;
    public LinearLayout tabsContainer;
    private ColorStateList textColor = null;
    private boolean uniformlySpaced;
    /* access modifiers changed from: private */
    public AUScrollLayout viewContainer;

    public interface TabSwitchListener {
        void onTabClick(int i, View view);
    }

    public AUSegment(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public AUSegment(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUSegment(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Segment);
        init(context, attrs, typedArray);
        initContent(context, attrs, typedArray);
        initStyleByTheme(context);
        initAttrStyle(context, attrs, typedArray);
        typedArray.recycle();
        initView(context);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.tabCount = typedArray.getInt(0, 4);
        this.tabTextArrayStr = typedArray.getString(5);
        this.uniformlySpaced = typedArray.getBoolean(6, Boolean.FALSE.booleanValue());
        this.isScroll = typedArray.getBoolean(10, Boolean.FALSE.booleanValue());
        this.repeatClick = typedArray.getBoolean(11, Boolean.FALSE.booleanValue());
        this.isAdd = typedArray.getBoolean(12, Boolean.FALSE.booleanValue());
        if (!TextUtils.isEmpty(this.tabTextArrayStr)) {
            this.tabNameArray = this.tabTextArrayStr.split(",");
            this.tabCount = this.tabNameArray.length;
            this.tabViews = new TextView[this.tabNameArray.length];
            this.tabRl = new RelativeLayout[this.tabNameArray.length];
            this.tabWidth = new int[this.tabNameArray.length];
            this.tabTextWidth = new int[this.tabNameArray.length];
            this.paddingDis = new int[this.tabNameArray.length];
        } else {
            this.tabNameArray[0] = typedArray.getString(1);
            this.tabNameArray[1] = typedArray.getString(2);
            this.tabNameArray[2] = typedArray.getString(3);
            this.tabNameArray[3] = typedArray.getString(4);
            if (this.tabCount > 4) {
                this.tabCount = 3;
            }
        }
        for (int i = 0; i < this.tabNameArray.length; i++) {
            if (this.tabNameArray[i] == null) {
                this.tabNameArray[i] = "";
            }
        }
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void initStyleByTheme(Context context) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        if (theme.containsKey(AUThemeKey.SEGMENT_TEXTCOLOR)) {
            this.textColor = theme.getColorStateList(context, AUThemeKey.SEGMENT_TEXTCOLOR);
        }
        if (theme.containsKey(AUThemeKey.SEGMENT_BOTTOM_COLOR)) {
            this.buttomLineColor = theme.getColor(context, AUThemeKey.SEGMENT_BOTTOM_COLOR).intValue();
        }
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
        if (typedArray.getColor(9, 0) != 0) {
            this.buttomLineColor = typedArray.getColor(9, 0);
        }
        if (typedArray.getColorStateList(7) != null) {
            this.textColor = typedArray.getColorStateList(7);
        }
        this.tabTextSize = typedArray.getDimension(8, 0.0f);
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    private void initView(Context context) {
        if (!this.isScroll) {
            LayoutInflater.from(context).inflate(R.layout.ap_switch_tab_layout, this, true);
            setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.AU_SPACE10));
            this.scroller = new Scroller(context);
            this.tabsContainer = (LinearLayout) findViewById(R.id.tabs);
            this.tabButtomLine = (ImageView) findViewById(R.id.tabLine);
            this.addIcon = (AUIconView) findViewById(R.id.addIcon);
            this.bottomLine = findViewById(R.id.bottomLine);
            this.screenWidth = getResources().getDisplayMetrics().widthPixels;
            setAdd();
            getTabViews(context);
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.category_bar_layout, this);
        this.viewContainer = (AUScrollLayout) findViewById(R.id.scroll_layout);
        this.horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        this.addIcon = (AUIconView) findViewById(R.id.addIcon);
        this.menuBar = (ImageView) findViewById(R.id.menu_bar);
        if (this.buttomLineColor != 0) {
            this.menuBar.setBackgroundColor(this.buttomLineColor);
        }
        this.currentScroolBarLeft = 0.0f;
        this.screenWidth = getResources().getDisplayMetrics().widthPixels;
        this.paddingDisScroll = getResources().getDimensionPixelSize(R.dimen.au_segment_text_padding);
        setAdd();
    }

    private void setAdd() {
        if (this.isAdd) {
            this.addIcon.setVisibility(0);
            this.tabContainerWidth = this.screenWidth - getResources().getDimensionPixelSize(R.dimen.au_segment_add_width);
            return;
        }
        this.addIcon.setVisibility(8);
        this.tabContainerWidth = this.screenWidth;
    }

    /* access modifiers changed from: protected */
    public void getTabViews(Context context) {
        LayoutParams llParams;
        if (!this.isScroll) {
            this.tabsContainer.removeAllViews();
            this.mSpaced = 0;
            for (int i = 0; i < this.tabCount; i++) {
                RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.ap_default_tab_view, null);
                this.tabRl[i] = rl;
                TextView textView = (TextView) rl.findViewById(R.id.tab_tv);
                textView.setText(this.tabNameArray[i]);
                if (this.textColor != null) {
                    textView.setTextColor(this.textColor);
                }
                if (this.tabTextSize != 0.0f) {
                    textView.setTextSize(0, this.tabTextSize);
                }
                this.tabViews[i] = textView;
                Paint paint = textView.getPaint();
                setBottomLinePositionParams(i, paint);
                if (!this.uniformlySpaced || this.mSpaced <= 0 || paint == null) {
                    llParams = new LayoutParams(-2, -1, 1.0f);
                } else {
                    int curTabWidth = (int) (paint.measureText(this.tabNameArray[i]) + ((float) this.mSpaced));
                    llParams = new LayoutParams(curTabWidth, -1);
                    this.tabWidth[i] = curTabWidth;
                    this.tabTextWidth[i] = (int) paint.measureText(this.tabNameArray[i]);
                }
                llParams.gravity = 17;
                this.tabsContainer.addView(rl, llParams);
            }
        }
    }

    private void setBottomLinePositionParams(int i, Paint paint) {
        if (this.uniformlySpaced && this.mSpaced == 0 && this.screenWidth > 0 && paint != null) {
            String totalContent = "";
            for (int j = 0; j < this.tabCount; j++) {
                totalContent = totalContent + this.tabNameArray[j];
            }
            this.mSpaced = (int) ((((float) this.tabContainerWidth) - paint.measureText(totalContent)) / ((float) (this.tabCount + 1)));
            this.paddingDisUniformly = this.mSpaced / 2;
        }
        if ((!this.uniformlySpaced || this.mSpaced <= 0 || paint == null) && paint != null) {
            this.tabTextWidth[i] = (int) paint.measureText(this.tabNameArray[i]);
            this.paddingDis[i] = ((this.tabContainerWidth / this.tabCount) - this.tabTextWidth[i]) / 2;
            if (!this.uniformlySpaced && this.tabContainerWidth / this.tabCount < this.tabTextWidth[i]) {
                this.paddingDis[i] = 0;
                this.tabTextWidth[i] = this.tabContainerWidth / this.tabCount;
            }
            AuiLogger.info(TAG, "paddingDis[" + i + "] = " + this.paddingDis[i]);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!this.isScroll) {
            int newlineWidth = (r - l) / this.tabCount;
            if (newlineWidth != this.tabWidthEqualDivScreen || this.lineDrawable == null) {
                if (this.isAdd) {
                    this.tabWidthEqualDivScreen = this.tabContainerWidth / this.tabCount;
                } else {
                    this.tabWidthEqualDivScreen = newlineWidth;
                }
                initButtomLineDrawable();
            }
            if (!(r - l == this.screenWidth && r - l == this.tabContainerWidth)) {
                if (this.isAdd) {
                    this.tabContainerWidth = (r - l) - getResources().getDimensionPixelSize(R.dimen.au_segment_add_width);
                } else {
                    this.tabContainerWidth = r - l;
                }
                if (this.tabViews.length > 0) {
                    for (int i = 0; i < this.tabViews.length; i++) {
                        if (this.tabViews[i] != null) {
                            setBottomLinePositionParams(i, this.tabViews[i].getPaint());
                        }
                    }
                }
            }
            super.onLayout(changed, l, t, r, b);
            adjustTabButtomLine();
            return;
        }
        super.onLayout(changed, l, t, r, b);
    }

    private void initButtomLineDrawable() {
        initButtomLineDrawable(this.currentPos);
    }

    private void initButtomLineDrawable(int position) {
        if (!this.isScroll) {
            this.lineDrawable = new GradientDrawable();
            this.lineDrawable.setShape(0);
            if (this.buttomLineColor != 0) {
                this.lineDrawable.setColor(this.buttomLineColor);
            } else {
                this.lineDrawable.setColor(getResources().getColor(R.color.AU_COLOR9));
            }
            this.lineDrawable.setSize(this.tabTextWidth[position], getResources().getDimensionPixelSize(R.dimen.switch_tab_line_height));
            this.tabButtomLine.setImageDrawable(this.lineDrawable);
        }
    }

    public void resetTabView(String[] tabNameArray2) {
        if (!this.isScroll && tabNameArray2 != null && tabNameArray2.length != 0) {
            this.tabCount = tabNameArray2.length;
            this.currentPos = Math.min(this.tabCount - 1, this.currentPos);
            this.tabNameArray = tabNameArray2;
            this.tabViews = new TextView[this.tabCount];
            this.tabRl = new RelativeLayout[this.tabCount];
            this.tabWidth = new int[tabNameArray2.length];
            this.tabTextWidth = new int[tabNameArray2.length];
            this.paddingDis = new int[tabNameArray2.length];
            getTabViews(getContext());
            adjustTabButtomLine();
        }
    }

    public void adjustLinePosition(int position, float positionOffset) {
        if (!this.isScroll) {
            AuiLogger.info(TAG, "adjustLinePosition=" + position);
            if (this.uniformlySpaced) {
                this.matrix.setTranslate((float) getUSOffset(position), 0.0f);
                this.matrix.postTranslate(((float) this.tabWidth[position]) * positionOffset, 0.0f);
                this.tabButtomLine.setImageMatrix(this.matrix);
                initButtomLineDrawable(position);
                return;
            }
            this.matrix.setTranslate((float) ((this.tabWidthEqualDivScreen * position) + this.paddingDis[position]), 0.0f);
            this.matrix.postTranslate(((float) this.tabWidthEqualDivScreen) * positionOffset, 0.0f);
            this.tabButtomLine.setImageMatrix(this.matrix);
        }
    }

    private int getUSOffset(int position) {
        int offset = (this.mSpaced / 2) + this.paddingDisUniformly;
        for (int i = 0; i < position; i++) {
            offset += this.tabWidth[i];
        }
        return offset;
    }

    private void adjustLinePosition(int offsetPixels) {
        if (!this.isScroll) {
            this.matrix.setTranslate((float) offsetPixels, 0.0f);
            this.tabButtomLine.setImageMatrix(this.matrix);
        }
    }

    public void selectTab(int position) {
        boolean z;
        RelativeLayout relativeLayout;
        boolean z2;
        if (!this.isScroll) {
            for (int i = 0; i < this.tabCount; i++) {
                RelativeLayout relativeLayout2 = this.tabRl[i];
                if (position == i) {
                    z = true;
                } else {
                    z = false;
                }
                relativeLayout2.setSelected(z);
                if (this.allowDuplicateClick) {
                    relativeLayout = this.tabRl[i];
                    z2 = true;
                } else {
                    RelativeLayout relativeLayout3 = this.tabRl[i];
                    if (position != i) {
                        relativeLayout = relativeLayout3;
                        z2 = true;
                    } else {
                        relativeLayout = relativeLayout3;
                        z2 = false;
                    }
                }
                relativeLayout.setClickable(z2);
            }
            this.currentPos = position;
        }
    }

    public void selectTabAndAdjustLine(int position) {
        if (!this.isScroll) {
            selectTabAndAdjustLine(position, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        }
    }

    public void selectTabAndAdjustLine(int position, int during) {
        if (!this.isScroll && position != this.currentPos) {
            selectTab(position);
            if (this.scroller.computeScrollOffset()) {
                adjustLinePosition(this.currentPos, 0.0f);
                this.scroller.forceFinished(true);
            }
            initButtomLineDrawable(position);
            if (this.uniformlySpaced) {
                this.scroller.startScroll(getUSOffset(this.currentPos), 0, getUSOffset(position) - getUSOffset(this.currentPos), 0, during * Math.abs(this.currentPos - position));
            } else {
                this.scroller.startScroll((this.currentPos * this.tabWidthEqualDivScreen) + this.paddingDis[this.currentPos], 0, ((this.tabWidthEqualDivScreen * position) + this.paddingDis[position]) - ((this.currentPos * this.tabWidthEqualDivScreen) + this.paddingDis[this.currentPos]), 0, during * Math.abs(this.currentPos - position));
            }
            invalidate();
            this.currentPos = position;
        }
    }

    public void computeScroll() {
        if (!this.isScroll) {
            if (this.scroller.computeScrollOffset()) {
                adjustLinePosition(this.scroller.getCurrX());
                postInvalidate();
            }
            super.computeScroll();
        }
    }

    public void setTabSwitchListener(TabSwitchListener tabSwitchListener2) {
        if (!this.isScroll) {
            this.tabSwitchListener = tabSwitchListener2;
            for (int i = 0; i < this.tabCount; i++) {
                this.tabRl[i].setOnClickListener(new a(this).a(i));
            }
            return;
        }
        this.tabSwitchListener = tabSwitchListener2;
    }

    public TextView[] getTextViews() {
        return this.tabViews;
    }

    public View getBottomLine() {
        return this.bottomLine;
    }

    public void addTextRightView(View view, int position) {
        if (this.isScroll) {
            if (position < this.mData.size()) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
                params.leftMargin = DensityUtil.dip2px(getContext(), -15.0f);
                params.topMargin = DensityUtil.dip2px(getContext(), 6.0f);
                params.addRule(1, R.id.tv_menu_name);
                params.addRule(6, R.id.tv_menu_name);
                RelativeLayout tabInner = (RelativeLayout) ((MenuItemLayout) this.viewContainer.getChildAt(position)).findViewById(R.id.item_kernel);
                if (tabInner != null) {
                    tabInner.addView(view, params);
                }
                this.rightViewMap.put(Integer.valueOf(position), view);
            }
        } else if (this.tabRl != null || (position < this.tabRl.length && view != null && this.tabRl[position] != null)) {
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(-2, -2);
            params2.topMargin = DensityUtil.dip2px(getContext(), -5.0f);
            params2.addRule(1, R.id.tab_tv);
            params2.addRule(6, R.id.tab_tv);
            RelativeLayout tabInner2 = (RelativeLayout) this.tabRl[position].findViewById(R.id.tab_inner);
            if (tabInner2 != null) {
                tabInner2.addView(view, params2);
            }
        }
    }

    public void addTextRightView(View view, RelativeLayout.LayoutParams params, int position) {
        if (this.isScroll) {
            if (position < this.mData.size()) {
                RelativeLayout tabInner = (RelativeLayout) ((MenuItemLayout) this.viewContainer.getChildAt(position)).findViewById(R.id.item_kernel);
                if (tabInner != null) {
                    tabInner.addView(view, params);
                }
                this.rightViewMap.put(Integer.valueOf(position), view);
            }
        } else if (this.tabRl != null || (position < this.tabRl.length && view != null && params != null && this.tabRl[position] != null)) {
            RelativeLayout tabInner2 = (RelativeLayout) this.tabRl[position].findViewById(R.id.tab_inner);
            if (tabInner2 != null) {
                tabInner2.addView(view, params);
            }
        }
    }

    public void removeTextRightView(View view, int position) {
        if (this.isScroll) {
            if (position < this.mData.size()) {
                RelativeLayout tabInner = (RelativeLayout) ((MenuItemLayout) this.viewContainer.getChildAt(position)).findViewById(R.id.item_kernel);
                if (tabInner != null) {
                    tabInner.removeView(view);
                    this.rightViewMap.remove(Integer.valueOf(position));
                }
            }
        } else if (this.tabRl != null || (position < this.tabRl.length && view != null && this.tabRl[position] != null)) {
            RelativeLayout tabInner2 = (RelativeLayout) this.tabRl[position].findViewById(R.id.tab_inner);
            if (tabInner2 != null) {
                tabInner2.removeView(view);
            }
        }
    }

    public void removeTextRightViewScroll(int position) {
        if (this.isScroll && position < this.mData.size() && this.rightViewMap.containsKey(Integer.valueOf(position))) {
            RelativeLayout tabInner = (RelativeLayout) ((MenuItemLayout) this.viewContainer.getChildAt(position)).findViewById(R.id.item_kernel);
            if (tabInner != null) {
                tabInner.removeView(this.rightViewMap.get(Integer.valueOf(position)));
                this.rightViewMap.remove(Integer.valueOf(position));
            }
        }
    }

    public View getRightViewScroll(int position) {
        if (this.isScroll && position < this.mData.size() && this.rightViewMap.containsKey(Integer.valueOf(position))) {
            return this.rightViewMap.get(Integer.valueOf(position));
        }
        return null;
    }

    public void setAllowDuplicateClick(boolean allowDuplicateClick2) {
        this.allowDuplicateClick = allowDuplicateClick2;
    }

    public boolean isViewAllVisible(View view) {
        if (view.getRight() > this.screenWidth) {
            AuiLogger.info(TAG, String.format("view is not all Visible: screenWidth = %s, viewRight = %s ", new Object[]{Integer.valueOf(this.screenWidth), Integer.valueOf(view.getRight())}));
            return false;
        }
        AuiLogger.info(TAG, String.format("view is all Visible: screenWidth = %s, viewRight = %s ", new Object[]{Integer.valueOf(this.screenWidth), Integer.valueOf(view.getRight())}));
        return true;
    }

    public void setCurrentSelTab(int position) {
        if (!this.isScroll) {
            selectTab(position);
            this.currentPos = position;
            adjustTabButtomLine();
            return;
        }
        setCurrentIndex(position);
    }

    private void adjustTabButtomLine() {
        if (!this.isScroll) {
            this.scroller.forceFinished(true);
            if (this.uniformlySpaced) {
                this.matrix.setTranslate((float) getUSOffset(this.currentPos), 0.0f);
                initButtomLineDrawable(this.currentPos);
            } else {
                this.matrix.setTranslate((float) ((this.tabWidthEqualDivScreen * this.currentPos) + this.paddingDis[this.currentPos]), 0.0f);
            }
            this.tabButtomLine.setImageMatrix(this.matrix);
        }
    }

    public RelativeLayout[] getRls() {
        return this.tabRl;
    }

    private boolean isSameContent(List<ItemCategory> list1, List<ItemCategory> list2) {
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!TextUtils.equals(list1.get(i).categoryId, list2.get(i).categoryId) || !TextUtils.equals(list1.get(i).categoryname, list2.get(i).categoryname)) {
                return false;
            }
        }
        return true;
    }

    public void init(List<ItemCategory> list) {
        if (list != null) {
            if (this.mData == null || !isSameContent(this.mData, list)) {
                this.mData = list;
                if (list.size() >= 0) {
                    this.horizontalScrollView.smoothScrollTo(0, 0);
                    this.viewContainer.removeAllViews();
                    this.currentIndex = 0;
                    this.currentScroolBarLeft = 0.0f;
                }
                setMenuItemLayout();
                setBarState();
                this.rightViewMap.clear();
            }
        }
    }

    public void setAnchor(int anchor) {
        if (this.viewContainer.getChildCount() <= 0) {
            AuiLogger.error(TAG, "没有锚点对应的view,anchor = %s, 没有选项");
        } else if (this.viewContainer.getChildCount() < anchor) {
            AuiLogger.error(TAG, String.format("锚点位置超越选项总数,anchor = %s, 选项总数 = %s", new Object[]{Integer.valueOf(anchor), Integer.valueOf(this.viewContainer.getChildCount())}));
        } else if (this.viewContainer.getChildAt(anchor) == null) {
            AuiLogger.error(TAG, String.format("没有锚点对应的view,anchor = %s, 选项总数 = %s", new Object[]{Integer.valueOf(anchor), Integer.valueOf(this.viewContainer.getChildCount())}));
        } else if (!isViewAllVisible(this.viewContainer.getChildAt(anchor))) {
            AuiLogger.info(TAG, "不是全部显示在屏幕内，重新定位");
            post(new b(this, anchor));
        } else {
            AuiLogger.info(TAG, "全部显示在屏幕内，不重新定位");
        }
    }

    public void init(List<ItemCategory> list, boolean reset) {
        if (list != null) {
            if (this.mData == null || reset || !isSameContent(this.mData, list)) {
                this.mData = list;
                if (list.size() >= 0) {
                    this.horizontalScrollView.smoothScrollTo(0, 0);
                    this.viewContainer.removeAllViews();
                    this.currentIndex = 0;
                    this.currentScroolBarLeft = 0.0f;
                }
                setMenuItemLayout();
                setBarState();
                this.rightViewMap.clear();
            }
        }
    }

    public void setDivideAutoSize(boolean divideAutoSize) {
        this.viewContainer.setDivideAutoSize(divideAutoSize);
        this.viewContainer.invalidate();
        this.viewContainer.requestLayout();
    }

    private void setBarState() {
        try {
            if (this.currentIndex < this.mData.size()) {
                this.currentClickedOnMenu = (MenuItemLayout) this.viewContainer.getChildAt(this.currentIndex);
                if (this.textColor != null) {
                    this.currentClickedOnMenu.setTextColor(this.textColor);
                } else {
                    this.currentClickedOnMenu.setInitTextColor(getResources().getColor(R.color.indicate_color));
                }
                this.currentCategoryTag = (String) this.currentClickedOnMenu.getTag();
            }
            if (this.mData.size() == 0) {
                this.viewContainer.setVisibility(8);
                this.menuBar.setVisibility(4);
            } else {
                this.viewContainer.setVisibility(0);
                this.menuBar.setVisibility(0);
            }
            post(new c(this));
        } catch (Exception e) {
            AuiLogger.error(TAG, "setBarState:" + e);
        }
    }

    private void setMenuItemLayout() {
        for (int i = 0; i < this.mData.size(); i++) {
            MenuItemLayout itemLayout = new MenuItemLayout(getContext());
            ItemCategory item = this.mData.get(i);
            itemLayout.setTag(String.valueOf(i));
            itemLayout.setText(item.categoryname);
            itemLayout.setInitTextColor(getResources().getColor(R.color.black));
            if (this.tabTextSize != 0.0f) {
                itemLayout.setTextSize(this.tabTextSize);
            }
            setMenuCLick(itemLayout, item);
            this.viewContainer.addView(itemLayout);
        }
        this.viewContainer.invalidate();
        this.viewContainer.requestLayout();
    }

    private void setMenuCLick(MenuItemLayout menuItemLayout, ItemCategory item) {
        menuItemLayout.setOnClickListener(new d(this, item));
    }

    /* access modifiers changed from: private */
    public void setCurMenuState(View v, ItemCategory item) {
        if (this.currentIndex < this.mData.size()) {
            this.currentClickedOnMenu = (MenuItemLayout) this.viewContainer.getChildAt(this.currentIndex);
        }
        if (this.currentClickedOnMenu != null) {
            this.currentClickedOnMenu.setInitTextColor(getResources().getColor(R.color.black));
        }
        if (this.textColor != null) {
            ((MenuItemLayout) v).setTextColor(this.textColor);
        } else {
            ((MenuItemLayout) v).setInitTextColor(getResources().getColor(R.color.indicate_color));
        }
        if (this.tabSwitchListener != null) {
            this.tabSwitchListener.onTabClick(this.mData.indexOf(item), v);
        }
        float x = v.getX();
        moveScrollBarTo(x, ((MenuItemLayout) v).getTextWidth());
        menuBarScroll(v, x);
        this.currentIndex = this.viewContainer.indexOfChild(v);
    }

    /* access modifiers changed from: private */
    public void menuBarScroll(View v, float x) {
        int[] location = new int[2];
        v.getLocationInWindow(location);
        this.horizontalScrollView.smoothScrollBy(location[0] - (this.screenWidth / 3), 0);
        this.currentScroolBarLeft = x;
    }

    /* access modifiers changed from: private */
    public void moveScrollBarTo(float x, int width) {
        try {
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(new TranslateAnimation(this.currentScroolBarLeft, x, 0.0f, 0.0f));
            animationSet.setFillBefore(true);
            animationSet.setFillAfter(true);
            animationSet.setDuration(200);
            this.menuBar.startAnimation(animationSet);
            LayoutParams menuBarPrams = (LayoutParams) this.menuBar.getLayoutParams();
            menuBarPrams.width = width - (this.paddingDisScroll * 2);
            menuBarPrams.leftMargin = this.paddingDisScroll;
            menuBarPrams.rightMargin = this.paddingDisScroll;
            this.menuBar.setLayoutParams(menuBarPrams);
        } catch (Exception e) {
            AuiLogger.error(TAG, "moveScrollBarTo:" + e);
        }
    }

    private void setCurrentIndex(int index) {
        if (index < this.mData.size()) {
            if (this.currentIndex < this.mData.size()) {
                this.currentClickedOnMenu = (MenuItemLayout) this.viewContainer.getChildAt(this.currentIndex);
            }
            if (this.currentClickedOnMenu != null) {
                this.currentClickedOnMenu.setInitTextColor(getResources().getColor(R.color.black));
            }
            this.currentIndex = index;
            if (this.currentIndex < this.mData.size()) {
                this.currentClickedOnMenu = (MenuItemLayout) this.viewContainer.getChildAt(this.currentIndex);
                if (this.textColor != null) {
                    this.currentClickedOnMenu.setTextColor(this.textColor);
                } else {
                    this.currentClickedOnMenu.setInitTextColor(getResources().getColor(R.color.indicate_color));
                }
                post(new e(this));
                this.currentCategoryTag = (String) this.currentClickedOnMenu.getTag();
            }
        }
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public AUIconView getAddIcon() {
        return this.addIcon;
    }
}
