package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.tips.TipInfo;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.Arrays;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartTipsManager implements OnLayoutChangeListener, OnGlobalLayoutListener {
    private static final String AMAP_TIPS_SP_KEY = "amap_tips_sp_key";
    private static final String AMAP_TIPS_SP_KEY_TIMES_SUFFIX = "_times";
    private static final String AMAP_TIPS_SP_KEY_TIME_SUFFIX = "_time";
    private static final long ONE_DAY = 86400000;
    private static final long SHOW_TIP_DELAY_INTERVAL = 500;
    private AutoDismissTipsRunnable mAutoDismissTipsRunnable;
    private ViewGroup mContainer;
    private View mContentView;
    /* access modifiers changed from: private */
    public TipInfo mCurrentTipInfo;
    private Handler mHandler;
    private boolean mIsPause;
    /* access modifiers changed from: private */
    public boolean mIsShowing;
    /* access modifiers changed from: private */
    public boolean mIsShown;
    private HashMap<String, TipInfo> mRegisteredTipInfosMap;
    private ViewGroup mRootContainer;
    private ShowRunnable mShowRunnable;
    /* access modifiers changed from: private */
    public ITipShowJudger mTipShowJudger;
    private final String[] mTipsRuleArray;
    private ViewTreeObserver mViewTreeObserver;
    private int mXPosition;
    private int mYPosition;

    class AutoDismissTipsRunnable implements Runnable {
        private AutoDismissTipsRunnable() {
        }

        public void run() {
            SmartTipsManager.this.dismiss();
        }
    }

    public interface ITipShowJudger {
        boolean canShow();
    }

    class ShowRunnable implements Runnable {
        private ShowRunnable() {
        }

        public void run() {
            if (SmartTipsManager.this.mTipShowJudger == null || SmartTipsManager.this.mTipShowJudger.canShow()) {
                SmartTipsManager.this.realShow(SmartTipsManager.this.mCurrentTipInfo);
                SmartTipsManager.this.mIsShowing = true;
                SmartTipsManager.this.mIsShown = true;
            }
        }
    }

    public SmartTipsManager(ViewGroup viewGroup) {
        this(viewGroup, R.array.main_tips_all);
    }

    public void setTipShowJudger(ITipShowJudger iTipShowJudger) {
        this.mTipShowJudger = iTipShowJudger;
    }

    public SmartTipsManager(ViewGroup viewGroup, int i) {
        this.mRegisteredTipInfosMap = new HashMap<>();
        this.mIsPause = false;
        this.mRootContainer = viewGroup;
        this.mTipsRuleArray = viewGroup.getResources().getStringArray(i);
        this.mHandler = new Handler(viewGroup.getContext().getMainLooper());
        this.mShowRunnable = new ShowRunnable();
        this.mAutoDismissTipsRunnable = new AutoDismissTipsRunnable();
    }

    public void pause() {
        this.mIsPause = true;
        dismiss();
    }

    public void resume() {
        this.mIsPause = false;
    }

    public boolean isPause() {
        return this.mIsPause;
    }

    public void show() {
        String[] strArr;
        if (!this.mIsPause && !this.mIsShowing && this.mRegisteredTipInfosMap.size() != 0) {
            for (String str : this.mTipsRuleArray) {
                if (this.mRegisteredTipInfosMap.containsKey(str)) {
                    TipInfo tipInfo = this.mRegisteredTipInfosMap.get(str);
                    if (needShow(str)) {
                        prepareToShow(tipInfo);
                        return;
                    }
                }
            }
        }
    }

    public void forceShow(String str, boolean z) {
        if (!this.mIsPause) {
            TipInfo tipInfo = this.mRegisteredTipInfosMap.get(str);
            if (tipInfo != null) {
                if (!z || needShow(str)) {
                    if (this.mIsShowing) {
                        if (this.mCurrentTipInfo == null || Arrays.binarySearch(this.mTipsRuleArray, str) > Arrays.binarySearch(this.mTipsRuleArray, this.mCurrentTipInfo.a)) {
                            dismiss();
                        } else {
                            return;
                        }
                    }
                    prepareToShow(tipInfo);
                }
            }
        }
    }

    private void prepareToShow(TipInfo tipInfo) {
        this.mCurrentTipInfo = tipInfo;
        this.mHandler.removeCallbacks(this.mShowRunnable);
        this.mHandler.postDelayed(this.mShowRunnable, 500);
    }

    public void forceAdd(TipInfo tipInfo) {
        this.mRegisteredTipInfosMap.put(tipInfo.a, tipInfo);
    }

    public void add(TipInfo tipInfo) {
        if (!this.mIsShown) {
            this.mRegisteredTipInfosMap.put(tipInfo.a, tipInfo);
        }
    }

    public void dismiss() {
        this.mHandler.removeCallbacks(this.mShowRunnable);
        if (this.mIsShowing) {
            Runnable runnable = this.mCurrentTipInfo.n;
            if (runnable != null) {
                runnable.run();
            }
            removeOnGlobalLayoutListener();
            this.mCurrentTipInfo.b.removeOnLayoutChangeListener(this);
            this.mHandler.removeCallbacks(this.mAutoDismissTipsRunnable);
            this.mCurrentTipInfo = null;
            this.mIsShowing = false;
            this.mRootContainer.removeView(this.mContainer);
            this.mContainer = null;
            this.mContentView = null;
            this.mRegisteredTipInfosMap.clear();
        }
    }

    public void reset() {
        this.mIsShown = false;
    }

    public void onTargetClick(String str) {
        setTipDontShow(str);
    }

    public boolean needShow(String str) {
        SharedPreferences sharedPreferences = this.mRootContainer.getContext().getSharedPreferences(AMAP_TIPS_SP_KEY, 0);
        if (!sharedPreferences.getBoolean(str, false)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(AMAP_TIPS_SP_KEY_TIME_SUFFIX);
            if (System.currentTimeMillis() - sharedPreferences.getLong(sb.toString(), 0) > 86400000) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void realShow(final TipInfo tipInfo) {
        if (!this.mIsShowing) {
            recordTipShow(tipInfo);
            this.mContainer = new FrameLayout(this.mRootContainer.getContext());
            this.mContainer.setLayoutParams(new LayoutParams(-1, -1));
            if (tipInfo.c != null) {
                this.mContentView = tipInfo.c;
            } else {
                this.mContentView = createContent(this.mRootContainer.getContext(), tipInfo);
            }
            if (tipInfo.e == 0 || tipInfo.d == 0) {
                measure(this.mContentView, tipInfo);
            }
            this.mContainer.addView(this.mContentView, tipInfo.d, tipInfo.e);
            this.mContainer.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    SmartTipsManager.this.dismiss();
                    return false;
                }
            });
            this.mContentView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SmartTipsManager.this.setTipDontShow(tipInfo.a);
                    Runnable runnable = tipInfo.j;
                    if (runnable != null) {
                        runnable.run();
                    } else {
                        tipInfo.b.performClick();
                    }
                    SmartTipsManager.this.dismiss();
                }
            });
            this.mViewTreeObserver = tipInfo.b.getViewTreeObserver();
            this.mViewTreeObserver.addOnGlobalLayoutListener(this);
            showAtLocation();
            this.mRootContainer.addView(this.mContainer);
            this.mHandler.postDelayed(this.mAutoDismissTipsRunnable, (long) tipInfo.m);
            if (!TextUtils.isEmpty(tipInfo.l)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("name", tipInfo.l);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_SMART_TIPS_SHOW, jSONObject);
            }
        }
    }

    @TargetApi(16)
    private void removeOnGlobalLayoutListener() {
        if (this.mViewTreeObserver != null && this.mViewTreeObserver.isAlive()) {
            this.mViewTreeObserver.removeOnGlobalLayoutListener(this);
        }
    }

    private void measure(View view, TipInfo tipInfo) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        int measuredHeight = view.getMeasuredHeight();
        tipInfo.d = view.getMeasuredWidth();
        tipInfo.e = measuredHeight;
    }

    private void showAtLocation() {
        this.mCurrentTipInfo.b.removeOnLayoutChangeListener(this);
        this.mCurrentTipInfo.b.addOnLayoutChangeListener(this);
        calculateNewPosition();
        this.mContentView.setTranslationX((float) this.mXPosition);
        this.mContentView.setTranslationY((float) this.mYPosition);
    }

    private void calculateNewPosition() {
        TipInfo tipInfo = this.mCurrentTipInfo;
        Rect rect = new Rect();
        tipInfo.b.getGlobalVisibleRect(rect);
        Rect rect2 = new Rect();
        this.mContainer.getGlobalVisibleRect(rect2);
        switch (tipInfo.h) {
            case LEFT:
                switch (tipInfo.i) {
                    case TOP:
                        this.mXPosition = ((rect.left - tipInfo.d) - tipInfo.f) - rect2.left;
                        this.mYPosition = rect.top - rect2.top;
                        break;
                    case BOTTOM:
                        this.mXPosition = ((rect.left - tipInfo.d) - tipInfo.f) - rect2.left;
                        this.mYPosition = (rect.bottom - tipInfo.e) - rect2.top;
                        break;
                    case CENTER:
                        this.mXPosition = ((rect.left - tipInfo.d) - tipInfo.f) - rect2.left;
                        this.mYPosition = (rect.centerY() - (tipInfo.e / 2)) - rect2.top;
                        break;
                }
            case RIGHT:
                switch (tipInfo.i) {
                    case TOP:
                        this.mXPosition = (rect.right + tipInfo.f) - rect2.left;
                        this.mYPosition = rect.top - rect2.top;
                        break;
                    case BOTTOM:
                        this.mXPosition = (rect.right + tipInfo.f) - rect2.left;
                        this.mYPosition = (rect.bottom - tipInfo.e) - rect2.top;
                        break;
                    case CENTER:
                        this.mXPosition = (rect.right + tipInfo.f) - rect2.left;
                        this.mYPosition = (rect.centerY() - (tipInfo.e / 2)) - rect2.top;
                        break;
                }
            case TOP:
                switch (tipInfo.i) {
                    case CENTER:
                        this.mXPosition = (rect.centerX() - (tipInfo.d / 2)) - rect2.left;
                        this.mYPosition = ((rect.top - tipInfo.e) - tipInfo.f) - rect2.top;
                        break;
                    case LEFT:
                        this.mXPosition = rect.left - rect2.left;
                        this.mYPosition = ((rect.top - tipInfo.e) - tipInfo.f) - rect2.top;
                        break;
                    case RIGHT:
                        this.mXPosition = (rect.right - tipInfo.d) - rect2.left;
                        this.mYPosition = ((rect.top - tipInfo.e) - tipInfo.f) - rect2.top;
                        break;
                }
            case BOTTOM:
                switch (tipInfo.i) {
                    case CENTER:
                        this.mXPosition = (rect.centerX() - (tipInfo.d / 2)) - rect2.left;
                        this.mYPosition = ((rect.top + tipInfo.e) + tipInfo.f) - rect2.top;
                        break;
                    case LEFT:
                        this.mXPosition = rect.left - rect2.left;
                        this.mYPosition = ((rect.top + tipInfo.e) + tipInfo.f) - rect2.top;
                        break;
                    case RIGHT:
                        this.mXPosition = (rect.right - tipInfo.d) - rect2.left;
                        this.mYPosition = ((rect.top + tipInfo.e) + tipInfo.f) - rect2.top;
                        break;
                }
        }
        if (this.mXPosition < 0) {
            this.mXPosition = 0;
        }
        if (this.mYPosition < 0) {
            this.mYPosition = 0;
        }
        if (this.mXPosition + tipInfo.d > rect2.right) {
            this.mXPosition = rect2.right - tipInfo.d;
        }
        if (this.mYPosition + tipInfo.e > rect2.bottom) {
            this.mYPosition = rect2.bottom - tipInfo.e;
        }
    }

    private View createContent(Context context, TipInfo tipInfo) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ScaleType.FIT_XY);
        imageView.setImageResource(tipInfo.g);
        return imageView;
    }

    private boolean updatePosition() {
        int i = this.mXPosition;
        int i2 = this.mYPosition;
        calculateNewPosition();
        return (i == this.mXPosition && i2 == this.mYPosition) ? false : true;
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.mCurrentTipInfo != null) {
            boolean z = !this.mCurrentTipInfo.b.isShown();
            boolean updatePosition = updatePosition();
            if (z) {
                dismiss();
                return;
            }
            if (updatePosition) {
                showAtLocation();
            }
        }
    }

    private void recordTipShow(TipInfo tipInfo) {
        SharedPreferences sharedPreferences = this.mRootContainer.getContext().getSharedPreferences(AMAP_TIPS_SP_KEY, 0);
        if (!sharedPreferences.getBoolean(tipInfo.a, false)) {
            StringBuilder sb = new StringBuilder();
            sb.append(tipInfo.a);
            sb.append(AMAP_TIPS_SP_KEY_TIMES_SUFFIX);
            int i = sharedPreferences.getInt(sb.toString(), 0);
            Editor edit = sharedPreferences.edit();
            if (i < tipInfo.k) {
                i++;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(tipInfo.a);
                sb2.append(AMAP_TIPS_SP_KEY_TIMES_SUFFIX);
                edit.putInt(sb2.toString(), i);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(tipInfo.a);
                sb3.append(AMAP_TIPS_SP_KEY_TIME_SUFFIX);
                edit.putLong(sb3.toString(), System.currentTimeMillis());
            }
            if (i >= tipInfo.k) {
                edit.putBoolean(tipInfo.a, true);
            }
            edit.apply();
        }
    }

    /* access modifiers changed from: private */
    public void setTipDontShow(String str) {
        Editor edit = this.mRootContainer.getContext().getSharedPreferences(AMAP_TIPS_SP_KEY, 0).edit();
        edit.putBoolean(str, true);
        edit.apply();
    }

    public void onGlobalLayout() {
        if (this.mIsShowing && this.mCurrentTipInfo != null) {
            boolean z = !this.mCurrentTipInfo.b.isShown();
            boolean updatePosition = updatePosition();
            if (z) {
                dismiss();
                return;
            }
            if (updatePosition) {
                showAtLocation();
            }
        }
    }
}
