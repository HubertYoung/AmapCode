package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.lang.ref.WeakReference;

/* renamed from: dsz reason: default package */
/* compiled from: OrderErrorBiz */
public final class dsz {
    public WeakReference<bid> a;

    public dsz(bid bid) {
        this.a = new WeakReference<>(bid);
    }

    public final void a(String str, @NonNull final String str2, @NonNull String str3, @NonNull String str4, final int i) {
        final bid bid = (bid) this.a.get();
        if (bid != null) {
            a a2 = new a(bid.getContext()).a((CharSequence) str).b((CharSequence) str4, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    bid.dismissViewLayer(alertView);
                }
            }).a((CharSequence) str3, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    if (bid.isAlive()) {
                        bid.dismissViewLayer(alertView);
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str2));
                        if (i == 10080 && bid.getClass().getSimpleName().equals("AmapSettingPage")) {
                            intent.putExtra("param_no_need_remove_page", true);
                        }
                        bid.startScheme(intent);
                    }
                }
            });
            a2.b = new a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            a2.c = new a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            bid.showViewLayer(a2.a(false).a());
        }
    }

    public final void a(String str) {
        final bid bid = (bid) this.a.get();
        if (bid != null) {
            a a2 = new a(bid.getContext()).a((CharSequence) str).a((CharSequence) bid.getContext().getString(R.string.i_know), (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    bid.dismissViewLayer(alertView);
                }
            });
            a2.b = new a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            a2.c = new a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            bid.showViewLayer(a2.a(false).a());
        }
    }
}
