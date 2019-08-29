package defpackage;

import android.content.Intent;

/* renamed from: ezu reason: default package */
/* compiled from: PushClientFactory */
public final class ezu implements ewv {
    private eyh a = new eyh();

    public final fbh a(Intent intent) {
        fbh fbh;
        fbh fbh2;
        int intExtra = intent.getIntExtra("command", -1);
        if (intExtra < 0) {
            intExtra = intent.getIntExtra("method", -1);
        }
        if (intExtra != 20) {
            switch (intExtra) {
                case 1:
                case 2:
                    fbh2 = new ext(intExtra);
                    break;
                case 3:
                    fbh = new exo();
                    break;
                case 4:
                    fbh = new exq();
                    break;
                case 5:
                    fbh = new exp();
                    break;
                case 6:
                    fbh = new exr();
                    break;
                case 7:
                    fbh = new exn();
                    break;
                case 8:
                    fbh = new exm();
                    break;
                case 9:
                    fbh = new exl();
                    break;
                case 10:
                case 11:
                    fbh2 = new exj(intExtra);
                    break;
                case 12:
                    fbh = new exk();
                    break;
                default:
                    fbh = null;
                    break;
            }
            fbh = fbh2;
        } else {
            fbh = new exu();
        }
        if (fbh != null) {
            ewx a2 = ewx.a(intent);
            if (a2 == null) {
                fat.b((String) "PushCommand", (String) "bundleWapper is null");
            } else {
                fbh.d(a2);
            }
        }
        return fbh;
    }

    public final fbe a(fbh fbh) {
        int i = fbh.f;
        if (i == 20) {
            return new eyg(fbh);
        }
        switch (i) {
            case 0:
                break;
            case 1:
                return new eyb(fbh);
            case 2:
                return new eyq(fbh);
            case 3:
                return new eyx(fbh);
            case 4:
                return new eyz(fbh);
            case 5:
                return new ezb(fbh);
            case 6:
                return new ezh(fbh);
            case 7:
                return new eyv(fbh);
            case 8:
                return new eyt(fbh);
            case 9:
                return new eyp(fbh);
            case 10:
                return new eym(fbh);
            case 11:
                return new eye(fbh);
            case 12:
                return new eyo(fbh);
            default:
                switch (i) {
                    case 100:
                        return new eyk(fbh);
                    case 101:
                        return new eyl(fbh);
                    default:
                        switch (i) {
                            case 2000:
                            case 2001:
                            case 2002:
                            case 2003:
                            case 2004:
                            case 2005:
                            case 2008:
                            case 2009:
                            case 2010:
                            case 2011:
                            case 2012:
                            case 2013:
                            case 2014:
                                break;
                            case 2006:
                                return new exz(fbh);
                            case 2007:
                                return new eyj(fbh);
                            default:
                                return null;
                        }
                }
        }
        return new eyi(fbh);
    }

    public final eya b(fbh fbh) {
        int i = fbh.f;
        if (i == 20) {
            return new eyg(fbh);
        }
        switch (i) {
            case 1:
                return new eyb(fbh);
            case 2:
                return new eyq(fbh);
            case 3:
                return new eyx(fbh);
            case 4:
                return new eyz(fbh);
            case 5:
                return new ezb(fbh);
            case 6:
                return new ezh(fbh);
            case 7:
                return new eyv(fbh);
            case 8:
                return new eyt(fbh);
            case 9:
                return new eyp(fbh);
            case 10:
                return new eym(fbh);
            case 11:
                return new eye(fbh);
            default:
                return null;
        }
    }
}
