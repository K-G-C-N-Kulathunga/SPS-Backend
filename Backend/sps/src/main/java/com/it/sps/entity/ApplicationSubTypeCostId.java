package com.it.sps.entity;

import java.io.Serializable;
import java.util.Objects;

public class ApplicationSubTypeCostId implements Serializable {

    private String applicationType;
    private String applicationSubType;
    private String costItemCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationSubTypeCostId)) return false;
        ApplicationSubTypeCostId that = (ApplicationSubTypeCostId) o;
        return Objects.equals(applicationType, that.applicationType) &&
                Objects.equals(applicationSubType, that.applicationSubType) &&
                Objects.equals(costItemCode, that.costItemCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationType, applicationSubType, costItemCode);
    }
}
