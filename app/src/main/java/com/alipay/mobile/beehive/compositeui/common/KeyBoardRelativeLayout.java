package com.alipay.mobile.beehive.compositeui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.api.BeehiveConstant;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class KeyBoardRelativeLayout extends AURelativeLayout {
    private static final int MIN_HEIGHT = 150;
    /* access modifiers changed from: private */
    public boolean isInit = true;
    /* access modifiers changed from: private */
    public int mDefaultHeight = 0;
    private int mMinKeyBoardHeight;
    private OnSoftKeyboardListener onSoftKeyboardListener;
    /* access modifiers changed from: private */
    public int screenHeight;

    public interface OnSoftKeyboardListener {
        void onKeyBoardHidden();

        void onKeyBoardShown(int i);
    }

    public KeyBoardRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KeyBoardRelativeLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public final void onGlobalLayout() {
                if (KeyBoardRelativeLayout.this.isInit) {
                    KeyBoardRelativeLayout.this.isInit = false;
                    KeyBoardRelativeLayout.this.mDefaultHeight = KeyBoardRelativeLayout.this.getMeasuredHeight();
                    KeyBoardRelativeLayout.this.screenHeight = KeyBoardRelativeLayout.this.getRootView().getHeight();
                }
            }
        });
        this.mMinKeyBoardHeight = (int) getContext().getResources().getDimension(R.dimen.emotion_default_height);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            if (this.onSoftKeyboardListener != null) {
                int newSpec = MeasureSpec.getSize(heightMeasureSpec);
                int oldSpec = getMeasuredHeight();
                if (this.mDefaultHeight != 0) {
                    if (oldSpec > newSpec) {
                        if (this.mDefaultHeight <= oldSpec) {
                            this.mDefaultHeight = oldSpec;
                        } else if (this.mDefaultHeight - oldSpec < 150) {
                            LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG + "_keyborad", "屏幕微调,default:" + this.mDefaultHeight + " oldSpec:" + oldSpec);
                            this.mDefaultHeight = oldSpec;
                        }
                        LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG + "_keyborad", "窗口缩小：default=" + this.mDefaultHeight + "; newHeight=" + newSpec + "; 屏幕高度：" + this.screenHeight);
                        showKeyBoard(this.mDefaultHeight - newSpec);
                    } else if (oldSpec < newSpec) {
                        if (newSpec >= this.mDefaultHeight) {
                            this.mDefaultHeight = newSpec;
                            this.onSoftKeyboardListener.onKeyBoardHidden();
                        } else if (this.mDefaultHeight - newSpec < 150) {
                            LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG + "_keyborad", "屏幕微调,default:" + this.mDefaultHeight + " newSpec:" + newSpec);
                            this.mDefaultHeight = newSpec;
                            this.onSoftKeyboardListener.onKeyBoardHidden();
                        } else {
                            LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG + "_keyborad", "窗口放大：default=" + this.mDefaultHeight + "; newHeight=" + newSpec + "; 屏幕高度：" + this.screenHeight);
                            showKeyBoard(this.mDefaultHeight - newSpec);
                        }
                    }
                }
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (Exception e) {
            setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        }
    }

    public void showKeyBoard(int height) {
        if (this.screenHeight >= this.mDefaultHeight) {
            int height2 = Math.min(height, (this.screenHeight * 3) / 4);
            if (height2 >= 150) {
                height = Math.max(height2, this.mMinKeyBoardHeight);
            } else {
                return;
            }
        }
        this.onSoftKeyboardListener.onKeyBoardShown(height);
    }

    public final void setOnSoftKeyboardListener(OnSoftKeyboardListener listener) {
        this.onSoftKeyboardListener = listener;
    }
}
