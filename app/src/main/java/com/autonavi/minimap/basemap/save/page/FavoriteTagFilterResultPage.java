package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.ActionSheet;
import com.autonavi.widget.ui.ActionSheet.b;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteTagFilterResultPage extends AbstractBasePage<crd> implements com.autonavi.minimap.basemap.save.page.SavePointEditMenuPage.a {
    crb a;
    public String b = "";
    private ProgressDlg c;
    private ActionSheet d;
    /* access modifiers changed from: private */
    public bth e;
    private ImageView f;
    private TextView g;
    /* access modifiers changed from: private */
    public ListView h;
    /* access modifiers changed from: private */
    public ViewGroup i;
    private View j;
    /* access modifiers changed from: private */
    public FilterTagResultAdapter k;
    /* access modifiers changed from: private */
    public a l;
    /* access modifiers changed from: private */
    public int m = -1;
    /* access modifiers changed from: private */
    public String n = "";

    public class FilterTagResultAdapter extends BaseAdapter {
        /* access modifiers changed from: private */
        public AbstractBasePage mFragment;
        private List<String> mPoints = new ArrayList();

        class a {
            ViewGroup a;
            ImageView b;
            ImageView c;
            View d;
            TextView e;
            TextView f;
            View g;
            View h;

            private a() {
            }

            /* synthetic */ a(FilterTagResultAdapter filterTagResultAdapter, byte b2) {
                this();
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public FilterTagResultAdapter(AbstractBasePage abstractBasePage) {
            this.mFragment = abstractBasePage;
        }

        public int getCount() {
            return this.mPoints.size();
        }

        public Object getItem(int i) {
            return this.mPoints.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = LayoutInflater.from(AMapAppGlobal.getApplication()).inflate(R.layout.save_point_item, null);
                aVar = new a(this, 0);
                aVar.a = (ViewGroup) view.findViewById(R.id.layout_save_point_item);
                aVar.b = (ImageView) view.findViewById(R.id.is_top);
                aVar.c = (ImageView) view.findViewById(R.id.image_status);
                aVar.d = view.findViewById(R.id.more);
                aVar.e = (TextView) view.findViewById(R.id.text_point_name);
                aVar.f = (TextView) view.findViewById(R.id.text_point_detail);
                aVar.g = view.findViewById(R.id.view_divider_part);
                aVar.h = view.findViewById(R.id.view_divider_all);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            if (i >= this.mPoints.size() - 1) {
                aVar.h.setVisibility(0);
                aVar.g.setVisibility(8);
            } else {
                aVar.h.setVisibility(8);
                aVar.g.setVisibility(0);
            }
            bindViewDetail(new cpb(this.mPoints.get(i)), aVar);
            return view;
        }

        public void bindViewDetail(final cpb cpb, a aVar) {
            aVar.e.setText(cru.a(cpb));
            aVar.c.setImageResource(cpb.c());
            if (cpb.a()) {
                aVar.b.setVisibility(0);
            } else {
                aVar.b.setVisibility(8);
            }
            String b = cru.b(cpb);
            if (!TextUtils.isEmpty(b)) {
                aVar.f.setVisibility(0);
                aVar.f.setText(b);
            } else {
                aVar.f.setVisibility(8);
            }
            aVar.a.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    bth a2 = bsr.a(bim.aa().b((String) "101", cpb.b), cpb.b, FavoriteTagFilterResultPage.b());
                    if (a2 != null) {
                        PageBundle pageBundle = new PageBundle();
                        a2.a().getPoiExtra().put(FavoriteLayer.POI_KEY, cpb.b);
                        pageBundle.putObject("currentSelectedPoi", a2);
                        new ArrayList().add(cpb.a);
                        FavoriteTagFilterResultPage.this.startPage(SavePointToMapPage.class, pageBundle);
                    }
                }
            });
            aVar.d.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    FavoriteTagFilterResultPage.this.e = bsr.a(bim.aa().b((String) "101", cpb.b), cpb.b, FavoriteTagFilterResultPage.b());
                    FavoriteTagFilterResultPage.a(FavoriteTagFilterResultPage.this, FilterTagResultAdapter.this.mFragment);
                }
            });
        }

        public void setData(List<String> list) {
            this.mPoints.clear();
            if (list != null) {
                this.mPoints.addAll(list);
            }
            notifyDataSetChanged();
        }
    }

    static class a extends Handler {
        private WeakReference<FavoriteTagFilterResultPage> a;

        public a(FavoriteTagFilterResultPage favoriteTagFilterResultPage) {
            this.a = new WeakReference<>(favoriteTagFilterResultPage);
        }

        public final void handleMessage(Message message) {
            if (message.what == 0) {
                List list = (List) message.obj;
                FavoriteTagFilterResultPage favoriteTagFilterResultPage = (FavoriteTagFilterResultPage) this.a.get();
                if (favoriteTagFilterResultPage != null) {
                    if (list == null || list.size() <= 0) {
                        favoriteTagFilterResultPage.k.setData(null);
                        favoriteTagFilterResultPage.i.setVisibility(0);
                        favoriteTagFilterResultPage.h.setVisibility(8);
                    } else {
                        favoriteTagFilterResultPage.k.setData(list);
                        favoriteTagFilterResultPage.i.setVisibility(8);
                        favoriteTagFilterResultPage.h.setVisibility(0);
                    }
                }
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.save_tag_filter_result_fragment);
        View contentView = getContentView();
        if (contentView != null) {
            contentView.setPadding(contentView.getPaddingLeft(), euk.a(getContext()) + contentView.getPaddingTop(), contentView.getPaddingRight(), contentView.getPaddingBottom());
            this.f = (ImageView) contentView.findViewById(R.id.btn_back);
            this.g = (TextView) contentView.findViewById(R.id.title);
            this.h = (ListView) contentView.findViewById(R.id.container);
            this.k = new FilterTagResultAdapter(this);
            this.h.setAdapter(this.k);
            this.i = (ViewGroup) contentView.findViewById(R.id.layout_point);
            this.i.setVisibility(8);
            this.j = contentView.findViewById(R.id.layout_add_point_from_none);
            this.f.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    FavoriteTagFilterResultPage.this.finish();
                }
            });
            this.j.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    FavoriteTagFilterResultPage.this.startPageForResult(SaveSearchPage.class, (PageBundle) null, 240);
                }
            });
        }
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("tag_type") && arguments.containsKey("tag_string")) {
            this.m = arguments.getInt("tag_type", -1);
            this.b = arguments.getString("tag_string", "");
            this.n = arguments.getString("tag_code", "");
            this.a = (crb) arguments.get("tag_key");
            this.l = new a(this);
        }
        a(true);
    }

    public final void a(boolean z) {
        if (this.m != -1 && !this.b.equals("")) {
            String str = this.b;
            if (str.length() > 15) {
                StringBuilder sb = new StringBuilder();
                sb.append(str.substring(0, 15));
                sb.append("...");
                str = sb.toString();
            }
            this.g.setText(str);
            if (z) {
                c();
                this.c.setMessage(getString(R.string.loading));
            }
            a();
        }
    }

    public final void a() {
        ahl.b(new defpackage.ahl.a<Void>() {
            private List<String> b = new ArrayList();

            public final void onError(Throwable th) {
                FavoriteTagFilterResultPage.e(FavoriteTagFilterResultPage.this);
            }

            public final /* synthetic */ void onFinished(Object obj) {
                FavoriteTagFilterResultPage.e(FavoriteTagFilterResultPage.this);
                Message obtain = Message.obtain(FavoriteTagFilterResultPage.this.l);
                obtain.what = 0;
                obtain.obj = this.b;
                obtain.sendToTarget();
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                this.b.clear();
                if (FavoriteTagFilterResultPage.this.m == -1 || FavoriteTagFilterResultPage.this.b.equals("")) {
                    return null;
                }
                switch (FavoriteTagFilterResultPage.this.m) {
                    case 1:
                        List<String> c = bim.aa().c(FavoriteTagFilterResultPage.this.b);
                        if (c != null && c.size() != 0) {
                            this.b.clear();
                            this.b.addAll(c);
                            break;
                        } else {
                            Message obtain = Message.obtain(FavoriteTagFilterResultPage.this.l);
                            obtain.what = 0;
                            obtain.obj = this.b;
                            obtain.sendToTarget();
                            break;
                        }
                        break;
                    case 2:
                        List<String> d = bim.aa().d(FavoriteTagFilterResultPage.this.n);
                        this.b.clear();
                        this.b.addAll(d);
                        break;
                    case 3:
                        List<String> e = bim.aa().e(FavoriteTagFilterResultPage.this.b);
                        this.b.clear();
                        this.b.addAll(e);
                        break;
                }
                return null;
            }
        });
    }

    public final void a(final bth bth) {
        if (bth != null) {
            ahl.b(new defpackage.ahl.a<Object>() {
                public final void onError(Throwable th) {
                }

                public final Object doBackground() throws Exception {
                    cpf.b(cpm.b().a()).g(bth.a());
                    return null;
                }

                public final void onFinished(Object obj) {
                    FavoriteTagFilterResultPage.this.a(true);
                }
            });
        }
    }

    private void c() {
        if (this.c == null) {
            this.c = new ProgressDlg(getActivity());
            this.c.setCancelable(true);
        }
        if (this.c.isShowing()) {
            this.c.dismiss();
        }
        this.c.show();
    }

    public void editSavePoint(bth bth) {
        a(bth);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new crd(this);
    }

    static /* synthetic */ void e(FavoriteTagFilterResultPage favoriteTagFilterResultPage) {
        if (favoriteTagFilterResultPage.c != null) {
            favoriteTagFilterResultPage.c.dismiss();
            favoriteTagFilterResultPage.c = null;
        }
    }

    static /* synthetic */ String b() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e2 = iAccountService.e();
        if (e2 == null) {
            return "";
        }
        return e2.a;
    }

    static /* synthetic */ void a(FavoriteTagFilterResultPage favoriteTagFilterResultPage, final AbstractBasePage abstractBasePage) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(favoriteTagFilterResultPage.getString(R.string.save_edit_menu_set_extra)));
        if (cru.b(favoriteTagFilterResultPage.e)) {
            arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(favoriteTagFilterResultPage.getString(R.string.save_edit_menu_cancel_top)));
        } else {
            arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(favoriteTagFilterResultPage.getString(R.string.save_edit_menu_top)));
        }
        arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(favoriteTagFilterResultPage.getString(R.string.save_edit_menu_set_tag)));
        arrayList.add(new com.autonavi.widget.ui.ActionSheet.a(favoriteTagFilterResultPage.getString(R.string.save_edit_menu_add_shortcut)));
        String a2 = cru.a(favoriteTagFilterResultPage.e);
        b bVar = new b(favoriteTagFilterResultPage.getActivity());
        bVar.c = arrayList;
        bVar.b = a2;
        bVar.d = favoriteTagFilterResultPage.getString(R.string.cancel);
        bVar.f = new defpackage.erl.a() {
            public final void a(ActionSheet actionSheet, int i) {
                FavoriteTagFilterResultPage.a(FavoriteTagFilterResultPage.this, actionSheet);
            }
        };
        bVar.e = new defpackage.erl.a() {
            public final void a(ActionSheet actionSheet, int i) {
                FavoriteTagFilterResultPage.a(FavoriteTagFilterResultPage.this, actionSheet);
            }
        };
        bVar.g = new defpackage.erl.a() {
            public final void a(ActionSheet actionSheet, int i) {
                switch (i) {
                    case 0:
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putObject("savepointkey", FavoriteTagFilterResultPage.this.e);
                        pageBundle.putObject("save_fragment_key", (com.autonavi.minimap.basemap.save.page.SavePointEditMenuPage.a) abstractBasePage);
                        FavoriteTagFilterResultPage.this.startPage(SavePointEditExtraPage.class, pageBundle);
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("action", 1);
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            break;
                        }
                    case 1:
                        cru.c(FavoriteTagFilterResultPage.this.e);
                        FavoriteTagFilterResultPage.this.a(FavoriteTagFilterResultPage.this.e);
                        try {
                            int i2 = cru.b(FavoriteTagFilterResultPage.this.e) ? 3 : 2;
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("action", i2);
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject2);
                            break;
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                            break;
                        }
                    case 2:
                        PageBundle pageBundle2 = new PageBundle();
                        pageBundle2.putObject("save_point_key", FavoriteTagFilterResultPage.this.e);
                        FavoriteTagFilterResultPage.this.startPageForResult(SetTagPage.class, pageBundle2, (int) FavoritesPointFragment.REQUEST_EDIT_POINT);
                        try {
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("action", 4);
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject3);
                            break;
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                            break;
                        }
                    case 3:
                        cru.a(FavoriteTagFilterResultPage.this.e, actionSheet.getContext());
                        try {
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put("action", 5);
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject4);
                            break;
                        } catch (JSONException e4) {
                            e4.printStackTrace();
                            break;
                        }
                }
                FavoriteTagFilterResultPage.a(FavoriteTagFilterResultPage.this, actionSheet);
            }
        };
        favoriteTagFilterResultPage.d = bVar.a();
        favoriteTagFilterResultPage.d.setCancelable(true);
        if (favoriteTagFilterResultPage.getPageContext() != null) {
            favoriteTagFilterResultPage.getPageContext().showViewLayer(favoriteTagFilterResultPage.d);
        }
    }

    static /* synthetic */ void a(FavoriteTagFilterResultPage favoriteTagFilterResultPage, ActionSheet actionSheet) {
        if (favoriteTagFilterResultPage.getPageContext() != null) {
            favoriteTagFilterResultPage.getPageContext().dismissViewLayer(actionSheet);
        }
    }
}
