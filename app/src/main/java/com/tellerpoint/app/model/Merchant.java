package com.tellerpoint.app.model;

/**
 * Created by eit on 3/18/16.
 */
public class Merchant {

    private String merchant_name;
    private String merchant_logo;

    public String getMerchantId() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    private String merchant_id;
    private String merchant_email;
    private String merchant_phone;
    private String merchant_vfcode;
    private String merchant_vfpin;
    private String merchant_vftoken;

    public String getAccountType() {
        return account_type;
    }

    public void setAccountType(String account_type) {
        this.account_type = account_type;
    }

    private String account_type;


    public Merchant(String merchant_name, String logo, String merchant_id, String account_type) {
        this.merchant_name = merchant_name;
        this.merchant_logo = logo;
        this.merchant_id = merchant_id;
        this.account_type = account_type;
    }

    public String getName() {
        return merchant_name;
    }

    public void setName(String name) {
        this.merchant_name = name;
    }

    public String getLogo() {
        return merchant_logo;
    }

    public void setLogo(String logo) {
        this.merchant_logo = logo;
    }
}
