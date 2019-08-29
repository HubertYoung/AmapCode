package com.autonavi.map.search.album.page;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.album.adapter.AlbumAdapter;
import com.autonavi.map.search.album.adapter.AlbumAdapter.c;
import com.autonavi.map.search.album.adapter.AlbumAdapter.d;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.TitleBar;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.album.action.AlbumMainPage")
public class AlbumMainPage extends AbstractBasePage<bvg> implements OnClickListener {
    private anq A = new anq() {
        public final void loginOrBindCancel() {
        }

        public final void onComplete(boolean z) {
            if (z) {
                AlbumMainPage.this.w.putBooleanValue("scene_user_resume_need_update", true);
                ((bvg) AlbumMainPage.this.mPresenter).a(AlbumMainPage.this.e.getSelectedData());
            }
        }
    };
    public TitleBar a;
    public View b;
    public ProgressDlg c;
    public ExpandableListView d;
    public AlbumAdapter e;
    public Map<String, List<ImageInfo>> f;
    public boolean g = true;
    public int h = 4;
    public boolean i = false;
    public List<ImageInfo> j = new ArrayList();
    private b k;
    private TextView l;
    private TextView m;
    private TextView n;
    private TextView o;
    private TextView p;
    private TextView q;
    private View r;
    /* access modifiers changed from: private */
    public List<ImageInfo> s;
    /* access modifiers changed from: private */
    public int t = 500;
    /* access modifiers changed from: private */
    public int u = -1;
    private boolean v = false;
    /* access modifiers changed from: private */
    public MapSharePreference w = new MapSharePreference(SharePreferenceName.SharedPreferences);
    /* access modifiers changed from: private */
    public cah x = null;
    private OnClickListener y = new OnClickListener() {
        public final void onClick(View view) {
            AlbumMainPage.this.a();
            AlbumMainPage.this.finish();
        }
    };
    private OnClickListener z = new OnClickListener() {
        public final void onClick(View view) {
            if (AlbumMainPage.this.f != null) {
                List<ImageInfo> selectedData = AlbumMainPage.this.e.getSelectedData();
                AlbumMainPage albumMainPage = AlbumMainPage.this;
                Map h = AlbumMainPage.this.f;
                int b = AlbumMainPage.this.t;
                int g = AlbumMainPage.this.u;
                cah i = AlbumMainPage.this.x;
                if (albumMainPage != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("SELECT_DATA_LIST", selectedData);
                    pageBundle.putInt("SELECT_MAX_NUM", b);
                    pageBundle.putObject("BUNDLE_KEY_FODER_LIST", h);
                    pageBundle.putObject("PHOTO_REQUEST_CODE", Integer.valueOf(g));
                    pageBundle.putObject("album_bundle_builder", i);
                    albumMainPage.startPageForResult((String) "amap.album.action.AlbumFolderPage", pageBundle, 12289);
                }
                if (AlbumMainPage.this.u == 20482) {
                    LogManager.actionLogV2("P00178", "B001");
                }
            }
        }
    };

    public static class a {
        public boolean a = false;
        public ImageInfo b;

        public a(boolean z) {
            this.a = z;
        }

        public a(ImageInfo imageInfo) {
            this.b = imageInfo;
        }
    }

    public static class b {
        public String a;
        public String b;
        public boolean c;
        public boolean d = true;
        public boolean e;
        public int f = 4;
        public int g;
    }

    public void onEventMainThread(a aVar) {
        this.i = true;
        if (!this.j.contains(aVar.b)) {
            this.j.add(aVar.b);
        }
    }

    public final void a() {
        EventBus.getDefault().post(new a(this.v));
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.real_scene_album_fragment);
        View contentView = getContentView();
        this.a = (TitleBar) contentView.findViewById(R.id.title_bar);
        this.a.setActionTextVisibility(4);
        this.a.setOnActionClickListener(this.z);
        this.a.setOnBackClickListener(this.y);
        this.l = (TextView) contentView.findViewById(R.id.album_num);
        this.l.setOnClickListener(this);
        this.m = (TextView) contentView.findViewById(R.id.album_publish);
        this.m.setOnClickListener(this);
        this.n = (TextView) contentView.findViewById(R.id.album_browser);
        this.n.setOnClickListener(this);
        this.b = contentView.findViewById(R.id.empty_data_area);
        this.o = (TextView) contentView.findViewById(R.id.real_scene_album_filter_fail_text1);
        this.p = (TextView) contentView.findViewById(R.id.real_scene_album_filter_fail_tip1);
        this.q = (TextView) contentView.findViewById(R.id.real_scene_album_filter_fail_tip2);
        this.r = contentView.findViewById(R.id.album_take_pic);
        this.r.setOnClickListener(this);
        this.d = (ExpandableListView) contentView.findViewById(R.id.album_selector);
        this.e = new AlbumAdapter(getContext(), this.d);
        this.d.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }
        });
        this.e.setSelectDataChangeListener(new d() {
            public final void a(int i) {
                AlbumMainPage.this.a(i);
            }
        });
        this.e.setOnImageClickListener(new c() {
            public final void a(ImageInfo imageInfo) {
                List<ImageInfo> list;
                List<ImageInfo> selectedData = AlbumMainPage.this.e.getSelectedData();
                if (selectedData != null) {
                    caj caj = new caj();
                    caj.a = AlbumMainPage.this.t;
                    if (AlbumMainPage.this.s == null) {
                        list = ((bvg) AlbumMainPage.this.mPresenter).b.a();
                    } else {
                        list = AlbumMainPage.this.s;
                    }
                    caj.c = list;
                    caj.b = selectedData;
                    caj.d = imageInfo;
                    caj.f = AlbumMainPage.this.e.getDateData();
                    caj.e = AlbumMainPage.this.e.getImageData();
                    caj.i = ((bvg) AlbumMainPage.this.mPresenter).a;
                    caj.h = 1;
                    caj.g = 3;
                    caj.l = ((bvg) AlbumMainPage.this.mPresenter).d;
                    AlbumMainPage.this.a(caj);
                    if (AlbumMainPage.this.u == 20485 || AlbumMainPage.this.u == 20481) {
                        caj.j = 2;
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("album_preview_builder", caj);
                    AlbumMainPage.this.startPageForResult((String) "amap.album.action.AlbumPreviewPage", pageBundle, 12290);
                }
            }
        });
        this.d.setAdapter(this.e);
        ((bvg) this.mPresenter).b = new bvk(getContext());
        PageBundle arguments = getArguments();
        if (arguments == null) {
            finish();
        } else {
            this.u = getRequestCode();
            b bVar = (b) arguments.get("ALBUM_FRAGMENT_STYLE");
            boolean z2 = false;
            if (bVar != null) {
                if (this.a != null && !TextUtils.isEmpty(bVar.a)) {
                    this.a.setTitle(bVar.a);
                }
                if (this.a != null && bVar.c) {
                    this.a.setActionTextVisibility(0);
                }
                if (this.m != null && !TextUtils.isEmpty(bVar.b)) {
                    this.m.setText(bVar.b);
                }
                if (this.e != null) {
                    this.e.setShowSelectAllCheckbox(bVar.d);
                }
            }
            this.k = bVar;
            if (this.k != null) {
                this.g = this.k.e;
                this.h = this.k.f;
                z2 = this.k.c;
                this.t = this.k.g;
                this.e.setmMaxNum(this.t);
            }
            Object object = arguments.getObject("SELECT_DATA_LIST");
            List<ImageInfo> list = (object == null || !(object instanceof List)) ? null : (List) object;
            Object object2 = arguments.getObject("album_bundle_builder");
            if (object2 != null) {
                this.x = (cah) object2;
            }
            Object object3 = arguments.getObject("ALBUM_IMAGE_LIST");
            if (object3 != null) {
                this.s = (List) object3;
                if (!this.s.isEmpty()) {
                    bvg bvg = (bvg) this.mPresenter;
                    List<ImageInfo> list2 = this.s;
                    cah cah = this.x;
                    ((AlbumMainPage) bvg.mPage).e();
                    if (cah != null) {
                        bvg.e = cah;
                        bvg.d = bvg.e.d;
                    }
                    ArrayList arrayList = new ArrayList();
                    new ArrayList();
                    HashMap hashMap = new HashMap();
                    for (ImageInfo next : list2) {
                        String str = next.h;
                        if (!TextUtils.isEmpty(str)) {
                            Date a2 = lf.a(str);
                            if (a2 != null) {
                                cak cak = new cak();
                                String a3 = bvj.a(a2.getTime());
                                cak.a = a3;
                                if (!arrayList.contains(cak)) {
                                    arrayList.add(cak);
                                }
                                bvk.a((Map<String, List<ImageInfo>>) hashMap, next, a3);
                            }
                        }
                    }
                    bvg.f.a(arrayList, hashMap, list, true);
                }
            } else {
                bvg bvg2 = (bvg) this.mPresenter;
                boolean z3 = this.g;
                cah cah2 = this.x;
                ((AlbumMainPage) bvg2.mPage).e();
                if (z2) {
                    bvg2.b.h = true;
                    bvg2.b.d = bvg2;
                }
                bvg2.b.g = z3;
                bvg2.b.i = list;
                if (cah2 != null) {
                    bvg2.e = cah2;
                    bvg2.b.f = bvg2.e.b;
                    bvg2.b.e = bvg2.e.a;
                    bvg2.c = bvg2.e.c;
                    bvg2.d = bvg2.e.d;
                }
                bvg2.b.a(bvg2.f);
            }
            ((bvg) this.mPresenter).a = (cao) arguments.getObject("activity_tip");
            if (!this.g) {
                this.o.setText(R.string.real_scene_album_filter_fail_text1_no_location);
                this.p.setText(R.string.real_scene_album_filter_fail_tip1_no_location);
                this.q.setText(R.string.real_scene_album_filter_fail_tip2_no_location);
            }
        }
        if (this.u == 20481) {
            LogManager.actionLogV2(LogConstant.ALBUM_PAGE_FOR_REAL_SCENE, "B001", null);
        }
        EventBus.getDefault().register(this);
    }

    public final void b() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("BUNDLE_KEY_SELECTED_IMAGE_LIST", this.e.getSelectedData());
        pageBundle.putInt("COMMENT_REQUEST_CODE", 20482);
        startPageForResult((String) "amap.search.action.comment", pageBundle, 1);
    }

    public final void c() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("BUNDLE_KEY_SELECTED_IMAGE_LIST", this.e.getSelectedData());
        pageBundle.putInt("COMMENT_REQUEST_CODE", 20484);
        startPageForResult((String) "amap.search.action.photo", pageBundle, 2);
    }

    public void onClick(View view) {
        int i2;
        if (view == this.m || view == this.l) {
            switch (this.u) {
                case 20482:
                    b();
                    return;
                case 20483:
                    b();
                    return;
                case 20484:
                case 20485:
                    c();
                    return;
                case 20486:
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("photo_select_list", this.e.getSelectedData());
                    setResult(ResultType.OK, pageBundle);
                    finish();
                    return;
                default:
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("status", aaw.b(getContext()));
                        jSONObject.put("isLogin", d());
                        if (this.e.getDateData() == null) {
                            i2 = 0;
                        } else {
                            i2 = this.e.getDateData().size();
                        }
                        jSONObject.put("itemAccount", i2);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    LogManager.actionLogV2(LogConstant.ALBUM_PAGE_FOR_REAL_SCENE, "B003", jSONObject);
                    if (d()) {
                        ((bvg) this.mPresenter).a(this.e.getSelectedData());
                        return;
                    }
                    IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                    if (iAccountService != null) {
                        iAccountService.a(getPageContext(), this.A);
                        return;
                    }
                    return;
            }
        } else if (view == this.n) {
            List<ImageInfo> selectedData = this.e.getSelectedData();
            if (selectedData != null && selectedData.size() > 0) {
                caj caj = new caj();
                caj.a = this.t;
                caj.c = this.s == null ? ((bvg) this.mPresenter).b.a() : this.s;
                caj.b = selectedData;
                caj.f = this.e.getDateData();
                caj.e = this.e.getImageData();
                caj.i = ((bvg) this.mPresenter).a;
                caj.h = 1;
                caj.g = 3;
                caj.l = ((bvg) this.mPresenter).d;
                a(caj);
                if (this.u == 20485 || this.u == 20481) {
                    caj.j = 2;
                }
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putObject("album_preview_builder", caj);
                startPageForResult((String) "amap.album.action.AlbumPreviewPage", pageBundle2, 12290);
            }
        } else {
            if (view == this.r) {
                if (20486 == this.u) {
                    bvj.a(this, this.g, 7);
                    return;
                }
                bvj.a(this, this.g, this.h);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(caj caj) {
        if (this.u == 20484 || this.u == 20485) {
            caj.g = 3;
            caj.h = 1;
        } else if (this.h == 5 || this.u == 20483) {
            caj.g = 3;
            caj.h = 1;
        } else if (this.u == 20486) {
            caj.g = 3;
            caj.h = 0;
        } else {
            caj.k = ((bvg) this.mPresenter).c;
            caj.g = 3;
            caj.h = 2;
        }
    }

    public final void a(int i2) {
        this.v = true;
        if (i2 > 0) {
            this.l.setText(String.valueOf(i2));
            this.l.setVisibility(0);
            this.m.setEnabled(true);
            this.l.setEnabled(true);
            this.n.setEnabled(true);
            return;
        }
        this.l.setVisibility(8);
        this.m.setEnabled(false);
        this.l.setEnabled(false);
        this.n.setEnabled(false);
    }

    private void e() {
        Activity activity = getActivity();
        if (activity != null) {
            this.c = new ProgressDlg(activity, this.g ? "正在筛选有地理位置的照片..." : "加载中...", "");
            this.c.setCancelable(false);
            this.c.show();
        }
    }

    private static boolean d() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bvg(this);
    }
}
