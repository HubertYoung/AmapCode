package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.constant.AUConstant;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import java.util.Arrays;
import java.util.List;

public class AUBladeView extends AUView implements AntUI, AUViewInterface {
    private static final String TAG = "AUBladeView";
    private static final String[] defaultLetters = {"A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", LogHelper.DEFAULT_LEVEL, "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "O", "P", "Q", "R", "S", "T", "U", SecureSignatureDefine.SG_KEY_SIGN_VERSION, "W", "X", "Y", "Z", MetaRecord.LOG_SEPARATOR};
    private static final String[] skip_device = {"FRD-DL00", "KNT-AL20", "PRA-AL00"};
    Runnable dismissRunnable = new b(this);
    private Boolean isAP;
    private int mChoose = -1;
    private Handler mHandler = new Handler();
    private String[] mLetters;
    private OnItemClickListener mOnItemClickListener;
    protected Paint mPaint = new Paint();
    private AUTextView mPopupText;
    /* access modifiers changed from: private */
    public AUPopupWindow mPopupWindow;
    private boolean mShowBkg = false;
    private boolean mShowSelectPop = false;
    private String mText1 = "";
    private String mText2 = "";

    public interface OnItemClickListener {
        void onClickUp();

        void onItemClick(String str);
    }

    public AUBladeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public AUBladeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUBladeView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.AUBladeView);
            initContent(context, null, styled);
            initStyleByTheme(context);
            initAttrStyle(context, null, styled);
            styled.recycle();
            return;
        }
        initStyleByTheme(context);
    }

    /* access modifiers changed from: protected */
    public String[] getLetters() {
        return this.mLetters;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mShowBkg) {
            canvas.drawColor(Color.parseColor("#44000000"));
        }
        String[] b = getLetters();
        int height = getHeight();
        int width = getWidth();
        int count = b.length;
        int singleHeight = height / count;
        for (int i = 0; i < count; i++) {
            this.mPaint.setColor(Color.parseColor("#565656"));
            this.mPaint.setAntiAlias(true);
            if (height < 400) {
                this.mPaint.setTextSize(getResources().getDimension(R.dimen.AU_SPACE2));
            } else {
                this.mPaint.setTextSize(getResources().getDimension(R.dimen.AU_TEXTSIZE3));
            }
            if (i == this.mChoose) {
                this.mPaint.setColor(Color.parseColor("#3399ff"));
            }
            canvas.drawText(b[i], ((float) (width / 2)) - (this.mPaint.measureText(b[i]) / 2.0f), (float) ((singleHeight * i) + singleHeight), this.mPaint);
        }
        this.mPaint.reset();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = this.mChoose;
        int count = getLetters().length;
        int c = (int) ((y / ((float) getHeight())) * ((float) count));
        switch (action) {
            case 0:
                this.mShowBkg = true;
                if (oldChoose != c && c >= 0 && c < count) {
                    performItemClicked(c);
                    this.mChoose = c;
                    invalidate();
                    break;
                }
            case 1:
                this.mShowBkg = false;
                this.mChoose = -1;
                if (this.mOnItemClickListener != null) {
                    this.mOnItemClickListener.onClickUp();
                }
                dismissPopup();
                invalidate();
                break;
            case 2:
                if (oldChoose != c && c >= 0 && c < count) {
                    performItemClicked(c);
                    this.mChoose = c;
                    invalidate();
                    break;
                }
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        try {
            return super.onTouchEvent(event);
        } catch (Exception e) {
            Log.e(AUConstant.TAG, TAG, e);
            return true;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private void performItemClicked(int item) {
        String[] b = getLetters();
        if (this.mShowSelectPop) {
            showPopup(b[item]);
        }
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(b[item]);
        }
    }

    private void showPopup(String item) {
        if (this.mPopupWindow == null) {
            this.mHandler.removeCallbacks(this.dismissRunnable);
            int popupSize = DensityUtil.dip2px(getContext(), 80.0f);
            View layoutView = LayoutInflater.from(getContext()).inflate(R.layout.au_bladeview_firstchar_pop, null);
            this.mPopupText = (AUTextView) layoutView.findViewById(R.id.tv_first_char);
            this.mPopupWindow = new AUPopupWindow(layoutView, popupSize, popupSize, false);
        }
        this.mPopupText.setText(item);
        if (this.mPopupWindow.isShowing()) {
            List list = Arrays.asList(skip_device);
            if (list == null || TextUtils.isEmpty(Build.MODEL) || !list.contains(Build.MODEL.toUpperCase())) {
                this.mPopupWindow.update();
            } else {
                AuiLogger.error(TAG, "is skip device");
            }
        } else {
            this.mPopupWindow.showAtLocation(getRootView(), 17, 0, 0);
        }
    }

    private void dismissPopup() {
        this.mHandler.postDelayed(this.dismissRunnable, 1000);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
        this.mText1 = typedArray.getString(1);
        this.mText2 = typedArray.getString(2);
        this.mShowSelectPop = typedArray.getBoolean(0, true);
    }

    public void initStyleByTheme(Context context) {
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
        if (!TextUtils.isEmpty(this.mText1) && TextUtils.isEmpty(this.mText2)) {
            this.mLetters = new String[(defaultLetters.length + 1)];
            this.mLetters[0] = this.mText1;
            for (int i = 0; i < defaultLetters.length; i++) {
                this.mLetters[i + 1] = defaultLetters[i];
            }
        } else if (TextUtils.isEmpty(this.mText1) || TextUtils.isEmpty(this.mText2)) {
            this.mLetters = defaultLetters;
        } else {
            this.mLetters = new String[(defaultLetters.length + 2)];
            this.mLetters[0] = this.mText1;
            this.mLetters[1] = this.mText2;
            for (int i2 = 0; i2 < defaultLetters.length; i2++) {
                this.mLetters[i2 + 2] = defaultLetters[i2];
            }
        }
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
