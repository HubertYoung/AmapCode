package defpackage;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.autonavi.minimap.R;

/* renamed from: deg reason: default package */
/* compiled from: AutoLimitionDialog */
public final class deg extends sr {
    public ss c;
    private Animation d = null;
    private Animation e = null;
    /* access modifiers changed from: private */
    public boolean f = false;

    public deg(Context context) {
        super(context, R.layout.auto_bluetooth_connection_limition);
        this.d = AnimationUtils.loadAnimation(context, R.anim.autonavi_bottom_in);
        this.e = AnimationUtils.loadAnimation(context, R.anim.autonavi_bottom_out);
        this.e.setAnimationListener(new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
                deg.this.f = true;
            }

            public final void onAnimationEnd(Animation animation) {
                deg.this.f = false;
                deg.this.c.a();
            }
        });
    }

    public final void a() {
        this.a.startAnimation(this.d);
        this.a.setOnClickListener(null);
        SpannableString spannableString = new SpannableString("Wi-Fi连接方式：请在车机版“更多>车机互联”页面底部点击“解除绑定”后，再按照说明进行连接");
        spannableString.setSpan(new ForegroundColorSpan(-16739841), 16, 23, 33);
        spannableString.setSpan(new ForegroundColorSpan(-16739841), 31, 35, 33);
        ((TextView) this.a.findViewById(R.id.textview_bluetooth_readme)).setText(spannableString);
        ((TextView) this.a.findViewById(R.id.auto_confirm)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                deg.this.b();
            }
        });
        this.a.findViewById(R.id.content_banner).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                deg.this.b();
            }
        });
    }

    public final void b() {
        if (!this.f) {
            this.a.startAnimation(this.e);
        }
    }
}
