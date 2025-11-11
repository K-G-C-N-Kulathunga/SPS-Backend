package com.it.sps.entity;

import java.io.Serializable;
import java.util.Objects;

public class ApplicationOtherAccountId implements Serializable {
    private String id;
    private String accountNo;

    public ApplicationOtherAccountId() {}

    public ApplicationOtherAccountId(String id, String accountNo) {
        this.id = id;
        this.accountNo = accountNo;
    }

    // equals() and hashCode() are required for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationOtherAccountId)) return false;
        ApplicationOtherAccountId that = (ApplicationOtherAccountId) o;
        return Objects.equals(id, that.id) && Objects.equals(accountNo, that.accountNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNo);
    }
}
