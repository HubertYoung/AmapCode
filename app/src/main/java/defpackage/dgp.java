package defpackage;

import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback.a;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.storage.StorageFragment;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/* renamed from: dgp reason: default package */
/* compiled from: StoragePresenter */
public final class dgp extends sw<StorageFragment, dgo> implements a {
    public dgs c;
    public String d;
    public List<dgn> e;
    public a f;
    private boolean g;
    private dgn h;

    public final /* bridge */ /* synthetic */ su a() {
        return null;
    }

    private dgp(StorageFragment storageFragment, dgs dgs) {
        super(storageFragment);
        this.d = null;
        this.e = null;
        this.g = false;
        this.b = new dgo(this);
        this.c = dgs;
    }

    public static dgp a(StorageFragment storageFragment, dgs dgs) {
        return new dgp(storageFragment, dgs);
    }

    public final void onPageCreated() {
        StorageFragment storageFragment = (StorageFragment) this.mPage;
        storageFragment.e = storageFragment.getContentView();
        ((TextView) storageFragment.e.findViewById(R.id.title_text_name)).setText(R.string.offline_storage_title);
        storageFragment.e.findViewById(R.id.title_btn_left).setOnClickListener(storageFragment);
        storageFragment.a = (LinearLayout) storageFragment.e.findViewById(R.id.storagelist);
        d();
    }

    public final void onStart() {
        StorageFragment storageFragment = (StorageFragment) this.mPage;
        if (FileUtil.enumExternalSDcardInfo(storageFragment.d.getApplicationContext()).size() == 1) {
            ToastHelper.showToast(storageFragment.d.getString(R.string.switch_sdcard_only_one));
        }
    }

    public final void onDestroy() {
        StorageFragment storageFragment = (StorageFragment) this.mPage;
        storageFragment.c();
        storageFragment.b = null;
        storageFragment.c = null;
        this.c = null;
        this.f = null;
    }

    private void d() {
        this.d = PathManager.a().b(DirType.DRIVE_VOICE);
        this.e = dgr.a();
        if (this.e.isEmpty() && this.c != null) {
            this.c.a(2);
        }
        if (this.c != null) {
            this.c.a(this.e, this.d);
        }
    }

    public final void a(dgn dgn) {
        this.h = dgn;
        this.f = dgq.a((a) this).a(this.d, dgn.a, Arrays.asList(new String[]{"/autonavi/900_960", "/autonavi/data/voice"}));
    }

    public final void a(int i) {
        if (this.c != null) {
            this.c.b(i);
        }
    }

    public final void b(int i) {
        if (this.c != null) {
            switch (i) {
                case 1:
                    return;
                case 2:
                    this.c.b();
                    return;
                case 3:
                    this.c.c();
                    this.c.b(0);
                    return;
                case 4:
                    String str = this.h.a;
                    if (!TextUtils.isEmpty(str) && str.length() > 2 && new File(str).isDirectory()) {
                        if (!TextUtils.isEmpty(this.d) && !str.equals(this.d)) {
                            this.d = str;
                            if (!TextUtils.isEmpty(this.d) && this.d.indexOf("Android/") > 0) {
                                this.d = this.d.substring(0, this.d.indexOf("Android/"));
                            }
                        }
                        PathManager a = PathManager.a();
                        DirType dirType = DirType.DRIVE_VOICE;
                        StringBuilder sb = new StringBuilder();
                        sb.append(File.separator);
                        sb.append("autonavi");
                        sb.append(DirType.DRIVE_VOICE.getPath());
                        a.a(dirType, str.replace(sb.toString(), ""));
                        dgu.b();
                        if (this.c != null) {
                            this.c.a(1);
                        }
                    }
                    return;
                case 5:
                    this.g = true;
                    d();
                    this.c.c();
                    return;
                case 6:
                    this.c.d();
                    break;
            }
        }
    }

    public final void c(int i) {
        if (this.c != null) {
            switch (i) {
                case 1:
                    this.c.c(this.h);
                    return;
                case 2:
                    this.c.c();
                    this.c.a(this.h);
                    return;
                case 3:
                    this.c.c();
                    this.c.a(3);
                    break;
            }
        }
    }

    public final void c() {
        if (this.g && this.c != null) {
            this.c.a(ResultType.OK);
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        c();
        return StorageFragment.a();
    }
}
