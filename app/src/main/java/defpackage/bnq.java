package defpackage;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.map.core.view.XDrawerLayout;

/* renamed from: bnq reason: default package */
/* compiled from: DrawerLayoutInstaller */
public final class bnq {

    /* renamed from: bnq$a */
    /* compiled from: DrawerLayoutInstaller */
    public static class a {
        public Context a;
        public ViewGroup b;
        public DrawerListener c;
        public View d;
        public XDrawerLayout e;
        public int f = GravityCompat.START;

        public a(Context context) {
            this.a = context;
        }

        private a() {
            throw new RuntimeException("Not supported. Use DrawerBuilder(Context mContext) instead.");
        }
    }
}
