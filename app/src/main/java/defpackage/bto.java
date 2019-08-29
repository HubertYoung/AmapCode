package defpackage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;

/* renamed from: bto reason: default package */
/* compiled from: BaseDialog */
public class bto extends Dialog {
    /* access modifiers changed from: private */
    public OnDismissListener mOnDismissListener;
    /* access modifiers changed from: private */
    public OnShowListener mOnShowListener;

    public bto(@NonNull Context context) {
        super(context);
        initListener();
    }

    public bto(@NonNull Context context, int i) {
        super(context, i);
        initListener();
    }

    protected bto(@NonNull Context context, boolean z, @Nullable OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        initListener();
    }

    private void initListener() {
        super.setOnShowListener(new OnShowListener() {
            public final void onShow(DialogInterface dialogInterface) {
                if (bto.this.mOnShowListener != null) {
                    bto.this.mOnShowListener.onShow(dialogInterface);
                }
                btp.a().a(new WeakReference<>(bto.this));
            }
        });
        super.setOnDismissListener(new OnDismissListener() {
            public final void onDismiss(DialogInterface dialogInterface) {
                if (bto.this.mOnDismissListener != null) {
                    bto.this.mOnDismissListener.onDismiss(dialogInterface);
                }
                btp.a().b(new WeakReference(bto.this));
            }
        });
    }

    public void setOnShowListener(@Nullable OnShowListener onShowListener) {
        this.mOnShowListener = onShowListener;
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }
}
