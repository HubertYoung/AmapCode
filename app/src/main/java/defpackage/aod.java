package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.minimap.account.alipay.AlipayAuthorizeRequestHolder;
import com.autonavi.minimap.account.alipay.model.AlipayAuthorizeInfoResponse;
import com.autonavi.minimap.account.alipay.param.AlipayAuthorizeInfoParam;
import com.autonavi.minimap.account.appeal.AppealRequestHolder;
import com.autonavi.minimap.account.appeal.model.AppealBindMobileResponse;
import com.autonavi.minimap.account.appeal.param.AppealBindMobileParam;
import com.autonavi.minimap.account.bind.BindRequestHolder;
import com.autonavi.minimap.account.deactivate.DeactivateRequestHolder;
import com.autonavi.minimap.account.deactivate.param.DeactivateParam;
import com.autonavi.minimap.account.login.LoginRequestHolder;
import com.autonavi.minimap.account.login.param.LoginMobileParam;
import com.autonavi.minimap.account.login.param.LoginPWParam;
import com.autonavi.minimap.account.login.param.ProfileGetParam;
import com.autonavi.minimap.account.login.param.ProfileUpdateParam;
import com.autonavi.minimap.account.logout.LogoutRequestHolder;
import com.autonavi.minimap.account.logout.param.LogoutParam;
import com.autonavi.minimap.account.modify.ModifyPWRequestHolder;
import com.autonavi.minimap.account.modify.model.ModifyPWResponse;
import com.autonavi.minimap.account.modify.param.ModifyPWParam;
import com.autonavi.minimap.account.password.PasswordRequestHolder;
import com.autonavi.minimap.account.password.model.PasswordInitResponse;
import com.autonavi.minimap.account.password.param.PasswordInitParam;
import com.autonavi.minimap.account.payment.PaymentRequestHolder;
import com.autonavi.minimap.account.payment.model.PaymentMobileResponse;
import com.autonavi.minimap.account.payment.param.PaymentMobileParam;
import com.autonavi.minimap.account.reset.ResetPWRequestHolder;
import com.autonavi.minimap.account.reset.model.ResetPWResponse;
import com.autonavi.minimap.account.reset.param.ResetPWParam;
import com.autonavi.minimap.account.unbind.UnBindRequestHolder;
import com.autonavi.minimap.account.verify.VerifyRequestHolder;
import com.autonavi.minimap.account.verify.model.VerifycodeResponse;
import com.autonavi.minimap.account.verify.param.VerifyCheckParam;
import com.autonavi.minimap.account.verify.param.VerifyGetParam;
import java.io.File;

/* renamed from: aod reason: default package */
/* compiled from: AccountAmapModel */
public final class aod {
    private static String a = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_PASSPORT_URL_KEY);

    public static void a(String str, String str2, aox aox) {
        LoginMobileParam loginMobileParam = new LoginMobileParam();
        loginMobileParam.mobile = str;
        loginMobileParam.code = str2;
        LoginRequestHolder.getInstance().sendLoginMobile(loginMobileParam, aox);
    }

    public static void b(String str, String str2, aox aox) {
        LoginPWParam loginPWParam = new LoginPWParam();
        loginPWParam.userid = str;
        loginPWParam.password = str2;
        LoginRequestHolder.getInstance().sendLoginPW(loginPWParam, aox);
    }

    public static void a(aoy aoy) {
        LogoutRequestHolder.getInstance().sendLogout(new LogoutParam(), aoy);
    }

    public static void a(String str, String str2, String str3, int i, final dko<VerifycodeResponse> dko) {
        VerifyGetParam verifyGetParam = new VerifyGetParam();
        verifyGetParam.code_type = str;
        verifyGetParam.target_type = str2;
        verifyGetParam.target_value = str3;
        verifyGetParam.skip_new = i;
        VerifyRequestHolder.getInstance().sendVerifyGet(verifyGetParam, new dko<VerifycodeResponse>() {
            public final /* bridge */ /* synthetic */ void a(dkm dkm) {
                VerifycodeResponse verifycodeResponse = (VerifycodeResponse) dkm;
                if (verifycodeResponse.code == 14) {
                    aoy.a();
                }
                dko.a(verifycodeResponse);
            }

            public final void a(Exception exc) {
                dko.a(exc);
            }
        });
    }

    public static void a(String str, String str2, String str3, String str4, dko<VerifycodeResponse> dko) {
        VerifyCheckParam verifyCheckParam = new VerifyCheckParam();
        if (!TextUtils.isEmpty(str2)) {
            if (str2.equals("1")) {
                verifyCheckParam.email = str3;
            } else if (str2.equals("2")) {
                verifyCheckParam.mobile = str3;
            }
        }
        verifyCheckParam.code = str4;
        verifyCheckParam.code_type = str;
        VerifyRequestHolder.getInstance().sendVerifyCheck(verifyCheckParam, dko);
    }

    public static void a(int i, aox aox) {
        ProfileGetParam profileGetParam = new ProfileGetParam();
        profileGetParam.mode = i;
        LoginRequestHolder.getInstance().sendProfileGet(profileGetParam, aox);
    }

    public static void a(String str, String str2, String str3, String str4, aox aox) {
        ProfileUpdateParam profileUpdateParam = new ProfileUpdateParam();
        if (!TextUtils.isEmpty(str3)) {
            profileUpdateParam.nickname = str3;
        }
        if (!TextUtils.isEmpty(str4)) {
            profileUpdateParam.gender = str4;
        }
        if (!TextUtils.isEmpty(str2)) {
            profileUpdateParam.birthday = str2;
        }
        if (!TextUtils.isEmpty(str)) {
            profileUpdateParam.avatarfield = "avatar";
            profileUpdateParam.avatar = new File(str);
        }
        LoginRequestHolder.getInstance().sendProfileUpdate(profileUpdateParam, aox);
    }

    public static void a(String str, String str2, String str3, dko<ResetPWResponse> dko) {
        ResetPWParam resetPWParam = new ResetPWParam();
        resetPWParam.target_value = str;
        resetPWParam.code = str2;
        resetPWParam.password = str3;
        ResetPWRequestHolder.getInstance().sendResetPW(resetPWParam, dko);
    }

    public static void a(String str, String str2, final dko<ModifyPWResponse> dko) {
        ModifyPWParam modifyPWParam = new ModifyPWParam();
        modifyPWParam.newpassword = str2;
        modifyPWParam.oldpassword = str;
        ModifyPWRequestHolder.getInstance().sendModifyPW(modifyPWParam, new dko<ModifyPWResponse>() {
            public final /* bridge */ /* synthetic */ void a(dkm dkm) {
                ModifyPWResponse modifyPWResponse = (ModifyPWResponse) dkm;
                if (modifyPWResponse.code == 14) {
                    aoy.a();
                }
                dko.a(modifyPWResponse);
            }

            public final void a(Exception exc) {
                dko.a(exc);
            }
        });
    }

    public static void a(String str, final dko<PasswordInitResponse> dko) {
        PasswordInitParam passwordInitParam = new PasswordInitParam();
        passwordInitParam.password = str;
        PasswordRequestHolder.getInstance().sendPasswordInit(passwordInitParam, new dko<PasswordInitResponse>() {
            public final /* synthetic */ void a(dkm dkm) {
                PasswordInitResponse passwordInitResponse = (PasswordInitResponse) dkm;
                if (passwordInitResponse.code == 1) {
                    ant b = aoe.a().b();
                    if (b != null) {
                        if (passwordInitResponse.data != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(passwordInitResponse.data.repwd);
                            b.w = sb.toString();
                        }
                        aoe.a().a(b);
                    }
                }
                if (passwordInitResponse.code == 14) {
                    aoy.a();
                }
                dko.a(passwordInitResponse);
            }

            public final void a(Exception exc) {
                dko.a(exc);
            }
        });
    }

    public static void a(String str, String str2, aow aow) {
        DeactivateParam deactivateParam = new DeactivateParam();
        deactivateParam.mobile = str;
        deactivateParam.code = str2;
        DeactivateRequestHolder.getInstance().sendDeactivate(deactivateParam, aow);
    }

    public static void a(dko<AlipayAuthorizeInfoResponse> dko) {
        AlipayAuthorizeInfoParam alipayAuthorizeInfoParam = new AlipayAuthorizeInfoParam();
        alipayAuthorizeInfoParam.adiu = NetworkParam.getAdiu();
        AlipayAuthorizeRequestHolder.getInstance().sendAlipayAuthorizeInfo(alipayAuthorizeInfoParam, dko);
    }

    public static void a(String str, String str2, int i, aox aox) {
        cho cho = new cho();
        cho.a = str;
        cho.b = str2;
        cho.c = i;
        BindRequestHolder.getInstance().sendBindEmail(cho, aox);
    }

    public static void b(String str, String str2, int i, aox aox) {
        chq chq = new chq();
        chq.a = str;
        chq.b = str2;
        chq.c = i;
        BindRequestHolder.getInstance().sendBindMobile(chq, aox);
    }

    public static void a(AccountType accountType, String str, aoz aoz) {
        chv chv = new chv();
        chv.a = str;
        UnBindRequestHolder instance = UnBindRequestHolder.getInstance();
        switch (accountType) {
            case Mobile:
                instance.sendUnBindMobile(chv, aoz);
                return;
            case Email:
                instance.sendUnBindEmail(chv, aoz);
                return;
            case QQ:
                instance.sendUnBindQQ(chv, aoz);
                return;
            case Weixin:
                instance.sendUnBindWx(chv, aoz);
                return;
            case Alipay:
                String a2 = a();
                if (TextUtils.equals(a2, "test")) {
                    chv.b = "sit";
                } else if (TextUtils.equals(a2, "dev")) {
                    chv.b = "dev";
                }
                instance.sendUnBindAlipay(chv, aoz);
                return;
            case Taobao:
                instance.sendUnBindTabobao(chv, aoz);
                return;
            case Sina:
                instance.sendUnBindWeibo(chv, aoz);
                return;
            default:
                StringBuilder sb = new StringBuilder("doUnbind unknow type:");
                sb.append(accountType.name());
                AMapLog.e("accountTAG", sb.toString());
                return;
        }
    }

    public static void a(String str, String str2, String str3, int i, final aox aox) {
        PaymentMobileParam paymentMobileParam = new PaymentMobileParam();
        paymentMobileParam.mobile = str;
        paymentMobileParam.code = str2;
        paymentMobileParam.verifyToken = str3;
        paymentMobileParam.replaceType = i;
        paymentMobileParam.mode = 31;
        PaymentRequestHolder.getInstance().sendPaymentMobile(paymentMobileParam, new dko<PaymentMobileResponse>() {
            public final /* synthetic */ void a(dkm dkm) {
                PaymentMobileResponse paymentMobileResponse = (PaymentMobileResponse) dkm;
                chm chm = new chm();
                apg.a(paymentMobileResponse, chm);
                chm.d = paymentMobileResponse.data;
                aox.a(chm);
            }

            public final void a(Exception exc) {
                aox.a(exc);
            }
        });
    }

    public static void b(String str, String str2, String str3, int i, final aox aox) {
        AppealBindMobileParam appealBindMobileParam = new AppealBindMobileParam();
        appealBindMobileParam.mobile = str;
        appealBindMobileParam.code = str2;
        appealBindMobileParam.replaceType = i;
        appealBindMobileParam.signId = str3;
        AppealRequestHolder.getInstance().sendAppealBindMobile(appealBindMobileParam, new dko<AppealBindMobileResponse>() {
            public final void a(Exception exc) {
            }

            public final /* synthetic */ void a(dkm dkm) {
                AppealBindMobileResponse appealBindMobileResponse = (AppealBindMobileResponse) dkm;
                chm chm = new chm();
                apg.a(appealBindMobileResponse, chm);
                chm.d = appealBindMobileResponse.data.profile;
                chm.e = appealBindMobileResponse.data.remain;
                aox.a(chm);
            }
        });
    }

    public static String a() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences.toString()).getStringValue(ConfigerHelper.alipay_env_new, "online");
    }
}
