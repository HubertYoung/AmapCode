package com.autonavi.map.search.page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.utils.view.OneClickListener;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.comment.recommend.CommendRecommendUtil;
import com.autonavi.map.search.comment.recommend.CommendRecommendUtil.CommentRecommendParam;
import com.autonavi.map.search.comment.recommend.CommendRecommendUtil.b;
import com.autonavi.map.search.comment.recommend.CommendRecommendUtil.c;
import com.autonavi.map.search.comment.widget.PublishCommentDialog;
import com.autonavi.map.search.comment.widget.RatingBar;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.map.search.view.recyclerview.FullyGridLayoutManager;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.search.action.comment")
public class EditCommentPage extends AbstractSearchBasePage<caw> implements OnClickListener, defpackage.bvx.a, LocationNone, launchModeSingleTask, com.autonavi.map.search.comment.recommend.CommendRecommendUtil.a<b> {
    private TextView[] A;
    /* access modifiers changed from: private */
    public boolean B = false;
    private wa C;
    private OnTouchListener D = new OnTouchListener() {
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (EditCommentPage.this.a.canScrollVertically(-1) || EditCommentPage.this.a.canScrollVertically(1)) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
            }
            if ((motionEvent.getAction() & 255) == 1) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        }
    };
    public EditText a;
    public RecyclerView b;
    public int c;
    public String d;
    public String e;
    public String f;
    public String g;
    public boolean h;
    public AosRequest i;
    public OnPreDrawListener j = new OnPreDrawListener() {
        public final boolean onPreDraw() {
            if (EditCommentPage.this.b.getChildAt(0) != null) {
                int height = EditCommentPage.this.b.getChildAt(0).getHeight();
                LayoutParams layoutParams = EditCommentPage.this.b.getLayoutParams();
                layoutParams.height = height + (EditCommentPage.this.b.getResources().getDimensionPixelSize(R.dimen.comment_photo_upload_grid_divider) * 2);
                EditCommentPage.this.b.setLayoutParams(layoutParams);
            }
            return true;
        }
    };
    public TextWatcher k = new TextWatcher() {
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void afterTextChanged(Editable editable) {
            EditCommentPage.this.n.setText(Html.fromHtml(((caw) EditCommentPage.this.mPresenter).a(editable.length(), EditCommentPage.this.p.a())));
            EditCommentPage.this.n.invalidate();
        }
    };
    private RatingBar l;
    private TextView m;
    /* access modifiers changed from: private */
    public TextView n;
    private FullyGridLayoutManager o;
    /* access modifiers changed from: private */
    public bvx p;
    private TextView q;
    private View r;
    private LinearLayout s;
    private View t;
    private TextView u;
    private LinearLayout v;
    private AlertView w;
    private View x;
    private TitleBar y;
    private TextView[] z;

    static class a implements com.autonavi.map.search.comment.widget.RatingBar.a {
        private WeakReference<EditCommentPage> a;
        private int b = Integer.MIN_VALUE;

        public a(@NonNull EditCommentPage editCommentPage) {
            this.a = new WeakReference<>(editCommentPage);
        }

        public a(@NonNull EditCommentPage editCommentPage, int i) {
            this.a = new WeakReference<>(editCommentPage);
            this.b = i;
        }

        private EditCommentPage a() {
            if (this.a == null || this.a.get() == null) {
                return null;
            }
            return (EditCommentPage) this.a.get();
        }

        public final void a(int i) {
            if (a() != null) {
                EditCommentPage a2 = a();
                if (this.b == Integer.MIN_VALUE) {
                    EditCommentPage.a(a2, i);
                } else {
                    EditCommentPage.a(a2, i, this.b);
                }
            }
        }
    }

    public final /* synthetic */ void a(Object obj) {
        b bVar = (b) obj;
        if (!this.h && isAlive() && bVar != null) {
            ((caw) this.mPresenter).a = bVar.g;
            if (!(bVar == null || bVar.h == null || bVar.h.isEmpty())) {
                if (!TextUtils.isEmpty(bVar.f)) {
                    this.s.setVisibility(0);
                    this.u.setText(bVar.f);
                    this.t.setVisibility(0);
                }
                if (bVar.h != null && !bVar.h.isEmpty()) {
                    int size = bVar.h.size();
                    if (size > 3) {
                        size = 3;
                    }
                    LayoutInflater from = LayoutInflater.from(getContext());
                    this.v.removeAllViews();
                    for (int i2 = 0; i2 < size; i2++) {
                        final c cVar = bVar.h.get(i2);
                        if (cVar != null) {
                            View inflate = from.inflate(R.layout.comment_main_layout_recommend_poi, this.v, false);
                            Button button = (Button) inflate.findViewById(R.id.recommend_action);
                            ((TextView) inflate.findViewById(R.id.recommend_title)).setText(cVar.a);
                            button.setText(cVar.h);
                            button.setOnClickListener(new OneClickListener() {
                                public final void doClick(View view) {
                                    EditCommentPage.this.recordActionLog((String) "P00176", (String) "B013", new SimpleEntry("type", Integer.valueOf(EditCommentPage.b(cVar) ? 1 : 0)), new SimpleEntry("from", Integer.valueOf(EditCommentPage.this.B ^ true ? 1 : 0)));
                                    EditCommentPage.a(EditCommentPage.this, cVar);
                                }
                            });
                            this.v.addView(inflate);
                        }
                    }
                }
            }
            if (bVar.h != null && bVar.h.size() > 0) {
                recordActionLog((String) "P00176", (String) "B012", new SimpleEntry("type", Integer.valueOf(a(bVar) ? 1 : 0)), new SimpleEntry("from", Integer.valueOf(this.B ^ true ? 1 : 0)), new SimpleEntry("text", Integer.valueOf(bVar.h.size())));
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.comment_main_layout);
        getContentView();
        this.y = (TitleBar) findView(R.id.title);
        this.y.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                KeyboardUtil.hideInputMethod(EditCommentPage.this.getActivity());
                EditCommentPage.this.b();
            }
        });
        this.y.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                KeyboardUtil.hideInputMethod(EditCommentPage.this.getActivity());
                caw caw = (caw) EditCommentPage.this.mPresenter;
                String a2 = EditCommentPage.this.d;
                int b = EditCommentPage.this.c;
                String obj = EditCommentPage.this.a.getText().toString();
                String d = EditCommentPage.this.e;
                if (b <= 0) {
                    EditCommentPage.b((String) "您还未打分评星");
                } else if (obj.length() == 0) {
                    EditCommentPage.b((String) "您还未写评论");
                } else if (obj.length() < 10) {
                    EditCommentPage.b((String) "您的评论不足10个字");
                } else if (obj.length() > 400) {
                    EditCommentPage.b((String) "您的评论超出400字，请删减");
                } else {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("status", aaw.a(((EditCommentPage) caw.mPage).getContext()));
                        jSONObject.put("isLogin", caw.a());
                        jSONObject.put(TrafficUtil.KEYWORD, caw.b.size() == 0 ? 0 : 1);
                        jSONObject.put("itemName", b);
                        jSONObject.put("type", d);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ((EditCommentPage) caw.mPage).recordActionLog((String) "P00176", (String) "B006", jSONObject);
                    if (!aaw.c(((EditCommentPage) caw.mPage).getContext())) {
                        EditCommentPage.b((String) "请检查网络后重试");
                        return;
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("RATING", b);
                    pageBundle.putString("COMMENT", obj);
                    pageBundle.putString("POI_ID", a2);
                    pageBundle.putObject("PHOTOUPLOAD", caw.b);
                    ((AbstractBasePage) caw.mPage).startPageForResult(PublishCommentDialog.class, pageBundle, (int) MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
                }
            }
        });
        this.l = (RatingBar) findView(R.id.rating_bar);
        this.m = (TextView) findView(R.id.txt_rating_desc);
        this.q = (TextView) findView(R.id.login_tip);
        this.r = findView(R.id.login_tip_diveder);
        this.s = (LinearLayout) findView(R.id.recommend_header_bg);
        this.u = (TextView) findView(R.id.recommend_header_title);
        this.t = findView(R.id.recommend_container_top);
        this.v = (LinearLayout) findView(R.id.recommend_container);
        this.q.setOnClickListener(this);
        c();
        this.l.setOnRatingChangedListener(new a(this));
        this.a = (EditText) findView(R.id.edt_comments);
        this.n = (TextView) findView(R.id.txt_comment_tip);
        this.n.setTextColor(getColor(R.color.f_c_2));
        this.b = (RecyclerView) findView(R.id.grid_album);
        this.o = new FullyGridLayoutManager(getContext(), 3);
        this.o.setOrientation(1);
        this.p = new bvx(this.o);
        this.p.c = this;
        bvx bvx = this.p;
        bvx.a = this.b;
        bvx.b = bvx.a.getResources().getDimensionPixelSize(R.dimen.comment_photo_upload_grid_divider);
        this.b.setLayoutManager(this.o);
        this.b.setAdapter(this.p);
        this.b.addItemDecoration(new cci(getResources().getDimensionPixelSize(R.dimen.comment_photo_upload_grid_divider)));
        this.a.addTextChangedListener(this.k);
        this.a.setOnTouchListener(this.D);
        if (this.q.getVisibility() == 0) {
            recordActionLog("P00176", "B010");
        }
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.d = arguments.getString("EDIT_COMMENT_POI_ID");
            this.g = arguments.getString("EDIT_COMMENT_POI");
            this.e = arguments.getString("EDIT_COMMENT_POI_BUSINESS");
            this.y.setTitle(this.g);
            this.l.setRating(arguments.getInt("EDIT_COMMENT_RATING"));
            this.a.setHint(caw.a(this.e));
            this.a.setHintTextColor(getColor(R.color.f_c_4));
            this.a.setTextColor(getColor(R.color.f_c_2));
            Object obj = arguments.get("EDIT_COMMENT_CALLBACK");
            if (obj instanceof wa) {
                this.C = (wa) obj;
            }
            this.f = arguments.getString("COMMENT_FROM");
            recordActionLog((String) "P00176", (String) "B001", new SimpleEntry("from", this.f), new SimpleEntry("type", this.e), new SimpleEntry("status", aaw.a(getContext())));
        }
        if (caw.b(this.e)) {
            ViewStub viewStub = (ViewStub) findView(R.id.scenic_comment_stub);
            if (viewStub != null) {
                this.x = viewStub.inflate();
                a(this.x.findViewById(R.id.scenic_commnet_item1), this.x.findViewById(R.id.scenic_commnet_item2), this.x.findViewById(R.id.scenic_commnet_item3), this.x.findViewById(R.id.scenic_commnet_item4), this.x.findViewById(R.id.scenic_commnet_item5));
            }
        }
        e();
        requestScreenOrientation(1);
    }

    private void a(View... viewArr) {
        this.z = new TextView[5];
        this.A = new TextView[5];
        for (int i2 = 0; i2 < 5; i2++) {
            this.z[i2] = (TextView) viewArr[i2].findViewById(R.id.comment_tag);
            this.A[i2] = (TextView) viewArr[i2].findViewById(R.id.rate_decs);
            RatingBar ratingBar = (RatingBar) viewArr[i2].findViewById(R.id.rating_bar);
            if (this.z[i2] != null) {
                this.z[i2].setText(caw.c(i2));
            }
            if (ratingBar != null) {
                ratingBar.setOnRatingChangedListener(new a(this, i2));
            }
        }
    }

    public void onClick(View view) {
        KeyboardUtil.hideInputMethod(getActivity());
        if (view.getId() == R.id.login_tip) {
            IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.a(getPageContext(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        if (z) {
                            EditCommentPage.this.c();
                            EditCommentPage.this.d();
                        }
                    }
                });
                recordActionLog("P00176", "B011");
            }
        }
    }

    public final void a(int i2, int i3) {
        KeyboardUtil.hideInputMethod(getActivity());
        if (i3 == 1) {
            caw caw = (caw) this.mPresenter;
            List<ImageInfo> list = caw.b;
            ArrayList arrayList = new ArrayList();
            if (list != null) {
                for (int i4 = 0; i4 < list.size(); i4++) {
                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.g = list.get(i4).g;
                    imageInfo.b = list.get(i4).b;
                    imageInfo.f = list.get(i4).f;
                    imageInfo.m = list.get(i4).m;
                    imageInfo.e = list.get(i4).e;
                    imageInfo.a = list.get(i4).a;
                    imageInfo.h = list.get(i4).h;
                    imageInfo.i = list.get(i4).i;
                    imageInfo.j = list.get(i4).j;
                    imageInfo.d = list.get(i4).d;
                    imageInfo.c = list.get(i4).c;
                    imageInfo.k = list.get(i4).k;
                    imageInfo.l = list.get(i4).l;
                    imageInfo.p = list.get(i4).p;
                    arrayList.add(imageInfo);
                }
            }
            caj caj = new caj();
            caj.a = 500;
            caj.c = arrayList;
            caj.b = arrayList;
            caj.d = (ImageInfo) arrayList.get(i2);
            caj.h = 1;
            caj.g = 2;
            caj.l = "amap.search.action.comment";
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("album_preview_builder", caj);
            ((EditCommentPage) caw.mPage).startPageForResult((String) "amap.album.action.AlbumPreviewPage", pageBundle, 12291);
            recordActionLog("P00176", "B005");
            return;
        }
        if (i3 == 2) {
            ((caw) this.mPresenter).b();
            this.a.clearFocus();
            recordActionLog("P00176", "B003");
        }
    }

    public static boolean a(String str) {
        return !TextUtils.isEmpty(str);
    }

    public final void a(String str, int i2) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("EDIT_COMMENT_POI_BUSINESS", this.e);
        pageBundle.putInt("COMMENT_PUBLISH_STATUS", a(str) ? 1 : 0);
        pageBundle.putString("EDIT_COMMENT_CONTENT", this.a.getEditableText().toString());
        pageBundle.putInt("EDIT_COMMENT_RATING", this.c);
        pageBundle.putString("COMMENT_PUBLISH_ID", str);
        pageBundle.putString("POI_NAME", this.g);
        pageBundle.putString("POI_BUSINESS", this.e);
        pageBundle.putInt("EDIT_COMMENT_PICCOUNT", i2);
        pageBundle.putString("EDIT_COMMENT_POI_ID", this.d);
        pageBundle.putObject("EDIT_COMMENT_CALLBACK", this.C);
        pageBundle.putInt("COMMENT_TYPE", a() ? 2 : 1);
        finish();
        setResult(ResultType.OK, pageBundle);
    }

    public final boolean a() {
        boolean z2 = this.a.getEditableText().length() >= 100;
        if (this.p.a() < 3) {
            return false;
        }
        return z2;
    }

    public static void b(String str) {
        ToastHelper.showToast(str);
    }

    public final void a(List<ImageInfo> list) {
        if (list != null) {
            Collections.sort(list, new Comparator<ImageInfo>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return (int) (new File(((ImageInfo) obj2).b).lastModified() - new File(((ImageInfo) obj).b).lastModified());
                }
            });
            bvx bvx = this.p;
            bvx.d.clear();
            bvx.d.addAll(list);
            this.p.notifyDataSetChanged();
            d();
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        this.n.setText(Html.fromHtml(((caw) this.mPresenter).a(this.a.length(), this.p.a())));
    }

    public final void b() {
        if (this.c == 0 && this.a.length() == 0 && this.p.getItemCount() == 1) {
            a((String) "", 0);
        } else if (this.w == null || !isViewLayerShowing(this.w)) {
            com.autonavi.widget.ui.AlertView.a b2 = new com.autonavi.widget.ui.AlertView.a(getContext()).a((CharSequence) "真的放弃评论吗？").a((CharSequence) "继续评论", (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    EditCommentPage.this.dismissViewLayer(alertView);
                }
            }).b((CharSequence) "放弃", (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    EditCommentPage.this.a((String) "", 0);
                    EditCommentPage.this.recordActionLog("P00176", "B014", "type", !TextUtils.isEmpty(EditCommentPage.this.a.getText().toString()) ? "1" : "0");
                }
            });
            b2.c = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    EditCommentPage.this.dismissViewLayer(alertView);
                }
            };
            this.w = b2.a(true).a();
            ccf.a(this, this.w);
        } else {
            dismissViewLayer(this.w);
        }
    }

    private void e() {
        if (!TextUtils.isEmpty(this.d) && isAlive()) {
            this.i = aax.a(new CommentRecommendParam(this.d));
            new CommendRecommendUtil().a(this.i, this);
            this.h = false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(c cVar) {
        return cVar.b == 21;
    }

    private static boolean a(b bVar) {
        if (bVar == null || bVar.h == null || bVar.h.size() == 0) {
            return false;
        }
        for (c b2 : bVar.h) {
            if (b(b2)) {
                return true;
            }
        }
        return false;
    }

    public final void c() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            boolean a2 = iAccountService.a();
            int i2 = 0;
            this.q.setVisibility(a2 ? 8 : 0);
            View view = this.r;
            if (a2) {
                i2 = 8;
            }
            view.setVisibility(i2);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new caw(this);
    }

    static /* synthetic */ void a(EditCommentPage editCommentPage, c cVar) {
        if (cVar != null) {
            editCommentPage.d = cVar.e;
            editCommentPage.g = cVar.a;
            editCommentPage.e = cVar.f;
            editCommentPage.y.setTitle(editCommentPage.g);
            editCommentPage.l.setRating(0);
            editCommentPage.a.setHint(caw.a(editCommentPage.e));
            editCommentPage.a.setText("");
            editCommentPage.B = true;
            ((caw) editCommentPage.mPresenter).a((List<ImageInfo>) new ArrayList<ImageInfo>());
            editCommentPage.n.setText("");
            editCommentPage.e();
            editCommentPage.s.setVisibility(8);
            editCommentPage.v.removeAllViews();
            editCommentPage.t.setVisibility(8);
        }
    }

    static /* synthetic */ void a(EditCommentPage editCommentPage, int i2) {
        KeyboardUtil.hideInputMethod(editCommentPage.getActivity());
        if (i2 > 0 && i2 <= 5) {
            editCommentPage.c = i2;
            editCommentPage.m.setText(caw.a(i2));
            editCommentPage.m.setTextColor(editCommentPage.getResources().getColor(R.color.comment_highlight_red));
            editCommentPage.m.invalidate();
            editCommentPage.recordActionLog((String) "P00176", (String) "B002", new SimpleEntry("type", Integer.valueOf(i2)));
        }
    }

    static /* synthetic */ void a(EditCommentPage editCommentPage, int i2, int i3) {
        KeyboardUtil.hideInputMethod(editCommentPage.getActivity());
        if (i2 > 0 && i2 <= 5 && editCommentPage.A[i3] != null) {
            editCommentPage.A[i3].setText(caw.b(i2));
        }
    }
}
