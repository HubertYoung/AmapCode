package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bgn reason: default package */
/* compiled from: DefaultVUIPresenter */
public final class bgn implements bgo {

    /* renamed from: bgn$a */
    /* compiled from: DefaultVUIPresenter */
    public static class a {
        public static final bgn a = new bgn(0);
    }

    /* synthetic */ bgn(byte b) {
        this();
    }

    private bgn() {
    }

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        bgd a2 = bgl.a(bgb.d);
        if (a2 != null) {
            return a2.a(bgb, bfb);
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("token", bgb.a);
            jSONObject2.put("code", 9004);
            jSONObject2.put("tip", new String[]{"抱歉，这个问题我没听懂", "抱歉，我没理解你的意思", "我没听懂，下次试试换个说法"}[(int) (System.currentTimeMillis() % 3)]);
            jSONObject.put("result", jSONObject2);
            bfb.a(bgb.a, 9004, jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
