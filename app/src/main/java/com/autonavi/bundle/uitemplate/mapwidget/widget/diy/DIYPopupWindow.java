package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.annotation.MainMapInvokePriority;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DragRecyclerView.OnItemClickListener;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.diy.DIYMainMapEntry;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import java.lang.ref.WeakReference;
import java.util.List;

public class DIYPopupWindow {
    public static final int POP_WINDOW_ANIMATION_DURATION = 200;
    private static final int POP_WINDOW_ANIMATION_DURATION_RESPONSE = 300;
    private int BLACK_LIGHT;
    private int BUTTON_PADDING;
    private int BUTTON_SIZE;
    private int MAIN_ENTRY_ITEM_HEIGHT;
    private int MAIN_ENTRY_ITEM_PADDING;
    private int MAIN_ENTRY_MORE_HEIGHT;
    private int PADDING;
    private int POP_WINDOW_BOTTOM_HEIGHT;
    private int POP_WINDOW_ITEM_HEIGHT;
    private int POP_WINDOW_OTHER_HEIGHT;
    private int TRANSPARENT;
    private int X_OFFSET;
    private int Y_OFFSET;
    /* access modifiers changed from: private */
    public AnimationListener mAnimationListener;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public DragRecyclerView mDragRecyclerView;
    /* access modifiers changed from: private */
    public List<DIYMainMapEntry> mEntries;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public LinearLayout mMainView;
    /* access modifiers changed from: private */
    public int mMainViewMarginTop;
    /* access modifiers changed from: private */
    public czs mPageBackPressListener = new OnBackPressedListener(this);
    /* access modifiers changed from: private */
    public View mRootView;
    /* access modifiers changed from: private */
    public View mView;
    /* access modifiers changed from: private */
    public DIYPopupWindowEventListener mWindowEventListener;
    /* access modifiers changed from: private */
    public PopupView popupView;
    /* access modifiers changed from: private */
    public int yOffset = 0;

    public interface DIYPopupWindowEventListener {
        void dismissPopupWindow(List<DIYMainMapEntry> list);

        void showPopupWindow(List<DIYMainMapEntry> list);
    }

    public static class OnBackPressedListener implements czs {
        private WeakReference<DIYPopupWindow> mHost;

        public OnBackPressedListener(DIYPopupWindow dIYPopupWindow) {
            this.mHost = new WeakReference<>(dIYPopupWindow);
        }

        @MainMapInvokePriority(3.0f)
        public ON_BACK_TYPE onBackPressed() {
            DIYPopupWindow dIYPopupWindow = (DIYPopupWindow) this.mHost.get();
            if (dIYPopupWindow != null) {
                return dIYPopupWindow.onBackPressed();
            }
            return null;
        }
    }

    class PopupView {
        private boolean isDismissing = false;
        private boolean isShowing = false;

        public PopupView() {
            DIYPopupWindow.this.mRootView = LayoutInflater.from(DIYPopupWindow.this.mContext).inflate(R.layout.layout_diy_main_map_pop_view, null);
            DIYPopupWindow.this.mMainView = (LinearLayout) DIYPopupWindow.this.mRootView.findViewById(R.id.ll_main_view);
            DIYPopupWindow.this.mDragRecyclerView = (DragRecyclerView) DIYPopupWindow.this.mRootView.findViewById(R.id.rv_recycler_view);
            DIYPopupWindow.this.mMainViewMarginTop = ((LayoutParams) DIYPopupWindow.this.mMainView.getLayoutParams()).topMargin;
            DIYPopupWindow.this.mRootView.setFocusable(true);
            DIYPopupWindow.this.mRootView.setOnClickListener(new OnClickListener(DIYPopupWindow.this) {
                public void onClick(View view) {
                    PopupView.this.dismiss(true);
                }
            });
            DIYPopupWindow.this.mMainView.setOnClickListener(new OnClickListener(DIYPopupWindow.this) {
                public void onClick(View view) {
                }
            });
        }

        public void show() {
            ViewGroup viewGroup = (ViewGroup) AMapPageUtil.getPageContext().getContentView().getParent();
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            if (DIYPopupWindow.this.popupView.getView().getParent() != null) {
                ((ViewGroup) DIYPopupWindow.this.popupView.getView().getParent()).removeView(DIYPopupWindow.this.popupView.getView());
            }
            viewGroup.addView(DIYPopupWindow.this.popupView.getView(), layoutParams);
            ((IMainMapService) ank.a(IMainMapService.class)).a((Object) DIYPopupWindow.this.mPageBackPressListener);
            this.isShowing = true;
        }

        public void dismiss(boolean z) {
            if (DIYPopupWindow.this.mWindowEventListener != null) {
                DIYPopupWindow.this.mWindowEventListener.dismissPopupWindow(DIYPopupWindow.this.mEntries);
            }
            if (!z) {
                doDismiss();
            } else if (!this.isDismissing) {
                this.isDismissing = true;
                AnimationSet animationSet = new AnimationSet(true);
                ScaleAnimation access$1100 = DIYPopupWindow.this.getDismissAnimation();
                access$1100.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                        if (DIYPopupWindow.this.mAnimationListener != null) {
                            DIYPopupWindow.this.mAnimationListener.onAnimationStart(animation);
                        }
                    }

                    public void onAnimationEnd(Animation animation) {
                        if (DIYPopupWindow.this.mAnimationListener != null) {
                            DIYPopupWindow.this.mAnimationListener.onAnimationEnd(animation);
                            DIYPopupWindow.this.mAnimationListener = null;
                        }
                        PopupView.this.doDismiss();
                    }

                    public void onAnimationRepeat(Animation animation) {
                        if (DIYPopupWindow.this.mAnimationListener != null) {
                            DIYPopupWindow.this.mAnimationListener.onAnimationRepeat(animation);
                        }
                    }
                });
                animationSet.addAnimation(access$1100);
                animationSet.addAnimation(DIYPopupWindow.this.getAlphaAnimation(false));
                DIYPopupWindow.this.mMainView.startAnimation(animationSet);
            }
        }

        /* access modifiers changed from: private */
        public void doDismiss() {
            ((ViewGroup) AMapPageUtil.getPageContext().getContentView().getParent()).removeView(DIYPopupWindow.this.popupView.getView());
            ((IMainMapService) ank.a(IMainMapService.class)).b(DIYPopupWindow.this.mPageBackPressListener);
            this.isDismissing = false;
            this.isShowing = false;
        }

        public void dismiss() {
            dismiss(false);
        }

        public boolean isShowing() {
            return this.isShowing;
        }

        public View getView() {
            return DIYPopupWindow.this.mRootView;
        }
    }

    public DIYPopupWindow(Context context) {
        this.mContext = context;
        this.popupView = new PopupView();
        this.POP_WINDOW_ITEM_HEIGHT = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_item_height);
        this.POP_WINDOW_OTHER_HEIGHT = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_other_height);
        this.POP_WINDOW_BOTTOM_HEIGHT = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_bottom_height);
        this.MAIN_ENTRY_ITEM_HEIGHT = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_item_height);
        this.MAIN_ENTRY_MORE_HEIGHT = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_more_height);
        this.MAIN_ENTRY_ITEM_PADDING = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_item_padding);
        this.MAIN_ENTRY_ITEM_HEIGHT += this.MAIN_ENTRY_ITEM_PADDING;
        this.X_OFFSET = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_x_offset);
        this.Y_OFFSET = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_padding_top_bottom);
        this.Y_OFFSET += this.MAIN_ENTRY_ITEM_PADDING;
        this.BUTTON_SIZE = this.mContext.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        this.BUTTON_PADDING = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_item_padding);
        this.PADDING = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_padding_left_right);
        this.TRANSPARENT = this.mContext.getResources().getColor(R.color.c_t);
        this.BLACK_LIGHT = this.mContext.getResources().getColor(R.color.c_5_b);
    }

    public void attachView(View view) {
        this.mView = view;
        if (this.mView != null) {
            this.mView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                Rect mRect;

                public void onGlobalLayout() {
                    Rect rect = new Rect();
                    DIYPopupWindow.this.mView.getGlobalVisibleRect(rect);
                    if (this.mRect != null && !rect.isEmpty()) {
                        int i = rect.top - this.mRect.top;
                        if (i != 0 && Math.abs(i) <= 200) {
                            DIYPopupWindow.this.yOffset = DIYPopupWindow.this.yOffset + i;
                            this.mRect = rect;
                        } else {
                            return;
                        }
                    }
                    if (this.mRect == null) {
                        this.mRect = rect;
                    }
                    if (DIYPopupWindow.this.mMainView != null) {
                        LayoutParams layoutParams = (LayoutParams) DIYPopupWindow.this.mMainView.getLayoutParams();
                        layoutParams.topMargin = DIYPopupWindow.this.mMainViewMarginTop + DIYPopupWindow.this.yOffset;
                        DIYPopupWindow.this.mMainView.setLayoutParams(layoutParams);
                    }
                }
            });
        }
    }

    public ON_BACK_TYPE onBackPressed() {
        this.popupView.dismiss(true);
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public void setEntries(List<DIYMainMapEntry> list, OnItemClickListener onItemClickListener) {
        this.mEntries = list;
        this.mDragRecyclerView.setEntries(list, onItemClickListener);
        ViewGroup.LayoutParams layoutParams = this.mDragRecyclerView.getLayoutParams();
        layoutParams.height = this.POP_WINDOW_ITEM_HEIGHT * (((list.size() - 1) / 3) + 1);
        int height = ((WindowManager) this.mContext.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getHeight() - this.POP_WINDOW_OTHER_HEIGHT;
        if (layoutParams.height > height) {
            layoutParams.height = height;
        }
        this.mDragRecyclerView.setLayoutParams(layoutParams);
    }

    public void notifyDataSetChanged() {
        this.mDragRecyclerView.refresh();
    }

    private void showAtLocation() {
        loadColorAnimation(this.mRootView, true);
        float f = (float) (this.mMainView.getLayoutParams().width - this.X_OFFSET);
        float size = (float) ((this.MAIN_ENTRY_ITEM_HEIGHT * ((this.mEntries == null || this.mEntries.size() >= 3) ? 3 : this.mEntries.size())) + this.Y_OFFSET);
        ScaleAnimation scaleAnimation = new ScaleAnimation(((float) (this.BUTTON_SIZE - (this.BUTTON_PADDING * 2))) / ((float) (this.mMainView.getLayoutParams().width - (this.PADDING * 2))), 1.05f, ((float) this.MAIN_ENTRY_MORE_HEIGHT) / ((float) ((this.POP_WINDOW_ITEM_HEIGHT * (((this.mEntries.size() - 1) / 3) + 1)) + this.POP_WINDOW_BOTTOM_HEIGHT)), 1.05f, 0, f, 0, size);
        scaleAnimation.setDuration(200);
        scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.05f, 0.94f, 1.05f, 0.94f, f, size);
        scaleAnimation2.setDuration(300);
        scaleAnimation2.setInterpolator(new FastOutSlowInInterpolator());
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(scaleAnimation2);
        animationSet.addAnimation(getAlphaAnimation(true));
        animationSet.setFillAfter(true);
        this.mMainView.startAnimation(animationSet);
        this.popupView.show();
    }

    public boolean isShowing() {
        return this.popupView.isShowing();
    }

    public void dismiss(AnimationListener animationListener) {
        this.mAnimationListener = animationListener;
        if (this.popupView != null && this.popupView.isShowing()) {
            this.popupView.dismiss(true);
        }
    }

    public void dismiss() {
        if (this.popupView != null && this.popupView.isShowing()) {
            this.popupView.dismiss(false);
        }
    }

    /* access modifiers changed from: private */
    public ScaleAnimation getDismissAnimation() {
        loadColorAnimation(this.mRootView, false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, ((float) (this.BUTTON_SIZE - (this.BUTTON_PADDING * 2))) / ((float) (this.mMainView.getLayoutParams().width - (this.PADDING * 2))), 1.0f, ((float) this.MAIN_ENTRY_MORE_HEIGHT) / ((float) ((this.POP_WINDOW_ITEM_HEIGHT * (((this.mEntries.size() - 1) / 3) + 1)) + this.POP_WINDOW_BOTTOM_HEIGHT)), 0, (float) (this.mMainView.getLayoutParams().width - this.X_OFFSET), 0, (float) ((this.MAIN_ENTRY_ITEM_HEIGHT * (this.mEntries.size() < 3 ? this.mEntries.size() : 3)) + this.Y_OFFSET));
        scaleAnimation.setDuration(200);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        return scaleAnimation;
    }

    public void showAsDropDown() {
        if (this.mView != null) {
            if (this.mWindowEventListener != null) {
                this.mWindowEventListener.showPopupWindow(this.mEntries);
            }
            showAtLocation();
        }
    }

    private void loadColorAnimation(View view, boolean z) {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(view, "backgroundColor", new int[]{z ? this.TRANSPARENT : this.BLACK_LIGHT, z ? this.BLACK_LIGHT : this.TRANSPARENT});
        ofInt.setDuration(200);
        ofInt.setInterpolator(new DecelerateInterpolator());
        ofInt.setEvaluator(new ArgbEvaluator());
        ofInt.start();
    }

    /* access modifiers changed from: private */
    public Animation getAlphaAnimation(boolean z) {
        float f = 1.0f;
        float f2 = z ? 0.0f : 1.0f;
        if (!z) {
            f = 0.0f;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

    public void setWindowEventListener(DIYPopupWindowEventListener dIYPopupWindowEventListener) {
        this.mWindowEventListener = dIYPopupWindowEventListener;
    }
}
