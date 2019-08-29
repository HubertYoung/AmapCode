package com.alipay.mobile.tinyappcommon.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.basic.AUHorizontalListView;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RecentUseTinyAppPopWindow */
public class b extends a {
    private static final String c = b.class.getSimpleName();
    /* access modifiers changed from: private */
    public float d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public List<a> f;
    /* access modifiers changed from: private */
    public TinyAppMixActionService g = TinyAppService.get().getMixActionService();
    /* access modifiers changed from: private */
    public String h;
    private H5Page i;
    private int j = 0;
    private boolean k;

    /* compiled from: RecentUseTinyAppPopWindow */
    private class a {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        public boolean j;
        public boolean k;
        public boolean l;

        private a() {
        }

        /* synthetic */ a(b x0, byte b2) {
            this();
        }
    }

    /* renamed from: com.alipay.mobile.tinyappcommon.view.b$b reason: collision with other inner class name */
    /* compiled from: RecentUseTinyAppPopWindow */
    private class C0104b implements H5ImageListener {
        private ImageView b;

        C0104b(ImageView imageView) {
            this.b = imageView;
        }

        public final void onImage(Bitmap bitmap) {
            if (this.b != null && bitmap != null) {
                this.b.setImageBitmap(bitmap);
            }
        }
    }

    /* compiled from: RecentUseTinyAppPopWindow */
    private static class c {
        ImageView a;
        TextView b;
        View c;

        private c() {
        }

        /* synthetic */ c(byte b2) {
            this();
        }
    }

    public b(Context context, JSONArray recentUseArray) {
        super(context, recentUseArray);
        this.j = TinyappUtils.getStatusBarHeight(context);
    }

    /* access modifiers changed from: protected */
    public final View a(Object data) {
        if (data == null) {
            this.f = new ArrayList();
        } else {
            this.f = a((JSONArray) data);
        }
        if (this.f == null || this.f.isEmpty()) {
            this.k = false;
            H5Log.d(c, "initContentView.. no valid item");
            return null;
        }
        this.k = true;
        AUHorizontalListView listView = new AUHorizontalListView(this.a);
        listView.setAdapter((ListAdapter) new BaseAdapter() {
            public final int getCount() {
                return b.this.f.size();
            }

            public final Object getItem(int position) {
                return b.this.f.get(position);
            }

            public final long getItemId(int position) {
                return (long) position;
            }

            /* JADX WARNING: type inference failed for: r15v2, types: [android.view.View] */
            /* JADX WARNING: type inference failed for: r4v2, types: [android.view.View] */
            /* JADX WARNING: Multi-variable type inference failed */
            /* JADX WARNING: Unknown variable types count: 2 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final android.view.View getView(int r14, android.view.View r15, android.view.ViewGroup r16) {
                /*
                    r13 = this;
                    java.lang.Object r7 = r13.getItem(r14)
                    com.alipay.mobile.tinyappcommon.view.b$a r7 = (com.alipay.mobile.tinyappcommon.view.b.a) r7
                    if (r15 != 0) goto L_0x00c9
                    r9 = 1110441984(0x42300000, float:44.0)
                    com.alipay.mobile.tinyappcommon.view.b r10 = com.alipay.mobile.tinyappcommon.view.b.this
                    float r10 = r10.d
                    float r9 = r9 * r10
                    int r6 = (int) r9
                    android.widget.LinearLayout r0 = new android.widget.LinearLayout
                    com.alipay.mobile.tinyappcommon.view.b r9 = com.alipay.mobile.tinyappcommon.view.b.this
                    android.content.Context r9 = r9.a
                    r0.<init>(r9)
                    r9 = 1
                    r0.setOrientation(r9)
                    com.alipay.mobile.antui.basic.AUHorizontalListView$LayoutParams r9 = new com.alipay.mobile.antui.basic.AUHorizontalListView$LayoutParams
                    com.alipay.mobile.tinyappcommon.view.b r10 = com.alipay.mobile.tinyappcommon.view.b.this
                    int r10 = r10.e
                    r11 = -1
                    r9.<init>(r10, r11)
                    r0.setLayoutParams(r9)
                    r9 = -1
                    r0.setBackgroundColor(r9)
                    r9 = 17
                    r0.setGravity(r9)
                    r15 = r0
                    com.alipay.mobile.tinyappcommon.view.b$c r8 = new com.alipay.mobile.tinyappcommon.view.b$c
                    r9 = 0
                    r8.<init>(r9)
                    r15.setTag(r8)
                    boolean r9 = r7.l
                    if (r9 == 0) goto L_0x0047
                    r4 = r15
                L_0x0046:
                    return r4
                L_0x0047:
                    com.alipay.mobile.tinyappcommon.view.RoundCornerImageView r1 = new com.alipay.mobile.tinyappcommon.view.RoundCornerImageView
                    com.alipay.mobile.tinyappcommon.view.b r9 = com.alipay.mobile.tinyappcommon.view.b.this
                    android.content.Context r9 = r9.a
                    r1.<init>(r9)
                    android.widget.FrameLayout$LayoutParams r5 = new android.widget.FrameLayout$LayoutParams
                    r5.<init>(r6, r6)
                    r9 = 17
                    r5.gravity = r9
                    r9 = 1109131264(0x421c0000, float:39.0)
                    r1.setRoundSize(r9)
                    android.widget.ImageView$ScaleType r9 = android.widget.ImageView.ScaleType.FIT_XY
                    r1.setScaleType(r9)
                    r1.setLayoutParams(r5)
                    r9 = 1
                    r10 = 1
                    r11 = 1
                    r12 = 1
                    r1.setPadding(r9, r10, r11, r12)
                    int r9 = com.alipay.mobile.tinyapp.R.drawable.recent_use_tiny_app_icon_bg
                    r1.setBackgroundResource(r9)
                    java.lang.String r9 = r7.h
                    com.alipay.mobile.tinyappcommon.view.b$b r10 = new com.alipay.mobile.tinyappcommon.view.b$b
                    com.alipay.mobile.tinyappcommon.view.b r11 = com.alipay.mobile.tinyappcommon.view.b.this
                    r10.<init>(r1)
                    com.alipay.mobile.nebula.util.H5ImageUtil.loadImage(r9, r10)
                    r0.addView(r1)
                    android.widget.TextView r2 = new android.widget.TextView
                    com.alipay.mobile.tinyappcommon.view.b r9 = com.alipay.mobile.tinyappcommon.view.b.this
                    android.content.Context r9 = r9.a
                    r2.<init>(r9)
                    java.lang.String r9 = r7.i
                    r2.setText(r9)
                    r9 = 1095761920(0x41500000, float:13.0)
                    r2.setTextSize(r9)
                    r9 = 17
                    r2.setGravity(r9)
                    r9 = 1
                    r2.setSingleLine(r9)
                    android.text.TextUtils$TruncateAt r9 = android.text.TextUtils.TruncateAt.END
                    r2.setEllipsize(r9)
                    android.widget.LinearLayout$LayoutParams r3 = new android.widget.LinearLayout$LayoutParams
                    r9 = -1
                    r10 = -2
                    r3.<init>(r9, r10)
                    r9 = 17
                    r3.gravity = r9
                    r9 = 6
                    r10 = 25
                    r11 = 6
                    r12 = 0
                    r3.setMargins(r9, r10, r11, r12)
                    r0.addView(r2, r3)
                    r8.a = r1
                    r8.b = r2
                    r8.c = r0
                    com.alipay.mobile.tinyappcommon.view.b$1$1 r9 = new com.alipay.mobile.tinyappcommon.view.b$1$1
                    r9.<init>(r7)
                    r0.setOnClickListener(r9)
                L_0x00c6:
                    r4 = r15
                    goto L_0x0046
                L_0x00c9:
                    java.lang.Object r8 = r15.getTag()
                    com.alipay.mobile.tinyappcommon.view.b$c r8 = (com.alipay.mobile.tinyappcommon.view.b.c) r8
                    android.widget.TextView r9 = r8.b
                    if (r9 == 0) goto L_0x00c6
                    boolean r9 = r7.l
                    if (r9 == 0) goto L_0x00e6
                    android.widget.TextView r9 = r8.b
                    r10 = 0
                    r9.setText(r10)
                    android.widget.ImageView r9 = r8.a
                    r10 = 0
                    r9.setImageBitmap(r10)
                    r4 = r15
                    goto L_0x0046
                L_0x00e6:
                    android.widget.TextView r9 = r8.b
                    java.lang.String r10 = r7.i
                    r9.setText(r10)
                    android.widget.ImageView r9 = r8.a
                    r10 = 0
                    r9.setImageBitmap(r10)
                    java.lang.String r9 = r7.h
                    com.alipay.mobile.tinyappcommon.view.b$b r10 = new com.alipay.mobile.tinyappcommon.view.b$b
                    com.alipay.mobile.tinyappcommon.view.b r11 = com.alipay.mobile.tinyappcommon.view.b.this
                    android.widget.ImageView r12 = r8.a
                    r10.<init>(r12)
                    com.alipay.mobile.nebula.util.H5ImageUtil.loadImage(r9, r10)
                    android.view.View r9 = r8.c
                    com.alipay.mobile.tinyappcommon.view.b$1$2 r10 = new com.alipay.mobile.tinyappcommon.view.b$1$2
                    r10.<init>(r7)
                    r9.setOnClickListener(r10)
                    goto L_0x00c6
                */
                throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.view.b.AnonymousClass1.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
            }
        });
        return listView;
    }

    /* access modifiers changed from: private */
    public void b(final String appId) {
        if (!TextUtils.isEmpty(appId)) {
            boolean CAN_FORCE_START_FROM_MAINUI = true;
            String canForceStartFromMainUi = ((ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName())).getConfig("KEY_CAN_FORCE_START_FROM_MAINUI");
            if (!TextUtils.isEmpty(canForceStartFromMainUi)) {
                CAN_FORCE_START_FROM_MAINUI = Boolean.valueOf(canForceStartFromMainUi).booleanValue();
            }
            if (LoggerFactory.getProcessInfo().isMainProcess() || !CAN_FORCE_START_FROM_MAINUI) {
                H5Log.d(c, "startTinyAppAndCloseCurrent old " + CAN_FORCE_START_FROM_MAINUI);
                if (this.i != null) {
                    this.i.sendEvent(CommonEvents.EXIT_SESSION, null);
                }
                if (this.g != null) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        public final void run() {
                            JSONObject params = new JSONObject();
                            params.put((String) "chInfo", (Object) "ch_tinylongpress");
                            b.this.g.startApp(b.this.h, appId, params, false);
                        }
                    }, 300);
                }
            } else if (this.g != null) {
                H5Log.d(c, "startTinyAppAndCloseCurrent new " + CAN_FORCE_START_FROM_MAINUI);
                JSONObject params = new JSONObject();
                params.put((String) "chInfo", (Object) "ch_tinylongpress");
                params.put((String) "FORCE_START_LITE_APP_FROM_MAIN_UI", (Object) Boolean.valueOf(true));
                this.g.startApp(this.h, appId, params, false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final int a() {
        return (int) (100.0f * this.d);
    }

    /* access modifiers changed from: protected */
    public final int b() {
        int width = -1;
        try {
            DisplayMetrics dm = this.a.getResources().getDisplayMetrics();
            this.d = dm.density;
            width = dm.widthPixels - (((int) (5.0f * this.d)) * 2);
            this.e = width / 4;
            return width;
        } catch (Throwable e2) {
            H5Log.e(c, "initWidth...e=" + e2);
            return width;
        }
    }

    public final void a(View parent) {
        if (this.k) {
            showAtLocation(parent, 8388659, (int) (5.0f * this.d), this.j + ((int) this.a.getResources().getDimension(R.dimen.h5_title_height)) + 10);
        }
    }

    public final void a(String appId) {
        this.h = appId;
    }

    public final void a(H5Page h5Page) {
        this.i = h5Page;
    }

    private List<a> a(JSONArray array) {
        if (array == null || array.isEmpty()) {
            return null;
        }
        int size = array.size();
        int validSiz = 0;
        List modelList = new ArrayList();
        for (int i2 = 1; i2 < size && validSiz < 8; i2++) {
            JSONObject item = (JSONObject) array.get(i2);
            String nbsn = item.getString("nbsn");
            if (!TextUtils.isEmpty(nbsn)) {
                H5Log.d(c, "filter dev version");
                if (i2 == size - 1 && validSiz < 4 && validSiz > 0) {
                    for (int fill = validSiz; fill < 4; fill++) {
                        a fillModel = new a(this, 0);
                        fillModel.l = true;
                        modelList.add(fillModel);
                    }
                    return modelList;
                }
            } else {
                a model = new a(this, 0);
                model.g = item.getString("appId");
                model.h = item.getString("iconUrl");
                model.i = item.getString("name");
                model.j = item.getBoolean("display").booleanValue();
                model.a = item.getString("itemId");
                model.b = nbsn;
                model.c = item.getString(H5PreferAppList.nbsv);
                model.d = item.getString("slogan");
                model.f = item.getString("scheme");
                model.e = item.getString("extra");
                model.k = item.getBoolean("inMarketStage").booleanValue();
                model.l = false;
                validSiz++;
                modelList.add(model);
                if (i2 == size - 1 && validSiz < 4 && validSiz > 0) {
                    for (int fill2 = validSiz; fill2 < 4; fill2++) {
                        a fillModel2 = new a(this, 0);
                        fillModel2.l = true;
                        modelList.add(fillModel2);
                    }
                    return modelList;
                }
            }
        }
        return modelList;
    }
}
