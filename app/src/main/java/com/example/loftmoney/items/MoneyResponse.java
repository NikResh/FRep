package com.example.loftmoney.items;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoneyResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Item> moneyItemList;

    public String getStatus() {return status;}

    public List<Item> getMoneyItemList() {return moneyItemList;}

}
