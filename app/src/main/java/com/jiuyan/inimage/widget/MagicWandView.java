package com.jiuyan.inimage.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.jiuyan.inimage.InSDKEntrance;
import com.jiuyan.inimage.b.a;
import com.jiuyan.inimage.b.e;
import com.jiuyan.inimage.b.p;
import com.jiuyan.inimage.bean.BeanAKeyUse;
import com.jiuyan.inimage.bean.BeanAKeyUse.PasterItem;
import com.jiuyan.inimage.bean.BeanAKeyUseLocation;
import com.jiuyan.inimage.bean.BeanBaseOneKeyFacePaster;
import com.jiuyan.inimage.bean.BeanBaseOneKeyFacePaster.DataOneKeyFacePaster;
import com.jiuyan.inimage.bean.BeanBaseOneKeyFacePaster.ItemOneKeyPaster;
import com.jiuyan.inimage.bean.BeanBaseOneKeyFacePaster.RowPaster;
import com.jiuyan.inimage.bean.BeanFacePaster;
import com.jiuyan.inimage.bean.BeanPaster;
import com.jiuyan.inimage.callback.OnMagicWandClickListener;
import com.jiuyan.inimage.e.c;
import com.jiuyan.inimage.e.h;
import com.jiuyan.inimage.http.HttpLauncher;
import com.jiuyan.inimage.util.f;
import com.jiuyan.inimage.util.g;
import com.jiuyan.inimage.util.j;
import com.jiuyan.inimage.util.k;
import com.jiuyan.inimage.util.q;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicWandView extends ImageView implements OnClickListener {
    /* access modifiers changed from: private */
    public DataOneKeyFacePaster a;
    /* access modifiers changed from: private */
    public Bitmap b;
    /* access modifiers changed from: private */
    public OnMagicWandClickListener c;
    /* access modifiers changed from: private */
    public c d;
    private AnimatorSet e = new AnimatorSet();
    /* access modifiers changed from: private */
    public boolean f = true;
    private long g = 0;

    public MagicWandView(Context context) {
        super(context);
        c();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public MagicWandView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c();
    }

    public MagicWandView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        c();
    }

    private void c() {
        super.setOnClickListener(this);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "scaleY", new float[]{1.0f, 0.8f, 1.0f});
        this.e.play(ofFloat).with(ObjectAnimator.ofFloat(this, "scaleX", new float[]{1.0f, 0.8f, 1.0f}));
        this.e.setDuration(200);
        this.e.addListener(new f(this));
        postDelayed(new k(this, this), 1200);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
    }

    public void a(Bitmap bitmap) {
        this.b = bitmap;
        this.a = null;
        this.d = null;
        q.a("onGotImageFaceCount", "faceIdentify");
        if (bitmap != null) {
            if (InSDKEntrance.sIFaceDelegate != null) {
                InSDKEntrance.sIFaceDelegate.detectFace(bitmap, new g(this));
                return;
            }
            q.a((String) "sIFaceDelegate  is null");
            a(0, (String) "get", (String) "");
        }
    }

    /* access modifiers changed from: private */
    public float a(c cVar, Bitmap bitmap) {
        float f2 = 0.0f;
        if (bitmap == null || cVar == null) {
            return 0.0f;
        }
        float width = (float) (bitmap.getWidth() * bitmap.getHeight());
        if (cVar.c() <= 0) {
            return 0.0f;
        }
        for (int i = 0; i < cVar.c(); i++) {
            Rect rect = cVar.a()[i];
            int i2 = rect.top;
            int i3 = rect.bottom;
            f2 += ((float) ((rect.right - rect.left) * (i3 - i2))) / width;
        }
        return f2 / ((float) cVar.c());
    }

    /* access modifiers changed from: private */
    public void a(int i, String str, String str2) {
        a(i, str, str2, false);
    }

    private void a(int i, String str, String str2, boolean z) {
        this.f = true;
        HttpLauncher httpLauncher = new HttpLauncher(getContext(), 0, "https://www.in66.com/", "extapi/alipay/magic");
        if (i > 0) {
            httpLauncher.putParam("people_count", "" + i);
        }
        httpLauncher.putParam("type", str);
        httpLauncher.putParam("value", str2);
        if (this.b != null) {
            int width = this.b.getWidth();
            int height = this.b.getHeight();
            httpLauncher.putParam("photo_width", String.valueOf(width));
            httpLauncher.putParam("photo_height", String.valueOf(height));
        }
        httpLauncher.setOnCompleteListener(new h(this, z));
        httpLauncher.execute(BeanBaseOneKeyFacePaster.class);
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return System.currentTimeMillis() - this.g < 200;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.g = System.currentTimeMillis();
    }

    public void onClick(View view) {
        if (a()) {
            this.e.cancel();
            this.e.start();
            return;
        }
        b();
        this.e.cancel();
        this.e.start();
        if (this.f) {
            q.a((String) "gotoAddRecPaster:paster data is not loaded");
        } else if (this.a == null) {
            q.a((String) "gotoAddRecPaster:data is null");
            if (view != null) {
                a(true);
                this.f = true;
                if (this.d == null || this.d.c() <= 0) {
                    q.a((String) "face count is 0");
                    a(0, (String) "get", (String) "", true);
                    return;
                }
                q.a("face count is " + this.d.c());
                a(this.d.c(), (String) "face", String.valueOf(a(this.d, this.b)), true);
            }
        } else if (this.a.list != null && this.a.list.size() > 0) {
            int i = this.a.curRowPos;
            this.a.curRowPos++;
            if (this.a.curRowPos >= this.a.list.size()) {
                this.a.curRowPos = 0;
            }
            if (i < this.a.list.size()) {
                RowPaster rowPaster = this.a.list.get(i);
                if (TextUtils.isEmpty(rowPaster.play_type)) {
                    return;
                }
                if ("face".equals(rowPaster.play_type)) {
                    b(this.a, rowPaster);
                } else if ("get".equals(rowPaster.play_type)) {
                    a(this.a, rowPaster);
                }
            }
        }
    }

    public boolean a(DataOneKeyFacePaster dataOneKeyFacePaster, RowPaster rowPaster) {
        if (dataOneKeyFacePaster == null || rowPaster == null || rowPaster.get_list == null || rowPaster.get_list.size() == 0) {
            q.a((String) "randomUseAKeyPaster fail,data is null");
            return false;
        }
        List<Integer> a2 = j.a(0, rowPaster.get_list.size() - 1, 1);
        if (a2 == null) {
            return false;
        }
        for (Integer intValue : a2) {
            ItemOneKeyPaster itemOneKeyPaster = rowPaster.get_list.get(intValue.intValue());
            if (itemOneKeyPaster != null) {
                a(true);
                e eVar = new e();
                eVar.a = itemOneKeyPaster.pid;
                eVar.b = itemOneKeyPaster.pcid;
                eVar.c = 0;
                a.a(getContext(), eVar, new i(this));
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void a(BeanAKeyUse beanAKeyUse) {
        if (beanAKeyUse == null || beanAKeyUse.data == null) {
            q.a((String) "usePaster fail,data is null");
            return;
        }
        com.jiuyan.inimage.d.a aVar = new com.jiuyan.inimage.d.a();
        if (beanAKeyUse.data.paster != null && beanAKeyUse.data.paster.size() > 0) {
            aVar.a = a(beanAKeyUse.data.paster);
        }
        if (this.c != null) {
            this.c.onMagicWandClick(aVar);
        }
    }

    private List<BeanPaster> a(List<PasterItem> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            for (PasterItem next : list) {
                BeanPaster beanPaster = new BeanPaster();
                beanPaster.id = next.id;
                beanPaster.name = next.name;
                beanPaster.url = next.url;
                beanPaster.type = next.from;
                beanPaster.location = new BeanAKeyUseLocation();
                beanPaster.location.rect = next.location.rect;
                beanPaster.location.r = next.location.r;
                beanPaster.location.s = next.location.s;
                beanPaster.location.f = next.location.f;
                beanPaster.fromWhere = "1";
                arrayList.add(beanPaster);
            }
        }
        return arrayList;
    }

    public boolean b(DataOneKeyFacePaster dataOneKeyFacePaster, RowPaster rowPaster) {
        if (dataOneKeyFacePaster == null || rowPaster == null || rowPaster.list == null || rowPaster.list.size() == 0) {
            q.a((String) "randomUseAKeyPaster fail,data is null");
            return false;
        } else if (1 > rowPaster.list.size()) {
            q.a((String) "Too many faces !");
            return false;
        } else {
            List<Integer> a2 = j.a(0, rowPaster.list.size() - 1, 1);
            if (a2 != null) {
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                for (Integer intValue : a2) {
                    List list = rowPaster.list.get(intValue.intValue());
                    if (list != null) {
                        ArrayList arrayList2 = new ArrayList();
                        for (int i = 0; i < list.size(); i++) {
                            BeanFacePaster beanFacePaster = (BeanFacePaster) list.get(i);
                            BeanPaster beanPaster = new BeanPaster();
                            beanPaster.id = beanFacePaster.id;
                            beanPaster.name = beanFacePaster.name;
                            beanPaster.url = beanFacePaster.url;
                            hashMap.put("" + beanPaster.hashCode(), beanFacePaster.face_param);
                            arrayList2.add(beanPaster);
                        }
                        arrayList.add(arrayList2);
                    }
                }
                a((List<List<BeanPaster>>) arrayList, (Map<String, Map<String, String>>) hashMap);
            }
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        if (getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).toast(str, 0);
        } else if (getContext() != null) {
            Toast.makeText(getContext(), str, 0).show();
        }
    }

    private void a(List<List<BeanPaster>> list, Map<String, Map<String, String>> map) {
        if (list != null) {
            ArrayList<BeanPaster> arrayList = new ArrayList<>();
            for (List<BeanPaster> addAll : list) {
                arrayList.addAll(addAll);
            }
            ArrayList arrayList2 = new ArrayList();
            if (arrayList != null && arrayList.size() > 0) {
                for (BeanPaster beanPaster : arrayList) {
                    arrayList2.add(new p(beanPaster.id, beanPaster.url, g.a + File.separator + f.a(beanPaster.url)));
                }
            }
            a(true);
            com.jiuyan.inimage.b.q.a(getContext(), arrayList2, new j(this, list, map));
        }
    }

    public List<List<BeanPaster>> a(Bitmap bitmap, List<List<BeanPaster>> list, Map<String, Map<String, String>> map) {
        if (bitmap == null || list == null || map == null || this.d == null) {
            return null;
        }
        if (this.d.c() != list.size()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.d.c()) {
                return arrayList;
            }
            ArrayList arrayList2 = new ArrayList();
            List list2 = list.get(i2);
            HashMap hashMap = new HashMap();
            ArrayList arrayList3 = new ArrayList();
            int i3 = 0;
            while (true) {
                int i4 = i3;
                if (i4 >= list2.size()) {
                    break;
                }
                BeanPaster beanPaster = (BeanPaster) list2.get(i4);
                Bitmap a2 = k.a(beanPaster.url);
                if (a2 != null) {
                    if (a2 != null) {
                        hashMap.put(beanPaster.id, a2);
                    }
                    arrayList3.add(map.get("" + beanPaster.hashCode()));
                }
                i3 = i4 + 1;
            }
            ArrayList<h> a3 = com.jiuyan.inimage.e.e.a(arrayList3, hashMap).a(this.d, i2, bitmap.getWidth(), bitmap.getHeight());
            int i5 = 0;
            while (true) {
                int i6 = i5;
                if (i6 >= a3.size()) {
                    break;
                }
                h hVar = a3.get(i6);
                BeanPaster beanPaster2 = (BeanPaster) list2.get(i6);
                beanPaster2.location = new BeanAKeyUseLocation();
                beanPaster2.location.rect = "" + (hVar.a / ((float) bitmap.getWidth())) + "," + (hVar.b / ((float) bitmap.getHeight()));
                beanPaster2.location.s = "" + hVar.d;
                beanPaster2.location.r = "" + hVar.c;
                arrayList2.add(beanPaster2);
                i5 = i6 + 1;
            }
            arrayList.add(arrayList2);
            i = i2 + 1;
        }
    }

    public void setOnMagicWandClickListener(OnMagicWandClickListener onMagicWandClickListener) {
        this.c = onMagicWandClickListener;
    }

    public void a(boolean z) {
        if (getContext() instanceof BaseActivity) {
            if (z) {
                ((BaseActivity) getContext()).showProgressDialog(null);
            } else {
                ((BaseActivity) getContext()).dismissProgressDialog();
            }
        }
    }
}
