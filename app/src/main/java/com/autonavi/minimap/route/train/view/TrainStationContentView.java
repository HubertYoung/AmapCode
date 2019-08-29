package com.autonavi.minimap.route.train.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.AmapTextView;
import java.util.Calendar;
import java.util.Date;

public class TrainStationContentView extends LinearLayout implements OnClickListener {
    private static final int FAST_ANIMATION_DURATION = 300;
    private TranslateAnimation mBottomInAnimation;
    private AmapTextView mEndStationTextview;
    private ImageView mExchageSwitch;
    private CheckBox mHighTrainCheckBox;
    private RelativeLayout mLayoutChooseData;
    private AmapTextView mStartStationTextview;
    private CheckBox mStudentCheckBox;
    private TranslateAnimation mTopInAnimation;
    private a mTrainClicklistener;
    private AmapTextView mTrainDataTextView;
    private AmapTextView mTrainDataWeekTextView;

    private String getDayToZh(int i) {
        switch (i) {
            case 1:
                return "日";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                return null;
        }
    }

    public void setClickTrainContentListener(a aVar) {
        this.mTrainClicklistener = aVar;
    }

    public TrainStationContentView(Context context) {
        super(context);
        initView();
        setShowTicketData(new Date());
        initAnimation();
    }

    public TrainStationContentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
        setShowTicketData(new Date());
        initAnimation();
    }

    private void initView() {
        inflate(getContext(), R.layout.route_train_station_content_layout, this);
        this.mStartStationTextview = (AmapTextView) findViewById(R.id.train_station_from_keyword);
        this.mStartStationTextview.setOnClickListener(this);
        this.mEndStationTextview = (AmapTextView) findViewById(R.id.train_station_to_keyword);
        this.mEndStationTextview.setOnClickListener(this);
        this.mExchageSwitch = (ImageView) findViewById(R.id.train_exchage_poi);
        this.mExchageSwitch.setOnClickListener(this);
        this.mTrainDataTextView = (AmapTextView) findViewById(R.id.time_choose);
        this.mLayoutChooseData = (RelativeLayout) findViewById(R.id.layout_time_choose);
        this.mLayoutChooseData.setOnClickListener(this);
        this.mTrainDataWeekTextView = (AmapTextView) findViewById(R.id.time_show_week);
        this.mHighTrainCheckBox = (CheckBox) findViewById(R.id.train_check_hightrain);
        this.mStudentCheckBox = (CheckBox) findViewById(R.id.train_check_student);
    }

    private void initAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        this.mBottomInAnimation = translateAnimation;
        TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        this.mTopInAnimation = translateAnimation2;
        this.mTopInAnimation.setDuration(300);
        this.mBottomInAnimation.setDuration(300);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.train_station_from_keyword) {
            if (this.mTrainClicklistener != null) {
            }
        } else if (id == R.id.train_station_to_keyword) {
            if (this.mTrainClicklistener != null) {
            }
        } else if (id != R.id.layout_time_choose) {
            int i = R.id.train_exchage_poi;
        } else if (this.mTrainClicklistener != null) {
        }
    }

    public void setStationExchange() {
        this.mStartStationTextview.startAnimation(this.mBottomInAnimation);
        this.mEndStationTextview.startAnimation(this.mTopInAnimation);
        String charSequence = this.mStartStationTextview.getText().toString();
        this.mStartStationTextview.setText(this.mEndStationTextview.getText().toString());
        this.mEndStationTextview.setText(charSequence);
    }

    public AmapTextView getEndStationTextview() {
        return this.mEndStationTextview;
    }

    public AmapTextView getStartStationTextview() {
        return this.mStartStationTextview;
    }

    public AmapTextView getTrainDataTextView() {
        return this.mTrainDataTextView;
    }

    public void setStartPOI(String str) {
        if (str.endsWith("市")) {
            str = str.substring(0, str.length() - 1);
        } else if (str.endsWith("地区")) {
            str = str.substring(0, str.length() - 2);
        }
        this.mStartStationTextview.setText(str);
    }

    public void setEndPOI(String str) {
        this.mEndStationTextview.setText(str);
    }

    public void setShowTicketData(Date date) {
        StringBuilder sb = new StringBuilder();
        sb.append(date.getMonth() + 1);
        sb.append("月");
        sb.append(date.getDate());
        sb.append("日");
        this.mTrainDataTextView.setText(sb.toString());
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        AmapTextView amapTextView = this.mTrainDataWeekTextView;
        StringBuilder sb2 = new StringBuilder("周");
        sb2.append(getDayToZh(instance.get(7)));
        amapTextView.setText(sb2.toString());
    }

    public boolean getIsCheckHighTrain() {
        return this.mHighTrainCheckBox.isChecked();
    }

    public boolean getIsCheckStudent() {
        return this.mStudentCheckBox.isChecked();
    }

    public void setIsCheckHighTrain(boolean z) {
        this.mHighTrainCheckBox.setChecked(z);
    }

    public void setIsCheckStudent(boolean z) {
        this.mStudentCheckBox.setChecked(z);
    }
}
