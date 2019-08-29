package defpackage;

import android.view.View;
import android.view.View.OnClickListener;

/* renamed from: dgb reason: default package */
/* compiled from: BottomDialogBuilder */
public final class dgb implements OnClickListener {
    private a a;

    /* renamed from: dgb$a */
    /* compiled from: BottomDialogBuilder */
    public interface a {
        void onClickDialogItem(int i, Object obj);
    }

    /* renamed from: dgb$b */
    /* compiled from: BottomDialogBuilder */
    static class b {
        public final int a;
        public final Object b;
    }

    public final void onClick(View view) {
        if (this.a != null) {
            b bVar = (b) view.getTag();
            this.a.onClickDialogItem(bVar.a, bVar.b);
        }
    }
}
