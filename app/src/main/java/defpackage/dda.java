package defpackage;

import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseDialog;
import com.autonavi.map.fragmentcontainer.page.IViewLayerExt;
import com.autonavi.minimap.R;

/* renamed from: dda reason: default package */
/* compiled from: PassPhraseAlertDialog */
public final class dda extends AbstractBaseDialog implements OnClickListener, IViewLayerExt {
    boolean a = false;
    private View b;
    private bid c;
    private int d;

    public final boolean isDismiss() {
        return false;
    }

    public final boolean onBackPressed() {
        return true;
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void showBackground(boolean z) {
    }

    public dda(bid bid, int i) {
        super(bid);
        this.c = bid;
        this.d = i;
    }

    public final void onCreate(PageBundle pageBundle) {
        super.onCreate(pageBundle);
        this.b = LayoutInflater.from(this.c.getContext()).inflate(R.layout.passphrase_alert_view, null);
        ImageView imageView = (ImageView) this.b.findViewById(R.id.alert_icon);
        TextView textView = (TextView) this.b.findViewById(R.id.content_tv);
        TextView textView2 = (TextView) this.b.findViewById(R.id.alert_tips);
        TextView textView3 = (TextView) this.b.findViewById(R.id.i_know_btn);
        if (this.d == 1) {
            imageView.setImageResource(R.drawable.passphrase_alert_agroup_title);
            textView.setText(R.string.passphrase_dialog_join_group_unsupport);
        } else {
            imageView.setImageResource(R.drawable.passphrase_alert_other_title);
            textView.setText(R.string.passphrase_dialog_other_unsupport);
        }
        textView2.setText(R.string.passphrase_dialog_tips2);
        textView3.setText(R.string.i_know);
        this.b.setOnClickListener(this);
        this.b.findViewById(R.id.close_btn).setOnClickListener(this);
        this.b.findViewById(R.id.i_know_btn).setOnClickListener(this);
    }

    public final View getView() {
        return this.b;
    }

    public final void onClick(View view) {
        if (view.getId() == R.id.close_btn || view.getId() == R.id.i_know_btn) {
            dismiss();
            return;
        }
        if (view == this.b && this.a) {
            dismiss();
        }
    }
}
