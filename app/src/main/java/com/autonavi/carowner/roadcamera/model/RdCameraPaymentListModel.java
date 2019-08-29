package com.autonavi.carowner.roadcamera.model;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.carowner.roadcamera.page.RdCameraPaymentListPage;
import com.autonavi.carowner.roadcamera.page.RdCameraPaymentTypeFragment;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.feedback.FeedbackRequestHolder;
import com.autonavi.minimap.feedback.param.UserpayTypeRequest;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public final class RdCameraPaymentListModel extends su<bhv> {
    public bhs c;
    private PaymentAdapter d;
    private PaymentAdapter e;

    public class PaymentAdapter extends BaseAdapter {
        private boolean isApplyAlready = false;
        private Context mContext;
        private LayoutInflater mInflater;
        private List<RdCameraPaymentItem> mItems = new ArrayList();

        class a {
            public TextView a;
            public TextView b;
            public TextView c;
            public TextView d;
            public TextView e;
            public TextView f;
            public TextView g;
            public TextView h;
            public TextView i;
            public ImageView j;
            public ImageView k;

            private a() {
            }

            /* synthetic */ a(PaymentAdapter paymentAdapter, byte b2) {
                this();
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public PaymentAdapter(Context context, boolean z) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
            this.isApplyAlready = z;
        }

        public void setData(List<RdCameraPaymentItem> list) {
            this.mItems.clear();
            this.mItems.addAll(list);
            notifyDataSetChanged();
        }

        public void addData(List<RdCameraPaymentItem> list) {
            this.mItems.addAll(list);
            notifyDataSetChanged();
        }

        public void clearData() {
            this.mItems.clear();
            notifyDataSetChanged();
        }

        public int getCount() {
            if (this.mItems == null) {
                return 0;
            }
            return this.mItems.size();
        }

        public Object getItem(int i) {
            return this.mItems.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = this.mInflater.inflate(R.layout.rd_camera_payment_list_item_layout, null);
                aVar = new a(this, 0);
                aVar.a = (TextView) view.findViewById(R.id.date);
                aVar.b = (TextView) view.findViewById(R.id.start);
                aVar.c = (TextView) view.findViewById(R.id.end);
                aVar.d = (TextView) view.findViewById(R.id.distance);
                aVar.e = (TextView) view.findViewById(R.id.time_spend);
                aVar.f = (TextView) view.findViewById(R.id.speed);
                aVar.g = (TextView) view.findViewById(R.id.apply_payment);
                aVar.h = (TextView) view.findViewById(R.id.state_info);
                aVar.i = (TextView) view.findViewById(R.id.submit_time);
                aVar.j = (ImageView) view.findViewById(R.id.type);
                aVar.k = (ImageView) view.findViewById(R.id.clock_separator);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            final RdCameraPaymentItem rdCameraPaymentItem = this.mItems.get(i);
            if (!this.isApplyAlready) {
                aVar.h.setVisibility(8);
                aVar.i.setVisibility(8);
                aVar.j.setVisibility(8);
                aVar.g.setVisibility(0);
                aVar.g.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        RdCameraPaymentListModel.this.a(rdCameraPaymentItem);
                    }
                });
            } else {
                aVar.h.setVisibility(0);
                aVar.h.setText(rdCameraPaymentItem.getStatusDescr());
                aVar.h.setTextColor(rdCameraPaymentItem.getStatusColor());
                aVar.i.setVisibility(0);
                aVar.i.setText(this.mContext.getString(R.string.rd_camera_payment_apply_submit_time, new Object[]{rdCameraPaymentItem.getReportTime()}));
                aVar.j.setVisibility(0);
                aVar.j.setImageResource(getTypeResource(rdCameraPaymentItem.getType()));
                aVar.g.setVisibility(8);
            }
            aVar.a.setText(rdCameraPaymentItem.getDateDesc());
            aVar.b.setText(rdCameraPaymentItem.start);
            aVar.c.setText(rdCameraPaymentItem.end);
            aVar.d.setText(rdCameraPaymentItem.getDistanceDescr());
            aVar.e.setText(rdCameraPaymentItem.getTimeCostDescr());
            aVar.f.setText(rdCameraPaymentItem.getSpeedDescr());
            if (i == 0) {
                LayoutParams layoutParams = (LayoutParams) aVar.k.getLayoutParams();
                layoutParams.height = agn.a(this.mContext, 17.0f);
                aVar.k.setLayoutParams(layoutParams);
            } else {
                LayoutParams layoutParams2 = (LayoutParams) aVar.k.getLayoutParams();
                layoutParams2.height = -1;
                aVar.k.setLayoutParams(layoutParams2);
            }
            return view;
        }

        private int getTypeResource(String str) {
            if (str.equals("6100")) {
                return R.drawable.rd_camera_corner_overspeed;
            }
            if (str.equals("6102")) {
                return R.drawable.rd_camera_corner_emergency;
            }
            if (str.equals("6101")) {
                return R.drawable.rd_camera_corner_bus;
            }
            return R.drawable.rd_camera_corner_other;
        }
    }

    public RdCameraPaymentListModel(bhv bhv) {
        super(bhv);
    }

    public final PaymentAdapter b() {
        if (this.d == null) {
            this.d = new PaymentAdapter(a(), false);
        }
        return this.d;
    }

    public final PaymentAdapter c() {
        if (this.e == null) {
            this.e = new PaymentAdapter(a(), true);
        }
        return this.e;
    }

    public final void a(final RdCameraPaymentItem rdCameraPaymentItem) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (iAccountService.a()) {
                UserpayTypeRequest userpayTypeRequest = new UserpayTypeRequest();
                userpayTypeRequest.c = "4";
                FeedbackRequestHolder.getInstance().sendUserpayType(userpayTypeRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
                    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                        try {
                            ((bhv) RdCameraPaymentListModel.this.a).d();
                            JSONObject jSONObject = null;
                            if (aosByteResponse != null) {
                                try {
                                    byte[] bArr = (byte[]) aosByteResponse.getResult();
                                    if (bArr != null) {
                                        String str = new String(bArr, "UTF-8");
                                        if (!TextUtils.isEmpty(str)) {
                                            jSONObject = new JSONObject(str);
                                        }
                                    }
                                } catch (Exception unused) {
                                }
                            }
                            if (jSONObject != null) {
                                if ("1".equals(jSONObject.optString("code"))) {
                                    List<bho> a2 = bho.a(jSONObject);
                                    PageBundle pageBundle = new PageBundle();
                                    pageBundle.putObject("rd_camera_payment_type_list_key", a2);
                                    pageBundle.putObject("rd_camera_payment_data_key", rdCameraPaymentItem);
                                    if (RdCameraPaymentListModel.this.c != null) {
                                        pageBundle.putObject("bundle_key_h5_page_param", RdCameraPaymentListModel.this.c);
                                    }
                                    ((RdCameraPaymentListPage) ((bhv) RdCameraPaymentListModel.this.a).mPage).startPage(RdCameraPaymentTypeFragment.class, pageBundle);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                        ((bhv) RdCameraPaymentListModel.this.a).d();
                        ToastHelper.showLongToast(RdCameraPaymentListModel.this.a().getString(R.string.ic_net_error_tipinfo));
                    }
                });
                ((bhv) this.a).a((AosRequest) userpayTypeRequest, a().getString(R.string.rd_camera_payment_loading));
                return;
            }
            iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        RdCameraPaymentListModel.this.a(rdCameraPaymentItem);
                    }
                }
            });
        }
    }
}
