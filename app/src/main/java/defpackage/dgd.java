package defpackage;

import android.content.res.Configuration;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.fragment.NVPackageSavingDlgFragment;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;

/* renamed from: dgd reason: default package */
/* compiled from: NVPackageSavingPresenter */
public final class dgd extends sw<NVPackageSavingDlgFragment, dgc> {
    public dgd(NVPackageSavingDlgFragment nVPackageSavingDlgFragment) {
        super(nVPackageSavingDlgFragment);
    }

    public final void onPageCreated() {
        NVPackageSavingDlgFragment nVPackageSavingDlgFragment = (NVPackageSavingDlgFragment) this.mPage;
        PageBundle arguments = nVPackageSavingDlgFragment.getArguments();
        if (arguments == null) {
            ToastHelper.showLongToast("语音包找不到了，请重新录制");
            nVPackageSavingDlgFragment.finish();
            return;
        }
        nVPackageSavingDlgFragment.d = arguments.getString("bundle_key_voice_package_name");
        nVPackageSavingDlgFragment.c = (File) arguments.getObject("bundle_key_voice_package_obj");
        int i = 0;
        nVPackageSavingDlgFragment.a = arguments.getInt("bundle_key_work_mode", 0);
        nVPackageSavingDlgFragment.b = arguments.getInt("bundle_key_dialog_mode", 0);
        nVPackageSavingDlgFragment.e = (File) arguments.getObject("bundle_key_backup_voice_package_obj");
        if (!nVPackageSavingDlgFragment.c.exists() || TextUtils.isEmpty(nVPackageSavingDlgFragment.d)) {
            ToastHelper.showLongToast("语音包找不到了，请重新录制");
            nVPackageSavingDlgFragment.finish();
            return;
        }
        nVPackageSavingDlgFragment.f = (EditText) nVPackageSavingDlgFragment.getContentView().findViewById(R.id.input_package_name);
        nVPackageSavingDlgFragment.j.sendEmptyMessageDelayed(1, 300);
        nVPackageSavingDlgFragment.g = (ImageButton) nVPackageSavingDlgFragment.getContentView().findViewById(R.id.btn_clean);
        if (nVPackageSavingDlgFragment.g != null) {
            nVPackageSavingDlgFragment.g.setOnClickListener(nVPackageSavingDlgFragment);
        }
        if (nVPackageSavingDlgFragment.f != null) {
            nVPackageSavingDlgFragment.f.setOnClickListener(nVPackageSavingDlgFragment);
            nVPackageSavingDlgFragment.f.addTextChangedListener(nVPackageSavingDlgFragment);
            if (!TextUtils.isEmpty(nVPackageSavingDlgFragment.d)) {
                nVPackageSavingDlgFragment.f.setText(nVPackageSavingDlgFragment.d);
            }
            switch (nVPackageSavingDlgFragment.a) {
                case 0:
                    EditText editText = nVPackageSavingDlgFragment.f;
                    StringBuilder sb = new StringBuilder();
                    sb.append(FilePathHelper.DEFAULT_SAVED_VOICE_PACKAGE_NAME);
                    StringBuilder sb2 = new StringBuilder(sb);
                    sb2.append(NVPackageSavingDlgFragment.a(1));
                    String sb3 = sb2.toString();
                    File parentFile = nVPackageSavingDlgFragment.c.getParentFile();
                    if (parentFile.exists()) {
                        File[] listFiles = parentFile.listFiles();
                        if (!(listFiles == null || listFiles.length == 0)) {
                            int i2 = 2;
                            while (i < listFiles.length) {
                                if (sb3.equals(listFiles[i].getName())) {
                                    StringBuilder sb4 = new StringBuilder(sb);
                                    sb4.append(NVPackageSavingDlgFragment.a(i2));
                                    sb3 = sb4.toString();
                                    i2++;
                                    i = -1;
                                }
                                i++;
                            }
                        }
                    } else if (!parentFile.mkdirs()) {
                        ToastHelper.showLongToast("应用目录出错，请清除数据后，重新安装应用。");
                    }
                    editText.setText(sb3);
                    break;
                case 1:
                    nVPackageSavingDlgFragment.f.setText(nVPackageSavingDlgFragment.d);
                    break;
            }
        }
        nVPackageSavingDlgFragment.h = (TextView) nVPackageSavingDlgFragment.getContentView().findViewById(R.id.decision_cancel);
        String str = "";
        switch (nVPackageSavingDlgFragment.b) {
            case 0:
            case 1:
                str = "不保存";
                break;
            case 2:
                str = "取消";
                break;
        }
        nVPackageSavingDlgFragment.h.setText(str);
        nVPackageSavingDlgFragment.h.setOnClickListener(nVPackageSavingDlgFragment);
        nVPackageSavingDlgFragment.i = (TextView) nVPackageSavingDlgFragment.getContentView().findViewById(R.id.decision_confirm);
        nVPackageSavingDlgFragment.i.setOnClickListener(nVPackageSavingDlgFragment);
        nVPackageSavingDlgFragment.k = nVPackageSavingDlgFragment.getContentView();
    }

    public final void onStart() {
        ((NVPackageSavingDlgFragment) this.mPage).setSoftInputMode(16);
        KeyboardUtil.showKeyboard(((NVPackageSavingDlgFragment) this.mPage).f);
    }

    public final void onStop() {
        KeyboardUtil.forceHideKeyboard(((NVPackageSavingDlgFragment) this.mPage).f);
    }

    public final void onDestroy() {
        NVPackageSavingDlgFragment nVPackageSavingDlgFragment = (NVPackageSavingDlgFragment) this.mPage;
        if (nVPackageSavingDlgFragment.f != null) {
            nVPackageSavingDlgFragment.f.setOnClickListener(null);
            nVPackageSavingDlgFragment.f.removeTextChangedListener(nVPackageSavingDlgFragment);
        }
        if (nVPackageSavingDlgFragment.g != null) {
            nVPackageSavingDlgFragment.g.setOnClickListener(null);
        }
        if (nVPackageSavingDlgFragment.h != null) {
            nVPackageSavingDlgFragment.h.setOnClickListener(null);
        }
        if (nVPackageSavingDlgFragment.i != null) {
            nVPackageSavingDlgFragment.i.setOnClickListener(null);
        }
        if (nVPackageSavingDlgFragment.k != null) {
            nVPackageSavingDlgFragment.k.setOnTouchListener(null);
        }
        dhd.i();
        if (nVPackageSavingDlgFragment.j != null) {
            nVPackageSavingDlgFragment.j.removeCallbacksAndMessages(null);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        NVPackageSavingDlgFragment nVPackageSavingDlgFragment = (NVPackageSavingDlgFragment) this.mPage;
        nVPackageSavingDlgFragment.f.setSelection(nVPackageSavingDlgFragment.f.getSelectionEnd());
    }

    public final /* synthetic */ su a() {
        return new dgc(this);
    }
}
