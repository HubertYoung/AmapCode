package defpackage;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.minimap.R;

/* renamed from: dza reason: default package */
/* compiled from: RouteBusIrregularTimeDialog */
public final class dza extends Dialog {
    private ViewGroup a;
    private View b;
    private ViewGroup c;
    private View d;
    private ViewGroup e;
    private BusPathSection f;

    public dza(Context context, BusPathSection busPathSection) {
        super(context);
        if (getWindow() != null) {
            getWindow().requestFeature(1);
        }
        this.f = busPathSection;
    }

    /* access modifiers changed from: protected */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.route_bus_result_detail_irregular_time_dialog);
        this.a = (ViewGroup) findViewById(R.id.normal_time_list);
        this.b = findViewById(R.id.workday_container);
        this.c = (ViewGroup) findViewById(R.id.workday_time_list);
        this.d = findViewById(R.id.holiday_container);
        this.e = (ViewGroup) findViewById(R.id.holiday_time_list);
        BusPathSection busPathSection = this.f;
        if (!(busPathSection == null || busPathSection.irregulartime == null)) {
            this.a.setVisibility(8);
            this.b.setVisibility(8);
            this.d.setVisibility(8);
            if (!TextUtils.isEmpty(busPathSection.irregulartime.normalday)) {
                String[] split = busPathSection.irregulartime.normalday.split(",");
                if (split.length > 0) {
                    a(this.a, split);
                    this.a.setVisibility(0);
                }
            } else {
                if (!TextUtils.isEmpty(busPathSection.irregulartime.workday)) {
                    String[] split2 = busPathSection.irregulartime.workday.split(",");
                    if (split2.length > 0) {
                        a(this.c, split2);
                        this.b.setVisibility(0);
                    }
                }
                if (!TextUtils.isEmpty(busPathSection.irregulartime.holiday)) {
                    String[] split3 = busPathSection.irregulartime.holiday.split(",");
                    if (split3.length > 0) {
                        a(this.e, split3);
                        this.d.setVisibility(0);
                    }
                }
            }
        }
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.transparent);
            LayoutParams attributes = window.getAttributes();
            attributes.gravity = 80;
            attributes.width = -1;
            attributes.height = -2;
            window.setAttributes(attributes);
        }
    }

    private void a(ViewGroup viewGroup, String[] strArr) {
        viewGroup.removeAllViews();
        for (String text : strArr) {
            TextView textView = new TextView(getContext());
            textView.setTextSize(1, 14.0f);
            textView.setTextColor(-10066330);
            textView.setText(text);
            viewGroup.addView(textView);
        }
    }
}
