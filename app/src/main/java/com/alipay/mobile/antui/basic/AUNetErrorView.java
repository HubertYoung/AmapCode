package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.TimerListener;
import com.alipay.mobile.antui.lottie.AULottieLayout;
import com.alipay.mobile.antui.lottie.LoadLottieCallback;
import com.alipay.mobile.antui.lottie.NetErrorLottieFile;
import com.alipay.mobile.antui.utils.DensityUtil;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public class AUNetErrorView extends AULinearLayout implements LoadLottieCallback {
    public static final int TYPE_EMPTY = 17;
    public static final int TYPE_NOTFOUND = 20;
    public static final int TYPE_NOT_SHOW = 0;
    public static final int TYPE_OVER_FLOW = 19;
    public static final int TYPE_SIGNAL = 16;
    public static final int TYPE_USER_LOGOUT = 21;
    public static final int TYPE_WARNING = 18;
    /* access modifiers changed from: private */
    public static int times = 0;
    private boolean isSimpleMode = false;
    /* access modifiers changed from: private */
    public AUButton mActionBtn;
    /* access modifiers changed from: private */
    public LinearLayout mActionContainer;
    /* access modifiers changed from: private */
    public String mActionStr;
    /* access modifiers changed from: private */
    public Handler mHandler = new z(this);
    /* access modifiers changed from: private */
    public AUImageView mIcon;
    private AULottieLayout mIconLottie;
    private boolean mIsButtonBottom;
    private boolean mIsLottieEnabled;
    /* access modifiers changed from: private */
    public AUButton mSubActionBtn;
    private AUTextView mSubTips;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private AUTextView mTips;
    /* access modifiers changed from: private */
    public int mType;
    /* access modifiers changed from: private */
    public String timeColor;
    private TimerListener timerlistener;

    static /* synthetic */ int access$310() {
        int i = times;
        times = i - 1;
        return i;
    }

    public AUNetErrorView(Context context) {
        super(context);
        init(context, null);
    }

    public AUNetErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUNetErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.au_net_error_view, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NetErrorView);
        this.mType = a.getInt(0, 16);
        this.isSimpleMode = a.getBoolean(1, false);
        a.recycle();
        setBackgroundColor(getResources().getColor(R.color.AU_COLOR_CLIENT_BG1));
        setOrientation(1);
        setGravity(17);
        int padding = getResources().getDimensionPixelSize(R.dimen.AU_SPACE3);
        setPadding(padding, 0, padding, 0);
        this.mActionContainer = (LinearLayout) findViewById(R.id.button);
        this.mActionBtn = (AUButton) findViewById(R.id.action);
        this.mSubActionBtn = (AUButton) findViewById(R.id.sub_action);
        this.mTips = (AUTextView) findViewById(R.id.tips);
        this.mSubTips = (AUTextView) findViewById(R.id.sub_tips);
        this.mIconLottie = (AULottieLayout) findViewById(R.id.icon_lottie);
        this.mIcon = (AUImageView) findViewById(R.id.icon);
        post(new v(this));
        this.mActionBtn.setVisibilityChangeObserver(new w(this));
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIconLottie.playAnimation();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIconLottie.cancelAnimation();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mIsButtonBottom) {
            int extPadding = (this.mActionContainer.getHeight() - this.mActionBtn.getHeight()) - this.mSubActionBtn.getHeight();
            if (extPadding < getResources().getDimensionPixelSize(R.dimen.AU_HEIGHT7)) {
                this.mActionContainer.setPadding(0, 0, 0, extPadding);
            }
        }
    }

    public void setIsSimpleType(boolean isSimple) {
        this.isSimpleMode = isSimple;
    }

    /* access modifiers changed from: private */
    public void changeButtonStyleByIsSimple() {
        if (this.isSimpleMode) {
            this.mTips.setTextSize(1, 14.0f);
            this.mSubTips.setTextSize(1, 14.0f);
            this.mActionBtn.setBtnType(AUButton.BTN_TYPE_ASS_TRANS);
            this.mActionBtn.setLayoutParams(new LayoutParams(-2, getResources().getDimensionPixelSize(R.dimen.AU_SPACE7)));
            this.mActionBtn.setPadding(getResources().getDimensionPixelSize(R.dimen.AU_HEIGHT1), 0, getResources().getDimensionPixelSize(R.dimen.AU_HEIGHT1), 0);
            return;
        }
        this.mTips.setTextSize(1, 18.0f);
        this.mSubTips.setTextSize(1, 18.0f);
        this.mActionBtn.setBtnType(AUButton.BIN_TYPE_NO_RECT);
        this.mActionBtn.setLayoutParams(new LayoutParams(DensityUtil.dip2px(getContext(), 220.0f), getResources().getDimensionPixelSize(R.dimen.AU_SPACE12)));
    }

    public void resetNetErrorType(int type) {
        this.mType = type;
        this.mTips.setVisibility(8);
        this.mSubTips.setVisibility(8);
        this.mActionContainer.setVisibility(8);
        this.mActionBtn.setVisibility(8);
        this.mSubActionBtn.setVisibility(8);
        this.mIcon.setVisibility(8);
        this.mIconLottie.setVisibility(8);
        this.mIsLottieEnabled = true;
        changeButtonStyleByIsSimple();
        if (!this.isSimpleMode) {
            switch (this.mType) {
                case 16:
                    setIconStyle(NetErrorLottieFile.NET_ERROR_SIGNAL_FILE_NAME, R.drawable.net_error_signal);
                    setTips(getResources().getString(R.string.net_connection_error));
                    setSubTips(getResources().getString(R.string.net_connection_error_sub));
                    return;
                case 17:
                    setIconStyle(NetErrorLottieFile.NET_ERROR_EMPTY_FILE_NAME, R.drawable.net_error_empty);
                    setTips(getResources().getString(R.string.net_empty));
                    return;
                case 18:
                    setIconStyle(NetErrorLottieFile.NET_ERROR_WARNING_FILE_NAME, R.drawable.net_error_warning);
                    setTips(getResources().getString(R.string.net_system_wrong));
                    setSubTips(getResources().getString(R.string.net_system_sub));
                    return;
                case 19:
                    setIconStyle(NetErrorLottieFile.NET_ERROR_OVERFLOW_FILE_NAME, R.drawable.net_error_overflow);
                    setTips(getResources().getString(R.string.net_overflow));
                    setSubTips(getResources().getString(R.string.net_overflow_sub));
                    return;
                case 20:
                    setIconStyle(NetErrorLottieFile.NET_ERROR_WARNING_FILE_NAME, R.drawable.net_error_warning);
                    setTips(getResources().getString(R.string.net_system_wrong));
                    return;
                case 21:
                    setIconStyle(R.drawable.net_error_user_logout);
                    setTips(getResources().getString(R.string.net_user_logout));
                    return;
                default:
                    return;
            }
        } else {
            switch (this.mType) {
                case 16:
                    setIconStyle(R.drawable.net_error_signal_simple);
                    setTips(getResources().getString(R.string.net_connection_error));
                    setSubTips(getResources().getString(R.string.net_connection_error_sub));
                    return;
                case 17:
                    setIconStyle(NetErrorLottieFile.NET_ERROR_EMPTY_SIMPLE_FILE_NAME, R.drawable.net_error_empty_simple);
                    setTips(getResources().getString(R.string.net_empty));
                    return;
                case 18:
                    setIconStyle(R.drawable.net_error_warning_simple);
                    setTips(getResources().getString(R.string.net_system_wrong));
                    setSubTips(getResources().getString(R.string.net_system_sub));
                    return;
                case 19:
                    setIconStyle(R.drawable.net_error_overflow_simple);
                    setTips(getResources().getString(R.string.net_overflow));
                    setSubTips(getResources().getString(R.string.net_overflow_sub));
                    return;
                case 21:
                    setIconStyle(R.drawable.net_error_user_logout_simple);
                    setTips(getResources().getString(R.string.net_user_logout));
                    return;
                default:
                    return;
            }
        }
    }

    private void setIconStyle(int resID) {
        setIconStyle(null, resID);
    }

    private void setIconStyle(String lottieFileName, int defResID) {
        if (!TextUtils.isEmpty(lottieFileName)) {
            JSONObject animationSource = NetErrorLottieFile.getInstance().getNetErrorAnimation(lottieFileName, this.mType, this);
            if (animationSource != null) {
                loadLottie(animationSource);
            } else {
                setIconStyle(null, defResID);
            }
        } else {
            this.mIconLottie.setVisibility(8);
            this.mIcon.setImageResource(defResID);
            this.mIcon.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void loadLottie(JSONObject animationSource) {
        int iconSize;
        if (this.mIsLottieEnabled) {
            this.mIconLottie.setVisibility(0);
            if (this.isSimpleMode) {
                iconSize = getResources().getDimensionPixelSize(R.dimen.au_net_error_icon_simple_size);
            } else {
                iconSize = getResources().getDimensionPixelSize(R.dimen.au_net_error_icon_size);
            }
            ViewGroup.LayoutParams layoutParams = this.mIconLottie.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(iconSize, iconSize);
            }
            layoutParams.height = iconSize;
            layoutParams.width = iconSize;
            this.mIconLottie.setLayoutParams(layoutParams);
            this.mIconLottie.setAnimationSource(animationSource);
            this.mIconLottie.addAnimatorListener(new x(this));
            this.mIconLottie.playAnimation();
            this.mIconLottie.loop(true);
        }
    }

    public void setAction(String text, OnClickListener clickListener) {
        setActionStr(text);
        this.mActionBtn.setOnClickListener(clickListener);
    }

    public void setAction(OnClickListener clickListener) {
        if (this.isSimpleMode) {
            setAction(getResources().getString(R.string.refresh_net_simple), clickListener);
        } else {
            setAction(getResources().getString(R.string.refresh_net), clickListener);
        }
    }

    /* access modifiers changed from: private */
    public void setActionStr(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mActionContainer.setVisibility(0);
            changeButtonStyleByIsSimple();
            this.mActionBtn.setText(Html.fromHtml(text));
            this.mActionBtn.setVisibility(0);
            return;
        }
        if (this.mSubActionBtn.getVisibility() == 8) {
            this.mActionContainer.setVisibility(8);
        }
        this.mActionBtn.setVisibility(8);
    }

    public void setNoAction() {
        if (this.mSubActionBtn.getVisibility() != 0) {
            this.mActionContainer.setVisibility(4);
        }
        this.mActionBtn.setVisibility(4);
    }

    public void setSubAction(String text, OnClickListener clickListener) {
        setSubActionStr(text);
        this.mSubActionBtn.setOnClickListener(clickListener);
    }

    public void setSubAction(OnClickListener clickListener) {
        setSubAction(getResources().getString(R.string.fix_net), clickListener);
    }

    private void setSubActionStr(String text) {
        if (TextUtils.isEmpty(text) || this.isSimpleMode) {
            if (this.mActionBtn.getVisibility() == 8) {
                this.mActionContainer.setVisibility(8);
            }
            this.mSubActionBtn.setVisibility(8);
            return;
        }
        this.mActionContainer.setVisibility(0);
        changeButtonStyleByIsSimple();
        this.mSubActionBtn.setText(Html.fromHtml(text));
        this.mSubActionBtn.setVisibility(0);
    }

    public void setNoSubAction() {
        if (this.mActionBtn.getVisibility() != 0) {
            this.mActionContainer.setVisibility(4);
        }
        this.mSubActionBtn.setVisibility(4);
    }

    public void setTips(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mTips.setText(Html.fromHtml(text));
            this.mTips.setVisibility(0);
            return;
        }
        this.mTips.setVisibility(8);
    }

    public void setSubTips(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mSubTips.setText(Html.fromHtml(text));
            this.mSubTips.setVisibility(0);
            return;
        }
        this.mSubTips.setVisibility(8);
    }

    public AUButton getActionButton() {
        return this.mActionBtn;
    }

    public AUImageView getImageView() {
        this.mIsLottieEnabled = false;
        if (this.mIcon.getVisibility() != 0) {
            this.mIcon.setVisibility(0);
        }
        this.mIconLottie.cancelAnimation();
        if (this.mIconLottie.getVisibility() == 0) {
            this.mIconLottie.setVisibility(8);
        }
        return this.mIcon;
    }

    public String getDefaultTimeColorStr() {
        return "#D83B1E";
    }

    public void setTimer(int seconds, String action, String tickColor, OnClickListener clickListener, TimerListener timerlistener2) {
        this.timerlistener = timerlistener2;
        times = seconds + 1;
        setAction(action, clickListener);
        this.mActionStr = action;
        this.timeColor = filterColor(tickColor);
        cancelTimer();
        this.mTimer = new Timer();
        this.mTimerTask = new y(this);
        this.mTimer.schedule(this.mTimerTask, 0, 1000);
    }

    public void setTimer(int seconds, OnClickListener clickListener, TimerListener timerlistener2) {
        setTimer(seconds, getResources().getString(R.string.try_again_once), getDefaultTimeColorStr(), clickListener, timerlistener2);
    }

    public void setTimer(int seconds, String action, String color, OnClickListener clickListener) {
        setTimer(seconds, action, color, clickListener, null);
    }

    /* access modifiers changed from: private */
    public void callTimeFinish() {
        if (this.timerlistener != null) {
            this.timerlistener.onFinish();
        }
    }

    private String filterColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            return "<font color='" + color + "'>%s</font>";
        }
        return "%s";
    }

    /* access modifiers changed from: private */
    public void cancelTimer() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
        if (this.mTimerTask != null) {
            this.mTimerTask.cancel();
            this.mTimerTask = null;
        }
    }

    public void setButtonBlueStyle() {
        changeButtonStyleByIsSimple();
    }

    public void setButtonLinkStyle() {
        changeButtonStyleByIsSimple();
    }

    public void setButtonBottom(boolean isBottom) {
        this.mIsButtonBottom = isBottom;
        if (this.mIsButtonBottom) {
            this.mActionContainer.setGravity(81);
            this.mActionContainer.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.AU_HEIGHT7));
            if (this.mActionBtn.getVisibility() == 8 && this.mSubActionBtn.getVisibility() == 8) {
                this.mActionContainer.setVisibility(4);
                return;
            }
            return;
        }
        this.mActionContainer.setGravity(49);
        this.mActionContainer.setPadding(0, 0, 0, 0);
    }

    public void onLottieLoadFinish(JSONObject lottieJson, int errorType) {
        if (errorType == this.mType) {
            post(new aa(this, lottieJson));
        }
    }
}
