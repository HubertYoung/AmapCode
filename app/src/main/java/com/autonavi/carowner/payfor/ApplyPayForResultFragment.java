package com.autonavi.carowner.payfor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import java.io.File;

public class ApplyPayForResultFragment extends DriveBasePage<bhl> implements OnClickListener {
    public PayforNaviData a;
    public String b = null;
    public View c;
    public View d;
    public TextView e;
    public View f;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.activities_apply_pay_for_result_dialog);
    }

    public void onClick(View view) {
        if (view == this.c) {
            a();
        } else if (view == this.d) {
            finishAllFragmentsWithoutRoot();
            StringBuilder sb = new StringBuilder();
            sb.append(ConfigerHelper.getInstance().getActivitiesUrl());
            sb.append("/activity/payError/index.html");
            aja aja = new aja(sb.toString());
            aja.b = new ajf();
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a((bid) this, aja);
            }
        } else {
            if (view == this.f) {
                ahn.b().execute(new Runnable() {
                    public final void run() {
                        final File file = new File(FileUtil.getCacheDir(), "shared_image_weibo_poi.jpg");
                        if (!file.exists()) {
                            try {
                                ahd.a(ApplyPayForResultFragment.this.getContext(), R.drawable.shared_image_weibo_poi, file);
                            } catch (Exception unused) {
                            }
                        }
                        aho.a(new Runnable() {
                            public final void run() {
                                dct dct = new dct(0);
                                dct.f = true;
                                dct.d = true;
                                dct.e = true;
                                ApplyPayForResultFragment applyPayForResultFragment = ApplyPayForResultFragment.this;
                                Context context = ApplyPayForResultFragment.this.getContext();
                                String a2 = ApplyPayForResultFragment.this.b;
                                StringBuilder sb = new StringBuilder();
                                sb.append(ApplyPayForResultFragment.this.a.moneyMaypayed);
                                ApplyPayForResultFragment.a(applyPayForResultFragment, context, dct, a2, sb.toString(), file.getAbsolutePath());
                            }
                        });
                    }
                });
            }
        }
    }

    public final void a() {
        setResult(ResultType.OK, (PageBundle) null);
        finish();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bhl(this);
    }

    static /* synthetic */ void a(ApplyPayForResultFragment applyPayForResultFragment, Context context, dct dct, String str, String str2, String str3) {
        final Resources resources = AMapAppGlobal.getApplication().getApplicationContext().getResources();
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb != null) {
            final String str4 = str2;
            final String str5 = str3;
            final String str6 = str;
            final Context context2 = context;
            AnonymousClass2 r1 = new dcd() {
                public final ShareParam getShareDataByType(int i) {
                    switch (i) {
                        case 3:
                            e eVar = new e(0);
                            eVar.f = resources.getString(R.string.sns_share_title);
                            StringBuilder sb = new StringBuilder();
                            sb.append(resources.getString(R.string.shareutil_wechat_slogan1));
                            sb.append(str4);
                            sb.append(resources.getString(R.string.shareutil_wechat_slogan2));
                            eVar.a = sb.toString();
                            eVar.g = BitmapFactory.decodeResource(context2.getResources(), R.drawable.pay_for_share_icon);
                            eVar.b = str6;
                            eVar.e = 0;
                            return eVar;
                        case 4:
                            e eVar2 = new e(1);
                            eVar2.f = resources.getString(R.string.sns_share_title);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(resources.getString(R.string.shareutil_friend_circle_slogan1));
                            sb2.append(str4);
                            sb2.append(resources.getString(R.string.shareutil_friend_circle_slogan2));
                            eVar2.a = sb2.toString();
                            eVar2.g = BitmapFactory.decodeResource(context2.getResources(), R.drawable.pay_for_share_icon);
                            eVar2.b = str6;
                            eVar2.e = 0;
                            return eVar2;
                        case 5:
                            f fVar = new f();
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(resources.getString(R.string.shareutil_weibo_slogan1));
                            sb3.append(str4);
                            sb3.append(resources.getString(R.string.shareutil_weibo_slogan2));
                            fVar.a = sb3.toString();
                            fVar.h = str5;
                            fVar.b = str6;
                            return fVar;
                        default:
                            return null;
                    }
                }
            };
            dcb.a(dct, (dcd) r1);
        }
    }
}
