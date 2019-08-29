package com.autonavi.bundle.cityselect.page;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.cloudsync.widget.IphoneTreeView;
import com.autonavi.common.cloudsync.widget.IphoneTreeView.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.LocationMode.LocationNetworkOnly;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.IndexView;
import com.autonavi.widget.ui.TitleBar;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;
import java.util.Arrays;

@PageAction("amap.basemap.action.switch_city_node_page")
public class SwitchCityNodePage extends AbstractBasePage<ats> implements TextWatcher, OnClickListener, LocationNetworkOnly {
    private TitleBar a;
    /* access modifiers changed from: private */
    public IphoneTreeView b;
    /* access modifiers changed from: private */
    public ExpandableListAdapter c;
    private EditText d;
    private ImageButton e;
    private IndexView f;

    class ExpandableListAdapter extends BaseExpandableListAdapter implements a {
        private Context context;
        private boolean isSearch;
        private ArrayList<cew> localList;

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

        public ExpandableListAdapter(Context context2) {
            this.context = context2;
        }

        public void setLocalList(ArrayList<cew> arrayList, boolean z) {
            if (this.localList != null) {
                this.localList.clear();
            }
            this.localList = arrayList;
            this.isSearch = z;
        }

        public final ArrayList<cew> getLocalList() {
            return this.localList;
        }

        public Object getChild(int i, int i2) {
            return this.localList.get(i).b.get(i2);
        }

        public int getChildrenCount(int i) {
            int i2;
            if (i < 0 || i >= this.localList.size()) {
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
            return this.localList.get(i);
        }

        public int getGroupCount() {
            if (this.localList == null) {
                return 0;
            }
            return this.localList.size();
        }

        public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.basemap_switchcity_list_child_item, null);
            }
            View findViewById = view.findViewById(R.id.view_switch_city_divider);
            findViewById.setVisibility(0);
            ArrayList<lj> arrayList = this.localList.get(i).b;
            lj ljVar = arrayList.get(i2);
            ((TextView) view.findViewById(R.id.name)).setText(ljVar.a);
            if (arrayList != null && arrayList.indexOf(ljVar) >= arrayList.size() - 1) {
                findViewById.setVisibility(8);
            }
            return view;
        }

        public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.basemap_switchcity_list_group_item, null);
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
            if (i2 != -1 || SwitchCityNodePage.this.b.isGroupExpanded(i)) {
                return 1;
            }
            return 0;
        }

        public void configureTreeHeader(View view, int i, int i2, int i3) {
            SwitchCityNodePage.this.mPresenter;
            ((TextView) view.findViewById(R.id.basemap_switchcity_indicator_text)).setText(ats.e[i]);
        }

        public void doRelatedActions() {
            SwitchCityNodePage.this.a(SwitchCityNodePage.this.getContext());
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.basemap_switchcity_layout);
        ats ats = (ats) this.mPresenter;
        PageBundle arguments = getArguments();
        ats.a = arguments.getInt("SWITCH_CITY_FOR", 0);
        if (arguments.containsKey("SWICH_CITY_HOTS_FROM")) {
            ats.b = arguments.getInt("SWICH_CITY_HOTS_FROM");
        }
        ats.c = ats.a();
        if (ats.c == null) {
            ((SwitchCityNodePage) ats.mPage).finish();
        } else {
            if (ats.b == 1) {
                ats.e[0] = AMapAppGlobal.getApplication().getString(R.string.popular_city);
            }
            ats.d = ats.a(ats.c, false);
        }
        this.a = (TitleBar) findViewById(R.id.title);
        this.a.setTitle(getString(R.string.switch_city));
        this.a.setOnBackClickListener((OnClickListener) this);
        this.d = (EditText) findViewById(R.id.search_text);
        this.d.addTextChangedListener(this);
        this.e = (ImageButton) findViewById(R.id.search_clear);
        this.e.setOnClickListener(this);
        this.c = new ExpandableListAdapter(getActivity());
        this.c.setLocalList(((ats) this.mPresenter).d, false);
        this.b = (IphoneTreeView) findViewById(R.id.ex_city_list);
        this.b.setAdapter(this.c);
        this.b.setHeaderView(getActivity().getLayoutInflater().inflate(R.layout.basemap_switchcity_list_head_item, this.b, false));
        this.b.setGroupIndicator(null);
        this.f = (IndexView) findViewById(R.id.index_view);
        this.f.setPageViewId(15, 2);
        if (((ats) this.mPresenter).b == 1) {
            this.f.setIndexValue(0, AMapAppGlobal.getApplication().getString(R.string.hot));
        }
        this.f.init(getActivity(), ((ats) this.mPresenter).d, this.b);
        if (((ats) this.mPresenter).d != null) {
            int size = ((ats) this.mPresenter).d.size();
            for (int i = 0; i < size; i++) {
                this.b.expandGroup(i);
            }
        }
        this.b.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }
        });
        this.b.setOnChildClickListener(new OnChildClickListener() {
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
                lj ljVar = SwitchCityNodePage.this.c.getLocalList().get(i).b.get(i2);
                if (ljVar == null) {
                    return false;
                }
                SwitchCityNodePage.this.a((Context) SwitchCityNodePage.this.getActivity());
                ats ats = (ats) SwitchCityNodePage.this.mPresenter;
                if (ats.b == 0) {
                    String valueOf = String.valueOf(ljVar.j);
                    SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
                    ArrayList arrayList = new ArrayList(Arrays.asList(sharedPrefs.getString("hotcity", "110000,310000,440100,440300").split(",")));
                    if (!arrayList.contains(valueOf)) {
                        arrayList.remove(3);
                        ats.a(valueOf, arrayList);
                    } else if (arrayList.indexOf(valueOf) != 0) {
                        arrayList.remove(valueOf);
                        ats.a(valueOf, arrayList);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append((String) arrayList.get(0));
                    sb.append(",");
                    sb.append((String) arrayList.get(1));
                    sb.append(",");
                    sb.append((String) arrayList.get(2));
                    sb.append(",");
                    sb.append((String) arrayList.get(3));
                    String sb2 = sb.toString();
                    Editor edit = sharedPrefs.edit();
                    edit.putString("hotcity", sb2);
                    if (VERSION.SDK_INT >= 9) {
                        edit.apply();
                    } else {
                        edit.commit();
                    }
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString(Constants.KEY_ACTION, "action_switch_city");
                pageBundle.putObject("key_map_center", new GeoPoint(ljVar.f, ljVar.g));
                pageBundle.putInt("key_map_level", ljVar.h);
                pageBundle.putString("key_area_name", ljVar.a);
                pageBundle.putString("key_city_adcode", String.valueOf(ljVar.j));
                pageBundle.putObject("key_city", ljVar);
                if (ats.a == 0) {
                    ((SwitchCityNodePage) ats.mPage).setResult(ResultType.OK, pageBundle);
                    ((SwitchCityNodePage) ats.mPage).finish();
                } else if (ats.mPage != null) {
                    ((SwitchCityNodePage) ats.mPage).startPage((String) "amap.basemap.action.default_page", pageBundle);
                }
                return false;
            }
        });
        this.a.setDivideVisibility(4);
    }

    /* access modifiers changed from: private */
    public void a(Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(this.d.getWindowToken(), 0);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.search_clear) {
            this.d.setText("");
            a((Context) getActivity());
            return;
        }
        if (view.getId() == R.id.title_back_img || view.getId() == R.id.title_back_text) {
            finish();
        }
    }

    public void afterTextChanged(Editable editable) {
        String obj = editable.toString();
        if (obj.length() > 0 && (obj.toCharArray()[obj.length() - 1] == 10 || obj.toCharArray()[obj.length() - 1] == 13)) {
            this.d.setText(obj.substring(0, obj.length() - 1));
        }
        ((ats) this.mPresenter).a(this.d.getText().toString());
        if (TextUtils.isEmpty(this.d.getText().toString())) {
            this.e.setVisibility(8);
            this.f.setVisibility(0);
            this.f.init(getActivity(), ((ats) this.mPresenter).d, this.b);
            return;
        }
        this.e.setVisibility(0);
        this.f.setVisibility(8);
    }

    public final void a(ArrayList<cew> arrayList, boolean z) {
        this.c.setLocalList(arrayList, z);
        this.c.notifyDataSetChanged();
        for (int i = 0; i < arrayList.size(); i++) {
            this.b.expandGroup(i);
        }
        this.b.setSelection(0);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ats(this);
    }
}
