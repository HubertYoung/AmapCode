package defpackage;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseDialog;
import com.autonavi.map.fragmentcontainer.page.IViewLayerExt;
import com.autonavi.minimap.R;

@BundleInterface(apq.class)
/* renamed from: aqx reason: default package */
/* compiled from: AGroupJoinGroupDialog */
public final class aqx extends esi implements apq, IViewLayerExt {
    private static final char[] a = {8230};
    private static final String b = new String(a);
    /* access modifiers changed from: private */
    public int c;
    /* access modifiers changed from: private */
    public defpackage.apq.a e = null;
    /* access modifiers changed from: private */
    public View f = null;
    /* access modifiers changed from: private */
    public bid g = null;
    /* access modifiers changed from: private */
    public boolean h = false;
    private AbstractBaseDialog i = null;
    /* access modifiers changed from: private */
    public String j = null;
    /* access modifiers changed from: private */
    public String k = null;

    /* renamed from: aqx$a */
    /* compiled from: AGroupJoinGroupDialog */
    class a extends AbstractBaseDialog implements OnClickListener {
        public final boolean onBackPressed() {
            return true;
        }

        public final void onConfigurationChanged(Configuration configuration) {
        }

        public final void showBackground(boolean z) {
        }

        public a(bid bid) {
            super(bid);
        }

        public final void onCreate(PageBundle pageBundle) {
            super.onCreate(pageBundle);
            if (pageBundle != null) {
                aqx.this.j = pageBundle.getString("user_name_key", "");
                aqx.this.k = pageBundle.getString("password_key", "");
            } else {
                aqx.this.j = "";
                aqx.this.k = "";
            }
            Context context = aqx.this.g.getContext();
            aqx.this.f = LayoutInflater.from(context).inflate(R.layout.agroup_dialog_join_group_view, null);
            ImageView imageView = (ImageView) aqx.this.f.findViewById(R.id.join_group_icon);
            TextView textView = (TextView) aqx.this.f.findViewById(R.id.join_group_user_name);
            TextView textView2 = (TextView) aqx.this.f.findViewById(R.id.join_group_tips);
            TextView textView3 = (TextView) aqx.this.f.findViewById(R.id.join_group_btn);
            if (aqx.this.c == 2) {
                imageView.setBackgroundResource(R.drawable.agroup_dialog_join_group_icon);
                textView.setText(R.string.agroup_dialog_join_group_unsupport);
                textView2.setText(R.string.agroup_dialog_join_group_tips2);
                textView3.setText(R.string.i_know);
            } else {
                imageView.setBackgroundResource(R.drawable.agroup_dialog_join_group_icon);
                textView.setText(aqx.a(context, aqx.this.j, textView.getTextSize()));
                textView2.setText(R.string.agroup_dialog_join_group_tips);
                textView3.setText(R.string.agroup_dialog_join_group_btn);
            }
            aqx.this.f.setOnClickListener(this);
            aqx.this.f.findViewById(R.id.close_btn).setOnClickListener(this);
            aqx.this.f.findViewById(R.id.join_group_btn).setOnClickListener(this);
            if (aqx.this.e != null) {
                aqx.this.e.a((AbstractBaseDialog) this);
            }
        }

        public final View getView() {
            return aqx.this.f;
        }

        public final void onClick(View view) {
            if (view.getId() != R.id.close_btn) {
                if (view == aqx.this.f) {
                    if (!aqx.this.h) {
                        return;
                    }
                } else if (view.getId() != R.id.join_group_btn) {
                    return;
                } else {
                    if (aqx.this.c == 1 && aqx.this.e != null) {
                        aqx.this.e.a(aqx.this.k);
                    }
                }
            }
            dismiss();
        }
    }

    public final boolean isDismiss() {
        return false;
    }

    public final void a(PageBundle pageBundle) {
        this.i.onCreate(pageBundle);
    }

    public final void a() {
        this.h = false;
    }

    public final void a(defpackage.apq.a aVar) {
        this.e = aVar;
    }

    public final void b() {
        this.i.show();
    }

    public final void a(bid bid) {
        this.g = bid;
        this.c = 1;
        this.i = new a(bid);
    }

    static /* synthetic */ String a(Context context, String str, float f2) {
        String string = context.getString(R.string.agroup_dialog_join_group_default_name);
        if (TextUtils.isEmpty(str)) {
            return string;
        }
        String string2 = context.getString(R.string.agroup_dialog_join_group_name_suffix);
        char[] charArray = string2.toCharArray();
        Paint paint = new Paint();
        paint.setTextSize(f2);
        int measureText = (int) paint.measureText(charArray, 0, 1);
        if (measureText <= 0) {
            return string;
        }
        int a2 = (agn.a(context, 240.0f) / measureText) - string2.length();
        int length = b.length();
        if (a2 <= length) {
            return string;
        }
        if (a2 >= str.length()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(string2);
            return sb.toString();
        }
        String substring = str.substring(0, a2 - length);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(substring);
        sb2.append(b);
        sb2.append(string2);
        return sb2.toString();
    }
}
