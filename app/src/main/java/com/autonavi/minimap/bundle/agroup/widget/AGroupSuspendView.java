package com.autonavi.minimap.bundle.agroup.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.agroup.api.IDataService;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;

public class AGroupSuspendView extends LinearLayout implements OnClickListener {
    private a dataListener;
    private Context mContext;
    private ImageView mImageView;
    private boolean mIsDriveLongScene;
    private b mOnEntryEventListener;
    private TextView mTextView;
    private IDataService service;

    class a implements com.autonavi.minimap.bundle.agroup.api.IDataService.a {
        private a() {
        }

        /* synthetic */ a(AGroupSuspendView aGroupSuspendView, byte b) {
            this();
        }

        public final void onTeamInfoChanged() {
            AGroupSuspendView.this.updateGroupStatus();
        }

        public final void onMemberBaseInfoChanged() {
            AGroupSuspendView.this.updateGroupStatus();
        }

        public final void onMemberLocationInfoChanged() {
            AGroupSuspendView.this.updateGroupStatus();
        }

        public final void onTeamStatusChanged(TeamStatus teamStatus) {
            AGroupSuspendView.this.updateGroupStatus();
        }

        public final void onSuperGroupInfoChanged() {
            AGroupSuspendView.this.updateGroupStatus();
        }
    }

    public interface b {
        void a();
    }

    public AGroupSuspendView(Context context) {
        this(context, null);
    }

    public AGroupSuspendView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AGroupSuspendView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dataListener = new a(this, 0);
        this.mIsDriveLongScene = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AGroupSuspendView, i, 0);
        this.mIsDriveLongScene = obtainStyledAttributes.getBoolean(R.styleable.AGroupSuspendView_isDriveLongScene, false);
        obtainStyledAttributes.recycle();
        this.mContext = context;
        initDataService();
        initView(context);
    }

    public void setOnEntryEventListener(b bVar) {
        this.mOnEntryEventListener = bVar;
    }

    public void destroy() {
        if (this.service != null) {
            this.service.b((com.autonavi.minimap.bundle.agroup.api.IDataService.a) this.dataListener);
        }
        this.mOnEntryEventListener = null;
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(this.mIsDriveLongScene ? R.layout.agroup_long_pannel_layout : R.layout.agroup_suspend_view_layout, this, true);
        this.mImageView = (ImageView) findViewById(R.id.iv_group_status);
        this.mTextView = (TextView) findViewById(R.id.tv_group_desc);
        if (this.mIsDriveLongScene) {
            this.mTextView.getPaint().setFakeBoldText(true);
        }
        setOnClickListener(this);
        updateGroupStatus();
    }

    /* access modifiers changed from: private */
    public void updateGroupStatus() {
        int i = this.service != null ? this.service.j() > 0 ? this.service.j() : this.service.i() : 0;
        if (!this.service.m() || i <= 0) {
            this.mImageView.setImageResource(this.mIsDriveLongScene ? R.drawable.icon_c43 : R.drawable.icon_c45_selector);
            this.mTextView.setText(null);
            return;
        }
        this.mImageView.setImageResource(this.mIsDriveLongScene ? R.drawable.icon_c44 : R.drawable.icon_c46_selector);
        String valueOf = String.valueOf(i);
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            valueOf = cuh.l().b(i);
        }
        this.mTextView.setText(valueOf);
    }

    private void initDataService() {
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            this.service = cuh.l();
            if (this.service != null) {
                this.service.a(this.dataListener);
            }
        }
    }

    public void onClick(View view) {
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.c((String) "amapuri://AGroup/joinGroup");
        }
        if (this.mOnEntryEventListener != null) {
            this.mOnEntryEventListener.a();
        }
    }
}
