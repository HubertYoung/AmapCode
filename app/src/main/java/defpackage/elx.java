package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: elx reason: default package */
/* compiled from: CommonVoiceTitleImpl */
public final class elx implements OnClickListener {
    protected ImageView a;
    protected String b;
    protected TextView c;
    protected ImageView d;
    protected View e;
    protected ViewGroup f;
    public boolean g = false;
    public boolean h = false;

    public final void a(ViewGroup viewGroup) {
        this.f = viewGroup;
        View.inflate(viewGroup.getContext(), R.layout.common_voice_title, this.f);
        this.f.setClickable(true);
        this.a = (ImageView) this.f.findViewById(R.id.title_btn_back);
        this.a.setOnClickListener(this);
        this.d = (ImageView) this.f.findViewById(R.id.show_style);
        this.c = (TextView) this.f.findViewById(R.id.keyword_label);
        this.e = this.f.findViewById(R.id.view_cut_line);
    }

    public final void a(boolean z) {
        if (!this.h && this.d != null) {
            if (z) {
                this.d.setVisibility(0);
            } else {
                this.d.setVisibility(8);
            }
        }
    }

    public final void a(int i) {
        if (this.d != null) {
            if (i == 2) {
                this.d.setImageResource(R.drawable.voice_poi_show_style_list);
                this.d.setContentDescription(AMapAppGlobal.getApplication().getResources().getString(R.string.v4_btn_list));
                return;
            }
            this.d.setImageResource(R.drawable.voice_poi_show_style_map);
            this.d.setContentDescription(AMapAppGlobal.getApplication().getResources().getString(R.string.v4_btn_map));
        }
    }

    public final void onClick(View view) {
        view.getId();
        int i = R.id.title_btn_back;
    }

    public final void a(String str) {
        this.b = str;
        this.c.setText(this.b);
        this.c.setTextColor(-16777216);
    }

    public final String a() {
        return this.b;
    }

    public final void b() {
        this.a.setImageResource(R.drawable.icon_a1_selector);
        this.g = true;
    }

    public final void c() {
        this.a.setImageResource(R.drawable.icon_a1_selector);
        this.g = false;
    }

    public final void b(int i) {
        this.e.setVisibility(i);
    }
}
