package com.autonavi.map.permission;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public final class SplashPrivacyPolicyLayout extends FrameLayout implements OnClickListener {
    /* access modifiers changed from: private */
    public b mClickListener;

    static class a extends LinkMovementMethod {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            boolean onTouchEvent = super.onTouchEvent(textView, spannable, motionEvent);
            Selection.removeSelection(spannable);
            return onTouchEvent;
        }
    }

    public interface b {
        void a();

        void b();

        void c();

        void d();
    }

    public SplashPrivacyPolicyLayout(@NonNull Context context) {
        super(context);
        initContentView(context);
    }

    private void initContentView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_splash_privacy_policy, this);
        findViewById(R.id.agree).setOnClickListener(this);
        findViewById(R.id.disagree).setOnClickListener(this);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("您也可以在系统设置中关闭授权，但可能影响部分功能使用。请在使用前查看并同意完整的隐私权政策及服务条款。");
        int indexOf = "您也可以在系统设置中关闭授权，但可能影响部分功能使用。请在使用前查看并同意完整的隐私权政策及服务条款。".indexOf("隐私权政策");
        spannableStringBuilder.setSpan(new ClickableSpan() {
            public final void onClick(@NonNull View view) {
                if (SplashPrivacyPolicyLayout.this.mClickListener != null) {
                    SplashPrivacyPolicyLayout.this.mClickListener.a();
                }
            }

            public final void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
                textPaint.setColor(Color.parseColor("#3377FF"));
            }
        }, indexOf, indexOf + 5, 33);
        int indexOf2 = "您也可以在系统设置中关闭授权，但可能影响部分功能使用。请在使用前查看并同意完整的隐私权政策及服务条款。".indexOf("服务条款");
        spannableStringBuilder.setSpan(new ClickableSpan() {
            public final void onClick(@NonNull View view) {
                if (SplashPrivacyPolicyLayout.this.mClickListener != null) {
                    SplashPrivacyPolicyLayout.this.mClickListener.b();
                }
            }

            public final void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
                textPaint.bgColor = 0;
                textPaint.setColor(Color.parseColor("#3377FF"));
            }
        }, indexOf2, indexOf2 + 4, 33);
        TextView textView = (TextView) findViewById(R.id.policy_desc);
        textView.setMovementMethod(new a(0));
        textView.setText(spannableStringBuilder);
    }

    public final void onClick(View view) {
        if (this.mClickListener != null) {
            if (view.getId() == R.id.agree) {
                this.mClickListener.c();
                return;
            }
            if (view.getId() == R.id.disagree) {
                this.mClickListener.d();
            }
        }
    }

    public final void setOnInternalClickListener(b bVar) {
        this.mClickListener = bVar;
    }
}
