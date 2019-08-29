package com.autonavi.minimap.life.order.base.page;

import android.os.CountDownTimer;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.adapter.OrderListAdapterCommonOld;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"})
public abstract class BaseOrderPage<Presenter> extends AbstractBasePage<Presenter> implements OnClickListener, LocationNone, d<ListView>, dpq {
    protected OrderListAdapterCommonOld a;
    protected Button b;
    protected a c;
    protected ProgressDlg d;
    protected EditText e;
    protected EditText f;
    protected TextView g;
    /* access modifiers changed from: private */
    public CheckBox h;

    final class VerifyCallback implements Callback<dpn> {
        public final void error(Throwable th, boolean z) {
        }

        private VerifyCallback() {
        }

        public final void callback(dpn dpn) {
            BaseOrderPage.b(BaseOrderPage.this);
            if (dpn.c == 1) {
                ToastHelper.showToast(BaseOrderPage.this.getString(R.string.life_order_phone_code_success));
                return;
            }
            BaseOrderPage.this.c.cancel();
            BaseOrderPage.this.c.onFinish();
            ToastHelper.showToast(dpn.d);
        }
    }

    public class a extends CountDownTimer {
        final /* synthetic */ BaseOrderPage a;

        public final void onFinish() {
            this.a.b.setEnabled(true);
            this.a.g.setVisibility(8);
        }

        public final void onTick(long j) {
            this.a.g.setVisibility(0);
            this.a.b.setEnabled(false);
            TextView textView = this.a.g;
            StringBuilder sb = new StringBuilder();
            sb.append(j / 1000);
            sb.append("秒后可重试");
            textView.setText(sb.toString());
        }
    }

    static /* synthetic */ void b(BaseOrderPage baseOrderPage) {
        if (baseOrderPage.d != null) {
            baseOrderPage.d.dismiss();
        }
    }
}
