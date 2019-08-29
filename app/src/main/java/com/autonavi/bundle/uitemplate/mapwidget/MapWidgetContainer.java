package com.autonavi.bundle.uitemplate.mapwidget;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.api.IAJXWidgetProperty;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetContainer;
import com.autonavi.bundle.uitemplate.mapwidget.widget.ajxtemplate.IAJXTemplateContainer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MapWidgetContainer extends RelativeLayout implements IMapWidgetContainer {
    private final String TAG;
    Animator appearChangeAnimator;
    Animator disappearChangeAnimator;
    private List<IMapWidget> immersiveHoldWidgets;
    private boolean isAllWidgetsVisible;
    private boolean isImmersiveMode;
    private List<IMapWidget> mCenterBottomList;
    private List<IMapWidget> mCenterTopList;
    private List<IMapWidget> mFooterList;
    private List<IMapWidget> mHeaderList;
    private List<IMapWidget> mLeftBottomList;
    private List<IMapWidget> mLeftCenterList;
    private List<IMapWidget> mLeftTopList;
    private MarginLayoutParams mMarginLayoutParams;
    private PaddingLayoutParams mPaddingLayoutParams;
    private List<IMapWidget> mRightBottomList;
    private List<IMapWidget> mRightCenterList;
    private List<IMapWidget> mRightTopList;
    private LayoutTransition mTransition;
    private MarginLayoutParams preMarginParams;
    private Comparator<IMapWidget> sIndexComparator;
    private Comparator<IMapWidget> sPriorityComparator;

    static class PaddingLayoutParams {
        int bottom;
        int left;
        int right;
        int top;

        private PaddingLayoutParams() {
            this.left = 0;
            this.top = 0;
            this.right = 0;
            this.bottom = 0;
        }
    }

    public MapWidgetContainer(@NonNull Context context) {
        this(context, null);
    }

    public MapWidgetContainer(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public MapWidgetContainer(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = getClass().getSimpleName();
        this.sPriorityComparator = new Comparator<IMapWidget>() {
            public int compare(IMapWidget iMapWidget, IMapWidget iMapWidget2) {
                if (iMapWidget.getWidgetProperty().getPriority() < iMapWidget2.getWidgetProperty().getPriority()) {
                    return 1;
                }
                return iMapWidget.getWidgetProperty().getPriority() > iMapWidget2.getWidgetProperty().getPriority() ? -1 : 0;
            }
        };
        this.sIndexComparator = new Comparator<IMapWidget>() {
            public int compare(IMapWidget iMapWidget, IMapWidget iMapWidget2) {
                if (iMapWidget.getWidgetProperty().getIndex() < iMapWidget2.getWidgetProperty().getIndex()) {
                    return -1;
                }
                return iMapWidget.getWidgetProperty().getPriority() > iMapWidget2.getWidgetProperty().getPriority() ? 1 : 0;
            }
        };
        this.mLeftTopList = new ArrayList();
        this.mLeftCenterList = new ArrayList();
        this.mLeftBottomList = new ArrayList();
        this.mRightTopList = new ArrayList();
        this.mRightCenterList = new ArrayList();
        this.mRightBottomList = new ArrayList();
        this.mCenterTopList = new ArrayList();
        this.mCenterBottomList = new ArrayList();
        this.mHeaderList = new ArrayList();
        this.mFooterList = new ArrayList();
        this.isAllWidgetsVisible = true;
        this.isImmersiveMode = false;
        this.preMarginParams = null;
        init();
        initLayoutTransition();
    }

    private void init() {
        this.mMarginLayoutParams = new MarginLayoutParams(-2, -2);
        this.mPaddingLayoutParams = new PaddingLayoutParams();
    }

    public int addWidget(IMapWidget iMapWidget) {
        addWidget(iMapWidget, -1);
        return -1;
    }

    public int addWidget(IMapWidget iMapWidget, int i) {
        if (iMapWidget == null || iMapWidget.getContentView() == null) {
            return -1;
        }
        int addWidgetToList = addWidgetToList(iMapWidget);
        addWidgetToView(iMapWidget);
        return addWidgetToList;
    }

    public void removeWidget(IMapWidget iMapWidget) {
        if (iMapWidget != null) {
            removeWidgetFromList(iMapWidget);
            removeWidgetFromView(iMapWidget);
        }
    }

    public void removeAllWidget() {
        removeAllWidgetFromList();
        removeALlWidgetFromView();
    }

    public void setWidgets(List<IMapWidget> list) {
        if (list != null) {
            resetState();
            setWidget(list, getAllList());
        }
    }

    public void setWidgetVisible(IMapWidget iMapWidget, int i) {
        if (iMapWidget != null) {
            View contentView = iMapWidget.getContentView();
            if (contentView != null && contentView.getVisibility() != i) {
                try {
                    contentView.setVisibility(i);
                } catch (NullPointerException unused) {
                }
                if (i == 0) {
                    contentView.measure(0, 0);
                }
                requestLayout();
            }
        }
    }

    public int getWidgetVisible(IMapWidget iMapWidget) {
        if (iMapWidget == null || iMapWidget.getContentView() == null) {
            return 8;
        }
        return iMapWidget.getContentView().getVisibility();
    }

    public void setWidgetsVisibility(boolean z) {
        if (this.isAllWidgetsVisible != z) {
            this.isAllWidgetsVisible = z;
            requestLayout();
        }
    }

    public void enterImmersiveMode(IMapWidget... iMapWidgetArr) {
        enterImmersiveMode(null, iMapWidgetArr);
    }

    public void enterImmersiveMode(Rect rect, IMapWidget... iMapWidgetArr) {
        this.isImmersiveMode = true;
        if (iMapWidgetArr != null) {
            this.immersiveHoldWidgets = Arrays.asList(iMapWidgetArr);
        } else {
            this.immersiveHoldWidgets = null;
        }
        if (rect != null) {
            this.preMarginParams = new MarginLayoutParams(this.mMarginLayoutParams);
            setContainerMargin(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void existImmersiveMode() {
        this.isImmersiveMode = false;
        this.immersiveHoldWidgets = null;
        if (this.preMarginParams != null) {
            setContainerMargin(this.preMarginParams.leftMargin, this.preMarginParams.topMargin, this.preMarginParams.rightMargin, this.preMarginParams.bottomMargin);
            this.preMarginParams = null;
        }
    }

    public Rect getContainerArea(boolean z) {
        int i;
        Rect rect = new Rect();
        int[] iArr = new int[2];
        getLocationInWindow(iArr);
        int i2 = 0;
        rect.left = iArr[0];
        rect.right = (rect.left + getMeasuredWidth()) - this.mMarginLayoutParams.rightMargin;
        if (this.mHeaderList.isEmpty() || !z) {
            i = 0;
        } else {
            IMapWidget iMapWidget = this.mHeaderList.get(0);
            i = iMapWidget.getWidgetProperty().getLayoutParams().topMargin + iMapWidget.getContentView().getMeasuredHeight();
        }
        rect.top = iArr[1] + i;
        if (!this.mFooterList.isEmpty() && z) {
            IMapWidget iMapWidget2 = this.mFooterList.get(0);
            i2 = iMapWidget2.getContentView().getMeasuredHeight() + iMapWidget2.getWidgetProperty().getLayoutParams().bottomMargin;
        }
        rect.bottom = ((iArr[1] + getMeasuredHeight()) - this.mMarginLayoutParams.bottomMargin) - i2;
        return rect;
    }

    public IMapWidget findWidgetByWidgetType(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (IMapWidget next : getAllList()) {
            if (str.equals(next.getWidgetProperty().getWidgetType())) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        List<IMapWidget> list;
        Iterator<IMapWidget> it;
        int i9;
        MapWidgetContainer mapWidgetContainer = this;
        if (!mapWidgetContainer.isAllWidgetsVisible) {
            removeAllViewsInLayout();
            return;
        }
        int i10 = i + mapWidgetContainer.mPaddingLayoutParams.left + mapWidgetContainer.mMarginLayoutParams.leftMargin;
        int i11 = (i3 - mapWidgetContainer.mPaddingLayoutParams.right) - mapWidgetContainer.mMarginLayoutParams.rightMargin;
        int i12 = i2 + mapWidgetContainer.mPaddingLayoutParams.top + mapWidgetContainer.mMarginLayoutParams.topMargin;
        int i13 = (i4 - mapWidgetContainer.mPaddingLayoutParams.bottom) - mapWidgetContainer.mMarginLayoutParams.bottomMargin;
        handleWidgetsEnableState();
        mapWidgetContainer.handleConflict(i13, i12);
        controlAllWidgetsAttach();
        int i14 = i11 - i10;
        if (mapWidgetContainer.mHeaderList.size() > 0) {
            IMapWidget iMapWidget = mapWidgetContainer.mHeaderList.get(0);
            View contentView = iMapWidget.getContentView();
            IWidgetProperty widgetProperty = iMapWidget.getWidgetProperty();
            MarginLayoutParams combineLayoutParams = mapWidgetContainer.getCombineLayoutParams(widgetProperty);
            if (widgetProperty.isEnable()) {
                i5 = combineLayoutParams.topMargin + i12;
                int i15 = combineLayoutParams.topMargin + i12;
                i9 = i12 + combineLayoutParams.topMargin;
                i12 = i15;
            } else {
                i9 = i12;
                i5 = i9;
            }
            int i16 = combineLayoutParams.leftMargin + i10;
            contentView.layout(i16, i12, contentView.getMeasuredWidth() + i16, contentView.getMeasuredHeight() + i12);
            if (widgetProperty.isEnable()) {
                i12 += contentView.getMeasuredHeight() + combineLayoutParams.bottomMargin;
                i6 = contentView.getMeasuredHeight() + combineLayoutParams.bottomMargin + i9;
                i5 = contentView.getMeasuredHeight() + combineLayoutParams.bottomMargin + i5;
            } else {
                i6 = i9;
            }
        } else {
            i6 = i12;
            i5 = i6;
        }
        if (mapWidgetContainer.mFooterList.size() > 0) {
            IMapWidget iMapWidget2 = mapWidgetContainer.mFooterList.get(0);
            View contentView2 = iMapWidget2.getContentView();
            IWidgetProperty widgetProperty2 = iMapWidget2.getWidgetProperty();
            MarginLayoutParams combineLayoutParams2 = mapWidgetContainer.getCombineLayoutParams(widgetProperty2);
            if (widgetProperty2.isEnable()) {
                i7 = i13 - combineLayoutParams2.bottomMargin;
                int i17 = i13 - combineLayoutParams2.bottomMargin;
                i8 = i13 - combineLayoutParams2.bottomMargin;
                i13 = i17;
            } else {
                i8 = i13;
                i7 = i8;
            }
            int measuredWidth = ((i14 - ((combineLayoutParams2.leftMargin + contentView2.getMeasuredWidth()) + combineLayoutParams2.rightMargin)) / 2) + i10 + combineLayoutParams2.leftMargin;
            contentView2.layout(measuredWidth, i13 - contentView2.getMeasuredHeight(), measuredWidth + contentView2.getMeasuredWidth(), i13);
            if (widgetProperty2.isEnable()) {
                i13 -= contentView2.getMeasuredHeight() + combineLayoutParams2.topMargin;
                i7 -= contentView2.getMeasuredHeight() + combineLayoutParams2.topMargin;
                i8 -= contentView2.getMeasuredHeight() + combineLayoutParams2.topMargin;
            }
        } else {
            i8 = i13;
            i7 = i8;
        }
        List<IMapWidget> allList = getAllList();
        Collections.sort(allList, mapWidgetContainer.sIndexComparator);
        Iterator<IMapWidget> it2 = allList.iterator();
        while (it2.hasNext()) {
            IMapWidget next = it2.next();
            View contentView3 = next.getContentView();
            IWidgetProperty widgetProperty3 = next.getWidgetProperty();
            MarginLayoutParams combineLayoutParams3 = mapWidgetContainer.getCombineLayoutParams(widgetProperty3);
            switch (widgetProperty3.getAlignType()) {
                case 1:
                    list = allList;
                    it = it2;
                    int i18 = combineLayoutParams3.leftMargin + i10;
                    contentView3.layout(i18, combineLayoutParams3.topMargin + i12, contentView3.getMeasuredWidth() + i18, combineLayoutParams3.topMargin + i12 + contentView3.getMeasuredHeight());
                    if (!widgetProperty3.isEnable()) {
                        break;
                    } else {
                        i12 += combineLayoutParams3.topMargin + contentView3.getMeasuredHeight() + combineLayoutParams3.bottomMargin;
                        break;
                    }
                case 3:
                    list = allList;
                    it = it2;
                    int i19 = combineLayoutParams3.leftMargin + i10;
                    contentView3.layout(i19, (i13 - combineLayoutParams3.bottomMargin) - contentView3.getMeasuredHeight(), contentView3.getMeasuredWidth() + i19, i13 - combineLayoutParams3.bottomMargin);
                    if (!widgetProperty3.isEnable()) {
                        break;
                    } else {
                        i13 -= (combineLayoutParams3.topMargin + contentView3.getMeasuredHeight()) + combineLayoutParams3.bottomMargin;
                        break;
                    }
                case 4:
                    list = allList;
                    it = it2;
                    int i20 = i11 - combineLayoutParams3.rightMargin;
                    contentView3.layout(i20 - contentView3.getMeasuredWidth(), combineLayoutParams3.topMargin + i6, i20, combineLayoutParams3.topMargin + i6 + contentView3.getMeasuredHeight());
                    if (!widgetProperty3.isEnable()) {
                        break;
                    } else {
                        i6 += combineLayoutParams3.topMargin + contentView3.getMeasuredHeight() + combineLayoutParams3.bottomMargin;
                        break;
                    }
                case 6:
                    list = allList;
                    it = it2;
                    int i21 = i11 - combineLayoutParams3.rightMargin;
                    contentView3.layout(i21 - contentView3.getMeasuredWidth(), (i8 - contentView3.getMeasuredHeight()) - combineLayoutParams3.bottomMargin, i21, i8 - combineLayoutParams3.bottomMargin);
                    if (!widgetProperty3.isEnable()) {
                        break;
                    } else {
                        i8 -= (combineLayoutParams3.topMargin + contentView3.getMeasuredHeight()) + combineLayoutParams3.bottomMargin;
                        break;
                    }
                case 7:
                    list = allList;
                    it = it2;
                    int measuredWidth2 = ((i14 / 2) + i10) - (contentView3.getMeasuredWidth() / 2);
                    contentView3.layout(measuredWidth2, combineLayoutParams3.topMargin + i5, contentView3.getMeasuredWidth() + measuredWidth2, combineLayoutParams3.topMargin + i5 + contentView3.getMeasuredWidth());
                    if (!widgetProperty3.isEnable()) {
                        break;
                    } else {
                        i5 += combineLayoutParams3.topMargin + contentView3.getMeasuredHeight() + combineLayoutParams3.bottomMargin;
                        break;
                    }
                case 8:
                    int measuredWidth3 = ((i14 / 2) + i10) - (contentView3.getMeasuredWidth() / 2);
                    it = it2;
                    list = allList;
                    contentView3.layout(measuredWidth3, (i7 - combineLayoutParams3.bottomMargin) - contentView3.getMeasuredHeight(), measuredWidth3 + contentView3.getMeasuredWidth(), i7 - combineLayoutParams3.bottomMargin);
                    if (!widgetProperty3.isEnable()) {
                        break;
                    } else {
                        i7 -= (combineLayoutParams3.topMargin + contentView3.getMeasuredHeight()) + combineLayoutParams3.bottomMargin;
                        break;
                    }
                default:
                    list = allList;
                    it = it2;
                    break;
            }
            it2 = it;
            allList = list;
            mapWidgetContainer = this;
        }
        int measureWidgetsHeight = i12 + (((i13 - i12) - measureWidgetsHeight(this.mLeftCenterList)) / 2);
        int measureWidgetsHeight2 = i6 + (((i8 - i6) - measureWidgetsHeight(this.mRightCenterList)) / 2);
        for (IMapWidget next2 : allList) {
            View contentView4 = next2.getContentView();
            IWidgetProperty widgetProperty4 = next2.getWidgetProperty();
            MarginLayoutParams combineLayoutParams4 = getCombineLayoutParams(widgetProperty4);
            int alignType = widgetProperty4.getAlignType();
            if (alignType == 2) {
                int i22 = combineLayoutParams4.leftMargin + i10;
                contentView4.layout(i22, combineLayoutParams4.topMargin + measureWidgetsHeight, contentView4.getMeasuredWidth() + i22, combineLayoutParams4.topMargin + measureWidgetsHeight + contentView4.getMeasuredHeight());
                if (widgetProperty4.isEnable()) {
                    measureWidgetsHeight += combineLayoutParams4.topMargin + contentView4.getMeasuredHeight() + combineLayoutParams4.bottomMargin;
                }
            } else if (alignType == 5) {
                int i23 = i11 - combineLayoutParams4.rightMargin;
                contentView4.layout(i23 - contentView4.getMeasuredWidth(), combineLayoutParams4.topMargin + measureWidgetsHeight2, i23, combineLayoutParams4.topMargin + measureWidgetsHeight2 + contentView4.getMeasuredHeight());
                if (widgetProperty4.isEnable()) {
                    measureWidgetsHeight2 += combineLayoutParams4.topMargin + contentView4.getMeasuredHeight() + combineLayoutParams4.bottomMargin;
                }
            }
        }
    }

    private MarginLayoutParams getCombineLayoutParams(IWidgetProperty iWidgetProperty) {
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(iWidgetProperty.getLayoutParams());
        marginLayoutParams.leftMargin += iWidgetProperty.getOffsetParams().leftMargin;
        marginLayoutParams.topMargin += iWidgetProperty.getOffsetParams().topMargin;
        marginLayoutParams.rightMargin += iWidgetProperty.getOffsetParams().rightMargin;
        marginLayoutParams.bottomMargin += iWidgetProperty.getOffsetParams().bottomMargin;
        return marginLayoutParams;
    }

    private void handleWidgetsEnableState() {
        List<IMapWidget> allList = getAllList();
        if (this.isImmersiveMode) {
            for (IMapWidget next : allList) {
                next.getWidgetProperty().setEnable((this.immersiveHoldWidgets == null || !this.immersiveHoldWidgets.contains(next) || next.getContentView().getVisibility() == 8) ? false : true);
            }
            return;
        }
        for (IMapWidget next2 : allList) {
            next2.getWidgetProperty().setEnable(next2.getContentView().getVisibility() != 8);
        }
    }

    private void handleConflict(int i, int i2) {
        int headerHeight = (i - i2) - (getHeaderHeight() + getFooterHeight());
        handleConflict(headerHeight, (List<IMapWidget>[]) new List[]{this.mLeftTopList, this.mLeftCenterList, this.mLeftBottomList});
        handleConflict(headerHeight, (List<IMapWidget>[]) new List[]{this.mRightTopList, this.mRightCenterList, this.mRightBottomList});
    }

    private void handleConflict(int i, List<IMapWidget>... listArr) {
        if (listArr != null && listArr.length != 0) {
            ArrayList<IMapWidget> arrayList = new ArrayList<>();
            for (List<IMapWidget> addAll : listArr) {
                arrayList.addAll(addAll);
            }
            Collections.sort(arrayList, this.sPriorityComparator);
            int i2 = i;
            boolean z = false;
            for (IMapWidget iMapWidget : arrayList) {
                if (iMapWidget.getWidgetProperty().isEnable()) {
                    if (z) {
                        iMapWidget.getWidgetProperty().setEnable(false);
                    } else {
                        if ((iMapWidget.getContentView() instanceof IAJXTemplateContainer) && (iMapWidget.getWidgetProperty() instanceof IAJXWidgetProperty) && ((IAJXWidgetProperty) iMapWidget.getWidgetProperty()).isLayoutAdjustable()) {
                            z = !((IAJXTemplateContainer) iMapWidget.getContentView()).relayoutOfMaxHeight(i2);
                            StringBuilder sb = new StringBuilder("handleConflict: showMax = ");
                            sb.append(!z);
                            sb.append(", widgetHeight = ");
                            sb.append(getWidgetHeight(iMapWidget));
                        }
                        int widgetHeight = getWidgetHeight(iMapWidget);
                        if (widgetHeight > i2) {
                            iMapWidget.getWidgetProperty().setEnable(false);
                            z = true;
                        } else {
                            i2 -= widgetHeight;
                        }
                    }
                }
            }
        }
    }

    private int getHeaderHeight() {
        if (this.mHeaderList.size() > 0) {
            return 0 + getWidgetHeight(this.mHeaderList.get(0));
        }
        return 0;
    }

    private int getFooterHeight() {
        if (this.mFooterList.size() > 0) {
            return 0 + getWidgetHeight(this.mFooterList.get(0));
        }
        return 0;
    }

    private void controlAllWidgetsAttach() {
        for (IMapWidget next : getAllList()) {
            View contentView = next.getContentView();
            if (next.getWidgetProperty().isEnable()) {
                ViewParent parent = contentView.getParent();
                if (parent == null) {
                    addViewInLayout(contentView, -1, contentView.getLayoutParams() == null ? generateDefaultLayoutParams() : contentView.getLayoutParams());
                } else {
                    ViewGroup viewGroup = (ViewGroup) parent;
                    if (viewGroup != this) {
                        viewGroup.removeView(contentView);
                        addViewInLayout(contentView, -1, contentView.getLayoutParams() == null ? generateDefaultLayoutParams() : contentView.getLayoutParams());
                    }
                }
            } else {
                removeViewInLayout(contentView);
            }
        }
    }

    private int getWidgetHeight(IMapWidget iMapWidget) {
        return iMapWidget.getContentView().getMeasuredHeight() + iMapWidget.getWidgetProperty().getLayoutParams().topMargin + iMapWidget.getWidgetProperty().getLayoutParams().bottomMargin;
    }

    private int measureWidgetsHeight(List<IMapWidget> list) {
        int i = 0;
        if (list == null || list.size() == 0) {
            return 0;
        }
        for (IMapWidget next : list) {
            View contentView = next.getContentView();
            if (next.getWidgetProperty().isEnable()) {
                MarginLayoutParams layoutParams = next.getWidgetProperty().getLayoutParams();
                i += contentView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
        }
        return i;
    }

    private void initLayoutTransition() {
        this.mTransition = new bes();
        setLayoutTransition(this.mTransition);
        this.mTransition.setDuration(2, 300);
        this.mTransition.setInterpolator(2, new AccelerateDecelerateInterpolator());
        this.mTransition.setAnimator(2, ObjectAnimator.ofFloat(null, "alpha", new float[]{0.0f, 1.0f}));
        this.mTransition.disableTransitionType(2);
        this.mTransition.setDuration(3, 300);
        this.mTransition.setInterpolator(3, new AccelerateDecelerateInterpolator());
        this.mTransition.setAnimator(3, ObjectAnimator.ofFloat(null, "alpha", new float[]{1.0f, 0.0f}));
        this.mTransition.disableTransitionType(3);
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("left", new int[]{0, 1});
        PropertyValuesHolder ofInt2 = PropertyValuesHolder.ofInt("top", new int[]{0, 1});
        PropertyValuesHolder ofInt3 = PropertyValuesHolder.ofInt("right", new int[]{0, 1});
        PropertyValuesHolder ofInt4 = PropertyValuesHolder.ofInt("bottom", new int[]{0, 1});
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scaleX", new float[]{1.0f, 1.0f});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 1.0f});
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofInt, ofInt2, ofInt3, ofInt4, ofFloat, ofFloat2});
        this.mTransition.setDuration(0, 500);
        this.mTransition.setInterpolator(0, new buq());
        this.mTransition.setAnimator(0, ofPropertyValuesHolder);
        this.mTransition.disableTransitionType(0);
        ObjectAnimator ofPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofInt, ofInt2, ofInt3, ofInt4, ofFloat, ofFloat2});
        this.mTransition.setDuration(1, 500);
        this.mTransition.setInterpolator(1, new buq());
        this.mTransition.setAnimator(1, ofPropertyValuesHolder2);
        this.mTransition.disableTransitionType(1);
    }

    private int addWidgetToList(IMapWidget iMapWidget) {
        IWidgetProperty widgetProperty = iMapWidget.getWidgetProperty();
        switch (widgetProperty.getAlignType()) {
            case 1:
                this.mLeftTopList.remove(iMapWidget);
                this.mLeftTopList.add(iMapWidget);
                break;
            case 2:
                this.mLeftCenterList.remove(iMapWidget);
                this.mLeftCenterList.add(iMapWidget);
                break;
            case 3:
                this.mLeftBottomList.remove(iMapWidget);
                this.mLeftBottomList.add(iMapWidget);
                break;
            case 4:
                this.mRightTopList.remove(iMapWidget);
                this.mRightTopList.add(iMapWidget);
                break;
            case 5:
                this.mRightCenterList.remove(iMapWidget);
                this.mRightCenterList.add(iMapWidget);
                break;
            case 6:
                this.mRightBottomList.remove(iMapWidget);
                this.mRightBottomList.add(iMapWidget);
                break;
            case 7:
                this.mCenterTopList.remove(iMapWidget);
                this.mCenterTopList.add(iMapWidget);
                break;
            case 8:
                this.mCenterBottomList.remove(iMapWidget);
                this.mCenterBottomList.add(iMapWidget);
                break;
            case 9:
                this.mHeaderList.clear();
                this.mHeaderList.add(iMapWidget);
                break;
            case 10:
                this.mFooterList.clear();
                this.mFooterList.add(iMapWidget);
                break;
        }
        return widgetProperty.getIndex();
    }

    private void addWidgetToView(IMapWidget iMapWidget) {
        ViewParent parent = iMapWidget.getContentView().getParent();
        if (iMapWidget.getContentView().getVisibility() != 8) {
            if (parent == null) {
                addView(iMapWidget.getContentView());
            } else if (parent != this) {
                ((ViewGroup) iMapWidget.getContentView().getParent()).removeView(iMapWidget.getContentView());
                addView(iMapWidget.getContentView());
            } else {
                requestLayout();
            }
        }
    }

    private void removeWidgetFromList(IMapWidget iMapWidget) {
        this.mLeftTopList.remove(iMapWidget);
        this.mLeftCenterList.remove(iMapWidget);
        this.mLeftBottomList.remove(iMapWidget);
        this.mRightTopList.remove(iMapWidget);
        this.mRightCenterList.remove(iMapWidget);
        this.mRightBottomList.remove(iMapWidget);
        this.mCenterTopList.remove(iMapWidget);
        this.mCenterBottomList.remove(iMapWidget);
        this.mHeaderList.remove(iMapWidget);
        this.mFooterList.remove(iMapWidget);
    }

    private void removeAllWidgetFromList() {
        this.mLeftTopList.clear();
        this.mLeftCenterList.clear();
        this.mLeftBottomList.clear();
        this.mRightTopList.clear();
        this.mRightCenterList.clear();
        this.mRightBottomList.clear();
        this.mCenterTopList.clear();
        this.mCenterBottomList.clear();
        this.mHeaderList.clear();
        this.mFooterList.clear();
    }

    private void removeALlWidgetFromView() {
        removeAllViews();
    }

    private void removeWidgetFromView(IMapWidget iMapWidget) {
        removeView(iMapWidget.getContentView());
    }

    private void setWidget(List<IMapWidget> list, List<IMapWidget> list2) {
        for (int size = list2.size() - 1; size >= 0; size--) {
            IMapWidget iMapWidget = list2.get(size);
            if (!list.contains(iMapWidget)) {
                removeWidget(iMapWidget);
            }
        }
        for (IMapWidget next : list) {
            if (!list2.contains(next)) {
                addWidget(next);
            } else if (merge(next)) {
                requestLayout();
            }
        }
    }

    private boolean merge(IMapWidget iMapWidget) {
        boolean z;
        boolean z2;
        switch (iMapWidget.getWidgetProperty().getAlignType()) {
            case 1:
                z2 = this.mLeftTopList.contains(iMapWidget);
                break;
            case 2:
                z2 = this.mLeftCenterList.contains(iMapWidget);
                break;
            case 3:
                z2 = this.mLeftBottomList.contains(iMapWidget);
                break;
            case 4:
                z2 = this.mRightTopList.contains(iMapWidget);
                break;
            case 5:
                z2 = this.mRightCenterList.contains(iMapWidget);
                break;
            case 6:
                z2 = this.mRightBottomList.contains(iMapWidget);
                break;
            case 7:
                z2 = this.mCenterTopList.contains(iMapWidget);
                break;
            case 8:
                z2 = this.mCenterBottomList.contains(iMapWidget);
                break;
            case 9:
                z2 = this.mHeaderList.contains(iMapWidget);
                break;
            case 10:
                z2 = this.mFooterList.contains(iMapWidget);
                break;
            default:
                z = false;
                break;
        }
        z = !z2;
        if (z) {
            removeWidgetFromList(iMapWidget);
            addWidgetToList(iMapWidget);
            doAnimForMerge(iMapWidget);
        }
        return z;
    }

    private void doAnimForMerge(IMapWidget iMapWidget) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(null, "alpha", new float[]{0.0f, 1.0f});
        iMapWidget.getContentView().setAlpha(0.0f);
        iMapWidget.getContentView().clearAnimation();
        ofFloat.setTarget(iMapWidget.getContentView());
        ofFloat.setStartDelay(200);
        ofFloat.setDuration(500);
        ofFloat.start();
    }

    private List<IMapWidget> getAllList() {
        ArrayList arrayList = new ArrayList(this.mLeftTopList.size() + this.mLeftCenterList.size() + this.mLeftBottomList.size() + this.mRightTopList.size() + this.mRightCenterList.size() + this.mRightBottomList.size() + this.mCenterTopList.size() + this.mCenterBottomList.size());
        arrayList.addAll(this.mLeftTopList);
        arrayList.addAll(this.mLeftCenterList);
        arrayList.addAll(this.mLeftBottomList);
        arrayList.addAll(this.mCenterTopList);
        arrayList.addAll(this.mCenterBottomList);
        arrayList.addAll(this.mRightTopList);
        arrayList.addAll(this.mRightCenterList);
        arrayList.addAll(this.mRightBottomList);
        arrayList.addAll(this.mHeaderList);
        arrayList.addAll(this.mFooterList);
        return arrayList;
    }

    public void setContainerMargin(int i, int i2, int i3, int i4) {
        StringBuilder sb = new StringBuilder("setContainerMargin() called with: left = [");
        sb.append(i);
        sb.append("], top = [");
        sb.append(i2);
        sb.append("], right = [");
        sb.append(i3);
        sb.append("], bottom = [");
        sb.append(i4);
        sb.append("]");
        this.mMarginLayoutParams.leftMargin = i;
        this.mMarginLayoutParams.topMargin = i2;
        this.mMarginLayoutParams.rightMargin = i3;
        this.mMarginLayoutParams.bottomMargin = i4;
        requestLayout();
    }

    public MarginLayoutParams getContainerMargin() {
        return new MarginLayoutParams(this.mMarginLayoutParams);
    }

    public void setContainerBottomMargin(int i, boolean z) {
        int i2 = this.mMarginLayoutParams.leftMargin;
        int i3 = this.mMarginLayoutParams.topMargin;
        int i4 = this.mMarginLayoutParams.rightMargin;
        if (this.mMarginLayoutParams.bottomMargin != i) {
            setContainerMargin(i2, i3, i4, i);
        }
    }

    public void setContainerTopMargin(int i, boolean z) {
        int i2 = this.mMarginLayoutParams.leftMargin;
        int i3 = this.mMarginLayoutParams.topMargin;
        int i4 = this.mMarginLayoutParams.rightMargin;
        int i5 = this.mMarginLayoutParams.bottomMargin;
        if (i3 != i) {
            setContainerMargin(i2, i, i4, i5);
        }
    }

    public void setContainerPadding(int i, int i2, int i3, int i4) {
        this.mPaddingLayoutParams.left = i;
        this.mPaddingLayoutParams.top = i2;
        this.mPaddingLayoutParams.right = i3;
        this.mPaddingLayoutParams.bottom = i4;
        requestLayout();
    }

    public void setContainerAlpha(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        setAlpha(f);
    }

    public float getContainerAlpha() {
        return getAlpha();
    }

    public Context getContainerContext() {
        return getContext();
    }

    public void requestContainerLayout() {
        requestLayout();
    }

    private void resetState() {
        this.isImmersiveMode = false;
        this.immersiveHoldWidgets = null;
        this.isAllWidgetsVisible = true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (getContainerAlpha() <= 0.0f) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
