package com.amap.bundle.planhome.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.amap.bundle.planhome.view.RouteTabToolBarContainer.a;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.State;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.RouteHeaderModel;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RouteTabScrollView;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RouteInputViewContainer extends LinearLayout {
    private static final int MAX_PASS_POI_SIZE = 3;
    private AnimatorListenerAdapter mAnimatorListenerAdapter;
    private View mBottomShadow;
    private boolean mEditEnable;
    private CharSequence mEndContent;
    private HashMap<RouteType, Boolean> mExchangeMap;
    private View mInputHeaderWithShadow;
    /* access modifiers changed from: private */
    public View mInputHeaderWithoutShadow;
    private View mLeftShadow;
    private View mRightShadow;
    private View mRightToolbar;
    private RouteEditView mRouteInputEditView;
    /* access modifiers changed from: private */
    public acx mRouteInputEditViewDispatcher;
    private RouteTabScrollView mRouteTabScrollView;
    /* access modifiers changed from: private */
    public RouteTabToolBarContainer mRouteTabToolBarView;
    /* access modifiers changed from: private */
    public RouteZoomTabView mRouteTabView;
    private CharSequence mStartContent;
    private TitleStyle mTitleStyle;
    private View mToolbarBtn;
    private AnimatorUpdateListener mUpdateListener;

    public enum TitleStyle {
        NORMAL,
        VOICE_STYLE,
        FAVORITATE_STYLE,
        ETRIP_STYLE
    }

    private void setPassViewVisible(int i) {
    }

    private void updatePassName() {
    }

    public void hideVUIEmoji() {
    }

    public void showVUIEmoji() {
    }

    public RouteInputViewContainer(Context context) {
        this(context, null);
    }

    public RouteInputViewContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteInputViewContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mExchangeMap = new HashMap<>();
        this.mEditEnable = true;
        initViews(context);
        initUpdater();
    }

    public boolean setParentView(RelativeLayout relativeLayout) {
        initRouteToolbar(relativeLayout);
        return true;
    }

    public int getTabPos(RouteType routeType) {
        if (this.mRouteTabView != null) {
            return this.mRouteTabView.getTabPos(routeType);
        }
        return 0;
    }

    public ViewGroup getTabViewGroup(RouteType routeType) {
        if (this.mRouteTabView != null) {
            return this.mRouteTabView.getTabViewGoup(routeType);
        }
        return null;
    }

    public void enableExchange(RouteType routeType, boolean z) {
        if (this.mExchangeMap != null) {
            this.mExchangeMap.put(routeType, Boolean.valueOf(z));
        }
        if (getCurrentType() == routeType) {
            this.mRouteInputEditView.setEnable(3, canExchange());
        }
    }

    public boolean canExchange() {
        if (this.mExchangeMap != null) {
            Boolean bool = this.mExchangeMap.get(getCurrentType());
            if (bool != null) {
                return bool.booleanValue();
            }
        }
        return true;
    }

    public void setStyle(TitleStyle titleStyle) {
        if (this.mRouteInputEditView != null) {
            this.mTitleStyle = titleStyle;
            switch (titleStyle) {
                case NORMAL:
                    this.mRouteInputEditView.setVisibility(0);
                    this.mRouteTabScrollView.setVisibility(0);
                    findViewById(R.id.view_voice_title).setVisibility(8);
                    findViewById(R.id.route_favorite_title_bar).setVisibility(8);
                    this.mRightToolbar.setVisibility(0);
                    return;
                case VOICE_STYLE:
                    this.mRouteInputEditView.setVisibility(8);
                    this.mRouteTabScrollView.setVisibility(0);
                    findViewById(R.id.view_voice_title).setVisibility(0);
                    findViewById(R.id.route_favorite_title_bar).setVisibility(8);
                    this.mRightToolbar.setVisibility(0);
                    return;
                case FAVORITATE_STYLE:
                    this.mRouteInputEditView.setVisibility(8);
                    this.mRouteTabScrollView.setVisibility(8);
                    this.mRouteTabScrollView.setShadowIsShow(false);
                    findViewById(R.id.view_voice_title).setVisibility(8);
                    this.mRightToolbar.setVisibility(8);
                    return;
                case ETRIP_STYLE:
                    this.mRouteInputEditView.setVisibility(8);
                    this.mRouteTabScrollView.setVisibility(8);
                    this.mRouteTabScrollView.setShadowIsShow(false);
                    findViewById(R.id.view_voice_title).setVisibility(8);
                    findViewById(R.id.title_btn_left).setVisibility(0);
                    this.mRightToolbar.setVisibility(8);
                    this.mBottomShadow.setVisibility(8);
                    break;
            }
        }
    }

    public TitleStyle getStyle() {
        return this.mTitleStyle;
    }

    public boolean isToolbarShown() {
        return this.mRouteTabToolBarView != null && this.mRouteTabToolBarView.isShown();
    }

    public void setToolbarVisibility(int i) {
        if (this.mRouteTabToolBarView != null) {
            updateToolbarTop();
            this.mRouteTabToolBarView.setVisibility(i);
        }
    }

    /* access modifiers changed from: private */
    public void updateToolbarTop() {
        int i;
        if (this.mInputHeaderWithShadow != null) {
            i = this.mInputHeaderWithShadow.getMeasuredHeight() - agn.a(getContext(), 60.0f);
        } else {
            i = 0;
        }
        this.mRouteTabToolBarView.setMarginTop(i);
    }

    private void initRouteToolbar(View view) {
        if (view != null) {
            this.mRouteTabToolBarView = (RouteTabToolBarContainer) view.findViewById(R.id.route_input_tool_bar_container);
            this.mRouteTabToolBarView.setOnTabStatusListener(new a() {
                public final void a(RouteType routeType) {
                    if (RouteInputViewContainer.this.getCurrentType() != routeType) {
                        RouteInputViewContainer.this.selectTab(routeType, true);
                    } else {
                        RouteInputViewContainer.this.scrollToTab(routeType);
                    }
                }

                public final void a(RouteType[] routeTypeArr) {
                    if (routeTypeArr.length != 0) {
                        RouteType routeType = null;
                        if (RouteInputViewContainer.this.mRouteTabView != null) {
                            routeType = RouteInputViewContainer.this.mRouteTabView.getCurrentType();
                        }
                        List asList = Arrays.asList(routeTypeArr);
                        RouteInputViewContainer.this.setRouteTypes(asList);
                        acs.a(asList);
                        if (routeType != null) {
                            RouteInputViewContainer.this.selectTab(routeType, false);
                        }
                        if (RouteInputViewContainer.this.mRouteTabView != null) {
                            RouteInputViewContainer.this.mRouteTabView.post(new Runnable() {
                                public final void run() {
                                    RouteInputViewContainer.this.scrollToTab(RouteInputViewContainer.this.getCurrentType());
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.plan_input_view_group, this, true);
        this.mInputHeaderWithShadow = findViewById(R.id.route_fragment_header_with_shadow);
        this.mInputHeaderWithoutShadow = findViewById(R.id.route_fragment_header);
        this.mRouteInputEditView = (RouteEditView) findViewById(R.id.route_page_input_edit_view);
        this.mRouteTabScrollView = (RouteTabScrollView) findViewById(R.id.tab_holder);
        this.mRouteTabView = (RouteZoomTabView) findViewById(R.id.tab);
        this.mLeftShadow = findViewById(R.id.tab_left_shadow);
        this.mRightShadow = findViewById(R.id.tab_right_shadow);
        this.mBottomShadow = findViewById(R.id.route_input_view_container_shadow);
        this.mRightToolbar = findViewById(R.id.tab_tool_bar_img);
        this.mToolbarBtn = findViewById(R.id.tab_tool_bar_img);
        this.mRouteTabScrollView.setShadows(this.mLeftShadow, this.mRightShadow);
        this.mLeftShadow.setVisibility(8);
        this.mRightShadow.setVisibility(8);
        findViewById(R.id.route_fragment_header).setVisibility(0);
        this.mRouteInputEditView.setEditable(false);
        this.mRouteInputEditView.changeState(State.PRE_EDIT, true, null);
        prepareEnterAnimator();
        this.mRouteTabView.setScrollView(this.mRouteTabScrollView);
        initTabView();
        initInnerListener();
    }

    private void initUpdater() {
        this.mUpdateListener = new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int measuredHeight = RouteInputViewContainer.this.mInputHeaderWithoutShadow != null ? RouteInputViewContainer.this.mInputHeaderWithoutShadow.getMeasuredHeight() : 0;
                if (RouteInputViewContainer.this.mRouteInputEditViewDispatcher != null) {
                    eao.e(getClass().getName(), "Header onAnimationUpdate: ".concat(String.valueOf(measuredHeight)));
                    acx access$300 = RouteInputViewContainer.this.mRouteInputEditViewDispatcher;
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt(IRouteHeaderEvent.HEAD_ANIMATION_DOING.toString(), measuredHeight);
                    access$300.a(IRouteHeaderEvent.HEAD_ANIMATION_DOING, pageBundle);
                }
            }
        };
        this.mAnimatorListenerAdapter = new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (RouteInputViewContainer.this.mRouteInputEditViewDispatcher != null) {
                    eao.e(getClass().getName(), "Header Animation done ");
                    RouteInputViewContainer.this.mRouteInputEditViewDispatcher.a(IRouteHeaderEvent.HEAD_SUMMARY_ANIMAITON_DONE, new PageBundle());
                }
            }
        };
    }

    public void setRouteTypes(List<RouteType> list) {
        if (list != null && list.size() != 0) {
            int size = list.size();
            this.mRouteTabScrollView.setShadowIsShow(((float) size) > ((getScreenSizeOfDevice() > 4.6d ? 1 : (getScreenSizeOfDevice() == 4.6d ? 0 : -1)) > 0 ? 6.5f : 5.5f));
            this.mRouteTabView.clearTabs();
            for (int i = 0; i < list.size(); i++) {
                RouteType routeType = list.get(i);
                this.mRouteTabView.addTab(routeType, getTabName(routeType));
            }
        }
    }

    public void addRouteType(RouteType routeType) {
        this.mRouteTabView.addTab(routeType, getTabName(routeType), true);
    }

    public RouteType getCurrentType() {
        if (this.mRouteTabView != null) {
            return this.mRouteTabView.getCurrentType();
        }
        return RouteType.DEFAULT;
    }

    public RouteType[] getCurrentTypes() {
        if (this.mRouteTabView != null) {
            return this.mRouteTabView.getCurrentTypes();
        }
        return new RouteType[0];
    }

    public void prepareEnterAnimator() {
        if (this.mRouteInputEditView != null) {
            this.mRouteInputEditView.prepareEnterAnimator();
        }
    }

    public void enterAnimation() {
        if (this.mRouteInputEditView != null) {
            this.mRouteInputEditView.enterAnimator();
        }
    }

    private void initTabView() {
        this.mRouteTabView.setOnTabSelectedListener(new RouteZoomTabView.a() {
            public final void a(RouteType routeType) {
                RouteInputViewContainer.this.selectTab(routeType, true);
            }
        });
    }

    public void selectTab(RouteType routeType) {
        selectTab(routeType, false);
    }

    /* access modifiers changed from: private */
    public void selectTab(RouteType routeType, boolean z) {
        if (this.mRouteInputEditViewDispatcher != null && z) {
            acx acx = this.mRouteInputEditViewDispatcher;
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("route_type", routeType);
            acx.a(IRouteHeaderEvent.TAB_CLICK, pageBundle);
        }
        int tabIndex = this.mRouteTabView.getTabIndex(routeType);
        this.mRouteTabView.setSelectTab(tabIndex);
        this.mRouteInputEditView.setEnable(3, canExchange());
        if (routeType == RouteType.CAR || routeType == RouteType.TRUCK) {
            showPassViews();
        } else {
            hidePassViews();
        }
        setEditPOIEnable(true);
        this.mRouteInputEditView.setVUIExpectVisibility(routeType == RouteType.CAR ? 0 : 8);
        scrollToTab(tabIndex);
    }

    public void scrollToTab(RouteType routeType) {
        scrollToTab(this.mRouteTabView.getTabIndex(routeType));
    }

    private void scrollToTab(int i) {
        if (this.mRouteTabView != null) {
            this.mRouteTabView.scrollToTab(i);
        }
    }

    private String getTabName(RouteType routeType) {
        switch (routeType) {
            case TAXI:
                return "打车";
            case CAR:
                return "驾车";
            case BUS:
                return "公交";
            case ONFOOT:
                return "步行";
            case RIDE:
                return "骑行";
            case TRAIN:
                return "火车";
            case COACH:
                return "客车";
            case TRUCK:
                return "货车";
            case ETRIP:
                return "易行";
            case FREERIDE:
                return "顺风车";
            case AIRTICKET:
                return "飞机";
            case MOTOR:
                return "摩托车";
            default:
                return "";
        }
    }

    public void setEditPOIEnable(boolean z) {
        if (this.mEditEnable != z) {
            this.mEditEnable = z;
            setStartTextColor(getContext().getResources().getColor(z ? R.color.f_c_2 : R.color.f_c_4));
            setEndTextColor(getContext().getResources().getColor(z ? R.color.f_c_2 : R.color.f_c_4));
        }
    }

    public boolean getEditPOIEnable() {
        return this.mEditEnable;
    }

    private double getScreenSizeOfDevice() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d));
    }

    private int getTabIndex(RouteType routeType) {
        if (this.mRouteTabView != null) {
            return this.mRouteTabView.getTabIndex(routeType);
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void initInnerListener() {
        this.mRouteInputEditViewDispatcher = new acx(this);
        this.mRouteInputEditView.setOnRouteEditClickListener(this.mRouteInputEditViewDispatcher);
        this.mToolbarBtn.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (RouteInputViewContainer.this.mRouteInputEditViewDispatcher != null) {
                    RouteInputViewContainer.this.mRouteInputEditViewDispatcher.a(IRouteHeaderEvent.TOOL_BAR_CLICK, new PageBundle());
                }
                if (RouteInputViewContainer.this.mRouteTabToolBarView != null) {
                    RouteType[] currentTypes = RouteInputViewContainer.this.getCurrentTypes();
                    RouteInputViewContainer.this.mRouteTabToolBarView.bringToFront();
                    RouteInputViewContainer.this.mRouteTabToolBarView.updateRouteTypes(currentTypes);
                    RouteInputViewContainer.this.mRouteTabToolBarView.setTabHighlight(RouteInputViewContainer.this.getCurrentType(), true);
                    RouteInputViewContainer.this.updateToolbarTop();
                    RouteInputViewContainer.this.mRouteTabToolBarView.setVisibility(0);
                }
            }
        });
    }

    public void hideRouteTabToolBarView() {
        if (this.mRouteTabToolBarView != null) {
            this.mRouteTabToolBarView.setVisibility(8);
        }
    }

    public void setInputHint(String[] strArr) {
        String str = "输入起点";
        String str2 = "输入终点";
        if (strArr != null && strArr.length == 2 && !TextUtils.isEmpty(strArr[0]) && !TextUtils.isEmpty(strArr[1])) {
            str = strArr[0];
            str2 = strArr[1];
        }
        if (this.mRouteInputEditView != null) {
            this.mRouteInputEditView.setStartHint(str);
            this.mRouteInputEditView.setEndHint(str2);
        }
    }

    public void setStartView(CharSequence charSequence) {
        if (this.mRouteInputEditView != null) {
            this.mStartContent = charSequence;
            this.mRouteInputEditView.setStartText(charSequence);
        }
    }

    public String getStartViewContent() {
        if (this.mStartContent == null) {
            return "";
        }
        return this.mStartContent.toString();
    }

    public void setStartTextColor(int i) {
        if (this.mRouteInputEditView != null) {
            this.mRouteInputEditView.setTextColor(17, i);
            this.mRouteInputEditView.setTextColor(256, i);
        }
    }

    public void setEndTextColor(int i) {
        if (this.mRouteInputEditView != null) {
            this.mRouteInputEditView.setTextColor(33, i);
            this.mRouteInputEditView.setTextColor(512, i);
        }
    }

    public void setEndView(CharSequence charSequence) {
        if (this.mRouteInputEditView != null) {
            this.mEndContent = charSequence;
            this.mRouteInputEditView.setEndText(charSequence);
        }
    }

    public String getEndViewContent() {
        if (this.mEndContent == null) {
            return "";
        }
        return this.mEndContent.toString();
    }

    public void setMidView(CharSequence charSequence, CharSequence... charSequenceArr) {
        this.mRouteInputEditView.setMidTexts(charSequence, charSequenceArr);
    }

    public void exchange() {
        exchangeAnimation();
        exchangeViews();
    }

    private void exchangeAnimation() {
        if (this.mRouteInputEditView != null) {
            this.mRouteInputEditView.exchangeAnimator();
        }
    }

    private void exchangeViews() {
        if (this.mRouteInputEditView != null) {
            CharSequence startText = this.mRouteInputEditView.getStartText();
            this.mRouteInputEditView.setStartText(this.mRouteInputEditView.getEndText());
            this.mRouteInputEditView.setEndText(startText);
            CharSequence[] midTexts = this.mRouteInputEditView.getMidTexts();
            int length = midTexts != null ? midTexts.length : 0;
            CharSequence[] charSequenceArr = length != 0 ? new CharSequence[length] : null;
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    charSequenceArr[i] = midTexts[(length - 1) - i];
                }
            }
            this.mRouteInputEditView.setMidTexts(RouteHeaderModel.getMidSummaryDes((Object[]) charSequenceArr), charSequenceArr);
        }
    }

    public void setInputEventListener(axe axe) {
        if (this.mRouteInputEditViewDispatcher != null) {
            this.mRouteInputEditViewDispatcher.a = axe;
        }
    }

    public void changeStateForLevel(int i) {
        if (i == 1) {
            this.mRouteInputEditView.changeState(State.PRE_EDIT, true, null, this.mUpdateListener);
            return;
        }
        if (i == 2) {
            this.mRouteInputEditView.changeState(State.SUMMARY, true, this.mAnimatorListenerAdapter, this.mUpdateListener);
        }
    }

    public void hidePassViews() {
        if (this.mRouteInputEditView.getVisibility() != 8) {
            setPassViewVisible(8);
            this.mRouteInputEditView.setAddExpectVisibility(8);
            this.mRouteInputEditView.setMidTexts("", new CharSequence[0]);
        }
    }

    public void showPassViews() {
        this.mRouteInputEditView.setAddExpectVisibility(0);
        setPassViewVisible(8);
    }

    public boolean removeRouteInputView(int i, boolean z) {
        updatePassName();
        return false;
    }

    public boolean isAddMiddleVisibility() {
        return this.mRouteInputEditView != null && this.mRouteInputEditView.getAddExpectVisibility() == 0;
    }

    public View getInputHeaderWithShadow() {
        return this.mInputHeaderWithShadow;
    }

    public View getInputHeaderWithoutShadow() {
        return this.mInputHeaderWithoutShadow;
    }
}
