package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.save.widget.TagListView;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteTagSelectPage extends AbstractBasePage<cre> implements LocationNone {
    private ImageButton a;
    private TagListView b;
    private TagListView c;
    private TagListView d;
    private ViewGroup e;
    private ViewGroup f;
    private ViewGroup g;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.save_tag_fragment);
        View contentView = getContentView();
        if (contentView != null) {
            contentView.setPadding(contentView.getPaddingLeft(), euk.a(getContext()) + contentView.getPaddingTop(), contentView.getPaddingRight(), contentView.getPaddingBottom());
            this.b = (TagListView) contentView.findViewById(R.id.custom_taglist);
            this.c = (TagListView) contentView.findViewById(R.id.classification_taglist);
            this.d = (TagListView) contentView.findViewById(R.id.citycode_taglist);
            this.e = (ViewGroup) contentView.findViewById(R.id.custom_tag_linear);
            this.f = (ViewGroup) contentView.findViewById(R.id.classification_tag_linear);
            this.g = (ViewGroup) contentView.findViewById(R.id.citycode_tag_linear);
            this.a = (ImageButton) contentView.findViewById(R.id.btn_back);
            this.a.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    FavoriteTagSelectPage.this.finish();
                }
            });
        }
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("tag_key")) {
            crb crb = (crb) arguments.get("tag_key");
            if (crb != null) {
                if (crb.a != null && crb.a.size() > 0) {
                    this.e.setVisibility(0);
                    for (String next : crb.a) {
                        a(this.b.addTag(next), 1, next, "");
                    }
                }
                if (crb.b != null && crb.b.size() > 0) {
                    this.g.setVisibility(0);
                    for (String next2 : crb.b) {
                        String a2 = cru.a(next2);
                        a(this.c.addTag(a2), 2, a2, next2);
                    }
                    if (crb.c != null && crb.c.size() > 0) {
                        this.f.setVisibility(0);
                        for (String next3 : crb.c) {
                            a(this.d.addTag(next3), 3, next3, "");
                        }
                    }
                }
            }
        }
    }

    private void a(View view, final int i, final String str, final String str2) {
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    FavoriteTagSelectPage.this.finish();
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("tag_type", i);
                    pageBundle.putString("tag_string", str);
                    pageBundle.putString("tag_code", str2);
                    crb crb = new crb();
                    crb.c = bim.aa().r();
                    crb.b = bim.aa().u();
                    crb.a = bim.aa().v();
                    pageBundle.putObject("tag_key", crb);
                    FavoriteTagSelectPage.this.startPage(FavoriteTagFilterResultPage.class, pageBundle);
                    try {
                        char c2 = 3;
                        if (i == 1) {
                            c2 = 1;
                        } else if (i == 2) {
                            c2 = 2;
                        } else if (i != 3) {
                            c2 = 65535;
                        }
                        if (65535 != c2) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("type", i);
                            LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B010", jSONObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cre(this);
    }
}
