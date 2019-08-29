package com.autonavi.minimap.basemap.favorites.page;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.adapter.SmartFragmentPagerAdapter;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesRouteFragment;
import com.autonavi.minimap.basemap.favorites.view.FavoriteActionSheet;
import com.autonavi.minimap.basemap.favorites.view.NonSwipeableViewPager;
import com.autonavi.minimap.widget.SyncPopupWindow;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.badgeview.BadgeView;
import com.autonavi.widget.ui.ActionSheet;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.b;
import java.util.ArrayList;
import org.json.JSONObject;

@PageAction("amap.basemap.action.favorite_page")
public class FavoritesPage extends AbstractBasePage<cph> implements OnClickListener, bgm, IVoiceCmdResponder, launchModeSingleInstance {
    public TitleBar a;
    public SyncPopupWindow b;
    public NonSwipeableViewPager c;
    public FavoritesPointFragment d;
    public FavoritesRouteFragment e;
    public cow f;
    public boolean g;
    public ProgressDlg h;
    public boolean i = false;
    public boolean j = false;
    public FavoriteActionSheet k;
    private TitleBar l;
    /* access modifiers changed from: private */
    public BadgeView m;
    private ArrayList<b> n = new ArrayList<>();
    private View o;
    private TextView p;
    /* access modifiers changed from: private */
    public boolean q = true;
    /* access modifiers changed from: private */
    public boolean r = true;
    private OnClickListener s = new OnClickListener() {
        public final void onClick(View view) {
            FavoritesPage.this.finish();
        }
    };
    private OnClickListener t = new OnClickListener() {
        public final void onClick(View view) {
            FavoritesPage.this.f.setEditModeEnabled(false, false);
        }
    };
    private OnClickListener u = new OnClickListener() {
        public final void onClick(View view) {
            FavoritesPage.this.d();
            FavoritesPage.h(FavoritesPage.this);
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("SaveManagerSyncRedPoint", false);
            FavoritesPage.this.m.setVisibility(8);
        }
    };
    private biv v = new biv() {
        public final void saveSucess() {
            IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
            if (iAccountService != null && !iAccountService.a()) {
                if (FavoritesPage.this.b == null) {
                    FavoritesPage.this.b = new SyncPopupWindow(FavoritesPage.this.getContentView());
                }
                FavoritesPage.this.b.show();
            }
        }
    };

    class a extends SimpleOnPageChangeListener {
        private a() {
        }

        /* synthetic */ a(FavoritesPage favoritesPage, byte b) {
            this();
        }

        public final void onPageSelected(int i) {
            switch (i) {
                case 0:
                    FavoritesPage.this.f = FavoritesPage.this.d;
                    FavoritesPage.this.f.loadDataAsync(FavoritesPage.this.q);
                    FavoritesPage.this.q = false;
                    return;
                case 1:
                    FavoritesPage.this.f = FavoritesPage.this.e;
                    FavoritesPage.this.f.loadDataAsync(FavoritesPage.this.r);
                    FavoritesPage.this.r = false;
                    break;
            }
        }
    }

    public void finishSelf() {
    }

    public long getScene() {
        return 68719476736L;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 68719476736L;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        int i2 = 1;
        requestScreenOrientation(1);
        this.j = false;
        setContentView(R.layout.favorites_fragment);
        View contentView = getContentView();
        this.a = (TitleBar) contentView.findViewById(R.id.title_tab);
        this.a.setActionImg(R.drawable.save_favorite_more);
        this.l = (TitleBar) contentView.findViewById(R.id.title_back);
        this.a.setOnBackClickListener(this.s);
        this.a.setOnActionClickListener(this.u);
        this.a.setActionImgContentDescription(getString(R.string.favorites_cont_des_more));
        this.l.setOnBackClickListener(this.t);
        this.m = new BadgeView(getActivity());
        this.m.setImageResource(R.drawable.idle_info);
        this.m.setBadgeMargin(0, agn.a(getContext(), 8.0f) + (euk.a() ? euk.a(getContext()) : 0), agn.a(getContext(), 6.0f), 0);
        this.m.setTargetView(this.a);
        this.m.setVisibility(8);
        this.n.add(new b((CharSequence) getString(R.string.fav_shou_chang_de_dian)));
        this.n.add(new b((CharSequence) getString(R.string.fav_shou_chang_lu_xian)));
        this.a.addTabs(this.n, 0);
        this.a.setOnTabSelectedListener(new erq() {
            public final void a(int i) {
                if (i == 0) {
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_ROUTE, "B002");
                    FavoritesPage.this.c.setCurrentItem(0);
                    return;
                }
                if (i == 1) {
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B001");
                    FavoritesPage.this.c.setCurrentItem(1);
                }
            }

            public final void b(int i) {
                if (i == 0) {
                    FavoritesPage.this.c.setCurrentItem(0);
                    return;
                }
                if (i == 1) {
                    FavoritesPage.this.c.setCurrentItem(1);
                }
            }
        });
        this.o = contentView.findViewById(R.id.layout_delete);
        this.p = (TextView) contentView.findViewById(R.id.del_btn);
        this.o.setOnClickListener(this);
        this.c = (NonSwipeableViewPager) contentView.findViewById(R.id.favorites_fragment_pager);
        this.e = new FavoritesRouteFragment(this);
        this.c.setOnPageChangeListener(new a(this, 0));
        this.c.setAdapter(new SmartFragmentPagerAdapter(getFragmentManager()) {
            public final int getCount() {
                return 2;
            }

            public final Fragment a(int i) {
                if (i == 0) {
                    if (FavoritesPage.this.d == null) {
                        FavoritesPage.this.d = new FavoritesPointFragment(FavoritesPage.this);
                        FavoritesPage.this.d = FavoritesPage.this.d;
                        FavoritesPage.this.f = FavoritesPage.this.d;
                        FavoritesPage.this.f.loadDataAsync(FavoritesPage.this.q);
                        FavoritesPage.this.q = false;
                    }
                    return FavoritesPage.this.d;
                } else if (i != 1) {
                    return null;
                } else {
                    if (FavoritesPage.this.e == null) {
                        FavoritesPage.this.e = new FavoritesRouteFragment(FavoritesPage.this);
                    }
                    return FavoritesPage.this.e;
                }
            }
        });
        PageBundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey("key_tab_favorites") || arguments.getInt("key_tab_favorites") != 1) {
            i2 = 0;
        }
        this.a.setSelectTab(i2);
        bim.aa().z();
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    public void onClick(View view) {
        String str;
        String str2;
        if (view.getId() == R.id.layout_delete) {
            this.f.onParentDeleteClick();
            if (this.c.getCurrentItem() == 0) {
                str = LogConstant.PAGE_SAVE_MAIN_POINT_EDITMODE;
                str2 = "B003";
            } else {
                str = LogConstant.PAGE_SAVE_MAIN_ROUTE_EDITMODE;
                str2 = "B002";
            }
            LogManager.actionLogV2(str, str2);
        }
    }

    private void e() {
        this.m.setVisibility(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("SaveManagerSyncRedPoint", true) ? 0 : 8);
    }

    public final void a() {
        bim.aa().a(this.v);
    }

    public final void c() {
        if (this.h != null) {
            this.h.dismiss();
            this.h = null;
        }
    }

    public final void d() {
        if (this.b != null) {
            this.b.hide();
        }
    }

    public final void a(boolean z) {
        this.g = z;
        int i2 = 0;
        this.a.setVisibility(z ? 8 : 0);
        this.l.setVisibility(z ? 0 : 8);
        View view = this.o;
        if (!z) {
            i2 = 8;
        }
        view.setVisibility(i2);
        if (z) {
            this.a.setActionImgVisibility(8);
            this.m.setVisibility(8);
            d();
        }
    }

    public final void b(boolean z) {
        this.o.setEnabled(z);
        this.p.setAlpha(z ? 1.0f : 0.5f);
    }

    public final void a(int i2, Fragment fragment) {
        if (fragment == this.f && !this.g) {
            this.a.setActionImgVisibility(i2);
            if (i2 == 8) {
                this.m.setVisibility(8);
                return;
            }
            e();
        }
    }

    public final void a(String str) {
        this.l.setTitle(str);
    }

    public final void b() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (!iAccountService.a()) {
                iAccountService.a(getPageContext(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        if (z) {
                            bim.aa().e(true);
                            bim.aa().z();
                        }
                    }
                });
                return;
            }
            bim.aa().e(true);
            bim.aa().z();
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cph(this);
    }

    static /* synthetic */ void a(FavoritesPage favoritesPage, Runnable runnable) {
        Activity activity = favoritesPage.getActivity();
        if (activity != null) {
            activity.runOnUiThread(runnable);
        }
    }

    static /* synthetic */ void h(FavoritesPage favoritesPage) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(favoritesPage.getString(R.string.manager)));
        arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(favoritesPage.getString(R.string.sync_immediate)));
        ActionSheet.b bVar = new ActionSheet.b(favoritesPage.getActivity());
        bVar.c = arrayList;
        bVar.d = favoritesPage.getString(R.string.cancel);
        bVar.f = new defpackage.erl.a() {
            public final void a(ActionSheet actionSheet, int i) {
                FavoritesPage.a(FavoritesPage.this, actionSheet);
            }
        };
        bVar.e = new defpackage.erl.a() {
            public final void a(ActionSheet actionSheet, int i) {
                FavoritesPage.a(FavoritesPage.this, actionSheet);
            }
        };
        bVar.g = new defpackage.erl.a() {
            public final void a(ActionSheet actionSheet, int i) {
                switch (i) {
                    case 0:
                        FavoritesPage.this.f.onParentManageClick();
                        break;
                    case 1:
                        FavoritesPage.this.j = true;
                        FavoritesPage.this.b();
                        break;
                }
                FavoritesPage.a(FavoritesPage.this, actionSheet);
            }
        };
        bVar.a = new FavoriteActionSheet(favoritesPage.getActivity(), 1);
        favoritesPage.k = (FavoriteActionSheet) bVar.a();
        favoritesPage.k.setCancelable(true);
        favoritesPage.k.getTitleLayout().setVisibility(8);
        favoritesPage.k = favoritesPage.k;
        if (favoritesPage.getPageContext() != null) {
            favoritesPage.getPageContext().showViewLayer(favoritesPage.k);
        }
    }

    static /* synthetic */ void a(FavoritesPage favoritesPage, ActionSheet actionSheet) {
        if (favoritesPage.getPageContext() != null) {
            favoritesPage.getPageContext().dismissViewLayer(actionSheet);
        }
    }
}
