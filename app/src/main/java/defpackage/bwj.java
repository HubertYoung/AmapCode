package defpackage;

import com.autonavi.map.search.comment.model.MyCommentedListResponse;

/* renamed from: bwj reason: default package */
/* compiled from: MyCommentActionCreator */
public final class bwj {
    public static bwa a(final int i) {
        return new bwa() {
            public final String a() {
                return "changeTab";
            }

            public final Object b() {
                return Integer.valueOf(i);
            }
        };
    }

    public static bwa a(final MyCommentedListResponse myCommentedListResponse, final boolean z) {
        return new bwa() {
            public final String a() {
                return "loadCommentedList";
            }

            public final Object b() {
                return new a(myCommentedListResponse, z);
            }
        };
    }
}
