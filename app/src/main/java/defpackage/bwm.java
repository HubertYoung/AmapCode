package defpackage;

import android.text.TextUtils;
import com.autonavi.map.search.comment.common.adapter.GroupList;
import com.autonavi.map.search.comment.model.MyCommentGoldBanner;
import com.autonavi.map.search.comment.model.MyCommentedListResponse.Item;
import com.autonavi.map.search.comment.model.MyCommentingListResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* renamed from: bwm reason: default package */
/* compiled from: MyCommentState */
public final class bwm extends bwe {
    public int a = 0;
    public MyCommentGoldBanner b = new MyCommentGoldBanner();
    public MyCommentGoldBanner c = new MyCommentGoldBanner();
    public List<GroupList<String, Item>> d = new ArrayList();
    public List<MyCommentingListResponse.Item> e = new ArrayList();
    public List<MyCommentingListResponse.Item> f = new ArrayList();
    public String g = "";
    public String h = "";
    public int i = 0;
    public int j = 0;
    public boolean k = false;
    public boolean l = false;
    public boolean m = false;
    public boolean n = false;
    public boolean o = false;

    /* renamed from: bwm$a */
    /* compiled from: MyCommentState */
    static class a extends defpackage.bwe.a<bwm> {
        private bwm b;

        public a(bwm bwm) {
            super(bwm);
        }

        /* access modifiers changed from: 0000 */
        public final bwm a() {
            if (this.b == null) {
                this.b = (bwm) ((bwm) this.a).clone();
            }
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public final a a(int i) {
            if (!(i == 0 || i == 1)) {
                a("currentTab");
            }
            if (((bwm) this.a).a == i) {
                return this;
            }
            a().a = i;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a a(String str, boolean z) {
            Item a = a(((bwm) this.a).d, str);
            if (!(a == null || a.showAllComment == z)) {
                a(a().d, str).showAllComment = z;
            }
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a b(String str) {
            Item a = a(((bwm) this.a).d, str);
            if (a != null) {
                GroupList<String, Item> b2 = b(a().d, a(a.createdTime));
                if (b2 != null) {
                    b2.remove(a);
                    if (b2.size() == 0) {
                        a().d.remove(b2);
                    }
                }
            }
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a b() {
            if (((bwm) this.a).l) {
                return this;
            }
            a().l = true;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a c() {
            if (((bwm) this.a).m) {
                return this;
            }
            a().m = true;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a a(List<Item> list, int i) {
            if (list == null) {
                list = new ArrayList<>();
            }
            if (i < 0) {
                a("currentPage");
            }
            if (i == ((bwm) this.a).i) {
                return this;
            }
            bwm a = a();
            a.i = i;
            for (Item next : list) {
                String a2 = a(next.createdTime);
                GroupList<String, Item> b2 = b(a.d, a2);
                if (b2 == null) {
                    b2 = new GroupList<>(a2);
                    a.d.add(b2);
                }
                b2.add(next);
            }
            return this;
        }

        private static Item a(List<GroupList<String, Item>> list, String str) {
            for (GroupList next : list) {
                int i = 0;
                while (true) {
                    if (i < next.size()) {
                        if (TextUtils.equals(((Item) next.get(i)).id, str)) {
                            return (Item) next.get(i);
                        }
                        i++;
                    }
                }
            }
            return null;
        }

        private static GroupList<String, Item> b(List<GroupList<String, Item>> list, String str) {
            for (GroupList<String, Item> next : list) {
                if (((String) next.getGroupObj()).equals(str)) {
                    return next;
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public final a a(List<MyCommentingListResponse.Item> list) {
            if (list == null) {
                list = new ArrayList<>();
            }
            if (((bwm) this.a).e == list) {
                return this;
            }
            a().e = new ArrayList(list);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a b(List<MyCommentingListResponse.Item> list) {
            if (list == null) {
                list = new ArrayList<>();
            }
            if (((bwm) this.a).f == list) {
                return this;
            }
            a().f = new ArrayList(list);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a b(int i) {
            if (i < 0) {
                a("totalPage");
            }
            if (((bwm) this.a).j == i) {
                return this;
            }
            a().j = i;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a c(String str) {
            if (TextUtils.equals(((bwm) this.a).g, str)) {
                return this;
            }
            a().g = str;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a d(String str) {
            if (TextUtils.equals(((bwm) this.a).h, str)) {
                return this;
            }
            a().h = str;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a a(MyCommentGoldBanner myCommentGoldBanner) {
            if (myCommentGoldBanner == null) {
                myCommentGoldBanner = new MyCommentGoldBanner();
            }
            if (((bwm) this.a).b == myCommentGoldBanner) {
                return this;
            }
            a().b = myCommentGoldBanner;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a b(MyCommentGoldBanner myCommentGoldBanner) {
            if (myCommentGoldBanner == null) {
                myCommentGoldBanner = new MyCommentGoldBanner();
            }
            if (((bwm) this.a).c == myCommentGoldBanner) {
                return this;
            }
            a().c = myCommentGoldBanner;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a a(boolean z) {
            if (((bwm) this.a).n == z) {
                return this;
            }
            a().n = z;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a b(boolean z) {
            if (((bwm) this.a).o == z) {
                return this;
            }
            a().o = z;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final a c(boolean z) {
            if (((bwm) this.a).k == z) {
                return this;
            }
            a().k = z;
            return this;
        }

        private static String a(long j) {
            return new SimpleDateFormat("M月d日").format(new Date(j));
        }

        public final bwm d() {
            if (this.b == null) {
                return (bwm) this.a;
            }
            return this.b;
        }
    }

    public bwm(boolean z, int i2) {
        this.k = z;
        this.a = i2;
    }

    public final Object clone() {
        bwm bwm = (bwm) super.clone();
        if (bwm != null) {
            bwm.f = new ArrayList(bwm.f);
            bwm.e = new ArrayList(bwm.e);
            bwm.d = new ArrayList(bwm.d);
        }
        return bwm;
    }
}
