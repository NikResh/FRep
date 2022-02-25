package com.example.loftmoney.api;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("id")
    private String id;

    @SerializedName("auth_token")
    private String auth_token;

    public String getStatus(){return status;}
    public String getId(){return id;}
    public String getAuthToken(){return auth_token;}
}
