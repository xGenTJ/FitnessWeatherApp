package com.example.jingt.testp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WorkOutSet {

    @SerializedName("data")
    private ArrayList<WorkOut> data;
    @SerializedName("more")
    private Boolean more;

    public ArrayList<WorkOut> getworkOutList() {
        return data;
    }

    public void setData(ArrayList<WorkOut> data) {
        this.data = data;
    }

    public Boolean getMore() {
        return more;
    }

    public void setMore(Boolean more) {
        this.more = more;
    }
}
