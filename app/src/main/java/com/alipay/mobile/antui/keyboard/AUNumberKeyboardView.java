package com.alipay.mobile.antui.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.excutor.AntUIExecutorManager;
import com.alipay.mobile.antui.excutor.ConfigExecutor;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import java.util.ArrayList;
import java.util.List;

public class AUNumberKeyboardView extends AULinearLayout implements OnClickListener, OnLongClickListener, OnTouchListener {
    private static final String CONFIG_KEY = "AUNumberKeyboardView_ENABLE_OLD_LAYOUT";
    private static final String[] NUM = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static final int STYLE_NONE = 3;
    public static final int STYLE_PHONE = 4;
    public static final int STYLE_POINT = 1;
    public static final int STYLE_X = 2;
    private OnActionClickListener actionClickListener;
    private AUImageView closeView;
    private boolean mIsOldLayout;
    private List<AUTextView> numViewList;
    private boolean startLongDelete;
    private AUTextView styleView;
    private WindowStateChangeListener windowStateChangeListener;

    public interface OnActionClickListener {
        void onCloseClick(View view);

        void onConfirmClick(View view);

        void onDeleteClick(View view);

        void onNumClick(View view, CharSequence charSequence);
    }

    public boolean onLongClick(View v) {
        if (v.getId() == R.id.au_key_delete) {
            this.startLongDelete = true;
            AuiLogger.debug("AUNumberKeyboardView", "startLongDelete = true");
        }
        return false;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() != R.id.au_key_delete) {
            return true;
        }
        AuiLogger.debug("AUNumberKeyboardView", "onTouch");
        switch (event.getAction() & 255) {
            case 1:
            case 3:
                if (this.startLongDelete) {
                    AuiLogger.debug("AUNumberKeyboardView", "startLongDelete = false");
                    this.startLongDelete = false;
                    break;
                }
                break;
        }
        if (!this.startLongDelete || this.actionClickListener == null) {
            return false;
        }
        this.actionClickListener.onDeleteClick(v);
        try {
            Thread.sleep(150);
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public AUNumberKeyboardView(Context context) {
        this(context, null, 1, false, null);
    }

    public AUNumberKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 1, false, null);
    }

    public AUNumberKeyboardView(Context context, int style, boolean usingOldLayout) {
        this(context, null, style, usingOldLayout, null);
    }

    public AUNumberKeyboardView(Context context, int style, OnActionClickListener clickListener) {
        this(context, null, style, false, clickListener);
    }

    public AUNumberKeyboardView(Context context, AttributeSet attrs, int style, boolean usingOldLayout, OnActionClickListener clickListener) {
        super(context, attrs);
        this.startLongDelete = false;
        this.numViewList = new ArrayList();
        this.mIsOldLayout = usingOldLayout;
        this.actionClickListener = clickListener;
        init(context, attrs, style);
    }

    private void init(Context context, AttributeSet attrs, int style) {
        int i = 0;
        this.mIsOldLayout = checkIfUseOldLayout(attrs) || this.mIsOldLayout;
        LayoutInflater.from(context).inflate(this.mIsOldLayout ? R.layout.au_number_keyboard_view : R.layout.au_number_keyboard_view_v2, this, true);
        if (this.mIsOldLayout) {
            i = 1;
        }
        setOrientation(i);
        setBackgroundColor(getResources().getColor(R.color.AU_COLOR_CLIENT_BG1));
        setClickable(true);
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_0));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_1));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_2));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_3));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_4));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_5));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_6));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_7));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_8));
        this.numViewList.add((AUTextView) findViewById(R.id.au_num_9));
        for (AUTextView onClickListener : this.numViewList) {
            onClickListener.setOnClickListener(this);
        }
        this.styleView = (AUTextView) findViewById(R.id.au_num_style);
        this.styleView.setOnClickListener(this);
        this.closeView = (AUImageView) findViewById(R.id.au_key_close);
        this.closeView.setOnClickListener(this);
        setStyle(style);
        findViewById(R.id.au_key_delete).setOnClickListener(this);
        findViewById(R.id.au_key_delete).setOnLongClickListener(this);
        findViewById(R.id.au_key_delete).setOnTouchListener(this);
        if (!this.mIsOldLayout) {
            findViewById(R.id.au_key_confirm).setOnClickListener(this);
        }
    }

    private boolean checkIfUseOldLayout(AttributeSet attrs) {
        boolean result = false;
        ConfigExecutor executor = AntUIExecutorManager.getInstance().getConfigExecutor();
        if (executor != null) {
            try {
                result = Boolean.parseBoolean(executor.getConfig(CONFIG_KEY));
            } catch (Exception e) {
                AuiLogger.error("AUNumberKeyboardView", "ConfigService 配置错误: " + e);
            }
        }
        if (result || attrs == null) {
            return result;
        }
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AUNumberKeyboardView);
        boolean result2 = a.getBoolean(1, false);
        a.recycle();
        return result2;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.au_key_close) {
            if (this.actionClickListener != null) {
                this.actionClickListener.onCloseClick(v);
            }
        } else if (id == R.id.au_key_delete) {
            if (this.actionClickListener != null) {
                AuiLogger.debug("AUNumberKeyboardView", "onClick");
                this.actionClickListener.onDeleteClick(v);
            }
        } else if (id == R.id.au_key_confirm) {
            if (this.actionClickListener != null) {
                this.actionClickListener.onConfirmClick(v);
            }
        } else if (v instanceof AUTextView) {
            onNumClick(v, ((AUTextView) v).getText());
        }
    }

    private void onNumClick(View view, CharSequence num) {
        if (this.actionClickListener != null) {
            this.actionClickListener.onNumClick(view, num);
        }
    }

    public void setNumKeyRandom(boolean isRandom) {
        int length = NUM.length;
        if (isRandom) {
            List arr = new ArrayList();
            for (String add : NUM) {
                arr.add(add);
            }
            for (int i = 0; i < length; i++) {
                this.numViewList.get(i).setText(getRandomNum(arr));
            }
            return;
        }
        for (int i2 = 0; i2 < length; i2++) {
            this.numViewList.get(i2).setText(NUM[i2]);
        }
    }

    private String getRandomNum(List<String> arr) {
        int t = (int) (Math.random() * ((double) arr.size()));
        String out = arr.get(t);
        arr.remove(t);
        return out;
    }

    public void setStyle(int style) {
        switch (style) {
            case 1:
                this.styleView.setText(".");
                showStyleView();
                return;
            case 2:
                this.styleView.setText("X");
                showStyleView();
                return;
            case 3:
                this.styleView.setText("");
                hideStyleView();
                return;
            case 4:
                this.styleView.setText("-");
                showStyleView();
                return;
            default:
                return;
        }
    }

    private void hideStyleView() {
        if (this.styleView.getVisibility() == 0) {
            this.styleView.setVisibility(8);
            LayoutParams lp = (LayoutParams) this.closeView.getLayoutParams();
            lp.weight = 0.5f;
            this.closeView.setLayoutParams(lp);
        }
    }

    private void showStyleView() {
        if (this.styleView.getVisibility() == 8) {
            this.styleView.setVisibility(0);
            LayoutParams lp = (LayoutParams) this.closeView.getLayoutParams();
            lp.weight = 1.0f;
            this.closeView.setLayoutParams(lp);
        }
    }

    public void setActionClickListener(OnActionClickListener listener) {
        this.actionClickListener = listener;
    }

    public void setWindowStateChangeListener(WindowStateChangeListener windowStateChangeListener2) {
        this.windowStateChangeListener = windowStateChangeListener2;
    }

    public void show() {
        setVisibility(0);
        if (this.windowStateChangeListener != null) {
            this.windowStateChangeListener.stateChange(true, DensityUtil.dip2px(getContext(), 222.0f));
        }
    }

    public void hide() {
        setVisibility(8);
        if (this.windowStateChangeListener != null) {
            this.windowStateChangeListener.stateChange(false, 0);
        }
    }

    public boolean isShow() {
        return getVisibility() == 0;
    }
}
