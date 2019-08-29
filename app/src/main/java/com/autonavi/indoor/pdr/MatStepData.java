package com.autonavi.indoor.pdr;

public class MatStepData {
    public double angle_;
    public double angle_no_mag_;
    public double[] mat_data_ = new double[3];
    public int mat_num_;
    public int move_direction_;
    public double move_state_score_;
    public double pressure;
    public double q1_;
    public double q2_;
    public double q3_;
    public double q4_;
    public double rotate_rate_x_;
    public double rotate_rate_y_;
    public double rotate_rate_z_;
    public double showangle;
    public int step_;
    public double step_len_;
    public double step_len_f_;
    public double step_len_v_;
    public long timestamp_;

    public MatStepData() {
    }

    public MatStepData(int i, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, double d13, double d14, int i2, double d15, double d16) {
        double d17 = d5;
        double d18 = d6;
        double d19 = d7;
        double d20 = d8;
        this.step_ = i;
        this.angle_ = d;
        this.mat_num_ = 1;
        this.rotate_rate_x_ = d9;
        this.rotate_rate_y_ = d10;
        this.rotate_rate_z_ = d11;
        JNIWrapper.jniChangeCoordinate2Ground((float) d2, (float) d3, (float) d4, (float) d17, (float) d18, (float) d19, (float) d20);
        this.mat_data_[0] = JNIWrapper.mX;
        this.mat_data_[1] = JNIWrapper.mY;
        this.mat_data_[2] = JNIWrapper.mZ;
        this.step_len_ = d12;
        this.step_len_f_ = d13;
        this.step_len_v_ = d14;
        this.move_direction_ = i2;
        this.q1_ = d17;
        this.q2_ = d18;
        this.q3_ = d19;
        this.q4_ = d20;
        this.angle_no_mag_ = d15;
        this.move_state_score_ = d16;
    }

    public synchronized void AddMatData(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10) {
        synchronized (this) {
            try {
                JNIWrapper.jniChangeCoordinate2Ground((float) d, (float) d2, (float) d3, (float) d4, (float) d5, (float) d6, (float) d7);
                this.mat_data_[0] = ((this.mat_data_[0] * ((double) this.mat_num_)) + JNIWrapper.mX) / ((((double) this.mat_num_) * 1.0d) + 1.0d);
                this.mat_data_[1] = ((this.mat_data_[1] * ((double) this.mat_num_)) + JNIWrapper.mY) / ((((double) this.mat_num_) * 1.0d) + 1.0d);
                this.mat_data_[2] = ((this.mat_data_[2] * ((double) this.mat_num_)) + JNIWrapper.mZ) / ((((double) this.mat_num_) * 1.0d) + 1.0d);
                this.rotate_rate_x_ = ((this.rotate_rate_x_ * ((double) this.mat_num_)) + d8) / ((((double) this.mat_num_) * 1.0d) + 1.0d);
                this.rotate_rate_y_ = ((this.rotate_rate_y_ * ((double) this.mat_num_)) + d9) / ((((double) this.mat_num_) * 1.0d) + 1.0d);
                this.rotate_rate_z_ = ((this.rotate_rate_z_ * ((double) this.mat_num_)) + d10) / ((((double) this.mat_num_) * 1.0d) + 1.0d);
                this.mat_num_++;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public synchronized void update(int i, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, double d13, double d14, int i2, double d15, double d16) {
        double d17 = d5;
        double d18 = d6;
        double d19 = d7;
        double d20 = d8;
        synchronized (this) {
            try {
                this.step_ = i;
                this.angle_ = d;
                this.mat_num_ = 1;
                this.rotate_rate_x_ = d9;
                this.rotate_rate_y_ = d10;
                this.rotate_rate_z_ = d11;
                JNIWrapper.jniChangeCoordinate2Ground((float) d2, (float) d3, (float) d4, (float) d17, (float) d18, (float) d19, (float) d20);
                this.mat_data_[0] = JNIWrapper.mX;
                this.mat_data_[1] = JNIWrapper.mY;
                this.mat_data_[2] = JNIWrapper.mZ;
                this.step_len_ = d12;
                this.step_len_f_ = d13;
                this.step_len_v_ = d14;
                this.move_direction_ = i2;
                this.q1_ = d17;
                this.q2_ = d18;
                this.q3_ = d7;
                this.q4_ = d20;
                this.angle_no_mag_ = d15;
                this.move_state_score_ = d16;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public synchronized void copy(MatStepData matStepData) {
        matStepData.timestamp_ = this.timestamp_;
        matStepData.step_ = this.step_;
        matStepData.step_len_ = this.step_len_;
        matStepData.step_len_f_ = this.step_len_f_;
        matStepData.step_len_v_ = this.step_len_v_;
        matStepData.move_direction_ = this.move_direction_;
        matStepData.angle_ = this.angle_;
        matStepData.angle_no_mag_ = this.angle_no_mag_;
        matStepData.mat_data_[0] = this.mat_data_[0];
        matStepData.mat_data_[1] = this.mat_data_[1];
        matStepData.mat_data_[2] = this.mat_data_[2];
        matStepData.rotate_rate_x_ = this.rotate_rate_x_;
        matStepData.rotate_rate_y_ = this.rotate_rate_y_;
        matStepData.rotate_rate_z_ = this.rotate_rate_z_;
        matStepData.q1_ = this.q1_;
        matStepData.q2_ = this.q2_;
        matStepData.q3_ = this.q3_;
        matStepData.q4_ = this.q4_;
        matStepData.mat_num_ = this.mat_num_;
        matStepData.move_state_score_ = this.move_state_score_;
        matStepData.showangle = this.showangle;
        matStepData.pressure = this.pressure;
    }
}
