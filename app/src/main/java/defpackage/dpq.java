package defpackage;

/* renamed from: dpq reason: default package */
/* compiled from: IOrderFinishedListener */
public interface dpq {
    void onDeleteFinished(dqe dqe);

    void onError();

    void onOrderListByPhoneNetDataFinished(dpp dpp);

    void onOrderListNetDataFinished(dpp dpp);

    void onOrderListNetDataFinishedNew(dpp dpp);
}
