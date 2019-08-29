package com.autonavi.map.core.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.map.widget.LinearListView;
import com.autonavi.map.widget.LinearListView.OnItemClickListener;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class MapLayerDrawerView implements DrawerListener, OnClickListener, g<f>, h {
    private TextView A;
    private ImageView B;
    private ImageView C;
    private ImageView D;
    private ImageView E;
    private ImageView F;
    private final String a = getClass().getSimpleName();
    private a b;
    private final Context c;
    /* access modifiers changed from: private */
    public f d;
    private List<e> e;
    private XDrawerLayout f;
    private View g;
    private Real3DSwitchView h;
    private LinearListView i;
    private FlexBoxLayout j;
    private LinearLayout k;
    private LinearLayout l;
    private LinearLayout m;
    private LinearLayout n;
    private LinearLayout o;
    private ToggleButton p;
    private ToggleButton q;
    private ToggleButton r;
    private ToggleButton s;
    private ToggleButton t;
    private LinearLayout u;
    private LinearLayout v;
    private LinearLayout w;
    private RelativeLayout x;
    private TextView y;
    private TextView z;

    static class OthersAdapter extends BaseAdapter {
        private final Context mContext;
        private List<LayerItem> mDynamicItems;

        static class a {
            public ImageView a;
            public TextView b;
            public ImageView c;

            a() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public OthersAdapter(Context context, List<LayerItem> list) {
            this.mDynamicItems = list;
            this.mContext = context;
        }

        public int getCount() {
            if (this.mDynamicItems == null) {
                return 0;
            }
            return this.mDynamicItems.size();
        }

        public Object getItem(int i) {
            if (this.mDynamicItems != null) {
                return this.mDynamicItems.get(i);
            }
            return null;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = LayoutInflater.from(this.mContext).inflate(R.layout.layout_slideview_adapter, null);
                aVar = new a();
                aVar.a = (ImageView) view.findViewById(R.id.img_info);
                aVar.b = (TextView) view.findViewById(R.id.tv_title);
                aVar.c = (ImageView) view.findViewById(R.id.red_flag);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            LayerItem layerItem = this.mDynamicItems.get(i);
            aVar.b.setText(layerItem.getName());
            if (layerItem.isDynamic()) {
                ko.a(aVar.a, layerItem.getIcon(), R.drawable.map_drawer_dynamic_iten_icon_default);
            } else {
                aVar.a.setImageResource(layerItem.getIcon_id());
            }
            return view;
        }
    }

    public interface a {
        Context a();

        ViewGroup b();
    }

    public void onDrawerSlide(View view, float f2) {
    }

    public void onDrawerStateChanged(int i2) {
    }

    public MapLayerDrawerView(a aVar) {
        this.b = aVar;
        this.c = this.b.a();
        this.g = LayoutInflater.from(this.c).inflate(R.layout.slide_layout_common, null);
        this.h = (Real3DSwitchView) this.g.findViewById(R.id.viewReal3DSwitch);
        this.i = (LinearListView) this.g.findViewById(R.id.linear_others);
        this.j = (FlexBoxLayout) this.g.findViewById(R.id.event_container);
        this.k = (LinearLayout) this.g.findViewById(R.id.linear_traffic_condition);
        this.l = (LinearLayout) this.g.findViewById(R.id.linear_traffic_accident);
        this.m = (LinearLayout) this.g.findViewById(R.id.linear_my_collection);
        this.n = (LinearLayout) this.g.findViewById(R.id.linear_bus_radar);
        this.o = (LinearLayout) this.g.findViewById(R.id.linear_scenic);
        this.x = (RelativeLayout) this.g.findViewById(R.id.map_setting_bottom);
        this.p = (ToggleButton) this.g.findViewById(R.id.tb_traffic_condition);
        this.q = (ToggleButton) this.g.findViewById(R.id.tb_traffic_accident);
        this.r = (ToggleButton) this.g.findViewById(R.id.tb_my_collection);
        this.s = (ToggleButton) this.g.findViewById(R.id.tb_bus_radar);
        this.t = (ToggleButton) this.g.findViewById(R.id.tb_scenic);
        this.B = (ImageView) this.g.findViewById(R.id.check_defaultmode_check_iv);
        this.F = (ImageView) this.g.findViewById(R.id.iv_map_setting_red_point);
        this.u = (LinearLayout) this.g.findViewById(R.id.module_subject);
        this.v = (LinearLayout) this.g.findViewById(R.id.module_event);
        this.w = (LinearLayout) this.g.findViewById(R.id.module_others);
        this.g.findViewById(R.id.home_page_ab_switch_container).setVisibility(8);
        View view = this.g;
        this.y = (TextView) view.findViewById(R.id.tv_map_mode_bus_desc);
        this.A = (TextView) view.findViewById(R.id.tv_map_mode_normal_desc);
        this.z = (TextView) view.findViewById(R.id.tv_map_mode_satellite_desc);
        this.C = (ImageView) view.findViewById(R.id.check_defaultmode);
        this.D = (ImageView) view.findViewById(R.id.check_satellitemode);
        this.E = (ImageView) view.findViewById(R.id.check_busmode);
        this.j.setVerticalSpace(5);
        this.k.setOnClickListener(this);
        this.l.setOnClickListener(this);
        this.m.setOnClickListener(this);
        this.n.setOnClickListener(this);
        this.o.setOnClickListener(this);
        this.x.setOnClickListener(this);
        this.C.setOnClickListener(this);
        this.D.setOnClickListener(this);
        this.E.setOnClickListener(this);
        this.i.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(Object obj, View view, int i) {
                MapLayerDrawerView.this.d.b(i);
            }
        });
        ViewGroup b2 = this.b.b();
        if (b2 != null) {
            defpackage.bnq.a aVar2 = new defpackage.bnq.a(this.c);
            aVar2.b = b2;
            aVar2.f = GravityCompat.END;
            aVar2.d = this.g;
            aVar2.c = this;
            if (aVar2.e == null) {
                if (aVar2.a == null) {
                    throw new RuntimeException("please pass an mContext first to use this call");
                }
                aVar2.e = (XDrawerLayout) LayoutInflater.from(aVar2.a).inflate(R.layout.drawer_layout, aVar2.b, false);
                aVar2.e.setId(R.id.drawer_layout_root);
                aVar2.e.setDrawerBuilder(aVar2);
            }
            aVar2.e.addDrawerListener(aVar2.c);
            aVar2.e.registerLayoutListener(this);
            if (aVar2.b == null) {
                throw new RuntimeException("the mRootView should not be null!");
            } else if (aVar2.d == null) {
                throw new RuntimeException("please add a slide view first to use this call");
            } else {
                if (!(aVar2.b.getParent() != null && (aVar2.b.getParent() instanceof XDrawerLayout))) {
                    ViewGroup viewGroup = (ViewGroup) aVar2.b.getParent();
                    int indexOfChild = viewGroup.indexOfChild(aVar2.b);
                    viewGroup.removeView(aVar2.b);
                    aVar2.e.addView(aVar2.b);
                    aVar2.e.addView(aVar2.d, 1, new LayoutParams((int) aVar2.a.getResources().getDimension(R.dimen.main_drawer_width), -1, aVar2.f));
                    viewGroup.addView(aVar2.e, indexOfChild, new FrameLayout.LayoutParams(-1, -1));
                }
                aVar2.a = null;
                this.f = aVar2.e;
                this.f.setClickable(false);
            }
        }
    }

    public final void a(@NonNull f fVar) {
        this.d = fVar;
    }

    public final void a() {
        this.u.setVisibility(0);
        this.v.setVisibility(0);
        this.w.setVisibility(8);
        this.f.openDrawer();
    }

    public final void b() {
        this.f.closeDrawer();
    }

    public final void a(e eVar) {
        if (this.e == null) {
            this.e = new ArrayList();
        }
        this.e.add(eVar);
    }

    public void onDrawerOpened(View view) {
        this.f.setDrawerLockMode(0);
        if (this.e != null) {
            for (e a2 : this.e) {
                a2.a();
            }
        }
    }

    public void onDrawerClosed(View view) {
        this.f.setDrawerLockMode(1);
        if (this.e != null) {
            for (e b2 : this.e) {
                b2.b();
            }
        }
    }

    public final void a(d dVar) {
        int i2 = 8;
        this.k.setVisibility(dVar.a() ? 0 : 8);
        this.l.setVisibility(0);
        this.m.setVisibility(0);
        this.n.setVisibility(8);
        LinearLayout linearLayout = this.o;
        if (dVar.b()) {
            i2 = 0;
        }
        linearLayout.setVisibility(i2);
        if (this.j != null) {
            this.j.forceNextLayout();
            this.j.requestLayout();
        }
    }

    public final void a(List<LayerItem> list) {
        if (list == null || list.isEmpty()) {
            this.w.setVisibility(8);
            return;
        }
        this.w.setVisibility(0);
        this.i.setAdapter(new OthersAdapter(this.c, list));
    }

    public final void a(boolean z2) {
        if (this.s.isChecked() || !z2) {
            if (!z2) {
                this.s.setChecked(false);
            }
            return;
        }
        this.s.setChecked(true);
    }

    public final void b(boolean z2) {
        this.p.setChecked(z2);
    }

    public final void c(boolean z2) {
        this.q.setChecked(z2);
    }

    public final void d(boolean z2) {
        this.r.setChecked(z2);
    }

    public final void e(boolean z2) {
        this.t.setChecked(z2);
    }

    public final void f(boolean z2) {
        this.F.setVisibility(z2 ? 0 : 8);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.linear_traffic_condition) {
            this.d.a(!this.p.isChecked());
        } else if (id == R.id.linear_traffic_accident) {
            this.d.b(!this.q.isChecked());
        } else if (id == R.id.linear_my_collection) {
            this.d.c(!this.r.isChecked());
        } else if (id == R.id.linear_bus_radar) {
            this.d.d(!this.s.isChecked());
        } else if (id == R.id.linear_scenic) {
            this.d.e(!this.t.isChecked());
        } else if (id == R.id.map_setting_bottom) {
            this.F.setVisibility(8);
            this.d.e();
        } else if (id == R.id.check_defaultmode) {
            this.d.a(0);
        } else if (id == R.id.check_satellitemode) {
            this.d.a(1);
        } else {
            if (id == R.id.check_busmode) {
                this.d.a(2);
            }
        }
    }

    public final void g() {
        if (this.e != null) {
            for (e g2 : this.e) {
                g2.g();
            }
        }
        this.f.unregisterLayoutListener();
    }

    public final void a(int i2) {
        switch (i2) {
            case 0:
                this.B.setVisibility(0);
                this.C.setSelected(true);
                this.D.setSelected(false);
                this.E.setSelected(false);
                this.C.setClickable(false);
                this.D.setClickable(true);
                this.E.setClickable(true);
                this.A.setSelected(true);
                this.y.setSelected(false);
                this.z.setSelected(false);
                return;
            case 1:
                this.B.setVisibility(8);
                this.C.setSelected(false);
                this.D.setSelected(true);
                this.E.setSelected(false);
                this.C.setClickable(true);
                this.D.setClickable(false);
                this.E.setClickable(true);
                this.A.setSelected(false);
                this.y.setSelected(false);
                this.z.setSelected(true);
                return;
            case 2:
                this.B.setVisibility(8);
                this.C.setSelected(false);
                this.D.setSelected(false);
                this.E.setSelected(true);
                this.C.setClickable(true);
                this.D.setClickable(true);
                this.E.setClickable(false);
                this.A.setSelected(false);
                this.y.setSelected(true);
                this.z.setSelected(false);
                break;
        }
    }
}
