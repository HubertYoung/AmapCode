package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.life.common.widget.view.headersearchview.HeaderSearchView;
import org.json.JSONObject;

/* renamed from: dom reason: default package */
/* compiled from: InputSuggestAddPlusClickListenerCommon */
public final class dom implements OnClickListener {
    private TipItem a;
    private HeaderSearchView b;
    private int c;
    private int d;

    public dom(TipItem tipItem, HeaderSearchView headerSearchView, int i, int i2) {
        this.a = tipItem;
        this.b = headerSearchView;
        this.c = i;
        this.d = i2;
    }

    public final void onClick(View view) {
        String obj = view.getTag().toString();
        String str = "";
        if (!(this.b == null || this.b.getSearchEdit() == null)) {
            str = this.b.getSearchEdit().getText().toString();
        }
        if (TextUtils.isEmpty(str) && this.a.type == 0) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ItemId", this.c);
                jSONObject.put("from_page", this.d);
                jSONObject.put(TrafficUtil.KEYWORD, this.a.name);
                jSONObject.put("from", "historyList");
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00003", "B007", jSONObject);
        } else if (this.a.type == 0) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("ItemId", this.c);
                jSONObject2.put("from_page", this.d);
                jSONObject2.put(TrafficUtil.KEYWORD, this.a.name);
                jSONObject2.put("from", "suggest");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00003", "B007", jSONObject2);
        } else if (this.a.type == 1) {
            JSONObject jSONObject3 = new JSONObject();
            try {
                jSONObject3.put("ItemId", this.c);
                jSONObject3.put("from_page", this.d);
                jSONObject3.put(TrafficUtil.KEYWORD, this.a.name);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            LogManager.actionLogV2("P00003", "B008", jSONObject3);
        }
        if (this.b != null) {
            if (this.b.getSearchEdit() != null) {
                this.b.getSearchEdit().requestFocus();
                this.b.getSearchEdit().setText(obj);
                this.b.getSearchEdit().setSelection(obj.length());
            }
            this.b.showHistory();
        }
    }
}
