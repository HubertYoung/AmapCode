package com.autonavi.minimap.drive.navi.navitts.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import java.io.File;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class NVPackageSavingDlgFragment extends DriveBasePage<dgd> implements Callback, TextWatcher, OnClickListener, Transparent {
    public int a;
    public int b;
    public File c;
    public String d;
    public File e;
    public EditText f;
    public ImageButton g;
    public TextView h;
    public TextView i;
    public Handler j = new Handler(this);
    public View k;
    /* access modifiers changed from: private */
    public Lock l = new ReentrantLock();
    /* access modifiers changed from: private */
    public final Condition m = this.l.newCondition();
    /* access modifiers changed from: private */
    public Lock n = new ReentrantLock();
    /* access modifiers changed from: private */
    public final Condition o = this.n.newCondition();
    private Lock p = new ReentrantLock();
    private final Condition q = this.p.newCondition();
    /* access modifiers changed from: private */
    public String r;

    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.nv_package_saving_dlg_fragment);
        requestScreenOrientation(1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:73:0x013e, code lost:
        r5.c = new java.io.File(r5.r);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x014d, code lost:
        if (r5.c.exists() == false) goto L_0x013e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x014f, code lost:
        r5.j.sendEmptyMessage(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0154, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r6) {
        /*
            r5 = this;
            int r6 = r6.getId()
            int r0 = com.autonavi.minimap.R.id.btn_clean
            if (r6 != r0) goto L_0x0017
            android.widget.EditText r6 = r5.f
            java.lang.String r0 = ""
            r6.setText(r0)
            android.widget.ImageButton r6 = r5.g
            r0 = 8
            r6.setVisibility(r0)
            return
        L_0x0017:
            int r0 = com.autonavi.minimap.R.id.input_package_name
            r1 = 1
            if (r6 != r0) goto L_0x0022
            android.widget.EditText r6 = r5.f
            r6.setSelectAllOnFocus(r1)
            return
        L_0x0022:
            int r0 = com.autonavi.minimap.R.id.decision_cancel
            if (r6 != r0) goto L_0x0075
            int r6 = r5.a
            r0 = 2
            switch(r6) {
                case 0: goto L_0x0050;
                case 1: goto L_0x002d;
                default: goto L_0x002c;
            }
        L_0x002c:
            return
        L_0x002d:
            int r6 = r5.b
            if (r6 != r0) goto L_0x0035
            r5.finish()
            return
        L_0x0035:
            int r6 = r5.b
            if (r6 != r1) goto L_0x0155
            java.io.File r6 = r5.c
            java.io.File r6 = r6.getParentFile()
            com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment$5 r0 = new com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment$5
            r0.<init>(r6)
            defpackage.ahl.a(r0)
            com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment$4 r6 = new com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment$4
            r6.<init>()
            defpackage.ahl.a(r6)
            return
        L_0x0050:
            int r6 = r5.b
            if (r6 != r0) goto L_0x0058
            r5.finish()
            return
        L_0x0058:
            int r6 = r5.b
            if (r6 == r1) goto L_0x0060
            int r6 = r5.b
            if (r6 != 0) goto L_0x0155
        L_0x0060:
            com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment$6 r6 = new com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment$6
            r6.<init>()
            defpackage.ahl.a(r6)
            r5.finish()
            com.autonavi.common.Page$ResultType r6 = com.autonavi.common.Page.ResultType.OK
            r0 = 0
            r5.setResult(r6, r0)
            a()
            return
        L_0x0075:
            int r0 = com.autonavi.minimap.R.id.decision_confirm
            if (r6 != r0) goto L_0x0155
            android.widget.EditText r6 = r5.f
            android.text.Editable r6 = r6.getText()
            java.lang.String r6 = r6.toString()
            java.lang.String r6 = r6.trim()
            r5.r = r6
            java.io.File r6 = r5.c
            boolean r6 = r6.exists()
            r0 = 0
            if (r6 != 0) goto L_0x0095
            com.autonavi.minimap.drive.navi.navitts.NVUtil$SavingNewPackageName r6 = com.autonavi.minimap.drive.navi.navitts.NVUtil.SavingNewPackageName.SNPN_PACKAGE_NAME_NOT_FOUND
            goto L_0x00dd
        L_0x0095:
            java.lang.String r6 = r5.r
            java.lang.String r1 = r5.d
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x00a2
            com.autonavi.minimap.drive.navi.navitts.NVUtil$SavingNewPackageName r6 = com.autonavi.minimap.drive.navi.navitts.NVUtil.SavingNewPackageName.SNPN_PACKAGE_NAME_NOT_CHANGED
            goto L_0x00dd
        L_0x00a2:
            java.io.File r6 = r5.c
            java.io.File r6 = r6.getParentFile()
            boolean r1 = r6.exists()
            if (r1 != 0) goto L_0x00bd
            boolean r6 = r6.mkdir()
            if (r6 != 0) goto L_0x00ba
            java.lang.String r6 = "应用目录出错，请清除数据后，重新安装应用。"
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r6)
        L_0x00ba:
            com.autonavi.minimap.drive.navi.navitts.NVUtil$SavingNewPackageName r6 = com.autonavi.minimap.drive.navi.navitts.NVUtil.SavingNewPackageName.SNPN_PACKAGE_NAME_NOT_FOUND
            goto L_0x00dd
        L_0x00bd:
            java.io.File[] r6 = r6.listFiles()
            if (r6 == 0) goto L_0x00db
            int r1 = r6.length
            r2 = 0
        L_0x00c5:
            if (r2 >= r1) goto L_0x00db
            java.lang.String r3 = r5.r
            r4 = r6[r2]
            java.lang.String r4 = r4.getName()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x00d8
            com.autonavi.minimap.drive.navi.navitts.NVUtil$SavingNewPackageName r6 = com.autonavi.minimap.drive.navi.navitts.NVUtil.SavingNewPackageName.SNPN_PACKAGE_NAME_ALREADY_EXISTS
            goto L_0x00dd
        L_0x00d8:
            int r2 = r2 + 1
            goto L_0x00c5
        L_0x00db:
            com.autonavi.minimap.drive.navi.navitts.NVUtil$SavingNewPackageName r6 = com.autonavi.minimap.drive.navi.navitts.NVUtil.SavingNewPackageName.SNPN_RENAME_CURRENT_PACKAGE_NAME
        L_0x00dd:
            int[] r1 = com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment.AnonymousClass7.a
            int r6 = r6.ordinal()
            r6 = r1[r6]
            switch(r6) {
                case 1: goto L_0x013e;
                case 2: goto L_0x0138;
                case 3: goto L_0x0131;
                case 4: goto L_0x00e9;
                default: goto L_0x00e8;
            }
        L_0x00e8:
            goto L_0x0155
        L_0x00e9:
            java.lang.String r6 = r5.r
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 == 0) goto L_0x00f8
            java.lang.String r6 = "名称不能为空"
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r6)
            return
        L_0x00f8:
            java.lang.String r6 = r5.r
            int r6 = r6.length()
            r0 = 10
            if (r6 <= r0) goto L_0x0109
            java.lang.String r6 = "名称最多10个字符"
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r6)
            return
        L_0x0109:
            int r6 = r5.a
            switch(r6) {
                case 0: goto L_0x0120;
                case 1: goto L_0x010f;
                default: goto L_0x010e;
            }
        L_0x010e:
            goto L_0x0155
        L_0x010f:
            java.io.File r6 = r5.c
            java.lang.String r0 = r5.d
            r5.a(r6, r0)
            java.io.File r6 = r5.c
            java.io.File r6 = r6.getParentFile()
            r5.a(r6)
            goto L_0x0155
        L_0x0120:
            java.io.File r6 = r5.c
            java.lang.String r0 = "__anc_voices"
            r5.a(r6, r0)
            java.io.File r6 = r5.c
            java.io.File r6 = r6.getParentFile()
            r5.a(r6)
            return
        L_0x0131:
            java.lang.String r6 = "已经有这个语音了，换个名字吧。"
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r6)
            return
        L_0x0138:
            android.os.Handler r6 = r5.j
            r6.sendEmptyMessage(r0)
            return
        L_0x013e:
            java.io.File r6 = new java.io.File
            java.lang.String r1 = r5.r
            r6.<init>(r1)
            r5.c = r6
            java.io.File r6 = r5.c
            boolean r6 = r6.exists()
            if (r6 == 0) goto L_0x013e
            android.os.Handler r6 = r5.j
            r6.sendEmptyMessage(r0)
            return
        L_0x0155:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment.onClick(android.view.View):void");
    }

    public void onTextChanged(final CharSequence charSequence, int i2, int i3, int i4) {
        this.f.removeTextChangedListener(this);
        this.j.post(new Runnable() {
            public final void run() {
                String charSequence = charSequence.toString();
                String a2 = Pattern.compile("[/\\:*?<>!！？@#$￥%……&()^/~*|\"\n\t]").matcher(charSequence).replaceAll("");
                if (!TextUtils.isEmpty(a2) && !TextUtils.isEmpty(charSequence) && !a2.equals(charSequence)) {
                    NVPackageSavingDlgFragment.this.f.setText(a2);
                    NVPackageSavingDlgFragment.this.f.setSelection(a2.length());
                }
            }
        });
        this.f.addTextChangedListener(this);
    }

    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(this.f.getText().toString().trim())) {
            this.g.setVisibility(0);
        } else {
            this.g.setVisibility(8);
        }
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                finish();
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("bundle_key_voice_package_name", this.r);
                pageBundle.putInt("bundle_key_work_mode", this.a);
                pageBundle.putInt("bundle_key_user_action", 0);
                setResult(ResultType.OK, pageBundle);
                a();
                break;
            case 1:
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
                if (inputMethodManager != null) {
                    this.f.setFocusableInTouchMode(true);
                    this.f.requestFocus();
                    this.f.setSelection(this.f.getText().toString().trim().length());
                    this.f.setImeOptions(268435456);
                    inputMethodManager.showSoftInput(this.f, 0);
                    break;
                }
                break;
            case 2:
                finish();
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putString("bundle_key_voice_package_name", this.r);
                pageBundle2.putInt("bundle_key_work_mode", this.a);
                pageBundle2.putInt("bundle_key_user_action", 1);
                setResult(ResultType.OK, pageBundle2);
                break;
            default:
                return false;
        }
        return true;
    }

    public static String a(int i2) {
        int length = String.valueOf(i2).length();
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < 2 - length; i3++) {
            sb.append("0");
        }
        sb.append(String.valueOf(i2));
        return sb.toString();
    }

    private void a(final File file, final String str) {
        ahl.a(new a() {
            public final Object doBackground() throws Exception {
                NVPackageSavingDlgFragment.this.n.lock();
                while (file.exists() && str.equals(file.getName())) {
                    try {
                        NVPackageSavingDlgFragment.this.o.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Throwable th) {
                        NVPackageSavingDlgFragment.this.n.unlock();
                        throw th;
                    }
                }
                NVPackageSavingDlgFragment.this.j.sendEmptyMessage(0);
                NVPackageSavingDlgFragment.this.n.unlock();
                return null;
            }
        });
    }

    private void a(final File file) {
        ahl.a(new a() {
            public final Object doBackground() throws Exception {
                NVPackageSavingDlgFragment.this.n.lock();
                if (file.exists() || file.mkdir()) {
                    File f = NVPackageSavingDlgFragment.this.c;
                    StringBuilder sb = new StringBuilder();
                    sb.append(file.getPath());
                    sb.append("/");
                    sb.append(NVPackageSavingDlgFragment.this.r);
                    if (f.renameTo(new File(sb.toString()))) {
                        NVPackageSavingDlgFragment.this.o.signal();
                    }
                    NVPackageSavingDlgFragment.this.n.unlock();
                    return null;
                }
                ToastHelper.showLongToast("应用目录出错，请清除数据后，重新安装应用。");
                return null;
            }
        });
    }

    private static void a() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.a().b(DirType.DRIVE_VOICE));
        sb.append("/autonaviautonavi/data/voice");
        File file = new File(sb.toString());
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2 != null && file2.exists() && file2.getName().startsWith("__")) {
                        dgu.a(file2, false);
                        if (!file2.delete()) {
                            return;
                        }
                    }
                }
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dgd(this);
    }
}
