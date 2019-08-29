package defpackage;

import android.graphics.Rect;
import com.autonavi.minimap.base.overlay.PointOverlay;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bya reason: default package */
/* compiled from: SearchResultOverLayHelper */
public final class bya {
    private bzh a;

    public bya(bzh bzh) {
        this.a = bzh;
    }

    public final void a(List<bzu> list, bbr bbr, bty bty, bxf bxf) {
        boolean z;
        boolean z2;
        bbr bbr2 = list.get(list.size() - 1).a;
        if (bbr2 != null) {
            boolean z3 = false;
            if (bbr != null && bbr2.a(bty).isEmpty()) {
                for (bzu next : list) {
                    boolean z4 = next != null && next.b == 1;
                    if (next != null) {
                        a(next, z4, false);
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (bbr != null) {
                int i = 0;
                while (i < list.size()) {
                    try {
                        bbr bbr3 = list.get(i).a;
                        if (!bbr3.equals(bbr) && Rect.intersects(a(bbr.a(bty)), bbr3.a(bty))) {
                            a((List<bzu>) arrayList, (List<Boolean>) arrayList2, list.get(i), list.get(i).b == 1);
                        }
                        i++;
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                bbr bbr4 = list.get(i2).a;
                if (!bbr4.equals(bbr)) {
                    int i3 = i2 + 1;
                    while (true) {
                        if (i3 >= list.size()) {
                            break;
                        }
                        bbr bbr5 = list.get(i3).a;
                        if (!bbr5.equals(bbr) && Rect.intersects(a(bbr4.a(bty)), bbr5.a(bty))) {
                            boolean z5 = list.get(i3).b == 1;
                            if (bbr5 == bbr) {
                                a((List<bzu>) arrayList, (List<Boolean>) arrayList2, list.get(i2), z5);
                                break;
                            }
                            a((List<bzu>) arrayList, (List<Boolean>) arrayList2, list.get(i3), z5);
                        }
                        i3++;
                    }
                }
            }
            int size = list.size();
            int size2 = arrayList.size();
            for (int i4 = 0; i4 < size; i4++) {
                bzu bzu = list.get(i4);
                if (bzu != null) {
                    int i5 = 0;
                    while (true) {
                        if (i5 >= size2) {
                            z = true;
                            z2 = true;
                            break;
                        }
                        bzu bzu2 = (bzu) arrayList.get(i5);
                        if (bzu2 != null && bzu != null && bzu2 != null && bzu.equals(bzu2)) {
                            z = ((Boolean) arrayList2.get(i5)).booleanValue();
                            z2 = false;
                            break;
                        }
                        i5++;
                    }
                    a(bzu, z, z2);
                }
            }
            if (!(bxf == null || this.a == null)) {
                int i6 = bxf.c;
                bzh bzh = this.a;
                if (bzh != null) {
                    if (bzh.b != null) {
                        List items = bzh.b.getItems();
                        int size3 = items.size();
                        if (size3 > 0 && i6 >= 0 && i6 < size3) {
                            z3 = Rect.intersects(((bbr) items.get(0)).a(bty), ((bbr) items.get(i6)).a(bty));
                        }
                    }
                }
                if (!z3) {
                    this.a.s();
                }
            }
        }
    }

    private static void a(List<bzu> list, List<Boolean> list2, bzu bzu, boolean z) {
        if (bzu != null) {
            boolean z2 = true;
            for (bzu equals : list) {
                if (equals.equals(bzu)) {
                    z2 = false;
                }
            }
            if (z2) {
                list.add(bzu);
                list2.add(Boolean.valueOf(z));
            }
        }
    }

    private void a(bzu bzu, boolean z, boolean z2) {
        this.a.a(bzu, z, z2);
    }

    public static bbr a(PointOverlay pointOverlay, List<bzu> list, int i) {
        int size = pointOverlay.getSize();
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                list.add(new bzu((bbr) pointOverlay.getItem(i2), i));
            }
            if (pointOverlay.getFocus() != null) {
                return (bbr) pointOverlay.getFocus();
            }
        }
        return null;
    }

    private static Rect a(Rect rect) {
        Rect rect2 = new Rect(rect);
        rect2.top += 8;
        rect2.bottom -= 8;
        rect2.left += 8;
        rect2.right -= 8;
        return rect2;
    }
}
