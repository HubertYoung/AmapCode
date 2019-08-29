package defpackage;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.autonavi.minimap.R;

/* renamed from: def reason: default package */
/* compiled from: AutoDisconnectionDialog */
public final class def extends sr {
    public ss c;
    private Animation d = null;
    private Animation e = null;
    /* access modifiers changed from: private */
    public boolean f = false;

    public def(Context context) {
        super(context, R.layout.auto_disconnection_dialog_layout);
        this.d = AnimationUtils.loadAnimation(context, R.anim.autonavi_bottom_in);
        this.e = AnimationUtils.loadAnimation(context, R.anim.autonavi_bottom_out);
        this.e.setAnimationListener(new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
                def.this.f = true;
            }

            public final void onAnimationEnd(Animation animation) {
                def.this.f = false;
                def.this.c.a();
            }
        });
    }

    public final void a() {
        if (!this.f) {
            this.a.startAnimation(this.e);
        }
    }

    public final void b() {
        this.a.startAnimation(this.d);
        this.a.setOnClickListener(null);
        ((TextView) this.a.findViewById(R.id.auto_confirm)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                def.this.a();
            }
        });
        this.a.findViewById(R.id.content_banner).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                def.this.a();
            }
        });
    }
}
