package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.save.widget.TagListView;
import com.autonavi.minimap.basemap.save.widget.TagListView.a;
import com.autonavi.minimap.basemap.save.widget.TagView;
import com.autonavi.minimap.widget.ClearableEditText;
import com.autonavi.widget.ui.AlertView;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetTagPage extends AbstractBasePage<crq> implements OnClickListener, LocationNone {
    public ClearableEditText a;
    /* access modifiers changed from: private */
    public ViewGroup b;
    /* access modifiers changed from: private */
    public TagListView c;
    private ProgressDlg d;
    /* access modifiers changed from: private */
    public bth e;
    private AlertView f;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.save_add_tag_fragment);
        View contentView = getContentView();
        if (contentView != null) {
            contentView.setPadding(contentView.getPaddingLeft(), euk.a(getContext()) + contentView.getPaddingTop(), contentView.getPaddingRight(), contentView.getPaddingBottom());
            contentView.findViewById(R.id.btn_back).setOnClickListener(this);
            contentView.findViewById(R.id.text_save).setOnClickListener(this);
            this.a = (ClearableEditText) contentView.findViewById(R.id.edit_tag_name);
            this.b = (ViewGroup) contentView.findViewById(R.id.total_tag_place);
            this.b.setVisibility(8);
            this.c = (TagListView) contentView.findViewById(R.id.tag_list);
            this.c.setTagSelectListener(new a() {
                public final void a(TagView tagView) {
                    SetTagPage.this.a.setText(tagView.mOriginTagStr);
                    Editable text = SetTagPage.this.a.getText();
                    if (text != null) {
                        Selection.setSelection(text, text.length());
                    }
                }
            });
            PageBundle arguments = getArguments();
            if (arguments != null && arguments.containsKey("save_point_key")) {
                this.e = (bth) arguments.get("save_point_key");
                if (this.d == null) {
                    this.d = new ProgressDlg(getActivity());
                    this.d.setCancelable(true);
                }
                if (this.d.isShowing()) {
                    this.d.dismiss();
                }
                this.d.show();
                ahl.b(new a<Set<String>>() {
                    public final /* synthetic */ void onFinished(Object obj) {
                        Set<String> set = (Set) obj;
                        SetTagPage.b(SetTagPage.this);
                        if (set == null || set.size() <= 0) {
                            SetTagPage.this.b.setVisibility(8);
                        } else {
                            SetTagPage.this.b.setVisibility(0);
                            for (String addTag : set) {
                                SetTagPage.this.c.addTag(addTag);
                            }
                            if (!(SetTagPage.this.e == null || SetTagPage.this.e.a() == null)) {
                                String tag = ((FavoritePOI) SetTagPage.this.e.a().as(FavoritePOI.class)).getTag();
                                if (!TextUtils.isEmpty(tag)) {
                                    SetTagPage.this.a.setText(tag);
                                    SetTagPage.this.c.selectTag(tag);
                                }
                            }
                        }
                    }

                    public final void onError(Throwable th) {
                        SetTagPage.b(SetTagPage.this);
                    }

                    public final /* synthetic */ Object doBackground() throws Exception {
                        List<bth> c = cpf.c(cpf.b(cpm.b().a()).a);
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < c.size(); i++) {
                            bth bth = c.get(i);
                            FavoritePOI favoritePOI = (FavoritePOI) bth.a().as(FavoritePOI.class);
                            favoritePOI.setUserId(bth.b);
                            favoritePOI.setCreateTime(bth.f);
                            favoritePOI.setPoiJson(bth.c);
                            favoritePOI.setCommonName(bth.d);
                            arrayList.add(favoritePOI);
                        }
                        return cru.a((List<FavoritePOI>) arrayList);
                    }
                });
            }
        }
    }

    public final void a() {
        if (this.e == null || this.e.a() == null) {
            setResult(ResultType.OK, (PageBundle) null);
            finish();
        } else if (TextUtils.equals(((FavoritePOI) this.e.a().as(FavoritePOI.class)).getTag(), this.a.getText().toString()) || TextUtils.isEmpty(this.a.getText().toString())) {
            setResult(ResultType.OK, (PageBundle) null);
            finish();
        } else {
            KeyboardUtil.hideInputMethod(getActivity());
            if (this.f == null || !isViewLayerShowing(this.f)) {
                AlertView.a aVar = new AlertView.a(getContext());
                aVar.a(R.string.save_current_edit);
                aVar.b(R.string.cancel, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        SetTagPage.this.dismissViewLayer(alertView);
                        SetTagPage.this.setResult(ResultType.OK, (PageBundle) null);
                        SetTagPage.this.finish();
                    }
                });
                aVar.a(R.string.save, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        SetTagPage.this.dismissViewLayer(alertView);
                        SetTagPage.this.b();
                    }
                });
                aVar.c = new a() {
                    public final void onClick(AlertView alertView, int i) {
                        SetTagPage.this.dismissViewLayer(alertView);
                    }
                };
                aVar.b = new a() {
                    public final void onClick(AlertView alertView, int i) {
                    }
                };
                aVar.c = new a() {
                    public final void onClick(AlertView alertView, int i) {
                    }
                };
                aVar.a(false);
                this.f = aVar.a();
                showViewLayer(this.f);
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            a();
            return;
        }
        if (view.getId() == R.id.text_save) {
            b();
        }
    }

    public final void b() {
        String obj = this.a.getText().toString();
        if (cot.a.equals(obj)) {
            ToastHelper.showToast(getString(R.string.do_not_use_all_tag));
        } else if (crt.c.equals(obj)) {
            ToastHelper.showToast(getString(R.string.do_not_use_company_tag));
        } else if (crt.b.equals(obj)) {
            ToastHelper.showToast(getString(R.string.do_not_use_home_tag));
        } else {
            if (!(this.e == null || this.e.a() == null)) {
                ((FavoritePOI) this.e.a().as(FavoritePOI.class)).setTag(obj);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("savepointkey", this.e);
                setResult(ResultType.OK, pageBundle);
            }
            finish();
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new crq(this);
    }

    static /* synthetic */ void b(SetTagPage setTagPage) {
        if (setTagPage.d != null) {
            setTagPage.d.dismiss();
            setTagPage.d = null;
        }
    }
}
