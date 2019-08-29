package com.autonavi.bundle.account.api;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.alipay.multimedia.gles.GlUtil;
import com.autonavi.minimap.account.logout.model.LogoutResponse;
import com.tencent.connect.common.Constants;
import java.util.List;

public interface IAccountService extends bie, esc {

    public enum AccountType {
        Sina,
        Taobao,
        QQ,
        Weixin,
        Mobile,
        Email,
        Gaode,
        Alipay
    }

    public enum HistoryLoginType {
        Phone("Phone"),
        Password("Password"),
        Sina("Sina"),
        Taobao("Taobao"),
        QQ(Constants.SOURCE_QQ),
        Weixin("Weixin"),
        Alipay(GlUtil.TAG);
        
        String a;

        private HistoryLoginType(String str) {
            this.a = str;
        }

        @Nullable
        public static HistoryLoginType getHistoryLoginType(String str) {
            HistoryLoginType[] values;
            for (HistoryLoginType historyLoginType : values()) {
                if (historyLoginType.a.equals(str)) {
                    return historyLoginType;
                }
            }
            return null;
        }
    }

    public interface a {
        void openH5Page(Bundle bundle);
    }

    @Nullable
    HistoryLoginType a(@NonNull Context context);

    @Nullable
    String a(@NonNull String str);

    void a(Activity activity, String str, anq anq);

    void a(Activity activity, String str, List<String> list, List<String> list2, String str2, Bundle bundle, anq anq);

    void a(anp anp);

    void a(anq anq);

    void a(@Nullable bid bid);

    void a(@Nullable bid bid, @Nullable anq anq);

    void a(@Nullable bid bid, @NonNull anq anq, @Nullable String str, @Nullable String str2);

    void a(bid bid, AccountType accountType, anq anq);

    void a(@Nullable bid bid, @Nullable String str, anq anq);

    void a(@Nullable AccountType accountType, anq anq);

    void a(a aVar);

    void a(dko<LogoutResponse> dko);

    void a(String str, List<String> list, List<String> list2, String str2, Bundle bundle, anq anq);

    boolean a();

    boolean a(AccountType accountType);

    String b();

    void b(anp anp);

    void b(bid bid);

    IThirdAuth c();

    void c(bid bid);

    void d();

    @Nullable
    ant e();

    void f();
}
