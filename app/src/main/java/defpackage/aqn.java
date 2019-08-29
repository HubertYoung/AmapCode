package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.LogVersionType;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView.a;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.autosec.UTAnalyticsUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aqn reason: default package */
/* compiled from: QuickServiceHeader */
public final class aqn implements OnClickListener {
    public final aqu a = a.a;
    public final View b;
    public VUIEmojiView c = ((VUIEmojiView) this.b.findViewById(R.id.btn_emoji));
    public aqv d;
    private final TextView e = ((TextView) this.b.findViewById(R.id.txt_hotword));
    private final bid f;

    public aqn(Context context, bid bid, ViewGroup viewGroup) {
        this.f = bid;
        this.b = LayoutInflater.from(context).inflate(R.layout.amaphome_quickservice_searchhead, viewGroup, false);
        this.b.setOnClickListener(this);
        this.b.findViewById(R.id.btn_voice).setOnClickListener(this);
        this.a.a(context, this.e);
        this.c.setOnVuiEmojiViewClickListener(new a() {
            public final void onClick(View view) {
                aqn.a();
            }
        });
        this.c.setShowInit(true);
        this.c.setVisibility(0);
        this.d = new aqv(this.f, this.c);
    }

    public final void onClick(View view) {
        if (!bnp.a(view.getId()) && view == this.b) {
            a.k();
            JSONObject jSONObject = new JSONObject();
            String charSequence = this.e.getText().toString();
            String str = "0";
            if (!TextUtils.isEmpty(charSequence)) {
                if (charSequence.contains(AMapAppGlobal.getApplication().getString(R.string.search_indoor_end))) {
                    str = "1";
                }
            }
            try {
                jSONObject.put("isIndoor", str);
                jSONObject.put("status", LogVersionType.REDESIGN);
                jSONObject.put("type", "普通搜框");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String valueOf = String.valueOf(System.currentTimeMillis());
            HashMap hashMap = new HashMap();
            hashMap.put("name", "SearchEdit");
            hashMap.put("timestamp", valueOf);
            UTAnalyticsUtils.getInstance().userDefinedTracker("DefaultPage", hashMap);
            try {
                jSONObject.put("timestamp", valueOf);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B002", jSONObject);
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "普通搜框");
            kd.a((String) "amap.P00001.0.B002", (Map<String, String>) hashMap2);
            this.f.startPage((String) "amap.search.action.searchfragment", new PageBundle());
        }
    }

    static /* synthetic */ void a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", LogVersionType.REDESIGN);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B003", jSONObject);
        kd.a((String) "amap.P00001.0.B003", (Map<String, String>) null);
    }
}
