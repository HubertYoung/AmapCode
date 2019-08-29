package defpackage;

/* renamed from: dpn reason: default package */
/* compiled from: AosCaptchaVerifyResponser */
public final class dpn extends dog {
    public final void a(byte[] bArr) {
        super.a(bArr);
    }

    public final String a() {
        this.d = "获取验证码失败，请稍后重试";
        int i = this.c;
        if (i == 14) {
            this.d = "请重新登录后再试";
        } else if (i != 55) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 4:
                case 5:
                    break;
                case 3:
                    this.d = "请输入有效的手机号码";
                    break;
                default:
                    switch (i) {
                        case 25:
                            this.d = "该手机号已绑定过另一账户";
                            break;
                        case 26:
                        case 27:
                        case 28:
                            break;
                        default:
                            switch (i) {
                                case 41:
                                    this.d = "超出每分钟最大请求数";
                                    break;
                                case 42:
                                    this.d = "超出每小时最大请求数";
                                    break;
                            }
                    }
            }
        } else {
            this.d = "手机号格式不正确";
        }
        return this.d;
    }
}
