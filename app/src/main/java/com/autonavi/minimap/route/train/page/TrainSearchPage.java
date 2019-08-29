package com.autonavi.minimap.route.train.page;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.presenter.TrainManager;
import com.autonavi.minimap.route.train.presenter.TrainManager.TrainStationCallback;
import com.autonavi.minimap.route.train.presenter.TrainManager.TrainStationSearchListener;
import com.autonavi.minimap.route.train.view.TrainSearchInfoView;
import com.autonavi.minimap.route.train.view.TrainSearchStationEndView;
import com.autonavi.minimap.route.train.view.TrainSearchStationStartView;
import com.autonavi.minimap.train.TrainRequestHolder;
import com.autonavi.minimap.train.param.StationRequest;
import java.lang.ref.WeakReference;

@PageAction("amap.life.action.TrainSearchFragment")
public class TrainSearchPage extends AbstractBasePage<ejn> {
    public View a;
    public View b;
    public EditText c;
    public EditText d;
    public EditText e;
    public TrainSearchStationStartView f;
    public TrainSearchStationEndView g;
    public TrainSearchInfoView h;
    public TextView i;
    public TextView j;
    public ImageButton k;
    public TextView l;
    public LinearLayout m;
    public TextView n;
    public TextView o;
    public OnKeyListener p = new OnKeyListener() {
        public final boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i == 66 && keyEvent.getAction() == 1) {
                TrainSearchPage.this.c();
                return true;
            } else if (i != 4 || keyEvent.getAction() != 1 || !TrainSearchPage.this.e.hasFocus()) {
                return false;
            } else {
                TrainSearchPage.this.e.clearFocus();
                return true;
            }
        }
    };
    private Handler q;

    public enum SearchType {
        TICKET_LIST,
        TRAIN_INFO
    }

    static final class a extends Handler {
        private WeakReference<View> a;
        private WeakReference<Context> b;

        a(Context context, View view) {
            this.b = new WeakReference<>(context);
            this.a = new WeakReference<>(view);
        }

        public final void handleMessage(Message message) {
            if (!(message.what != 2001 || this.a == null || this.b == null)) {
                View view = (View) this.a.get();
                Context context = (Context) this.b.get();
                if (!(view == null || context == null)) {
                    view.setFocusable(true);
                    view.requestFocus();
                    try {
                        ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(view, 0);
                    } catch (NullPointerException unused) {
                    }
                }
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.train_search_layout);
    }

    public final void a() {
        this.c.setText("");
        this.d.setText("");
        this.e.setText("");
        a((View) this.f);
        finish();
    }

    public final void b() {
        String obj = this.c.getText().toString();
        String obj2 = this.d.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            ToastHelper.showToast(getString(R.string.tt_empty_departure));
        } else if (TextUtils.isEmpty(obj2)) {
            ToastHelper.showToast(getString(R.string.tt_empty_destination));
        } else if (obj.trim().equals(obj2.trim())) {
            ToastHelper.showToast(getString(R.string.tt_departure_destination_same));
        } else {
            a((View) this.c);
            bid bid = (bid) ((ejn) this.mPresenter).mPage;
            if (bid != null) {
                TrainStationSearchListener trainStationSearchListener = new TrainStationSearchListener(obj, obj2, bid);
                StationRequest stationRequest = new StationRequest();
                stationRequest.b = obj;
                stationRequest.c = obj2;
                TrainStationCallback trainStationCallback = new TrainStationCallback(trainStationSearchListener);
                CompatDialog a2 = aav.a(stationRequest, eay.a(R.string.loadingMessage));
                TrainManager.a = a2;
                a2.show();
                TrainRequestHolder.getInstance().sendStation(stationRequest, trainStationCallback);
            }
        }
    }

    public final void c() {
        String obj = this.e.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            ToastHelper.showToast(getString(R.string.tt_empty_train_name));
            return;
        }
        a((View) this.e);
        TrainManager.a(obj, (bid) ((ejn) this.mPresenter).mPage);
    }

    public final void a(SearchType searchType) {
        if (searchType == SearchType.TICKET_LIST) {
            this.a.setEnabled(false);
            this.b.setEnabled(true);
            this.h.setVisibility(8);
            this.f.setVisibility(0);
            this.g.setVisibility(0);
            this.j.setTextColor(getContext().getResources().getColor(R.color.blue));
            this.i.setTextColor(getContext().getResources().getColor(R.color.gray));
            a(this.c);
            return;
        }
        if (searchType == SearchType.TRAIN_INFO) {
            this.a.setEnabled(true);
            this.b.setEnabled(false);
            this.h.setVisibility(0);
            this.f.setVisibility(8);
            this.g.setVisibility(8);
            this.i.setTextColor(getContext().getResources().getColor(R.color.blue));
            this.j.setTextColor(getContext().getResources().getColor(R.color.gray));
            a(this.e);
        }
    }

    private void a(EditText editText) {
        this.q = new a(getContext(), editText);
        Message message = new Message();
        message.what = 2001;
        this.q.sendMessageDelayed(message, 500);
    }

    private void a(View view) {
        if (this.q != null) {
            this.q.removeMessages(2001);
        }
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public final SearchType d() {
        return ((ejn) this.mPresenter).a;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ejn(this);
    }
}
