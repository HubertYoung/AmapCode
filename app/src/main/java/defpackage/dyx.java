package defpackage;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.map.widget.wheel.TimePickerWidgetView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.CustomTimePickerAdapter;
import com.autonavi.minimap.widget.NumericTimePickerAdapter;

/* renamed from: dyx reason: default package */
/* compiled from: RealTimePickerDialog */
public final class dyx extends AlertDialog implements OnClickListener {
    public a a;
    public String b;
    private TimePickerWidgetView c;
    private TimePickerWidgetView d;
    private TimePickerWidgetView e;
    private TextView f;
    private TextView g;

    /* renamed from: dyx$a */
    /* compiled from: RealTimePickerDialog */
    public interface a {
        void a(String str, String str2, String str3);
    }

    public dyx(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.busline_attention_settingtime_dialog);
        this.c = (TimePickerWidgetView) findViewById(R.id.fromto_route_timepicker_day);
        this.d = (TimePickerWidgetView) findViewById(R.id.fromto_route_timepicker_hour);
        this.e = (TimePickerWidgetView) findViewById(R.id.fromto_route_timepicker_mins);
        this.f = (TextView) findViewById(R.id.tv_cancle);
        this.g = (TextView) findViewById(R.id.tv_sure);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
        String[] split = this.b.split(":");
        int b2 = ahh.b(split[0].replace(Token.SEPARATOR, "").trim());
        int b3 = ahh.b(split[1]);
        this.c.setCyclic(false);
        this.c.setAdapter(new dya());
        if (b2 >= 13 || b2 == 0) {
            this.c.setCurrentItem(1);
        } else {
            this.c.setCurrentItem(0);
        }
        if (b2 > 12) {
            b2 -= 12;
        }
        this.d.setAdapter(new NumericTimePickerAdapter(1, 12));
        this.d.setCyclic(true);
        this.d.setCurrentItem(b2 - 1);
        this.e.setAdapter(new CustomTimePickerAdapter(0, 59, 1, "%02d"));
        this.e.setCyclic(true);
        this.e.setCurrentItem(b3);
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

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_sure) {
            this.a.a(this.c.getTextItem(this.c.getCurrentItem()), this.d.getTextItem(this.d.getCurrentItem()), this.e.getTextItem(this.e.getCurrentItem()));
            dismiss();
            return;
        }
        if (id == R.id.tv_cancle) {
            dismiss();
        }
    }
}
