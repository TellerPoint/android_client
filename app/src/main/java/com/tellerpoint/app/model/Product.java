package com.tellerpoint.app.model;

/**
 * Created by eit on 8/29/15.
 */
public class Product {
    private String id;

    private String product_name;
    private String merchant_id;
    private String product_desc;
    private String product_img;
    private String product_code;
    private String product_amount;



    private String merchant_name;

    public Product(String id, String product_name, String merchant_id, String merchant_name, String product_desc, String product_img, String product_code, String product_amount) {
        this.id = id;
        this.product_name = product_name;
        this.merchant_id = merchant_id;
        this.product_desc = product_desc;
        this.product_img = product_img;
        this.product_code = product_code;
        this.product_amount = product_amount;
        this.merchant_name = merchant_name;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String  getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(String product_amount) {
        this.product_amount = product_amount;
    }


}
