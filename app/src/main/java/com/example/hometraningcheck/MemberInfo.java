package com.example.hometraningcheck;

import android.widget.EditText;

public class MemberInfo {
    private String name;
    private String recentWeight;
    private String wishWeight;
    private String wishDate;

    public MemberInfo(String name,String recentWeight,String wishWeight, String wishDate){
        this.name = name;
        this.recentWeight = recentWeight;
        this.wishWeight = wishWeight;
        this.wishDate = wishDate;
    }

    public String getName(){
        return this.name;
    }
    public void  setName(String name){
        this.name = name;
    }
    public String getRecentWeight(){
        return this.recentWeight;
    }
    public void  setRecentWeight(){
        this.recentWeight = recentWeight;
    }
    public String getWishWeight(String wishWeight){
        return this.wishWeight;
    }
    public void  setWishWeight(String wishWeight){
        this.wishWeight = wishWeight;
    }
    public String getWishDate(){
        return this.wishDate;
    }
    public void setWishDate(String wishDate){
        this.wishDate =wishDate;
    }
}
