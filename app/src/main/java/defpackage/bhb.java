package defpackage;

import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;

/* renamed from: bhb reason: default package */
/* compiled from: PayTipsController */
public final class bhb {
    public static void a(TextView textView, TextView textView2, PayforNaviData payforNaviData, final AbstractBasePage abstractBasePage) {
        if (textView2 != null) {
            textView2.setText(Html.fromHtml(abstractBasePage.getString(R.string.pay_tips_out)));
            textView2.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ConfigerHelper.getInstance().getActivitiesUrl());
                    sb.append("/activity/payError/rule.html");
                    aja aja = new aja(sb.toString());
                    aja.b = new ajf();
                    aix aix = (aix) a.a.a(aix.class);
                    if (aix != null) {
                        aix.a((bid) abstractBasePage, aja);
                    }
                }
            });
        }
        if (textView != null) {
            if (payforNaviData == null || !PayforNaviData.isNeedShowMoney(payforNaviData.moneyMaypayed)) {
                textView.setText(R.string.activities_apply_maypayed_without_moneycount);
            } else {
                textView.setText(abstractBasePage.getString(R.string.activities_apply_maypayed_money, Double.valueOf(payforNaviData.moneyMaypayed)));
            }
        }
    }
}
