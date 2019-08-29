package defpackage;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* renamed from: cjs reason: default package */
/* compiled from: CommonUtils */
public final class cjs {
    public static void a(bid bid) {
        if (bid != null) {
            Context context = bid.getContext();
            if (context != null) {
                View contentView = bid.getContentView();
                if (contentView != null) {
                    IBinder windowToken = contentView.getWindowToken();
                    if (windowToken != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
                        if (inputMethodManager != null && inputMethodManager.isActive()) {
                            inputMethodManager.hideSoftInputFromWindow(windowToken, 2);
                        }
                    }
                }
            }
        }
    }
}
