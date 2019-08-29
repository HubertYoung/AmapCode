package defpackage;

import android.support.v4.app.FragmentTransaction;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.page.FavoritesPage;
import com.autonavi.minimap.basemap.favorites.view.FavoriteActionSheet;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.widget.ui.ActionSheet;

/* renamed from: cph reason: default package */
/* compiled from: FavoritesPresenter */
public final class cph extends AbstractBasePresenter<FavoritesPage> implements bgo {
    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public cph(FavoritesPage favoritesPage) {
        super(favoritesPage);
    }

    public final void onDestroy() {
        super.onDestroy();
        KeyboardUtil.hideInputMethod(((FavoritesPage) this.mPage).getActivity());
        FavoritesPage favoritesPage = (FavoritesPage) this.mPage;
        favoritesPage.c();
        favoritesPage.d();
        favoritesPage.j = false;
        bim.aa().d(true);
        bim.aa().a((biy) null);
        bim.aa().a((bix) null);
        bim.aa().a((biv) null);
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null) {
            dfm.a(CalcRouteScene.SCENE_FAVORITE);
        }
        FavoritesPage favoritesPage2 = (FavoritesPage) this.mPage;
        FragmentTransaction beginTransaction = favoritesPage2.getFragmentManager().beginTransaction();
        if (!(favoritesPage2.d == null || beginTransaction == null)) {
            beginTransaction.detach(favoritesPage2.d);
            beginTransaction.remove(favoritesPage2.d);
        }
        if (!(favoritesPage2.e == null || beginTransaction == null)) {
            beginTransaction.detach(favoritesPage2.e);
            beginTransaction.remove(favoritesPage2.e);
        }
        if (beginTransaction != null) {
            try {
                beginTransaction.commit();
            } catch (Exception e) {
                if (bno.a) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void onStart() {
        super.onStart();
        FavoritesPage favoritesPage = (FavoritesPage) this.mPage;
        favoritesPage.a();
        bim.aa().a((biy) new biy() {
            public final void updateSuccess() {
                FavoritesPage.a(FavoritesPage.this, (Runnable) new Runnable() {
                    public final void run() {
                        if (bim.aa().h() && FavoritesPage.this.j) {
                            ToastHelper.showToast(FavoritesPage.this.getString(R.string.all_sync_success));
                            bim.aa().e(false);
                            bim.aa().i(false);
                        }
                        FavoritesPage.this.i = true;
                        bim.aa().f(true);
                        if (FavoritesPage.this.f != null) {
                            if (!FavoritesPage.this.g) {
                                FavoritesPage.this.f.loadDataAsync(true);
                            }
                            if (FavoritesPage.this.e == FavoritesPage.this.f) {
                                FavoritesPage.this.q = true;
                                FavoritesPage.this.r = FavoritesPage.this.g;
                                return;
                            }
                            FavoritesPage.this.q = false;
                            FavoritesPage.this.r = FavoritesPage.this.g;
                        }
                    }
                });
            }
        });
        bim.aa().a((bix) new bix() {
            public final void a() {
                FavoritesPage.a(FavoritesPage.this, (Runnable) new Runnable() {
                    public final void run() {
                        if (bim.aa().h() && FavoritesPage.this.j) {
                            ToastHelper.showToast(FavoritesPage.this.getString(R.string.all_sync_failed));
                            bim.aa().e(false);
                            bim.aa().h(false);
                        }
                        if (FavoritesPage.this.f != null && !FavoritesPage.this.g) {
                            FavoritesPage.this.f.loadDataAsync(true);
                        }
                        FavoritesPage.this.i = true;
                        FavoritesPage.this.q = false;
                        FavoritesPage.this.r = false;
                    }
                });
            }
        });
    }

    public final void onStop() {
        super.onStop();
        FavoritesPage favoritesPage = (FavoritesPage) this.mPage;
        if (favoritesPage.b != null) {
            favoritesPage.b.hide();
        }
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        FavoritesPage favoritesPage = (FavoritesPage) this.mPage;
        int currentItem = favoritesPage.c.getCurrentItem();
        if (pageBundle != null && pageBundle.containsKey("key_tab_favorites")) {
            currentItem = pageBundle.getInt("key_tab_favorites") == 1 ? 1 : 0;
        }
        if (favoritesPage.a.getChildCount() >= currentItem) {
            favoritesPage.a.setSelectTab(currentItem);
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        FavoritesPage favoritesPage = (FavoritesPage) this.mPage;
        if (favoritesPage != null) {
            cow cow = favoritesPage.f;
            if (!(cow == null || pageBundle == null)) {
                favoritesPage.a();
                cow.onEditFragmentResult(i, resultType, pageBundle);
                if (resultType == ResultType.OK) {
                    if (pageBundle.get("change_type_key") != null && pageBundle.getInt("change_type_key") == 246) {
                        cow.onParentManageClick();
                    } else if (pageBundle.get("change_type_key") != null && pageBundle.getInt("change_type_key") == 247) {
                        favoritesPage.j = true;
                        favoritesPage.b();
                    }
                }
            }
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        bid pageContext = ((FavoritesPage) this.mPage).getPageContext();
        FavoriteActionSheet favoriteActionSheet = ((FavoritesPage) this.mPage).k;
        ActionSheet actionSheet = ((FavoritesPage) this.mPage).d.getFavoritesPointAdapter().getActionSheet();
        if (pageContext != null && pageContext.isViewLayerShowing(favoriteActionSheet)) {
            pageContext.dismissViewLayer(favoriteActionSheet);
            return super.onBackPressed();
        } else if (pageContext == null || !pageContext.isViewLayerShowing(actionSheet)) {
            FavoritesPage favoritesPage = (FavoritesPage) this.mPage;
            boolean z = false;
            if (favoritesPage.g) {
                favoritesPage.f.setEditModeEnabled(false, false);
                z = true;
            }
            if (z) {
                return ON_BACK_TYPE.TYPE_IGNORE;
            }
            return super.onBackPressed();
        } else {
            pageContext.dismissViewLayer(actionSheet);
            return super.onBackPressed();
        }
    }
}
