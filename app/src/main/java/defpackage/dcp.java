package defpackage;

import android.text.TextUtils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.HttpUrlCollector;
import com.amap.bundle.statistics.HttpUrlCollector.SCENE;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.SendToCarShare;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.a;
import com.autonavi.minimap.bundle.share.entity.ShareParam.b;
import com.autonavi.minimap.bundle.share.entity.ShareParam.c;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.bundle.share.entity.WeiboShare;

/* renamed from: dcp reason: default package */
/* compiled from: ShareDirect */
public final class dcp {
    private dco a;
    private dcd b;

    public dcp(dco dco, dcd dcd) {
        this.a = dco;
        ddi.a().b();
        ddi.a().a(new dcd() {
            public final ShareParam getShareDataByType(int i) {
                return null;
            }

            public final void onFinish(int i, int i2) {
                if (i2 == 0) {
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.pubok));
                    return;
                }
                if (i2 == -1) {
                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.puberr));
                }
            }
        });
        ddi.a().a(dcd);
        this.b = dcd;
    }

    public final void a() {
        if (!aaw.c(AMapPageUtil.getAppContext())) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.network_error_msg));
            return;
        }
        int[] iArr = this.a.a.a;
        if (iArr == null || iArr.length == 0 || iArr.length > 1) {
            ddi.a().b();
            return;
        }
        if (this.b != null) {
            int i = iArr[0];
            ShareParam shareDataByType = this.b.getShareDataByType(i);
            if (shareDataByType != null && !TextUtils.isEmpty(shareDataByType.b)) {
                shareDataByType.b = a(shareDataByType.b);
                HttpUrlCollector.a(SCENE.SHARE, shareDataByType.b);
            }
            if (shareDataByType == null) {
                ddi.a().b();
                return;
            }
            a(this.a, shareDataByType, i);
        }
        switch (iArr[0]) {
            case 0:
                if (this.a.c == null) {
                    ddi.a().b();
                    break;
                } else {
                    new dcu(this.a.c).startShare();
                    break;
                }
            case 1:
                if (this.a.d == null) {
                    ddi.a().b();
                    break;
                } else {
                    new dcg(this.a.d).startShare();
                    break;
                }
            case 2:
                if (this.a.i == null) {
                    ddi.a().b();
                    break;
                } else {
                    new SendToCarShare(this.a.i).startShare();
                    break;
                }
            case 3:
                if (this.a.f == null) {
                    ddi.a().b();
                    break;
                } else {
                    new dcv(this.a.f).startShare();
                    break;
                }
            case 4:
                if (this.a.g == null) {
                    ddi.a().b();
                    break;
                } else {
                    new dcv(this.a.g).startShare();
                    break;
                }
            case 5:
                if (this.a.e == null) {
                    ddi.a().b();
                    break;
                } else {
                    new WeiboShare(this.a.e).startShare();
                    break;
                }
            case 8:
                if (this.a.j == null) {
                    ddi.a().b();
                    break;
                } else {
                    new dcl(this.a.j).startShare();
                    break;
                }
            case 9:
                if (this.a.k == null) {
                    ddi.a().b();
                    break;
                } else {
                    new dcm(this.a.k).startShare();
                    break;
                }
            case 10:
                if (this.a.i == null) {
                    ddi.a().b();
                    break;
                } else {
                    new dcn(this.a.i).startShare();
                    break;
                }
            case 11:
                if (this.a.h == null) {
                    ddi.a().b();
                    break;
                } else {
                    new dcf(this.a.h).startShare();
                    break;
                }
            default:
                ddi.a().b();
                break;
        }
        if (this.b != null) {
            this.b.onEntrySelected(iArr[0]);
        }
    }

    private static void a(dco dco, ShareParam shareParam, int i) {
        switch (i) {
            case 0:
                g gVar = new g();
                gVar.h = shareParam.c;
                gVar.i = shareParam.d;
                gVar.f = shareParam.a;
                gVar.g = shareParam.b;
                dco.c = gVar;
                return;
            case 1:
                a aVar = (a) shareParam;
                b bVar = new b();
                bVar.h = shareParam.c;
                bVar.i = shareParam.d;
                bVar.f = aVar.a;
                bVar.g = aVar.b;
                bVar.a = aVar.e;
                dco.d = bVar;
                return;
            case 2:
            case 10:
                c cVar = (c) shareParam;
                f fVar = new f();
                fVar.h = shareParam.c;
                fVar.i = shareParam.d;
                fVar.a = cVar.e;
                fVar.g = cVar.b;
                fVar.f = cVar.a;
                dco.i = fVar;
                return;
            case 3:
                e eVar = (e) shareParam;
                i iVar = new i();
                iVar.h = eVar.c;
                iVar.i = eVar.d;
                iVar.f = eVar.a;
                iVar.g = eVar.b;
                iVar.b = eVar.f;
                iVar.c = eVar.g;
                iVar.a = eVar.e;
                iVar.d = eVar.h;
                dco.f = iVar;
                return;
            case 4:
                e eVar2 = (e) shareParam;
                h hVar = new h();
                hVar.h = eVar2.c;
                hVar.i = eVar2.d;
                hVar.f = eVar2.a;
                hVar.g = eVar2.b;
                hVar.b = eVar2.f;
                hVar.c = eVar2.g;
                hVar.a = eVar2.e;
                hVar.d = eVar2.h;
                dco.g = hVar;
                return;
            case 5:
                f fVar2 = (f) shareParam;
                k kVar = new k();
                kVar.h = shareParam.c;
                kVar.i = shareParam.d;
                kVar.g = fVar2.b;
                kVar.f = fVar2.a;
                kVar.c = fVar2.h;
                kVar.j = fVar2.j;
                kVar.e = fVar2.i;
                kVar.a = fVar2.e;
                kVar.b = fVar2.f;
                dco.e = kVar;
                return;
            case 8:
                b bVar2 = (b) shareParam;
                d dVar = new d();
                dVar.h = bVar2.c;
                dVar.i = bVar2.d;
                dVar.f = bVar2.a;
                dVar.g = bVar2.b;
                dVar.a = bVar2.e;
                dVar.b = bVar2.f;
                dVar.c = bVar2.g;
                dco.j = dVar;
                return;
            case 9:
                b bVar3 = (b) shareParam;
                e eVar3 = new e();
                eVar3.h = bVar3.c;
                eVar3.i = bVar3.d;
                eVar3.f = bVar3.a;
                eVar3.g = bVar3.b;
                eVar3.a = bVar3.e;
                eVar3.b = bVar3.f;
                eVar3.c = bVar3.g;
                dco.k = eVar3;
                return;
            case 11:
                try {
                    dco.h = dcf.a((DingDingParam) shareParam);
                    break;
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    throw new ClassCastException("please specify a valid type by sharetype!!!");
                }
        }
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str) || str.contains("src=app_share")) {
            return str;
        }
        if (!str.contains("wb.amap.com") && !str.contains("wb.testing.amap.com")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(b(str));
        sb.append("&src=app_");
        sb.append(NetworkParam.getDic());
        return sb.toString();
    }

    private static String b(String str) {
        if (str.contains("?")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("?");
        return sb.toString();
    }
}
