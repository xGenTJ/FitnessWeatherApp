package com.example.jingt.testp;

import com.google.gson.annotations.SerializedName;

class Peptalks {


    @SerializedName("allowed")
    private Boolean allowed;

    @SerializedName("count")
    private Integer count;


    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
