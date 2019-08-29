package com.autonavi.minimap.basemap.favorites.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.miniapp.plugin.constant.ConstantCompat.SaveSearchResultMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.archive.ArchiveRequestHolder;
import com.autonavi.minimap.archive.param.FavoritesNaviRemindRequest;
import com.autonavi.minimap.basemap.favorites.model.FavoritesPointAdapter;
import com.autonavi.minimap.basemap.favorites.page.FavoritesPage;
import com.autonavi.minimap.basemap.save.controller.SaveSyncAutoDataController$1;
import com.autonavi.minimap.basemap.save.page.SaveDuplicateConfirmPage;
import com.autonavi.minimap.basemap.save.page.SaveSearchPage;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.sync.beans.GirfFavoritePoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FavoritesPointFragment extends Fragment implements OnClickListener, com.autonavi.minimap.basemap.save.page.SavePointEditMenuPage.a, cow, coz {
    public static final int REQUEST_COMPNAY = 242;
    public static final int REQUEST_EDIT_POINT = 243;
    public static final int REQUEST_HOME = 241;
    public static final int REQUEST_MANAGER_SYNC = 244;
    public static final int REQUEST_NEW_POINT = 240;
    public static final int REQUEST_TAG_SELECT = 245;
    public static final String TAG = "FavoritesPointFragment";
    private ExpandableListView expandableListView;
    private boolean firstShowDuped = false;
    private boolean isCallLoadAndSyncByOwn = false;
    private View mAddFavoritesPointView;
    /* access modifiers changed from: private */
    public cpb mCompanyPoint;
    /* access modifiers changed from: private */
    public List<String> mFavoritePointIds;
    /* access modifiers changed from: private */
    public FavoritesPointAdapter mFavoritesPointAdapter;
    private List<cpa> mFavoritesPointGroups;
    /* access modifiers changed from: private */
    public cpb mHomePoint;
    public FavoritesPage mParentFragment;
    /* access modifiers changed from: private */
    public Button mSyncAutoDataBtn;

    static final class a {
        cpb a;
        cpb b;
        List<String> c;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public FavoritesPointFragment(FavoritesPage favoritesPage) {
        this.mParentFragment = favoritesPage;
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.favorites_point_fragment, null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mSyncAutoDataBtn = (Button) view.findViewById(R.id.sync_auto_data_bar);
        this.expandableListView = (ExpandableListView) view.findViewById(R.id.expandablelist);
        this.expandableListView.setGroupIndicator(null);
        this.expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }
        });
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.save_point_add_layout, null);
        this.mAddFavoritesPointView = inflate.findViewById(R.id.layout_add_save_point);
        this.mAddFavoritesPointView.setOnClickListener(this);
        inflate.setLayoutParams(new LayoutParams(-1, -2));
        this.expandableListView.addFooterView(inflate);
        this.mFavoritesPointGroups = new ArrayList();
        this.mFavoritesPointAdapter = new FavoritesPointAdapter(this.mParentFragment, this.mFavoritesPointGroups, this, this);
        this.mFavoritesPointAdapter.setFavoritesListActionListener(this);
        setFavoritesPointAdapter(this.mFavoritesPointAdapter);
        this.expandableListView.setAdapter(this.mFavoritesPointAdapter);
        this.expandableListView.setFriction(ViewConfiguration.getScrollFriction() * 3.0f);
        this.mFavoritesPointGroups.add(buildHomeCompanyPointGroup());
        this.mFavoritesPointAdapter.notifyDataSetChanged();
        this.firstShowDuped = false;
    }

    private cpb buildEmptyHomeCompanyPoint(String str) {
        GirfFavoritePoint girfFavoritePoint = new GirfFavoritePoint();
        girfFavoritePoint.commonName = str;
        return new cpb(girfFavoritePoint);
    }

    private cpa buildHomeCompanyPointGroup() {
        cpa cpa = new cpa();
        cpa.a = crt.d;
        ArrayList arrayList = new ArrayList();
        if (this.mHomePoint == null) {
            this.mHomePoint = buildEmptyHomeCompanyPoint(crt.b);
        }
        if (this.mCompanyPoint == null) {
            this.mCompanyPoint = buildEmptyHomeCompanyPoint(crt.c);
        }
        arrayList.add(this.mHomePoint);
        arrayList.add(this.mCompanyPoint);
        cpa.b = arrayList;
        return cpa;
    }

    private bty getMapView() {
        if (this.mParentFragment == null || DoNotUseTool.getMapManager() == null) {
            return null;
        }
        return DoNotUseTool.getMapManager().getMapView();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.layout_add_save_point) {
            this.mParentFragment.startPageForResult(SaveSearchPage.class, (PageBundle) null, 240);
            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B005");
        }
    }

    /* access modifiers changed from: private */
    public void loadHomeCompanyPoint(a aVar) {
        List<GirfFavoritePoint> o = bim.aa().o();
        List<GirfFavoritePoint> p = bim.aa().p();
        if (o == null || o.size() <= 0) {
            aVar.a = buildEmptyHomeCompanyPoint(crt.b);
        } else {
            aVar.a = new cpb(o.get(0));
        }
        if (p == null || p.size() <= 0) {
            aVar.b = buildEmptyHomeCompanyPoint(crt.c);
        } else {
            aVar.b = new cpb(p.get(0));
        }
    }

    public void loadDataAsync(final boolean z) {
        this.mParentFragment.a(isFavoritesPointListEmpty() ? 8 : 0, (Fragment) this);
        ahl.b(new defpackage.ahl.a<a>() {
            public final /* synthetic */ void onFinished(Object obj) {
                a aVar = (a) obj;
                if (aVar != null) {
                    FavoritesPointFragment.this.mFavoritePointIds = aVar.c;
                    if (aVar.a != null) {
                        FavoritesPointFragment.this.mHomePoint = aVar.a;
                    }
                    if (aVar.b != null) {
                        FavoritesPointFragment.this.mCompanyPoint = aVar.b;
                    }
                    try {
                        FavoritesPointFragment.this.refreshListView();
                    } catch (Throwable unused) {
                    }
                }
                if (!FavoritesPointFragment.this.mParentFragment.i && !bim.aa().A()) {
                    bim.aa().z();
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                if (FavoritesPointFragment.this.mFavoritePointIds != null && FavoritesPointFragment.this.mFavoritePointIds.size() > 0 && !z) {
                    return null;
                }
                a aVar = new a(0);
                aVar.c = bim.aa().y();
                FavoritesPointFragment.this.loadHomeCompanyPoint(aVar);
                return aVar;
            }
        });
    }

    private void showDuplicatePointSelectDialog(List<cox> list, boolean z) {
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        if (topPageClass == null || topPageClass != SaveDuplicateConfirmPage.class) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("key_duplicate_points", list);
            pageBundle.putBoolean("key_from_public", z);
            this.mParentFragment.startPage(SaveDuplicateConfirmPage.class, pageBundle);
        }
    }

    /* access modifiers changed from: private */
    public void refreshListView() {
        cpa cpa;
        ArrayList arrayList = new ArrayList();
        if (bim.aa().o() != null && bim.aa().o().size() > 1) {
            cox cox = new cox();
            cox.a = "家";
            List<GirfFavoritePoint> o = bim.aa().o();
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < o.size(); i++) {
                String str = o.get(i).item_id;
                bth a2 = bsr.a(bim.aa().b((String) "101", str), str, getUid());
                if (a2.d.equals("家")) {
                    arrayList2.add(a2);
                }
            }
            cox.b = arrayList2;
            if (cox.b != null && cox.b.size() > 1) {
                arrayList.add(cox);
            }
        }
        if (bim.aa().p() != null && bim.aa().p().size() > 1) {
            cox cox2 = new cox();
            cox2.a = "公司";
            List<GirfFavoritePoint> p = bim.aa().p();
            ArrayList arrayList3 = new ArrayList();
            for (int i2 = 0; i2 < p.size(); i2++) {
                String str2 = p.get(i2).item_id;
                bth a3 = bsr.a(bim.aa().b((String) "101", str2), str2, getUid());
                if (a3.d.equals("公司")) {
                    arrayList3.add(a3);
                }
            }
            cox2.b = arrayList3;
            if (cox2.b != null && cox2.b.size() > 1) {
                arrayList.add(cox2);
            }
        }
        if (arrayList.size() > 0) {
            showDuplicatePointSelectDialog(arrayList, true);
        }
        if (this.mFavoritePointIds == null || this.mFavoritePointIds.size() <= 0) {
            cpa = null;
        } else {
            cpa = new cpa();
            cpa.a = getStringSafely(R.string.save_other);
            cpa.c = this.mFavoritePointIds;
        }
        if (this.mFavoritesPointGroups.size() > 1) {
            if (cpa == null) {
                this.mFavoritesPointGroups.remove(1);
            } else {
                this.mFavoritesPointGroups.set(1, cpa);
            }
        } else if (cpa != null) {
            this.mFavoritesPointGroups.add(cpa);
        }
        this.mFavoritesPointGroups.set(0, buildHomeCompanyPointGroup());
        this.mParentFragment.a(isFavoritesPointListEmpty() ? 8 : 0, (Fragment) this);
        if (this.mParentFragment.g) {
            if (isFavoritesPointListEmpty()) {
                setEditModeEnabled(false, false);
            } else {
                onItemCheckedChangeListener();
            }
        }
        this.mFavoritesPointAdapter.notifyDataSetChanged();
        brn brn = (brn) ank.a(brn.class);
        if (brn != null) {
            brn.d();
        }
        AMapLog.debug("basemap.favorites", TAG, "refreshListView");
    }

    public void dealFragmentResult(POI poi, int i, boolean z, boolean z2) {
        if (poi != null) {
            doAddNewPoint(poi, i, z);
        }
    }

    private void doAddNewPoint(POI poi, int i, boolean z) {
        String a2 = cpm.b().a();
        if (i == 240) {
            if (poi != null) {
                this.isCallLoadAndSyncByOwn = true;
                FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
                if (i == 240) {
                    favoritePOI.setCommonName("");
                }
                cpf.b(a2).b((POI) favoritePOI);
                loadDataAsync(true);
            }
        } else if (i == 241) {
            this.isCallLoadAndSyncByOwn = true;
            dealAddUsefulPoint(poi, crt.b, z);
        } else if (i == 242) {
            this.isCallLoadAndSyncByOwn = true;
            dealAddUsefulPoint(poi, crt.c, z);
        }
    }

    private void buildSimplePoint(FavoritePOI favoritePOI, cpb cpb, String str) {
        cpf.b(str);
        cpm.b();
        cpb.b = cpm.b((POI) favoritePOI);
        cpb.k = favoritePOI.getCommonName();
        if (favoritePOI != null) {
            if (favoritePOI.getPoint() != null) {
                cpb.d = favoritePOI.getPoint().x;
                cpb.e = favoritePOI.getPoint().y;
            }
            cpb.c = favoritePOI.getName();
            cpb.l = favoritePOI.getAddr();
            cpb.g = favoritePOI.getCityCode();
            cpb.f = favoritePOI.getCityName();
        }
    }

    private void dealAddUsefulPoint(POI poi, String str, boolean z) {
        if (poi != null) {
            FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
            favoritePOI.setCommonName(str);
            String a2 = cpm.b().a();
            boolean z2 = true;
            if (str.equals(crt.b)) {
                List<GirfFavoritePoint> o = bim.aa().o();
                if (!(o == null || o.size() == 0)) {
                    z2 = false;
                }
                bim.aa().a(0);
            } else if (str.equals(crt.c)) {
                List<GirfFavoritePoint> p = bim.aa().p();
                if (!(p == null || p.size() == 0)) {
                    z2 = false;
                }
                bim.aa().b(0);
            } else {
                z2 = false;
            }
            cpf.b(a2).b((POI) favoritePOI);
            if (str.equals(crt.b) || str.equals(crt.c)) {
                cpf.a(z2);
            }
            FavoritePOI favoritePOI2 = (FavoritePOI) cpf.b(a2).d((POI) favoritePOI).as(FavoritePOI.class);
            if (str.equals(crt.c)) {
                buildSimplePoint(favoritePOI2, this.mCompanyPoint, a2);
                ank.a(djk.class);
            } else if (str.equals(crt.b)) {
                buildSimplePoint(favoritePOI2, this.mHomePoint, a2);
                ank.a(djk.class);
            }
            refreshListView();
        }
    }

    private POI parsePOI(PageBundle pageBundle) {
        if (pageBundle.containsKey("result_poi")) {
            return (POI) pageBundle.getObject("result_poi");
        }
        return null;
    }

    public void onItemCheckedChangeListener() {
        if (this.mParentFragment.g) {
            int checkedCount = this.mFavoritesPointAdapter.getCheckedCount();
            boolean z = true;
            this.mParentFragment.a(String.format(getStringSafely(R.string.favorites_point_selected), new Object[]{Integer.valueOf(checkedCount)}));
            FavoritesPage favoritesPage = this.mParentFragment;
            if (checkedCount == 0) {
                z = false;
            }
            favoritesPage.b(z);
        }
    }

    private String getStringSafely(int i) {
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null) {
            return "";
        }
        return appContext.getResources().getString(i);
    }

    public void onItemLongClick() {
        if (!this.mParentFragment.g) {
            setEditModeEnabled(true, false);
        }
    }

    public void onShowDialogFragment() {
        if (this.mParentFragment != null) {
            this.mParentFragment.d();
        }
    }

    public void setEditModeEnabled(boolean z, boolean z2) {
        this.mParentFragment.a(z);
        this.mFavoritesPointAdapter.setEditMode(z, z2);
        this.mFavoritesPointAdapter.notifyDataSetChanged();
        int i = 0;
        if (!z) {
            this.mParentFragment.a(isFavoritesPointListEmpty() ? 8 : 0, (Fragment) this);
        }
        View view = this.mAddFavoritesPointView;
        if (z) {
            i = 8;
        }
        view.setVisibility(i);
    }

    public void deleteFavoritesPoints(final bth bth) {
        ahl.b(new defpackage.ahl.a<Void>() {
            public final /* synthetic */ void onFinished(Object obj) {
                FavoritesPointFragment favoritesPointFragment = FavoritesPointFragment.this;
                if (favoritesPointFragment != null && !favoritesPointFragment.isDetached() && !favoritesPointFragment.isRemoving()) {
                    if (bth.d.equals(crt.b)) {
                        FavoritesPointFragment.this.mHomePoint = null;
                    } else if (bth.d.equals(crt.c)) {
                        FavoritesPointFragment.this.mCompanyPoint = null;
                    }
                    try {
                        FavoritesPointFragment.this.refreshListView();
                    } catch (Throwable unused) {
                    }
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                String str = "";
                coy coy = (coy) ank.a(coy.class);
                if (coy != null) {
                    str = coy.a();
                }
                cpf.b(str).a(bth.a());
                return null;
            }
        });
    }

    private boolean isFavoritesPointListEmpty() {
        return (this.mHomePoint == null || !this.mHomePoint.b()) && (this.mCompanyPoint == null || !this.mCompanyPoint.b()) && (this.mFavoritePointIds == null || this.mFavoritePointIds.isEmpty());
    }

    public void onParentDeleteClick() {
        FavoritesPage favoritesPage = this.mParentFragment;
        if (favoritesPage.h == null) {
            favoritesPage.h = new ProgressDlg(favoritesPage.getActivity());
            favoritesPage.h.setMessage(favoritesPage.getString(R.string.loading));
            favoritesPage.h.setCancelable(true);
        }
        if (favoritesPage.h.isShowing()) {
            favoritesPage.h.dismiss();
        }
        favoritesPage.h.show();
        ahl.b(new defpackage.ahl.a<Set<String>>() {
            public final /* synthetic */ void onFinished(Object obj) {
                Set set = (Set) obj;
                FavoritesPointFragment.this.mParentFragment.c();
                if (set != null && set.size() > 0) {
                    if (set.contains(crt.b)) {
                        FavoritesPointFragment.this.mHomePoint = null;
                    }
                    if (set.contains(crt.c)) {
                        FavoritesPointFragment.this.mCompanyPoint = null;
                    }
                    FavoritesPointFragment.this.mFavoritePointIds.removeAll(set);
                    FavoritesPointFragment.this.mFavoritesPointAdapter.getCheckedItems().clear();
                    try {
                        FavoritesPointFragment.this.refreshListView();
                    } catch (Throwable unused) {
                    }
                }
            }

            public final void onError(Throwable th) {
                FavoritesPointFragment.this.mParentFragment.c();
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                Map<String, String> checkedItems = FavoritesPointFragment.this.mFavoritesPointAdapter.getCheckedItems();
                if (checkedItems == null || checkedItems.size() <= 0) {
                    return null;
                }
                ArrayList arrayList = new ArrayList();
                for (String str : checkedItems.keySet()) {
                    arrayList.add(checkedItems.get(str));
                }
                String str2 = "";
                coy coy = (coy) ank.a(coy.class);
                if (coy != null) {
                    str2 = coy.a();
                }
                cpf.b(str2);
                cpf.d((List<String>) arrayList);
                return checkedItems.keySet();
            }
        });
    }

    public void onParentManageClick() {
        if (this.mParentFragment.g) {
            setEditModeEnabled(false, false);
            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT_EDITMODE, "B001");
            return;
        }
        setEditModeEnabled(true, true);
        showSyncAutoDataButton();
        onItemCheckedChangeListener();
        LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B013");
    }

    public void onEditFragmentResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (resultType == ResultType.OK) {
            boolean z = false;
            if (i == 240 || i == 242 || i == 241) {
                dealFragmentResult(parsePOI(pageBundle), i, false, (pageBundle == null || !pageBundle.containsKey(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY)) ? false : pageBundle.getBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY));
            } else if (i == 243) {
                if (pageBundle.containsKey("change_type_key") && pageBundle.containsKey("savepointkey")) {
                    deleteFavoritesPoints((bth) pageBundle.get("savepointkey"));
                } else if (pageBundle.containsKey("savepointkey")) {
                    doEditSavePoint((bth) pageBundle.get("savepointkey"));
                } else if (pageBundle.containsKey("result_poi") && pageBundle.containsKey("request_type_key")) {
                    POI poi = (POI) pageBundle.get("result_poi");
                    int i2 = pageBundle.getInt("request_type_key");
                    if (pageBundle != null && pageBundle.containsKey(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY)) {
                        z = pageBundle.getBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY);
                    }
                    if (i2 == 241 || i2 == 242) {
                        ToastHelper.showToast(getStringSafely(R.string.update_favourite_successful));
                    }
                    dealFragmentResult(poi, i2, true, z);
                }
            } else if (i == 245) {
                loadDataAsync(true);
            }
        }
    }

    public void doEditSavePoint(final bth bth) {
        if (bth != null) {
            ahl.b(new defpackage.ahl.a<Object>() {
                public final Object doBackground() throws Exception {
                    String str = "";
                    coy coy = (coy) ank.a(coy.class);
                    if (coy != null) {
                        str = coy.a();
                    }
                    cpf.b(str).g(bth.a());
                    return null;
                }

                public final void onFinished(Object obj) {
                    FavoritesPointFragment.this.loadDataAsync(true);
                }
            });
        }
    }

    private void showSyncAutoDataButton() {
        cra a2 = cra.a();
        AnonymousClass6 r1 = new defpackage.cra.a() {
            public final void a(String str, final String str2) {
                FavoritesPointFragment.this.mSyncAutoDataBtn.setVisibility(0);
                FavoritesPointFragment.this.mSyncAutoDataBtn.setText(str);
                FavoritesPointFragment.this.mSyncAutoDataBtn.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        aja aja = new aja(str2);
                        aja.b = new ajf();
                        aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                        if (aix != null) {
                            aix.a((bid) FavoritesPointFragment.this.mParentFragment, aja);
                        }
                        LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT_EDITMODE, "B005");
                    }
                });
                LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT_EDITMODE, "B004");
            }
        };
        if (NetworkReachability.b() && cra.a("com.autonavi.xmgd.navigator")) {
            ArchiveRequestHolder.getInstance().sendFavoritesNaviRemind(new FavoritesNaviRemindRequest(), new SaveSyncAutoDataController$1(a2, r1));
        }
    }

    public void editSavePoint(bth bth) {
        doEditSavePoint(bth);
    }

    public FavoritesPointAdapter getFavoritesPointAdapter() {
        return this.mFavoritesPointAdapter;
    }

    public void setFavoritesPointAdapter(FavoritesPointAdapter favoritesPointAdapter) {
        this.mFavoritesPointAdapter = favoritesPointAdapter;
    }

    private String getUid() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }
}
