package defpackage;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.carowner.roadcamera.page.RdCameraApplyFragment;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.widget.CarPlateInputView;

/* renamed from: bht reason: default package */
/* compiled from: RdCameraApplyPresenter */
public final class bht extends sw<RdCameraApplyFragment, bhp> {
    public bht(RdCameraApplyFragment rdCameraApplyFragment) {
        super(rdCameraApplyFragment);
        this.b = new bhp(this);
    }

    public final void onPageCreated() {
        String str;
        RdCameraApplyFragment rdCameraApplyFragment = (RdCameraApplyFragment) this.mPage;
        PageBundle arguments = rdCameraApplyFragment.getArguments();
        if (arguments != null) {
            if (!arguments.containsKey("rd_camera_payment_data_item") || !arguments.containsKey("rd_camera_payment_type")) {
                throw new IllegalArgumentException("parameters is incomplete!");
            }
            if (arguments.containsKey("bundle_key_h5_page_param")) {
                rdCameraApplyFragment.u = (bhs) arguments.getObject("bundle_key_h5_page_param");
            }
            rdCameraApplyFragment.s = (RdCameraPaymentItem) arguments.getObject("rd_camera_payment_data_item");
            rdCameraApplyFragment.t = (bho) arguments.getObject("rd_camera_payment_type");
            rdCameraApplyFragment.v = arguments.getBoolean("bundle_key_got_rule");
        }
        RdCameraApplyFragment rdCameraApplyFragment2 = (RdCameraApplyFragment) this.mPage;
        if (euk.a()) {
            int a = euk.a(rdCameraApplyFragment2.getContext());
            View contentView = rdCameraApplyFragment2.getContentView();
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + a, contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        NoDBClickUtil.a((View) (ImageButton) rdCameraApplyFragment2.getContentView().findViewById(R.id.btn_back), (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (RdCameraApplyFragment.this.c()) {
                    RdCameraApplyFragment.this.d();
                    return;
                }
                RdCameraApplyFragment.this.b();
                RdCameraApplyFragment.this.finish();
            }
        });
        ((ScrollView) rdCameraApplyFragment2.getContentView().findViewById(R.id.scroll_view)).setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                RdCameraApplyFragment.this.b();
                return false;
            }
        });
        NoDBClickUtil.a(rdCameraApplyFragment2.getContentView().findViewById(R.id.rd_camera_btn_loc), (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyFragment.this.b();
                RdCameraApplyFragment.c(RdCameraApplyFragment.this);
            }
        });
        rdCameraApplyFragment2.a = (TextView) rdCameraApplyFragment2.getContentView().findViewById(R.id.map_mark_tag);
        rdCameraApplyFragment2.c = (EditText) rdCameraApplyFragment2.getContentView().findViewById(R.id.tv_desc_input);
        NoDBClickUtil.a((View) rdCameraApplyFragment2.c, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyFragment.this.d();
                RdCameraApplyFragment.a(RdCameraApplyFragment.this, RdCameraApplyFragment.this.c);
            }
        });
        rdCameraApplyFragment2.c.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                StringBuilder sb = new StringBuilder(charSequence.toString());
                if (sb.length() < 140) {
                    RdCameraApplyFragment.this.d.setText(String.format(RdCameraApplyFragment.this.getString(R.string.rd_camera_apply_compensation_words_remaining), new Object[]{Integer.valueOf(140 - charSequence.length())}));
                    RdCameraApplyFragment.this.d.setTextColor(RdCameraApplyFragment.this.getResources().getColor(R.color.gray_disabled));
                    return;
                }
                RdCameraApplyFragment.this.c.removeTextChangedListener(this);
                sb.delete(140, charSequence.length());
                RdCameraApplyFragment.this.c.setText(sb.toString());
                RdCameraApplyFragment.this.c.setSelection(sb.length());
                RdCameraApplyFragment.this.c.addTextChangedListener(this);
                RdCameraApplyFragment.this.d.setText(String.format(RdCameraApplyFragment.this.getString(R.string.rd_camera_apply_compensation_words_remaining), new Object[]{Integer.valueOf(0)}));
                RdCameraApplyFragment.this.d.setTextColor(RdCameraApplyFragment.this.getResources().getColor(R.color.red));
            }
        });
        rdCameraApplyFragment2.d = (TextView) rdCameraApplyFragment2.getContentView().findViewById(R.id.words_remaining);
        rdCameraApplyFragment2.d.setText(String.format(rdCameraApplyFragment2.getString(R.string.rd_camera_apply_compensation_words_remaining), new Object[]{Integer.valueOf(140)}));
        rdCameraApplyFragment2.e = (CarPlateInputView) rdCameraApplyFragment2.getContentView().findViewById(R.id.rdcamera_btn_plate_city);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean(CarPlateInputView.BUNDLE_KEY_FROM_EXTERNAL, true);
        rdCameraApplyFragment2.e.setIsFromExternnal(true);
        if (rdCameraApplyFragment2.u != null && !TextUtils.isEmpty(rdCameraApplyFragment2.u.b)) {
            String substring = rdCameraApplyFragment2.u.b.substring(0, 1);
            pageBundle.putString(CarPlateInputView.BUNDLE_KEY_PLATE_NUMBER, rdCameraApplyFragment2.u.b.substring(1, rdCameraApplyFragment2.u.b.length()));
            pageBundle.putString(CarPlateInputView.BUNDLE_KEY_PLATE_PROVINCE_NAME, substring);
        }
        rdCameraApplyFragment2.e.setPrebuiltData(pageBundle);
        rdCameraApplyFragment2.e.setParentView(rdCameraApplyFragment2.getContentView());
        rdCameraApplyFragment2.e.setStatusUpdate(rdCameraApplyFragment2);
        rdCameraApplyFragment2.f = rdCameraApplyFragment2.getContentView().findViewById(R.id.car_engine_view);
        NoDBClickUtil.a(rdCameraApplyFragment2.f, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyFragment.this.d();
                RdCameraApplyFragment.a(RdCameraApplyFragment.this, RdCameraApplyFragment.this.g);
            }
        });
        rdCameraApplyFragment2.h = rdCameraApplyFragment2.getContentView().findViewById(R.id.car_engine_bottom_separator);
        rdCameraApplyFragment2.g = (EditText) rdCameraApplyFragment2.getContentView().findViewById(R.id.rd_camera_engine);
        rdCameraApplyFragment2.g.addTextChangedListener(rdCameraApplyFragment2.w);
        rdCameraApplyFragment2.g.setFilters(rdCameraApplyFragment2.x);
        NoDBClickUtil.a((View) rdCameraApplyFragment2.g, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyFragment.this.d();
                RdCameraApplyFragment.a(RdCameraApplyFragment.this, RdCameraApplyFragment.this.g);
            }
        });
        if (rdCameraApplyFragment2.u != null && !TextUtils.isEmpty(rdCameraApplyFragment2.u.e)) {
            rdCameraApplyFragment2.g.setText(rdCameraApplyFragment2.u.e);
        }
        rdCameraApplyFragment2.i = rdCameraApplyFragment2.getContentView().findViewById(R.id.car_frame_view);
        NoDBClickUtil.a(rdCameraApplyFragment2.i, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyFragment.this.d();
                RdCameraApplyFragment.a(RdCameraApplyFragment.this, RdCameraApplyFragment.this.j);
            }
        });
        rdCameraApplyFragment2.k = rdCameraApplyFragment2.getContentView().findViewById(R.id.car_frame_top_separator);
        rdCameraApplyFragment2.j = (EditText) rdCameraApplyFragment2.getContentView().findViewById(R.id.rd_camera_frame_no);
        rdCameraApplyFragment2.j.addTextChangedListener(rdCameraApplyFragment2.w);
        rdCameraApplyFragment2.j.setFilters(rdCameraApplyFragment2.x);
        NoDBClickUtil.a((View) rdCameraApplyFragment2.j, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyFragment.this.d();
                RdCameraApplyFragment.a(RdCameraApplyFragment.this, RdCameraApplyFragment.this.j);
            }
        });
        if (rdCameraApplyFragment2.u != null && !TextUtils.isEmpty(rdCameraApplyFragment2.u.d)) {
            rdCameraApplyFragment2.j.setText(rdCameraApplyFragment2.u.d);
        }
        rdCameraApplyFragment2.l = rdCameraApplyFragment2.getContentView().findViewById(R.id.phone_number_view);
        NoDBClickUtil.a(rdCameraApplyFragment2.l, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraApplyFragment.this.d();
                RdCameraApplyFragment.a(RdCameraApplyFragment.this, RdCameraApplyFragment.this.m);
            }
        });
        rdCameraApplyFragment2.m = (EditText) rdCameraApplyFragment2.getContentView().findViewById(R.id.phone_number);
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            ant e = iAccountService.e();
            if (e == null) {
                str = null;
            } else {
                str = e.h;
            }
            if (!TextUtils.isEmpty(str)) {
                rdCameraApplyFragment2.m.setText(str);
            }
            rdCameraApplyFragment2.m.addTextChangedListener(rdCameraApplyFragment2.w);
            NoDBClickUtil.a((View) rdCameraApplyFragment2.m, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    RdCameraApplyFragment.this.d();
                    RdCameraApplyFragment.a(RdCameraApplyFragment.this, RdCameraApplyFragment.this.m);
                }
            });
            if (rdCameraApplyFragment2.u != null && !TextUtils.isEmpty(rdCameraApplyFragment2.u.c)) {
                rdCameraApplyFragment2.m.setText(rdCameraApplyFragment2.u.c);
            }
            rdCameraApplyFragment2.n = (CheckBox) rdCameraApplyFragment2.getContentView().findViewById(R.id.agreement_checkbox);
            rdCameraApplyFragment2.n.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    RdCameraApplyFragment.this.a(false);
                }
            });
            rdCameraApplyFragment2.o = (TextView) rdCameraApplyFragment2.getContentView().findViewById(R.id.agreement_caption);
            NoDBClickUtil.a((View) rdCameraApplyFragment2.o, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    RdCameraApplyFragment.this.b();
                    aja aja = new aja("http://wap.amap.com/activity/wzcx/authorization.html");
                    aja.b = new ajf() {
                        public final defpackage.ajh.a l_() {
                            return new defpackage.ajh.a() {
                                public final String a() {
                                    return RdCameraApplyFragment.this.getString(R.string.refresh);
                                }

                                public final boolean b() {
                                    if (AnonymousClass1.this.a != null) {
                                        AnonymousClass1.this.a.a().reload();
                                    }
                                    return true;
                                }
                            };
                        }
                    };
                    aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                    if (aix != null) {
                        aix.a((bid) RdCameraApplyFragment.this, aja);
                    }
                }
            });
            rdCameraApplyFragment2.p = (TextView) rdCameraApplyFragment2.getContentView().findViewById(R.id.compensation_hint);
            rdCameraApplyFragment2.p.setText(rdCameraApplyFragment2.getString(R.string.rd_camera_apply_compensation_compensation_hint, rdCameraApplyFragment2.t.c));
            rdCameraApplyFragment2.q = (TextView) rdCameraApplyFragment2.getContentView().findViewById(R.id.compensation_details);
            NoDBClickUtil.a((View) rdCameraApplyFragment2.q, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    RdCameraApplyFragment.this.b();
                    aja aja = new aja(ConfigerHelper.getInstance().getRdcameraPaymentKnowMoreRuleUrl());
                    aja.b = new ajf() {
                        public final defpackage.ajh.a l_() {
                            return new defpackage.ajh.a() {
                                public final String a() {
                                    return RdCameraApplyFragment.this.getString(R.string.refresh);
                                }

                                public final boolean b() {
                                    if (AnonymousClass1.this.a != null) {
                                        AnonymousClass1.this.a.a().reload();
                                    }
                                    return true;
                                }
                            };
                        }
                    };
                    aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                    if (aix != null) {
                        aix.a((bid) RdCameraApplyFragment.this, aja);
                    }
                }
            });
            rdCameraApplyFragment2.r = (Button) rdCameraApplyFragment2.getContentView().findViewById(R.id.rd_camera_btn_submit);
            NoDBClickUtil.a((View) rdCameraApplyFragment2.r, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    RdCameraApplyFragment.i(RdCameraApplyFragment.this);
                }
            });
        }
        RdCameraApplyFragment rdCameraApplyFragment3 = (RdCameraApplyFragment) this.mPage;
        rdCameraApplyFragment3.a();
        rdCameraApplyFragment3.c.clearFocus();
        rdCameraApplyFragment3.g.clearFocus();
        rdCameraApplyFragment3.j.clearFocus();
        rdCameraApplyFragment3.m.clearFocus();
    }

    public final void onStart() {
        ((RdCameraApplyFragment) this.mPage).b();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((RdCameraApplyFragment) this.mPage).c()) {
            ((RdCameraApplyFragment) this.mPage).d();
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        ((RdCameraApplyFragment) this.mPage).b();
        return super.onBackPressed();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        RdCameraApplyFragment rdCameraApplyFragment = (RdCameraApplyFragment) this.mPage;
        if (i == 261 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("SelectFixPoiFromMapFragment.MapClickResult")) {
            rdCameraApplyFragment.b = (POI) pageBundle.getObject("SelectFixPoiFromMapFragment.MapClickResult");
            if (rdCameraApplyFragment.b != null) {
                rdCameraApplyFragment.a(true);
            }
            rdCameraApplyFragment.a.setText(Html.fromHtml(rdCameraApplyFragment.getString(R.string.rd_camera_apply_compensation_has_marked)));
        }
    }

    public final /* synthetic */ su a() {
        return new bhp(this);
    }
}
