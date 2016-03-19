package com.tellerpoint.app.rest.responses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tellerpoint.app.model.Merchant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eit on 2/1/16.
 */
public class MerchantResponse {
    @SerializedName("merchants")
    @Expose
    private List<Merchant> merchants = new ArrayList<Merchant>();

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }

    public MerchantResponse(){
        merchants = new ArrayList<Merchant>();
    }

    public static MerchantResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MerchantResponse merchantResponse = gson.fromJson(response, MerchantResponse.class);
        return merchantResponse;
    }
}
