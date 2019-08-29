package com.alibaba.baichuan.android.auth;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import com.alibaba.baichuan.android.trade.utils.i;
import com.alipay.mobile.common.share.widget.ResUtils;

public class AlibcAuthActivity extends Activity implements OnClickListener {
    static int a;
    /* access modifiers changed from: private */
    public boolean b = false;

    class a implements e {
        com.alibaba.baichuan.android.auth.AlibcAuth.a a;

        private a() {
            this.a = (com.alibaba.baichuan.android.auth.AlibcAuth.a) AlibcAuth.a.get(Integer.valueOf(AlibcAuthActivity.a));
        }

        public void a() {
            this.a.e.a();
            AlibcAuth.a.remove(Integer.valueOf(AlibcAuthActivity.a));
            AlibcAuthActivity.this.finish();
        }

        public void a(String str, String str2) {
            if (TextUtils.equals("FAIL_SYS_ACCESS_TOKEN_STOP_SERVICE", str)) {
                this.a.e.a(str, str2);
                AlibcAuth.a.remove(Integer.valueOf(AlibcAuthActivity.a));
                return;
            }
            AlibcAuthActivity.this.b = true;
            AlibcAuthActivity.this.a();
            c();
        }

        public void b() {
            this.a.e.b();
            AlibcAuth.a.remove(Integer.valueOf(AlibcAuthActivity.a));
            AlibcAuthActivity.this.finish();
        }

        public void c() {
            this.a.e.c();
        }
    }

    class b implements e {
        com.alibaba.baichuan.android.auth.AlibcAuth.a a;

        private b() {
            this.a = (com.alibaba.baichuan.android.auth.AlibcAuth.a) AlibcAuth.a.get(Integer.valueOf(AlibcAuthActivity.a));
        }

        public void a() {
            AlibcAuthActivity.this.b = false;
            AlibcAuthActivity.this.a();
        }

        public void a(String str, String str2) {
            AlibcAuthActivity.this.b = true;
            AlibcAuthActivity.this.a();
            c();
        }

        public void b() {
            this.a.e.b();
            AlibcAuth.a.remove(Integer.valueOf(AlibcAuthActivity.a));
            AlibcAuthActivity.this.finish();
        }

        public void c() {
            this.a.e.c();
        }
    }

    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v10, types: [java.lang.String] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r7 = this;
            boolean r0 = r7.isFinishing()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.util.Map r0 = com.alibaba.baichuan.android.auth.AlibcAuth.a
            int r1 = a
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.Object r0 = r0.get(r1)
            com.alibaba.baichuan.android.auth.AlibcAuth$a r0 = (com.alibaba.baichuan.android.auth.AlibcAuth.a) r0
            android.content.pm.PackageManager r1 = r7.getPackageManager()
            android.content.pm.ApplicationInfo r2 = r7.getApplicationInfo()
            java.lang.CharSequence r1 = r2.loadLabel(r1)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "id"
            java.lang.String r3 = "open_auth_title"
            int r2 = com.alibaba.baichuan.android.trade.utils.i.a(r7, r2, r3)
            android.view.View r2 = r7.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            java.lang.String r3 = "id"
            java.lang.String r4 = "open_auth_desc"
            int r3 = com.alibaba.baichuan.android.trade.utils.i.a(r7, r3, r4)
            android.view.View r3 = r7.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            java.lang.String r4 = "id"
            java.lang.String r5 = "open_auth_btn_grant"
            int r4 = com.alibaba.baichuan.android.trade.utils.i.a(r7, r4, r5)
            android.view.View r4 = r7.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            java.lang.String r5 = "id"
            java.lang.String r6 = "open_auth_rl"
            int r5 = com.alibaba.baichuan.android.trade.utils.i.a(r7, r5, r6)
            android.view.View r5 = r7.findViewById(r5)
            r6 = 0
            r5.setVisibility(r6)
            r7.c()
            boolean r5 = r7.b
            if (r5 == 0) goto L_0x007a
            java.lang.String r0 = "淘宝授权失败"
            r2.setText(r0)
            java.lang.String r0 = "请确认网络环境后再试试？"
            r3.setText(r0)
            java.lang.String r0 = "重试"
        L_0x0075:
            r4.setText(r0)
            goto L_0x014c
        L_0x007a:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r1)
            java.lang.String r1 = "将获取"
            r5.append(r1)
            java.lang.String r1 = r5.toString()
            r2.setText(r1)
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r2 = r0.a
            if (r2 == 0) goto L_0x00e2
            java.lang.String r0 = r0.a
            java.util.Set r0 = com.alibaba.baichuan.android.auth.AlibcAuthHint.getApiAndHint(r0)
            java.util.Iterator r0 = r0.iterator()
        L_0x00a2:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x012c
            java.lang.Object r2 = r0.next()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r5 = com.alibaba.baichuan.android.auth.AlibcAuthHint.getHintInfo(r2)
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 == 0) goto L_0x00cd
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "访问您淘宝账号信息的权限("
            r5.<init>(r6)
            r5.append(r2)
            java.lang.String r2 = ")"
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            goto L_0x00de
        L_0x00cd:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r5)
            java.lang.String r5 = "\n"
            r2.append(r5)
            java.lang.String r2 = r2.toString()
        L_0x00de:
            r1.append(r2)
            goto L_0x00a2
        L_0x00e2:
            java.util.Set r2 = r0.c
            if (r2 == 0) goto L_0x012c
            java.util.Set r0 = r0.c
            java.util.Iterator r0 = r0.iterator()
        L_0x00ec:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x012c
            java.lang.Object r2 = r0.next()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r5 = com.alibaba.baichuan.android.auth.AlibcAuthHint.getHintInfo(r2)
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 == 0) goto L_0x0117
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "访问您淘宝账号信息的权限("
            r5.<init>(r6)
            r5.append(r2)
            java.lang.String r2 = ")"
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            goto L_0x0128
        L_0x0117:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r5)
            java.lang.String r5 = "\n"
            r2.append(r5)
            java.lang.String r2 = r2.toString()
        L_0x0128:
            r1.append(r2)
            goto L_0x00ec
        L_0x012c:
            java.lang.String r0 = "\n"
            int r0 = r1.lastIndexOf(r0)
            int r2 = r1.length()
            r1.delete(r0, r2)
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 == 0) goto L_0x0142
            java.lang.String r1 = "访问您淘宝账号信息的权限"
        L_0x0142:
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            r3.setText(r1)
            java.lang.String r0 = "确认授权"
            goto L_0x0075
        L_0x014c:
            r4.setOnClickListener(r7)
            java.lang.String r0 = "id"
            java.lang.String r1 = "open_auth_btn_cancel"
            int r0 = com.alibaba.baichuan.android.trade.utils.i.a(r7, r0, r1)
            android.view.View r0 = r7.findViewById(r0)
            r0.setOnClickListener(r7)
            java.lang.String r0 = "id"
            java.lang.String r1 = "open_auth_btn_close"
            int r0 = com.alibaba.baichuan.android.trade.utils.i.a(r7, r0, r1)
            android.view.View r0 = r7.findViewById(r0)
            r0.setOnClickListener(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.auth.AlibcAuthActivity.a():void");
    }

    private void b() {
        findViewById(i.a(this, "id", "com_alibc_auth_progressbar")).setVisibility(0);
    }

    private void c() {
        findViewById(i.a(this, "id", "com_alibc_auth_progressbar")).setVisibility(8);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0085, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r5) {
        /*
            r4 = this;
            int r5 = r5.getId()
            java.lang.String r0 = "id"
            java.lang.String r1 = "open_auth_btn_cancel"
            int r0 = com.alibaba.baichuan.android.trade.utils.i.a(r4, r0, r1)
            if (r5 == r0) goto L_0x008a
            java.lang.String r0 = "id"
            java.lang.String r1 = "open_auth_btn_close"
            int r0 = com.alibaba.baichuan.android.trade.utils.i.a(r4, r0, r1)
            if (r5 != r0) goto L_0x0019
            goto L_0x008a
        L_0x0019:
            java.lang.String r0 = "id"
            java.lang.String r1 = "open_auth_btn_grant"
            int r0 = com.alibaba.baichuan.android.trade.utils.i.a(r4, r0, r1)
            if (r5 != r0) goto L_0x0089
            monitor-enter(r4)
            java.util.Map r5 = com.alibaba.baichuan.android.auth.AlibcAuth.a     // Catch:{ all -> 0x0086 }
            int r0 = a     // Catch:{ all -> 0x0086 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x0086 }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ all -> 0x0086 }
            com.alibaba.baichuan.android.auth.AlibcAuth$a r5 = (com.alibaba.baichuan.android.auth.AlibcAuth.a) r5     // Catch:{ all -> 0x0086 }
            if (r5 != 0) goto L_0x0039
            r4.finish()     // Catch:{ all -> 0x0086 }
            monitor-exit(r4)     // Catch:{ all -> 0x0086 }
            return
        L_0x0039:
            r4.b()     // Catch:{ all -> 0x0086 }
            java.lang.String r0 = "id"
            java.lang.String r1 = "open_auth_rl"
            int r0 = com.alibaba.baichuan.android.trade.utils.i.a(r4, r0, r1)     // Catch:{ all -> 0x0086 }
            android.view.View r0 = r4.findViewById(r0)     // Catch:{ all -> 0x0086 }
            r1 = 8
            r0.setVisibility(r1)     // Catch:{ all -> 0x0086 }
            java.lang.String r0 = r5.a     // Catch:{ all -> 0x0086 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0086 }
            r1 = 0
            r2 = 0
            if (r0 != 0) goto L_0x007a
            java.lang.String r0 = r5.a     // Catch:{ all -> 0x0086 }
            java.util.Set r0 = com.alibaba.baichuan.android.auth.AlibcAuthHint.getApiAndHint(r0)     // Catch:{ all -> 0x0086 }
            if (r0 == 0) goto L_0x006f
            int r3 = r0.size()     // Catch:{ all -> 0x0086 }
            if (r3 > 0) goto L_0x0066
            goto L_0x006f
        L_0x0066:
            com.alibaba.baichuan.android.auth.AlibcAuthActivity$a r5 = new com.alibaba.baichuan.android.auth.AlibcAuthActivity$a     // Catch:{ all -> 0x0086 }
            r5.<init>()     // Catch:{ all -> 0x0086 }
            com.alibaba.baichuan.android.auth.AlibcAuth.a(r0, r5, r2)     // Catch:{ all -> 0x0086 }
            goto L_0x0084
        L_0x006f:
            java.lang.String r5 = r5.a     // Catch:{ all -> 0x0086 }
            com.alibaba.baichuan.android.auth.AlibcAuthActivity$b r0 = new com.alibaba.baichuan.android.auth.AlibcAuthActivity$b     // Catch:{ all -> 0x0086 }
            r0.<init>()     // Catch:{ all -> 0x0086 }
            com.alibaba.baichuan.android.auth.AlibcAuth.a(r5, r0, r2, r2)     // Catch:{ all -> 0x0086 }
            goto L_0x0084
        L_0x007a:
            java.util.Set r5 = r5.c     // Catch:{ all -> 0x0086 }
            com.alibaba.baichuan.android.auth.AlibcAuthActivity$a r0 = new com.alibaba.baichuan.android.auth.AlibcAuthActivity$a     // Catch:{ all -> 0x0086 }
            r0.<init>()     // Catch:{ all -> 0x0086 }
            com.alibaba.baichuan.android.auth.AlibcAuth.a(r5, r0, r2)     // Catch:{ all -> 0x0086 }
        L_0x0084:
            monitor-exit(r4)     // Catch:{ all -> 0x0086 }
            return
        L_0x0086:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0086 }
            throw r5
        L_0x0089:
            return
        L_0x008a:
            monitor-enter(r4)
            java.util.Map r5 = com.alibaba.baichuan.android.auth.AlibcAuth.a     // Catch:{ all -> 0x00b0 }
            int r0 = a     // Catch:{ all -> 0x00b0 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x00b0 }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ all -> 0x00b0 }
            com.alibaba.baichuan.android.auth.AlibcAuth$a r5 = (com.alibaba.baichuan.android.auth.AlibcAuth.a) r5     // Catch:{ all -> 0x00b0 }
            if (r5 == 0) goto L_0x00ab
            com.alibaba.baichuan.android.auth.e r5 = r5.e     // Catch:{ all -> 0x00b0 }
            r5.b()     // Catch:{ all -> 0x00b0 }
            java.util.Map r5 = com.alibaba.baichuan.android.auth.AlibcAuth.a     // Catch:{ all -> 0x00b0 }
            int r0 = a     // Catch:{ all -> 0x00b0 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x00b0 }
            r5.remove(r0)     // Catch:{ all -> 0x00b0 }
        L_0x00ab:
            r4.finish()     // Catch:{ all -> 0x00b0 }
            monitor-exit(r4)     // Catch:{ all -> 0x00b0 }
            return
        L_0x00b0:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00b0 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.auth.AlibcAuthActivity.onClick(android.view.View):void");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(i.a(this, ResUtils.LAYOUT, "com_alibc_auth_actiivty"));
        a = getIntent().getExtras().getInt("authId");
        com.alibaba.baichuan.android.auth.AlibcAuth.a aVar = (com.alibaba.baichuan.android.auth.AlibcAuth.a) AlibcAuth.a.get(Integer.valueOf(a));
        if (aVar.c == null) {
            AlibcAuth.a(aVar.a, new b(), false, false);
        } else {
            a();
        }
    }
}
