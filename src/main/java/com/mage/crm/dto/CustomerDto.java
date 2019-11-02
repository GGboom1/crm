package com.mage.crm.dto;

import com.mage.crm.vo.Customer;

public class CustomerDto extends Customer {
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
