package com.cookandroid.beer;
public class Beer {


    public String beerName;
    public String beerCountry;
    public String style;
    public String Code;

    public Beer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Beer(String beerName, String beerCountry, String style, String Code) {
        this.beerName = beerName;
        this.beerCountry=beerCountry;
        this.style=style;
        this.Code=Code;
    }
    //이름
    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    //국가
    public String getBeerCountry() {
        return beerCountry;
    }

    public void setBeerCountry(String beerCountry) {
        this.beerCountry = beerCountry;
    }

    //스타일
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    //식별 코드
    public String getCode(){return Code;}

    public void setCode(String Code){this.Code = Code;}


    @Override
    public String toString() {
        return "Beer{" +
                "beerName='" + beerName + '\'' +
                ",beerCountry ='" + beerCountry + '\'' +
                ",Style ='" + style + '\'' +
                '}';
    }
}