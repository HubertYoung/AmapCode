package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class SaveDuplicateConfirmPage extends AbstractBasePage<crf> implements OnClickListener, LocationNone {
    private ExpandableListView a;
    /* access modifiers changed from: private */
    public List<cox> b = new ArrayList();
    private DAdapter c = new DAdapter();
    private boolean d = false;
    private Button e;

    class DAdapter extends BaseExpandableListAdapter {
        public long getChildId(int i, int i2) {
            return (long) i2;
        }

        public long getGroupId(int i) {
            return (long) i;
        }

        public boolean hasStableIds() {
            return true;
        }

        public boolean isChildSelectable(int i, int i2) {
            return true;
        }

        private DAdapter() {
        }

        public int getGroupCount() {
            return SaveDuplicateConfirmPage.this.b.size();
        }

        public int getChildrenCount(int i) {
            cox cox = (cox) SaveDuplicateConfirmPage.this.b.get(i);
            if (cox == null || cox.b == null) {
                return 0;
            }
            return cox.b.size();
        }

        public Object getGroup(int i) {
            return SaveDuplicateConfirmPage.this.b.get(i);
        }

        public Object getChild(int i, int i2) {
            cox cox = (cox) SaveDuplicateConfirmPage.this.b.get(i);
            if (cox == null || cox.b == null || i2 < 0 || i2 >= cox.b.size()) {
                return null;
            }
            return cox.b.get(i2);
        }

        public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
            b bVar;
            int i2 = 0;
            if (view == null) {
                view = LayoutInflater.from(SaveDuplicateConfirmPage.this.getActivity()).inflate(R.layout.save_duplicate_group_item, null);
                bVar = new b(0);
                bVar.a = view.findViewById(R.id.view_group_indicator);
                view.setTag(bVar);
            } else {
                bVar = (b) view.getTag();
            }
            if (i <= 0) {
                i2 = 8;
            }
            bVar.a.setVisibility(i2);
            return view;
        }

        public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = LayoutInflater.from(SaveDuplicateConfirmPage.this.getActivity()).inflate(R.layout.save_duplicate_child_item, null);
                aVar = new a(0);
                aVar.a = (ImageView) view.findViewById(R.id.check);
                aVar.b = (TextView) view.findViewById(R.id.text_name);
                aVar.c = (TextView) view.findViewById(R.id.text_detail);
                aVar.d = view.findViewById(R.id.view_divider_part);
                aVar.e = view.findViewById(R.id.view_divider_all);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            a aVar2 = aVar;
            cox cox = (cox) SaveDuplicateConfirmPage.this.b.get(i);
            if (cox == null || cox.b == null || cox.b.size() <= 0) {
                return view;
            }
            bth bth = cox.b.get(i2);
            if (bth == null || bth.a() == null) {
                return view;
            }
            bindData(aVar2, cox, bth, i, i2);
            return view;
        }

        private void bindData(a aVar, final cox cox, final bth bth, int i, int i2) {
            final boolean z = false;
            if (i2 >= cox.b.size() - 1) {
                aVar.e.setVisibility(0);
                aVar.d.setVisibility(8);
            } else {
                aVar.e.setVisibility(8);
                aVar.d.setVisibility(0);
            }
            POI a = bth.a();
            if (a != null) {
                FavoritePOI favoritePOI = (FavoritePOI) a.as(FavoritePOI.class);
                String commonName = favoritePOI.getCommonName();
                if (TextUtils.isEmpty(commonName)) {
                    commonName = favoritePOI.getCustomName();
                    if (TextUtils.isEmpty(commonName)) {
                        commonName = favoritePOI.getName();
                    }
                }
                String name = favoritePOI.getName();
                aVar.b.setText(commonName);
                if (TextUtils.isEmpty(name)) {
                    aVar.c.setVisibility(8);
                } else {
                    aVar.c.setVisibility(0);
                    aVar.c.setText(name);
                }
            }
            Boolean bool = (Boolean) bth.g;
            if (bool != null) {
                z = bool.booleanValue();
            }
            aVar.a.setImageResource(z ? R.drawable.radio_btn_on : R.drawable.radio_btn_off);
            aVar.a.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (!z) {
                        bth.g = Boolean.TRUE;
                        for (bth next : cox.b) {
                            if (next != null && !bth.equals(next)) {
                                next.g = Boolean.FALSE;
                            }
                        }
                        SaveDuplicateConfirmPage.this.a();
                        SaveDuplicateConfirmPage.c(SaveDuplicateConfirmPage.this);
                    }
                }
            });
        }
    }

    static class a {
        public ImageView a;
        public TextView b;
        public TextView c;
        public View d;
        public View e;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    static class b {
        public View a;

        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.save_duplicate_confirm_layout);
        View contentView = getContentView();
        this.a = (ExpandableListView) contentView.findViewById(R.id.expandablelist);
        this.e = (Button) contentView.findViewById(R.id.btn_sure);
        this.e.setEnabled(false);
        this.e.setTextColor(getResources().getColor(R.color.common_text_disable_color));
        this.e.setOnClickListener(this);
        contentView.findViewById(R.id.title_btn_left).setVisibility(8);
        ((TextView) contentView.findViewById(R.id.title_text_name)).setText(R.string.save_useful_choose);
        this.a.setGroupIndicator(null);
        this.a.setAdapter(this.c);
        this.a.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }
        });
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("key_duplicate_points")) {
            List list = (List) arguments.get("key_duplicate_points");
            if (list != null) {
                this.b.addAll(list);
                a();
            }
        }
        if (arguments != null && arguments.containsKey("key_from_public")) {
            this.d = ((Boolean) arguments.get("key_from_public")).booleanValue();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:79:0x0165  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r11) {
        /*
            r10 = this;
            int r11 = r11.getId()
            int r0 = com.autonavi.minimap.R.id.btn_sure
            if (r11 != r0) goto L_0x0188
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List<cox> r1 = r10.b
            java.util.Iterator r1 = r1.iterator()
            r2 = 0
            r3 = 0
        L_0x001a:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L_0x0057
            java.lang.Object r4 = r1.next()
            cox r4 = (defpackage.cox) r4
            if (r4 == 0) goto L_0x001a
            java.util.List<bth> r5 = r4.b
            if (r5 == 0) goto L_0x001a
            java.util.List<bth> r4 = r4.b
            java.util.Iterator r4 = r4.iterator()
        L_0x0032:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x001a
            java.lang.Object r5 = r4.next()
            bth r5 = (defpackage.bth) r5
            if (r5 == 0) goto L_0x0032
            com.autonavi.common.model.POI r6 = r5.a()
            if (r6 == 0) goto L_0x0032
            java.lang.Object r5 = r5.g
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            if (r5 == 0) goto L_0x0051
            boolean r5 = r5.booleanValue()
            goto L_0x0052
        L_0x0051:
            r5 = 0
        L_0x0052:
            if (r5 == 0) goto L_0x0032
            int r3 = r3 + 1
            goto L_0x0032
        L_0x0057:
            java.util.List<cox> r1 = r10.b
            int r1 = r1.size()
            r4 = 1
            if (r3 == r1) goto L_0x00a2
            if (r3 != 0) goto L_0x006a
            java.lang.String r11 = "请选择家和公司"
            com.amap.bundle.utils.ui.ToastHelper.showToast(r11)
            goto L_0x0181
        L_0x006a:
            if (r3 != r4) goto L_0x0181
            java.util.List<cox> r11 = r10.b
            java.lang.Object r11 = r11.get(r2)
            cox r11 = (defpackage.cox) r11
            java.lang.String r11 = r11.a
            java.lang.String r0 = "家"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0087
            java.lang.String r11 = "请选择公司"
            com.amap.bundle.utils.ui.ToastHelper.showToast(r11)
            goto L_0x0181
        L_0x0087:
            java.util.List<cox> r11 = r10.b
            java.lang.Object r11 = r11.get(r2)
            cox r11 = (defpackage.cox) r11
            java.lang.String r11 = r11.a
            java.lang.String r0 = "公司"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0181
            java.lang.String r11 = "请选择家"
            com.amap.bundle.utils.ui.ToastHelper.showToast(r11)
            goto L_0x0181
        L_0x00a2:
            java.util.List<cox> r1 = r10.b
            java.util.Iterator r1 = r1.iterator()
        L_0x00a8:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x00ff
            java.lang.Object r3 = r1.next()
            cox r3 = (defpackage.cox) r3
            if (r3 == 0) goto L_0x00a8
            java.util.List<bth> r5 = r3.b
            if (r5 == 0) goto L_0x00a8
            java.util.List<bth> r3 = r3.b
            java.util.Iterator r3 = r3.iterator()
        L_0x00c0:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x00a8
            java.lang.Object r5 = r3.next()
            bth r5 = (defpackage.bth) r5
            if (r5 == 0) goto L_0x00c0
            com.autonavi.common.model.POI r6 = r5.a()
            if (r6 == 0) goto L_0x00c0
            java.lang.Object r6 = r5.g
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            if (r6 == 0) goto L_0x00df
            boolean r6 = r6.booleanValue()
            goto L_0x00e0
        L_0x00df:
            r6 = 0
        L_0x00e0:
            if (r6 == 0) goto L_0x00e6
            r11.add(r5)
            goto L_0x00c0
        L_0x00e6:
            com.autonavi.common.model.POI r6 = r5.a()
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r7 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r6 = r6.as(r7)
            com.amap.bundle.datamodel.FavoritePOI r6 = (com.amap.bundle.datamodel.FavoritePOI) r6
            r7 = 0
            r6.setCommonName(r7)
            r5.a(r6)
            r5.d = r7
            r0.add(r5)
            goto L_0x00c0
        L_0x00ff:
            cpm r1 = defpackage.cpm.b()
            java.lang.String r1 = r1.a()
            cpf r1 = defpackage.cpf.b(r1)
            int r3 = r11.size()
            if (r3 <= 0) goto L_0x0175
            java.util.Iterator r11 = r11.iterator()
        L_0x0115:
            boolean r3 = r11.hasNext()
            if (r3 == 0) goto L_0x0175
            java.lang.Object r3 = r11.next()
            bth r3 = (defpackage.bth) r3
            com.autonavi.common.model.POI r5 = r3.a()
            if (r5 == 0) goto L_0x016d
            boolean r5 = r10.d
            if (r5 == 0) goto L_0x016d
            com.autonavi.common.model.POI r5 = r3.a()
            java.lang.String r6 = "public"
            if (r5 == 0) goto L_0x0162
            defpackage.cpm.b()
            java.lang.String r7 = defpackage.cpm.b(r5)
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 != 0) goto L_0x0156
            bim r8 = defpackage.bim.aa()
            java.lang.String r9 = "101"
            java.lang.String r7 = r8.b(r9, r7)
            if (r7 == 0) goto L_0x0156
            java.lang.String r8 = ""
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x0156
            r7 = 1
            goto L_0x0157
        L_0x0156:
            r7 = 0
        L_0x0157:
            if (r7 == 0) goto L_0x015b
        L_0x0159:
            r5 = 1
            goto L_0x0163
        L_0x015b:
            bth r5 = defpackage.cpf.a(r5, r6)
            if (r5 == 0) goto L_0x0162
            goto L_0x0159
        L_0x0162:
            r5 = 0
        L_0x0163:
            if (r5 == 0) goto L_0x016d
            com.autonavi.common.model.POI r3 = r3.a()
            r1.b(r3)
            goto L_0x0115
        L_0x016d:
            com.autonavi.common.model.POI r3 = r3.a()
            r1.g(r3)
            goto L_0x0115
        L_0x0175:
            int r11 = r0.size()
            if (r11 <= 0) goto L_0x017e
            defpackage.cpf.b(r0)
        L_0x017e:
            r10.finish()
        L_0x0181:
            java.lang.String r11 = "P00056"
            java.lang.String r0 = "B001"
            com.amap.bundle.statistics.LogManager.actionLogV2(r11, r0)
        L_0x0188:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.save.page.SaveDuplicateConfirmPage.onClick(android.view.View):void");
    }

    /* access modifiers changed from: private */
    public void a() {
        this.c.notifyDataSetChanged();
        for (int i = 0; i < this.b.size(); i++) {
            this.a.expandGroup(i);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new crf(this);
    }

    static /* synthetic */ void c(SaveDuplicateConfirmPage saveDuplicateConfirmPage) {
        int i = 0;
        for (cox next : saveDuplicateConfirmPage.b) {
            if (!(next == null || next.b == null)) {
                for (bth next2 : next.b) {
                    if (!(next2 == null || next2.a() == null)) {
                        Boolean bool = (Boolean) next2.g;
                        if (bool != null ? bool.booleanValue() : false) {
                            i++;
                        }
                    }
                }
            }
        }
        if (i == saveDuplicateConfirmPage.b.size()) {
            saveDuplicateConfirmPage.e.setEnabled(true);
            saveDuplicateConfirmPage.e.setTextColor(saveDuplicateConfirmPage.getResources().getColor(R.color.f_c_6));
        }
    }
}
