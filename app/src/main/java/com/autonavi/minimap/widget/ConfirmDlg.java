package com.autonavi.minimap.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.TitleBar;
import com.feather.support.SoftInputAdjustRootLinearLayout;

public class ConfirmDlg extends bto {
    public static final String SP_KEY_log_state = "log_state";
    /* access modifiers changed from: private */
    public boolean alreayClick = false;
    /* access modifiers changed from: private */
    public OnClickListener listener;
    private Context mContext;
    private int mLayoutResId;

    static class ProtocalSpan extends URLSpan {
        public ProtocalSpan(String str) {
            super(str);
        }

        public void onClick(View view) {
            try {
                super.onClick(view);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(Color.rgb(0, 145, 255));
            textPaint.setUnderlineText(false);
        }
    }

    public ConfirmDlg(Activity activity, OnClickListener onClickListener, int i) {
        super(activity, R.style.custom_declare_dlg);
        requestWindowFeature(1);
        if (euk.a()) {
            euk.a((Dialog) this, 0);
        }
        this.listener = onClickListener;
        this.mContext = activity;
        this.mLayoutResId = i;
        if (i == R.layout.navi_declare || i == R.layout.navi_truck_declare || i == R.layout.navi_motorbike_declare) {
            setContentView(i);
            TitleBar titleBar = (TitleBar) findViewById(R.id.title);
            if (titleBar != null) {
                titleBar.setWidgetVisibility(1, 4);
            }
        } else if (i == R.layout.onfoot_declare) {
            setContentView(R.layout.onfoot_declare);
            TitleBar titleBar2 = (TitleBar) findViewById(R.id.title_bar);
            if (titleBar2 != null) {
                titleBar2.setWidgetVisibility(1, 8);
                titleBar2.setTitle(activity.getString(R.string.foot_navi_using_tips));
            }
        } else if (i == R.layout.ondest_declare) {
            setContentView(R.layout.ondest_declare);
            TitleBar titleBar3 = (TitleBar) findViewById(R.id.title_bar);
            if (titleBar3 != null) {
                titleBar3.setWidgetVisibility(1, 8);
                titleBar3.setTitle(activity.getString(R.string.dest_navi_using_tips));
            }
        } else if (i == R.layout.bus_declare) {
            setContentView(R.layout.bus_declare);
            TitleBar titleBar4 = (TitleBar) findViewById(R.id.title_bar);
            if (titleBar4 != null) {
                titleBar4.setWidgetVisibility(1, 8);
                titleBar4.setTitle(activity.getString(R.string.bus_navi_using_tips));
            }
        } else if (i == R.layout.taxi_declare) {
            setContentView(R.layout.taxi_declare);
            TitleBar titleBar5 = (TitleBar) findViewById(R.id.title_bar);
            if (titleBar5 != null) {
                titleBar5.setWidgetVisibility(1, 8);
                titleBar5.setTitle(activity.getString(R.string.autonavi_taxi_using_tips));
            }
            TextView textView = (TextView) findViewById(R.id.declare_info);
            String string = this.mContext.getString(R.string.taxi_declare);
            String string2 = AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_service_tips);
            int indexOf = string.indexOf(string2);
            SpannableString valueOf = SpannableString.valueOf(string);
            valueOf.setSpan(new ProtocalSpan(ConfigerHelper.getInstance().getServiceItemUrl()), indexOf, string2.length() + indexOf, 17);
            textView.setText(valueOf);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (i == R.layout.designated_driver_declarer) {
            setContentView(R.layout.designated_driver_declarer);
            ((ImageButton) findViewById(R.id.title_btn_left)).setVisibility(4);
            ((ImageButton) findViewById(R.id.title_btn_right)).setVisibility(4);
            ((TextView) findViewById(R.id.title_text_name)).setText(activity.getString(R.string.autonavi_car_driving_service_using_tips));
            String string3 = this.mContext.getString(R.string.designated_derived_declare_str);
            String string4 = activity.getString(R.string.autonavi_map_service_tip);
            int indexOf2 = string3.indexOf(string4);
            SpannableString valueOf2 = SpannableString.valueOf(string3);
            valueOf2.setSpan(new URLSpan(ConfigerHelper.getInstance().getServiceItemUrl()), indexOf2, string4.length() + indexOf2, 17);
            String string5 = activity.getString(R.string.autonavi_security_using_tips);
            int indexOf3 = string3.indexOf(string5);
            valueOf2.setSpan(new URLSpan("http://h5.edaijia.cn/amap/treaty.html"), indexOf3, string5.length() + indexOf3, 17);
            TextView textView2 = (TextView) findViewById(R.id.designated_driver_declare);
            textView2.setText(valueOf2);
            textView2.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (i == R.layout.looking_for_clean) {
            setContentView(R.layout.looking_for_clean);
            ((ImageButton) findViewById(R.id.title_btn_left)).setVisibility(4);
            ((ImageButton) findViewById(R.id.title_btn_right)).setVisibility(4);
            ((TextView) findViewById(R.id.title_text_name)).setText(activity.getString(R.string.autonavi_home_clean_using_tips));
            String string6 = this.mContext.getString(R.string.looking_for_clean_str);
            String string7 = activity.getString(R.string.autonavi_map_service_tips);
            int indexOf4 = string6.indexOf(string7);
            SpannableString valueOf3 = SpannableString.valueOf(string6);
            valueOf3.setSpan(new URLSpan(ConfigerHelper.getInstance().getServiceItemUrl()), indexOf4, string7.length() + indexOf4, 17);
            String string8 = activity.getString(R.string.autonavi_security_using_tips);
            int indexOf5 = string6.indexOf(string8);
            valueOf3.setSpan(new URLSpan("http://www.1jiajie.com/termsofservice.php"), indexOf5, string8.length() + indexOf5, 17);
            TextView textView3 = (TextView) findViewById(R.id.designated_driver_declare);
            textView3.setText(valueOf3);
            textView3.setMovementMethod(LinkMovementMethod.getInstance());
        } else if (i == R.layout.prevent_steal_clause_layout) {
            setContentView(i);
            View findViewById = findViewById(R.id.title_navi);
            if (findViewById != null) {
                findViewById.findViewById(R.id.title_btn_left).setVisibility(8);
                findViewById.findViewById(R.id.title_btn_right).setVisibility(8);
                ((TextView) findViewById.findViewById(R.id.title_text_name)).setText(activity.getString(R.string.autonavi_security_check));
            }
            ((TextView) findViewById(R.id.txt_declaration_top)).setText(activity.getString(R.string.autonavi_tip_360_1));
            ((TextView) findViewById(R.id.txt_declaration_centre)).setText(activity.getString(R.string.autonavi_tip_360_2));
            TextView textView4 = (TextView) findViewById(R.id.txt_declaration_bottom);
            String string9 = activity.getString(R.string.autonavi_360_tip_3);
            String string10 = activity.getString(R.string.autonavi_360_tip_4);
            int indexOf6 = string9.indexOf(string10);
            SpannableString valueOf4 = SpannableString.valueOf(string9);
            valueOf4.setSpan(new URLSpan("https://wap.amap.com/360/statement.html"), indexOf6, string10.length() + indexOf6, 17);
            textView4.setText(valueOf4);
            textView4.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            setContentView(i);
            TextView textView5 = (TextView) findViewById(R.id.tvServices);
            String string11 = activity.getString(R.string.autonavi_tips_agree_term);
            String string12 = activity.getString(R.string.autonavi_tips_term_string);
            int indexOf7 = string11.indexOf(string12);
            SpannableString valueOf5 = SpannableString.valueOf(string11);
            valueOf5.setSpan(new ProtocalSpan(ConfigerHelper.getInstance().getServiceItemUrl()), indexOf7, string12.length() + indexOf7, 17);
            textView5.setText(valueOf5);
            textView5.setMovementMethod(LinkMovementMethod.getInstance());
            TextView textView6 = (TextView) findViewById(R.id.user_experience);
            String string13 = activity.getString(R.string.autonavi_confirm_known_detail);
            String string14 = activity.getString(R.string.autonavi_known_detail);
            int indexOf8 = string13.indexOf(string14);
            SpannableString valueOf6 = SpannableString.valueOf(string13);
            valueOf6.setSpan(new ProtocalSpan("http://wap.amap.com/user/plan.html"), indexOf8, string14.length() + indexOf8, 17);
            textView6.setText(valueOf6);
            textView6.setMovementMethod(LinkMovementMethod.getInstance());
            CheckBox checkBox = (CheckBox) findViewById(R.id.cb_experience);
            checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(ConfirmDlg.SP_KEY_log_state, z);
                    LogConstant.isLogOn = z;
                }
            });
            checkBox.setChecked(true);
        }
    }

    public void setContentView(View view) {
        SoftInputAdjustRootLinearLayout softInputAdjustRootLinearLayout = new SoftInputAdjustRootLinearLayout(getContext());
        softInputAdjustRootLinearLayout.addView(view);
        view.setFitsSystemWindows(true);
        super.setContentView(softInputAdjustRootLinearLayout);
    }

    public void setContentView(int i) {
        SoftInputAdjustRootLinearLayout softInputAdjustRootLinearLayout = new SoftInputAdjustRootLinearLayout(getContext());
        LayoutInflater.from(getContext()).inflate(i, softInputAdjustRootLinearLayout, true).setFitsSystemWindows(true);
        super.setContentView(softInputAdjustRootLinearLayout);
    }

    public void show() {
        this.alreayClick = false;
        View findViewById = findViewById(R.id.cancel);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (ConfirmDlg.this.listener != null) {
                            ConfirmDlg.this.dismiss();
                            ConfirmDlg.this.listener.onClick(view);
                        }
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                }
            });
        }
        View findViewById2 = findViewById(R.id.confirm);
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ConfirmDlg.this.alreayClick = true;
                    try {
                        if (ConfirmDlg.this.listener != null) {
                            ConfirmDlg.this.dismiss();
                            ConfirmDlg.this.listener.onClick(view);
                        }
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                }
            });
        }
        ConfirmDlgLifeCircle.addPool(this.mLayoutResId, this);
        super.show();
    }

    public void dismiss() {
        ConfirmDlgLifeCircle.popPool(this.mLayoutResId, this);
        super.dismiss();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.alreayClick) {
            return true;
        }
        if (4 != keyEvent.getKeyCode()) {
            return super.onKeyDown(i, keyEvent);
        }
        View findViewById = findViewById(R.id.cancel);
        if (!(this.listener == null || findViewById == null)) {
            this.listener.onClick(findViewById);
        }
        dismiss();
        return true;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }
}
