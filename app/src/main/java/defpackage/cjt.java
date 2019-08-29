package defpackage;

import android.content.res.Resources;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.entity.GroupInfo;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.minimap.agroup.entity.MemberInfo.IdentityType;
import com.autonavi.minimap.agroup.entity.TeamInfo;
import com.autonavi.minimap.bundle.agroup.api.IDataService;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: cjt reason: default package */
/* compiled from: DataService */
public final class cjt implements IDataService {
    /* access modifiers changed from: private */
    public CopyOnWriteArrayList<com.autonavi.minimap.bundle.agroup.api.IDataService.a> a;
    /* access modifiers changed from: private */
    public GroupInfo b;
    private Comparator<MemberInfo> c;

    /* renamed from: cjt$a */
    /* compiled from: DataService */
    public static class a {
        public static cjt a = new cjt(0);
    }

    /* synthetic */ cjt(byte b2) {
        this();
    }

    public static cjt a() {
        return a.a;
    }

    private cjt() {
        this.a = new CopyOnWriteArrayList<>();
        this.b = new GroupInfo();
        this.c = new cjv();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(com.autonavi.minimap.bundle.agroup.api.IDataService.a r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 == 0) goto L_0x0016
            java.util.concurrent.CopyOnWriteArrayList<com.autonavi.minimap.bundle.agroup.api.IDataService$a> r0 = r1.a     // Catch:{ all -> 0x0013 }
            boolean r0 = r0.contains(r2)     // Catch:{ all -> 0x0013 }
            if (r0 == 0) goto L_0x000c
            goto L_0x0016
        L_0x000c:
            java.util.concurrent.CopyOnWriteArrayList<com.autonavi.minimap.bundle.agroup.api.IDataService$a> r0 = r1.a     // Catch:{ all -> 0x0013 }
            r0.add(r2)     // Catch:{ all -> 0x0013 }
            monitor-exit(r1)
            return
        L_0x0013:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        L_0x0016:
            monitor-exit(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cjt.a(com.autonavi.minimap.bundle.agroup.api.IDataService$a):void");
    }

    public final synchronized void b(com.autonavi.minimap.bundle.agroup.api.IDataService.a aVar) {
        if (aVar != null) {
            this.a.remove(aVar);
        }
    }

    public final synchronized void a(GroupInfo groupInfo) {
        if (groupInfo != null) {
            a(groupInfo.getTeamStamp(), groupInfo.getMemberStamp());
            a(groupInfo.getTeamInfo(), groupInfo.getTeamStatus());
            a(groupInfo.getMemberInfoList());
            a(groupInfo.getSuperGroupMemberCount());
            this.b.setCode(groupInfo.getCode());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(com.autonavi.minimap.agroup.entity.TeamInfo r2, final com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            com.autonavi.minimap.agroup.entity.GroupInfo r0 = r1.b     // Catch:{ all -> 0x003c }
            com.autonavi.minimap.bundle.agroup.api.IDataService$TeamStatus r0 = r0.getTeamStatus()     // Catch:{ all -> 0x003c }
            if (r3 == 0) goto L_0x0018
            if (r3 == r0) goto L_0x0018
            com.autonavi.minimap.agroup.entity.GroupInfo r0 = r1.b     // Catch:{ all -> 0x003c }
            r0.setTeamStatus(r3)     // Catch:{ all -> 0x003c }
            cjt$1 r0 = new cjt$1     // Catch:{ all -> 0x003c }
            r0.<init>(r3)     // Catch:{ all -> 0x003c }
            defpackage.aho.a(r0)     // Catch:{ all -> 0x003c }
        L_0x0018:
            if (r2 == 0) goto L_0x003a
            java.lang.String r3 = r2.teamId     // Catch:{ all -> 0x003c }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x003c }
            if (r3 != 0) goto L_0x003a
            java.lang.String r3 = r2.teamNumber     // Catch:{ all -> 0x003c }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x003c }
            if (r3 == 0) goto L_0x002b
            goto L_0x003a
        L_0x002b:
            com.autonavi.minimap.agroup.entity.GroupInfo r3 = r1.b     // Catch:{ all -> 0x003c }
            r3.setTeamInfo(r2)     // Catch:{ all -> 0x003c }
            cjt$2 r2 = new cjt$2     // Catch:{ all -> 0x003c }
            r2.<init>()     // Catch:{ all -> 0x003c }
            defpackage.aho.a(r2)     // Catch:{ all -> 0x003c }
            monitor-exit(r1)
            return
        L_0x003a:
            monitor-exit(r1)
            return
        L_0x003c:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cjt.a(com.autonavi.minimap.agroup.entity.TeamInfo, com.autonavi.minimap.bundle.agroup.api.IDataService$TeamStatus):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0055, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.lang.String r3, java.lang.String r4, final com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            com.autonavi.minimap.agroup.entity.GroupInfo r0 = r2.b     // Catch:{ all -> 0x0056 }
            com.autonavi.minimap.agroup.entity.TeamInfo r0 = r0.getTeamInfo()     // Catch:{ all -> 0x0056 }
            if (r3 == 0) goto L_0x0054
            if (r4 == 0) goto L_0x0054
            if (r5 != 0) goto L_0x000e
            goto L_0x0054
        L_0x000e:
            if (r0 == 0) goto L_0x0037
            java.lang.String r1 = r0.teamId     // Catch:{ all -> 0x0056 }
            boolean r1 = r3.equals(r1)     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x0037
            java.lang.String r0 = r0.teamNumber     // Catch:{ all -> 0x0056 }
            boolean r0 = r4.equals(r0)     // Catch:{ all -> 0x0056 }
            if (r0 == 0) goto L_0x0037
            com.autonavi.minimap.agroup.entity.GroupInfo r3 = r2.b     // Catch:{ all -> 0x0056 }
            com.autonavi.minimap.bundle.agroup.api.IDataService$TeamStatus r3 = r3.getTeamStatus()     // Catch:{ all -> 0x0056 }
            if (r5 == r3) goto L_0x0052
            com.autonavi.minimap.agroup.entity.GroupInfo r3 = r2.b     // Catch:{ all -> 0x0056 }
            r3.setTeamStatus(r5)     // Catch:{ all -> 0x0056 }
            cjt$3 r3 = new cjt$3     // Catch:{ all -> 0x0056 }
            r3.<init>(r5)     // Catch:{ all -> 0x0056 }
            defpackage.aho.a(r3)     // Catch:{ all -> 0x0056 }
            monitor-exit(r2)
            return
        L_0x0037:
            com.autonavi.minimap.agroup.entity.TeamInfo r0 = new com.autonavi.minimap.agroup.entity.TeamInfo     // Catch:{ all -> 0x0056 }
            r0.<init>()     // Catch:{ all -> 0x0056 }
            r0.teamId = r3     // Catch:{ all -> 0x0056 }
            r0.teamNumber = r4     // Catch:{ all -> 0x0056 }
            com.autonavi.minimap.agroup.entity.GroupInfo r3 = r2.b     // Catch:{ all -> 0x0056 }
            r3.setTeamInfo(r0)     // Catch:{ all -> 0x0056 }
            com.autonavi.minimap.agroup.entity.GroupInfo r3 = r2.b     // Catch:{ all -> 0x0056 }
            r3.setTeamStatus(r5)     // Catch:{ all -> 0x0056 }
            cjt$4 r3 = new cjt$4     // Catch:{ all -> 0x0056 }
            r3.<init>(r5)     // Catch:{ all -> 0x0056 }
            defpackage.aho.a(r3)     // Catch:{ all -> 0x0056 }
        L_0x0052:
            monitor-exit(r2)
            return
        L_0x0054:
            monitor-exit(r2)
            return
        L_0x0056:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cjt.a(java.lang.String, java.lang.String, com.autonavi.minimap.bundle.agroup.api.IDataService$TeamStatus):void");
    }

    public final synchronized void a(String str, String str2) {
        String teamStamp = this.b.getTeamStamp();
        if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, teamStamp)) {
            this.b.setTeamStamp(str);
        }
        String memberStamp = this.b.getMemberStamp();
        if (!TextUtils.isEmpty(str2) && !TextUtils.equals(str2, memberStamp)) {
            this.b.setMemberStamp(str2);
        }
    }

    private static String a(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (str.indexOf("@180h_180w_1e_1c") < 0) {
            str = str.replace(".jpg", ".jpg@180h_180w_1e_1c");
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0062, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.util.List<com.autonavi.minimap.agroup.entity.MemberInfo> r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 == 0) goto L_0x0061
            boolean r0 = r5.isEmpty()     // Catch:{ all -> 0x005e }
            if (r0 == 0) goto L_0x000a
            goto L_0x0061
        L_0x000a:
            esb r0 = defpackage.esb.a.a     // Catch:{ all -> 0x005e }
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r1 = com.autonavi.bundle.account.api.IAccountService.class
            esc r0 = r0.a(r1)     // Catch:{ all -> 0x005e }
            com.autonavi.bundle.account.api.IAccountService r0 = (com.autonavi.bundle.account.api.IAccountService) r0     // Catch:{ all -> 0x005e }
            if (r0 != 0) goto L_0x001b
            java.lang.String r0 = ""
            goto L_0x0026
        L_0x001b:
            ant r0 = r0.e()     // Catch:{ all -> 0x005e }
            if (r0 != 0) goto L_0x0024
            java.lang.String r0 = ""
            goto L_0x0026
        L_0x0024:
            java.lang.String r0 = r0.a     // Catch:{ all -> 0x005e }
        L_0x0026:
            java.lang.String r0 = defpackage.cjw.b(r0)     // Catch:{ all -> 0x005e }
            com.autonavi.minimap.agroup.entity.GroupInfo r1 = r4.b     // Catch:{ all -> 0x005e }
            com.autonavi.minimap.agroup.entity.TeamInfo r1 = r1.getTeamInfo()     // Catch:{ all -> 0x005e }
            r2 = 0
            if (r1 == 0) goto L_0x0035
            java.lang.String r2 = r1.teamLeaderUid     // Catch:{ all -> 0x005e }
        L_0x0035:
            r1 = 0
            java.lang.Object r1 = r5.get(r1)     // Catch:{ all -> 0x005e }
            com.autonavi.minimap.agroup.entity.MemberInfo r1 = (com.autonavi.minimap.agroup.entity.MemberInfo) r1     // Catch:{ all -> 0x005e }
            com.autonavi.minimap.agroup.entity.MemberInfo$CreateType r1 = r1.getCreateType()     // Catch:{ all -> 0x005e }
            com.autonavi.minimap.agroup.entity.MemberInfo$CreateType r3 = com.autonavi.minimap.agroup.entity.MemberInfo.CreateType.ALL_INFO     // Catch:{ all -> 0x005e }
            if (r1 != r3) goto L_0x0051
            r4.a(r5, r0, r2)     // Catch:{ all -> 0x005e }
            cjt$5 r5 = new cjt$5     // Catch:{ all -> 0x005e }
            r5.<init>()     // Catch:{ all -> 0x005e }
            defpackage.aho.a(r5)     // Catch:{ all -> 0x005e }
            monitor-exit(r4)
            return
        L_0x0051:
            r4.b(r5, r0, r2)     // Catch:{ all -> 0x005e }
            cjt$6 r5 = new cjt$6     // Catch:{ all -> 0x005e }
            r5.<init>(r1)     // Catch:{ all -> 0x005e }
            defpackage.aho.a(r5)     // Catch:{ all -> 0x005e }
            monitor-exit(r4)
            return
        L_0x005e:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        L_0x0061:
            monitor-exit(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cjt.a(java.util.List):void");
    }

    private synchronized void a(List<MemberInfo> list, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (MemberInfo next : list) {
            if (next != null) {
                next.imgUrl = a(next.imgUrl);
                if (!TextUtils.isEmpty(str2) && TextUtils.equals(str2, next.uid)) {
                    next.setIdentityType(IdentityType.LEADER);
                }
                if (!TextUtils.isEmpty(str) && TextUtils.equals(str, next.uid)) {
                    next.setIdentityType(IdentityType.MYSELF);
                    next.isSelf = true;
                    arrayList.add(next);
                } else if (next.online) {
                    arrayList2.add(next);
                } else {
                    arrayList3.add(next);
                }
            }
        }
        arrayList.addAll(arrayList2);
        arrayList.addAll(arrayList3);
        this.b.setMemberInfoList(arrayList);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d5, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b(java.util.List<com.autonavi.minimap.agroup.entity.MemberInfo> r16, java.lang.String r17, java.lang.String r18) {
        /*
            r15 = this;
            r1 = r15
            monitor-enter(r15)
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x00d6 }
            r2.<init>()     // Catch:{ all -> 0x00d6 }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x00d6 }
            r3.<init>()     // Catch:{ all -> 0x00d6 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ all -> 0x00d6 }
            r4.<init>()     // Catch:{ all -> 0x00d6 }
            com.autonavi.minimap.agroup.entity.GroupInfo r5 = r1.b     // Catch:{ all -> 0x00d6 }
            java.util.List r5 = r5.getMemberInfoList()     // Catch:{ all -> 0x00d6 }
            if (r5 == 0) goto L_0x00d4
            boolean r6 = r5.isEmpty()     // Catch:{ all -> 0x00d6 }
            if (r6 == 0) goto L_0x0021
            goto L_0x00d4
        L_0x0021:
            int r6 = r5.size()     // Catch:{ all -> 0x00d6 }
            r7 = 0
            r8 = 0
        L_0x0027:
            if (r8 >= r6) goto L_0x00bd
            java.lang.Object r9 = r5.get(r8)     // Catch:{ all -> 0x00d6 }
            com.autonavi.minimap.agroup.entity.MemberInfo r9 = (com.autonavi.minimap.agroup.entity.MemberInfo) r9     // Catch:{ all -> 0x00d6 }
            if (r9 == 0) goto L_0x00b5
            java.util.Iterator r10 = r16.iterator()     // Catch:{ all -> 0x00d6 }
        L_0x0035:
            boolean r11 = r10.hasNext()     // Catch:{ all -> 0x00d6 }
            r12 = 1
            if (r11 == 0) goto L_0x00a2
            java.lang.Object r11 = r10.next()     // Catch:{ all -> 0x00d6 }
            com.autonavi.minimap.agroup.entity.MemberInfo r11 = (com.autonavi.minimap.agroup.entity.MemberInfo) r11     // Catch:{ all -> 0x00d6 }
            if (r11 == 0) goto L_0x009d
            java.lang.String r13 = r9.uid     // Catch:{ all -> 0x00d6 }
            java.lang.String r14 = r11.uid     // Catch:{ all -> 0x00d6 }
            boolean r13 = android.text.TextUtils.equals(r13, r14)     // Catch:{ all -> 0x00d6 }
            if (r13 == 0) goto L_0x009d
            java.lang.String r10 = r11.imgUrl     // Catch:{ all -> 0x00d6 }
            java.lang.String r10 = a(r10)     // Catch:{ all -> 0x00d6 }
            r11.imgUrl = r10     // Catch:{ all -> 0x00d6 }
            boolean r10 = android.text.TextUtils.isEmpty(r18)     // Catch:{ all -> 0x00d6 }
            if (r10 != 0) goto L_0x006c
            java.lang.String r10 = r11.uid     // Catch:{ all -> 0x00d6 }
            r13 = r18
            boolean r10 = android.text.TextUtils.equals(r13, r10)     // Catch:{ all -> 0x00d6 }
            if (r10 == 0) goto L_0x006e
            com.autonavi.minimap.agroup.entity.MemberInfo$IdentityType r10 = com.autonavi.minimap.agroup.entity.MemberInfo.IdentityType.LEADER     // Catch:{ all -> 0x00d6 }
            r11.setIdentityType(r10)     // Catch:{ all -> 0x00d6 }
            goto L_0x006e
        L_0x006c:
            r13 = r18
        L_0x006e:
            boolean r10 = android.text.TextUtils.isEmpty(r17)     // Catch:{ all -> 0x00d6 }
            if (r10 != 0) goto L_0x008c
            java.lang.String r10 = r11.uid     // Catch:{ all -> 0x00d6 }
            r14 = r17
            boolean r10 = android.text.TextUtils.equals(r14, r10)     // Catch:{ all -> 0x00d6 }
            if (r10 == 0) goto L_0x008e
            com.autonavi.minimap.agroup.entity.MemberInfo$IdentityType r10 = com.autonavi.minimap.agroup.entity.MemberInfo.IdentityType.MYSELF     // Catch:{ all -> 0x00d6 }
            r11.setIdentityType(r10)     // Catch:{ all -> 0x00d6 }
            r11.isSelf = r12     // Catch:{ all -> 0x00d6 }
            r9.copyFrom(r11)     // Catch:{ all -> 0x00d6 }
            r2.add(r9)     // Catch:{ all -> 0x00d6 }
            goto L_0x00a7
        L_0x008c:
            r14 = r17
        L_0x008e:
            r9.copyFrom(r11)     // Catch:{ all -> 0x00d6 }
            boolean r10 = r9.online     // Catch:{ all -> 0x00d6 }
            if (r10 == 0) goto L_0x0099
            r3.add(r9)     // Catch:{ all -> 0x00d6 }
            goto L_0x00a7
        L_0x0099:
            r4.add(r9)     // Catch:{ all -> 0x00d6 }
            goto L_0x00a7
        L_0x009d:
            r14 = r17
            r13 = r18
            goto L_0x0035
        L_0x00a2:
            r14 = r17
            r13 = r18
            r12 = 0
        L_0x00a7:
            if (r12 != 0) goto L_0x00b9
            boolean r10 = r9.online     // Catch:{ all -> 0x00d6 }
            if (r10 == 0) goto L_0x00b1
            r3.add(r9)     // Catch:{ all -> 0x00d6 }
            goto L_0x00b9
        L_0x00b1:
            r4.add(r9)     // Catch:{ all -> 0x00d6 }
            goto L_0x00b9
        L_0x00b5:
            r14 = r17
            r13 = r18
        L_0x00b9:
            int r8 = r8 + 1
            goto L_0x0027
        L_0x00bd:
            java.util.Comparator<com.autonavi.minimap.agroup.entity.MemberInfo> r5 = r1.c     // Catch:{ all -> 0x00d6 }
            java.util.Collections.sort(r3, r5)     // Catch:{ all -> 0x00d6 }
            java.util.Comparator<com.autonavi.minimap.agroup.entity.MemberInfo> r5 = r1.c     // Catch:{ all -> 0x00d6 }
            java.util.Collections.sort(r4, r5)     // Catch:{ all -> 0x00d6 }
            r2.addAll(r3)     // Catch:{ all -> 0x00d6 }
            r2.addAll(r4)     // Catch:{ all -> 0x00d6 }
            com.autonavi.minimap.agroup.entity.GroupInfo r3 = r1.b     // Catch:{ all -> 0x00d6 }
            r3.setMemberInfoList(r2)     // Catch:{ all -> 0x00d6 }
            monitor-exit(r15)
            return
        L_0x00d4:
            monitor-exit(r15)
            return
        L_0x00d6:
            r0 = move-exception
            r2 = r0
            monitor-exit(r15)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cjt.b(java.util.List, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0058, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String r5 = defpackage.cjw.b(r5)     // Catch:{ all -> 0x005d }
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x005d }
            if (r0 != 0) goto L_0x005b
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x005d }
            if (r6 == 0) goto L_0x0018
            boolean r6 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x005d }
            if (r6 == 0) goto L_0x0018
            goto L_0x005b
        L_0x0018:
            com.autonavi.minimap.agroup.entity.GroupInfo r6 = r4.b     // Catch:{ all -> 0x005d }
            java.util.List r6 = r6.getMemberInfoList()     // Catch:{ all -> 0x005d }
            if (r6 == 0) goto L_0x0059
            boolean r0 = r6.isEmpty()     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0027
            goto L_0x0059
        L_0x0027:
            int r0 = r6.size()     // Catch:{ all -> 0x005d }
            r1 = 0
        L_0x002c:
            if (r1 >= r0) goto L_0x0057
            java.lang.Object r2 = r6.get(r1)     // Catch:{ all -> 0x005d }
            com.autonavi.minimap.agroup.entity.MemberInfo r2 = (com.autonavi.minimap.agroup.entity.MemberInfo) r2     // Catch:{ all -> 0x005d }
            if (r2 == 0) goto L_0x0054
            java.lang.String r3 = r2.uid     // Catch:{ all -> 0x005d }
            boolean r3 = r5.equals(r3)     // Catch:{ all -> 0x005d }
            if (r3 == 0) goto L_0x0054
            java.lang.String r5 = a(r7)     // Catch:{ all -> 0x005d }
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x005d }
            if (r6 != 0) goto L_0x0057
            java.lang.String r6 = r2.imgUrl     // Catch:{ all -> 0x005d }
            boolean r6 = r5.equals(r6)     // Catch:{ all -> 0x005d }
            if (r6 != 0) goto L_0x0057
            r2.imgUrl = r5     // Catch:{ all -> 0x005d }
            monitor-exit(r4)
            return
        L_0x0054:
            int r1 = r1 + 1
            goto L_0x002c
        L_0x0057:
            monitor-exit(r4)
            return
        L_0x0059:
            monitor-exit(r4)
            return
        L_0x005b:
            monitor-exit(r4)
            return
        L_0x005d:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cjt.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public final synchronized void a(TeamStatus teamStatus) {
        TeamStatus teamStatus2 = this.b.getTeamStatus();
        this.b.clear();
        if (teamStatus != null && ((teamStatus == TeamStatus.STATUS_TEAM_DISMISS || teamStatus == TeamStatus.STATUS_USER_NOT_JOIN_IN_TEAM) && !teamStatus.equals(teamStatus2))) {
            this.b.setTeamStatus(teamStatus);
        }
        aho.a(new Runnable() {
            public final void run() {
                Iterator it = cjt.this.a.iterator();
                while (it.hasNext()) {
                    com.autonavi.minimap.bundle.agroup.api.IDataService.a aVar = (com.autonavi.minimap.bundle.agroup.api.IDataService.a) it.next();
                    if (aVar != null) {
                        aVar.onTeamStatusChanged(cjt.this.b.getTeamStatus());
                    }
                }
            }
        });
    }

    public final void a(int i) {
        if (i != this.b.getSuperGroupMemberCount()) {
            aho.a(new Runnable() {
                public final void run() {
                    Iterator it = cjt.this.a.iterator();
                    while (it.hasNext()) {
                        com.autonavi.minimap.bundle.agroup.api.IDataService.a aVar = (com.autonavi.minimap.bundle.agroup.api.IDataService.a) it.next();
                        if (aVar != null) {
                            aVar.onSuperGroupInfoChanged();
                        }
                    }
                }
            });
        }
        this.b.setSuperGroupMemberCount(i);
    }

    public final synchronized GroupInfo b() {
        try {
        }
        return this.b.clone();
    }

    public final synchronized TeamInfo c() {
        try {
            TeamInfo teamInfo = this.b.getTeamInfo();
            if (teamInfo == null) {
                return null;
            }
            return teamInfo.clone();
        }
    }

    public final synchronized String d() {
        try {
        }
        return this.b.getTeamStamp();
    }

    public final synchronized String e() {
        try {
        }
        return this.b.getMemberStamp();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0030, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.util.List<com.autonavi.minimap.agroup.entity.MemberInfo> f() {
        /*
            r3 = this;
            monitor-enter(r3)
            com.autonavi.minimap.agroup.entity.GroupInfo r0 = r3.b     // Catch:{ all -> 0x0031 }
            java.util.List r0 = r0.getMemberInfoList()     // Catch:{ all -> 0x0031 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0031 }
            r1.<init>()     // Catch:{ all -> 0x0031 }
            if (r0 == 0) goto L_0x002f
            boolean r2 = r0.isEmpty()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x0015
            goto L_0x002f
        L_0x0015:
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0031 }
        L_0x0019:
            boolean r2 = r0.hasNext()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x002d
            java.lang.Object r2 = r0.next()     // Catch:{ all -> 0x0031 }
            com.autonavi.minimap.agroup.entity.MemberInfo r2 = (com.autonavi.minimap.agroup.entity.MemberInfo) r2     // Catch:{ all -> 0x0031 }
            com.autonavi.minimap.agroup.entity.MemberInfo r2 = r2.clone()     // Catch:{ all -> 0x0031 }
            r1.add(r2)     // Catch:{ all -> 0x0031 }
            goto L_0x0019
        L_0x002d:
            monitor-exit(r3)
            return r1
        L_0x002f:
            monitor-exit(r3)
            return r1
        L_0x0031:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cjt.f():java.util.List");
    }

    public final synchronized MemberInfo g() {
        try {
            List<MemberInfo> memberInfoList = this.b.getMemberInfoList();
            if (memberInfoList != null && !memberInfoList.isEmpty()) {
                for (MemberInfo next : memberInfoList) {
                    if (next != null && next.isMyself()) {
                        return next;
                    }
                }
            }
            return null;
        }
    }

    public final synchronized int h() {
        List<MemberInfo> memberInfoList = this.b.getMemberInfoList();
        if (memberInfoList == null) {
            return 0;
        }
        return memberInfoList.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002d, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized int i() {
        /*
            r3 = this;
            monitor-enter(r3)
            com.autonavi.minimap.agroup.entity.GroupInfo r0 = r3.b     // Catch:{ all -> 0x002e }
            java.util.List r0 = r0.getMemberInfoList()     // Catch:{ all -> 0x002e }
            r1 = 0
            if (r0 == 0) goto L_0x002c
            boolean r2 = r0.isEmpty()     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x0011
            goto L_0x002c
        L_0x0011:
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x002e }
        L_0x0015:
            boolean r2 = r0.hasNext()     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x002a
            java.lang.Object r2 = r0.next()     // Catch:{ all -> 0x002e }
            com.autonavi.minimap.agroup.entity.MemberInfo r2 = (com.autonavi.minimap.agroup.entity.MemberInfo) r2     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x0015
            boolean r2 = r2.online     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x0015
            int r1 = r1 + 1
            goto L_0x0015
        L_0x002a:
            monitor-exit(r3)
            return r1
        L_0x002c:
            monitor-exit(r3)
            return r1
        L_0x002e:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cjt.i():int");
    }

    public final int j() {
        return this.b.getSuperGroupMemberCount();
    }

    public final synchronized TeamStatus k() {
        try {
        }
        return this.b.getTeamStatus();
    }

    public final synchronized boolean l() {
        TeamStatus teamStatus = this.b.getTeamStatus();
        if (teamStatus == null) {
            return false;
        }
        if (teamStatus == TeamStatus.STATUS_USER_IN_OTHER_TEAM || teamStatus == TeamStatus.STATUS_USER_IN_TEAM || teamStatus == TeamStatus.STATUS_USER_IN_THIS_TEAM || teamStatus == TeamStatus.STATUS_LEADER_IN_OTHER_TEAM) {
            return true;
        }
        return false;
    }

    public final boolean m() {
        return c() != null;
    }

    public final String b(int i) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (i >= 10000) {
            return String.format(resources.getString(R.string.agroup_ten_thousands_member_count), new Object[]{Integer.valueOf(i / 10000)});
        } else if (i >= 1000) {
            return String.format(resources.getString(R.string.agroup_thousands_member_count), new Object[]{Integer.valueOf(i / 1000)});
        } else {
            return String.format(resources.getString(R.string.agroup_member_count), new Object[]{Integer.valueOf(i)});
        }
    }
}
