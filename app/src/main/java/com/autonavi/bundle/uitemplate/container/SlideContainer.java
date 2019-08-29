package com.autonavi.bundle.uitemplate.container;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.container.internal.RoundRectRelativeLayout;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.LayoutParams;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SlideContainer extends SlidableLayout {
    private ImageView mBtnClose;
    /* access modifiers changed from: private */
    public RoundRectRelativeLayout mContentContainer;
    private View mContentView;
    private HashMap<String, Object> mCustomAttributes;
    private View mHeadView;
    /* access modifiers changed from: private */
    public c mOnCloseButtonClickListener;
    private agl<a> mPageStateListeners;
    private View mPreloadView;
    /* access modifiers changed from: private */
    public View mShadowLayer;
    private agl<b> mTouchEventListeners;

    public interface a {
        void a(boolean z);

        void b(boolean z);
    }

    public interface b {
        void a();
    }

    public interface c {
        boolean a();
    }

    public SlideContainer(Context context) {
        this(context, null, 0);
        LayoutParams layoutParams = new LayoutParams(0);
        this.mShadowLayer = new View(context) {
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (SlideContainer.this.getPanelState() != PanelState.EXPANDED || motionEvent.getY() >= ((float) SlideContainer.this.getTransparentHeight())) {
                    return super.onTouchEvent(motionEvent);
                }
                if ((motionEvent.getAction() & 255) == 1) {
                    SlideContainer.this.setPanelState(PanelState.ANCHORED, true);
                }
                return true;
            }
        };
        this.mShadowLayer.setLayoutParams(layoutParams);
        this.mShadowLayer.setBackgroundColor(0);
        addView(this.mShadowLayer);
        addView(this.mContentContainer);
    }

    public SlideContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlideContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCustomAttributes = new HashMap<>();
        this.mPageStateListeners = new agl<>();
        this.mTouchEventListeners = new agl<>();
        init(context);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount == 1) {
            this.mPreloadView = getChildAt(0);
        } else if (childCount >= 2) {
            this.mHeadView = getChildAt(0);
            this.mPreloadView = getChildAt(1);
        }
        removeAllViews();
        addView(new View(getContext()));
        addView(this.mContentContainer);
        if (this.mHeadView != null) {
            setHeadView(this.mHeadView);
        }
        if (this.mPreloadView != null) {
            setPreloadView(this.mPreloadView);
        }
    }

    public void setOnCloseButtonClickListener(c cVar) {
        this.mOnCloseButtonClickListener = cVar;
    }

    private void init(Context context) {
        this.mBtnClose = new ImageButton(context);
        this.mBtnClose.setScaleType(ScaleType.CENTER);
        this.mBtnClose.setImageResource(R.drawable.btn_slide_close);
        this.mBtnClose.setBackgroundColor(0);
        this.mBtnClose.setId(R.id.slidecontainer_slideclose);
        this.mContentContainer = new RoundRectRelativeLayout(getContext());
        this.mContentContainer.setLayoutParams(new LayoutParams(0));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(bet.a(getContext(), 44), bet.a(getContext(), 44));
        layoutParams.addRule(10);
        layoutParams.addRule(11);
        this.mBtnClose.setPadding(bet.a(getContext(), 10), bet.a(getContext(), 10), bet.a(getContext(), 10), bet.a(getContext(), 10));
        layoutParams.topMargin = bet.a(getContext(), 22);
        layoutParams.rightMargin = bet.a(getContext(), 12);
        this.mContentContainer.addView(this.mBtnClose, layoutParams);
        setTransparentHeight(bet.a(getContext(), 0));
        setMinHeight(bet.a(getContext(), 80));
        setAnchorHeight(bet.a(getContext(), AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST));
        addPanelSlideListener(new com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.c() {
            public final void a(View view, PanelState panelState, PanelState panelState2) {
            }

            public final void a(View view, float f) {
                SlideContainer.this.mContentContainer.bending(f, SlideContainer.this.getAnchorPoint());
                int anchorPoint = (int) ((102.0f / (1.0f - SlideContainer.this.getAnchorPoint())) + 0.5f);
                int anchorPoint2 = (int) (((f - (SlideContainer.this.getAnchorPoint() * 2.0f)) * ((float) anchorPoint)) + 0.5f);
                if (anchorPoint2 < 0) {
                    anchorPoint = 0;
                } else if (anchorPoint2 <= anchorPoint) {
                    anchorPoint = anchorPoint2;
                }
                SlideContainer.this.mShadowLayer.setBackgroundColor(Color.argb(anchorPoint, 0, 0, 0));
            }
        });
        this.mBtnClose.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (SlideContainer.this.mOnCloseButtonClickListener == null || !SlideContainer.this.mOnCloseButtonClickListener.a()) {
                    SlideContainer.this.setPanelState(PanelState.COLLAPSED, true);
                }
            }
        });
    }

    public void setMinHeight(int i) {
        int a2 = i + bet.a(getContext(), 10);
        super.setMinHeight(a2);
        int height = getHeight();
        if (height != 0) {
            smoothSlideTo((float) (a2 / height), 0);
        }
    }

    public void setAnchorHeight(int i) {
        super.setAnchorHeight(i + bet.a(getContext(), 10));
    }

    public void setHeadView(View view) {
        this.mContentContainer.removeView(this.mHeadView);
        if (view != null) {
            this.mHeadView = view;
            this.mHeadView.setId(R.id.slidecontainer_header);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mHeadView.getLayoutParams();
            if (marginLayoutParams == null) {
                marginLayoutParams = new MarginLayoutParams(-1, -2);
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginLayoutParams);
            layoutParams.addRule(10);
            layoutParams.topMargin = bet.a(getContext(), 26);
            this.mContentContainer.addView(this.mHeadView, layoutParams);
            this.mBtnClose.bringToFront();
        }
    }

    public void setPreloadView(View view) {
        this.mContentContainer.removeView(this.mPreloadView);
        if (view != null) {
            this.mPreloadView = view;
            this.mPreloadView.setId(R.id.slidecontainer_preloader);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(3, R.id.slidecontainer_header);
            this.mContentContainer.addView(this.mPreloadView, layoutParams);
            this.mBtnClose.bringToFront();
        }
    }

    public View getPreloadView() {
        return this.mPreloadView;
    }

    public View getHeadView() {
        return this.mHeadView;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void setContentView(View view) {
        this.mContentContainer.removeView(this.mContentView);
        if (view != null) {
            this.mContentView = view;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.topMargin = bet.a(getContext(), 14) + 1;
            this.mContentView.setLayoutParams(layoutParams);
            this.mContentContainer.addView(this.mContentView, 0, layoutParams);
            this.mBtnClose.bringToFront();
        }
    }

    public void setContentBackgroundColor(int i) {
        this.mContentContainer.setBackgroundColor(i);
    }

    public void hideDragBar() {
        this.mContentContainer.setShowDragBar(false);
    }

    public void showDragBar() {
        this.mContentContainer.setShowDragBar(true);
    }

    public void hideCloseButton() {
        this.mBtnClose.setVisibility(8);
    }

    public void showCloseButton() {
        this.mBtnClose.setVisibility(0);
    }

    public void onPageShow(final boolean z) {
        if (this.mPageStateListeners != null) {
            this.mPageStateListeners.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                public final /* synthetic */ void onNotify(Object obj) {
                    ((a) obj).a(z);
                }
            });
        }
    }

    public void onPageHide(final boolean z) {
        if (this.mPageStateListeners != null) {
            this.mPageStateListeners.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                public final /* synthetic */ void onNotify(Object obj) {
                    ((a) obj).b(z);
                }
            });
        }
    }

    public void addPageStateListener(a aVar) {
        this.mPageStateListeners.a(aVar);
    }

    public void removePageStateListener(a aVar) {
        this.mPageStateListeners.b(aVar);
    }

    public void setTransparentHeight(int i) {
        super.setTransparentHeight(i + euk.a(getContext()));
    }

    public void addTouchEventListener(b bVar) {
        this.mTouchEventListeners.a(bVar);
    }

    public void removeTouchEventListener(b bVar) {
        this.mTouchEventListeners.b(bVar);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (isDropTouchEvent()) {
            return true;
        }
        this.mTouchEventListeners.a((defpackage.agl.a<T>) new defpackage.agl.a<b>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((b) obj).a();
            }
        });
        return super.dispatchTouchEvent(motionEvent);
    }

    private boolean isDropTouchEvent() {
        boolean z;
        ArrayList<bid> pageContextStacks = AMapPageUtil.getPageContextStacks();
        if (pageContextStacks == null) {
            return false;
        }
        Iterator<bid> it = pageContextStacks.iterator();
        while (true) {
            if (it.hasNext()) {
                if (SearchCQDetailPage.a.equals(it.next().getClass().getSimpleName())) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (!z || pageContextStacks.size() < 2) {
            return false;
        }
        return true;
    }

    public void setCloseButtonVisible(boolean z) {
        if (z) {
            this.mBtnClose.setVisibility(0);
        } else {
            this.mBtnClose.setVisibility(8);
        }
    }

    public void setArrowVisible(boolean z) {
        this.mContentContainer.setShowDragBar(z);
    }

    public void setCustomAttribute(String str, Object obj) {
        if (!TextUtils.isEmpty(str)) {
            this.mCustomAttributes.put(str, obj);
        }
    }

    public Object getCustomAttribute(String str) {
        if (!TextUtils.isEmpty(str)) {
            return this.mCustomAttributes.get(str);
        }
        return null;
    }
}
