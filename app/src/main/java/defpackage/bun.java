package defpackage;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.util.HashMap;
import org.json.JSONObject;

/* renamed from: bun reason: default package */
/* compiled from: MvpHost */
public class bun implements akc, buk {
    public AbstractBasePage a;
    private akg b;
    private Context c;
    private LayoutInflater d;
    private bul e;
    private Class<?> f;
    private boolean g;

    public final void a(Context context, LayoutInflater layoutInflater, Object obj) {
        this.b = new akg(this);
        this.c = context;
        this.d = layoutInflater;
        this.e = (bul) obj;
    }

    public final View a() {
        if (this.g) {
            try {
                FrameLayout frameLayout = new FrameLayout(this.c);
                frameLayout.addView(this.a.getContentView(), new LayoutParams(-1, -1));
                frameLayout.setLayoutParams(new LayoutParams(-1, -1));
                return frameLayout;
            } catch (Throwable th) {
                a("onCreateView", th);
            }
        }
        return null;
    }

    public final void b(akh akh) {
        HashMap hashMap;
        if (this.g) {
            PageBundle pageBundle = null;
            if (akh != null) {
                try {
                    hashMap = akh.c;
                } catch (Throwable th) {
                    a("onNewParams", th);
                }
            } else {
                hashMap = null;
            }
            if (hashMap != null) {
                if (((Integer) hashMap.get("PAGE_COUNT")).intValue() <= 0) {
                    pageBundle = (PageBundle) hashMap.get("CUSCTOM_BUNDLE");
                } else {
                    return;
                }
            }
            this.a.setArguments(pageBundle);
            this.a.onNewIntent(pageBundle);
        }
    }

    public final void b() {
        if (this.g) {
            try {
                PageBundle arguments = this.a.getArguments();
                if (arguments != null) {
                    Object obj = arguments.get("com.autonavi.mvphost.Callback");
                    if (obj != null) {
                        if (obj instanceof bha) {
                            Pair<ResultType, PageBundle> result = this.a.getResult();
                            Object obj2 = result.first;
                            Object obj3 = result.second;
                        }
                    }
                }
                this.a.onDestroy();
            } catch (Throwable th) {
                a("onDestroy", th);
            }
        }
    }

    public final void c() {
        if (this.g) {
            try {
                this.a.onStart();
            } catch (Throwable th) {
                a("onStart", th);
            }
        }
    }

    public final void d() {
        if (this.g) {
            try {
                this.a.onStop();
            } catch (Throwable th) {
                a("onStop", th);
            }
        }
    }

    public final void e() {
        if (this.g) {
            try {
                this.a.onResume();
            } catch (Throwable th) {
                a("onResume", th);
            }
        }
    }

    public final void f() {
        if (this.g) {
            try {
                this.a.onPause();
            } catch (Throwable th) {
                a("onPause", th);
            }
        }
    }

    public final void a(int i, int i2, akh akh) {
        HashMap hashMap;
        if (this.g) {
            PageBundle pageBundle = null;
            if (akh != null) {
                try {
                    hashMap = akh.c;
                } catch (Throwable th) {
                    a("onPageResult", th);
                }
            } else {
                hashMap = null;
            }
            if (hashMap != null) {
                pageBundle = (PageBundle) hashMap.get("CUSCTOM_BUNDLE");
            }
            ResultType resultType = ResultType.NONE;
            if (i2 == 1) {
                resultType = ResultType.OK;
            } else if (i2 == -1) {
                resultType = ResultType.CANCEL;
            }
            this.a.onResult(i, resultType, pageBundle);
        }
    }

    public final int g() {
        int i = 0;
        if (this.g) {
            try {
                ON_BACK_TYPE onBackPressed = this.a.onBackPressed();
                if (onBackPressed == ON_BACK_TYPE.TYPE_NORMAL) {
                    this.e.a.a = false;
                } else if (onBackPressed == ON_BACK_TYPE.TYPE_FINISH) {
                    this.e.a.a = true;
                } else {
                    this.e.a.a = false;
                    i = 1;
                }
                return i;
            } catch (Throwable th) {
                a("onBackPressed", th);
            }
        }
        return 0;
    }

    private void a(String str, Throwable th) {
        try {
            if (bgz.a) {
                int i = 4;
                StackTraceElement[] stackTrace = th.getStackTrace();
                StringBuffer stringBuffer = new StringBuffer();
                for (int i2 = 1; i2 < stackTrace.length; i2++) {
                    stringBuffer.append(stackTrace[i2]);
                    stringBuffer.append("\n\t\t\t");
                    i--;
                    if (i < 0) {
                        break;
                    }
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("method", str);
                jSONObject.put("page", this.a.getClass().getSimpleName());
                jSONObject.put(LogCategory.CATEGORY_EXCEPTION, stringBuffer.toString());
                AMapLog.logFatalNative(AMapLog.GROUP_COMMON, "P0019", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, jSONObject.toString());
            }
        } catch (Throwable unused) {
            StringBuilder sb = new StringBuilder("exception: ");
            sb.append(th.getMessage());
            AMapLog.e("MvpFrameWork", sb.toString());
        }
    }

    public final bid h() {
        return this.a;
    }

    public final akg i() {
        return this.b;
    }

    public final void a(akh akh) {
        if (akh != null) {
            this.f = akh.d;
            if (this.f != null) {
                try {
                    this.a = (AbstractBasePage) this.f.newInstance();
                    this.a.attach(this.c, this.d, null, this.b, this.e);
                    HashMap<String, Object> hashMap = akh.c;
                    PageBundle pageBundle = null;
                    if (hashMap != null) {
                        pageBundle = (PageBundle) hashMap.get("CUSCTOM_BUNDLE");
                    }
                    this.a.setArguments(pageBundle);
                    this.a.setRequestCode(akh.a);
                    this.a.performCreate(this.c);
                    this.g = true;
                } catch (IllegalAccessException | InstantiationException unused) {
                } catch (Throwable th) {
                    a("onCreate", th);
                }
            }
        }
    }
}
