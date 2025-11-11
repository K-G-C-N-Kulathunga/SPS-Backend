package com.it.sps.entity;

import java.io.Serializable;
import java.util.Objects;

public class SpestedyId implements Serializable {

    private String appointmentId;
    private String deptId;

    public SpestedyId() {}

    public SpestedyId(String appointmentId, String deptId) {
        this.appointmentId = appointmentId;
        this.deptId = deptId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpestedyId)) return false;
        SpestedyId that = (SpestedyId) o;
        return Objects.equals(appointmentId, that.appointmentId) &&
                Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, deptId);
    }
}
