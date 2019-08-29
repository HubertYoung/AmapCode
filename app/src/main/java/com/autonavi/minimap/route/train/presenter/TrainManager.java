package com.autonavi.minimap.route.train.presenter;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.model.TrainTicketGeneralInfoItem;
import com.autonavi.minimap.route.train.model.TrainTypeItem.TrainType;
import com.autonavi.minimap.route.train.page.TrainInfoPage;
import com.autonavi.minimap.route.train.page.TrainTicketListPage;
import com.autonavi.minimap.train.TrainRequestHolder;
import com.autonavi.minimap.train.param.IndexRequest;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;

public final class TrainManager {
    public static CompatDialog a;

    public static final class TrainNoCallback extends TrainRequestCallback<ejd> {
        public TrainNoCallback(Callback<ejd> callback) {
            super(new ejd(), callback);
        }
    }

    static class TrainNoSearchListener implements Callback<ejd> {
        private bid mFragment;
        private String mTrainName;

        public TrainNoSearchListener(String str, bid bid) {
            this.mTrainName = str;
            this.mFragment = bid;
        }

        public void callback(ejd ejd) {
            if (this.mFragment == null || ejd == null || ejd.h == null || ejd.h.size() <= 0) {
                ToastHelper.showLongToast(eay.a(R.string.ic_net_error_noresult));
                return;
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("train name passed from station-station search", this.mTrainName);
            pageBundle.putObject("trainResponser", ejd);
            this.mFragment.startPage(TrainInfoPage.class, pageBundle);
        }

        public void error(Throwable th, boolean z) {
            if (z) {
                ToastHelper.showLongToast(eay.a(R.string.tt_cannot_find_results));
            } else if (th instanceof UnknownHostException) {
                ToastHelper.showLongToast(eay.a(R.string.network_error_message));
            } else {
                ToastHelper.showLongToast(eay.a(R.string.tt_cannot_find_results));
            }
        }
    }

    public static class TrainRequestCallback<T extends AbstractAOSParser> implements AosResponseCallback<AosByteResponse> {
        T a;
        Callback<T> b;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            try {
                this.a.parser((byte[]) ((AosByteResponse) aosResponse).getResult());
                aho.a(new Runnable() {
                    public final void run() {
                        if (TrainManager.a != null && TrainManager.a.isShowing()) {
                            TrainManager.a.dismiss();
                        }
                        if (TrainRequestCallback.this.b != null) {
                            TrainRequestCallback.this.b.callback(TrainRequestCallback.this.a);
                        }
                    }
                });
            } catch (UnsupportedEncodingException | JSONException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public TrainRequestCallback(T t, Callback<T> callback) {
            this.a = t;
            this.b = callback;
        }

        public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
            aho.a(new Runnable() {
                public final void run() {
                    if (TrainManager.a != null && TrainManager.a.isShowing()) {
                        TrainManager.a.dismiss();
                    }
                    if (TrainRequestCallback.this.b != null) {
                        TrainRequestCallback.this.b.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
                    }
                }
            });
        }
    }

    public static final class TrainStationCallback extends TrainRequestCallback<ejf> {
        public TrainStationCallback(Callback<ejf> callback) {
            super(new ejf(), callback);
        }
    }

    public static class TrainStationSearchListener implements Callback<ejf> {
        private String mDeparture = "";
        private String mDestination = "";
        private bid mPageContext;

        public TrainStationSearchListener(String str, String str2, bid bid) {
            this.mDeparture = str;
            this.mDestination = str2;
            this.mPageContext = bid;
        }

        public void callback(ejf ejf) {
            if (this.mPageContext == null || ejf == null || ejf.a == null || ejf.a.size() <= 0) {
                ToastHelper.showToast(eay.a(R.string.ic_net_error_noresult));
                return;
            }
            List<TrainTicketGeneralInfoItem> list = ejf.a;
            Collections.sort(list, new Comparator<TrainTicketGeneralInfoItem>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    TrainTicketGeneralInfoItem trainTicketGeneralInfoItem = (TrainTicketGeneralInfoItem) obj;
                    TrainTicketGeneralInfoItem trainTicketGeneralInfoItem2 = (TrainTicketGeneralInfoItem) obj2;
                    if (trainTicketGeneralInfoItem == trainTicketGeneralInfoItem2) {
                        return 0;
                    }
                    return trainTicketGeneralInfoItem.departureTime.compareTo(trainTicketGeneralInfoItem2.departureTime);
                }
            });
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("result list", list);
            pageBundle.putString("departure", this.mDeparture);
            pageBundle.putString("destination", this.mDestination);
            this.mPageContext.startPage(TrainTicketListPage.class, pageBundle);
        }

        public void error(Throwable th, boolean z) {
            if (z) {
                ToastHelper.showLongToast(eay.a(R.string.tt_cannot_find_results));
            } else if (th instanceof UnknownHostException) {
                ToastHelper.showLongToast(eay.a(R.string.network_error_message));
            } else {
                ToastHelper.showLongToast(eay.a(R.string.tt_cannot_find_results));
            }
        }
    }

    public static final class TrainTicketPurchasingCallback extends TrainRequestCallback<ejg> {
        public TrainTicketPurchasingCallback(Callback<ejg> callback) {
            super(new ejg(), callback);
        }
    }

    public static AosRequest a(String str, bid bid) {
        if (bid == null) {
            return null;
        }
        TrainNoSearchListener trainNoSearchListener = new TrainNoSearchListener(str, bid);
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.b = str;
        TrainNoCallback trainNoCallback = new TrainNoCallback(trainNoSearchListener);
        CompatDialog a2 = aav.a(indexRequest, eay.a(R.string.loadingMessage));
        a = a2;
        a2.show();
        TrainRequestHolder.getInstance().sendIndex(indexRequest, trainNoCallback);
        return indexRequest;
    }

    public static TrainType a(TrainTicketGeneralInfoItem trainTicketGeneralInfoItem) {
        char charAt = trainTicketGeneralInfoItem.trainName.toUpperCase(Locale.getDefault()).charAt(0);
        if (charAt == 'G') {
            return TrainType.G;
        }
        if (charAt == 'K') {
            return TrainType.K;
        }
        if (charAt == 'T') {
            return TrainType.T;
        }
        if (charAt == 'Z') {
            return TrainType.Z;
        }
        switch (charAt) {
            case 'C':
                return TrainType.C;
            case 'D':
                return TrainType.D;
            default:
                return TrainType.OTHERS;
        }
    }
}
