package com.alipay.mobile.antui.input;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUButton;
import java.util.Timer;

public class AUTextCodeInputBox extends AUInputBox {
    private static final int STOPMESSAGE = 2;
    private static final int UPDATEMESSAGE = 1;
    /* access modifiers changed from: private */
    public int currentSecond;
    /* access modifiers changed from: private */
    public SendResultCallback mResultCallback;
    private Timer mScheduleTimer;
    /* access modifiers changed from: private */
    public AUButton mSendButton;
    private SendButtonEnableChecker mSendButtonEnableChecker;
    private String mSendButtonText;
    /* access modifiers changed from: private */
    public String mSendButtonTextRetry;
    /* access modifiers changed from: private */
    public OnSendCallback mSendCallback;
    private int mTimeInterval;
    /* access modifiers changed from: private */
    public final Handler mTimerHanlder;
    private View rightView;
    /* access modifiers changed from: private */
    public boolean sendButtonInnerCheckEnable;
    /* access modifiers changed from: private */
    public String timeStr;

    public interface SendButtonEnableChecker {
        boolean checkIsEnabled();
    }

    public AUTextCodeInputBox(Context context) {
        this(context, null);
    }

    public AUTextCodeInputBox(Context context, AttributeSet set) {
        super(context, set);
        this.mTimerHanlder = new h(this, 0);
        this.sendButtonInnerCheckEnable = true;
        this.mTimeInterval = 60;
        this.rightView = LayoutInflater.from(context).inflate(R.layout.au_text_code_inputbox, null);
        this.mInputContainer.addView(this.rightView);
        if (TextUtils.isEmpty(getInputName().getText())) {
            setInputName(getResources().getString(R.string.checkCode));
        }
        if (TextUtils.isEmpty(getInputEdit().getHint())) {
            setHint(getResources().getString(R.string.mobile_checkCode));
        }
        resetTime();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSendButton = (AUButton) findViewById(R.id.checkCodeSendButton);
        this.timeStr = getContext().getText(R.string.timeAfter);
        this.mResultCallback = new g(this, 0);
        this.rightView.setOnClickListener(new e(this));
        if (!TextUtils.isEmpty(this.mSendButtonText)) {
            this.mSendButton.setText(this.mSendButtonText);
        }
    }

    public void setOnSendCallback(OnSendCallback callback) {
        if (callback != null) {
            this.mSendCallback = callback;
        }
    }

    /* access modifiers changed from: private */
    public void resetTime() {
        this.currentSecond = this.mTimeInterval;
    }

    public void currentSecond2Zero() {
        this.currentSecond = 0;
    }

    public void setCurrentSecond(int current) {
        this.currentSecond = current;
    }

    public int getCurrentSecond() {
        return this.currentSecond;
    }

    public AUButton getSendCodeButton() {
        return this.mSendButton;
    }

    public void setSendButtonText(String sendButtonText) {
        this.mSendButtonText = sendButtonText;
        this.mSendButton.setText(this.mSendButtonText);
    }

    public void setSendButtonTextRetry(String sendButtonTextRetry) {
        this.mSendButtonTextRetry = sendButtonTextRetry;
    }

    public void setTimeInterval(int timeInterval) {
        this.mTimeInterval = timeInterval;
        resetTime();
    }

    public void releaseTimer() {
        if (this.mScheduleTimer != null) {
            this.mScheduleTimer.cancel();
            this.mScheduleTimer = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        releaseTimer();
        super.onDetachedFromWindow();
    }

    public void scheduleTimer() {
        this.sendButtonInnerCheckEnable = false;
        updateSendButtonEnableStatus();
        if (this.mScheduleTimer != null) {
            this.mScheduleTimer.cancel();
            this.mScheduleTimer = null;
        }
        this.mScheduleTimer = new Timer();
        this.mScheduleTimer.schedule(new f(this), 0, 1000);
    }

    public void setSendButtonEnableChecker(SendButtonEnableChecker checker) {
        this.mSendButtonEnableChecker = checker;
    }

    public void updateSendButtonEnableStatus() {
        updateSendButtonEnableStatus(this.sendButtonInnerCheckEnable);
    }

    public void updateSendButtonEnableStatus(boolean isEnable) {
        this.sendButtonInnerCheckEnable = isEnable;
        if (this.mSendButtonEnableChecker == null || this.mSendButtonEnableChecker.checkIsEnabled()) {
            this.rightView.setEnabled(isEnable);
            this.mSendButton.setEnabled(isEnable);
            return;
        }
        this.rightView.setEnabled(false);
        this.mSendButton.setEnabled(false);
    }

    public SendResultCallback getSendResultCallback() {
        return this.mResultCallback;
    }
}
