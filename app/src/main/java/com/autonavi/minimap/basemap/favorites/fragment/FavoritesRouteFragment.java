package com.autonavi.minimap.basemap.favorites.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.model.FavoritesRouteAdapter;
import com.autonavi.minimap.basemap.favorites.page.FavoritesPage;
import com.autonavi.minimap.basemap.save.page.SavePointEditMenuPage.a;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FavoritesRouteFragment extends Fragment implements a, cow, coz {
    public static final String TAG = "FavoritesRouteFragment";
    /* access modifiers changed from: private */
    public List<String> mFavoriteRouteIds;
    /* access modifiers changed from: private */
    public FavoritesPage mParentFragment;
    private View noRouteView;
    /* access modifiers changed from: private */
    public FavoritesRouteAdapter routeAdapter;
    private ListView routeListView;

    public void editSavePoint(bth bth) {
        if (bth != null) {
        }
    }

    public void onEditFragmentResult(int i, ResultType resultType, PageBundle pageBundle) {
    }

    public void onItemLongClick() {
    }

    public void onShowDialogFragment() {
    }

    public FavoritesRouteFragment(FavoritesPage favoritesPage) {
        this.mParentFragment = favoritesPage;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.routeListView = (ListView) view.findViewById(R.id.list_route);
        this.mFavoriteRouteIds = new ArrayList();
        this.noRouteView = view.findViewById(R.id.fav_route_no_data_rl);
        this.routeAdapter = new FavoritesRouteAdapter(this.mParentFragment, this.mFavoriteRouteIds);
        this.routeAdapter.setFavoritesListActionListener(this);
        this.routeListView.setAdapter(this.routeAdapter);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.favorites_route_fragment, null);
    }

    public void onParentDeleteClick() {
        ahl.b(new a<Set<String>>() {
            public final /* synthetic */ void onFinished(Object obj) {
                Set set = (Set) obj;
                FavoritesRouteFragment favoritesRouteFragment = FavoritesRouteFragment.this;
                if (favoritesRouteFragment != null && !favoritesRouteFragment.isRemoving() && !favoritesRouteFragment.isDetached() && set != null && !set.isEmpty()) {
                    FavoritesRouteFragment.this.mFavoriteRouteIds.removeAll(set);
                    FavoritesRouteFragment.this.routeAdapter.getCheckedItems().clear();
                    try {
                        FavoritesRouteFragment.this.refreshListView();
                    } catch (Throwable unused) {
                    }
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                Map<String, String> checkedItems = FavoritesRouteFragment.this.routeAdapter.getCheckedItems();
                if (checkedItems != null && checkedItems.size() > 0) {
                    com com2 = (com) ank.a(com.class);
                    if (com2 != null) {
                        coq a2 = com2.a(com2.a());
                        for (Entry next : checkedItems.entrySet()) {
                            a2.a((String) next.getValue(), new cpc((String) next.getKey()).d.routeType);
                        }
                        return checkedItems.keySet();
                    }
                }
                return null;
            }
        });
    }

    public void onParentManageClick() {
        if (this.mParentFragment.g) {
            setEditModeEnabled(false, false);
            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_ROUTE_EDITMODE, "B001");
            return;
        }
        setEditModeEnabled(true, true);
        LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_ROUTE, "B005");
        onItemCheckedChangeListener();
    }

    public void setEditModeEnabled(boolean z, boolean z2) {
        this.mParentFragment.a(z);
        if (this.routeAdapter != null) {
            this.routeAdapter.setEditMode(z, z2);
        }
        if (!z) {
            this.mParentFragment.a((this.mFavoriteRouteIds == null || this.mFavoriteRouteIds.size() <= 0) ? 8 : 0, (Fragment) this);
        }
        this.routeAdapter.notifyDataSetChanged();
    }

    public void loadDataAsync(final boolean z) {
        this.mParentFragment.a((this.mFavoriteRouteIds == null || this.mFavoriteRouteIds.size() <= 0) ? 8 : 0, (Fragment) this);
        ahl.b(new a<List<String>>() {
            public final /* synthetic */ void onFinished(Object obj) {
                List list = (List) obj;
                if (list != null) {
                    FavoritesRouteFragment.this.mFavoriteRouteIds.clear();
                    FavoritesRouteFragment.this.mFavoriteRouteIds.addAll(list);
                    try {
                        FavoritesRouteFragment.this.refreshListView();
                    } catch (Throwable unused) {
                    }
                }
                if ((FavoritesRouteFragment.this.mFavoriteRouteIds == null || FavoritesRouteFragment.this.mFavoriteRouteIds.size() <= 0) && !FavoritesRouteFragment.this.mParentFragment.i && !bim.aa().A()) {
                    bim.aa().z();
                }
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                if (FavoritesRouteFragment.this.mFavoriteRouteIds == null || FavoritesRouteFragment.this.mFavoriteRouteIds.size() <= 0 || z) {
                    return bim.aa().s();
                }
                return null;
            }
        });
    }

    /* access modifiers changed from: private */
    public void refreshListView() {
        int i = 8;
        if (this.mFavoriteRouteIds == null || this.mFavoriteRouteIds.size() <= 0) {
            this.noRouteView.setVisibility(0);
            this.routeListView.setVisibility(8);
        } else {
            this.noRouteView.setVisibility(8);
            this.routeListView.setVisibility(0);
        }
        FavoritesPage favoritesPage = this.mParentFragment;
        if (this.mFavoriteRouteIds != null && this.mFavoriteRouteIds.size() > 0) {
            i = 0;
        }
        favoritesPage.a(i, (Fragment) this);
        if (this.mParentFragment.g) {
            if (this.mFavoriteRouteIds.size() <= 0) {
                setEditModeEnabled(false, false);
            } else {
                onItemCheckedChangeListener();
            }
        }
        this.routeAdapter.notifyDataSetChanged();
        AMapLog.debug("basemap.favorites", TAG, "refreshListView");
    }

    public void onItemCheckedChangeListener() {
        if (this.mParentFragment.g) {
            int checkedCount = this.routeAdapter.getCheckedCount();
            boolean z = true;
            this.mParentFragment.a(String.format(getStringSafely(R.string.favorites_route_selected), new Object[]{Integer.valueOf(checkedCount)}));
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
}
