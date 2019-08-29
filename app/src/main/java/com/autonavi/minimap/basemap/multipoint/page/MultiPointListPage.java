package com.autonavi.minimap.basemap.multipoint.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

@PageAction("amap.basemap.action.multpoint_lis_page")
public class MultiPointListPage extends AbstractBasePage<cqg> implements OnClickListener {
    public ListView a;
    public TextView b;
    public List<POI> c = new ArrayList();
    public int d = 0;
    private ImageButton e;
    private Button f;

    public class PointsAdapter extends BaseAdapter {
        public long getItemId(int i) {
            return (long) i;
        }

        private PointsAdapter() {
        }

        public int getCount() {
            return MultiPointListPage.this.c.size();
        }

        public Object getItem(int i) {
            return MultiPointListPage.this.c.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null || view.getTag() == null) {
                view = LayoutInflater.from(MultiPointListPage.this.getContext()).inflate(R.layout.intent_multi_point_listitem, null);
                aVar = new a(0);
                aVar.a = view.findViewById(R.id.poi_item_layout_rl);
                aVar.b = (TextView) view.findViewById(R.id.poiName);
                aVar.c = (TextView) view.findViewById(R.id.poiAddr);
                aVar.d = view.findViewById(R.id.btn_to_map_rl);
                aVar.e = view.findViewById(R.id.btn_to_call_rl);
                aVar.f = (Button) view.findViewById(R.id.btn_to_call);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            final POI poi = (POI) MultiPointListPage.this.c.get(i);
            if (poi == null) {
                return view;
            }
            TextView textView = aVar.b;
            StringBuilder sb = new StringBuilder();
            sb.append(i + 1);
            sb.append(".");
            sb.append(poi.getName());
            textView.setText(sb.toString());
            if (!TextUtils.isEmpty(poi.getAddr())) {
                aVar.c.setText(poi.getAddr());
            } else {
                aVar.c.setText(MultiPointListPage.this.getString(R.string.click_for_more));
            }
            aVar.e.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    PointsAdapter.this.callPhone(poi);
                }
            });
            aVar.a.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("POI", poi);
                    MultiPointListPage.this.startPage((String) "amap.search.action.poidetail", pageBundle);
                }
            });
            aVar.d.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    bax bax = (bax) defpackage.esb.a.a.a(bax.class);
                    if (bax != null) {
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putObject("bundle_key_poi_end", poi);
                        pageBundle.putBoolean("bundle_key_auto_route", true);
                        bax.a(pageBundle);
                    }
                }
            });
            if (TextUtils.isEmpty(poi.getPhone())) {
                aVar.f.setEnabled(false);
                aVar.e.setEnabled(false);
            } else {
                aVar.f.setEnabled(true);
                aVar.e.setEnabled(true);
            }
            return view;
        }

        /* access modifiers changed from: private */
        public void callPhone(POI poi) {
            String phone = poi.getPhone();
            if (!TextUtils.isEmpty(phone)) {
                String type = poi.getType();
                if (type.length() >= 4) {
                    type = type.substring(0, 4);
                }
                if ("1001".equals(type) || "1002".equals(type)) {
                    ArrayList arrayList = new ArrayList();
                    if (phone.indexOf(59) >= 0) {
                        String[] split = phone.split(";");
                        for (int i = 0; i < split.length; i++) {
                            if (split[i].substring(0, 3).equals("400")) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(MultiPointListPage.this.getString(R.string.book_by_phone, split[i]));
                                sb.append("$");
                                sb.append(split[i]);
                                arrayList.add(sb.toString());
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(MultiPointListPage.this.getString(R.string.reception_phone, split[i]));
                                sb2.append("$");
                                sb2.append(split[i]);
                                arrayList.add(sb2.toString());
                            }
                        }
                    } else if (phone.substring(0, 3).equals("400")) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(MultiPointListPage.this.getString(R.string.book_by_phone, phone));
                        sb3.append("$");
                        sb3.append(phone);
                        arrayList.add(sb3.toString());
                    } else {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(MultiPointListPage.this.getString(R.string.reception_phone, phone));
                        sb4.append("$");
                        sb4.append(phone);
                        arrayList.add(sb4.toString());
                    }
                    if (arrayList.size() > 0) {
                        bnz.a(arrayList, MultiPointListPage.this.getActivity());
                    }
                } else if (phone.indexOf(";") > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    String[] split2 = phone.split(";");
                    for (int i2 = 0; i2 < split2.length; i2++) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(split2[i2]);
                        sb5.append("$");
                        sb5.append(split2[i2]);
                        arrayList2.add(sb5.toString());
                    }
                    if (arrayList2.size() > 0) {
                        bnz.a(arrayList2, MultiPointListPage.this.getActivity());
                    }
                } else {
                    MultiPointListPage.this.getActivity();
                    bnz.a(phone);
                }
            }
        }
    }

    static class a {
        View a;
        TextView b;
        TextView c;
        View d;
        View e;
        Button f;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.intent_multi_point_list);
        this.a = (ListView) findViewById(R.id.listview);
        this.b = (TextView) findViewById(R.id.text_title);
        this.e = (ImageButton) findViewById(R.id.btn_title_left);
        this.f = (Button) findViewById(R.id.btn_show_type);
        LayoutParams layoutParams = new LayoutParams(-1, 0);
        View view = new View(getActivity());
        View view2 = new View(getActivity());
        view.setLayoutParams(layoutParams);
        view2.setLayoutParams(layoutParams);
        this.a.addFooterView(view2, null, false);
        this.a.addHeaderView(view, null, false);
        this.a.setDescendantFocusability(393216);
        this.e.setOnClickListener(this);
        this.f.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == this.e) {
            finish();
            return;
        }
        if (view == this.f) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("key_focus_index", this.d);
            pageBundle.putString("key_title", this.b.getText().toString());
            pageBundle.putObject("key_multi_points", new ArrayList(this.c));
            finish();
            startPage((String) "amap.basemap.action.multpoint_map_page", pageBundle);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cqg(this);
    }
}
