package com.cookandroid.beer;

public class MainbeerItem {
    String mbCompany;
    String mbName;
    String mbRating;
    int mbImage;
    String code;

    public MainbeerItem(String mbCompany, String mbName, String mbRating, int mbImage, String code) {
        this.mbCompany = mbCompany;
        this.mbName = mbName;
        this.mbRating = mbRating;
        this.mbImage = mbImage;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMbCompany() {
        return mbCompany;
    }

    public void setMbCompany(String mbCompany) {
        this.mbCompany = mbCompany;
    }

    public String getMbName() {
        return mbName;
    }

    public void setMbName(String mbName) {
        this.mbName = mbName;
    }

    public String getMbRating() {
        return mbRating;
    }

    public void setMbRating(String mbRating) {
        this.mbRating = mbRating;
    }

    public int getMbImage() {
        return mbImage;
    }

    public void setMbImage(int mbImage) {
        this.mbImage = mbImage;
    }
}
