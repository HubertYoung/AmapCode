package com.autonavi.minimap.drive.restrictedarea;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.cloudsync.widget.IphoneTreeView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.navigation.NavigationRequestHolder;
import com.autonavi.minimap.navigation.param.RestrictedAreaRequest;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.TitleBar;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;

@SuppressFBWarnings({"IS2_INCONSISTENT_SYNC"})
@PageAction("amap.basemap.action.car_restrict_city_list")
public class RestrictedCityListFragment extends DriveBasePage<dhv> implements TextWatcher, OnClickListener {
    public IphoneTreeView a;
    public View b;
    public FrameLayout c;
    public View d;
    public EditText e;
    public ImageButton f;
    public DriveIndexView g;
    public LinkedList<dhr> h = new LinkedList<>();
    public LinkedList<dhr> i = new LinkedList<>();
    public dhr j;
    public String k;
    public int l;
    public AosRequest m;
    public TitleBar n;
    /* access modifiers changed from: private */
    public ExpandableListAdapter o;
    /* access modifiers changed from: private */
    public List<dhr> p = null;
    /* access modifiers changed from: private */
    public List<dhr> q = null;
    private List<dhr> r = new ArrayList();
    private List<dhs> s = null;
    /* access modifiers changed from: private */
    public a t = new a(0);
    /* access modifiers changed from: private */
    public String[] u = {AMapAppGlobal.getApplication().getString(R.string.common_used_city), "A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    /* access modifiers changed from: private */
    public int v = 0;

    class ExpandableListAdapter extends BaseExpandableListAdapter implements com.autonavi.common.cloudsync.widget.IphoneTreeView.a {
        private Context context;
        private boolean isSearch;
        private List<dhs> localList = new ArrayList();

        public long getChildId(int i, int i2) {
            return (long) i2;
        }

        public long getGroupId(int i) {
            return (long) i;
        }

        public int getHeadViewClickStatus(int i) {
            return 0;
        }

        public boolean hasStableIds() {
            return true;
        }

        public boolean isChildSelectable(int i, int i2) {
            return true;
        }

        public void onHeadViewClick(int i, int i2) {
        }

        public ExpandableListAdapter(Context context2, List<dhs> list, boolean z) {
            this.context = context2;
            this.localList.addAll(list);
            this.isSearch = z;
        }

        public void setLocalList(List<dhs> list, boolean z) {
            if (this.localList != null) {
                this.localList.clear();
                this.localList.addAll(list);
            }
            this.isSearch = z;
            notifyDataSetChanged();
        }

        public final List<dhs> getLocalList() {
            return this.localList;
        }

        public Object getChild(int i, int i2) {
            if (this.localList == null) {
                return null;
            }
            return this.localList.get(i).b.get(i2);
        }

        public int getChildrenCount(int i) {
            int i2;
            if (this.localList == null || i < 0 || i >= this.localList.size()) {
                return 0;
            }
            try {
                i2 = this.localList.get(i).b.size();
            } catch (Exception e) {
                e.printStackTrace();
                i2 = 0;
            }
            return i2;
        }

        public Object getGroup(int i) {
            if (this.localList == null) {
                return null;
            }
            return this.localList.get(i);
        }

        public int getGroupCount() {
            if (this.localList == null) {
                return 0;
            }
            return this.localList.size();
        }

        public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
            if (this.localList == null) {
                return null;
            }
            if (view == null) {
                view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.route_car_result_restricted_area_city_list_child_item, viewGroup, false);
            }
            TextView textView = (TextView) view.findViewById(R.id.name);
            TextView textView2 = (TextView) view.findViewById(R.id.descr);
            textView2.setVisibility(8);
            View findViewById = view.findViewById(R.id.view_switch_city_divider);
            findViewById.setVisibility(0);
            List<dhr> list = this.localList.get(i).b;
            if (list != null) {
                dhr dhr = list.get(i2);
                if (dhr == null) {
                    return view;
                }
                textView.setText(dhr.a);
                String str = dhr.c;
                if (!TextUtils.isEmpty(str)) {
                    textView2.setText(str);
                    textView2.setVisibility(0);
                }
                if (list.indexOf(dhr) >= list.size() - 1) {
                    findViewById.setVisibility(8);
                }
            }
            return view;
        }

        public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.route_car_result_restricted_area_city_list_group_item, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.name);
            textView.setText(this.localList.get(i).a);
            if (this.isSearch) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
            }
            return view;
        }

        public int getTreeHeaderState(int i, int i2) {
            if (this.isSearch || i == -1) {
                return 0;
            }
            if (i2 == getChildrenCount(i) - 1) {
                return 2;
            }
            if (i2 != -1 || RestrictedCityListFragment.this.a.isGroupExpanded(i)) {
                return 1;
            }
            return 0;
        }

        public void configureTreeHeader(View view, int i, int i2, int i3) {
            ((TextView) view.findViewById(R.id.city_indicator_text)).setText(RestrictedCityListFragment.this.u[i]);
        }

        public void doRelatedActions() {
            RestrictedCityListFragment.this.a(RestrictedCityListFragment.this.getContext());
        }
    }

    static class a {
        boolean a;
        boolean b;
        String[] c;
        String[] d;
        List<dhs> e;
        List<dhs> f;

        private a() {
            this.a = false;
            this.b = false;
            this.e = null;
            this.f = null;
        }

        /* synthetic */ a(byte b2) {
            this();
        }

        public final void a(int i, boolean z) {
            if (i == 0) {
                this.a = z;
                return;
            }
            if (i == 1) {
                this.b = z;
            }
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.route_car_result_restricted_area_city_list_fragment_layout);
    }

    public final void a() {
        if (this.b != null) {
            if (b()) {
                this.b.setVisibility(0);
                if (e() == 0) {
                    ((TextView) this.b.findViewById(R.id.city_list_tips_text)).setText(getString(R.string.restrict_car_plate_tips));
                    ((Button) this.b.findViewById(R.id.city_list_set_car_plate)).setText(getString(R.string.restrict_set_car_plate));
                    return;
                }
                ((TextView) this.b.findViewById(R.id.city_list_tips_text)).setText(getString(R.string.restrict_truck_plate_tips));
                ((Button) this.b.findViewById(R.id.city_list_set_car_plate)).setText(getString(R.string.restrict_set_truck_plate));
                return;
            }
            this.b.setVisibility(8);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.search_clear) {
            this.e.setText("");
            a((Context) getActivity());
        }
    }

    public void afterTextChanged(Editable editable) {
        String obj = editable.toString();
        if (obj.length() > 0 && (obj.toCharArray()[obj.length() - 1] == 10 || obj.toCharArray()[obj.length() - 1] == 13)) {
            this.e.setText(obj.substring(0, obj.length() - 1));
        }
        a(this.e.getText().toString());
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if ((e() == 1 && (this.q == null || this.q.size() <= 0)) || (e() == 0 && (this.p == null || this.p.size() <= 0))) {
            return;
        }
        if (!b(str)) {
            b(4);
        } else {
            b(0);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.ArrayList<defpackage.dhs> a(java.util.List<defpackage.dhr> r10) {
        /*
            r9 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = 0
        L_0x0007:
            java.lang.String[] r3 = r9.u
            int r3 = r3.length
            if (r2 >= r3) goto L_0x0024
            dhs r3 = new dhs
            r3.<init>()
            java.lang.String[] r4 = r9.u
            r4 = r4[r2]
            r3.a = r4
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r3.b = r4
            r0.add(r3)
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0024:
            int r2 = r9.e()
            r3 = 1
            if (r2 != 0) goto L_0x0042
            java.util.LinkedList<dhr> r2 = r9.h
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x0058
            java.lang.Object r2 = r0.get(r1)
            dhs r2 = (defpackage.dhs) r2
            java.util.List<dhr> r2 = r2.b
            java.util.LinkedList<dhr> r4 = r9.h
            r2.addAll(r4)
        L_0x0040:
            r2 = 1
            goto L_0x0059
        L_0x0042:
            java.util.LinkedList<dhr> r2 = r9.i
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x0058
            java.lang.Object r2 = r0.get(r1)
            dhs r2 = (defpackage.dhs) r2
            java.util.List<dhr> r2 = r2.b
            java.util.LinkedList<dhr> r4 = r9.i
            r2.addAll(r4)
            goto L_0x0040
        L_0x0058:
            r2 = 0
        L_0x0059:
            r4 = 0
        L_0x005a:
            int r5 = r10.size()
            if (r4 >= r5) goto L_0x0090
            java.lang.Object r5 = r10.get(r4)
            dhr r5 = (defpackage.dhr) r5
            java.lang.String r6 = r5.b
            if (r6 == 0) goto L_0x008d
            java.lang.String r6 = r6.substring(r1, r3)
            r7 = 0
        L_0x006f:
            java.lang.String[] r8 = r9.u
            int r8 = r8.length
            if (r7 >= r8) goto L_0x008d
            java.lang.String[] r8 = r9.u
            r8 = r8[r7]
            boolean r8 = r8.equalsIgnoreCase(r6)
            if (r8 == 0) goto L_0x008a
            java.lang.Object r6 = r0.get(r7)
            dhs r6 = (defpackage.dhs) r6
            java.util.List<dhr> r6 = r6.b
            r6.add(r5)
            goto L_0x008d
        L_0x008a:
            int r7 = r7 + 1
            goto L_0x006f
        L_0x008d:
            int r4 = r4 + 1
            goto L_0x005a
        L_0x0090:
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
        L_0x0095:
            int r3 = r0.size()
            if (r1 >= r3) goto L_0x00ba
            java.lang.Object r3 = r0.get(r1)
            dhs r3 = (defpackage.dhs) r3
            java.util.List<dhr> r3 = r3.b
            if (r2 == 0) goto L_0x00a7
            if (r1 == 0) goto L_0x00aa
        L_0x00a7:
            java.util.Collections.sort(r3)
        L_0x00aa:
            int r3 = r3.size()
            if (r3 <= 0) goto L_0x00b7
            java.lang.Object r3 = r0.get(r1)
            r10.add(r3)
        L_0x00b7:
            int r1 = r1 + 1
            goto L_0x0095
        L_0x00ba:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.restrictedarea.RestrictedCityListFragment.a(java.util.List):java.util.ArrayList");
    }

    public final void a(int i2) {
        int i3 = i2 == 0 ? 0 : 1;
        if (i3 != this.v) {
            this.v = i3;
            if (this.n != null) {
                this.n.setSelectTab(this.v);
            }
        }
        a();
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
        this.c.removeAllViews();
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.route_car_result_restricted_city_list_error_tips, this.c, true);
        View findViewById = inflate.findViewById(R.id.restrict_tips_error_on_loading);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.restrict_tips_error_icon);
        TextView textView = (TextView) inflate.findViewById(R.id.restrict_tips_text);
        switch (i2) {
            case 1:
                findViewById.setVisibility(0);
                imageView.setVisibility(8);
                textView.setText(getString(R.string.restrict_loading));
                this.c.setVisibility(0);
                this.d.setVisibility(8);
                return;
            case 2:
                findViewById.setVisibility(8);
                imageView.setVisibility(0);
                imageView.setImageResource(R.drawable.restricted_area_fragment_loading_failed_selector);
                imageView.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        RestrictedCityListFragment.this.m = RestrictedCityListFragment.this.c();
                    }
                });
                textView.setText(getString(R.string.restrict_net_error));
                this.c.setVisibility(0);
                this.d.setVisibility(8);
                return;
            case 3:
                findViewById.setVisibility(8);
                imageView.setVisibility(0);
                imageView.setImageResource(R.drawable.restrict_city_list_empty);
                textView.setText(getString(R.string.restrict_city_list_no_cities));
                this.c.setVisibility(0);
                this.d.setVisibility(8);
                return;
            case 4:
                this.c.setVisibility(8);
                this.d.setVisibility(0);
                return;
            default:
                this.c.setVisibility(8);
                this.d.setVisibility(8);
                return;
        }
    }

    public static boolean b() {
        return TextUtils.isEmpty(DriveUtil.getCarPlateNumber()) && TextUtils.isEmpty(DriveUtil.getTruckCarPlateNumber());
    }

    private String[] b(List<dhr> list) {
        HashSet hashSet = new HashSet();
        for (dhr next : list) {
            String str = next.b;
            if (!TextUtils.isEmpty(str) && str.length() > 0) {
                hashSet.add(next.b.substring(0, 1));
            }
        }
        String[] strArr = new String[hashSet.size()];
        hashSet.toArray(strArr);
        Arrays.sort(strArr);
        if ((e() == 0 && this.h.isEmpty()) || (e() == 1 && this.i.isEmpty())) {
            return strArr;
        }
        String[] strArr2 = new String[(strArr.length + 1)];
        strArr2[0] = "常用城市";
        System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
        return strArr2;
    }

    /* access modifiers changed from: private */
    public void c(int i2) {
        String[] strArr;
        List<dhs> list;
        List<dhr> list2 = i2 == 0 ? this.p : i2 == 1 ? this.q : null;
        if (list2 != null) {
            if (list2.size() <= 0) {
                b(3);
                return;
            }
            this.r.clear();
            this.r.addAll(list2);
            a aVar = this.t;
            if (i2 != 0 ? !(!aVar.b || aVar.d == null || aVar.f == null) : !(!aVar.a || aVar.c == null || aVar.e == null)) {
                a aVar2 = this.t;
                if (i2 == 0) {
                    strArr = aVar2.c;
                } else {
                    strArr = aVar2.d;
                }
                this.u = strArr;
                a aVar3 = this.t;
                if (i2 == 0) {
                    list = aVar3.e;
                } else {
                    list = aVar3.f;
                }
                this.s = list;
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(list2);
                this.u = b((List<dhr>) arrayList);
                this.s = a((List<dhr>) arrayList, false);
                a aVar4 = this.t;
                String[] strArr2 = this.u;
                List<dhs> list3 = this.s;
                if (i2 == 0) {
                    aVar4.c = strArr2;
                    aVar4.e = list3;
                } else {
                    aVar4.d = strArr2;
                    aVar4.f = list3;
                }
                this.t.a(i2, true);
            }
            if (this.o == null) {
                this.o = new ExpandableListAdapter(getActivity(), this.s, false);
                this.a.setAdapter(this.o);
            } else {
                this.o.setLocalList(this.s, false);
            }
            if ((e() != 0 || !this.h.isEmpty()) && (e() != 1 || !this.i.isEmpty())) {
                String[] strArr3 = new String[this.u.length];
                System.arraycopy(this.u, 0, strArr3, 0, this.u.length);
                strArr3[0] = "常";
                this.g.updateIndex(this.s, strArr3);
            } else {
                this.g.updateIndex(this.s, this.u);
            }
            if (this.s != null) {
                for (int i3 = 0; i3 < this.s.size(); i3++) {
                    this.a.expandGroup(i3);
                }
            }
            b(0);
        }
    }

    public final AosRequest c() {
        Car car;
        b(1);
        RestrictedAreaRequest restrictedAreaRequest = new RestrictedAreaRequest();
        restrictedAreaRequest.b = 8;
        restrictedAreaRequest.o = "110000";
        restrictedAreaRequest.c = "高A0000";
        if (e() == 1) {
            car = DriveUtil.getCarTruckInfo();
        } else {
            car = DriveUtil.getCarInfo();
        }
        restrictedAreaRequest.e = DriveUtil.getVtype(car, e());
        NavigationRequestHolder.getInstance().sendRestrictedArea(restrictedAreaRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                com.autonavi.minimap.drive.restrictedarea.RestrictedCityListCallback.a aVar = new com.autonavi.minimap.drive.restrictedarea.RestrictedCityListCallback.a();
                try {
                    aVar.parser(aosByteResponse.getResponseBodyData());
                } catch (UnsupportedEncodingException e) {
                    kf.a((Throwable) e);
                } catch (JSONException e2) {
                    kf.a((Throwable) e2);
                }
                RestrictedCityListFragment.this.p = aVar.a;
                RestrictedCityListFragment.this.q = aVar.b;
                RestrictedCityListFragment.this.c(RestrictedCityListFragment.this.e());
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                RestrictedCityListFragment.this.b(2);
            }
        });
        return restrictedAreaRequest;
    }

    public final void d() {
        int i2;
        ArrayList<lj> b2 = li.a().b();
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        lj a2 = latestPosition != null ? a((List<lj>) b2, (latestPosition.getAdCode() / 100) * 100) : null;
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        String stringValue = mapSharePreference.getStringValue("restrictHotCities", null);
        if (!TextUtils.isEmpty(stringValue)) {
            String[] split = stringValue.split(",");
            for (int length = split.length - 1; length >= 0; length--) {
                try {
                    i2 = Integer.parseInt(split[length]);
                } catch (NumberFormatException e2) {
                    kf.a((Throwable) e2);
                    i2 = -1;
                }
                lj a3 = a((List<lj>) b2, i2);
                if (!(a3 == null || a3.j == 0)) {
                    if (a2 == null) {
                        a(new dhr(a3.a, String.valueOf(a3.j), a3.b));
                    } else if (a3.j != a2.j) {
                        a(new dhr(a3.a, String.valueOf(a3.j), a3.b));
                    }
                }
            }
        }
        String stringValue2 = mapSharePreference.getStringValue("restrictTruckHotCities", null);
        if (!TextUtils.isEmpty(stringValue2)) {
            String[] split2 = stringValue2.split(",");
            for (int length2 = split2.length - 1; length2 >= 0; length2--) {
                lj a4 = a((List<lj>) b2, Integer.parseInt(split2[length2]));
                if (!(a4 == null || a4.j == 0)) {
                    if (a2 == null) {
                        b(new dhr(a4.a, String.valueOf(a4.j), a4.b));
                    } else if (a4.j != a2.j) {
                        b(new dhr(a4.a, String.valueOf(a4.j), a4.b));
                    }
                }
            }
        }
        if (a2 != null) {
            this.j = new dhr(a2.a, String.valueOf(a2.j), a2.b, getString(R.string.restrict_city_list_cur_city));
            a(this.j);
            b(this.j);
        }
    }

    /* access modifiers changed from: private */
    public void a(dhr dhr) {
        if (dhr != null) {
            this.h.remove(dhr);
            if (this.h.size() >= 10) {
                this.h.removeLast();
            }
            this.h.addFirst(dhr);
            if (this.j != null) {
                this.h.remove(this.j);
                this.h.addFirst(this.j);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(dhr dhr) {
        if (dhr != null) {
            this.i.remove(dhr);
            if (this.i.size() >= 10) {
                this.i.removeLast();
            }
            this.i.addFirst(dhr);
            if (this.j != null) {
                this.i.remove(this.j);
                this.i.addFirst(this.j);
            }
        }
    }

    private static lj a(List<lj> list, int i2) {
        for (lj next : list) {
            if (next.j == i2) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void a(Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(this.e.getWindowToken(), 0);
    }

    private synchronized boolean b(String str) {
        boolean z;
        z = true;
        if (TextUtils.isEmpty(str)) {
            c(e());
            this.f.setVisibility(8);
            this.g.setVisibility(0);
        } else {
            List<dhr> list = e() == 0 ? this.p : this.q;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (int i2 = 0; i2 < list.size(); i2++) {
                dhr dhr = list.get(i2);
                if (dhr.a.indexOf(str) == 0) {
                    arrayList3.add(dhr);
                } else if (dhr.b.toLowerCase().indexOf(str.toLowerCase()) == 0) {
                    arrayList2.add(dhr);
                }
            }
            arrayList.addAll(arrayList2);
            arrayList.addAll(arrayList3);
            this.s = a((List<dhr>) arrayList, true);
            if (this.o != null) {
                this.o.setLocalList(this.s, true);
            }
            this.a.expandGroup(0);
            this.a.setSelection(0);
            this.f.setVisibility(0);
            this.g.setVisibility(8);
        }
        Iterator<dhs> it = this.s.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().b.size() > 0) {
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        return z;
    }

    public final int e() {
        return this.v == 0 ? 0 : 1;
    }

    private List<dhs> a(List<dhr> list, boolean z) {
        if (!z) {
            return a(list);
        }
        ArrayList arrayList = new ArrayList();
        dhs dhs = new dhs();
        dhs.b = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            dhs.b.add(list.get(i2));
        }
        arrayList.add(dhs);
        return arrayList;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dhv(this);
    }
}
