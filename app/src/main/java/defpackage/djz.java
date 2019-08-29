package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.bundle.entity.infolite.internal.Condition;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.trafficboard.widget.FilterMenuMainAdapter;
import com.autonavi.minimap.drive.trafficboard.widget.FilterMenuSubAdapter;
import java.util.List;

/* renamed from: djz reason: default package */
/* compiled from: FilterPopup */
public final class djz {
    private Condition A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    public final View a;
    public final PopupWindow b;
    public final FilterMenuMainAdapter c;
    public final FilterMenuSubAdapter d;
    public final ListView e;
    public final ListView f;
    public final LinearLayout g;
    public final TextView[] h;
    public final LinearLayout[] i;
    public LinearLayout j;
    public List<Condition> k;
    public Condition l;
    public final Context m;
    public boolean n;
    public final String[] o;
    int p;
    public int q;
    public a r;
    private LinearLayout s;
    private TextView t;
    private TextView u;
    private LinearLayout v;
    private LinearLayout w;
    private View x;
    private View y;
    private final View z;

    /* renamed from: djz$a */
    /* compiled from: FilterPopup */
    public interface a {
        void a(int i, Condition condition);
    }

    public djz(View view) {
        this(view, 0);
    }

    private djz(View view, byte b2) {
        this.s = null;
        this.h = new TextView[4];
        this.i = new LinearLayout[4];
        this.k = null;
        this.A = null;
        this.l = null;
        this.n = false;
        this.B = -1;
        this.o = new String[3];
        this.p = 0;
        this.q = -1;
        this.G = -1;
        this.H = -1;
        this.a = view;
        this.m = view.getContext();
        this.n = false;
        View inflate = LayoutInflater.from(this.m).inflate(R.layout.report_filterlayout, null);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ll_list);
        linearLayout.measure(0, 0);
        this.C = this.m.getResources().getDrawable(R.drawable.tag_filter_main_itembg).getIntrinsicHeight();
        linearLayout.getLayoutParams().height = this.C * 5;
        this.z = inflate.findViewById(R.id.tab_layout);
        int i2 = 0;
        while (i2 < this.h.length) {
            TextView[] textViewArr = this.h;
            View view2 = this.z;
            StringBuilder sb = new StringBuilder("textview");
            int i3 = i2 + 1;
            sb.append(i3);
            textViewArr[i2] = (TextView) view2.findViewWithTag(sb.toString());
            LinearLayout[] linearLayoutArr = this.i;
            View view3 = this.z;
            StringBuilder sb2 = new StringBuilder(ResUtils.LAYOUT);
            sb2.append(i3);
            linearLayoutArr[i2] = (LinearLayout) view3.findViewWithTag(sb2.toString());
            i2 = i3;
        }
        this.c = new FilterMenuMainAdapter(this.m);
        this.d = new FilterMenuSubAdapter(this.m);
        this.e = (ListView) inflate.findViewById(R.id.lv_main);
        this.e.setChoiceMode(1);
        this.f = (ListView) inflate.findViewById(R.id.lv_sub);
        this.f.setChoiceMode(1);
        this.e.setAdapter(this.c);
        this.f.setAdapter(this.d);
        this.t = (TextView) inflate.findViewById(R.id.leftTxt);
        this.u = (TextView) inflate.findViewById(R.id.rightTxt);
        this.g = (LinearLayout) inflate.findViewById(R.id.ll_bottom);
        if (this.t != null) {
            this.t.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    djz.this.p = 0;
                    djz.this.a(djz.this.q, true);
                }
            });
        }
        if (this.u != null) {
            this.u.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    djz.this.p = 1;
                    djz.this.a(djz.this.q, true);
                }
            });
        }
        this.s = (LinearLayout) inflate.findViewById(R.id.rightLL);
        this.v = (LinearLayout) inflate.findViewById(R.id.left_title);
        this.w = (LinearLayout) inflate.findViewById(R.id.right_title);
        this.x = inflate.findViewById(R.id.left_blue_line);
        this.y = inflate.findViewById(R.id.right_blue_line);
        this.F = ags.a(this.m).height();
        this.E = (int) (ags.c(this.m) * 100.0f);
        this.C = this.m.getResources().getDrawable(R.drawable.tag_filter_main_itembg).getIntrinsicHeight();
        this.z.measure(0, 0);
        this.D = this.z.getMeasuredHeight();
        this.b = new PopupWindow(inflate, -1, -1);
        this.b.setBackgroundDrawable(this.m.getResources().getDrawable(17170445));
        this.b.setAnimationStyle(0);
        this.e.setVisibility(4);
        this.f.setVisibility(4);
        this.b.setOutsideTouchable(false);
        this.b.setFocusable(true);
        this.b.update();
    }

    private void b() {
        int[] iArr = new int[2];
        this.a.getLocationOnScreen(iArr);
        this.B = iArr[1];
        int i2 = ((this.F - this.D) - this.B) - this.E;
        int i3 = this.C * 5;
        if (i3 >= i2) {
            LayoutParams layoutParams = (LayoutParams) this.g.getLayoutParams();
            layoutParams.height = (i2 - ((i2 / this.C) * this.C)) + this.E;
            this.g.setLayoutParams(layoutParams);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) this.g.getLayoutParams();
        layoutParams2.height = (i2 - i3) + this.E;
        this.g.setLayoutParams(layoutParams2);
    }

    public final void a(int i2) {
        try {
            a(i2, false);
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x019b  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01d1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r8, boolean r9) {
        /*
            r7 = this;
            int r0 = r7.q
            r1 = -1
            if (r0 == r1) goto L_0x001d
            int r0 = r7.q
            if (r0 != r8) goto L_0x001d
            if (r9 != 0) goto L_0x001d
            android.widget.PopupWindow r0 = r7.b
            if (r0 == 0) goto L_0x001d
            android.widget.PopupWindow r0 = r7.b
            boolean r0 = r0.isShowing()
            if (r0 == 0) goto L_0x001d
            android.widget.PopupWindow r8 = r7.b
            r8.dismiss()
            return
        L_0x001d:
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = r7.A
            r2 = 8
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L_0x009e
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = r7.A
            int r0 = r0.getSubwayEnable()
            if (r0 != r3) goto L_0x009e
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = r7.A
            int r0 = r0.getDistrictEnable()
            if (r0 != r3) goto L_0x009e
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = r7.A
            int r0 = r0.getRangeIndex()
            if (r8 != r0) goto L_0x009e
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = r7.A
            int r0 = r0.getRangeIndex()
            if (r0 == r1) goto L_0x009e
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = r7.A
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r0.conditionsData
            if (r0 == 0) goto L_0x009e
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = r7.A
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r0.conditionsData
            int r0 = r0.size()
            if (r0 <= r3) goto L_0x009e
            android.widget.LinearLayout r0 = r7.v
            r0.setVisibility(r4)
            android.widget.LinearLayout r0 = r7.w
            r0.setVisibility(r4)
            android.widget.TextView r0 = r7.t
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = r7.A
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r5 = r5.conditionsData
            com.autonavi.bundle.entity.infolite.internal.Condition r6 = r7.A
            int r6 = r6.getRangeIndex()
            java.lang.Object r5 = r5.get(r6)
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = (com.autonavi.bundle.entity.infolite.internal.Condition) r5
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r5 = r5.subConditions
            java.lang.Object r5 = r5.get(r4)
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = (com.autonavi.bundle.entity.infolite.internal.Condition) r5
            java.lang.String r5 = r5.name
            r0.setText(r5)
            android.widget.TextView r0 = r7.u
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = r7.A
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r5 = r5.conditionsData
            com.autonavi.bundle.entity.infolite.internal.Condition r6 = r7.A
            int r6 = r6.getRangeIndex()
            java.lang.Object r5 = r5.get(r6)
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = (com.autonavi.bundle.entity.infolite.internal.Condition) r5
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r5 = r5.subConditions
            java.lang.Object r5 = r5.get(r3)
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = (com.autonavi.bundle.entity.infolite.internal.Condition) r5
            java.lang.String r5 = r5.name
            r0.setText(r5)
            goto L_0x00a8
        L_0x009e:
            android.widget.LinearLayout r0 = r7.v
            r0.setVisibility(r2)
            android.widget.LinearLayout r0 = r7.w
            r0.setVisibility(r2)
        L_0x00a8:
            java.util.List<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r7.k
            if (r0 == 0) goto L_0x01ec
            java.util.List<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r7.k
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x01ec
            java.util.List<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r7.k
            int r0 = r0.size()
            if (r8 >= r0) goto L_0x01ec
            java.util.List<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r7.k
            java.lang.Object r0 = r0.get(r8)
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = (com.autonavi.bundle.entity.infolite.internal.Condition) r0
            if (r0 != 0) goto L_0x00c7
            return
        L_0x00c7:
            if (r9 == 0) goto L_0x00cc
            int r9 = r7.p
            goto L_0x00da
        L_0x00cc:
            com.autonavi.bundle.entity.infolite.internal.Condition r9 = r7.A
            if (r9 == 0) goto L_0x00d9
            com.autonavi.bundle.entity.infolite.internal.Condition r9 = r7.A
            int r9 = r9.getAreaSelectIndex()
            r7.p = r9
            goto L_0x00da
        L_0x00d9:
            r9 = 0
        L_0x00da:
            boolean r5 = r0.isHasDistrictAndSubway()
            if (r5 == 0) goto L_0x0114
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r0.subConditions
            java.lang.Object r0 = r0.get(r9)
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = (com.autonavi.bundle.entity.infolite.internal.Condition) r0
            r5 = 4
            if (r9 != 0) goto L_0x0100
            android.view.View r9 = r7.x
            r9.setVisibility(r4)
            android.widget.TextView r9 = r7.t
            r9.setSelected(r3)
            android.widget.TextView r9 = r7.u
            r9.setSelected(r4)
            android.view.View r9 = r7.y
            r9.setVisibility(r5)
            goto L_0x0114
        L_0x0100:
            android.view.View r9 = r7.x
            r9.setVisibility(r5)
            android.widget.TextView r9 = r7.t
            r9.setSelected(r4)
            android.widget.TextView r9 = r7.u
            r9.setSelected(r3)
            android.view.View r9 = r7.y
            r9.setVisibility(r4)
        L_0x0114:
            r7.q = r8
            r7.G = r1
            r7.H = r1
            com.autonavi.bundle.entity.infolite.internal.Condition r9 = new com.autonavi.bundle.entity.infolite.internal.Condition
            r9.<init>()
            r7.l = r9
            com.autonavi.bundle.entity.infolite.internal.Condition r9 = r7.l
            java.lang.String r1 = r0.checkedValue
            r9.checkedValue = r1
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r9 = r0.subConditions
            if (r9 != 0) goto L_0x012c
            return
        L_0x012c:
            r7.b()
            com.autonavi.minimap.drive.trafficboard.widget.FilterMenuMainAdapter r1 = r7.c
            r1.setData(r9)
            com.autonavi.minimap.drive.trafficboard.widget.FilterMenuSubAdapter r1 = r7.d
            r5 = 0
            r1.setData(r5)
            r7.a(r0)
            android.widget.LinearLayout r0 = r7.s
            r0.setVisibility(r2)
            android.widget.ListView r0 = r7.f
            r0.setVisibility(r2)
            int r0 = r7.G
            if (r0 < 0) goto L_0x0198
            com.autonavi.minimap.drive.trafficboard.widget.FilterMenuMainAdapter r0 = r7.c
            int r1 = r7.G
            r0.setSelection(r1)
            int r0 = r7.H
            if (r0 < 0) goto L_0x0198
            int r0 = r7.G
            if (r0 < 0) goto L_0x0198
            int r0 = r7.G
            java.lang.Object r0 = r9.get(r0)
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = (com.autonavi.bundle.entity.infolite.internal.Condition) r0
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r0.subConditions
            if (r0 == 0) goto L_0x0198
            int r0 = r7.G
            java.lang.Object r0 = r9.get(r0)
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = (com.autonavi.bundle.entity.infolite.internal.Condition) r0
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r0.subConditions
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0198
            android.widget.LinearLayout r0 = r7.s
            r0.setVisibility(r4)
            android.widget.ListView r0 = r7.f
            r0.setVisibility(r4)
            com.autonavi.minimap.drive.trafficboard.widget.FilterMenuSubAdapter r0 = r7.d
            int r1 = r7.G
            java.lang.Object r1 = r9.get(r1)
            com.autonavi.bundle.entity.infolite.internal.Condition r1 = (com.autonavi.bundle.entity.infolite.internal.Condition) r1
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r1 = r1.subConditions
            r0.setData(r1)
            com.autonavi.minimap.drive.trafficboard.widget.FilterMenuSubAdapter r0 = r7.d
            int r1 = r7.H
            r0.setSelection(r1)
            r0 = 1
            goto L_0x0199
        L_0x0198:
            r0 = 0
        L_0x0199:
            if (r0 != 0) goto L_0x01c1
            java.util.Iterator r9 = r9.iterator()
        L_0x019f:
            boolean r0 = r9.hasNext()
            if (r0 == 0) goto L_0x01c1
            java.lang.Object r0 = r9.next()
            com.autonavi.bundle.entity.infolite.internal.Condition r0 = (com.autonavi.bundle.entity.infolite.internal.Condition) r0
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r1 = r0.subConditions
            if (r1 == 0) goto L_0x019f
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r0 = r0.subConditions
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x019f
            android.widget.LinearLayout r9 = r7.s
            r9.setVisibility(r4)
            android.widget.ListView r9 = r7.f
            r9.setVisibility(r4)
        L_0x01c1:
            com.autonavi.minimap.drive.trafficboard.widget.FilterMenuMainAdapter r9 = r7.c
            r9.notifyDataSetChanged()
            com.autonavi.minimap.drive.trafficboard.widget.FilterMenuSubAdapter r9 = r7.d
            r9.notifyDataSetChanged()
            r9 = 0
        L_0x01cc:
            android.widget.TextView[] r0 = r7.h
            int r0 = r0.length
            if (r9 >= r0) goto L_0x01ec
            android.widget.TextView[] r0 = r7.h
            r0 = r0[r9]
            if (r9 != r8) goto L_0x01d9
            r1 = 1
            goto L_0x01da
        L_0x01d9:
            r1 = 0
        L_0x01da:
            r0.setSelected(r1)
            android.widget.LinearLayout[] r0 = r7.i
            r0 = r0[r9]
            if (r9 != r8) goto L_0x01e5
            r1 = 1
            goto L_0x01e6
        L_0x01e5:
            r1 = 0
        L_0x01e6:
            r0.setSelected(r1)
            int r9 = r9 + 1
            goto L_0x01cc
        L_0x01ec:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.djz.a(int, boolean):void");
    }

    private void a(Condition condition) {
        if (!TextUtils.isEmpty(condition.checkedValue) && condition.subConditions != null) {
            int i2 = 0;
            while (i2 < condition.subConditions.size()) {
                Condition condition2 = condition.subConditions.get(i2);
                if (TextUtils.isEmpty(condition2.value) || !condition2.value.equals(condition.checkedValue) || !(condition2.subConditions == null || condition2.subConditions.size() == 0)) {
                    if (condition2.subConditions != null && condition2.subConditions.size() > 0) {
                        int i3 = 0;
                        while (true) {
                            if (i3 >= condition2.subConditions.size()) {
                                break;
                            }
                            Condition condition3 = condition2.subConditions.get(i3);
                            if (!TextUtils.isEmpty(condition3.value) && condition3.value.equals(condition.checkedValue)) {
                                this.G = i2;
                                this.H = i3;
                                break;
                            }
                            i3++;
                        }
                    }
                    i2++;
                } else {
                    this.G = i2;
                    return;
                }
            }
        }
    }

    public final void a() {
        if (this.b != null && this.b.isShowing()) {
            if (!(this.e == null || this.f == null)) {
                this.e.setVisibility(4);
                this.f.setVisibility(4);
            }
            this.b.dismiss();
        }
    }

    public static /* synthetic */ String a(djz djz, Condition condition) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < djz.k.size(); i2++) {
            String stringBuffer2 = stringBuffer.toString();
            if (djz.q == i2 || TextUtils.isEmpty(djz.k.get(i2).checkedValue)) {
                stringBuffer.append(condition.checkedValue);
                if (i2 != djz.k.size() - 1) {
                    stringBuffer.append("+");
                }
            } else if (TextUtils.isEmpty(stringBuffer2) || !stringBuffer2.contains(djz.k.get(i2).checkedValue)) {
                stringBuffer.append(djz.k.get(i2).checkedValue);
                if (i2 != djz.k.size() - 1) {
                    stringBuffer.append("+");
                }
            }
        }
        return stringBuffer.toString();
    }
}
