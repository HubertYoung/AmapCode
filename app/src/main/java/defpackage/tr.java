package defpackage;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

/* renamed from: tr reason: default package */
/* compiled from: DriveToast */
public final class tr {
    static View a;
    static View b;

    public static void a(AbstractBasePage abstractBasePage, CharSequence charSequence, int i) {
        a(abstractBasePage, b(abstractBasePage, charSequence, i));
    }

    private static View b(AbstractBasePage abstractBasePage, CharSequence charSequence, int i) {
        TextView textView = (TextView) LayoutInflater.from(abstractBasePage.getContext()).inflate(R.layout.ae_toast_with_image_left, null);
        textView.setText(charSequence);
        Drawable drawable = abstractBasePage.getResources().getDrawable(i);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
        return textView;
    }

    private static void a(final AbstractBasePage abstractBasePage, final View view) {
        final Handler handler = new Handler(abstractBasePage.getContext().getMainLooper());
        handler.post(new Runnable() {
            final WeakReference<AbstractBasePage> a = new WeakReference<>(abstractBasePage);
            final /* synthetic */ int e = 5000;

            public final void run() {
                AbstractBasePage abstractBasePage = (AbstractBasePage) this.a.get();
                if (abstractBasePage != null && abstractBasePage.isAlive()) {
                    final ViewGroup viewGroup = (ViewGroup) abstractBasePage.getContentView();
                    if (viewGroup != null) {
                        final FrameLayout frameLayout = new FrameLayout(abstractBasePage.getContext());
                        if (tr.b != null) {
                            viewGroup.removeView(tr.b);
                        }
                        tr.a = view;
                        tr.b = frameLayout;
                        LayoutParams layoutParams = new LayoutParams(-2, -2);
                        layoutParams.gravity = 81;
                        if (abstractBasePage.getResources().getConfiguration().orientation == 1) {
                            layoutParams.bottomMargin = agn.a(abstractBasePage.getContext(), 120.0f);
                            layoutParams.leftMargin = agn.a(abstractBasePage.getContext(), 32.0f);
                            layoutParams.rightMargin = agn.a(abstractBasePage.getContext(), 32.0f);
                        } else {
                            layoutParams.bottomMargin = agn.a(abstractBasePage.getContext(), 80.0f);
                            layoutParams.leftMargin = agn.a(abstractBasePage.getContext(), 100.0f);
                        }
                        frameLayout.addView(tr.a, layoutParams);
                        viewGroup.addView(frameLayout, new ViewGroup.LayoutParams(-1, -1));
                        handler.postDelayed(new Runnable() {
                            public final void run() {
                                AbstractBasePage abstractBasePage = (AbstractBasePage) AnonymousClass1.this.a.get();
                                if (abstractBasePage != null && abstractBasePage.isAlive() && viewGroup != null) {
                                    viewGroup.removeView(frameLayout);
                                    frameLayout.removeView(tr.a);
                                    tr.a = null;
                                    tr.b = null;
                                }
                            }
                        }, (long) this.e);
                    }
                }
            }
        });
    }
}
