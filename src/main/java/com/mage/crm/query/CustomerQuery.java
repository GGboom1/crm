package com.mage.crm.query;

public class CustomerQuery extends BaseQuery{
    private Integer khno;
    private String name;

    public Integer getKhno() {
        return khno;
    }

    public void setKhno(Integer khno) {
        this.khno = khno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
