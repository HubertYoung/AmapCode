package defpackage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import com.feather.support.SoftInputAdjustRootLinearLayout;
import java.lang.ref.WeakReference;

/* renamed from: btn reason: default package */
/* compiled from: BaseAlertDialog */
public class btn extends AlertDialog {
    /* access modifiers changed from: private */
    public OnShowListener a;
    /* access modifiers changed from: private */
    public OnDismissListener b;

    public btn(Context context) {
        super(context);
        super.setOnShowListener(new OnShowListener() {
            public final void onShow(DialogInterface dialogInterface) {
                if (btn.this.a != null) {
                    btn.this.a.onShow(dialogInterface);
                }
                btp.a().a(new WeakReference<>(btn.this));
            }
        });
        super.setOnDismissListener(new OnDismissListener() {
            public final void onDismiss(DialogInterface dialogInterface) {
                if (btn.this.b != null) {
                    btn.this.b.onDismiss(dialogInterface);
                }
                btp.a().b(new WeakReference(btn.this));
            }
        });
    }

    public void setContentView(View view) {
        SoftInputAdjustRootLinearLayout softInputAdjustRootLinearLayout = new SoftInputAdjustRootLinearLayout(getContext());
        softInputAdjustRootLinearLayout.addView(view);
        view.setFitsSystemWindows(true);
        super.setContentView(softInputAdjustRootLinearLayout);
    }

    public void setContentView(int i) {
        SoftInputAdjustRootLinearLayout softInputAdjustRootLinearLayout = new SoftInputAdjustRootLinearLayout(getContext());
        LayoutInflater.from(getContext()).inflate(i, softInputAdjustRootLinearLayout, true).setFitsSystemWindows(true);
        super.setContentView(softInputAdjustRootLinearLayout);
    }

    public void setOnShowListener(@Nullable OnShowListener onShowListener) {
        this.a = onShowListener;
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.b = onDismissListener;
    }
}
