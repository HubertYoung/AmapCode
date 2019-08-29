package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.map.widget.DatePickerAdapter;
import com.autonavi.map.widget.wheel.TimePickerWidgetView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.adapter.TrainTicketFilterAdapter;
import com.autonavi.minimap.route.train.model.TrainTypeItem;
import com.autonavi.minimap.route.train.view.TrainTicketFilterPopup$3;
import java.util.List;

/* renamed from: ejy reason: default package */
/* compiled from: TrainTicketFilterPopup */
public final class ejy {
    public int a = -1;
    public int b = 0;
    public final View c;
    public final PopupWindow d;
    public final LinearLayout e;
    final TimePickerWidgetView f;
    public final ListView g;
    public a h;
    private final Context i;
    private final String[] j = new String[3];
    private List<eix> k = null;
    private final DatePickerAdapter l;
    private final TrainTicketFilterAdapter m;
    private final LinearLayout n;
    private final View o;
    private final TextView[] p = new TextView[4];
    private final LinearLayout[] q = new LinearLayout[4];
    private final Button r;
    private int s = -1;
    private int t;
    private int u;
    private int v;
    private int w;

    /* renamed from: ejy$a */
    /* compiled from: TrainTicketFilterPopup */
    public interface a {
        void a(int i, int i2, int i3);
    }

    public ejy(View view) {
        int i2;
        this.i = view.getContext();
        this.c = view;
        View inflate = LayoutInflater.from(this.i).inflate(R.layout.widget_popup_tab_layout, null);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ll_list);
        if (ags.a(this.i).width() > ags.a(this.i).height()) {
            i2 = ags.a(this.i).width();
        } else {
            i2 = ags.a(this.i).height();
        }
        linearLayout.measure(0, 0);
        linearLayout.getLayoutParams().height = (i2 * 6) / 13;
        this.o = inflate.findViewById(R.id.tab_layout);
        int i3 = 0;
        while (i3 < this.p.length) {
            TextView[] textViewArr = this.p;
            View view2 = this.o;
            StringBuilder sb = new StringBuilder("caption");
            int i4 = i3 + 1;
            sb.append(i4);
            textViewArr[i3] = (TextView) view2.findViewWithTag(sb.toString());
            LinearLayout[] linearLayoutArr = this.q;
            View view3 = this.o;
            StringBuilder sb2 = new StringBuilder(ResUtils.LAYOUT);
            sb2.append(i4);
            linearLayoutArr[i3] = (LinearLayout) view3.findViewWithTag(sb2.toString());
            i3 = i4;
        }
        this.q[this.q.length - 1].setVisibility(8);
        this.p[this.p.length - 1].setVisibility(8);
        this.e = (LinearLayout) inflate.findViewById(R.id.date_picker_layout);
        this.f = (TimePickerWidgetView) inflate.findViewById(R.id.date_picker);
        this.r = (Button) inflate.findViewById(R.id.confirm_button);
        this.f.setCyclic(true);
        this.l = new DatePickerAdapter(this.i, 0, 59);
        this.f.setAdapter(this.l);
        this.f.setCurrentItem(0);
        this.r.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ejy.this.h.a(ejy.this.a, 0, ejy.this.f.getCurrentItem());
                ejy.this.e();
            }
        });
        d();
        this.m = new TrainTicketFilterAdapter(this.i);
        this.g = (ListView) inflate.findViewById(R.id.lv_main);
        this.g.setChoiceMode(1);
        this.g.setAdapter(this.m);
        this.g.setVisibility(4);
        this.n = (LinearLayout) inflate.findViewById(R.id.ll_bottom);
        b();
        inflate.findViewById(R.id.rightLL).setVisibility(8);
        inflate.findViewById(R.id.left_title).setVisibility(8);
        inflate.findViewById(R.id.right_title).setVisibility(8);
        inflate.findViewById(R.id.left_text).setVisibility(8);
        inflate.findViewById(R.id.right_text).setVisibility(8);
        this.w = ags.a(this.i).height();
        this.v = (int) (ags.c(this.i) * 100.0f);
        this.t = this.i.getResources().getDrawable(R.drawable.widget_filter_main_itembg).getIntrinsicHeight();
        this.o.measure(0, 0);
        this.u = this.o.getMeasuredHeight();
        this.d = new PopupWindow(inflate, -1, -1);
        this.d.setBackgroundDrawable(this.i.getResources().getDrawable(17170445));
        this.d.setAnimationStyle(0);
        this.d.setOutsideTouchable(false);
        this.d.setFocusable(true);
        this.d.update();
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        this.g.setVisibility(0);
    }

    public final void b() {
        this.g.setVisibility(4);
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        this.e.setVisibility(0);
    }

    public final void d() {
        this.e.setVisibility(4);
    }

    private void b(int i2) {
        int[] iArr = new int[2];
        this.c.getLocationOnScreen(iArr);
        this.s = iArr[1];
        int i3 = ((this.w - this.u) - this.s) - this.v;
        int i4 = i2 * this.t;
        if (i4 >= i3) {
            LayoutParams layoutParams = (LayoutParams) this.n.getLayoutParams();
            layoutParams.height = (i3 - ((i3 / this.t) * this.t)) + this.v;
            this.n.setLayoutParams(layoutParams);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) this.n.getLayoutParams();
        layoutParams2.height = (i3 - i4) + this.v;
        this.n.setLayoutParams(layoutParams2);
    }

    public final void a(int i2, String str) {
        this.p[i2].setText(str);
    }

    public final void a(List<eix> list) {
        this.k = list;
        if (list != null && list.size() != 0) {
            if (this.d != null && this.d.isShowing()) {
                this.d.dismiss();
            }
            for (final int i2 = 0; i2 < this.p.length - 1; i2++) {
                if (i2 < list.size()) {
                    eix eix = list.get(i2);
                    if (eix != null) {
                        this.p[i2].setText(eix.b);
                        this.p[i2].setEnabled(true);
                        this.q[i2].setEnabled(true);
                        this.p[i2].setTextColor(this.i.getResources().getColorStateList(R.color.filter_text_click_selector));
                    } else {
                        this.p[i2].setEnabled(false);
                        this.q[i2].setEnabled(false);
                    }
                    this.p[i2].setVisibility(0);
                    this.q[i2].setVisibility(0);
                    if (this.j[i2] == null || this.j[i2].length() == 0) {
                        this.j[i2] = this.p[i2].getText().toString();
                    }
                } else {
                    this.p[i2].setVisibility(8);
                    this.q[i2].setVisibility(8);
                }
                this.q[i2].setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        ejy.this.a(i2);
                    }
                });
            }
            this.g.setOnItemClickListener(new TrainTicketFilterPopup$3(this));
            this.n.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (ejy.this.d != null && ejy.this.d.isShowing()) {
                        ejy.this.d.dismiss();
                    }
                }
            });
        }
    }

    public final void a(int i2) {
        if (this.a == -1 || this.a != i2 || this.d == null || !this.d.isShowing()) {
            this.a = i2;
            switch (i2) {
                case 0:
                    b();
                    c();
                    break;
                case 1:
                    d();
                    a();
                    if (this.k != null && this.k.size() > 0 && i2 < this.k.size()) {
                        eix eix = this.k.get(i2);
                        if (eix != null) {
                            List<TrainTypeItem> list = eix.c;
                            if (list != null) {
                                b(Math.max(list.size(), eix.a()));
                                this.m.setData(list);
                                this.m.setSelection(this.b);
                                this.m.notifyDataSetChanged();
                                break;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                    break;
            }
            int i3 = 0;
            while (i3 < this.p.length) {
                boolean z = true;
                this.p[i3].setSelected(i3 == i2);
                LinearLayout linearLayout = this.q[i3];
                if (i3 != i2) {
                    z = false;
                }
                linearLayout.setSelected(z);
                i3++;
            }
            return;
        }
        this.d.dismiss();
    }

    public final void e() {
        if (this.d != null && this.d.isShowing()) {
            if (this.g != null) {
                this.g.setVisibility(4);
            }
            this.d.dismiss();
        }
    }
}
