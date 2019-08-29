package com.autonavi.map.search.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.searchhome.ajx.ModuleSearchHome;
import com.autonavi.bundle.searchhome.ajx.ModuleSearchHome.a;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView.b;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.search.action.searchfragment")
public class SearchPage extends Ajx3Page implements bgm, launchModeSingleTask {
    b a;
    /* access modifiers changed from: private */
    public View b;
    /* access modifiers changed from: private */
    public boolean c;
    /* access modifiers changed from: private */
    public cck d;
    /* access modifiers changed from: private */
    public VUIEmojiView e;
    private cbh f;
    private Callback<AmapAjxView> g;
    private Callback<AmapAjxView> h;
    private OnClickListener i;
    /* access modifiers changed from: private */
    public final a j;

    public void finishSelf() {
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 8388608;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public SearchPage() {
        this.f = new cbh(getArguments() == null ? "" : getArguments().getString(TrafficUtil.KEYWORD));
        this.a = new b() {
            public final void a(boolean z) {
                if (SearchPage.this.b != null) {
                    SearchPage.this.b.setVisibility(z ? 0 : 8);
                    SearchPage.this.b.setVisibility(0);
                    VUIEmojiView b = SearchPage.this.e;
                    if (z || b == null || b.getVisibility() == 0) {
                        SearchPage.this.d.b();
                    } else {
                        SearchPage.this.d.a();
                    }
                }
            }
        };
        this.g = new Callback<AmapAjxView>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(AmapAjxView amapAjxView) {
                ModuleSearchHome moduleSearchHome = (ModuleSearchHome) amapAjxView.getJsModule(ModuleSearchHome.MODULE_NAME);
                if (moduleSearchHome != null) {
                    moduleSearchHome.setActionCallback(SearchPage.this.j);
                }
            }
        };
        this.h = new Callback<AmapAjxView>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(AmapAjxView amapAjxView) {
                SearchPage.this.e = (VUIEmojiView) SearchPage.this.a((ViewGroup) SearchPage.this.mAjxView, VUIEmojiView.class);
                if (SearchPage.this.e != null) {
                    SearchPage.this.e.setOnVisibleListener(SearchPage.this.a);
                }
            }
        };
        this.i = new OnClickListener() {
            public final void onClick(View view) {
                if (view == SearchPage.this.b) {
                    Entry[] entryArr = new Entry[0];
                    SearchPage.a((String) "P00004", (String) "B039");
                    if (SearchPage.this.c) {
                        VUIEmojiView b = SearchPage.this.e;
                        if (b != null) {
                            b.performClick();
                        }
                    }
                }
            }
        };
        this.j = new a() {
            public final void a(boolean z) {
                SearchPage.a(SearchPage.this, z);
            }

            public final void a(String str) {
                try {
                    ((cbi) SearchPage.this.mPresenter).a(SearchHistoryHelper.getInstance().parseItemCloudJSON(new JSONObject(str)), false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public final void a(String str, boolean z, String str2) {
                try {
                    TipItem a2 = bxs.a(new JSONObject(str));
                    a2.userInput = str2;
                    ((cbi) SearchPage.this.mPresenter).a(a2, z);
                } catch (JSONException unused) {
                }
            }
        };
    }

    public Ajx3PagePresenter createPresenter() {
        return new cbi(this);
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
        }
        arguments.putString("url", "path://amap_bundle_search/src/home/pages/HomePage.page.js");
        arguments.putString("jsData", this.f.a());
        super.onCreate(context);
        this.c = VUIStateManager.f().m();
        this.d = new cck(this);
        this.b = LayoutInflater.from(getContext()).inflate(R.layout.search_fragment_voice_window_emoji, null);
        this.d.c = 49;
        this.b.setOnClickListener(this.i);
        cck cck = this.d;
        cck.b.setContentView(this.b);
        this.mAjxView.onAjxContextCreated(this.g);
        this.mAjxView.setLoadingCallback(this.h);
    }

    public void resume() {
        this.ajxPageStateInvoker.setResumeData(((cbi) this.mPresenter).a());
        super.resume();
        requestScreenOrientation(1);
        if (!this.c || this.b.getVisibility() == 0) {
            this.d.b();
        }
    }

    public void pause() {
        super.pause();
        this.d.a();
    }

    public bgo getPresenter() {
        return (cbi) this.mPresenter;
    }

    /* access modifiers changed from: private */
    public <T extends View> T a(ViewGroup viewGroup, Class<T> cls) {
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            T childAt = viewGroup.getChildAt(i2);
            if (cls.isAssignableFrom(childAt.getClass())) {
                return childAt;
            }
            if (childAt instanceof ViewGroup) {
                T a2 = a((ViewGroup) childAt, cls);
                if (a2 != null) {
                    return a2;
                }
            }
        }
        return null;
    }

    static /* synthetic */ void a(String str, String str2) {
        try {
            LogManager.actionLogV2(str, str2, null);
        } catch (JSONException unused) {
            LogManager.actionLogV2(str, str2);
        }
    }

    static /* synthetic */ void a(SearchPage searchPage, boolean z) {
        if (z) {
            searchPage.b.setVisibility(0);
            VUIEmojiView vUIEmojiView = searchPage.e;
            if (!searchPage.c || vUIEmojiView == null || vUIEmojiView.getEmojiVisibility() == 0) {
                searchPage.d.b();
            }
            return;
        }
        searchPage.b.setVisibility(8);
        searchPage.d.a();
    }
}
