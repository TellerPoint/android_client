package com.tellerpoint.app.adapter;

/**
 * Created by eit on 3/18/16.
 */
public class ItemObject {

    private String name;
    private int logo;

    public ItemObject(String name, int logo) {
        this.name = name;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
