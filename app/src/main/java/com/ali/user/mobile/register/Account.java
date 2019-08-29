package com.ali.user.mobile.register;

import android.text.TextUtils;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.util.StringUtil;
import com.ali.user.mobile.utils.ResourceUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialVersionUID = 7965825854793204250L;
    private String areaCode;
    private String mAreaName;
    private String phoneNumber;

    public Account() {
        update("86", "", ResourceUtil.a(R.string.C));
    }

    public Account(Account account) {
        update(account.areaCode, account.phoneNumber, account.mAreaName);
    }

    public Account(String str) {
        int indexOf = str.indexOf(45);
        if (indexOf < 0) {
            update("86", str, ResourceUtil.a(R.string.C));
        } else if (indexOf == 0) {
            update("86", str.substring(1), ResourceUtil.a(R.string.C));
        } else if (indexOf == str.length() - 1) {
            update(str.substring(0, str.length() - 1), "", "");
        } else {
            update(str.substring(0, indexOf), str.substring(indexOf + 1), "");
        }
    }

    public Account(String str, String str2, String str3) {
        update(str, str2, str3);
    }

    public final void update(String str, String str2, String str3) {
        this.areaCode = null;
        this.phoneNumber = null;
        if (!TextUtils.isEmpty(str)) {
            this.areaCode = str.replace("+", "");
        }
        if (!TextUtils.isEmpty(str2)) {
            int indexOf = str2.indexOf(45);
            if (-1 != indexOf) {
                if (TextUtils.isEmpty(this.areaCode)) {
                    this.areaCode = str2.substring(0, indexOf).replace("+", "");
                }
                this.phoneNumber = str2.substring(indexOf + 1).replace(Token.SEPARATOR, "");
            } else {
                this.phoneNumber = str2.replace(Token.SEPARATOR, "");
            }
        }
        this.mAreaName = str3;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    private String getFullAccount(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append(this.areaCode);
        sb.append(str2);
        sb.append(this.phoneNumber);
        return sb.toString();
    }

    public String getAreaName() {
        return this.mAreaName;
    }

    public String getFullAreaCode() {
        StringBuilder sb = new StringBuilder("+");
        sb.append(this.areaCode);
        return sb.toString();
    }

    public String getAreaCodeForRPC() {
        if (TextUtils.isEmpty(this.areaCode)) {
            return "";
        }
        return this.areaCode.replace("+", "");
    }

    public String accountForDisplay() {
        if (isMainland()) {
            return StringUtil.a(this.phoneNumber, 4);
        }
        return getFullAccount("", "-");
    }

    public String asAccount() {
        if (isMainland()) {
            return this.phoneNumber;
        }
        return getFullAccount("", "-");
    }

    public String getAccountForRPC() {
        return this.phoneNumber;
    }

    public boolean isMainland() {
        return "86".equals(this.areaCode);
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.phoneNumber);
    }

    public boolean isAllEmpty() {
        return TextUtils.isEmpty(this.areaCode) && TextUtils.isEmpty(this.phoneNumber) && TextUtils.isEmpty(this.mAreaName);
    }
}
