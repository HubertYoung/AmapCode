package com.alipay.mobile.antui.badge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.util.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AUBadgeView extends FrameLayout {
    public static final String KEY_BADGE_CONTENT = "badgeText";
    public static final String KEY_BADGE_STYLE = "badgeType";
    protected static final int MAX_MSG_COUNT = 100;
    private static final String TAG = "AUBadgeView";
    protected boolean isCenterLocate = false;
    private boolean isSmallTextSize;
    protected Context mContext;
    protected volatile boolean mInited = false;
    private OnContentChangedListener mListener;
    protected int mLocateX = -1;
    protected int mLocateY = -1;
    int mTextMaxEms = -1;
    int mTextMaxLength = -1;
    int mTextMaxWidth = -1;
    protected int msgCount = 0;
    protected String msgText;
    protected ImageView pointImageView;
    protected int redHeight = 0;
    protected int redWidth = 0;
    protected Style style = Style.NONE;
    protected TextView txtTextView;

    public interface OnContentChangedListener {
        void onChange(Style style, String str);
    }

    public enum Style {
        NONE(Constants.ANIMATOR_NONE),
        POINT("point"),
        NUM("num"),
        TEXT("text"),
        MORE("more");
        
        private static final Map<String, Style> sStringToEnum = null;
        private String desc;

        static {
            int i;
            Style[] values;
            sStringToEnum = new HashMap();
            for (Style value : values()) {
                sStringToEnum.put(value.desc, value);
            }
        }

        private Style(String desc2) {
            this.desc = desc2;
        }

        public final String getDes() {
            return this.desc;
        }

        public static Style fromString(String symbol) {
            return sStringToEnum.get(symbol);
        }
    }

    public AUBadgeView(Context context) {
        super(context);
        init(context, null);
    }

    public AUBadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUBadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AUBadgeView);
            this.isSmallTextSize = array.getBoolean(0, false);
            this.mTextMaxEms = array.getInt(2, -1);
            this.mTextMaxLength = array.getInt(1, -1);
            this.mTextMaxWidth = array.getDimensionPixelOffset(3, -1);
            array.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void lazyInit() {
        if (!this.mInited) {
            LayoutInflater.from(getContext()).inflate(R.layout.default_badge_layout, this, true);
            this.pointImageView = (ImageView) findViewById(R.id.redPoint);
            this.txtTextView = (TextView) findViewById(R.id.msgText);
            if (this.mTextMaxLength != -1) {
                this.txtTextView.setFilters(new InputFilter[]{new LengthFilter(this.mTextMaxLength)});
            }
            if (this.mTextMaxWidth != -1) {
                this.txtTextView.setMaxWidth(this.mTextMaxWidth);
            }
            this.mInited = true;
        }
    }

    private void measureRedPoint(Style style2, String text) {
        if (this.isCenterLocate) {
            if (style2 == Style.POINT) {
                this.redWidth = DensityUtil.dip2px(this.mContext, 10.0f);
                this.redHeight = DensityUtil.dip2px(this.mContext, 10.0f);
            } else if (style2 == Style.MORE) {
                this.redWidth = DensityUtil.dip2px(this.mContext, 24.0f);
                this.redHeight = DensityUtil.dip2px(this.mContext, 16.0f);
            } else {
                this.redHeight = DensityUtil.dip2px(this.mContext, 16.0f);
                this.redWidth = (int) this.txtTextView.getPaint().measureText(text);
            }
            AuiLogger.debug(TAG, "measureRedPoint redWidth = " + this.redWidth + " redHeight" + this.redHeight + " x = " + getX());
        }
    }

    private void measureRedPoint(Drawable drawable) {
        if (this.isCenterLocate) {
            if (drawable == null) {
                this.redWidth = 35;
                this.redHeight = 35;
                return;
            }
            this.redWidth = drawable.getIntrinsicWidth();
            this.redHeight = drawable.getIntrinsicHeight();
            AuiLogger.debug(TAG, "measureRedPoint redWidth = " + this.redWidth + " redHeight" + this.redHeight + " x = " + getX());
        }
    }

    /* access modifiers changed from: protected */
    public void locate() {
        if (this.isCenterLocate && this.mLocateX != -1 && this.mLocateY != -1) {
            ViewParent parent = getParent();
            if (parent != null) {
                int top = this.mLocateY - (this.redHeight / 2);
                int right = this.mLocateX - (this.redWidth / 2);
                AuiLogger.debug(TAG, "locate right = " + right + " top = " + top + " redWidth = " + this.redWidth);
                if (top < 0) {
                    top = 0;
                }
                if (right < 0) {
                    right = 0;
                }
                if (parent instanceof RelativeLayout) {
                    LayoutParams params = (LayoutParams) getLayoutParams();
                    params.addRule(11);
                    params.addRule(10);
                    params.setMargins(0, top, right, 0);
                    setLayoutParams(params);
                } else if (parent instanceof FrameLayout) {
                    FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) getLayoutParams();
                    params2.gravity = 53;
                    params2.setMargins(0, top, right, 0);
                    setLayoutParams(params2);
                }
            }
        }
    }

    public void setMsgCount(int msgCount2) {
        this.msgCount = msgCount2;
        if (msgCount2 <= 0) {
            setStyleAndContent(Style.NONE, "");
        } else {
            setStyleAndContent(Style.NUM, String.valueOf(msgCount2));
        }
    }

    public void setRedPoint(boolean isShow) {
        setStyleAndMsgText(Style.POINT, isShow ? "1" : "0");
    }

    public void setMsgText(String text) {
        setStyleAndContent(Style.TEXT, text);
    }

    public void setSmallTextSize(boolean isSmallTextSize2) {
        this.isSmallTextSize = isSmallTextSize2;
    }

    public void dismiss() {
        setStyleAndContent(Style.NONE, "");
    }

    public void setStyleAndContent(Style style2, String content) {
        if (this.mListener != null && (this.style != style2 || !TextUtils.equals(this.msgText, content))) {
            this.mListener.onChange(style2, content);
        }
        this.style = style2;
        this.msgText = content;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            doRefresh();
        } else {
            post(new a(this));
        }
    }

    public void setOnContentChangedListener(OnContentChangedListener listener) {
        this.mListener = listener;
    }

    @Deprecated
    public void setStyleAndMsgText(Style style2, String text) {
        if (TextUtils.isEmpty(text) || TextUtils.equals(text, "0") || style2 == null) {
            style2 = Style.NONE;
            if (TextUtils.equals(text, "0")) {
                this.msgCount = 0;
            }
        }
        setStyleAndContent(style2, text);
    }

    /* access modifiers changed from: private */
    public void doRefresh() {
        lazyInit();
        if (this.txtTextView == null || this.pointImageView == null) {
            AuiLogger.error(TAG, "doRefresh view == null " + this.txtTextView + Token.SEPARATOR + this.pointImageView);
            return;
        }
        if (this.style == Style.NONE) {
            setVisibility(8);
        } else if (Style.POINT == this.style) {
            setBadgeImageStyles(Style.POINT, R.drawable.shock_point_small);
        } else if (Style.NUM == this.style) {
            try {
                int count = Integer.parseInt(this.msgText);
                this.msgCount = count;
                if (count > 0 && count < 100) {
                    setBadgeTextStyles(Style.NUM, this.msgText);
                } else if (count >= 100) {
                    setBadgeImageStyles(Style.MORE, R.drawable.shock_point_more);
                }
            } catch (NumberFormatException e) {
                if (TextUtils.isEmpty(this.msgText)) {
                    AuiLogger.debug(TAG, "Style.NUM with illegal params : msgText is empty");
                    return;
                } else {
                    AuiLogger.error(TAG, "Style.NUM with illegal params : msgText = " + this.msgText);
                    return;
                }
            }
        } else if (Style.MORE == this.style) {
            setBadgeImageStyles(Style.MORE, R.drawable.shock_point_more);
        } else if (Style.TEXT == this.style) {
            setBadgeTextStyles(this.style, this.msgText);
        } else {
            return;
        }
        locate();
    }

    private void setBadgeTextStyles(Style style2, String text) {
        if (!TextUtils.isEmpty(text)) {
            setVisibility(0);
            this.pointImageView.setVisibility(8);
            this.txtTextView.setVisibility(0);
            float customTextDpSize = getTextDpSize();
            if (customTextDpSize > 0.0f) {
                this.txtTextView.setTextSize(1, customTextDpSize);
            } else if (style2 == Style.NUM) {
                this.txtTextView.setTextSize(1, 10.0f);
            } else if (!this.isSmallTextSize || text.length() < 3 || isAlphabet(text)) {
                this.txtTextView.setTextSize(1, 11.0f);
            } else {
                this.txtTextView.setTextSize(1, 9.0f);
            }
            if (this.mTextMaxEms > 0 && text.length() > this.mTextMaxEms) {
                this.txtTextView.setMaxWidth((int) ((((double) this.mTextMaxEms) + 0.2d) * ((double) this.txtTextView.getLineHeight())));
            }
            this.txtTextView.setText(text);
            Drawable customBg = getBgDrawable(style2);
            if (customBg != null) {
                this.txtTextView.setBackgroundDrawable(customBg);
                measureRedPoint(customBg);
                return;
            }
            this.txtTextView.setBackgroundResource(R.drawable.shock_point_large);
            measureRedPoint(style2, text);
        }
    }

    private boolean isAlphabet(String text) {
        return Pattern.compile("[a-zA-Z]").matcher(text).find();
    }

    private void setBadgeImageStyles(Style style2, int resId) {
        setVisibility(0);
        this.pointImageView.setVisibility(0);
        this.txtTextView.setVisibility(8);
        Drawable customBg = getBgDrawable(style2);
        if (customBg != null) {
            this.pointImageView.setImageDrawable(customBg);
            measureRedPoint(customBg);
            return;
        }
        this.pointImageView.setImageResource(resId);
        measureRedPoint(style2, "");
    }

    public Style getBadgeViewStyle() {
        return this.style;
    }

    public String getBadgeViewContent() {
        return this.msgText;
    }

    @Deprecated
    public int getMsgCount() {
        return this.msgCount;
    }

    public void setCenterLocate(boolean centerLocate) {
        this.isCenterLocate = centerLocate;
    }

    public boolean isCenterLocate() {
        return this.isCenterLocate;
    }

    public void setCenterMargin(int top, int right) {
        this.mLocateX = right;
        this.mLocateY = top;
        Log.w(TAG, "setCenterMargin set top,right top = " + top + "  right =" + right);
        locate();
    }

    public void setMaxLines(int maxLines) {
        this.txtTextView.setMaxLines(maxLines);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Drawable getBgDrawable(Style style2, int msgCount2) {
        return null;
    }

    /* access modifiers changed from: protected */
    public Drawable getBgDrawable(Style style2) {
        return getBgDrawable(style2, this.msgCount);
    }

    /* access modifiers changed from: protected */
    public Drawable getRedPointDrawable() {
        return null;
    }

    /* access modifiers changed from: protected */
    public float getTextDpSize() {
        return -1.0f;
    }
}
