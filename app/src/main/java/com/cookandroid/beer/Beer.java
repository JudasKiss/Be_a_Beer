package com.cookandroid.beer;
public class Beer {


    public String beerName;
    public String beerCountry;
    public String style;
    public String code;
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Beer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Beer(String beerName, String beerCountry, String style, String code, String url) {
        this.beerName = beerName;
        this.beerCountry = beerCountry;
        this.style = style;
        this.code = code;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Beer(String beerName, String beerCountry, String style) {
        this.beerName = beerName;
        this.beerCountry=beerCountry;
        this.style=style;
    }
    //이름
    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    //원산지
    public String getBeerCountry() {
        return beerCountry;
    }

    public void setBeerCountry(String beerCountry) {
        this.beerCountry = beerCountry;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    @Override
    public String toString() {
        return "Beer{" +
                "beerName='" + beerName + '\'' +
                ",beerCountry ='" + beerCountry + '\'' +
                ",Style ='" + style + '\'' +
                '}';
    }
}