package defpackage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.amap.bundle.appupgrade.NumberProgressBar;
import com.amap.bundle.appupgrade.NumberProgressBar.a;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;

/* renamed from: jl reason: default package */
/* compiled from: DownloadProgressDialog */
public final class jl extends Dialog implements a, jn {
    private boolean a;
    private TextView b;
    private NumberProgressBar c;
    private Button d;

    public final void c() {
    }

    public jl(Context context, boolean z) {
        super(context);
        this.a = z;
    }

    /* access modifiers changed from: protected */
    public final void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(17170445);
        }
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.progress_dialog_layout, null);
        setContentView(inflate);
        this.c = (NumberProgressBar) inflate.findViewById(R.id.progress_bar_horizontal);
        this.d = (Button) inflate.findViewById(R.id.dismiss_dialog_button);
        this.b = (TextView) inflate.findViewById(R.id.progress_text_indicator);
        if (this.a) {
            this.d.setVisibility(0);
            setCancelable(true);
        } else {
            findViewById(R.id.view_bottom_cutline).setVisibility(8);
            this.d.setVisibility(8);
            setCancelable(false);
        }
        this.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                jl.this.cancel();
            }
        });
        this.c.setTrackProgressListener(this);
    }

    public final void a(int i) {
        AMapLog.d("DownloadProgressDialog", "setProgress, ".concat(String.valueOf(i)));
        this.c.setProgress(i);
    }

    public final void a() {
        if (isShowing()) {
            dismiss();
        }
    }

    public final void b() {
        if (isShowing()) {
            dismiss();
        }
    }

    public final void a(int i, int i2) {
        double d2 = ((double) i) / ((double) i2);
        if (d2 < 1.0d) {
            MarginLayoutParams cast = MarginLayoutParams.class.cast(this.b.getLayoutParams());
            cast.leftMargin = (this.c.getLeft() + ((int) (d2 * ((double) this.c.getWidth())))) - (this.b.getWidth() / 2);
            this.b.setLayoutParams(cast);
            this.b.postInvalidate();
        }
        TextView textView = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append("%");
        textView.setText(sb.toString());
    }
}
