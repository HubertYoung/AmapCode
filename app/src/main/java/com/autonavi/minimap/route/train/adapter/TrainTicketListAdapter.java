package com.autonavi.minimap.route.train.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.model.TrainTicketGeneralInfoItem;
import com.autonavi.minimap.route.train.model.TrainTypeItem.TrainType;
import com.autonavi.minimap.route.train.presenter.TrainManager;
import com.autonavi.minimap.route.train.presenter.TrainManager.TrainTicketPurchasingCallback;
import com.autonavi.minimap.train.TrainRequestHolder;
import com.autonavi.minimap.train.param.SubmitOrderRequest;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TrainTicketListAdapter extends AbstractViewHolderBaseAdapter<TrainTicketGeneralInfoItem, eik> implements Filterable {
    private static final String PurchasingRequestDataFormat = "yyyy-MM-dd";
    private static final int TRAIN_TICKET_PURCHASING_FREEZING_TIME = 120;
    /* access modifiers changed from: private */
    public bid mPageContext;
    private int mSeletedDateIndex = 0;

    class TrainTicketPurchasingListener implements Callback<ejg> {
        private TrainTicketPurchasingListener() {
        }

        /* synthetic */ TrainTicketPurchasingListener(TrainTicketListAdapter trainTicketListAdapter, byte b) {
            this();
        }

        public void callback(ejg ejg) {
            if (ejg != null && !TextUtils.isEmpty(ejg.a)) {
                aja aja = new aja(ejg.a);
                aja.b = new ajf() {
                    public final b c() {
                        return new b() {
                            public final boolean a() {
                                return false;
                            }

                            public final String b() {
                                return null;
                            }

                            public final long c() {
                                return 100;
                            }
                        };
                    }
                };
                aix aix = (aix) a.a.a(aix.class);
                if (aix != null) {
                    aix.a(TrainTicketListAdapter.this.mPageContext, aja);
                }
            }
        }

        public void error(Throwable th, boolean z) {
            if (z || !(th instanceof UnknownHostException)) {
                ToastHelper.showLongToast(eay.a(R.string.life_base_nosearch_result));
            } else {
                ToastHelper.showLongToast(eay.a(R.string.network_error_message));
            }
        }
    }

    public TrainTicketListAdapter(bid bid, int i) {
        this.mPageContext = bid;
        this.mSeletedDateIndex = i;
    }

    public View onCreateView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(this.mPageContext.getContext()).inflate(R.layout.train_ticket_list_item, viewGroup, false);
    }

    public eik onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
        return new eik(view);
    }

    public void onBindViewHolderWithData(eik eik, final TrainTicketGeneralInfoItem trainTicketGeneralInfoItem, int i, int i2) {
        if (trainTicketGeneralInfoItem != null) {
            eik.itemView.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    TrainManager.a(trainTicketGeneralInfoItem.trainName, TrainTicketListAdapter.this.mPageContext);
                }
            });
            eik.a.setText(trainTicketGeneralInfoItem.trainName);
            String str = "";
            if (trainTicketGeneralInfoItem.map != null) {
                if (trainTicketGeneralInfoItem.map.containsKey("seat_yw_x") && (TrainManager.a(trainTicketGeneralInfoItem) == TrainType.Z || TrainManager.a(trainTicketGeneralInfoItem) == TrainType.T || TrainManager.a(trainTicketGeneralInfoItem) == TrainType.K || TrainManager.a(trainTicketGeneralInfoItem) == TrainType.OTHERS)) {
                    StringBuilder sb = new StringBuilder("硬卧-下:￥");
                    sb.append((String) trainTicketGeneralInfoItem.map.get("seat_yw_x"));
                    str = sb.toString();
                } else if (trainTicketGeneralInfoItem.map.containsKey("seat_2") && (TrainManager.a(trainTicketGeneralInfoItem) == TrainType.C || TrainManager.a(trainTicketGeneralInfoItem) == TrainType.G || TrainManager.a(trainTicketGeneralInfoItem) == TrainType.D)) {
                    StringBuilder sb2 = new StringBuilder("二等座:￥");
                    sb2.append((String) trainTicketGeneralInfoItem.map.get("seat_2"));
                    str = sb2.toString();
                }
            }
            eik.b.setText(str);
            eik.c.setText(trainTicketGeneralInfoItem.departureTime);
            eik.d.setText(trainTicketGeneralInfoItem.departure);
            eik.e.setText(trainTicketGeneralInfoItem.arrivalTime);
            eik.f.setText(trainTicketGeneralInfoItem.destination);
            String str2 = trainTicketGeneralInfoItem.runningTime;
            int indexOf = str2.indexOf(":");
            if (indexOf > 0) {
                String substring = str2.substring(0, indexOf);
                String substring2 = str2.substring(indexOf + 1);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(substring);
                sb3.append("时");
                sb3.append(substring2);
                sb3.append("分");
                str2 = sb3.toString();
            }
            eik.g.setText(str2);
            if (isShortestTimeTrain(i)) {
                eik.h.setVisibility(0);
            } else {
                eik.h.setVisibility(8);
            }
            if (isTicketsAvailable(trainTicketGeneralInfoItem.departureTime)) {
                eik.j.setVisibility(0);
                eik.i.setVisibility(8);
            } else {
                eik.i.setVisibility(0);
                eik.j.setVisibility(8);
            }
            eik.j.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                    if (iAccountService != null) {
                        iAccountService.a(TrainTicketListAdapter.this.mPageContext, (String) "请绑定手机号进行购票", (anq) new anq() {
                            public final void loginOrBindCancel() {
                            }

                            public final void onComplete(boolean z) {
                                TrainTicketListAdapter.this.launchH5ForPurchasingTickets(trainTicketGeneralInfoItem);
                            }
                        });
                    }
                }
            });
        }
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                ArrayList arrayList = new ArrayList();
                List<TrainTicketGeneralInfoItem> data = TrainTicketListAdapter.this.getData();
                String upperCase = charSequence.toString().toUpperCase(Locale.getDefault());
                if ("ALL".equals(upperCase)) {
                    filterResults.count = data.size();
                    filterResults.values = data;
                } else if ("OTHERS".equals(upperCase)) {
                    for (TrainTicketGeneralInfoItem trainTicketGeneralInfoItem : data) {
                        if (!Character.isLetter(trainTicketGeneralInfoItem.trainName.toUpperCase(Locale.getDefault()).charAt(0))) {
                            arrayList.add(trainTicketGeneralInfoItem);
                        }
                    }
                    filterResults.count = arrayList.size();
                    filterResults.values = arrayList;
                } else {
                    for (TrainTicketGeneralInfoItem trainTicketGeneralInfoItem2 : data) {
                        if (trainTicketGeneralInfoItem2.trainName.toUpperCase(Locale.getDefault()).charAt(0) == upperCase.charAt(0)) {
                            arrayList.add(trainTicketGeneralInfoItem2);
                        }
                    }
                    filterResults.count = arrayList.size();
                    filterResults.values = arrayList;
                }
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                TrainTicketListAdapter.this.setDataAndChangeDataSet((List) filterResults.values);
            }
        };
    }

    private boolean isTicketsAvailable(String str) {
        if (this.mSeletedDateIndex > 0) {
            return true;
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        String[] split = str.split(":");
        try {
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            instance2.set(6, instance2.get(6) + this.mSeletedDateIndex);
            instance2.set(11, parseInt);
            instance2.set(12, parseInt2 - 120);
            if (instance.getTimeInMillis() < instance2.getTimeInMillis()) {
                return true;
            }
            return false;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void launchH5ForPurchasingTickets(TrainTicketGeneralInfoItem trainTicketGeneralInfoItem) {
        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest();
        submitOrderRequest.b = trainTicketGeneralInfoItem.departure;
        submitOrderRequest.c = trainTicketGeneralInfoItem.destination;
        submitOrderRequest.d = getSelectedDate("yyyy-MM-dd");
        submitOrderRequest.e = trainTicketGeneralInfoItem.trainName;
        TrainTicketPurchasingListener trainTicketPurchasingListener = new TrainTicketPurchasingListener(this, 0);
        if (this.mPageContext != null) {
            TrainTicketPurchasingCallback trainTicketPurchasingCallback = new TrainTicketPurchasingCallback(trainTicketPurchasingListener);
            CompatDialog a = aav.a(submitOrderRequest, eay.a(R.string.loadingMessage));
            TrainManager.a = a;
            a.show();
            TrainRequestHolder.getInstance().sendSubmitOrder(submitOrderRequest, trainTicketPurchasingCallback);
        }
    }

    private String getSelectedDate(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.CHINA);
        Calendar instance = Calendar.getInstance();
        instance.set(6, instance.get(6) + this.mSeletedDateIndex);
        return simpleDateFormat.format(instance.getTime());
    }

    private boolean isShortestTimeTrain(int i) {
        List<TrainTicketGeneralInfoItem> data = getData();
        String str = ((TrainTicketGeneralInfoItem) data.get(i)).runningTime;
        int indexOf = str.indexOf(":");
        if (indexOf <= 0) {
            return false;
        }
        try {
            int parseInt = Integer.parseInt(str.substring(0, indexOf));
            int parseInt2 = Integer.parseInt(str.substring(indexOf + 1));
            for (TrainTicketGeneralInfoItem trainTicketGeneralInfoItem : data) {
                String str2 = trainTicketGeneralInfoItem.runningTime;
                int indexOf2 = str2.indexOf(":");
                if (indexOf2 > 0) {
                    try {
                        int parseInt3 = Integer.parseInt(str2.substring(0, indexOf2));
                        int parseInt4 = Integer.parseInt(str2.substring(indexOf2 + 1));
                        if (parseInt3 < parseInt) {
                            return false;
                        }
                        if (parseInt3 == parseInt && parseInt4 < parseInt2) {
                            return false;
                        }
                    } catch (NumberFormatException unused) {
                        continue;
                    }
                }
            }
            return true;
        } catch (NumberFormatException unused2) {
            return false;
        }
    }

    public void onSelectedDateChanged(int i) {
        this.mSeletedDateIndex = i;
        notifyDataSetChanged();
    }
}
