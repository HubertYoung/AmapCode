package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.amap.bundle.drivecommon.restrictedarea.RestrictedAreaParam;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.cloudsync.widget.IphoneTreeView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.restrictedarea.DriveIndexView;
import com.autonavi.minimap.drive.restrictedarea.RestrictedCityListFragment;
import com.autonavi.minimap.drive.widget.CarPlateInputView;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: dhv reason: default package */
/* compiled from: RestrictedCityListPresenter */
public final class dhv extends sw<RestrictedCityListFragment, dhu> {
    public dhv(RestrictedCityListFragment restrictedCityListFragment) {
        super(restrictedCityListFragment);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        RestrictedCityListFragment restrictedCityListFragment = (RestrictedCityListFragment) this.mPage;
        restrictedCityListFragment.requestScreenOrientation(1);
        View contentView = restrictedCityListFragment.getContentView();
        if (RestrictedCityListFragment.b()) {
            restrictedCityListFragment.b = ((ViewStub) restrictedCityListFragment.getContentView().findViewById(R.id.city_list_tips_view_stup)).inflate();
            restrictedCityListFragment.b.findViewById(R.id.city_list_set_car_plate).setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (RestrictedCityListFragment.this.e() == 0) {
                        RestrictedCityListFragment.this.startPageForResult((String) "amap.basemap.action.car_plate_input", new PageBundle(), 1001);
                        return;
                    }
                    if (RestrictedCityListFragment.this.e() == 1) {
                        DriveUtil.startAddCarPage(2, RestrictedCityListFragment.this);
                    }
                }
            });
        }
        restrictedCityListFragment.c = (FrameLayout) contentView.findViewById(R.id.error_view_layout);
        restrictedCityListFragment.e = (EditText) contentView.findViewById(R.id.search_text);
        if (restrictedCityListFragment.e != null) {
            restrictedCityListFragment.e.addTextChangedListener(restrictedCityListFragment);
        }
        restrictedCityListFragment.n = (TitleBar) contentView.findViewById(R.id.title);
        if (restrictedCityListFragment.n != null) {
            restrictedCityListFragment.n.setActionImgVisibility(8);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new b((CharSequence) "小客车"));
            arrayList.add(new b((CharSequence) "货车"));
            restrictedCityListFragment.n.addTabs(arrayList, 0);
            restrictedCityListFragment.n.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    RestrictedCityListFragment.this.finish();
                }
            });
            restrictedCityListFragment.n.setOnTabSelectedListener(new erq() {
                public final void a(int i) {
                    RestrictedCityListFragment.this.v = i;
                    if (i == 0) {
                        RestrictedCityListFragment.this.a(0);
                        String obj = RestrictedCityListFragment.this.e.getText().toString();
                        if (!TextUtils.isEmpty(obj)) {
                            RestrictedCityListFragment.this.a(obj);
                        } else {
                            RestrictedCityListFragment.this.c(RestrictedCityListFragment.this.e());
                        }
                    } else {
                        RestrictedCityListFragment.this.a(1);
                        String obj2 = RestrictedCityListFragment.this.e.getText().toString();
                        if (!TextUtils.isEmpty(obj2)) {
                            RestrictedCityListFragment.this.a(obj2);
                        } else {
                            RestrictedCityListFragment.this.c(RestrictedCityListFragment.this.e());
                        }
                    }
                }

                public final void b(int i) {
                    RestrictedCityListFragment.this.v = i;
                }
            });
        }
        if (!TextUtils.isEmpty(DriveUtil.getCarPlateNumber()) || TextUtils.isEmpty(DriveUtil.getTruckCarPlateNumber())) {
            restrictedCityListFragment.a(0);
        } else {
            restrictedCityListFragment.a(1);
        }
        restrictedCityListFragment.f = (ImageButton) contentView.findViewById(R.id.search_clear);
        restrictedCityListFragment.f.setOnClickListener(restrictedCityListFragment);
        restrictedCityListFragment.a = (IphoneTreeView) contentView.findViewById(R.id.city_list);
        restrictedCityListFragment.d = contentView.findViewById(R.id.city_list_empty_on_search);
        restrictedCityListFragment.a.setHeaderView(restrictedCityListFragment.getActivity().getLayoutInflater().inflate(R.layout.route_car_result_restricted_area_city_list_head_item_layout, restrictedCityListFragment.a, false));
        restrictedCityListFragment.a.setGroupIndicator(null);
        restrictedCityListFragment.g = (DriveIndexView) contentView.findViewById(R.id.city_index);
        restrictedCityListFragment.g.init(restrictedCityListFragment.getActivity(), restrictedCityListFragment.a);
        restrictedCityListFragment.a.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }
        });
        restrictedCityListFragment.a.setOnChildClickListener(new OnChildClickListener() {
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                if (RestrictedCityListFragment.this.o == null) {
                    return false;
                }
                List<dhr> list = RestrictedCityListFragment.this.o.getLocalList().get(i).b;
                if (list == null || list.isEmpty()) {
                    return false;
                }
                dhr dhr = list.get(i2);
                if (dhr == null) {
                    return false;
                }
                if (RestrictedCityListFragment.this.j == null || !RestrictedCityListFragment.this.j.equals(dhr) || ((RestrictedCityListFragment.this.e() != 0 || RestrictedCityListFragment.this.p.contains(RestrictedCityListFragment.this.j)) && (RestrictedCityListFragment.this.e() != 1 || RestrictedCityListFragment.this.q.contains(RestrictedCityListFragment.this.j)))) {
                    if (RestrictedCityListFragment.this.e() == 0) {
                        RestrictedCityListFragment.this.a(dhr);
                        RestrictedCityListFragment.this.t.a(0, false);
                    } else {
                        RestrictedCityListFragment.this.b(dhr);
                        RestrictedCityListFragment.this.t.a(1, false);
                    }
                    RestrictedCityListFragment.this.c(RestrictedCityListFragment.this.e());
                    RestrictedCityListFragment.this.e.setText("");
                    RestrictedCityListFragment.this.e.clearFocus();
                    RestrictedCityListFragment.this.a((Context) RestrictedCityListFragment.this.getActivity());
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putString("url", "path://amap_bundle_drive/src/car/restrict_page/CarRestrictPage.page.js");
                    pageBundle.putInt("source", 1);
                    pageBundle.putObject(AutoJsonUtils.JSON_ADCODE, RestrictedAreaParam.buildCityRestrictPolicyParam(dhr.a(), RestrictedCityListFragment.this.e()));
                    pageBundle.putInt("cartype", RestrictedCityListFragment.this.e());
                    if (RestrictedCityListFragment.b()) {
                        RestrictedCityListFragment.this.startPageForResult((String) "amap.basemap.action.car_restrict", pageBundle, 1001);
                    } else {
                        RestrictedCityListFragment.this.startPage((String) "amap.basemap.action.car_restrict", pageBundle);
                    }
                    return false;
                }
                ToastHelper.showLongToast(RestrictedCityListFragment.this.getString(R.string.restrict_area_loading_no_data));
                return false;
            }
        });
        restrictedCityListFragment.d();
        PageBundle arguments = restrictedCityListFragment.getArguments();
        if (arguments != null && arguments.containsKey("bundle_key_car_or_truck")) {
            int i = arguments.getInt("bundle_key_car_or_truck", -1);
            if (i == 0) {
                restrictedCityListFragment.a(0);
            } else if (i == 1) {
                restrictedCityListFragment.a(1);
            }
        }
        RestrictedCityListFragment restrictedCityListFragment2 = (RestrictedCityListFragment) this.mPage;
        restrictedCityListFragment2.m = restrictedCityListFragment2.c();
    }

    public final void onStop() {
        super.onStop();
        RestrictedCityListFragment restrictedCityListFragment = (RestrictedCityListFragment) this.mPage;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(restrictedCityListFragment.h);
        if (restrictedCityListFragment.j != null) {
            arrayList.remove(restrictedCityListFragment.j);
        }
        Iterator it = arrayList.iterator();
        StringBuilder sb = new StringBuilder();
        if (it.hasNext()) {
            sb.append(((dhr) it.next()).a());
            while (it.hasNext()) {
                sb.append(",");
                sb.append(((dhr) it.next()).a());
            }
        }
        arrayList.clear();
        arrayList.addAll(restrictedCityListFragment.i);
        if (restrictedCityListFragment.j != null) {
            arrayList.remove(restrictedCityListFragment.j);
        }
        Iterator it2 = arrayList.iterator();
        StringBuilder sb2 = new StringBuilder();
        if (it2.hasNext()) {
            sb2.append(((dhr) it2.next()).a());
            while (it2.hasNext()) {
                sb2.append(",");
                sb2.append(((dhr) it2.next()).a());
            }
        }
        Editor edit = new MapSharePreference(SharePreferenceName.SharedPreferences).edit();
        edit.putString("restrictHotCities", sb.toString());
        edit.putString("restrictTruckHotCities", sb2.toString());
        edit.apply();
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onResume() {
        super.onResume();
        RestrictedCityListFragment restrictedCityListFragment = (RestrictedCityListFragment) this.mPage;
        if (!restrictedCityListFragment.a.hasFocus()) {
            restrictedCityListFragment.a.requestFocus();
        }
        if (restrictedCityListFragment.e() == 0) {
            restrictedCityListFragment.k = DriveUtil.getCarPlateNumber();
            restrictedCityListFragment.l = 1;
        } else {
            restrictedCityListFragment.k = DriveUtil.getTruckCarPlateNumber();
            restrictedCityListFragment.l = 2;
        }
        restrictedCityListFragment.a();
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onDestroy() {
        super.onDestroy();
        RestrictedCityListFragment restrictedCityListFragment = (RestrictedCityListFragment) this.mPage;
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(restrictedCityListFragment.k)) {
            pageBundle.putString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER, restrictedCityListFragment.k);
            pageBundle.putInt("bundle_key_car_plate_type", restrictedCityListFragment.l);
        }
        restrictedCityListFragment.setResult(ResultType.OK, pageBundle);
        if (restrictedCityListFragment.m != null) {
            restrictedCityListFragment.m.cancel();
            restrictedCityListFragment.m = null;
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        RestrictedCityListFragment restrictedCityListFragment = (RestrictedCityListFragment) this.mPage;
        if ((i == 1001 || i == 1002) && resultType == ResultType.OK) {
            restrictedCityListFragment.k = pageBundle.getString(CarPlateInputView.BUNDLE_KEY_CAR_PLATE_NUMBER);
            restrictedCityListFragment.l = pageBundle.getInt("bundle_key_car_plate_type");
            restrictedCityListFragment.a();
        }
    }

    public final /* synthetic */ su a() {
        return new dhu(this);
    }
}
