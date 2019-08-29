package defpackage;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;

/* renamed from: dee reason: default package */
/* compiled from: AutoDeclareDialog */
public final class dee extends sr {
    public dee(Context context) {
        super(context, R.layout.auto_declare_dialog);
    }

    public final void a() {
        TitleBar titleBar = (TitleBar) this.a.findViewById(R.id.title);
        titleBar.setWidgetVisibility(1, 4);
        titleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                dee.this.b.a();
            }
        });
        ((TextView) this.a.findViewById(R.id.confirm)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                dee.this.b.b();
            }
        });
        ((TextView) this.a.findViewById(R.id.cancel)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                dee.this.b.a();
            }
        });
    }
}
