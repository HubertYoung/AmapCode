package defpackage;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.carowner.roadcamera.page.RdCameraApplyResultFragment;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.widget.AmapTextView;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bhu reason: default package */
/* compiled from: RdCameraApplyResultPresenter */
public final class bhu extends sw<RdCameraApplyResultFragment, bhq> {
    public bhu(RdCameraApplyResultFragment rdCameraApplyResultFragment) {
        super(rdCameraApplyResultFragment);
    }

    public final void onPageCreated() {
        RdCameraApplyResultFragment rdCameraApplyResultFragment = (RdCameraApplyResultFragment) this.mPage;
        PageBundle arguments = rdCameraApplyResultFragment.getArguments();
        if (arguments != null) {
            rdCameraApplyResultFragment.a = arguments.getString("bundle_key_compensation_amount");
            if (TextUtils.isEmpty(rdCameraApplyResultFragment.a)) {
                rdCameraApplyResultFragment.a = "0.0";
            }
            rdCameraApplyResultFragment.b = arguments.getString("bundle_key_sharing_title");
            rdCameraApplyResultFragment.c = arguments.getString("bundle_key_sharing_content");
            rdCameraApplyResultFragment.d = arguments.getString("bundle_key_sharing_url");
        }
        RdCameraApplyResultFragment rdCameraApplyResultFragment2 = (RdCameraApplyResultFragment) this.mPage;
        rdCameraApplyResultFragment2.e = (TextView) rdCameraApplyResultFragment2.getContentView().findViewById(R.id.title_text_name);
        rdCameraApplyResultFragment2.getContentView().findViewById(R.id.title_btn_left).setVisibility(8);
        rdCameraApplyResultFragment2.f = (TextView) rdCameraApplyResultFragment2.getContentView().findViewById(R.id.apply_conclusion);
        rdCameraApplyResultFragment2.g = (AmapTextView) rdCameraApplyResultFragment2.getContentView().findViewById(R.id.conclusion_message);
        rdCameraApplyResultFragment2.h = rdCameraApplyResultFragment2.getContentView().findViewById(R.id.actions_when_login);
        rdCameraApplyResultFragment2.i = (TextView) rdCameraApplyResultFragment2.getContentView().findViewById(R.id.apply_result_info);
        rdCameraApplyResultFragment2.i.setText(Html.fromHtml(rdCameraApplyResultFragment2.getString(R.string.rd_camera_apply_result_verified_info_for_entrance_in_html, Integer.valueOf(10))));
        rdCameraApplyResultFragment2.j = (Button) rdCameraApplyResultFragment2.getContentView().findViewById(R.id.back_to_map_view);
        rdCameraApplyResultFragment2.j.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyResultFragment.this.finishAllFragmentsWithoutRoot();
            }
        });
        rdCameraApplyResultFragment2.k = (Button) rdCameraApplyResultFragment2.getContentView().findViewById(R.id.next_compensation);
        rdCameraApplyResultFragment2.k.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyResultFragment.this.finish();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("pageID", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("amap.extra.road.camera.pageparam", jSONObject);
                RdCameraApplyResultFragment.this.startPage(RdCameraPaymentListPage.class, pageBundle);
            }
        });
        rdCameraApplyResultFragment2.l = rdCameraApplyResultFragment2.getContentView().findViewById(R.id.actions_not_login);
        rdCameraApplyResultFragment2.m = (Button) rdCameraApplyResultFragment2.getContentView().findViewById(R.id.login_btn);
        rdCameraApplyResultFragment2.m.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.a(RdCameraApplyResultFragment.this.getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                RdCameraApplyResultFragment.this.a();
                            }
                        }
                    });
                }
            }
        });
        rdCameraApplyResultFragment2.n = rdCameraApplyResultFragment2.getContentView().findViewById(R.id.bottom_bar);
        ((TextView) rdCameraApplyResultFragment2.getContentView().findViewById(R.id.friends_text)).setText(Html.fromHtml(rdCameraApplyResultFragment2.getString(R.string.rd_camera_apply_result_friends_text)));
        rdCameraApplyResultFragment2.o = rdCameraApplyResultFragment2.getContentView().findViewById(R.id.sharing_view);
        rdCameraApplyResultFragment2.o.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ahn.b().execute(new Runnable() {
                    public final void run() {
                        final File file = new File(FileUtil.getCacheDir(), "shared_image_weibo_rd_camera.jpg");
                        if (!file.exists()) {
                            try {
                                ahd.a(RdCameraApplyResultFragment.this.getContext(), R.drawable.shared_image_weibo_rd_camera, file);
                            } catch (Exception unused) {
                            }
                        }
                        aho.a(new Runnable() {
                            public final void run() {
                                dct dct = new dct();
                                dct.a();
                                dct.f = true;
                                dct.d = true;
                                dct.e = true;
                                RdCameraApplyResultFragment.this.d = ConfigerHelper.getInstance().getRdcameraPaymentSharingLinkUrl();
                                Context context = RdCameraApplyResultFragment.this.getContext();
                                String c = RdCameraApplyResultFragment.this.d;
                                String d = RdCameraApplyResultFragment.this.a;
                                String absolutePath = file.getAbsolutePath();
                                dcb dcb = (dcb) a.a.a(dcb.class);
                                if (dcb != null) {
                                    dcb.a(dct, (dcd) new dcd(context, absolutePath, d, c) {
                                        final /* synthetic */ Context a;
                                        final /* synthetic */ String b;
                                        final /* synthetic */ String c;
                                        final /* synthetic */ String d;

                                        {
                                            this.a = r1;
                                            this.b = r2;
                                            this.c = r3;
                                            this.d = r4;
                                        }

                                        public final ShareParam getShareDataByType(int i) {
                                            switch (i) {
                                                case 3:
                                                    e eVar = new e(0);
                                                    eVar.g = BitmapFactory.decodeResource(this.a.getResources(), R.drawable.rd_camera_shared_image_wechat);
                                                    eVar.h = this.b;
                                                    eVar.f = String.format(this.a.getResources().getString(R.string.rd_camera_apply_result_fixed_wechat_sharing_title), new Object[]{this.c});
                                                    eVar.a = this.a.getResources().getString(R.string.rd_camera_apply_result_fixed_wechat_friends_sharing_content);
                                                    eVar.b = this.d;
                                                    eVar.e = 0;
                                                    return eVar;
                                                case 4:
                                                    e eVar2 = new e(1);
                                                    eVar2.f = String.format(this.a.getResources().getString(R.string.rd_camera_apply_result_fixed_wechat_sharing_title), new Object[]{this.c});
                                                    eVar2.a = String.format(this.a.getResources().getString(R.string.rd_camera_apply_result_fixed_wechat_moments_sharing_content), new Object[]{this.c});
                                                    eVar2.g = BitmapFactory.decodeResource(this.a.getResources(), R.drawable.rd_camera_shared_image_wechat);
                                                    eVar2.b = this.d;
                                                    eVar2.e = 0;
                                                    return eVar2;
                                                case 5:
                                                    f fVar = new f();
                                                    fVar.a = String.format(this.a.getResources().getString(R.string.rd_camera_apply_result_fixed_weibo_sharing_content), new Object[]{this.c});
                                                    fVar.h = this.b;
                                                    fVar.b = this.d;
                                                    return fVar;
                                                default:
                                                    return null;
                                            }
                                        }

                                        public final void onShow() {
                                            super.onShow();
                                        }

                                        public final void onDismiss() {
                                            super.onDismiss();
                                        }

                                        public final void onFinish(int i, int i2) {
                                            super.onFinish(i, i2);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });
        RdCameraApplyResultFragment rdCameraApplyResultFragment3 = (RdCameraApplyResultFragment) this.mPage;
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (iAccountService.a()) {
                rdCameraApplyResultFragment3.a();
                return;
            }
            rdCameraApplyResultFragment3.e.setText(R.string.rd_camera_apply_result_apply_hint);
            rdCameraApplyResultFragment3.f.setText(R.string.rd_camera_apply_result_submit_successfully);
            rdCameraApplyResultFragment3.g.setText(Html.fromHtml(rdCameraApplyResultFragment3.getString(R.string.rd_camera_apply_result_login_notice_info_in_html, Float.valueOf(rdCameraApplyResultFragment3.a))));
            rdCameraApplyResultFragment3.l.setVisibility(0);
            rdCameraApplyResultFragment3.h.setVisibility(8);
            rdCameraApplyResultFragment3.n.setVisibility(8);
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((RdCameraApplyResultFragment) this.mPage).finishAllFragmentsWithoutRoot();
        return super.onBackPressed();
    }

    public final /* synthetic */ su a() {
        return new bhq(this);
    }
}
