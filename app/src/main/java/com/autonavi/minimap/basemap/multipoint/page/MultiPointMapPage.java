package com.autonavi.minimap.basemap.multipoint.page;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.widget.RecyclablePagerAdapter;
import com.autonavi.map.widget.RecyclableViewPager;
import com.autonavi.map.widget.RecyclableViewPager.OnPageChangeListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.widget.PoiDetailView;
import com.autonavi.minimap.widget.PoiDetailViewFactory;
import java.util.ArrayList;
import java.util.List;

@PageAction("amap.basemap.action.multpoint_map_page")
public class MultiPointMapPage extends MapBasePage<cqh> implements OnClickListener {
    public RecyclableViewPager a;
    public b b;
    public MultiPointAdapter c;
    public TextView d;
    public MultiPointOverlay e;
    public List<POI> f = new ArrayList();
    public boolean g = false;
    private ImageButton h;
    private Button i;

    public class MultiPointAdapter extends RecyclablePagerAdapter<POI> {
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public MultiPointAdapter(List<POI> list) {
            super(list);
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View a2 = MultiPointMapPage.a(MultiPointMapPage.this, i);
            if (a2 != null) {
                try {
                    viewGroup.addView(a2);
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }
            return a2;
        }

        public float getPageWidth(int i) {
            return MultiPointMapPage.this.f.size() == 1 ? 0.98f : 0.9f;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    public class a implements OnItemClickListener<cqf> {
        private a() {
        }

        public /* synthetic */ a(MultiPointMapPage multiPointMapPage, byte b) {
            this();
        }

        public final /* synthetic */ void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
            cqf cqf = (cqf) obj;
            if (cqf != null) {
                int indexOf = MultiPointMapPage.this.f.indexOf(cqf.getPOI());
                if (indexOf >= 0 && indexOf < MultiPointMapPage.this.f.size()) {
                    MultiPointMapPage.this.showViewFooter(MultiPointMapPage.this.a);
                    MultiPointMapPage.this.a.setCurrentItem(indexOf);
                }
            }
        }
    }

    public class b implements OnPageChangeListener {
        public boolean a;

        public final void onPageScrollStateChanged(int i) {
        }

        public final void onPageScrolled(int i, float f, int i2) {
        }

        private b() {
            this.a = false;
        }

        public /* synthetic */ b(MultiPointMapPage multiPointMapPage, byte b2) {
            this();
        }

        public final void onPageSelected(int i) {
            if (this.a) {
                this.a = false;
                return;
            }
            if (i >= 0 && i < MultiPointMapPage.this.f.size()) {
                MultiPointMapPage.this.e.setFocus(i, true);
            }
        }
    }

    public View getMapSuspendView() {
        return null;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public cqh createPresenter() {
        return new cqh(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.intent_multi_point_map);
        this.e = new MultiPointOverlay(getMapManager().getMapView());
        addOverlay(this.e);
        this.a = (RecyclableViewPager) findViewById(R.id.viewpager);
        this.h = (ImageButton) findViewById(R.id.btn_title_left);
        this.i = (Button) findViewById(R.id.btn_show_type);
        this.d = (TextView) findViewById(R.id.text_title);
        this.h.setOnClickListener(this);
        this.i.setOnClickListener(this);
        this.i.setText(R.string.list);
        Drawable drawable = getResources().getDrawable(R.drawable.list_icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.i.setCompoundDrawables(null, drawable, null, null);
        this.a.setUseRecycler(false);
        this.a.setPageMargin(getResources().getDimensionPixelSize(R.dimen.poi_tip_margin));
        this.a.setDescendantFocusability(393216);
    }

    public void onClick(View view) {
        if (view == this.h) {
            finish();
            return;
        }
        if (view == this.i) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("key_focus_index", this.e.getLastFocusedIndex());
            pageBundle.putString("key_title", this.d.getText().toString());
            pageBundle.putObject("key_multi_points", new ArrayList(this.f));
            finish();
            startPage((String) "amap.basemap.action.multpoint_lis_page", pageBundle);
        }
    }

    static /* synthetic */ View a(MultiPointMapPage multiPointMapPage, int i2) {
        if (i2 < 0 || i2 >= multiPointMapPage.f.size()) {
            return null;
        }
        POI poi = multiPointMapPage.f.get(i2);
        PoiDetailView createPoiDetailView = PoiDetailViewFactory.createPoiDetailView();
        String name = poi.getName();
        String addr = poi.getAddr();
        if (TextUtils.isEmpty(name)) {
            name = multiPointMapPage.getString(R.string.map_point);
        }
        if (TextUtils.isEmpty(addr)) {
            addr = multiPointMapPage.getString(R.string.click_for_more);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(i2 + 1);
        sb.append(".");
        sb.append(name);
        createPoiDetailView.setMainTitle(sb.toString());
        createPoiDetailView.setViceTitle(addr);
        createPoiDetailView.setPoi(poi);
        return createPoiDetailView;
    }
}
