package defpackage;

/* renamed from: bwl reason: default package */
/* compiled from: MyCommentReducer */
public final class bwl implements bwd<bwm> {
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ defpackage.bwe a(defpackage.bwe r5, defpackage.bwa r6) {
        /*
            r4 = this;
            bwm r5 = (defpackage.bwm) r5
            java.lang.String r0 = r6.a()
            int r1 = r0.hashCode()
            r2 = 1
            r3 = 0
            switch(r1) {
                case -1784388038: goto L_0x0042;
                case -712791597: goto L_0x0038;
                case 103149417: goto L_0x002e;
                case 330843574: goto L_0x0024;
                case 1231616743: goto L_0x001a;
                case 1455248837: goto L_0x0010;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x004c
        L_0x0010:
            java.lang.String r1 = "changeTab"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 0
            goto L_0x004d
        L_0x001a:
            java.lang.String r1 = "loadCommentingList"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 2
            goto L_0x004d
        L_0x0024:
            java.lang.String r1 = "loadCommentedList"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 1
            goto L_0x004d
        L_0x002e:
            java.lang.String r1 = "login"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 3
            goto L_0x004d
        L_0x0038:
            java.lang.String r1 = "deleteCommented"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 5
            goto L_0x004d
        L_0x0042:
            java.lang.String r1 = "showAllCommented"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004c
            r0 = 4
            goto L_0x004d
        L_0x004c:
            r0 = -1
        L_0x004d:
            switch(r0) {
                case 0: goto L_0x0160;
                case 1: goto L_0x00fd;
                case 2: goto L_0x00a1;
                case 3: goto L_0x0089;
                case 4: goto L_0x0065;
                case 5: goto L_0x0051;
                default: goto L_0x0050;
            }
        L_0x0050:
            return r5
        L_0x0051:
            java.lang.Object r6 = r6.b()
            java.lang.String r6 = (java.lang.String) r6
            bwm$a r0 = new bwm$a
            r0.<init>(r5)
            bwm$a r5 = r0.b(r6)
            bwm r5 = r5.d()
            return r5
        L_0x0065:
            java.lang.Object r6 = r6.b()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r0 = r6.getKey()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r6 = r6.getValue()
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            bwm$a r1 = new bwm$a
            r1.<init>(r5)
            bwm$a r5 = r1.a(r0, r6)
            bwm r5 = r5.d()
            return r5
        L_0x0089:
            java.lang.Object r6 = r6.b()
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            bwm$a r0 = new bwm$a
            r0.<init>(r5)
            bwm$a r5 = r0.c(r6)
            bwm r5 = r5.d()
            return r5
        L_0x00a1:
            java.lang.Object r6 = r6.b()
            bwk$a r6 = (defpackage.bwk.a) r6
            bwm$a r0 = new bwm$a
            r0.<init>(r5)
            T r5 = r6.b
            com.autonavi.map.search.comment.model.MyCommentingListResponse r5 = (com.autonavi.map.search.comment.model.MyCommentingListResponse) r5
            int r5 = r5.showBanner
            if (r5 != r2) goto L_0x00b5
            goto L_0x00b6
        L_0x00b5:
            r2 = 0
        L_0x00b6:
            bwm$a r5 = r0.b(r2)
            T r0 = r6.b
            com.autonavi.map.search.comment.model.MyCommentingListResponse r0 = (com.autonavi.map.search.comment.model.MyCommentingListResponse) r0
            com.autonavi.map.search.comment.model.MyCommentGoldBanner r0 = r0.goldBanner
            bwm$a r5 = r5.b(r0)
            T r0 = r6.b
            com.autonavi.map.search.comment.model.MyCommentingListResponse r0 = (com.autonavi.map.search.comment.model.MyCommentingListResponse) r0
            com.autonavi.map.search.comment.model.MyCommentingListResponse$List r0 = r0.firstComment
            java.lang.String r0 = r0.title
            bwm$a r5 = r5.c(r0)
            T r0 = r6.b
            com.autonavi.map.search.comment.model.MyCommentingListResponse r0 = (com.autonavi.map.search.comment.model.MyCommentingListResponse) r0
            com.autonavi.map.search.comment.model.MyCommentingListResponse$List r0 = r0.firstComment
            java.util.List<com.autonavi.map.search.comment.model.MyCommentingListResponse$Item> r0 = r0.commentingList
            bwm$a r5 = r5.a(r0)
            T r0 = r6.b
            com.autonavi.map.search.comment.model.MyCommentingListResponse r0 = (com.autonavi.map.search.comment.model.MyCommentingListResponse) r0
            com.autonavi.map.search.comment.model.MyCommentingListResponse$List r0 = r0.generalComment
            java.lang.String r0 = r0.title
            bwm$a r5 = r5.d(r0)
            T r6 = r6.b
            com.autonavi.map.search.comment.model.MyCommentingListResponse r6 = (com.autonavi.map.search.comment.model.MyCommentingListResponse) r6
            com.autonavi.map.search.comment.model.MyCommentingListResponse$List r6 = r6.generalComment
            java.util.List<com.autonavi.map.search.comment.model.MyCommentingListResponse$Item> r6 = r6.commentingList
            bwm$a r5 = r5.b(r6)
            bwm$a r5 = r5.c()
            bwm r5 = r5.d()
            return r5
        L_0x00fd:
            java.lang.Object r6 = r6.b()
            bwk$a r6 = (defpackage.bwk.a) r6
            boolean r0 = r6.a
            if (r0 == 0) goto L_0x0124
            bwm$a r0 = new bwm$a
            r0.<init>(r5)
            bwm r5 = r0.a()
            r5.i = r3
            r5.j = r3
            r5.l = r3
            java.util.List r5 = r5.d
            r5.clear()
            bwm r5 = r0.d()
        L_0x0124:
            bwm$a r0 = new bwm$a
            r0.<init>(r5)
            T r1 = r6.b
            com.autonavi.map.search.comment.model.MyCommentedListResponse r1 = (com.autonavi.map.search.comment.model.MyCommentedListResponse) r1
            int r1 = r1.totalPage
            bwm$a r0 = r0.b(r1)
            T r1 = r6.b
            com.autonavi.map.search.comment.model.MyCommentedListResponse r1 = (com.autonavi.map.search.comment.model.MyCommentedListResponse) r1
            int r1 = r1.showBanner
            if (r1 != r2) goto L_0x013c
            r3 = 1
        L_0x013c:
            bwm$a r0 = r0.a(r3)
            T r1 = r6.b
            com.autonavi.map.search.comment.model.MyCommentedListResponse r1 = (com.autonavi.map.search.comment.model.MyCommentedListResponse) r1
            com.autonavi.map.search.comment.model.MyCommentGoldBanner r1 = r1.goldBanner
            bwm$a r0 = r0.a(r1)
            T r6 = r6.b
            com.autonavi.map.search.comment.model.MyCommentedListResponse r6 = (com.autonavi.map.search.comment.model.MyCommentedListResponse) r6
            java.util.List<com.autonavi.map.search.comment.model.MyCommentedListResponse$Item> r6 = r6.commentedList
            int r5 = r5.i
            int r5 = r5 + r2
            bwm$a r5 = r0.a(r6, r5)
            bwm$a r5 = r5.b()
            bwm r5 = r5.d()
            return r5
        L_0x0160:
            java.lang.Object r6 = r6.b()
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            bwm$a r0 = new bwm$a
            r0.<init>(r5)
            bwm$a r5 = r0.a(r6)
            bwm r5 = r5.d()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bwl.a(bwe, bwa):bwe");
    }
}
