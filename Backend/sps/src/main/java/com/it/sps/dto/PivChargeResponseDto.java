package com.it.sps.dto;

import java.math.BigDecimal;
import java.util.List;

public class PivChargeResponseDto {

    private List<PivChargeDto> charges;
    private BigDecimal totalAmount;

    public List<PivChargeDto> getCharges() {
        return charges;
    }

    public void setCharges(List<PivChargeDto> charges) {
        this.charges = charges;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
