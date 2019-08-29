package com.autonavi.minimap.life.common.template;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.common.LifePOI;
import com.autonavi.minimap.search.templete.model.ITemplate;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.HashMap;
import java.util.Map;

public class LifePoiListItemAdapter extends PoiTemplateBaseAdapter<LifePOI, a> {
    private static final Map<String, Integer> EXTIMAGESRC;
    private static final int LIST_DEEPINFO = 2010;
    private static final int LIST_DISTANCE = 2002;
    private static final int LIST_ICON = 2005;
    private static final int LIST_ICON_WLAN_PARK = 2017;
    private static final int LIST_IMG = 2019;
    private static final int LIST_POI_NAME = 2001;
    private static final int LIST_PRICE = 2008;
    private static final int LIST_RATE = 2006;
    private static final int LIST_TAG = 2007;
    private static final int LIST_TEXT_BUSINESS_AREA = 2020;
    private long lastClickTime;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private bid mPageContext;
    private dnn<LifePOI> mShowFirstPoiListener;
    private boolean mStopGetView;

    public static class a extends dol {
        View a;
        ImageView b;
        TextView c;
        ImageView[] d = new ImageView[3];
        ImageView[] e = new ImageView[2];
        TextView f;
        RatingBar g;
        TextView h;
        TextView i;
        TextView j;
        TextView k;
        TextView l;

        public a(View view) {
            super(view);
            this.a = view.findViewById(R.id.life_main_layout);
            this.b = (ImageView) view.findViewById(R.id.life_image_view);
            this.c = (TextView) view.findViewById(R.id.life_poi_name);
            this.d[0] = (ImageView) view.findViewById(R.id.life_poi_iv_1);
            this.d[1] = (ImageView) view.findViewById(R.id.life_poi_iv_2);
            this.d[2] = (ImageView) view.findViewById(R.id.life_poi_iv_3);
            this.f = (TextView) view.findViewById(R.id.life_poi_no_rating);
            this.g = (RatingBar) view.findViewById(R.id.life_rating_bar);
            this.h = (TextView) view.findViewById(R.id.life_poi_deepinfo_tv);
            this.i = (TextView) view.findViewById(R.id.life_avgprice);
            this.j = (TextView) view.findViewById(R.id.life_tag);
            this.k = (TextView) view.findViewById(R.id.life_distance);
            this.e[0] = (ImageView) view.findViewById(R.id.poi_iv_wlan);
            this.e[1] = (ImageView) view.findViewById(R.id.poi_iv_park);
            this.l = (TextView) view.findViewById(R.id.business_area);
        }
    }

    class b implements dok<LifePOI, a> {
        private b() {
        }

        /* synthetic */ b(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            a aVar = (a) dol;
            if (z) {
                LifePoiListItemAdapter.this.setViewStatus(aVar.l, poiLayoutTemplate);
                aVar.j.setText(poiLayoutTemplate.getValue());
                return;
            }
            aVar.l.setVisibility(8);
        }
    }

    class c implements dok<LifePOI, a> {
        private c() {
        }

        /* synthetic */ c(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            a aVar = (a) dol;
            TextView textView = aVar.h;
            if (textView != null) {
                if (z) {
                    LifePoiListItemAdapter.this.setViewStatus(aVar.h, poiLayoutTemplate);
                    textView.setText(((PoiHtmlTemplate) poiLayoutTemplate).getSpanned());
                    return;
                }
                textView.setVisibility(8);
            }
        }
    }

    class d implements dok<LifePOI, a> {
        private d() {
        }

        /* synthetic */ d(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            a aVar = (a) dol;
            if (aVar.k != null) {
                if (z) {
                    LifePoiListItemAdapter.this.setViewStatus(aVar.k, poiLayoutTemplate);
                    aVar.k.setText(poiLayoutTemplate.getValue());
                    return;
                }
                aVar.k.setVisibility(8);
            }
        }
    }

    class e implements dok<LifePOI, a> {
        private e() {
        }

        /* synthetic */ e(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            a aVar = (a) dol;
            aVar.d[0].setVisibility(8);
            aVar.d[1].setVisibility(8);
            aVar.d[2].setVisibility(8);
            if (z) {
                String[] src = ((PoiImageTemplate) poiLayoutTemplate).getSrc();
                if (src != null) {
                    LifePoiListItemAdapter.this.resetImages(aVar.d, src, 3);
                }
            }
        }
    }

    class f implements dok<LifePOI, a> {
        private f() {
        }

        /* synthetic */ f(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            a aVar = (a) dol;
            aVar.e[0].setVisibility(8);
            aVar.e[1].setVisibility(8);
            if (z) {
                String[] src = ((PoiImageTemplate) poiLayoutTemplate).getSrc();
                if (src != null) {
                    LifePoiListItemAdapter.this.resetImages(aVar.e, src, 2);
                }
            }
        }
    }

    static class g implements dok<LifePOI, a> {
        private g() {
        }

        /* synthetic */ g(byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            a aVar = (a) dol;
            LifePOI lifePOI = (LifePOI) iTemplate;
            aVar.b.setImageResource(R.drawable.poi_list_item_img_default);
            if (z) {
                lifePOI.setImageURL(poiLayoutTemplate.getValue());
                ko.a(aVar.b, lifePOI.getImageURL(), null, R.drawable.poi_list_item_img_default, null);
            }
        }
    }

    class h implements dok<LifePOI, a> {
        private h() {
        }

        /* synthetic */ h(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            TextView textView = ((a) dol).c;
            if (textView != null) {
                if (z) {
                    LifePoiListItemAdapter.this.setViewStatus(textView, poiLayoutTemplate);
                    textView.setText(poiLayoutTemplate.getValue());
                    return;
                }
                textView.setVisibility(4);
            }
        }
    }

    class i implements dok<LifePOI, a> {
        private i() {
        }

        /* synthetic */ i(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            TextView textView = ((a) dol).i;
            if (textView != null) {
                if (z) {
                    LifePoiListItemAdapter.this.setViewStatus(textView, poiLayoutTemplate);
                    textView.setText(((PoiHtmlTemplate) poiLayoutTemplate).getSpanned());
                    return;
                }
                textView.setVisibility(8);
            }
        }
    }

    class j implements dok<LifePOI, a> {
        private j() {
        }

        /* synthetic */ j(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            a aVar = (a) dol;
            int i = 0;
            if (z) {
                try {
                    i = (int) (Float.valueOf(poiLayoutTemplate.getValue()).floatValue() * 10.0f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                RatingBar ratingBar = aVar.g;
                if (i > 0) {
                    aVar.f.setVisibility(8);
                    if (ratingBar != null) {
                        LifePoiListItemAdapter.this.setViewStatus(ratingBar, poiLayoutTemplate);
                        ratingBar.setProgress(i);
                        return;
                    }
                } else {
                    aVar.f.setVisibility(8);
                    if (ratingBar != null) {
                        ratingBar.setVisibility(8);
                    }
                }
                return;
            }
            aVar.f.setVisibility(0);
            RatingBar ratingBar2 = aVar.g;
            if (ratingBar2 != null) {
                ratingBar2.setVisibility(8);
            }
        }
    }

    class k implements dok<LifePOI, a> {
        private k() {
        }

        /* synthetic */ k(LifePoiListItemAdapter lifePoiListItemAdapter, byte b) {
            this();
        }

        public final /* synthetic */ void a(dol dol, ITemplate iTemplate, PoiLayoutTemplate poiLayoutTemplate, boolean z) {
            TextView textView = ((a) dol).j;
            if (textView != null) {
                if (z) {
                    LifePoiListItemAdapter.this.setViewStatus(textView, poiLayoutTemplate);
                    textView.setText(((PoiHtmlTemplate) poiLayoutTemplate).getSpanned());
                    return;
                }
                textView.setVisibility(8);
            }
        }
    }

    public void onBindUnTemplateViewHolder(a aVar, LifePOI lifePOI, int i2, int i3) {
    }

    public void onFinishProcessViewHolder(a aVar, LifePOI lifePOI, int i2, int i3) {
    }

    static {
        HashMap hashMap = new HashMap(10);
        EXTIMAGESRC = hashMap;
        hashMap.put("free_parking_flag", Integer.valueOf(R.drawable.free_parking_flag));
        EXTIMAGESRC.put("wifi_flag", Integer.valueOf(R.drawable.wifi_flag));
        EXTIMAGESRC.put("poi_room", Integer.valueOf(R.drawable.poi_room));
        EXTIMAGESRC.put("poi_yikuaiqu_order", Integer.valueOf(R.drawable.poi_yikuaiqu_order));
        EXTIMAGESRC.put("poi_bank", Integer.valueOf(R.drawable.poi_bank));
        EXTIMAGESRC.put("poi_atm", Integer.valueOf(R.drawable.poi_atm));
        EXTIMAGESRC.put("poi_ticket", Integer.valueOf(R.drawable.poi_ticket));
        EXTIMAGESRC.put("poi_group", Integer.valueOf(R.drawable.poi_group));
        EXTIMAGESRC.put("poi_alipay", Integer.valueOf(R.drawable.poi_alipay));
        EXTIMAGESRC.put("poi_queue", Integer.valueOf(R.drawable.poi_queue));
        EXTIMAGESRC.put("poi_favorable", Integer.valueOf(R.drawable.poi_favorable));
        EXTIMAGESRC.put("poi_diandian", Integer.valueOf(R.drawable.poi_diandian));
    }

    public LifePoiListItemAdapter(Context context, bid bid) {
        this.mPageContext = bid;
        this.mContext = context;
        AddProcessTemplate2(2001, new h(this, 0));
        AddProcessTemplate2(2005, new e(this, 0));
        AddProcessTemplate2(2006, new j(this, 0));
        AddProcessTemplate2(2010, new c(this, 0));
        AddProcessTemplate2(2008, new i(this, 0));
        AddProcessTemplate2(2007, new k(this, 0));
        AddProcessTemplate2(2002, new d(this, 0));
        AddProcessTemplate2(LIST_TEXT_BUSINESS_AREA, new b(this, 0));
        AddProcessTemplate2(2017, new f(this, 0));
        AddProcessTemplate2(2019, new g(0));
    }

    public boolean isStopGetView() {
        return this.mStopGetView;
    }

    public void setStopGetView(boolean z) {
        this.mStopGetView = z;
    }

    public View onCreateView(ViewGroup viewGroup, int i2) {
        return LayoutInflater.from(this.mContext).inflate(R.layout.life_poi_list_template_layout, viewGroup, false);
    }

    public a onCreateViewHolder(View view, ViewGroup viewGroup, int i2) {
        return new a(view);
    }

    public void onBindViewHolderWithData(a aVar, LifePOI lifePOI, int i2, int i3) {
        if (!this.mStopGetView) {
            AddProcessTemplate2(2005, new e(this, 0));
            AddProcessTemplate2(2006, new j(this, 0));
            AddProcessTemplate2(2010, new c(this, 0));
            AddProcessTemplate2(2008, new i(this, 0));
            AddProcessTemplate2(2007, new k(this, 0));
            AddProcessTemplate2(2002, new d(this, 0));
            AddProcessTemplate2(LIST_TEXT_BUSINESS_AREA, new b(this, 0));
            AddProcessTemplate2(2017, new f(this, 0));
            AddProcessTemplate2(2019, new g(0));
            super.onBindViewHolderWithData(aVar, lifePOI, i2, i3);
            return;
        }
        aVar.b.setImageResource(R.drawable.poi_list_item_img_default);
        TextView textView = aVar.c;
        if (textView != null && textView.getVisibility() == 0) {
            RemoveProcessTemplate2(2005);
            RemoveProcessTemplate2(2006);
            RemoveProcessTemplate2(2008);
            RemoveProcessTemplate2(2007);
            RemoveProcessTemplate2(2002);
            RemoveProcessTemplate2(LIST_TEXT_BUSINESS_AREA);
            RemoveProcessTemplate2(2017);
        }
        RemoveProcessTemplate2(2019);
        super.onBindViewHolderWithData(aVar, lifePOI, i2, i3);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnShowFirstPoiListener(dnn<LifePOI> dnn) {
        this.mShowFirstPoiListener = dnn;
    }

    public void unRegisterAllListener() {
        this.mOnItemClickListener = null;
        this.mShowFirstPoiListener = null;
    }

    /* access modifiers changed from: private */
    public void setViewStatus(View view, PoiLayoutTemplate poiLayoutTemplate) {
        if (view != null) {
            view.setVisibility(poiLayoutTemplate.isShown());
            view.setEnabled(poiLayoutTemplate.isEnable());
        }
    }

    /* access modifiers changed from: private */
    public void resetImages(ImageView[] imageViewArr, String[] strArr, int i2) {
        for (int i3 = 0; i3 < strArr.length; i3++) {
            Integer num = EXTIMAGESRC.get(strArr[i3]);
            if (num != null) {
                int i4 = i3 % i2;
                imageViewArr[i4].setVisibility(0);
                imageViewArr[i4].setImageResource(num.intValue());
            }
        }
    }

    /* access modifiers changed from: private */
    public void onItemHolderClick(a aVar, LifePOI lifePOI) {
        if (lifePOI != null) {
            goToDetail(lifePOI, 1);
        }
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(null, aVar.itemView, aVar.getPosition(), 0);
        }
    }

    private void goToDetail(POI poi, int i2) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.lastClickTime >= 1000) {
            this.lastClickTime = currentTimeMillis;
            bid bid = this.mPageContext;
            PageBundle pageBundle = new PageBundle();
            pageBundle.putSerializable("POI", poi);
            pageBundle.putInt("from_id", i2);
            pageBundle.putInt("poi_detail_page_type", 5);
            bid.startPage((String) "amap.search.action.poidetail", pageBundle);
        }
    }

    public void onPreprocessViewHolder(final a aVar, LifePOI lifePOI, final int i2, int i3) {
        aVar.a.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (view == aVar.a) {
                    LifePoiListItemAdapter.this.onItemHolderClick(aVar, (LifePOI) LifePoiListItemAdapter.this.getItem(i2));
                }
            }
        });
    }
}
