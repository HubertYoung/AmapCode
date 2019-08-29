package com.ali.user.mobile.common.ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.ui.widget.AUBoxInput;
import com.ali.user.mobile.ui.widget.WidgetUtil;
import com.ali.user.mobile.util.ResizeScrollView;
import com.ali.user.mobile.util.ShowRegionHelper;
import com.alipay.mobile.h5container.api.H5PageData;
import java.util.ArrayList;

public abstract class AbsVerifySmsActivity extends BaseActivity {
    private static final int sCountDown = 60;
    private static final String sTag = "Reg_absSms";
    /* access modifiers changed from: private */
    public TextView mCountDown;
    /* access modifiers changed from: private */
    public int mCountDownStart;
    private ValueAnimator mCounter;
    private TextView mError;
    /* access modifiers changed from: private */
    public AUBoxInput mInput;
    private final AnimatorListener mLifeListener = new AnimatorListener() {
        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
            if (AbsVerifySmsActivity.this.mCountDown == null) {
                AliUserLog.d(AbsVerifySmsActivity.sTag, "start, null count down tv");
                return;
            }
            AbsVerifySmsActivity.this.mCountDown.setText(AbsVerifySmsActivity.this.getResources().getString(R.string.bY, new Object[]{Integer.valueOf(AbsVerifySmsActivity.this.mCountDownStart)}));
            AbsVerifySmsActivity.this.mCountDown.setTextColor(AbsVerifySmsActivity.this.getResources().getColor(R.color.E));
            AbsVerifySmsActivity.this.mCountDown.setOnClickListener(null);
            AbsVerifySmsActivity.this.mCountDown.setPadding(1, 0, 1, 0);
        }

        public void onAnimationEnd(Animator animator) {
            if (AbsVerifySmsActivity.this.mCountDown == null) {
                AliUserLog.d(AbsVerifySmsActivity.sTag, "end, null count down tv");
                return;
            }
            AbsVerifySmsActivity.this.mCountDown.setText(R.string.bW);
            int e = UIConfigManager.e();
            if (Integer.MAX_VALUE == e) {
                AliUserLog.d(AbsVerifySmsActivity.sTag, "end, default color");
                e = AbsVerifySmsActivity.this.getResources().getColor(R.color.k);
            }
            AbsVerifySmsActivity.this.mCountDown.setTextColor(e);
            AbsVerifySmsActivity.this.mCountDown.setOnClickListener(AbsVerifySmsActivity.this.mResendListener);
        }
    };
    /* access modifiers changed from: private */
    public final OnClickListener mResendListener = new OnClickListener() {
        public void onClick(View view) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(AbsVerifySmsActivity.this.getString(R.string.bX));
            AbsVerifySmsActivity.this.showListDialog(arrayList);
        }
    };
    /* access modifiers changed from: private */
    public ResizeScrollView mScrollView;
    private final AnimatorUpdateListener mUpdater = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (AbsVerifySmsActivity.this.mCountDown == null) {
                AliUserLog.d(AbsVerifySmsActivity.sTag, "update, null count down tv");
                return;
            }
            Integer num = (Integer) valueAnimator.getAnimatedValue();
            if (!num.equals(AbsVerifySmsActivity.this.mCountDown.getTag())) {
                AbsVerifySmsActivity.this.mCountDown.setText(AbsVerifySmsActivity.this.getResources().getString(R.string.bY, new Object[]{Integer.valueOf(num.intValue())}));
                StringBuilder sb = new StringBuilder("update ");
                sb.append(AbsVerifySmsActivity.this.mCountDown.getText());
                AliUserLog.c(AbsVerifySmsActivity.sTag, sb.toString());
                AbsVerifySmsActivity.this.mCountDown.setTextColor(AbsVerifySmsActivity.this.getResources().getColor(R.color.E));
                AbsVerifySmsActivity.this.mCountDown.setTag(num);
                AbsVerifySmsActivity.this.mCountDown.invalidate();
            }
        }
    };

    /* access modifiers changed from: protected */
    public void onGoBack() {
    }

    /* access modifiers changed from: protected */
    public void onWait() {
    }

    public abstract void sendSms();

    public abstract void verifySms(String str);

    public void performDialogAction(String str) {
        if (getString(R.string.bX).equals(str)) {
            hideHints();
            sendSms();
            callUpKeyboard();
            return;
        }
        super.performDialogAction(str);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.i);
        initView();
        startCountDown();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.mScrollView != null) {
            this.mScrollView.postDelayed(new Runnable() {
                public void run() {
                    AbsVerifySmsActivity.this.mScrollView.forceLayout();
                }
            }, 400);
        }
    }

    public void onDestroy() {
        if (this.mCounter != null) {
            this.mCounter.removeAllUpdateListeners();
            this.mCounter.removeAllListeners();
            this.mCounter.cancel();
        }
        super.onDestroy();
    }

    private void initView() {
        initTitleBar();
        initTip();
        initInput();
        initError();
        initCountDown();
        initWrapper();
    }

    private void initWrapper() {
        this.mScrollView = (ResizeScrollView) findViewById(R.id.bz);
        new ShowRegionHelper(this.mScrollView).a(this.mInput, this.mCountDown, true);
    }

    private void initError() {
        this.mError = (TextView) findViewById(R.id.bh);
    }

    private void initTitleBar() {
        APTitleBar aPTitleBar = (APTitleBar) findViewById(R.id.cE);
        if (aPTitleBar.getTitlebarBg() != null) {
            aPTitleBar.getTitlebarBg().setBackgroundColor(-1);
        }
        UIConfigManager.a(aPTitleBar);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initTip() {
        /*
            r3 = this;
            com.ali.user.mobile.register.RegContext r0 = com.ali.user.mobile.register.RegContext.a()
            java.lang.String r1 = ""
            com.ali.user.mobile.register.store.ActionCenter r2 = r0.c
            if (r2 == 0) goto L_0x0021
            com.ali.user.mobile.register.store.ActionCenter r0 = r0.c
            com.ali.user.mobile.register.model.State r0 = r0.a()
            if (r0 == 0) goto L_0x0021
            com.ali.user.mobile.register.Account r2 = r0.a()
            if (r2 == 0) goto L_0x0021
            com.ali.user.mobile.register.Account r0 = r0.a()
            java.lang.String r1 = r0.accountForDisplay()
            goto L_0x0028
        L_0x0021:
            java.lang.String r0 = "Reg_absSms"
            java.lang.String r2 = "no account for display"
            com.ali.user.mobile.log.AliUserLog.d(r0, r2)
        L_0x0028:
            boolean r0 = com.ali.user.mobile.util.StringUtil.e(r1)
            if (r0 == 0) goto L_0x0033
            r0 = 4
            java.lang.String r1 = com.ali.user.mobile.util.StringUtil.a(r1, r0)
        L_0x0033:
            r3.initTip(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.common.ui.AbsVerifySmsActivity.initTip():void");
    }

    /* access modifiers changed from: protected */
    public void initTip(String str) {
        String string = getResources().getString(R.string.bZ, new Object[]{str});
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        int indexOf = string.indexOf(str);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(19, true), indexOf, str.length() + indexOf, 17);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.z)), indexOf, str.length() + indexOf, 17);
        spannableStringBuilder.setSpan(new StyleSpan(1), indexOf, str.length() + indexOf, 17);
        ((TextView) findViewById(R.id.bj)).setText(spannableStringBuilder);
    }

    private void initInput() {
        this.mInput = (AUBoxInput) findViewById(R.id.bi);
        this.mInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable != null && !TextUtils.isEmpty(editable.toString())) {
                    AbsVerifySmsActivity.this.hideError();
                }
                if (AbsVerifySmsActivity.this.mInput != null && AbsVerifySmsActivity.this.mInput.isFull()) {
                    AbsVerifySmsActivity.this.closeInputMethod(AbsVerifySmsActivity.this.mInput);
                    AbsVerifySmsActivity.this.hideHints();
                    AbsVerifySmsActivity.this.verifySms(AbsVerifySmsActivity.this.mInput.getText().toString());
                }
            }
        });
        callUpKeyboard();
    }

    public void hideHints() {
        if (this.mCountDown != null) {
            this.mCountDown.setVisibility(8);
        }
        if (this.mError != null) {
            this.mError.setVisibility(4);
        }
    }

    /* access modifiers changed from: protected */
    public void callUpKeyboard() {
        this.mInput.performClick();
        showInputMethodPannel(this.mInput.getRealInput());
    }

    public void onBackPressed() {
        closeInputMethod(this.mInput);
        exitSmsVerifyAlert();
    }

    private void initCountDown() {
        this.mCountDown = (TextView) findViewById(R.id.bg);
    }

    /* access modifiers changed from: protected */
    public void startCountDown() {
        this.mCountDownStart = 60;
        Intent intent = getIntent();
        if (intent != null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            try {
                uptimeMillis = intent.getLongExtra(H5PageData.KEY_UC_START, SystemClock.uptimeMillis());
            } catch (Throwable th) {
                AliUserLog.b(sTag, "get intent ", th);
            }
            intent.removeExtra(H5PageData.KEY_UC_START);
            this.mCountDownStart = 60 - ((int) ((SystemClock.uptimeMillis() - uptimeMillis) / 1000));
            StringBuilder sb = new StringBuilder("start count ");
            sb.append(this.mCountDownStart);
            AliUserLog.c(sTag, sb.toString());
        }
        this.mCounter = ValueAnimator.ofInt(new int[]{this.mCountDownStart, 0});
        this.mCounter.setInterpolator(new LinearInterpolator());
        this.mCounter.setDuration((long) (this.mCountDownStart * 1000));
        this.mCounter.addUpdateListener(this.mUpdater);
        this.mCounter.addListener(this.mLifeListener);
        this.mCounter.start();
    }

    private void exitSmsVerifyAlert() {
        alert(getString(R.string.cC), "", getString(R.string.cM), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                AbsVerifySmsActivity.this.onWait();
                AbsVerifySmsActivity.this.callUpKeyboard();
            }
        }, getString(R.string.cG), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                AbsVerifySmsActivity.this.onGoBack();
                AbsVerifySmsActivity.this.finish();
            }
        });
    }

    public void showError(String str) {
        if (this.mError != null) {
            if (!TextUtils.isEmpty(str)) {
                this.mError.setText(str);
            }
            this.mError.setVisibility(0);
            WidgetUtil.a(this.mError);
            clearInput();
        }
    }

    public void hideError() {
        if (this.mError != null) {
            this.mError.setVisibility(4);
        }
    }

    public void clearInput() {
        if (this.mInput != null) {
            this.mInput.clear();
            this.mInput.performClick();
        }
    }

    public void showHint() {
        if (this.mCountDown != null) {
            this.mCountDown.setVisibility(0);
        }
    }
}
