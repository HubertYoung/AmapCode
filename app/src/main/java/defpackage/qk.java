package defpackage;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;

/* renamed from: qk reason: default package */
/* compiled from: EagleTmcSettingView */
public final class qk {
    OnClickListener a = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.eagle_map_layout) {
                re.b((String) "eagle_setting_switch", true);
                qk.this.a(true);
                return;
            }
            if (view.getId() == R.id.eagle_light_layout) {
                qk.this.a(false);
                re.b((String) "eagle_setting_switch", false);
            }
        }
    };
    private boolean b;
    private RelativeLayout c;
    private RelativeLayout d;
    private LinearLayout e;
    private LinearLayout f;
    private ImageView g;
    private ImageView h;
    private ImageView i;
    private ImageView j;
    private ImageView k;
    private ImageView l;
    private ImageView m;
    private ImageView n;
    private TextView o;
    private TextView p;
    private boolean q;

    private void b(View view) {
        this.c = (RelativeLayout) view.findViewById(R.id.eagle_map_layout);
        this.d = (RelativeLayout) view.findViewById(R.id.eagle_light_layout);
        this.c.setOnClickListener(this.a);
        this.d.setOnClickListener(this.a);
        this.e = (LinearLayout) view.findViewById(R.id.eagle_setting_layout);
        this.f = (LinearLayout) view.findViewById(R.id.eagle_text_layout);
        this.g = (ImageView) view.findViewById(R.id.eagle_mapselected_iv);
        this.h = (ImageView) view.findViewById(R.id.eagle_lightselected_iv);
        this.k = (ImageView) view.findViewById(R.id.eagle_map_bg_iv);
        this.l = (ImageView) view.findViewById(R.id.eagle_light_bg_iv);
        this.m = (ImageView) view.findViewById(R.id.eagle_map_iv);
        this.n = (ImageView) view.findViewById(R.id.eagle_light_iv);
        this.i = (ImageView) view.findViewById(R.id.eagle_mapselected_icon_iv);
        this.j = (ImageView) view.findViewById(R.id.eagle_lightselected_icon_iv);
        this.o = (TextView) view.findViewById(R.id.eagle_setting_map_tv);
        this.p = (TextView) view.findViewById(R.id.eagle_setting_light_tv);
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean z) {
        if (z) {
            this.i.setVisibility(0);
            this.j.setVisibility(8);
        } else {
            this.i.setVisibility(8);
            this.j.setVisibility(0);
        }
        this.g.setSelected(z);
        this.h.setSelected(!z);
    }

    public final void a(int i2) {
        Resources resources;
        int i3;
        LayoutParams layoutParams = (LayoutParams) this.m.getLayoutParams();
        LayoutParams layoutParams2 = (LayoutParams) this.n.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.f.getLayoutParams();
        if (i2 == 1) {
            this.k.setBackgroundResource(this.q ? R.drawable.eagle_v_night_bg : R.drawable.eagle_v_bg);
            this.l.setBackgroundResource(this.q ? R.drawable.eagle_v_night_bg : R.drawable.eagle_v_bg);
            this.m.setImageResource(this.q ? R.drawable.eagle_icon_night : R.drawable.eagle_icon);
            layoutParams.rightMargin = agn.a(this.k.getContext(), 30.0f);
            layoutParams2.rightMargin = agn.a(this.n.getContext(), 30.0f);
            layoutParams3.height = agn.a(this.f.getContext(), 36.0f);
        } else if (i2 == 2) {
            this.k.setBackgroundResource(this.q ? R.drawable.eagle_v_night_bg_land : R.drawable.eagle_v_bg_land);
            this.l.setBackgroundResource(this.q ? R.drawable.eagle_v_night_bg_land : R.drawable.eagle_v_bg_land);
            this.m.setImageResource(this.q ? R.drawable.eagle_icon_night : R.drawable.eagle_icon);
            layoutParams.rightMargin = agn.a(this.k.getContext(), 45.0f);
            layoutParams2.rightMargin = agn.a(this.n.getContext(), 45.0f);
            layoutParams3.height = agn.a(this.f.getContext(), 32.0f);
        }
        if (this.q) {
            resources = this.o.getContext().getResources();
            i3 = R.color.font_white_per55;
        } else {
            resources = this.o.getContext().getResources();
            i3 = R.color.f_c_2;
        }
        int color = resources.getColor(i3);
        this.o.setTextColor(color);
        this.p.setTextColor(color);
        this.d.setBackgroundResource(this.q ? R.drawable.eagle_setting_stroke_night : R.drawable.eagle_setting_stroke);
        this.j.setImageResource(this.q ? R.drawable.eagle_setting_selected_night : R.drawable.eagle_setting_selected_day);
        this.i.setImageResource(this.q ? R.drawable.eagle_setting_selected_night : R.drawable.eagle_setting_selected_day);
        this.e.setBackgroundResource(this.q ? R.color.c_32 : R.color.white);
        this.g.setBackgroundResource(this.q ? R.drawable.eagle_setting_stroke_selector_night : R.drawable.eagle_setting_stroke_selector_day);
        this.h.setBackgroundResource(this.q ? R.drawable.eagle_setting_stroke_selector_night : R.drawable.eagle_setting_stroke_selector_day);
        this.m.setLayoutParams(layoutParams);
        this.n.setLayoutParams(layoutParams2);
        this.f.setLayoutParams(layoutParams3);
    }

    public final void a(View view) {
        this.b = re.a((String) "eagle_setting_switch", false);
        this.q = false;
        b(view);
        a(view.getResources().getConfiguration().orientation);
        a(this.b);
    }
}
