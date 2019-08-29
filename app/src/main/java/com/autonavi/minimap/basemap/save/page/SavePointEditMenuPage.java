package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class SavePointEditMenuPage extends AbstractBasePage<cri> implements launchModeSingleTask, Transparent {
    private View a;
    /* access modifiers changed from: private */
    public a b;
    private TextView c;
    private Button d;
    private Button e;
    private Button f;
    private Button g;
    private Button h;
    private Button i;
    private Button j;
    private Button k;
    private Button l;
    /* access modifiers changed from: private */
    public bth m;
    private boolean n;

    public interface a {
        void editSavePoint(bth bth);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.a = new RelativeLayout(context);
        this.a.setBackgroundColor(context.getResources().getColor(R.color.dialog_bg));
        View inflate = LayoutInflater.from(context).inflate(R.layout.save_point_edit_menu_fragment, null);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(12);
        ((ViewGroup) this.a).addView(inflate, layoutParams);
        this.a.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SavePointEditMenuPage.this.finish();
            }
        });
        setContentView(this.a);
        View view = this.a;
        this.c = (TextView) view.findViewById(R.id.title);
        this.d = (Button) view.findViewById(R.id.set_extra);
        this.e = (Button) view.findViewById(R.id.set_top);
        this.f = (Button) view.findViewById(R.id.set_tag);
        this.g = (Button) view.findViewById(R.id.change_address);
        this.h = (Button) view.findViewById(R.id.delete);
        this.i = (Button) view.findViewById(R.id.add_shortcut);
        this.j = (Button) view.findViewById(R.id.manager);
        this.k = (Button) view.findViewById(R.id.sync);
        this.l = (Button) view.findViewById(R.id.btn_cancel);
        a(getArguments());
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle == null) {
            finish();
        } else if (pageBundle.getInt("save_source_from_key") == 3) {
            c();
        } else if (pageBundle == null || !pageBundle.containsKey("save_point_key") || !pageBundle.containsKey("save_fragment_key")) {
            finish();
        } else {
            this.m = (bth) pageBundle.getObject("save_point_key");
            this.b = (a) pageBundle.getObject("save_fragment_key");
            this.n = cru.d(this.m);
            if (!this.n) {
                a();
            } else {
                b();
            }
        }
    }

    private void a() {
        this.d.setVisibility(0);
        this.e.setVisibility(0);
        this.f.setVisibility(0);
        this.g.setVisibility(8);
        this.h.setVisibility(8);
        this.i.setVisibility(0);
        this.j.setVisibility(8);
        this.k.setVisibility(8);
        this.l.setVisibility(0);
        this.c.setText(cru.a(this.m));
        if (cru.b(this.m)) {
            this.e.setText(R.string.save_edit_menu_cancel_top);
        } else {
            this.e.setText(R.string.save_edit_menu_top);
        }
        this.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SavePointEditMenuPage.this.finish();
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("savepointkey", SavePointEditMenuPage.this.m);
                pageBundle.putObject("save_fragment_key", SavePointEditMenuPage.this.b);
                SavePointEditMenuPage.this.startPage(SavePointEditExtraPage.class, pageBundle);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("action", 1);
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        this.e.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                cru.c(SavePointEditMenuPage.this.m);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("savepointkey", SavePointEditMenuPage.this.m);
                SavePointEditMenuPage.this.setResult(ResultType.OK, pageBundle);
                SavePointEditMenuPage.this.finish();
                try {
                    int i = cru.b(SavePointEditMenuPage.this.m) ? 3 : 2;
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("action", i);
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        this.f.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("save_point_key", SavePointEditMenuPage.this.m);
                SavePointEditMenuPage.this.startPageForResult(SetTagPage.class, pageBundle, (int) FavoritesPointFragment.REQUEST_EDIT_POINT);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("action", 4);
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        this.i.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                cru.a(SavePointEditMenuPage.this.m, SavePointEditMenuPage.this.getContext());
                SavePointEditMenuPage.this.finish();
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("action", 5);
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B009", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        this.l.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SavePointEditMenuPage.this.finish();
            }
        });
    }

    private void b() {
        this.d.setVisibility(8);
        this.e.setVisibility(8);
        this.f.setVisibility(8);
        this.g.setVisibility(0);
        this.h.setVisibility(0);
        this.i.setVisibility(0);
        this.j.setVisibility(8);
        this.k.setVisibility(8);
        this.l.setVisibility(0);
        if (this.m != null) {
            this.c.setText(this.m.d);
            this.g.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    SavePointEditMenuPage.a(SavePointEditMenuPage.this, SavePointEditMenuPage.this.m.d);
                    String str = crt.c.equals(SavePointEditMenuPage.this.m.d) ? "B008" : crt.b.equals(SavePointEditMenuPage.this.m.d) ? "B007" : null;
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("action", 1);
                        if (!TextUtils.isEmpty(str)) {
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, str, jSONObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.h.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("change_type_key", FavoritesPointFragment.REQUEST_TAG_SELECT);
                    pageBundle.putObject("savepointkey", SavePointEditMenuPage.this.m);
                    SavePointEditMenuPage.this.setResult(ResultType.OK, pageBundle);
                    SavePointEditMenuPage.this.finish();
                    String str = crt.c.equals(SavePointEditMenuPage.this.m.d) ? "B008" : crt.b.equals(SavePointEditMenuPage.this.m.d) ? "B007" : null;
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("action", 2);
                        if (!TextUtils.isEmpty(str)) {
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, str, jSONObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.i.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    cru.a(SavePointEditMenuPage.this.m, SavePointEditMenuPage.this.getContext());
                    SavePointEditMenuPage.this.finish();
                    String str = crt.c.equals(SavePointEditMenuPage.this.m.d) ? "B008" : crt.b.equals(SavePointEditMenuPage.this.m.d) ? "B007" : null;
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("action", 3);
                        if (!TextUtils.isEmpty(str)) {
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, str, jSONObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.l.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    SavePointEditMenuPage.this.finish();
                }
            });
        }
    }

    private void c() {
        this.c.setVisibility(8);
        this.d.setVisibility(8);
        this.e.setVisibility(8);
        this.f.setVisibility(8);
        this.g.setVisibility(8);
        this.h.setVisibility(8);
        this.i.setVisibility(8);
        this.j.setVisibility(0);
        this.k.setVisibility(0);
        this.l.setVisibility(0);
        this.j.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("change_type_key", 246);
                SavePointEditMenuPage.this.setResult(ResultType.OK, pageBundle);
                SavePointEditMenuPage.this.finish();
            }
        });
        this.k.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("change_type_key", 247);
                SavePointEditMenuPage.this.setResult(ResultType.OK, pageBundle);
                SavePointEditMenuPage.this.finish();
            }
        });
        this.l.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SavePointEditMenuPage.this.finish();
            }
        });
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cri(this);
    }

    static /* synthetic */ void a(SavePointEditMenuPage savePointEditMenuPage, String str) {
        int i2;
        PageBundle pageBundle = new PageBundle();
        if (str.equals(crt.c)) {
            i2 = FavoritesPointFragment.REQUEST_COMPNAY;
            pageBundle.putString("address", crt.c);
        } else {
            if (str.equals(crt.b)) {
                pageBundle.putString("address", crt.b);
            }
            i2 = FavoritesPointFragment.REQUEST_HOME;
        }
        savePointEditMenuPage.startPageForResult(SaveSearchPage.class, pageBundle, i2);
    }
}
