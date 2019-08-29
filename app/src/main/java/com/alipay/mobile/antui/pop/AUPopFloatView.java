package com.alipay.mobile.antui.pop;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.utils.AUStatusBarUtil;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.antui.utils.ToolUtils;
import java.lang.ref.WeakReference;

public class AUPopFloatView extends AULinearLayout implements OnTouchListener {
    private static final int MAX_CLICK_DISTANCE = 2;
    private static final long MAX_CLICK_DURATION = 200;
    private static final String TAG = "AUPopFloatView";
    private WeakReference<Activity> currentActivity;
    private int currentY = 0;
    private int deltaX;
    private int deltaY;
    private AUPopFloatEventListener eventListener;
    private LayoutParams layoutParams;
    private float pressDownX;
    private float pressDownY;
    private int removeDistance;
    private int screenHeight;
    private boolean stayedWithinClickDistance;
    private AUTextView titleTextView;
    private long touchDownTimestamp;

    public interface AUPopFloatEventListener {
        void onClick(View view);

        void onRemove(View view);
    }

    public AUPopFloatView(Context context) {
        super(context);
        init(context);
    }

    public AUPopFloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUPopFloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AUPopFloatView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(0);
        setGravity(16);
        setBackgroundResource(R.drawable.au_pop_float_bg);
        LayoutInflater.from(context).inflate(R.layout.au_pop_float_view, this, true);
        this.screenHeight = ToolUtils.getScreenWidth_Height(context)[1];
        this.currentY = this.screenHeight - getResources().getDimensionPixelOffset(R.dimen.au_pop_float_view_default_position_y);
        this.removeDistance = getResources().getDimensionPixelOffset(R.dimen.au_pop_float_view_to_remove_distance);
        this.titleTextView = (AUTextView) findViewById(R.id.title);
        this.layoutParams = new LayoutParams(-2, -2);
        this.layoutParams.gravity = 3;
        this.layoutParams.topMargin = this.currentY;
        setOnTouchListener(this);
    }

    public void setTitle(CharSequence title) {
        this.titleTextView.setText(title);
    }

    public void attachToActivity(Activity activity) {
        if (activity == null) {
            AuiLogger.error(TAG, "attachToActivity:activity is null");
        } else if (this.currentActivity == null || this.currentActivity.get() != activity) {
            try {
                View decorView = activity.getWindow().getDecorView();
                if (!(decorView instanceof FrameLayout) || getParent() == decorView) {
                    AuiLogger.debug(TAG, "attachToActivity操作不成功，activity视图未正常初始化：" + activity);
                    return;
                }
                removeFromParent();
                ((FrameLayout) decorView).addView(this, this.layoutParams);
                bringToFront();
                AuiLogger.debug(TAG, "attachToActivity:" + activity);
                this.currentActivity = new WeakReference<>(activity);
            } catch (Exception ex) {
                AuiLogger.error(TAG, "attachToActivity出错：" + ex.getLocalizedMessage());
            }
        } else {
            AuiLogger.debug(TAG, "attachToActivity,已经添加到指定Activity:" + activity);
        }
    }

    public Activity getCurrentActivity() {
        if (this.currentActivity != null) {
            return (Activity) this.currentActivity.get();
        }
        return null;
    }

    public void removeFromParent() {
        try {
            ViewParent parent = getParent();
            if (parent instanceof ViewGroup) {
                AuiLogger.debug(TAG, "removeFromParent:" + ((ViewGroup) parent).getContext());
                ((ViewGroup) parent).removeView(this);
            }
            this.currentActivity = null;
        } catch (Exception ex) {
            AuiLogger.error(TAG, "attachToActivity出错：" + ex.getLocalizedMessage());
        }
    }

    @Deprecated
    public void setOnClickListener(OnClickListener listener) {
        AuiLogger.error(TAG, "setOnClickListener(OnClickListener listener)方法不可用");
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.pressDownX = event.getRawX();
                this.pressDownY = event.getRawY();
                this.deltaX = (int) (this.pressDownX - ((float) this.layoutParams.leftMargin));
                this.deltaY = (int) (this.pressDownY - ((float) this.layoutParams.topMargin));
                this.touchDownTimestamp = System.currentTimeMillis();
                this.stayedWithinClickDistance = true;
                break;
            case 1:
                if (System.currentTimeMillis() - this.touchDownTimestamp < 200 && this.stayedWithinClickDistance) {
                    AuiLogger.debug(TAG, "触发onclick事件:" + this.eventListener);
                    if (this.eventListener != null) {
                        this.eventListener.onClick(this);
                    }
                }
                if (this.layoutParams.leftMargin + this.removeDistance >= 0) {
                    this.layoutParams.leftMargin = 0;
                    setLayoutParams(this.layoutParams);
                    break;
                } else {
                    if (this.eventListener != null) {
                        AuiLogger.debug(TAG, "触发onRemove事件:" + this.eventListener);
                        this.eventListener.onRemove(this);
                    }
                    removeFromParent();
                    this.layoutParams.leftMargin = 0;
                    break;
                }
            case 2:
                if (this.stayedWithinClickDistance) {
                    if (distance(getContext(), this.pressDownX, this.pressDownY, event.getRawX(), event.getRawY()) > 2.0f) {
                        this.stayedWithinClickDistance = false;
                    }
                }
                int leftMargin = ((int) event.getRawX()) - this.deltaX;
                if (leftMargin <= 0) {
                    this.layoutParams.leftMargin = leftMargin;
                } else {
                    this.layoutParams.leftMargin = 0;
                }
                int topMargin = ((int) event.getRawY()) - this.deltaY;
                if (AUStatusBarUtil.getStatusBarHeight(getContext()) > topMargin) {
                    topMargin = AUStatusBarUtil.getStatusBarHeight(getContext());
                } else if (topMargin > this.screenHeight - getMeasuredHeight()) {
                    topMargin = this.screenHeight - getMeasuredHeight();
                }
                this.layoutParams.topMargin = topMargin;
                setLayoutParams(this.layoutParams);
                break;
        }
        return true;
    }

    private float distance(Context context, float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return pxToDp(context, (float) Math.sqrt((double) ((dx * dx) + (dy * dy))));
    }

    private float pxToDp(Context context, float px) {
        return (float) DensityUtil.px2dip(context, px);
    }

    public void setAUPopFloatEventListener(AUPopFloatEventListener eventListener2) {
        this.eventListener = eventListener2;
    }
}
