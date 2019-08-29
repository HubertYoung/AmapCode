package com.alibaba.baichuan.android.auth;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.AlibcContext.Environment;
import com.alibaba.baichuan.android.trade.utils.StringUtils;
import com.alibaba.baichuan.android.trade.utils.http.HttpHelper;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashSet;
import java.util.Map;

final class c extends AsyncTask {
    final /* synthetic */ String a;
    final /* synthetic */ e b;
    final /* synthetic */ boolean c;
    final /* synthetic */ boolean d;

    c(String str, e eVar, boolean z, boolean z2) {
        this.a = str;
        this.b = eVar;
        this.c = z;
        this.d = z2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final String doInBackground(String... strArr) {
        String str;
        Object[] objArr;
        String str2;
        if (AlibcContext.environment == Environment.TEST) {
            str = "http://100.69.205.47/authHint.htm?apiList=[\"%s\"]";
            objArr = new Object[]{this.a.replace("$", "_")};
        } else if (AlibcContext.environment == Environment.PRE) {
            str = "http://pre.nbsdk-baichuan.taobao.com/authHint.htm?apiList=[\"%s\"]";
            objArr = new Object[]{this.a.replace("$", "_")};
        } else {
            str = "https://nbsdk-baichuan.alicdn.com/authHint.htm?apiList=[\"%s\"]";
            objArr = new Object[]{this.a.replace("$", "_")};
        }
        String format = String.format(str, objArr);
        try {
            AlibcLogger.e("alibc", "getHint : url  ".concat(String.valueOf(format)));
            str2 = HttpHelper.get(format, null);
            try {
                StringBuilder sb = new StringBuilder("getHint : url  ");
                sb.append(format);
                sb.append(Token.SEPARATOR);
                sb.append(str2);
                AlibcLogger.e("alibc", sb.toString());
                return str2;
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return str2;
            }
        } catch (Exception e2) {
            e = e2;
            str2 = "";
            e.printStackTrace();
            return str2;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void onPostExecute(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                this.b.a(a.HINTLIST_NULL.b, a.HINTLIST_NULL.c);
                return;
            }
            JSONObject parseObject = JSON.parseObject(str);
            HashSet hashSet = null;
            if (StringUtils.obj2Boolean(parseObject.get("success"))) {
                Map obj2MapObject = StringUtils.obj2MapObject(parseObject.get("authHintMap"));
                if (obj2MapObject != null && obj2MapObject.size() > 0) {
                    hashSet = new HashSet(obj2MapObject.size());
                    for (String str2 : obj2MapObject.keySet()) {
                        Map obj2MapString = StringUtils.obj2MapString(obj2MapObject.get(str2));
                        if (obj2MapString != null) {
                            AlibcAuthHint.putExpandList((String) obj2MapString.get("hintId"), (String) obj2MapString.get("hintName"));
                            hashSet.add(obj2MapString.get("hintId"));
                        }
                    }
                    AlibcAuthHint.putApiAndHint(this.a, hashSet);
                }
            }
            if (hashSet != null) {
                if (hashSet.size() > 0) {
                    if (this.c) {
                        AlibcAuth.a(hashSet, this.b, this.d);
                        return;
                    } else {
                        this.b.a();
                        return;
                    }
                }
            }
            this.b.a(a.HINTLIST_NULL.b, a.HINTLIST_NULL.c);
        } catch (Exception e) {
            this.b.a(a.HINTLIST_NULL.b, a.HINTLIST_NULL.c);
            e.printStackTrace();
        }
    }
}
