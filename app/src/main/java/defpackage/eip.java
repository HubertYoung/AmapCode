package defpackage;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter;
import com.autonavi.minimap.route.train.page.TrainPlanListPage;

/* renamed from: eip reason: default package */
/* compiled from: TrainPlanListListener */
public final class eip {
    AbstractBasePage<?> a;
    public RelativeLayout b;
    public TextView c;
    public TextView d;
    public View e;
    public TrainPlanListAdapter f;
    public eio g;
    public OnClickListener h;

    public eip(AbstractBasePage<?> abstractBasePage) {
        this.a = abstractBasePage;
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean[] zArr) {
        if (this.b != null && zArr != null && zArr.length == 5) {
            if (zArr[0] || (zArr[1] && zArr[2] && zArr[3] && zArr[4])) {
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_no_condition)).setChecked(true);
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_condition_0)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_condition_1)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_condition_2)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_condition_3)).setChecked(false);
            } else {
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_no_condition)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_condition_0)).setChecked(zArr[1]);
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_condition_1)).setChecked(zArr[2]);
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_condition_2)).setChecked(zArr[3]);
                ((CheckBox) this.b.findViewById(R.id.train_type_filter_condition_3)).setChecked(zArr[4]);
            }
            if (this.g != null) {
                this.g.a();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(boolean[] zArr) {
        if (this.b != null && zArr != null && zArr.length == 5) {
            if (zArr[0] || (zArr[1] && zArr[2] && zArr[3] && zArr[4])) {
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_no_condition)).setChecked(true);
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_condition_0)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_condition_1)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_condition_2)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_condition_3)).setChecked(false);
            } else {
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_no_condition)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_condition_0)).setChecked(zArr[1]);
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_condition_1)).setChecked(zArr[2]);
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_condition_2)).setChecked(zArr[3]);
                ((CheckBox) this.b.findViewById(R.id.train_departure_filter_condition_3)).setChecked(zArr[4]);
            }
            if (this.g != null) {
                this.g.a();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c(boolean[] zArr) {
        if (this.b != null && zArr != null && zArr.length == 5) {
            if (zArr[0] || (zArr[1] && zArr[2] && zArr[3] && zArr[4])) {
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_no_condition)).setChecked(true);
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_condition_0)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_condition_1)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_condition_2)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_condition_3)).setChecked(false);
            } else {
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_no_condition)).setChecked(false);
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_condition_0)).setChecked(zArr[1]);
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_condition_1)).setChecked(zArr[2]);
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_condition_2)).setChecked(zArr[3]);
                ((CheckBox) this.b.findViewById(R.id.train_arrival_filter_condition_3)).setChecked(zArr[4]);
            }
            if (this.g != null) {
                this.g.a();
            }
        }
    }

    static /* synthetic */ void a(eip eip) {
        if (eip.a != null && eip.a.isAlive() && (eip.a instanceof TrainPlanListPage)) {
            TrainPlanListPage trainPlanListPage = (TrainPlanListPage) eip.a;
            View findViewById = trainPlanListPage.d.findViewById(R.id.train_plan_filter_cancel_mask);
            View findViewById2 = trainPlanListPage.d.findViewById(R.id.train_plan_filter_content);
            int measuredHeight = findViewById2.getMeasuredHeight();
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 200});
            ofInt.setDuration(200);
            ofInt.addUpdateListener(new AnimatorUpdateListener(findViewById, measuredHeight, findViewById2) {
                final /* synthetic */ View a;
                final /* synthetic */ int b;
                final /* synthetic */ View c;

                {
                    this.a = r2;
                    this.b = r3;
                    this.c = r4;
                }

                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    this.a.setAlpha(((float) (200 - intValue)) / 200.0f);
                    this.c.setTranslationY((float) ((this.b / 200) * intValue));
                    if (intValue == 200) {
                        TrainPlanListPage.this.d.setVisibility(8);
                    }
                }
            });
            ofInt.start();
        }
    }

    static /* synthetic */ void b(eip eip) {
        if (eip.a != null && eip.a.isAlive() && (eip.a instanceof TrainPlanListPage)) {
            ((TrainPlanListPage) eip.a).b();
        }
    }

    static /* synthetic */ boolean[] c(eip eip) {
        boolean[] zArr = new boolean[5];
        if (eip.b == null) {
            return zArr;
        }
        zArr[0] = ((CheckBox) eip.b.findViewById(R.id.train_type_filter_no_condition)).isChecked();
        zArr[1] = ((CheckBox) eip.b.findViewById(R.id.train_type_filter_condition_0)).isChecked();
        zArr[2] = ((CheckBox) eip.b.findViewById(R.id.train_type_filter_condition_1)).isChecked();
        zArr[3] = ((CheckBox) eip.b.findViewById(R.id.train_type_filter_condition_2)).isChecked();
        zArr[4] = ((CheckBox) eip.b.findViewById(R.id.train_type_filter_condition_3)).isChecked();
        return zArr;
    }

    static /* synthetic */ boolean[] d(eip eip) {
        boolean[] zArr = new boolean[5];
        if (eip.b == null) {
            return zArr;
        }
        zArr[0] = ((CheckBox) eip.b.findViewById(R.id.train_departure_filter_no_condition)).isChecked();
        zArr[1] = ((CheckBox) eip.b.findViewById(R.id.train_departure_filter_condition_0)).isChecked();
        zArr[2] = ((CheckBox) eip.b.findViewById(R.id.train_departure_filter_condition_1)).isChecked();
        zArr[3] = ((CheckBox) eip.b.findViewById(R.id.train_departure_filter_condition_2)).isChecked();
        zArr[4] = ((CheckBox) eip.b.findViewById(R.id.train_departure_filter_condition_3)).isChecked();
        return zArr;
    }

    static /* synthetic */ boolean[] e(eip eip) {
        boolean[] zArr = new boolean[5];
        if (eip.b == null) {
            return zArr;
        }
        zArr[0] = ((CheckBox) eip.b.findViewById(R.id.train_arrival_filter_no_condition)).isChecked();
        zArr[1] = ((CheckBox) eip.b.findViewById(R.id.train_arrival_filter_condition_0)).isChecked();
        zArr[2] = ((CheckBox) eip.b.findViewById(R.id.train_arrival_filter_condition_1)).isChecked();
        zArr[3] = ((CheckBox) eip.b.findViewById(R.id.train_arrival_filter_condition_2)).isChecked();
        zArr[4] = ((CheckBox) eip.b.findViewById(R.id.train_arrival_filter_condition_3)).isChecked();
        return zArr;
    }
}
